/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.DirectionalBlock
 *  net.minecraft.world.level.block.DoublePlantBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.DoubleBlockHalf
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.ref.Ic2Blocks;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;

public class Cultivation
extends TerraformerBase {
    static List<BlockState> plants = new ArrayList<BlockState>();

    @Override
    void init() {
        plants.add(Blocks.f_50359_.m_49966_());
        plants.add(Blocks.f_50359_.m_49966_());
        plants.add(Blocks.f_50035_.m_49966_());
        plants.add(Blocks.f_50112_.m_49966_());
        plants.add(Blocks.f_50111_.m_49966_());
        plants.add(Blocks.f_50359_.m_49966_());
        plants.add(Blocks.f_50357_.m_49966_());
        plants.add(Blocks.f_50355_.m_49966_());
        for (Holder holder : Registry.f_122824_.m_206058_(BlockTags.f_13104_)) {
            Block block = (Block)holder.m_203334_();
            if (!Cultivation.isVanilla(block)) continue;
            plants.add(block.m_49966_());
        }
        plants.add(Blocks.f_50092_.m_49966_());
        plants.add(Blocks.f_50073_.m_49966_());
        plants.add(Blocks.f_50072_.m_49966_());
        plants.add(Blocks.f_50133_.m_49966_());
        plants.add(Blocks.f_50186_.m_49966_());
        plants.add(Ic2Blocks.RUBBER_SAPLING.m_49966_());
    }

    @Override
    boolean terraform(Level level, BlockPos blockPos) {
        Block block;
        if ((blockPos = TileEntityTerra.getFirstSolidBlockFrom(level, blockPos, 10)) == null) {
            return false;
        }
        if (TileEntityTerra.switchGround(level, blockPos, Blocks.f_49992_, Blocks.f_50493_.m_49966_(), true)) {
            return true;
        }
        if (TileEntityTerra.switchGround(level, blockPos, Blocks.f_50259_, Blocks.f_50493_.m_49966_(), true)) {
            int n = 4;
            while (--n > 0 && TileEntityTerra.switchGround(level, blockPos, Blocks.f_50259_, Blocks.f_50493_.m_49966_(), true)) {
            }
        }
        if ((block = level.m_8055_(blockPos).m_60734_()) == Blocks.f_50493_) {
            level.m_46597_(blockPos, Blocks.f_50034_.m_49966_());
            return true;
        }
        if (block == Blocks.f_50034_) {
            return Cultivation.growPlantsOn(level, blockPos);
        }
        return false;
    }

    private static boolean growPlantsOn(Level level, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.m_7494_();
        BlockState blockState = level.m_8055_(blockPos2);
        Block block = blockState.m_60734_();
        if (blockState.m_60795_() || block == Blocks.f_50359_ && level.f_46441_.m_188503_(4) == 0) {
            BlockState blockState2 = Cultivation.pickRandomPlant(level.f_46441_);
            if (blockState2.m_61148_().containsKey((Object)DirectionalBlock.f_52588_)) {
                blockState2 = (BlockState)blockState2.m_61124_((Property)DirectionalBlock.f_52588_, (Comparable)Util.HORIZONTAL_DIRS[level.f_46441_.m_188503_(Util.HORIZONTAL_DIRS.length)]);
            }
            if (blockState2.m_60734_() instanceof CropBlock) {
                level.m_46597_(blockPos, Blocks.f_50093_.m_49966_());
            } else if (blockState2.m_60734_() instanceof DoublePlantBlock) {
                level.m_46597_(blockPos2, (BlockState)blockState2.m_61124_((Property)DoublePlantBlock.f_52858_, (Comparable)DoubleBlockHalf.LOWER));
                level.m_46597_(blockPos2.m_7494_(), (BlockState)blockState2.m_61124_((Property)DoublePlantBlock.f_52858_, (Comparable)DoubleBlockHalf.UPPER));
                return true;
            }
            level.m_46597_(blockPos2, blockState2);
            return true;
        }
        return false;
    }

    private static BlockState pickRandomPlant(RandomSource randomSource) {
        return plants.get(randomSource.m_188503_(plants.size()));
    }
}

