/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.EntityCollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.inherit;

import ic2.core.IC2;
import ic2.core.ref.Ic2Blocks;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Ic2SheetBlock
extends Block {
    private static final VoxelShape aabb = Shapes.m_83048_((double)0.0, (double)0.0, (double)0.0, (double)1.0, (double)0.125, (double)1.0);
    private static final Direction[] positiveHorizontalFacings = new Direction[]{Direction.EAST, Direction.SOUTH};

    public Ic2SheetBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoxelShape m_5940_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return aabb;
    }

    public VoxelShape m_5939_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        EntityCollisionContext entityCollisionContext;
        if (blockState.m_60734_() == Ic2Blocks.RESIN_SHEET) {
            return Shapes.m_83040_();
        }
        if (blockState.m_60734_() == Ic2Blocks.WOOL_SHEET && collisionContext instanceof EntityCollisionContext && (entityCollisionContext = (EntityCollisionContext)collisionContext).m_193113_() instanceof Player && (entityCollisionContext.m_193113_().m_6144_() || entityCollisionContext.m_193113_().m_20186_() < (double)blockPos.m_123342_() + 0.125 - (double)entityCollisionContext.m_193113_().f_19793_)) {
            return Shapes.m_83040_();
        }
        return aabb;
    }

    private boolean isValidPosition(Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.m_60734_() == Ic2Blocks.RESIN_SHEET) {
            return this.isNormalCubeBelow(level, blockPos);
        }
        if (blockState.m_60734_() == Ic2Blocks.RUBBER_SHEET) {
            for (Direction direction : Util.HORIZONTAL_DIRS) {
                blockState = level.m_8055_(blockPos.m_121945_(direction));
                if (blockState != Ic2Blocks.RUBBER_SHEET.m_49966_() && !blockState.m_60734_().m_180643_(blockState, (BlockGetter)level, blockPos)) continue;
                return true;
            }
            return this.isNormalCubeBelow(level, blockPos);
        }
        return blockState.m_60734_() == Ic2Blocks.WOOL_SHEET;
    }

    private boolean isNormalCubeBelow(Level level, BlockPos blockPos) {
        blockPos = blockPos.m_7495_();
        BlockState blockState = level.m_8055_(blockPos);
        return blockState.m_60734_().m_180643_(blockState, (BlockGetter)level, blockPos);
    }

    public void m_6861_(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!this.isValidPosition(level, blockPos, blockState)) {
            Block.m_49892_((BlockState)blockState, (LevelAccessor)level, (BlockPos)blockPos, null);
            level.m_7471_(blockPos, false);
        }
    }

    public void m_7892_(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (blockState.m_60734_() == Ic2Blocks.RESIN_SHEET) {
            entity.f_19789_ = (float)((double)entity.f_19789_ * 0.75);
            entity.m_20334_(entity.m_20184_().m_7096_() * 0.6, entity.m_20184_().m_7098_() * 0.85, entity.m_20184_().m_7094_() * 0.6);
        } else if (blockState.m_60734_() == Ic2Blocks.RUBBER_SHEET) {
            if (!level.m_46859_(blockPos.m_7495_())) {
                return;
            }
            if (entity instanceof LivingEntity && !Ic2SheetBlock.canSupportWeight(level, blockPos)) {
                level.m_46796_(2001, blockPos, Block.m_49956_((BlockState)blockState));
                level.m_7471_(blockPos, false);
                return;
            }
            if (entity.m_20184_().m_7098_() <= -0.4) {
                entity.f_19789_ = 0.0f;
                entity.m_20334_(entity.m_20184_().m_7096_() * 1.1, entity.m_20184_().m_7098_(), entity.m_20184_().m_7094_() * 1.1);
                if (entity instanceof LivingEntity) {
                    if (entity instanceof Player && IC2.keyboard.isJumpKeyDown((Player)entity)) {
                        entity.m_20334_(entity.m_20184_().m_7096_(), entity.m_20184_().m_7098_() * -1.3, entity.m_20184_().m_7094_());
                    } else if (entity instanceof Player && entity.m_6144_()) {
                        entity.m_20334_(entity.m_20184_().m_7096_(), entity.m_20184_().m_7098_() * -0.1, entity.m_20184_().m_7094_());
                    } else {
                        entity.m_20334_(entity.m_20184_().m_7096_(), entity.m_20184_().m_7098_() * -0.8, entity.m_20184_().m_7094_());
                    }
                } else {
                    entity.m_20334_(entity.m_20184_().m_7096_(), entity.m_20184_().m_7098_() * -0.8, entity.m_20184_().m_7094_());
                }
            } else if (blockState.m_60734_() == Ic2Blocks.WOOL_SHEET) {
                entity.f_19789_ = (float)((double)entity.f_19789_ * 0.95);
            }
        }
    }

    private static boolean canSupportWeight(Level level, BlockPos blockPos) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        block0: for (Direction direction : positiveHorizontalFacings) {
            for (int i = -1; i <= 1; i += 2) {
                mutableBlockPos.m_122190_((Vec3i)blockPos);
                boolean bl = false;
                for (int j = 0; j < 16; ++j) {
                    mutableBlockPos.m_122175_(direction, i);
                    BlockState blockState = level.m_8055_((BlockPos)mutableBlockPos);
                    if (blockState.m_60734_().m_180643_(blockState, (BlockGetter)level, (BlockPos)mutableBlockPos)) {
                        bl = true;
                        break;
                    }
                    if (blockState != Ic2Blocks.RUBBER_SHEET.m_49966_()) break;
                    mutableBlockPos.m_122173_(Direction.DOWN);
                    BlockState blockState2 = level.m_8055_((BlockPos)mutableBlockPos);
                    if (blockState2.m_60734_().m_180643_(blockState2, (BlockGetter)level, (BlockPos)mutableBlockPos)) {
                        bl = true;
                        break;
                    }
                    mutableBlockPos.m_122173_(Direction.UP);
                }
                if (!bl) continue block0;
                if (i != 1) continue;
                return true;
            }
        }
        return false;
    }
}

