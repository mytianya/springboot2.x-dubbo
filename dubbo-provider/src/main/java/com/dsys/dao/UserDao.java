package com.dsys.dao;

import com.dsys.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author dsys
 * @CreateTime 18-10-8-下午11:35
 * @Description
 **/
public interface UserDao {
    void addUser(User user);
    User findUserByAccount(String account);
    User findUserByAccountAndPwd(@Param("account") String account,@Param("password") String password);
}
