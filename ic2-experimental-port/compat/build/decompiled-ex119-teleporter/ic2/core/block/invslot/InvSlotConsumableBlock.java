/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.tileentity.TileEntityInventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableBlock
extends InvSlotConsumable {
    public InvSlotConsumableBlock(TileEntityInventory tileEntityInventory, String string, int n) {
        this(tileEntityInventory, string, InvSlot.Access.I, n, InvSlot.InvSide.TOP);
    }

    public InvSlotConsumableBlock(TileEntityInventory tileEntityInventory, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide) {
        super(tileEntityInventory, string, access, n, invSide);
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return itemStack.m_41720_() instanceof BlockItem;
    }
}

