/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GenericDirtMessageScreen
extends Screen {
    public GenericDirtMessageScreen(Component p_96061_) {
        super(p_96061_);
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    @Override
    public void m_6305_(PoseStack p_96063_, int p_96064_, int p_96065_, float p_96066_) {
        this.m_96626_(0);
        GenericDirtMessageScreen.m_93215_(p_96063_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 70, 0xFFFFFF);
        super.m_6305_(p_96063_, p_96064_, p_96065_, p_96066_);
    }
}

