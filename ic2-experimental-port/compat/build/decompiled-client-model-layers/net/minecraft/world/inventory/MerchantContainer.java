/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantContainer
implements Container {
    private final Merchant f_39997_;
    private final NonNullList<ItemStack> f_39998_ = NonNullList.m_122780_(3, ItemStack.f_41583_);
    @Nullable
    private MerchantOffer f_39999_;
    private int f_40000_;
    private int f_40001_;

    public MerchantContainer(Merchant p_40003_) {
        this.f_39997_ = p_40003_;
    }

    @Override
    public int m_6643_() {
        return this.f_39998_.size();
    }

    @Override
    public boolean m_7983_() {
        for (ItemStack $$0 : this.f_39998_) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack m_8020_(int p_40008_) {
        return this.f_39998_.get(p_40008_);
    }

    @Override
    public ItemStack m_7407_(int p_40010_, int p_40011_) {
        ItemStack $$2 = this.f_39998_.get(p_40010_);
        if (p_40010_ == 2 && !$$2.m_41619_()) {
            return ContainerHelper.m_18969_(this.f_39998_, p_40010_, $$2.m_41613_());
        }
        ItemStack $$3 = ContainerHelper.m_18969_(this.f_39998_, p_40010_, p_40011_);
        if (!$$3.m_41619_() && this.m_40022_(p_40010_)) {
            this.m_40024_();
        }
        return $$3;
    }

    private boolean m_40022_(int p_40023_) {
        return p_40023_ == 0 || p_40023_ == 1;
    }

    @Override
    public ItemStack m_8016_(int p_40018_) {
        return ContainerHelper.m_18966_(this.f_39998_, p_40018_);
    }

    @Override
    public void m_6836_(int p_40013_, ItemStack p_40014_) {
        this.f_39998_.set(p_40013_, p_40014_);
        if (!p_40014_.m_41619_() && p_40014_.m_41613_() > this.m_6893_()) {
            p_40014_.m_41764_(this.m_6893_());
        }
        if (this.m_40022_(p_40013_)) {
            this.m_40024_();
        }
    }

    @Override
    public boolean m_6542_(Player p_40016_) {
        return this.f_39997_.m_7962_() == p_40016_;
    }

    @Override
    public void m_6596_() {
        this.m_40024_();
    }

    public void m_40024_() {
        ItemStack $$3;
        ItemStack $$2;
        this.f_39999_ = null;
        if (this.f_39998_.get(0).m_41619_()) {
            ItemStack $$0 = this.f_39998_.get(1);
            ItemStack $$1 = ItemStack.f_41583_;
        } else {
            $$2 = this.f_39998_.get(0);
            $$3 = this.f_39998_.get(1);
        }
        if ($$2.m_41619_()) {
            this.m_6836_(2, ItemStack.f_41583_);
            this.f_40001_ = 0;
            return;
        }
        MerchantOffers $$4 = this.f_39997_.m_6616_();
        if (!$$4.isEmpty()) {
            MerchantOffer $$5 = $$4.m_45389_($$2, $$3, this.f_40000_);
            if ($$5 == null || $$5.m_45380_()) {
                this.f_39999_ = $$5;
                $$5 = $$4.m_45389_($$3, $$2, this.f_40000_);
            }
            if ($$5 != null && !$$5.m_45380_()) {
                this.f_39999_ = $$5;
                this.m_6836_(2, $$5.m_45370_());
                this.f_40001_ = $$5.m_45379_();
            } else {
                this.m_6836_(2, ItemStack.f_41583_);
                this.f_40001_ = 0;
            }
        }
        this.f_39997_.m_7713_(this.m_8020_(2));
    }

    @Nullable
    public MerchantOffer m_40025_() {
        return this.f_39999_;
    }

    public void m_40020_(int p_40021_) {
        this.f_40000_ = p_40021_;
        this.m_40024_();
    }

    @Override
    public void m_6211_() {
        this.f_39998_.clear();
    }

    public int m_40026_() {
        return this.f_40001_;
    }
}

