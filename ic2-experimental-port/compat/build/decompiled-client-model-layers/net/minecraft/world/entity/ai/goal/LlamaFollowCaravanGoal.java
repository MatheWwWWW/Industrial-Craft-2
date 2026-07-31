/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.phys.Vec3;

public class LlamaFollowCaravanGoal
extends Goal {
    public final Llama f_25497_;
    private double f_25498_;
    private static final int f_148114_ = 8;
    private int f_25499_;

    public LlamaFollowCaravanGoal(Llama p_25501_, double p_25502_) {
        this.f_25497_ = p_25501_;
        this.f_25498_ = p_25502_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25497_.m_21523_() || this.f_25497_.m_30811_()) {
            return false;
        }
        List<Entity> $$0 = this.f_25497_.f_19853_.m_6249_(this.f_25497_, this.f_25497_.m_20191_().m_82377_(9.0, 4.0, 9.0), p_25505_ -> {
            EntityType<?> $$1 = p_25505_.m_6095_();
            return $$1 == EntityType.f_20466_ || $$1 == EntityType.f_20488_;
        });
        Mob $$1 = null;
        double $$2 = Double.MAX_VALUE;
        for (Entity $$3 : $$0) {
            double $$5;
            Llama $$4 = (Llama)$$3;
            if (!$$4.m_30811_() || $$4.m_30810_() || ($$5 = this.f_25497_.m_20280_($$4)) > $$2) continue;
            $$2 = $$5;
            $$1 = $$4;
        }
        if ($$1 == null) {
            for (Entity $$6 : $$0) {
                double $$8;
                Llama $$7 = (Llama)$$6;
                if (!$$7.m_21523_() || $$7.m_30810_() || ($$8 = this.f_25497_.m_20280_($$7)) > $$2) continue;
                $$2 = $$8;
                $$1 = $$7;
            }
        }
        if ($$1 == null) {
            return false;
        }
        if ($$2 < 4.0) {
            return false;
        }
        if (!$$1.m_21523_() && !this.m_25506_((Llama)$$1, 1)) {
            return false;
        }
        this.f_25497_.m_30766_((Llama)$$1);
        return true;
    }

    @Override
    public boolean m_8045_() {
        if (!(this.f_25497_.m_30811_() && this.f_25497_.m_30812_().m_6084_() && this.m_25506_(this.f_25497_, 0))) {
            return false;
        }
        double $$0 = this.f_25497_.m_20280_(this.f_25497_.m_30812_());
        if ($$0 > 676.0) {
            if (this.f_25498_ <= 3.0) {
                this.f_25498_ *= 1.2;
                this.f_25499_ = LlamaFollowCaravanGoal.m_186073_(40);
                return true;
            }
            if (this.f_25499_ == 0) {
                return false;
            }
        }
        if (this.f_25499_ > 0) {
            --this.f_25499_;
        }
        return true;
    }

    @Override
    public void m_8041_() {
        this.f_25497_.m_30809_();
        this.f_25498_ = 2.1;
    }

    @Override
    public void m_8037_() {
        if (!this.f_25497_.m_30811_()) {
            return;
        }
        if (this.f_25497_.m_21524_() instanceof LeashFenceKnotEntity) {
            return;
        }
        Llama $$0 = this.f_25497_.m_30812_();
        double $$1 = this.f_25497_.m_20270_($$0);
        float $$2 = 2.0f;
        Vec3 $$3 = new Vec3($$0.m_20185_() - this.f_25497_.m_20185_(), $$0.m_20186_() - this.f_25497_.m_20186_(), $$0.m_20189_() - this.f_25497_.m_20189_()).m_82541_().m_82490_(Math.max($$1 - 2.0, 0.0));
        this.f_25497_.m_21573_().m_26519_(this.f_25497_.m_20185_() + $$3.f_82479_, this.f_25497_.m_20186_() + $$3.f_82480_, this.f_25497_.m_20189_() + $$3.f_82481_, this.f_25498_);
    }

    private boolean m_25506_(Llama p_25507_, int p_25508_) {
        if (p_25508_ > 8) {
            return false;
        }
        if (p_25507_.m_30811_()) {
            if (p_25507_.m_30812_().m_21523_()) {
                return true;
            }
            return this.m_25506_(p_25507_.m_30812_(), ++p_25508_);
        }
        return false;
    }
}

