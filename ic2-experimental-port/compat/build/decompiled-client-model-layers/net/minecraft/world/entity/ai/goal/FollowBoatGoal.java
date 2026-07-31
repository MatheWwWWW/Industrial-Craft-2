/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.BoatGoals;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;

public class FollowBoatGoal
extends Goal {
    private int f_25233_;
    private final PathfinderMob f_25234_;
    @Nullable
    private Player f_25235_;
    private BoatGoals f_25236_;

    public FollowBoatGoal(PathfinderMob p_25238_) {
        this.f_25234_ = p_25238_;
    }

    @Override
    public boolean m_8036_() {
        List<Boat> $$0 = this.f_25234_.f_19853_.m_45976_(Boat.class, this.f_25234_.m_20191_().m_82400_(5.0));
        boolean $$1 = false;
        for (Boat $$2 : $$0) {
            Entity $$3 = $$2.m_6688_();
            if (!($$3 instanceof Player) || !(Mth.m_14154_(((Player)$$3).f_20900_) > 0.0f) && !(Mth.m_14154_(((Player)$$3).f_20902_) > 0.0f)) continue;
            $$1 = true;
            break;
        }
        return this.f_25235_ != null && (Mth.m_14154_(this.f_25235_.f_20900_) > 0.0f || Mth.m_14154_(this.f_25235_.f_20902_) > 0.0f) || $$1;
    }

    @Override
    public boolean m_6767_() {
        return true;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25235_ != null && this.f_25235_.m_20159_() && (Mth.m_14154_(this.f_25235_.f_20900_) > 0.0f || Mth.m_14154_(this.f_25235_.f_20902_) > 0.0f);
    }

    @Override
    public void m_8056_() {
        List<Boat> $$0 = this.f_25234_.f_19853_.m_45976_(Boat.class, this.f_25234_.m_20191_().m_82400_(5.0));
        for (Boat $$1 : $$0) {
            if ($$1.m_6688_() == null || !($$1.m_6688_() instanceof Player)) continue;
            this.f_25235_ = (Player)$$1.m_6688_();
            break;
        }
        this.f_25233_ = 0;
        this.f_25236_ = BoatGoals.GO_TO_BOAT;
    }

    @Override
    public void m_8041_() {
        this.f_25235_ = null;
    }

    @Override
    public void m_8037_() {
        boolean $$0;
        boolean bl = $$0 = Mth.m_14154_(this.f_25235_.f_20900_) > 0.0f || Mth.m_14154_(this.f_25235_.f_20902_) > 0.0f;
        float $$1 = this.f_25236_ == BoatGoals.GO_IN_BOAT_DIRECTION ? ($$0 ? 0.01f : 0.0f) : 0.015f;
        this.f_25234_.m_19920_($$1, new Vec3(this.f_25234_.f_20900_, this.f_25234_.f_20901_, this.f_25234_.f_20902_));
        this.f_25234_.m_6478_(MoverType.SELF, this.f_25234_.m_20184_());
        if (--this.f_25233_ > 0) {
            return;
        }
        this.f_25233_ = this.m_183277_(10);
        if (this.f_25236_ == BoatGoals.GO_TO_BOAT) {
            BlockPos $$2 = this.f_25235_.m_20183_().m_121945_(this.f_25235_.m_6350_().m_122424_());
            $$2 = $$2.m_7918_(0, -1, 0);
            this.f_25234_.m_21573_().m_26519_($$2.m_123341_(), $$2.m_123342_(), $$2.m_123343_(), 1.0);
            if (this.f_25234_.m_20270_(this.f_25235_) < 4.0f) {
                this.f_25233_ = 0;
                this.f_25236_ = BoatGoals.GO_IN_BOAT_DIRECTION;
            }
        } else if (this.f_25236_ == BoatGoals.GO_IN_BOAT_DIRECTION) {
            Direction $$3 = this.f_25235_.m_6374_();
            BlockPos $$4 = this.f_25235_.m_20183_().m_5484_($$3, 10);
            this.f_25234_.m_21573_().m_26519_($$4.m_123341_(), $$4.m_123342_() - 1, $$4.m_123343_(), 1.0);
            if (this.f_25234_.m_20270_(this.f_25235_) > 12.0f) {
                this.f_25233_ = 0;
                this.f_25236_ = BoatGoals.GO_TO_BOAT;
            }
        }
    }
}

