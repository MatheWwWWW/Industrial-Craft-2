/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AmethystBlock
extends Block {
    public AmethystBlock(BlockBehaviour.Properties p_151999_) {
        super(p_151999_);
    }

    @Override
    public void m_5581_(Level p_152001_, BlockState p_152002_, BlockHitResult p_152003_, Projectile p_152004_) {
        if (!p_152001_.f_46443_) {
            BlockPos $$4 = p_152003_.m_82425_();
            p_152001_.m_5594_(null, $$4, SoundEvents.f_144245_, SoundSource.BLOCKS, 1.0f, 0.5f + p_152001_.f_46441_.m_188501_() * 1.2f);
            p_152001_.m_5594_(null, $$4, SoundEvents.f_144243_, SoundSource.BLOCKS, 1.0f, 0.5f + p_152001_.f_46441_.m_188501_() * 1.2f);
        }
    }
}

