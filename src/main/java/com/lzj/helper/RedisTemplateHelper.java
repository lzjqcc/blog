package com.lzj.helper;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.annotation.*;
@Component
public class RedisTemplateHelper {

    private RedisTemplate<String,Serializable> redisTemplate;
}
