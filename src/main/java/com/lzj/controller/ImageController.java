package com.lzj.controller;

import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Paths;

@Controller
public class ImageController {
    @Autowired
    private ResourceLoader resourceLoader;
    /**{filename:.+} 匹配 a.jpg
     * 获取图片
     * @param firstDir
     * @param filename
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{firstDir}/{userId}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable("firstDir") String firstDir,
                                     @PathVariable("filename") String filename,
                                     @PathVariable("userId") String userId) {

        try {
            String dir = "";
            if (ComentUtils.ARTICLE.equals(firstDir)){
                dir = ComentUtils.ARTICLE_PIC+"/"+userId;
            }else if (ComentUtils.ICON.equals(firstDir)){
                dir = ComentUtils.ICON_DIR+"/"+ userId;
            }else {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(dir, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
