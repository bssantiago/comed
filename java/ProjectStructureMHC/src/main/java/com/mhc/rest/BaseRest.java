package com.mhc.rest;

import org.springframework.context.ApplicationContext;

import com.mhc.services.ApplicationContextProvider;

public class BaseRest {
	protected static ApplicationContext beanFactory = ApplicationContextProvider.getApplicationContext();
}
