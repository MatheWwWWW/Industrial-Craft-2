/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.armor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IFoamSupplier {
    public boolean canProvideFoam(Player var1, ItemStack var2, InventoryType var3, int var4);

    public void useFoam(Player var1, ItemStack var2, int var3);

    public int getFreeFoamSpace(ItemStack var1);

    public void fillFoam(ItemStack var1, int var2);

    public static enum InventoryType {
        HOTBAR,
        OFFHAND,
        ARMOR,
        CURIO;

    }
}

