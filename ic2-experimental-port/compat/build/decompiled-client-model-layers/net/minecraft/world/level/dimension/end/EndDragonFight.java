/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ContiguousSet
 *  com.google.common.collect.DiscreteDomain
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Range
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.end.DragonRespawnAnimation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public class EndDragonFight {
    private static final Logger f_64058_ = LogUtils.getLogger();
    private static final int f_156737_ = 1200;
    private static final int f_156738_ = 100;
    private static final int f_156739_ = 20;
    private static final int f_156740_ = 8;
    public static final int f_156735_ = 9;
    private static final int f_156741_ = 20;
    private static final int f_156742_ = 96;
    public static final int f_156736_ = 128;
    private static final Predicate<Entity> f_64059_ = EntitySelector.f_20402_.and(EntitySelector.m_20410_(0.0, 128.0, 0.0, 192.0));
    private final ServerBossEvent f_64060_ = (ServerBossEvent)new ServerBossEvent(Component.m_237115_("entity.minecraft.ender_dragon"), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS).m_7005_(true).m_7006_(true);
    private final ServerLevel f_64061_;
    private final ObjectArrayList<Integer> f_64062_ = new ObjectArrayList();
    private final BlockPattern f_64063_;
    private int f_64064_;
    private int f_64065_;
    private int f_64066_;
    private int f_64067_;
    private boolean f_64068_;
    private boolean f_64069_;
    @Nullable
    private UUID f_64070_;
    private boolean f_64071_ = true;
    @Nullable
    private BlockPos f_64072_;
    @Nullable
    private DragonRespawnAnimation f_64073_;
    private int f_64074_;
    @Nullable
    private List<EndCrystal> f_64075_;

    public EndDragonFight(ServerLevel p_64078_, long p_64079_, CompoundTag p_64080_) {
        this.f_64061_ = p_64078_;
        if (p_64080_.m_128441_("NeedsStateScanning")) {
            this.f_64071_ = p_64080_.m_128471_("NeedsStateScanning");
        }
        if (p_64080_.m_128425_("DragonKilled", 99)) {
            if (p_64080_.m_128403_("Dragon")) {
                this.f_64070_ = p_64080_.m_128342_("Dragon");
            }
            this.f_64068_ = p_64080_.m_128471_("DragonKilled");
            this.f_64069_ = p_64080_.m_128471_("PreviouslyKilled");
            if (p_64080_.m_128471_("IsRespawning")) {
                this.f_64073_ = DragonRespawnAnimation.START;
            }
            if (p_64080_.m_128425_("ExitPortalLocation", 10)) {
                this.f_64072_ = NbtUtils.m_129239_(p_64080_.m_128469_("ExitPortalLocation"));
            }
        } else {
            this.f_64068_ = true;
            this.f_64069_ = true;
        }
        if (p_64080_.m_128425_("Gateways", 9)) {
            ListTag $$3 = p_64080_.m_128437_("Gateways", 3);
            for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
                this.f_64062_.add((Object)$$3.m_128763_($$4));
            }
        } else {
            this.f_64062_.addAll((Collection)ContiguousSet.create((Range)Range.closedOpen((Comparable)Integer.valueOf(0), (Comparable)Integer.valueOf(20)), (DiscreteDomain)DiscreteDomain.integers()));
            Util.m_214673_(this.f_64062_, RandomSource.m_216335_(p_64079_));
        }
        this.f_64063_ = BlockPatternBuilder.m_61243_().m_61247_("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").m_61247_("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").m_61247_("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").m_61247_("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").m_61247_("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").m_61244_('#', BlockInWorld.m_61169_(BlockPredicate.m_61275_(Blocks.f_50752_))).m_61249_();
    }

    public CompoundTag m_64081_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128379_("NeedsStateScanning", this.f_64071_);
        if (this.f_64070_ != null) {
            $$0.m_128362_("Dragon", this.f_64070_);
        }
        $$0.m_128379_("DragonKilled", this.f_64068_);
        $$0.m_128379_("PreviouslyKilled", this.f_64069_);
        if (this.f_64072_ != null) {
            $$0.m_128365_("ExitPortalLocation", NbtUtils.m_129224_(this.f_64072_));
        }
        ListTag $$1 = new ListTag();
        ObjectListIterator objectListIterator = this.f_64062_.iterator();
        while (objectListIterator.hasNext()) {
            int $$2 = (Integer)objectListIterator.next();
            $$1.add(IntTag.m_128679_($$2));
        }
        $$0.m_128365_("Gateways", $$1);
        return $$0;
    }

    public void m_64095_() {
        this.f_64060_.m_8321_(!this.f_64068_);
        if (++this.f_64067_ >= 20) {
            this.m_64107_();
            this.f_64067_ = 0;
        }
        if (!this.f_64060_.m_8324_().isEmpty()) {
            this.f_64061_.m_7726_().m_8387_(TicketType.f_9443_, new ChunkPos(0, 0), 9, Unit.INSTANCE);
            boolean $$0 = this.m_64106_();
            if (this.f_64071_ && $$0) {
                this.m_64102_();
                this.f_64071_ = false;
            }
            if (this.f_64073_ != null) {
                if (this.f_64075_ == null && $$0) {
                    this.f_64073_ = null;
                    this.m_64100_();
                }
                this.f_64073_.m_6363_(this.f_64061_, this, this.f_64075_, this.f_64074_++, this.f_64072_);
            }
            if (!this.f_64068_) {
                if ((this.f_64070_ == null || ++this.f_64064_ >= 1200) && $$0) {
                    this.m_64103_();
                    this.f_64064_ = 0;
                }
                if (++this.f_64066_ >= 100 && $$0) {
                    this.m_64108_();
                    this.f_64066_ = 0;
                }
            }
        } else {
            this.f_64061_.m_7726_().m_8438_(TicketType.f_9443_, new ChunkPos(0, 0), 9, Unit.INSTANCE);
        }
    }

    private void m_64102_() {
        f_64058_.info("Scanning for legacy world dragon fight...");
        boolean $$0 = this.m_64104_();
        if ($$0) {
            f_64058_.info("Found that the dragon has been killed in this world already.");
            this.f_64069_ = true;
        } else {
            f_64058_.info("Found that the dragon has not yet been killed in this world.");
            this.f_64069_ = false;
            if (this.m_64105_() == null) {
                this.m_64093_(false);
            }
        }
        List<? extends EnderDragon> $$1 = this.f_64061_.m_8857_();
        if ($$1.isEmpty()) {
            this.f_64068_ = true;
        } else {
            EnderDragon $$2 = $$1.get(0);
            this.f_64070_ = $$2.m_20148_();
            f_64058_.info("Found that there's a dragon still alive ({})", (Object)$$2);
            this.f_64068_ = false;
            if (!$$0) {
                f_64058_.info("But we didn't have a portal, let's remove it.");
                $$2.m_146870_();
                this.f_64070_ = null;
            }
        }
        if (!this.f_64069_ && this.f_64068_) {
            this.f_64068_ = false;
        }
    }

    private void m_64103_() {
        List<? extends EnderDragon> $$0 = this.f_64061_.m_8857_();
        if ($$0.isEmpty()) {
            f_64058_.debug("Haven't seen the dragon, respawning it");
            this.m_64110_();
        } else {
            f_64058_.debug("Haven't seen our dragon, but found another one to use.");
            this.f_64070_ = $$0.get(0).m_20148_();
        }
    }

    protected void m_64087_(DragonRespawnAnimation p_64088_) {
        if (this.f_64073_ == null) {
            throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
        }
        this.f_64074_ = 0;
        if (p_64088_ == DragonRespawnAnimation.END) {
            this.f_64073_ = null;
            this.f_64068_ = false;
            EnderDragon $$1 = this.m_64110_();
            for (ServerPlayer $$2 : this.f_64060_.m_8324_()) {
                CriteriaTriggers.f_10580_.m_68256_($$2, $$1);
            }
        } else {
            this.f_64073_ = p_64088_;
        }
    }

    private boolean m_64104_() {
        for (int $$0 = -8; $$0 <= 8; ++$$0) {
            for (int $$1 = -8; $$1 <= 8; ++$$1) {
                LevelChunk $$2 = this.f_64061_.m_6325_($$0, $$1);
                for (BlockEntity $$3 : $$2.m_62954_().values()) {
                    if (!($$3 instanceof TheEndPortalBlockEntity)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    private BlockPattern.BlockPatternMatch m_64105_() {
        int $$6;
        for (int $$0 = -8; $$0 <= 8; ++$$0) {
            for (int $$1 = -8; $$1 <= 8; ++$$1) {
                LevelChunk $$2 = this.f_64061_.m_6325_($$0, $$1);
                for (BlockEntity $$3 : $$2.m_62954_().values()) {
                    BlockPattern.BlockPatternMatch $$4;
                    if (!($$3 instanceof TheEndPortalBlockEntity) || ($$4 = this.f_64063_.m_61184_(this.f_64061_, $$3.m_58899_())) == null) continue;
                    BlockPos $$5 = $$4.m_61229_(3, 3, 3).m_61176_();
                    if (this.f_64072_ == null) {
                        this.f_64072_ = $$5;
                    }
                    return $$4;
                }
            }
        }
        for (int $$7 = $$6 = this.f_64061_.m_5452_(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.f_65714_).m_123342_(); $$7 >= this.f_64061_.m_141937_(); --$$7) {
            BlockPattern.BlockPatternMatch $$8 = this.f_64063_.m_61184_(this.f_64061_, new BlockPos(EndPodiumFeature.f_65714_.m_123341_(), $$7, EndPodiumFeature.f_65714_.m_123343_()));
            if ($$8 == null) continue;
            if (this.f_64072_ == null) {
                this.f_64072_ = $$8.m_61229_(3, 3, 3).m_61176_();
            }
            return $$8;
        }
        return null;
    }

    private boolean m_64106_() {
        for (int $$0 = -8; $$0 <= 8; ++$$0) {
            for (int $$1 = 8; $$1 <= 8; ++$$1) {
                ChunkAccess $$2 = this.f_64061_.m_6522_($$0, $$1, ChunkStatus.f_62326_, false);
                if (!($$2 instanceof LevelChunk)) {
                    return false;
                }
                ChunkHolder.FullChunkStatus $$3 = ((LevelChunk)$$2).m_6708_();
                if ($$3.m_140114_(ChunkHolder.FullChunkStatus.TICKING)) continue;
                return false;
            }
        }
        return true;
    }

    private void m_64107_() {
        HashSet $$0 = Sets.newHashSet();
        for (ServerPlayer $$1 : this.f_64061_.m_8795_(f_64059_)) {
            this.f_64060_.m_6543_($$1);
            $$0.add($$1);
        }
        HashSet $$2 = Sets.newHashSet(this.f_64060_.m_8324_());
        $$2.removeAll($$0);
        for (ServerPlayer $$3 : $$2) {
            this.f_64060_.m_6539_($$3);
        }
    }

    private void m_64108_() {
        this.f_64066_ = 0;
        this.f_64065_ = 0;
        for (SpikeFeature.EndSpike $$0 : SpikeFeature.m_66858_(this.f_64061_)) {
            this.f_64065_ += this.f_64061_.m_45976_(EndCrystal.class, $$0.m_66905_()).size();
        }
        f_64058_.debug("Found {} end crystals still alive", (Object)this.f_64065_);
    }

    public void m_64085_(EnderDragon p_64086_) {
        if (p_64086_.m_20148_().equals(this.f_64070_)) {
            this.f_64060_.m_142711_(0.0f);
            this.f_64060_.m_8321_(false);
            this.m_64093_(true);
            this.m_64109_();
            if (!this.f_64069_) {
                this.f_64061_.m_46597_(this.f_64061_.m_5452_(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.f_65714_), Blocks.f_50260_.m_49966_());
            }
            this.f_64069_ = true;
            this.f_64068_ = true;
        }
    }

    private void m_64109_() {
        if (this.f_64062_.isEmpty()) {
            return;
        }
        int $$0 = (Integer)this.f_64062_.remove(this.f_64062_.size() - 1);
        int $$1 = Mth.m_14107_(96.0 * Math.cos(2.0 * (-Math.PI + 0.15707963267948966 * (double)$$0)));
        int $$2 = Mth.m_14107_(96.0 * Math.sin(2.0 * (-Math.PI + 0.15707963267948966 * (double)$$0)));
        this.m_64089_(new BlockPos($$1, 75, $$2));
    }

    private void m_64089_(BlockPos p_64090_) {
        this.f_64061_.m_46796_(3000, p_64090_, 0);
        EndFeatures.f_194984_.m_203334_().m_224953_(this.f_64061_, this.f_64061_.m_7726_().m_8481_(), RandomSource.m_216327_(), p_64090_);
    }

    private void m_64093_(boolean p_64094_) {
        EndPodiumFeature $$1 = new EndPodiumFeature(p_64094_);
        if (this.f_64072_ == null) {
            this.f_64072_ = this.f_64061_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_).m_7495_();
            while (this.f_64061_.m_8055_(this.f_64072_).m_60713_(Blocks.f_50752_) && this.f_64072_.m_123342_() > this.f_64061_.m_5736_()) {
                this.f_64072_ = this.f_64072_.m_7495_();
            }
        }
        $$1.m_225028_(FeatureConfiguration.f_67737_, this.f_64061_, this.f_64061_.m_7726_().m_8481_(), RandomSource.m_216327_(), this.f_64072_);
    }

    private EnderDragon m_64110_() {
        this.f_64061_.m_46745_(new BlockPos(0, 128, 0));
        EnderDragon $$0 = EntityType.f_20565_.m_20615_(this.f_64061_);
        $$0.m_31157_().m_31416_(EnderDragonPhase.f_31377_);
        $$0.m_7678_(0.0, 128.0, 0.0, this.f_64061_.f_46441_.m_188501_() * 360.0f, 0.0f);
        this.f_64061_.m_7967_($$0);
        this.f_64070_ = $$0.m_20148_();
        return $$0;
    }

    public void m_64096_(EnderDragon p_64097_) {
        if (p_64097_.m_20148_().equals(this.f_64070_)) {
            this.f_64060_.m_142711_(p_64097_.m_21223_() / p_64097_.m_21233_());
            this.f_64064_ = 0;
            if (p_64097_.m_8077_()) {
                this.f_64060_.m_6456_(p_64097_.m_5446_());
            }
        }
    }

    public int m_64098_() {
        return this.f_64065_;
    }

    public void m_64082_(EndCrystal p_64083_, DamageSource p_64084_) {
        if (this.f_64073_ != null && this.f_64075_.contains(p_64083_)) {
            f_64058_.debug("Aborting respawn sequence");
            this.f_64073_ = null;
            this.f_64074_ = 0;
            this.m_64101_();
            this.m_64093_(true);
        } else {
            this.m_64108_();
            Entity $$2 = this.f_64061_.m_8791_(this.f_64070_);
            if ($$2 instanceof EnderDragon) {
                ((EnderDragon)$$2).m_31124_(p_64083_, p_64083_.m_20183_(), p_64084_);
            }
        }
    }

    public boolean m_64099_() {
        return this.f_64069_;
    }

    public void m_64100_() {
        if (this.f_64068_ && this.f_64073_ == null) {
            BlockPos $$0 = this.f_64072_;
            if ($$0 == null) {
                f_64058_.debug("Tried to respawn, but need to find the portal first.");
                BlockPattern.BlockPatternMatch $$1 = this.m_64105_();
                if ($$1 == null) {
                    f_64058_.debug("Couldn't find a portal, so we made one.");
                    this.m_64093_(true);
                } else {
                    f_64058_.debug("Found the exit portal & saved its location for next time.");
                }
                $$0 = this.f_64072_;
            }
            ArrayList $$2 = Lists.newArrayList();
            BlockPos $$3 = $$0.m_6630_(1);
            for (Direction $$4 : Direction.Plane.HORIZONTAL) {
                List<EndCrystal> $$5 = this.f_64061_.m_45976_(EndCrystal.class, new AABB($$3.m_5484_($$4, 2)));
                if ($$5.isEmpty()) {
                    return;
                }
                $$2.addAll($$5);
            }
            f_64058_.debug("Found all crystals, respawning dragon.");
            this.m_64091_($$2);
        }
    }

    private void m_64091_(List<EndCrystal> p_64092_) {
        if (this.f_64068_ && this.f_64073_ == null) {
            BlockPattern.BlockPatternMatch $$1 = this.m_64105_();
            while ($$1 != null) {
                for (int $$2 = 0; $$2 < this.f_64063_.m_61203_(); ++$$2) {
                    for (int $$3 = 0; $$3 < this.f_64063_.m_61202_(); ++$$3) {
                        for (int $$4 = 0; $$4 < this.f_64063_.m_61183_(); ++$$4) {
                            BlockInWorld $$5 = $$1.m_61229_($$2, $$3, $$4);
                            if (!$$5.m_61168_().m_60713_(Blocks.f_50752_) && !$$5.m_61168_().m_60713_(Blocks.f_50257_)) continue;
                            this.f_64061_.m_46597_($$5.m_61176_(), Blocks.f_50259_.m_49966_());
                        }
                    }
                }
                $$1 = this.m_64105_();
            }
            this.f_64073_ = DragonRespawnAnimation.START;
            this.f_64074_ = 0;
            this.m_64093_(false);
            this.f_64075_ = p_64092_;
        }
    }

    public void m_64101_() {
        for (SpikeFeature.EndSpike $$0 : SpikeFeature.m_66858_(this.f_64061_)) {
            List<EndCrystal> $$1 = this.f_64061_.m_45976_(EndCrystal.class, $$0.m_66905_());
            for (EndCrystal $$2 : $$1) {
                $$2.m_20331_(false);
                $$2.m_31052_(null);
            }
        }
    }
}

