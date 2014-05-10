package org.shiftone.jrat.provider.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.shiftone.jrat.core.MethodKey;
import org.shiftone.jrat.core.spi.AbstractMethodHandlerFactory;
import org.shiftone.jrat.core.spi.MethodHandler;
import org.shiftone.jrat.core.spi.RuntimeContext;
import org.shiftone.jrat.provider.tree.command.DumpOutputCommandlet;
import org.shiftone.jrat.provider.tree.command.ResetCommandlet;
import org.shiftone.jrat.provider.tree.command.WriteOutputCommandlet;
import org.shiftone.jrat.util.AtomicLong;
import org.shiftone.jrat.util.log.Logger;

/**
 * Class TreeMethodHandlerFactory
 *
 * @author jeff@shiftone.org (Jeff Drost)
 */
public class TreeMethodHandlerFactory extends AbstractMethodHandlerFactory implements TreeMethodHandlerFactoryMBean {

    private static final Logger LOG = Logger.getLogger(TreeMethodHandlerFactory.class);
    private final TreeNode rootNode = new TreeNode();
    private final Set allMethodKeys = new HashSet();
    private final DelegateThreadLocal delegateThreadLocal = new DelegateThreadLocal(this);
    private final AtomicLong methodHandlerCount = new AtomicLong();
    private final List treeNodes = new ArrayList(); /* <TreeNode> */

    private final TreeWebActionFactory webActionFactory = new TreeWebActionFactory(treeNodes);

    @Override
    public void startup(RuntimeContext context) throws Exception {
        super.startup(context);
        context.registerMBean(this);
        context.register(new ResetCommandlet(this));
        context.register(new WriteOutputCommandlet(this));
        context.register(new DumpOutputCommandlet(this));

        context.registerWebActionFactory(webActionFactory);
    }

    @Override
    public synchronized final MethodHandler createMethodHandler(MethodKey methodKey) {

        methodHandlerCount.incrementAndGet();
        allMethodKeys.add(methodKey);

        return new TreeMethodHandler(this, methodKey);
    }

    @Override
    public long getMethodHandlerCount() {
        return methodHandlerCount.get();
    }

    public TreeNode createTreeNode(MethodKey methodKey, TreeNode treeNode) {
        TreeNode newNode = new TreeNode(methodKey, treeNode);
        // this add makes the web factory able to look up a node by id.
        treeNodes.add(treeNode);
        return newNode;
    }

    /**
     * Returns the current thread's delegate instance. This delegate will
     * operate on this factory's call tree data structure when events are
     * processed.
     */
    public final Delegate getDelegate() {
        return (Delegate) delegateThreadLocal.get();
    }

    public final TreeNode getRootNode() {
        return rootNode;
    }

    @Override
    public synchronized void writeOutputFile() {
        writeOutputFile(getOutputFile());
    }

    public synchronized void reset() {
        rootNode.reset();
    }

    @Override
    public void writeOutputFile(String fileName) {

        LOG.info("writeOutputFile...");

        /* TODO
         getContext().writeSerializable(fileName,
         new TraceViewBuilder(
         rootNode,
         new HashSet(allMethodKeys), // copy to avoid sync issues
         getContext().getStartTimeMs(),
         System.currentTimeMillis(),
         getContext().getSystemPropertiesAtStartup(),
         getContext().getHostName(),
         getContext().getHostAddress()
         )
         );
         */
    }

    @Override
    public void shutdown() {

        LOG.info("shutdown...");
        writeOutputFile();
        LOG.info("shutdown complete");
    }

    @Override
    public String toString() {
        return "Tree Handler Factory";
    }
}
