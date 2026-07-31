/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.food_and_drink.drinks;

import ic2.core.IC2;
import ic2.core.item.food_and_drink.drinks.Coffee;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DarkCoffee
extends Coffee {
    public DarkCoffee(ResourceLocation id) {
        super(id);
        this.extendDuration = 1200;
        this.maxAmplifier = 5;
    }

    @Override
    public boolean drink(ItemStack stack, Level world, Player player) {
        int highestAmplifier = this.amplifyEffect(player, MobEffects.f_19596_);
        int currentAmplifier = this.amplifyEffect(player, MobEffects.f_19598_);
        IC2.PLATFORM.resetSleep(player);
        if (currentAmplifier > highestAmplifier) {
            highestAmplifier = currentAmplifier;
        }
        if (highestAmplifier >= 3) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19604_, (highestAmplifier - 2) * 200, 0));
            if (highestAmplifier >= 4) {
                MobEffects.f_19602_.m_19461_(null, null, (LivingEntity)player, highestAmplifier - 3, 0.25);
            }
        }
        return true;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + "/dark_coffee");
    }
}

