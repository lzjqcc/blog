package com.lzj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by li on 17-8-7.
 */
@RestController
public class AdminController {
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
}
