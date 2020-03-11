package com.imagination.cbs.advice;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.imagination.cbs.service.impl.DisciplineServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class ContractorBookingSystemLoggerAdviceTest {
	
	@InjectMocks
	private ContractorBookingSystemLoggerAdvice contractorBookingSystemLoggerAdvice;
	
	@Mock
	private JoinPoint joinPoint;
	
	@Mock
	private ProceedingJoinPoint proceedingJoinPoint;
	
	@Mock
	private Signature signature;
	
	@Test
	public void logBeforeInputProceedingJoinPointShouldExecuteLogBeforeWhenMethodCalled() throws Throwable { 
		
		
		when(proceedingJoinPoint.getSignature()).thenReturn(signature);
		when(proceedingJoinPoint.proceed()).thenReturn(new DisciplineServiceImpl());
		
		Object actual = contractorBookingSystemLoggerAdvice.logBefore(proceedingJoinPoint);
		
		verify(proceedingJoinPoint).getSignature();
		verify(proceedingJoinPoint).proceed();
		assertTrue(actual instanceof DisciplineServiceImpl);
	}
	
	@Test
	public void logBeforeInputProceedingJoinPointShouldExecuteLogBeofeWhenMethodNameContainsDot() throws Throwable { 
		
		when(proceedingJoinPoint.getSignature()).thenReturn(signature);
		when(proceedingJoinPoint.proceed()).thenReturn(new DisciplineServiceImpl());
		
		Object actual = contractorBookingSystemLoggerAdvice.logBefore(proceedingJoinPoint);
		
		verify(proceedingJoinPoint).getSignature();
		verify(proceedingJoinPoint).proceed();
		assertTrue(actual instanceof DisciplineServiceImpl);
	}
	
	@Test
	public void logAfterInputJoinPointShouldExecuteLogAfterWhenMethodCallingComplete() throws Throwable { 
		
		Object[] result = {};
		
		when(joinPoint.getArgs()).thenReturn(result);
		when(joinPoint.getSignature()).thenReturn(signature);
		
		contractorBookingSystemLoggerAdvice.logAfter(joinPoint,result);
		
		verify(joinPoint).getArgs();
		verify(joinPoint).getSignature();
	}

}
