/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.network.chat.Component;

public class ChatOptionsScreen
extends SimpleOptionsSubScreen {
    public ChatOptionsScreen(Screen p_95571_, Options p_95572_) {
        super(p_95571_, p_95572_, Component.m_237115_("options.chat.title"), new OptionInstance[]{p_95572_.m_232090_(), p_95572_.m_231814_(), p_95572_.m_231815_(), p_95572_.m_231816_(), p_95572_.m_232098_(), p_95572_.m_232104_(), p_95572_.m_232110_(), p_95572_.m_232101_(), p_95572_.m_232118_(), p_95572_.m_232113_(), p_95572_.m_232117_(), p_95572_.m_232116_(), p_95572_.m_231930_(), p_95572_.m_231813_(), p_95572_.m_231833_(), p_95572_.m_231824_(), p_95572_.m_231835_(), p_95572_.m_231836_()});
    }
}

