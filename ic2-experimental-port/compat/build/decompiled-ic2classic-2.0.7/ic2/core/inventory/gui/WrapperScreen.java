/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 */
package ic2.core.inventory.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WrapperScreen
extends Screen {
    Screen parent;

    public WrapperScreen(Screen parent) {
        super((Component)Component.m_237113_((String)"Transition"));
        this.parent = parent;
    }

    public void m_6305_(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        this.getMinecraft().m_91152_(this.parent);
    }
}

