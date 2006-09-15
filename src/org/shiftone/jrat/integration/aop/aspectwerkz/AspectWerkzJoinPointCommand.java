package org.shiftone.jrat.integration.aop.aspectwerkz;



import org.codehaus.aspectwerkz.joinpoint.JoinPoint;

import org.shiftone.jrat.api.Command;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.1 $
 */
public class AspectWerkzJoinPointCommand implements Command {

    private final JoinPoint joinPoint;

    public AspectWerkzJoinPointCommand(JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }


    public Object execute() throws Throwable {
        return joinPoint.proceed();
    }
}
