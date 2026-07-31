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

    GridUpdater(EnergyNetLocal energyNetLocal) {
        this.enet = energyNetLocal;
    }

    void startChangeCalc(Queue<GridChange> queue, Map<IEnergyTile, GridChange> map) {
        GridChange gridChange;
        assert (!queue.isEmpty());
        assert (this.changes.isEmpty());
        assert (!this.busy);
        this.busy = true;
        this.isChangeStep = true;
        while ((gridChange = queue.poll()) != null && gridChange != EnergyNetLocal.QUEUE_DELAY_CHANGE) {
            this.changes.add(gridChange);
            if (gridChange.type != GridChange.Type.ADDITION) continue;
            GridChange gridChange2 = map.remove(gridChange.ioTile);
            assert (gridChange2 == gridChange);
        }
        this.prepareUpdate();
        IC2.threadPool.execute(this);
    }

    void startTransferCalc() {
        assert (!this.busy);
        this.isChangeStep = false;
        if (this.enet.hasGrids() && EnergyNetGlobal.getCalculator().runSyncStep(this.enet)) {
            this.busy = true;
            Collection<Grid> collection = this.enet.getGrids();
            this.pendingCalculations.set(collection.size());
            int n = 0;
            for (Grid grid : collection) {
                if (EnergyNetGlobal.getCalculator().runSyncStep(grid)) {
                    GridCalcTask gridCalcTask;
                    if (n < this.calcTaskCache.length) {
                        gridCalcTask = this.calcTaskCache[n];
                        if (gridCalcTask == null) {
                            this.calcTaskCache[n] = gridCalcTask = new GridCalcTask();
                        }
                        ++n;
                    } else {
                        gridCalcTask = new GridCalcTask();
                    }
                    gridCalcTask.grid = grid;
                    IC2.threadPool.execute(gridCalcTask);
                    continue;
                }
                this.pendingCalculations.decrementAndGet();
            }
            if (collection.size() > 1) {
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
        catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
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
        Iterator iterator = this.changes.iterator();
        while (iterator.hasNext()) {
            GridChange gridChange = (GridChange)iterator.next();
            if (ChangeHandler.prepareSync(this.enet, gridChange)) continue;
            iterator.remove();
        }
    }

    private void updateGrid() {
        GridChange gridChange;
        long l = 0L;
        int n = this.changes.size();
        while ((gridChange = this.changes.poll()) != null) {
            switch (gridChange.type) {
                case ADDITION: {
                    ChangeHandler.applyAddition(this.enet, gridChange.ioTile, gridChange.pos, gridChange.subTiles, this.changes);
                    break;
                }
                case REMOVAL: {
                    ChangeHandler.applyRemoval(this.enet, gridChange.ioTile, gridChange.pos);
                }
            }
        }
        this.notifyCalculator();
    }

    private void notifyCalculator() {
        ArrayList<Grid> arrayList = new ArrayList<Grid>();
        for (Grid object : this.enet.getGrids()) {
            if (!object.clearDirty()) continue;
            arrayList.add(object);
        }
        if (arrayList.isEmpty()) {
            this.clearBusy();
            return;
        }
        this.pendingCalculations.set(arrayList.size());
        if (arrayList.size() > 1) {
            for (int i = 1; i < arrayList.size(); ++i) {
                GridUpdateTask gridUpdateTask = new GridUpdateTask();
                gridUpdateTask.grid = (Grid)arrayList.get(i);
                IC2.threadPool.execute(gridUpdateTask);
            }
        }
        EnergyNetGlobal.getCalculator().handleGridChange((Grid)arrayList.get(0));
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

