/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FungusBlock
extends BushBlock
implements BonemealableBlock {
    protected static final VoxelShape f_53596_ = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
    private static final double f_153271_ = 0.4;
    private final Supplier<Holder<ConfiguredFeature<HugeFungusConfiguration, ?>>> f_53597_;

    protected FungusBlock(BlockBehaviour.Properties p_53600_, Supplier<Holder<ConfiguredFeature<HugeFungusConfiguration, ?>>> p_53601_) {
        super(p_53600_);
        this.f_53597_ = p_53601_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53618_, BlockGetter p_53619_, BlockPos p_53620_, CollisionContext p_53621_) {
        return f_53596_;
    }

    @Override
    protected boolean m_6266_(BlockState p_53623_, BlockGetter p_53624_, BlockPos p_53625_) {
        return p_53623_.m_204336_(BlockTags.f_13077_) || p_53623_.m_60713_(Blocks.f_50195_) || p_53623_.m_60713_(Blocks.f_50136_) || super.m_6266_(p_53623_, p_53624_, p_53625_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_53608_, BlockPos p_53609_, BlockState p_53610_, boolean p_53611_) {
        Block $$4 = this.f_53597_.get().m_203334_().f_65378_().f_65897_.m_60734_();
        BlockState $$5 = p_53608_.m_8055_(p_53609_.m_7495_());
        return $$5.m_60713_($$4);
    }

    @Override
    public boolean m_214167_(Level p_221248_, RandomSource p_221249_, BlockPos p_221250_, BlockState p_221251_) {
        return (double)p_221249_.m_188501_() < 0.4;
    }

    @Override
    public void m_214148_(ServerLevel p_221243_, RandomSource p_221244_, BlockPos p_221245_, BlockState p_221246_) {
        this.f_53597_.get().m_203334_().m_224953_(p_221243_, p_221243_.m_7726_().m_8481_(), p_221244_, p_221245_);
    }
}

