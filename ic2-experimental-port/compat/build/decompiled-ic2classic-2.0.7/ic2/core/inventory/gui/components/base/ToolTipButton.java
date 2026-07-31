/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.inventory.gui.components.base;

import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.feature.ITooltipProvider;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ToolTipButton
extends ExtendedButton
implements ITooltipProvider {
    Component toolTip;

    public ToolTipButton(int xPos, int yPos, int width, int height, Component displayString, Button.OnPress handler) {
        super(xPos, yPos, width, height, displayString, handler);
    }

    public ToolTipButton setToolTip(Component toolTip) {
        this.toolTip = toolTip;
        return this;
    }

    public ToolTipButton setToolTip(String s, Object ... args) {
        return this.setToolTip((Component)Component.m_237110_((String)s, (Object[])args));
    }

    public ToolTipButton setToolTip(String s) {
        return this.setToolTip((Component)Component.m_237115_((String)s));
    }

    @Override
    public void addToolTip(IC2Screen gui, int x, int y, Consumer<Component> tooltip) {
        if (this.m_198029_() && this.toolTip != null) {
            tooltip.accept(this.toolTip);
        }
    }
}

