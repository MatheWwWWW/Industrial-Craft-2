/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.readers;

import net.minecraft.world.item.ItemStack;

public interface ICropReader {
    public boolean isCropReader(ItemStack var1);

    public static boolean isCropReaderImpl(ItemStack stack) {
        return stack.m_41720_() instanceof ICropReader && ((ICropReader)stack.m_41720_()).isCropReader(stack);
    }
}

