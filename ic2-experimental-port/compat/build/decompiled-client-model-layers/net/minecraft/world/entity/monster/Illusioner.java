/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Illusioner
extends SpellcasterIllager
implements RangedAttackMob {
    private static final int f_149715_ = 4;
    private static final int f_149713_ = 3;
    private static final int f_149714_ = 3;
    private int f_32908_;
    private final Vec3[][] f_32909_;

    public Illusioner(EntityType<? extends Illusioner> p_32911_, Level p_32912_) {
        super((EntityType<? extends SpellcasterIllager>)p_32911_, p_32912_);
        this.f_21364_ = 5;
        this.f_32909_ = new Vec3[2][4];
        for (int $$2 = 0; $$2 < 4; ++$$2) {
            this.f_32909_[0][$$2] = Vec3.f_82478_;
            this.f_32909_[1][$$2] = Vec3.f_82478_;
        }
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new SpellcasterIllager.SpellcasterCastingSpellGoal(this));
        this.f_21345_.m_25352_(4, new IllusionerMirrorSpellGoal());
        this.f_21345_.m_25352_(5, new IllusionerBlindnessSpellGoal());
        this.f_21345_.m_25352_(6, new RangedBowAttackGoal<Illusioner>(this, 0.5, 20, 15.0f));
        this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6));
        this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Raider.class).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true).m_26146_(300));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<AbstractVillager>((Mob)this, AbstractVillager.class, false).m_26146_(300));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, false).m_26146_(300));
    }

    public static AttributeSupplier.Builder m_32931_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.5).m_22268_(Attributes.f_22277_, 18.0).m_22268_(Attributes.f_22276_, 32.0);
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_32921_, DifficultyInstance p_32922_, MobSpawnType p_32923_, @Nullable SpawnGroupData p_32924_, @Nullable CompoundTag p_32925_) {
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42411_));
        return super.m_6518_(p_32921_, p_32922_, p_32923_, p_32924_, p_32925_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
    }

    @Override
    public AABB m_6921_() {
        return this.m_20191_().m_82377_(3.0, 0.0, 3.0);
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (this.f_19853_.f_46443_ && this.m_20145_()) {
            --this.f_32908_;
            if (this.f_32908_ < 0) {
                this.f_32908_ = 0;
            }
            if (this.f_20916_ == 1 || this.f_19797_ % 1200 == 0) {
                this.f_32908_ = 3;
                float $$0 = -6.0f;
                int $$1 = 13;
                for (int $$2 = 0; $$2 < 4; ++$$2) {
                    this.f_32909_[0][$$2] = this.f_32909_[1][$$2];
                    this.f_32909_[1][$$2] = new Vec3((double)(-6.0f + (float)this.f_19796_.m_188503_(13)) * 0.5, Math.max(0, this.f_19796_.m_188503_(6) - 4), (double)(-6.0f + (float)this.f_19796_.m_188503_(13)) * 0.5);
                }
                for (int $$3 = 0; $$3 < 16; ++$$3) {
                    this.f_19853_.m_7106_(ParticleTypes.f_123796_, this.m_20208_(0.5), this.m_20187_(), this.m_20246_(0.5), 0.0, 0.0, 0.0);
                }
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12052_, this.m_5720_(), 1.0f, 1.0f, false);
            } else if (this.f_20916_ == this.f_20917_ - 1) {
                this.f_32908_ = 3;
                for (int $$4 = 0; $$4 < 4; ++$$4) {
                    this.f_32909_[0][$$4] = this.f_32909_[1][$$4];
                    this.f_32909_[1][$$4] = new Vec3(0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public SoundEvent m_7930_() {
        return SoundEvents.f_12048_;
    }

    public Vec3[] m_32939_(float p_32940_) {
        if (this.f_32908_ <= 0) {
            return this.f_32909_[1];
        }
        double $$1 = ((float)this.f_32908_ - p_32940_) / 3.0f;
        $$1 = Math.pow($$1, 0.25);
        Vec3[] $$2 = new Vec3[4];
        for (int $$3 = 0; $$3 < 4; ++$$3) {
            $$2[$$3] = this.f_32909_[1][$$3].m_82490_(1.0 - $$1).m_82549_(this.f_32909_[0][$$3].m_82490_($$1));
        }
        return $$2;
    }

    @Override
    public boolean m_7307_(Entity p_32938_) {
        if (super.m_7307_(p_32938_)) {
            return true;
        }
        if (p_32938_ instanceof LivingEntity && ((LivingEntity)p_32938_).m_6336_() == MobType.f_21643_) {
            return this.m_5647_() == null && p_32938_.m_5647_() == null;
        }
        return false;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12048_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12050_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32930_) {
        return SoundEvents.f_12051_;
    }

    @Override
    protected SoundEvent m_7894_() {
        return SoundEvents.f_12049_;
    }

    @Override
    public void m_7895_(int p_32915_, boolean p_32916_) {
    }

    @Override
    public void m_6504_(LivingEntity p_32918_, float p_32919_) {
        ItemStack $$2 = this.m_6298_(this.m_21120_(ProjectileUtil.m_37297_(this, Items.f_42411_)));
        AbstractArrow $$3 = ProjectileUtil.m_37300_(this, $$2, p_32919_);
        double $$4 = p_32918_.m_20185_() - this.m_20185_();
        double $$5 = p_32918_.m_20227_(0.3333333333333333) - $$3.m_20186_();
        double $$6 = p_32918_.m_20189_() - this.m_20189_();
        double $$7 = Math.sqrt($$4 * $$4 + $$6 * $$6);
        $$3.m_6686_($$4, $$5 + $$7 * (double)0.2f, $$6, 1.6f, 14 - this.f_19853_.m_46791_().m_19028_() * 4);
        this.m_5496_(SoundEvents.f_12382_, 1.0f, 1.0f / (this.m_217043_().m_188501_() * 0.4f + 0.8f));
        this.f_19853_.m_7967_($$3);
    }

    @Override
    public AbstractIllager.IllagerArmPose m_6768_() {
        if (this.m_33736_()) {
            return AbstractIllager.IllagerArmPose.SPELLCASTING;
        }
        if (this.m_5912_()) {
            return AbstractIllager.IllagerArmPose.BOW_AND_ARROW;
        }
        return AbstractIllager.IllagerArmPose.CROSSED;
    }

    class IllusionerMirrorSpellGoal
    extends SpellcasterIllager.SpellcasterUseSpellGoal {
        IllusionerMirrorSpellGoal() {
            super(Illusioner.this);
        }

        @Override
        public boolean m_8036_() {
            if (!super.m_8036_()) {
                return false;
            }
            return !Illusioner.this.m_21023_(MobEffects.f_19609_);
        }

        @Override
        protected int m_8089_() {
            return 20;
        }

        @Override
        protected int m_8067_() {
            return 340;
        }

        @Override
        protected void m_8130_() {
            Illusioner.this.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 1200));
        }

        @Override
        @Nullable
        protected SoundEvent m_7030_() {
            return SoundEvents.f_12054_;
        }

        @Override
        protected SpellcasterIllager.IllagerSpell m_7269_() {
            return SpellcasterIllager.IllagerSpell.DISAPPEAR;
        }
    }

    class IllusionerBlindnessSpellGoal
    extends SpellcasterIllager.SpellcasterUseSpellGoal {
        private int f_32942_;

        IllusionerBlindnessSpellGoal() {
            super(Illusioner.this);
        }

        @Override
        public boolean m_8036_() {
            if (!super.m_8036_()) {
                return false;
            }
            if (Illusioner.this.m_5448_() == null) {
                return false;
            }
            if (Illusioner.this.m_5448_().m_19879_() == this.f_32942_) {
                return false;
            }
            return Illusioner.this.f_19853_.m_6436_(Illusioner.this.m_20183_()).m_19049_(Difficulty.NORMAL.ordinal());
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            LivingEntity $$0 = Illusioner.this.m_5448_();
            if ($$0 != null) {
                this.f_32942_ = $$0.m_19879_();
            }
        }

        @Override
        protected int m_8089_() {
            return 20;
        }

        @Override
        protected int m_8067_() {
            return 180;
        }

        @Override
        protected void m_8130_() {
            Illusioner.this.m_5448_().m_147207_(new MobEffectInstance(MobEffects.f_19610_, 400), Illusioner.this);
        }

        @Override
        protected SoundEvent m_7030_() {
            return SoundEvents.f_12053_;
        }

        @Override
        protected SpellcasterIllager.IllagerSpell m_7269_() {
            return SpellcasterIllager.IllagerSpell.BLINDNESS;
        }
    }
}

