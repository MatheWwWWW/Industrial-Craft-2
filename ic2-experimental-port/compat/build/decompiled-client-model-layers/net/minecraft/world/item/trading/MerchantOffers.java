/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item.trading;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class MerchantOffers
extends ArrayList<MerchantOffer> {
    public MerchantOffers() {
    }

    private MerchantOffers(int p_220323_) {
        super(p_220323_);
    }

    public MerchantOffers(CompoundTag p_45387_) {
        ListTag $$1 = p_45387_.m_128437_("Recipes", 10);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            this.add(new MerchantOffer($$1.m_128728_($$2)));
        }
    }

    @Nullable
    public MerchantOffer m_45389_(ItemStack p_45390_, ItemStack p_45391_, int p_45392_) {
        if (p_45392_ > 0 && p_45392_ < this.size()) {
            MerchantOffer $$3 = (MerchantOffer)this.get(p_45392_);
            if ($$3.m_45355_(p_45390_, p_45391_)) {
                return $$3;
            }
            return null;
        }
        for (int $$4 = 0; $$4 < this.size(); ++$$4) {
            MerchantOffer $$5 = (MerchantOffer)this.get($$4);
            if (!$$5.m_45355_(p_45390_, p_45391_)) continue;
            return $$5;
        }
        return null;
    }

    public void m_45393_(FriendlyByteBuf p_45394_) {
        p_45394_.m_236828_(this, (p_220325_, p_220326_) -> {
            p_220325_.m_130055_(p_220326_.m_45352_());
            p_220325_.m_130055_(p_220326_.m_45368_());
            p_220325_.m_130055_(p_220326_.m_45364_());
            p_220325_.writeBoolean(p_220326_.m_45380_());
            p_220325_.writeInt(p_220326_.m_45371_());
            p_220325_.writeInt(p_220326_.m_45373_());
            p_220325_.writeInt(p_220326_.m_45379_());
            p_220325_.writeInt(p_220326_.m_45377_());
            p_220325_.writeFloat(p_220326_.m_45378_());
            p_220325_.writeInt(p_220326_.m_45375_());
        });
    }

    public static MerchantOffers m_45395_(FriendlyByteBuf p_45396_) {
        return p_45396_.m_236838_(MerchantOffers::new, p_220328_ -> {
            ItemStack $$1 = p_220328_.m_130267_();
            ItemStack $$2 = p_220328_.m_130267_();
            ItemStack $$3 = p_220328_.m_130267_();
            boolean $$4 = p_220328_.readBoolean();
            int $$5 = p_220328_.readInt();
            int $$6 = p_220328_.readInt();
            int $$7 = p_220328_.readInt();
            int $$8 = p_220328_.readInt();
            float $$9 = p_220328_.readFloat();
            int $$10 = p_220328_.readInt();
            MerchantOffer $$11 = new MerchantOffer($$1, $$3, $$2, $$5, $$6, $$7, $$9, $$10);
            if ($$4) {
                $$11.m_45381_();
            }
            $$11.m_45359_($$8);
            return $$11;
        });
    }

    public CompoundTag m_45388_() {
        CompoundTag $$0 = new CompoundTag();
        ListTag $$1 = new ListTag();
        for (int $$2 = 0; $$2 < this.size(); ++$$2) {
            MerchantOffer $$3 = (MerchantOffer)this.get($$2);
            $$1.add($$3.m_45384_());
        }
        $$0.m_128365_("Recipes", $$1);
        return $$0;
    }
}

