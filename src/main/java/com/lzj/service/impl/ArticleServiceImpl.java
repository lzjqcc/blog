package com.lzj.service.impl;

import com.lzj.VO.ArticleMongo;
import com.lzj.VO.ResponseVO;
import com.lzj.dao.ArticleDao;
import com.lzj.dao.AssortmentDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.*;
import com.lzj.exception.BusinessException;
import com.lzj.exception.SystemException;
import com.lzj.service.ArticleService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 如果要
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    AssortmentDao assortmentDao;
    @Autowired
    MongoTemplate template;
    @Value("${spring.data.mongodb.database}")
    String mongoDB;
    public static final String ARTICLE_DIR = "./src/main/resources/article/username/assortment/title";
    @Transactional
    @Override
    public void insertArticle(Article article, AccountDto user, String assortment, List<String> picURLs) {
        if (assortment==null){
            throw new BusinessException(232,"文章分类不能为空");
        }
        Assortment assortmentEntity = assortmentDao.findByUserIdAndName(user.getId(), assortment);
        if (assortmentEntity == null ) {
            assortmentEntity = new Assortment();
            assortmentEntity.setArticleNum(1);
            assortmentEntity.setAssortmentName(assortment);
            assortmentEntity.setCurrentAccountId(user.getId());
            assortmentDao.insertAssortment(assortmentEntity);
        } else {
            assortmentDao.updateAssortment(null, assortmentEntity.getArticleNum() + 1, assortmentEntity.getId());
        }
        article.setAssortmentId(assortmentEntity.getId());
        ArticleMongo articleMongo = new ArticleMongo();

        articleDao.insertArticle(article);
        BeanUtils.copyProperties(article, articleMongo);
        if (picURLs!=null){
            articleMongo.setPicList(picURLs);
            Iterator<String> iterator=picURLs.iterator();
            while (iterator.hasNext()){
                String articlePic=iterator.next();
                String []array=articlePic.split("/");
                String name=array[array.length-1];
                if (!article.getContent().contains(name)){
                    File file=new File(articlePic);
                    file.deleteOnExit();
                    iterator.remove();
                }
            }
        }

        template.insert(articleMongo, mongoDB);
    }
    @Override
    public Article findById(Integer id) {
        Article article = articleDao.findById(id);
        if (article == null) {
            throw new BusinessException(1, "没有找到文章");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(article.getId()));
        ArticleMongo articleMongo = template.findOne(query, ArticleMongo.class,mongoDB);
        article.setContent(articleMongo.getContent());
        return article;
    }

    @Override
    public List<Article> findAllByUserId(Integer userId, Page page) {
        List<Article> list = articleDao.findByUserId(userId,  page);
        return list;
    }
    @Override
    public void updateByMap(Map map) {
        articleDao.updateByMap(map);
    }
    @Override
    public List<Assortment> findAssortmentByUserId(Integer userId) {
        return assortmentDao.findAssortmentByUserId(userId);
    }
    @Override
    public List<Article> specificChildren(Integer userId, String assortment,Page page) {
        Assortment assortmentEntity = assortmentDao.findByUserIdAndName(userId, assortment);
        return articleDao.findBycurrentAccountIdAndAssortment(userId, assortmentEntity.getId(), page);
    }
    @Override
    public List<Article> findHistoryMax(Integer userId, Page page) {
        return articleDao.findHistoryMax(userId, page);
    }
    @Override
    public void deleteArticle(Integer id) {
        articleDao.deleteById(id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        List<ArticleMongo> list=template.findAllAndRemove(query,ArticleMongo.class,mongoDB);
        if (list!=null && list.size()>0){
            ArticleMongo mongo=list.get(0);
            List<String> list1=mongo.getPicList();
            for (String pic:list1){
                File file=new File(pic);
                file.deleteOnExit();;
            }
        }

    }
    @Override
    public Map<String, List<Article>> findDateNum(Integer userId, Page page) {
        List<Article> list = articleDao.findByUserId(userId,  page);
        System.out.println(list.size());
        Map<String, List<Article>> map = new LinkedHashMap<>();
        for (Article article : list) {
            String formatDate = getYearAndMon(article.getCreateTime());
            if (!map.containsKey(formatDate)) {
                List<Article> articles = new ArrayList<>();
                articles.add(article);
                map.put(formatDate, articles);
            } else {
                List<Article> articles = map.get(formatDate);
                articles.add(article);
            }
        }
        return map;
    }

    @Override
    public ResponseVO<Map<String, Integer>> findGroupByCreateTime(Integer userId) {
        List<Article> list = articleDao.findGroupByCreateTime(userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Map<String, Integer> map = list.stream().collect(Collectors.toMap(t->format.format(t.getCreateTime()), Article::getCount));
        return ComentUtils.buildResponseVO(true, "操作成功", map);
    }

    private String getYearAndMon(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");

        return format.format(date);
    }

    /**
     * todo 将文章内容放到mongo里
     * contentURL  resources/article/作者名称/分类（默认是default）/文章名
     * 将字段contentURL中的内容放到content中
     *
     * @param article
     * @return
     */
    @Deprecated
    protected Article urlToContent(Article article) {
        // File file=new File(article.getContentURL());
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(""))));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            article.setContent(builder.toString());
        } catch (FileNotFoundException e) {
            throw new SystemException(300, "读取文件不存在", this.getClass().getSimpleName(), "urlToContent", e.toString());
        } catch (IOException e) {
            throw new SystemException(301, "io流异常", this.getClass().getSimpleName(), "urlToContent", e.toString());
        } finally {
            if (reader != null) {
                ComentUtils.closeStream(reader);
            }
        }
        return article;
    }

}
