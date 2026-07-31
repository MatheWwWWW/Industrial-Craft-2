/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ByteMap
 *  it.unimi.dsi.fastutil.ints.Int2ByteMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ByteMaps
 *  it.unimi.dsi.fastutil.ints.Int2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ShortMap
 *  it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.inventory.handler;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.BasicInventory;
import ic2.core.inventory.handler.IModularSlot;
import ic2.core.inventory.handler.InternalInventory;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.handler.filter.ISlotFilter;
import ic2.core.inventory.handler.filter.WrapperSlotFilter;
import ic2.core.inventory.handler.slots.ListModularSlot;
import ic2.core.inventory.inv.ListenerInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.item.base.features.IHandlerItem;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.capabilities.IToggleableCapabilityProvider;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.ints.Int2ByteMap;
import it.unimi.dsi.fastutil.ints.Int2ByteMaps;
import it.unimi.dsi.fastutil.ints.Int2ByteOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import java.util.function.IntPredicate;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class InventoryHandler
implements INetworkDataBuffer,
IToggleableCapabilityProvider,
IHandlerItem.IConfigurableInventory {
    IHasInventory owner;
    Int2ObjectMap<SlotType> allSlots = new Int2ObjectLinkedOpenHashMap();
    Object2ObjectMap<SlotType, IntList> namedSlots = CollectionUtils.createMap();
    boolean active = true;
    byte blockSides = 0;
    short blockAccess = 0;
    Int2ShortMap slotAccess = new Int2ShortOpenHashMap();
    IModularSlot[] modularSlots = new IModularSlot[6];
    IntList[] sideToSlot = CollectionUtils.createIntLists(6);
    Int2ByteMap slotToSide = new Int2ByteOpenHashMap();
    Int2ObjectMap<ISlotFilter> inputFilters = new Int2ObjectOpenHashMap();
    Int2ObjectMap<ISlotFilter> outputFilters = new Int2ObjectOpenHashMap();
    LazyOptional<IItemHandler>[] inventories = new LazyOptional[7];
    Int2IntMap defaultSlotValues = new Int2IntOpenHashMap();
    int defaultMachineValues = 0;
    boolean singleItem = false;
    boolean singleStack = false;
    boolean locked = false;
    SimpleInventory inv = new ListenerInventory(5, (K, V) -> this.syncFilters()).setMaxStackSize(1);
    Object2IntMap<Item> mappedItem = new Object2IntLinkedOpenHashMap();

    public InventoryHandler(IHasInventory inv) {
        this.mappedItem.defaultReturnValue(-1);
        this.owner = inv;
    }

    public void registerNamedSlot(SlotType type, int ... slots) {
        IntArrayList list = IntArrayList.wrap((int[])slots);
        this.namedSlots.put((Object)type, (Object)list);
        for (int slot : slots) {
            this.allSlots.put(slot, (Object)type);
        }
    }

    public void registerInvisibleSlot(SlotType type, int ... slots) {
        this.namedSlots.put((Object)type, (Object)IntArrayList.wrap((int[])slots));
    }

    public void registerInputFilter(IFilter filter, int ... slots) {
        this.registerInputFilter(new WrapperSlotFilter(filter), slots);
    }

    public void registerInputFilter(ISlotFilter filter, int ... slots) {
        for (int slot : slots) {
            this.inputFilters.put(slot, (Object)filter);
        }
    }

    public void registerOutputFilter(IFilter filter, int ... slots) {
        this.registerOutputFilter(new WrapperSlotFilter(filter), slots);
    }

    public void registerOutputFilter(ISlotFilter filter, int ... slots) {
        for (int slot : slots) {
            this.outputFilters.put(slot, (Object)filter);
        }
    }

    public void registerBlockSides(DirectionList rotation) {
        this.blockSides = (byte)rotation.getCode();
    }

    public void registerBlockAccess(DirectionList rotation, AccessRule rule) {
        for (Direction dir : rotation) {
            this.blockAccess = this.setValue(dir.m_122411_() * 2, rule.getIndex(), this.blockAccess);
        }
    }

    public void registerCustomSlot(IModularSlot slot) {
        for (Direction dir : DirectionList.ALL) {
            this.modularSlots[dir.m_122411_()] = slot;
            IntListIterator intListIterator = this.sideToSlot[dir.m_122411_()].iterator();
            while (intListIterator.hasNext()) {
                int entry = (Integer)intListIterator.next();
                this.slotToSide.remove(entry);
            }
            this.sideToSlot[dir.m_122411_()].clear();
        }
    }

    public void registerSlotsForSide(DirectionList rotation, int ... slots) {
        IntArrayList toAdd = IntArrayList.wrap((int[])slots);
        for (Direction dir : rotation) {
            if (this.modularSlots[dir.m_122411_()] != null) continue;
            this.sideToSlot[dir.m_122411_()].addAll((IntList)toAdd);
        }
        for (Object i : (Object)slots) {
            this.slotToSide.put((int)i, (byte)DirectionList.ofNumber(this.slotToSide.get((int)i)).add(rotation).getCode());
        }
    }

    public void registerSlotAccess(AccessRule rule, int ... slots) {
        for (int slot : slots) {
            short data = this.slotAccess.get(slot);
            for (Direction dir : DirectionList.ALL) {
                data = this.setValue(dir.m_122411_() * 2, rule.getIndex(), data);
            }
            this.slotAccess.put(slot, data);
        }
    }

    public void setSlotSide(int slot, DirectionList validSides) {
        for (Direction dir : DirectionList.ofNumber(this.slotToSide.get(slot))) {
            this.sideToSlot[dir.m_122411_()].rem(slot);
        }
        this.slotToSide.put(slot, (byte)validSides.getCode());
        for (Direction dir : validSides) {
            this.sideToSlot[dir.m_122411_()].add(slot);
        }
        for (int i = 0; i < this.sideToSlot.length; ++i) {
            this.sideToSlot[i].removeIf((IntPredicate)AntiSlotDuplicator.use());
            this.sideToSlot[i].sort(null);
        }
    }

    public void setSlotAccess(int slot, Direction dir, AccessRule rule) {
        this.slotAccess.put(slot, this.setValue(dir.m_122411_() * 2, rule.getIndex(), this.slotAccess.get(slot)));
    }

    public void setBlockAccess(Direction dir, AccessRule rule) {
        this.blockAccess = this.setValue(dir.m_122411_() * 2, rule.getIndex(), this.blockAccess);
    }

    public void remove() {
        for (int i = 0; i < 7; ++i) {
            LazyOptional<IItemHandler> handler = this.inventories[i];
            if (handler.isPresent()) {
                handler.invalidate();
            }
            this.inventories[i] = null;
        }
    }

    public void validateSlots() {
        for (int i = 0; i < this.sideToSlot.length; ++i) {
            this.sideToSlot[i].removeIf((IntPredicate)AntiSlotDuplicator.use());
            this.sideToSlot[i].sort(null);
        }
        for (Direction dir : DirectionList.ALL) {
            if ((this.blockSides & 1 << dir.m_122411_()) == 0 || !this.active) {
                this.inventories[dir.m_122411_()] = LazyOptional.empty();
                continue;
            }
            if (this.modularSlots[dir.m_122411_()] != null) {
                this.inventories[dir.m_122411_()] = LazyOptional.of(() -> new BasicInventory(this, dir, this.modularSlots[dir.m_122411_()]));
                continue;
            }
            this.inventories[dir.m_122411_()] = LazyOptional.of(() -> new BasicInventory(this, dir, new ListModularSlot(this.sideToSlot[dir.m_122411_()])));
        }
        this.inventories[6] = LazyOptional.of(() -> new InternalInventory(this));
        this.defaultMachineValues = MathUtils.putInt(this.blockAccess, this.blockSides);
        for (Int2ByteMap.Entry entry : this.slotToSide.int2ByteEntrySet()) {
            this.defaultSlotValues.put(entry.getIntKey(), MathUtils.putInt(this.slotAccess.get(entry.getIntKey()), entry.getByteValue()));
        }
    }

    protected void syncInventories() {
        for (Direction dir : DirectionList.ALL) {
            if ((this.blockSides & 1 << dir.m_122411_()) == 0 || !this.active) {
                if (!this.inventories[dir.m_122411_()].isPresent()) continue;
                this.inventories[dir.m_122411_()].invalidate();
                this.inventories[dir.m_122411_()] = LazyOptional.empty();
                continue;
            }
            if (this.inventories[dir.m_122411_()].isPresent()) continue;
            if (this.modularSlots[dir.m_122411_()] != null) {
                this.inventories[dir.m_122411_()] = LazyOptional.of(() -> new BasicInventory(this, dir, this.modularSlots[dir.m_122411_()]));
                continue;
            }
            this.inventories[dir.m_122411_()] = LazyOptional.of(() -> new BasicInventory(this, dir, new ListModularSlot(this.sideToSlot[dir.m_122411_()])));
        }
    }

    protected void syncFilters() {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        this.mappedItem.defaultReturnValue(-1);
        this.mappedItem.clear();
        int m = this.inv.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = this.inv.getStackInSlot(i);
            if (!(stack.m_41720_() instanceof IHandlerItem)) continue;
            ((IHandlerItem)stack.m_41720_()).handleInventory(stack, this);
        }
    }

    @Override
    public void setDefaultMaxStackSize(int max) {
        this.mappedItem.defaultReturnValue(max);
    }

    @Override
    public void setMaxStackSizeForItem(Item item, int max) {
        this.mappedItem.put((Object)item, max);
    }

    public IHasInventory getInventory() {
        return this.owner;
    }

    public IHasInventory getFilters() {
        return this.inv;
    }

    public ISlotFilter getInputFilter(int slot) {
        return (ISlotFilter)this.inputFilters.get(slot);
    }

    ISlotFilter getOutputFilter(int slot) {
        return (ISlotFilter)this.outputFilters.get(slot);
    }

    int getMaxStackSize(Item item) {
        return this.mappedItem.getInt((Object)item);
    }

    public CompoundTag save(CompoundTag nbt) {
        NBTUtils.putBoolean(nbt, "locked", this.locked, false);
        NBTUtils.putByte(nbt, "side", (int)this.blockSides, MathUtils.getShortValue(this.defaultMachineValues));
        NBTUtils.putShort(nbt, "access", (int)this.blockAccess, MathUtils.getShortKey(this.defaultMachineValues));
        NBTUtils.putByte(nbt, "flags", (int)((byte)((this.singleItem ? 1 : 0) | (this.singleStack ? 2 : 0))), 0);
        LongArrayList data = new LongArrayList();
        for (Int2ByteMap.Entry entry : this.slotToSide.int2ByteEntrySet()) {
            int slotData;
            int slot = entry.getIntKey();
            byte sides = entry.getByteValue();
            short access = this.slotAccess.get(slot);
            if (access == MathUtils.getShortKey(slotData = this.defaultSlotValues.get(slot)) && sides == MathUtils.getShortValue(slotData)) continue;
            data.add(MathUtils.putIntoLong(MathUtils.putInt(slot, access), sides));
        }
        NBTUtils.putLongArray(nbt, "slot_data", (LongCollection)data);
        NBTUtils.put(nbt, "inv", this.inv.save(new CompoundTag()));
        return nbt;
    }

    public void load(CompoundTag nbt) {
        if (nbt.m_128456_()) {
            return;
        }
        this.locked = nbt.m_128471_("locked");
        this.blockSides = NBTUtils.getByte(nbt, "side", (byte)MathUtils.getShortValue(this.defaultMachineValues));
        this.blockAccess = NBTUtils.getShort(nbt, "access", (byte)MathUtils.getShortKey(this.defaultMachineValues));
        int flags = nbt.m_128451_("flags");
        this.singleItem = (flags & 1) != 0;
        this.singleStack = (flags & 2) != 0;
        long[] data = nbt.m_128467_("slot_data");
        int m = data.length;
        for (int i = 0; i < m; ++i) {
            int value = MathUtils.getIntKey(data[i]);
            short slot = (short)MathUtils.getShortKey(value);
            if (!this.allSlots.containsKey((int)slot)) continue;
            this.slotAccess.put((int)slot, (short)MathUtils.getShortValue(value));
            this.slotToSide.put((int)slot, (byte)MathUtils.getIntValue(data[i]));
        }
        for (IntList list : this.sideToSlot) {
            list.clear();
        }
        for (Int2ByteMap.Entry entry : Int2ByteMaps.fastIterable((Int2ByteMap)this.slotToSide)) {
            int slot = entry.getIntKey();
            for (Direction side : DirectionList.ofNumber(entry.getByteValue())) {
                if (this.modularSlots[side.m_122411_()] != null) continue;
                this.sideToSlot[side.m_122411_()].add(slot);
            }
        }
        for (int i = 0; i < this.sideToSlot.length; ++i) {
            this.sideToSlot[i].removeIf((IntPredicate)AntiSlotDuplicator.use());
            this.sideToSlot[i].sort(null);
        }
        this.inv.load(nbt.m_128469_("inv"));
        this.syncInventories();
        this.syncFilters();
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte(this.blockSides);
        buffer.writeShort(this.blockAccess);
        buffer.writeByte((byte)((this.singleItem ? 1 : 0) | (this.singleStack ? 2 : 0) | (this.locked ? 4 : 0)));
        buffer.writeShort((short)this.slotToSide.size());
        for (Int2ByteMap.Entry entry : this.slotToSide.int2ByteEntrySet()) {
            buffer.writeShort((short)entry.getIntKey());
            buffer.writeByte(entry.getByteValue());
            buffer.writeShort(this.slotAccess.get(entry.getIntKey()));
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.blockSides = buffer.readByte();
        this.blockAccess = buffer.readShort();
        byte flags = buffer.readByte();
        this.singleItem = (flags & 1) != 0;
        this.singleStack = (flags & 2) != 0;
        this.locked = (flags & 4) != 0;
        int size = buffer.readShort();
        for (int i = 0; i < size; ++i) {
            short slot = buffer.readShort();
            this.slotToSide.put((int)slot, buffer.readByte());
            this.slotAccess.put((int)slot, buffer.readShort());
        }
    }

    public LazyOptional<IItemHandler> getHandler(Direction dir) {
        if (this.owner.getSlotCount() <= 0 || dir != null && (this.blockSides & 1 << dir.m_122411_()) == 0) {
            return LazyOptional.empty();
        }
        LazyOptional<IItemHandler> handler = this.inventories[dir == null ? 6 : dir.m_122411_()];
        return handler == null ? LazyOptional.empty() : handler.cast();
    }

    public boolean containsSlot(Slot slot) {
        if (!(slot instanceof SlotBase)) {
            return false;
        }
        return ((SlotBase)slot).getInventory() == this.owner && this.allSlots.containsKey(slot.getSlotIndex()) && (this.modularSlots[0] == null || !this.modularSlots[0].containsSlot(slot.getSlotIndex()));
    }

    public SlotType getSlotType(int slot) {
        return (SlotType)this.allSlots.get(slot);
    }

    public IntList getAllSlots() {
        return new IntArrayList((IntCollection)this.allSlots.keySet());
    }

    public Object2ObjectMap<SlotType, IntList> getNamedSlots() {
        return Object2ObjectMaps.unmodifiable(this.namedSlots);
    }

    public IntList getAllOfType(SlotType type) {
        IntArrayList list = new IntArrayList();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.namedSlots)) {
            if (!((SlotType)entry.getKey()).isParent(type)) continue;
            list.addAll((IntList)entry.getValue());
        }
        return list;
    }

    public Object2ObjectMap<SlotType, IntList> getSlotsOfType(SlotType type) {
        Object2ObjectLinkedOpenHashMap result = new Object2ObjectLinkedOpenHashMap();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.namedSlots)) {
            if (!((SlotType)entry.getKey()).isParent(type)) continue;
            result.put((Object)((SlotType)entry.getKey()), (Object)((IntList)entry.getValue()));
        }
        return result;
    }

    public AccessRule getSlotAccess(int slot, Direction dir) {
        return AccessRule.byIndex(this.slotAccess.get(slot) >> dir.m_122411_() * 2 & 3);
    }

    public boolean isSlotSideUnlocked(int slot, Direction dir) {
        if (this.modularSlots[dir.m_122411_()] != null) {
            return this.modularSlots[dir.m_122411_()].containsSlot(slot);
        }
        return (this.slotToSide.get(slot) & 1 << dir.m_122411_()) != 0;
    }

    public AccessRule getBlockAccess(Direction dir) {
        return AccessRule.byIndex(this.blockAccess >> dir.m_122411_() * 2 & 3);
    }

    public boolean isBlockSideUnlocked(Direction dir) {
        return (this.blockSides & 1 << dir.m_122411_()) != 0;
    }

    public boolean areAllSidesBlocked() {
        return this.blockSides == 0;
    }

    public void toggleMachineSide(Direction dir) {
        if (this.locked) {
            return;
        }
        this.blockSides = (byte)(this.blockSides ^ 1 << dir.m_122411_());
        this.syncInventories();
    }

    public void toggleMachineAccess(Direction dir) {
        if (this.locked) {
            return;
        }
        this.blockAccess = this.setValue(dir.m_122411_() * 2, AccessRule.byIndex(this.blockAccess >> dir.m_122411_() * 2 & 3).cycleRule(AccessRule.byIndex(MathUtils.getShortKey(this.defaultMachineValues) >> dir.m_122411_() * 2 & 3)).getIndex(), this.blockAccess);
    }

    public void resetMachineData() {
        if (this.locked) {
            return;
        }
        this.blockSides = (byte)MathUtils.getShortValue(this.defaultMachineValues);
        this.blockAccess = (short)MathUtils.getShortKey(this.defaultMachineValues);
    }

    public void toggleSlotSide(int slot, Direction dir) {
        if (this.locked) {
            return;
        }
        this.setSlotSide(slot, DirectionList.ofNumber(this.slotToSide.get(slot) ^ 1 << dir.m_122411_()));
    }

    public void toggleSlotAccess(int slot, Direction dir) {
        if (this.locked) {
            return;
        }
        short data = this.slotAccess.get(slot);
        this.slotAccess.put(slot, this.setValue(dir.m_122411_() * 2, AccessRule.byIndex(data >> dir.m_122411_() * 2 & 3).cycleRule(AccessRule.byIndex(MathUtils.getShortKey(this.defaultSlotValues.get(slot)) >> dir.m_122411_() * 2 & 3)).getIndex(), data));
    }

    public void resetSlotData(int slot) {
        if (this.locked) {
            return;
        }
        int slotData = this.defaultSlotValues.get(slot);
        this.slotAccess.put(slot, (short)MathUtils.getShortKey(slotData));
        this.slotToSide.put(slot, (byte)MathUtils.getShortValue(slotData));
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.getHandler(side).cast() : LazyOptional.empty();
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean value) {
        if (this.active != value) {
            this.active = value;
            this.syncInventories();
        }
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean isSingleItem() {
        return this.singleItem;
    }

    public void setSingleItem(boolean value) {
        if (this.locked) {
            return;
        }
        this.singleItem = value;
    }

    public boolean isSingleStack() {
        return this.singleStack;
    }

    public void setSingleStack(boolean value) {
        if (this.locked) {
            return;
        }
        this.singleStack = value;
    }

    public boolean isMultiStack() {
        return this.isSingleStack() || !this.mappedItem.isEmpty() || this.mappedItem.defaultReturnValue() > -1;
    }

    public boolean isNoDuplicateMode() {
        return this.isMultiStack() || this.isSingleItem();
    }

    private short setValue(int index, int data, int original) {
        return (short)(original & ~(3 << index) | (data & 3) << index);
    }

    static class AntiSlotDuplicator
    implements IntPredicate {
        static final ThreadLocal<AntiSlotDuplicator> LOCALS = ThreadLocal.withInitial(AntiSlotDuplicator::new);
        IntSet set = new IntOpenHashSet();

        AntiSlotDuplicator() {
        }

        public static AntiSlotDuplicator use() {
            AntiSlotDuplicator local = LOCALS.get();
            local.set.clear();
            return local;
        }

        @Override
        public boolean test(int value) {
            return !this.set.add(value);
        }
    }
}

