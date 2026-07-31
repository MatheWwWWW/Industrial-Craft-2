/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.model.Material
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.base.misc;

import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IAtlasProvider {
    @OnlyIn(value=Dist.CLIENT)
    public ResourceLocation getAtlas();

    @OnlyIn(value=Dist.CLIENT)
    public ResourceLocation getAtlasTexture();

    @OnlyIn(value=Dist.CLIENT)
    default public Material createMaterial() {
        return new Material(this.getAtlas(), this.getAtlasTexture());
    }
}

