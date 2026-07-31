/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SupportType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.misc;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.ref.Ic2Blocks;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScaffoldBlock
extends Block {
    private static final IRecipeInput stickInput = Recipes.inputFactory.forItem((ItemLike)Items.f_42398_);
    private static final Direction[] supportedFacings = new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private static final double border = 0.03125;
    private static final VoxelShape aabb = Shapes.m_83048_((double)0.03125, (double)0.0, (double)0.03125, (double)0.96875, (double)1.0, (double)0.96875);
    private final int maxDistance;

    public ScaffoldBlock(BlockBehaviour.Properties properties, int n) {
        super(properties);
        this.maxDistance = n;
    }

    public void m_7892_(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.f_19789_ = 0.0f;
            double d = 0.15;
            Vec3 vec3 = livingEntity.m_20184_();
            double d2 = Util.limit(vec3.m_7096_(), -d, d);
            double d3 = Util.limit(vec3.m_7094_(), -d, d);
            livingEntity.m_20334_(d2, vec3.m_7098_(), d3);
            if (livingEntity.m_6144_() && livingEntity instanceof Player) {
                if (livingEntity.m_20069_()) {
                    livingEntity.m_20334_(d2, 0.02, d3);
                } else {
                    livingEntity.m_20334_(d2, 0.08, d3);
                }
            } else if (livingEntity.f_19862_) {
                livingEntity.m_20334_(d2, 0.2, d3);
            } else {
                livingEntity.m_20334_(d2, -0.07, d3);
            }
        }
    }

    public VoxelShape m_5939_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return aabb;
    }

    public VoxelShape m_5940_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.m_83144_();
    }

    public VoxelShape m_7947_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.m_83144_();
    }

    public InteractionResult m_6227_(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        Block block;
        if (player.m_6144_()) {
            return InteractionResult.PASS;
        }
        ItemStack itemStack = player.m_21120_(interactionHand);
        if (StackUtil.isEmpty(itemStack)) {
            return InteractionResult.PASS;
        }
        Block block2 = blockState.m_60734_();
        if (block2 == Ic2Blocks.WOODEN_SCAFFOLD) {
            if (!stickInput.matches(itemStack) || StackUtil.getSize(itemStack) < 2) {
                return InteractionResult.PASS;
            }
            block = Ic2Blocks.REINFORCED_WOODEN_SCAFFOLD;
        } else if (block2 == Ic2Blocks.IRON_SCAFFOLD) {
            if (!StackUtil.checkItemEquality(itemStack, new ItemStack((ItemLike)Ic2Blocks.IRON_FENCE)) || StackUtil.getSize(itemStack) < 1) {
                return InteractionResult.PASS;
            }
            block = Ic2Blocks.REINFORCED_IRON_SCAFFOLD;
        } else {
            return InteractionResult.PASS;
        }
        if (!this.isPillar(level, blockPos)) {
            return InteractionResult.PASS;
        }
        if (block2 == Ic2Blocks.WOODEN_SCAFFOLD) {
            StackUtil.consumeOrError(player, interactionHand, StackUtil.recipeInput(stickInput), 2);
        } else {
            StackUtil.consumeOrError(player, interactionHand, StackUtil.sameStack(new ItemStack((ItemLike)Ic2Blocks.IRON_FENCE)), 1);
        }
        level.m_46597_(blockPos, block.m_49966_());
        return InteractionResult.SUCCESS;
    }

    public void m_6256_(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        InteractionHand interactionHand = InteractionHand.MAIN_HAND;
        ItemStack itemStack = player.m_21120_(interactionHand);
        if (StackUtil.isEmpty(itemStack)) {
            return;
        }
        if (StackUtil.checkItemEquality(itemStack, Item.m_41439_((Block)this))) {
            while (level.m_8055_(blockPos).m_60734_() == this) {
                blockPos = blockPos.m_7494_();
            }
            if (this.m_7898_(this.m_49966_(), (LevelReader)level, blockPos) && blockPos.m_123342_() < IC2.getWorldMaxHeight(level)) {
                boolean bl = player.m_150110_().f_35937_;
                ItemStack itemStack2 = bl ? StackUtil.copy(itemStack) : null;
                itemStack.m_41661_((UseOnContext)new BlockPlaceContext(player, interactionHand, itemStack, new BlockHitResult(new Vec3(0.5, 1.0, 0.5).m_82520_((double)blockPos.m_7495_().m_123341_(), (double)blockPos.m_7495_().m_123342_(), (double)blockPos.m_7495_().m_123343_()), Direction.UP, blockPos.m_7495_(), true)));
                if (!bl) {
                    StackUtil.clearEmpty(player, interactionHand);
                } else {
                    StackUtil.set(player, interactionHand, itemStack2);
                }
            }
        }
    }

    public boolean m_7898_(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return super.m_7898_(blockState, levelReader, blockPos) && this.hasSupport((BlockGetter)levelReader, blockPos, this);
    }

    public void m_6861_(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        this.checkSupport(level, blockPos);
    }

    public void m_213898_(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.m_188503_(8) == 0) {
            this.checkSupport((Level)serverLevel, blockPos);
        }
    }

    private boolean isPillar(Level level, BlockPos blockPos) {
        while (level.m_8055_(blockPos).m_60734_() == this) {
            blockPos = blockPos.m_7495_();
        }
        return level.m_8055_(blockPos).m_60659_((BlockGetter)level, blockPos, Direction.UP, SupportType.FULL);
    }

    private boolean hasSupport(BlockGetter blockGetter, BlockPos blockPos, ScaffoldBlock scaffoldBlock) {
        return this.calculateSupport((BlockGetter)blockGetter, (BlockPos)blockPos, (ScaffoldBlock)scaffoldBlock).get((Object)blockPos).strength >= 0;
    }

    private void checkSupport(Level level, BlockPos blockPos) {
        Block block = level.m_8055_(blockPos).m_60734_();
        if (!(block instanceof ScaffoldBlock)) {
            return;
        }
        Map<BlockPos, Support> map = this.calculateSupport((BlockGetter)level, blockPos, (ScaffoldBlock)block);
        boolean bl = false;
        for (Support support : map.values()) {
            if (support.strength >= 0) continue;
            level.m_7731_(support.pos, Blocks.f_50016_.m_49966_(), 2);
            Block.m_49950_((BlockState)support.block.m_49966_(), (Level)level, (BlockPos)support.pos);
            bl = true;
        }
        if (bl) {
            for (Support support : map.values()) {
                if (support.strength >= 0) continue;
                level.m_6289_(support.pos, (Block)this);
            }
        }
    }

    private Map<BlockPos, Support> calculateSupport(BlockGetter blockGetter, BlockPos blockPos, ScaffoldBlock scaffoldBlock) {
        HashMap<BlockPos, Support> hashMap = new HashMap<BlockPos, Support>();
        ArrayDeque<Support> arrayDeque = new ArrayDeque<Support>();
        HashSet<BlockPos> hashSet = new HashSet<BlockPos>();
        Support support = new Support(blockPos, scaffoldBlock, -1);
        hashMap.put(blockPos, support);
        arrayDeque.add(support);
        while ((support = (Support)arrayDeque.poll()) != null) {
            for (Direction direction : Util.ALL_DIRS) {
                BlockPos blockPos2 = support.pos.m_121945_(direction);
                if (hashMap.containsKey(blockPos2)) continue;
                BlockState object = blockGetter.m_8055_(blockPos2);
                Block block = object.m_60734_();
                if (block instanceof ScaffoldBlock) {
                    Support support2 = new Support(blockPos2, (ScaffoldBlock)block, -1);
                    hashMap.put(blockPos2, support2);
                    arrayDeque.add(support2);
                    continue;
                }
                if (!block.m_180643_(object, blockGetter, blockPos2)) continue;
                hashSet.add(blockPos2);
            }
        }
        for (BlockPos blockPos3 : hashSet) {
            BlockPos blockPos4 = blockPos3.m_7494_();
            int n = 0;
            while ((support = (Support)hashMap.get(blockPos4)) != null) {
                int n2;
                if (support.block.maxDistance >= n) {
                    n2 = support.block.maxDistance;
                    n = n2 - 1;
                } else {
                    n2 = n--;
                }
                if (support.strength < n2) {
                    support.strength = n2;
                    for (Direction direction : Util.HORIZONTAL_DIRS) {
                        BlockPos blockPos2 = blockPos4.m_121945_(direction);
                        Support support3 = (Support)hashMap.get(blockPos2);
                        if (support3 == null || support3.strength >= n2) continue;
                        support3.strength = n2 - 1;
                        arrayDeque.add(support3);
                    }
                }
                blockPos4 = blockPos4.m_7494_();
            }
        }
        while ((support = (Support)arrayDeque.poll()) != null) {
            for (Direction direction : supportedFacings) {
                BlockPos blockPos6 = support.pos.m_121945_(direction);
                Support support4 = (Support)hashMap.get(blockPos6);
                if (support4 == null || support4.strength >= support.strength) continue;
                support4.strength = support.strength - 1;
                if (support4.strength <= 0) continue;
                arrayDeque.add(support4);
            }
        }
        return hashMap;
    }

    private static class Support {
        final BlockPos pos;
        final ScaffoldBlock block;
        int strength;

        Support(BlockPos blockPos, ScaffoldBlock scaffoldBlock, int n) {
            this.pos = blockPos;
            this.block = scaffoldBlock;
            this.strength = n;
        }
    }
}

