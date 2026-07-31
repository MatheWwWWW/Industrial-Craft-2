/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.ContainerListener
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core;

import ic2.core.IC2;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.slot.SlotHologramSlot;
import ic2.core.slot.SlotInvSlot;
import ic2.core.slot.SlotInvSlotReadOnly;
import ic2.core.util.ReflectionUtil;
import ic2.core.util.StackUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class ContainerBase<T extends Container>
extends AbstractContainerMenu {
    protected static final int windowBorder = 8;
    protected static final int slotSize = 16;
    protected static final int slotDistance = 2;
    protected static final int slotSeparator = 4;
    protected static final int hotbarYOffset = -24;
    protected static final int inventoryYOffset = -82;
    private static final Field field_Container_listeners = ReflectionUtil.getField(AbstractContainerMenu.class, "listeners", "field_7765", "f_38848_");
    private final Player player;
    public final T base;

    public ContainerBase(MenuType<?> menuType, int n, Inventory inventory, T t) {
        super(menuType, n);
        this.player = inventory.f_35978_;
        this.base = t;
    }

    protected void addPlayerInventorySlots(Inventory inventory, int n) {
        this.addPlayerInventorySlots(inventory, 178, n);
    }

    protected void addPlayerInventorySlots(Inventory inventory, int n, int n2) {
        int n3;
        int n4 = (n - 162) / 2;
        for (n3 = 0; n3 < 3; ++n3) {
            for (int i = 0; i < 9; ++i) {
                this.m_38897_(new Slot((Container)inventory, i + n3 * 9 + 9, n4 + i * 18, n2 + -82 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 9; ++n3) {
            this.m_38897_(new Slot((Container)inventory, n3, n4 + n3 * 18, n2 + -24));
        }
    }

    public void m_150399_(int n, int n2, ClickType clickType, Player player) {
        Slot slot;
        if (n >= 0 && n < this.f_38839_.size() && (slot = (Slot)this.f_38839_.get(n)) instanceof SlotHologramSlot) {
            ((SlotHologramSlot)slot).slotClick(n2, clickType, player, this);
        } else {
            super.m_150399_(n, n2, clickType, player);
        }
    }

    public final ItemStack m_7648_(Player player, int n) {
        Slot slot = (Slot)this.f_38839_.get(n);
        if (slot != null && slot.m_6657_()) {
            ItemStack itemStack = slot.m_7993_();
            int n2 = StackUtil.getSize(itemStack);
            ItemStack itemStack2 = slot.f_40218_ == player.m_150109_() ? this.handlePlayerSlotShiftClick(player, itemStack) : this.handleGUISlotShiftClick(player, itemStack);
            if (StackUtil.isEmpty(itemStack2) || StackUtil.getSize(itemStack2) != n2) {
                slot.m_5852_(itemStack2);
                slot.m_142406_(player, itemStack);
                if (!player.m_20193_().f_46443_) {
                    this.m_38946_();
                }
            }
        }
        return StackUtil.emptyStack;
    }

    protected ItemStack handlePlayerSlotShiftClick(Player player, ItemStack itemStack) {
        block0: for (int i = 0; i < 4 && !StackUtil.isEmpty(itemStack); ++i) {
            for (Slot slot : this.f_38839_) {
                if (slot.f_40218_ == player.m_150109_() || !ContainerBase.isValidTargetSlot(slot, itemStack, i % 2 == 1, i < 2) || !StackUtil.isEmpty(itemStack = this.transfer(itemStack, slot))) continue;
                continue block0;
            }
        }
        return itemStack;
    }

    protected ItemStack handleGUISlotShiftClick(Player player, ItemStack itemStack) {
        block0: for (int i = 0; i < 2 && !StackUtil.isEmpty(itemStack); ++i) {
            ListIterator listIterator = this.f_38839_.listIterator(this.f_38839_.size());
            while (listIterator.hasPrevious()) {
                Slot slot = (Slot)listIterator.previous();
                if (slot.f_40218_ != player.m_150109_() || !ContainerBase.isValidTargetSlot(slot, itemStack, i == 1, false) || !StackUtil.isEmpty(itemStack = this.transfer(itemStack, slot))) continue;
                continue block0;
            }
        }
        return itemStack;
    }

    protected static final boolean isValidTargetSlot(Slot slot, ItemStack itemStack, boolean bl, boolean bl2) {
        if (slot instanceof SlotInvSlotReadOnly || slot instanceof SlotHologramSlot) {
            return false;
        }
        if (!slot.m_5857_(itemStack)) {
            return false;
        }
        if (!bl && !slot.m_6657_()) {
            return false;
        }
        if (bl2) {
            return slot instanceof SlotInvSlot && ((SlotInvSlot)slot).invSlot.canInput();
        }
        return true;
    }

    public boolean m_6875_(Player player) {
        return this.base.m_6542_(player);
    }

    public void m_38946_() {
        super.m_38946_();
        if (this.base instanceof BlockEntity) {
            for (String object : this.getNetworkedFields()) {
                if (!(this.player instanceof ServerPlayer)) continue;
                IC2.network.get(true).updateTileEntityFieldTo((BlockEntity)this.base, object, (ServerPlayer)this.player);
            }
            if (this.base instanceof Ic2TileEntity) {
                for (TileEntityComponent tileEntityComponent : ((Ic2TileEntity)this.base).getComponents()) {
                    if (!(this.player instanceof ServerPlayer)) continue;
                    tileEntityComponent.onContainerUpdate((ServerPlayer)this.player);
                }
            }
        }
    }

    public List<String> getNetworkedFields() {
        return new ArrayList<String>();
    }

    public final List<ContainerListener> getListeners() {
        try {
            return (List)field_Container_listeners.get((Object)this);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void onContainerEvent(String string) {
    }

    protected final ItemStack transfer(ItemStack itemStack, Slot slot) {
        int n = this.getTransferAmount(itemStack, slot);
        if (n <= 0) {
            return itemStack;
        }
        ItemStack itemStack2 = slot.m_7993_();
        if (StackUtil.isEmpty(itemStack2)) {
            slot.m_5852_(StackUtil.copyWithSize(itemStack, n));
        } else {
            slot.m_5852_(StackUtil.incSize(itemStack2, n));
        }
        itemStack = StackUtil.decSize(itemStack, n);
        return itemStack;
    }

    private int getTransferAmount(ItemStack itemStack, Slot slot) {
        int n = Math.min(slot.f_40218_.m_6893_(), slot.m_6641_());
        n = Math.min(n, itemStack.m_41753_() ? itemStack.m_41741_() : 1);
        ItemStack itemStack2 = slot.m_7993_();
        if (!StackUtil.isEmpty(itemStack2)) {
            if (!StackUtil.checkItemEqualityStrict(itemStack, itemStack2)) {
                return 0;
            }
            n -= StackUtil.getSize(itemStack2);
        }
        n = Math.min(n, StackUtil.getSize(itemStack));
        return n;
    }
}

