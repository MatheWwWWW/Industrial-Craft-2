/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features;

import ic2.core.platform.rendering.features.IRenderType;
import java.util.Objects;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICamouflageRenderType
extends IRenderType {
    @Override
    @OnlyIn(value=Dist.CLIENT)
    default public RenderType getType() {
        return null;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    default public boolean canRenderInLayer(BlockState state, RenderType type) {
        return Objects.equals(type, RenderType.m_110463_()) || Objects.equals(type, RenderType.m_110466_());
    }
}

