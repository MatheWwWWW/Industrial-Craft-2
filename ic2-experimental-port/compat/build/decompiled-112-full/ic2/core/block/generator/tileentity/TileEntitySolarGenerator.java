/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.Biome
 *  net.minecraftforge.common.BiomeDictionary
 *  net.minecraftforge.common.BiomeDictionary$Type
 */
package ic2.core.block.generator.tileentity;

import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.util.BiomeUtil;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class TileEntitySolarGenerator
extends TileEntityBaseGenerator {
    @GuiSynced
    public float skyLight;
    private int ticker = IC2.random.nextInt(128);
    private static final int tickRate = 128;
    private static final double energyMultiplier = ConfigUtil.getDouble(MainConfig.get(), "balance/energy/generator/solar");

    public TileEntitySolarGenerator() {
        super(1.0, 1, 2);
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
        this.skyLight = TileEntitySolarGenerator.getSkyLight(this.func_145831_w(), this.field_174879_c.func_177984_a());
    }

    public static float getSkyLight(World world, BlockPos pos) {
        if (world.field_73011_w.func_177495_o()) {
            return 0.0f;
        }
        float sunBrightness = Util.limit((float)Math.cos(world.func_72929_e(1.0f)) * 2.0f + 0.2f, 0.0f, 1.0f);
        if (!BiomeDictionary.hasType((Biome)BiomeUtil.getBiome(world, pos), (BiomeDictionary.Type)BiomeDictionary.Type.SANDY)) {
            sunBrightness *= 1.0f - world.func_72867_j(1.0f) * 5.0f / 16.0f;
            sunBrightness *= 1.0f - world.func_72819_i(1.0f) * 5.0f / 16.0f;
            sunBrightness = Util.limit(sunBrightness, 0.0f, 1.0f);
        }
        return (float)world.func_175642_b(EnumSkyBlock.SKY, pos) / 15.0f * sunBrightness;
    }

    @Override
    public boolean needsFuel() {
        return false;
    }

    @Override
    public boolean getGuiState(String name) {
        if ("sunlight".equals(name)) {
            return this.skyLight > 0.0f;
        }
        return super.getGuiState(name);
    }

    @Override
    protected boolean delayActiveUpdate() {
        return true;
    }
}

