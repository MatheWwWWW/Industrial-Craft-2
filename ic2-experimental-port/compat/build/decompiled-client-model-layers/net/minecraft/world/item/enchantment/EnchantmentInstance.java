/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentInstance
extends WeightedEntry.IntrusiveBase {
    public final Enchantment f_44947_;
    public final int f_44948_;

    public EnchantmentInstance(Enchantment p_44950_, int p_44951_) {
        super(p_44950_.m_44699_().m_44716_());
        this.f_44947_ = p_44950_;
        this.f_44948_ = p_44951_;
    }
}

