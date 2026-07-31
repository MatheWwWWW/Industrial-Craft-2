/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.npc;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface InventoryCarrier {
    public SimpleContainer m_35311_();

    public static void m_219611_(Mob p_219612_, InventoryCarrier p_219613_, ItemEntity p_219614_) {
        ItemStack $$3 = p_219614_.m_32055_();
        if (p_219612_.m_7243_($$3)) {
            SimpleContainer $$4 = p_219613_.m_35311_();
            boolean $$5 = $$4.m_19183_($$3);
            if (!$$5) {
                return;
            }
            p_219612_.m_21053_(p_219614_);
            int $$6 = $$3.m_41613_();
            ItemStack $$7 = $$4.m_19173_($$3);
            p_219612_.m_7938_(p_219614_, $$6 - $$7.m_41613_());
            if ($$7.m_41619_()) {
                p_219614_.m_146870_();
            } else {
                $$3.m_41764_($$7.m_41613_());
            }
        }
    }
}

