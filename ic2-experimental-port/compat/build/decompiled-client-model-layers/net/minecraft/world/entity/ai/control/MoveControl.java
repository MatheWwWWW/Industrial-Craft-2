/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.control;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.Control;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoveControl
implements Control {
    public static final float f_148053_ = 5.0E-4f;
    public static final float f_148054_ = 2.5000003E-7f;
    protected static final int f_148055_ = 90;
    protected final Mob f_24974_;
    protected double f_24975_;
    protected double f_24976_;
    protected double f_24977_;
    protected double f_24978_;
    protected float f_24979_;
    protected float f_24980_;
    protected Operation f_24981_ = Operation.WAIT;

    public MoveControl(Mob p_24983_) {
        this.f_24974_ = p_24983_;
    }

    public boolean m_24995_() {
        return this.f_24981_ == Operation.MOVE_TO;
    }

    public double m_24999_() {
        return this.f_24978_;
    }

    public void m_6849_(double p_24984_, double p_24985_, double p_24986_, double p_24987_) {
        this.f_24975_ = p_24984_;
        this.f_24976_ = p_24985_;
        this.f_24977_ = p_24986_;
        this.f_24978_ = p_24987_;
        if (this.f_24981_ != Operation.JUMPING) {
            this.f_24981_ = Operation.MOVE_TO;
        }
    }

    public void m_24988_(float p_24989_, float p_24990_) {
        this.f_24981_ = Operation.STRAFE;
        this.f_24979_ = p_24989_;
        this.f_24980_ = p_24990_;
        this.f_24978_ = 0.25;
    }

    public void m_8126_() {
        if (this.f_24981_ == Operation.STRAFE) {
            float $$8;
            float $$0 = (float)this.f_24974_.m_21133_(Attributes.f_22279_);
            float $$1 = (float)this.f_24978_ * $$0;
            float $$2 = this.f_24979_;
            float $$3 = this.f_24980_;
            float $$4 = Mth.m_14116_($$2 * $$2 + $$3 * $$3);
            if ($$4 < 1.0f) {
                $$4 = 1.0f;
            }
            $$4 = $$1 / $$4;
            float $$5 = Mth.m_14031_(this.f_24974_.m_146908_() * ((float)Math.PI / 180));
            float $$6 = Mth.m_14089_(this.f_24974_.m_146908_() * ((float)Math.PI / 180));
            float $$7 = ($$2 *= $$4) * $$6 - ($$3 *= $$4) * $$5;
            if (!this.m_24996_($$7, $$8 = $$3 * $$6 + $$2 * $$5)) {
                this.f_24979_ = 1.0f;
                this.f_24980_ = 0.0f;
            }
            this.f_24974_.m_7910_($$1);
            this.f_24974_.m_21564_(this.f_24979_);
            this.f_24974_.m_21570_(this.f_24980_);
            this.f_24981_ = Operation.WAIT;
        } else if (this.f_24981_ == Operation.MOVE_TO) {
            this.f_24981_ = Operation.WAIT;
            double $$9 = this.f_24975_ - this.f_24974_.m_20185_();
            double $$10 = this.f_24977_ - this.f_24974_.m_20189_();
            double $$11 = this.f_24976_ - this.f_24974_.m_20186_();
            double $$12 = $$9 * $$9 + $$11 * $$11 + $$10 * $$10;
            if ($$12 < 2.500000277905201E-7) {
                this.f_24974_.m_21564_(0.0f);
                return;
            }
            float $$13 = (float)(Mth.m_14136_($$10, $$9) * 57.2957763671875) - 90.0f;
            this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), $$13, 90.0f));
            this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
            BlockPos $$14 = this.f_24974_.m_20183_();
            BlockState $$15 = this.f_24974_.f_19853_.m_8055_($$14);
            VoxelShape $$16 = $$15.m_60812_(this.f_24974_.f_19853_, $$14);
            if ($$11 > (double)this.f_24974_.f_19793_ && $$9 * $$9 + $$10 * $$10 < (double)Math.max(1.0f, this.f_24974_.m_20205_()) || !$$16.m_83281_() && this.f_24974_.m_20186_() < $$16.m_83297_(Direction.Axis.Y) + (double)$$14.m_123342_() && !$$15.m_204336_(BlockTags.f_13103_) && !$$15.m_204336_(BlockTags.f_13039_)) {
                this.f_24974_.m_21569_().m_24901_();
                this.f_24981_ = Operation.JUMPING;
            }
        } else if (this.f_24981_ == Operation.JUMPING) {
            this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
            if (this.f_24974_.m_20096_()) {
                this.f_24981_ = Operation.WAIT;
            }
        } else {
            this.f_24974_.m_21564_(0.0f);
        }
    }

    private boolean m_24996_(float p_24997_, float p_24998_) {
        NodeEvaluator $$3;
        PathNavigation $$2 = this.f_24974_.m_21573_();
        return $$2 == null || ($$3 = $$2.m_26575_()) == null || $$3.m_8086_(this.f_24974_.f_19853_, Mth.m_14107_(this.f_24974_.m_20185_() + (double)p_24997_), this.f_24974_.m_146904_(), Mth.m_14107_(this.f_24974_.m_20189_() + (double)p_24998_)) == BlockPathTypes.WALKABLE;
    }

    protected float m_24991_(float p_24992_, float p_24993_, float p_24994_) {
        float $$4;
        float $$3 = Mth.m_14177_(p_24993_ - p_24992_);
        if ($$3 > p_24994_) {
            $$3 = p_24994_;
        }
        if ($$3 < -p_24994_) {
            $$3 = -p_24994_;
        }
        if (($$4 = p_24992_ + $$3) < 0.0f) {
            $$4 += 360.0f;
        } else if ($$4 > 360.0f) {
            $$4 -= 360.0f;
        }
        return $$4;
    }

    public double m_25000_() {
        return this.f_24975_;
    }

    public double m_25001_() {
        return this.f_24976_;
    }

    public double m_25002_() {
        return this.f_24977_;
    }

    protected static final class Operation
    extends Enum<Operation> {
        public static final /* enum */ Operation WAIT = new Operation();
        public static final /* enum */ Operation MOVE_TO = new Operation();
        public static final /* enum */ Operation STRAFE = new Operation();
        public static final /* enum */ Operation JUMPING = new Operation();
        private static final /* synthetic */ Operation[] $VALUES;

        public static Operation[] values() {
            return (Operation[])$VALUES.clone();
        }

        public static Operation valueOf(String p_25013_) {
            return Enum.valueOf(Operation.class, p_25013_);
        }

        private static /* synthetic */ Operation[] m_148056_() {
            return new Operation[]{WAIT, MOVE_TO, STRAFE, JUMPING};
        }

        static {
            $VALUES = Operation.m_148056_();
        }
    }
}

