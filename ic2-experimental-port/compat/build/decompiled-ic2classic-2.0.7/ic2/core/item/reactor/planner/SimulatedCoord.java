/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.reactor.planner;

import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;

public class SimulatedCoord {
    public SimulatedStack stack;
    public int x;
    public int y;

    public SimulatedCoord(SimulatedStack stack, int x, int y) {
        this.stack = stack;
        this.x = x;
        this.y = y;
    }

    public int storeHeat(ISimulatedReactor reactor, int toAdd) {
        return this.stack.storeHeat(reactor, this.x, this.y, toAdd);
    }

    public int getTransferRate(ISimulatedReactor reactor, double medium) {
        return (int)(medium * (double)this.stack.getMaxStoredHeat(reactor, this.x, this.y) - (double)this.stack.getStoredHeat(reactor, this.x, this.y));
    }

    public static class SimulatedDirCoord
    extends SimulatedCoord {
        boolean dir;

        public SimulatedDirCoord(SimulatedStack stack, int x, int y, boolean dir) {
            super(stack, x, y);
            this.dir = dir;
        }

        @Override
        public int getTransferRate(ISimulatedReactor reactor, double medium) {
            int prev = super.getTransferRate(reactor, medium);
            return prev > 0 != this.dir ? prev : 0;
        }
    }
}

