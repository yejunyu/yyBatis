package com.yejunyu.bizTest.dao;


import com.yejunyu.bizTest.model.User;

/**
 * @author : YeJunyu
 * @description :
 * @email : yyyejunyu@gmail.com
 * @date : 2022/4/12
 */
public interface IUserDao {

    String queryUserName(String uid);

    User queryUserAge(String uid);
}
