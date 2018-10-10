package com.dsys.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author dsys
 * @CreateTime 18-10-8-下午11:35
 * @Description
 **/
public interface UserDao {
    Integer register(@Param("nickname")String nickname,@Param("password")String password
            ,@Param("account")String account,@Param("sex")String sex);
    Integer findUserByAccount(@Param("account")String account);
    Map<String,Object> login(@Param("account")String account,@Param("password")String password);
}
