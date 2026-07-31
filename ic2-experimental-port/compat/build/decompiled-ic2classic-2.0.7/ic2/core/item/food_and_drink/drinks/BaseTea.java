/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.food_and_drink.drinks;

import ic2.api.items.IDrinkableFluid;
import ic2.core.IC2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class BaseTea
extends IDrinkableFluid {
    public BaseTea(String name) {
        super(new ResourceLocation(IC2.MODID, name));
    }

    @Override
    public boolean drink(ItemStack stack, Level world, Player player) {
        return this.applyEffects(stack, world, player);
    }

    public boolean applyEffects(ItemStack stack, Level world, Player player) {
        this.decreaseEffect(MobEffects.f_19599_, player, 600);
        this.decreaseEffect(MobEffects.f_19597_, player, 600);
        this.decreaseEffect(MobEffects.f_19604_, player, 1200);
        this.decreaseEffect(MobEffects.f_19612_, player, 2400);
        IC2.PLATFORM.resetSleep(player);
        return true;
    }

    public void applyEffect(MobEffect effect, Player player, int duration, int maxDuration, int amplifier) {
        MobEffectInstance currentEffect = player.m_21124_(effect);
        if (currentEffect == null) {
            currentEffect = new MobEffectInstance(effect, duration, amplifier);
        } else {
            if (currentEffect.m_19564_() > amplifier) {
                return;
            }
            currentEffect = new MobEffectInstance(effect, Math.max(maxDuration, currentEffect.m_19557_() + duration), amplifier);
        }
        player.m_7292_(currentEffect);
    }

    public void decreaseEffect(MobEffect effect, Player player, int decreaseDuration) {
        if (player.m_21023_(effect)) {
            MobEffectInstance currentEffect = player.m_21124_(effect);
            if (currentEffect.m_19557_() <= decreaseDuration) {
                player.m_6234_(effect);
                return;
            }
            currentEffect = new MobEffectInstance(currentEffect.m_19544_(), currentEffect.m_19557_() - decreaseDuration, currentEffect.m_19564_(), currentEffect.m_19571_(), currentEffect.m_19572_());
            player.m_6234_(effect);
            player.m_7292_(currentEffect);
        }
    }
}

