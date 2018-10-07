package com.dsys.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONUtils {
	private static final String STATUS="status";
	private static final String CONTENT="content";
	private static final String MESSAGE="msg";
	private static final String SUCCESS="0";
	private static final String FAIL="1";
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
			out.put(STATUS,SUCCESS);
		}else {
			out.put(STATUS,FAIL);
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
}
