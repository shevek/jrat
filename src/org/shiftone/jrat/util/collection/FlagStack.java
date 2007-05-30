package org.shiftone.jrat.util.collection;


import java.util.EmptyStackException;


/**
 * this is a stack that holds booleans. the stack has a max capacity - after
 * this capacity is reached, anything that is pushed onto it will be treated as
 * having a "default value" - the actual value is ignored.
 */
public class FlagStack {

    private boolean[] stack;
    private int depth = 0;
    private boolean defaultValue = false;    // used for overflow

    public FlagStack() {
        this(1000);
    }


    public FlagStack(int maxDepth) {
        stack = new boolean[maxDepth];
    }


    public void push(boolean value) {

        if (isInBounds()) {
            stack[depth] = value;
        }

        depth++;
    }


    private boolean isInBounds() {
        return depth < stack.length;
    }


    public int getDepth() {
        return depth;
    }


    public boolean pop() {

        depth--;

        if (depth < 0) {
            depth = 0;

            throw new EmptyStackException();
        } else if (isInBounds()) {
            return stack[depth];
        } else {
            return defaultValue;
        }
    }
}
