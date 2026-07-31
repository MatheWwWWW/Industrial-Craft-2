/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantMenu
extends AbstractContainerMenu {
    protected static final int f_150619_ = 0;
    protected static final int f_150620_ = 1;
    protected static final int f_150621_ = 2;
    private static final int f_150622_ = 3;
    private static final int f_150623_ = 30;
    private static final int f_150624_ = 30;
    private static final int f_150625_ = 39;
    private static final int f_150626_ = 136;
    private static final int f_150627_ = 162;
    private static final int f_150628_ = 220;
    private static final int f_150629_ = 37;
    private final Merchant f_40027_;
    private final MerchantContainer f_40028_;
    private int f_40029_;
    private boolean f_40030_;
    private boolean f_40031_;

    public MerchantMenu(int p_40033_, Inventory p_40034_) {
        this(p_40033_, p_40034_, new ClientSideMerchant(p_40034_.f_35978_));
    }

    public MerchantMenu(int p_40036_, Inventory p_40037_, Merchant p_40038_) {
        super(MenuType.f_39975_, p_40036_);
        this.f_40027_ = p_40038_;
        this.f_40028_ = new MerchantContainer(p_40038_);
        this.m_38897_(new Slot(this.f_40028_, 0, 136, 37));
        this.m_38897_(new Slot(this.f_40028_, 1, 162, 37));
        this.m_38897_(new MerchantResultSlot(p_40037_.f_35978_, p_40038_, this.f_40028_, 2, 220, 37));
        for (int $$3 = 0; $$3 < 3; ++$$3) {
            for (int $$4 = 0; $$4 < 9; ++$$4) {
                this.m_38897_(new Slot(p_40037_, $$4 + $$3 * 9 + 9, 108 + $$4 * 18, 84 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 9; ++$$5) {
            this.m_38897_(new Slot(p_40037_, $$5, 108 + $$5 * 18, 142));
        }
    }

    public void m_40048_(boolean p_40049_) {
        this.f_40030_ = p_40049_;
    }

    @Override
    public void m_6199_(Container p_40040_) {
        this.f_40028_.m_40024_();
        super.m_6199_(p_40040_);
    }

    public void m_40063_(int p_40064_) {
        this.f_40028_.m_40020_(p_40064_);
    }

    @Override
    public boolean m_6875_(Player p_40042_) {
        return this.f_40027_.m_7962_() == p_40042_;
    }

    public int m_40065_() {
        return this.f_40027_.m_7809_();
    }

    public int m_40068_() {
        return this.f_40028_.m_40026_();
    }

    public void m_40066_(int p_40067_) {
        this.f_40027_.m_6621_(p_40067_);
    }

    public int m_40071_() {
        return this.f_40029_;
    }

    public void m_40069_(int p_40070_) {
        this.f_40029_ = p_40070_;
    }

    public void m_40058_(boolean p_40059_) {
        this.f_40031_ = p_40059_;
    }

    public boolean m_40074_() {
        return this.f_40031_;
    }

    @Override
    public boolean m_5882_(ItemStack p_40044_, Slot p_40045_) {
        return false;
    }

    @Override
    public ItemStack m_7648_(Player p_40053_, int p_40054_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_40054_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_40054_ == 2) {
                if (!this.m_38903_($$4, 3, 39, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
                this.m_40077_();
            } else if (p_40054_ == 0 || p_40054_ == 1 ? !this.m_38903_($$4, 3, 39, false) : (p_40054_ >= 3 && p_40054_ < 30 ? !this.m_38903_($$4, 30, 39, false) : p_40054_ >= 30 && p_40054_ < 39 && !this.m_38903_($$4, 3, 30, false))) {
                return ItemStack.f_41583_;
            }
            if ($$4.m_41619_()) {
                $$3.m_5852_(ItemStack.f_41583_);
            } else {
                $$3.m_6654_();
            }
            if ($$4.m_41613_() == $$2.m_41613_()) {
                return ItemStack.f_41583_;
            }
            $$3.m_142406_(p_40053_, $$4);
        }
        return $$2;
    }

    private void m_40077_() {
        if (!this.f_40027_.m_183595_()) {
            Entity $$0 = (Entity)((Object)this.f_40027_);
            $$0.m_9236_().m_7785_($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_(), this.f_40027_.m_7596_(), SoundSource.NEUTRAL, 1.0f, 1.0f, false);
        }
    }

    @Override
    public void m_6877_(Player p_40051_) {
        super.m_6877_(p_40051_);
        this.f_40027_.m_7189_(null);
        if (this.f_40027_.m_183595_()) {
            return;
        }
        if (!p_40051_.m_6084_() || p_40051_ instanceof ServerPlayer && ((ServerPlayer)p_40051_).m_9232_()) {
            ItemStack $$1 = this.f_40028_.m_8016_(0);
            if (!$$1.m_41619_()) {
                p_40051_.m_36176_($$1, false);
            }
            if (!($$1 = this.f_40028_.m_8016_(1)).m_41619_()) {
                p_40051_.m_36176_($$1, false);
            }
        } else if (p_40051_ instanceof ServerPlayer) {
            p_40051_.m_150109_().m_150079_(this.f_40028_.m_8016_(0));
            p_40051_.m_150109_().m_150079_(this.f_40028_.m_8016_(1));
        }
    }

    public void m_40072_(int p_40073_) {
        ItemStack $$2;
        if (this.m_40075_().size() <= p_40073_) {
            return;
        }
        ItemStack $$1 = this.f_40028_.m_8020_(0);
        if (!$$1.m_41619_()) {
            if (!this.m_38903_($$1, 3, 39, true)) {
                return;
            }
            this.f_40028_.m_6836_(0, $$1);
        }
        if (!($$2 = this.f_40028_.m_8020_(1)).m_41619_()) {
            if (!this.m_38903_($$2, 3, 39, true)) {
                return;
            }
            this.f_40028_.m_6836_(1, $$2);
        }
        if (this.f_40028_.m_8020_(0).m_41619_() && this.f_40028_.m_8020_(1).m_41619_()) {
            ItemStack $$3 = ((MerchantOffer)this.m_40075_().get(p_40073_)).m_45358_();
            this.m_40060_(0, $$3);
            ItemStack $$4 = ((MerchantOffer)this.m_40075_().get(p_40073_)).m_45364_();
            this.m_40060_(1, $$4);
        }
    }

    private void m_40060_(int p_40061_, ItemStack p_40062_) {
        if (!p_40062_.m_41619_()) {
            for (int $$2 = 3; $$2 < 39; ++$$2) {
                ItemStack $$3 = ((Slot)this.f_38839_.get($$2)).m_7993_();
                if ($$3.m_41619_() || !ItemStack.m_150942_(p_40062_, $$3)) continue;
                ItemStack $$4 = this.f_40028_.m_8020_(p_40061_);
                int $$5 = $$4.m_41619_() ? 0 : $$4.m_41613_();
                int $$6 = Math.min(p_40062_.m_41741_() - $$5, $$3.m_41613_());
                ItemStack $$7 = $$3.m_41777_();
                int $$8 = $$5 + $$6;
                $$3.m_41774_($$6);
                $$7.m_41764_($$8);
                this.f_40028_.m_6836_(p_40061_, $$7);
                if ($$8 >= p_40062_.m_41741_()) break;
            }
        }
    }

    public void m_40046_(MerchantOffers p_40047_) {
        this.f_40027_.m_6255_(p_40047_);
    }

    public MerchantOffers m_40075_() {
        return this.f_40027_.m_6616_();
    }

    public boolean m_40076_() {
        return this.f_40030_;
    }
}

