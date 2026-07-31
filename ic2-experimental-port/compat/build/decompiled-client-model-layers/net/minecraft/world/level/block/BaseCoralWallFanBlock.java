/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCoralWallFanBlock
extends BaseCoralFanBlock {
    public static final DirectionProperty f_49192_ = HorizontalDirectionalBlock.f_54117_;
    private static final Map<Direction, VoxelShape> f_49193_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Block.m_49796_(0.0, 4.0, 5.0, 16.0, 12.0, 16.0), (Object)Direction.SOUTH, (Object)Block.m_49796_(0.0, 4.0, 0.0, 16.0, 12.0, 11.0), (Object)Direction.WEST, (Object)Block.m_49796_(5.0, 4.0, 0.0, 16.0, 12.0, 16.0), (Object)Direction.EAST, (Object)Block.m_49796_(0.0, 4.0, 0.0, 11.0, 12.0, 16.0)));

    protected BaseCoralWallFanBlock(BlockBehaviour.Properties p_49196_) {
        super(p_49196_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_49192_, Direction.NORTH)).m_61124_(f_49158_, true));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49219_, BlockGetter p_49220_, BlockPos p_49221_, CollisionContext p_49222_) {
        return f_49193_.get(p_49219_.m_61143_(f_49192_));
    }

    @Override
    public BlockState m_6843_(BlockState p_49207_, Rotation p_49208_) {
        return (BlockState)p_49207_.m_61124_(f_49192_, p_49208_.m_55954_(p_49207_.m_61143_(f_49192_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_49204_, Mirror p_49205_) {
        return p_49204_.m_60717_(p_49205_.m_54846_(p_49204_.m_61143_(f_49192_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49217_) {
        p_49217_.m_61104_(f_49192_, f_49158_);
    }

    @Override
    public BlockState m_7417_(BlockState p_49210_, Direction p_49211_, BlockState p_49212_, LevelAccessor p_49213_, BlockPos p_49214_, BlockPos p_49215_) {
        if (p_49210_.m_61143_(f_49158_).booleanValue()) {
            p_49213_.m_186469_(p_49214_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_49213_));
        }
        if (p_49211_.m_122424_() == p_49210_.m_61143_(f_49192_) && !p_49210_.m_60710_(p_49213_, p_49214_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return p_49210_;
    }

    @Override
    public boolean m_7898_(BlockState p_49200_, LevelReader p_49201_, BlockPos p_49202_) {
        Direction $$3 = p_49200_.m_61143_(f_49192_);
        BlockPos $$4 = p_49202_.m_121945_($$3.m_122424_());
        BlockState $$5 = p_49201_.m_8055_($$4);
        return $$5.m_60783_(p_49201_, $$4, $$3);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_49198_) {
        Direction[] $$4;
        BlockState $$1 = super.m_5573_(p_49198_);
        Level $$2 = p_49198_.m_43725_();
        BlockPos $$3 = p_49198_.m_8083_();
        for (Direction $$5 : $$4 = p_49198_.m_6232_()) {
            if (!$$5.m_122434_().m_122479_() || !($$1 = (BlockState)$$1.m_61124_(f_49192_, $$5.m_122424_())).m_60710_($$2, $$3)) continue;
            return $$1;
        }
        return null;
    }
}

