/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.food_and_drink.drinks;

import ic2.core.IC2;
import ic2.core.item.food_and_drink.drinks.BaseTea;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ColdBlackTea
extends BaseTea {
    public ColdBlackTea() {
        super("cold_black_tea");
    }

    @Override
    public boolean applyEffects(ItemStack stack, Level world, Player player) {
        this.decreaseEffect(MobEffects.f_19599_, player, 300);
        this.decreaseEffect(MobEffects.f_19597_, player, 300);
        this.decreaseEffect(MobEffects.f_19604_, player, 600);
        this.decreaseEffect(MobEffects.f_19612_, player, 1200);
        this.applyEffect(MobEffects.f_19596_, player, 50, 600, 0);
        this.applyEffect(MobEffects.f_19598_, player, 50, 600, 0);
        IC2.PLATFORM.resetSleep(player);
        return true;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + "/cold_black_tea");
    }
}

