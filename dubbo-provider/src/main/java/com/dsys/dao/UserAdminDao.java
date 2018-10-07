package com.dsys.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserAdminDao {
	Map<String,Object> adminLogin(@Param("account") String account, @Param("password") String password);
}
