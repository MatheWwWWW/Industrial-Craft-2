/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BookItem
extends Item {
    public BookItem(Item.Properties p_40643_) {
        super(p_40643_);
    }

    @Override
    public boolean m_8120_(ItemStack p_40646_) {
        return p_40646_.m_41613_() == 1;
    }

    @Override
    public int m_6473_() {
        return 1;
    }
}

