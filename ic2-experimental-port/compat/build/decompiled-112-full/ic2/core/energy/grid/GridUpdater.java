/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.energy.grid;

import ic2.api.energy.tile.IEnergyTile;
import ic2.core.IC2;
import ic2.core.energy.grid.ChangeHandler;
import ic2.core.energy.grid.EnergyNetGlobal;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.Grid;
import ic2.core.energy.grid.GridChange;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

class GridUpdater
implements Runnable {
    private final EnergyNetLocal enet;
    private final Queue<GridChange> changes = new ArrayDeque<GridChange>();
    private final GridCalcTask[] calcTaskCache = new GridCalcTask[16];
    private final AtomicInteger pendingCalculations = new AtomicInteger(0);
    private boolean busy;
    private boolean isChangeStep;

    GridUpdater(EnergyNetLocal enet) {
        this.enet = enet;
    }

    void startChangeCalc(Queue<GridChange> changes, Map<IEnergyTile, GridChange> additions) {
        GridChange change;
        assert (!changes.isEmpty());
        assert (this.changes.isEmpty());
        assert (!this.busy);
        this.busy = true;
        this.isChangeStep = true;
        while ((change = changes.poll()) != null && change != EnergyNetLocal.QUEUE_DELAY_CHANGE) {
            this.changes.add(change);
            if (change.type != GridChange.Type.ADDITION) continue;
            GridChange removedChange = additions.remove(change.ioTile);
            assert (removedChange == change);
        }
        this.prepareUpdate();
        IC2.getInstance().threadPool.execute(this);
    }

    void startTransferCalc() {
        assert (!this.busy);
        this.isChangeStep = false;
        if (this.enet.hasGrids() && EnergyNetGlobal.getCalculator().runSyncStep(this.enet)) {
            this.busy = true;
            Collection<Grid> grids = this.enet.getGrids();
            this.pendingCalculations.set(grids.size());
            int cacheIdx = 0;
            for (Grid grid : grids) {
                if (EnergyNetGlobal.getCalculator().runSyncStep(grid)) {
                    GridCalcTask task;
                    if (cacheIdx < this.calcTaskCache.length) {
                        task = this.calcTaskCache[cacheIdx];
                        if (task == null) {
                            this.calcTaskCache[cacheIdx] = task = new GridCalcTask();
                        }
                        ++cacheIdx;
                    } else {
                        task = new GridCalcTask();
                    }
                    task.grid = grid;
                    IC2.getInstance().threadPool.execute(task);
                    continue;
                }
                this.pendingCalculations.decrementAndGet();
            }
            if (grids.size() > 1) {
                this.enet.shuffleGrids();
            }
            if (this.pendingCalculations.get() == 0) {
                this.busy = false;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void awaitCompletion() {
        try {
            GridUpdater gridUpdater = this;
            synchronized (gridUpdater) {
                while (this.busy) {
                    this.wait();
                }
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInChangeStep() {
        return this.isChangeStep;
    }

    @Override
    public void run() {
        this.updateGrid();
    }

    private void prepareUpdate() {
        Iterator it = this.changes.iterator();
        while (it.hasNext()) {
            GridChange change = (GridChange)it.next();
            if (ChangeHandler.prepareSync(this.enet, change)) continue;
            it.remove();
        }
    }

    private void updateGrid() {
        GridChange change;
        long startTime = 0L;
        int totalChanges = this.changes.size();
        while ((change = this.changes.poll()) != null) {
            switch (change.type) {
                case ADDITION: {
                    ChangeHandler.applyAddition(this.enet, change.ioTile, change.pos, change.subTiles, this.changes);
                    break;
                }
                case REMOVAL: {
                    ChangeHandler.applyRemoval(this.enet, change.ioTile, change.pos);
                }
            }
        }
        this.notifyCalculator();
    }

    private void notifyCalculator() {
        ArrayList<Grid> dirtyGrids = new ArrayList<Grid>();
        for (Grid grid : this.enet.getGrids()) {
            if (!grid.clearDirty()) continue;
            dirtyGrids.add(grid);
        }
        if (dirtyGrids.isEmpty()) {
            this.clearBusy();
            return;
        }
        this.pendingCalculations.set(dirtyGrids.size());
        if (dirtyGrids.size() > 1) {
            for (int i = 1; i < dirtyGrids.size(); ++i) {
                GridUpdateTask task = new GridUpdateTask();
                task.grid = (Grid)dirtyGrids.get(i);
                IC2.getInstance().threadPool.execute(task);
            }
        }
        EnergyNetGlobal.getCalculator().handleGridChange((Grid)dirtyGrids.get(0));
        this.onTaskDone();
    }

    private void onTaskDone() {
        if (this.pendingCalculations.decrementAndGet() == 0) {
            this.clearBusy();
        }
    }

    private synchronized void clearBusy() {
        this.busy = false;
        this.notifyAll();
    }

    private class GridCalcTask
    implements Runnable {
        Grid grid;

        private GridCalcTask() {
        }

        @Override
        public void run() {
            EnergyNetGlobal.getCalculator().runAsyncStep(this.grid);
            this.grid = null;
            GridUpdater.this.onTaskDone();
        }
    }

    private class GridUpdateTask
    implements Runnable {
        Grid grid;

        private GridUpdateTask() {
        }

        @Override
        public void run() {
            EnergyNetGlobal.getCalculator().handleGridChange(this.grid);
            this.grid = null;
            GridUpdater.this.onTaskDone();
        }
    }
}

