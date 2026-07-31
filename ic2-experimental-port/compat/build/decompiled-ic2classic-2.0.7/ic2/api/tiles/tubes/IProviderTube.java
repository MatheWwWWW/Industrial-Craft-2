/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.tiles.tubes;

import ic2.api.tiles.tubes.ITube;
import java.util.UUID;
import java.util.function.ObjIntConsumer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public interface IProviderTube
extends ITube {
    public int provideItems(ItemStack var1, int var2, DyeColor var3, UUID var4);

    public void getItemsProvided(ObjIntConsumer<ItemStack> var1);

    public long getProviderSource();
}

