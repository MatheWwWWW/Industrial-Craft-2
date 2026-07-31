/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class GlowLichenBlock
extends MultifaceBlock
implements BonemealableBlock,
SimpleWaterloggedBlock {
    private static final BooleanProperty f_153279_ = BlockStateProperties.f_61362_;
    private final MultifaceSpreader f_221257_ = new MultifaceSpreader(this);

    public GlowLichenBlock(BlockBehaviour.Properties p_153282_) {
        super(p_153282_);
        this.m_49959_((BlockState)this.m_49966_().m_61124_(f_153279_, false));
    }

    public static ToIntFunction<BlockState> m_181222_(int p_181223_) {
        return p_181221_ -> MultifaceBlock.m_153960_(p_181221_) ? p_181223_ : 0;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153309_) {
        super.m_7926_(p_153309_);
        p_153309_.m_61104_(f_153279_);
    }

    @Override
    public BlockState m_7417_(BlockState p_153302_, Direction p_153303_, BlockState p_153304_, LevelAccessor p_153305_, BlockPos p_153306_, BlockPos p_153307_) {
        if (p_153302_.m_61143_(f_153279_).booleanValue()) {
            p_153305_.m_186469_(p_153306_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_153305_));
        }
        return super.m_7417_(p_153302_, p_153303_, p_153304_, p_153305_, p_153306_, p_153307_);
    }

    @Override
    public boolean m_6864_(BlockState p_153299_, BlockPlaceContext p_153300_) {
        return !p_153300_.m_43722_().m_150930_(Items.f_151025_) || super.m_6864_(p_153299_, p_153300_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_153289_, BlockPos p_153290_, BlockState p_153291_, boolean p_153292_) {
        return Direction.m_235666_().anyMatch(p_153316_ -> this.f_221257_.m_221601_(p_153291_, p_153289_, p_153290_, p_153316_.m_122424_()));
    }

    @Override
    public boolean m_214167_(Level p_221264_, RandomSource p_221265_, BlockPos p_221266_, BlockState p_221267_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221259_, RandomSource p_221260_, BlockPos p_221261_, BlockState p_221262_) {
        this.f_221257_.m_221619_(p_221262_, p_221259_, p_221261_, p_221260_);
    }

    @Override
    public FluidState m_5888_(BlockState p_153311_) {
        if (p_153311_.m_61143_(f_153279_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_153311_);
    }

    @Override
    public boolean m_7420_(BlockState p_181225_, BlockGetter p_181226_, BlockPos p_181227_) {
        return p_181225_.m_60819_().m_76178_();
    }

    @Override
    public MultifaceSpreader m_213612_() {
        return this.f_221257_;
    }
}

