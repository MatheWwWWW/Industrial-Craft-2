/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.config;

import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.screen.ListSelectionScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class SelectionElement
extends ConfigElement {
    Button textBox = (Button)this.addChild(new ExtendedButton(0, 0, 72, 18, (Component)Component.m_237115_((String)"gui.ic2.edit"), this::onPress));

    public SelectionElement(IConfigNode node) {
        super(node);
    }

    private void onPress(Button button) {
        this.mc.m_91152_((Screen)ListSelectionScreen.ofValue(this.mc.f_91080_, this.node, this.value, this.owner.getCustomTexture()));
    }
}

