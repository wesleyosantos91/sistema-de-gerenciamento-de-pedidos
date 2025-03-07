package io.github.wesleyosantos91.core.metric.aspect;

import io.github.wesleyosantos91.core.metric.annotation.CounterExecution;
import io.github.wesleyosantos91.core.metric.tag.counter.MultiTaggedCounter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.text.MessageFormat;

@Aspect
@Component
public class CounterExecutionAspect {

    private final MeterRegistry meterRegistry;

    public CounterExecutionAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Pointcut("@annotation(counterExecution)")
    public void counterExecutionPointcut(CounterExecution counterExecution) {
    }

    @Around("counterExecutionPointcut(counterExecution)")
    public Object counterExecutionAdvice(ProceedingJoinPoint joinPoint, CounterExecution counterExecution) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();


        MultiTaggedCounter multiTaggedCounter = new MultiTaggedCounter(
                counterExecution.name(),
                meterRegistry,
                "className", "methodName", "status", "message");

        String status = "SUCCESS";
        String message = "Successful executions";

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            status = "ERROR";
            message = MessageFormat.format("Executions with errors {0}", throwable.getMessage()) ;
            throw throwable;
        } finally {
            multiTaggedCounter.increment(className, methodName, status, message);
        }
    }
}