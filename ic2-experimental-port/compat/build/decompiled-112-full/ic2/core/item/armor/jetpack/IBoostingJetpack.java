/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item.armor.jetpack;

import ic2.core.item.armor.jetpack.IJetpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBoostingJetpack
extends IJetpack {
    public float getBaseThrust(ItemStack var1, boolean var2);

    public float getBoostThrust(EntityPlayer var1, ItemStack var2, boolean var3);

    public boolean useBoostPower(ItemStack var1, float var2);

    public float getHoverBoost(EntityPlayer var1, ItemStack var2, boolean var3);
}

