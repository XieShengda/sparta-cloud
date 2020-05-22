package com.sender.sparta.core.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 写入缓存
     */
    public void set(final String key, Object value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
    }

    /**
     * 写入缓存, 设置时效时间
     */
    public void set(final String key, Object value, Long expireTime) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存, 设置时效时间
     */
    public void set(final String key, Object value, Long expireTime, TimeUnit unit) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        redisTemplate.expire(key, expireTime, unit);
    }

    public void set(final String key, Object value, Date expiredDate) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        redisTemplate.expireAt(key, expiredDate);
    }

    /**
     * value ++
     */
    public Long increment(String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.increment(key);
    }

    /**
     * value ++, 如果没有设置过期时间则设置
     */
    public Long increment(String key, Long expireTime, TimeUnit unit) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Long currentValue = operations.increment(key);
        Long currentExpiredTime = redisTemplate.getExpire(key);
        if (null != currentExpiredTime && -1 == currentExpiredTime) {// 有这个key没设置过期时间
            redisTemplate.expire(key, expireTime, unit);
        }
        return currentValue;
    }

    /**
     * 批量删除对应的value
     */
    public void remove(final String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    public void remove(List<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 批量删除key
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (CollectionUtils.isNotEmpty(keys))
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     */
    public Boolean remove(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 判断缓存中是否有对应的value
     */
    public Boolean exists(final String key) {
        if (key == null) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 读取缓存, 重置过期时间
     */
    public Object get(final String key, Long expireTime) {
        Object result;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        if (result != null) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
        return result;
    }

    /**
     * 读取缓存, 重置过期时间
     */
    public Object get(final String key, Long expireTime, TimeUnit unit) {
        Object result;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        if (result != null) {
            redisTemplate.expire(key, expireTime, unit);
        }
        return result;
    }

    /**
     * 哈希 添加
     */
    public void hSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 写入缓存设置时效时间
     */
    public boolean hSet(final String key, Object hashKey, Object value, Long expireTime, TimeUnit unit) {
        boolean result = false;
        try {
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            hash.put(key, hashKey, value);
            redisTemplate.expire(key, expireTime, unit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 哈希 添加
     */
    public void hSet(String key, Map<Object, Object> map) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
    }

    /**
     * 哈希 递增(Long)
     */
    public Long hIncrement(String key, Object hashKey, Long delta) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.increment(key, hashKey, delta);
    }

    /**
     * hash 递增
     */
    public Long hIncrement(String key, Object hashKey) {
        return hIncrement(key, hashKey, 1L);
    }

    /**
     * hash 递减
     */
    public Long hDecrement(String key, Object hashKey) {
        return hIncrement(key, hashKey, -1L);
    }

    /**
     * 哈希 递增(Double)
     */
    public Double hIncrementDouble(String key, Object hashKey, Double delta) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.increment(key, hashKey, delta);
    }

    /**
     * 哈希获取数据
     */
    public Object hGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希获取数据, 重置过期时间
     */
    public Object hGet(String key, Object hashKey, Long expireTime, TimeUnit unit) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        Object result = hash.get(key, hashKey);
        if (result != null) {
            redisTemplate.expire(key, expireTime, unit);
        }
        return result;
    }

    /**
     * 哈希批量获取数据
     */
    public List<Object> hMultiGet(String key, List<Object> hashKeys) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.multiGet(key, hashKeys);
    }

    /**
     * 判断key是否存在
     */
    public boolean hExist(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.hasKey(key, hashKey);
    }

    /**
     * 获取Hash的keySet
     */
    public Set<Object> hKeys(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.keys(key);
    }

    /**
     * 获取Hash的所有值
     */
    public List<Object> hValues(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.values(key);
    }

    /**
     * 获取Hash的所有键值对
     */
    public Map<Object, Object> hEntities(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    /**
     * 删除Hash中的键值对
     */
    public void hRemove(String key, Object... hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.delete(key, hashKey);
    }

    /**
     * 列表添加
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表批量添加到底部
     */
    public void lPushAll(String k, List<?> vs) {
        redisTemplate.opsForList().rightPushAll(k, vs);
    }

    /**
     * 列表批量删除(只保留区间内的元素, 包含首尾索引的值 [])
     */
    public void lTrim(String k, int trimStart, int trimEnd) {
        redisTemplate.opsForList().trim(k, trimStart, trimEnd);
    }

    /**
     * 列表从头部获取
     */
    public List<Object> lRange(String key, long start, long stop) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(key, start, stop);
    }

    /**
     * 获取并删除最新数据
     */
    public Object lPop(String key) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.leftPop(key);
    }

    /**
     * 获取列表大小
     */
    public long lSize(String key) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.size(key);
    }

    /**
     * 集合添加
     */
    public void sAdd(String key, Object... value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合添加
     */
    public void sAdd(String key, List<?> vs) {
        if (CollectionUtils.isEmpty(vs)) {
            return;
        }
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, vs.toArray());
    }

    /**
     * 集合获取并删除
     */
    public Object sPop(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.pop(key);
    }

    /**
     * 集合删除
     */
    public void sRemove(String key, Object... value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.remove(key, value);
    }

    /**
     * 集合获取
     */
    public Set<Object> sMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 交集
     */
    public Set<Object> sIntersect(String key1, String key2) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.intersect(key1, key2);
    }

    /**
     * 判断集合是否存在某个元素
     */
    public boolean sContains(String key, Object element) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, element);
    }

    /**
     * 有序集合添加
     */
    public void zAdd(String key, Object value, double score) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, score);
    }

    /**
     * 有序集合获取
     */
    public Set<Object> rangeByScore(String key, double score, double score1) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.rangeByScore(key, score, score1);
    }

    public Set<String> getKeysByPattern(String pattern) {
        return redisTemplate.keys(pattern);
    }
}