package com.lzj.helper;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.annotation.*;
import java.util.List;

@Component
public class RedisTemplateHelper {
    @Autowired
    private RedisTemplate<String,Serializable> redisTemplate;
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
