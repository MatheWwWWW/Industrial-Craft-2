/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.shorts.ShortArrayList
 *  it.unimi.dsi.fastutil.shorts.ShortList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.StructureAccess;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.ticks.SerializableTickContainer;
import net.minecraft.world.ticks.TickContainerAccess;
import org.slf4j.Logger;

public abstract class ChunkAccess
implements BlockGetter,
BiomeManager.NoiseBiomeSource,
StructureAccess {
    private static final Logger f_187613_ = LogUtils.getLogger();
    private static final LongSet f_207932_ = new LongOpenHashSet();
    protected final ShortList[] f_187602_;
    protected volatile boolean f_187603_;
    private volatile boolean f_187614_;
    protected final ChunkPos f_187604_;
    private long f_187615_;
    @Nullable
    @Deprecated
    private BiomeGenerationSettings f_223003_;
    @Nullable
    protected NoiseChunk f_187605_;
    protected final UpgradeData f_187606_;
    @Nullable
    protected BlendingData f_187607_;
    protected final Map<Heightmap.Types, Heightmap> f_187608_ = Maps.newEnumMap(Heightmap.Types.class);
    private final Map<Structure, StructureStart> f_187617_ = Maps.newHashMap();
    private final Map<Structure, LongSet> f_187618_ = Maps.newHashMap();
    protected final Map<BlockPos, CompoundTag> f_187609_ = Maps.newHashMap();
    protected final Map<BlockPos, BlockEntity> f_187610_ = Maps.newHashMap();
    protected final LevelHeightAccessor f_187611_;
    protected final LevelChunkSection[] f_187612_;

    public ChunkAccess(ChunkPos p_187621_, UpgradeData p_187622_, LevelHeightAccessor p_187623_, Registry<Biome> p_187624_, long p_187625_, @Nullable LevelChunkSection[] p_187626_, @Nullable BlendingData p_187627_) {
        this.f_187604_ = p_187621_;
        this.f_187606_ = p_187622_;
        this.f_187611_ = p_187623_;
        this.f_187612_ = new LevelChunkSection[p_187623_.m_151559_()];
        this.f_187615_ = p_187625_;
        this.f_187602_ = new ShortList[p_187623_.m_151559_()];
        this.f_187607_ = p_187627_;
        if (p_187626_ != null) {
            if (this.f_187612_.length == p_187626_.length) {
                System.arraycopy(p_187626_, 0, this.f_187612_, 0, this.f_187612_.length);
            } else {
                f_187613_.warn("Could not set level chunk sections, array length is {} instead of {}", (Object)p_187626_.length, (Object)this.f_187612_.length);
            }
        }
        ChunkAccess.m_187634_(p_187623_, p_187624_, this.f_187612_);
    }

    private static void m_187634_(LevelHeightAccessor p_187635_, Registry<Biome> p_187636_, LevelChunkSection[] p_187637_) {
        for (int $$3 = 0; $$3 < p_187637_.length; ++$$3) {
            if (p_187637_[$$3] != null) continue;
            p_187637_[$$3] = new LevelChunkSection(p_187635_.m_151568_($$3), p_187636_);
        }
    }

    public GameEventDispatcher m_142336_(int p_156113_) {
        return GameEventDispatcher.f_157829_;
    }

    @Nullable
    public abstract BlockState m_6978_(BlockPos var1, BlockState var2, boolean var3);

    public abstract void m_142169_(BlockEntity var1);

    public abstract void m_6286_(Entity var1);

    @Nullable
    public LevelChunkSection m_62074_() {
        LevelChunkSection[] $$0 = this.m_7103_();
        for (int $$1 = $$0.length - 1; $$1 >= 0; --$$1) {
            LevelChunkSection $$2 = $$0[$$1];
            if ($$2.m_188008_()) continue;
            return $$2;
        }
        return null;
    }

    public int m_62098_() {
        LevelChunkSection $$0 = this.m_62074_();
        return $$0 == null ? this.m_141937_() : $$0.m_63017_();
    }

    public Set<BlockPos> m_5928_() {
        HashSet $$0 = Sets.newHashSet(this.f_187609_.keySet());
        $$0.addAll(this.f_187610_.keySet());
        return $$0;
    }

    public LevelChunkSection[] m_7103_() {
        return this.f_187612_;
    }

    public LevelChunkSection m_183278_(int p_187657_) {
        return this.m_7103_()[p_187657_];
    }

    public Collection<Map.Entry<Heightmap.Types, Heightmap>> m_6890_() {
        return Collections.unmodifiableSet(this.f_187608_.entrySet());
    }

    public void m_6511_(Heightmap.Types p_62083_, long[] p_62084_) {
        this.m_6005_(p_62083_).m_158364_(this, p_62083_, p_62084_);
    }

    public Heightmap m_6005_(Heightmap.Types p_62079_) {
        return this.f_187608_.computeIfAbsent(p_62079_, p_187665_ -> new Heightmap(this, (Heightmap.Types)p_187665_));
    }

    public boolean m_187658_(Heightmap.Types p_187659_) {
        return this.f_187608_.get(p_187659_) != null;
    }

    public int m_5885_(Heightmap.Types p_62080_, int p_62081_, int p_62082_) {
        Heightmap $$3 = this.f_187608_.get(p_62080_);
        if ($$3 == null) {
            if (SharedConstants.f_136183_ && this instanceof LevelChunk) {
                f_187613_.error("Unprimed heightmap: " + p_62080_ + " " + p_62081_ + " " + p_62082_);
            }
            Heightmap.m_64256_(this, EnumSet.of(p_62080_));
            $$3 = this.f_187608_.get(p_62080_);
        }
        return $$3.m_64242_(p_62081_ & 0xF, p_62082_ & 0xF) - 1;
    }

    public ChunkPos m_7697_() {
        return this.f_187604_;
    }

    @Override
    @Nullable
    public StructureStart m_213652_(Structure p_223005_) {
        return this.f_187617_.get(p_223005_);
    }

    @Override
    public void m_213792_(Structure p_223010_, StructureStart p_223011_) {
        this.f_187617_.put(p_223010_, p_223011_);
        this.f_187603_ = true;
    }

    public Map<Structure, StructureStart> m_6633_() {
        return Collections.unmodifiableMap(this.f_187617_);
    }

    public void m_8040_(Map<Structure, StructureStart> p_62090_) {
        this.f_187617_.clear();
        this.f_187617_.putAll(p_62090_);
        this.f_187603_ = true;
    }

    @Override
    public LongSet m_213649_(Structure p_223017_) {
        return this.f_187618_.getOrDefault(p_223017_, f_207932_);
    }

    @Override
    public void m_213843_(Structure p_223007_, long p_223008_) {
        this.f_187618_.computeIfAbsent(p_223007_, p_223019_ -> new LongOpenHashSet()).add(p_223008_);
        this.f_187603_ = true;
    }

    @Override
    public Map<Structure, LongSet> m_62769_() {
        return Collections.unmodifiableMap(this.f_187618_);
    }

    @Override
    public void m_62737_(Map<Structure, LongSet> p_187663_) {
        this.f_187618_.clear();
        this.f_187618_.putAll(p_187663_);
        this.f_187603_ = true;
    }

    public boolean m_5566_(int p_62075_, int p_62076_) {
        if (p_62075_ < this.m_141937_()) {
            p_62075_ = this.m_141937_();
        }
        if (p_62076_ >= this.m_151558_()) {
            p_62076_ = this.m_151558_() - 1;
        }
        for (int $$2 = p_62075_; $$2 <= p_62076_; $$2 += 16) {
            if (this.m_183278_(this.m_151564_($$2)).m_188008_()) continue;
            return false;
        }
        return true;
    }

    public void m_8092_(boolean p_62094_) {
        this.f_187603_ = p_62094_;
    }

    public boolean m_6344_() {
        return this.f_187603_;
    }

    public abstract ChunkStatus m_6415_();

    public abstract void m_8114_(BlockPos var1);

    public void m_8113_(BlockPos p_62102_) {
        f_187613_.warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", (Object)p_62102_);
    }

    public ShortList[] m_6720_() {
        return this.f_187602_;
    }

    public void m_6561_(short p_62092_, int p_62093_) {
        ChunkAccess.m_62095_(this.m_6720_(), p_62093_).add(p_62092_);
    }

    public void m_5604_(CompoundTag p_62091_) {
        this.f_187609_.put(BlockEntity.m_187472_(p_62091_), p_62091_);
    }

    @Nullable
    public CompoundTag m_8049_(BlockPos p_62103_) {
        return this.f_187609_.get(p_62103_);
    }

    @Nullable
    public abstract CompoundTag m_8051_(BlockPos var1);

    public abstract Stream<BlockPos> m_6267_();

    public abstract TickContainerAccess<Block> m_183531_();

    public abstract TickContainerAccess<Fluid> m_183526_();

    public abstract TicksToSave m_183568_();

    public UpgradeData m_7387_() {
        return this.f_187606_;
    }

    public boolean m_187675_() {
        return this.f_187607_ != null;
    }

    @Nullable
    public BlendingData m_183407_() {
        return this.f_187607_;
    }

    public void m_183400_(BlendingData p_187646_) {
        this.f_187607_ = p_187646_;
    }

    public long m_6319_() {
        return this.f_187615_;
    }

    public void m_187632_(long p_187633_) {
        this.f_187615_ += p_187633_;
    }

    public void m_6141_(long p_62099_) {
        this.f_187615_ = p_62099_;
    }

    public static ShortList m_62095_(ShortList[] p_62096_, int p_62097_) {
        if (p_62096_[p_62097_] == null) {
            p_62096_[p_62097_] = new ShortArrayList();
        }
        return p_62096_[p_62097_];
    }

    public boolean m_6332_() {
        return this.f_187614_;
    }

    public void m_8094_(boolean p_62100_) {
        this.f_187614_ = p_62100_;
        this.m_8092_(true);
    }

    @Override
    public int m_141937_() {
        return this.f_187611_.m_141937_();
    }

    @Override
    public int m_141928_() {
        return this.f_187611_.m_141928_();
    }

    public NoiseChunk m_223012_(Function<ChunkAccess, NoiseChunk> p_223013_) {
        if (this.f_187605_ == null) {
            this.f_187605_ = p_223013_.apply(this);
        }
        return this.f_187605_;
    }

    @Deprecated
    public BiomeGenerationSettings m_223014_(Supplier<BiomeGenerationSettings> p_223015_) {
        if (this.f_223003_ == null) {
            this.f_223003_ = p_223015_.get();
        }
        return this.f_223003_;
    }

    @Override
    public Holder<Biome> m_203495_(int p_204347_, int p_204348_, int p_204349_) {
        try {
            int $$3 = QuartPos.m_175400_(this.m_141937_());
            int $$4 = $$3 + QuartPos.m_175400_(this.m_141928_()) - 1;
            int $$5 = Mth.m_14045_(p_204348_, $$3, $$4);
            int $$6 = this.m_151564_(QuartPos.m_175402_($$5));
            return this.f_187612_[$$6].m_204433_(p_204347_ & 3, $$5 & 3, p_204349_ & 3);
        }
        catch (Throwable $$7) {
            CrashReport $$8 = CrashReport.m_127521_($$7, "Getting biome");
            CrashReportCategory $$9 = $$8.m_127514_("Biome being got");
            $$9.m_128165_("Location", () -> CrashReportCategory.m_178942_(this, p_204347_, p_204348_, p_204349_));
            throw new ReportedException($$8);
        }
    }

    public void m_183442_(BiomeResolver p_187638_, Climate.Sampler p_187639_) {
        ChunkPos $$2 = this.m_7697_();
        int $$3 = QuartPos.m_175400_($$2.m_45604_());
        int $$4 = QuartPos.m_175400_($$2.m_45605_());
        LevelHeightAccessor $$5 = this.m_183618_();
        for (int $$6 = $$5.m_151560_(); $$6 < $$5.m_151561_(); ++$$6) {
            LevelChunkSection $$7 = this.m_183278_(this.m_151566_($$6));
            $$7.m_188003_(p_187638_, p_187639_, $$3, $$4);
        }
    }

    public boolean m_187678_() {
        return !this.m_62769_().isEmpty();
    }

    @Nullable
    public BelowZeroRetrogen m_183376_() {
        return null;
    }

    public boolean m_187679_() {
        return this.m_183376_() != null;
    }

    public LevelHeightAccessor m_183618_() {
        return this;
    }

    public record TicksToSave(SerializableTickContainer<Block> f_187680_, SerializableTickContainer<Fluid> f_187681_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TicksToSave.class, "blocks;fluids", "f_187680_", "f_187681_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TicksToSave.class, "blocks;fluids", "f_187680_", "f_187681_"}, this);
        }

        @Override
        public final boolean equals(Object p_187688_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TicksToSave.class, "blocks;fluids", "f_187680_", "f_187681_"}, this, p_187688_);
        }
    }
}

