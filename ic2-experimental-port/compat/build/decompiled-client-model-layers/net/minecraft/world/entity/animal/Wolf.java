/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Wolf
extends TamableAnimal
implements NeutralMob {
    private static final EntityDataAccessor<Boolean> f_30358_ = SynchedEntityData.m_135353_(Wolf.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_30359_ = SynchedEntityData.m_135353_(Wolf.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_30360_ = SynchedEntityData.m_135353_(Wolf.class, EntityDataSerializers.f_135028_);
    public static final Predicate<LivingEntity> f_30357_ = p_30437_ -> {
        EntityType<?> $$1 = p_30437_.m_6095_();
        return $$1 == EntityType.f_20520_ || $$1 == EntityType.f_20517_ || $$1 == EntityType.f_20452_;
    };
    private static final float f_149082_ = 8.0f;
    private static final float f_149083_ = 20.0f;
    private float f_30361_;
    private float f_30362_;
    private boolean f_30363_;
    private boolean f_30364_;
    private float f_30365_;
    private float f_30366_;
    private static final UniformInt f_30355_ = TimeUtil.m_145020_(20, 39);
    @Nullable
    private UUID f_30356_;

    public Wolf(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
        super((EntityType<? extends TamableAnimal>)p_30369_, p_30370_);
        this.m_7105_(false);
        this.m_21441_(BlockPathTypes.POWDER_SNOW, -1.0f);
        this.m_21441_(BlockPathTypes.DANGER_POWDER_SNOW, -1.0f);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new WolfPanicGoal(1.5));
        this.f_21345_.m_25352_(2, new SitWhenOrderedToGoal(this));
        this.f_21345_.m_25352_(3, new WolfAvoidEntityGoal<Llama>(this, Llama.class, 24.0f, 1.5, 1.5));
        this.f_21345_.m_25352_(4, new LeapAtTargetGoal(this, 0.4f));
        this.f_21345_.m_25352_(5, new MeleeAttackGoal(this, 1.0, true));
        this.f_21345_.m_25352_(6, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.f_21345_.m_25352_(7, new BreedGoal(this, 1.0));
        this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(9, new BegGoal(this, 8.0f));
        this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
        this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
        this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::m_21674_));
        this.f_21346_.m_25352_(5, new NonTameRandomTargetGoal<Animal>(this, Animal.class, false, f_30357_));
        this.f_21346_.m_25352_(6, new NonTameRandomTargetGoal<Turtle>(this, Turtle.class, false, Turtle.f_30122_));
        this.f_21346_.m_25352_(7, new NearestAttackableTargetGoal<AbstractSkeleton>((Mob)this, AbstractSkeleton.class, false));
        this.f_21346_.m_25352_(8, new ResetUniversalAngerTargetGoal<Wolf>(this, true));
    }

    public static AttributeSupplier.Builder m_30425_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22276_, 8.0).m_22268_(Attributes.f_22281_, 2.0);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_30358_, false);
        this.f_19804_.m_135372_(f_30359_, DyeColor.RED.m_41060_());
        this.f_19804_.m_135372_(f_30360_, 0);
    }

    @Override
    protected void m_7355_(BlockPos p_30415_, BlockState p_30416_) {
        this.m_5496_(SoundEvents.f_12624_, 0.15f, 1.0f);
    }

    @Override
    public void m_7380_(CompoundTag p_30418_) {
        super.m_7380_(p_30418_);
        p_30418_.m_128344_("CollarColor", (byte)this.m_30428_().m_41060_());
        this.m_21678_(p_30418_);
    }

    @Override
    public void m_7378_(CompoundTag p_30402_) {
        super.m_7378_(p_30402_);
        if (p_30402_.m_128425_("CollarColor", 99)) {
            this.m_30397_(DyeColor.m_41053_(p_30402_.m_128451_("CollarColor")));
        }
        this.m_147285_(this.f_19853_, p_30402_);
    }

    @Override
    protected SoundEvent m_7515_() {
        if (this.m_21660_()) {
            return SoundEvents.f_12619_;
        }
        if (this.f_19796_.m_188503_(3) == 0) {
            if (this.m_21824_() && this.m_21223_() < 10.0f) {
                return SoundEvents.f_12625_;
            }
            return SoundEvents.f_12622_;
        }
        return SoundEvents.f_12617_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_30424_) {
        return SoundEvents.f_12621_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12618_;
    }

    @Override
    protected float m_6121_() {
        return 0.4f;
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (!this.f_19853_.f_46443_ && this.f_30363_ && !this.f_30364_ && !this.m_21691_() && this.f_19861_) {
            this.f_30364_ = true;
            this.f_30365_ = 0.0f;
            this.f_30366_ = 0.0f;
            this.f_19853_.m_7605_(this, (byte)8);
        }
        if (!this.f_19853_.f_46443_) {
            this.m_21666_((ServerLevel)this.f_19853_, true);
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (!this.m_6084_()) {
            return;
        }
        this.f_30362_ = this.f_30361_;
        this.f_30361_ = this.m_30429_() ? (this.f_30361_ += (1.0f - this.f_30361_) * 0.4f) : (this.f_30361_ += (0.0f - this.f_30361_) * 0.4f);
        if (this.m_20071_()) {
            this.f_30363_ = true;
            if (this.f_30364_ && !this.f_19853_.f_46443_) {
                this.f_19853_.m_7605_(this, (byte)56);
                this.m_30430_();
            }
        } else if ((this.f_30363_ || this.f_30364_) && this.f_30364_) {
            if (this.f_30365_ == 0.0f) {
                this.m_5496_(SoundEvents.f_12623_, this.m_6121_(), (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
                this.m_146850_(GameEvent.f_223710_);
            }
            this.f_30366_ = this.f_30365_;
            this.f_30365_ += 0.05f;
            if (this.f_30366_ >= 2.0f) {
                this.f_30363_ = false;
                this.f_30364_ = false;
                this.f_30366_ = 0.0f;
                this.f_30365_ = 0.0f;
            }
            if (this.f_30365_ > 0.4f) {
                float $$0 = (float)this.m_20186_();
                int $$1 = (int)(Mth.m_14031_((this.f_30365_ - 0.4f) * (float)Math.PI) * 7.0f);
                Vec3 $$2 = this.m_20184_();
                for (int $$3 = 0; $$3 < $$1; ++$$3) {
                    float $$4 = (this.f_19796_.m_188501_() * 2.0f - 1.0f) * this.m_20205_() * 0.5f;
                    float $$5 = (this.f_19796_.m_188501_() * 2.0f - 1.0f) * this.m_20205_() * 0.5f;
                    this.f_19853_.m_7106_(ParticleTypes.f_123769_, this.m_20185_() + (double)$$4, $$0 + 0.8f, this.m_20189_() + (double)$$5, $$2.f_82479_, $$2.f_82480_, $$2.f_82481_);
                }
            }
        }
    }

    private void m_30430_() {
        this.f_30364_ = false;
        this.f_30365_ = 0.0f;
        this.f_30366_ = 0.0f;
    }

    @Override
    public void m_6667_(DamageSource p_30384_) {
        this.f_30363_ = false;
        this.f_30364_ = false;
        this.f_30366_ = 0.0f;
        this.f_30365_ = 0.0f;
        super.m_6667_(p_30384_);
    }

    public boolean m_30426_() {
        return this.f_30363_;
    }

    public float m_30446_(float p_30447_) {
        return Math.min(0.5f + Mth.m_14179_(p_30447_, this.f_30366_, this.f_30365_) / 2.0f * 0.5f, 1.0f);
    }

    public float m_30432_(float p_30433_, float p_30434_) {
        float $$2 = (Mth.m_14179_(p_30433_, this.f_30366_, this.f_30365_) + p_30434_) / 1.8f;
        if ($$2 < 0.0f) {
            $$2 = 0.0f;
        } else if ($$2 > 1.0f) {
            $$2 = 1.0f;
        }
        return Mth.m_14031_($$2 * (float)Math.PI) * Mth.m_14031_($$2 * (float)Math.PI * 11.0f) * 0.15f * (float)Math.PI;
    }

    public float m_30448_(float p_30449_) {
        return Mth.m_14179_(p_30449_, this.f_30362_, this.f_30361_) * 0.15f * (float)Math.PI;
    }

    @Override
    protected float m_6431_(Pose p_30409_, EntityDimensions p_30410_) {
        return p_30410_.f_20378_ * 0.8f;
    }

    @Override
    public int m_8132_() {
        if (this.m_21825_()) {
            return 20;
        }
        return super.m_8132_();
    }

    @Override
    public boolean m_6469_(DamageSource p_30386_, float p_30387_) {
        if (this.m_6673_(p_30386_)) {
            return false;
        }
        Entity $$2 = p_30386_.m_7639_();
        if (!this.f_19853_.f_46443_) {
            this.m_21839_(false);
        }
        if ($$2 != null && !($$2 instanceof Player) && !($$2 instanceof AbstractArrow)) {
            p_30387_ = (p_30387_ + 1.0f) / 2.0f;
        }
        return super.m_6469_(p_30386_, p_30387_);
    }

    @Override
    public boolean m_7327_(Entity p_30372_) {
        boolean $$1 = p_30372_.m_6469_(DamageSource.m_19370_(this), (int)this.m_21133_(Attributes.f_22281_));
        if ($$1) {
            this.m_19970_(this, p_30372_);
        }
        return $$1;
    }

    @Override
    public void m_7105_(boolean p_30443_) {
        super.m_7105_(p_30443_);
        if (p_30443_) {
            this.m_21051_(Attributes.f_22276_).m_22100_(20.0);
            this.m_21153_(20.0f);
        } else {
            this.m_21051_(Attributes.f_22276_).m_22100_(8.0);
        }
        this.m_21051_(Attributes.f_22281_).m_22100_(4.0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public InteractionResult m_6071_(Player p_30412_, InteractionHand p_30413_) {
        ItemStack $$2 = p_30412_.m_21120_(p_30413_);
        Item $$3 = $$2.m_41720_();
        if (this.f_19853_.f_46443_) {
            boolean $$4 = this.m_21830_(p_30412_) || this.m_21824_() || $$2.m_150930_(Items.f_42500_) && !this.m_21824_() && !this.m_21660_();
            return $$4 ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        if (this.m_21824_()) {
            if (this.m_6898_($$2) && this.m_21223_() < this.m_21233_()) {
                if (!p_30412_.m_150110_().f_35937_) {
                    $$2.m_41774_(1);
                }
                this.m_5634_($$3.m_41473_().m_38744_());
                return InteractionResult.SUCCESS;
            }
            if ($$3 instanceof DyeItem) {
                DyeColor $$5 = ((DyeItem)$$3).m_41089_();
                if ($$5 == this.m_30428_()) return super.m_6071_(p_30412_, p_30413_);
                this.m_30397_($$5);
                if (p_30412_.m_150110_().f_35937_) return InteractionResult.SUCCESS;
                $$2.m_41774_(1);
                return InteractionResult.SUCCESS;
            }
            InteractionResult $$6 = super.m_6071_(p_30412_, p_30413_);
            if ($$6.m_19077_() && !this.m_6162_() || !this.m_21830_(p_30412_)) return $$6;
            this.m_21839_(!this.m_21827_());
            this.f_20899_ = false;
            this.f_21344_.m_26573_();
            this.m_6710_(null);
            return InteractionResult.SUCCESS;
        }
        if (!$$2.m_150930_(Items.f_42500_) || this.m_21660_()) return super.m_6071_(p_30412_, p_30413_);
        if (!p_30412_.m_150110_().f_35937_) {
            $$2.m_41774_(1);
        }
        if (this.f_19796_.m_188503_(3) == 0) {
            this.m_21828_(p_30412_);
            this.f_21344_.m_26573_();
            this.m_6710_(null);
            this.m_21839_(true);
            this.f_19853_.m_7605_(this, (byte)7);
            return InteractionResult.SUCCESS;
        } else {
            this.f_19853_.m_7605_(this, (byte)6);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void m_7822_(byte p_30379_) {
        if (p_30379_ == 8) {
            this.f_30364_ = true;
            this.f_30365_ = 0.0f;
            this.f_30366_ = 0.0f;
        } else if (p_30379_ == 56) {
            this.m_30430_();
        } else {
            super.m_7822_(p_30379_);
        }
    }

    public float m_30427_() {
        if (this.m_21660_()) {
            return 1.5393804f;
        }
        if (this.m_21824_()) {
            return (0.55f - (this.m_21233_() - this.m_21223_()) * 0.02f) * (float)Math.PI;
        }
        return 0.62831855f;
    }

    @Override
    public boolean m_6898_(ItemStack p_30440_) {
        Item $$1 = p_30440_.m_41720_();
        return $$1.m_41472_() && $$1.m_41473_().m_38746_();
    }

    @Override
    public int m_5792_() {
        return 8;
    }

    @Override
    public int m_6784_() {
        return this.f_19804_.m_135370_(f_30360_);
    }

    @Override
    public void m_7870_(int p_30404_) {
        this.f_19804_.m_135381_(f_30360_, p_30404_);
    }

    @Override
    public void m_6825_() {
        this.m_7870_(f_30355_.m_214085_(this.f_19796_));
    }

    @Override
    @Nullable
    public UUID m_6120_() {
        return this.f_30356_;
    }

    @Override
    public void m_6925_(@Nullable UUID p_30400_) {
        this.f_30356_ = p_30400_;
    }

    public DyeColor m_30428_() {
        return DyeColor.m_41053_(this.f_19804_.m_135370_(f_30359_));
    }

    public void m_30397_(DyeColor p_30398_) {
        this.f_19804_.m_135381_(f_30359_, p_30398_.m_41060_());
    }

    @Override
    public Wolf m_142606_(ServerLevel p_149088_, AgeableMob p_149089_) {
        Wolf $$2 = EntityType.f_20499_.m_20615_(p_149088_);
        UUID $$3 = this.m_21805_();
        if ($$3 != null) {
            $$2.m_21816_($$3);
            $$2.m_7105_(true);
        }
        return $$2;
    }

    public void m_30444_(boolean p_30445_) {
        this.f_19804_.m_135381_(f_30358_, p_30445_);
    }

    @Override
    public boolean m_7848_(Animal p_30392_) {
        if (p_30392_ == this) {
            return false;
        }
        if (!this.m_21824_()) {
            return false;
        }
        if (!(p_30392_ instanceof Wolf)) {
            return false;
        }
        Wolf $$1 = (Wolf)p_30392_;
        if (!$$1.m_21824_()) {
            return false;
        }
        if ($$1.m_21825_()) {
            return false;
        }
        return this.m_27593_() && $$1.m_27593_();
    }

    public boolean m_30429_() {
        return this.f_19804_.m_135370_(f_30358_);
    }

    @Override
    public boolean m_7757_(LivingEntity p_30389_, LivingEntity p_30390_) {
        if (p_30389_ instanceof Creeper || p_30389_ instanceof Ghast) {
            return false;
        }
        if (p_30389_ instanceof Wolf) {
            Wolf $$2 = (Wolf)p_30389_;
            return !$$2.m_21824_() || $$2.m_21826_() != p_30390_;
        }
        if (p_30389_ instanceof Player && p_30390_ instanceof Player && !((Player)p_30390_).m_7099_((Player)p_30389_)) {
            return false;
        }
        if (p_30389_ instanceof AbstractHorse && ((AbstractHorse)p_30389_).m_30614_()) {
            return false;
        }
        return !(p_30389_ instanceof TamableAnimal) || !((TamableAnimal)p_30389_).m_21824_();
    }

    @Override
    public boolean m_6573_(Player p_30396_) {
        return !this.m_21660_() && super.m_6573_(p_30396_);
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.6f * this.m_20192_(), this.m_20205_() * 0.4f);
    }

    public static boolean m_218291_(EntityType<Wolf> p_218292_, LevelAccessor p_218293_, MobSpawnType p_218294_, BlockPos p_218295_, RandomSource p_218296_) {
        return p_218293_.m_8055_(p_218295_.m_7495_()).m_204336_(BlockTags.f_184236_) && Wolf.m_186209_(p_218293_, p_218295_);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    class WolfPanicGoal
    extends PanicGoal {
        public WolfPanicGoal(double p_203124_) {
            super(Wolf.this, p_203124_);
        }

        @Override
        protected boolean m_202729_() {
            return this.f_25684_.m_203117_() || this.f_25684_.m_6060_();
        }
    }

    class WolfAvoidEntityGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {
        private final Wolf f_30451_;

        public WolfAvoidEntityGoal(Wolf p_30454_, Class<T> p_30455_, float p_30456_, double p_30457_, double p_30458_) {
            super(p_30454_, p_30455_, p_30456_, p_30457_, p_30458_);
            this.f_30451_ = p_30454_;
        }

        @Override
        public boolean m_8036_() {
            if (super.m_8036_() && this.f_25016_ instanceof Llama) {
                return !this.f_30451_.m_21824_() && this.m_30460_((Llama)this.f_25016_);
            }
            return false;
        }

        private boolean m_30460_(Llama p_30461_) {
            return p_30461_.m_30823_() >= Wolf.this.f_19796_.m_188503_(5);
        }

        @Override
        public void m_8056_() {
            Wolf.this.m_6710_(null);
            super.m_8056_();
        }

        @Override
        public void m_8037_() {
            Wolf.this.m_6710_(null);
            super.m_8037_();
        }
    }
}

