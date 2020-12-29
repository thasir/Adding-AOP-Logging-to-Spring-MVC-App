package springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	// set up logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// set up pointcut declarations
	@Pointcut("execution(* springdemo.controller.*.*(..))")
	private void forControllerPackage() {
	}

	// do the samething for service and dao
	@Pointcut("execution(* springdemo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* springdemo.dao.*.*(..))")
	private void forDaoPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {
	}

	// add @Before Advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		// display the method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====> in @Before : calling method: " + theMethod);
		// display the arguments to the method

		// get the arguements
		Object[] args = theJoinPoint.getArgs();
		// loop through and display args
		for (Object tempArg : args) {
			myLogger.info("====>argument: " + tempArg);
		}
	}

	// add @AfterReturning Advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		// display the method we are displaying from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====> in @AfterReturning : from method: " + theMethod);
		// display data returned
		myLogger.info("====>result: " + theResult);
	}
}
