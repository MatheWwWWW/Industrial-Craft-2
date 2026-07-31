/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BegGoal
extends Goal {
    private final Wolf f_25056_;
    @Nullable
    private Player f_25057_;
    private final Level f_25058_;
    private final float f_25059_;
    private int f_25060_;
    private final TargetingConditions f_25061_;

    public BegGoal(Wolf p_25063_, float p_25064_) {
        this.f_25056_ = p_25063_;
        this.f_25058_ = p_25063_.f_19853_;
        this.f_25059_ = p_25064_;
        this.f_25061_ = TargetingConditions.m_148353_().m_26883_(p_25064_);
        this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        this.f_25057_ = this.f_25058_.m_45946_(this.f_25061_, this.f_25056_);
        if (this.f_25057_ == null) {
            return false;
        }
        return this.m_25066_(this.f_25057_);
    }

    @Override
    public boolean m_8045_() {
        if (!this.f_25057_.m_6084_()) {
            return false;
        }
        if (this.f_25056_.m_20280_(this.f_25057_) > (double)(this.f_25059_ * this.f_25059_)) {
            return false;
        }
        return this.f_25060_ > 0 && this.m_25066_(this.f_25057_);
    }

    @Override
    public void m_8056_() {
        this.f_25056_.m_30444_(true);
        this.f_25060_ = this.m_183277_(40 + this.f_25056_.m_217043_().m_188503_(40));
    }

    @Override
    public void m_8041_() {
        this.f_25056_.m_30444_(false);
        this.f_25057_ = null;
    }

    @Override
    public void m_8037_() {
        this.f_25056_.m_21563_().m_24950_(this.f_25057_.m_20185_(), this.f_25057_.m_20188_(), this.f_25057_.m_20189_(), 10.0f, this.f_25056_.m_8132_());
        --this.f_25060_;
    }

    private boolean m_25066_(Player p_25067_) {
        for (InteractionHand $$1 : InteractionHand.values()) {
            ItemStack $$2 = p_25067_.m_21120_($$1);
            if (this.f_25056_.m_21824_() && $$2.m_150930_(Items.f_42500_)) {
                return true;
            }
            if (!this.f_25056_.m_6898_($$2)) continue;
            return true;
        }
        return false;
    }
}

