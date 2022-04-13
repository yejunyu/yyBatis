package com.yejunyu.mybatis.session;

import com.yejunyu.mybatis.binding.MapperRegistry;
import com.yejunyu.mybatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : YeJunyu
 * @description : 配置项
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public class Configuration {

    protected MapperRegistry mapperRegistry = new MapperRegistry();

    /**
     * 映射的语句存在 map 中
     */
    public final Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public void addMappedStatement(MappedStatement statement) {
        mappedStatementMap.put(statement.getId(), statement);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatementMap.get(id);
    }
}
