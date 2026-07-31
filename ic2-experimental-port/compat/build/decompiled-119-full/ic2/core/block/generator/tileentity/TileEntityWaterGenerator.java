/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 */
package ic2.core.block.generator.tileentity;

import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityBaseRotorGenerator;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

public class TileEntityWaterGenerator
extends TileEntityBaseRotorGenerator
implements IGuiValueProvider {
    private static final double energyMultiplier = ConfigUtil.getDouble(MainConfig.get(), "balance/energy/generator/water");
    private static final boolean allowAutomation = ConfigUtil.getBool(MainConfig.get(), "balance/watermillAutomation");
    public final InvSlotConsumableLiquid fuelSlot;
    private static final int tickRate = 128;
    private int ticker = IC2.random.m_188503_(128);
    @GuiSynced
    public int water = 0;
    public int microStorage = 0;
    public int maxWater = 2000;

    public TileEntityWaterGenerator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.WATER_GENERATOR, blockPos, blockState, 2.0, 1, 4, 2);
        this.production = 2.0;
        this.fuelSlot = new InvSlotConsumableLiquidByList(this, "fuel", allowAutomation ? InvSlot.Access.IO : InvSlot.Access.NONE, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, new Fluid[]{Fluids.f_76193_});
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateWaterCount();
    }

    @Override
    public boolean gainFuel() {
        if (this.fuel + 500 > this.maxWater) {
            return false;
        }
        if (!this.fuelSlot.isEmpty()) {
            ItemStack itemStack = this.fuelSlot.consume(1);
            if (itemStack == null) {
                return false;
            }
            this.fuel += 500;
            this.production = IC2.envProxy.hasRecipeRemainder(itemStack) ? 1.0 : 2.0;
            return true;
        }
        if (this.fuel <= 0) {
            this.flowPower();
            this.production = this.microStorage / 100;
            this.microStorage = (int)((double)this.microStorage - this.production * 100.0);
            if (this.production > 0.0) {
                ++this.fuel;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean isConverting() {
        return this.fuel > 0;
    }

    @Override
    public boolean needsFuel() {
        return this.fuel <= this.maxWater;
    }

    public void flowPower() {
        if (++this.ticker % 128 == 0) {
            this.updateWaterCount();
        }
        this.water = (int)Math.round((double)this.water * energyMultiplier);
        if (this.water > 0) {
            this.microStorage += this.water;
        }
    }

    public void updateWaterCount() {
        Level level = this.m_58904_();
        int n = 0;
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                for (int k = -1; k < 2; ++k) {
                    if (level.m_8055_(this.f_58858_.m_7918_(i, j, k)).m_60767_() != Material.f_76305_) continue;
                    ++n;
                }
            }
        }
        this.water = n;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.GENERATOR_WATER_LOOP;
    }

    @Override
    protected boolean delayActiveUpdate() {
        return true;
    }

    @Override
    protected boolean shouldRotorRotate() {
        return this.water > 0 || this.fuel > 0;
    }

    @Override
    protected float rotorSpeedFactor() {
        return this.fuel > 0 ? 1.0f : (float)this.water / 25.0f;
    }

    @Override
    public double getGuiValue(String string) {
        if ("water".equals(string)) {
            assert (this.maxWater > 0);
            return (double)this.fuel / (double)this.maxWater;
        }
        throw new IllegalArgumentException("Unexpected value requested: " + string);
    }
}

