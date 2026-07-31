/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.energy.grid;

import ic2.api.energy.NodeStats;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.Grid;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.Tile;
import java.io.PrintStream;

public interface IEnergyCalculator {
    public void handleGridChange(Grid var1);

    public boolean runSyncStep(EnergyNetLocal var1);

    public boolean runSyncStep(Grid var1);

    public void runAsyncStep(Grid var1);

    public NodeStats getNodeStats(Tile var1);

    public void dumpNodeInfo(Node var1, String var2, PrintStream var3, PrintStream var4);
}

