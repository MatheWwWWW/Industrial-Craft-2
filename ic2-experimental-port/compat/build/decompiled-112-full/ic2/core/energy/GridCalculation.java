/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.energy;

import ic2.core.energy.Grid;
import ic2.core.energy.Node;
import java.util.concurrent.Callable;

public class GridCalculation
implements Callable<Iterable<Node>> {
    private final Grid grid;

    public GridCalculation(Grid grid1) {
        this.grid = grid1;
    }

    @Override
    public Iterable<Node> call() throws Exception {
        return this.grid.calculate();
    }
}

