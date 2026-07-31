/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface DyeableLeatherItem {
    public static final String f_150826_ = "color";
    public static final String f_150827_ = "display";
    public static final int f_150828_ = 10511680;

    default public boolean m_41113_(ItemStack p_41114_) {
        CompoundTag $$1 = p_41114_.m_41737_(f_150827_);
        return $$1 != null && $$1.m_128425_(f_150826_, 99);
    }

    default public int m_41121_(ItemStack p_41122_) {
        CompoundTag $$1 = p_41122_.m_41737_(f_150827_);
        if ($$1 != null && $$1.m_128425_(f_150826_, 99)) {
            return $$1.m_128451_(f_150826_);
        }
        return 10511680;
    }

    default public void m_41123_(ItemStack p_41124_) {
        CompoundTag $$1 = p_41124_.m_41737_(f_150827_);
        if ($$1 != null && $$1.m_128441_(f_150826_)) {
            $$1.m_128473_(f_150826_);
        }
    }

    default public void m_41115_(ItemStack p_41116_, int p_41117_) {
        p_41116_.m_41698_(f_150827_).m_128405_(f_150826_, p_41117_);
    }

    public static ItemStack m_41118_(ItemStack p_41119_, List<DyeItem> p_41120_) {
        ItemStack $$2 = ItemStack.f_41583_;
        int[] $$3 = new int[3];
        int $$4 = 0;
        int $$5 = 0;
        DyeableLeatherItem $$6 = null;
        Item $$7 = p_41119_.m_41720_();
        if ($$7 instanceof DyeableLeatherItem) {
            $$6 = (DyeableLeatherItem)((Object)$$7);
            $$2 = p_41119_.m_41777_();
            $$2.m_41764_(1);
            if ($$6.m_41113_(p_41119_)) {
                int $$8 = $$6.m_41121_($$2);
                float $$9 = (float)($$8 >> 16 & 0xFF) / 255.0f;
                float $$10 = (float)($$8 >> 8 & 0xFF) / 255.0f;
                float $$11 = (float)($$8 & 0xFF) / 255.0f;
                $$4 += (int)(Math.max($$9, Math.max($$10, $$11)) * 255.0f);
                $$3[0] = $$3[0] + (int)($$9 * 255.0f);
                $$3[1] = $$3[1] + (int)($$10 * 255.0f);
                $$3[2] = $$3[2] + (int)($$11 * 255.0f);
                ++$$5;
            }
            for (DyeItem $$12 : p_41120_) {
                float[] $$13 = $$12.m_41089_().m_41068_();
                int $$14 = (int)($$13[0] * 255.0f);
                int $$15 = (int)($$13[1] * 255.0f);
                int $$16 = (int)($$13[2] * 255.0f);
                $$4 += Math.max($$14, Math.max($$15, $$16));
                $$3[0] = $$3[0] + $$14;
                $$3[1] = $$3[1] + $$15;
                $$3[2] = $$3[2] + $$16;
                ++$$5;
            }
        }
        if ($$6 == null) {
            return ItemStack.f_41583_;
        }
        int $$17 = $$3[0] / $$5;
        int $$18 = $$3[1] / $$5;
        int $$19 = $$3[2] / $$5;
        float $$20 = (float)$$4 / (float)$$5;
        float $$21 = Math.max($$17, Math.max($$18, $$19));
        $$17 = (int)((float)$$17 * $$20 / $$21);
        $$18 = (int)((float)$$18 * $$20 / $$21);
        $$19 = (int)((float)$$19 * $$20 / $$21);
        int $$22 = $$17;
        $$22 = ($$22 << 8) + $$18;
        $$22 = ($$22 << 8) + $$19;
        $$6.m_41115_($$2, $$22);
        return $$2;
    }
}

