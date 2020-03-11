package com.imagination.cbs.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ContractorBookingSystemLoggerAdvice {
	
	private final Logger logger = LoggerFactory.getLogger(ContractorBookingSystemExceptionAdvice.class);
	
	@Around("@within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository) || @within(org.springframework.stereotype.Controller)")
	public Object logBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		
		
		logger.info(" Started - : {} "+proceedingJoinPoint.getSignature());
		
		logger.info(" Arguments - : {} "+proceedingJoinPoint.getArgs());
		
		return proceedingJoinPoint.proceed();
		
		
	}
	
	@AfterReturning(pointcut = "execution(* com.imagination.cbs.controller.* .*(..)) || execution(* com.imagination.cbs.service.impl.* .*(..)) || execution(* com.imagination.cbs.repository.* .*(..))", returning ="result")
	public void logAfter(JoinPoint  joinPoint, Object result){
		
		logger.info(" End - : {} "+joinPoint.getSignature());
		
		logger.info(" Arguments : - {} "+joinPoint.getArgs());
		
		logger.info(" Returned Value : - {} "+result);
	}

}
