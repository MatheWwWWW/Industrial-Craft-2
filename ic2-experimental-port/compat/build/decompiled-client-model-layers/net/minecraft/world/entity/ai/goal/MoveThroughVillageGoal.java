/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MoveThroughVillageGoal
extends Goal {
    protected final PathfinderMob f_25573_;
    private final double f_25574_;
    @Nullable
    private Path f_25575_;
    private BlockPos f_25576_;
    private final boolean f_25577_;
    private final List<BlockPos> f_25578_ = Lists.newArrayList();
    private final int f_25579_;
    private final BooleanSupplier f_25580_;

    public MoveThroughVillageGoal(PathfinderMob p_25582_, double p_25583_, boolean p_25584_, int p_25585_, BooleanSupplier p_25586_) {
        this.f_25573_ = p_25582_;
        this.f_25574_ = p_25583_;
        this.f_25577_ = p_25584_;
        this.f_25579_ = p_25585_;
        this.f_25580_ = p_25586_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        if (!GoalUtils.m_26894_(p_25582_)) {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
    }

    @Override
    public boolean m_8036_() {
        if (!GoalUtils.m_26894_(this.f_25573_)) {
            return false;
        }
        this.m_25597_();
        if (this.f_25577_ && this.f_25573_.f_19853_.m_46461_()) {
            return false;
        }
        ServerLevel $$0 = (ServerLevel)this.f_25573_.f_19853_;
        BlockPos $$1 = this.f_25573_.m_20183_();
        if (!$$0.m_8736_($$1, 6)) {
            return false;
        }
        Vec3 $$2 = LandRandomPos.m_148503_(this.f_25573_, 15, 7, p_217751_ -> {
            if (!$$0.m_8802_((BlockPos)p_217751_)) {
                return Double.NEGATIVE_INFINITY;
            }
            Optional<BlockPos> $$3 = $$0.m_8904_().m_27186_(p_217758_ -> p_217758_.m_203656_(PoiTypeTags.f_215876_), this::m_25592_, (BlockPos)p_217751_, 10, PoiManager.Occupancy.IS_OCCUPIED);
            return $$3.map(p_217754_ -> -p_217754_.m_123331_($$1)).orElse(Double.NEGATIVE_INFINITY);
        });
        if ($$2 == null) {
            return false;
        }
        Optional<BlockPos> $$3 = $$0.m_8904_().m_27186_(p_217756_ -> p_217756_.m_203656_(PoiTypeTags.f_215876_), this::m_25592_, new BlockPos($$2), 10, PoiManager.Occupancy.IS_OCCUPIED);
        if ($$3.isEmpty()) {
            return false;
        }
        this.f_25576_ = $$3.get().m_7949_();
        GroundPathNavigation $$4 = (GroundPathNavigation)this.f_25573_.m_21573_();
        boolean $$5 = $$4.m_26492_();
        $$4.m_26477_(this.f_25580_.getAsBoolean());
        this.f_25575_ = $$4.m_7864_(this.f_25576_, 0);
        $$4.m_26477_($$5);
        if (this.f_25575_ == null) {
            Vec3 $$6 = DefaultRandomPos.m_148412_(this.f_25573_, 10, 7, Vec3.m_82539_(this.f_25576_), 1.5707963705062866);
            if ($$6 == null) {
                return false;
            }
            $$4.m_26477_(this.f_25580_.getAsBoolean());
            this.f_25575_ = this.f_25573_.m_21573_().m_26524_($$6.f_82479_, $$6.f_82480_, $$6.f_82481_, 0);
            $$4.m_26477_($$5);
            if (this.f_25575_ == null) {
                return false;
            }
        }
        for (int $$7 = 0; $$7 < this.f_25575_.m_77398_(); ++$$7) {
            Node $$8 = this.f_25575_.m_77375_($$7);
            BlockPos $$9 = new BlockPos($$8.f_77271_, $$8.f_77272_ + 1, $$8.f_77273_);
            if (!DoorBlock.m_52745_(this.f_25573_.f_19853_, $$9)) continue;
            this.f_25575_ = this.f_25573_.m_21573_().m_26524_($$8.f_77271_, $$8.f_77272_, $$8.f_77273_, 0);
            break;
        }
        return this.f_25575_ != null;
    }

    @Override
    public boolean m_8045_() {
        if (this.f_25573_.m_21573_().m_26571_()) {
            return false;
        }
        return !this.f_25576_.m_203195_(this.f_25573_.m_20182_(), this.f_25573_.m_20205_() + (float)this.f_25579_);
    }

    @Override
    public void m_8056_() {
        this.f_25573_.m_21573_().m_26536_(this.f_25575_, this.f_25574_);
    }

    @Override
    public void m_8041_() {
        if (this.f_25573_.m_21573_().m_26571_() || this.f_25576_.m_203195_(this.f_25573_.m_20182_(), this.f_25579_)) {
            this.f_25578_.add(this.f_25576_);
        }
    }

    private boolean m_25592_(BlockPos p_25593_) {
        for (BlockPos $$1 : this.f_25578_) {
            if (!Objects.equals(p_25593_, $$1)) continue;
            return false;
        }
        return true;
    }

    private void m_25597_() {
        if (this.f_25578_.size() > 15) {
            this.f_25578_.remove(0);
        }
    }
}

