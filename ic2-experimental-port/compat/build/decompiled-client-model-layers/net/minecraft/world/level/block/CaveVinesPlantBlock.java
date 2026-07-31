/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class CaveVinesPlantBlock
extends GrowingPlantBodyBlock
implements BonemealableBlock,
CaveVines {
    public CaveVinesPlantBlock(BlockBehaviour.Properties p_153000_) {
        super(p_153000_, Direction.DOWN, f_152948_, false);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_152949_, false));
    }

    @Override
    protected GrowingPlantHeadBlock m_7272_() {
        return (GrowingPlantHeadBlock)Blocks.f_152538_;
    }

    @Override
    protected BlockState m_142644_(BlockState p_153028_, BlockState p_153029_) {
        return (BlockState)p_153029_.m_61124_(f_152949_, p_153028_.m_61143_(f_152949_));
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_153007_, BlockPos p_153008_, BlockState p_153009_) {
        return new ItemStack(Items.f_151079_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_153021_, Level p_153022_, BlockPos p_153023_, Player p_153024_, InteractionHand p_153025_, BlockHitResult p_153026_) {
        return CaveVines.m_152953_(p_153021_, p_153022_, p_153023_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153031_) {
        p_153031_.m_61104_(f_152949_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_153011_, BlockPos p_153012_, BlockState p_153013_, boolean p_153014_) {
        return p_153013_.m_61143_(f_152949_) == false;
    }

    @Override
    public boolean m_214167_(Level p_220943_, RandomSource p_220944_, BlockPos p_220945_, BlockState p_220946_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_220938_, RandomSource p_220939_, BlockPos p_220940_, BlockState p_220941_) {
        p_220938_.m_7731_(p_220940_, (BlockState)p_220941_.m_61124_(f_152949_, true), 2);
    }
}

