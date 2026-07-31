/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  it.unimi.dsi.fastutil.shorts.ShortList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.ticks.LevelChunkTicks;
import net.minecraft.world.ticks.ProtoChunkTicks;
import net.minecraft.world.ticks.TickContainerAccess;

public class ProtoChunk
extends ChunkAccess {
    @Nullable
    private volatile LevelLightEngine f_63151_;
    private volatile ChunkStatus f_63153_ = ChunkStatus.f_62314_;
    private final List<CompoundTag> f_63157_ = Lists.newArrayList();
    private final List<BlockPos> f_63158_ = Lists.newArrayList();
    private final Map<GenerationStep.Carving, CarvingMask> f_63166_ = new Object2ObjectArrayMap();
    @Nullable
    private BelowZeroRetrogen f_188164_;
    private final ProtoChunkTicks<Block> f_63163_;
    private final ProtoChunkTicks<Fluid> f_188165_;

    public ProtoChunk(ChunkPos p_188167_, UpgradeData p_188168_, LevelHeightAccessor p_188169_, Registry<Biome> p_188170_, @Nullable BlendingData p_188171_) {
        this(p_188167_, p_188168_, null, new ProtoChunkTicks<Block>(), new ProtoChunkTicks<Fluid>(), p_188169_, p_188170_, p_188171_);
    }

    public ProtoChunk(ChunkPos p_188173_, UpgradeData p_188174_, @Nullable LevelChunkSection[] p_188175_, ProtoChunkTicks<Block> p_188176_, ProtoChunkTicks<Fluid> p_188177_, LevelHeightAccessor p_188178_, Registry<Biome> p_188179_, @Nullable BlendingData p_188180_) {
        super(p_188173_, p_188174_, p_188178_, p_188179_, 0L, p_188175_, p_188180_);
        this.f_63163_ = p_188176_;
        this.f_188165_ = p_188177_;
    }

    @Override
    public TickContainerAccess<Block> m_183531_() {
        return this.f_63163_;
    }

    @Override
    public TickContainerAccess<Fluid> m_183526_() {
        return this.f_188165_;
    }

    @Override
    public ChunkAccess.TicksToSave m_183568_() {
        return new ChunkAccess.TicksToSave(this.f_63163_, this.f_188165_);
    }

    @Override
    public BlockState m_8055_(BlockPos p_63264_) {
        int $$1 = p_63264_.m_123342_();
        if (this.m_151562_($$1)) {
            return Blocks.f_50626_.m_49966_();
        }
        LevelChunkSection $$2 = this.m_183278_(this.m_151564_($$1));
        if ($$2.m_188008_()) {
            return Blocks.f_50016_.m_49966_();
        }
        return $$2.m_62982_(p_63264_.m_123341_() & 0xF, $$1 & 0xF, p_63264_.m_123343_() & 0xF);
    }

    @Override
    public FluidState m_6425_(BlockPos p_63239_) {
        int $$1 = p_63239_.m_123342_();
        if (this.m_151562_($$1)) {
            return Fluids.f_76191_.m_76145_();
        }
        LevelChunkSection $$2 = this.m_183278_(this.m_151564_($$1));
        if ($$2.m_188008_()) {
            return Fluids.f_76191_.m_76145_();
        }
        return $$2.m_63007_(p_63239_.m_123341_() & 0xF, $$1 & 0xF, p_63239_.m_123343_() & 0xF);
    }

    @Override
    public Stream<BlockPos> m_6267_() {
        return this.f_63158_.stream();
    }

    public ShortList[] m_63291_() {
        ShortList[] $$0 = new ShortList[this.m_151559_()];
        for (BlockPos $$1 : this.f_63158_) {
            ChunkAccess.m_62095_($$0, this.m_151564_($$1.m_123342_())).add(ProtoChunk.m_63280_($$1));
        }
        return $$0;
    }

    public void m_63244_(short p_63245_, int p_63246_) {
        this.m_63277_(ProtoChunk.m_63227_(p_63245_, this.m_151568_(p_63246_), this.f_187604_));
    }

    public void m_63277_(BlockPos p_63278_) {
        this.f_63158_.add(p_63278_.m_7949_());
    }

    @Override
    @Nullable
    public BlockState m_6978_(BlockPos p_63217_, BlockState p_63218_, boolean p_63219_) {
        int $$3 = p_63217_.m_123341_();
        int $$4 = p_63217_.m_123342_();
        int $$5 = p_63217_.m_123343_();
        if ($$4 < this.m_141937_() || $$4 >= this.m_151558_()) {
            return Blocks.f_50626_.m_49966_();
        }
        int $$6 = this.m_151564_($$4);
        if (this.f_187612_[$$6].m_188008_() && p_63218_.m_60713_(Blocks.f_50016_)) {
            return p_63218_;
        }
        if (p_63218_.m_60791_() > 0) {
            this.f_63158_.add(new BlockPos(($$3 & 0xF) + this.m_7697_().m_45604_(), $$4, ($$5 & 0xF) + this.m_7697_().m_45605_()));
        }
        LevelChunkSection $$7 = this.m_183278_($$6);
        BlockState $$8 = $$7.m_62986_($$3 & 0xF, $$4 & 0xF, $$5 & 0xF, p_63218_);
        if (this.f_63153_.m_62427_(ChunkStatus.f_62322_) && p_63218_ != $$8 && (p_63218_.m_60739_(this, p_63217_) != $$8.m_60739_(this, p_63217_) || p_63218_.m_60791_() != $$8.m_60791_() || p_63218_.m_60787_() || $$8.m_60787_())) {
            this.f_63151_.m_7174_(p_63217_);
        }
        EnumSet<Heightmap.Types> $$9 = this.m_6415_().m_62500_();
        EnumSet<Heightmap.Types> $$10 = null;
        for (Heightmap.Types $$11 : $$9) {
            Heightmap $$12 = (Heightmap)this.f_187608_.get($$11);
            if ($$12 != null) continue;
            if ($$10 == null) {
                $$10 = EnumSet.noneOf(Heightmap.Types.class);
            }
            $$10.add($$11);
        }
        if ($$10 != null) {
            Heightmap.m_64256_(this, $$10);
        }
        for (Heightmap.Types $$13 : $$9) {
            ((Heightmap)this.f_187608_.get($$13)).m_64249_($$3 & 0xF, $$4, $$5 & 0xF, p_63218_);
        }
        return $$8;
    }

    @Override
    public void m_142169_(BlockEntity p_156488_) {
        this.f_187610_.put(p_156488_.m_58899_(), p_156488_);
    }

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_63257_) {
        return (BlockEntity)this.f_187610_.get(p_63257_);
    }

    public Map<BlockPos, BlockEntity> m_63292_() {
        return this.f_187610_;
    }

    public void m_63242_(CompoundTag p_63243_) {
        this.f_63157_.add(p_63243_);
    }

    @Override
    public void m_6286_(Entity p_63183_) {
        if (p_63183_.m_20159_()) {
            return;
        }
        CompoundTag $$1 = new CompoundTag();
        p_63183_.m_20223_($$1);
        this.m_63242_($$1);
    }

    @Override
    public void m_213792_(Structure p_223432_, StructureStart p_223433_) {
        BelowZeroRetrogen $$2 = this.m_183376_();
        if ($$2 != null && p_223433_.m_73603_()) {
            BoundingBox $$3 = p_223433_.m_73601_();
            LevelHeightAccessor $$4 = this.m_183618_();
            if ($$3.m_162396_() < $$4.m_141937_() || $$3.m_162400_() >= $$4.m_151558_()) {
                return;
            }
        }
        super.m_213792_(p_223432_, p_223433_);
    }

    public List<CompoundTag> m_63293_() {
        return this.f_63157_;
    }

    @Override
    public ChunkStatus m_6415_() {
        return this.f_63153_;
    }

    public void m_7150_(ChunkStatus p_63187_) {
        this.f_63153_ = p_63187_;
        if (this.f_188164_ != null && p_63187_.m_62427_(this.f_188164_.m_188466_())) {
            this.m_188183_(null);
        }
        this.m_8092_(true);
    }

    @Override
    public Holder<Biome> m_203495_(int p_204450_, int p_204451_, int p_204452_) {
        if (this.m_6415_().m_62427_(ChunkStatus.f_62317_) || this.f_188164_ != null && this.f_188164_.m_188466_().m_62427_(ChunkStatus.f_62317_)) {
            return super.m_203495_(p_204450_, p_204451_, p_204452_);
        }
        throw new IllegalStateException("Asking for biomes before we have biomes");
    }

    public static short m_63280_(BlockPos p_63281_) {
        int $$1 = p_63281_.m_123341_();
        int $$2 = p_63281_.m_123342_();
        int $$3 = p_63281_.m_123343_();
        int $$4 = $$1 & 0xF;
        int $$5 = $$2 & 0xF;
        int $$6 = $$3 & 0xF;
        return (short)($$4 | $$5 << 4 | $$6 << 8);
    }

    public static BlockPos m_63227_(short p_63228_, int p_63229_, ChunkPos p_63230_) {
        int $$3 = SectionPos.m_175554_(p_63230_.f_45578_, p_63228_ & 0xF);
        int $$4 = SectionPos.m_175554_(p_63229_, p_63228_ >>> 4 & 0xF);
        int $$5 = SectionPos.m_175554_(p_63230_.f_45579_, p_63228_ >>> 8 & 0xF);
        return new BlockPos($$3, $$4, $$5);
    }

    @Override
    public void m_8113_(BlockPos p_63266_) {
        if (!this.m_151570_(p_63266_)) {
            ChunkAccess.m_62095_(this.f_187602_, this.m_151564_(p_63266_.m_123342_())).add(ProtoChunk.m_63280_(p_63266_));
        }
    }

    @Override
    public void m_6561_(short p_63225_, int p_63226_) {
        ChunkAccess.m_62095_(this.f_187602_, p_63226_).add(p_63225_);
    }

    public Map<BlockPos, CompoundTag> m_63294_() {
        return Collections.unmodifiableMap(this.f_187609_);
    }

    @Override
    @Nullable
    public CompoundTag m_8051_(BlockPos p_63275_) {
        BlockEntity $$1 = this.m_7702_(p_63275_);
        if ($$1 != null) {
            return $$1.m_187480_();
        }
        return (CompoundTag)this.f_187609_.get(p_63275_);
    }

    @Override
    public void m_8114_(BlockPos p_63262_) {
        this.f_187610_.remove(p_63262_);
        this.f_187609_.remove(p_63262_);
    }

    @Nullable
    public CarvingMask m_183612_(GenerationStep.Carving p_188185_) {
        return this.f_63166_.get(p_188185_);
    }

    public CarvingMask m_183613_(GenerationStep.Carving p_188191_) {
        return this.f_63166_.computeIfAbsent(p_188191_, p_188193_ -> new CarvingMask(this.m_141928_(), this.m_141937_()));
    }

    public void m_188186_(GenerationStep.Carving p_188187_, CarvingMask p_188188_) {
        this.f_63166_.put(p_188187_, p_188188_);
    }

    public void m_63209_(LevelLightEngine p_63210_) {
        this.f_63151_ = p_63210_;
    }

    public void m_188183_(@Nullable BelowZeroRetrogen p_188184_) {
        this.f_188164_ = p_188184_;
    }

    @Override
    @Nullable
    public BelowZeroRetrogen m_183376_() {
        return this.f_188164_;
    }

    private static <T> LevelChunkTicks<T> m_188189_(ProtoChunkTicks<T> p_188190_) {
        return new LevelChunkTicks<T>(p_188190_.m_193306_());
    }

    public LevelChunkTicks<Block> m_188181_() {
        return ProtoChunk.m_188189_(this.f_63163_);
    }

    public LevelChunkTicks<Fluid> m_188182_() {
        return ProtoChunk.m_188189_(this.f_188165_);
    }

    @Override
    public LevelHeightAccessor m_183618_() {
        if (this.m_187679_()) {
            return BelowZeroRetrogen.f_188456_;
        }
        return this;
    }
}

