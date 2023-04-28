package ajou.mse.dimensionguard.log.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* ajou.mse.dimensionguard.controller..*(..))")
    public void controllerPoint(){}

    @Pointcut("execution(* ajou.mse.dimensionguard.service..*(..))")
    public void servicePoint(){}

    @Pointcut("execution(* ajou.mse.dimensionguard.repository..*(..)) && " +
            "!execution(* org.springframework.data.repository.CrudRepository.*(..))")
    public void repositoryPoint(){}
}
