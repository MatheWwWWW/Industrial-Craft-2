/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class InvSlotConsumableClass
extends InvSlotConsumable {
    private final Class<?> clazz;

    public InvSlotConsumableClass(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, Class<?> clazz) {
        super(iInventorySlotHolder, string, access, n, invSide);
        this.clazz = clazz;
    }

    public InvSlotConsumableClass(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, Class<?> clazz) {
        super(iInventorySlotHolder, string, n);
        this.clazz = clazz;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        if (itemStack.m_41720_() instanceof BlockItem) {
            return this.clazz.isInstance(Block.m_49814_((Item)itemStack.m_41720_()));
        }
        return this.clazz.isInstance(itemStack.m_41720_());
    }
}

