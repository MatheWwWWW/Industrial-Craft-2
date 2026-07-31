/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;

public class MerchantResultSlot
extends Slot {
    private final MerchantContainer f_40078_;
    private final Player f_40079_;
    private int f_40080_;
    private final Merchant f_40081_;

    public MerchantResultSlot(Player p_40083_, Merchant p_40084_, MerchantContainer p_40085_, int p_40086_, int p_40087_, int p_40088_) {
        super(p_40085_, p_40086_, p_40087_, p_40088_);
        this.f_40079_ = p_40083_;
        this.f_40081_ = p_40084_;
        this.f_40078_ = p_40085_;
    }

    @Override
    public boolean m_5857_(ItemStack p_40095_) {
        return false;
    }

    @Override
    public ItemStack m_6201_(int p_40090_) {
        if (this.m_6657_()) {
            this.f_40080_ += Math.min(p_40090_, this.m_7993_().m_41613_());
        }
        return super.m_6201_(p_40090_);
    }

    @Override
    protected void m_7169_(ItemStack p_40097_, int p_40098_) {
        this.f_40080_ += p_40098_;
        this.m_5845_(p_40097_);
    }

    @Override
    protected void m_5845_(ItemStack p_40100_) {
        p_40100_.m_41678_(this.f_40079_.f_19853_, this.f_40079_, this.f_40080_);
        this.f_40080_ = 0;
    }

    @Override
    public void m_142406_(Player p_150631_, ItemStack p_150632_) {
        this.m_5845_(p_150632_);
        MerchantOffer $$2 = this.f_40078_.m_40025_();
        if ($$2 != null) {
            ItemStack $$4;
            ItemStack $$3 = this.f_40078_.m_8020_(0);
            if ($$2.m_45361_($$3, $$4 = this.f_40078_.m_8020_(1)) || $$2.m_45361_($$4, $$3)) {
                this.f_40081_.m_6996_($$2);
                p_150631_.m_36220_(Stats.f_12941_);
                this.f_40078_.m_6836_(0, $$3);
                this.f_40078_.m_6836_(1, $$4);
            }
            this.f_40081_.m_6621_(this.f_40081_.m_7809_() + $$2.m_45379_());
        }
    }
}

