/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item.block;

import ic2.core.block.BlockMultiID;
import ic2.core.block.state.IIdProvider;
import ic2.core.item.block.ItemBlockIC2;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMulti
extends ItemBlockIC2 {
    public ItemBlockMulti(Block block) {
        super(block);
        this.func_77627_a(true);
    }

    public int func_77647_b(int damage) {
        return damage;
    }

    @Override
    public String func_77667_c(ItemStack stack) {
        String name = ((IIdProvider)((Object)((Enum)((Object)this.field_150939_a.func_176203_a(stack.func_77960_j()).func_177229_b(((BlockMultiID)this.field_150939_a).getTypeProperty()))))).getName();
        return super.func_77667_c(stack) + "." + name;
    }
}

