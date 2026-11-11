package com.example.eventmanagement.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.eventmanagement.service.*.*(..))")
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        try {

            logger.info("Method started: {}",
                    joinPoint.getSignature().getName());

            return joinPoint.proceed();

        } finally {

            long end = System.currentTimeMillis();

            logger.info("Method completed: {} in {} ms",
                    joinPoint.getSignature().getName(),
                    end - start);
        }
    }
}