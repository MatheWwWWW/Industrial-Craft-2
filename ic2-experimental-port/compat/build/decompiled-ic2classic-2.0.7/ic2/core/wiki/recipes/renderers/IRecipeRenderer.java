/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.recipes.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.rendering.RenderUtils;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IRecipeRenderer {
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen var1, PoseStack var2, int var3, int var4);

    @OnlyIn(value=Dist.CLIENT)
    public void renderItems(IC2Screen var1, PoseStack var2, int var3, int var4);

    @OnlyIn(value=Dist.CLIENT)
    public List<Component> getHoverInfo(IC2Screen var1, int var2, int var3, int var4, int var5);

    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getHoverStack(IC2Screen var1, int var2, int var3, int var4, int var5);

    @OnlyIn(value=Dist.CLIENT)
    default public List<Component> getToolTip(ItemStack stack) {
        return stack.m_41651_(IC2.PLATFORM.getClientPlayerInstance(), (TooltipFlag)TooltipFlag.Default.NORMAL);
    }

    @OnlyIn(value=Dist.CLIENT)
    default public void renderMultiOutput(PoseStack matrix, List<ItemStack> items, int x, int y, int clock, Font font, ItemRenderer render) {
        if (items.size() > 0) {
            ItemStack stack = items.get(clock % items.size());
            RenderUtils.renderGuiItem(render, matrix, stack, x, y);
            RenderUtils.renderGuiItemDecorations(matrix, font, stack, x, y);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    default public void renderMultiOutput(PoseStack matrix, ItemStack[] items, int x, int y, int clock, Font font, ItemRenderer render) {
        if (items.length > 0) {
            ItemStack stack = items[clock % items.length];
            RenderUtils.renderGuiItem(render, matrix, stack, x, y);
            RenderUtils.renderGuiItemDecorations(matrix, font, stack, x, y);
        }
    }
}

