/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.misc;

import ic2.api.tile.RetexturableBlock;
import ic2.api.tile.StainableBlock;
import ic2.core.block.tileentity.TileEntityWall;
import ic2.core.ref.Ic2Blocks;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WallBlock
extends Block
implements StainableBlock,
RetexturableBlock {
    public static final DyeColor DEFAULT_COLOR = DyeColor.LIGHT_GRAY;
    private static final Map<DyeColor, WallBlock> types = new EnumMap<DyeColor, WallBlock>(DyeColor.class);
    final DyeColor color;

    public WallBlock(BlockBehaviour.Properties properties, DyeColor dyeColor) {
        super(properties);
        this.color = dyeColor;
        types.put(dyeColor, this);
    }

    @Override
    public DyeColor getColor(Level level, BlockPos blockPos, Direction direction) {
        return this.color;
    }

    @Override
    public boolean setColor(Level level, BlockPos blockPos, Direction direction, DyeColor dyeColor) {
        WallBlock wallBlock;
        if (dyeColor != this.color && (wallBlock = WallBlock.get(dyeColor)) != null) {
            level.m_46597_(blockPos, wallBlock.m_49966_());
            return true;
        }
        return false;
    }

    @Override
    public boolean retexture(BlockState blockState, Level level, BlockPos blockPos, Direction direction, Player player, BlockState blockState2, String string, Direction direction2, int[] nArray) {
        level.m_46597_(blockPos, Ic2Blocks.OBSCURED_WALL.m_49966_());
        BlockEntity blockEntity = level.m_7702_(blockPos);
        if (blockEntity instanceof TileEntityWall) {
            TileEntityWall tileEntityWall = (TileEntityWall)blockEntity;
            tileEntityWall.setColor(this.color);
            if (tileEntityWall.getBlockType().retexture(tileEntityWall.m_58900_(), level, blockPos, direction, player, blockState2, string, direction2, nArray)) {
                return true;
            }
        }
        level.m_46597_(blockPos, blockState);
        return false;
    }

    public static WallBlock get(DyeColor dyeColor) {
        return types.get(dyeColor);
    }
}

