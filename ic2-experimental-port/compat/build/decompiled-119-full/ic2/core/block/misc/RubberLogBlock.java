/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.AxeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.BlockHitResult
 */
package ic2.core.block.misc;

import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import ic2.core.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

public class RubberLogBlock
extends RotatedPillarBlock {
    public static final EnumProperty<RubberWoodState> stateProperty = EnumProperty.m_61587_((String)"state", RubberWoodState.class);

    public RubberLogBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(stateProperty, (Comparable)((Object)RubberWoodState.plain)));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{stateProperty});
    }

    public BlockState m_5573_(BlockPlaceContext blockPlaceContext) {
        return (BlockState)((BlockState)this.m_49966_().m_61124_((Property)f_55923_, (Comparable)blockPlaceContext.m_43719_().m_122434_())).m_61124_(stateProperty, (Comparable)((Object)RubberWoodState.plain));
    }

    public void m_213898_(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.m_188503_(7) == 0) {
            RubberWoodState rubberWoodState = (RubberWoodState)((Object)blockState.m_61143_(stateProperty));
            if (!rubberWoodState.canRegenerate()) {
                return;
            }
            serverLevel.m_46597_(blockPos, (BlockState)blockState.m_61124_(stateProperty, (Comparable)((Object)rubberWoodState.getWet())));
        }
    }

    public PushReaction m_5537_(BlockState blockState) {
        Direction.Axis axis = (Direction.Axis)blockState.m_61143_((Property)f_55923_);
        if (axis == Direction.Axis.X || axis == Direction.Axis.Y || axis == Direction.Axis.Z) {
            return PushReaction.NORMAL;
        }
        return PushReaction.BLOCK;
    }

    public InteractionResult m_6227_(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.m_21205_();
        if (itemStack.m_41720_() instanceof AxeItem) {
            WorldUtil.strip(blockState, level, blockPos, player, itemStack, (BlockState)Ic2Blocks.STRIPPED_RUBBER_LOG.m_49966_().m_61124_((Property)RotatedPillarBlock.f_55923_, (Comparable)((Direction.Axis)blockState.m_61143_((Property)RotatedPillarBlock.f_55923_))));
            RubberWoodState rubberWoodState = (RubberWoodState)((Object)blockState.m_61143_(stateProperty));
            if (rubberWoodState == RubberWoodState.wet_north || rubberWoodState == RubberWoodState.wet_south || rubberWoodState == RubberWoodState.wet_west || rubberWoodState == RubberWoodState.wet_east) {
                this.dropResin(level, blockPos, level.f_46441_.m_188503_(2) + 1);
            }
            return InteractionResult.m_19078_((boolean)level.f_46443_);
        }
        return super.m_6227_(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    private void dropResin(Level level, BlockPos blockPos, int n) {
        for (int i = 0; i < n; ++i) {
            ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.m_123341_(), (double)blockPos.m_123342_(), (double)blockPos.m_123343_(), new ItemStack((ItemLike)Ic2Items.RESIN));
            itemEntity.m_32060_();
            level.m_7967_((Entity)itemEntity);
        }
    }

    public static enum RubberWoodState implements StringRepresentable
    {
        plain(null, false),
        dry_north(Direction.NORTH, false),
        dry_south(Direction.SOUTH, false),
        dry_west(Direction.WEST, false),
        dry_east(Direction.EAST, false),
        wet_north(Direction.NORTH, true),
        wet_south(Direction.SOUTH, true),
        wet_west(Direction.WEST, true),
        wet_east(Direction.EAST, true);

        public final Direction facing;
        public final boolean wet;
        private static final RubberWoodState[] values;

        private RubberWoodState(Direction direction, boolean bl) {
            this.facing = direction;
            this.wet = bl;
        }

        public String m_7912_() {
            return this.name();
        }

        public boolean isPlain() {
            return this.facing == null;
        }

        public boolean canRegenerate() {
            return !this.isPlain() && !this.wet;
        }

        public RubberWoodState getWet() {
            if (this.isPlain()) {
                return null;
            }
            if (this.wet) {
                return this;
            }
            return values[this.ordinal() + 4];
        }

        public RubberWoodState getDry() {
            if (this.isPlain() || !this.wet) {
                return this;
            }
            return values[this.ordinal() - 4];
        }

        public static RubberWoodState getWet(Direction direction) {
            switch (direction) {
                case NORTH: {
                    return wet_north;
                }
                case SOUTH: {
                    return wet_south;
                }
                case WEST: {
                    return wet_west;
                }
                case EAST: {
                    return wet_east;
                }
            }
            throw new IllegalArgumentException("incompatible facing: " + direction);
        }

        static {
            values = RubberWoodState.values();
        }
    }
}

