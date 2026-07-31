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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StonecutterBlock
extends Block {
    private static final Component f_57065_ = Component.m_237115_("container.stonecutter");
    public static final DirectionProperty f_57063_ = HorizontalDirectionalBlock.f_54117_;
    protected static final VoxelShape f_57064_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    public StonecutterBlock(BlockBehaviour.Properties p_57068_) {
        super(p_57068_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57063_, Direction.NORTH));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_57070_) {
        return (BlockState)this.m_49966_().m_61124_(f_57063_, p_57070_.m_8125_().m_122424_());
    }

    @Override
    public InteractionResult m_6227_(BlockState p_57083_, Level p_57084_, BlockPos p_57085_, Player p_57086_, InteractionHand p_57087_, BlockHitResult p_57088_) {
        if (p_57084_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_57086_.m_5893_(p_57083_.m_60750_(p_57084_, p_57085_));
        p_57086_.m_36220_(Stats.f_12978_);
        return InteractionResult.CONSUME;
    }

    @Override
    @Nullable
    public MenuProvider m_7246_(BlockState p_57105_, Level p_57106_, BlockPos p_57107_) {
        return new SimpleMenuProvider((p_57074_, p_57075_, p_57076_) -> new StonecutterMenu(p_57074_, p_57075_, ContainerLevelAccess.m_39289_(p_57106_, p_57107_)), f_57065_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57100_, BlockGetter p_57101_, BlockPos p_57102_, CollisionContext p_57103_) {
        return f_57064_;
    }

    @Override
    public boolean m_7923_(BlockState p_57109_) {
        return true;
    }

    @Override
    public RenderShape m_7514_(BlockState p_57098_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState m_6843_(BlockState p_57093_, Rotation p_57094_) {
        return (BlockState)p_57093_.m_61124_(f_57063_, p_57094_.m_55954_(p_57093_.m_61143_(f_57063_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_57090_, Mirror p_57091_) {
        return p_57090_.m_60717_(p_57091_.m_54846_(p_57090_.m_61143_(f_57063_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57096_) {
        p_57096_.m_61104_(f_57063_);
    }

    @Override
    public boolean m_7357_(BlockState p_57078_, BlockGetter p_57079_, BlockPos p_57080_, PathComputationType p_57081_) {
        return false;
    }
}

