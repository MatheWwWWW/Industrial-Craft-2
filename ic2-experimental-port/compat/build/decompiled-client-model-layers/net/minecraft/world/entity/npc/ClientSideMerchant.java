/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.npc;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class ClientSideMerchant
implements Merchant {
    private final Player f_35340_;
    private MerchantOffers f_35341_ = new MerchantOffers();
    private int f_35342_;

    public ClientSideMerchant(Player p_35344_) {
        this.f_35340_ = p_35344_;
    }

    @Override
    public Player m_7962_() {
        return this.f_35340_;
    }

    @Override
    public void m_7189_(@Nullable Player p_35356_) {
    }

    @Override
    public MerchantOffers m_6616_() {
        return this.f_35341_;
    }

    @Override
    public void m_6255_(MerchantOffers p_35348_) {
        this.f_35341_ = p_35348_;
    }

    @Override
    public void m_6996_(MerchantOffer p_35346_) {
        p_35346_.m_45374_();
    }

    @Override
    public void m_7713_(ItemStack p_35358_) {
    }

    @Override
    public boolean m_183595_() {
        return this.f_35340_.m_9236_().f_46443_;
    }

    @Override
    public int m_7809_() {
        return this.f_35342_;
    }

    @Override
    public void m_6621_(int p_35360_) {
        this.f_35342_ = p_35360_;
    }

    @Override
    public boolean m_7826_() {
        return true;
    }

    @Override
    public SoundEvent m_7596_() {
        return SoundEvents.f_12509_;
    }
}

