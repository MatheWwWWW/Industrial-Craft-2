/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features.block;

import ic2.core.utils.plugins.IRegistryProvider;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IBlockModel
extends IRegistryProvider {
    public static final AABB DEFAULT_BOUNDS = new AABB(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    default public List<BlockState> getModelStates() {
        return ((Block)this).m_49965_().m_61056_();
    }

    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState var1, Direction var2);

    @OnlyIn(value=Dist.CLIENT)
    default public TextureAtlasSprite getSpriteForParticle(BlockState state) {
        return this.getSpriteForState(state, Direction.NORTH);
    }

    default public AABB getModelBounds(BlockState state) {
        return DEFAULT_BOUNDS;
    }

    default public boolean isFullCube(BlockState state) {
        return true;
    }
}

