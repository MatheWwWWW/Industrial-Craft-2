/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.readers;

import net.minecraft.world.item.ItemStack;

public interface IEUReader {
    public boolean isEUReader(ItemStack var1);

    public static boolean isEUReaderImpl(ItemStack stack) {
        return stack.m_41720_() instanceof IEUReader && ((IEUReader)stack.m_41720_()).isEUReader(stack);
    }
}

