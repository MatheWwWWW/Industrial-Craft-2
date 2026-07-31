/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.models.items;

import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.features.item.ILayeredItemModel;
import ic2.core.platform.rendering.models.items.BaseItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class SimpleLayeredItemModel
extends BaseItemModel<SimpleLayeredItemModel> {
    ItemStack stack;
    ILayeredItemModel layer;

    public SimpleLayeredItemModel(ItemStack stack, TextureAtlasSprite sprite, ILayeredItemModel model) {
        this.stack = stack;
        this.layer = model;
        this.setParticleTexture(sprite);
    }

    private SimpleLayeredItemModel() {
    }

    @Override
    public void init() {
        int layerCount = this.layer.getLayerCount(this.stack);
        for (int i = 0; i < layerCount; ++i) {
            this.quads.addAll(QuadBaker.createQuadsFromTexture(this.getTintedIndex(this.stack, i), this.layer.getSpriteForLayer(this.stack, i), this.getTransformMap().f_111792_));
        }
        this.initOther(new SimpleLayeredItemModel());
    }
}

