/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.sounds.SoundEvents;

public class PageButton
extends Button {
    private final boolean f_99222_;
    private final boolean f_99223_;

    public PageButton(int p_99225_, int p_99226_, boolean p_99227_, Button.OnPress p_99228_, boolean p_99229_) {
        super(p_99225_, p_99226_, 23, 13, CommonComponents.f_237098_, p_99228_);
        this.f_99222_ = p_99227_;
        this.f_99223_ = p_99229_;
    }

    @Override
    public void m_6303_(PoseStack p_99233_, int p_99234_, int p_99235_, float p_99236_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, BookViewScreen.f_98252_);
        int $$4 = 0;
        int $$5 = 192;
        if (this.m_198029_()) {
            $$4 += 23;
        }
        if (!this.f_99222_) {
            $$5 += 13;
        }
        this.m_93228_(p_99233_, this.f_93620_, this.f_93621_, $$4, $$5, 23, 13);
    }

    @Override
    public void m_7435_(SoundManager p_99231_) {
        if (this.f_99223_) {
            p_99231_.m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_11713_, 1.0f));
        }
    }
}

