package com.dsys.aop;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.dsys.exception.CommonException;
import com.dsys.utils.JSONUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(99)
public class RequestLog {
	@Pointcut("execution(public * com.dsys.controller.*.*(..))")
	public void log() {}

	/**
	 * 请求打印
	 * @param joinPoint
	 */
	@Before("log()")
	public void deBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		System.out.println("URL:"+request.getRequestURL().toString()
				+",METHOD:"+request.getMethod()
				+",IP:"+request.getRemoteAddr()
				+",CLASS_METHOD:"+joinPoint.getSignature().getDeclaringTypeName()+"."
				+joinPoint.getSignature().getName()
				+"ARGS:"+Arrays.toString(joinPoint.getArgs()));
	}
	@Pointcut("execution(public * com.dsys.controller.*.*(..))")
	public void jsonp(){}
	/**
	 * 添加jsonp支持
	 * @param
	 */
	@Around("jsonp()")
	public Object wrapperJsonp(ProceedingJoinPoint proceedingJoinPoint){
		String paramName="callback";
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		Map<String, String[]>  params=  request.getParameterMap();
		Object result= null;
		try {
			result = proceedingJoinPoint.proceed();
			if(params.containsKey(paramName)){
				result=params.get("callback")[0]+"("+result+")";
			}
		} catch (Throwable ex) {
			if(ex instanceof CommonException){
				CommonException ex1=(CommonException)(ex);
				result= JSONUtils.packageResult(ex1.getCode(),"",ex1.getMessage());
			}
		}

		return result;
	}
}