/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.machine.tileentity;

import ic2.api.recipe.IElectrolyzerRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerElectrolyzer;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityTank;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.CustomGauge;
import ic2.core.network.GrowingBuffer;
import ic2.core.recipe.ElectrolyzerRecipeManager;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.util.LiquidUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class TileEntityElectrolyzer
extends TileEntityElectricMachine
implements IUpgradableBlock,
IHasGui,
CustomGauge.IGaugeRatioProvider {
    protected int progress = 0;
    protected IElectrolyzerRecipeManager.ElectrolyzerRecipe recipe = null;
    protected Ic2FluidTank input;
    public final InvSlotUpgrade upgradeSlot;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityElectrolyzer(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.ELECTROLYZER, blockPos, blockState, 32000, 2);
        this.input = this.fluids.addTankInsert("input", 8000, Fluids.fluidPredicate(Recipes.electrolyzer));
        this.upgradeSlot = new InvSlotUpgrade(this, "upgradeSlot", 4);
    }

    public static void init() {
        Recipes.electrolyzer = new ElectrolyzerRecipeManager();
        Recipes.electrolyzer.addRecipe((Fluid)net.minecraft.world.level.material.Fluids.f_76193_, 40, 32, new IElectrolyzerRecipeManager.ElectrolyzerOutput(Ic2Fluids.HYDROGEN.still, 26, Direction.DOWN), new IElectrolyzerRecipeManager.ElectrolyzerOutput(Ic2Fluids.OXYGEN.still, 13, Direction.UP));
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.progress = compoundTag.m_128451_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("progress", this.progress);
    }

    @Override
    public void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.canOperate()) {
            assert (this.recipe != null);
            this.setActive(true);
            this.energy.useEnergy(this.recipe.EUaTick);
            ++this.progress;
            if (this.progress >= this.recipe.ticksNeeded) {
                this.operate();
                this.progress = 0;
                bl = true;
            }
        } else {
            this.setActive(false);
            this.progress = 0;
        }
        if (bl |= this.upgradeSlot.tickNoMark()) {
            super.m_6596_();
        }
    }

    protected boolean canOperate() {
        if (this.input.isEmpty()) {
            return false;
        }
        this.recipe = Recipes.electrolyzer.getElectrolysisInformation(this.input.getFluidStack().getFluid());
        if (this.recipe == null || this.energy.getEnergy() < (double)this.recipe.EUaTick || this.input.getFluidAmount() < this.recipe.inputAmount) {
            return false;
        }
        for (IElectrolyzerRecipeManager.ElectrolyzerOutput electrolyzerOutput : this.recipe.outputs) {
            if (this.canFillTank(electrolyzerOutput.tankDirection, electrolyzerOutput.getOutput())) continue;
            return false;
        }
        return true;
    }

    protected void operate() {
        assert (this.recipe != null);
        this.input.drainMbUnchecked(this.recipe.inputAmount, false);
        for (IElectrolyzerRecipeManager.ElectrolyzerOutput electrolyzerOutput : this.recipe.outputs) {
            this.fillTank(electrolyzerOutput.tankDirection, electrolyzerOutput.getOutput());
        }
    }

    protected boolean canFillTank(Direction direction, Ic2FluidStack ic2FluidStack) {
        BlockEntity blockEntity = this.m_58904_().m_7702_(this.f_58858_.m_121945_(direction));
        if (blockEntity instanceof TileEntityTank) {
            return LiquidUtil.fillTile(blockEntity, direction, ic2FluidStack, true) == ic2FluidStack.getAmountMb();
        }
        return false;
    }

    protected void fillTank(Direction direction, Ic2FluidStack ic2FluidStack) {
        BlockEntity blockEntity = this.m_58904_().m_7702_(this.f_58858_.m_121945_(direction));
        if (blockEntity instanceof TileEntityTank) {
            LiquidUtil.fillTile(blockEntity, direction, ic2FluidStack, false);
        }
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.FluidConsuming);
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerElectrolyzer(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerElectrolyzer(n, inventory, this);
    }

    public Ic2FluidTank getInput() {
        return this.input;
    }

    public boolean hasRecipe() {
        return this.getCurrentRecipe() != null;
    }

    public IElectrolyzerRecipeManager.ElectrolyzerRecipe getCurrentRecipe() {
        return this.recipe;
    }

    @Override
    public double getRatio() {
        return this.recipe == null ? 0.0 : (double)this.progress / (double)this.recipe.ticksNeeded;
    }
}

