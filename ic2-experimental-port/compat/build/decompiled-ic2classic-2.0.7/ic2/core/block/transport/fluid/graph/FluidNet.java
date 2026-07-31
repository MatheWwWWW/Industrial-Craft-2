/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.transport.fluid.graph;

import ic2.core.block.transport.fluid.graph.FluidGrid;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidNet {
    public static final FluidNet INSTANCE = new FluidNet();
    static final Map<Level, FluidGrid> WORLDS = CollectionUtils.createMap();

    public void addPipe(IFluidPipe pipe) {
        FluidGrid grid = this.getOrCreate(pipe.getWorldObj());
        if (grid != null) {
            grid.addPipe(pipe);
        }
    }

    public void removePipe(IFluidPipe pipe) {
        FluidGrid grid = this.getOrCreate(pipe.getWorldObj());
        if (grid != null) {
            grid.removePipe(pipe);
        }
    }

    public void updatePipe(IFluidPipe pipe) {
        FluidGrid grid = this.getOrCreate(pipe.getWorldObj());
        if (grid != null) {
            grid.updatePipe(pipe);
        }
    }

    public List<FluidGrid.FluidPath> getPaths(IFluidPipe pump, Direction dir) {
        FluidGrid grid = this.getOrCreate(pump.getWorldObj());
        return grid == null ? ObjectLists.emptyList() : grid.getPaths(pump, dir);
    }

    public int sendFluid(IFluidPipe pipe, Direction dir, FluidStack fluid, int pressure) {
        return this.sendFluid(pipe, dir, fluid, pressure, IFluidHandler.FluidAction.EXECUTE);
    }

    public int sendFluid(IFluidPipe pipe, Direction dir, FluidStack fluid, int pressure, IFluidHandler.FluidAction action) {
        if (fluid.isEmpty() || pressure <= 0) {
            return 0;
        }
        FluidGrid grid = WORLDS.get(pipe.getWorldObj());
        return grid != null ? grid.pump(pipe, dir, fluid, pressure, action) : 0;
    }

    public IFluidPipe getPipe(Level world, BlockPos pos) {
        FluidGrid grid = this.getOrCreate(world);
        return grid != null ? grid.getPipe(pos) : null;
    }

    public TransportStats getStats(IFluidPipe pipe) {
        FluidGrid grid = this.getOrCreate(pipe.getWorldObj());
        return grid != null ? grid.getStats(pipe) : new TransportStats(0L, 0L);
    }

    FluidGrid getOrCreate(Level world) {
        if (world == null) {
            return null;
        }
        FluidGrid local = WORLDS.get(world);
        if (local == null) {
            local = new FluidGrid(world);
            WORLDS.put(world, local);
        }
        return local;
    }

    public static void onTick(Level world) {
        FluidGrid grid = WORLDS.get(world);
        if (grid != null) {
            grid.onTick();
        }
    }

    public static void onWorldUnload(Level world) {
        WORLDS.remove(world);
    }

    public static void onServerStopped() {
        WORLDS.clear();
    }

    public static class TransportStats {
        long in;
        long out;

        public TransportStats(long in, long out) {
            this.in = in;
            this.out = out;
        }

        public long getIn() {
            return this.in;
        }

        public long getOut() {
            return this.out;
        }
    }
}

