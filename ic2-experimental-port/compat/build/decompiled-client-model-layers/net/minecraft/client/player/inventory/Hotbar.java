/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ForwardingList
 */
package net.minecraft.client.player.inventory;

import com.google.common.collect.ForwardingList;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class Hotbar
extends ForwardingList<ItemStack> {
    private final NonNullList<ItemStack> f_108780_ = NonNullList.m_122780_(Inventory.m_36059_(), ItemStack.f_41583_);

    protected List<ItemStack> delegate() {
        return this.f_108780_;
    }

    public ListTag m_108782_() {
        ListTag $$0 = new ListTag();
        for (ItemStack $$1 : this.delegate()) {
            $$0.add($$1.m_41739_(new CompoundTag()));
        }
        return $$0;
    }

    public void m_108783_(ListTag p_108784_) {
        Collection $$1 = this.delegate();
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            $$1.set($$2, ItemStack.m_41712_(p_108784_.m_128728_($$2)));
        }
    }

    public boolean isEmpty() {
        for (ItemStack $$0 : this.delegate()) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        return true;
    }
}

