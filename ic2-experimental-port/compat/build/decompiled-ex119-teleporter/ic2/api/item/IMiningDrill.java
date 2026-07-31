/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.item;

import ic2.api.item.ElectricItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IMiningDrill {
    public int energyUse(ItemStack var1, Level var2, BlockPos var3, BlockState var4);

    public int breakTime(ItemStack var1, Level var2, BlockPos var3, BlockState var4);

    public boolean breakBlock(ItemStack var1, Level var2, BlockPos var3, BlockState var4);

    default public boolean tryUsePower(ItemStack itemStack, double d) {
        return ElectricItem.manager.use(itemStack, d, null);
    }
}

