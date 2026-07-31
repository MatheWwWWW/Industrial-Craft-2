/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Biomes
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.Biome
 *  net.minecraftforge.common.BiomeDictionary
 *  net.minecraftforge.common.BiomeDictionary$Type
 */
package ic2.core.util;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public final class BiomeUtil {
    public static Biome getOriginalBiome(World world, BlockPos pos) {
        return world.func_72959_q().func_180300_a(pos, Biomes.field_76772_c);
    }

    public static Biome getBiome(World world, BlockPos pos) {
        return world.func_180494_b(pos);
    }

    public static void setBiome(World world, BlockPos pos, Biome biome) {
        byte[] biomeArray = world.func_175726_f(pos).func_76605_m();
        int index = (pos.func_177952_p() & 0xF) << 4 | pos.func_177958_n() & 0xF;
        biomeArray[index] = (byte)Biome.func_185362_a((Biome)biome);
    }

    public static int getBiomeTemperature(World world, BlockPos pos) {
        Biome biome = BiomeUtil.getBiome(world, pos);
        if (BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.HOT)) {
            return 45;
        }
        if (BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.COLD)) {
            return 0;
        }
        return 25;
    }
}

