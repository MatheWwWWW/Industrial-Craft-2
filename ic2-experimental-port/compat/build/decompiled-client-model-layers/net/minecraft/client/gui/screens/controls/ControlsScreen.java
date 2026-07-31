/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.controls;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ControlsScreen
extends OptionsSubScreen {
    private static final int f_202378_ = 24;

    public ControlsScreen(Screen p_97519_, Options p_97520_) {
        super(p_97519_, p_97520_, Component.m_237115_("controls.title"));
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        int $$0 = this.f_96543_ / 2 - 155;
        int $$1 = $$0 + 160;
        int $$2 = this.f_96544_ / 6 - 12;
        this.m_142416_(new Button($$0, $$2, 150, 20, Component.m_237115_("options.mouse_settings"), p_97540_ -> this.f_96541_.m_91152_(new MouseSettingsScreen(this, this.f_96282_))));
        this.m_142416_(new Button($$1, $$2, 150, 20, Component.m_237115_("controls.keybinds"), p_97538_ -> this.f_96541_.m_91152_(new KeyBindsScreen(this, this.f_96282_))));
        this.m_142416_(this.f_96282_.m_231831_().m_231507_(this.f_96282_, $$0, $$2 += 24, 150));
        this.m_142416_(this.f_96282_.m_231832_().m_231507_(this.f_96282_, $$1, $$2, 150));
        this.m_142416_(this.f_96282_.m_231812_().m_231507_(this.f_96282_, $$0, $$2 += 24, 150));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, $$2 += 24, 200, 20, CommonComponents.f_130655_, p_97535_ -> this.f_96541_.m_91152_(this.f_96281_)));
    }

    @Override
    public void m_6305_(PoseStack p_97530_, int p_97531_, int p_97532_, float p_97533_) {
        this.m_7333_(p_97530_);
        ControlsScreen.m_93215_(p_97530_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 0xFFFFFF);
        super.m_6305_(p_97530_, p_97531_, p_97532_, p_97533_);
    }
}

