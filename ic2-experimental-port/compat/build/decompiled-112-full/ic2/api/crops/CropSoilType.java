/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 */
package ic2.api.crops;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum CropSoilType {
    FARMLAND(Blocks.field_150458_ak),
    MYCELIUM((Block)Blocks.field_150391_bh),
    SAND((Block)Blocks.field_150354_m),
    SOULSAND(Blocks.field_150425_aM);

    private final Block block;

    private CropSoilType(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public static boolean contais(Block block) {
        for (CropSoilType aux : CropSoilType.values()) {
            if (aux.getBlock() != block) continue;
            return true;
        }
        return false;
    }
}

