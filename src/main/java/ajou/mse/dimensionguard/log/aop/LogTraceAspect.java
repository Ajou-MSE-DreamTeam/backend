package ajou.mse.dimensionguard.log.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Around("ajou.mse.dimensionguard.log.aop.Pointcuts.controllerPoint() || " +
            "ajou.mse.dimensionguard.log.aop.Pointcuts.servicePoint() || " +
            "ajou.mse.dimensionguard.log.aop.Pointcuts.repositoryPoint()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // Logic call
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception ex) {
            logTrace.exception(status, ex);
            throw ex;
        }
    }
}
