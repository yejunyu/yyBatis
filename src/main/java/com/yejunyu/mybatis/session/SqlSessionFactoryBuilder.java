package com.yejunyu.mybatis.session;

import com.yejunyu.mybatis.builder.xml.XmlConfigBuilder;
import com.yejunyu.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author : YeJunyu
 * @description : SqlSessionFactoryBuilder 是作为整个 Mybatis 的入口类，通过指定解析XML的IO，引导整个流程的启动。
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader){
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
}
