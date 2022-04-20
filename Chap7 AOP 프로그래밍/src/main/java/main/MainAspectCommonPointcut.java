package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import chap07.Calculator;
import config.AppCtxWithCommonPointcut;

public class MainAspectCommonPointcut {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppCtxWithCommonPointcut.class);

		Calculator cal = ctx.getBean("calculator", Calculator.class);
		System.out.println(cal.getClass().getName());
		ctx.close();
	}

}
