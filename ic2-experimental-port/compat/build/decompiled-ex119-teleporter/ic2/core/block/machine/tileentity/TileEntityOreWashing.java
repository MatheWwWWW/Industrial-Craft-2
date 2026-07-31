/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.DynamicContainer;
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
import net.minecraft.world.level.material.Fluid;

@NotClassic
public class TileEntityOreWashing
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    public final InvSlotConsumableLiquid fluidSlot;
    public final InvSlotOutput cellSlot;
    @GuiSynced
    protected final Ic2FluidTank fluidTank;
    protected final Fluids fluids;

    public TileEntityOreWashing(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.ORE_WASHING_PLANT, blockPos, blockState, 16, 500, 3);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, (Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>>)Recipes.oreWashing);
        this.fluidSlot = new InvSlotConsumableLiquidByList(this, "fluid", 1, new Fluid[]{net.minecraft.world.level.material.Fluids.f_76193_});
        this.cellSlot = new InvSlotOutput(this, "cell", 1);
        this.fluids = this.addComponent(new Fluids(this));
        this.fluidTank = this.fluids.addTankInsert("fluid", 8000, Fluids.fluidPredicate(new Fluid[]{net.minecraft.world.level.material.Fluids.f_76193_}));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
            this.gainFluid();
        }
    }

    @Override
    public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult, Collection<ItemStack> collection) {
        super.operateOnce(machineRecipeResult, collection);
        this.fluidTank.drainMbUnchecked(machineRecipeResult.getRecipe().getMetaData().m_128451_("amount"), false);
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult = super.getOutput();
        if (machineRecipeResult != null) {
            if (machineRecipeResult.getRecipe().getMetaData() == null) {
                return null;
            }
            if (machineRecipeResult.getRecipe().getMetaData().m_128451_("amount") > this.fluidTank.getFluidAmount()) {
                return null;
            }
        }
        return machineRecipeResult;
    }

    public boolean gainFluid() {
        return this.fluidSlot.processIntoTank(this.fluidTank, this.cellSlot);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming});
    }
}

