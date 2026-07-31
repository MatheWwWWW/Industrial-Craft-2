/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.item.tfbp;

import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

abstract class TerraformerBase {
    TerraformerBase() {
    }

    abstract boolean terraform(Level var1, BlockPos var2);

    void init() {
    }

    protected static boolean isVanilla(Block block) {
        ResourceLocation resourceLocation = Util.getName(block);
        return resourceLocation != null && resourceLocation.m_135827_().equals("minecraft");
    }
}

