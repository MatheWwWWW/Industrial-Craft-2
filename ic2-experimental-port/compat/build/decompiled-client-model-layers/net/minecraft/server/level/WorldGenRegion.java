/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.WorldGenTickAccess;
import org.slf4j.Logger;

public class WorldGenRegion
implements WorldGenLevel {
    private static final Logger f_9474_ = LogUtils.getLogger();
    private final List<ChunkAccess> f_9475_;
    private final ChunkAccess f_143479_;
    private final int f_9478_;
    private final ServerLevel f_9479_;
    private final long f_9480_;
    private final LevelData f_9481_;
    private final RandomSource f_9482_;
    private final DimensionType f_9483_;
    private final WorldGenTickAccess<Block> f_9484_ = new WorldGenTickAccess(p_184191_ -> this.m_46865_((BlockPos)p_184191_).m_183531_());
    private final WorldGenTickAccess<Fluid> f_184181_ = new WorldGenTickAccess(p_184189_ -> this.m_46865_((BlockPos)p_184189_).m_183526_());
    private final BiomeManager f_9486_;
    private final ChunkPos f_9487_;
    private final ChunkPos f_9488_;
    private final StructureManager f_215157_;
    private final ChunkStatus f_143480_;
    private final int f_143481_;
    @Nullable
    private Supplier<String> f_143482_;
    private final AtomicLong f_184182_ = new AtomicLong();
    private static final ResourceLocation f_215158_ = new ResourceLocation("worldgen_region_random");

    public WorldGenRegion(ServerLevel p_143484_, List<ChunkAccess> p_143485_, ChunkStatus p_143486_, int p_143487_) {
        this.f_143480_ = p_143486_;
        this.f_143481_ = p_143487_;
        int $$4 = Mth.m_14107_(Math.sqrt(p_143485_.size()));
        if ($$4 * $$4 != p_143485_.size()) {
            throw Util.m_137570_(new IllegalStateException("Cache size is not a square."));
        }
        this.f_9475_ = p_143485_;
        this.f_143479_ = p_143485_.get(p_143485_.size() / 2);
        this.f_9478_ = $$4;
        this.f_9479_ = p_143484_;
        this.f_9480_ = p_143484_.m_7328_();
        this.f_9481_ = p_143484_.m_6106_();
        this.f_9482_ = p_143484_.m_7726_().m_214994_().m_224565_(f_215158_).m_224542_(this.f_143479_.m_7697_().m_45615_());
        this.f_9483_ = p_143484_.m_6042_();
        this.f_9486_ = new BiomeManager(this, BiomeManager.m_47877_(this.f_9480_));
        this.f_9487_ = p_143485_.get(0).m_7697_();
        this.f_9488_ = p_143485_.get(p_143485_.size() - 1).m_7697_();
        this.f_215157_ = p_143484_.m_215010_().m_220468_(this);
    }

    public boolean m_215159_(ChunkPos p_215160_, int p_215161_) {
        return this.f_9479_.m_7726_().f_8325_.m_223451_(p_215160_, p_215161_);
    }

    public ChunkPos m_143488_() {
        return this.f_143479_.m_7697_();
    }

    @Override
    public void m_143497_(@Nullable Supplier<String> p_143498_) {
        this.f_143482_ = p_143498_;
    }

    @Override
    public ChunkAccess m_6325_(int p_9507_, int p_9508_) {
        return this.m_46819_(p_9507_, p_9508_, ChunkStatus.f_62314_);
    }

    @Override
    @Nullable
    public ChunkAccess m_6522_(int p_9514_, int p_9515_, ChunkStatus p_9516_, boolean p_9517_) {
        ChunkAccess $$7;
        if (this.m_7232_(p_9514_, p_9515_)) {
            int $$4 = p_9514_ - this.f_9487_.f_45578_;
            int $$5 = p_9515_ - this.f_9487_.f_45579_;
            ChunkAccess $$6 = this.f_9475_.get($$4 + $$5 * this.f_9478_);
            if ($$6.m_6415_().m_62427_(p_9516_)) {
                return $$6;
            }
        } else {
            $$7 = null;
        }
        if (!p_9517_) {
            return null;
        }
        f_9474_.error("Requested chunk : {} {}", (Object)p_9514_, (Object)p_9515_);
        f_9474_.error("Region bounds : {} {} | {} {}", new Object[]{this.f_9487_.f_45578_, this.f_9487_.f_45579_, this.f_9488_.f_45578_, this.f_9488_.f_45579_});
        if ($$7 != null) {
            throw Util.m_137570_(new RuntimeException(String.format(Locale.ROOT, "Chunk is not of correct status. Expecting %s, got %s | %s %s", p_9516_, $$7.m_6415_(), p_9514_, p_9515_)));
        }
        throw Util.m_137570_(new RuntimeException(String.format(Locale.ROOT, "We are asking a region for a chunk out of bound | %s %s", p_9514_, p_9515_)));
    }

    @Override
    public boolean m_7232_(int p_9574_, int p_9575_) {
        return p_9574_ >= this.f_9487_.f_45578_ && p_9574_ <= this.f_9488_.f_45578_ && p_9575_ >= this.f_9487_.f_45579_ && p_9575_ <= this.f_9488_.f_45579_;
    }

    @Override
    public BlockState m_8055_(BlockPos p_9587_) {
        return this.m_6325_(SectionPos.m_123171_(p_9587_.m_123341_()), SectionPos.m_123171_(p_9587_.m_123343_())).m_8055_(p_9587_);
    }

    @Override
    public FluidState m_6425_(BlockPos p_9577_) {
        return this.m_46865_(p_9577_).m_6425_(p_9577_);
    }

    @Override
    @Nullable
    public Player m_5788_(double p_9501_, double p_9502_, double p_9503_, double p_9504_, Predicate<Entity> p_9505_) {
        return null;
    }

    @Override
    public int m_7445_() {
        return 0;
    }

    @Override
    public BiomeManager m_7062_() {
        return this.f_9486_;
    }

    @Override
    public Holder<Biome> m_203675_(int p_203787_, int p_203788_, int p_203789_) {
        return this.f_9479_.m_203675_(p_203787_, p_203788_, p_203789_);
    }

    @Override
    public float m_7717_(Direction p_9555_, boolean p_9556_) {
        return 1.0f;
    }

    @Override
    public LevelLightEngine m_5518_() {
        return this.f_9479_.m_5518_();
    }

    @Override
    public boolean m_7740_(BlockPos p_9550_, boolean p_9551_, @Nullable Entity p_9552_, int p_9553_) {
        BlockState $$4 = this.m_8055_(p_9550_);
        if ($$4.m_60795_()) {
            return false;
        }
        if (p_9551_) {
            BlockEntity $$5 = $$4.m_155947_() ? this.m_7702_(p_9550_) : null;
            Block.m_49881_($$4, this.f_9479_, p_9550_, $$5, p_9552_, ItemStack.f_41583_);
        }
        return this.m_6933_(p_9550_, Blocks.f_50016_.m_49966_(), 3, p_9553_);
    }

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_9582_) {
        ChunkAccess $$1 = this.m_46865_(p_9582_);
        BlockEntity $$2 = $$1.m_7702_(p_9582_);
        if ($$2 != null) {
            return $$2;
        }
        CompoundTag $$3 = $$1.m_8049_(p_9582_);
        BlockState $$4 = $$1.m_8055_(p_9582_);
        if ($$3 != null) {
            if ("DUMMY".equals($$3.m_128461_("id"))) {
                if (!$$4.m_155947_()) {
                    return null;
                }
                $$2 = ((EntityBlock)((Object)$$4.m_60734_())).m_142194_(p_9582_, $$4);
            } else {
                $$2 = BlockEntity.m_155241_(p_9582_, $$4, $$3);
            }
            if ($$2 != null) {
                $$1.m_142169_($$2);
                return $$2;
            }
        }
        if ($$4.m_155947_()) {
            f_9474_.warn("Tried to access a block entity before it was created. {}", (Object)p_9582_);
        }
        return null;
    }

    @Override
    public boolean m_180807_(BlockPos p_181031_) {
        int $$1 = SectionPos.m_123171_(p_181031_.m_123341_());
        int $$2 = SectionPos.m_123171_(p_181031_.m_123343_());
        ChunkPos $$3 = this.m_143488_();
        int $$4 = Math.abs($$3.f_45578_ - $$1);
        int $$5 = Math.abs($$3.f_45579_ - $$2);
        if ($$4 > this.f_143481_ || $$5 > this.f_143481_) {
            Util.m_143785_("Detected setBlock in a far chunk [" + $$1 + ", " + $$2 + "], pos: " + p_181031_ + ", status: " + this.f_143480_ + (String)(this.f_143482_ == null ? "" : ", currently generating: " + this.f_143482_.get()));
            return false;
        }
        if (this.f_143479_.m_187679_()) {
            LevelHeightAccessor $$6 = this.f_143479_.m_183618_();
            if (p_181031_.m_123342_() < $$6.m_141937_() || p_181031_.m_123342_() >= $$6.m_151558_()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean m_6933_(BlockPos p_9539_, BlockState p_9540_, int p_9541_, int p_9542_) {
        if (!this.m_180807_(p_9539_)) {
            return false;
        }
        ChunkAccess $$4 = this.m_46865_(p_9539_);
        BlockState $$5 = $$4.m_6978_(p_9539_, p_9540_, false);
        if ($$5 != null) {
            this.f_9479_.m_6559_(p_9539_, $$5, p_9540_);
        }
        if (p_9540_.m_155947_()) {
            if ($$4.m_6415_().m_62494_() == ChunkStatus.ChunkType.LEVELCHUNK) {
                BlockEntity $$6 = ((EntityBlock)((Object)p_9540_.m_60734_())).m_142194_(p_9539_, p_9540_);
                if ($$6 != null) {
                    $$4.m_142169_($$6);
                } else {
                    $$4.m_8114_(p_9539_);
                }
            } else {
                CompoundTag $$7 = new CompoundTag();
                $$7.m_128405_("x", p_9539_.m_123341_());
                $$7.m_128405_("y", p_9539_.m_123342_());
                $$7.m_128405_("z", p_9539_.m_123343_());
                $$7.m_128359_("id", "DUMMY");
                $$4.m_5604_($$7);
            }
        } else if ($$5 != null && $$5.m_155947_()) {
            $$4.m_8114_(p_9539_);
        }
        if (p_9540_.m_60835_(this, p_9539_)) {
            this.m_9591_(p_9539_);
        }
        return true;
    }

    private void m_9591_(BlockPos p_9592_) {
        this.m_46865_(p_9592_).m_8113_(p_9592_);
    }

    @Override
    public boolean m_7967_(Entity p_9580_) {
        int $$1 = SectionPos.m_123171_(p_9580_.m_146903_());
        int $$2 = SectionPos.m_123171_(p_9580_.m_146907_());
        this.m_6325_($$1, $$2).m_6286_(p_9580_);
        return true;
    }

    @Override
    public boolean m_7471_(BlockPos p_9547_, boolean p_9548_) {
        return this.m_7731_(p_9547_, Blocks.f_50016_.m_49966_(), 3);
    }

    @Override
    public WorldBorder m_6857_() {
        return this.f_9479_.m_6857_();
    }

    @Override
    public boolean m_5776_() {
        return false;
    }

    @Override
    @Deprecated
    public ServerLevel m_6018_() {
        return this.f_9479_;
    }

    @Override
    public RegistryAccess m_5962_() {
        return this.f_9479_.m_5962_();
    }

    @Override
    public LevelData m_6106_() {
        return this.f_9481_;
    }

    @Override
    public DifficultyInstance m_6436_(BlockPos p_9585_) {
        if (!this.m_7232_(SectionPos.m_123171_(p_9585_.m_123341_()), SectionPos.m_123171_(p_9585_.m_123343_()))) {
            throw new RuntimeException("We are asking a region for a chunk out of bound");
        }
        return new DifficultyInstance(this.f_9479_.m_46791_(), this.f_9479_.m_46468_(), 0L, this.f_9479_.m_46940_());
    }

    @Override
    @Nullable
    public MinecraftServer m_7654_() {
        return this.f_9479_.m_7654_();
    }

    @Override
    public ChunkSource m_7726_() {
        return this.f_9479_.m_7726_();
    }

    @Override
    public long m_7328_() {
        return this.f_9480_;
    }

    @Override
    public LevelTickAccess<Block> m_183326_() {
        return this.f_9484_;
    }

    @Override
    public LevelTickAccess<Fluid> m_183324_() {
        return this.f_184181_;
    }

    @Override
    public int m_5736_() {
        return this.f_9479_.m_5736_();
    }

    @Override
    public RandomSource m_213780_() {
        return this.f_9482_;
    }

    @Override
    public int m_6924_(Heightmap.Types p_9535_, int p_9536_, int p_9537_) {
        return this.m_6325_(SectionPos.m_123171_(p_9536_), SectionPos.m_123171_(p_9537_)).m_5885_(p_9535_, p_9536_ & 0xF, p_9537_ & 0xF) + 1;
    }

    @Override
    public void m_5594_(@Nullable Player p_9528_, BlockPos p_9529_, SoundEvent p_9530_, SoundSource p_9531_, float p_9532_, float p_9533_) {
    }

    @Override
    public void m_7106_(ParticleOptions p_9561_, double p_9562_, double p_9563_, double p_9564_, double p_9565_, double p_9566_, double p_9567_) {
    }

    @Override
    public void m_5898_(@Nullable Player p_9523_, int p_9524_, BlockPos p_9525_, int p_9526_) {
    }

    @Override
    public void m_214171_(GameEvent p_215163_, Vec3 p_215164_, GameEvent.Context p_215165_) {
    }

    @Override
    public DimensionType m_6042_() {
        return this.f_9483_;
    }

    @Override
    public boolean m_7433_(BlockPos p_9544_, Predicate<BlockState> p_9545_) {
        return p_9545_.test(this.m_8055_(p_9544_));
    }

    @Override
    public boolean m_142433_(BlockPos p_143500_, Predicate<FluidState> p_143501_) {
        return p_143501_.test(this.m_6425_(p_143500_));
    }

    @Override
    public <T extends Entity> List<T> m_142425_(EntityTypeTest<Entity, T> p_143494_, AABB p_143495_, Predicate<? super T> p_143496_) {
        return Collections.emptyList();
    }

    @Override
    public List<Entity> m_6249_(@Nullable Entity p_9519_, AABB p_9520_, @Nullable Predicate<? super Entity> p_9521_) {
        return Collections.emptyList();
    }

    public List<Player> m_6907_() {
        return Collections.emptyList();
    }

    @Override
    public int m_141937_() {
        return this.f_9479_.m_141937_();
    }

    @Override
    public int m_141928_() {
        return this.f_9479_.m_141928_();
    }

    @Override
    public long m_183596_() {
        return this.f_184182_.getAndIncrement();
    }
}

