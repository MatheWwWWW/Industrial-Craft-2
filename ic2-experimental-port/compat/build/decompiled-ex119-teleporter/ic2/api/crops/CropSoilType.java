/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.api.crops;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum CropSoilType {
    FARMLAND(Blocks.f_50093_),
    MYCELIUM(Blocks.f_50195_),
    SAND(Blocks.f_49992_),
    SOULSAND(Blocks.f_50135_);

    private final Block block;

    private CropSoilType(Block block) {
        if (block == null) {
            throw new NullPointerException("null block");
        }
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public static boolean contains(Block block) {
        for (CropSoilType cropSoilType : CropSoilType.values()) {
            if (cropSoilType.getBlock() != block) continue;
            return true;
        }
        return false;
    }
}

