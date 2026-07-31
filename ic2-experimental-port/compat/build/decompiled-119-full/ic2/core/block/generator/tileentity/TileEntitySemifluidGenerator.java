/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.generator.tileentity;

import ic2.api.recipe.ISemiFluidFuelManager;
import ic2.api.recipe.Recipes;
import ic2.core.SemiFluidFuelManager;
import ic2.core.block.comp.Fluids;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

@NotClassic
public class TileEntitySemifluidGenerator
extends TileEntityBaseGenerator {
    public final InvSlotConsumableLiquid fluidSlot;
    public final InvSlotOutput outputSlot;
    @GuiSynced
    protected final Ic2FluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntitySemifluidGenerator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.SEMIFLUID_GENERATOR, blockPos, blockState, 32.0, 1, 32000);
        this.fluidTank = this.fluids.addTankInsert("fluid", 10000, Fluids.fluidPredicate(Recipes.semiFluidGenerator));
        this.fluidSlot = new InvSlotConsumableLiquidByManager(this, "fluidSlot", 1, Recipes.semiFluidGenerator);
        this.outputSlot = new InvSlotOutput(this, "output", 1);
    }

    public static void init() {
        Recipes.semiFluidGenerator = new SemiFluidFuelManager();
        if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiogas") > 0.0f) {
            TileEntitySemifluidGenerator.addFuel(Ic2Fluids.BIOGAS.still, 10, Math.round(16.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiogas")));
        }
    }

    public static void addFuel(Fluid fluid, int n, int n2) {
        Recipes.semiFluidGenerator.addFluid(fluid, n, n2);
    }

    @Override
    public void updateEntityServer() {
        super.updateEntityServer();
        if (this.fluidSlot.processIntoTank(this.fluidTank, this.outputSlot)) {
            this.m_6596_();
        }
    }

    @Override
    public boolean gainFuel() {
        ISemiFluidFuelManager.BurnProperty burnProperty;
        boolean bl = false;
        Ic2FluidStack ic2FluidStack = this.fluidTank.drainMbUnchecked(Integer.MAX_VALUE, true);
        if (ic2FluidStack != null && (burnProperty = Recipes.semiFluidGenerator.getBurnProperty(ic2FluidStack.getFluid())) != null && ic2FluidStack.getAmountMb() >= burnProperty.amount) {
            this.fluidTank.drainMbUnchecked(burnProperty.amount, false);
            this.production = burnProperty.power;
            this.fuel += burnProperty.amount;
            bl = true;
        }
        return bl;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.GENERATOR_GEOTHERMAL_LOOP;
    }
}

