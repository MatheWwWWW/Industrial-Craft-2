/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class WaterloggedVegetationPatchFeature
extends VegetationPatchFeature {
    public WaterloggedVegetationPatchFeature(Codec<VegetationPatchConfiguration> p_160635_) {
        super(p_160635_);
    }

    @Override
    protected Set<BlockPos> m_213631_(WorldGenLevel p_225339_, VegetationPatchConfiguration p_225340_, RandomSource p_225341_, BlockPos p_225342_, Predicate<BlockState> p_225343_, int p_225344_, int p_225345_) {
        Set<BlockPos> $$7 = super.m_213631_(p_225339_, p_225340_, p_225341_, p_225342_, p_225343_, p_225344_, p_225345_);
        HashSet<BlockPos> $$8 = new HashSet<BlockPos>();
        BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
        for (BlockPos $$10 : $$7) {
            if (WaterloggedVegetationPatchFeature.m_160655_(p_225339_, $$7, $$10, $$9)) continue;
            $$8.add($$10);
        }
        for (BlockPos $$11 : $$8) {
            p_225339_.m_7731_($$11, Blocks.f_49990_.m_49966_(), 2);
        }
        return $$8;
    }

    private static boolean m_160655_(WorldGenLevel p_160656_, Set<BlockPos> p_160657_, BlockPos p_160658_, BlockPos.MutableBlockPos p_160659_) {
        return WaterloggedVegetationPatchFeature.m_160650_(p_160656_, p_160658_, p_160659_, Direction.NORTH) || WaterloggedVegetationPatchFeature.m_160650_(p_160656_, p_160658_, p_160659_, Direction.EAST) || WaterloggedVegetationPatchFeature.m_160650_(p_160656_, p_160658_, p_160659_, Direction.SOUTH) || WaterloggedVegetationPatchFeature.m_160650_(p_160656_, p_160658_, p_160659_, Direction.WEST) || WaterloggedVegetationPatchFeature.m_160650_(p_160656_, p_160658_, p_160659_, Direction.DOWN);
    }

    private static boolean m_160650_(WorldGenLevel p_160651_, BlockPos p_160652_, BlockPos.MutableBlockPos p_160653_, Direction p_160654_) {
        p_160653_.m_122159_(p_160652_, p_160654_);
        return !p_160651_.m_8055_(p_160653_).m_60783_(p_160651_, p_160653_, p_160654_.m_122424_());
    }

    @Override
    protected boolean m_213555_(WorldGenLevel p_225347_, VegetationPatchConfiguration p_225348_, ChunkGenerator p_225349_, RandomSource p_225350_, BlockPos p_225351_) {
        if (super.m_213555_(p_225347_, p_225348_, p_225349_, p_225350_, p_225351_.m_7495_())) {
            BlockState $$5 = p_225347_.m_8055_(p_225351_);
            if ($$5.m_61138_(BlockStateProperties.f_61362_) && !$$5.m_61143_(BlockStateProperties.f_61362_).booleanValue()) {
                p_225347_.m_7731_(p_225351_, (BlockState)$$5.m_61124_(BlockStateProperties.f_61362_, true), 2);
            }
            return true;
        }
        return false;
    }
}

