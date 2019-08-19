package com.mikiruki.vendingsystemapi.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggerAspect {

    private Logger logger = Logger.getLogger(LoggerAspect.class);

    @Before(value = "execution(* com.mikiruki.vendingsystemapi.utils.*.*(..))")
    public void logUtilCall(JoinPoint joinPoint) {
        logger.info("Helper method called with following signature: " + joinPoint.getSignature());
    }

}
