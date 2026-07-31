/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IronBarsBlock
extends CrossCollisionBlock {
    protected IronBarsBlock(BlockBehaviour.Properties p_54198_) {
        super(1.0f, 1.0f, 16.0f, 16.0f, 16.0f, p_54198_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52309_, false)).m_61124_(f_52310_, false)).m_61124_(f_52311_, false)).m_61124_(f_52312_, false)).m_61124_(f_52313_, false));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_54200_) {
        Level $$1 = p_54200_.m_43725_();
        BlockPos $$2 = p_54200_.m_8083_();
        FluidState $$3 = p_54200_.m_43725_().m_6425_(p_54200_.m_8083_());
        BlockPos $$4 = $$2.m_122012_();
        BlockPos $$5 = $$2.m_122019_();
        BlockPos $$6 = $$2.m_122024_();
        BlockPos $$7 = $$2.m_122029_();
        BlockState $$8 = $$1.m_8055_($$4);
        BlockState $$9 = $$1.m_8055_($$5);
        BlockState $$10 = $$1.m_8055_($$6);
        BlockState $$11 = $$1.m_8055_($$7);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_52309_, this.m_54217_($$8, $$8.m_60783_($$1, $$4, Direction.SOUTH)))).m_61124_(f_52311_, this.m_54217_($$9, $$9.m_60783_($$1, $$5, Direction.NORTH)))).m_61124_(f_52312_, this.m_54217_($$10, $$10.m_60783_($$1, $$6, Direction.EAST)))).m_61124_(f_52310_, this.m_54217_($$11, $$11.m_60783_($$1, $$7, Direction.WEST)))).m_61124_(f_52313_, $$3.m_76152_() == Fluids.f_76193_);
    }

    @Override
    public BlockState m_7417_(BlockState p_54211_, Direction p_54212_, BlockState p_54213_, LevelAccessor p_54214_, BlockPos p_54215_, BlockPos p_54216_) {
        if (p_54211_.m_61143_(f_52313_).booleanValue()) {
            p_54214_.m_186469_(p_54215_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_54214_));
        }
        if (p_54212_.m_122434_().m_122479_()) {
            return (BlockState)p_54211_.m_61124_((Property)f_52314_.get(p_54212_), this.m_54217_(p_54213_, p_54213_.m_60783_(p_54214_, p_54216_, p_54212_.m_122424_())));
        }
        return super.m_7417_(p_54211_, p_54212_, p_54213_, p_54214_, p_54215_, p_54216_);
    }

    @Override
    public VoxelShape m_5909_(BlockState p_54202_, BlockGetter p_54203_, BlockPos p_54204_, CollisionContext p_54205_) {
        return Shapes.m_83040_();
    }

    @Override
    public boolean m_6104_(BlockState p_54207_, BlockState p_54208_, Direction p_54209_) {
        if (p_54208_.m_60713_(this)) {
            if (!p_54209_.m_122434_().m_122479_()) {
                return true;
            }
            if (((Boolean)p_54207_.m_61143_((Property)f_52314_.get(p_54209_))).booleanValue() && ((Boolean)p_54208_.m_61143_((Property)f_52314_.get(p_54209_.m_122424_()))).booleanValue()) {
                return true;
            }
        }
        return super.m_6104_(p_54207_, p_54208_, p_54209_);
    }

    public final boolean m_54217_(BlockState p_54218_, boolean p_54219_) {
        return !IronBarsBlock.m_152463_(p_54218_) && p_54219_ || p_54218_.m_60734_() instanceof IronBarsBlock || p_54218_.m_204336_(BlockTags.f_13032_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54221_) {
        p_54221_.m_61104_(f_52309_, f_52310_, f_52312_, f_52311_, f_52313_);
    }
}

