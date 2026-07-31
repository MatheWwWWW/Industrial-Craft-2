/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LongJumpToRandomPos<E extends Mob>
extends Behavior<E> {
    protected static final int f_147623_ = 20;
    private static final int f_147624_ = 40;
    protected static final int f_147625_ = 8;
    private static final int f_147622_ = 200;
    private static final List<Integer> f_217289_ = Lists.newArrayList((Object[])new Integer[]{65, 70, 75, 80});
    private final UniformInt f_147626_;
    protected final int f_147627_;
    protected final int f_147628_;
    protected final float f_147629_;
    protected List<PossibleJump> f_147630_ = Lists.newArrayList();
    protected Optional<Vec3> f_147631_ = Optional.empty();
    @Nullable
    protected Vec3 f_147632_;
    protected int f_147633_;
    protected long f_147634_;
    private Function<E, SoundEvent> f_147635_;
    private final Predicate<BlockState> f_217290_;

    public LongJumpToRandomPos(UniformInt p_147637_, int p_147638_, int p_147639_, float p_147640_, Function<E, SoundEvent> p_147641_) {
        this(p_147637_, p_147638_, p_147639_, p_147640_, p_147641_, p_217311_ -> false);
    }

    public LongJumpToRandomPos(UniformInt p_217293_, int p_217294_, int p_217295_, float p_217296_, Function<E, SoundEvent> p_217297_, Predicate<BlockState> p_217298_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_148199_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_148200_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), 200);
        this.f_147626_ = p_217293_;
        this.f_147627_ = p_217294_;
        this.f_147628_ = p_217295_;
        this.f_147629_ = p_217296_;
        this.f_147635_ = p_217297_;
        this.f_217290_ = p_217298_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_147650_, Mob p_147651_) {
        boolean $$2;
        boolean bl = $$2 = p_147651_.m_20096_() && !p_147651_.m_20069_() && !p_147651_.m_20077_() && !p_147650_.m_8055_(p_147651_.m_20183_()).m_60713_(Blocks.f_50719_);
        if (!$$2) {
            p_147651_.m_6274_().m_21879_(MemoryModuleType.f_148199_, this.f_147626_.m_214085_(p_147650_.f_46441_) / 2);
        }
        return $$2;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_147653_, Mob p_147654_, long p_147655_) {
        boolean $$3;
        boolean bl = $$3 = this.f_147631_.isPresent() && this.f_147631_.get().equals(p_147654_.m_20182_()) && this.f_147633_ > 0 && !p_147654_.m_20072_() && (this.f_147632_ != null || !this.f_147630_.isEmpty());
        if (!$$3 && p_147654_.m_6274_().m_21952_(MemoryModuleType.f_148200_).isEmpty()) {
            p_147654_.m_6274_().m_21879_(MemoryModuleType.f_148199_, this.f_147626_.m_214085_(p_147653_.f_46441_) / 2);
            p_147654_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
        }
        return $$3;
    }

    @Override
    protected void m_6735_(ServerLevel p_147676_, E p_147677_, long p_147678_) {
        this.f_147632_ = null;
        this.f_147633_ = 20;
        this.f_147631_ = Optional.of(((Entity)p_147677_).m_20182_());
        BlockPos $$3 = ((Entity)p_147677_).m_20183_();
        int $$4 = $$3.m_123341_();
        int $$5 = $$3.m_123342_();
        int $$6 = $$3.m_123343_();
        this.f_147630_ = BlockPos.m_121886_($$4 - this.f_147628_, $$5 - this.f_147627_, $$6 - this.f_147628_, $$4 + this.f_147628_, $$5 + this.f_147627_, $$6 + this.f_147628_).filter(p_217317_ -> !p_217317_.equals($$3)).map(p_217314_ -> new PossibleJump(p_217314_.m_7949_(), Mth.m_14165_($$3.m_123331_((Vec3i)p_217314_)))).collect(Collectors.toCollection(Lists::newArrayList));
    }

    @Override
    protected void m_6725_(ServerLevel p_147680_, E p_147681_, long p_147682_) {
        if (this.f_147632_ != null) {
            if (p_147682_ - this.f_147634_ >= 40L) {
                ((Entity)p_147681_).m_146922_(((Mob)p_147681_).f_20883_);
                ((LivingEntity)p_147681_).m_147244_(true);
                double $$3 = this.f_147632_.m_82553_();
                double $$4 = $$3 + ((LivingEntity)p_147681_).m_182332_();
                ((Entity)p_147681_).m_20256_(this.f_147632_.m_82490_($$4 / $$3));
                ((LivingEntity)p_147681_).m_6274_().m_21879_(MemoryModuleType.f_148200_, true);
                p_147680_.m_6269_(null, (Entity)p_147681_, this.f_147635_.apply(p_147681_), SoundSource.NEUTRAL, 1.0f, 1.0f);
            }
        } else {
            --this.f_147633_;
            this.m_217318_(p_147680_, p_147681_, p_147682_);
        }
    }

    protected void m_217318_(ServerLevel p_217319_, E p_217320_, long p_217321_) {
        while (!this.f_147630_.isEmpty()) {
            Vec3 $$6;
            Vec3 $$7;
            PossibleJump $$4;
            BlockPos $$5;
            Optional<PossibleJump> $$3 = this.m_213675_(p_217319_);
            if ($$3.isEmpty() || !this.m_213828_(p_217319_, p_217320_, $$5 = ($$4 = $$3.get()).m_147693_()) || ($$7 = this.m_217303_((Mob)p_217320_, $$6 = Vec3.m_82512_($$5))) == null) continue;
            ((LivingEntity)p_217320_).m_6274_().m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker($$5));
            PathNavigation $$8 = ((Mob)p_217320_).m_21573_();
            Path $$9 = $$8.m_148218_($$5, 0, 8);
            if ($$9 != null && $$9.m_77403_()) continue;
            this.f_147632_ = $$7;
            this.f_147634_ = p_217321_;
            return;
        }
    }

    protected Optional<PossibleJump> m_213675_(ServerLevel p_217299_) {
        Optional<PossibleJump> $$1 = WeightedRandom.m_216822_(p_217299_.f_46441_, this.f_147630_);
        $$1.ifPresent(this.f_147630_::remove);
        return $$1;
    }

    protected boolean m_213828_(ServerLevel p_217300_, E p_217301_, BlockPos p_217302_) {
        BlockPos $$3 = ((Entity)p_217301_).m_20183_();
        int $$4 = $$3.m_123341_();
        int $$5 = $$3.m_123343_();
        if ($$4 == p_217302_.m_123341_() && $$5 == p_217302_.m_123343_()) {
            return false;
        }
        if (!((Mob)p_217301_).m_21573_().m_6342_(p_217302_) && !this.f_217290_.test(p_217300_.m_8055_(p_217302_.m_7495_()))) {
            return false;
        }
        return ((Mob)p_217301_).m_21439_(WalkNodeEvaluator.m_77604_(((Mob)p_217301_).f_19853_, p_217302_.m_122032_())) == 0.0f;
    }

    @Nullable
    protected Vec3 m_217303_(Mob p_217304_, Vec3 p_217305_) {
        ArrayList $$2 = Lists.newArrayList(f_217289_);
        Collections.shuffle($$2);
        Iterator iterator = $$2.iterator();
        while (iterator.hasNext()) {
            int $$3 = (Integer)iterator.next();
            Vec3 $$4 = this.m_217306_(p_217304_, p_217305_, $$3);
            if ($$4 == null) continue;
            return $$4;
        }
        return null;
    }

    @Nullable
    private Vec3 m_217306_(Mob p_217307_, Vec3 p_217308_, int p_217309_) {
        Vec3 $$3 = p_217307_.m_20182_();
        Vec3 $$4 = new Vec3(p_217308_.f_82479_ - $$3.f_82479_, 0.0, p_217308_.f_82481_ - $$3.f_82481_).m_82541_().m_82490_(0.5);
        p_217308_ = p_217308_.m_82546_($$4);
        Vec3 $$5 = p_217308_.m_82546_($$3);
        float $$6 = (float)p_217309_ * (float)Math.PI / 180.0f;
        double $$7 = Math.atan2($$5.f_82481_, $$5.f_82479_);
        double $$8 = $$5.m_82492_(0.0, $$5.f_82480_, 0.0).m_82556_();
        double $$9 = Math.sqrt($$8);
        double $$10 = $$5.f_82480_;
        double $$11 = Math.sin(2.0f * $$6);
        double $$12 = 0.08;
        double $$13 = Math.pow(Math.cos($$6), 2.0);
        double $$14 = Math.sin($$6);
        double $$15 = Math.cos($$6);
        double $$16 = Math.sin($$7);
        double $$17 = Math.cos($$7);
        double $$18 = $$8 * 0.08 / ($$9 * $$11 - 2.0 * $$10 * $$13);
        if ($$18 < 0.0) {
            return null;
        }
        double $$19 = Math.sqrt($$18);
        if ($$19 > (double)this.f_147629_) {
            return null;
        }
        double $$20 = $$19 * $$15;
        double $$21 = $$19 * $$14;
        int $$22 = Mth.m_14165_($$9 / $$20) * 2;
        double $$23 = 0.0;
        Vec3 $$24 = null;
        for (int $$25 = 0; $$25 < $$22 - 1; ++$$25) {
            double $$26 = $$14 / $$15 * ($$23 += $$9 / (double)$$22) - Math.pow($$23, 2.0) * 0.08 / (2.0 * $$18 * Math.pow($$15, 2.0));
            double $$27 = $$23 * $$17;
            double $$28 = $$23 * $$16;
            Vec3 $$29 = new Vec3($$3.f_82479_ + $$27, $$3.f_82480_ + $$26, $$3.f_82481_ + $$28);
            if ($$24 != null && !this.m_147663_(p_217307_, $$24, $$29)) {
                return null;
            }
            $$24 = $$29;
        }
        return new Vec3($$20 * $$17, $$21, $$20 * $$16).m_82490_(0.95f);
    }

    private boolean m_147663_(Mob p_147664_, Vec3 p_147665_, Vec3 p_147666_) {
        EntityDimensions $$3 = p_147664_.m_6972_(Pose.LONG_JUMPING);
        Vec3 $$4 = p_147666_.m_82546_(p_147665_);
        double $$5 = Math.min($$3.f_20377_, $$3.f_20378_);
        int $$6 = Mth.m_14165_($$4.m_82553_() / $$5);
        Vec3 $$7 = $$4.m_82541_();
        Vec3 $$8 = p_147665_;
        for (int $$9 = 0; $$9 < $$6; ++$$9) {
            $$8 = $$9 == $$6 - 1 ? p_147666_ : $$8.m_82549_($$7.m_82490_($$5 * (double)0.9f));
            AABB $$10 = $$3.m_20393_($$8);
            if (p_147664_.f_19853_.m_45756_(p_147664_, $$10)) continue;
            return false;
        }
        return true;
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (E)((Mob)livingEntity), l);
    }

    public static class PossibleJump
    extends WeightedEntry.IntrusiveBase {
        private final BlockPos f_147687_;

        public PossibleJump(BlockPos p_217323_, int p_217324_) {
            super(p_217324_);
            this.f_147687_ = p_217323_;
        }

        public BlockPos m_147693_() {
            return this.f_147687_;
        }
    }
}

