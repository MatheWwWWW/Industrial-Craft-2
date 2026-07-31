/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.dispenser.BehaviorDefaultDispenseItem
 *  net.minecraft.dispenser.IBlockSource
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.api.recipe.Recipes;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.properties.IProperty;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorScrapboxDispense
extends BehaviorDefaultDispenseItem {
    protected ItemStack func_82487_b(IBlockSource blockSource, ItemStack stack) {
        if (StackUtil.checkItemEquality(stack, ItemName.crafting.getItemStack(CraftingItemType.scrap_box))) {
            EnumFacing facing = (EnumFacing)blockSource.func_189992_e().func_177229_b((IProperty)BlockDispenser.field_176441_a);
            IPosition position = BlockDispenser.func_149939_a((IBlockSource)blockSource);
            BehaviorScrapboxDispense.func_82486_a((World)blockSource.func_82618_k(), (ItemStack)Recipes.scrapboxDrops.getDrop(stack, true), (int)6, (EnumFacing)facing, (IPosition)position);
        }
        return stack;
    }
}

