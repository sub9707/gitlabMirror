package com.repomon.rocketdan.common;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GHUtilsAop {
    @Around("@annotation(Retries)")
    public Object aroundRetriesAnno(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        int retries = 10;

        while(retries-- > 0) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (NullPointerException e) {
                Thread.sleep(500L);
            }
        }
        throw new RuntimeException();
    }
}
