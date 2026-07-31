/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallSkullBlock
extends AbstractSkullBlock {
    public static final DirectionProperty f_58097_ = HorizontalDirectionalBlock.f_54117_;
    private static final Map<Direction, VoxelShape> f_58098_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Block.m_49796_(4.0, 4.0, 8.0, 12.0, 12.0, 16.0), (Object)Direction.SOUTH, (Object)Block.m_49796_(4.0, 4.0, 0.0, 12.0, 12.0, 8.0), (Object)Direction.EAST, (Object)Block.m_49796_(0.0, 4.0, 4.0, 8.0, 12.0, 12.0), (Object)Direction.WEST, (Object)Block.m_49796_(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)));

    protected WallSkullBlock(SkullBlock.Type p_58101_, BlockBehaviour.Properties p_58102_) {
        super(p_58101_, p_58102_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_58097_, Direction.NORTH));
    }

    @Override
    public String m_7705_() {
        return this.m_5456_().m_5524_();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_58114_, BlockGetter p_58115_, BlockPos p_58116_, CollisionContext p_58117_) {
        return f_58098_.get(p_58114_.m_61143_(f_58097_));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_58104_) {
        Direction[] $$4;
        BlockState $$1 = this.m_49966_();
        Level $$2 = p_58104_.m_43725_();
        BlockPos $$3 = p_58104_.m_8083_();
        for (Direction $$5 : $$4 = p_58104_.m_6232_()) {
            if (!$$5.m_122434_().m_122479_()) continue;
            Direction $$6 = $$5.m_122424_();
            $$1 = (BlockState)$$1.m_61124_(f_58097_, $$6);
            if ($$2.m_8055_($$3.m_121945_($$5)).m_60629_(p_58104_)) continue;
            return $$1;
        }
        return null;
    }

    @Override
    public BlockState m_6843_(BlockState p_58109_, Rotation p_58110_) {
        return (BlockState)p_58109_.m_61124_(f_58097_, p_58110_.m_55954_(p_58109_.m_61143_(f_58097_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_58106_, Mirror p_58107_) {
        return p_58106_.m_60717_(p_58107_.m_54846_(p_58106_.m_61143_(f_58097_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58112_) {
        p_58112_.m_61104_(f_58097_);
    }
}

