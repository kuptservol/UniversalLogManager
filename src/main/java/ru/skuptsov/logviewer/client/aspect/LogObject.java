package ru.skuptsov.logviewer.client.aspect;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogObject {

	int logMethodArgument();

	boolean logAllMethodArguments() default true;

	boolean logReturnObject() default true;

}
