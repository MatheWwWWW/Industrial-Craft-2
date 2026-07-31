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
import ic2.core.platform.rendering.models.items.BaseItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class SimpleItemModel
extends BaseItemModel<SimpleItemModel> {
    ItemStack stack;

    public SimpleItemModel(ItemStack stack, TextureAtlasSprite sprite) {
        this.stack = stack;
        this.setParticleTexture(sprite);
    }

    private SimpleItemModel() {
    }

    @Override
    public void init() {
        this.quads.addAll(QuadBaker.createQuadsFromTexture(this.getTintedIndex(this.stack, 0), this.m_6160_(), this.getTransformMap().f_111792_));
        this.initOther(new SimpleItemModel());
    }
}

