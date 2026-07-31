/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
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
import ic2.core.block.machine.gui.GuiFluidBottler;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityFluidBottler
extends TileEntityStandardMachine<Void, Object, Object> {
    public final InvSlotConsumableLiquid drainInputSlot;
    public final InvSlotConsumableLiquid fillInputSlot;
    @GuiSynced
    public final FluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityFluidBottler() {
        super(2, 100, 1);
        this.fluidTank = this.fluids.addTank("fluidTank", 8000);
        this.drainInputSlot = new InvSlotConsumableLiquidByTank(this, "drainInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.fluidTank);
        this.fillInputSlot = new InvSlotConsumableLiquidByTank(this, "fillInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.fluidTank);
    }

    @Override
    protected Collection<ItemStack> getOutput(Object output) {
        if (output instanceof IEmptyFluidContainerRecipeManager.Output) {
            return ((IEmptyFluidContainerRecipeManager.Output)output).container;
        }
        return super.getOutput(output);
    }

    @Override
    public void operateOnce(MachineRecipeResult<Void, Object, Object> result, Collection<ItemStack> processResult) {
        if (result.getOutput() instanceof IEmptyFluidContainerRecipeManager.Output) {
            this.drainInputSlot.put((ItemStack)result.getAdjustedInput());
            FluidStack fs = ((IEmptyFluidContainerRecipeManager.Output)result.getOutput()).fluid;
            this.fluidTank.fill(fs, true);
        } else {
            IFillFluidContainerRecipeManager.Input adjInput = (IFillFluidContainerRecipeManager.Input)result.getAdjustedInput();
            this.fillInputSlot.put(adjInput.container);
            this.fluidTank.drain(adjInput.fluid == null ? this.fluidTank.getFluidAmount() : this.fluidTank.getFluidAmount() - adjInput.fluid.amount, true);
        }
        this.outputSlot.add(processResult);
    }

    @Override
    public MachineRecipeResult<Void, Object, Object> getOutput() {
        MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> emptyRes = Recipes.emptyFluidContainer.apply(this.drainInputSlot.get(), this.fluidTank.getFluid() == null ? null : this.fluidTank.getFluid().getFluid(), FluidContainerOutputMode.EmptyFullToOutput, false);
        if (emptyRes != null && emptyRes.getOutput().fluid.amount <= this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount() && this.outputSlot.canAdd(emptyRes.getOutput().container)) {
            return emptyRes;
        }
        MachineRecipeResult<Void, Object, Object> fillRes = Recipes.fillFluidContainer.apply(new IFillFluidContainerRecipeManager.Input(this.fillInputSlot.get(), this.fluidTank.getFluid()), FluidContainerOutputMode.EmptyFullToOutput, false);
        if (fillRes != null && this.outputSlot.canAdd(fillRes.getOutput())) {
            return fillRes;
        }
        return null;
    }

    @Override
    public ContainerBase<TileEntityFluidBottler> getGuiContainer(EntityPlayer player) {
        return new ContainerFluidBottler(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiFluidBottler(new ContainerFluidBottler(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing});
    }
}

