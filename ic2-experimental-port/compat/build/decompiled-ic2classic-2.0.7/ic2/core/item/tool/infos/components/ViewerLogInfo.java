/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2LongMaps
 *  it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.infos.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IMonitorRenderer;
import ic2.core.utils.tooltips.ILangHelper;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.time.Duration;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ViewerLogInfo
implements IDisplayInfo,
ILangHelper {
    Supplier<Object2LongMap<String>> dataProvider;
    BooleanSupplier aliveProvider;
    Object2LongMap<String> data;
    int color;

    public ViewerLogInfo(Supplier<Object2LongMap<String>> dataProvider, BooleanSupplier aliveProvider, int color) {
        this.dataProvider = dataProvider;
        this.aliveProvider = aliveProvider;
        this.color = color;
    }

    public ViewerLogInfo(FriendlyByteBuf buffer) {
        this.data = new Object2LongOpenHashMap();
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            this.data.put((Object)buffer.m_130277_(), buffer.readLong());
        }
        this.color = buffer.readInt();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack stack, int x, int y, int width, int height, IDisplayInfo.Alignment align, IMonitorRenderer helper) {
        Font font = helper.getFont();
        int heightConsumed = 0;
        for (FormattedCharSequence sub : font.m_92923_((FormattedText)this.buildText(), width)) {
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
        int n = font.m_92865_().m_92414_((FormattedText)this.buildText(), width, Style.f_131099_).size();
        Objects.requireNonNull(font);
        return n * 9;
    }

    private Component buildText() {
        long time = Minecraft.m_91087_().f_91073_.m_46467_();
        MutableComponent comp = this.string();
        ObjectIterator iter = Object2LongMaps.fastIterator(this.data);
        while (iter.hasNext()) {
            Object2LongMap.Entry entry = (Object2LongMap.Entry)iter.next();
            comp.m_130946_((String)entry.getKey()).m_130946_(": ").m_7220_(this.getDuration(Duration.ofSeconds((time - entry.getLongValue()) / 20L)));
            if (!iter.hasNext()) continue;
            comp.m_130946_("\n");
        }
        return comp;
    }

    private Component getDuration(Duration duration) {
        if (duration.toHours() > 1L) {
            return this.translate("gui.ic2.cards.ago.hours", duration.toHours());
        }
        if (duration.toMinutes() > 1L) {
            return this.translate("gui.ic2.cards.ago.minutes", duration.toMinutes());
        }
        return this.translate("gui.ic2.cards.ago.seconds", duration.getSeconds());
    }

    @Override
    public boolean isValid() {
        return this.aliveProvider.getAsBoolean();
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        Object2LongMap<String> data = this.dataProvider.get();
        buffer.m_130130_(data.size());
        for (Object2LongMap.Entry entry : Object2LongMaps.fastIterable(data)) {
            buffer.m_130070_((String)entry.getKey());
            buffer.writeLong(entry.getLongValue());
        }
        buffer.writeInt(this.color);
    }

    @Override
    public Tag getServerData() {
        ListTag list = new ListTag();
        Object2LongMap<String> data = this.dataProvider.get();
        for (Object2LongMap.Entry entry : Object2LongMaps.fastIterable(data)) {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128359_("user", (String)entry.getKey());
            nbt.m_128356_("lastView", entry.getLongValue());
            list.add((Object)nbt);
        }
        return list;
    }
}

