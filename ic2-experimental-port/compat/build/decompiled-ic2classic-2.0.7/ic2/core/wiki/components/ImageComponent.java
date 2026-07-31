/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.wiki.components.BaseWikiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ImageComponent
extends BaseWikiComponent {
    ResourceLocation texture;
    float height;
    float width;
    float offset;

    public ImageComponent(ResourceLocation texture, float width, float height, float offset) {
        super(Mth.m_14167_((float)height));
        this.texture = texture;
        this.height = height;
        this.width = width;
        this.offset = offset;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        screen.bindTexture(this.texture);
        screen.drawTextureRegion(stack, (float)x + this.offset, y, 0.0f, 0.0f, this.width, this.height, 256.0f, 256.0f, -1);
    }
}

