/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.wither;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WitherBoss
extends Monster
implements PowerableMob,
RangedAttackMob {
    private static final EntityDataAccessor<Integer> f_31420_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_31433_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_31434_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
    private static final List<EntityDataAccessor<Integer>> f_31421_ = ImmutableList.of(f_31420_, f_31433_, f_31434_);
    private static final EntityDataAccessor<Integer> f_31422_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
    private static final int f_149587_ = 220;
    private final float[] f_31423_ = new float[2];
    private final float[] f_31424_ = new float[2];
    private final float[] f_31425_ = new float[2];
    private final float[] f_31426_ = new float[2];
    private final int[] f_31427_ = new int[2];
    private final int[] f_31428_ = new int[2];
    private int f_31429_;
    private final ServerBossEvent f_31430_ = (ServerBossEvent)new ServerBossEvent(this.m_5446_(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).m_7003_(true);
    private static final Predicate<LivingEntity> f_31431_ = p_31504_ -> p_31504_.m_6336_() != MobType.f_21641_ && p_31504_.m_5789_();
    private static final TargetingConditions f_31432_ = TargetingConditions.m_148352_().m_26883_(20.0).m_26888_(f_31431_);

    public WitherBoss(EntityType<? extends WitherBoss> p_31437_, Level p_31438_) {
        super((EntityType<? extends Monster>)p_31437_, p_31438_);
        this.f_21342_ = new FlyingMoveControl(this, 10, false);
        this.m_21153_(this.m_21233_());
        this.f_21364_ = 50;
    }

    @Override
    protected PathNavigation m_6037_(Level p_186262_) {
        FlyingPathNavigation $$1 = new FlyingPathNavigation(this, p_186262_);
        $$1.m_26440_(false);
        $$1.m_7008_(true);
        $$1.m_26443_(true);
        return $$1;
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new WitherDoNothingGoal());
        this.f_21345_.m_25352_(2, new RangedAttackGoal(this, 1.0, 40, 20.0f));
        this.f_21345_.m_25352_(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 0, false, false, f_31431_));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_31420_, 0);
        this.f_19804_.m_135372_(f_31433_, 0);
        this.f_19804_.m_135372_(f_31434_, 0);
        this.f_19804_.m_135372_(f_31422_, 0);
    }

    @Override
    public void m_7380_(CompoundTag p_31485_) {
        super.m_7380_(p_31485_);
        p_31485_.m_128405_("Invul", this.m_31502_());
    }

    @Override
    public void m_7378_(CompoundTag p_31474_) {
        super.m_7378_(p_31474_);
        this.m_31510_(p_31474_.m_128451_("Invul"));
        if (this.m_8077_()) {
            this.f_31430_.m_6456_(this.m_5446_());
        }
    }

    @Override
    public void m_6593_(@Nullable Component p_31476_) {
        super.m_6593_(p_31476_);
        this.f_31430_.m_6456_(this.m_5446_());
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12554_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_31500_) {
        return SoundEvents.f_12557_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12556_;
    }

    @Override
    public void m_8107_() {
        Entity $$1;
        Vec3 $$0 = this.m_20184_().m_82542_(1.0, 0.6, 1.0);
        if (!this.f_19853_.f_46443_ && this.m_31512_(0) > 0 && ($$1 = this.f_19853_.m_6815_(this.m_31512_(0))) != null) {
            double $$2 = $$0.f_82480_;
            if (this.m_20186_() < $$1.m_20186_() || !this.m_7090_() && this.m_20186_() < $$1.m_20186_() + 5.0) {
                $$2 = Math.max(0.0, $$2);
                $$2 += 0.3 - $$2 * (double)0.6f;
            }
            $$0 = new Vec3($$0.f_82479_, $$2, $$0.f_82481_);
            Vec3 $$3 = new Vec3($$1.m_20185_() - this.m_20185_(), 0.0, $$1.m_20189_() - this.m_20189_());
            if ($$3.m_165925_() > 9.0) {
                Vec3 $$4 = $$3.m_82541_();
                $$0 = $$0.m_82520_($$4.f_82479_ * 0.3 - $$0.f_82479_ * 0.6, 0.0, $$4.f_82481_ * 0.3 - $$0.f_82481_ * 0.6);
            }
        }
        this.m_20256_($$0);
        if ($$0.m_165925_() > 0.05) {
            this.m_146922_((float)Mth.m_14136_($$0.f_82481_, $$0.f_82479_) * 57.295776f - 90.0f);
        }
        super.m_8107_();
        for (int $$5 = 0; $$5 < 2; ++$$5) {
            this.f_31426_[$$5] = this.f_31424_[$$5];
            this.f_31425_[$$5] = this.f_31423_[$$5];
        }
        for (int $$6 = 0; $$6 < 2; ++$$6) {
            int $$7 = this.m_31512_($$6 + 1);
            Entity $$8 = null;
            if ($$7 > 0) {
                $$8 = this.f_19853_.m_6815_($$7);
            }
            if ($$8 != null) {
                double $$9 = this.m_31514_($$6 + 1);
                double $$10 = this.m_31516_($$6 + 1);
                double $$11 = this.m_31518_($$6 + 1);
                double $$12 = $$8.m_20185_() - $$9;
                double $$13 = $$8.m_20188_() - $$10;
                double $$14 = $$8.m_20189_() - $$11;
                double $$15 = Math.sqrt($$12 * $$12 + $$14 * $$14);
                float $$16 = (float)(Mth.m_14136_($$14, $$12) * 57.2957763671875) - 90.0f;
                float $$17 = (float)(-(Mth.m_14136_($$13, $$15) * 57.2957763671875));
                this.f_31423_[$$6] = this.m_31442_(this.f_31423_[$$6], $$17, 40.0f);
                this.f_31424_[$$6] = this.m_31442_(this.f_31424_[$$6], $$16, 10.0f);
                continue;
            }
            this.f_31424_[$$6] = this.m_31442_(this.f_31424_[$$6], this.f_20883_, 10.0f);
        }
        boolean $$18 = this.m_7090_();
        for (int $$19 = 0; $$19 < 3; ++$$19) {
            double $$20 = this.m_31514_($$19);
            double $$21 = this.m_31516_($$19);
            double $$22 = this.m_31518_($$19);
            this.f_19853_.m_7106_(ParticleTypes.f_123762_, $$20 + this.f_19796_.m_188583_() * (double)0.3f, $$21 + this.f_19796_.m_188583_() * (double)0.3f, $$22 + this.f_19796_.m_188583_() * (double)0.3f, 0.0, 0.0, 0.0);
            if (!$$18 || this.f_19853_.f_46441_.m_188503_(4) != 0) continue;
            this.f_19853_.m_7106_(ParticleTypes.f_123811_, $$20 + this.f_19796_.m_188583_() * (double)0.3f, $$21 + this.f_19796_.m_188583_() * (double)0.3f, $$22 + this.f_19796_.m_188583_() * (double)0.3f, 0.7f, 0.7f, 0.5);
        }
        if (this.m_31502_() > 0) {
            for (int $$23 = 0; $$23 < 3; ++$$23) {
                this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20185_() + this.f_19796_.m_188583_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * 3.3f), this.m_20189_() + this.f_19796_.m_188583_(), 0.7f, 0.7f, 0.9f);
            }
        }
    }

    @Override
    protected void m_8024_() {
        if (this.m_31502_() > 0) {
            int $$0 = this.m_31502_() - 1;
            this.f_31430_.m_142711_(1.0f - (float)$$0 / 220.0f);
            if ($$0 <= 0) {
                Explosion.BlockInteraction $$1 = this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
                this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20188_(), this.m_20189_(), 7.0f, false, $$1);
                if (!this.m_20067_()) {
                    this.f_19853_.m_6798_(1023, this.m_20183_(), 0);
                }
            }
            this.m_31510_($$0);
            if (this.f_19797_ % 10 == 0) {
                this.m_5634_(10.0f);
            }
            return;
        }
        super.m_8024_();
        for (int $$2 = 1; $$2 < 3; ++$$2) {
            int $$8;
            if (this.f_19797_ < this.f_31427_[$$2 - 1]) continue;
            this.f_31427_[$$2 - 1] = this.f_19797_ + 10 + this.f_19796_.m_188503_(10);
            if (this.f_19853_.m_46791_() == Difficulty.NORMAL || this.f_19853_.m_46791_() == Difficulty.HARD) {
                int n = $$2 - 1;
                int n2 = this.f_31428_[n];
                this.f_31428_[n] = n2 + 1;
                if (n2 > 15) {
                    float $$3 = 10.0f;
                    float $$4 = 5.0f;
                    double $$5 = Mth.m_216263_(this.f_19796_, this.m_20185_() - 10.0, this.m_20185_() + 10.0);
                    double $$6 = Mth.m_216263_(this.f_19796_, this.m_20186_() - 5.0, this.m_20186_() + 5.0);
                    double $$7 = Mth.m_216263_(this.f_19796_, this.m_20189_() - 10.0, this.m_20189_() + 10.0);
                    this.m_31448_($$2 + 1, $$5, $$6, $$7, true);
                    this.f_31428_[$$2 - 1] = 0;
                }
            }
            if (($$8 = this.m_31512_($$2)) > 0) {
                LivingEntity $$9 = (LivingEntity)this.f_19853_.m_6815_($$8);
                if ($$9 == null || !this.m_6779_($$9) || this.m_20280_($$9) > 900.0 || !this.m_142582_($$9)) {
                    this.m_31454_($$2, 0);
                    continue;
                }
                this.m_31457_($$2 + 1, $$9);
                this.f_31427_[$$2 - 1] = this.f_19797_ + 40 + this.f_19796_.m_188503_(20);
                this.f_31428_[$$2 - 1] = 0;
                continue;
            }
            List<LivingEntity> $$10 = this.f_19853_.m_45971_(LivingEntity.class, f_31432_, this, this.m_20191_().m_82377_(20.0, 8.0, 20.0));
            if ($$10.isEmpty()) continue;
            LivingEntity $$11 = $$10.get(this.f_19796_.m_188503_($$10.size()));
            this.m_31454_($$2, $$11.m_19879_());
        }
        if (this.m_5448_() != null) {
            this.m_31454_(0, this.m_5448_().m_19879_());
        } else {
            this.m_31454_(0, 0);
        }
        if (this.f_31429_ > 0) {
            --this.f_31429_;
            if (this.f_31429_ == 0 && this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                int $$12 = Mth.m_14107_(this.m_20186_());
                int $$13 = Mth.m_14107_(this.m_20185_());
                int $$14 = Mth.m_14107_(this.m_20189_());
                boolean $$15 = false;
                for (int $$16 = -1; $$16 <= 1; ++$$16) {
                    for (int $$17 = -1; $$17 <= 1; ++$$17) {
                        for (int $$18 = 0; $$18 <= 3; ++$$18) {
                            int $$19 = $$13 + $$16;
                            int $$20 = $$12 + $$18;
                            int $$21 = $$14 + $$17;
                            BlockPos $$22 = new BlockPos($$19, $$20, $$21);
                            BlockState $$23 = this.f_19853_.m_8055_($$22);
                            if (!WitherBoss.m_31491_($$23)) continue;
                            $$15 = this.f_19853_.m_46953_($$22, true, this) || $$15;
                        }
                    }
                }
                if ($$15) {
                    this.f_19853_.m_5898_(null, 1022, this.m_20183_(), 0);
                }
            }
        }
        if (this.f_19797_ % 20 == 0) {
            this.m_5634_(1.0f);
        }
        this.f_31430_.m_142711_(this.m_21223_() / this.m_21233_());
    }

    public static boolean m_31491_(BlockState p_31492_) {
        return !p_31492_.m_60795_() && !p_31492_.m_204336_(BlockTags.f_13070_);
    }

    public void m_31506_() {
        this.m_31510_(220);
        this.f_31430_.m_142711_(0.0f);
        this.m_21153_(this.m_21233_() / 3.0f);
    }

    @Override
    public void m_7601_(BlockState p_31471_, Vec3 p_31472_) {
    }

    @Override
    public void m_6457_(ServerPlayer p_31483_) {
        super.m_6457_(p_31483_);
        this.f_31430_.m_6543_(p_31483_);
    }

    @Override
    public void m_6452_(ServerPlayer p_31488_) {
        super.m_6452_(p_31488_);
        this.f_31430_.m_6539_(p_31488_);
    }

    private double m_31514_(int p_31515_) {
        if (p_31515_ <= 0) {
            return this.m_20185_();
        }
        float $$1 = (this.f_20883_ + (float)(180 * (p_31515_ - 1))) * ((float)Math.PI / 180);
        float $$2 = Mth.m_14089_($$1);
        return this.m_20185_() + (double)$$2 * 1.3;
    }

    private double m_31516_(int p_31517_) {
        if (p_31517_ <= 0) {
            return this.m_20186_() + 3.0;
        }
        return this.m_20186_() + 2.2;
    }

    private double m_31518_(int p_31519_) {
        if (p_31519_ <= 0) {
            return this.m_20189_();
        }
        float $$1 = (this.f_20883_ + (float)(180 * (p_31519_ - 1))) * ((float)Math.PI / 180);
        float $$2 = Mth.m_14031_($$1);
        return this.m_20189_() + (double)$$2 * 1.3;
    }

    private float m_31442_(float p_31443_, float p_31444_, float p_31445_) {
        float $$3 = Mth.m_14177_(p_31444_ - p_31443_);
        if ($$3 > p_31445_) {
            $$3 = p_31445_;
        }
        if ($$3 < -p_31445_) {
            $$3 = -p_31445_;
        }
        return p_31443_ + $$3;
    }

    private void m_31457_(int p_31458_, LivingEntity p_31459_) {
        this.m_31448_(p_31458_, p_31459_.m_20185_(), p_31459_.m_20186_() + (double)p_31459_.m_20192_() * 0.5, p_31459_.m_20189_(), p_31458_ == 0 && this.f_19796_.m_188501_() < 0.001f);
    }

    private void m_31448_(int p_31449_, double p_31450_, double p_31451_, double p_31452_, boolean p_31453_) {
        if (!this.m_20067_()) {
            this.f_19853_.m_5898_(null, 1024, this.m_20183_(), 0);
        }
        double $$5 = this.m_31514_(p_31449_);
        double $$6 = this.m_31516_(p_31449_);
        double $$7 = this.m_31518_(p_31449_);
        double $$8 = p_31450_ - $$5;
        double $$9 = p_31451_ - $$6;
        double $$10 = p_31452_ - $$7;
        WitherSkull $$11 = new WitherSkull(this.f_19853_, this, $$8, $$9, $$10);
        $$11.m_5602_(this);
        if (p_31453_) {
            $$11.m_37629_(true);
        }
        $$11.m_20343_($$5, $$6, $$7);
        this.f_19853_.m_7967_($$11);
    }

    @Override
    public void m_6504_(LivingEntity p_31468_, float p_31469_) {
        this.m_31457_(0, p_31468_);
    }

    @Override
    public boolean m_6469_(DamageSource p_31461_, float p_31462_) {
        Entity $$2;
        if (this.m_6673_(p_31461_)) {
            return false;
        }
        if (p_31461_ == DamageSource.f_19312_ || p_31461_.m_7639_() instanceof WitherBoss) {
            return false;
        }
        if (this.m_31502_() > 0 && p_31461_ != DamageSource.f_19317_) {
            return false;
        }
        if (this.m_7090_() && ($$2 = p_31461_.m_7640_()) instanceof AbstractArrow) {
            return false;
        }
        Entity $$3 = p_31461_.m_7639_();
        if ($$3 != null && !($$3 instanceof Player) && $$3 instanceof LivingEntity && ((LivingEntity)$$3).m_6336_() == this.m_6336_()) {
            return false;
        }
        if (this.f_31429_ <= 0) {
            this.f_31429_ = 20;
        }
        int $$4 = 0;
        while ($$4 < this.f_31428_.length) {
            int n = $$4++;
            this.f_31428_[n] = this.f_31428_[n] + 3;
        }
        return super.m_6469_(p_31461_, p_31462_);
    }

    @Override
    protected void m_7472_(DamageSource p_31464_, int p_31465_, boolean p_31466_) {
        super.m_7472_(p_31464_, p_31465_, p_31466_);
        ItemEntity $$3 = this.m_19998_(Items.f_42686_);
        if ($$3 != null) {
            $$3.m_32064_();
        }
    }

    @Override
    public void m_6043_() {
        if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
            this.m_146870_();
            return;
        }
        this.f_20891_ = 0;
    }

    @Override
    public boolean m_142535_(float p_149589_, float p_149590_, DamageSource p_149591_) {
        return false;
    }

    @Override
    public boolean m_147207_(MobEffectInstance p_182397_, @Nullable Entity p_182398_) {
        return false;
    }

    public static AttributeSupplier.Builder m_31501_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 300.0).m_22268_(Attributes.f_22279_, 0.6f).m_22268_(Attributes.f_22280_, 0.6f).m_22268_(Attributes.f_22277_, 40.0).m_22268_(Attributes.f_22284_, 4.0);
    }

    public float m_31446_(int p_31447_) {
        return this.f_31424_[p_31447_];
    }

    public float m_31480_(int p_31481_) {
        return this.f_31423_[p_31481_];
    }

    public int m_31502_() {
        return this.f_19804_.m_135370_(f_31422_);
    }

    public void m_31510_(int p_31511_) {
        this.f_19804_.m_135381_(f_31422_, p_31511_);
    }

    public int m_31512_(int p_31513_) {
        return this.f_19804_.m_135370_(f_31421_.get(p_31513_));
    }

    public void m_31454_(int p_31455_, int p_31456_) {
        this.f_19804_.m_135381_(f_31421_.get(p_31455_), p_31456_);
    }

    @Override
    public boolean m_7090_() {
        return this.m_21223_() <= this.m_21233_() / 2.0f;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21641_;
    }

    @Override
    protected boolean m_7341_(Entity p_31508_) {
        return false;
    }

    @Override
    public boolean m_6072_() {
        return false;
    }

    @Override
    public boolean m_7301_(MobEffectInstance p_31495_) {
        if (p_31495_.m_19544_() == MobEffects.f_19615_) {
            return false;
        }
        return super.m_7301_(p_31495_);
    }

    class WitherDoNothingGoal
    extends Goal {
        public WitherDoNothingGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            return WitherBoss.this.m_31502_() > 0;
        }
    }
}

