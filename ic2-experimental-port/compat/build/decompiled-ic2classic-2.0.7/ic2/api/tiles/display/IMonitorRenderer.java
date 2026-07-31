/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.api.tiles.display;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public interface IMonitorRenderer {
    public MultiBufferSource.BufferSource getBatcher();

    public Font getFont();

    public ItemRenderer getItemRenderer();

    public void renderGuiItems(PoseStack var1, ItemStack var2, float var3, float var4);

    default public void renderGuiItemText(PoseStack matrixstack, ItemStack stack, float x, float y, String text) {
        this.renderGuiItemText(matrixstack, this.getFont(), stack, x, y, text);
    }

    public void renderGuiItemText(PoseStack var1, Font var2, ItemStack var3, float var4, float var5, String var6);
}

