/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceBlock
extends CrossCollisionBlock {
    private final VoxelShape[] f_53300_;

    public FenceBlock(BlockBehaviour.Properties p_53302_) {
        super(2.0f, 2.0f, 16.0f, 16.0f, 24.0f, p_53302_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52309_, false)).m_61124_(f_52310_, false)).m_61124_(f_52311_, false)).m_61124_(f_52312_, false)).m_61124_(f_52313_, false));
        this.f_53300_ = this.m_52326_(2.0f, 1.0f, 16.0f, 6.0f, 15.0f);
    }

    @Override
    public VoxelShape m_7952_(BlockState p_53338_, BlockGetter p_53339_, BlockPos p_53340_) {
        return this.f_53300_[this.m_52363_(p_53338_)];
    }

    @Override
    public VoxelShape m_5909_(BlockState p_53311_, BlockGetter p_53312_, BlockPos p_53313_, CollisionContext p_53314_) {
        return this.m_5940_(p_53311_, p_53312_, p_53313_, p_53314_);
    }

    @Override
    public boolean m_7357_(BlockState p_53306_, BlockGetter p_53307_, BlockPos p_53308_, PathComputationType p_53309_) {
        return false;
    }

    public boolean m_53329_(BlockState p_53330_, boolean p_53331_, Direction p_53332_) {
        Block $$3 = p_53330_.m_60734_();
        boolean $$4 = this.m_153254_(p_53330_);
        boolean $$5 = $$3 instanceof FenceGateBlock && FenceGateBlock.m_53378_(p_53330_, p_53332_);
        return !FenceBlock.m_152463_(p_53330_) && p_53331_ || $$4 || $$5;
    }

    private boolean m_153254_(BlockState p_153255_) {
        return p_153255_.m_204336_(BlockTags.f_13039_) && p_153255_.m_204336_(BlockTags.f_13098_) == this.m_49966_().m_204336_(BlockTags.f_13098_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_53316_, Level p_53317_, BlockPos p_53318_, Player p_53319_, InteractionHand p_53320_, BlockHitResult p_53321_) {
        if (p_53317_.f_46443_) {
            ItemStack $$6 = p_53319_.m_21120_(p_53320_);
            if ($$6.m_150930_(Items.f_42655_)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return LeadItem.m_42829_(p_53319_, p_53317_, p_53318_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53304_) {
        Level $$1 = p_53304_.m_43725_();
        BlockPos $$2 = p_53304_.m_8083_();
        FluidState $$3 = p_53304_.m_43725_().m_6425_(p_53304_.m_8083_());
        BlockPos $$4 = $$2.m_122012_();
        BlockPos $$5 = $$2.m_122029_();
        BlockPos $$6 = $$2.m_122019_();
        BlockPos $$7 = $$2.m_122024_();
        BlockState $$8 = $$1.m_8055_($$4);
        BlockState $$9 = $$1.m_8055_($$5);
        BlockState $$10 = $$1.m_8055_($$6);
        BlockState $$11 = $$1.m_8055_($$7);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.m_5573_(p_53304_).m_61124_(f_52309_, this.m_53329_($$8, $$8.m_60783_($$1, $$4, Direction.SOUTH), Direction.SOUTH))).m_61124_(f_52310_, this.m_53329_($$9, $$9.m_60783_($$1, $$5, Direction.WEST), Direction.WEST))).m_61124_(f_52311_, this.m_53329_($$10, $$10.m_60783_($$1, $$6, Direction.NORTH), Direction.NORTH))).m_61124_(f_52312_, this.m_53329_($$11, $$11.m_60783_($$1, $$7, Direction.EAST), Direction.EAST))).m_61124_(f_52313_, $$3.m_76152_() == Fluids.f_76193_);
    }

    @Override
    public BlockState m_7417_(BlockState p_53323_, Direction p_53324_, BlockState p_53325_, LevelAccessor p_53326_, BlockPos p_53327_, BlockPos p_53328_) {
        if (p_53323_.m_61143_(f_52313_).booleanValue()) {
            p_53326_.m_186469_(p_53327_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_53326_));
        }
        if (p_53324_.m_122434_().m_122480_() == Direction.Plane.HORIZONTAL) {
            return (BlockState)p_53323_.m_61124_((Property)f_52314_.get(p_53324_), this.m_53329_(p_53325_, p_53325_.m_60783_(p_53326_, p_53328_, p_53324_.m_122424_()), p_53324_.m_122424_()));
        }
        return super.m_7417_(p_53323_, p_53324_, p_53325_, p_53326_, p_53327_, p_53328_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53334_) {
        p_53334_.m_61104_(f_52309_, f_52310_, f_52312_, f_52311_, f_52313_);
    }
}

