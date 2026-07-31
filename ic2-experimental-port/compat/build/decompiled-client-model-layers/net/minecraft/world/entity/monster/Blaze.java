/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Blaze
extends Monster {
    private float f_32214_ = 0.5f;
    private int f_32215_;
    private static final EntityDataAccessor<Byte> f_32216_ = SynchedEntityData.m_135353_(Blaze.class, EntityDataSerializers.f_135027_);

    public Blaze(EntityType<? extends Blaze> p_32219_, Level p_32220_) {
        super((EntityType<? extends Monster>)p_32219_, p_32220_);
        this.m_21441_(BlockPathTypes.WATER, -1.0f);
        this.m_21441_(BlockPathTypes.LAVA, 8.0f);
        this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0f);
        this.f_21364_ = 10;
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(4, new BlazeAttackGoal(this));
        this.f_21345_.m_25352_(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0, 0.0f));
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
    }

    public static AttributeSupplier.Builder m_32238_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22281_, 6.0).m_22268_(Attributes.f_22279_, 0.23f).m_22268_(Attributes.f_22277_, 48.0);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_32216_, (byte)0);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11701_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32235_) {
        return SoundEvents.f_11704_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11703_;
    }

    @Override
    public float m_213856_() {
        return 1.0f;
    }

    @Override
    public void m_8107_() {
        if (!this.f_19861_ && this.m_20184_().f_82480_ < 0.0) {
            this.m_20256_(this.m_20184_().m_82542_(1.0, 0.6, 1.0));
        }
        if (this.f_19853_.f_46443_) {
            if (this.f_19796_.m_188503_(24) == 0 && !this.m_20067_()) {
                this.f_19853_.m_7785_(this.m_20185_() + 0.5, this.m_20186_() + 0.5, this.m_20189_() + 0.5, SoundEvents.f_11702_, this.m_5720_(), 1.0f + this.f_19796_.m_188501_(), this.f_19796_.m_188501_() * 0.7f + 0.3f, false);
            }
            for (int $$0 = 0; $$0 < 2; ++$$0) {
                this.f_19853_.m_7106_(ParticleTypes.f_123755_, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), 0.0, 0.0, 0.0);
            }
        }
        super.m_8107_();
    }

    @Override
    public boolean m_6126_() {
        return true;
    }

    @Override
    protected void m_8024_() {
        LivingEntity $$0;
        --this.f_32215_;
        if (this.f_32215_ <= 0) {
            this.f_32215_ = 100;
            this.f_32214_ = (float)this.f_19796_.m_216328_(0.5, 6.891);
        }
        if (($$0 = this.m_5448_()) != null && $$0.m_20188_() > this.m_20188_() + (double)this.f_32214_ && this.m_6779_($$0)) {
            Vec3 $$1 = this.m_20184_();
            this.m_20256_(this.m_20184_().m_82520_(0.0, ((double)0.3f - $$1.f_82480_) * (double)0.3f, 0.0));
            this.f_19812_ = true;
        }
        super.m_8024_();
    }

    @Override
    public boolean m_142535_(float p_149683_, float p_149684_, DamageSource p_149685_) {
        return false;
    }

    @Override
    public boolean m_6060_() {
        return this.m_32236_();
    }

    private boolean m_32236_() {
        return (this.f_19804_.m_135370_(f_32216_) & 1) != 0;
    }

    void m_32240_(boolean p_32241_) {
        byte $$1 = this.f_19804_.m_135370_(f_32216_);
        $$1 = p_32241_ ? (byte)($$1 | 1) : (byte)($$1 & 0xFFFFFFFE);
        this.f_19804_.m_135381_(f_32216_, $$1);
    }

    static class BlazeAttackGoal
    extends Goal {
        private final Blaze f_32242_;
        private int f_32243_;
        private int f_32244_;
        private int f_32245_;

        public BlazeAttackGoal(Blaze p_32247_) {
            this.f_32242_ = p_32247_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = this.f_32242_.m_5448_();
            return $$0 != null && $$0.m_6084_() && this.f_32242_.m_6779_($$0);
        }

        @Override
        public void m_8056_() {
            this.f_32243_ = 0;
        }

        @Override
        public void m_8041_() {
            this.f_32242_.m_32240_(false);
            this.f_32245_ = 0;
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            --this.f_32244_;
            LivingEntity $$0 = this.f_32242_.m_5448_();
            if ($$0 == null) {
                return;
            }
            boolean $$1 = this.f_32242_.m_21574_().m_148306_($$0);
            this.f_32245_ = $$1 ? 0 : ++this.f_32245_;
            double $$2 = this.f_32242_.m_20280_($$0);
            if ($$2 < 4.0) {
                if (!$$1) {
                    return;
                }
                if (this.f_32244_ <= 0) {
                    this.f_32244_ = 20;
                    this.f_32242_.m_7327_($$0);
                }
                this.f_32242_.m_21566_().m_6849_($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_(), 1.0);
            } else if ($$2 < this.m_32252_() * this.m_32252_() && $$1) {
                double $$3 = $$0.m_20185_() - this.f_32242_.m_20185_();
                double $$4 = $$0.m_20227_(0.5) - this.f_32242_.m_20227_(0.5);
                double $$5 = $$0.m_20189_() - this.f_32242_.m_20189_();
                if (this.f_32244_ <= 0) {
                    ++this.f_32243_;
                    if (this.f_32243_ == 1) {
                        this.f_32244_ = 60;
                        this.f_32242_.m_32240_(true);
                    } else if (this.f_32243_ <= 4) {
                        this.f_32244_ = 6;
                    } else {
                        this.f_32244_ = 100;
                        this.f_32243_ = 0;
                        this.f_32242_.m_32240_(false);
                    }
                    if (this.f_32243_ > 1) {
                        double $$6 = Math.sqrt(Math.sqrt($$2)) * 0.5;
                        if (!this.f_32242_.m_20067_()) {
                            this.f_32242_.f_19853_.m_5898_(null, 1018, this.f_32242_.m_20183_(), 0);
                        }
                        for (int $$7 = 0; $$7 < 1; ++$$7) {
                            SmallFireball $$8 = new SmallFireball(this.f_32242_.f_19853_, this.f_32242_, this.f_32242_.m_217043_().m_216328_($$3, 2.297 * $$6), $$4, this.f_32242_.m_217043_().m_216328_($$5, 2.297 * $$6));
                            $$8.m_6034_($$8.m_20185_(), this.f_32242_.m_20227_(0.5) + 0.5, $$8.m_20189_());
                            this.f_32242_.f_19853_.m_7967_($$8);
                        }
                    }
                }
                this.f_32242_.m_21563_().m_24960_($$0, 10.0f, 10.0f);
            } else if (this.f_32245_ < 5) {
                this.f_32242_.m_21566_().m_6849_($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_(), 1.0);
            }
            super.m_8037_();
        }

        private double m_32252_() {
            return this.f_32242_.m_21133_(Attributes.f_22277_);
        }
    }
}

