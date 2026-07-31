/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CartographyTableScreen
extends AbstractContainerScreen<CartographyTableMenu> {
    private static final ResourceLocation f_98346_ = new ResourceLocation("textures/gui/container/cartography_table.png");

    public CartographyTableScreen(CartographyTableMenu p_98349_, Inventory p_98350_, Component p_98351_) {
        super(p_98349_, p_98350_, p_98351_);
        this.f_97729_ -= 2;
    }

    @Override
    public void m_6305_(PoseStack p_98363_, int p_98364_, int p_98365_, float p_98366_) {
        super.m_6305_(p_98363_, p_98364_, p_98365_, p_98366_);
        this.m_7025_(p_98363_, p_98364_, p_98365_);
    }

    @Override
    protected void m_7286_(PoseStack p_98358_, float p_98359_, int p_98360_, int p_98361_) {
        MapItemSavedData $$15;
        Integer $$14;
        this.m_7333_(p_98358_);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_98346_);
        int $$4 = this.f_97735_;
        int $$5 = this.f_97736_;
        this.m_93228_(p_98358_, $$4, $$5, 0, 0, this.f_97726_, this.f_97727_);
        ItemStack $$6 = ((CartographyTableMenu)this.f_97732_).m_38853_(1).m_7993_();
        boolean $$7 = $$6.m_150930_(Items.f_42676_);
        boolean $$8 = $$6.m_150930_(Items.f_42516_);
        boolean $$9 = $$6.m_150930_(Items.f_42027_);
        ItemStack $$10 = ((CartographyTableMenu)this.f_97732_).m_38853_(0).m_7993_();
        boolean $$11 = false;
        if ($$10.m_150930_(Items.f_42573_)) {
            Integer $$12 = MapItem.m_151131_($$10);
            MapItemSavedData $$13 = MapItem.m_151128_($$12, this.f_96541_.f_91073_);
            if ($$13 != null) {
                if ($$13.f_77892_) {
                    $$11 = true;
                    if ($$8 || $$9) {
                        this.m_93228_(p_98358_, $$4 + 35, $$5 + 31, this.f_97726_ + 50, 132, 28, 21);
                    }
                }
                if ($$8 && $$13.f_77890_ >= 4) {
                    $$11 = true;
                    this.m_93228_(p_98358_, $$4 + 35, $$5 + 31, this.f_97726_ + 50, 132, 28, 21);
                }
            }
        } else {
            $$14 = null;
            $$15 = null;
        }
        this.m_169710_(p_98358_, $$14, $$15, $$7, $$8, $$9, $$11);
    }

    private void m_169710_(PoseStack p_169711_, @Nullable Integer p_169712_, @Nullable MapItemSavedData p_169713_, boolean p_169714_, boolean p_169715_, boolean p_169716_, boolean p_169717_) {
        int $$7 = this.f_97735_;
        int $$8 = this.f_97736_;
        if (p_169715_ && !p_169717_) {
            this.m_93228_(p_169711_, $$7 + 67, $$8 + 13, this.f_97726_, 66, 66, 66);
            this.m_169703_(p_169711_, p_169712_, p_169713_, $$7 + 85, $$8 + 31, 0.226f);
        } else if (p_169714_) {
            this.m_93228_(p_169711_, $$7 + 67 + 16, $$8 + 13, this.f_97726_, 132, 50, 66);
            this.m_169703_(p_169711_, p_169712_, p_169713_, $$7 + 86, $$8 + 16, 0.34f);
            RenderSystem.m_157456_(0, f_98346_);
            p_169711_.m_85836_();
            p_169711_.m_85837_(0.0, 0.0, 1.0);
            this.m_93228_(p_169711_, $$7 + 67, $$8 + 13 + 16, this.f_97726_, 132, 50, 66);
            this.m_169703_(p_169711_, p_169712_, p_169713_, $$7 + 70, $$8 + 32, 0.34f);
            p_169711_.m_85849_();
        } else if (p_169716_) {
            this.m_93228_(p_169711_, $$7 + 67, $$8 + 13, this.f_97726_, 0, 66, 66);
            this.m_169703_(p_169711_, p_169712_, p_169713_, $$7 + 71, $$8 + 17, 0.45f);
            RenderSystem.m_157456_(0, f_98346_);
            p_169711_.m_85836_();
            p_169711_.m_85837_(0.0, 0.0, 1.0);
            this.m_93228_(p_169711_, $$7 + 66, $$8 + 12, 0, this.f_97727_, 66, 66);
            p_169711_.m_85849_();
        } else {
            this.m_93228_(p_169711_, $$7 + 67, $$8 + 13, this.f_97726_, 0, 66, 66);
            this.m_169703_(p_169711_, p_169712_, p_169713_, $$7 + 71, $$8 + 17, 0.45f);
        }
    }

    private void m_169703_(PoseStack p_169704_, @Nullable Integer p_169705_, @Nullable MapItemSavedData p_169706_, int p_169707_, int p_169708_, float p_169709_) {
        if (p_169705_ != null && p_169706_ != null) {
            p_169704_.m_85836_();
            p_169704_.m_85837_(p_169707_, p_169708_, 1.0);
            p_169704_.m_85841_(p_169709_, p_169709_, 1.0f);
            MultiBufferSource.BufferSource $$6 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
            this.f_96541_.f_91063_.m_109151_().m_168771_(p_169704_, $$6, p_169705_, p_169706_, true, 0xF000F0);
            $$6.m_109911_();
            p_169704_.m_85849_();
        }
    }
}

