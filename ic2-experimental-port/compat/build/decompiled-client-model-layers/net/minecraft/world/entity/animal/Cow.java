/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Cow
extends Animal {
    public Cow(EntityType<? extends Cow> p_28285_, Level p_28286_) {
        super((EntityType<? extends Animal>)p_28285_, p_28286_);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new PanicGoal(this, 2.0));
        this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0));
        this.f_21345_.m_25352_(3, new TemptGoal(this, 1.25, Ingredient.m_43929_(Items.f_42405_), false));
        this.f_21345_.m_25352_(4, new FollowParentGoal(this, 1.25));
        this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder m_28307_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22279_, 0.2f);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11830_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_28306_) {
        return SoundEvents.f_11832_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11831_;
    }

    @Override
    protected void m_7355_(BlockPos p_28301_, BlockState p_28302_) {
        this.m_5496_(SoundEvents.f_11834_, 0.15f, 1.0f);
    }

    @Override
    protected float m_6121_() {
        return 0.4f;
    }

    @Override
    public InteractionResult m_6071_(Player p_28298_, InteractionHand p_28299_) {
        ItemStack $$2 = p_28298_.m_21120_(p_28299_);
        if ($$2.m_150930_(Items.f_42446_) && !this.m_6162_()) {
            p_28298_.m_5496_(SoundEvents.f_11833_, 1.0f, 1.0f);
            ItemStack $$3 = ItemUtils.m_41813_($$2, p_28298_, Items.f_42455_.m_7968_());
            p_28298_.m_21008_(p_28299_, $$3);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return super.m_6071_(p_28298_, p_28299_);
    }

    @Override
    public Cow m_142606_(ServerLevel p_148890_, AgeableMob p_148891_) {
        return EntityType.f_20557_.m_20615_(p_148890_);
    }

    @Override
    protected float m_6431_(Pose p_28295_, EntityDimensions p_28296_) {
        if (this.m_6162_()) {
            return p_28296_.f_20378_ * 0.95f;
        }
        return 1.3f;
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }
}

