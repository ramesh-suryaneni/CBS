package com.imagination.cbs;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ServletInitializerTest {

	@Mock
	private SpringApplicationBuilder springApplicationBuilder;

	@InjectMocks
	private ServletInitializer ServletInitializer;

	@Test
	public void configureClassesAndComponentToCBSApplication() {

		when(springApplicationBuilder.sources(ContractorBookingSystemApplication.class))
				.thenReturn(springApplicationBuilder);

		ServletInitializer.configure(springApplicationBuilder);

		verify(springApplicationBuilder).sources(ContractorBookingSystemApplication.class);

	}

}
