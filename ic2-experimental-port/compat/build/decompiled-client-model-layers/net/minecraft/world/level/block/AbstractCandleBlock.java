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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractCandleBlock
extends Block {
    public static final int f_151894_ = 3;
    public static final BooleanProperty f_151895_ = BlockStateProperties.f_61443_;

    protected AbstractCandleBlock(BlockBehaviour.Properties p_151898_) {
        super(p_151898_);
    }

    protected abstract Iterable<Vec3> m_142199_(BlockState var1);

    public static boolean m_151933_(BlockState p_151934_) {
        return p_151934_.m_61138_(f_151895_) && (p_151934_.m_204336_(BlockTags.f_144265_) || p_151934_.m_204336_(BlockTags.f_144268_)) && p_151934_.m_61143_(f_151895_) != false;
    }

    @Override
    public void m_5581_(Level p_151905_, BlockState p_151906_, BlockHitResult p_151907_, Projectile p_151908_) {
        if (!p_151905_.f_46443_ && p_151908_.m_6060_() && this.m_142595_(p_151906_)) {
            AbstractCandleBlock.m_151918_(p_151905_, p_151906_, p_151907_.m_82425_(), true);
        }
    }

    protected boolean m_142595_(BlockState p_151935_) {
        return p_151935_.m_61143_(f_151895_) == false;
    }

    @Override
    public void m_214162_(BlockState p_220697_, Level p_220698_, BlockPos p_220699_, RandomSource p_220700_) {
        if (!p_220697_.m_61143_(f_151895_).booleanValue()) {
            return;
        }
        this.m_142199_(p_220697_).forEach(p_220695_ -> AbstractCandleBlock.m_220687_(p_220698_, p_220695_.m_82520_(p_220699_.m_123341_(), p_220699_.m_123342_(), p_220699_.m_123343_()), p_220700_));
    }

    private static void m_220687_(Level p_220688_, Vec3 p_220689_, RandomSource p_220690_) {
        float $$3 = p_220690_.m_188501_();
        if ($$3 < 0.3f) {
            p_220688_.m_7106_(ParticleTypes.f_123762_, p_220689_.f_82479_, p_220689_.f_82480_, p_220689_.f_82481_, 0.0, 0.0, 0.0);
            if ($$3 < 0.17f) {
                p_220688_.m_7785_(p_220689_.f_82479_ + 0.5, p_220689_.f_82480_ + 0.5, p_220689_.f_82481_ + 0.5, SoundEvents.f_144096_, SoundSource.BLOCKS, 1.0f + p_220690_.m_188501_(), p_220690_.m_188501_() * 0.7f + 0.3f, false);
            }
        }
        p_220688_.m_7106_(ParticleTypes.f_175834_, p_220689_.f_82479_, p_220689_.f_82480_, p_220689_.f_82481_, 0.0, 0.0, 0.0);
    }

    public static void m_151899_(@Nullable Player p_151900_, BlockState p_151901_, LevelAccessor p_151902_, BlockPos p_151903_) {
        AbstractCandleBlock.m_151918_(p_151902_, p_151901_, p_151903_, false);
        if (p_151901_.m_60734_() instanceof AbstractCandleBlock) {
            ((AbstractCandleBlock)p_151901_.m_60734_()).m_142199_(p_151901_).forEach(p_151926_ -> p_151902_.m_7106_(ParticleTypes.f_123762_, (double)p_151903_.m_123341_() + p_151926_.m_7096_(), (double)p_151903_.m_123342_() + p_151926_.m_7098_(), (double)p_151903_.m_123343_() + p_151926_.m_7094_(), 0.0, 0.1f, 0.0));
        }
        p_151902_.m_5594_(null, p_151903_, SoundEvents.f_144098_, SoundSource.BLOCKS, 1.0f, 1.0f);
        p_151902_.m_142346_(p_151900_, GameEvent.f_157792_, p_151903_);
    }

    private static void m_151918_(LevelAccessor p_151919_, BlockState p_151920_, BlockPos p_151921_, boolean p_151922_) {
        p_151919_.m_7731_(p_151921_, (BlockState)p_151920_.m_61124_(f_151895_, p_151922_), 11);
    }
}

