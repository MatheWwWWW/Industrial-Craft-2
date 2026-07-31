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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallBannerBlock
extends AbstractBannerBlock {
    public static final DirectionProperty f_57916_ = HorizontalDirectionalBlock.f_54117_;
    private static final Map<Direction, VoxelShape> f_57917_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Block.m_49796_(0.0, 0.0, 14.0, 16.0, 12.5, 16.0), (Object)Direction.SOUTH, (Object)Block.m_49796_(0.0, 0.0, 0.0, 16.0, 12.5, 2.0), (Object)Direction.WEST, (Object)Block.m_49796_(14.0, 0.0, 0.0, 16.0, 12.5, 16.0), (Object)Direction.EAST, (Object)Block.m_49796_(0.0, 0.0, 0.0, 2.0, 12.5, 16.0)));

    public WallBannerBlock(DyeColor p_57920_, BlockBehaviour.Properties p_57921_) {
        super(p_57920_, p_57921_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57916_, Direction.NORTH));
    }

    @Override
    public String m_7705_() {
        return this.m_5456_().m_5524_();
    }

    @Override
    public boolean m_7898_(BlockState p_57925_, LevelReader p_57926_, BlockPos p_57927_) {
        return p_57926_.m_8055_(p_57927_.m_121945_(p_57925_.m_61143_(f_57916_).m_122424_())).m_60767_().m_76333_();
    }

    @Override
    public BlockState m_7417_(BlockState p_57935_, Direction p_57936_, BlockState p_57937_, LevelAccessor p_57938_, BlockPos p_57939_, BlockPos p_57940_) {
        if (p_57936_ == p_57935_.m_61143_(f_57916_).m_122424_() && !p_57935_.m_60710_(p_57938_, p_57939_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_57935_, p_57936_, p_57937_, p_57938_, p_57939_, p_57940_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57944_, BlockGetter p_57945_, BlockPos p_57946_, CollisionContext p_57947_) {
        return f_57917_.get(p_57944_.m_61143_(f_57916_));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_57923_) {
        Direction[] $$4;
        BlockState $$1 = this.m_49966_();
        Level $$2 = p_57923_.m_43725_();
        BlockPos $$3 = p_57923_.m_8083_();
        for (Direction $$5 : $$4 = p_57923_.m_6232_()) {
            Direction $$6;
            if (!$$5.m_122434_().m_122479_() || !($$1 = (BlockState)$$1.m_61124_(f_57916_, $$6 = $$5.m_122424_())).m_60710_($$2, $$3)) continue;
            return $$1;
        }
        return null;
    }

    @Override
    public BlockState m_6843_(BlockState p_57932_, Rotation p_57933_) {
        return (BlockState)p_57932_.m_61124_(f_57916_, p_57933_.m_55954_(p_57932_.m_61143_(f_57916_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_57929_, Mirror p_57930_) {
        return p_57929_.m_60717_(p_57930_.m_54846_(p_57929_.m_61143_(f_57916_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57942_) {
        p_57942_.m_61104_(f_57916_);
    }
}

