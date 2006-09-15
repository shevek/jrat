package org.shiftone.jrat.inject.bytecode;



/**
 * JRat can swap out bytecode manipulation libraries - each supported library
 * must have a InjectorStrategy implementation.
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.3 $
 */
public interface InjectorStrategy {

    /**
     * when a class is injected, a magic field is added that contains a comment.
     * this field's existance tells the injector that "jrat was here".
     */
    public static final String METHOD_POSTFIX     = "_$jrat";
    public static final String HANDLER_PREFIX     = "jrat$";
    public static final String COMMENT_FIELD_NAME = "$hiftOne_JRat_was_here";

    /**
     * this method takes a byte array containing the data of a class, transforms
     * the class, and returns a new byte array containing the data of a
     * transformed / injected class.
     */
    byte[] inject(byte[] inputClassData, TransformerOptions options) throws Exception;
}
