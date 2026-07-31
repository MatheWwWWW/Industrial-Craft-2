/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;

public class OnlineOptionsScreen
extends SimpleOptionsSubScreen {
    public OnlineOptionsScreen(Screen p_193843_, Options p_193844_) {
        super(p_193843_, p_193844_, Component.m_237115_("options.online.title"), new OptionInstance[]{p_193844_.m_231822_(), p_193844_.m_231823_()});
    }

    @Override
    protected void m_7853_() {
        if (this.f_96541_.f_91073_ != null) {
            CycleButton<Difficulty> $$0 = this.m_142416_(OptionsScreen.m_193846_(this.f_96666_.length, this.f_96543_, this.f_96544_, "options.difficulty.online", this.f_96541_));
            $$0.f_93623_ = false;
        }
        super.m_7853_();
    }
}

