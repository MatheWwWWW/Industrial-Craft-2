/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.platform.recipes.villager;

import ic2.api.crops.ICrop;
import ic2.core.platform.recipes.villager.CropTradeComp;
import ic2.core.platform.recipes.villager.ElemeraldTradeComp;
import ic2.core.platform.recipes.villager.ItemTradeComp;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface ITradeComp {
    public ItemStack getItem(RandomSource var1);

    public Target getTarget();

    public static ITradeComp emeralds(int amount, Target target) {
        return new ElemeraldTradeComp(amount, target);
    }

    public static ITradeComp emeralds(int min, int max, Target target) {
        return new ElemeraldTradeComp(min, max, target);
    }

    public static ITradeComp item(ItemLike item, Target target) {
        return new ItemTradeComp(item, 1, target);
    }

    public static ITradeComp item(ItemLike item, int count, Target target) {
        return new ItemTradeComp(item, count, target);
    }

    public static ITradeComp item(ItemLike item, int min, int max, Target target) {
        return new ItemTradeComp(item, min, max, target);
    }

    public static ITradeComp item(ItemStack item, Target target) {
        return new ItemTradeComp(item, 1, target);
    }

    public static ITradeComp item(ItemStack item, int count, Target target) {
        return new ItemTradeComp(item, count, target);
    }

    public static ITradeComp item(ItemStack item, int min, int max, Target target) {
        return new ItemTradeComp(item, min, max, target);
    }

    public static ITradeComp crop(ICrop crop, int stat, boolean scan, Target target) {
        return new CropTradeComp(crop, stat, scan, target);
    }

    public static ITradeComp crop(ICrop crop, int min, int max, boolean scan, Target target) {
        return new CropTradeComp(crop, min, max, scan, target);
    }

    public static enum Target {
        MAIN,
        SUB,
        OUT;

    }
}

