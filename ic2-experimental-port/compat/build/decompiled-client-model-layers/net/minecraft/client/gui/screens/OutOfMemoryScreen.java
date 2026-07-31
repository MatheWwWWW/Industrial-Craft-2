/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

public class OutOfMemoryScreen
extends Screen {
    private MultiLineLabel f_232750_ = MultiLineLabel.f_94331_;

    public OutOfMemoryScreen() {
        super(Component.m_237115_("outOfMemory.error"));
    }

    @Override
    protected void m_7856_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 4 + 120 + 12, 150, 20, Component.m_237115_("gui.toTitle"), p_96304_ -> this.f_96541_.m_91152_(new TitleScreen())));
        this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ / 4 + 120 + 12, 150, 20, Component.m_237115_("menu.quit"), p_96300_ -> this.f_96541_.m_91395_()));
        this.f_232750_ = MultiLineLabel.m_94341_(this.f_96547_, Component.m_237115_("outOfMemory.message"), 295);
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_96295_, int p_96296_, int p_96297_, float p_96298_) {
        this.m_7333_(p_96295_);
        OutOfMemoryScreen.m_93215_(p_96295_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96544_ / 4 - 60 + 20, 0xFFFFFF);
        this.f_232750_.m_6516_(p_96295_, this.f_96543_ / 2 - 145, this.f_96544_ / 4, 9, 0xA0A0A0);
        super.m_6305_(p_96295_, p_96296_, p_96297_, p_96298_);
    }
}

