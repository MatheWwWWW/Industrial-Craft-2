/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.ingridients.queue;

import ic2.api.recipes.ingridients.queue.IInputter;
import ic2.api.recipes.ingridients.queue.IStackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class MultiStackOutput
implements IStackOutput {
    ItemStack output;
    int[] slots;

    public MultiStackOutput(CompoundTag nbt) {
        this.output = ItemStack.m_41712_((CompoundTag)nbt.m_128469_("stack"));
        this.slots = nbt.m_128465_("slots");
    }

    public MultiStackOutput(ItemStack output, int ... slots) {
        this.output = output;
        this.slots = slots;
    }

    @Override
    public boolean addToInventory(IInputter inventory) {
        for (int slot : this.slots) {
            inventory.addItemIntoSlot(slot, this.output);
            if (!this.output.m_41619_()) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getStack() {
        return this.output;
    }

    @Override
    public void save(CompoundTag save) {
        save.m_128385_("slots", this.slots);
        save.m_128365_("stack", (Tag)this.output.m_41739_(new CompoundTag()));
    }
}

