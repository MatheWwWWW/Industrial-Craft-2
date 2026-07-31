/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.wiki.components.BaseWikiComponent;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HeaderComponent
extends BaseWikiComponent {
    Component header;
    int offset;

    public HeaderComponent(Component header) {
        this(header, Minecraft.m_91087_().f_91062_.m_92923_((FormattedText)header, 118).size());
    }

    public HeaderComponent(Component header, int lines) {
        super(6 + lines * 9);
        this.header = header;
        this.offset = lines * 9;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addSearchString(Consumer<Component> listener) {
        listener.accept(this.header);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        screen.drawColoredRegion(stack, x, y + this.offset, 118.0f, 1.0f, -12829907);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderForeground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (FormattedCharSequence process : screen.getFont().m_92923_((FormattedText)this.header, 118)) {
            screen.drawCenterString(stack, process, x + 62, y, -12829907);
            y += 9;
        }
    }

    public Component getHeader() {
        return this.header;
    }
}

