package org.shiftone.jrat.provider.config;



import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.MethodHandlerFactory;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.util.regex.CompositeMatcher;
import org.shiftone.jrat.util.regex.Matcher;


public class GlobMethodHandlerFactory implements MethodHandlerFactory {

    private Matcher              classNameMatcher  = Matcher.ALL;
    private Matcher              methodNameMatcher = Matcher.ALL;
    private Matcher              signatureMatcher  = Matcher.ALL;
    private MethodHandlerFactory methodHandlerFactory;

    public MethodHandler createMethodHandler(MethodKey methodKey) throws Exception {

        if (classNameMatcher.isMatch(methodKey.getClassName()) && methodNameMatcher.isMatch(methodKey.getMethodName())
                && signatureMatcher.isMatch(methodKey.getSignature()))
        {
            return methodHandlerFactory.createMethodHandler(methodKey);
        }
        else
        {
            return null;
        }
    }


    public void startup(RuntimeContext context) throws Exception {
        methodHandlerFactory.startup(context);
    }


    public void setClassNamePattern(String pattern) {
        classNameMatcher = CompositeMatcher.buildCompositeGlobMatcher(pattern);
    }


    public void setMethodNamePattern(String pattern) {
        methodNameMatcher = CompositeMatcher.buildCompositeGlobMatcher(pattern);
    }


    public void setSignaturePattern(String pattern) {
        signatureMatcher = CompositeMatcher.buildCompositeGlobMatcher(pattern);
    }


    public void setMethodHandlerFactory(MethodHandlerFactory methodHandlerFactory) {
        this.methodHandlerFactory = methodHandlerFactory;
    }
}
