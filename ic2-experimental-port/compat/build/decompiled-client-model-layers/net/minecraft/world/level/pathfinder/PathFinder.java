/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.pathfinder;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BinaryHeap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.Target;

public class PathFinder {
    private static final float f_164714_ = 1.5f;
    private final Node[] f_77420_ = new Node[32];
    private final int f_77421_;
    private final NodeEvaluator f_77422_;
    private static final boolean f_164715_ = false;
    private final BinaryHeap f_77423_ = new BinaryHeap();

    public PathFinder(NodeEvaluator p_77425_, int p_77426_) {
        this.f_77422_ = p_77425_;
        this.f_77421_ = p_77426_;
    }

    @Nullable
    public Path m_77427_(PathNavigationRegion p_77428_, Mob p_77429_, Set<BlockPos> p_77430_, float p_77431_, int p_77432_, float p_77433_) {
        this.f_77423_.m_77081_();
        this.f_77422_.m_6028_(p_77428_, p_77429_);
        Node $$6 = this.f_77422_.m_7171_();
        if ($$6 == null) {
            return null;
        }
        Map<Target, BlockPos> $$7 = p_77430_.stream().collect(Collectors.toMap(p_77448_ -> this.f_77422_.m_7568_(p_77448_.m_123341_(), p_77448_.m_123342_(), p_77448_.m_123343_()), Function.identity()));
        Path $$8 = this.m_164716_(p_77428_.m_151625_(), $$6, $$7, p_77431_, p_77432_, p_77433_);
        this.f_77422_.m_6802_();
        return $$8;
    }

    @Nullable
    private Path m_164716_(ProfilerFiller p_164717_, Node p_164718_, Map<Target, BlockPos> p_164719_, float p_164720_, int p_164721_, float p_164722_) {
        p_164717_.m_6180_("find_path");
        p_164717_.m_142259_(MetricCategory.PATH_FINDING);
        Set<Target> $$6 = p_164719_.keySet();
        p_164718_.f_77275_ = 0.0f;
        p_164718_.f_77277_ = p_164718_.f_77276_ = this.m_77444_(p_164718_, $$6);
        this.f_77423_.m_77081_();
        this.f_77423_.m_77084_(p_164718_);
        ImmutableSet $$7 = ImmutableSet.of();
        int $$8 = 0;
        HashSet $$9 = Sets.newHashSetWithExpectedSize((int)$$6.size());
        int $$10 = (int)((float)this.f_77421_ * p_164722_);
        while (!this.f_77423_.m_77092_() && ++$$8 < $$10) {
            Node $$11 = this.f_77423_.m_77091_();
            $$11.f_77279_ = true;
            for (Target $$12 : $$6) {
                if (!($$11.m_77304_($$12) <= (float)p_164721_)) continue;
                $$12.m_77509_();
                $$9.add($$12);
            }
            if (!$$9.isEmpty()) break;
            if ($$11.m_77293_(p_164718_) >= p_164720_) continue;
            int $$13 = this.f_77422_.m_6065_(this.f_77420_, $$11);
            for (int $$14 = 0; $$14 < $$13; ++$$14) {
                Node $$15 = this.f_77420_[$$14];
                float $$16 = this.m_214208_($$11, $$15);
                $$15.f_77280_ = $$11.f_77280_ + $$16;
                float $$17 = $$11.f_77275_ + $$16 + $$15.f_77281_;
                if (!($$15.f_77280_ < p_164720_) || $$15.m_77303_() && !($$17 < $$15.f_77275_)) continue;
                $$15.f_77278_ = $$11;
                $$15.f_77275_ = $$17;
                $$15.f_77276_ = this.m_77444_($$15, $$6) * 1.5f;
                if ($$15.m_77303_()) {
                    this.f_77423_.m_77086_($$15, $$15.f_77275_ + $$15.f_77276_);
                    continue;
                }
                $$15.f_77277_ = $$15.f_77275_ + $$15.f_77276_;
                this.f_77423_.m_77084_($$15);
            }
        }
        Optional<Path> $$18 = !$$9.isEmpty() ? $$9.stream().map(p_77454_ -> this.m_77434_(p_77454_.m_77508_(), (BlockPos)p_164719_.get(p_77454_), true)).min(Comparator.comparingInt(Path::m_77398_)) : $$6.stream().map(p_77451_ -> this.m_77434_(p_77451_.m_77508_(), (BlockPos)p_164719_.get(p_77451_), false)).min(Comparator.comparingDouble(Path::m_77407_).thenComparingInt(Path::m_77398_));
        p_164717_.m_7238_();
        if (!$$18.isPresent()) {
            return null;
        }
        Path $$19 = $$18.get();
        return $$19;
    }

    protected float m_214208_(Node p_230617_, Node p_230618_) {
        return p_230617_.m_77293_(p_230618_);
    }

    private float m_77444_(Node p_77445_, Set<Target> p_77446_) {
        float $$2 = Float.MAX_VALUE;
        for (Target $$3 : p_77446_) {
            float $$4 = p_77445_.m_77293_($$3);
            $$3.m_77503_($$4, p_77445_);
            $$2 = Math.min($$4, $$2);
        }
        return $$2;
    }

    private Path m_77434_(Node p_77435_, BlockPos p_77436_, boolean p_77437_) {
        ArrayList $$3 = Lists.newArrayList();
        Node $$4 = p_77435_;
        $$3.add(0, $$4);
        while ($$4.f_77278_ != null) {
            $$4 = $$4.f_77278_;
            $$3.add(0, $$4);
        }
        return new Path($$3, p_77436_, p_77437_);
    }
}

