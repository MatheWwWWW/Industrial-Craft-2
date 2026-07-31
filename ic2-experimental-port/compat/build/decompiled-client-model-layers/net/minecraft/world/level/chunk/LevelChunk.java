/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.gameevent.EuclideanGameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.ticks.LevelChunkTicks;
import net.minecraft.world.ticks.LevelTicks;
import net.minecraft.world.ticks.TickContainerAccess;
import org.slf4j.Logger;

public class LevelChunk
extends ChunkAccess {
    static final Logger f_62771_ = LogUtils.getLogger();
    private static final TickingBlockEntity f_156361_ = new TickingBlockEntity(){

        @Override
        public void m_142224_() {
        }

        @Override
        public boolean m_142220_() {
            return true;
        }

        @Override
        public BlockPos m_142689_() {
            return BlockPos.f_121853_;
        }

        @Override
        public String m_142280_() {
            return "<null>";
        }
    };
    private final Map<BlockPos, RebindableTickingBlockEntityWrapper> f_156362_ = Maps.newHashMap();
    private boolean f_62775_;
    private boolean f_196848_ = false;
    final Level f_62776_;
    @Nullable
    private Supplier<ChunkHolder.FullChunkStatus> f_62790_;
    @Nullable
    private PostLoadProcessor f_62791_;
    private final Int2ObjectMap<GameEventDispatcher> f_156363_;
    private final LevelChunkTicks<Block> f_62784_;
    private final LevelChunkTicks<Fluid> f_187943_;

    public LevelChunk(Level p_187945_, ChunkPos p_187946_) {
        this(p_187945_, p_187946_, UpgradeData.f_63320_, new LevelChunkTicks<Block>(), new LevelChunkTicks<Fluid>(), 0L, null, null, null);
    }

    public LevelChunk(Level p_196854_, ChunkPos p_196855_, UpgradeData p_196856_, LevelChunkTicks<Block> p_196857_, LevelChunkTicks<Fluid> p_196858_, long p_196859_, @Nullable LevelChunkSection[] p_196860_, @Nullable PostLoadProcessor p_196861_, @Nullable BlendingData p_196862_) {
        super(p_196855_, p_196856_, p_196854_, p_196854_.m_5962_().m_175515_(Registry.f_122885_), p_196859_, p_196860_, p_196862_);
        this.f_62776_ = p_196854_;
        this.f_156363_ = new Int2ObjectOpenHashMap();
        for (Heightmap.Types $$9 : Heightmap.Types.values()) {
            if (!ChunkStatus.f_62326_.m_62500_().contains($$9)) continue;
            this.f_187608_.put($$9, new Heightmap(this, $$9));
        }
        this.f_62791_ = p_196861_;
        this.f_62784_ = p_196857_;
        this.f_187943_ = p_196858_;
    }

    public LevelChunk(ServerLevel p_196850_, ProtoChunk p_196851_, @Nullable PostLoadProcessor p_196852_) {
        this(p_196850_, p_196851_.m_7697_(), p_196851_.m_7387_(), p_196851_.m_188181_(), p_196851_.m_188182_(), p_196851_.m_6319_(), p_196851_.m_7103_(), p_196852_, p_196851_.m_183407_());
        for (BlockEntity $$3 : p_196851_.m_63292_().values()) {
            this.m_142169_($$3);
        }
        this.f_187609_.putAll(p_196851_.m_63294_());
        for (int $$4 = 0; $$4 < p_196851_.m_6720_().length; ++$$4) {
            this.f_187602_[$$4] = p_196851_.m_6720_()[$$4];
        }
        this.m_8040_(p_196851_.m_6633_());
        this.m_62737_(p_196851_.m_62769_());
        for (Map.Entry<Heightmap.Types, Heightmap> $$5 : p_196851_.m_6890_()) {
            if (!ChunkStatus.f_62326_.m_62500_().contains($$5.getKey())) continue;
            this.m_6511_($$5.getKey(), $$5.getValue().m_64239_());
        }
        this.m_8094_(p_196851_.m_6332_());
        this.f_187603_ = true;
    }

    @Override
    public TickContainerAccess<Block> m_183531_() {
        return this.f_62784_;
    }

    @Override
    public TickContainerAccess<Fluid> m_183526_() {
        return this.f_187943_;
    }

    @Override
    public ChunkAccess.TicksToSave m_183568_() {
        return new ChunkAccess.TicksToSave(this.f_62784_, this.f_187943_);
    }

    @Override
    public GameEventDispatcher m_142336_(int p_156372_) {
        Level level = this.f_62776_;
        if (level instanceof ServerLevel) {
            ServerLevel $$1 = (ServerLevel)level;
            return (GameEventDispatcher)this.f_156363_.computeIfAbsent(p_156372_, p_223411_ -> new EuclideanGameEventDispatcher($$1));
        }
        return super.m_142336_(p_156372_);
    }

    @Override
    public BlockState m_8055_(BlockPos p_62923_) {
        int $$1 = p_62923_.m_123341_();
        int $$2 = p_62923_.m_123342_();
        int $$3 = p_62923_.m_123343_();
        if (this.f_62776_.m_46659_()) {
            BlockState $$4 = null;
            if ($$2 == 60) {
                $$4 = Blocks.f_50375_.m_49966_();
            }
            if ($$2 == 70) {
                $$4 = DebugLevelSource.m_64148_($$1, $$3);
            }
            return $$4 == null ? Blocks.f_50016_.m_49966_() : $$4;
        }
        try {
            LevelChunkSection $$6;
            int $$5 = this.m_151564_($$2);
            if ($$5 >= 0 && $$5 < this.f_187612_.length && !($$6 = this.f_187612_[$$5]).m_188008_()) {
                return $$6.m_62982_($$1 & 0xF, $$2 & 0xF, $$3 & 0xF);
            }
            return Blocks.f_50016_.m_49966_();
        }
        catch (Throwable $$7) {
            CrashReport $$8 = CrashReport.m_127521_($$7, "Getting block state");
            CrashReportCategory $$9 = $$8.m_127514_("Block being got");
            $$9.m_128165_("Location", () -> CrashReportCategory.m_178942_(this, $$1, $$2, $$3));
            throw new ReportedException($$8);
        }
    }

    @Override
    public FluidState m_6425_(BlockPos p_62895_) {
        return this.m_62814_(p_62895_.m_123341_(), p_62895_.m_123342_(), p_62895_.m_123343_());
    }

    public FluidState m_62814_(int p_62815_, int p_62816_, int p_62817_) {
        try {
            LevelChunkSection $$4;
            int $$3 = this.m_151564_(p_62816_);
            if ($$3 >= 0 && $$3 < this.f_187612_.length && !($$4 = this.f_187612_[$$3]).m_188008_()) {
                return $$4.m_63007_(p_62815_ & 0xF, p_62816_ & 0xF, p_62817_ & 0xF);
            }
            return Fluids.f_76191_.m_76145_();
        }
        catch (Throwable $$5) {
            CrashReport $$6 = CrashReport.m_127521_($$5, "Getting fluid state");
            CrashReportCategory $$7 = $$6.m_127514_("Block being got");
            $$7.m_128165_("Location", () -> CrashReportCategory.m_178942_(this, p_62815_, p_62816_, p_62817_));
            throw new ReportedException($$6);
        }
    }

    @Override
    @Nullable
    public BlockState m_6978_(BlockPos p_62865_, BlockState p_62866_, boolean p_62867_) {
        int $$8;
        int $$7;
        int $$3 = p_62865_.m_123342_();
        LevelChunkSection $$4 = this.m_183278_(this.m_151564_($$3));
        boolean $$5 = $$4.m_188008_();
        if ($$5 && p_62866_.m_60795_()) {
            return null;
        }
        int $$6 = p_62865_.m_123341_() & 0xF;
        BlockState $$9 = $$4.m_62986_($$6, $$7 = $$3 & 0xF, $$8 = p_62865_.m_123343_() & 0xF, p_62866_);
        if ($$9 == p_62866_) {
            return null;
        }
        Block $$10 = p_62866_.m_60734_();
        ((Heightmap)this.f_187608_.get(Heightmap.Types.MOTION_BLOCKING)).m_64249_($$6, $$3, $$8, p_62866_);
        ((Heightmap)this.f_187608_.get(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)).m_64249_($$6, $$3, $$8, p_62866_);
        ((Heightmap)this.f_187608_.get(Heightmap.Types.OCEAN_FLOOR)).m_64249_($$6, $$3, $$8, p_62866_);
        ((Heightmap)this.f_187608_.get(Heightmap.Types.WORLD_SURFACE)).m_64249_($$6, $$3, $$8, p_62866_);
        boolean $$11 = $$4.m_188008_();
        if ($$5 != $$11) {
            this.f_62776_.m_7726_().m_7827_().m_75834_(p_62865_, $$11);
        }
        boolean $$12 = $$9.m_155947_();
        if (!this.f_62776_.f_46443_) {
            $$9.m_60753_(this.f_62776_, p_62865_, p_62866_, p_62867_);
        } else if (!$$9.m_60713_($$10) && $$12) {
            this.m_8114_(p_62865_);
        }
        if (!$$4.m_62982_($$6, $$7, $$8).m_60713_($$10)) {
            return null;
        }
        if (!this.f_62776_.f_46443_) {
            p_62866_.m_60696_(this.f_62776_, p_62865_, $$9, p_62867_);
        }
        if (p_62866_.m_155947_()) {
            BlockEntity $$13 = this.m_5685_(p_62865_, EntityCreationType.CHECK);
            if ($$13 == null) {
                $$13 = ((EntityBlock)((Object)$$10)).m_142194_(p_62865_, p_62866_);
                if ($$13 != null) {
                    this.m_142170_($$13);
                }
            } else {
                $$13.m_155250_(p_62866_);
                this.m_156406_($$13);
            }
        }
        this.f_187603_ = true;
        return $$9;
    }

    @Override
    @Deprecated
    public void m_6286_(Entity p_62826_) {
    }

    @Nullable
    private BlockEntity m_62934_(BlockPos p_62935_) {
        BlockState $$1 = this.m_8055_(p_62935_);
        if (!$$1.m_155947_()) {
            return null;
        }
        return ((EntityBlock)((Object)$$1.m_60734_())).m_142194_(p_62935_, $$1);
    }

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_62912_) {
        return this.m_5685_(p_62912_, EntityCreationType.CHECK);
    }

    @Nullable
    public BlockEntity m_5685_(BlockPos p_62868_, EntityCreationType p_62869_) {
        BlockEntity $$4;
        CompoundTag $$3;
        BlockEntity $$2 = (BlockEntity)this.f_187610_.get(p_62868_);
        if ($$2 == null && ($$3 = (CompoundTag)this.f_187609_.remove(p_62868_)) != null && ($$4 = this.m_62870_(p_62868_, $$3)) != null) {
            return $$4;
        }
        if ($$2 == null) {
            if (p_62869_ == EntityCreationType.IMMEDIATE && ($$2 = this.m_62934_(p_62868_)) != null) {
                this.m_142170_($$2);
            }
        } else if ($$2.m_58901_()) {
            this.f_187610_.remove(p_62868_);
            return null;
        }
        return $$2;
    }

    public void m_142170_(BlockEntity p_156391_) {
        this.m_142169_(p_156391_);
        if (this.m_156370_()) {
            Level level = this.f_62776_;
            if (level instanceof ServerLevel) {
                ServerLevel $$1 = (ServerLevel)level;
                this.m_223415_(p_156391_, $$1);
            }
            this.m_156406_(p_156391_);
        }
    }

    private boolean m_156370_() {
        return this.f_62775_ || this.f_62776_.m_5776_();
    }

    boolean m_156410_(BlockPos p_156411_) {
        if (!this.f_62776_.m_6857_().m_61937_(p_156411_)) {
            return false;
        }
        Level level = this.f_62776_;
        if (level instanceof ServerLevel) {
            ServerLevel $$1 = (ServerLevel)level;
            return this.m_6708_().m_140114_(ChunkHolder.FullChunkStatus.TICKING) && $$1.m_143319_(ChunkPos.m_151388_(p_156411_));
        }
        return true;
    }

    @Override
    public void m_142169_(BlockEntity p_156374_) {
        BlockPos $$1 = p_156374_.m_58899_();
        if (!this.m_8055_($$1).m_155947_()) {
            return;
        }
        p_156374_.m_142339_(this.f_62776_);
        p_156374_.m_6339_();
        BlockEntity $$2 = this.f_187610_.put($$1.m_7949_(), p_156374_);
        if ($$2 != null && $$2 != p_156374_) {
            $$2.m_7651_();
        }
    }

    @Override
    @Nullable
    public CompoundTag m_8051_(BlockPos p_62932_) {
        BlockEntity $$1 = this.m_7702_(p_62932_);
        if ($$1 != null && !$$1.m_58901_()) {
            CompoundTag $$2 = $$1.m_187480_();
            $$2.m_128379_("keepPacked", false);
            return $$2;
        }
        CompoundTag $$3 = (CompoundTag)this.f_187609_.get(p_62932_);
        if ($$3 != null) {
            $$3 = $$3.m_6426_();
            $$3.m_128379_("keepPacked", true);
        }
        return $$3;
    }

    @Override
    public void m_8114_(BlockPos p_62919_) {
        BlockEntity $$1;
        if (this.m_156370_() && ($$1 = (BlockEntity)this.f_187610_.remove(p_62919_)) != null) {
            Level level = this.f_62776_;
            if (level instanceof ServerLevel) {
                ServerLevel $$2 = (ServerLevel)level;
                this.m_223412_($$1, $$2);
            }
            $$1.m_7651_();
        }
        this.m_156412_(p_62919_);
    }

    private <T extends BlockEntity> void m_223412_(T p_223413_, ServerLevel p_223414_) {
        GameEventListener $$3;
        Block $$2 = p_223413_.m_58900_().m_60734_();
        if ($$2 instanceof EntityBlock && ($$3 = ((EntityBlock)((Object)$$2)).m_214009_(p_223414_, p_223413_)) != null) {
            int $$4 = SectionPos.m_123171_(p_223413_.m_58899_().m_123342_());
            GameEventDispatcher $$5 = this.m_142336_($$4);
            $$5.m_142500_($$3);
            if ($$5.m_142086_()) {
                this.f_156363_.remove($$4);
            }
        }
    }

    private void m_156412_(BlockPos p_156413_) {
        RebindableTickingBlockEntityWrapper $$1 = this.f_156362_.remove(p_156413_);
        if ($$1 != null) {
            $$1.m_156449_(f_156361_);
        }
    }

    public void m_62952_() {
        if (this.f_62791_ != null) {
            this.f_62791_.m_196866_(this);
            this.f_62791_ = null;
        }
    }

    public boolean m_6430_() {
        return false;
    }

    public void m_187971_(FriendlyByteBuf p_187972_, CompoundTag p_187973_, Consumer<ClientboundLevelChunkPacketData.BlockEntityTagOutput> p_187974_) {
        this.m_187957_();
        for (LevelChunkSection $$3 : this.f_187612_) {
            $$3.m_63004_(p_187972_);
        }
        for (Heightmap.Types $$4 : Heightmap.Types.values()) {
            String $$5 = $$4.m_64294_();
            if (!p_187973_.m_128425_($$5, 12)) continue;
            this.m_6511_($$4, p_187973_.m_128467_($$5));
        }
        p_187974_.accept((p_187968_, p_187969_, p_187970_) -> {
            BlockEntity $$3 = this.m_5685_(p_187968_, EntityCreationType.IMMEDIATE);
            if ($$3 != null && p_187970_ != null && $$3.m_58903_() == p_187969_) {
                $$3.m_142466_(p_187970_);
            }
        });
    }

    public void m_62913_(boolean p_62914_) {
        this.f_62775_ = p_62914_;
    }

    public Level m_62953_() {
        return this.f_62776_;
    }

    public Map<BlockPos, BlockEntity> m_62954_() {
        return this.f_187610_;
    }

    @Override
    public Stream<BlockPos> m_6267_() {
        return StreamSupport.stream(BlockPos.m_121976_(this.f_187604_.m_45604_(), this.m_141937_(), this.f_187604_.m_45605_(), this.f_187604_.m_45608_(), this.m_151558_() - 1, this.f_187604_.m_45609_()).spliterator(), false).filter(p_187990_ -> this.m_8055_((BlockPos)p_187990_).m_60791_() != 0);
    }

    public void m_62812_() {
        ChunkPos $$0 = this.m_7697_();
        for (int $$1 = 0; $$1 < this.f_187602_.length; ++$$1) {
            if (this.f_187602_[$$1] == null) continue;
            for (Short $$2 : this.f_187602_[$$1]) {
                BlockPos $$3 = ProtoChunk.m_63227_($$2, this.m_151568_($$1), $$0);
                BlockState $$4 = this.m_8055_($$3);
                FluidState $$5 = $$4.m_60819_();
                if (!$$5.m_76178_()) {
                    $$5.m_76163_(this.f_62776_, $$3);
                }
                if ($$4.m_60734_() instanceof LiquidBlock) continue;
                BlockState $$6 = Block.m_49931_($$4, this.f_62776_, $$3);
                this.f_62776_.m_7731_($$3, $$6, 20);
            }
            this.f_187602_[$$1].clear();
        }
        for (BlockPos $$7 : ImmutableList.copyOf(this.f_187609_.keySet())) {
            this.m_7702_($$7);
        }
        this.f_187609_.clear();
        this.f_187606_.m_63341_(this);
    }

    @Nullable
    private BlockEntity m_62870_(BlockPos p_62871_, CompoundTag p_62872_) {
        BlockEntity $$5;
        BlockState $$2 = this.m_8055_(p_62871_);
        if ("DUMMY".equals(p_62872_.m_128461_("id"))) {
            if ($$2.m_155947_()) {
                BlockEntity $$3 = ((EntityBlock)((Object)$$2.m_60734_())).m_142194_(p_62871_, $$2);
            } else {
                Object $$4 = null;
                f_62771_.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", (Object)p_62871_, (Object)$$2);
            }
        } else {
            $$5 = BlockEntity.m_155241_(p_62871_, $$2, p_62872_);
        }
        if ($$5 != null) {
            $$5.m_142339_(this.f_62776_);
            this.m_142170_($$5);
        } else {
            f_62771_.warn("Tried to load a block entity for block {} but failed at location {}", (Object)$$2, (Object)p_62871_);
        }
        return $$5;
    }

    public void m_187985_(long p_187986_) {
        this.f_62784_.m_193171_(p_187986_);
        this.f_187943_.m_193171_(p_187986_);
    }

    public void m_187958_(ServerLevel p_187959_) {
        ((LevelTicks)p_187959_.m_183326_()).m_193231_(this.f_187604_, this.f_62784_);
        ((LevelTicks)p_187959_.m_183324_()).m_193231_(this.f_187604_, this.f_187943_);
    }

    public void m_187979_(ServerLevel p_187980_) {
        ((LevelTicks)p_187980_.m_183326_()).m_193229_(this.f_187604_);
        ((LevelTicks)p_187980_.m_183324_()).m_193229_(this.f_187604_);
    }

    @Override
    public ChunkStatus m_6415_() {
        return ChunkStatus.f_62326_;
    }

    public ChunkHolder.FullChunkStatus m_6708_() {
        if (this.f_62790_ == null) {
            return ChunkHolder.FullChunkStatus.BORDER;
        }
        return this.f_62790_.get();
    }

    public void m_62879_(Supplier<ChunkHolder.FullChunkStatus> p_62880_) {
        this.f_62790_ = p_62880_;
    }

    public void m_187957_() {
        this.f_187610_.values().forEach(BlockEntity::m_7651_);
        this.f_187610_.clear();
        this.f_156362_.values().forEach(p_187966_ -> p_187966_.m_156449_(f_156361_));
        this.f_156362_.clear();
    }

    public void m_156369_() {
        this.f_187610_.values().forEach(p_187988_ -> {
            Level $$1 = this.f_62776_;
            if ($$1 instanceof ServerLevel) {
                ServerLevel $$2 = (ServerLevel)$$1;
                this.m_223415_(p_187988_, $$2);
            }
            this.m_156406_(p_187988_);
        });
    }

    private <T extends BlockEntity> void m_223415_(T p_223416_, ServerLevel p_223417_) {
        GameEventListener $$3;
        Block $$2 = p_223416_.m_58900_().m_60734_();
        if ($$2 instanceof EntityBlock && ($$3 = ((EntityBlock)((Object)$$2)).m_214009_(p_223417_, p_223416_)) != null) {
            GameEventDispatcher $$4 = this.m_142336_(SectionPos.m_123171_(p_223416_.m_58899_().m_123342_()));
            $$4.m_142501_($$3);
        }
    }

    private <T extends BlockEntity> void m_156406_(T p_156407_) {
        BlockState $$1 = p_156407_.m_58900_();
        BlockEntityTicker<?> $$2 = $$1.m_155944_(this.f_62776_, p_156407_.m_58903_());
        if ($$2 == null) {
            this.m_156412_(p_156407_.m_58899_());
        } else {
            this.f_156362_.compute(p_156407_.m_58899_(), (p_187963_, p_187964_) -> {
                TickingBlockEntity $$4 = this.m_156375_(p_156407_, $$2);
                if (p_187964_ != null) {
                    p_187964_.m_156449_($$4);
                    return p_187964_;
                }
                if (this.m_156370_()) {
                    RebindableTickingBlockEntityWrapper $$5 = new RebindableTickingBlockEntityWrapper($$4);
                    this.f_62776_.m_151525_($$5);
                    return $$5;
                }
                return null;
            });
        }
    }

    private <T extends BlockEntity> TickingBlockEntity m_156375_(T p_156376_, BlockEntityTicker<T> p_156377_) {
        return new BoundTickingBlockEntity(this, p_156376_, p_156377_);
    }

    public boolean m_196863_() {
        return this.f_196848_;
    }

    public void m_196864_(boolean p_196865_) {
        this.f_196848_ = p_196865_;
    }

    @FunctionalInterface
    public static interface PostLoadProcessor {
        public void m_196866_(LevelChunk var1);
    }

    public static final class EntityCreationType
    extends Enum<EntityCreationType> {
        public static final /* enum */ EntityCreationType IMMEDIATE = new EntityCreationType();
        public static final /* enum */ EntityCreationType QUEUED = new EntityCreationType();
        public static final /* enum */ EntityCreationType CHECK = new EntityCreationType();
        private static final /* synthetic */ EntityCreationType[] $VALUES;

        public static EntityCreationType[] values() {
            return (EntityCreationType[])$VALUES.clone();
        }

        public static EntityCreationType valueOf(String p_62965_) {
            return Enum.valueOf(EntityCreationType.class, p_62965_);
        }

        private static /* synthetic */ EntityCreationType[] m_156442_() {
            return new EntityCreationType[]{IMMEDIATE, QUEUED, CHECK};
        }

        static {
            $VALUES = EntityCreationType.m_156442_();
        }
    }

    class RebindableTickingBlockEntityWrapper
    implements TickingBlockEntity {
        private TickingBlockEntity f_156444_;

        RebindableTickingBlockEntityWrapper(TickingBlockEntity p_156447_) {
            this.f_156444_ = p_156447_;
        }

        void m_156449_(TickingBlockEntity p_156450_) {
            this.f_156444_ = p_156450_;
        }

        @Override
        public void m_142224_() {
            this.f_156444_.m_142224_();
        }

        @Override
        public boolean m_142220_() {
            return this.f_156444_.m_142220_();
        }

        @Override
        public BlockPos m_142689_() {
            return this.f_156444_.m_142689_();
        }

        @Override
        public String m_142280_() {
            return this.f_156444_.m_142280_();
        }

        public String toString() {
            return this.f_156444_.toString() + " <wrapped>";
        }
    }

    static class BoundTickingBlockEntity<T extends BlockEntity>
    implements TickingBlockEntity {
        private final T f_156428_;
        private final BlockEntityTicker<T> f_156429_;
        private boolean f_156430_;
        final /* synthetic */ LevelChunk f_156427_;

        BoundTickingBlockEntity(T p_156433_, BlockEntityTicker<T> p_156434_) {
            this.f_156427_ = var1_1;
            this.f_156428_ = p_156433_;
            this.f_156429_ = p_156434_;
        }

        @Override
        public void m_142224_() {
            BlockPos $$0;
            if (!((BlockEntity)this.f_156428_).m_58901_() && ((BlockEntity)this.f_156428_).m_58898_() && this.f_156427_.m_156410_($$0 = ((BlockEntity)this.f_156428_).m_58899_())) {
                try {
                    ProfilerFiller $$1 = this.f_156427_.f_62776_.m_46473_();
                    $$1.m_6521_(this::m_142280_);
                    BlockState $$2 = this.f_156427_.m_8055_($$0);
                    if (((BlockEntity)this.f_156428_).m_58903_().m_155262_($$2)) {
                        this.f_156429_.m_155252_(this.f_156427_.f_62776_, ((BlockEntity)this.f_156428_).m_58899_(), $$2, this.f_156428_);
                        this.f_156430_ = false;
                    } else if (!this.f_156430_) {
                        this.f_156430_ = true;
                        f_62771_.warn("Block entity {} @ {} state {} invalid for ticking:", new Object[]{LogUtils.defer(this::m_142280_), LogUtils.defer(this::m_142689_), $$2});
                    }
                    $$1.m_7238_();
                }
                catch (Throwable $$3) {
                    CrashReport $$4 = CrashReport.m_127521_($$3, "Ticking block entity");
                    CrashReportCategory $$5 = $$4.m_127514_("Block entity being ticked");
                    ((BlockEntity)this.f_156428_).m_58886_($$5);
                    throw new ReportedException($$4);
                }
            }
        }

        @Override
        public boolean m_142220_() {
            return ((BlockEntity)this.f_156428_).m_58901_();
        }

        @Override
        public BlockPos m_142689_() {
            return ((BlockEntity)this.f_156428_).m_58899_();
        }

        @Override
        public String m_142280_() {
            return BlockEntityType.m_58954_(((BlockEntity)this.f_156428_).m_58903_()).toString();
        }

        public String toString() {
            return "Level ticker for " + this.m_142280_() + "@" + this.m_142689_();
        }
    }
}

