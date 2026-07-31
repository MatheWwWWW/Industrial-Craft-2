/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import ic2.api.item.HudMode;
import net.minecraft.world.item.ItemStack;

public interface IItemHudProvider {
    public boolean doesProvideHUD(ItemStack var1);

    public HudMode getHudMode(ItemStack var1);

    public static interface IItemHudBarProvider {
        public int getBarPercent(ItemStack var1);
    }
}

