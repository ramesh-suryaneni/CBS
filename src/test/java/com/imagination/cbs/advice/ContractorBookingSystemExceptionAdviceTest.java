package com.imagination.cbs.advice;
import static org.mockito.Mockito.when;

import org.aspectj.lang.JoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContractorBookingSystemExceptionAdviceTest {
	
	@InjectMocks
	private ContractorBookingSystemExceptionAdvice contractorBookingSystemExceptionAdvice;
	
	@Mock
	private JoinPoint joinPoint;
	
	@Mock
	private Throwable excep;
	
	@Test
	public void afterThrowing_inputThrowable_shouldExecuteAdvice() throws Throwable {
		String arr[]={"Argument 1","Argument 2"};
		when(joinPoint.getArgs()).thenReturn(arr);
		contractorBookingSystemExceptionAdvice.afterThrowing(joinPoint,excep);
	}

}
