/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.proxy.EnvProxy;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.BiomeUtil;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntitySolarGenerator
extends TileEntityBaseGenerator {
    @GuiSynced
    public float skyLight;
    private int ticker = IC2.random.m_188503_(128);
    private static final int tickRate = 128;
    private static final double energyMultiplier = ConfigUtil.getDouble(MainConfig.get(), "balance/energy/generator/solar");

    public TileEntitySolarGenerator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.SOLAR_GENERATOR, blockPos, blockState, 1.0, 1, 2);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateSunVisibility();
    }

    @Override
    public boolean gainEnergy() {
        if (++this.ticker % 128 == 0) {
            this.updateSunVisibility();
        }
        if (this.skyLight > 0.0f) {
            this.energy.addEnergy(energyMultiplier * (double)this.skyLight);
            return true;
        }
        return false;
    }

    @Override
    public boolean gainFuel() {
        return false;
    }

    public void updateSunVisibility() {
        this.skyLight = TileEntitySolarGenerator.getSkyLight(this.m_58904_(), this.f_58858_.m_7494_());
    }

    public static float getSkyLight(Level level, BlockPos blockPos) {
        if (!level.m_6042_().f_223549_()) {
            return 0.0f;
        }
        float f = Util.limit((float)Math.cos(level.m_46490_(1.0f)) * 2.0f + 0.2f, 0.0f, 1.0f);
        if (!IC2.envProxy.biomeHasType(BiomeUtil.getBiome((LevelReader)level, blockPos), EnvProxy.BiomeType.SANDY)) {
            f *= 1.0f - level.m_46722_(1.0f) * 5.0f / 16.0f;
            f *= 1.0f - level.m_46661_(1.0f) * 5.0f / 16.0f;
            f = Util.limit(f, 0.0f, 1.0f);
        }
        return (float)level.m_45517_(LightLayer.SKY, blockPos) / 15.0f * f;
    }

    @Override
    public boolean needsFuel() {
        return false;
    }

    @Override
    public boolean getGuiState(String string) {
        if ("sunlight".equals(string)) {
            return this.skyLight > 0.0f;
        }
        return super.getGuiState(string);
    }

    @Override
    protected boolean delayActiveUpdate() {
        return true;
    }
}

