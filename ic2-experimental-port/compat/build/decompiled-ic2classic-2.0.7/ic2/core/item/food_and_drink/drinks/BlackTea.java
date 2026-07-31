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

import ic2.core.item.food_and_drink.drinks.BaseTea;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlackTea
extends BaseTea {
    public BlackTea() {
        super("black_tea");
    }

    @Override
    public boolean applyEffects(ItemStack stack, Level world, Player player) {
        this.applyEffect(MobEffects.f_19596_, player, 100, 1200, 0);
        this.applyEffect(MobEffects.f_19598_, player, 100, 1200, 0);
        return super.applyEffects(stack, world, player);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + "/black_tea");
    }
}

