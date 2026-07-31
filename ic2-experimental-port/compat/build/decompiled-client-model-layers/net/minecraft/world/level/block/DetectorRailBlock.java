/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;

public class DetectorRailBlock
extends BaseRailBlock {
    public static final EnumProperty<RailShape> f_52427_ = BlockStateProperties.f_61404_;
    public static final BooleanProperty f_52428_ = BlockStateProperties.f_61448_;
    private static final int f_153121_ = 20;

    public DetectorRailBlock(BlockBehaviour.Properties p_52431_) {
        super(true, p_52431_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52428_, false)).m_61124_(f_52427_, RailShape.NORTH_SOUTH)).m_61124_(f_152149_, false));
    }

    @Override
    public boolean m_7899_(BlockState p_52489_) {
        return true;
    }

    @Override
    public void m_7892_(BlockState p_52458_, Level p_52459_, BlockPos p_52460_, Entity p_52461_) {
        if (p_52459_.f_46443_) {
            return;
        }
        if (p_52458_.m_61143_(f_52428_).booleanValue()) {
            return;
        }
        this.m_52432_(p_52459_, p_52460_, p_52458_);
    }

    @Override
    public void m_213897_(BlockState p_221060_, ServerLevel p_221061_, BlockPos p_221062_, RandomSource p_221063_) {
        if (!p_221060_.m_61143_(f_52428_).booleanValue()) {
            return;
        }
        this.m_52432_(p_221061_, p_221062_, p_221060_);
    }

    @Override
    public int m_6378_(BlockState p_52449_, BlockGetter p_52450_, BlockPos p_52451_, Direction p_52452_) {
        return p_52449_.m_61143_(f_52428_) != false ? 15 : 0;
    }

    @Override
    public int m_6376_(BlockState p_52478_, BlockGetter p_52479_, BlockPos p_52480_, Direction p_52481_) {
        if (!p_52478_.m_61143_(f_52428_).booleanValue()) {
            return 0;
        }
        return p_52481_ == Direction.UP ? 15 : 0;
    }

    private void m_52432_(Level p_52433_, BlockPos p_52434_, BlockState p_52435_) {
        if (!this.m_7898_(p_52435_, p_52433_, p_52434_)) {
            return;
        }
        boolean $$3 = p_52435_.m_61143_(f_52428_);
        boolean $$4 = false;
        List<AbstractMinecart> $$5 = this.m_52436_(p_52433_, p_52434_, AbstractMinecart.class, p_153125_ -> true);
        if (!$$5.isEmpty()) {
            $$4 = true;
        }
        if ($$4 && !$$3) {
            BlockState $$6 = (BlockState)p_52435_.m_61124_(f_52428_, true);
            p_52433_.m_7731_(p_52434_, $$6, 3);
            this.m_52472_(p_52433_, p_52434_, $$6, true);
            p_52433_.m_46672_(p_52434_, this);
            p_52433_.m_46672_(p_52434_.m_7495_(), this);
            p_52433_.m_6550_(p_52434_, p_52435_, $$6);
        }
        if (!$$4 && $$3) {
            BlockState $$7 = (BlockState)p_52435_.m_61124_(f_52428_, false);
            p_52433_.m_7731_(p_52434_, $$7, 3);
            this.m_52472_(p_52433_, p_52434_, $$7, false);
            p_52433_.m_46672_(p_52434_, this);
            p_52433_.m_46672_(p_52434_.m_7495_(), this);
            p_52433_.m_6550_(p_52434_, p_52435_, $$7);
        }
        if ($$4) {
            p_52433_.m_186460_(p_52434_, this, 20);
        }
        p_52433_.m_46717_(p_52434_, this);
    }

    protected void m_52472_(Level p_52473_, BlockPos p_52474_, BlockState p_52475_, boolean p_52476_) {
        RailState $$4 = new RailState(p_52473_, p_52474_, p_52475_);
        List<BlockPos> $$5 = $$4.m_55424_();
        for (BlockPos $$6 : $$5) {
            BlockState $$7 = p_52473_.m_8055_($$6);
            p_52473_.m_213960_($$7, $$6, $$7.m_60734_(), p_52474_, false);
        }
    }

    @Override
    public void m_6807_(BlockState p_52483_, Level p_52484_, BlockPos p_52485_, BlockState p_52486_, boolean p_52487_) {
        if (p_52486_.m_60713_(p_52483_.m_60734_())) {
            return;
        }
        BlockState $$5 = this.m_49389_(p_52483_, p_52484_, p_52485_, p_52487_);
        this.m_52432_(p_52484_, p_52485_, $$5);
    }

    @Override
    public Property<RailShape> m_7978_() {
        return f_52427_;
    }

    @Override
    public boolean m_7278_(BlockState p_52442_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_52454_, Level p_52455_, BlockPos p_52456_) {
        if (p_52454_.m_61143_(f_52428_).booleanValue()) {
            List<MinecartCommandBlock> $$3 = this.m_52436_(p_52455_, p_52456_, MinecartCommandBlock.class, p_153123_ -> true);
            if (!$$3.isEmpty()) {
                return $$3.get(0).m_38534_().m_45436_();
            }
            List<AbstractMinecart> $$4 = this.m_52436_(p_52455_, p_52456_, AbstractMinecart.class, EntitySelector.f_20405_);
            if (!$$4.isEmpty()) {
                return AbstractContainerMenu.m_38938_((Container)((Object)$$4.get(0)));
            }
        }
        return 0;
    }

    private <T extends AbstractMinecart> List<T> m_52436_(Level p_52437_, BlockPos p_52438_, Class<T> p_52439_, Predicate<Entity> p_52440_) {
        return p_52437_.m_6443_(p_52439_, this.m_52470_(p_52438_), p_52440_);
    }

    private AABB m_52470_(BlockPos p_52471_) {
        double $$1 = 0.2;
        return new AABB((double)p_52471_.m_123341_() + 0.2, p_52471_.m_123342_(), (double)p_52471_.m_123343_() + 0.2, (double)(p_52471_.m_123341_() + 1) - 0.2, (double)(p_52471_.m_123342_() + 1) - 0.2, (double)(p_52471_.m_123343_() + 1) - 0.2);
    }

    @Override
    public BlockState m_6843_(BlockState p_52466_, Rotation p_52467_) {
        switch (p_52467_) {
            case CLOCKWISE_180: {
                switch (p_52466_.m_61143_(f_52427_)) {
                    case ASCENDING_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
                    }
                }
            }
            case COUNTERCLOCKWISE_90: {
                switch (p_52466_.m_61143_(f_52427_)) {
                    case NORTH_SOUTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_WEST);
                    }
                }
            }
            case CLOCKWISE_90: {
                switch (p_52466_.m_61143_(f_52427_)) {
                    case NORTH_SOUTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_52466_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
                    }
                }
            }
        }
        return p_52466_;
    }

    @Override
    public BlockState m_6943_(BlockState p_52463_, Mirror p_52464_) {
        RailShape $$2 = p_52463_.m_61143_(f_52427_);
        switch (p_52464_) {
            case LEFT_RIGHT: {
                switch ($$2) {
                    case ASCENDING_NORTH: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
                    }
                }
                break;
            }
            case FRONT_BACK: {
                switch ($$2) {
                    case ASCENDING_EAST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)p_52463_.m_61124_(f_52427_, RailShape.NORTH_WEST);
                    }
                }
                break;
            }
        }
        return super.m_6943_(p_52463_, p_52464_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52469_) {
        p_52469_.m_61104_(f_52427_, f_52428_, f_152149_);
    }
}

