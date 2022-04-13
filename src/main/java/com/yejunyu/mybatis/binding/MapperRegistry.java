package com.yejunyu.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import com.yejunyu.mybatis.session.Configuration;
import com.yejunyu.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : YeJunyu
 * @description : 提供包路径的扫描和映射器代理类注册机服务，完成接口对象的代理类注册处理。
 * 映射器注册类的核心主要在于提供了 ClassScanner.scanPackage 扫描包路径，调用 addMapper 方法，给接口类创建 MapperProxyFactory 映射器代理类，
 * 并写入到 knownMappers 的 HashMap 缓存中。
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public class MapperRegistry {

    /**
     * 将已添加的映射器代理加入 map 中管理
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    /**
     * 通过 type 获取代理工厂
     *
     * @param type
     * @param session
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> type, SqlSession session) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(session);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    /**
     * 把 DAO 和 mapperFactory 对应起来
     *
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T> type) {
        /**
         * 只代理接口
         */
        if (type.isInterface()) {
            if (hasMapper(type)) {
                // 如果重复添加了，报错
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
        }
        // 注册映射器代理工厂
        knownMappers.put(type, new MapperProxyFactory<>(type));
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    /**
     * 扫描包,把 DAO 和 mapperFactory 对应起来
     *
     * @param packageName
     */
    public void addMappers(String packageName) {
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for (Class<?> mapper : mapperSet) {
            addMapper(mapper);
        }
    }
}
