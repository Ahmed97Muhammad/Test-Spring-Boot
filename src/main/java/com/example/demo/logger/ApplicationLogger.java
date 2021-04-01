package com.example.demo.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
@Slf4j
public class ApplicationLogger {


    private final ObjectMapper mapper;

    public ApplicationLogger() {
        mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    @Pointcut("execution(public * *(..))")
    public void allMethod() {
    }

    @Before("(controller() || restController()) and (args(data) || args(..,data))")
    public void logRequest(final JoinPoint joinPoint, final Object data) {
        String parsedData = null;
        final String methodName = joinPoint.getSignature().getName();
        try {
            parsedData = mapper.writer().writeValueAsString(data);

        } catch (final IOException e) {
            log.warn(String.format("Request Data Parse Failed: %s", e.getMessage()));
        }
        final String logInfo = String.format("ASPECT [CONTENT] Request: %s , Data : %s ", methodName, parsedData);
        log.info(logInfo);
    }

    @AfterReturning(pointcut = "(controller() || restController())", returning = "returnValue")
    public void loggingRepositoryMethods(final JoinPoint joinPoint, final Object returnValue) {
        String returnValueString = null;
        final String methodName = joinPoint.getSignature().getName();
        try {
            returnValueString = mapper.writer().writeValueAsString(returnValue);
        } catch (final JsonProcessingException e) {
            log.warn(String.format("Response Data Parse Failed: %s", e.getMessage()));
        }
        final String logInfo = String.format("ASPECT [CONTENT] Response: %s , Data : %s", methodName,
                returnValueString);
        log.info(logInfo);
    }
}
