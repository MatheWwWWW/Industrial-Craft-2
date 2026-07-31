/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.inv;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class PortableInventory
implements IPortableInventory {
    protected final Player owner;
    protected NonNullList<ItemStack> inventory;
    protected final int id;
    protected final ItemStack stack;
    protected final Slot slot;

    public PortableInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        this.owner = owner;
        this.stack = stack.m_41777_();
        this.slot = slot;
        this.inventory = NonNullList.m_122780_((int)this.getSlotCount(), (Object)ItemStack.f_41583_);
        if (IC2.PLATFORM.isSimulating()) {
            this.id = owner.f_19853_.f_46441_.m_188503_(Short.MAX_VALUE);
            held.setGuiID(stack, this.id);
        } else {
            this.id = -1;
        }
    }

    public int getID() {
        return this.id;
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return true;
    }

    @Override
    public boolean canInteractWith(Player player) {
        return player.m_6084_() && this.findContainer(player);
    }

    private boolean findContainer(Player player) {
        int i;
        if (this.slot != null) {
            return this.isContainer(this.slot.m_7993_());
        }
        Inventory inv = player.m_150109_();
        AbstractContainerMenu menu = player.f_36096_;
        ItemStack stack = menu.m_142621_();
        if (this.isContainer(stack)) {
            return true;
        }
        for (i = 0; i < inv.m_6643_(); ++i) {
            if (!this.isContainer(inv.m_8020_(i))) continue;
            return true;
        }
        for (i = 0; i < this.getSlotCount(); ++i) {
            if (!this.isContainer(this.getStackInSlot(i))) continue;
            return true;
        }
        return false;
    }

    protected ItemStack getOwnerStack(Player player) {
        int i;
        if (this.slot != null && this.isContainer(this.slot.m_7993_())) {
            return this.slot.m_7993_();
        }
        Inventory inv = player.m_150109_();
        AbstractContainerMenu menu = player.f_36096_;
        ItemStack stack = menu.m_142621_();
        if (this.isContainer(stack)) {
            return stack;
        }
        for (i = 0; i < inv.m_6643_(); ++i) {
            if (!this.isContainer(inv.m_8020_(i))) continue;
            return inv.m_8020_(i);
        }
        for (i = 0; i < this.getSlotCount(); ++i) {
            if (!this.isContainer(this.getStackInSlot(i))) continue;
            return this.getStackInSlot(i);
        }
        return this.getInventoryStack();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public Screen createGui(Player player, InteractionHand hand, Direction side, IC2Container container) {
        return new ComponentContainerScreen((ContainerComponent)container);
    }

    @Override
    public void onGuiClosed(Player player) {
        if (IC2.PLATFORM.isSimulating()) {
            if (this.slot != null) {
                ItemStack stack = this.slot.m_7993_();
                if (this.isContainer(stack)) {
                    ((IHasHeldGui)stack.m_41720_()).setGuiID(stack, -1);
                    this.save(this.getNBTData(stack, true));
                    this.onClose(stack);
                }
                return;
            }
            ItemStack stack = this.getInv(player);
            if (!stack.m_41619_()) {
                ((IHasHeldGui)stack.m_41720_()).setGuiID(stack, -1);
                this.save(this.getNBTData(stack, true));
                this.onClose(stack);
            } else {
                ItemStack items = this.getInventoryStack();
                for (int i = 0; i < this.getSlotCount(); ++i) {
                    if (!this.isContainer(this.getStackInSlot(i))) continue;
                    this.setStackInSlot(i, ItemStack.f_41583_);
                    ((IHasHeldGui)items.m_41720_()).setGuiID(items, -1);
                    this.save(this.getNBTData(items, true));
                    this.onClose(items);
                    Block.m_49840_((Level)player.m_20193_(), (BlockPos)player.m_20183_(), (ItemStack)items);
                    return;
                }
            }
        }
    }

    public void onClose(ItemStack data) {
        if (!data.m_41782_()) {
            data.m_41751_(null);
        }
    }

    protected final boolean isContainer(ItemStack stack) {
        return stack.m_41720_() instanceof IHasHeldGui && ((IHasHeldGui)stack.m_41720_()).getGuiId(stack) == this.id;
    }

    public ItemStack getInv(Player player) {
        Inventory inv = player.m_150109_();
        AbstractContainerMenu menu = player.f_36096_;
        ItemStack stack = menu.m_142621_();
        if (this.isContainer(stack)) {
            stack = this.getInventoryStack();
            menu.m_142503_(stack);
            return stack;
        }
        for (int i = 0; i < inv.m_6643_(); ++i) {
            if (!this.isContainer(inv.m_8020_(i))) continue;
            ItemStack invSlot = inv.m_8020_(i);
            inv.m_6836_(i, invSlot);
            return invSlot;
        }
        return ItemStack.f_41583_;
    }

    @Override
    public abstract int getSlotCount();

    @Override
    public ItemStack getStackInSlot(int slot) {
        return (ItemStack)this.inventory.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.inventory.set(slot, (Object)stack);
        if (stack.m_41613_() > this.getMaxStackSize(slot)) {
            stack.m_41764_(this.getMaxStackSize(slot));
        }
        this.markDirty();
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 64;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getInventoryStack() {
        return this.stack;
    }

    protected void markDirty() {
        if (this.slot != null) {
            ItemStack stack = this.slot.m_7993_();
            if (this.isContainer(stack)) {
                this.save(this.getNBTData(stack, true));
            }
            this.slot.m_6654_();
        }
    }

    @Override
    public IPortableInventory load(ItemStack stack) {
        this.load(this.getNBTData(stack, false));
        return this;
    }

    public void load(CompoundTag nbt) {
        NBTUtils.loadItems(nbt, this.inventory);
    }

    public void save(CompoundTag nbt) {
        NBTUtils.saveItems(nbt, this.inventory);
    }

    public CompoundTag getNBTData(ItemStack stack, boolean create) {
        return create ? stack.m_41784_() : StackUtil.getNbtData(stack);
    }
}

