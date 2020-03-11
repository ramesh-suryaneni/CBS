package com.imagination.cbs.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ContractorBookingSystemExceptionAdvice {

	
	private final Logger logger = LoggerFactory.getLogger(ContractorBookingSystemExceptionAdvice.class);

	@AfterThrowing(pointcut = "execution(* com.imagination.cbs.services.impl.* .*(..))", throwing = "throwable")
	public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {

		logger.error("Exception occured : {}", throwable);
		String arguments = Arrays.toString(joinPoint.getArgs());
		logger.error("Method Arguments : {} ", arguments);
		logger.error("Method Signature : {}", joinPoint.getSignature());
	}
}
