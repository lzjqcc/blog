package com.lzj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by li on 17-8-6.
 */
@Controller
public class ArticleController {
    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
}
