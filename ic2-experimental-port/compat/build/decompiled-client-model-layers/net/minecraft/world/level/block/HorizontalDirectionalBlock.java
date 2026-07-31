/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public abstract class HorizontalDirectionalBlock
extends Block {
    public static final DirectionProperty f_54117_ = BlockStateProperties.f_61374_;

    protected HorizontalDirectionalBlock(BlockBehaviour.Properties p_54120_) {
        super(p_54120_);
    }

    @Override
    public BlockState m_6843_(BlockState p_54125_, Rotation p_54126_) {
        return (BlockState)p_54125_.m_61124_(f_54117_, p_54126_.m_55954_(p_54125_.m_61143_(f_54117_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_54122_, Mirror p_54123_) {
        return p_54122_.m_60717_(p_54123_.m_54846_(p_54122_.m_61143_(f_54117_)));
    }
}

