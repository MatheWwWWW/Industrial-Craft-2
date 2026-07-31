/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RailState;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;

public class RailBlock
extends BaseRailBlock {
    public static final EnumProperty<RailShape> f_55392_ = BlockStateProperties.f_61403_;

    protected RailBlock(BlockBehaviour.Properties p_55395_) {
        super(false, p_55395_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55392_, RailShape.NORTH_SOUTH)).m_61124_(f_152149_, false));
    }

    @Override
    protected void m_6360_(BlockState p_55397_, Level p_55398_, BlockPos p_55399_, Block p_55400_) {
        if (p_55400_.m_49966_().m_60803_() && new RailState(p_55398_, p_55399_, p_55397_).m_55435_() == 3) {
            this.m_49367_(p_55398_, p_55399_, p_55397_, false);
        }
    }

    @Override
    public Property<RailShape> m_7978_() {
        return f_55392_;
    }

    @Override
    public BlockState m_6843_(BlockState p_55405_, Rotation p_55406_) {
        switch (p_55406_) {
            case CLOCKWISE_180: {
                switch (p_55405_.m_61143_(f_55392_)) {
                    case ASCENDING_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.SOUTH_WEST);
                    }
                }
            }
            case COUNTERCLOCKWISE_90: {
                switch (p_55405_.m_61143_(f_55392_)) {
                    case NORTH_SOUTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_WEST);
                    }
                }
            }
            case CLOCKWISE_90: {
                switch (p_55405_.m_61143_(f_55392_)) {
                    case NORTH_SOUTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.ASCENDING_WEST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55405_.m_61124_(f_55392_, RailShape.SOUTH_EAST);
                    }
                }
            }
        }
        return p_55405_;
    }

    @Override
    public BlockState m_6943_(BlockState p_55402_, Mirror p_55403_) {
        RailShape $$2 = p_55402_.m_61143_(f_55392_);
        switch (p_55403_) {
            case LEFT_RIGHT: {
                switch ($$2) {
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.SOUTH_EAST);
                    }
                }
                break;
            }
            case FRONT_BACK: {
                switch ($$2) {
                    case ASCENDING_EAST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55402_.m_61124_(f_55392_, RailShape.NORTH_WEST);
                    }
                }
                break;
            }
        }
        return super.m_6943_(p_55402_, p_55403_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55408_) {
        p_55408_.m_61104_(f_55392_, f_152149_);
    }
}

