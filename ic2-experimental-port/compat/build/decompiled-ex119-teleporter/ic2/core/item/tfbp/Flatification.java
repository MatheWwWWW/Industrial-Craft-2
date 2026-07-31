/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.ref.Ic2Blocks;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class Flatification
extends TerraformerBase {
    static Set<Block> removable = Collections.newSetFromMap(new IdentityHashMap());

    @Override
    void init() {
        removable.add(Blocks.f_50125_);
        removable.add(Blocks.f_50126_);
        removable.add(Blocks.f_50034_);
        removable.add(Blocks.f_50069_);
        removable.add(Blocks.f_49994_);
        removable.add(Blocks.f_49992_);
        removable.add(Blocks.f_50493_);
        removable.add(Blocks.f_50050_);
        removable.add(Blocks.f_50051_);
        removable.add(Blocks.f_50052_);
        removable.add(Blocks.f_50053_);
        removable.add(Blocks.f_50054_);
        removable.add(Blocks.f_50055_);
        removable.add(Blocks.f_50359_);
        removable.add(Blocks.f_50112_);
        removable.add(Blocks.f_50111_);
        removable.add(Blocks.f_50092_);
        removable.add(Blocks.f_50073_);
        removable.add(Blocks.f_50072_);
        removable.add(Blocks.f_50133_);
        removable.add(Blocks.f_50186_);
        removable.add((Block)Ic2Blocks.RUBBER_LEAVES);
        removable.add(Ic2Blocks.RUBBER_SAPLING);
        removable.add((Block)Ic2Blocks.RUBBER_LOG);
    }

    @Override
    boolean terraform(Level level, BlockPos blockPos) {
        BlockPos blockPos2 = TileEntityTerra.getFirstBlockFrom(level, blockPos, 20);
        if (blockPos2 == null) {
            return false;
        }
        if (level.m_8055_(blockPos2).m_60734_() == Blocks.f_50125_) {
            blockPos2 = blockPos2.m_7495_();
        }
        if (blockPos.m_123342_() == blockPos2.m_123342_()) {
            return false;
        }
        if (blockPos2.m_123342_() < blockPos.m_123342_()) {
            level.m_46597_(blockPos2.m_7494_(), Blocks.f_50493_.m_49966_());
            return true;
        }
        if (Flatification.canRemove(level.m_8055_(blockPos2).m_60734_())) {
            level.m_7471_(blockPos2, false);
            return true;
        }
        return false;
    }

    private static boolean canRemove(Block block) {
        return removable.contains(block) || block.m_204297_().m_203656_(BlockTags.f_13104_) || block.m_204297_().m_203656_(BlockTags.f_13106_);
    }
}

