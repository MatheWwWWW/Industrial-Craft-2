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
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class GhostRecipe {
    @Nullable
    private Recipe<?> f_100136_;
    private final List<GhostIngredient> f_100137_ = Lists.newArrayList();
    float f_100138_;

    public void m_100140_() {
        this.f_100136_ = null;
        this.f_100137_.clear();
        this.f_100138_ = 0.0f;
    }

    public void m_100143_(Ingredient p_100144_, int p_100145_, int p_100146_) {
        this.f_100137_.add(new GhostIngredient(p_100144_, p_100145_, p_100146_));
    }

    public GhostIngredient m_100141_(int p_100142_) {
        return this.f_100137_.get(p_100142_);
    }

    public int m_100158_() {
        return this.f_100137_.size();
    }

    @Nullable
    public Recipe<?> m_100159_() {
        return this.f_100136_;
    }

    public void m_100147_(Recipe<?> p_100148_) {
        this.f_100136_ = p_100148_;
    }

    public void m_100149_(PoseStack p_100150_, Minecraft p_100151_, int p_100152_, int p_100153_, boolean p_100154_, float p_100155_) {
        if (!Screen.m_96637_()) {
            this.f_100138_ += p_100155_;
        }
        for (int $$6 = 0; $$6 < this.f_100137_.size(); ++$$6) {
            GhostIngredient $$7 = this.f_100137_.get($$6);
            int $$8 = $$7.m_100169_() + p_100152_;
            int $$9 = $$7.m_100170_() + p_100153_;
            if ($$6 == 0 && p_100154_) {
                GuiComponent.m_93172_(p_100150_, $$8 - 4, $$9 - 4, $$8 + 20, $$9 + 20, 0x30FF0000);
            } else {
                GuiComponent.m_93172_(p_100150_, $$8, $$9, $$8 + 16, $$9 + 16, 0x30FF0000);
            }
            ItemStack $$10 = $$7.m_100171_();
            ItemRenderer $$11 = p_100151_.m_91291_();
            $$11.m_115218_($$10, $$8, $$9);
            RenderSystem.m_69456_(516);
            GuiComponent.m_93172_(p_100150_, $$8, $$9, $$8 + 16, $$9 + 16, 0x30FFFFFF);
            RenderSystem.m_69456_(515);
            if ($$6 != 0) continue;
            $$11.m_115169_(p_100151_.f_91062_, $$10, $$8, $$9);
        }
    }

    public class GhostIngredient {
        private final Ingredient f_100161_;
        private final int f_100162_;
        private final int f_100163_;

        public GhostIngredient(Ingredient p_100166_, int p_100167_, int p_100168_) {
            this.f_100161_ = p_100166_;
            this.f_100162_ = p_100167_;
            this.f_100163_ = p_100168_;
        }

        public int m_100169_() {
            return this.f_100162_;
        }

        public int m_100170_() {
            return this.f_100163_;
        }

        public ItemStack m_100171_() {
            ItemStack[] $$0 = this.f_100161_.m_43908_();
            if ($$0.length == 0) {
                return ItemStack.f_41583_;
            }
            return $$0[Mth.m_14143_(GhostRecipe.this.f_100138_ / 30.0f) % $$0.length];
        }
    }
}

