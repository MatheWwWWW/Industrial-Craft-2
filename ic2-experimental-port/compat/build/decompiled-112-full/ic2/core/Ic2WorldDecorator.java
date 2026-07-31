/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.set.TIntSet
 *  gnu.trove.set.hash.TIntHashSet
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.gen.IChunkGenerator
 *  net.minecraft.world.gen.feature.WorldGenMinable
 *  net.minecraftforge.common.BiomeDictionary
 *  net.minecraftforge.common.BiomeDictionary$Type
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.world.ChunkDataEvent$Load
 *  net.minecraftforge.event.world.ChunkDataEvent$Save
 *  net.minecraftforge.event.world.ChunkEvent$Unload
 *  net.minecraftforge.fml.common.IWorldGenerator
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ic2.core;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import ic2.core.IC2;
import ic2.core.WorldData;
import ic2.core.block.WorldGenRubTree;
import ic2.core.block.type.ResourceBlock;
import ic2.core.init.MainConfig;
import ic2.core.ref.BlockName;
import ic2.core.util.BiomeUtil;
import ic2.core.util.Config;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Ic2BlockPos;
import ic2.core.util.Util;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Ic2WorldDecorator
implements IWorldGenerator {
    private static final boolean DEBUG = false;
    private static final String chunkDataTag = "ic2WorldGen";
    private static final String keyRubberTree = "rubberTree";
    private static final String keyCopperOre = "copperOre";
    private static final String keyLeadOre = "leadOre";
    private static final String keyTinOre = "tinOre";
    private static final String keyUraniumOre = "uraniumOre";
    public static final int chunkSize = 16;
    public static final int chunkOffset = 8;
    private static final int referenceHeight = 64;
    private static final TIntSet rubberTreeBlacklist = new TIntHashSet(ConfigUtil.asIntArray(MainConfig.get(), "worldgen/rubberTreeBlacklist"));

    public Ic2WorldDecorator() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event) {
        assert (!event.getWorld().field_72995_K);
        Chunk chunk = event.getChunk();
        WorldData worldData = WorldData.get(event.getWorld());
        if (!worldData.pendingUnloadChunks.remove(chunk)) {
            NBTTagCompound nbt = event.getData().func_74775_l(chunkDataTag);
            worldData.worldGenData.put(chunk, nbt);
            Ic2WorldDecorator.checkRetroGen(chunk, nbt);
        }
    }

    private static void checkRetroGen(Chunk chunk, NBTTagCompound nbt) {
        if (!chunk.func_177419_t()) {
            return;
        }
        Config config = MainConfig.get().getSub("worldgen");
        if (Ic2WorldDecorator.getCheckLimit(config) <= 0 || Ic2WorldDecorator.getUpdateLimit(config) <= 0) {
            return;
        }
        float epsilon = 1.0E-5f;
        float treeScale = Ic2WorldDecorator.getTreeScale(config) - epsilon;
        float oreScale = Ic2WorldDecorator.getOreScale(config, Ic2WorldDecorator.getBaseHeight(config, chunk.func_177412_p())) - epsilon;
        if (treeScale <= 0.0f && oreScale <= 0.0f) {
            return;
        }
        if (Ic2WorldDecorator.rubberTreeGenEnabled(config, chunk.func_177412_p()) && nbt.func_74760_g(keyRubberTree) < treeScale || ConfigUtil.getBool(config, "copper/enabled") && nbt.func_74760_g(keyCopperOre) < oreScale || ConfigUtil.getBool(config, "lead/enabled") && nbt.func_74760_g(keyLeadOre) < oreScale || ConfigUtil.getBool(config, "tin/enabled") && nbt.func_74760_g(keyTinOre) < oreScale || ConfigUtil.getBool(config, "uranium/enabled") && nbt.func_74760_g(keyUraniumOre) < oreScale) {
            WorldData.get((World)chunk.func_177412_p()).chunksToDecorate.add(chunk);
        }
    }

    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event) {
        assert (!event.getWorld().field_72995_K);
        Chunk chunk = event.getChunk();
        NBTTagCompound nbt = WorldData.get((World)event.getWorld()).worldGenData.get(chunk);
        if (nbt != null && !nbt.func_82582_d()) {
            nbt = nbt.func_74737_b();
            event.getData().func_74782_a(chunkDataTag, (NBTBase)nbt);
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onChunkUnload(ChunkEvent.Unload event) {
        if (event.getWorld().field_72995_K) {
            return;
        }
        Chunk chunk = event.getChunk();
        WorldData worldData = WorldData.get(event.getWorld(), false);
        if (worldData == null) {
            return;
        }
        worldData.pendingUnloadChunks.add(chunk);
    }

    private static void applyPendingUnloads(WorldData worldData) {
        Set<Chunk> chunks = worldData.pendingUnloadChunks;
        if (chunks.isEmpty()) {
            return;
        }
        for (Chunk chunk : chunks) {
            worldData.worldGenData.remove(chunk);
            if (!worldData.chunksToDecorate.remove(chunk)) continue;
        }
        chunks.clear();
    }

    public static void onTick(World world, WorldData worldData) {
        Ic2WorldDecorator.applyPendingUnloads(worldData);
        if (worldData.chunksToDecorate.isEmpty()) {
            return;
        }
        Config config = MainConfig.get().getSub("worldgen");
        int chunksToCheck = Ic2WorldDecorator.getCheckLimit(config);
        int chunksToDecorate = Ic2WorldDecorator.getUpdateLimit(config);
        long worldSeed = world.func_72905_C();
        Random rnd = new Random(worldSeed);
        long xSeed = rnd.nextLong() >> 3;
        long zSeed = rnd.nextLong() >> 3;
        int baseHeight = Ic2WorldDecorator.getBaseHeight(config, world);
        int worldHeight = world.func_72800_K();
        float treeScale = Ic2WorldDecorator.getTreeScale(config);
        float oreScale = Ic2WorldDecorator.getOreScale(config, baseHeight);
        int skip = worldData.chunksToDecorate.size() - chunksToCheck;
        if (skip > 0) {
            skip = IC2.random.nextInt(skip + 1);
        }
        Iterator<Chunk> it = worldData.chunksToDecorate.iterator();
        while (skip > 0) {
            --skip;
            it.next();
        }
        while (it.hasNext()) {
            Chunk chunk = it.next();
            if (Ic2WorldDecorator.hasNeighborChunks(chunk)) {
                float f;
                float f2;
                float f3;
                float f4;
                float extra;
                NBTTagCompound nbt = worldData.worldGenData.get(chunk);
                if (nbt == null) {
                    nbt = new NBTTagCompound();
                }
                long chunkSeed = xSeed * (long)chunk.field_76635_g + zSeed * (long)chunk.field_76647_h ^ worldSeed;
                rnd.setSeed(chunkSeed);
                long rubberTreeSeed = rnd.nextLong();
                long copperOreSeed = rnd.nextLong();
                long tinOreSeed = rnd.nextLong();
                long uraniumOreSeed = rnd.nextLong();
                long leadOreSeed = rnd.nextLong();
                if (Ic2WorldDecorator.rubberTreeGenEnabled(config, world)) {
                    float f5;
                    extra = treeScale - nbt.func_74760_g(keyRubberTree);
                    if (f5 > 0.0f) {
                        Ic2WorldDecorator.genRubberTree(rnd, rubberTreeSeed, chunk, extra);
                    }
                }
                extra = oreScale - nbt.func_74760_g(keyCopperOre);
                if (f4 > 0.0f) {
                    Ic2WorldDecorator.genOre(rnd, copperOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.copper_ore), keyCopperOre, config.getSub("copper"), baseHeight, worldHeight, extra);
                }
                extra = oreScale - nbt.func_74760_g(keyLeadOre);
                if (f3 > 0.0f) {
                    Ic2WorldDecorator.genOre(rnd, leadOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.lead_ore), keyLeadOre, config.getSub("lead"), baseHeight, worldHeight, extra);
                }
                extra = oreScale - nbt.func_74760_g(keyTinOre);
                if (f2 > 0.0f) {
                    Ic2WorldDecorator.genOre(rnd, tinOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.tin_ore), keyTinOre, config.getSub("tin"), baseHeight, worldHeight, extra);
                }
                extra = oreScale - nbt.func_74760_g(keyUraniumOre);
                if (f > 0.0f) {
                    Ic2WorldDecorator.genOre(rnd, uraniumOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.uranium_ore), keyUraniumOre, config.getSub("uranium"), baseHeight, worldHeight, extra);
                }
                it.remove();
                if (--chunksToDecorate == 0) break;
            }
            if (--chunksToCheck != 0) continue;
            break;
        }
    }

    private static boolean hasNeighborChunks(Chunk chunk) {
        World world = chunk.func_177412_p();
        Ic2BlockPos pos = new Ic2BlockPos();
        for (int dx = 0; dx <= 1; ++dx) {
            for (int dz = 0; dz <= 1; ++dz) {
                if (dx == 0 && dz == 0) continue;
                pos.set((chunk.field_76635_g + dx) * 16, 0, (chunk.field_76647_h + dz) * 16);
                if (world.func_175668_a((BlockPos)pos, false)) continue;
                return false;
            }
        }
        return true;
    }

    public void generate(Random rnd, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        Chunk chunk = chunkProvider.func_186025_d(chunkX, chunkZ);
        assert (Ic2WorldDecorator.hasNeighborChunks(chunk));
        long rubberTreeSeed = rnd.nextLong();
        long copperOreSeed = rnd.nextLong();
        long tinOreSeed = rnd.nextLong();
        long uraniumOreSeed = rnd.nextLong();
        long leadOreSeed = rnd.nextLong();
        Config config = MainConfig.get().getSub("worldgen");
        int baseHeight = Ic2WorldDecorator.getBaseHeight(config, world);
        float treeScale = Ic2WorldDecorator.getTreeScale(config);
        float oreScale = Ic2WorldDecorator.getOreScale(config, baseHeight);
        if (Ic2WorldDecorator.rubberTreeGenEnabled(config, world) && treeScale > 0.0f) {
            Ic2WorldDecorator.genRubberTree(rnd, rubberTreeSeed, chunk, treeScale);
        }
        if (oreScale > 0.0f) {
            int worldHeight = world.func_72800_K();
            Ic2WorldDecorator.genOre(rnd, copperOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.copper_ore), keyCopperOre, config.getSub("copper"), baseHeight, worldHeight, oreScale);
            Ic2WorldDecorator.genOre(rnd, leadOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.lead_ore), keyLeadOre, config.getSub("lead"), baseHeight, worldHeight, oreScale);
            Ic2WorldDecorator.genOre(rnd, tinOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.tin_ore), keyTinOre, config.getSub("tin"), baseHeight, worldHeight, oreScale);
            Ic2WorldDecorator.genOre(rnd, uraniumOreSeed, chunk, BlockName.resource.getBlockState(ResourceBlock.uranium_ore), keyUraniumOre, config.getSub("uranium"), baseHeight, worldHeight, oreScale);
        }
    }

    private static void genRubberTree(Random rnd, long seed, Chunk chunk, float baseScale) {
        rnd.setSeed(seed);
        Biome[] biomes = new Biome[4];
        for (int i = 0; i < 4; ++i) {
            int x = chunk.field_76635_g * 16 + 8 + (i & 1) * 15;
            int z = chunk.field_76647_h * 16 + 8 + ((i & 2) >>> 1) * 15;
            BlockPos pos = new BlockPos(x, chunk.func_177412_p().func_181545_F(), z);
            biomes[i] = BiomeUtil.getOriginalBiome(chunk.func_177412_p(), pos);
        }
        int rubberTrees = 0;
        for (Biome biome : biomes) {
            if (biome == null) continue;
            if (BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.SWAMP)) {
                rubberTrees += rnd.nextInt(10) + 5;
            }
            if (!BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.FOREST) && !BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.JUNGLE)) continue;
            rubberTrees += rnd.nextInt(5) + 1;
        }
        rubberTrees = Math.round((float)rubberTrees * baseScale);
        if ((rubberTrees /= 2) > 0 && rnd.nextInt(100) < rubberTrees) {
            WorldGenRubTree gen = new WorldGenRubTree(false);
            for (int i = 0; i < rubberTrees; ++i) {
                if (gen.func_180709_b(chunk.func_177412_p(), rnd, new BlockPos(Ic2WorldDecorator.randomX(chunk, rnd), chunk.func_177412_p().func_181545_F(), Ic2WorldDecorator.randomZ(chunk, rnd)))) continue;
                rubberTrees -= 3;
            }
        }
        Ic2WorldDecorator.updateScale(chunk, keyRubberTree, baseScale);
    }

    private static void genOre(Random rnd, long seed, Chunk chunk, IBlockState ore, String oreScaleKey, Config config, int baseHeight, int worldHeight, float baseScale) {
        if (!ConfigUtil.getBool(config, "enabled")) {
            return;
        }
        rnd.setSeed(seed);
        int count = ConfigUtil.getInt(config, "count");
        int size = ConfigUtil.getInt(config, "size");
        int minHeight = ConfigUtil.getInt(config, "minHeight");
        int maxHeight = ConfigUtil.getInt(config, "maxHeight");
        OreDistribution distribution = OreDistribution.of(ConfigUtil.getString(config, "distribution"));
        float baseCount = (float)count * baseScale / 64.0f;
        count = (int)Math.round(rnd.nextGaussian() * Math.sqrt(baseCount) + (double)baseCount);
        minHeight = Util.limit(minHeight * baseHeight / 64, 0, worldHeight - 1);
        maxHeight = Util.limit(maxHeight * baseHeight / 64, minHeight + 1, worldHeight - 1);
        int heightSpan = maxHeight - minHeight;
        if (heightSpan == 0) {
            return;
        }
        WorldGenMinable gen = new WorldGenMinable(ore, size);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int n = 0; n < count; ++n) {
            int x = Ic2WorldDecorator.randomX(chunk, rnd);
            int z = Ic2WorldDecorator.randomZ(chunk, rnd);
            int y = minHeight;
            switch (distribution) {
                case UNIFORM: {
                    y += rnd.nextInt(heightSpan);
                    break;
                }
                case TRIANGLE: {
                    int halfHeightSpan = heightSpan >>> 1;
                    y += rnd.nextInt(halfHeightSpan + 1) + rnd.nextInt(heightSpan - halfHeightSpan);
                    break;
                }
                case RAMP: {
                    y += heightSpan - 1 - (int)Math.sqrt(rnd.nextInt(heightSpan * heightSpan));
                    break;
                }
                case REVRAMP: {
                    y += (int)Math.sqrt(rnd.nextInt(heightSpan * heightSpan));
                    break;
                }
                case SMOOTH: {
                    int maxA = (heightSpan * 4 + 6) / 7;
                    y += rnd.nextInt(maxA);
                    int maxB = ((heightSpan - maxA + 1) * 2 + 2) / 3;
                    y += rnd.nextInt(maxB);
                    int maxC = heightSpan - maxA - maxB + 2;
                    y += rnd.nextInt(maxC);
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            pos.func_181079_c(x, y, z);
            gen.func_180709_b(chunk.func_177412_p(), rnd, (BlockPos)pos);
        }
        Ic2WorldDecorator.updateScale(chunk, oreScaleKey, baseScale);
    }

    private static int getBaseHeight(Config config, World world) {
        if (ConfigUtil.getBool(config, "normalizeHeight")) {
            return world.func_181545_F() + 1;
        }
        return 64;
    }

    private static boolean rubberTreeGenEnabled(Config config, World world) {
        return ConfigUtil.getBool(config, keyRubberTree) && !rubberTreeBlacklist.contains(world.field_73011_w.getDimension());
    }

    private static float getTreeScale(Config config) {
        return ConfigUtil.getFloat(config, "treeDensityFactor");
    }

    private static float getOreScale(Config config, int baseHeight) {
        return ConfigUtil.getFloat(config, "oreDensityFactor") * (float)baseHeight;
    }

    private static int getCheckLimit(Config config) {
        return ConfigUtil.getInt(config, "retrogenCheckLimit");
    }

    private static int getUpdateLimit(Config config) {
        return ConfigUtil.getInt(config, "retrogenUpdateLimit");
    }

    private static void updateScale(Chunk chunk, String key, float scale) {
        WorldData worldData = WorldData.get(chunk.func_177412_p());
        NBTTagCompound nbt = worldData.worldGenData.get(chunk);
        if (nbt == null) {
            nbt = new NBTTagCompound();
            worldData.worldGenData.put(chunk, nbt);
        }
        nbt.func_74776_a(key, nbt.func_74760_g(key) + scale);
        chunk.func_177427_f(true);
    }

    private static int zeroRnd(Random rnd, int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("The limit must not be negative: " + limit);
        }
        if (limit == 0) {
            return 0;
        }
        return rnd.nextInt(limit);
    }

    private static int randomX(Chunk chunk, Random rnd) {
        return chunk.field_76635_g * 16 + rnd.nextInt(16);
    }

    private static int randomZ(Chunk chunk, Random rnd) {
        return chunk.field_76647_h * 16 + rnd.nextInt(16);
    }

    private static enum OreDistribution {
        UNIFORM("uniform"),
        TRIANGLE("triangle"),
        RAMP("ramp"),
        REVRAMP("revramp"),
        SMOOTH("smooth");

        private static final OreDistribution[] values;
        final String name;

        private OreDistribution(String name) {
            this.name = name;
        }

        public static OreDistribution of(String name) {
            for (OreDistribution value : values) {
                if (!value.name.equalsIgnoreCase(name)) continue;
                return value;
            }
            throw new RuntimeException("Invalid/unknown worldgen distribution configured: " + name);
        }

        static {
            values = OreDistribution.values();
        }
    }
}

