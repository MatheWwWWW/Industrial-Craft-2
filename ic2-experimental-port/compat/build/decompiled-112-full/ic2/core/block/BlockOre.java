/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 */
package ic2.core.block;

import ic2.core.block.BlockMultiID;
import ic2.core.block.type.ResourceBlock;
import ic2.core.ref.BlockName;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockOre
extends BlockMultiID<ResourceBlock> {
    public static BlockOre create() {
        return BlockMultiID.create(BlockOre.class, ResourceBlock.class, new Object[0]);
    }

    public BlockOre() {
        super(BlockName.resource, Material.field_151576_e);
    }

    public int func_180651_a(IBlockState state) {
        return this.func_176201_c(state);
    }
}

