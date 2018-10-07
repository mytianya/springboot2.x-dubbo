package com.dsys.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.dsys.exception.CommonException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAop {
	@Pointcut("execution(public * com.dsys.provider.*.*(..))")
	public void wrapperException() {}
	@AfterThrowing(throwing="ex",value="wrapperException()")
	public void throwss(JoinPoint joinPoint,Exception ex) {
		if(ex instanceof NullPointerException) {
			throw new CommonException(CommonException.ExceptionType.NULL.getCode(),CommonException.ExceptionType.NULL.getMessage());
		}else if(ex instanceof CommonException) {
			throw (CommonException)ex;
		}else {
			throw new CommonException(CommonException.ExceptionType.SYS.getCode(),CommonException.ExceptionType.SYS.getMessage());
		}
	}
}
