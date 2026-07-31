/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.nbt.LongTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.infos.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IMonitorRenderer;
import ic2.core.utils.math.ColorUtils;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TimeDisplayInfo
implements IDisplayInfo {
    int color;
    boolean cut;

    public TimeDisplayInfo(int color, boolean cut) {
        this.color = color;
        this.cut = cut;
    }

    public TimeDisplayInfo(FriendlyByteBuf buffer) {
        this.color = buffer.readInt();
        this.cut = buffer.readBoolean();
    }

    @Override
    public Tag getServerData() {
        return LongTag.m_128882_((long)((this.cut ? 1L : 0L) << 33 | (long)this.color));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack stack, int x, int y, int width, int height, IDisplayInfo.Alignment align, IMonitorRenderer helper) {
        Font font = helper.getFont();
        int heightConsumed = 0;
        for (FormattedCharSequence sub : font.m_92923_((FormattedText)this.getTime(), width)) {
            Objects.requireNonNull(font);
            if (height - heightConsumed < 9) {
                return;
            }
            font.m_92733_(sub, (float)(x - align.getXOffset(font.m_92724_(sub))), (float)(y + heightConsumed), -1, false, stack.m_85850_().m_85861_(), (MultiBufferSource)helper.getBatcher(), false, 0, 0xF000F0);
            Objects.requireNonNull(font);
            heightConsumed += 9;
        }
    }

    private Component getTime() {
        if (this.cut) {
            return Component.m_237113_((String)LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))).m_130948_(ColorUtils.toColor(this.color));
        }
        return Component.m_237110_((String)"tooltip.item.ic2.time_card.real", (Object[])new Object[]{LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))}).m_130948_(ColorUtils.toColor(this.color));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getHeight(int width, IDisplayInfo.Alignment align) {
        Font font = Minecraft.m_91087_().f_91062_;
        int n = font.m_92865_().m_92414_((FormattedText)this.getTime(), width, Style.f_131099_).size();
        Objects.requireNonNull(font);
        return n * 9;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeInt(this.color);
        buffer.writeBoolean(this.cut);
    }
}

