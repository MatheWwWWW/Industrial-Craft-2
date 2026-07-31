/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap$BasicEntry
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.IItemStyle
 *  mcjty.theoneprobe.apiimpl.client.ElementItemStackRender
 *  mcjty.theoneprobe.apiimpl.styles.ItemStyle
 *  mcjty.theoneprobe.rendering.RenderHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.probeplugin.override.components;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IItemStyle;
import mcjty.theoneprobe.apiimpl.client.ElementItemStackRender;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import mcjty.theoneprobe.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GridElement
implements IElement {
    public static final ResourceLocation ELEMENT = new ResourceLocation("ic2", "gridelement");
    int gridSlots;
    int pixelPerSlot;
    int textPadding = 0;
    List<Map.Entry<Component, List<ItemStack>>> display = new ObjectArrayList();
    int[] boundsCache = null;

    public GridElement() {
        this(6, 18);
    }

    public GridElement(Map<Component, List<ItemStack>> rows) {
        this(6, 18);
        this.addRows(rows);
    }

    public GridElement(int gridSlots, int pixelPerSlot) {
        this.gridSlots = gridSlots;
        this.pixelPerSlot = pixelPerSlot;
    }

    public GridElement setTextPadding(int text) {
        this.textPadding = text;
        return this;
    }

    public GridElement addRow(Map.Entry<Component, List<ItemStack>> entry) {
        this.display.add(entry);
        return this;
    }

    public GridElement addRow(Component name, List<ItemStack> items) {
        this.display.add((Map.Entry<Component, List<ItemStack>>)new AbstractObject2ObjectMap.BasicEntry((Object)name, items));
        return this;
    }

    public GridElement addRows(Map<Component, List<ItemStack>> rows) {
        this.display.addAll(rows.entrySet());
        return this;
    }

    public GridElement(FriendlyByteBuf buffer) {
        this.gridSlots = buffer.m_130242_();
        this.pixelPerSlot = buffer.m_130242_();
        this.textPadding = buffer.m_130242_();
        int m = buffer.m_130242_();
        for (int i = 0; i < m; ++i) {
            Component name = buffer.m_130238_();
            int size = buffer.m_130242_();
            NonNullList items = NonNullList.m_122780_((int)size, (Object)ItemStack.f_41583_);
            for (int j = 0; j < size; ++j) {
                ItemStack stack = buffer.m_130267_();
                stack.m_41764_(buffer.m_130242_());
                items.set(j, (Object)stack);
            }
            this.display.add((Map.Entry<Component, List<ItemStack>>)new AbstractObject2ObjectMap.BasicEntry((Object)name, (Object)items));
        }
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.gridSlots);
        buffer.m_130130_(this.pixelPerSlot);
        buffer.m_130130_(this.textPadding);
        buffer.m_130130_(this.display.size());
        int m = this.display.size();
        for (int i = 0; i < m; ++i) {
            Map.Entry<Component, List<ItemStack>> entry = this.display.get(i);
            buffer.m_130083_(entry.getKey());
            List<ItemStack> items = entry.getValue();
            buffer.m_130130_(items.size());
            int n = items.size();
            for (int j = 0; j < n; ++j) {
                ItemStack stack = items.get(j);
                buffer.m_130055_(stack);
                buffer.m_130130_(stack.m_41613_());
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public int calculateGridPadding() {
        Font font = Minecraft.m_91087_().f_91062_;
        int padding = 0;
        int m = this.display.size();
        for (int i = 0; i < m; ++i) {
            int left;
            Map.Entry<Component, List<ItemStack>> entry = this.display.get(i);
            int textWidth = font.m_92724_(entry.getKey().m_7532_()) + this.textPadding;
            double textSlots = Mth.m_14107_((double)((double)textWidth / (double)this.pixelPerSlot));
            if (textSlots + (double)entry.getValue().size() >= (double)this.gridSlots || (left = (int)(textSlots * (double)this.pixelPerSlot - (double)textWidth) + padding) >= 0) continue;
            padding -= left;
        }
        return padding;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack matrix, int x, int y) {
        IItemStyle style = new ItemStyle().bounds(this.pixelPerSlot, this.pixelPerSlot);
        Minecraft mc = Minecraft.m_91087_();
        Font font = mc.f_91062_;
        int yOffset = 0;
        int gridPadding = this.calculateGridPadding();
        int m = this.display.size();
        for (int i = 0; i < m; ++i) {
            int j;
            int n;
            List<ItemStack> items;
            Map.Entry<Component, List<ItemStack>> entry = this.display.get(i);
            int textSlots = Mth.m_14107_((double)((double)(RenderHelper.renderText((Minecraft)mc, (PoseStack)matrix, (int)x, (int)(y + yOffset + 5), (Component)entry.getKey()) + this.textPadding) / (double)this.pixelPerSlot));
            if (textSlots + (items = entry.getValue()).size() <= this.gridSlots) {
                n = items.size();
                for (j = 0; j < n; ++j) {
                    ElementItemStackRender.render((ItemStack)items.get(j), (IItemStyle)style, (PoseStack)matrix, (int)(x + (textSlots + j) * this.pixelPerSlot + gridPadding + 1), (int)(y + yOffset + 1));
                }
                yOffset += this.pixelPerSlot;
                continue;
            }
            Objects.requireNonNull(font);
            yOffset += 9;
            n = items.size();
            for (j = 0; j < n; ++j) {
                ElementItemStackRender.render((ItemStack)items.get(j), (IItemStyle)style, (PoseStack)matrix, (int)(x + j % this.gridSlots * this.pixelPerSlot + gridPadding + 1), (int)(y + yOffset + j / this.gridSlots * this.pixelPerSlot + 6));
            }
            yOffset += Mth.m_14165_((double)((double)items.size() / (double)this.gridSlots)) * this.pixelPerSlot;
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private void renderBounds(PoseStack stack, int x, int y, int width, int height) {
        RenderHelper.drawHorizontalLine((PoseStack)stack, (int)x, (int)y, (int)(x + width - 1), (int)-1);
        RenderHelper.drawHorizontalLine((PoseStack)stack, (int)x, (int)(y + height - 1), (int)(x + width - 1), (int)-1);
        RenderHelper.drawVerticalLine((PoseStack)stack, (int)x, (int)y, (int)(y + height - 1), (int)-1);
        RenderHelper.drawVerticalLine((PoseStack)stack, (int)(x + width - 1), (int)y, (int)(y + height), (int)-1);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void boundsCache() {
        this.boundsCache = new int[2];
        Font font = Minecraft.m_91087_().f_91062_;
        int gridPadding = this.calculateGridPadding();
        int m = this.display.size();
        for (int i = 0; i < m; ++i) {
            int textWidth;
            int textSlots;
            Map.Entry<Component, List<ItemStack>> entry = this.display.get(i);
            int items = entry.getValue().size();
            if (items + (textSlots = Mth.m_14107_((double)((double)(textWidth = font.m_92724_(entry.getKey().m_7532_()) + this.textPadding) / (double)this.pixelPerSlot))) <= this.gridSlots) {
                this.boundsCache[0] = Math.max(this.boundsCache[0], this.pixelPerSlot * (items + textSlots) + gridPadding);
                this.boundsCache[1] = this.boundsCache[1] + this.pixelPerSlot;
                continue;
            }
            if (items > this.gridSlots) {
                this.boundsCache[0] = Math.max(this.boundsCache[0], this.pixelPerSlot * this.gridSlots + gridPadding);
                int n = this.boundsCache[1];
                Objects.requireNonNull(font);
                this.boundsCache[1] = n + (9 + Mth.m_14165_((double)((double)items / (double)this.gridSlots)) * this.pixelPerSlot + 5);
                continue;
            }
            this.boundsCache[0] = Math.max(this.boundsCache[0], Math.max(textWidth, items * this.pixelPerSlot + gridPadding));
            int n = this.boundsCache[1];
            Objects.requireNonNull(font);
            this.boundsCache[1] = n + (9 + this.pixelPerSlot + 5);
        }
    }

    public int getHeight() {
        if (this.boundsCache == null) {
            this.boundsCache();
        }
        return this.boundsCache[1];
    }

    public int getWidth() {
        if (this.boundsCache == null) {
            this.boundsCache();
        }
        return this.boundsCache[0];
    }

    public ResourceLocation getID() {
        return ELEMENT;
    }
}

