/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  org.jetbrains.annotations.NotNull
 */
package ic2.core.block.transport.fluid.tiles;

import ic2.core.block.transport.fluid.tiles.PipeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class VoidPipeTileEntity
extends PipeTileEntity
implements IFluidHandler {
    public VoidPipeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.VOID_PIPE;
    }

    @Override
    public boolean canPushFluid(Direction dir) {
        return false;
    }

    @Override
    public boolean isFluidAcceptor() {
        return true;
    }

    public int getTanks() {
        return 1;
    }

    public FluidStack getFluidInTank(int tank) {
        return FluidStack.EMPTY;
    }

    public int getTankCapacity(int tank) {
        return Integer.MAX_VALUE;
    }

    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return resource.getAmount();
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }
}

