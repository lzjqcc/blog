package com.lzj.controller;

import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.exception.BusinessException;
import com.lzj.exception.SystemException;
import com.lzj.service.AccountService;
import com.lzj.utils.ComentUtils;
import com.lzj.utils.ReflectUtils;
import org.assertj.core.util.Arrays;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户信息修改，头像上传,查看个人信息
 */
@Controller
@RequestMapping("/user")
public class AccountController {
    @Autowired
    AccountService accountService;
    @RequestMapping(value = "getUserDetail", method = RequestMethod.GET)
    @ResponseBody
    public AccountDto getUser() {
        Account account = ComentUtils.getCurrentAccount();
        AccountDto dto = new AccountDto();
        BeanUtils.copyProperties(account,dto);
        dto.setHeadIconURL(ComentUtils.getImageURL(account.getHeadIcon()));
        return dto;
    }
    @RequestMapping(value = "isLogin", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO isLogin() {
        Account account = ComentUtils.getCurrentAccount();
        if (Objects.isNull(account)) {
            return ComentUtils.buildResponseVO(true, "操作成功", false);
        }
        return ComentUtils.buildResponseVO(true,"操作成功", account);
    }
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@RequestBody AccountDto dto) {
        Account user = ComentUtils.getCurrentAccount();
        dto.setId(user.getId());
        accountService.updateUser(dto);
    }
    /**
     * 头像上传
     *
     * @param uploadFile
     * @param
     * @throws IOException
     */
    @RequestMapping(value = "/uploadHeadPortrait", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadHeadPortrait(@RequestParam(value = "headPortrait", required = true) MultipartFile uploadFile) throws IOException {
        if (uploadFile != null) {
            BufferedInputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            try {
                inputStream = new BufferedInputStream(uploadFile.getInputStream());
                Account user = ComentUtils.getCurrentAccount();
                setAccountHeadIcon(user,uploadFile.getOriginalFilename());
                File file = new File(user.getHeadIcon());
                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buff = new byte[1024*2];
                int length;
                while ((length = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, length);
                }
                outputStream.flush();
                AccountDto dto = new AccountDto();
                ReflectUtils.copyFieldValue(user,dto,"id","headIcon");
                accountService.updateUser(dto);
                Map<String,String> map=new HashMap<>();
                map.put("iconURL",ComentUtils.getImageURL(user.getHeadIcon()));
                return map;
            } catch (IOException e) {
                throw new BusinessException(300,"头像上传失败！");
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

    private void setAccountHeadIcon(Account account, String sourceName) {
        String iconDir = ComentUtils.ICON_DIR + account.getId();
        if (account != null && account.getHeadIcon() != null) {
            File file = new File(account.getHeadIcon());
            file.deleteOnExit();;
            File dir = new File(iconDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        String iconName = System.currentTimeMillis()+sourceName.split(".")[1];
        account.setHeadIcon(iconDir+File.separator+iconName);

    }

}
