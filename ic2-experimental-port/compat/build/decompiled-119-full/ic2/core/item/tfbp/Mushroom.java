/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.MushroomBlock
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.util.BiomeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Mushroom
extends TerraformerBase {
    @Override
    boolean terraform(Level level, BlockPos blockPos) {
        if ((blockPos = TileEntityTerra.getFirstSolidBlockFrom(level, blockPos, 20)) == null) {
            return false;
        }
        return Mushroom.growBlockWithDependancy(level, blockPos, Blocks.f_50180_, Blocks.f_50072_);
    }

    private static boolean growBlockWithDependancy(Level level, BlockPos blockPos, Block block, Block block2) {
        Block block3;
        Block block4;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = blockPos.m_123341_() - 1; block2 != null && i < blockPos.m_123341_() + 1; ++i) {
            block1: for (int j = blockPos.m_123343_() - 1; j < blockPos.m_123343_() + 1; ++j) {
                for (int k = blockPos.m_123342_() + 5; k > blockPos.m_123342_() - 2; --k) {
                    mutableBlockPos.m_122178_(i, k, j);
                    block4 = level.m_8055_((BlockPos)mutableBlockPos);
                    block3 = block4.m_60734_();
                    if (block2 == Blocks.f_50195_) {
                        if (block3 == block2 || block3 == Blocks.f_50180_ || block3 == Blocks.f_50181_) continue block1;
                        if (block4.m_60795_() || block3 != Blocks.f_50493_ && block3 != Blocks.f_50034_) continue;
                        BlockPos blockPos2 = new BlockPos((Vec3i)mutableBlockPos);
                        level.m_46597_(blockPos2, block2.m_49966_());
                        BiomeUtil.setBiome((LevelReader)level, blockPos2, BiomeUtil.getBiome(level, (ResourceKey<Biome>)Biomes.f_48215_));
                        return true;
                    }
                    if (block2 != Blocks.f_50072_) continue;
                    if (block3 == Blocks.f_50072_ || block3 == Blocks.f_50073_) continue block1;
                    if (block4.m_60795_() || !Mushroom.growBlockWithDependancy(level, (BlockPos)mutableBlockPos, Blocks.f_50072_, Blocks.f_50195_)) continue;
                    return true;
                }
            }
        }
        if (block == Blocks.f_50072_) {
            Block block5 = level.m_8055_(blockPos).m_60734_();
            if (block5 != Blocks.f_50195_) {
                if (block5 == Blocks.f_50180_ || block5 == Blocks.f_50181_) {
                    level.m_46597_(blockPos, Blocks.f_50195_.m_49966_());
                } else {
                    return false;
                }
            }
            BlockPos blockPos3 = blockPos.m_7494_();
            BlockState blockState = level.m_8055_(blockPos3);
            block4 = blockState.m_60734_();
            if (!blockState.m_60795_() && block4 != Blocks.f_50359_) {
                return false;
            }
            block3 = level.f_46441_.m_188499_() ? Blocks.f_50072_ : Blocks.f_50073_;
            level.m_46597_(blockPos3, block3.m_49966_());
            return true;
        }
        if (block == Blocks.f_50180_) {
            BlockPos blockPos4 = blockPos.m_7494_();
            BlockState blockState = level.m_8055_(blockPos4);
            Block block6 = blockState.m_60734_();
            if (block6 != Blocks.f_50072_ && block6 != Blocks.f_50073_) {
                return false;
            }
            if (((MushroomBlock)block6).m_221773_((ServerLevel)level, blockPos, blockState, level.f_46441_)) {
                for (int i = blockPos.m_123341_() - 1; i < blockPos.m_123341_() + 1; ++i) {
                    for (int j = blockPos.m_123343_() - 1; j < blockPos.m_123343_() + 1; ++j) {
                        mutableBlockPos.m_122178_(i, blockPos4.m_123342_(), j);
                        Block block7 = level.m_8055_((BlockPos)mutableBlockPos).m_60734_();
                        if (block7 != Blocks.f_50072_ && block7 != Blocks.f_50073_) continue;
                        level.m_7471_(new BlockPos((Vec3i)mutableBlockPos), false);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

