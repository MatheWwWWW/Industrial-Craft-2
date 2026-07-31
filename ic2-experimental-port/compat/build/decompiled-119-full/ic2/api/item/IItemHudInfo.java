/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import java.util.List;
import net.minecraft.world.item.ItemStack;

public interface IItemHudInfo {
    public List<String> getHudInfo(ItemStack var1, boolean var2);
}

