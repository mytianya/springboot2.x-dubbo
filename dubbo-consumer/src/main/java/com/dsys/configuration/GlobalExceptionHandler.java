package com.dsys.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsys.exception.CommonException;
import com.dsys.utils.JSONUtils;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value =CommonException.class)
    @ResponseBody
    public String jsonErrorHandler(HttpServletRequest req,CommonException ex) throws Exception {
		return JSONUtils.packageResult(ex.getCode(),"",ex.getMessage());
    }
}
