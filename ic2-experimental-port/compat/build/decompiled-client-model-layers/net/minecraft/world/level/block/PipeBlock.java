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
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PipeBlock
extends Block {
    private static final Direction[] f_55156_ = Direction.values();
    public static final BooleanProperty f_55148_ = BlockStateProperties.f_61368_;
    public static final BooleanProperty f_55149_ = BlockStateProperties.f_61369_;
    public static final BooleanProperty f_55150_ = BlockStateProperties.f_61370_;
    public static final BooleanProperty f_55151_ = BlockStateProperties.f_61371_;
    public static final BooleanProperty f_55152_ = BlockStateProperties.f_61366_;
    public static final BooleanProperty f_55153_ = BlockStateProperties.f_61367_;
    public static final Map<Direction, BooleanProperty> f_55154_ = ImmutableMap.copyOf((Map)Util.m_137469_(Maps.newEnumMap(Direction.class), p_55164_ -> {
        p_55164_.put(Direction.NORTH, f_55148_);
        p_55164_.put(Direction.EAST, f_55149_);
        p_55164_.put(Direction.SOUTH, f_55150_);
        p_55164_.put(Direction.WEST, f_55151_);
        p_55164_.put(Direction.UP, f_55152_);
        p_55164_.put(Direction.DOWN, f_55153_);
    }));
    protected final VoxelShape[] f_55155_;

    protected PipeBlock(float p_55159_, BlockBehaviour.Properties p_55160_) {
        super(p_55160_);
        this.f_55155_ = this.m_55161_(p_55159_);
    }

    private VoxelShape[] m_55161_(float p_55162_) {
        float $$1 = 0.5f - p_55162_;
        float $$2 = 0.5f + p_55162_;
        VoxelShape $$3 = Block.m_49796_($$1 * 16.0f, $$1 * 16.0f, $$1 * 16.0f, $$2 * 16.0f, $$2 * 16.0f, $$2 * 16.0f);
        VoxelShape[] $$4 = new VoxelShape[f_55156_.length];
        for (int $$5 = 0; $$5 < f_55156_.length; ++$$5) {
            Direction $$6 = f_55156_[$$5];
            $$4[$$5] = Shapes.m_83048_(0.5 + Math.min((double)(-p_55162_), (double)$$6.m_122429_() * 0.5), 0.5 + Math.min((double)(-p_55162_), (double)$$6.m_122430_() * 0.5), 0.5 + Math.min((double)(-p_55162_), (double)$$6.m_122431_() * 0.5), 0.5 + Math.max((double)p_55162_, (double)$$6.m_122429_() * 0.5), 0.5 + Math.max((double)p_55162_, (double)$$6.m_122430_() * 0.5), 0.5 + Math.max((double)p_55162_, (double)$$6.m_122431_() * 0.5));
        }
        VoxelShape[] $$7 = new VoxelShape[64];
        for (int $$8 = 0; $$8 < 64; ++$$8) {
            VoxelShape $$9 = $$3;
            for (int $$10 = 0; $$10 < f_55156_.length; ++$$10) {
                if (($$8 & 1 << $$10) == 0) continue;
                $$9 = Shapes.m_83110_($$9, $$4[$$10]);
            }
            $$7[$$8] = $$9;
        }
        return $$7;
    }

    @Override
    public boolean m_7420_(BlockState p_55166_, BlockGetter p_55167_, BlockPos p_55168_) {
        return false;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_55170_, BlockGetter p_55171_, BlockPos p_55172_, CollisionContext p_55173_) {
        return this.f_55155_[this.m_55174_(p_55170_)];
    }

    protected int m_55174_(BlockState p_55175_) {
        int $$1 = 0;
        for (int $$2 = 0; $$2 < f_55156_.length; ++$$2) {
            if (!((Boolean)p_55175_.m_61143_(f_55154_.get(f_55156_[$$2]))).booleanValue()) continue;
            $$1 |= 1 << $$2;
        }
        return $$1;
    }
}

