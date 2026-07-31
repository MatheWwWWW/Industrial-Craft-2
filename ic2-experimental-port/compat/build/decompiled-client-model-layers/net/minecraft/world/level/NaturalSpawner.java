/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.PotentialCalculator;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public final class NaturalSpawner {
    private static final Logger f_46977_ = LogUtils.getLogger();
    private static final int f_151589_ = 24;
    public static final int f_151587_ = 8;
    public static final int f_151588_ = 128;
    static final int f_46978_ = (int)Math.pow(17.0, 2.0);
    private static final MobCategory[] f_46979_ = (MobCategory[])Stream.of(MobCategory.values()).filter(p_47037_ -> p_47037_ != MobCategory.MISC).toArray(MobCategory[]::new);

    private NaturalSpawner() {
    }

    public static SpawnState m_186524_(int p_186525_, Iterable<Entity> p_186526_, ChunkGetter p_186527_, LocalMobCapCalculator p_186528_) {
        PotentialCalculator $$4 = new PotentialCalculator();
        Object2IntOpenHashMap $$5 = new Object2IntOpenHashMap();
        for (Entity $$6 : p_186526_) {
            MobCategory $$8;
            Mob $$7;
            if ($$6 instanceof Mob && (($$7 = (Mob)$$6).m_21532_() || $$7.m_8023_()) || ($$8 = $$6.m_6095_().m_20674_()) == MobCategory.MISC) continue;
            BlockPos $$9 = $$6.m_20183_();
            p_186527_.m_47103_(ChunkPos.m_151388_($$9), p_186541_ -> {
                MobSpawnSettings.MobSpawnCost $$7 = NaturalSpawner.m_47095_($$9, p_186541_).m_47518_().m_48345_($$6.m_6095_());
                if ($$7 != null) {
                    $$4.m_47192_($$6.m_20183_(), $$7.m_48400_());
                }
                if ($$6 instanceof Mob) {
                    p_186528_.m_186512_(p_186541_.m_7697_(), $$8);
                }
                $$5.addTo((Object)$$8, 1);
            });
        }
        return new SpawnState(p_186525_, (Object2IntOpenHashMap<MobCategory>)$$5, $$4, p_186528_);
    }

    static Biome m_47095_(BlockPos p_47096_, ChunkAccess p_47097_) {
        return p_47097_.m_203495_(QuartPos.m_175400_(p_47096_.m_123341_()), QuartPos.m_175400_(p_47096_.m_123342_()), QuartPos.m_175400_(p_47096_.m_123343_())).m_203334_();
    }

    public static void m_47029_(ServerLevel p_47030_, LevelChunk p_47031_, SpawnState p_47032_, boolean p_47033_, boolean p_47034_, boolean p_47035_) {
        p_47030_.m_46473_().m_6180_("spawner");
        for (MobCategory $$6 : f_46979_) {
            if (!p_47033_ && $$6.m_21609_() || !p_47034_ && !$$6.m_21609_() || !p_47035_ && $$6.m_21610_() || !p_47032_.m_186548_($$6, p_47031_.m_7697_())) continue;
            NaturalSpawner.m_47045_($$6, p_47030_, p_47031_, p_47032_::m_47127_, p_47032_::m_47131_);
        }
        p_47030_.m_46473_().m_7238_();
    }

    public static void m_47045_(MobCategory p_47046_, ServerLevel p_47047_, LevelChunk p_47048_, SpawnPredicate p_47049_, AfterSpawnCallback p_47050_) {
        BlockPos $$5 = NaturalSpawner.m_47062_(p_47047_, p_47048_);
        if ($$5.m_123342_() < p_47047_.m_141937_() + 1) {
            return;
        }
        NaturalSpawner.m_47038_(p_47046_, p_47047_, p_47048_, $$5, p_47049_, p_47050_);
    }

    @VisibleForDebug
    public static void m_151612_(MobCategory p_151613_, ServerLevel p_151614_, BlockPos p_151615_) {
        NaturalSpawner.m_47038_(p_151613_, p_151614_, p_151614_.m_46865_(p_151615_), p_151615_, (p_151606_, p_151607_, p_151608_) -> true, (p_151610_, p_151611_) -> {});
    }

    public static void m_47038_(MobCategory p_47039_, ServerLevel p_47040_, ChunkAccess p_47041_, BlockPos p_47042_, SpawnPredicate p_47043_, AfterSpawnCallback p_47044_) {
        StructureManager $$6 = p_47040_.m_215010_();
        ChunkGenerator $$7 = p_47040_.m_7726_().m_8481_();
        int $$8 = p_47042_.m_123342_();
        BlockState $$9 = p_47041_.m_8055_(p_47042_);
        if ($$9.m_60796_(p_47041_, p_47042_)) {
            return;
        }
        BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
        int $$11 = 0;
        block0: for (int $$12 = 0; $$12 < 3; ++$$12) {
            int $$13 = p_47042_.m_123341_();
            int $$14 = p_47042_.m_123343_();
            int $$15 = 6;
            MobSpawnSettings.SpawnerData $$16 = null;
            SpawnGroupData $$17 = null;
            int $$18 = Mth.m_14167_(p_47040_.f_46441_.m_188501_() * 4.0f);
            int $$19 = 0;
            for (int $$20 = 0; $$20 < $$18; ++$$20) {
                double $$24;
                $$10.m_122178_($$13 += p_47040_.f_46441_.m_188503_(6) - p_47040_.f_46441_.m_188503_(6), $$8, $$14 += p_47040_.f_46441_.m_188503_(6) - p_47040_.f_46441_.m_188503_(6));
                double $$21 = (double)$$13 + 0.5;
                double $$22 = (double)$$14 + 0.5;
                Player $$23 = p_47040_.m_45924_($$21, $$8, $$22, -1.0, false);
                if ($$23 == null || !NaturalSpawner.m_47024_(p_47040_, p_47041_, $$10, $$24 = $$23.m_20275_($$21, $$8, $$22))) continue;
                if ($$16 == null) {
                    Optional<MobSpawnSettings.SpawnerData> $$25 = NaturalSpawner.m_220429_(p_47040_, $$6, $$7, p_47039_, p_47040_.f_46441_, $$10);
                    if ($$25.isEmpty()) continue block0;
                    $$16 = $$25.get();
                    $$18 = $$16.f_48405_ + p_47040_.f_46441_.m_188503_(1 + $$16.f_48406_ - $$16.f_48405_);
                }
                if (!NaturalSpawner.m_220421_(p_47040_, p_47039_, $$6, $$7, $$16, $$10, $$24) || !p_47043_.m_47106_($$16.f_48404_, $$10, p_47041_)) continue;
                Mob $$26 = NaturalSpawner.m_46988_(p_47040_, $$16.f_48404_);
                if ($$26 == null) {
                    return;
                }
                $$26.m_7678_($$21, $$8, $$22, p_47040_.f_46441_.m_188501_() * 360.0f, 0.0f);
                if (!NaturalSpawner.m_46991_(p_47040_, $$26, $$24)) continue;
                $$17 = $$26.m_6518_(p_47040_, p_47040_.m_6436_($$26.m_20183_()), MobSpawnType.NATURAL, $$17, null);
                ++$$19;
                p_47040_.m_47205_($$26);
                p_47044_.m_47100_($$26, p_47041_);
                if (++$$11 >= $$26.m_5792_()) {
                    return;
                }
                if ($$26.m_7296_($$19)) continue block0;
            }
        }
    }

    private static boolean m_47024_(ServerLevel p_47025_, ChunkAccess p_47026_, BlockPos.MutableBlockPos p_47027_, double p_47028_) {
        if (p_47028_ <= 576.0) {
            return false;
        }
        if (p_47025_.m_220360_().m_203195_(new Vec3((double)p_47027_.m_123341_() + 0.5, p_47027_.m_123342_(), (double)p_47027_.m_123343_() + 0.5), 24.0)) {
            return false;
        }
        return Objects.equals(new ChunkPos(p_47027_), p_47026_.m_7697_()) || p_47025_.m_201918_(p_47027_);
    }

    private static boolean m_220421_(ServerLevel p_220422_, MobCategory p_220423_, StructureManager p_220424_, ChunkGenerator p_220425_, MobSpawnSettings.SpawnerData p_220426_, BlockPos.MutableBlockPos p_220427_, double p_220428_) {
        EntityType<?> $$7 = p_220426_.f_48404_;
        if ($$7.m_20674_() == MobCategory.MISC) {
            return false;
        }
        if (!$$7.m_20673_() && p_220428_ > (double)($$7.m_20674_().m_21611_() * $$7.m_20674_().m_21611_())) {
            return false;
        }
        if (!$$7.m_20654_() || !NaturalSpawner.m_220436_(p_220422_, p_220424_, p_220425_, p_220423_, p_220426_, p_220427_)) {
            return false;
        }
        SpawnPlacements.Type $$8 = SpawnPlacements.m_21752_($$7);
        if (!NaturalSpawner.m_47051_($$8, p_220422_, p_220427_, $$7)) {
            return false;
        }
        if (!SpawnPlacements.m_217074_($$7, p_220422_, MobSpawnType.NATURAL, p_220427_, p_220422_.f_46441_)) {
            return false;
        }
        return p_220422_.m_45772_($$7.m_20585_((double)p_220427_.m_123341_() + 0.5, p_220427_.m_123342_(), (double)p_220427_.m_123343_() + 0.5));
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private static Mob m_46988_(ServerLevel p_46989_, EntityType<?> p_46990_) {
        void $$5;
        try {
            Object $$2 = p_46990_.m_20615_(p_46989_);
            if (!($$2 instanceof Mob)) {
                throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.f_122826_.m_7981_(p_46990_));
            }
            Mob $$3 = (Mob)$$2;
        }
        catch (Exception $$4) {
            f_46977_.warn("Failed to create mob", (Throwable)$$4);
            return null;
        }
        return $$5;
    }

    private static boolean m_46991_(ServerLevel p_46992_, Mob p_46993_, double p_46994_) {
        if (p_46994_ > (double)(p_46993_.m_6095_().m_20674_().m_21611_() * p_46993_.m_6095_().m_20674_().m_21611_()) && p_46993_.m_6785_(p_46994_)) {
            return false;
        }
        return p_46993_.m_5545_(p_46992_, MobSpawnType.NATURAL) && p_46993_.m_6914_(p_46992_);
    }

    private static Optional<MobSpawnSettings.SpawnerData> m_220429_(ServerLevel p_220430_, StructureManager p_220431_, ChunkGenerator p_220432_, MobCategory p_220433_, RandomSource p_220434_, BlockPos p_220435_) {
        Holder<Biome> $$6 = p_220430_.m_204166_(p_220435_);
        if (p_220433_ == MobCategory.WATER_AMBIENT && $$6.m_203656_(BiomeTags.f_215811_) && p_220434_.m_188501_() < 0.98f) {
            return Optional.empty();
        }
        return NaturalSpawner.m_220443_(p_220430_, p_220431_, p_220432_, p_220433_, p_220435_, $$6).m_216829_(p_220434_);
    }

    private static boolean m_220436_(ServerLevel p_220437_, StructureManager p_220438_, ChunkGenerator p_220439_, MobCategory p_220440_, MobSpawnSettings.SpawnerData p_220441_, BlockPos p_220442_) {
        return NaturalSpawner.m_220443_(p_220437_, p_220438_, p_220439_, p_220440_, p_220442_, null).m_146338_().contains(p_220441_);
    }

    private static WeightedRandomList<MobSpawnSettings.SpawnerData> m_220443_(ServerLevel p_220444_, StructureManager p_220445_, ChunkGenerator p_220446_, MobCategory p_220447_, BlockPos p_220448_, @Nullable Holder<Biome> p_220449_) {
        if (NaturalSpawner.m_220455_(p_220448_, p_220444_, p_220447_, p_220445_)) {
            return NetherFortressStructure.f_228517_;
        }
        return p_220446_.m_223133_(p_220449_ != null ? p_220449_ : p_220444_.m_204166_(p_220448_), p_220445_, p_220447_, p_220448_);
    }

    public static boolean m_220455_(BlockPos p_220456_, ServerLevel p_220457_, MobCategory p_220458_, StructureManager p_220459_) {
        if (p_220458_ != MobCategory.MONSTER || !p_220457_.m_8055_(p_220456_.m_7495_()).m_60713_(Blocks.f_50197_)) {
            return false;
        }
        Structure $$4 = p_220459_.m_220521_().m_175515_(Registry.f_235725_).m_6246_(BuiltinStructures.f_209859_);
        if ($$4 == null) {
            return false;
        }
        return p_220459_.m_220494_(p_220456_, $$4).m_73603_();
    }

    private static BlockPos m_47062_(Level p_47063_, LevelChunk p_47064_) {
        ChunkPos $$2 = p_47064_.m_7697_();
        int $$3 = $$2.m_45604_() + p_47063_.f_46441_.m_188503_(16);
        int $$4 = $$2.m_45605_() + p_47063_.f_46441_.m_188503_(16);
        int $$5 = p_47064_.m_5885_(Heightmap.Types.WORLD_SURFACE, $$3, $$4) + 1;
        int $$6 = Mth.m_216287_(p_47063_.f_46441_, p_47063_.m_141937_(), $$5);
        return new BlockPos($$3, $$6, $$4);
    }

    public static boolean m_47056_(BlockGetter p_47057_, BlockPos p_47058_, BlockState p_47059_, FluidState p_47060_, EntityType<?> p_47061_) {
        if (p_47059_.m_60838_(p_47057_, p_47058_)) {
            return false;
        }
        if (p_47059_.m_60803_()) {
            return false;
        }
        if (!p_47060_.m_76178_()) {
            return false;
        }
        if (p_47059_.m_204336_(BlockTags.f_13054_)) {
            return false;
        }
        return !p_47061_.m_20630_(p_47059_);
    }

    public static boolean m_47051_(SpawnPlacements.Type p_47052_, LevelReader p_47053_, BlockPos p_47054_, @Nullable EntityType<?> p_47055_) {
        if (p_47052_ == SpawnPlacements.Type.NO_RESTRICTIONS) {
            return true;
        }
        if (p_47055_ == null || !p_47053_.m_6857_().m_61937_(p_47054_)) {
            return false;
        }
        BlockState $$4 = p_47053_.m_8055_(p_47054_);
        FluidState $$5 = p_47053_.m_6425_(p_47054_);
        BlockPos $$6 = p_47054_.m_7494_();
        BlockPos $$7 = p_47054_.m_7495_();
        switch (p_47052_) {
            case IN_WATER: {
                return $$5.m_205070_(FluidTags.f_13131_) && !p_47053_.m_8055_($$6).m_60796_(p_47053_, $$6);
            }
            case IN_LAVA: {
                return $$5.m_205070_(FluidTags.f_13132_);
            }
        }
        BlockState $$8 = p_47053_.m_8055_($$7);
        if (!$$8.m_60643_(p_47053_, $$7, p_47055_)) {
            return false;
        }
        return NaturalSpawner.m_47056_(p_47053_, p_47054_, $$4, $$5, p_47055_) && NaturalSpawner.m_47056_(p_47053_, $$6, p_47053_.m_8055_($$6), p_47053_.m_6425_($$6), p_47055_);
    }

    /*
     * WARNING - void declaration
     */
    public static void m_220450_(ServerLevelAccessor p_220451_, Holder<Biome> p_220452_, ChunkPos p_220453_, RandomSource p_220454_) {
        MobSpawnSettings $$4 = p_220452_.m_203334_().m_47518_();
        WeightedRandomList<MobSpawnSettings.SpawnerData> $$5 = $$4.m_151798_(MobCategory.CREATURE);
        if ($$5.m_146337_()) {
            return;
        }
        int $$6 = p_220453_.m_45604_();
        int $$7 = p_220453_.m_45605_();
        while (p_220454_.m_188501_() < $$4.m_48344_()) {
            Optional<MobSpawnSettings.SpawnerData> $$8 = $$5.m_216829_(p_220454_);
            if (!$$8.isPresent()) continue;
            MobSpawnSettings.SpawnerData $$9 = $$8.get();
            int $$10 = $$9.f_48405_ + p_220454_.m_188503_(1 + $$9.f_48406_ - $$9.f_48405_);
            SpawnGroupData $$11 = null;
            int $$12 = $$6 + p_220454_.m_188503_(16);
            int $$13 = $$7 + p_220454_.m_188503_(16);
            int $$14 = $$12;
            int $$15 = $$13;
            for (int $$16 = 0; $$16 < $$10; ++$$16) {
                boolean $$17 = false;
                for (int $$18 = 0; !$$17 && $$18 < 4; ++$$18) {
                    BlockPos $$19 = NaturalSpawner.m_47065_(p_220451_, $$9.f_48404_, $$12, $$13);
                    if ($$9.f_48404_.m_20654_() && NaturalSpawner.m_47051_(SpawnPlacements.m_21752_($$9.f_48404_), p_220451_, $$19, $$9.f_48404_)) {
                        Mob $$26;
                        void $$25;
                        float $$20 = $$9.f_48404_.m_20678_();
                        double $$21 = Mth.m_14008_($$12, (double)$$6 + (double)$$20, (double)$$6 + 16.0 - (double)$$20);
                        double $$22 = Mth.m_14008_($$13, (double)$$7 + (double)$$20, (double)$$7 + 16.0 - (double)$$20);
                        if (!p_220451_.m_45772_($$9.f_48404_.m_20585_($$21, $$19.m_123342_(), $$22)) || !SpawnPlacements.m_217074_($$9.f_48404_, p_220451_, MobSpawnType.CHUNK_GENERATION, new BlockPos($$21, (double)$$19.m_123342_(), $$22), p_220451_.m_213780_())) continue;
                        try {
                            Object $$23 = $$9.f_48404_.m_20615_(p_220451_.m_6018_());
                        }
                        catch (Exception $$24) {
                            f_46977_.warn("Failed to create mob", (Throwable)$$24);
                            continue;
                        }
                        $$25.m_7678_($$21, $$19.m_123342_(), $$22, p_220454_.m_188501_() * 360.0f, 0.0f);
                        if ($$25 instanceof Mob && ($$26 = (Mob)$$25).m_5545_(p_220451_, MobSpawnType.CHUNK_GENERATION) && $$26.m_6914_(p_220451_)) {
                            $$11 = $$26.m_6518_(p_220451_, p_220451_.m_6436_($$26.m_20183_()), MobSpawnType.CHUNK_GENERATION, $$11, null);
                            p_220451_.m_47205_($$26);
                            $$17 = true;
                        }
                    }
                    $$12 += p_220454_.m_188503_(5) - p_220454_.m_188503_(5);
                    $$13 += p_220454_.m_188503_(5) - p_220454_.m_188503_(5);
                    while ($$12 < $$6 || $$12 >= $$6 + 16 || $$13 < $$7 || $$13 >= $$7 + 16) {
                        $$12 = $$14 + p_220454_.m_188503_(5) - p_220454_.m_188503_(5);
                        $$13 = $$15 + p_220454_.m_188503_(5) - p_220454_.m_188503_(5);
                    }
                }
            }
        }
    }

    private static BlockPos m_47065_(LevelReader p_47066_, EntityType<?> p_47067_, int p_47068_, int p_47069_) {
        Vec3i $$6;
        int $$4 = p_47066_.m_6924_(SpawnPlacements.m_21765_(p_47067_), p_47068_, p_47069_);
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos(p_47068_, $$4, p_47069_);
        if (p_47066_.m_6042_().f_63856_()) {
            do {
                $$5.m_122173_(Direction.DOWN);
            } while (!p_47066_.m_8055_($$5).m_60795_());
            do {
                $$5.m_122173_(Direction.DOWN);
            } while (p_47066_.m_8055_($$5).m_60795_() && $$5.m_123342_() > p_47066_.m_141937_());
        }
        if (SpawnPlacements.m_21752_(p_47067_) == SpawnPlacements.Type.ON_GROUND && p_47066_.m_8055_((BlockPos)($$6 = $$5.m_7495_())).m_60647_(p_47066_, (BlockPos)$$6, PathComputationType.LAND)) {
            return $$6;
        }
        return $$5.m_7949_();
    }

    @FunctionalInterface
    public static interface ChunkGetter {
        public void m_47103_(long var1, Consumer<LevelChunk> var3);
    }

    public static class SpawnState {
        private final int f_47110_;
        private final Object2IntOpenHashMap<MobCategory> f_47111_;
        private final PotentialCalculator f_47112_;
        private final Object2IntMap<MobCategory> f_47113_;
        private final LocalMobCapCalculator f_186542_;
        @Nullable
        private BlockPos f_47114_;
        @Nullable
        private EntityType<?> f_47115_;
        private double f_47116_;

        SpawnState(int p_186544_, Object2IntOpenHashMap<MobCategory> p_186545_, PotentialCalculator p_186546_, LocalMobCapCalculator p_186547_) {
            this.f_47110_ = p_186544_;
            this.f_47111_ = p_186545_;
            this.f_47112_ = p_186546_;
            this.f_186542_ = p_186547_;
            this.f_47113_ = Object2IntMaps.unmodifiable(p_186545_);
        }

        private boolean m_47127_(EntityType<?> p_47128_, BlockPos p_47129_, ChunkAccess p_47130_) {
            double $$4;
            this.f_47114_ = p_47129_;
            this.f_47115_ = p_47128_;
            MobSpawnSettings.MobSpawnCost $$3 = NaturalSpawner.m_47095_(p_47129_, p_47130_).m_47518_().m_48345_(p_47128_);
            if ($$3 == null) {
                this.f_47116_ = 0.0;
                return true;
            }
            this.f_47116_ = $$4 = $$3.m_48400_();
            double $$5 = this.f_47112_.m_47195_(p_47129_, $$4);
            return $$5 <= $$3.m_48395_();
        }

        private void m_47131_(Mob p_47132_, ChunkAccess p_47133_) {
            double $$7;
            EntityType<?> $$2 = p_47132_.m_6095_();
            BlockPos $$3 = p_47132_.m_20183_();
            if ($$3.equals(this.f_47114_) && $$2 == this.f_47115_) {
                double $$4 = this.f_47116_;
            } else {
                MobSpawnSettings.MobSpawnCost $$5 = NaturalSpawner.m_47095_($$3, p_47133_).m_47518_().m_48345_($$2);
                if ($$5 != null) {
                    double $$6 = $$5.m_48400_();
                } else {
                    $$7 = 0.0;
                }
            }
            this.f_47112_.m_47192_($$3, $$7);
            MobCategory $$8 = $$2.m_20674_();
            this.f_47111_.addTo((Object)$$8, 1);
            this.f_186542_.m_186512_(new ChunkPos($$3), $$8);
        }

        public int m_47126_() {
            return this.f_47110_;
        }

        public Object2IntMap<MobCategory> m_47148_() {
            return this.f_47113_;
        }

        boolean m_186548_(MobCategory p_186549_, ChunkPos p_186550_) {
            int $$2 = p_186549_.m_21608_() * this.f_47110_ / f_46978_;
            if (this.f_47111_.getInt((Object)p_186549_) >= $$2) {
                return false;
            }
            return this.f_186542_.m_186504_(p_186549_, p_186550_);
        }
    }

    @FunctionalInterface
    public static interface SpawnPredicate {
        public boolean m_47106_(EntityType<?> var1, BlockPos var2, ChunkAccess var3);
    }

    @FunctionalInterface
    public static interface AfterSpawnCallback {
        public void m_47100_(Mob var1, ChunkAccess var2);
    }
}

