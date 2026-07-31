/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.lighting.LayerLightEngine;

public class NyliumBlock
extends Block
implements BonemealableBlock {
    protected NyliumBlock(BlockBehaviour.Properties p_55057_) {
        super(p_55057_);
    }

    private static boolean m_55078_(BlockState p_55079_, LevelReader p_55080_, BlockPos p_55081_) {
        BlockPos $$3 = p_55081_.m_7494_();
        BlockState $$4 = p_55080_.m_8055_($$3);
        int $$5 = LayerLightEngine.m_75667_(p_55080_, p_55079_, p_55081_, $$4, $$3, Direction.UP, $$4.m_60739_(p_55080_, $$3));
        return $$5 < p_55080_.m_7469_();
    }

    @Override
    public void m_213898_(BlockState p_221835_, ServerLevel p_221836_, BlockPos p_221837_, RandomSource p_221838_) {
        if (!NyliumBlock.m_55078_(p_221835_, p_221836_, p_221837_)) {
            p_221836_.m_46597_(p_221837_, Blocks.f_50134_.m_49966_());
        }
    }

    @Override
    public boolean m_7370_(BlockGetter p_55064_, BlockPos p_55065_, BlockState p_55066_, boolean p_55067_) {
        return p_55064_.m_8055_(p_55065_.m_7494_()).m_60795_();
    }

    @Override
    public boolean m_214167_(Level p_221830_, RandomSource p_221831_, BlockPos p_221832_, BlockState p_221833_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221825_, RandomSource p_221826_, BlockPos p_221827_, BlockState p_221828_) {
        BlockState $$4 = p_221825_.m_8055_(p_221827_);
        BlockPos $$5 = p_221827_.m_7494_();
        ChunkGenerator $$6 = p_221825_.m_7726_().m_8481_();
        if ($$4.m_60713_(Blocks.f_50699_)) {
            NetherFeatures.f_195037_.m_203334_().m_224953_(p_221825_, $$6, p_221826_, $$5);
        } else if ($$4.m_60713_(Blocks.f_50690_)) {
            NetherFeatures.f_195040_.m_203334_().m_224953_(p_221825_, $$6, p_221826_, $$5);
            NetherFeatures.f_195042_.m_203334_().m_224953_(p_221825_, $$6, p_221826_, $$5);
            if (p_221826_.m_188503_(8) == 0) {
                NetherFeatures.f_195044_.m_203334_().m_224953_(p_221825_, $$6, p_221826_, $$5);
            }
        }
    }
}

