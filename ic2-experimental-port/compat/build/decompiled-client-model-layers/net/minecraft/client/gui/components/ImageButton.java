/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ImageButton
extends Button {
    private final ResourceLocation f_94223_;
    private final int f_94224_;
    private final int f_94225_;
    private final int f_94226_;
    private final int f_94227_;
    private final int f_94228_;

    public ImageButton(int p_169011_, int p_169012_, int p_169013_, int p_169014_, int p_169015_, int p_169016_, ResourceLocation p_169017_, Button.OnPress p_169018_) {
        this(p_169011_, p_169012_, p_169013_, p_169014_, p_169015_, p_169016_, p_169014_, p_169017_, 256, 256, p_169018_);
    }

    public ImageButton(int p_94269_, int p_94270_, int p_94271_, int p_94272_, int p_94273_, int p_94274_, int p_94275_, ResourceLocation p_94276_, Button.OnPress p_94277_) {
        this(p_94269_, p_94270_, p_94271_, p_94272_, p_94273_, p_94274_, p_94275_, p_94276_, 256, 256, p_94277_);
    }

    public ImageButton(int p_94230_, int p_94231_, int p_94232_, int p_94233_, int p_94234_, int p_94235_, int p_94236_, ResourceLocation p_94237_, int p_94238_, int p_94239_, Button.OnPress p_94240_) {
        this(p_94230_, p_94231_, p_94232_, p_94233_, p_94234_, p_94235_, p_94236_, p_94237_, p_94238_, p_94239_, p_94240_, CommonComponents.f_237098_);
    }

    public ImageButton(int p_94256_, int p_94257_, int p_94258_, int p_94259_, int p_94260_, int p_94261_, int p_94262_, ResourceLocation p_94263_, int p_94264_, int p_94265_, Button.OnPress p_94266_, Component p_94267_) {
        this(p_94256_, p_94257_, p_94258_, p_94259_, p_94260_, p_94261_, p_94262_, p_94263_, p_94264_, p_94265_, p_94266_, f_93716_, p_94267_);
    }

    public ImageButton(int p_94242_, int p_94243_, int p_94244_, int p_94245_, int p_94246_, int p_94247_, int p_94248_, ResourceLocation p_94249_, int p_94250_, int p_94251_, Button.OnPress p_94252_, Button.OnTooltip p_94253_, Component p_94254_) {
        super(p_94242_, p_94243_, p_94244_, p_94245_, p_94254_, p_94252_, p_94253_);
        this.f_94227_ = p_94250_;
        this.f_94228_ = p_94251_;
        this.f_94224_ = p_94246_;
        this.f_94225_ = p_94247_;
        this.f_94226_ = p_94248_;
        this.f_94223_ = p_94249_;
    }

    public void m_94278_(int p_94279_, int p_94280_) {
        this.f_93620_ = p_94279_;
        this.f_93621_ = p_94280_;
    }

    @Override
    public void m_6303_(PoseStack p_94282_, int p_94283_, int p_94284_, float p_94285_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, this.f_94223_);
        int $$4 = this.f_94225_;
        if (!this.m_142518_()) {
            $$4 += this.f_94226_ * 2;
        } else if (this.m_198029_()) {
            $$4 += this.f_94226_;
        }
        RenderSystem.m_69482_();
        ImageButton.m_93133_(p_94282_, this.f_93620_, this.f_93621_, this.f_94224_, $$4, this.f_93618_, this.f_93619_, this.f_94227_, this.f_94228_);
        if (this.f_93622_) {
            this.m_7428_(p_94282_, p_94283_, p_94284_);
        }
    }
}

