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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallSignBlock
extends SignBlock {
    public static final DirectionProperty f_58064_ = HorizontalDirectionalBlock.f_54117_;
    protected static final float f_154882_ = 2.0f;
    protected static final float f_154883_ = 4.5f;
    protected static final float f_154884_ = 12.5f;
    private static final Map<Direction, VoxelShape> f_58065_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Block.m_49796_(0.0, 4.5, 14.0, 16.0, 12.5, 16.0), (Object)Direction.SOUTH, (Object)Block.m_49796_(0.0, 4.5, 0.0, 16.0, 12.5, 2.0), (Object)Direction.EAST, (Object)Block.m_49796_(0.0, 4.5, 0.0, 2.0, 12.5, 16.0), (Object)Direction.WEST, (Object)Block.m_49796_(14.0, 4.5, 0.0, 16.0, 12.5, 16.0)));

    public WallSignBlock(BlockBehaviour.Properties p_58068_, WoodType p_58069_) {
        super(p_58068_, p_58069_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_58064_, Direction.NORTH)).m_61124_(f_56268_, false));
    }

    @Override
    public String m_7705_() {
        return this.m_5456_().m_5524_();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_58092_, BlockGetter p_58093_, BlockPos p_58094_, CollisionContext p_58095_) {
        return f_58065_.get(p_58092_.m_61143_(f_58064_));
    }

    @Override
    public boolean m_7898_(BlockState p_58073_, LevelReader p_58074_, BlockPos p_58075_) {
        return p_58074_.m_8055_(p_58075_.m_121945_(p_58073_.m_61143_(f_58064_).m_122424_())).m_60767_().m_76333_();
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_58071_) {
        Direction[] $$5;
        BlockState $$1 = this.m_49966_();
        FluidState $$2 = p_58071_.m_43725_().m_6425_(p_58071_.m_8083_());
        Level $$3 = p_58071_.m_43725_();
        BlockPos $$4 = p_58071_.m_8083_();
        for (Direction $$6 : $$5 = p_58071_.m_6232_()) {
            Direction $$7;
            if (!$$6.m_122434_().m_122479_() || !($$1 = (BlockState)$$1.m_61124_(f_58064_, $$7 = $$6.m_122424_())).m_60710_($$3, $$4)) continue;
            return (BlockState)$$1.m_61124_(f_56268_, $$2.m_76152_() == Fluids.f_76193_);
        }
        return null;
    }

    @Override
    public BlockState m_7417_(BlockState p_58083_, Direction p_58084_, BlockState p_58085_, LevelAccessor p_58086_, BlockPos p_58087_, BlockPos p_58088_) {
        if (p_58084_.m_122424_() == p_58083_.m_61143_(f_58064_) && !p_58083_.m_60710_(p_58086_, p_58087_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_58083_, p_58084_, p_58085_, p_58086_, p_58087_, p_58088_);
    }

    @Override
    public BlockState m_6843_(BlockState p_58080_, Rotation p_58081_) {
        return (BlockState)p_58080_.m_61124_(f_58064_, p_58081_.m_55954_(p_58080_.m_61143_(f_58064_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_58077_, Mirror p_58078_) {
        return p_58077_.m_60717_(p_58078_.m_54846_(p_58077_.m_61143_(f_58064_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58090_) {
        p_58090_.m_61104_(f_58064_, f_56268_);
    }
}

