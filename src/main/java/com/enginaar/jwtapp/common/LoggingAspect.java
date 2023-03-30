package com.enginaar.jwtapp.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

	@AfterThrowing(pointcut = "execution(* com.enginaar.*.controllers.*.*(..))", throwing = "ex")
	public void logAfterExcetion(JoinPoint joinPoint, Throwable ex) {
		log.error("Failed : {}", joinPoint.getSignature().toShortString());
	}

	@Around("execution(* com.enginaar.*.controllers.*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("Starting : {}", joinPoint.getSignature().toShortString());
		log.trace("Input parameters: {}", joinPoint.getArgs());
		Object result = joinPoint.proceed();
		log.debug("Starting : {}", joinPoint.getSignature().toShortString());
		return result;

	}

}
