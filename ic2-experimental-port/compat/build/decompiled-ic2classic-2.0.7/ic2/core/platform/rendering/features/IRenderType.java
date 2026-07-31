/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features;

import com.google.common.base.Objects;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IRenderType {
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType();

    @OnlyIn(value=Dist.CLIENT)
    default public boolean canRenderInLayer(BlockState state, RenderType type) {
        return Objects.equal((Object)type, (Object)this.getType());
    }
}

