package com.yejunyu;

import com.yejunyu.bizTest.dao.IUserDao;
import com.yejunyu.bizTest.model.User;
import com.yejunyu.mybatis.io.Resources;
import com.yejunyu.mybatis.session.SqlSession;
import com.yejunyu.mybatis.session.SqlSessionFactory;
import com.yejunyu.mybatis.session.SqlSessionFactoryBuilder;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * @author : YeJunyu
 * @description :
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public class ApiTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Test
//    public void test_MapperProxyFactory() {
//        // 1. 注册 mapper
//        MapperRegistry registry = new MapperRegistry();
//        registry.addMappers("dao");
//
//        // 2. 从 SqlSessionFactory 获取 Session
//        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(registry);
//        SqlSession sqlSession = defaultSqlSessionFactory.openSession();
//
//        // 3. 获取映射的 mapper
//        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
//
//        // 执行 sql
//        String res = mapper.queryUserName("10001");
//        logger.info("测试结果：{}", res);
//    }

    @Test
    public void test_SqlSessionFactory() throws IOException {
        // 1. 通过配置文件获取 SqlSession
        Reader reader = Resources.getResourceAsReader("configuration.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        // 3. 执行 sql
        String res = mapper.queryUserName("10001");
        logger.info("测试结果：{}", res);
    }
}
