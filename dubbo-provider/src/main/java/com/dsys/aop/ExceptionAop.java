package com.dsys.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

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
			throw new CommonException(CommonException.StatusCode.NULL.getCode(), CommonException.StatusCode.NULL.getMessage());
		}else if(ex instanceof CommonException) {
			throw (CommonException)ex;
		}else {
			throw new CommonException(CommonException.StatusCode.SYS.getCode(), CommonException.StatusCode.SYS.getMessage());
		}
	}
}
