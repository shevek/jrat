package org.shiftone.jrat.integration.aop.aspectwerkz;


import org.codehaus.aspectwerkz.intercept.AroundAdvice;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.Rtti;
import org.codehaus.aspectwerkz.joinpoint.impl.MethodRttiImpl;
import org.shiftone.jrat.api.Monitor;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class AspectWerkzAroundAdvice implements AroundAdvice {

    public Object invoke(JoinPoint joinPoint) throws Throwable {

        Rtti rtti = joinPoint.getRtti();

        if (rtti instanceof MethodRttiImpl) {
            MethodRttiImpl methodRtti = (MethodRttiImpl) rtti;

            return Monitor.execute(methodRtti.getMethod(), joinPoint.getThis(),    // todo

                    // or
                    // target?
                    new AspectWerkzJoinPointCommand(joinPoint));
        } else {

            // this is not a method - not sure why we were called, but might as
            // well
            // just proceed and not cause any problems.
            return joinPoint.proceed();
        }
    }
}
