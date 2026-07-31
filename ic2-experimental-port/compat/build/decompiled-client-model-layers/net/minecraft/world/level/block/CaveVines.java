/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CaveVines {
    public static final VoxelShape f_152948_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    public static final BooleanProperty f_152949_ = BlockStateProperties.f_155977_;

    public static InteractionResult m_152953_(BlockState p_152954_, Level p_152955_, BlockPos p_152956_) {
        if (p_152954_.m_61143_(f_152949_).booleanValue()) {
            Block.m_49840_(p_152955_, p_152956_, new ItemStack(Items.f_151079_, 1));
            float $$3 = Mth.m_216283_(p_152955_.f_46441_, 0.8f, 1.2f);
            p_152955_.m_5594_(null, p_152956_, SoundEvents.f_144088_, SoundSource.BLOCKS, 1.0f, $$3);
            p_152955_.m_7731_(p_152956_, (BlockState)p_152954_.m_61124_(f_152949_, false), 2);
            return InteractionResult.m_19078_(p_152955_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public static boolean m_152951_(BlockState p_152952_) {
        return p_152952_.m_61138_(f_152949_) && p_152952_.m_61143_(f_152949_) != false;
    }

    public static ToIntFunction<BlockState> m_181217_(int p_181218_) {
        return p_181216_ -> p_181216_.m_61143_(BlockStateProperties.f_155977_) != false ? p_181218_ : 0;
    }
}

