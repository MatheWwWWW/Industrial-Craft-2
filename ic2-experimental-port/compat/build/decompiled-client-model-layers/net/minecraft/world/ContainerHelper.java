/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class ContainerHelper {
    public static ItemStack m_18969_(List<ItemStack> p_18970_, int p_18971_, int p_18972_) {
        if (p_18971_ < 0 || p_18971_ >= p_18970_.size() || p_18970_.get(p_18971_).m_41619_() || p_18972_ <= 0) {
            return ItemStack.f_41583_;
        }
        return p_18970_.get(p_18971_).m_41620_(p_18972_);
    }

    public static ItemStack m_18966_(List<ItemStack> p_18967_, int p_18968_) {
        if (p_18968_ < 0 || p_18968_ >= p_18967_.size()) {
            return ItemStack.f_41583_;
        }
        return p_18967_.set(p_18968_, ItemStack.f_41583_);
    }

    public static CompoundTag m_18973_(CompoundTag p_18974_, NonNullList<ItemStack> p_18975_) {
        return ContainerHelper.m_18976_(p_18974_, p_18975_, true);
    }

    public static CompoundTag m_18976_(CompoundTag p_18977_, NonNullList<ItemStack> p_18978_, boolean p_18979_) {
        ListTag $$3 = new ListTag();
        for (int $$4 = 0; $$4 < p_18978_.size(); ++$$4) {
            ItemStack $$5 = p_18978_.get($$4);
            if ($$5.m_41619_()) continue;
            CompoundTag $$6 = new CompoundTag();
            $$6.m_128344_("Slot", (byte)$$4);
            $$5.m_41739_($$6);
            $$3.add($$6);
        }
        if (!$$3.isEmpty() || p_18979_) {
            p_18977_.m_128365_("Items", $$3);
        }
        return p_18977_;
    }

    public static void m_18980_(CompoundTag p_18981_, NonNullList<ItemStack> p_18982_) {
        ListTag $$2 = p_18981_.m_128437_("Items", 10);
        for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
            CompoundTag $$4 = $$2.m_128728_($$3);
            int $$5 = $$4.m_128445_("Slot") & 0xFF;
            if ($$5 < 0 || $$5 >= p_18982_.size()) continue;
            p_18982_.set($$5, ItemStack.m_41712_($$4));
        }
    }

    public static int m_18956_(Container p_18957_, Predicate<ItemStack> p_18958_, int p_18959_, boolean p_18960_) {
        int $$4 = 0;
        for (int $$5 = 0; $$5 < p_18957_.m_6643_(); ++$$5) {
            ItemStack $$6 = p_18957_.m_8020_($$5);
            int $$7 = ContainerHelper.m_18961_($$6, p_18958_, p_18959_ - $$4, p_18960_);
            if ($$7 > 0 && !p_18960_ && $$6.m_41619_()) {
                p_18957_.m_6836_($$5, ItemStack.f_41583_);
            }
            $$4 += $$7;
        }
        return $$4;
    }

    public static int m_18961_(ItemStack p_18962_, Predicate<ItemStack> p_18963_, int p_18964_, boolean p_18965_) {
        if (p_18962_.m_41619_() || !p_18963_.test(p_18962_)) {
            return 0;
        }
        if (p_18965_) {
            return p_18962_.m_41613_();
        }
        int $$4 = p_18964_ < 0 ? p_18962_.m_41613_() : Math.min(p_18964_, p_18962_.m_41613_());
        p_18962_.m_41774_($$4);
        return $$4;
    }
}

