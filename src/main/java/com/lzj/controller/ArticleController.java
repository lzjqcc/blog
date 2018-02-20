package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.constant.CommentTypeEnum;
import com.lzj.dao.AssortmentDao;
import com.lzj.dao.dto.AccountDto;
import com.lzj.dao.dto.ArticleDto;
import com.lzj.domain.*;
import com.lzj.exception.BusinessException;
import com.lzj.service.ArticleService;

import com.lzj.service.impl.CommentService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by li on 17-8-6.
 */
@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    AssortmentDao assortmentDao;
    private final static Map<Integer, List<String>> picMap = new ConcurrentHashMap<>();

    /**
     * bu需要登录
     * @param id
     * @return
     */
    @RequestMapping(value = "findById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<Map<String, Object>> findById(@PathVariable("id")Integer id){
        ResponseVO<Map<String, Object>> responseVO = new ResponseVO<>();
        Article article= articleService.findById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("id", id);
        map.put("visit_times", article.getVisitTimes()+1);
        articleService.updateByMap(map);
        Map<String, Object> entityMap = new HashMap<>();
        entityMap.put("article", article);
        responseVO.setSuccess(true);
        responseVO.setResult(entityMap);
        return responseVO;
    }

    /**
     * 需要登录
     * 文章保存成功后跳转到显示文章那一页
     *
     * @param
     * @param content
     * @param title
     * @param assortment
     * @param top
     * @param toTop
     * @return
     */
    @RequestMapping(value = "insertArticle", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO insertArticle(@RequestBody ArticleDto articleDto) {
       /* boolean isAccess =  ComentUtils.vailedToken(response, request);
        if (!isAccess){
            return;
        }*/

        Account account = ComentUtils.getCurrentAccount();
        Article article = new Article();
        BeanUtils.copyProperties(articleDto, article);
        article.setCurrentAccountId(account.getId());
        AccountDto dto = new AccountDto();
        BeanUtils.copyProperties(account,dto);
        articleService.insertArticle(article, dto, articleDto.getAssortment(), picMap.get(account.getId()));
        picMap.remove(account.getId());
        return ComentUtils.buildResponseVO(true, "操作成功", article.getId());
       // return "forward:/articles/articlePage";
    }
    /**不需要登录
     *支持数
     */
    @RequestMapping(value = "support")
    @ResponseBody
    public void suppert(@RequestParam("id")Integer id,@RequestParam("support")Integer support){
       Map<String,Object> map=new HashMap<>();
       map.put("id",id);
       map.put("support",support);
       articleService.updateByMap(map);
    }

    /**不需要登录
     * 反对数
     * @param id
     * @param dislike
     */
    @RequestMapping(value = "dislike")
    @ResponseBody
    public void dislike(@RequestParam("id")Integer id,@RequestParam("dislike")Integer dislike){
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("dislike", dislike);
        articleService.updateByMap(map);
    }
    /**这个不需要登录
     * 根据user_id来查询显示博客分类及文章个数
     */
    @RequestMapping(value = "findGroup/{userId}" )
    @ResponseBody
    public ResponseVO<List<Assortment>> findGroupByUserId(@PathVariable Integer userId){
        if (Objects.isNull(userId)) {
            userId = ComentUtils.getCurrentAccount().getId();
        }
        return ComentUtils.buildResponseVO(true, "操作成功", articleService.findAssortmentByUserId(userId));
    }
    @RequestMapping(value = "updateAssortment")
    public void updateAssortment(@RequestParam("assortmentId")Integer assortmentId ,@RequestParam("assortment")String assortment){
        assortmentDao.updateAssortment(assortment,null,assortmentId);
    }

    /**不需要登录
     * 描述：点击具体的某个分类，显示该分类下面所有的文章
     * @return
     */
    @RequestMapping(value = "specificChildrend",method = RequestMethod.GET)
    @ResponseBody
    public List<Article> specificChildrend(@RequestParam("userId")Integer userId,
                                                   @RequestParam("assortment")String assortment,
                                           @RequestParam("currentPage") Integer currentPage,
                                           @RequestParam("pageSize")Integer pageSize){
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        return articleService.specificChildren(userId, assortment,page);
    }

    /**不需要登录
     * 获取阅读排名
     * @param userId
     * @return
     */
    @RequestMapping(value = "findHistoryMax",method = RequestMethod.GET)
    @ResponseBody
    public List<Article> findHistoryMax(@RequestParam("userId") Integer userId,
                                        @RequestParam("currentPage") Integer currentPage,
                                        @RequestParam("pageSize")Integer pageSize){
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        return articleService.findHistoryMax(userId, page);
    }

    /**不需要登录
     * 总浏览   每月写作数目
     */
    @RequestMapping(value = "findDateNum/{userId}")
    @ResponseBody
    public Map<String, List<Article>> findDateNum(@PathVariable("userId")Integer userId,
                                                  @RequestParam("currentPage") Integer currentPage,
                                                  @RequestParam("pageSize")Integer pageSize) {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        return articleService.findDateNum(userId, page);
    }
    @RequestMapping(value = "findGroupByCreateTime/{userId}")
    @ResponseBody
    public ResponseVO<Map<String, Integer>> findGroupByCreateTime(@PathVariable Integer userId) {
      return    articleService.findGroupByCreateTime(userId);
    }
    /**
     * 不需要登录
     * 点击单月显示这个月的文章
     * singleDate:2017年6月
     */
    @RequestMapping(value = "findSingleDateNum")
    @ResponseBody
    public List<Article> findSingleDateNum(@RequestParam("userId")Integer userId,@RequestParam("singleDate")String singleDate,
                                           @RequestParam("currentPage") Integer currentPage,
                                           @RequestParam("pageSize")Integer pageSize){
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        Map<String,List<Article>> listMap=articleService.findDateNum(userId,page);
        return listMap.get(singleDate);
    }
    @RequestMapping(value = "/uploadArticlePic",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadArticlePic(@RequestParam(value = "image", required = true) MultipartFile uploadFile) throws IOException {
            BufferedInputStream inputStream = null;
            File file = null;
            try {
                inputStream = new BufferedInputStream(uploadFile.getInputStream());

                Account user = ComentUtils.getCurrentAccount();
                ComentUtils.sureLogin(user);
                String articlePic = ComentUtils.ARTICLE_PIC + File.separator+ user.getId().toString();
                File parent=new File(articlePic);
                if (!parent.exists()){
                    parent.mkdirs();
                }
                file = new File(parent, System.currentTimeMillis()+"."+uploadFile.getOriginalFilename().split("\\.")[1]);
                Files.copy(inputStream, Paths.get(articlePic, file.getName()));
                String picURL = ComentUtils.getImageURL(file.getPath());
                Map<String,String> map=new HashMap<>();
                if (!picMap.containsKey(user.getId())){
                    List<String> list = new ArrayList<>();
                    list.add(file.getPath());
                    picMap.put(user.getId(),list);
                }else {
                    picMap.get(user.getId()).add(file.getPath());
                }
                map.put("picURL",picURL);
                return map;
            } catch (IOException e) {
                if (file != null) {
                    file.deleteOnExit();
                }
                throw new BusinessException(300, "图片上传失败");
            } finally {
                if (inputStream != null) {
                    ComentUtils.closeStream(inputStream);
                }
            }
    }

    /**需要登录
     * 不保存文章,转到文章管理页面
     */
    @RequestMapping(value = "abandonArticle")
    public String abandonArticle(){
        Account user= ComentUtils.getCurrentAccount();
        List<String> list=picMap.get(user.getId());
        if (list!=null && list.size()>0){
            for (String picURL:list){
                File file=new File(picURL);
                file.deleteOnExit();
            }
        }
        return "manager";
    }

    /**
     * 需要登录
     * 删除文章：删除mysql，mongo以及相关的图片
     */
    @RequestMapping(value = "deleteArticle")
    public void deleteArticle(@RequestParam("id")Integer articleId){

    }
}
