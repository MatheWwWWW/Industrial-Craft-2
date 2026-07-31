/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.AbstractButton
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.client.gui.ScreenUtils
 */
package ic2.core.utils.config.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.widgets.GuiUtils;
import ic2.core.utils.config.gui.widgets.Icon;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ScreenUtils;

public class CarbonIconButton
extends AbstractButton {
    Consumer<CarbonIconButton> listener;
    Icon icon;
    boolean iconOnly = false;

    public CarbonIconButton(int x, int y, int width, int height, Icon icon, Component name, Consumer<CarbonIconButton> listener) {
        super(x, y, width, height, name);
        this.listener = listener;
        this.icon = icon;
    }

    public CarbonIconButton setIconOnly() {
        this.iconOnly = true;
        return this;
    }

    public void m_6303_(PoseStack stack, int mouseX, int mouseY, float p_93679_) {
        int k = this.m_7202_(this.m_198029_());
        ScreenUtils.blitWithBorder((PoseStack)stack, (ResourceLocation)f_93617_, (int)this.f_93620_, (int)this.f_93621_, (int)0, (int)(46 + k * 20), (int)this.f_93618_, (int)this.f_93619_, (int)200, (int)20, (int)2, (int)3, (int)2, (int)2, (float)this.m_93252_());
        if (this.iconOnly) {
            int j = this.getFGColor();
            RenderSystem.m_157429_((float)((float)(j >> 16 & 0xFF) / 255.0f), (float)((float)(j >> 8 & 0xFF) / 255.0f), (float)((float)(j & 0xFF) / 255.0f), (float)1.0f);
            GuiUtils.drawTextureRegion(stack, (float)(this.f_93620_ + this.f_93618_ / 2) - 5.5f, (float)(this.f_93621_ + this.f_93619_ / 2) - 5.5f, 11.0f, 11.0f, this.icon, 16.0f, 16.0f);
            RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            return;
        }
        Minecraft minecraft = Minecraft.m_91087_();
        Font font = minecraft.f_91062_;
        FormattedText text = GuiUtils.ellipsize((FormattedText)this.m_6035_(), this.f_93618_ - 21, font);
        int width = font.m_92852_(text) + 21;
        float minX = this.f_93620_ + 4 + this.f_93618_ / 2 - width / 2;
        int j = this.getFGColor();
        RenderSystem.m_157429_((float)((float)(j >> 16 & 0xFF) / 255.0f), (float)((float)(j >> 8 & 0xFF) / 255.0f), (float)((float)(j & 0xFF) / 255.0f), (float)1.0f);
        GuiUtils.drawTextureRegion(stack, minX, this.f_93621_ + (this.f_93619_ - 8) / 2, 11.0f, 11.0f, this.icon, 16.0f, 16.0f);
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        font.m_92877_(stack, Language.m_128107_().m_5536_(text), minX + 15.0f, (float)(this.f_93621_ + (this.f_93619_ - 8) / 2), j);
    }

    public void m_5691_() {
        if (this.listener == null) {
            return;
        }
        this.listener.accept(this);
    }

    public void m_142291_(NarrationElementOutput output) {
        this.m_168802_(output);
    }
}

