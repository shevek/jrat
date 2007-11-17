package org.shiftone.jrat.provider.tree;

import org.shiftone.jrat.core.spi.WebAction;
import org.shiftone.jrat.core.spi.WebActionFactory;
import org.shiftone.jrat.http.Response;

import java.io.PrintStream;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * @author (jeff@shiftone.org) Jeff Drost
 */
public class TreeWebActionFactory implements WebActionFactory {

    private final List treeNodes;

    public TreeWebActionFactory(List treeNodes) {
        this.treeNodes = treeNodes;
    }

    public WebAction createAction() throws Exception {
        return new Action();
    }

    public String getTitle() {
        return "Tree Handler";
    }

    public void registerNode(TreeNode treeNode) {
        treeNodes.add(treeNode);
    }

    public TreeNode getNode(int index) {
        return (TreeNode) treeNodes.get(index);
    }

    public class Action implements WebAction {

        public static final int MODE_FRAMESET = (int)'F';
        public static final int MODE_TREE = (int)'T';
        public static final int MODE_STACK = (int)'S';

        private int mode = MODE_FRAMESET;
        private int treeNode = 0;

        public void setMode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }

        public void setTreeNode(int treeNode) {
            this.treeNode = treeNode;
        }


        public int getTreeNode() {
            return treeNode;
        }

        public void execute(Response response) throws Exception {
            PrintStream out = new PrintStream(response.getOutputStream());
            switch (mode) {
                case MODE_FRAMESET:
                    executeFrames(out);
                    return;
                case MODE_TREE:
                    executeTree(getNode(treeNode), out);
                    return;
                case MODE_STACK:
                    executeStack(getNode(treeNode), out);
            }
        }


        private void executeFrames(PrintStream out) {
            out.print("<html><frameset rows='50%,*'>");
            out.print("<frame src='?mode=" + MODE_TREE + "'>");
            out.print("<frame src='?mode=" + MODE_STACK + "'>");
            out.print("</frameset></html>");
        }

        private void executeTree(TreeNode treeNode, PrintStream out) {

            out.print("<html></body>");
            out.println("<ul>");
            printTreeParents(treeNode, out);
            printTreeChildren(treeNode, out);
            out.println("</ul>");
            out.print("</body></html>");

        }

        /*
          ul x
             ul x
                ul x
                   ul x
                   /ul /ul /ul /ul
         */
        private void printTreeParents(TreeNode treeNode, PrintStream out) {

            if (!treeNode.isRootNode()) {              
              printTreeParents(treeNode.getParentNode(), out);
              out.println("</ul>");  
            }

            out.println("<ul>");
            printTreeNode(treeNode, out);

        }

        private void printTreeChildren(TreeNode treeNode, PrintStream out) {
            out.println("<ul>");
            List children = treeNode.getChildren();
            for (Iterator i = children.iterator() ; i.hasNext() ; ) {
                printTreeNode((TreeNode)i.next(), out);
            }
            out.println("</ul>");
        }

        private void printTreeNode(TreeNode treeNode, PrintStream out) {
            out.print("<li>");
            out.print("<a href='?mode=" + MODE_TREE + "&treeNode=" + treeNodes.indexOf(treeNode) + "'>");
            out.print(treeNode.isRootNode() ? "Root" : treeNode.getMethodKey().toString());
            out.print("</a>");
            out.print("</li>");
        }


        private void executeStack(TreeNode treeNode, PrintStream out) {

            
            out.print("<h1>xxxx</h1>");
        }
    }
}
