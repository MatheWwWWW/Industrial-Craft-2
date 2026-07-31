/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.platform.rendering.features.block;

import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockModifiers {
    public boolean hasTextureRotation(BlockState var1, Direction var2);

    public int getTextureRotation(BlockState var1, Direction var2);

    public boolean hasCustomTextureUVs(BlockState var1, Direction var2);

    public float[] getCustomTextureUVs(BlockState var1, Direction var2);

    public static interface IAutoUV
    extends IBlockModifiers {
        @Override
        default public boolean hasTextureRotation(BlockState state, Direction side) {
            return false;
        }

        @Override
        default public int getTextureRotation(BlockState state, Direction side) {
            return 0;
        }

        @Override
        default public boolean hasCustomTextureUVs(BlockState state, Direction side) {
            return this instanceof IBlockModel;
        }

        @Override
        default public float[] getCustomTextureUVs(BlockState state, Direction side) {
            return ShapeBuilder.uvsByFace(side, ((IBlockModel)((Object)this)).getModelBounds(state));
        }
    }
}

