package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.constant.ImageTypeEnum;
import com.lzj.domain.Account;
import com.lzj.domain.Page;
import com.lzj.exception.BusinessException;
import com.lzj.service.impl.PictureGroupService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageController {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private PictureGroupService pictureGroupService;
    /**{filename:.+} 匹配 a.jpg
     * 文章 /articlepic/1/2344.jpg
     *       头像   /icon/1/1234.jpg
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
            if (ImageTypeEnum.ARTICLEICON.type.equals(firstDir)){
                dir = ComentUtils.ARTICLE_PIC+"/"+userId;
            }else if (ImageTypeEnum.HEADICON.type.equals(firstDir)){
                dir = ComentUtils.ICON_DIR+"/"+ userId;
            }else if (ImageTypeEnum.PICTUREICON.type.equals(firstDir)){

            } else {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(dir, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/pictureGroup")
    public ResponseVO gropFile() {
        return ComentUtils.buildResponseVO(true, "可以访问", null);
    }
    /**
     * /picture/pictureDir/userId/pictureGroupId/pictureName
     */
    @RequestMapping(method =  RequestMethod.GET,value = "/picture/{userId}/{pictureGroupId}/{pictureName:.+}")
    public ResponseEntity<?> getPicture(@PathVariable("userId") String userId,
                                        @PathVariable("pictureGroupId") String pictureGroupId,@PathVariable("pictureName") String pictureName) {
        String dir = getPictureDir(userId, pictureGroupId);
        return ResponseEntity.ok(resourceLoader.getResource("file:"+Paths.get(dir,pictureName)));

    }
    @RequestMapping(value = "/uploadPicture",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadArticlePic(@RequestParam(value = "image") MultipartFile uploadFile,
                                               @RequestParam(value = "groupId")Integer groupId,
                                               HttpSession session) throws IOException {
        BufferedInputStream inputStream = null;
        File file =null;
        try {
            Account account = (Account) session.getAttribute("user");
            inputStream = new BufferedInputStream(uploadFile.getInputStream());
            File dir = new File(getPictureDir(account.getId()+"", groupId+""));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(dir, System.currentTimeMillis() + "." + uploadFile.getOriginalFilename().split("\\.")[1]);
            Files.copy(inputStream, Paths.get(dir.getPath(), file.getName()));
            Map<String, String> map = new HashMap<>();
            map.put("pictureURL",ComentUtils.getImageURL(file.getPath()));
            return map;
        }catch (Exception e) {
            if (file !=null) {
                file.deleteOnExit();;
            }
            throw new BusinessException(200, "图片上传失败");
        }finally {
            if (inputStream != null) {
                ComentUtils.closeStream(inputStream);
            }
        }
    }
    @RequestMapping(method = RequestMethod.POST,value = "/pictureGroup")
    @ResponseBody
    public Map<String, Object> getPictureGroup(@RequestParam("accountId")Integer accoutId,
                                               @RequestBody Page page,
                                               HttpSession session) {
        return null;
    }
    private String getPictureDir(String userId,String groupId) {
        return  ComentUtils.PICTURE_DIR+File.separator+userId+File.separator+groupId;
    }
}
