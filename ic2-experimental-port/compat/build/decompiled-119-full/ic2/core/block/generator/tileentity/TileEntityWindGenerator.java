/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.core.IC2;
import ic2.core.WindSim;
import ic2.core.block.generator.tileentity.TileEntityBaseRotorGenerator;
import ic2.core.event.WorldData;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityWindGenerator
extends TileEntityBaseRotorGenerator
implements IGuiValueProvider.IActiveGuiValueProvider {
    private static final double energyMultiplier = ConfigUtil.getDouble(MainConfig.get(), "balance/energy/generator/wind");
    private static final double windToEnergy = 0.1 * energyMultiplier;
    private static final double safeWindRatio = 0.5;
    private static final int tickRate = 128;
    private int ticker = IC2.random.m_188503_(128);
    private int obstructedBlockCount;
    @GuiSynced
    private double overheatRatio;

    public TileEntityWindGenerator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.WIND_GENERATOR, blockPos, blockState, 4.0, 1, 32, 2);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateObscuratedBlockCount();
    }

    @Override
    public boolean gainEnergy() {
        if (++this.ticker % 128 == 0) {
            if (this.ticker % 1024 == 0) {
                this.updateObscuratedBlockCount();
            }
            this.production = 0.0;
            this.overheatRatio = 0.0;
            if (windToEnergy <= 0.0) {
                return false;
            }
            Level level = this.m_58904_();
            WindSim windSim = WorldData.get((Level)level).windSim;
            double d = windSim.getWindAt(this.f_58858_.m_123342_()) * (1.0 - (double)this.obstructedBlockCount / 567.0);
            if (d <= 0.0) {
                return false;
            }
            double d2 = d / windSim.getMaxWind();
            this.overheatRatio = Math.max(0.0, (d2 - 0.5) / 0.5);
            if (d > windSim.getMaxWind() * 0.5 && (double)level.f_46441_.m_188503_(5000) <= this.production - 5.0) {
                if (Util.harvestBlock(level, this.f_58858_)) {
                    for (int i = level.f_46441_.m_188503_(5); i > 0; --i) {
                        StackUtil.dropAsEntity(level, this.f_58858_, new ItemStack((ItemLike)Items.f_42416_));
                    }
                }
                return false;
            }
            this.production = d * windToEnergy;
        }
        return super.gainEnergy();
    }

    @Override
    public boolean gainFuel() {
        return false;
    }

    public void updateObscuratedBlockCount() {
        Level level = this.m_58904_();
        int n = -1;
        for (int i = -4; i < 5; ++i) {
            for (int j = -2; j < 5; ++j) {
                for (int k = -4; k < 5; ++k) {
                    if (level.m_46859_(this.f_58858_.m_7918_(i, j, k))) continue;
                    ++n;
                }
            }
        }
        this.obstructedBlockCount = n;
    }

    public int getObstructions() {
        return this.obstructedBlockCount;
    }

    @Override
    public boolean needsFuel() {
        return false;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.GENERATOR_WIND_LOOP;
    }

    @Override
    protected boolean delayActiveUpdate() {
        return true;
    }

    @Override
    protected boolean shouldRotorRotate() {
        return this.production > 0.0;
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("production");
        return list;
    }

    @Override
    public boolean isGuiValueActive(String string) {
        if ("wind".equals(string)) {
            return this.production > 0.0;
        }
        throw new IllegalArgumentException("Unexpected value requested: " + string);
    }

    @Override
    public double getGuiValue(String string) {
        if ("wind".equals(string)) {
            return Math.max(this.overheatRatio, 0.0);
        }
        throw new IllegalArgumentException("Unexpected value requested: " + string);
    }
}

