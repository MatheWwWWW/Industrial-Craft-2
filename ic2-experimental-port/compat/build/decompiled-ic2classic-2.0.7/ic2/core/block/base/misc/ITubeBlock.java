/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.base.misc;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITubeBlock {
    public float getRadius(BlockState var1);

    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(BlockState var1, boolean var2, Direction var3, Direction var4);

    public VoxelShape[] getRealShapes(BlockState var1, BlockGetter var2, BlockPos var3);

    public int getHighlightColor(BlockState var1);

    public DyeColor getBlockColor(BlockState var1);

    public static Direction isClickingAt(Vec3 subHit, Direction base, float radius) {
        float min = 0.5f - radius;
        float max = 0.5f + radius;
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return base;
        }
        if (subHit.f_82479_ >= 0.0 && subHit.f_82479_ <= (double)min && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return Direction.WEST;
        }
        if (subHit.f_82479_ >= (double)max && subHit.f_82479_ <= 1.0 && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return Direction.EAST;
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= 0.0 && subHit.f_82480_ <= (double)min && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return Direction.DOWN;
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)max && subHit.f_82480_ <= 1.0 && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return Direction.UP;
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= 0.0 && subHit.f_82481_ <= (double)min) {
            return Direction.NORTH;
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)max && subHit.f_82481_ <= 1.0) {
            return Direction.SOUTH;
        }
        return null;
    }

    public static AABB getHitBox(Vec3 subHit, Direction base, float radius) {
        float min = 0.5f - radius;
        float max = 0.5f + radius;
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return new AABB((double)min, (double)min, (double)min, (double)max, (double)max, (double)max);
        }
        if (subHit.f_82479_ >= 0.0 && subHit.f_82479_ <= (double)min && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return new AABB(0.0, (double)min, (double)min, (double)min, (double)max, (double)max);
        }
        if (subHit.f_82479_ >= (double)max && subHit.f_82479_ <= 1.0 && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return new AABB((double)max, (double)min, (double)min, 1.0, (double)max, (double)max);
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= 0.0 && subHit.f_82480_ <= (double)min && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return new AABB((double)min, 0.0, (double)min, (double)max, (double)min, (double)max);
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)max && subHit.f_82480_ <= 1.0 && subHit.f_82481_ >= (double)min && subHit.f_82481_ <= (double)max) {
            return new AABB((double)min, (double)max, (double)min, (double)max, 1.0, (double)max);
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= 0.0 && subHit.f_82481_ <= (double)min) {
            return new AABB((double)min, (double)min, 0.0, (double)max, (double)max, (double)min);
        }
        if (subHit.f_82479_ >= (double)min && subHit.f_82479_ <= (double)max && subHit.f_82480_ >= (double)min && subHit.f_82480_ <= (double)max && subHit.f_82481_ >= (double)max && subHit.f_82481_ <= 1.0) {
            return new AABB((double)min, (double)min, (double)max, (double)max, (double)max, 1.0);
        }
        return null;
    }
}

