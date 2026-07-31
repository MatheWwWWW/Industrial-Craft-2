/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.container;

import ic2.core.IC2;
import ic2.core.inventory.slot.IQuickMoveBlocker;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.platform.corehacks.mixins.server.ContainerMixin;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class IC2Container
extends AbstractContainerMenu {
    int extra;

    public IC2Container(int id) {
        super(null, id);
    }

    protected void addExtraSlots(int extra) {
        this.extra += extra;
    }

    public abstract int getInventorySize();

    public boolean moveBackwardsIntoMachine() {
        return false;
    }

    public boolean moveBackwardsIntoInventory() {
        return true;
    }

    public Slot m_38853_(int slotId) {
        try {
            return super.m_38853_(slotId);
        }
        catch (Exception e) {
            IC2.LOGGER.info("Slot Crash: " + ((Object)((Object)this)).getClass());
            return super.m_38853_(slotId);
        }
    }

    public void clearSlots(Class<? extends Slot> filter) {
        NonNullList<ItemStack> references = ((ContainerMixin)((Object)this)).getLastSlots();
        int m = this.f_38839_.size();
        for (int i = 0; i < m; ++i) {
            if (!filter.isInstance(this.m_38853_(i))) continue;
            references.set(i, (Object)ItemStack.f_41583_);
        }
    }

    public void clearSlots() {
        NonNullList<ItemStack> references = ((ContainerMixin)((Object)this)).getLastSlots();
        int m = references.size();
        for (int i = 0; i < m; ++i) {
            references.set(i, (Object)ItemStack.f_41583_);
        }
    }

    public boolean m_5622_(Slot slot) {
        if (slot instanceof SlotBase) {
            ((SlotBase)slot).markQuickCraft();
        }
        return true;
    }

    public ItemStack m_7648_(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.f_41583_;
        Slot slot = (Slot)this.f_38839_.get(index);
        if (slot != null && slot.m_6657_() && !(slot instanceof IQuickMoveBlocker)) {
            ItemStack itemstack1 = slot.m_7993_();
            itemstack = itemstack1.m_41777_();
            if (index < this.getInventorySize() ? !this.m_38903_(itemstack1, this.getInventorySize() + this.extra, this.f_38839_.size(), this.moveBackwardsIntoInventory()) : !this.m_38903_(itemstack1, 0, this.getInventorySize() + this.extra, this.moveBackwardsIntoMachine())) {
                return ItemStack.f_41583_;
            }
            if (itemstack1.m_41619_()) {
                slot.m_5852_(ItemStack.f_41583_);
            } else {
                slot.m_6654_();
            }
            if (itemstack1.m_41613_() == itemstack.m_41613_()) {
                return ItemStack.f_41583_;
            }
            slot.m_142406_(playerIn, itemstack1);
        }
        return itemstack;
    }

    protected boolean m_38903_(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return this.moveItemStackTo(stack, (List<Slot>)this.f_38839_, startIndex, endIndex, reverseDirection);
    }

    protected boolean moveItemStackToPriorizeUpgradeSlots(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        ObjectArrayList targetSlots = new ObjectArrayList(endIndex - startIndex);
        ObjectArrayList normalSlots = new ObjectArrayList(endIndex - startIndex);
        for (int i = startIndex; i < endIndex; ++i) {
            Slot s = (Slot)this.f_38839_.get(i);
            (s instanceof UpgradeSlot ? targetSlots : normalSlots).add(s);
        }
        targetSlots.addAll(normalSlots);
        return this.moveItemStackTo(stack, (List<Slot>)targetSlots, 0, targetSlots.size(), reverseDirection);
    }

    protected boolean moveItemStackTo(ItemStack stack, List<Slot> slots, int startIndex, int endIndex, boolean reverseDirection) {
        int i;
        boolean flag = false;
        int n = i = reverseDirection ? endIndex - 1 : startIndex;
        if (stack.m_41753_()) {
            while (!stack.m_41619_() && !(!reverseDirection ? i >= endIndex : i < startIndex)) {
                Slot slot = slots.get(i);
                ItemStack itemstack = slot.m_7993_();
                if (!itemstack.m_41619_() && slot.m_5857_(stack) && !(slot instanceof IQuickMoveBlocker) && ItemStack.m_150942_((ItemStack)stack, (ItemStack)itemstack)) {
                    int maxSize;
                    int j = itemstack.m_41613_() + stack.m_41613_();
                    if (j <= (maxSize = Math.min(slot.m_5866_(itemstack), stack.m_41741_()))) {
                        stack.m_41764_(0);
                        itemstack.m_41764_(j);
                        slot.m_5852_(itemstack);
                        slot.m_6654_();
                        flag = true;
                    } else if (itemstack.m_41613_() < maxSize) {
                        stack.m_41774_(maxSize - itemstack.m_41613_());
                        itemstack.m_41764_(maxSize);
                        slot.m_5852_(itemstack);
                        slot.m_6654_();
                        flag = true;
                    }
                }
                i += reverseDirection ? -1 : 1;
            }
        }
        if (!stack.m_41619_()) {
            int n2 = i = reverseDirection ? endIndex - 1 : startIndex;
            while (!(!reverseDirection ? i >= endIndex : i < startIndex)) {
                Slot slot1 = slots.get(i);
                ItemStack itemstack1 = slot1.m_7993_();
                if (itemstack1.m_41619_() && slot1.m_5857_(stack) && !(slot1 instanceof IQuickMoveBlocker)) {
                    slot1.m_5852_(stack.m_41620_(Math.min(stack.m_41613_(), slot1.m_5866_(stack))));
                    slot1.m_6654_();
                    flag = true;
                    break;
                }
                i += reverseDirection ? -1 : 1;
            }
        }
        return flag;
    }
}

