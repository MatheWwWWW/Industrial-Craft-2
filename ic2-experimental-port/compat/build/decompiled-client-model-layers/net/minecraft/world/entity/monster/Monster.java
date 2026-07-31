/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;

public abstract class Monster
extends PathfinderMob
implements Enemy {
    protected Monster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super((EntityType<? extends PathfinderMob>)p_33002_, p_33003_);
        this.f_21364_ = 5;
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.HOSTILE;
    }

    @Override
    public void m_8107_() {
        this.m_21203_();
        this.m_7562_();
        super.m_8107_();
    }

    protected void m_7562_() {
        float $$0 = this.m_213856_();
        if ($$0 > 0.5f) {
            this.f_20891_ += 2;
        }
    }

    @Override
    protected boolean m_8028_() {
        return true;
    }

    @Override
    protected SoundEvent m_5501_() {
        return SoundEvents.f_12042_;
    }

    @Override
    protected SoundEvent m_5509_() {
        return SoundEvents.f_12041_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33034_) {
        return SoundEvents.f_12039_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12038_;
    }

    @Override
    public LivingEntity.Fallsounds m_196493_() {
        return new LivingEntity.Fallsounds(SoundEvents.f_12040_, SoundEvents.f_12037_);
    }

    @Override
    public float m_5610_(BlockPos p_33013_, LevelReader p_33014_) {
        return -p_33014_.m_220419_(p_33013_);
    }

    public static boolean m_219009_(ServerLevelAccessor p_219010_, BlockPos p_219011_, RandomSource p_219012_) {
        if (p_219010_.m_45517_(LightLayer.SKY, p_219011_) > p_219012_.m_188503_(32)) {
            return false;
        }
        DimensionType $$3 = p_219010_.m_6042_();
        int $$4 = $$3.m_223570_();
        if ($$4 < 15 && p_219010_.m_45517_(LightLayer.BLOCK, p_219011_) > $$4) {
            return false;
        }
        int $$5 = p_219010_.m_6018_().m_46470_() ? p_219010_.m_46849_(p_219011_, 10) : p_219010_.m_46803_(p_219011_);
        return $$5 <= $$3.m_223569_().m_214085_(p_219012_);
    }

    public static boolean m_219013_(EntityType<? extends Monster> p_219014_, ServerLevelAccessor p_219015_, MobSpawnType p_219016_, BlockPos p_219017_, RandomSource p_219018_) {
        return p_219015_.m_46791_() != Difficulty.PEACEFUL && Monster.m_219009_(p_219015_, p_219017_, p_219018_) && Monster.m_217057_(p_219014_, p_219015_, p_219016_, p_219017_, p_219018_);
    }

    public static boolean m_219019_(EntityType<? extends Monster> p_219020_, LevelAccessor p_219021_, MobSpawnType p_219022_, BlockPos p_219023_, RandomSource p_219024_) {
        return p_219021_.m_46791_() != Difficulty.PEACEFUL && Monster.m_217057_(p_219020_, p_219021_, p_219022_, p_219023_, p_219024_);
    }

    public static AttributeSupplier.Builder m_33035_() {
        return Mob.m_21552_().m_22266_(Attributes.f_22281_);
    }

    @Override
    public boolean m_6149_() {
        return true;
    }

    @Override
    protected boolean m_6125_() {
        return true;
    }

    public boolean m_6935_(Player p_33036_) {
        return true;
    }

    @Override
    public ItemStack m_6298_(ItemStack p_33038_) {
        if (p_33038_.m_41720_() instanceof ProjectileWeaponItem) {
            Predicate<ItemStack> $$1 = ((ProjectileWeaponItem)p_33038_.m_41720_()).m_6442_();
            ItemStack $$2 = ProjectileWeaponItem.m_43010_(this, $$1);
            return $$2.m_41619_() ? new ItemStack(Items.f_42412_) : $$2;
        }
        return ItemStack.f_41583_;
    }
}

