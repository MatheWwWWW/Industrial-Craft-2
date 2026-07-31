/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.ILiquidHeatExchangerManager;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.LiquidHeatExchangerManager;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerLiquidHeatExchanger;
import ic2.core.block.tileentity.TileEntityHeatSourceInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class TileEntityLiquidHeatExchanger
extends TileEntityHeatSourceInventory
implements IHasGui,
IUpgradableBlock {
    private boolean newActive;
    public final Ic2FluidTank inputTank;
    public final Ic2FluidTank outputTank;
    public final InvSlotConsumable heatexchangerslots;
    public final InvSlotOutput hotoutputSlot;
    public final InvSlotOutput cooloutputSlot;
    public final InvSlotConsumableLiquid hotfluidinputSlot;
    public final InvSlotConsumableLiquid coolfluidinputSlot;
    public final InvSlotUpgrade upgradeSlot;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityLiquidHeatExchanger(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityHeatSourceInventory>)Ic2BlockEntities.LIQUID_HEAT_EXCHANGER, blockPos, blockState);
        this.inputTank = this.fluids.addTankInsert("inputTank", 2000, Fluids.fluidPredicate(Recipes.liquidCooldownManager));
        this.outputTank = this.fluids.addTankExtract("outputTank", 2000);
        this.heatexchangerslots = new InvSlotConsumableItemStack(this, "heatExchanger", 10, new ItemStack((ItemLike)Ic2Items.HEAT_CONDUCTOR));
        this.heatexchangerslots.setStackSizeLimit(1);
        this.hotoutputSlot = new InvSlotOutput(this, "hotOutputSlot", 1);
        this.cooloutputSlot = new InvSlotOutput(this, "outputSlot", 1);
        this.hotfluidinputSlot = new InvSlotConsumableLiquidByManager(this, "hotFluidInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, Recipes.liquidCooldownManager);
        this.coolfluidinputSlot = new InvSlotConsumableLiquidByTank(this, "coolFluidOutput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, this.outputTank);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3);
        this.newActive = false;
    }

    public static void init() {
        Recipes.liquidCooldownManager = new LiquidHeatExchangerManager(false);
        Recipes.liquidHeatupManager = new LiquidHeatExchangerManager(true);
        IC2.envProxy.runAfterRegistryInit(() -> {
            TileEntityLiquidHeatExchanger.addCooldownRecipe((Fluid)net.minecraft.world.level.material.Fluids.f_76195_, Ic2Fluids.PAHOEHOE_LAVA.still, Math.round(20.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/fluidconversion/heatExchangerLava")));
            TileEntityLiquidHeatExchanger.addBiDiRecipe(Ic2Fluids.HOT_COOLANT.still, Ic2Fluids.COOLANT.still, Math.round(20.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/fluidconversion/heatExchangerHotCoolant")));
            TileEntityLiquidHeatExchanger.addHeatupRecipe(Ic2Fluids.HOT_WATER.still, (Fluid)net.minecraft.world.level.material.Fluids.f_76193_, Math.round(1.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/fluidconversion/heatExchangerWater")));
        });
    }

    public static void addBiDiRecipe(Fluid fluid, Fluid fluid2, int n) {
        TileEntityLiquidHeatExchanger.addHeatupRecipe(fluid, fluid2, n);
        TileEntityLiquidHeatExchanger.addCooldownRecipe(fluid, fluid2, n);
    }

    public static void addHeatupRecipe(Fluid fluid, Fluid fluid2, int n) {
        Recipes.liquidHeatupManager.addFluid(fluid2, fluid, n);
    }

    public static void addCooldownRecipe(Fluid fluid, Fluid fluid2, int n) {
        Recipes.liquidCooldownManager.addFluid(fluid, fluid2, n);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.hotfluidinputSlot.processIntoTank(this.inputTank, this.hotoutputSlot);
        this.coolfluidinputSlot.processFromTank(this.outputTank, this.cooloutputSlot);
        boolean bl = this.newActive = this.HeatBuffer > 0;
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
        this.upgradeSlot.tick();
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerLiquidHeatExchanger(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerLiquidHeatExchanger(n, inventory, this);
    }

    public int gaugeLiquidScaled(int n, int n2) {
        switch (n2) {
            case 0: {
                if (this.inputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.inputTank.getFluidAmount() * n / this.inputTank.getCapacity();
            }
            case 1: {
                if (this.outputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.outputTank.getFluidAmount() * n / this.outputTank.getCapacity();
            }
        }
        return 0;
    }

    @Override
    public int getMaxHeatEmittedPerTick() {
        int n = 0;
        for (int i = 0; i < this.heatexchangerslots.size(); ++i) {
            if (this.heatexchangerslots.isEmpty(i)) continue;
            n += 10;
        }
        return n;
    }

    @Override
    protected int fillHeatBuffer(int n) {
        if (n > 0) {
            int n2 = this.inputTank.getFluidAmount();
            int n3 = this.outputTank.getCapacity() - this.outputTank.getFluidAmount();
            Ic2FluidStack ic2FluidStack = null;
            if (n3 == 0 || n2 == 0) {
                return 0;
            }
            Fluid fluid = this.inputTank.getFluidStack().getFluid();
            Fluid fluid2 = null;
            int n4 = 0;
            if (Recipes.liquidCooldownManager.acceptsFluid(fluid)) {
                ILiquidHeatExchangerManager.HeatExchangeProperty heatExchangeProperty = Recipes.liquidCooldownManager.getHeatExchangeProperty(fluid);
                fluid2 = heatExchangeProperty.outputFluid;
                n4 = heatExchangeProperty.huPerMB;
            }
            if (fluid2 == null) {
                return 0;
            }
            if (this.outputTank.getFluidAmount() > 0 && !this.outputTank.getFluidStack().hasExactFluid(fluid2)) {
                return 0;
            }
            int n5 = n / n4;
            ic2FluidStack = n3 >= n2 ? (n5 <= n2 ? this.inputTank.drainMbUnchecked(n5, true) : this.inputTank.drainMbUnchecked(n2, true)) : (n5 <= n3 ? this.inputTank.drainMbUnchecked(n5, true) : this.inputTank.drainMbUnchecked(n3 * 20, true));
            if (ic2FluidStack != null) {
                this.inputTank.drainMbUnchecked(ic2FluidStack.getAmountMb(), false);
                this.outputTank.fillMbUnchecked(Ic2FluidStack.create(fluid2, ic2FluidStack.getAmountMb()), false);
                return ic2FluidStack.getAmountMb() * n4;
            }
        }
        return 0;
    }

    public Ic2FluidTank getInputTank() {
        return this.inputTank;
    }

    public Ic2FluidTank getOutputTank() {
        return this.outputTank;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return true;
    }
}

