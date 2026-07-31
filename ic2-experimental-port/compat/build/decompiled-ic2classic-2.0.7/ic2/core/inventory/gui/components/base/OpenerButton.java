/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.network.chat.Component
 */
package ic2.core.inventory.gui.components.base;

import ic2.core.inventory.gui.components.base.ToolTipButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class OpenerButton
extends ToolTipButton {
    public OpenerButton(int xPos, int yPos, int width, int height, Component displayString, Button.OnPress handler) {
        super(xPos, yPos, width, height, displayString, handler);
    }

    public void m_5691_() {
        super.m_5691_();
        this.m_93692_(false);
    }
}

