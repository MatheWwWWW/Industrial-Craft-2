/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Ravager
extends Raider {
    private static final Predicate<Entity> f_33319_ = p_33346_ -> p_33346_.m_6084_() && !(p_33346_ instanceof Ravager);
    private static final double f_149747_ = 0.3;
    private static final double f_149748_ = 0.35;
    private static final int f_149749_ = 8356754;
    private static final double f_149750_ = 0.5725490196078431;
    private static final double f_149751_ = 0.5137254901960784;
    private static final double f_149752_ = 0.4980392156862745;
    private static final int f_149753_ = 10;
    public static final int f_149746_ = 40;
    private int f_33320_;
    private int f_33321_;
    private int f_33322_;

    public Ravager(EntityType<? extends Ravager> p_33325_, Level p_33326_) {
        super((EntityType<? extends Raider>)p_33325_, p_33326_);
        this.f_19793_ = 1.0f;
        this.f_21364_ = 20;
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(4, new RavagerMeleeAttackGoal());
        this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.4));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, Raider.class).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
        this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal<AbstractVillager>((Mob)this, AbstractVillager.class, true, p_199899_ -> !p_199899_.m_6162_()));
        this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, true));
    }

    @Override
    protected void m_8022_() {
        boolean $$0 = !(this.m_6688_() instanceof Mob) || this.m_6688_().m_6095_().m_204039_(EntityTypeTags.f_13121_);
        boolean $$1 = !(this.m_20202_() instanceof Boat);
        this.f_21345_.m_25360_(Goal.Flag.MOVE, $$0);
        this.f_21345_.m_25360_(Goal.Flag.JUMP, $$0 && $$1);
        this.f_21345_.m_25360_(Goal.Flag.LOOK, $$0);
        this.f_21345_.m_25360_(Goal.Flag.TARGET, $$0);
    }

    public static AttributeSupplier.Builder m_33371_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 100.0).m_22268_(Attributes.f_22279_, 0.3).m_22268_(Attributes.f_22278_, 0.75).m_22268_(Attributes.f_22281_, 12.0).m_22268_(Attributes.f_22282_, 1.5).m_22268_(Attributes.f_22277_, 32.0);
    }

    @Override
    public void m_7380_(CompoundTag p_33353_) {
        super.m_7380_(p_33353_);
        p_33353_.m_128405_("AttackTick", this.f_33320_);
        p_33353_.m_128405_("StunTick", this.f_33321_);
        p_33353_.m_128405_("RoarTick", this.f_33322_);
    }

    @Override
    public void m_7378_(CompoundTag p_33344_) {
        super.m_7378_(p_33344_);
        this.f_33320_ = p_33344_.m_128451_("AttackTick");
        this.f_33321_ = p_33344_.m_128451_("StunTick");
        this.f_33322_ = p_33344_.m_128451_("RoarTick");
    }

    @Override
    public SoundEvent m_7930_() {
        return SoundEvents.f_12358_;
    }

    @Override
    protected PathNavigation m_6037_(Level p_33348_) {
        return new RavagerNavigation(this, p_33348_);
    }

    @Override
    public int m_8085_() {
        return 45;
    }

    @Override
    public double m_6048_() {
        return 2.1;
    }

    @Override
    @Nullable
    public Entity m_6688_() {
        Entity $$0 = this.m_146895_();
        return $$0 != null && this.m_219062_($$0) ? $$0 : null;
    }

    private boolean m_219062_(Entity p_219063_) {
        return !this.m_21525_() && p_219063_ instanceof LivingEntity;
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (!this.m_6084_()) {
            return;
        }
        if (this.m_6107_()) {
            this.m_21051_(Attributes.f_22279_).m_22100_(0.0);
        } else {
            double $$0 = this.m_5448_() != null ? 0.35 : 0.3;
            double $$1 = this.m_21051_(Attributes.f_22279_).m_22115_();
            this.m_21051_(Attributes.f_22279_).m_22100_(Mth.m_14139_(0.1, $$1, $$0));
        }
        if (this.f_19862_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
            boolean $$2 = false;
            AABB $$3 = this.m_20191_().m_82400_(0.2);
            for (BlockPos $$4 : BlockPos.m_121976_(Mth.m_14107_($$3.f_82288_), Mth.m_14107_($$3.f_82289_), Mth.m_14107_($$3.f_82290_), Mth.m_14107_($$3.f_82291_), Mth.m_14107_($$3.f_82292_), Mth.m_14107_($$3.f_82293_))) {
                BlockState $$5 = this.f_19853_.m_8055_($$4);
                Block $$6 = $$5.m_60734_();
                if (!($$6 instanceof LeavesBlock)) continue;
                $$2 = this.f_19853_.m_46953_($$4, true, this) || $$2;
            }
            if (!$$2 && this.f_19861_) {
                this.m_6135_();
            }
        }
        if (this.f_33322_ > 0) {
            --this.f_33322_;
            if (this.f_33322_ == 10) {
                this.m_33368_();
            }
        }
        if (this.f_33320_ > 0) {
            --this.f_33320_;
        }
        if (this.f_33321_ > 0) {
            --this.f_33321_;
            this.m_33367_();
            if (this.f_33321_ == 0) {
                this.m_5496_(SoundEvents.f_12363_, 1.0f, 1.0f);
                this.f_33322_ = 20;
            }
        }
    }

    private void m_33367_() {
        if (this.f_19796_.m_188503_(6) == 0) {
            double $$0 = this.m_20185_() - (double)this.m_20205_() * Math.sin(this.f_20883_ * ((float)Math.PI / 180)) + (this.f_19796_.m_188500_() * 0.6 - 0.3);
            double $$1 = this.m_20186_() + (double)this.m_20206_() - 0.3;
            double $$2 = this.m_20189_() + (double)this.m_20205_() * Math.cos(this.f_20883_ * ((float)Math.PI / 180)) + (this.f_19796_.m_188500_() * 0.6 - 0.3);
            this.f_19853_.m_7106_(ParticleTypes.f_123811_, $$0, $$1, $$2, 0.4980392156862745, 0.5137254901960784, 0.5725490196078431);
        }
    }

    @Override
    protected boolean m_6107_() {
        return super.m_6107_() || this.f_33320_ > 0 || this.f_33321_ > 0 || this.f_33322_ > 0;
    }

    @Override
    public boolean m_142582_(Entity p_149755_) {
        if (this.f_33321_ > 0 || this.f_33322_ > 0) {
            return false;
        }
        return super.m_142582_(p_149755_);
    }

    @Override
    protected void m_6731_(LivingEntity p_33361_) {
        if (this.f_33322_ == 0) {
            if (this.f_19796_.m_188500_() < 0.5) {
                this.f_33321_ = 40;
                this.m_5496_(SoundEvents.f_12362_, 1.0f, 1.0f);
                this.f_19853_.m_7605_(this, (byte)39);
                p_33361_.m_7334_(this);
            } else {
                this.m_33339_(p_33361_);
            }
            p_33361_.f_19864_ = true;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void m_33368_() {
        if (this.m_6084_()) {
            void var3_5;
            List<Entity> $$0 = this.f_19853_.m_6443_(LivingEntity.class, this.m_20191_().m_82400_(4.0), f_33319_);
            for (LivingEntity livingEntity : $$0) {
                if (!(livingEntity instanceof AbstractIllager)) {
                    livingEntity.m_6469_(DamageSource.m_19370_(this), 6.0f);
                }
                this.m_33339_(livingEntity);
            }
            Vec3 $$2 = this.m_20191_().m_82399_();
            boolean bl = false;
            while (var3_5 < 40) {
                double $$4 = this.f_19796_.m_188583_() * 0.2;
                double $$5 = this.f_19796_.m_188583_() * 0.2;
                double $$6 = this.f_19796_.m_188583_() * 0.2;
                this.f_19853_.m_7106_(ParticleTypes.f_123759_, $$2.f_82479_, $$2.f_82480_, $$2.f_82481_, $$4, $$5, $$6);
                ++var3_5;
            }
            this.m_146850_(GameEvent.f_223709_);
        }
    }

    private void m_33339_(Entity p_33340_) {
        double $$1 = p_33340_.m_20185_() - this.m_20185_();
        double $$2 = p_33340_.m_20189_() - this.m_20189_();
        double $$3 = Math.max($$1 * $$1 + $$2 * $$2, 0.001);
        p_33340_.m_5997_($$1 / $$3 * 4.0, 0.2, $$2 / $$3 * 4.0);
    }

    @Override
    public void m_7822_(byte p_33335_) {
        if (p_33335_ == 4) {
            this.f_33320_ = 10;
            this.m_5496_(SoundEvents.f_12357_, 1.0f, 1.0f);
        } else if (p_33335_ == 39) {
            this.f_33321_ = 40;
        }
        super.m_7822_(p_33335_);
    }

    public int m_33362_() {
        return this.f_33320_;
    }

    public int m_33364_() {
        return this.f_33321_;
    }

    public int m_33366_() {
        return this.f_33322_;
    }

    @Override
    public boolean m_7327_(Entity p_33328_) {
        this.f_33320_ = 10;
        this.f_19853_.m_7605_(this, (byte)4);
        this.m_5496_(SoundEvents.f_12357_, 1.0f, 1.0f);
        return super.m_7327_(p_33328_);
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12356_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33359_) {
        return SoundEvents.f_12360_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12359_;
    }

    @Override
    protected void m_7355_(BlockPos p_33350_, BlockState p_33351_) {
        this.m_5496_(SoundEvents.f_12361_, 0.15f, 1.0f);
    }

    @Override
    public boolean m_6914_(LevelReader p_33342_) {
        return !p_33342_.m_46855_(this.m_20191_());
    }

    @Override
    public void m_7895_(int p_33337_, boolean p_33338_) {
    }

    @Override
    public boolean m_7490_() {
        return false;
    }

    class RavagerMeleeAttackGoal
    extends MeleeAttackGoal {
        public RavagerMeleeAttackGoal() {
            super(Ravager.this, 1.0, true);
        }

        @Override
        protected double m_6639_(LivingEntity p_33377_) {
            float $$1 = Ravager.this.m_20205_() - 0.1f;
            return $$1 * 2.0f * ($$1 * 2.0f) + p_33377_.m_20205_();
        }
    }

    static class RavagerNavigation
    extends GroundPathNavigation {
        public RavagerNavigation(Mob p_33379_, Level p_33380_) {
            super(p_33379_, p_33380_);
        }

        @Override
        protected PathFinder m_5532_(int p_33382_) {
            this.f_26508_ = new RavagerNodeEvaluator();
            return new PathFinder(this.f_26508_, p_33382_);
        }
    }

    static class RavagerNodeEvaluator
    extends WalkNodeEvaluator {
        RavagerNodeEvaluator() {
        }

        @Override
        protected BlockPathTypes m_6603_(BlockGetter p_33387_, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
            if (p_33391_ == BlockPathTypes.LEAVES) {
                return BlockPathTypes.OPEN;
            }
            return super.m_6603_(p_33387_, p_33388_, p_33389_, p_33390_, p_33391_);
        }
    }
}

