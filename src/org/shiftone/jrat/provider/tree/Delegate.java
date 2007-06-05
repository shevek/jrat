package org.shiftone.jrat.provider.tree;


import org.shiftone.jrat.core.MethodKey;


/**
 * This is basically a thread specific MethodHandler. The typical JRat model is
 * to have a seperate handler for each method. This is also true for the
 * TreeMethodHandler, however that handler delegates to an instance of this
 * class, passing it the method key with each invocation. One instance of this
 * class will exist for each thread that is creating JRat events. This class
 * manipulates a tree structure as invocations are made.
 *
 * @author Jeff Drost
 */
public class Delegate {

    private StackNode currentNode = null;

    public Delegate(StackNode rootNode) {

        if (rootNode == null) {
            throw new NullPointerException("delegate created to null initial node");
        }

        currentNode = rootNode;
    }


    public final void onMethodStart(MethodKey methodKey) {

        currentNode = currentNode.getChild(methodKey);

        currentNode.getAccumulator().onMethodStart();
    }


    public final void onMethodFinish(MethodKey methodKey, long duration, boolean success) {

        currentNode.getAccumulator().onMethodFinish(duration, success);

        currentNode = currentNode.getParentNode();
    }
}
