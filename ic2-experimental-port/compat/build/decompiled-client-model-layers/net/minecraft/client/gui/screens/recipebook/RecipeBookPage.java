/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.RecipeShownListener;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBookPage {
    public static final int f_170052_ = 20;
    private final List<RecipeButton> f_100394_ = Lists.newArrayListWithCapacity((int)20);
    @Nullable
    private RecipeButton f_100395_;
    private final OverlayRecipeComponent f_100396_ = new OverlayRecipeComponent();
    private Minecraft f_100397_;
    private final List<RecipeShownListener> f_100398_ = Lists.newArrayList();
    private List<RecipeCollection> f_100399_ = ImmutableList.of();
    private StateSwitchingButton f_100400_;
    private StateSwitchingButton f_100401_;
    private int f_100402_;
    private int f_100403_;
    private RecipeBook f_100404_;
    @Nullable
    private Recipe<?> f_100405_;
    @Nullable
    private RecipeCollection f_100406_;

    public RecipeBookPage() {
        for (int $$0 = 0; $$0 < 20; ++$$0) {
            this.f_100394_.add(new RecipeButton());
        }
    }

    public void m_100428_(Minecraft p_100429_, int p_100430_, int p_100431_) {
        this.f_100397_ = p_100429_;
        this.f_100404_ = p_100429_.f_91074_.m_108631_();
        for (int $$3 = 0; $$3 < this.f_100394_.size(); ++$$3) {
            this.f_100394_.get($$3).m_100474_(p_100430_ + 11 + 25 * ($$3 % 5), p_100431_ + 31 + 25 * ($$3 / 5));
        }
        this.f_100400_ = new StateSwitchingButton(p_100430_ + 93, p_100431_ + 137, 12, 17, false);
        this.f_100400_.m_94624_(1, 208, 13, 18, RecipeBookComponent.f_100268_);
        this.f_100401_ = new StateSwitchingButton(p_100430_ + 38, p_100431_ + 137, 12, 17, true);
        this.f_100401_.m_94624_(1, 208, 13, 18, RecipeBookComponent.f_100268_);
    }

    public void m_100432_(RecipeBookComponent p_100433_) {
        this.f_100398_.remove(p_100433_);
        this.f_100398_.add(p_100433_);
    }

    public void m_100436_(List<RecipeCollection> p_100437_, boolean p_100438_) {
        this.f_100399_ = p_100437_;
        this.f_100402_ = (int)Math.ceil((double)p_100437_.size() / 20.0);
        if (this.f_100402_ <= this.f_100403_ || p_100438_) {
            this.f_100403_ = 0;
        }
        this.m_100443_();
    }

    private void m_100443_() {
        int $$0 = 20 * this.f_100403_;
        for (int $$1 = 0; $$1 < this.f_100394_.size(); ++$$1) {
            RecipeButton $$2 = this.f_100394_.get($$1);
            if ($$0 + $$1 < this.f_100399_.size()) {
                RecipeCollection $$3 = this.f_100399_.get($$0 + $$1);
                $$2.m_100479_($$3, this);
                $$2.f_93624_ = true;
                continue;
            }
            $$2.f_93624_ = false;
        }
        this.m_100444_();
    }

    private void m_100444_() {
        this.f_100400_.f_93624_ = this.f_100402_ > 1 && this.f_100403_ < this.f_100402_ - 1;
        this.f_100401_.f_93624_ = this.f_100402_ > 1 && this.f_100403_ > 0;
    }

    public void m_100421_(PoseStack p_100422_, int p_100423_, int p_100424_, int p_100425_, int p_100426_, float p_100427_) {
        if (this.f_100402_ > 1) {
            String $$6 = this.f_100403_ + 1 + "/" + this.f_100402_;
            int $$7 = this.f_100397_.f_91062_.m_92895_($$6);
            this.f_100397_.f_91062_.m_92883_(p_100422_, $$6, p_100423_ - $$7 / 2 + 73, p_100424_ + 141, -1);
        }
        this.f_100395_ = null;
        for (RecipeButton $$8 : this.f_100394_) {
            $$8.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
            if (!$$8.f_93624_ || !$$8.m_198029_()) continue;
            this.f_100395_ = $$8;
        }
        this.f_100401_.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
        this.f_100400_.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
        this.f_100396_.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
    }

    public void m_100417_(PoseStack p_100418_, int p_100419_, int p_100420_) {
        if (this.f_100397_.f_91080_ != null && this.f_100395_ != null && !this.f_100396_.m_100212_()) {
            this.f_100397_.f_91080_.m_96597_(p_100418_, this.f_100395_.m_100477_(this.f_100397_.f_91080_), p_100419_, p_100420_);
        }
    }

    @Nullable
    public Recipe<?> m_100408_() {
        return this.f_100405_;
    }

    @Nullable
    public RecipeCollection m_100439_() {
        return this.f_100406_;
    }

    public void m_100440_() {
        this.f_100396_.m_100204_(false);
    }

    public boolean m_100409_(double p_100410_, double p_100411_, int p_100412_, int p_100413_, int p_100414_, int p_100415_, int p_100416_) {
        this.f_100405_ = null;
        this.f_100406_ = null;
        if (this.f_100396_.m_100212_()) {
            if (this.f_100396_.m_6375_(p_100410_, p_100411_, p_100412_)) {
                this.f_100405_ = this.f_100396_.m_100206_();
                this.f_100406_ = this.f_100396_.m_100184_();
            } else {
                this.f_100396_.m_100204_(false);
            }
            return true;
        }
        if (this.f_100400_.m_6375_(p_100410_, p_100411_, p_100412_)) {
            ++this.f_100403_;
            this.m_100443_();
            return true;
        }
        if (this.f_100401_.m_6375_(p_100410_, p_100411_, p_100412_)) {
            --this.f_100403_;
            this.m_100443_();
            return true;
        }
        for (RecipeButton $$7 : this.f_100394_) {
            if (!$$7.m_6375_(p_100410_, p_100411_, p_100412_)) continue;
            if (p_100412_ == 0) {
                this.f_100405_ = $$7.m_100488_();
                this.f_100406_ = $$7.m_100471_();
            } else if (p_100412_ == 1 && !this.f_100396_.m_100212_() && !$$7.m_100482_()) {
                this.f_100396_.m_100194_(this.f_100397_, $$7.m_100471_(), $$7.f_93620_, $$7.f_93621_, p_100413_ + p_100415_ / 2, p_100414_ + 13 + p_100416_ / 2, $$7.m_5711_());
            }
            return true;
        }
        return false;
    }

    public void m_100434_(List<Recipe<?>> p_100435_) {
        for (RecipeShownListener $$1 : this.f_100398_) {
            $$1.m_7262_(p_100435_);
        }
    }

    public Minecraft m_100441_() {
        return this.f_100397_;
    }

    public RecipeBook m_100442_() {
        return this.f_100404_;
    }

    protected void m_170053_(Consumer<AbstractWidget> p_170054_) {
        p_170054_.accept(this.f_100400_);
        p_170054_.accept(this.f_100401_);
        this.f_100394_.forEach(p_170054_);
    }
}

