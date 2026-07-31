/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import ic2.api.item.IElectricItemManager;
import net.minecraft.world.item.ItemStack;

public interface IBackupElectricItemManager
extends IElectricItemManager {
    public boolean handles(ItemStack var1);
}

