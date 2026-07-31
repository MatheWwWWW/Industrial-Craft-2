/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features.item;

import ic2.core.platform.rendering.features.item.IMultiItemModel;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IItemModel
extends IMultiItemModel {
    public List<ItemStack> getModelTypes();

    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack var1);
}

