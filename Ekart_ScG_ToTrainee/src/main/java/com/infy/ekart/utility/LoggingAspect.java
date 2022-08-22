package com.infy.ekart.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//Add the missing annotation
@Aspect
@Component
public class LoggingAspect {

	private static Log logger = LogFactory.getLog(LoggingAspect.class);

	// annotation to handle service layer exception
    @AfterThrowing(pointcut="execution(* com.infy.ekart.*Impl.*(..))",throwing="exception")
	public void logExceptionFromService(Exception exception) {
		logger.error(exception.getMessage(), exception);
	}

}