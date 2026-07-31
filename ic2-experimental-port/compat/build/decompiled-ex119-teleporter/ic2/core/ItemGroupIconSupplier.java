/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core;

import ic2.core.IC2;
import ic2.core.ref.Ic2Items;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

final class ItemGroupIconSupplier
implements Supplier<ItemStack> {
    private static ItemStack a;
    private static ItemStack b;
    private static ItemStack z;
    private int ticker;

    ItemGroupIconSupplier() {
    }

    @Override
    public ItemStack get() {
        if (IC2.seasonal) {
            if (a == null) {
                a = new ItemStack((ItemLike)Items.f_42681_);
            }
            if (b == null) {
                b = new ItemStack((ItemLike)Items.f_42678_);
            }
            if (z == null) {
                z = Ic2Items.NANO_CHESTPLATE.m_7968_();
            }
            if (++this.ticker >= 5000) {
                this.ticker = 0;
            }
            if (this.ticker >= 2500) {
                return this.ticker < 3000 ? a : (this.ticker < 4500 ? b : z);
            }
        }
        return new ItemStack((ItemLike)Ic2Items.MINING_LASER).m_41714_(Component.m_130674_((String)"ic2:tab_icon"));
    }
}

