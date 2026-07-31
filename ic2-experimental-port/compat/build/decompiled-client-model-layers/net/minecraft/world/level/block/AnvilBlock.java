/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AnvilBlock
extends FallingBlock {
    public static final DirectionProperty f_48764_ = HorizontalDirectionalBlock.f_54117_;
    private static final VoxelShape f_48765_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
    private static final VoxelShape f_48766_ = Block.m_49796_(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
    private static final VoxelShape f_48767_ = Block.m_49796_(4.0, 5.0, 6.0, 12.0, 10.0, 10.0);
    private static final VoxelShape f_48768_ = Block.m_49796_(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape f_48769_ = Block.m_49796_(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
    private static final VoxelShape f_48770_ = Block.m_49796_(6.0, 5.0, 4.0, 10.0, 10.0, 12.0);
    private static final VoxelShape f_48771_ = Block.m_49796_(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
    private static final VoxelShape f_48772_ = Shapes.m_83124_(f_48765_, f_48766_, f_48767_, f_48768_);
    private static final VoxelShape f_48773_ = Shapes.m_83124_(f_48765_, f_48769_, f_48770_, f_48771_);
    private static final Component f_48774_ = Component.m_237115_("container.repair");
    private static final float f_152050_ = 2.0f;
    private static final int f_152051_ = 40;

    public AnvilBlock(BlockBehaviour.Properties p_48777_) {
        super(p_48777_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_48764_, Direction.NORTH));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_48781_) {
        return (BlockState)this.m_49966_().m_61124_(f_48764_, p_48781_.m_8125_().m_122427_());
    }

    @Override
    public InteractionResult m_6227_(BlockState p_48804_, Level p_48805_, BlockPos p_48806_, Player p_48807_, InteractionHand p_48808_, BlockHitResult p_48809_) {
        if (p_48805_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_48807_.m_5893_(p_48804_.m_60750_(p_48805_, p_48806_));
        p_48807_.m_36220_(Stats.f_12951_);
        return InteractionResult.CONSUME;
    }

    @Override
    @Nullable
    public MenuProvider m_7246_(BlockState p_48821_, Level p_48822_, BlockPos p_48823_) {
        return new SimpleMenuProvider((p_48785_, p_48786_, p_48787_) -> new AnvilMenu(p_48785_, p_48786_, ContainerLevelAccess.m_39289_(p_48822_, p_48823_)), f_48774_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
        Direction $$4 = p_48816_.m_61143_(f_48764_);
        if ($$4.m_122434_() == Direction.Axis.X) {
            return f_48772_;
        }
        return f_48773_;
    }

    @Override
    protected void m_6788_(FallingBlockEntity p_48779_) {
        p_48779_.m_149656_(2.0f, 40);
    }

    @Override
    public void m_48792_(Level p_48793_, BlockPos p_48794_, BlockState p_48795_, BlockState p_48796_, FallingBlockEntity p_48797_) {
        if (!p_48797_.m_20067_()) {
            p_48793_.m_46796_(1031, p_48794_, 0);
        }
    }

    @Override
    public void m_142525_(Level p_152053_, BlockPos p_152054_, FallingBlockEntity p_152055_) {
        if (!p_152055_.m_20067_()) {
            p_152053_.m_46796_(1029, p_152054_, 0);
        }
    }

    @Override
    public DamageSource m_142088_() {
        return DamageSource.f_19321_;
    }

    @Nullable
    public static BlockState m_48824_(BlockState p_48825_) {
        if (p_48825_.m_60713_(Blocks.f_50322_)) {
            return (BlockState)Blocks.f_50323_.m_49966_().m_61124_(f_48764_, p_48825_.m_61143_(f_48764_));
        }
        if (p_48825_.m_60713_(Blocks.f_50323_)) {
            return (BlockState)Blocks.f_50324_.m_49966_().m_61124_(f_48764_, p_48825_.m_61143_(f_48764_));
        }
        return null;
    }

    @Override
    public BlockState m_6843_(BlockState p_48811_, Rotation p_48812_) {
        return (BlockState)p_48811_.m_61124_(f_48764_, p_48812_.m_55954_(p_48811_.m_61143_(f_48764_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_48814_) {
        p_48814_.m_61104_(f_48764_);
    }

    @Override
    public boolean m_7357_(BlockState p_48799_, BlockGetter p_48800_, BlockPos p_48801_, PathComputationType p_48802_) {
        return false;
    }

    @Override
    public int m_6248_(BlockState p_48827_, BlockGetter p_48828_, BlockPos p_48829_) {
        return p_48827_.m_60780_((BlockGetter)p_48828_, (BlockPos)p_48829_).f_76396_;
    }
}

