package com.dsys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CardAreaDao {
	 Map<String,Object>findAreaByCardNo(@Param("cardNo") String cardNo);
}
