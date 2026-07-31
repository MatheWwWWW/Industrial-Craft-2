/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.nbt.LongArrayTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.api.tiles.display.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IMonitorRenderer;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProgressDisplayInfo
implements IDisplayInfo {
    Progress progress;
    Supplier<Progress> progressProvider;
    BooleanSupplier aliveProvider;

    public ProgressDisplayInfo(FriendlyByteBuf buffer) {
        this.progress = new Progress(buffer);
    }

    public ProgressDisplayInfo(Supplier<Progress> progressProvider, BooleanSupplier aliveProvider) {
        this.progressProvider = progressProvider;
        this.aliveProvider = aliveProvider;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack stack, int x, int y, int width, int height, IDisplayInfo.Alignment align, IMonitorRenderer helper) {
        Font font = helper.getFont();
        Objects.requireNonNull(font);
        if (height <= 9 + 2) {
            return;
        }
        Tesselator tes = Tesselator.m_85913_();
        BufferBuilder builder = tes.m_85915_();
        builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Objects.requireNonNull(font);
        int fontHeight = Math.min(9 + 2, height);
        int color = this.progress.mainColor;
        this.drawColorFrame(stack, (VertexConsumer)builder, x -= align.getXOffset(width), y, 1.0f, fontHeight, color);
        this.drawColorFrame(stack, (VertexConsumer)builder, x + width - 2, y, 1.0f, fontHeight, color);
        this.drawColorFrame(stack, (VertexConsumer)builder, x, y, width - 1, 1.0f, color);
        this.drawColorFrame(stack, (VertexConsumer)builder, x, y + fontHeight, width - 1, 1.0f, color);
        int size = (int)((double)(width - 3) * this.progress.progress);
        for (int i = 0; i < size; ++i) {
            this.drawColorFrame(stack, (VertexConsumer)builder, x + i + 1, y + 1, 1.0f, fontHeight - 1, i % 2 == 0 ? color : this.progress.secondaryColor);
        }
        RenderSystem.m_69482_();
        RenderSystem.m_69472_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        tes.m_85914_();
    }

    @OnlyIn(value=Dist.CLIENT)
    public void drawColorFrame(PoseStack stack, VertexConsumer builder, float x, float y, float width, float height, int color) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = color >> 24 & 0xFF;
        builder.m_85982_(matrix, x, y + height, 0.0f).m_6122_(r, g, b, a).m_5752_();
        builder.m_85982_(matrix, x + width, y + height, 0.0f).m_6122_(r, g, b, a).m_5752_();
        builder.m_85982_(matrix, x + width, y, 0.0f).m_6122_(r, g, b, a).m_5752_();
        builder.m_85982_(matrix, x, y, 0.0f).m_6122_(r, g, b, a).m_5752_();
    }

    public static int darker(int color, float factor) {
        int r = Math.max(0, (int)((float)(color >> 16 & 0xFF) * factor));
        int g = Math.max(0, (int)((float)(color >> 8 & 0xFF) * factor));
        int b = Math.max(0, (int)((float)(color & 0xFF) * factor));
        return color & 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getHeight(int width, IDisplayInfo.Alignment align) {
        Objects.requireNonNull(Minecraft.m_91087_().f_91062_);
        return 9 + 5;
    }

    @Override
    public boolean isValid() {
        return this.aliveProvider.getAsBoolean();
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        this.progressProvider.get().serialize(buffer);
    }

    @Override
    public Tag getServerData() {
        Progress progress = this.progressProvider.get();
        return new LongArrayTag(new long[]{Double.doubleToLongBits(progress.progress), progress.mainColor, progress.secondaryColor});
    }

    public static class Progress {
        double progress;
        int mainColor;
        int secondaryColor;

        public Progress(FriendlyByteBuf buffer) {
            this.progress = buffer.readDouble();
            this.mainColor = buffer.readInt();
            this.secondaryColor = buffer.readInt();
        }

        public Progress(double progress, int mainColor) {
            this(progress, mainColor, mainColor);
        }

        public Progress(double progress, int mainColor, int secondaryColor) {
            this.progress = progress;
            this.mainColor = mainColor;
            this.secondaryColor = secondaryColor;
        }

        public void serialize(FriendlyByteBuf buffer) {
            buffer.writeDouble(this.progress);
            buffer.writeInt(this.mainColor);
            buffer.writeInt(this.secondaryColor);
        }
    }
}

