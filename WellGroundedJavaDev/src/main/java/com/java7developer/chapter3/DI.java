package com.java7developer.chapter3;

import javax.inject.Inject;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jndi.JndiObjectFactoryBean;

public class DI {

	@Inject public DI(String something)
	{
		BeanFactory beanfactory;
		ApplicationContext appContext;
		JndiObjectFactoryBean bean;

	}


}
