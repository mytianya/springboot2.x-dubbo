package com.dsys.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.dsys.exception.CommonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONUtils {
	public static final String STATUS="status";
	public static final String CONTENT="content";
	public static final String MESSAGE="msg";
//	private static final String SUCCESS="0";
//	private static final String FAIL="1";
	private static final ObjectMapper mapper=new ObjectMapper();
	static {
		DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(formatter);
		mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
			public void serialize(Object value,JsonGenerator jg,SerializerProvider sp) throws IOException,JsonProcessingException{
				jg.writeString("");
			}
		});
	}
	public static String packageResult(boolean status,Object content,String msg) {
		ObjectNode out=mapper.createObjectNode();
		if(status) {
			out.put(STATUS,CommonException.StatusCode.SUCCESS.getCode());
		}else {
			out.put(STATUS,CommonException.StatusCode.FAIL.getCode());
		}
		try {
			out.put(CONTENT,mapper.readTree(mapper.writeValueAsString(content)));
			out.put(MESSAGE,msg);
			return mapper.writeValueAsString(out);
		} catch (Exception e) {
			return null;
		}
		
	}
	public static String packageResult(String code,Object content,String msg) {
		ObjectNode out=mapper.createObjectNode();
		try {
			out.put(STATUS,code);
			out.put(CONTENT,mapper.readTree(mapper.writeValueAsString(content)));
			out.put(MESSAGE,msg);
			return mapper.writeValueAsString(out);
		} catch (Exception e) {
			return null;
		}
	}
	public static Map<String,Object> json2Map(String json){
		try {
			Map<String,Object> m=mapper.readValue(json,Map.class);
			return m;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String map2Json(Map<String,Object> map){
		try {
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void testEmpty(String paramName,String value){
		if(value==null&&value.length()==0)
			throw new CommonException(CommonException.StatusCode.FAIL.getCode(),String.format("参数:%s不能为空",paramName));
	}
	public static void main(String args[]) throws IOException {
		String json="{\"status\":\"0\",\"content\":{\"sex\":\"man\",\"nickname\":\"mytianya\",\"account\":\"6a6bbe4f26c145b6afdb85c631a8bac7\",\"status\":0},\"msg\":\"登录成功\"}";
		ObjectMapper mapper=new ObjectMapper();
		Map<String,Object> m=mapper.readValue(json, Map.class);
		m.put("token","11222");
		System.out.println(mapper.writeValueAsString(m));
		System.out.println(m.get("status"));
	}
}
