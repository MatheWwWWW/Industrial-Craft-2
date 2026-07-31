/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.Direction
 */
package ic2.core.block.transport.fluid.graph;

import ic2.core.block.transport.fluid.graph.IFluidPipe;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.core.Direction;

public interface ISimpleFluidSource
extends IFluidPipe {
    @Override
    default public boolean hasAnchor(Direction side) {
        return false;
    }

    @Override
    default public boolean addAnchor(Direction side) {
        return false;
    }

    @Override
    default public boolean removeAnchor(Direction side) {
        return false;
    }

    @Override
    default public boolean canPushFluid(Direction dir) {
        return true;
    }

    @Override
    default public boolean canReceiveFluid(Direction dir) {
        return true;
    }

    @Override
    default public List<IFluidPipe.FluidOutput> getOutputs() {
        return ObjectLists.emptyList();
    }

    @Override
    default public Iterable<Direction> getEmitterSources() {
        return ObjectLists.singleton(null);
    }
}

