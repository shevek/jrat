package org.shiftone.jrat.util.time;

/**
 * @author Jeff Drost
 */
public class PauseMovement implements Movement {

    private Movement movement;

    private static final ThreadLocal STATE = new ThreadLocal() {
        protected Object initialValue() {
            return new ThreadState();
        }
    };

    public PauseMovement(Movement movement) {
        this.movement = movement;
    }

    public void pauseTime() {
        ThreadState state = (ThreadState) STATE.get();
        if (state.pauseTime == 0) {
            // if not paused, record the pause time (now)
            state.pauseTime = movement.currentTimeNanos();
        }
    }

    public void resumeTime() {
        ThreadState state = (ThreadState) STATE.get();
        if (state.pauseTime != 0) {
            // if paused, calc the new skew
            state.clockSkew += (movement.currentTimeNanos() - state.pauseTime);
            state.pauseTime = 0;
        }
    }

    public long currentTimeNanos() {
        ThreadState state = (ThreadState) STATE.get();
        return movement.currentTimeNanos() - state.clockSkew;
    }

    private static class ThreadState {
        private long clockSkew; // starts with 0 and grows for each pause
        private long pauseTime; // 0 indicates the clock is not paused
    }
}
