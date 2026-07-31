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
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AttachedStemBlock
extends BushBlock {
    public static final DirectionProperty f_48830_ = HorizontalDirectionalBlock.f_54117_;
    protected static final float f_152057_ = 2.0f;
    private static final Map<Direction, VoxelShape> f_48832_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.SOUTH, (Object)Block.m_49796_(6.0, 0.0, 6.0, 10.0, 10.0, 16.0), (Object)Direction.WEST, (Object)Block.m_49796_(0.0, 0.0, 6.0, 10.0, 10.0, 10.0), (Object)Direction.NORTH, (Object)Block.m_49796_(6.0, 0.0, 0.0, 10.0, 10.0, 10.0), (Object)Direction.EAST, (Object)Block.m_49796_(6.0, 0.0, 6.0, 16.0, 10.0, 10.0)));
    private final StemGrownBlock f_48831_;
    private final Supplier<Item> f_152058_;

    protected AttachedStemBlock(StemGrownBlock p_152060_, Supplier<Item> p_152061_, BlockBehaviour.Properties p_152062_) {
        super(p_152062_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_48830_, Direction.NORTH));
        this.f_48831_ = p_152060_;
        this.f_152058_ = p_152061_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_48858_, BlockGetter p_48859_, BlockPos p_48860_, CollisionContext p_48861_) {
        return f_48832_.get(p_48858_.m_61143_(f_48830_));
    }

    @Override
    public BlockState m_7417_(BlockState p_48848_, Direction p_48849_, BlockState p_48850_, LevelAccessor p_48851_, BlockPos p_48852_, BlockPos p_48853_) {
        if (!p_48850_.m_60713_(this.f_48831_) && p_48849_ == p_48848_.m_61143_(f_48830_)) {
            return (BlockState)this.f_48831_.m_7161_().m_49966_().m_61124_(StemBlock.f_57013_, 7);
        }
        return super.m_7417_(p_48848_, p_48849_, p_48850_, p_48851_, p_48852_, p_48853_);
    }

    @Override
    protected boolean m_6266_(BlockState p_48863_, BlockGetter p_48864_, BlockPos p_48865_) {
        return p_48863_.m_60713_(Blocks.f_50093_);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_48838_, BlockPos p_48839_, BlockState p_48840_) {
        return new ItemStack(this.f_152058_.get());
    }

    @Override
    public BlockState m_6843_(BlockState p_48845_, Rotation p_48846_) {
        return (BlockState)p_48845_.m_61124_(f_48830_, p_48846_.m_55954_(p_48845_.m_61143_(f_48830_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_48842_, Mirror p_48843_) {
        return p_48842_.m_60717_(p_48843_.m_54846_(p_48842_.m_61143_(f_48830_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_48855_) {
        p_48855_.m_61104_(f_48830_);
    }
}

