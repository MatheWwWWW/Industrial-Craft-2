/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item;

import ic2.core.Ic2Potion;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemTerraWart
extends Item {
    public ItemTerraWart(Item.Properties properties) {
        super(properties);
    }

    public ItemStack m_5922_(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        livingEntity.m_21195_(MobEffects.f_19604_);
        livingEntity.m_21195_(MobEffects.f_19599_);
        livingEntity.m_21195_(MobEffects.f_19612_);
        livingEntity.m_21195_(MobEffects.f_19597_);
        livingEntity.m_21195_(MobEffects.f_19613_);
        livingEntity.m_21195_(MobEffects.f_19610_);
        livingEntity.m_21195_(MobEffects.f_19614_);
        livingEntity.m_21195_(MobEffects.f_19615_);
        MobEffectInstance mobEffectInstance = livingEntity.m_21124_((MobEffect)Ic2Potion.radiation);
        if (mobEffectInstance != null) {
            if (mobEffectInstance.m_19557_() <= 600) {
                livingEntity.m_21195_((MobEffect)Ic2Potion.radiation);
            } else {
                livingEntity.m_21195_((MobEffect)Ic2Potion.radiation);
                Ic2Potion.radiation.applyTo(livingEntity, mobEffectInstance.m_19557_() - 600, mobEffectInstance.m_19564_());
            }
        }
        return super.m_5922_(itemStack, level, livingEntity);
    }
}

