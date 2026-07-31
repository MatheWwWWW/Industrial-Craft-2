/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEventListener;

public class SculkCatalystBlock
extends BaseEntityBlock {
    public static final int f_222085_ = 8;
    public static final BooleanProperty f_222086_ = BlockStateProperties.f_222995_;
    private final IntProvider f_222087_ = ConstantInt.m_146483_(5);

    public SculkCatalystBlock(BlockBehaviour.Properties p_222090_) {
        super(p_222090_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_222086_, false));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_222115_) {
        p_222115_.m_61104_(f_222086_);
    }

    @Override
    public void m_213897_(BlockState p_222104_, ServerLevel p_222105_, BlockPos p_222106_, RandomSource p_222107_) {
        if (p_222104_.m_61143_(f_222086_).booleanValue()) {
            p_222105_.m_7731_(p_222106_, (BlockState)p_222104_.m_61124_(f_222086_, false), 3);
        }
    }

    public static void m_222094_(ServerLevel p_222095_, BlockPos p_222096_, BlockState p_222097_, RandomSource p_222098_) {
        p_222095_.m_7731_(p_222096_, (BlockState)p_222097_.m_61124_(f_222086_, true), 3);
        p_222095_.m_186460_(p_222096_, p_222097_.m_60734_(), 8);
        p_222095_.m_8767_(ParticleTypes.f_235898_, (double)p_222096_.m_123341_() + 0.5, (double)p_222096_.m_123342_() + 1.15, (double)p_222096_.m_123343_() + 0.5, 2, 0.2, 0.0, 0.2, 0.0);
        p_222095_.m_5594_(null, p_222096_, SoundEvents.f_215740_, SoundSource.BLOCKS, 2.0f, 0.6f + p_222098_.m_188501_() * 0.4f);
    }

    @Override
    @Nullable
    public BlockEntity m_142194_(BlockPos p_222117_, BlockState p_222118_) {
        return new SculkCatalystBlockEntity(p_222117_, p_222118_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> GameEventListener m_214009_(ServerLevel p_222092_, T p_222093_) {
        if (p_222093_ instanceof SculkCatalystBlockEntity) {
            SculkCatalystBlockEntity $$2 = (SculkCatalystBlockEntity)p_222093_;
            return $$2;
        }
        return null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_222100_, BlockState p_222101_, BlockEntityType<T> p_222102_) {
        if (p_222100_.f_46443_) {
            return null;
        }
        return SculkCatalystBlock.m_152132_(p_222102_, BlockEntityType.f_222758_, SculkCatalystBlockEntity::m_222779_);
    }

    @Override
    public RenderShape m_7514_(BlockState p_222120_) {
        return RenderShape.MODEL;
    }

    @Override
    public void m_213646_(BlockState p_222109_, ServerLevel p_222110_, BlockPos p_222111_, ItemStack p_222112_, boolean p_222113_) {
        super.m_213646_(p_222109_, p_222110_, p_222111_, p_222112_, p_222113_);
        if (p_222113_) {
            this.m_220822_(p_222110_, p_222111_, p_222112_, this.f_222087_);
        }
    }
}

