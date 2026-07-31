/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DropExperienceBlock
extends Block {
    private final IntProvider f_221079_;

    public DropExperienceBlock(BlockBehaviour.Properties p_221081_) {
        this(p_221081_, ConstantInt.m_146483_(0));
    }

    public DropExperienceBlock(BlockBehaviour.Properties p_221083_, IntProvider p_221084_) {
        super(p_221083_);
        this.f_221079_ = p_221084_;
    }

    @Override
    public void m_213646_(BlockState p_221086_, ServerLevel p_221087_, BlockPos p_221088_, ItemStack p_221089_, boolean p_221090_) {
        super.m_213646_(p_221086_, p_221087_, p_221088_, p_221089_, p_221090_);
        if (p_221090_) {
            this.m_220822_(p_221087_, p_221088_, p_221089_, this.f_221079_);
        }
    }
}

