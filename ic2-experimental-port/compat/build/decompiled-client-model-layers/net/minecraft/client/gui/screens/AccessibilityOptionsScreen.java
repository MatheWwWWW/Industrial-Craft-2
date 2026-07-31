/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import net.minecraft.Util;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AccessibilityOptionsScreen
extends SimpleOptionsSubScreen {
    private static final String f_169230_ = "https://aka.ms/MinecraftJavaAccessibility";

    private static OptionInstance<?>[] m_232690_(Options p_232691_) {
        return new OptionInstance[]{p_232691_.m_231930_(), p_232691_.m_231825_(), p_232691_.m_232104_(), p_232691_.m_231827_(), p_232691_.m_232098_(), p_232691_.m_232101_(), p_232691_.m_232118_(), p_232691_.m_231812_(), p_232691_.m_231831_(), p_232691_.m_231832_(), p_232691_.m_231924_(), p_232691_.m_231925_(), p_232691_.m_231838_(), p_232691_.m_231935_(), p_232691_.m_231926_()};
    }

    public AccessibilityOptionsScreen(Screen p_95504_, Options p_95505_) {
        super(p_95504_, p_95505_, Component.m_237115_("options.accessibility.title"), AccessibilityOptionsScreen.m_232690_(p_95505_));
    }

    @Override
    protected void m_7853_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 27, 150, 20, Component.m_237115_("options.accessibility.link"), p_95509_ -> this.f_96541_.m_91152_(new ConfirmLinkScreen(p_169232_ -> {
            if (p_169232_) {
                Util.m_137581_().m_137646_(f_169230_);
            }
            this.f_96541_.m_91152_(this);
        }, f_169230_, true))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 27, 150, 20, CommonComponents.f_130655_, p_95507_ -> this.f_96541_.m_91152_(this.f_96281_)));
    }
}

