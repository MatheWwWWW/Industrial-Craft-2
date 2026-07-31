/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface Container
extends Clearable {
    public static final int f_146642_ = 64;

    public int m_6643_();

    public boolean m_7983_();

    public ItemStack m_8020_(int var1);

    public ItemStack m_7407_(int var1, int var2);

    public ItemStack m_8016_(int var1);

    public void m_6836_(int var1, ItemStack var2);

    default public int m_6893_() {
        return 64;
    }

    public void m_6596_();

    public boolean m_6542_(Player var1);

    default public void m_5856_(Player p_18955_) {
    }

    default public void m_5785_(Player p_18954_) {
    }

    default public boolean m_7013_(int p_18952_, ItemStack p_18953_) {
        return true;
    }

    default public int m_18947_(Item p_18948_) {
        int $$1 = 0;
        for (int $$2 = 0; $$2 < this.m_6643_(); ++$$2) {
            ItemStack $$3 = this.m_8020_($$2);
            if (!$$3.m_41720_().equals(p_18948_)) continue;
            $$1 += $$3.m_41613_();
        }
        return $$1;
    }

    default public boolean m_18949_(Set<Item> p_18950_) {
        return this.m_216874_(p_216873_ -> !p_216873_.m_41619_() && p_18950_.contains(p_216873_.m_41720_()));
    }

    default public boolean m_216874_(Predicate<ItemStack> p_216875_) {
        for (int $$1 = 0; $$1 < this.m_6643_(); ++$$1) {
            ItemStack $$2 = this.m_8020_($$1);
            if (!p_216875_.test($$2)) continue;
            return true;
        }
        return false;
    }
}

