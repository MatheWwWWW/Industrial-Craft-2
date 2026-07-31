/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLeaves
 *  net.minecraft.item.ItemLeaves
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item.block;

import ic2.core.block.Ic2Leaves;
import ic2.core.init.Localization;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemLeaves;
import net.minecraft.item.ItemStack;

public class ItemIc2Leaves
extends ItemLeaves {
    public ItemIc2Leaves(Block block) {
        super((BlockLeaves)block);
        this.func_77627_a(false);
    }

    public String func_77658_a() {
        return "ic2." + super.func_77658_a().substring(5);
    }

    public String func_77667_c(ItemStack stack) {
        return this.func_77658_a() + "." + ((Ic2Leaves.LeavesType)((Object)this.field_150939_a.func_176203_a(stack.func_77960_j()).func_177229_b(Ic2Leaves.typeProperty))).func_176610_l();
    }

    public String func_77653_i(ItemStack stack) {
        return Localization.translate(this.func_77667_c(stack));
    }
}

