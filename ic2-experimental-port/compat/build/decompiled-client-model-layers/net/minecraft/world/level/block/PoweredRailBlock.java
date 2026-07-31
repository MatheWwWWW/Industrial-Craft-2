/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;

public class PoweredRailBlock
extends BaseRailBlock {
    public static final EnumProperty<RailShape> f_55214_ = BlockStateProperties.f_61404_;
    public static final BooleanProperty f_55215_ = BlockStateProperties.f_61448_;

    protected PoweredRailBlock(BlockBehaviour.Properties p_55218_) {
        super(true, p_55218_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55214_, RailShape.NORTH_SOUTH)).m_61124_(f_55215_, false)).m_61124_(f_152149_, false));
    }

    protected boolean m_55219_(Level p_55220_, BlockPos p_55221_, BlockState p_55222_, boolean p_55223_, int p_55224_) {
        if (p_55224_ >= 8) {
            return false;
        }
        int $$5 = p_55221_.m_123341_();
        int $$6 = p_55221_.m_123342_();
        int $$7 = p_55221_.m_123343_();
        boolean $$8 = true;
        RailShape $$9 = p_55222_.m_61143_(f_55214_);
        switch ($$9) {
            case NORTH_SOUTH: {
                if (p_55223_) {
                    ++$$7;
                    break;
                }
                --$$7;
                break;
            }
            case EAST_WEST: {
                if (p_55223_) {
                    --$$5;
                    break;
                }
                ++$$5;
                break;
            }
            case ASCENDING_EAST: {
                if (p_55223_) {
                    --$$5;
                } else {
                    ++$$5;
                    ++$$6;
                    $$8 = false;
                }
                $$9 = RailShape.EAST_WEST;
                break;
            }
            case ASCENDING_WEST: {
                if (p_55223_) {
                    --$$5;
                    ++$$6;
                    $$8 = false;
                } else {
                    ++$$5;
                }
                $$9 = RailShape.EAST_WEST;
                break;
            }
            case ASCENDING_NORTH: {
                if (p_55223_) {
                    ++$$7;
                } else {
                    --$$7;
                    ++$$6;
                    $$8 = false;
                }
                $$9 = RailShape.NORTH_SOUTH;
                break;
            }
            case ASCENDING_SOUTH: {
                if (p_55223_) {
                    ++$$7;
                    ++$$6;
                    $$8 = false;
                } else {
                    --$$7;
                }
                $$9 = RailShape.NORTH_SOUTH;
            }
        }
        if (this.m_55225_(p_55220_, new BlockPos($$5, $$6, $$7), p_55223_, p_55224_, $$9)) {
            return true;
        }
        return $$8 && this.m_55225_(p_55220_, new BlockPos($$5, $$6 - 1, $$7), p_55223_, p_55224_, $$9);
    }

    protected boolean m_55225_(Level p_55226_, BlockPos p_55227_, boolean p_55228_, int p_55229_, RailShape p_55230_) {
        BlockState $$5 = p_55226_.m_8055_(p_55227_);
        if (!$$5.m_60713_(this)) {
            return false;
        }
        RailShape $$6 = $$5.m_61143_(f_55214_);
        if (p_55230_ == RailShape.EAST_WEST && ($$6 == RailShape.NORTH_SOUTH || $$6 == RailShape.ASCENDING_NORTH || $$6 == RailShape.ASCENDING_SOUTH)) {
            return false;
        }
        if (p_55230_ == RailShape.NORTH_SOUTH && ($$6 == RailShape.EAST_WEST || $$6 == RailShape.ASCENDING_EAST || $$6 == RailShape.ASCENDING_WEST)) {
            return false;
        }
        if ($$5.m_61143_(f_55215_).booleanValue()) {
            if (p_55226_.m_46753_(p_55227_)) {
                return true;
            }
            return this.m_55219_(p_55226_, p_55227_, $$5, p_55228_, p_55229_ + 1);
        }
        return false;
    }

    @Override
    protected void m_6360_(BlockState p_55232_, Level p_55233_, BlockPos p_55234_, Block p_55235_) {
        boolean $$5;
        boolean $$4 = p_55232_.m_61143_(f_55215_);
        boolean bl = $$5 = p_55233_.m_46753_(p_55234_) || this.m_55219_(p_55233_, p_55234_, p_55232_, true, 0) || this.m_55219_(p_55233_, p_55234_, p_55232_, false, 0);
        if ($$5 != $$4) {
            p_55233_.m_7731_(p_55234_, (BlockState)p_55232_.m_61124_(f_55215_, $$5), 3);
            p_55233_.m_46672_(p_55234_.m_7495_(), this);
            if (p_55232_.m_61143_(f_55214_).m_61745_()) {
                p_55233_.m_46672_(p_55234_.m_7494_(), this);
            }
        }
    }

    @Override
    public Property<RailShape> m_7978_() {
        return f_55214_;
    }

    @Override
    public BlockState m_6843_(BlockState p_55240_, Rotation p_55241_) {
        switch (p_55241_) {
            case CLOCKWISE_180: {
                switch (p_55240_.m_61143_(f_55214_)) {
                    case ASCENDING_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
                    }
                }
            }
            case COUNTERCLOCKWISE_90: {
                switch (p_55240_.m_61143_(f_55214_)) {
                    case NORTH_SOUTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_WEST);
                    }
                }
            }
            case CLOCKWISE_90: {
                switch (p_55240_.m_61143_(f_55214_)) {
                    case NORTH_SOUTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55240_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
                    }
                }
            }
        }
        return p_55240_;
    }

    @Override
    public BlockState m_6943_(BlockState p_55237_, Mirror p_55238_) {
        RailShape $$2 = p_55237_.m_61143_(f_55214_);
        switch (p_55238_) {
            case LEFT_RIGHT: {
                switch ($$2) {
                    case ASCENDING_NORTH: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
                    }
                }
                break;
            }
            case FRONT_BACK: {
                switch ($$2) {
                    case ASCENDING_EAST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_55237_.m_61124_(f_55214_, RailShape.NORTH_WEST);
                    }
                }
                break;
            }
        }
        return super.m_6943_(p_55237_, p_55238_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55243_) {
        p_55243_.m_61104_(f_55214_, f_55215_, f_152149_);
    }
}

