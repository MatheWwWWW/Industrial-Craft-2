/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.transport.fluid.graph;

import ic2.api.tiles.IAnchorTile;
import ic2.api.util.ILocation;
import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidPipe
extends ILocation,
IAnchorTile {
    public boolean canPushFluid(Direction var1);

    public boolean canReceiveFluid(Direction var1);

    default public boolean isFluidAcceptor() {
        return false;
    }

    public List<FluidOutput> getOutputs();

    public Iterable<Direction> getEmitterSources();

    public static final class FluidOutput {
        final IFluidPipe source;
        final long position;
        final Direction dir;
        final IFluidHandler handler;
        final IntSupplier priority;
        final Predicate<Fluid> filter;

        public FluidOutput(IFluidPipe source, BlockPos position, Direction dir, IFluidHandler handler, IntSupplier priority, Predicate<Fluid> filter) {
            this.source = source;
            this.position = position.m_121878_();
            this.dir = dir;
            this.handler = handler;
            this.priority = priority;
            this.filter = filter;
        }

        public int hashCode() {
            return Objects.hash(this.dir, this.position);
        }

        public boolean equals(Object obj) {
            if (obj instanceof FluidOutput) {
                FluidOutput output = (FluidOutput)obj;
                return output.dir == this.dir && output.position == this.position;
            }
            return false;
        }

        public int getPriority() {
            return this.priority.getAsInt();
        }

        public IFluidHandler getHandler() {
            return this.handler;
        }

        public boolean isFluidAllowed(Fluid fluid) {
            return this.filter.test(fluid);
        }
    }
}

