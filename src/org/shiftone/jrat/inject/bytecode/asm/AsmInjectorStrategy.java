package org.shiftone.jrat.inject.bytecode.asm;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.SerialVersionUIDAdder;
import org.shiftone.jrat.inject.bytecode.InjectorStrategy;
import org.shiftone.jrat.inject.bytecode.TransformerOptions;


public class AsmInjectorStrategy implements InjectorStrategy {

    public byte[] inject(byte[] rawClassData, TransformerOptions options) throws Exception {

        ClassReader reader = new ClassReader(rawClassData);
        ClassWriter classWriter = new ClassWriter(true);
        ClassVisitor target = classWriter;

        // target = new DebugClassVisitor(target);
        ClassInitClassVisitor classInitClassVisitor = new ClassInitClassVisitor(target);
        SerialVersionUIDAdder serialVersionUIDAdder = new SerialVersionUIDAdder(classInitClassVisitor);
        InjectClassVisitor injectClassVisitor = new InjectClassVisitor(serialVersionUIDAdder);
        MethodCriteriaClassVisitor criteriaClassVisitor = new MethodCriteriaClassVisitor(injectClassVisitor,
                serialVersionUIDAdder);
        ClassVisitor visitor = new IfInterfaceClassVisitor(target, criteriaClassVisitor);

        criteriaClassVisitor.setCriteria(options.getCriteria());

        // * IfInterfaceClassVisitor delegates to either ClassWriter or
        // InjectClassVisitor
        // this causes interface classes to be skipped (not be processed)
        // * InjectClassVisitor will create a proxy method for each method in
        // the class
        // it also adds a "jrat static init" method
        // * SerialVersionUIDAdder will add a serialVersionUID method if it is
        // missing
        // most likely this will cause a static initializer to be added
        // * ClassInitClassVisitor will add to the static initializer or add on
        // the result is the class will have one that calls the jrat static init
        // method
        reader.accept(visitor, false);

        return classWriter.toByteArray();
    }    
}
