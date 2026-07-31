/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeToast
implements Toast {
    private static final long f_169076_ = 5000L;
    private static final Component f_94803_ = Component.m_237115_("recipe.toast.title");
    private static final Component f_94804_ = Component.m_237115_("recipe.toast.description");
    private final List<Recipe<?>> f_94805_ = Lists.newArrayList();
    private long f_94806_;
    private boolean f_94807_;

    public RecipeToast(Recipe<?> p_94810_) {
        this.f_94805_.add(p_94810_);
    }

    @Override
    public Toast.Visibility m_7172_(PoseStack p_94814_, ToastComponent p_94815_, long p_94816_) {
        if (this.f_94807_) {
            this.f_94806_ = p_94816_;
            this.f_94807_ = false;
        }
        if (this.f_94805_.isEmpty()) {
            return Toast.Visibility.HIDE;
        }
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_94893_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        p_94815_.m_93228_(p_94814_, 0, 0, 0, 32, this.m_7828_(), this.m_94899_());
        p_94815_.m_94929_().f_91062_.m_92889_(p_94814_, f_94803_, 30.0f, 7.0f, -11534256);
        p_94815_.m_94929_().f_91062_.m_92889_(p_94814_, f_94804_, 30.0f, 18.0f, -16777216);
        Recipe<?> $$3 = this.f_94805_.get((int)(p_94816_ / Math.max(1L, 5000L / (long)this.f_94805_.size()) % (long)this.f_94805_.size()));
        ItemStack $$4 = $$3.m_8042_();
        PoseStack $$5 = RenderSystem.m_157191_();
        $$5.m_85836_();
        $$5.m_85841_(0.6f, 0.6f, 1.0f);
        RenderSystem.m_157182_();
        p_94815_.m_94929_().m_91291_().m_115218_($$4, 3, 3);
        $$5.m_85849_();
        RenderSystem.m_157182_();
        p_94815_.m_94929_().m_91291_().m_115218_($$3.m_8043_(), 8, 8);
        return p_94816_ - this.f_94806_ >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    private void m_94811_(Recipe<?> p_94812_) {
        this.f_94805_.add(p_94812_);
        this.f_94807_ = true;
    }

    public static void m_94817_(ToastComponent p_94818_, Recipe<?> p_94819_) {
        RecipeToast $$2 = p_94818_.m_94926_(RecipeToast.class, f_94894_);
        if ($$2 == null) {
            p_94818_.m_94922_(new RecipeToast(p_94819_));
        } else {
            $$2.m_94811_(p_94819_);
        }
    }
}

