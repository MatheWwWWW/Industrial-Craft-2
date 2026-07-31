/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MouseSettingsScreen
extends OptionsSubScreen {
    private OptionsList f_96218_;

    private static OptionInstance<?>[] m_232748_(Options p_232749_) {
        return new OptionInstance[]{p_232749_.m_231964_(), p_232749_.m_231820_(), p_232749_.m_232122_(), p_232749_.m_231821_(), p_232749_.m_231828_()};
    }

    public MouseSettingsScreen(Screen p_96222_, Options p_96223_) {
        super(p_96222_, p_96223_, Component.m_237115_("options.mouse_settings.title"));
    }

    @Override
    protected void m_7856_() {
        this.f_96218_ = new OptionsList(this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 32, 25);
        if (InputConstants.m_84826_()) {
            this.f_96218_.m_232533_((OptionInstance[])Stream.concat(Arrays.stream(MouseSettingsScreen.m_232748_(this.f_96282_)), Stream.of(this.f_96282_.m_232123_())).toArray(OptionInstance[]::new));
        } else {
            this.f_96218_.m_232533_(MouseSettingsScreen.m_232748_(this.f_96282_));
        }
        this.m_7787_(this.f_96218_);
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 27, 200, 20, CommonComponents.f_130655_, p_96232_ -> {
            this.f_96282_.m_92169_();
            this.f_96541_.m_91152_(this.f_96281_);
        }));
    }

    @Override
    public void m_6305_(PoseStack p_96227_, int p_96228_, int p_96229_, float p_96230_) {
        this.m_7333_(p_96227_);
        this.f_96218_.m_6305_(p_96227_, p_96228_, p_96229_, p_96230_);
        MouseSettingsScreen.m_93215_(p_96227_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 5, 0xFFFFFF);
        super.m_6305_(p_96227_, p_96228_, p_96229_, p_96230_);
    }
}

