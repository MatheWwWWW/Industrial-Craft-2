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

public class Coffee
extends IDrinkableFluid {
    int extendDuration = 600;
    int maxAmplifier = 1;

    public Coffee(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean drink(ItemStack stack, Level world, Player player) {
        this.amplifyEffect(player, MobEffects.f_19596_);
        this.amplifyEffect(player, MobEffects.f_19598_);
        IC2.PLATFORM.resetSleep(player);
        return true;
    }

    public int amplifyEffect(Player player, MobEffect effect) {
        MobEffectInstance activeEffect = player.m_21124_(effect);
        if (activeEffect != null) {
            int amplifier = activeEffect.m_19564_();
            int duration = activeEffect.m_19557_();
            if (amplifier < this.maxAmplifier) {
                ++amplifier;
            }
            activeEffect.m_19558_(new MobEffectInstance(activeEffect.m_19544_(), duration += this.extendDuration, amplifier));
            return amplifier;
        }
        player.m_7292_(new MobEffectInstance(effect, 300, 0));
        return 1;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + "/coffee");
    }
}

