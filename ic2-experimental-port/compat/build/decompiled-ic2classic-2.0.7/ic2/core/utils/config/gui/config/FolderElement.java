/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.screen.ConfigScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class FolderElement
extends ConfigElement {
    Button button = (Button)this.addChild(new ExtendedButton(0, 0, 0, 18, (Component)Component.m_237119_(), this::onPress));
    Component name;

    public FolderElement(IConfigNode node, Component name) {
        super(node);
        this.button.m_93666_(node.getName());
        this.name = name.m_6881_().m_7220_((Component)Component.m_237113_((String)" > ").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})).m_7220_(node.getName());
    }

    protected void onPress(Button button) {
        this.mc.m_91152_((Screen)new ConfigScreen(this.name, this.node, this.mc.f_91080_, this.owner.getCustomTexture()));
    }

    @Override
    public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
        this.button.f_93620_ = left;
        this.button.f_93621_ = top;
        this.button.m_93674_(width);
        this.button.m_6305_(poseStack, mouseX, mouseY, partialTicks);
    }
}

