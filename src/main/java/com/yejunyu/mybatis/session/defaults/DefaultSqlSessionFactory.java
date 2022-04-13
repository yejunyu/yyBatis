package com.yejunyu.mybatis.session.defaults;

import com.yejunyu.mybatis.binding.MapperRegistry;
import com.yejunyu.mybatis.session.Configuration;
import com.yejunyu.mybatis.session.SqlSession;
import com.yejunyu.mybatis.session.SqlSessionFactory;

/**
 * @author : YeJunyu
 * @description :
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
