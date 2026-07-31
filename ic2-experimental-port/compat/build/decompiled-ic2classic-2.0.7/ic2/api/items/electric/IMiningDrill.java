/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.items.electric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public interface IMiningDrill {
    public boolean canMineBlock(ItemStack var1, BlockState var2, LevelReader var3, BlockPos var4);

    default public int getMiningBoost(ItemStack stack, BlockState state) {
        return 0;
    }

    default public int getExtraEnergyCost(ItemStack stack) {
        return 0;
    }

    public boolean canDrillBeUsed(ItemStack var1);

    public void onDrillUsed(ItemStack var1);
}

