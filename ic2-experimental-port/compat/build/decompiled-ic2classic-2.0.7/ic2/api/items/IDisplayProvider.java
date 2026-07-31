/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items;

import ic2.api.tiles.display.IDisplayInfo;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;

public interface IDisplayProvider {
    public void provideInfo(ItemStack var1, Consumer<IDisplayInfo> var2);
}

