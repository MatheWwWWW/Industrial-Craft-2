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

public class SimpleStackOutput
implements IStackOutput {
    ItemStack output;
    int slot;

    public SimpleStackOutput(CompoundTag nbt) {
        this.output = ItemStack.m_41712_((CompoundTag)nbt.m_128469_("stack"));
        this.slot = nbt.m_128451_("slot");
    }

    public SimpleStackOutput(ItemStack output, int slot) {
        this.output = output;
        this.slot = slot;
    }

    @Override
    public boolean addToInventory(IInputter inventory) {
        inventory.addItemIntoSlot(this.slot, this.output);
        return this.output.m_41619_();
    }

    @Override
    public ItemStack getStack() {
        return this.output;
    }

    @Override
    public void save(CompoundTag save) {
        save.m_128344_("slot", (byte)this.slot);
        save.m_128365_("stack", (Tag)this.output.m_41739_(new CompoundTag()));
    }
}

