/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.block.base.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.DirectionalInventoryComparator;
import ic2.core.block.base.misc.comparator.types.base.InventoryComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.handler.IHasInventoryHandler;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.filter.ISlotFilter;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BaseInventoryTileEntity
extends BaseTileEntity
implements IHasInventory,
IHasInventoryHandler,
IDropProvider {
    public NonNullList<ItemStack> inventory;
    public int inventorySize;
    @NetworkInfo
    InventoryHandler handler = new InventoryHandler(this);

    public BaseInventoryTileEntity(BlockPos pos, BlockState state, int size) {
        super(pos, state);
        this.inventorySize = size;
        this.addGuiFields("handler");
        this.inventory = NonNullList.m_122780_((int)size, (Object)ItemStack.f_41583_);
        this.addSlotInfo(this.handler);
        this.handler.validateSlots();
        if (this.inventorySize > 0) {
            this.addComparator(new InventoryComparator("slots", ComparatorNames.FILLED_SLOTS, this, true));
            this.addComparator(new InventoryComparator("items", ComparatorNames.FILLED_INVENTORY, this, false));
            this.addComparator(new DirectionalInventoryComparator("dir_slots", ComparatorNames.FILLED_DIR_SLOTS, this, true));
            this.addComparator(new DirectionalInventoryComparator("dir_items", ComparatorNames.FILLED_DIR_INVENTORY, this, false));
        }
    }

    protected void addSlotInfo(InventoryHandler handler) {
    }

    @Override
    public InventoryHandler getInventoryHandler() {
        return this.handler;
    }

    @Override
    public boolean allowsUI() {
        return true;
    }

    @Override
    public int getSlotCount() {
        return this.inventorySize;
    }

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
        this.handleNonTickComparators();
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 64;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        InventoryHandler handler = this.getInventoryHandler();
        if (handler == null) {
            return true;
        }
        ISlotFilter filter = handler.getInputFilter(slot);
        return filter == null || filter.matches(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return true;
    }

    protected void setOrGrow(int slot, ItemStack stack, boolean compare) {
        ItemStack inv = (ItemStack)this.inventory.get(slot);
        if (inv.m_41619_()) {
            this.inventory.set(slot, (Object)stack);
        } else if (!compare || StackUtil.isStackEqual(inv, stack)) {
            inv.m_41764_(Mth.m_14045_((int)(stack.m_41613_() + inv.m_41613_()), (int)0, (int)Math.min(stack.m_41741_(), this.getMaxStackSize(slot))));
        }
    }

    protected boolean canPlace(int slot, ItemStack stack) {
        ItemStack inv = (ItemStack)this.inventory.get(slot);
        return inv.m_41619_() || StackUtil.isStackEqual(inv, stack) && StackUtil.getStackSizeLeft(inv) >= stack.m_41613_();
    }

    protected boolean transferStack(int from, int to) {
        int left;
        if (((ItemStack)this.inventory.get(from)).m_41619_()) {
            return false;
        }
        if (((ItemStack)this.inventory.get(to)).m_41619_()) {
            this.inventory.set(to, (Object)((ItemStack)this.inventory.get(from)));
            this.inventory.set(from, (Object)ItemStack.f_41583_);
            this.handleNonTickComparators();
            return true;
        }
        if (StackUtil.isStackEqual((ItemStack)this.inventory.get(to), (ItemStack)this.inventory.get(from)) && (left = StackUtil.getStackSizeLeft((ItemStack)this.inventory.get(to))) > 0) {
            if (left >= ((ItemStack)this.inventory.get(from)).m_41613_()) {
                ((ItemStack)this.inventory.get(to)).m_41769_(((ItemStack)this.inventory.get(from)).m_41613_());
                this.inventory.set(from, (Object)ItemStack.f_41583_);
            } else {
                ((ItemStack)this.inventory.get(to)).m_41769_(left);
                ((ItemStack)this.inventory.get(from)).m_41774_(left);
            }
            this.handleNonTickComparators();
            return true;
        }
        return false;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        NBTUtils.loadItems(compound, this.inventory);
        this.handler.load(compound.m_128469_("handler"));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.saveItems(compound, this.inventory);
        NBTUtils.put(compound, "handler", this.handler.save(new CompoundTag()));
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        for (int i = 0; i < this.inventorySize; ++i) {
            ItemStack stack = (ItemStack)this.inventory.get(i);
            if (stack.m_41619_()) continue;
            drops.add(stack);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER && this.inventorySize > 0) {
            return this.handler.getHandler(side).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.handler.remove();
        super.onUnloaded(chunk);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key >= Short.MAX_VALUE && key <= Short.MAX_VALUE + this.inventorySize + 3) {
            if (key == Short.MAX_VALUE + this.inventorySize + 3) {
                if (value == -1) {
                    this.handler.resetMachineData();
                } else {
                    this.handler.resetSlotData(value);
                }
            } else if (key == Short.MAX_VALUE + this.inventorySize + 2) {
                this.handler.setSingleStack(value == 1);
            } else if (key == Short.MAX_VALUE + this.inventorySize + 1) {
                this.handler.setSingleItem(value == 1);
            } else if (key == Short.MAX_VALUE) {
                if ((value & 1) == 1) {
                    this.handler.toggleMachineSide(Direction.m_122376_((int)(value >>> 1)));
                } else {
                    this.handler.toggleMachineAccess(Direction.m_122376_((int)(value >>> 1)));
                }
            } else {
                int slot = key - Short.MAX_VALUE - 1;
                if ((value & 1) == 1) {
                    this.handler.toggleSlotSide(slot, Direction.m_122376_((int)(value >>> 1)));
                } else {
                    this.handler.toggleSlotAccess(slot, Direction.m_122376_((int)(value >>> 1)));
                }
            }
            this.updateGuiField("handler");
        }
    }
}

