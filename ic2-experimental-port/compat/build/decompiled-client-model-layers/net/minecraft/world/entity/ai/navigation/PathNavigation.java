/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.navigation;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class PathNavigation {
    private static final int f_148217_ = 20;
    protected final Mob f_26494_;
    protected final Level f_26495_;
    @Nullable
    protected Path f_26496_;
    protected double f_26497_;
    protected int f_26498_;
    protected int f_26499_;
    protected Vec3 f_26500_ = Vec3.f_82478_;
    protected Vec3i f_26501_ = Vec3i.f_123288_;
    protected long f_26502_;
    protected long f_26503_;
    protected double f_26504_;
    protected float f_26505_ = 0.5f;
    protected boolean f_26506_;
    protected long f_26507_;
    protected NodeEvaluator f_26508_;
    @Nullable
    private BlockPos f_26509_;
    private int f_26510_;
    private float f_26511_ = 1.0f;
    private final PathFinder f_26512_;
    private boolean f_26513_;

    public PathNavigation(Mob p_26515_, Level p_26516_) {
        this.f_26494_ = p_26515_;
        this.f_26495_ = p_26516_;
        int $$2 = Mth.m_14107_(p_26515_.m_21133_(Attributes.f_22277_) * 16.0);
        this.f_26512_ = this.m_5532_($$2);
    }

    public void m_26566_() {
        this.f_26511_ = 1.0f;
    }

    public void m_26529_(float p_26530_) {
        this.f_26511_ = p_26530_;
    }

    @Nullable
    public BlockPos m_26567_() {
        return this.f_26509_;
    }

    protected abstract PathFinder m_5532_(int var1);

    public void m_26517_(double p_26518_) {
        this.f_26497_ = p_26518_;
    }

    public void m_26569_() {
        if (this.f_26495_.m_46467_() - this.f_26507_ > 20L) {
            if (this.f_26509_ != null) {
                this.f_26496_ = null;
                this.f_26496_ = this.m_7864_(this.f_26509_, this.f_26510_);
                this.f_26507_ = this.f_26495_.m_46467_();
                this.f_26506_ = false;
            }
        } else {
            this.f_26506_ = true;
        }
    }

    @Nullable
    public final Path m_26524_(double p_26525_, double p_26526_, double p_26527_, int p_26528_) {
        return this.m_7864_(new BlockPos(p_26525_, p_26526_, p_26527_), p_26528_);
    }

    @Nullable
    public Path m_26556_(Stream<BlockPos> p_26557_, int p_26558_) {
        return this.m_26551_(p_26557_.collect(Collectors.toSet()), 8, false, p_26558_);
    }

    @Nullable
    public Path m_26548_(Set<BlockPos> p_26549_, int p_26550_) {
        return this.m_26551_(p_26549_, 8, false, p_26550_);
    }

    @Nullable
    public Path m_7864_(BlockPos p_26546_, int p_26547_) {
        return this.m_26551_((Set<BlockPos>)ImmutableSet.of((Object)p_26546_), 8, false, p_26547_);
    }

    @Nullable
    public Path m_148218_(BlockPos p_148219_, int p_148220_, int p_148221_) {
        return this.m_148222_((Set<BlockPos>)ImmutableSet.of((Object)p_148219_), 8, false, p_148220_, p_148221_);
    }

    @Nullable
    public Path m_6570_(Entity p_26534_, int p_26535_) {
        return this.m_26551_((Set<BlockPos>)ImmutableSet.of((Object)p_26534_.m_20183_()), 16, true, p_26535_);
    }

    @Nullable
    protected Path m_26551_(Set<BlockPos> p_26552_, int p_26553_, boolean p_26554_, int p_26555_) {
        return this.m_148222_(p_26552_, p_26553_, p_26554_, p_26555_, (float)this.f_26494_.m_21133_(Attributes.f_22277_));
    }

    @Nullable
    protected Path m_148222_(Set<BlockPos> p_148223_, int p_148224_, boolean p_148225_, int p_148226_, float p_148227_) {
        if (p_148223_.isEmpty()) {
            return null;
        }
        if (this.f_26494_.m_20186_() < (double)this.f_26495_.m_141937_()) {
            return null;
        }
        if (!this.m_7632_()) {
            return null;
        }
        if (this.f_26496_ != null && !this.f_26496_.m_77392_() && p_148223_.contains(this.f_26509_)) {
            return this.f_26496_;
        }
        this.f_26495_.m_46473_().m_6180_("pathfind");
        BlockPos $$5 = p_148225_ ? this.f_26494_.m_20183_().m_7494_() : this.f_26494_.m_20183_();
        int $$6 = (int)(p_148227_ + (float)p_148224_);
        PathNavigationRegion $$7 = new PathNavigationRegion(this.f_26495_, $$5.m_7918_(-$$6, -$$6, -$$6), $$5.m_7918_($$6, $$6, $$6));
        Path $$8 = this.f_26512_.m_77427_($$7, this.f_26494_, p_148223_, p_148227_, p_148226_, this.f_26511_);
        this.f_26495_.m_46473_().m_7238_();
        if ($$8 != null && $$8.m_77406_() != null) {
            this.f_26509_ = $$8.m_77406_();
            this.f_26510_ = p_148226_;
            this.m_26565_();
        }
        return $$8;
    }

    public boolean m_26519_(double p_26520_, double p_26521_, double p_26522_, double p_26523_) {
        return this.m_26536_(this.m_26524_(p_26520_, p_26521_, p_26522_, 1), p_26523_);
    }

    public boolean m_5624_(Entity p_26532_, double p_26533_) {
        Path $$2 = this.m_6570_(p_26532_, 1);
        return $$2 != null && this.m_26536_($$2, p_26533_);
    }

    public boolean m_26536_(@Nullable Path p_26537_, double p_26538_) {
        if (p_26537_ == null) {
            this.f_26496_ = null;
            return false;
        }
        if (!p_26537_.m_77385_(this.f_26496_)) {
            this.f_26496_ = p_26537_;
        }
        if (this.m_26571_()) {
            return false;
        }
        this.m_6804_();
        if (this.f_26496_.m_77398_() <= 0) {
            return false;
        }
        this.f_26497_ = p_26538_;
        Vec3 $$2 = this.m_7475_();
        this.f_26499_ = this.f_26498_;
        this.f_26500_ = $$2;
        return true;
    }

    @Nullable
    public Path m_26570_() {
        return this.f_26496_;
    }

    public void m_7638_() {
        ++this.f_26498_;
        if (this.f_26506_) {
            this.m_26569_();
        }
        if (this.m_26571_()) {
            return;
        }
        if (this.m_7632_()) {
            this.m_7636_();
        } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
            Vec3 $$0 = this.m_7475_();
            Vec3 $$1 = this.f_26496_.m_77380_(this.f_26494_);
            if ($$0.f_82480_ > $$1.f_82480_ && !this.f_26494_.m_20096_() && Mth.m_14107_($$0.f_82479_) == Mth.m_14107_($$1.f_82479_) && Mth.m_14107_($$0.f_82481_) == Mth.m_14107_($$1.f_82481_)) {
                this.f_26496_.m_77374_();
            }
        }
        DebugPackets.m_133703_(this.f_26495_, this.f_26494_, this.f_26496_, this.f_26505_);
        if (this.m_26571_()) {
            return;
        }
        Vec3 $$2 = this.f_26496_.m_77380_(this.f_26494_);
        this.f_26494_.m_21566_().m_6849_($$2.f_82479_, this.m_183345_($$2), $$2.f_82481_, this.f_26497_);
    }

    protected double m_183345_(Vec3 p_186132_) {
        BlockPos $$1 = new BlockPos(p_186132_);
        return this.f_26495_.m_8055_($$1.m_7495_()).m_60795_() ? p_186132_.f_82480_ : WalkNodeEvaluator.m_77611_(this.f_26495_, $$1);
    }

    protected void m_7636_() {
        boolean $$5;
        Vec3 $$0 = this.m_7475_();
        this.f_26505_ = this.f_26494_.m_20205_() > 0.75f ? this.f_26494_.m_20205_() / 2.0f : 0.75f - this.f_26494_.m_20205_() / 2.0f;
        BlockPos $$1 = this.f_26496_.m_77400_();
        double $$2 = Math.abs(this.f_26494_.m_20185_() - ((double)$$1.m_123341_() + 0.5));
        double $$3 = Math.abs(this.f_26494_.m_20186_() - (double)$$1.m_123342_());
        double $$4 = Math.abs(this.f_26494_.m_20189_() - ((double)$$1.m_123343_() + 0.5));
        boolean bl = $$5 = $$2 < (double)this.f_26505_ && $$4 < (double)this.f_26505_ && $$3 < 1.0;
        if ($$5 || this.f_26494_.m_21481_(this.f_26496_.m_77401_().f_77282_) && this.m_26559_($$0)) {
            this.f_26496_.m_77374_();
        }
        this.m_6481_($$0);
    }

    private boolean m_26559_(Vec3 p_26560_) {
        Vec3 $$4;
        if (this.f_26496_.m_77399_() + 1 >= this.f_26496_.m_77398_()) {
            return false;
        }
        Vec3 $$1 = Vec3.m_82539_(this.f_26496_.m_77400_());
        if (!p_26560_.m_82509_($$1, 2.0)) {
            return false;
        }
        if (this.m_183431_(p_26560_, this.f_26496_.m_77380_(this.f_26494_))) {
            return true;
        }
        Vec3 $$2 = Vec3.m_82539_(this.f_26496_.m_77396_(this.f_26496_.m_77399_() + 1));
        Vec3 $$3 = $$2.m_82546_($$1);
        return $$3.m_82526_($$4 = p_26560_.m_82546_($$1)) > 0.0;
    }

    protected void m_6481_(Vec3 p_26539_) {
        if (this.f_26498_ - this.f_26499_ > 100) {
            if (p_26539_.m_82557_(this.f_26500_) < 2.25) {
                this.f_26513_ = true;
                this.m_26573_();
            } else {
                this.f_26513_ = false;
            }
            this.f_26499_ = this.f_26498_;
            this.f_26500_ = p_26539_;
        }
        if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
            BlockPos $$1 = this.f_26496_.m_77400_();
            if ($$1.equals(this.f_26501_)) {
                this.f_26502_ += Util.m_137550_() - this.f_26503_;
            } else {
                this.f_26501_ = $$1;
                double $$2 = p_26539_.m_82554_(Vec3.m_82539_(this.f_26501_));
                double d = this.f_26504_ = this.f_26494_.m_6113_() > 0.0f ? $$2 / (double)this.f_26494_.m_6113_() * 1000.0 : 0.0;
            }
            if (this.f_26504_ > 0.0 && (double)this.f_26502_ > this.f_26504_ * 3.0) {
                this.m_26564_();
            }
            this.f_26503_ = Util.m_137550_();
        }
    }

    private void m_26564_() {
        this.m_26565_();
        this.m_26573_();
    }

    private void m_26565_() {
        this.f_26501_ = Vec3i.f_123288_;
        this.f_26502_ = 0L;
        this.f_26504_ = 0.0;
        this.f_26513_ = false;
    }

    public boolean m_26571_() {
        return this.f_26496_ == null || this.f_26496_.m_77392_();
    }

    public boolean m_26572_() {
        return !this.m_26571_();
    }

    public void m_26573_() {
        this.f_26496_ = null;
    }

    protected abstract Vec3 m_7475_();

    protected abstract boolean m_7632_();

    protected boolean m_26574_() {
        return this.f_26494_.m_20072_() || this.f_26494_.m_20077_();
    }

    protected void m_6804_() {
        if (this.f_26496_ == null) {
            return;
        }
        for (int $$0 = 0; $$0 < this.f_26496_.m_77398_(); ++$$0) {
            Node $$1 = this.f_26496_.m_77375_($$0);
            Node $$2 = $$0 + 1 < this.f_26496_.m_77398_() ? this.f_26496_.m_77375_($$0 + 1) : null;
            BlockState $$3 = this.f_26495_.m_8055_(new BlockPos($$1.f_77271_, $$1.f_77272_, $$1.f_77273_));
            if (!$$3.m_204336_(BlockTags.f_144269_)) continue;
            this.f_26496_.m_77377_($$0, $$1.m_77289_($$1.f_77271_, $$1.f_77272_ + 1, $$1.f_77273_));
            if ($$2 == null || $$1.f_77272_ < $$2.f_77272_) continue;
            this.f_26496_.m_77377_($$0 + 1, $$1.m_77289_($$2.f_77271_, $$1.f_77272_ + 1, $$2.f_77273_));
        }
    }

    protected boolean m_183431_(Vec3 p_186133_, Vec3 p_186134_) {
        return false;
    }

    protected static boolean m_217803_(Mob p_217804_, Vec3 p_217805_, Vec3 p_217806_) {
        Vec3 $$3 = new Vec3(p_217806_.f_82479_, p_217806_.f_82480_ + (double)p_217804_.m_20206_() * 0.5, p_217806_.f_82481_);
        return p_217804_.f_19853_.m_45547_(new ClipContext(p_217805_, $$3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_217804_)).m_6662_() == HitResult.Type.MISS;
    }

    public boolean m_6342_(BlockPos p_26545_) {
        BlockPos $$1 = p_26545_.m_7495_();
        return this.f_26495_.m_8055_($$1).m_60804_(this.f_26495_, $$1);
    }

    public NodeEvaluator m_26575_() {
        return this.f_26508_;
    }

    public void m_7008_(boolean p_26563_) {
        this.f_26508_.m_77358_(p_26563_);
    }

    public boolean m_26576_() {
        return this.f_26508_.m_77361_();
    }

    public boolean m_200903_(BlockPos p_200904_) {
        if (this.f_26506_) {
            return false;
        }
        if (this.f_26496_ == null || this.f_26496_.m_77392_() || this.f_26496_.m_77398_() == 0) {
            return false;
        }
        Node $$1 = this.f_26496_.m_77395_();
        Vec3 $$2 = new Vec3(((double)$$1.f_77271_ + this.f_26494_.m_20185_()) / 2.0, ((double)$$1.f_77272_ + this.f_26494_.m_20186_()) / 2.0, ((double)$$1.f_77273_ + this.f_26494_.m_20189_()) / 2.0);
        return p_200904_.m_203195_($$2, this.f_26496_.m_77398_() - this.f_26496_.m_77399_());
    }

    public float m_148228_() {
        return this.f_26505_;
    }

    public boolean m_26577_() {
        return this.f_26513_;
    }
}

