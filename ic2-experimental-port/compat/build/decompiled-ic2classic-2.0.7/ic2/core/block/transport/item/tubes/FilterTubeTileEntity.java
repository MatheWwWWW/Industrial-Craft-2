/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.FilterTubeContainer;
import ic2.core.inventory.base.ICamouflageGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.collection.SynchronizedList;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class FilterTubeTileEntity
extends TubeTileEntity
implements ICamouflageGui,
ICopyableSettings {
    public SimpleInventory inventory = new SimpleInventory(1);
    public Object2IntMap<Item> filters = new Object2IntOpenHashMap();
    @NetworkInfo
    public SynchronizedList<FilterEntry> stacks = new SynchronizedList<FilterEntry>(FilterEntry::write, FilterEntry::new);
    @NetworkInfo
    public boolean invPriority = false;
    @NetworkInfo
    public int emptyDirections = 63;

    public FilterTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("stacks", "invPriority", "emptyDirections");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FILTER_TUBE;
    }

    @Override
    public boolean isFoamed() {
        return this.foamed > 0;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FilterTubeContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        ListTag list = new ListTag();
        for (int i = 0; i < this.stacks.size(); ++i) {
            list.add((Object)((FilterEntry)this.stacks.get(i)).write());
        }
        NBTUtils.put(compound, "filters", list);
        NBTUtils.putBoolean(compound, "invPrio", this.invPriority, false);
        compound.m_128405_("emptyDirs", this.emptyDirections);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.invPriority = compound.m_128471_("invPrio");
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("filters", 10), CompoundTag.class)) {
            FilterEntry entry = FilterEntry.read(data);
            if (entry == null) continue;
            this.stacks.add(entry);
            this.filters.put((Object)entry.getStack().m_41720_(), entry.getSides().getCode());
        }
        this.emptyDirections = compound.m_128441_("emptyDirs") ? compound.m_128451_("emptyDirs") : this.generateEmptyDirs();
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        ListTag list = new ListTag();
        for (int i = 0; i < this.stacks.size(); ++i) {
            list.add((Object)((FilterEntry)this.stacks.get(i)).write());
        }
        NBTUtils.put(compound, "filters", list);
        NBTUtils.putBoolean(compound, "invPrio", this.invPriority, false);
        compound.m_128405_("emptyDirs", this.emptyDirections);
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        this.stacks.clear();
        this.filters.clear();
        this.invPriority = compound.m_128471_("invPrio");
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("filters", 10), CompoundTag.class)) {
            FilterEntry entry = FilterEntry.read(data);
            if (entry == null) continue;
            this.stacks.add(entry);
            this.filters.put((Object)entry.getStack().m_41720_(), entry.getSides().getCode());
        }
        this.emptyDirections = compound.m_128441_("emptyDirs") ? compound.m_128451_("emptyDirs") : this.generateEmptyDirs();
        this.updateGuiFields("stacks", "filters", "emptyDirections", "invPriority");
    }

    private int generateEmptyDirs() {
        DirectionList list = DirectionList.ALL;
        ObjectListIterator objectListIterator = this.stacks.iterator();
        while (objectListIterator.hasNext()) {
            FilterEntry entry = (FilterEntry)objectListIterator.next();
            if (!(list = list.remove(entry.getSides())).isEmpty()) continue;
            return 0;
        }
        return list.getCode();
    }

    public DirectionList getEmptyDirs() {
        return DirectionList.ofNumber(this.emptyDirections);
    }

    @Override
    public DirectionList getValidDirections(TransportedItem item) {
        DirectionList list = super.getValidDirections(item);
        if (this.isSimulating() && list.size() > 0) {
            DirectionList filter = list.keep(DirectionList.ofNumber(this.filters.getInt((Object)item.getStack().m_41720_())));
            DirectionList directionList = list = filter.isEmpty() ? list.keep(this.getEmptyDirs()) : filter;
            if (this.invPriority && item.getRequestId() == null && list.keep(this.inventories.getPresentSides()).size() > 0) {
                list = list.keep(this.inventories.getPresentSides());
            }
        }
        return list;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 1) {
            if (value >= 0 && value < this.stacks.size()) {
                FilterEntry entry = (FilterEntry)this.stacks.remove(value);
                this.filters.removeInt((Object)entry.getStack().m_41720_());
                this.updateGuiField("stacks");
            }
        } else if (key == 2) {
            this.invPriority = !this.invPriority;
            this.updateGuiField("invPriority");
        } else if (key == 3) {
            ItemStack stack = this.inventory.getStackInSlot(0);
            if (stack.m_41619_() || this.stacks.size() >= 127) {
                return;
            }
            FilterEntry entry = new FilterEntry(stack, DirectionList.ALL);
            if (this.stacks.indexOf(entry) == -1) {
                this.stacks.add(entry);
                this.inventory.setStackInSlot(0, ItemStack.f_41583_);
                this.filters.put((Object)entry.getStack().m_41720_(), DirectionList.ALL.getCode());
                this.updateGuiField("stacks");
            }
        } else if (key >= 10 && key < 16) {
            if (value >= 0 && value < this.stacks.size()) {
                Direction dir = Direction.m_122376_((int)(key - 10));
                FilterEntry entry = (FilterEntry)this.stacks.get(value);
                entry.flip(dir);
                this.filters.put((Object)entry.getStack().m_41720_(), entry.getSides().getCode());
                this.updateGuiField("stacks");
            } else if (value == -1) {
                this.emptyDirections ^= 1 << Direction.m_122376_((int)(key - 10)).m_122411_();
                this.updateGuiField("emptyDirections");
            }
        }
    }

    public static class FilterEntry {
        ItemStack stack;
        DirectionList sides;

        public FilterEntry(ItemStack stack, DirectionList sides) {
            this.stack = stack;
            this.sides = sides;
        }

        public FilterEntry(IInputBuffer buffer) {
            this.stack = new ItemStack((ItemLike)buffer.readForgeRegistryEntry(ForgeRegistries.ITEMS));
            this.sides = DirectionList.ofNumber(buffer.readByte());
        }

        public static FilterEntry read(CompoundTag data) {
            ItemStack stack = new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation(data.m_128461_("item"))));
            return stack.m_41619_() ? null : new FilterEntry(stack, DirectionList.ofNumber(data.m_128451_("sides")));
        }

        public void write(IOutputBuffer buffer) {
            buffer.writeForgeEntry(this.stack.m_41720_(), ForgeRegistries.ITEMS);
            buffer.writeByte((byte)this.sides.getCode());
        }

        public CompoundTag write() {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128359_("item", ForgeRegistries.ITEMS.getKey((Object)this.stack.m_41720_()).toString());
            nbt.m_128344_("sides", (byte)this.sides.getCode());
            return nbt;
        }

        public boolean equals(Object obj) {
            if (obj instanceof FilterEntry) {
                return this.stack.m_41720_() == ((FilterEntry)obj).getStack().m_41720_();
            }
            return false;
        }

        public ItemStack getStack() {
            return this.stack;
        }

        public DirectionList getSides() {
            return this.sides;
        }

        public boolean flip(Direction dir) {
            boolean result = this.sides.notContains(dir);
            this.sides = this.sides.flip(dir);
            return result;
        }
    }
}

