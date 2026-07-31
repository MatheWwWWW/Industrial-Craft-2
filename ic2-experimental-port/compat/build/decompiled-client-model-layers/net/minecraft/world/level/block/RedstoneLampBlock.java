/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneLampBlock
extends Block {
    public static final BooleanProperty f_55654_ = RedstoneTorchBlock.f_55674_;

    public RedstoneLampBlock(BlockBehaviour.Properties p_55657_) {
        super(p_55657_);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(f_55654_, false));
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_55659_) {
        return (BlockState)this.m_49966_().m_61124_(f_55654_, p_55659_.m_43725_().m_46753_(p_55659_.m_8083_()));
    }

    @Override
    public void m_6861_(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_) {
        if (p_55667_.f_46443_) {
            return;
        }
        boolean $$6 = p_55666_.m_61143_(f_55654_);
        if ($$6 != p_55667_.m_46753_(p_55668_)) {
            if ($$6) {
                p_55667_.m_186460_(p_55668_, this, 4);
            } else {
                p_55667_.m_7731_(p_55668_, (BlockState)p_55666_.m_61122_(f_55654_), 2);
            }
        }
    }

    @Override
    public void m_213897_(BlockState p_221937_, ServerLevel p_221938_, BlockPos p_221939_, RandomSource p_221940_) {
        if (p_221937_.m_61143_(f_55654_).booleanValue() && !p_221938_.m_46753_(p_221939_)) {
            p_221938_.m_7731_(p_221939_, (BlockState)p_221937_.m_61122_(f_55654_), 2);
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55673_) {
        p_55673_.m_61104_(f_55654_);
    }
}

