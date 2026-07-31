/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeButton
extends AbstractWidget {
    private static final ResourceLocation f_100461_ = new ResourceLocation("textures/gui/recipe_book.png");
    private static final float f_170057_ = 15.0f;
    private static final int f_170058_ = 25;
    public static final int f_170056_ = 30;
    private static final Component f_100462_ = Component.m_237115_("gui.recipebook.moreRecipes");
    private RecipeBookMenu<?> f_100463_;
    private RecipeBook f_100464_;
    private RecipeCollection f_100465_;
    private float f_100466_;
    private float f_100467_;
    private int f_100468_;

    public RecipeButton() {
        super(0, 0, 25, 25, CommonComponents.f_237098_);
    }

    public void m_100479_(RecipeCollection p_100480_, RecipeBookPage p_100481_) {
        this.f_100465_ = p_100480_;
        this.f_100463_ = (RecipeBookMenu)p_100481_.m_100441_().f_91074_.f_36096_;
        this.f_100464_ = p_100481_.m_100442_();
        List<Recipe<?>> $$2 = p_100480_.m_100510_(this.f_100464_.m_12689_(this.f_100463_));
        for (Recipe<?> $$3 : $$2) {
            if (!this.f_100464_.m_12717_($$3)) continue;
            p_100481_.m_100434_($$2);
            this.f_100467_ = 15.0f;
            break;
        }
    }

    public RecipeCollection m_100471_() {
        return this.f_100465_;
    }

    public void m_100474_(int p_100475_, int p_100476_) {
        this.f_93620_ = p_100475_;
        this.f_93621_ = p_100476_;
    }

    @Override
    public void m_6303_(PoseStack p_100484_, int p_100485_, int p_100486_, float p_100487_) {
        if (!Screen.m_96637_()) {
            this.f_100466_ += p_100487_;
        }
        Minecraft $$4 = Minecraft.m_91087_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_100461_);
        int $$5 = 29;
        if (!this.f_100465_.m_100512_()) {
            $$5 += 25;
        }
        int $$6 = 206;
        if (this.f_100465_.m_100510_(this.f_100464_.m_12689_(this.f_100463_)).size() > 1) {
            $$6 += 25;
        }
        boolean $$7 = this.f_100467_ > 0.0f;
        PoseStack $$8 = RenderSystem.m_157191_();
        if ($$7) {
            float $$9 = 1.0f + 0.1f * (float)Math.sin(this.f_100467_ / 15.0f * (float)Math.PI);
            $$8.m_85836_();
            $$8.m_85837_(this.f_93620_ + 8, this.f_93621_ + 12, 0.0);
            $$8.m_85841_($$9, $$9, 1.0f);
            $$8.m_85837_(-(this.f_93620_ + 8), -(this.f_93621_ + 12), 0.0);
            RenderSystem.m_157182_();
            this.f_100467_ -= p_100487_;
        }
        this.m_93228_(p_100484_, this.f_93620_, this.f_93621_, $$5, $$6, this.f_93618_, this.f_93619_);
        List<Recipe<?>> $$10 = this.m_100490_();
        this.f_100468_ = Mth.m_14143_(this.f_100466_ / 30.0f) % $$10.size();
        ItemStack $$11 = $$10.get(this.f_100468_).m_8043_();
        int $$12 = 4;
        if (this.f_100465_.m_100517_() && this.m_100490_().size() > 1) {
            $$4.m_91291_().m_174258_($$11, this.f_93620_ + $$12 + 1, this.f_93621_ + $$12 + 1, 0, 10);
            --$$12;
        }
        $$4.m_91291_().m_115218_($$11, this.f_93620_ + $$12, this.f_93621_ + $$12);
        if ($$7) {
            $$8.m_85849_();
            RenderSystem.m_157182_();
        }
    }

    private List<Recipe<?>> m_100490_() {
        List<Recipe<?>> $$0 = this.f_100465_.m_100513_(true);
        if (!this.f_100464_.m_12689_(this.f_100463_)) {
            $$0.addAll(this.f_100465_.m_100513_(false));
        }
        return $$0;
    }

    public boolean m_100482_() {
        return this.m_100490_().size() == 1;
    }

    public Recipe<?> m_100488_() {
        List<Recipe<?>> $$0 = this.m_100490_();
        return $$0.get(this.f_100468_);
    }

    public List<Component> m_100477_(Screen p_100478_) {
        ItemStack $$1 = this.m_100490_().get(this.f_100468_).m_8043_();
        ArrayList $$2 = Lists.newArrayList(p_100478_.m_96555_($$1));
        if (this.f_100465_.m_100510_(this.f_100464_.m_12689_(this.f_100463_)).size() > 1) {
            $$2.add(f_100462_);
        }
        return $$2;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_170060_) {
        ItemStack $$1 = this.m_100490_().get(this.f_100468_).m_8043_();
        p_170060_.m_169146_(NarratedElementType.TITLE, Component.m_237110_("narration.recipe", $$1.m_41786_()));
        if (this.f_100465_.m_100510_(this.f_100464_.m_12689_(this.f_100463_)).size() > 1) {
            p_170060_.m_169149_(NarratedElementType.USAGE, Component.m_237115_("narration.button.usage.hovered"), Component.m_237115_("narration.recipe.usage.more"));
        } else {
            p_170060_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.button.usage.hovered"));
        }
    }

    @Override
    public int m_5711_() {
        return 25;
    }

    @Override
    protected boolean m_7972_(int p_100473_) {
        return p_100473_ == 0 || p_100473_ == 1;
    }
}

