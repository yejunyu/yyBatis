package com.yejunyu.mybatis.session;

/**
 * @author : YeJunyu
 * @description : 用于定义执行 SQL 标准、获取映射器以及将来管理事务等方面的操作。基本我们平常使用 Mybatis 的 API 接口也都是从这个接口类定义的方法进行使用的。
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public interface SqlSession {

    /**
     * 根据指定的 sqlId 获取一条记录
     *
     * @param statement sqlId
     * @param <T>       封装的对象类型
     * @return 对撞的对象
     */
    <T> T selectOne(String statement);

    /**
     * 根据指定的 sqlId 获取一条记录
     *
     * @param statement sqlId
     * @param <T>       封装的对象类型
     * @param parameter 查询语句的传参
     * @return 对撞的对象
     */
    <T> T selectOne(String statement, Object parameter);

    /**
     * 得到 mapper 映射器
     *
     * @param type Mapper Interface class
     * @param <T>  封装的对象类型
     * @return 绑定的映射器
     */
    <T> T getMapper(Class<T> type);

    /**
     * 获取配置项
     *
     * @return Configuration
     */
    Configuration getConfiguration();
}
