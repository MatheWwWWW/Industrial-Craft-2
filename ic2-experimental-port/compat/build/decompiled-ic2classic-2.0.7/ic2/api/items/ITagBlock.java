/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 */
package ic2.api.items;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public interface ITagBlock {
    public boolean matches(ItemStack var1, Block var2);

    public List<Block> getBlocks(ItemStack var1);
}

