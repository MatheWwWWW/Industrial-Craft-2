/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.shorts.ShortOpenHashSet
 *  it.unimi.dsi.fastutil.shorts.ShortSet
 *  javax.annotation.Nullable
 */
package net.minecraft.server.level;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.DebugBuffer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ChunkHolder {
    public static final Either<ChunkAccess, ChunkLoadingFailure> f_139995_ = Either.right((Object)ChunkLoadingFailure.f_140101_);
    public static final CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> f_139996_ = CompletableFuture.completedFuture(f_139995_);
    public static final Either<LevelChunk, ChunkLoadingFailure> f_139997_ = Either.right((Object)ChunkLoadingFailure.f_140101_);
    private static final Either<ChunkAccess, ChunkLoadingFailure> f_203750_ = Either.right((Object)ChunkLoadingFailure.f_140101_);
    private static final CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> f_139998_ = CompletableFuture.completedFuture(f_139997_);
    private static final List<ChunkStatus> f_139999_ = ChunkStatus.m_62349_();
    private static final FullChunkStatus[] f_140000_ = FullChunkStatus.values();
    private static final int f_142982_ = 64;
    private final AtomicReferenceArray<CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>> f_140001_ = new AtomicReferenceArray(f_139999_.size());
    private final LevelHeightAccessor f_142983_;
    private volatile CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> f_140002_ = f_139998_;
    private volatile CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> f_140003_ = f_139998_;
    private volatile CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> f_140004_ = f_139998_;
    private CompletableFuture<ChunkAccess> f_140005_ = CompletableFuture.completedFuture(null);
    @Nullable
    private final DebugBuffer<ChunkSaveDebug> f_142984_ = null;
    private int f_140006_;
    private int f_140007_;
    private int f_140008_;
    final ChunkPos f_140009_;
    private boolean f_140010_;
    private final ShortSet[] f_140011_;
    private final BitSet f_140012_ = new BitSet();
    private final BitSet f_140013_ = new BitSet();
    private final LevelLightEngine f_140014_;
    private final LevelChangeListener f_140015_;
    private final PlayerProvider f_140016_;
    private boolean f_140017_;
    private boolean f_140018_;
    private CompletableFuture<Void> f_142981_ = CompletableFuture.completedFuture(null);

    public ChunkHolder(ChunkPos p_142986_, int p_142987_, LevelHeightAccessor p_142988_, LevelLightEngine p_142989_, LevelChangeListener p_142990_, PlayerProvider p_142991_) {
        this.f_140009_ = p_142986_;
        this.f_142983_ = p_142988_;
        this.f_140014_ = p_142989_;
        this.f_140015_ = p_142990_;
        this.f_140016_ = p_142991_;
        this.f_140007_ = this.f_140006_ = ChunkMap.f_140127_ + 1;
        this.f_140008_ = this.f_140006_;
        this.m_140027_(p_142987_);
        this.f_140011_ = new ShortSet[p_142988_.m_151559_()];
    }

    public CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> m_140047_(ChunkStatus p_140048_) {
        CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$1 = this.f_140001_.get(p_140048_.m_62445_());
        return $$1 == null ? f_139996_ : $$1;
    }

    public CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> m_140080_(ChunkStatus p_140081_) {
        if (ChunkHolder.m_140074_(this.f_140007_).m_62427_(p_140081_)) {
            return this.m_140047_(p_140081_);
        }
        return f_139996_;
    }

    public CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> m_140026_() {
        return this.f_140003_;
    }

    public CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> m_140073_() {
        return this.f_140004_;
    }

    public CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> m_140082_() {
        return this.f_140002_;
    }

    @Nullable
    public LevelChunk m_140085_() {
        CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> $$0 = this.m_140026_();
        Either $$1 = $$0.getNow(null);
        if ($$1 == null) {
            return null;
        }
        return $$1.left().orElse(null);
    }

    @Nullable
    public LevelChunk m_212234_() {
        CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> $$0 = this.m_140082_();
        Either $$1 = $$0.getNow(null);
        if ($$1 == null) {
            return null;
        }
        return $$1.left().orElse(null);
    }

    @Nullable
    public ChunkStatus m_140088_() {
        for (int $$0 = f_139999_.size() - 1; $$0 >= 0; --$$0) {
            ChunkStatus $$1 = f_139999_.get($$0);
            CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$2 = this.m_140047_($$1);
            if (!$$2.getNow(f_139995_).left().isPresent()) continue;
            return $$1;
        }
        return null;
    }

    @Nullable
    public ChunkAccess m_140089_() {
        for (int $$0 = f_139999_.size() - 1; $$0 >= 0; --$$0) {
            Optional $$3;
            ChunkStatus $$1 = f_139999_.get($$0);
            CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$2 = this.m_140047_($$1);
            if ($$2.isCompletedExceptionally() || !($$3 = $$2.getNow(f_139995_).left()).isPresent()) continue;
            return (ChunkAccess)$$3.get();
        }
        return null;
    }

    public CompletableFuture<ChunkAccess> m_140090_() {
        return this.f_140005_;
    }

    public void m_140056_(BlockPos p_140057_) {
        LevelChunk $$1 = this.m_140085_();
        if ($$1 == null) {
            return;
        }
        int $$2 = this.f_142983_.m_151564_(p_140057_.m_123342_());
        if (this.f_140011_[$$2] == null) {
            this.f_140010_ = true;
            this.f_140011_[$$2] = new ShortOpenHashSet();
        }
        this.f_140011_[$$2].add(SectionPos.m_123218_(p_140057_));
    }

    public void m_140036_(LightLayer p_140037_, int p_140038_) {
        Either $$2 = this.m_140080_(ChunkStatus.f_62322_).getNow(null);
        if ($$2 == null) {
            return;
        }
        ChunkAccess $$3 = $$2.left().orElse(null);
        if ($$3 == null) {
            return;
        }
        $$3.m_8092_(true);
        LevelChunk $$4 = this.m_140085_();
        if ($$4 == null) {
            return;
        }
        int $$5 = this.f_140014_.m_164447_();
        int $$6 = this.f_140014_.m_164448_();
        if (p_140038_ < $$5 || p_140038_ > $$6) {
            return;
        }
        int $$7 = p_140038_ - $$5;
        if (p_140037_ == LightLayer.SKY) {
            this.f_140013_.set($$7);
        } else {
            this.f_140012_.set($$7);
        }
    }

    public void m_140054_(LevelChunk p_140055_) {
        if (!this.f_140010_ && this.f_140013_.isEmpty() && this.f_140012_.isEmpty()) {
            return;
        }
        Level $$1 = p_140055_.m_62953_();
        int $$2 = 0;
        for (int $$3 = 0; $$3 < this.f_140011_.length; ++$$3) {
            $$2 += this.f_140011_[$$3] != null ? this.f_140011_[$$3].size() : 0;
        }
        this.f_140018_ |= $$2 >= 64;
        if (!this.f_140013_.isEmpty() || !this.f_140012_.isEmpty()) {
            this.m_140063_(new ClientboundLightUpdatePacket(p_140055_.m_7697_(), this.f_140014_, this.f_140013_, this.f_140012_, true), !this.f_140018_);
            this.f_140013_.clear();
            this.f_140012_.clear();
        }
        for (int $$4 = 0; $$4 < this.f_140011_.length; ++$$4) {
            ShortSet $$5 = this.f_140011_[$$4];
            if ($$5 == null) continue;
            int $$6 = this.f_142983_.m_151568_($$4);
            SectionPos $$7 = SectionPos.m_123196_(p_140055_.m_7697_(), $$6);
            if ($$5.size() == 1) {
                BlockPos $$8 = $$7.m_123245_($$5.iterator().nextShort());
                BlockState $$9 = $$1.m_8055_($$8);
                this.m_140063_(new ClientboundBlockUpdatePacket($$8, $$9), false);
                this.m_140032_($$1, $$8, $$9);
            } else {
                LevelChunkSection $$10 = p_140055_.m_183278_($$4);
                ClientboundSectionBlocksUpdatePacket $$11 = new ClientboundSectionBlocksUpdatePacket($$7, $$5, $$10, this.f_140018_);
                this.m_140063_($$11, false);
                $$11.m_132992_((p_140078_, p_140079_) -> this.m_140032_($$1, (BlockPos)p_140078_, (BlockState)p_140079_));
            }
            this.f_140011_[$$4] = null;
        }
        this.f_140010_ = false;
    }

    private void m_140032_(Level p_140033_, BlockPos p_140034_, BlockState p_140035_) {
        if (p_140035_.m_155947_()) {
            this.m_140029_(p_140033_, p_140034_);
        }
    }

    private void m_140029_(Level p_140030_, BlockPos p_140031_) {
        Packet<ClientGamePacketListener> $$3;
        BlockEntity $$2 = p_140030_.m_7702_(p_140031_);
        if ($$2 != null && ($$3 = $$2.m_58483_()) != null) {
            this.m_140063_($$3, false);
        }
    }

    private void m_140063_(Packet<?> p_140064_, boolean p_140065_) {
        this.f_140016_.m_183262_(this.f_140009_, p_140065_).forEach(p_140062_ -> p_140062_.f_8906_.m_9829_(p_140064_));
    }

    public CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> m_140049_(ChunkStatus p_140050_, ChunkMap p_140051_) {
        int $$2 = p_140050_.m_62445_();
        CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$3 = this.f_140001_.get($$2);
        if ($$3 != null) {
            Either<ChunkAccess, ChunkLoadingFailure> $$4 = $$3.getNow(f_203750_);
            if ($$4 == null) {
                String $$5 = "value in future for status: " + p_140050_ + " was incorrectly set to null at chunk: " + this.f_140009_;
                throw p_140051_.m_203751_(new IllegalStateException("null value previously set for chunk status"), $$5);
            }
            if ($$4 == f_203750_ || $$4.right().isEmpty()) {
                return $$3;
            }
        }
        if (ChunkHolder.m_140074_(this.f_140007_).m_62427_(p_140050_)) {
            CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$6 = p_140051_.m_140292_(this, p_140050_);
            this.m_143017_($$6, "schedule " + p_140050_);
            this.f_140001_.set($$2, $$6);
            return $$6;
        }
        return $$3 == null ? f_139996_ : $$3;
    }

    protected void m_200416_(String p_200417_, CompletableFuture<?> p_200418_) {
        if (this.f_142984_ != null) {
            this.f_142984_.m_144625_(new ChunkSaveDebug(Thread.currentThread(), p_200418_, p_200417_));
        }
        this.f_140005_ = this.f_140005_.thenCombine(p_200418_, (p_200414_, p_200415_) -> p_200414_);
    }

    private void m_143017_(CompletableFuture<? extends Either<? extends ChunkAccess, ChunkLoadingFailure>> p_143018_, String p_143019_) {
        if (this.f_142984_ != null) {
            this.f_142984_.m_144625_(new ChunkSaveDebug(Thread.currentThread(), p_143018_, p_143019_));
        }
        this.f_140005_ = this.f_140005_.thenCombine(p_143018_, (p_200411_, p_200412_) -> (ChunkAccess)p_200412_.map(p_200406_ -> p_200406_, p_200409_ -> p_200411_));
    }

    public FullChunkStatus m_140091_() {
        return ChunkHolder.m_140083_(this.f_140007_);
    }

    public ChunkPos m_140092_() {
        return this.f_140009_;
    }

    public int m_140093_() {
        return this.f_140007_;
    }

    public int m_140094_() {
        return this.f_140008_;
    }

    private void m_140086_(int p_140087_) {
        this.f_140008_ = p_140087_;
    }

    public void m_140027_(int p_140028_) {
        this.f_140007_ = p_140028_;
    }

    private void m_142998_(ChunkMap p_142999_, CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> p_143000_, Executor p_143001_, FullChunkStatus p_143002_) {
        this.f_142981_.cancel(false);
        CompletableFuture $$4 = new CompletableFuture();
        $$4.thenRunAsync(() -> p_142999_.m_143075_(this.f_140009_, p_143002_), p_143001_);
        this.f_142981_ = $$4;
        p_143000_.thenAccept(p_200421_ -> p_200421_.ifLeft(p_200424_ -> $$4.complete(null)));
    }

    private void m_142992_(ChunkMap p_142993_, FullChunkStatus p_142994_) {
        this.f_142981_.cancel(false);
        p_142993_.m_143075_(this.f_140009_, p_142994_);
    }

    protected void m_143003_(ChunkMap p_143004_, Executor p_143005_) {
        ChunkStatus $$2 = ChunkHolder.m_140074_(this.f_140006_);
        ChunkStatus $$3 = ChunkHolder.m_140074_(this.f_140007_);
        boolean $$4 = this.f_140006_ <= ChunkMap.f_140127_;
        boolean $$5 = this.f_140007_ <= ChunkMap.f_140127_;
        FullChunkStatus $$6 = ChunkHolder.m_140083_(this.f_140006_);
        FullChunkStatus $$7 = ChunkHolder.m_140083_(this.f_140007_);
        if ($$4) {
            int $$9;
            Either $$8 = Either.right((Object)new ChunkLoadingFailure(){

                public String toString() {
                    return "Unloaded ticket level " + ChunkHolder.this.f_140009_;
                }
            });
            int n = $$9 = $$5 ? $$3.m_62445_() + 1 : 0;
            while ($$9 <= $$2.m_62445_()) {
                CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$10 = this.f_140001_.get($$9);
                if ($$10 == null) {
                    this.f_140001_.set($$9, CompletableFuture.completedFuture($$8));
                }
                ++$$9;
            }
        }
        boolean $$11 = $$6.m_140114_(FullChunkStatus.BORDER);
        boolean $$12 = $$7.m_140114_(FullChunkStatus.BORDER);
        this.f_140017_ |= $$12;
        if (!$$11 && $$12) {
            this.f_140002_ = p_143004_.m_143109_(this);
            this.m_142998_(p_143004_, this.f_140002_, p_143005_, FullChunkStatus.BORDER);
            this.m_143017_(this.f_140002_, "full");
        }
        if ($$11 && !$$12) {
            this.f_140002_.complete(f_139997_);
            this.f_140002_ = f_139998_;
        }
        boolean $$13 = $$6.m_140114_(FullChunkStatus.TICKING);
        boolean $$14 = $$7.m_140114_(FullChunkStatus.TICKING);
        if (!$$13 && $$14) {
            this.f_140003_ = p_143004_.m_143053_(this);
            this.m_142998_(p_143004_, this.f_140003_, p_143005_, FullChunkStatus.TICKING);
            this.m_143017_(this.f_140003_, "ticking");
        }
        if ($$13 && !$$14) {
            this.f_140003_.complete(f_139997_);
            this.f_140003_ = f_139998_;
        }
        boolean $$15 = $$6.m_140114_(FullChunkStatus.ENTITY_TICKING);
        boolean $$16 = $$7.m_140114_(FullChunkStatus.ENTITY_TICKING);
        if (!$$15 && $$16) {
            if (this.f_140004_ != f_139998_) {
                throw Util.m_137570_(new IllegalStateException());
            }
            this.f_140004_ = p_143004_.m_143117_(this.f_140009_);
            this.m_142998_(p_143004_, this.f_140004_, p_143005_, FullChunkStatus.ENTITY_TICKING);
            this.m_143017_(this.f_140004_, "entity ticking");
        }
        if ($$15 && !$$16) {
            this.f_140004_.complete(f_139997_);
            this.f_140004_ = f_139998_;
        }
        if (!$$7.m_140114_($$6)) {
            this.m_142992_(p_143004_, $$7);
        }
        this.f_140015_.m_6250_(this.f_140009_, this::m_140094_, this.f_140007_, this::m_140086_);
        this.f_140006_ = this.f_140007_;
    }

    public static ChunkStatus m_140074_(int p_140075_) {
        if (p_140075_ < 33) {
            return ChunkStatus.f_62326_;
        }
        return ChunkStatus.m_156185_(p_140075_ - 33);
    }

    public static FullChunkStatus m_140083_(int p_140084_) {
        return f_140000_[Mth.m_14045_(33 - p_140084_ + 1, 0, f_140000_.length - 1)];
    }

    public boolean m_140095_() {
        return this.f_140017_;
    }

    public void m_140096_() {
        this.f_140017_ = ChunkHolder.m_140083_(this.f_140007_).m_140114_(FullChunkStatus.BORDER);
    }

    public void m_140052_(ImposterProtoChunk p_140053_) {
        for (int $$1 = 0; $$1 < this.f_140001_.length(); ++$$1) {
            Optional $$3;
            CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$2 = this.f_140001_.get($$1);
            if ($$2 == null || ($$3 = $$2.getNow(f_139995_).left()).isEmpty() || !($$3.get() instanceof ProtoChunk)) continue;
            this.f_140001_.set($$1, CompletableFuture.completedFuture(Either.left((Object)p_140053_)));
        }
        this.m_143017_(CompletableFuture.completedFuture(Either.left((Object)p_140053_.m_62768_())), "replaceProto");
    }

    public List<Pair<ChunkStatus, CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>>> m_202980_() {
        ArrayList<Pair<ChunkStatus, CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>>> $$0 = new ArrayList<Pair<ChunkStatus, CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>>>();
        for (int $$1 = 0; $$1 < f_139999_.size(); ++$$1) {
            $$0.add((Pair<ChunkStatus, CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>>)Pair.of((Object)f_139999_.get($$1), this.f_140001_.get($$1)));
        }
        return $$0;
    }

    @FunctionalInterface
    public static interface LevelChangeListener {
        public void m_6250_(ChunkPos var1, IntSupplier var2, int var3, IntConsumer var4);
    }

    public static interface PlayerProvider {
        public List<ServerPlayer> m_183262_(ChunkPos var1, boolean var2);
    }

    static final class ChunkSaveDebug {
        private final Thread f_143023_;
        private final CompletableFuture<?> f_143024_;
        private final String f_143025_;

        ChunkSaveDebug(Thread p_143027_, CompletableFuture<?> p_143028_, String p_143029_) {
            this.f_143023_ = p_143027_;
            this.f_143024_ = p_143028_;
            this.f_143025_ = p_143029_;
        }
    }

    public static final class FullChunkStatus
    extends Enum<FullChunkStatus> {
        public static final /* enum */ FullChunkStatus INACCESSIBLE = new FullChunkStatus();
        public static final /* enum */ FullChunkStatus BORDER = new FullChunkStatus();
        public static final /* enum */ FullChunkStatus TICKING = new FullChunkStatus();
        public static final /* enum */ FullChunkStatus ENTITY_TICKING = new FullChunkStatus();
        private static final /* synthetic */ FullChunkStatus[] $VALUES;

        public static FullChunkStatus[] values() {
            return (FullChunkStatus[])$VALUES.clone();
        }

        public static FullChunkStatus valueOf(String p_140117_) {
            return Enum.valueOf(FullChunkStatus.class, p_140117_);
        }

        public boolean m_140114_(FullChunkStatus p_140115_) {
            return this.ordinal() >= p_140115_.ordinal();
        }

        private static /* synthetic */ FullChunkStatus[] m_143030_() {
            return new FullChunkStatus[]{INACCESSIBLE, BORDER, TICKING, ENTITY_TICKING};
        }

        static {
            $VALUES = FullChunkStatus.m_143030_();
        }
    }

    public static interface ChunkLoadingFailure {
        public static final ChunkLoadingFailure f_140101_ = new ChunkLoadingFailure(){

            public String toString() {
                return "UNLOADED";
            }
        };
    }
}

