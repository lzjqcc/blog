package com.lzj.controller;

import com.lzj.dao.AssortmentDao;
import com.lzj.domain.Article;
import com.lzj.domain.Assortment;
import com.lzj.domain.LimitCondition;
import com.lzj.domain.User;
import com.lzj.service.ArticleService;

import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
    AssortmentDao assortmentDao;
    private final static Map<Integer, List<String>> picMap = new ConcurrentHashMap<>();
    @RequestMapping(value = "test" ,method = RequestMethod.GET)
    @ResponseBody
    public String test(){
        return "test";
    }


    /**
     * bu需要登录
     * @param id
     * @return
     */
    @RequestMapping(value = "findById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Article findById(@PathVariable("id")Integer id){
        Article article= articleService.findById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("id", id);
        map.put("visit_times", article.getVisitTimes()+1);
        articleService.updateByMap(map);
        return article;
    }

    /**
     * 需要登录
     * 文章保存成功后跳转到显示文章那一页
     *
     * @param session
     * @param content
     * @param title
     * @param assortment
     * @param top
     * @param toTop
     * @return
     */
    @RequestMapping(value = "insertArticle", method = RequestMethod.POST)
    @ResponseBody
    public void insertArticle(HttpSession session,
                              @RequestParam(name = "content") String content,
                              @RequestParam(name = "title") String title,
                              @RequestParam(name = "assortment", required = false) String assortment,
                              @RequestParam(name = "top", required = false) Integer top,
                              @RequestParam(name = "toTop", required = false) Boolean toTop,
                              HttpServletResponse response, HttpServletRequest request) {
        boolean isAccess =  ComentUtils.vailedToken(response, request);
        if (!isAccess){
            return;
        }
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setTop(top);
        article.setToTop(toTop);
        article.setCurrentAccountId(((User) session.getAttribute("user")).getId());

        articleService.insertArticle(article, (User) session.getAttribute("user"), assortment, picMap.get(((User) session.getAttribute("user")).getId()));

        picMap.remove(((User) session.getAttribute("user")).getId());
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
    @RequestMapping(value = "findGroup" )
    @ResponseBody
    public List<Assortment> findGroupByUserId(@RequestParam("userId")Integer userId){
       return articleService.findAssortmentByUserId(userId);
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
                                           @RequestParam("first") Integer first,@RequestParam("second")Integer second){
        return articleService.specificChildren(userId, assortment,new LimitCondition(first,second));
    }

    /**不需要登录
     * 获取阅读排名
     * @param userId
     * @return
     */
    @RequestMapping(value = "findHistoryMax",method = RequestMethod.GET)
    @ResponseBody
    public List<Article> findHistoryMax(@RequestParam("userId") Integer userId,@RequestParam("first") Integer first,@RequestParam("second")Integer second){
        return articleService.findHistoryMax(userId,new LimitCondition(first,second));
    }

    /**不需要登录
     * 总浏览   每月写作数目
     */
    @RequestMapping(value = "findDateNum")
    @ResponseBody
    public Map<String, List<Article>> findDateNum(@RequestParam("userId")Integer userId) {
        return articleService.findDateNum(userId);
    }
    /**
     * 不需要登录
     * 点击单月显示这个月的文章
     * singleDate:2017年6月
     */
    @RequestMapping(value = "findSingleDateNum")
    @ResponseBody
    public List<Article> findSingleDateNum(@RequestParam("userId")Integer userId,@RequestParam("singleDate")String singleDate){
        Map<String,List<Article>> listMap=articleService.findDateNum(userId);
        return listMap.get(singleDate);
    }

    /**需要登录
     *
     * 上传文章相关的图片
     * @return
     */

    /**需要登录
     * 显示单月文章数的页面
     */
    @RequestMapping(value = "findSingleDateNumPage")
    public String findSinglePage(){
        return "findSinglePage";
    }
    @RequestMapping(value = "/uploadPic",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadArticlePic(@RequestParam(value = "pic", required = true) MultipartFile uploadFile, HttpSession session) throws IOException {
        if (uploadFile != null) {
            BufferedInputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            try {
                inputStream = new BufferedInputStream(uploadFile.getInputStream());
                User user = (User) session.getAttribute("user");
                ComentUtils.sureLogin(user);
                String articlePic = ComentUtils.ARTICLE_PIC + "/" + user.getId().toString();
                String []dd=uploadFile.getOriginalFilename().split("\\.");
                File parent=new File(articlePic);
                if (!parent.exists()){
                    parent.mkdirs();
                }
                File file = new File(parent, System.currentTimeMillis()+"."+uploadFile.getOriginalFilename().split("\\.")[1]);
                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buff = new byte[1024*2];
                int length;
                while ((length = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, length);
                }
                outputStream.flush();
                String picURL=ComentUtils.HOST+file.getPath().split("static")[1];
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
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    ComentUtils.closeStream(inputStream);
                }
                if (outputStream != null) {
                    ComentUtils.closeStream(outputStream);
                }
            }
        }
        return null;
    }
    /**需要登录
     * 不保存文章,转到文章管理页面
     */
    @RequestMapping(value = "abandonArticle")
    public String abandonArticle(HttpSession session){
        User user= (User) session.getAttribute("user");
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
