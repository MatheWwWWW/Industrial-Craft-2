/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RepeaterBlock
extends DiodeBlock {
    public static final BooleanProperty f_55797_ = BlockStateProperties.f_61444_;
    public static final IntegerProperty f_55798_ = BlockStateProperties.f_61413_;

    protected RepeaterBlock(BlockBehaviour.Properties p_55801_) {
        super(p_55801_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_55798_, 1)).m_61124_(f_55797_, false)).m_61124_(f_52496_, false));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_55809_, Level p_55810_, BlockPos p_55811_, Player p_55812_, InteractionHand p_55813_, BlockHitResult p_55814_) {
        if (!p_55812_.m_150110_().f_35938_) {
            return InteractionResult.PASS;
        }
        p_55810_.m_7731_(p_55811_, (BlockState)p_55809_.m_61122_(f_55798_), 3);
        return InteractionResult.m_19078_(p_55810_.f_46443_);
    }

    @Override
    protected int m_6112_(BlockState p_55830_) {
        return p_55830_.m_61143_(f_55798_) * 2;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_55803_) {
        BlockState $$1 = super.m_5573_(p_55803_);
        return (BlockState)$$1.m_61124_(f_55797_, this.m_7346_(p_55803_.m_43725_(), p_55803_.m_8083_(), $$1));
    }

    @Override
    public BlockState m_7417_(BlockState p_55821_, Direction p_55822_, BlockState p_55823_, LevelAccessor p_55824_, BlockPos p_55825_, BlockPos p_55826_) {
        if (!p_55824_.m_5776_() && p_55822_.m_122434_() != p_55821_.m_61143_(f_54117_).m_122434_()) {
            return (BlockState)p_55821_.m_61124_(f_55797_, this.m_7346_(p_55824_, p_55825_, p_55821_));
        }
        return super.m_7417_(p_55821_, p_55822_, p_55823_, p_55824_, p_55825_, p_55826_);
    }

    @Override
    public boolean m_7346_(LevelReader p_55805_, BlockPos p_55806_, BlockState p_55807_) {
        return this.m_52547_(p_55805_, p_55806_, p_55807_) > 0;
    }

    @Override
    protected boolean m_6137_(BlockState p_55832_) {
        return RepeaterBlock.m_52586_(p_55832_);
    }

    @Override
    public void m_214162_(BlockState p_221964_, Level p_221965_, BlockPos p_221966_, RandomSource p_221967_) {
        if (!p_221964_.m_61143_(f_52496_).booleanValue()) {
            return;
        }
        Direction $$4 = p_221964_.m_61143_(f_54117_);
        double $$5 = (double)p_221966_.m_123341_() + 0.5 + (p_221967_.m_188500_() - 0.5) * 0.2;
        double $$6 = (double)p_221966_.m_123342_() + 0.4 + (p_221967_.m_188500_() - 0.5) * 0.2;
        double $$7 = (double)p_221966_.m_123343_() + 0.5 + (p_221967_.m_188500_() - 0.5) * 0.2;
        float $$8 = -5.0f;
        if (p_221967_.m_188499_()) {
            $$8 = p_221964_.m_61143_(f_55798_) * 2 - 1;
        }
        double $$9 = ($$8 /= 16.0f) * (float)$$4.m_122429_();
        double $$10 = $$8 * (float)$$4.m_122431_();
        p_221965_.m_7106_(DustParticleOptions.f_123656_, $$5 + $$9, $$6, $$7 + $$10, 0.0, 0.0, 0.0);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55828_) {
        p_55828_.m_61104_(f_54117_, f_55798_, f_55797_, f_52496_);
    }
}

