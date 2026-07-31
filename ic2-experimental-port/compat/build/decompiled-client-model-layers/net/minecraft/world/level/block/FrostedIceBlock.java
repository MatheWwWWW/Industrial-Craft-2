/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FrostedIceBlock
extends IceBlock {
    public static final int f_153268_ = 3;
    public static final IntegerProperty f_53561_ = BlockStateProperties.f_61407_;
    private static final int f_153269_ = 4;
    private static final int f_153270_ = 2;

    public FrostedIceBlock(BlockBehaviour.Properties p_53564_) {
        super(p_53564_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53561_, 0));
    }

    @Override
    public void m_213898_(BlockState p_221238_, ServerLevel p_221239_, BlockPos p_221240_, RandomSource p_221241_) {
        this.m_213897_(p_221238_, p_221239_, p_221240_, p_221241_);
    }

    @Override
    public void m_213897_(BlockState p_221233_, ServerLevel p_221234_, BlockPos p_221235_, RandomSource p_221236_) {
        if ((p_221236_.m_188503_(3) == 0 || this.m_53565_(p_221234_, p_221235_, 4)) && p_221234_.m_46803_(p_221235_) > 11 - p_221233_.m_61143_(f_53561_) - p_221233_.m_60739_(p_221234_, p_221235_) && this.m_53592_(p_221233_, p_221234_, p_221235_)) {
            BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
            for (Direction $$5 : Direction.values()) {
                $$4.m_122159_(p_221235_, $$5);
                BlockState $$6 = p_221234_.m_8055_($$4);
                if (!$$6.m_60713_(this) || this.m_53592_($$6, p_221234_, $$4)) continue;
                p_221234_.m_186460_($$4, this, Mth.m_216271_(p_221236_, 20, 40));
            }
            return;
        }
        p_221234_.m_186460_(p_221235_, this, Mth.m_216271_(p_221236_, 20, 40));
    }

    private boolean m_53592_(BlockState p_53593_, Level p_53594_, BlockPos p_53595_) {
        int $$3 = p_53593_.m_61143_(f_53561_);
        if ($$3 < 3) {
            p_53594_.m_7731_(p_53595_, (BlockState)p_53593_.m_61124_(f_53561_, $$3 + 1), 2);
            return false;
        }
        this.m_54168_(p_53593_, p_53594_, p_53595_);
        return true;
    }

    @Override
    public void m_6861_(BlockState p_53579_, Level p_53580_, BlockPos p_53581_, Block p_53582_, BlockPos p_53583_, boolean p_53584_) {
        if (p_53582_.m_49966_().m_60713_(this) && this.m_53565_(p_53580_, p_53581_, 2)) {
            this.m_54168_(p_53579_, p_53580_, p_53581_);
        }
        super.m_6861_(p_53579_, p_53580_, p_53581_, p_53582_, p_53583_, p_53584_);
    }

    private boolean m_53565_(BlockGetter p_53566_, BlockPos p_53567_, int p_53568_) {
        int $$3 = 0;
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (Direction $$5 : Direction.values()) {
            $$4.m_122159_(p_53567_, $$5);
            if (!p_53566_.m_8055_($$4).m_60713_(this) || ++$$3 < p_53568_) continue;
            return false;
        }
        return true;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53586_) {
        p_53586_.m_61104_(f_53561_);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_53570_, BlockPos p_53571_, BlockState p_53572_) {
        return ItemStack.f_41583_;
    }
}

