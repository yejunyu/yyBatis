package com.yejunyu.mybatis.builder.xml;

import com.yejunyu.mybatis.builder.BaseBuilder;
import com.yejunyu.mybatis.io.Resources;
import com.yejunyu.mybatis.mapping.MappedStatement;
import com.yejunyu.mybatis.mapping.SqlCommandType;
import com.yejunyu.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : YeJunyu
 * @description :
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public class XmlConfigBuilder extends BaseBuilder {

    private Element root;

    private static final Pattern compile = Pattern.compile("(#\\{(.*?)})");


    public XmlConfigBuilder(Reader reader) {
        super(new Configuration());
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new InputSource(reader));
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析方法
     *
     * @return Configuration
     */
    public Configuration parse() {
        try {
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    private void mapperElement(Element mappers) throws Exception {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element e : mapperList) {
            String resource = e.attributeValue("resource");
            Reader resourceAsReader = Resources.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(resourceAsReader));
            Element root = document.getRootElement();
            // 命名空间
            String namespace = root.attributeValue("namespace");

            // select
            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // ? 匹配
                Map<Integer, String> parameter = new HashMap<>(8);
                Matcher matcher = compile.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    String g1 = matcher.group(1);
                    String g2 = matcher.group(2);
                    parameter.put(i, g2);
                    sql = sql.replace(g1, "?");

                }

                String msId = namespace + "." + id;
                String nodeName = node.getName();
                SqlCommandType commandType = SqlCommandType.valueOf(nodeName.toUpperCase());
                MappedStatement statement = new MappedStatement.Builder(configuration, msId, commandType, parameterType, resultType, sql, parameter).build();
                // 解析 sql
                configuration.addMappedStatement(statement);
            }
            configuration.addMapper(Resources.classForName(namespace));
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String sql = "SELECT id, userId, userHead, createTime\n" +
                "        FROM user\n" +
                "        where id = #{id}";
        Matcher matcher = compile.matcher(sql);
        Map<Integer, String> parameter = new HashMap<>();

        for (int i = 1; matcher.find(); i++) {
            String g1 = matcher.group(1);
            String g2 = matcher.group(2);
            System.out.println(g1);
            System.out.println(g2);
            parameter.put(i, g2);
            sql = sql.replace(g1, "?");
        }
        System.out.println(sql);
        System.out.println(parameter);


        Class<?> aClass = Resources.classForName("com.yejunyu.bizTest.dao.IUserDao");
        System.out.println(aClass);
    }
}
