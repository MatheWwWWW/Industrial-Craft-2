/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotConsumableFuel;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ConfigUtil;
import ic2.core.util.ParticleUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityGenerator
extends TileEntityBaseGenerator
implements IGuiValueProvider {
    public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, false);
    @GuiSynced
    public int totalFuel = 0;

    public TileEntityGenerator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.GENERATOR, blockPos, blockState, Math.round(10.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/generator")), 1, 4000);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive()) {
            ParticleUtil.showFurnaceFlames(this.m_58904_(), this.f_58858_, this.getFacing());
        }
    }

    public double getFuelRatio() {
        if (this.fuel <= 0) {
            return 0.0;
        }
        return (double)this.fuel / (double)this.totalFuel;
    }

    @Override
    public boolean gainFuel() {
        int n = this.fuelSlot.consumeFuel() / 4;
        if (n == 0) {
            return false;
        }
        this.fuel += n;
        this.totalFuel = n;
        return true;
    }

    @Override
    public boolean isConverting() {
        return this.fuel > 0;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.GENERATOR_GENERATOR_LOOP;
    }

    @Override
    public double getGuiValue(String string) {
        if ("fuel".equals(string)) {
            return this.getFuelRatio();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.totalFuel = compoundTag.m_128451_("totalFuel");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("totalFuel", this.totalFuel);
    }
}

