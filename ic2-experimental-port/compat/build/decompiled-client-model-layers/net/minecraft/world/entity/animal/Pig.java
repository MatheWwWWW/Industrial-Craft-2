/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Pig
extends Animal
implements ItemSteerable,
Saddleable {
    private static final EntityDataAccessor<Boolean> f_29456_ = SynchedEntityData.m_135353_(Pig.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_29457_ = SynchedEntityData.m_135353_(Pig.class, EntityDataSerializers.f_135028_);
    private static final Ingredient f_29458_ = Ingredient.m_43929_(Items.f_42619_, Items.f_42620_, Items.f_42732_);
    private final ItemBasedSteering f_29459_;

    public Pig(EntityType<? extends Pig> p_29462_, Level p_29463_) {
        super((EntityType<? extends Animal>)p_29462_, p_29463_);
        this.f_29459_ = new ItemBasedSteering(this.f_19804_, f_29457_, f_29456_);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new PanicGoal(this, 1.25));
        this.f_21345_.m_25352_(3, new BreedGoal(this, 1.0));
        this.f_21345_.m_25352_(4, new TemptGoal(this, 1.2, Ingredient.m_43929_(Items.f_42684_), false));
        this.f_21345_.m_25352_(4, new TemptGoal(this, 1.2, f_29458_, false));
        this.f_21345_.m_25352_(5, new FollowParentGoal(this, 1.1));
        this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder m_29503_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22279_, 0.25);
    }

    @Override
    @Nullable
    public Entity m_6688_() {
        Entity $$0 = this.m_146895_();
        return $$0 != null && this.m_218247_($$0) ? $$0 : null;
    }

    private boolean m_218247_(Entity p_218248_) {
        if (this.m_6254_() && p_218248_ instanceof Player) {
            Player $$1 = (Player)p_218248_;
            return $$1.m_21205_().m_150930_(Items.f_42684_) || $$1.m_21206_().m_150930_(Items.f_42684_);
        }
        return false;
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_29480_) {
        if (f_29457_.equals(p_29480_) && this.f_19853_.f_46443_) {
            this.f_29459_.m_20844_();
        }
        super.m_7350_(p_29480_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_29456_, false);
        this.f_19804_.m_135372_(f_29457_, 0);
    }

    @Override
    public void m_7380_(CompoundTag p_29495_) {
        super.m_7380_(p_29495_);
        this.f_29459_.m_20847_(p_29495_);
    }

    @Override
    public void m_7378_(CompoundTag p_29478_) {
        super.m_7378_(p_29478_);
        this.f_29459_.m_20852_(p_29478_);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12233_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_29502_) {
        return SoundEvents.f_12235_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12234_;
    }

    @Override
    protected void m_7355_(BlockPos p_29492_, BlockState p_29493_) {
        this.m_5496_(SoundEvents.f_12237_, 0.15f, 1.0f);
    }

    @Override
    public InteractionResult m_6071_(Player p_29489_, InteractionHand p_29490_) {
        boolean $$2 = this.m_6898_(p_29489_.m_21120_(p_29490_));
        if (!$$2 && this.m_6254_() && !this.m_20160_() && !p_29489_.m_36341_()) {
            if (!this.f_19853_.f_46443_) {
                p_29489_.m_20329_(this);
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        InteractionResult $$3 = super.m_6071_(p_29489_, p_29490_);
        if (!$$3.m_19077_()) {
            ItemStack $$4 = p_29489_.m_21120_(p_29490_);
            if ($$4.m_150930_(Items.f_42450_)) {
                return $$4.m_41647_(p_29489_, this, p_29490_);
            }
            return InteractionResult.PASS;
        }
        return $$3;
    }

    @Override
    public boolean m_6741_() {
        return this.m_6084_() && !this.m_6162_();
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        if (this.m_6254_()) {
            this.m_19998_(Items.f_42450_);
        }
    }

    @Override
    public boolean m_6254_() {
        return this.f_29459_.m_20851_();
    }

    @Override
    public void m_5853_(@Nullable SoundSource p_29476_) {
        this.f_29459_.m_20849_(true);
        if (p_29476_ != null) {
            this.f_19853_.m_6269_(null, this, SoundEvents.f_12236_, p_29476_, 0.5f, 1.0f);
        }
    }

    @Override
    public Vec3 m_7688_(LivingEntity p_29487_) {
        Direction $$1 = this.m_6374_();
        if ($$1.m_122434_() == Direction.Axis.Y) {
            return super.m_7688_(p_29487_);
        }
        int[][] $$2 = DismountHelper.m_38467_($$1);
        BlockPos $$3 = this.m_20183_();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (Pose $$5 : p_29487_.m_7431_()) {
            AABB $$6 = p_29487_.m_21270_($$5);
            for (int[] $$7 : $$2) {
                Vec3 $$9;
                $$4.m_122178_($$3.m_123341_() + $$7[0], $$3.m_123342_(), $$3.m_123343_() + $$7[1]);
                double $$8 = this.f_19853_.m_45573_($$4);
                if (!DismountHelper.m_38439_($$8) || !DismountHelper.m_38456_(this.f_19853_, p_29487_, $$6.m_82383_($$9 = Vec3.m_82514_($$4, $$8)))) continue;
                p_29487_.m_20124_($$5);
                return $$9;
            }
        }
        return super.m_7688_(p_29487_);
    }

    @Override
    public void m_8038_(ServerLevel p_29473_, LightningBolt p_29474_) {
        if (p_29473_.m_46791_() != Difficulty.PEACEFUL) {
            ZombifiedPiglin $$2 = EntityType.f_20531_.m_20615_(p_29473_);
            $$2.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42430_));
            $$2.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
            $$2.m_21557_(this.m_21525_());
            $$2.m_6863_(this.m_6162_());
            if (this.m_8077_()) {
                $$2.m_6593_(this.m_7770_());
                $$2.m_20340_(this.m_20151_());
            }
            $$2.m_21530_();
            p_29473_.m_7967_($$2);
            this.m_146870_();
        } else {
            super.m_8038_(p_29473_, p_29474_);
        }
    }

    @Override
    public void m_7023_(Vec3 p_29506_) {
        this.m_20854_(this, this.f_29459_, p_29506_);
    }

    @Override
    public float m_6748_() {
        return (float)this.m_21133_(Attributes.f_22279_) * 0.225f;
    }

    @Override
    public void m_7760_(Vec3 p_29482_) {
        super.m_7023_(p_29482_);
    }

    @Override
    public boolean m_6746_() {
        return this.f_29459_.m_217032_(this.m_217043_());
    }

    @Override
    public Pig m_142606_(ServerLevel p_149001_, AgeableMob p_149002_) {
        return EntityType.f_20510_.m_20615_(p_149001_);
    }

    @Override
    public boolean m_6898_(ItemStack p_29508_) {
        return f_29458_.test(p_29508_);
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.6f * this.m_20192_(), this.m_20205_() * 0.4f);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }
}

