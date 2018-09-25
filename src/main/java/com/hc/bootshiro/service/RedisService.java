package com.hc.bootshiro.service;


import com.hc.bootshiro.util.RedisKeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: 胡丛
 * @Date: 2018/9/5 13:52
 * @Description:
 */
@Component
public class RedisService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 默认过期时长，单位：
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * redis共用前缀
     */
    private static String redisKeyPrefix = RedisKeyPrefix.COMMONPREFIX;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;


    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys
     */
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     * @param seconds 失效时间(秒)
     */
    public void set(String key, Object value, long seconds) {
        if (null == key || null == value) {
            throw new RuntimeException("key or value must not null");
        }
        key = appendKeyPrefix(key);
        if (value instanceof String) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Integer || value instanceof Long) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Double || value instanceof Float) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Short || value instanceof Boolean) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
        if (seconds > 0) {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field, Class<T> clazz) {
        return (T) redisTemplate.boundHashOps(appendKeyPrefix(key)).get(field);
    }

    private static String appendKeyPrefix(String key) {
        return redisKeyPrefix.concat(key);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public void addMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(appendKeyPrefix(key), field, value);
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public String getStr(String key) {
        return stringRedisTemplate.boundValueOps(appendKeyPrefix(key)).get();
    }

    /**
     * 添加set
     *
     * @param key
     * @param value
     */
    public void sadd(String key, String... value) {
        redisTemplate.boundSetOps(appendKeyPrefix(key)).add(value);
    }

    /**
     * set集合中的对象数
     *
     * @param key
     */
    public Long ssize(String key) {
        return redisTemplate.boundSetOps(appendKeyPrefix(key)).size();
    }

    /**
     * 取出set集合中的对象
     *
     * @param key
     */
    public Object spop(String key) {
        return redisTemplate.boundSetOps(appendKeyPrefix(key)).pop();
    }


}
