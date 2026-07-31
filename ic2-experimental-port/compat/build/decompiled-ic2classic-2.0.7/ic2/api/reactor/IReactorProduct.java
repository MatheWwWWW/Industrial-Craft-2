/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.reactor;

import ic2.api.reactor.IReactor;
import net.minecraft.world.item.ItemStack;

public interface IReactorProduct {
    public boolean isValidForReactor(ItemStack var1, IReactor var2);
}

