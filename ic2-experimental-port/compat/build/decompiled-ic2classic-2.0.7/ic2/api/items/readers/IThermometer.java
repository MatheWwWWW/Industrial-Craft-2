/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.readers;

import net.minecraft.world.item.ItemStack;

public interface IThermometer {
    public boolean isThermometer(ItemStack var1);

    public static boolean isThermometerImpl(ItemStack stack) {
        return stack.m_41720_() instanceof IThermometer && ((IThermometer)stack.m_41720_()).isThermometer(stack);
    }
}

