package org.shiftone.jrat.inject.bytecode.asm;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.util.log.Logger;


/**
 * @author Jeff Drost
 */
public class MethodCriteriaClassVisitor implements ClassVisitor {

    private static final Logger LOG = Logger.getLogger(MethodCriteriaClassVisitor.class);
    private final ClassVisitor injector;
    private final ClassVisitor bypass;
    private MethodCriteria criteria;
    private String className;
    private ClassVisitor defaultClassVisitor;

    public MethodCriteriaClassVisitor(ClassVisitor injector, ClassVisitor bypass) {
        this.injector = injector;
        this.bypass = bypass;
    }


    public void setCriteria(MethodCriteria criteria) {
        this.criteria = criteria;
    }


    /**
     * when this method is called MethodCriteria.isMatch(className) is checked
     * to see if any injection is necessary. If not, then the default visitor is
     * set to bypass the injection process.
     */
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

        className = name.replace('/', '.');

        if (criteria.isMatch(className, access)) {
            defaultClassVisitor = injector;

            LOG.debug("not filtering class " + className);
        } else {
            defaultClassVisitor = bypass;
        }

        defaultClassVisitor.visit(version, access, name, signature, superName, interfaces);
    }


    /**
     * when this method is called MethodCriteria.isMatch(className, methodName,
     * ...) is checked to see if any injection is necessary for this method. If
     * not, then the bypass visitor is used. Otherwise, the default visitor is
     * used (which was set in the class visit method).
     */
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        if (criteria.isMatch(className, name, desc, access)) {
            return defaultClassVisitor.visitMethod(access, name, desc, signature, exceptions);
        } else {
            return bypass.visitMethod(access, name, desc, signature, exceptions);
        }
    }


    public void visitSource(String source, String debug) {
        defaultClassVisitor.visitSource(source, debug);
    }


    public void visitOuterClass(String owner, String name, String desc) {
        defaultClassVisitor.visitOuterClass(owner, name, desc);
    }


    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return defaultClassVisitor.visitAnnotation(desc, visible);
    }


    public void visitAttribute(Attribute attr) {
        defaultClassVisitor.visitAttribute(attr);
    }


    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        defaultClassVisitor.visitInnerClass(name, outerName, innerName, access);
    }


    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return defaultClassVisitor.visitField(access, name, desc, signature, value);
    }


    public void visitEnd() {

        defaultClassVisitor.visitEnd();

        defaultClassVisitor = null;
        className = null;
    }
}
