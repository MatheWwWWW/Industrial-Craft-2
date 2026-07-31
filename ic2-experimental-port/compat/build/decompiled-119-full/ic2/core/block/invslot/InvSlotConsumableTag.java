/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.util.StackUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableTag
extends InvSlotConsumable {
    protected final TagKey<Item> tag;

    public InvSlotConsumableTag(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, TagKey<Item> tagKey) {
        super(iInventorySlotHolder, string, n);
        this.tag = tagKey;
    }

    public InvSlotConsumableTag(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, TagKey<Item> tagKey) {
        super(iInventorySlotHolder, string, access, n, invSide);
        this.tag = tagKey;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        return itemStack.m_204117_(this.tag);
    }
}

