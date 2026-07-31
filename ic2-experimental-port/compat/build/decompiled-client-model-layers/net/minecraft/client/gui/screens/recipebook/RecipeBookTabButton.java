/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBookTabButton
extends StateSwitchingButton {
    private final RecipeBookCategories f_100445_;
    private static final float f_170055_ = 15.0f;
    private float f_100446_;

    public RecipeBookTabButton(RecipeBookCategories p_100448_) {
        super(0, 0, 35, 27, false);
        this.f_100445_ = p_100448_;
        this.m_94624_(153, 2, 35, 0, RecipeBookComponent.f_100268_);
    }

    public void m_100451_(Minecraft p_100452_) {
        ClientRecipeBook $$1 = p_100452_.f_91074_.m_108631_();
        List<RecipeCollection> $$2 = $$1.m_90623_(this.f_100445_);
        if (!(p_100452_.f_91074_.f_36096_ instanceof RecipeBookMenu)) {
            return;
        }
        for (RecipeCollection $$3 : $$2) {
            for (Recipe<?> $$4 : $$3.m_100510_($$1.m_12689_((RecipeBookMenu)p_100452_.f_91074_.f_36096_))) {
                if (!$$1.m_12717_($$4)) continue;
                this.f_100446_ = 15.0f;
                return;
            }
        }
    }

    @Override
    public void m_6303_(PoseStack p_100457_, int p_100458_, int p_100459_, float p_100460_) {
        if (this.f_100446_ > 0.0f) {
            float $$4 = 1.0f + 0.1f * (float)Math.sin(this.f_100446_ / 15.0f * (float)Math.PI);
            p_100457_.m_85836_();
            p_100457_.m_85837_(this.f_93620_ + 8, this.f_93621_ + 12, 0.0);
            p_100457_.m_85841_(1.0f, $$4, 1.0f);
            p_100457_.m_85837_(-(this.f_93620_ + 8), -(this.f_93621_ + 12), 0.0);
        }
        Minecraft $$5 = Minecraft.m_91087_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, this.f_94608_);
        RenderSystem.m_69465_();
        int $$6 = this.f_94610_;
        int $$7 = this.f_94611_;
        if (this.f_94609_) {
            $$6 += this.f_94612_;
        }
        if (this.m_198029_()) {
            $$7 += this.f_94613_;
        }
        int $$8 = this.f_93620_;
        if (this.f_94609_) {
            $$8 -= 2;
        }
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        this.m_93228_(p_100457_, $$8, this.f_93621_, $$6, $$7, this.f_93618_, this.f_93619_);
        RenderSystem.m_69482_();
        this.m_100453_($$5.m_91291_());
        if (this.f_100446_ > 0.0f) {
            p_100457_.m_85849_();
            this.f_100446_ -= p_100460_;
        }
    }

    private void m_100453_(ItemRenderer p_100454_) {
        int $$2;
        List<ItemStack> $$1 = this.f_100445_.m_92268_();
        int n = $$2 = this.f_94609_ ? -2 : 0;
        if ($$1.size() == 1) {
            p_100454_.m_115218_($$1.get(0), this.f_93620_ + 9 + $$2, this.f_93621_ + 5);
        } else if ($$1.size() == 2) {
            p_100454_.m_115218_($$1.get(0), this.f_93620_ + 3 + $$2, this.f_93621_ + 5);
            p_100454_.m_115218_($$1.get(1), this.f_93620_ + 14 + $$2, this.f_93621_ + 5);
        }
    }

    public RecipeBookCategories m_100455_() {
        return this.f_100445_;
    }

    public boolean m_100449_(ClientRecipeBook p_100450_) {
        List<RecipeCollection> $$1 = p_100450_.m_90623_(this.f_100445_);
        this.f_93624_ = false;
        if ($$1 != null) {
            for (RecipeCollection $$2 : $$1) {
                if (!$$2.m_100498_() || !$$2.m_100515_()) continue;
                this.f_93624_ = true;
                break;
            }
        }
        return this.f_93624_;
    }
}

