/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.api.tiles.display.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IMonitorRenderer;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StringDisplayInfo
implements IDisplayInfo {
    Component comp;
    Supplier<Component> textProvider;
    BooleanSupplier aliveProvider;

    public StringDisplayInfo(Supplier<Component> textProvider, BooleanSupplier aliveProvider) {
        this.textProvider = textProvider;
        this.aliveProvider = aliveProvider;
    }

    public StringDisplayInfo(FriendlyByteBuf buffer) {
        this.comp = buffer.m_130238_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack stack, int x, int y, int width, int height, IDisplayInfo.Alignment align, IMonitorRenderer helper) {
        Font font = helper.getFont();
        int heightConsumed = 0;
        for (FormattedCharSequence sub : font.m_92923_((FormattedText)this.comp, width)) {
            Objects.requireNonNull(font);
            if (height - heightConsumed < 9) {
                return;
            }
            font.m_92733_(sub, (float)(x - align.getXOffset(font.m_92724_(sub))), (float)(y + heightConsumed), -1, false, stack.m_85850_().m_85861_(), (MultiBufferSource)helper.getBatcher(), false, 0, 0xF000F0);
            Objects.requireNonNull(font);
            heightConsumed += 9;
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getHeight(int width, IDisplayInfo.Alignment align) {
        Font font = Minecraft.m_91087_().f_91062_;
        int n = font.m_92865_().m_92414_((FormattedText)this.comp, width, Style.f_131099_).size();
        Objects.requireNonNull(font);
        return n * 9;
    }

    @Override
    public boolean isValid() {
        return this.aliveProvider.getAsBoolean();
    }

    @Override
    public Tag getServerData() {
        return StringTag.m_129297_((String)this.textProvider.get().getString());
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130083_(this.textProvider.get());
    }
}

