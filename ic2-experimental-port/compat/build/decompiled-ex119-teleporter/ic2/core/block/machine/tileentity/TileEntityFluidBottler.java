/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
import ic2.api.recipe.IFillFluidContainerRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.ContainerBase;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.machine.container.ContainerFluidBottler;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityFluidBottler
extends TileEntityStandardMachine<Void, Object, Object> {
    public final InvSlotConsumableLiquid drainInputSlot;
    public final InvSlotConsumableLiquid fillInputSlot;
    @GuiSynced
    public final Ic2FluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityFluidBottler(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.FLUID_BOTTLER, blockPos, blockState, 2, 100, 1);
        this.fluidTank = this.fluids.addTank("fluidTank", 8000);
        this.drainInputSlot = new InvSlotConsumableLiquidByTank(this, "drainInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, this.fluidTank);
        this.fillInputSlot = new InvSlotConsumableLiquidByTank(this, "fillInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, this.fluidTank);
    }

    @Override
    protected Collection<ItemStack> getOutput(Object object) {
        if (object instanceof IEmptyFluidContainerRecipeManager.Output) {
            return ((IEmptyFluidContainerRecipeManager.Output)object).container;
        }
        return super.getOutput(object);
    }

    @Override
    public void operateOnce(MachineRecipeResult<Void, Object, Object> machineRecipeResult, Collection<ItemStack> collection) {
        if (machineRecipeResult.getOutput() instanceof IEmptyFluidContainerRecipeManager.Output) {
            this.drainInputSlot.put((ItemStack)machineRecipeResult.getAdjustedInput());
            Ic2FluidStack ic2FluidStack = ((IEmptyFluidContainerRecipeManager.Output)machineRecipeResult.getOutput()).fluid;
            this.fluidTank.fillMbUnchecked(ic2FluidStack, false);
        } else {
            IFillFluidContainerRecipeManager.Input input = (IFillFluidContainerRecipeManager.Input)machineRecipeResult.getAdjustedInput();
            this.fillInputSlot.put(input.container);
            this.fluidTank.drainMbUnchecked(input.fluid == null ? this.fluidTank.getFluidAmount() : this.fluidTank.getFluidAmount() - input.fluid.getAmountMb(), false);
        }
        this.outputSlot.add(collection);
    }

    @Override
    public MachineRecipeResult<Void, Object, Object> getOutput() {
        MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> machineRecipeResult = Recipes.emptyFluidContainer.apply(this.drainInputSlot.get(), this.fluidTank.isEmpty() ? null : this.fluidTank.getFluidStack().getFluid(), FluidContainerOutputMode.EmptyFullToOutput, false);
        if (machineRecipeResult != null && machineRecipeResult.getOutput().fluid.getAmountMb() <= this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount() && this.outputSlot.canAdd(machineRecipeResult.getOutput().container)) {
            return machineRecipeResult;
        }
        MachineRecipeResult<Void, Object, Object> machineRecipeResult2 = Recipes.fillFluidContainer.apply(new IFillFluidContainerRecipeManager.Input(this.fillInputSlot.get(), this.fluidTank.getFluidStack()), FluidContainerOutputMode.EmptyFullToOutput, false);
        if (machineRecipeResult2 != null && this.outputSlot.canAdd(machineRecipeResult2.getOutput())) {
            return machineRecipeResult2;
        }
        return null;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerFluidBottler(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerFluidBottler(n, inventory, this);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing});
    }
}

