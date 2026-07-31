/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item.trading;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public interface Merchant {
    public void m_7189_(@Nullable Player var1);

    @Nullable
    public Player m_7962_();

    public MerchantOffers m_6616_();

    public void m_6255_(MerchantOffers var1);

    public void m_6996_(MerchantOffer var1);

    public void m_7713_(ItemStack var1);

    public int m_7809_();

    public void m_6621_(int var1);

    public boolean m_7826_();

    public SoundEvent m_7596_();

    default public boolean m_7862_() {
        return false;
    }

    default public void m_45301_(Player p_45302_, Component p_45303_, int p_45304_) {
        MerchantOffers $$4;
        OptionalInt $$3 = p_45302_.m_5893_(new SimpleMenuProvider((p_45298_, p_45299_, p_45300_) -> new MerchantMenu(p_45298_, p_45299_, this), p_45303_));
        if ($$3.isPresent() && !($$4 = this.m_6616_()).isEmpty()) {
            p_45302_.m_7662_($$3.getAsInt(), $$4, p_45304_, this.m_7809_(), this.m_7826_(), this.m_7862_());
        }
    }

    public boolean m_183595_();
}

