package com.lzj.controller;

import com.lzj.domain.User;
import com.lzj.exception.BusinessException;
import com.lzj.service.UserService;
import com.lzj.utils.ComentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.Buffer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息修改，头像上传,查看个人信息
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户详细信息页面
     *
     * @return
     */
    @RequestMapping(value = "userDetailPage", method = RequestMethod.GET)
    public String userDetail() {
        return "userDetail";
    }

    @RequestMapping(value = "getUserDetail", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam("id") Integer id) {

        return userService.findById(id);
    }

    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@RequestParam("name") String name,
                           @RequestParam("password") String password,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setUserName(name);
        user.setPassword(password);
        userService.updateUser(user);
    }

    /**
     * 头像上传
     *
     * @param uploadFile
     * @param session
     * @throws IOException
     */
    @RequestMapping(value = "/uploadHeadPortrait", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadHeadPortrait(@RequestParam(value = "headPortrait", required = true) MultipartFile uploadFile, HttpSession session) throws IOException {
        if (uploadFile != null) {
            BufferedInputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            try {
                inputStream = new BufferedInputStream(uploadFile.getInputStream());
                User user = (User) session.getAttribute("user");
                String userIconDir = ComentUtils.ICON_DIR + "/" + user.getId().toString();
                String preUserIcon=user.getIcon();
                if (!preUserIcon.contains("defaultIcon")){
                    File file=new File(preUserIcon);
                    file.deleteOnExit();
                }
                user.setIcon(userIconDir.substring(ComentUtils.ICON_DIR.indexOf("static")) + "/" + System.currentTimeMillis()+uploadFile.getOriginalFilename().split(".")[1]);
                File file = new File(userIconDir, System.currentTimeMillis()+uploadFile.getOriginalFilename().split(".")[1]);
                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buff = new byte[1024*2];
                int length;
                while ((length = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, length);
                }
                outputStream.flush();
                userService.updateUser(user);
                Map<String,String> map=new HashMap<>();
                map.put("iconURL",ComentUtils.HOST+user.getIcon());
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

}
