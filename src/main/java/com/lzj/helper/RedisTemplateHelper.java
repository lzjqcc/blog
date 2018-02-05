package com.lzj.helper;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.annotation.*;
import java.util.List;
import java.util.Map;

@Component
public class RedisTemplateHelper{
    @Autowired
    private StringRedisTemplate stringRedisTemplate ;
    private RedisTemplate<String, Serializable> redisTemplate;
    @PostConstruct
    public void init() {
        redisTemplate = (RedisTemplate) stringRedisTemplate;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Serializable>(Serializable.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<Serializable>(Serializable.class));
    }
/*    public Map<String, Serializable> getAll() {
        return redisTemplate.execute(new RedisCallback<Map<String, Serializable>>() {
            @Override
            public Map<String, Serializable> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.hGetAll()
                return null;
            }
        });
    }*/
    public long remove(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
               return connection.del(key.getBytes());
            }
        });
    }

    public void put(String key, Serializable value) {
        redisTemplate.execute(new RedisCallback<Serializable>(){

            @Override
            public Serializable doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key.getBytes(),serial(value));
                return value;
            }
        });
    }
    public Serializable get(String key) {
       return redisTemplate.execute(new RedisCallback<Serializable>() {
            @Override
            public Serializable doInRedis(RedisConnection connection) throws DataAccessException {
               return unSerial(connection.get(key.getBytes()));
            }
        });
    }
    private Serializable unSerial(byte[] b) {
        Object result = null;
        if (b == null) {
            return null;
        } else {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(b);
                ObjectInputStream ois = new ObjectInputStream(bais);
                result = ois.readObject();
                ois.close();
                bais.close();
            } catch (Exception var5) {
            }

            return (Serializable)result;
        }
    }
    private byte[] serial(Serializable data) {
        if (data == null) {
            return null;
        } else {
            byte[] result = null;

            try {
                ByteArrayOutputStream boo = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(boo);
                oos.writeObject(data);
                result = boo.toByteArray();
                oos.close();
                boo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
