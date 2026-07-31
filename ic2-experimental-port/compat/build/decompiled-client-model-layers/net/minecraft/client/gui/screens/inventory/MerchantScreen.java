/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantScreen
extends AbstractContainerScreen<MerchantMenu> {
    private static final ResourceLocation f_99113_ = new ResourceLocation("textures/gui/container/villager2.png");
    private static final int f_169785_ = 512;
    private static final int f_169786_ = 256;
    private static final int f_169787_ = 99;
    private static final int f_169788_ = 136;
    private static final int f_169789_ = 16;
    private static final int f_169790_ = 5;
    private static final int f_169791_ = 35;
    private static final int f_169792_ = 68;
    private static final int f_169793_ = 6;
    private static final int f_169794_ = 7;
    private static final int f_169795_ = 5;
    private static final int f_169796_ = 20;
    private static final int f_169797_ = 89;
    private static final int f_169798_ = 27;
    private static final int f_169799_ = 6;
    private static final int f_169800_ = 139;
    private static final int f_169801_ = 18;
    private static final int f_169802_ = 94;
    private static final Component f_99114_ = Component.m_237115_("merchant.trades");
    private static final Component f_99115_ = Component.m_237113_(" - ");
    private static final Component f_99116_ = Component.m_237115_("merchant.deprecated");
    private int f_99117_;
    private final TradeOfferButton[] f_99118_ = new TradeOfferButton[7];
    int f_99119_;
    private boolean f_99120_;

    public MerchantScreen(MerchantMenu p_99123_, Inventory p_99124_, Component p_99125_) {
        super(p_99123_, p_99124_, p_99125_);
        this.f_97726_ = 276;
        this.f_97730_ = 107;
    }

    private void m_99200_() {
        ((MerchantMenu)this.f_97732_).m_40063_(this.f_99117_);
        ((MerchantMenu)this.f_97732_).m_40072_(this.f_99117_);
        this.f_96541_.m_91403_().m_104955_(new ServerboundSelectTradePacket(this.f_99117_));
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        int $$0 = (this.f_96543_ - this.f_97726_) / 2;
        int $$1 = (this.f_96544_ - this.f_97727_) / 2;
        int $$2 = $$1 + 16 + 2;
        for (int $$3 = 0; $$3 < 7; ++$$3) {
            this.f_99118_[$$3] = this.m_142416_(new TradeOfferButton($$0 + 5, $$2, $$3, p_99174_ -> {
                if (p_99174_ instanceof TradeOfferButton) {
                    this.f_99117_ = ((TradeOfferButton)p_99174_).m_99209_() + this.f_99119_;
                    this.m_99200_();
                }
            }));
            $$2 += 20;
        }
    }

    @Override
    protected void m_7027_(PoseStack p_99185_, int p_99186_, int p_99187_) {
        int $$3 = ((MerchantMenu)this.f_97732_).m_40071_();
        if ($$3 > 0 && $$3 <= 5 && ((MerchantMenu)this.f_97732_).m_40076_()) {
            MutableComponent $$4 = this.f_96539_.m_6881_().m_7220_(f_99115_).m_7220_(Component.m_237115_("merchant.level." + $$3));
            int $$5 = this.f_96547_.m_92852_($$4);
            int $$6 = 49 + this.f_97726_ / 2 - $$5 / 2;
            this.f_96547_.m_92889_(p_99185_, $$4, $$6, 6.0f, 0x404040);
        } else {
            this.f_96547_.m_92889_(p_99185_, this.f_96539_, 49 + this.f_97726_ / 2 - this.f_96547_.m_92852_(this.f_96539_) / 2, 6.0f, 0x404040);
        }
        this.f_96547_.m_92889_(p_99185_, this.f_169604_, this.f_97730_, this.f_97731_, 0x404040);
        int $$7 = this.f_96547_.m_92852_(f_99114_);
        this.f_96547_.m_92889_(p_99185_, f_99114_, 5 - $$7 / 2 + 48, 6.0f, 0x404040);
    }

    @Override
    protected void m_7286_(PoseStack p_99143_, float p_99144_, int p_99145_, int p_99146_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_99113_);
        int $$4 = (this.f_96543_ - this.f_97726_) / 2;
        int $$5 = (this.f_96544_ - this.f_97727_) / 2;
        MerchantScreen.m_93143_(p_99143_, $$4, $$5, this.m_93252_(), 0.0f, 0.0f, this.f_97726_, this.f_97727_, 512, 256);
        MerchantOffers $$6 = ((MerchantMenu)this.f_97732_).m_40075_();
        if (!$$6.isEmpty()) {
            int $$7 = this.f_99117_;
            if ($$7 < 0 || $$7 >= $$6.size()) {
                return;
            }
            MerchantOffer $$8 = (MerchantOffer)$$6.get($$7);
            if ($$8.m_45380_()) {
                RenderSystem.m_157456_(0, f_99113_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                MerchantScreen.m_93143_(p_99143_, this.f_97735_ + 83 + 99, this.f_97736_ + 35, this.m_93252_(), 311.0f, 0.0f, 28, 21, 512, 256);
            }
        }
    }

    private void m_99152_(PoseStack p_99153_, int p_99154_, int p_99155_, MerchantOffer p_99156_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_99113_);
        int $$4 = ((MerchantMenu)this.f_97732_).m_40071_();
        int $$5 = ((MerchantMenu)this.f_97732_).m_40065_();
        if ($$4 >= 5) {
            return;
        }
        MerchantScreen.m_93143_(p_99153_, p_99154_ + 136, p_99155_ + 16, this.m_93252_(), 0.0f, 186.0f, 102, 5, 512, 256);
        int $$6 = VillagerData.m_35572_($$4);
        if ($$5 < $$6 || !VillagerData.m_35582_($$4)) {
            return;
        }
        int $$7 = 100;
        float $$8 = 100.0f / (float)(VillagerData.m_35577_($$4) - $$6);
        int $$9 = Math.min(Mth.m_14143_($$8 * (float)($$5 - $$6)), 100);
        MerchantScreen.m_93143_(p_99153_, p_99154_ + 136, p_99155_ + 16, this.m_93252_(), 0.0f, 191.0f, $$9 + 1, 5, 512, 256);
        int $$10 = ((MerchantMenu)this.f_97732_).m_40068_();
        if ($$10 > 0) {
            int $$11 = Math.min(Mth.m_14143_((float)$$10 * $$8), 100 - $$9);
            MerchantScreen.m_93143_(p_99153_, p_99154_ + 136 + $$9 + 1, p_99155_ + 16 + 1, this.m_93252_(), 2.0f, 182.0f, $$11, 3, 512, 256);
        }
    }

    private void m_99157_(PoseStack p_99158_, int p_99159_, int p_99160_, MerchantOffers p_99161_) {
        int $$4 = p_99161_.size() + 1 - 7;
        if ($$4 > 1) {
            int $$5 = 139 - (27 + ($$4 - 1) * 139 / $$4);
            int $$6 = 1 + $$5 / $$4 + 139 / $$4;
            int $$7 = 113;
            int $$8 = Math.min(113, this.f_99119_ * $$6);
            if (this.f_99119_ == $$4 - 1) {
                $$8 = 113;
            }
            MerchantScreen.m_93143_(p_99158_, p_99159_ + 94, p_99160_ + 18 + $$8, this.m_93252_(), 0.0f, 199.0f, 6, 27, 512, 256);
        } else {
            MerchantScreen.m_93143_(p_99158_, p_99159_ + 94, p_99160_ + 18, this.m_93252_(), 6.0f, 199.0f, 6, 27, 512, 256);
        }
    }

    @Override
    public void m_6305_(PoseStack p_99148_, int p_99149_, int p_99150_, float p_99151_) {
        this.m_7333_(p_99148_);
        super.m_6305_(p_99148_, p_99149_, p_99150_, p_99151_);
        MerchantOffers $$4 = ((MerchantMenu)this.f_97732_).m_40075_();
        if (!$$4.isEmpty()) {
            int $$5 = (this.f_96543_ - this.f_97726_) / 2;
            int $$6 = (this.f_96544_ - this.f_97727_) / 2;
            int $$7 = $$6 + 16 + 1;
            int $$8 = $$5 + 5 + 5;
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_99113_);
            this.m_99157_(p_99148_, $$5, $$6, $$4);
            int $$9 = 0;
            for (MerchantOffer $$10 : $$4) {
                if (this.m_99140_($$4.size()) && ($$9 < this.f_99119_ || $$9 >= 7 + this.f_99119_)) {
                    ++$$9;
                    continue;
                }
                ItemStack $$11 = $$10.m_45352_();
                ItemStack $$12 = $$10.m_45358_();
                ItemStack $$13 = $$10.m_45364_();
                ItemStack $$14 = $$10.m_45368_();
                this.f_96542_.f_115093_ = 100.0f;
                int $$15 = $$7 + 2;
                this.m_99162_(p_99148_, $$12, $$11, $$8, $$15);
                if (!$$13.m_41619_()) {
                    this.f_96542_.m_115218_($$13, $$5 + 5 + 35, $$15);
                    this.f_96542_.m_115169_(this.f_96547_, $$13, $$5 + 5 + 35, $$15);
                }
                this.m_99168_(p_99148_, $$10, $$5, $$15);
                this.f_96542_.m_115218_($$14, $$5 + 5 + 68, $$15);
                this.f_96542_.m_115169_(this.f_96547_, $$14, $$5 + 5 + 68, $$15);
                this.f_96542_.f_115093_ = 0.0f;
                $$7 += 20;
                ++$$9;
            }
            int $$16 = this.f_99117_;
            MerchantOffer $$17 = (MerchantOffer)$$4.get($$16);
            if (((MerchantMenu)this.f_97732_).m_40076_()) {
                this.m_99152_(p_99148_, $$5, $$6, $$17);
            }
            if ($$17.m_45380_() && this.m_6774_(186, 35, 22, 21, p_99149_, p_99150_) && ((MerchantMenu)this.f_97732_).m_40074_()) {
                this.m_96602_(p_99148_, f_99116_, p_99149_, p_99150_);
            }
            for (TradeOfferButton $$18 : this.f_99118_) {
                if ($$18.m_198029_()) {
                    $$18.m_7428_(p_99148_, p_99149_, p_99150_);
                }
                $$18.f_93624_ = $$18.f_99201_ < ((MerchantMenu)this.f_97732_).m_40075_().size();
            }
            RenderSystem.m_69482_();
        }
        this.m_7025_(p_99148_, p_99149_, p_99150_);
    }

    private void m_99168_(PoseStack p_99169_, MerchantOffer p_99170_, int p_99171_, int p_99172_) {
        RenderSystem.m_69478_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_99113_);
        if (p_99170_.m_45380_()) {
            MerchantScreen.m_93143_(p_99169_, p_99171_ + 5 + 35 + 20, p_99172_ + 3, this.m_93252_(), 25.0f, 171.0f, 10, 9, 512, 256);
        } else {
            MerchantScreen.m_93143_(p_99169_, p_99171_ + 5 + 35 + 20, p_99172_ + 3, this.m_93252_(), 15.0f, 171.0f, 10, 9, 512, 256);
        }
    }

    private void m_99162_(PoseStack p_99163_, ItemStack p_99164_, ItemStack p_99165_, int p_99166_, int p_99167_) {
        this.f_96542_.m_115218_(p_99164_, p_99166_, p_99167_);
        if (p_99165_.m_41613_() == p_99164_.m_41613_()) {
            this.f_96542_.m_115169_(this.f_96547_, p_99164_, p_99166_, p_99167_);
        } else {
            this.f_96542_.m_115174_(this.f_96547_, p_99165_, p_99166_, p_99167_, p_99165_.m_41613_() == 1 ? "1" : null);
            this.f_96542_.m_115174_(this.f_96547_, p_99164_, p_99166_ + 14, p_99167_, p_99164_.m_41613_() == 1 ? "1" : null);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_99113_);
            this.m_93250_(this.m_93252_() + 300);
            MerchantScreen.m_93143_(p_99163_, p_99166_ + 7, p_99167_ + 12, this.m_93252_(), 0.0f, 176.0f, 9, 2, 512, 256);
            this.m_93250_(this.m_93252_() - 300);
        }
    }

    private boolean m_99140_(int p_99141_) {
        return p_99141_ > 7;
    }

    @Override
    public boolean m_6050_(double p_99127_, double p_99128_, double p_99129_) {
        int $$3 = ((MerchantMenu)this.f_97732_).m_40075_().size();
        if (this.m_99140_($$3)) {
            int $$4 = $$3 - 7;
            this.f_99119_ = Mth.m_14045_((int)((double)this.f_99119_ - p_99129_), 0, $$4);
        }
        return true;
    }

    @Override
    public boolean m_7979_(double p_99135_, double p_99136_, int p_99137_, double p_99138_, double p_99139_) {
        int $$5 = ((MerchantMenu)this.f_97732_).m_40075_().size();
        if (this.f_99120_) {
            int $$6 = this.f_97736_ + 18;
            int $$7 = $$6 + 139;
            int $$8 = $$5 - 7;
            float $$9 = ((float)p_99136_ - (float)$$6 - 13.5f) / ((float)($$7 - $$6) - 27.0f);
            $$9 = $$9 * (float)$$8 + 0.5f;
            this.f_99119_ = Mth.m_14045_((int)$$9, 0, $$8);
            return true;
        }
        return super.m_7979_(p_99135_, p_99136_, p_99137_, p_99138_, p_99139_);
    }

    @Override
    public boolean m_6375_(double p_99131_, double p_99132_, int p_99133_) {
        this.f_99120_ = false;
        int $$3 = (this.f_96543_ - this.f_97726_) / 2;
        int $$4 = (this.f_96544_ - this.f_97727_) / 2;
        if (this.m_99140_(((MerchantMenu)this.f_97732_).m_40075_().size()) && p_99131_ > (double)($$3 + 94) && p_99131_ < (double)($$3 + 94 + 6) && p_99132_ > (double)($$4 + 18) && p_99132_ <= (double)($$4 + 18 + 139 + 1)) {
            this.f_99120_ = true;
        }
        return super.m_6375_(p_99131_, p_99132_, p_99133_);
    }

    class TradeOfferButton
    extends Button {
        final int f_99201_;

        public TradeOfferButton(int p_99205_, int p_99206_, int p_99207_, Button.OnPress p_99208_) {
            super(p_99205_, p_99206_, 89, 20, CommonComponents.f_237098_, p_99208_);
            this.f_99201_ = p_99207_;
            this.f_93624_ = false;
        }

        public int m_99209_() {
            return this.f_99201_;
        }

        @Override
        public void m_7428_(PoseStack p_99211_, int p_99212_, int p_99213_) {
            if (this.f_93622_ && ((MerchantMenu)MerchantScreen.this.f_97732_).m_40075_().size() > this.f_99201_ + MerchantScreen.this.f_99119_) {
                if (p_99212_ < this.f_93620_ + 20) {
                    ItemStack $$3 = ((MerchantOffer)((MerchantMenu)MerchantScreen.this.f_97732_).m_40075_().get(this.f_99201_ + MerchantScreen.this.f_99119_)).m_45358_();
                    MerchantScreen.this.m_6057_(p_99211_, $$3, p_99212_, p_99213_);
                } else if (p_99212_ < this.f_93620_ + 50 && p_99212_ > this.f_93620_ + 30) {
                    ItemStack $$4 = ((MerchantOffer)((MerchantMenu)MerchantScreen.this.f_97732_).m_40075_().get(this.f_99201_ + MerchantScreen.this.f_99119_)).m_45364_();
                    if (!$$4.m_41619_()) {
                        MerchantScreen.this.m_6057_(p_99211_, $$4, p_99212_, p_99213_);
                    }
                } else if (p_99212_ > this.f_93620_ + 65) {
                    ItemStack $$5 = ((MerchantOffer)((MerchantMenu)MerchantScreen.this.f_97732_).m_40075_().get(this.f_99201_ + MerchantScreen.this.f_99119_)).m_45368_();
                    MerchantScreen.this.m_6057_(p_99211_, $$5, p_99212_, p_99213_);
                }
            }
        }
    }
}

