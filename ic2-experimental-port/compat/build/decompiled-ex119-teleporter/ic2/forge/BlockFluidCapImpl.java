/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.common.util.NonNullSupplier
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.forge;

import ic2.core.fluid.FluidTankInfo;
import ic2.core.fluid.Ic2FluidBlock;
import ic2.core.util.Util;
import ic2.forge.EnvFluidHandlerForge;
import ic2.forge.Ic2FluidStackImpl;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

final class BlockFluidCapImpl
implements ICapabilityProvider {
    private final Ic2FluidBlock parent;
    private final BlockEntity be;
    private final LazyOptional<IFluidHandler>[] sides;

    public BlockFluidCapImpl(Ic2FluidBlock ic2FluidBlock, BlockEntity blockEntity) {
        this.parent = ic2FluidBlock;
        this.be = blockEntity;
        this.sides = new LazyOptional[Util.ALL_DIRS.length];
        for (Direction direction : Util.ALL_DIRS) {
            this.sides[direction.ordinal()] = LazyOptional.of((NonNullSupplier)new SideHandler(direction));
        }
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
        return direction != null && capability == ForgeCapabilities.FLUID_HANDLER ? this.sides[direction.ordinal()] : LazyOptional.empty();
    }

    private class SideHandler
    implements IFluidHandler,
    NonNullSupplier<IFluidHandler> {
        private final Direction side;

        SideHandler(Direction direction) {
            this.side = direction;
        }

        public int getTanks() {
            FluidTankInfo[] fluidTankInfoArray = BlockFluidCapImpl.this.parent.getTankInfos(null, null, null, BlockFluidCapImpl.this.be);
            if (fluidTankInfoArray == null) {
                return 0;
            }
            int n = 1 << this.side.ordinal();
            int n2 = 0;
            for (FluidTankInfo fluidTankInfo : fluidTankInfoArray) {
                if (((fluidTankInfo.getDrainSideMask() | fluidTankInfo.getFillSideMask()) & n) == 0) continue;
                ++n2;
            }
            return n2;
        }

        private FluidTankInfo getTankInfo(int n) {
            FluidTankInfo[] fluidTankInfoArray = BlockFluidCapImpl.this.parent.getTankInfos(null, null, null, BlockFluidCapImpl.this.be);
            if (fluidTankInfoArray == null) {
                return null;
            }
            int n2 = 1 << this.side.ordinal();
            for (FluidTankInfo fluidTankInfo : fluidTankInfoArray) {
                if (((fluidTankInfo.getDrainSideMask() | fluidTankInfo.getFillSideMask()) & n2) == 0 || n-- != 0) continue;
                return fluidTankInfo;
            }
            return null;
        }

        public int getTankCapacity(int n) {
            FluidTankInfo fluidTankInfo = this.getTankInfo(n);
            return fluidTankInfo != null ? fluidTankInfo.getCapacity() : 0;
        }

        public FluidStack getFluidInTank(int n) {
            FluidTankInfo fluidTankInfo = this.getTankInfo(n);
            return fluidTankInfo != null ? EnvFluidHandlerForge.getForgeFs(fluidTankInfo.getContent()) : FluidStack.EMPTY;
        }

        public boolean isFluidValid(int n, FluidStack fluidStack) {
            FluidTankInfo fluidTankInfo = this.getTankInfo(n);
            return fluidTankInfo != null && fluidTankInfo.getCapacity() > 0;
        }

        public FluidStack drain(int n, IFluidHandler.FluidAction fluidAction) {
            if (n <= 0) {
                return FluidStack.EMPTY;
            }
            return EnvFluidHandlerForge.getForgeFs(BlockFluidCapImpl.this.parent.drainMb(null, null, null, BlockFluidCapImpl.this.be, this.side, n, fluidAction.simulate()));
        }

        public FluidStack drain(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction) {
            if (fluidStack == null || fluidStack.isEmpty()) {
                return FluidStack.EMPTY;
            }
            int n = BlockFluidCapImpl.this.parent.drainMb(null, null, null, BlockFluidCapImpl.this.be, this.side, new Ic2FluidStackImpl(fluidStack), fluidAction.simulate());
            if (n <= 0) {
                return FluidStack.EMPTY;
            }
            fluidStack = fluidStack.copy();
            fluidStack.setAmount(n);
            return fluidStack;
        }

        public int fill(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction) {
            if (fluidStack == null || fluidStack.isEmpty()) {
                return 0;
            }
            return BlockFluidCapImpl.this.parent.fillMb(null, null, null, BlockFluidCapImpl.this.be, this.side, new Ic2FluidStackImpl(fluidStack), fluidAction.simulate());
        }

        public IFluidHandler get() {
            return this;
        }
    }
}

