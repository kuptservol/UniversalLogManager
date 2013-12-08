package ru.skuptsov.logviewer.client.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SimpleLogObjectClientAspect {
	
	@Around("@annotation(logObject)")
	public Object myAspect(final ProceedingJoinPoint pjp,
	    final LogObject logObject) throws Throwable{

	    for(final Object argument : pjp.getArgs()){
	        System.out.println("Parameter value:" + argument);
	    }

	    return pjp.proceed();
	}
	
	@After("@annotation(ru.skuptsov.logviewer.client.aspect.LogObject)")
    public void after() {

            int i =0;

    }

}
