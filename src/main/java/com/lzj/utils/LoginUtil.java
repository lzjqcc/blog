package com.lzj.utils;

import javax.servlet.http.HttpSession;

public class LoginUtil {
    private static HttpSession session;
    public static HttpSession getCurrentSession(){
        return session;
    }
}
