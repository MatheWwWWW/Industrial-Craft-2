/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 */
package ic2.core.model;

import ic2.core.block.state.Ic2BlockState;
import ic2.core.model.ModelUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface ISpecialParticleModel
extends IBakedModel {
    default public boolean needsEnhancing(IBlockState state) {
        return ModelUtil.getMissingModel().func_177554_e().func_94215_i().equals(this.func_177554_e().func_94215_i());
    }

    default public void enhanceParticle(Particle particle, Ic2BlockState.Ic2BlockStateInstance state) {
        particle.func_187117_a(this.getParticleTexture(state));
    }

    default public TextureAtlasSprite getParticleTexture(Ic2BlockState.Ic2BlockStateInstance state) {
        return this.func_177554_e();
    }
}

