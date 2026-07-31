/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CauldronBlock
extends AbstractCauldronBlock {
    private static final float f_182448_ = 0.05f;
    private static final float f_182449_ = 0.1f;

    public CauldronBlock(BlockBehaviour.Properties p_51403_) {
        super(p_51403_, CauldronInteraction.f_175606_);
    }

    @Override
    public boolean m_142596_(BlockState p_152947_) {
        return false;
    }

    protected static boolean m_182450_(Level p_182451_, Biome.Precipitation p_182452_) {
        if (p_182452_ == Biome.Precipitation.RAIN) {
            return p_182451_.m_213780_().m_188501_() < 0.05f;
        }
        if (p_182452_ == Biome.Precipitation.SNOW) {
            return p_182451_.m_213780_().m_188501_() < 0.1f;
        }
        return false;
    }

    @Override
    public void m_141997_(BlockState p_152935_, Level p_152936_, BlockPos p_152937_, Biome.Precipitation p_152938_) {
        if (!CauldronBlock.m_182450_(p_152936_, p_152938_)) {
            return;
        }
        if (p_152938_ == Biome.Precipitation.RAIN) {
            p_152936_.m_46597_(p_152937_, Blocks.f_152476_.m_49966_());
            p_152936_.m_142346_(null, GameEvent.f_157792_, p_152937_);
        } else if (p_152938_ == Biome.Precipitation.SNOW) {
            p_152936_.m_46597_(p_152937_, Blocks.f_152478_.m_49966_());
            p_152936_.m_142346_(null, GameEvent.f_157792_, p_152937_);
        }
    }

    @Override
    protected boolean m_142087_(Fluid p_152945_) {
        return true;
    }

    @Override
    protected void m_142310_(BlockState p_152940_, Level p_152941_, BlockPos p_152942_, Fluid p_152943_) {
        if (p_152943_ == Fluids.f_76193_) {
            BlockState $$4 = Blocks.f_152476_.m_49966_();
            p_152941_.m_46597_(p_152942_, $$4);
            p_152941_.m_220407_(GameEvent.f_157792_, p_152942_, GameEvent.Context.m_223722_($$4));
            p_152941_.m_46796_(1047, p_152942_, 0);
        } else if (p_152943_ == Fluids.f_76195_) {
            BlockState $$5 = Blocks.f_152477_.m_49966_();
            p_152941_.m_46597_(p_152942_, $$5);
            p_152941_.m_220407_(GameEvent.f_157792_, p_152942_, GameEvent.Context.m_223722_($$5));
            p_152941_.m_46796_(1046, p_152942_, 0);
        }
    }
}

