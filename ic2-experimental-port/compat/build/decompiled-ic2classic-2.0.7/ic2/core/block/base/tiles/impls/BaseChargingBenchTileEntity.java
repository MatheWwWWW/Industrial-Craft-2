/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.AbstractInt2IntMap$BasicEntry
 *  it.unimi.dsi.fastutil.ints.Int2IntMap$Entry
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.storage.container.ChargingBenchContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.utils.collection.Averager;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.ints.AbstractInt2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseChargingBenchTileEntity
extends BaseElectricTileEntity
implements ITileGui,
ITickListener {
    @NetworkInfo
    public boolean sortedMode = false;
    @NetworkInfo
    public Averager averager = new Averager();
    public int state = 0;
    IntList chargeableSlot = new IntArrayList();
    int slots;

    public BaseChargingBenchTileEntity(BlockPos pos, BlockState state, int maxInput, int maxEnergy) {
        super(pos, state, 17, maxInput, maxEnergy);
        this.setFuelSlot(16);
        this.addNetworkFields("sortedMode");
        this.addGuiFields("averager");
        this.redstoneIsFull = false;
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerBlockSides(DirectionList.ALL);
        int[] slots = MathUtils.fromTo(0, 17);
        handler.registerSlotAccess(AccessRule.BOTH, slots);
        handler.registerSlotsForSide(DirectionList.ALL, slots);
        handler.registerNamedSlot(SlotType.CHARGE, MathUtils.fromTo(0, 16));
        for (int i = 0; i < 16; ++i) {
            handler.registerInputFilter(this.createFilter(i), i);
        }
        handler.registerInputFilter(ElectricItemFilter.DISCHARGE_EXTERNAL_FILTER, 16);
        handler.registerNamedSlot(SlotType.BATTERY, 16);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    private int createState() {
        return this.slots <= 0 ? 0 : (this.energy <= 0 && this.chargeableSlot.size() > 0 ? 1 : (this.energy > 0 && this.chargeableSlot.size() > 0 ? 2 : 3));
    }

    public int getChargingSlots() {
        return this.chargeableSlot.size();
    }

    public int getSlots() {
        return this.slots;
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 1;
    }

    public Int2IntMap.Entry getMissingEnergy() {
        long energy = 0L;
        long transfer = 0L;
        for (int i = 0; i < 16; ++i) {
            ItemStack stack = (ItemStack)this.inventory.get(i);
            if (stack.m_41619_()) continue;
            int entry = ElectricItem.MANAGER.charge(stack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, true);
            energy += (long)entry;
            transfer += (long)Math.min(entry, ElectricItem.MANAGER.getTransferLimit(stack));
        }
        return new AbstractInt2IntMap.BasicEntry(energy > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)energy, transfer > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)transfer);
    }

    public int getState() {
        return this.state;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ChargingBenchContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putIntArray(compound, "chargeable", this.chargeableSlot.toIntArray());
        NBTUtils.putByte(compound, "states", (int)((byte)this.state), 0);
        NBTUtils.putInt(compound, "present", this.slots, 0);
        NBTUtils.putBoolean(compound, "sorted", this.sortedMode, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.chargeableSlot.addAll((IntList)IntArrayList.wrap((int[])compound.m_128465_("chargeable")));
        this.state = compound.m_128451_("states");
        this.slots = compound.m_128451_("present");
        this.sortedMode = compound.m_128471_("sorted");
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating() && slot < 16) {
            if (stack.m_41619_()) {
                this.chargeableSlot.rem(slot);
                this.slots &= ~(1 << slot);
                return;
            }
            if (!this.chargeableSlot.contains(slot)) {
                this.chargeableSlot.add(slot);
            }
            this.slots |= 1 << slot;
        }
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount > this.maxEnergy || amount <= 0) {
            return 0;
        }
        return amount - this.addEnergy(amount);
    }

    @Override
    public int addEnergy(int amount) {
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
            this.averager.addValue(added);
            this.updateGuiField("energy");
        }
        return added;
    }

    @Override
    public void onTick() {
        int newState;
        this.handleChargeSlot((int)((float)this.maxEnergy * 0.85f));
        if (this.averager.shouldRun()) {
            this.averager.finished();
        }
        this.updateGuiField("averager");
        if (this.clock(10) && (newState = this.createState()) != this.state) {
            this.state = newState;
            this.onStateChanged();
        }
        if (this.chargeableSlot.size() > 0 && this.energy > 0) {
            int m = this.chargeableSlot.size();
            for (int i = 0; i < m && this.energy > 0; ++i) {
                ItemStack stack = (ItemStack)this.inventory.get(this.chargeableSlot.getInt(i));
                int use = ElectricItem.MANAGER.charge(stack, this.energy, Integer.MAX_VALUE, false, false);
                this.useEnergy(use);
                if (use > 0) continue;
                this.chargeableSlot.removeInt(i--);
                --m;
            }
        }
    }

    @Override
    public void onGuiFieldChanged(Set<String> fields, Player player) {
        super.onGuiFieldChanged(fields, player);
        if (fields.contains("sortedMode")) {
            ((ChargingBenchContainer)this.getContainer(player, ChargingBenchContainer.class)).applySlots(null);
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            this.sortedMode = !this.sortedMode;
            this.updateTileField("sortedMode");
            this.updateGuiField("sortedMode");
            ((ChargingBenchContainer)this.getContainer(entity, ChargingBenchContainer.class)).applySlots(entity);
        } else if (this.sortedMode) {
            switch (key) {
                case 1: {
                    this.swap(0, entity.m_150109_());
                    break;
                }
                case 2: {
                    this.swap(2, entity.m_150109_());
                    break;
                }
                case 3: {
                    this.swap(1, entity.m_150109_());
                }
            }
        }
    }

    public void swap(int rule, Inventory player) {
        for (int i = 0; i < 4; ++i) {
            this.swap(i * 4, 3 - i, (NonNullList<ItemStack>)player.f_35975_, rule);
        }
        this.swap(5, player.f_35977_, (NonNullList<ItemStack>)player.f_35974_, rule);
        this.swap(9, 0, (NonNullList<ItemStack>)player.f_35976_, rule);
    }

    protected void swap(int invSlot, int playerSlot, NonNullList<ItemStack> playerInv, int flag) {
        ItemStack stored = (ItemStack)this.inventory.get(invSlot);
        if (!stored.m_41619_() && flag == 0 || stored.m_41619_() && flag == 1) {
            return;
        }
        ItemStack player = (ItemStack)playerInv.get(playerSlot);
        if (!player.m_41619_() && flag == 1 || player.m_41619_() && flag == 0 || !player.m_41619_() && !ElectricItemFilter.ELECTRIC_ITEM.matches(player)) {
            return;
        }
        this.setStackInSlot(invSlot, player);
        playerInv.set(playerSlot, (Object)stored);
    }

    public IFilter createFilter(int slot) {
        return T -> this.sortedMode && slot % 4 == 0 ? ElectricItemFilter.ELECTRIC_ITEM.matches(T) && T.canEquip(ChargingBenchContainer.VALID_EQUIPMENT_SLOTS[slot / 4], null) : ElectricItemFilter.ELECTRIC_ITEM.matches(T);
    }
}

