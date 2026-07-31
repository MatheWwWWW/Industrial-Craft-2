/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.food_and_drink.drinks;

import ic2.api.items.IDrinkableFluid;
import ic2.core.IC2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HotCocoa
extends IDrinkableFluid {
    public HotCocoa() {
        super(new ResourceLocation(IC2.MODID, "hot_cocoa"));
    }

    @Override
    public boolean drink(ItemStack stack, Level world, Player player) {
        player.m_36324_().m_38707_(1, 0.0f);
        player.m_5634_(1.0f);
        return true;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + "/hot_cocoa");
    }
}

