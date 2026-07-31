/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DemoIntroScreen
extends Screen {
    private static final ResourceLocation f_95935_ = new ResourceLocation("textures/gui/demo_background.png");
    private MultiLineLabel f_95936_ = MultiLineLabel.f_94331_;
    private MultiLineLabel f_95937_ = MultiLineLabel.f_94331_;

    public DemoIntroScreen() {
        super(Component.m_237115_("demo.help.title"));
    }

    @Override
    protected void m_7856_() {
        int $$0 = -16;
        this.m_142416_(new Button(this.f_96543_ / 2 - 116, this.f_96544_ / 2 + 62 + -16, 114, 20, Component.m_237115_("demo.help.buy"), p_95951_ -> {
            p_95951_.f_93623_ = false;
            Util.m_137581_().m_137646_("https://aka.ms/BuyMinecraftJava");
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 + 2, this.f_96544_ / 2 + 62 + -16, 114, 20, Component.m_237115_("demo.help.later"), p_95948_ -> {
            this.f_96541_.m_91152_(null);
            this.f_96541_.f_91067_.m_91601_();
        }));
        Options $$1 = this.f_96541_.f_91066_;
        this.f_95936_ = MultiLineLabel.m_94350_(this.f_96547_, Component.m_237110_("demo.help.movementShort", $$1.f_92085_.m_90863_(), $$1.f_92086_.m_90863_(), $$1.f_92087_.m_90863_(), $$1.f_92088_.m_90863_()), Component.m_237115_("demo.help.movementMouse"), Component.m_237110_("demo.help.jump", $$1.f_92089_.m_90863_()), Component.m_237110_("demo.help.inventory", $$1.f_92092_.m_90863_()));
        this.f_95937_ = MultiLineLabel.m_94341_(this.f_96547_, Component.m_237115_("demo.help.fullWrapped"), 218);
    }

    @Override
    public void m_7333_(PoseStack p_95941_) {
        super.m_7333_(p_95941_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_95935_);
        int $$1 = (this.f_96543_ - 248) / 2;
        int $$2 = (this.f_96544_ - 166) / 2;
        this.m_93228_(p_95941_, $$1, $$2, 0, 0, 248, 166);
    }

    @Override
    public void m_6305_(PoseStack p_95943_, int p_95944_, int p_95945_, float p_95946_) {
        this.m_7333_(p_95943_);
        int $$4 = (this.f_96543_ - 248) / 2 + 10;
        int $$5 = (this.f_96544_ - 166) / 2 + 8;
        this.f_96547_.m_92889_(p_95943_, this.f_96539_, $$4, $$5, 0x1F1F1F);
        $$5 = this.f_95936_.m_6508_(p_95943_, $$4, $$5 + 12, 12, 0x4F4F4F);
        this.f_95937_.m_6508_(p_95943_, $$4, $$5 + 20, this.f_96547_.f_92710_, 0x1F1F1F);
        super.m_6305_(p_95943_, p_95944_, p_95945_, p_95946_);
    }
}

