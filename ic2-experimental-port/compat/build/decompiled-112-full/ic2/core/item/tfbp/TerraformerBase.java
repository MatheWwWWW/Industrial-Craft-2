/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.tfbp;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

abstract class TerraformerBase {
    TerraformerBase() {
    }

    abstract boolean terraform(World var1, BlockPos var2);

    void init() {
    }
}

