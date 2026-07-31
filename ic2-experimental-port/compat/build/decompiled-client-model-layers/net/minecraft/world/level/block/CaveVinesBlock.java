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
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class CaveVinesBlock
extends GrowingPlantHeadBlock
implements BonemealableBlock,
CaveVines {
    private static final float f_152957_ = 0.11f;

    public CaveVinesBlock(BlockBehaviour.Properties p_152959_) {
        super(p_152959_, Direction.DOWN, f_152948_, false, 0.1);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53924_, 0)).m_61124_(f_152949_, false));
    }

    @Override
    protected int m_213627_(RandomSource p_220928_) {
        return 1;
    }

    @Override
    protected boolean m_5971_(BlockState p_152998_) {
        return p_152998_.m_60795_();
    }

    @Override
    protected Block m_7777_() {
        return Blocks.f_152539_;
    }

    @Override
    protected BlockState m_142643_(BlockState p_152987_, BlockState p_152988_) {
        return (BlockState)p_152988_.m_61124_(f_152949_, p_152987_.m_61143_(f_152949_));
    }

    @Override
    protected BlockState m_214070_(BlockState p_220935_, RandomSource p_220936_) {
        return (BlockState)super.m_214070_(p_220935_, p_220936_).m_61124_(f_152949_, p_220936_.m_188501_() < 0.11f);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_152966_, BlockPos p_152967_, BlockState p_152968_) {
        return new ItemStack(Items.f_151079_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_152980_, Level p_152981_, BlockPos p_152982_, Player p_152983_, InteractionHand p_152984_, BlockHitResult p_152985_) {
        return CaveVines.m_152953_(p_152980_, p_152981_, p_152982_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152993_) {
        super.m_7926_(p_152993_);
        p_152993_.m_61104_(f_152949_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_152970_, BlockPos p_152971_, BlockState p_152972_, boolean p_152973_) {
        return p_152972_.m_61143_(f_152949_) == false;
    }

    @Override
    public boolean m_214167_(Level p_220930_, RandomSource p_220931_, BlockPos p_220932_, BlockState p_220933_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_220923_, RandomSource p_220924_, BlockPos p_220925_, BlockState p_220926_) {
        p_220923_.m_7731_(p_220925_, (BlockState)p_220926_.m_61124_(f_152949_, true), 2);
    }
}

