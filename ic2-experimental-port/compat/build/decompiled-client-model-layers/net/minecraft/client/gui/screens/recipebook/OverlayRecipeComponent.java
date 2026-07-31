/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class OverlayRecipeComponent
extends GuiComponent
implements Widget,
GuiEventListener {
    static final ResourceLocation f_100172_ = new ResourceLocation("textures/gui/recipe_book.png");
    private static final int f_170036_ = 4;
    private static final int f_170037_ = 5;
    private static final float f_170038_ = 0.375f;
    private final List<OverlayRecipeButton> f_100173_ = Lists.newArrayList();
    private boolean f_100174_;
    private int f_100175_;
    private int f_100176_;
    Minecraft f_100177_;
    private RecipeCollection f_100178_;
    @Nullable
    private Recipe<?> f_100179_;
    float f_100180_;
    boolean f_100181_;

    public void m_100194_(Minecraft p_100195_, RecipeCollection p_100196_, int p_100197_, int p_100198_, int p_100199_, int p_100200_, float p_100201_) {
        float $$20;
        float $$19;
        float $$18;
        float $$17;
        float $$16;
        this.f_100177_ = p_100195_;
        this.f_100178_ = p_100196_;
        if (p_100195_.f_91074_.f_36096_ instanceof AbstractFurnaceMenu) {
            this.f_100181_ = true;
        }
        boolean $$7 = p_100195_.f_91074_.m_108631_().m_12689_((RecipeBookMenu)p_100195_.f_91074_.f_36096_);
        List<Recipe<?>> $$8 = p_100196_.m_100513_(true);
        List $$9 = $$7 ? Collections.emptyList() : p_100196_.m_100513_(false);
        int $$10 = $$8.size();
        int $$11 = $$10 + $$9.size();
        int $$12 = $$11 <= 16 ? 4 : 5;
        int $$13 = (int)Math.ceil((float)$$11 / (float)$$12);
        this.f_100175_ = p_100197_;
        this.f_100176_ = p_100198_;
        int $$14 = 25;
        float $$15 = this.f_100175_ + Math.min($$11, $$12) * 25;
        if ($$15 > ($$16 = (float)(p_100199_ + 50))) {
            this.f_100175_ = (int)((float)this.f_100175_ - p_100201_ * (float)((int)(($$15 - $$16) / p_100201_)));
        }
        if (($$17 = (float)(this.f_100176_ + $$13 * 25)) > ($$18 = (float)(p_100200_ + 50))) {
            this.f_100176_ = (int)((float)this.f_100176_ - p_100201_ * (float)Mth.m_14167_(($$17 - $$18) / p_100201_));
        }
        if (($$19 = (float)this.f_100176_) < ($$20 = (float)(p_100200_ - 100))) {
            this.f_100176_ = (int)((float)this.f_100176_ - p_100201_ * (float)Mth.m_14167_(($$19 - $$20) / p_100201_));
        }
        this.f_100174_ = true;
        this.f_100173_.clear();
        for (int $$21 = 0; $$21 < $$11; ++$$21) {
            boolean $$22 = $$21 < $$10;
            Recipe $$23 = $$22 ? $$8.get($$21) : (Recipe)$$9.get($$21 - $$10);
            int $$24 = this.f_100175_ + 4 + 25 * ($$21 % $$12);
            int $$25 = this.f_100176_ + 5 + 25 * ($$21 / $$12);
            if (this.f_100181_) {
                this.f_100173_.add(new OverlaySmeltingRecipeButton($$24, $$25, $$23, $$22));
                continue;
            }
            this.f_100173_.add(new OverlayRecipeButton($$24, $$25, $$23, $$22));
        }
        this.f_100179_ = null;
    }

    @Override
    public boolean m_5755_(boolean p_100224_) {
        return false;
    }

    public RecipeCollection m_100184_() {
        return this.f_100178_;
    }

    @Nullable
    public Recipe<?> m_100206_() {
        return this.f_100179_;
    }

    @Override
    public boolean m_6375_(double p_100186_, double p_100187_, int p_100188_) {
        if (p_100188_ != 0) {
            return false;
        }
        for (OverlayRecipeButton $$3 : this.f_100173_) {
            if (!$$3.m_6375_(p_100186_, p_100187_, p_100188_)) continue;
            this.f_100179_ = $$3.f_100228_;
            return true;
        }
        return false;
    }

    @Override
    public boolean m_5953_(double p_100208_, double p_100209_) {
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_100190_, int p_100191_, int p_100192_, float p_100193_) {
        if (!this.f_100174_) {
            return;
        }
        this.f_100180_ += p_100193_;
        RenderSystem.m_69478_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_100172_);
        p_100190_.m_85836_();
        p_100190_.m_85837_(0.0, 0.0, 170.0);
        int $$4 = this.f_100173_.size() <= 16 ? 4 : 5;
        int $$5 = Math.min(this.f_100173_.size(), $$4);
        int $$6 = Mth.m_14167_((float)this.f_100173_.size() / (float)$$4);
        int $$7 = 24;
        int $$8 = 4;
        int $$9 = 82;
        int $$10 = 208;
        this.m_100213_(p_100190_, $$5, $$6, 24, 4, 82, 208);
        RenderSystem.m_69461_();
        for (OverlayRecipeButton $$11 : this.f_100173_) {
            $$11.m_6305_(p_100190_, p_100191_, p_100192_, p_100193_);
        }
        p_100190_.m_85849_();
    }

    private void m_100213_(PoseStack p_100214_, int p_100215_, int p_100216_, int p_100217_, int p_100218_, int p_100219_, int p_100220_) {
        this.m_93228_(p_100214_, this.f_100175_, this.f_100176_, p_100219_, p_100220_, p_100218_, p_100218_);
        this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_, p_100219_ + p_100217_ + p_100218_, p_100220_, p_100218_, p_100218_);
        this.m_93228_(p_100214_, this.f_100175_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_, p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);
        this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100217_ + p_100218_, p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);
        for (int $$7 = 0; $$7 < p_100215_; ++$$7) {
            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + $$7 * p_100217_, this.f_100176_, p_100219_ + p_100218_, p_100220_, p_100217_, p_100218_);
            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + ($$7 + 1) * p_100217_, this.f_100176_, p_100219_ + p_100218_, p_100220_, p_100218_, p_100218_);
            for (int $$8 = 0; $$8 < p_100216_; ++$$8) {
                if ($$7 == 0) {
                    this.m_93228_(p_100214_, this.f_100175_, this.f_100176_ + p_100218_ + $$8 * p_100217_, p_100219_, p_100220_ + p_100218_, p_100218_, p_100217_);
                    this.m_93228_(p_100214_, this.f_100175_, this.f_100176_ + p_100218_ + ($$8 + 1) * p_100217_, p_100219_, p_100220_ + p_100218_, p_100218_, p_100218_);
                }
                this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + $$7 * p_100217_, this.f_100176_ + p_100218_ + $$8 * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100217_, p_100217_);
                this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + ($$7 + 1) * p_100217_, this.f_100176_ + p_100218_ + $$8 * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100218_, p_100217_);
                this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + $$7 * p_100217_, this.f_100176_ + p_100218_ + ($$8 + 1) * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100217_, p_100218_);
                this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + ($$7 + 1) * p_100217_ - 1, this.f_100176_ + p_100218_ + ($$8 + 1) * p_100217_ - 1, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100218_ + 1, p_100218_ + 1);
                if ($$7 != p_100215_ - 1) continue;
                this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_ + p_100218_ + $$8 * p_100217_, p_100219_ + p_100217_ + p_100218_, p_100220_ + p_100218_, p_100218_, p_100217_);
                this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_ + p_100218_ + ($$8 + 1) * p_100217_, p_100219_ + p_100217_ + p_100218_, p_100220_ + p_100218_, p_100218_, p_100218_);
            }
            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + $$7 * p_100217_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100217_ + p_100218_, p_100217_, p_100218_);
            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + ($$7 + 1) * p_100217_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);
        }
    }

    public void m_100204_(boolean p_100205_) {
        this.f_100174_ = p_100205_;
    }

    public boolean m_100212_() {
        return this.f_100174_;
    }

    class OverlaySmeltingRecipeButton
    extends OverlayRecipeButton {
        public OverlaySmeltingRecipeButton(int p_100262_, int p_100263_, Recipe<?> p_100264_, boolean p_100265_) {
            super(p_100262_, p_100263_, p_100264_, p_100265_);
        }

        @Override
        protected void m_6222_(Recipe<?> p_100267_) {
            ItemStack[] $$1 = p_100267_.m_7527_().get(0).m_43908_();
            this.f_100226_.add(new OverlayRecipeButton.Pos(10, 10, $$1));
        }
    }

    class OverlayRecipeButton
    extends AbstractWidget
    implements PlaceRecipe<Ingredient> {
        final Recipe<?> f_100228_;
        private final boolean f_100229_;
        protected final List<Pos> f_100226_;

        public OverlayRecipeButton(int p_100232_, int p_100233_, Recipe<?> p_100234_, boolean p_100235_) {
            super(p_100232_, p_100233_, 200, 20, CommonComponents.f_237098_);
            this.f_100226_ = Lists.newArrayList();
            this.f_93618_ = 24;
            this.f_93619_ = 24;
            this.f_100228_ = p_100234_;
            this.f_100229_ = p_100235_;
            this.m_6222_(p_100234_);
        }

        protected void m_6222_(Recipe<?> p_100236_) {
            this.m_135408_(3, 3, -1, p_100236_, p_100236_.m_7527_().iterator(), 0);
        }

        @Override
        public void m_142291_(NarrationElementOutput p_170040_) {
            this.m_168802_(p_170040_);
        }

        @Override
        public void m_5817_(Iterator<Ingredient> p_100240_, int p_100241_, int p_100242_, int p_100243_, int p_100244_) {
            ItemStack[] $$5 = p_100240_.next().m_43908_();
            if ($$5.length != 0) {
                this.f_100226_.add(new Pos(3 + p_100244_ * 7, 3 + p_100243_ * 7, $$5));
            }
        }

        @Override
        public void m_6303_(PoseStack p_100246_, int p_100247_, int p_100248_, float p_100249_) {
            int $$5;
            RenderSystem.m_157456_(0, f_100172_);
            int $$4 = 152;
            if (!this.f_100229_) {
                $$4 += 26;
            }
            int n = $$5 = OverlayRecipeComponent.this.f_100181_ ? 130 : 78;
            if (this.m_198029_()) {
                $$5 += 26;
            }
            this.m_93228_(p_100246_, this.f_93620_, this.f_93621_, $$4, $$5, this.f_93618_, this.f_93619_);
            PoseStack $$6 = RenderSystem.m_157191_();
            $$6.m_85836_();
            $$6.m_85837_(this.f_93620_ + 2, this.f_93621_ + 2, 125.0);
            for (Pos $$7 : this.f_100226_) {
                $$6.m_85836_();
                $$6.m_85837_($$7.f_100251_, $$7.f_100252_, 0.0);
                $$6.m_85841_(0.375f, 0.375f, 1.0f);
                $$6.m_85837_(-8.0, -8.0, 0.0);
                RenderSystem.m_157182_();
                OverlayRecipeComponent.this.f_100177_.m_91291_().m_115203_($$7.f_100250_[Mth.m_14143_(OverlayRecipeComponent.this.f_100180_ / 30.0f) % $$7.f_100250_.length], 0, 0);
                $$6.m_85849_();
            }
            $$6.m_85849_();
            RenderSystem.m_157182_();
        }

        protected class Pos {
            public final ItemStack[] f_100250_;
            public final int f_100251_;
            public final int f_100252_;

            public Pos(int p_100256_, int p_100257_, ItemStack[] p_100258_) {
                this.f_100251_ = p_100256_;
                this.f_100252_ = p_100257_;
                this.f_100250_ = p_100258_;
            }
        }
    }
}

