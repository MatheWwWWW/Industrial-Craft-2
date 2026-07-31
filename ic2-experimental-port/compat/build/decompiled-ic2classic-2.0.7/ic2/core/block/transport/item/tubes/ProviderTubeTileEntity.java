/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.tiles.tubes.IProviderTube;
import ic2.api.tiles.tubes.ITube;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.ProviderTubeContainer;
import ic2.core.inventory.base.ICamouflageGui;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.collection.SynchronizedList;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import java.util.UUID;
import java.util.function.ObjIntConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ProviderTubeTileEntity
extends TubeTileEntity
implements IProviderTube,
ICamouflageGui,
ICopyableSettings {
    public IHasInventory filterInv = new SimpleInventory(1);
    @NetworkInfo
    public int globalKeepItems;
    @NetworkInfo
    public int keepItems;
    @NetworkInfo
    public boolean compareNBT;
    @NetworkInfo
    public boolean whiteList = true;
    @NetworkInfo
    public int selectedIndex = -1;
    @NetworkInfo
    public SynchronizedList<ProvideEntry> filters = new SynchronizedList<ProvideEntry>(ProvideEntry::write, ProvideEntry::new);
    @NetworkInfo
    public boolean keepMode = false;
    public int priorityDir = 0;
    public Object2IntMap<ItemStack> mappedFilter;
    public int requestLimit = 512;
    public int maxTubeRequest = 512;
    long lastRequest = -1L;
    Object2IntMap<ItemStack> foundItems = null;

    public ProviderTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("globalKeepItems", "keepItems", "compareNBT", "whiteList", "keepMode", "filters");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PROVIDER_TUBE;
    }

    @Override
    public void applySpeed(TransportedItem item) {
    }

    @Override
    public boolean isFoamed() {
        return this.foamed > 0;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ProviderTubeContainer(this, player, windowID);
    }

    @Override
    public int getPrioritySide() {
        return this.priorityDir;
    }

    @Override
    public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        Direction clicked = ITubeBlock.isClickingAt(hit, side, 0.1875f);
        if (player.m_6144_() && IC2.KEYBOARD.isAltKeyDown(player) && clicked != null && clicked != this.getFacing()) {
            this.priorityDir = clicked.ordinal() != this.priorityDir - 1 ? clicked.ordinal() + 1 : 0;
            this.onStateChanged();
            return true;
        }
        return super.doSpecialAction(side, hit, player);
    }

    @Override
    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        Direction clicked;
        if (action == TubeAction.OUTPUT_PRIORITY && (clicked = ITubeBlock.isClickingAt(hit, side, 0.1875f)) != null && clicked != this.getFacing()) {
            this.priorityDir = clicked.ordinal() != this.priorityDir - 1 ? clicked.ordinal() + 1 : 0;
            this.onStateChanged();
            return true;
        }
        return false;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "global_keep", this.globalKeepItems, 0);
        NBTUtils.putBoolean(compound, "compare_nbt", this.compareNBT, false);
        NBTUtils.putBoolean(compound, "white_list", this.whiteList, false);
        NBTUtils.putByte(compound, "priority", this.priorityDir, 0);
        NBTUtils.putBoolean(compound, "keep_mode", this.keepMode, false);
        ListTag list = new ListTag();
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            list.add((Object)((ProvideEntry)this.filters.get(i)).save());
        }
        NBTUtils.put(compound, "filters", list);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.globalKeepItems = compound.m_128451_("global_keep");
        this.compareNBT = compound.m_128471_("compare_nbt");
        this.whiteList = compound.m_128471_("white_list");
        this.priorityDir = compound.m_128451_("priority");
        this.keepMode = compound.m_128471_("keep_mode");
        for (CompoundTag tag : NBTListWrapper.wrap(compound.m_128437_("filters", 10), CompoundTag.class)) {
            ProvideEntry entry = ProvideEntry.read(tag);
            if (entry == null) continue;
            this.filters.add(entry);
        }
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        NBTUtils.putInt(compound, "global_keep", this.globalKeepItems, 0);
        NBTUtils.putBoolean(compound, "compare_nbt", this.compareNBT, false);
        NBTUtils.putBoolean(compound, "white_list", this.whiteList, false);
        NBTUtils.putByte(compound, "priority", this.priorityDir, 0);
        NBTUtils.putBoolean(compound, "keep_mode", this.keepMode, false);
        ListTag list = new ListTag();
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            list.add((Object)((ProvideEntry)this.filters.get(i)).save());
        }
        NBTUtils.put(compound, "filters", list);
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        this.globalKeepItems = compound.m_128451_("global_keep");
        this.compareNBT = compound.m_128471_("compare_nbt");
        this.whiteList = compound.m_128471_("white_list");
        this.priorityDir = compound.m_128451_("priority");
        this.keepMode = compound.m_128471_("keep_mode");
        this.keepItems = 0;
        this.selectedIndex = -1;
        this.filterInv.setStackInSlot(0, ItemStack.f_41583_);
        this.filters.clear();
        for (CompoundTag tag : NBTListWrapper.wrap(compound.m_128437_("filters", 10), CompoundTag.class)) {
            ProvideEntry entry = ProvideEntry.read(tag);
            if (entry == null) continue;
            this.filters.add(entry);
        }
        this.updateGuiFields("globalKeepItems", "keepItems", "compareNBT", "whiteList", "keepMode");
    }

    @Override
    public boolean isRotatable() {
        return true;
    }

    @Override
    public boolean canSetFacingInternal(Direction dir) {
        return dir != this.getFacing() && (this.isRendering() ? DirectionList.getNeighborCapability(this, dir, ForgeCapabilities.ITEM_HANDLER).isPresent() : this.inventories.getHandler(dir) != null);
    }

    @Override
    public boolean canLoseUpdateTick() {
        return false;
    }

    @Override
    public boolean canConnect(ITube other, Direction dir) {
        return super.canConnect(other, dir) && other.getTubeType() == ITube.TubeType.SIMPLE;
    }

    @Override
    public DirectionList getValidDirections(TransportedItem item) {
        DirectionList newList;
        DirectionList list = super.getValidDirections(item).remove(this.getFacing());
        if (this.priorityDir > 0 && (newList = list.keep(DirectionList.ofFacing(Direction.m_122376_((int)(this.priorityDir - 1))))).size() > 0) {
            return newList;
        }
        return list;
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.inventories.getHandler(this.getFacing()) == null && this.inventories.getPresentSides().size() > 0) {
            this.setFacing(this.inventories.getPresentSides().getDefaultFacing());
        }
    }

    @Override
    public void onTubeUpdate() {
        super.onTubeUpdate();
        this.requestLimit = this.maxTubeRequest;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            if (!this.filterInv.getStackInSlot(0).m_41619_()) {
                if (this.selectedIndex != -1) {
                    ((ProvideEntry)this.filters.get(this.selectedIndex)).setData(this.filterInv.getStackInSlot(0).m_41777_(), this.keepItems);
                } else if (this.filters.size() < 18) {
                    this.filters.add(new ProvideEntry(this.filterInv.getStackInSlot(0).m_41777_(), this.keepItems));
                }
                this.keepItems = 0;
                this.selectedIndex = -1;
                this.filterInv.setStackInSlot(0, ItemStack.f_41583_);
                this.updateGuiFields("keepItems", "selectedIndex", "filters");
            }
        } else if (key == 1) {
            this.compareNBT = !this.compareNBT;
            this.updateGuiField("compareNBT");
            this.mappedFilter = null;
        } else if (key == 3) {
            this.whiteList = !this.whiteList;
            this.updateGuiField("whiteList");
        } else if (key == 4) {
            if (value < 0 || value >= this.filters.size()) {
                return;
            }
            this.selectedIndex = value;
            ProvideEntry entry = (ProvideEntry)this.filters.get(value);
            this.keepItems = entry.getAmount();
            this.filterInv.setStackInSlot(0, entry.getStack());
            this.updateGuiFields("selectedIndex", "keepItems");
        } else if (key == 5) {
            if (value < 0 || value >= this.filters.size()) {
                return;
            }
            this.filters.remove(value);
            this.keepItems = 0;
            this.selectedIndex = -1;
            this.filterInv.setStackInSlot(0, ItemStack.f_41583_);
            this.mappedFilter = null;
            this.updateGuiFields("keepItems", "selectedIndex", "filters");
        } else if (key == 6) {
            this.globalKeepItems = Mth.m_14045_((int)value, (int)0, (int)9999);
            this.updateGuiField("globalKeepItems");
        } else if (key == 7) {
            this.keepItems = Mth.m_14045_((int)value, (int)0, (int)9999);
            this.updateGuiField("keepItems");
        } else if (key == 8) {
            this.keepMode = !this.keepMode;
            this.updateGuiField("keepMode");
        }
    }

    @Override
    public long getProviderSource() {
        return this.m_58899_().m_121945_(this.getFacing()).m_121878_();
    }

    @Override
    public void getItemsProvided(ObjIntConsumer<ItemStack> listener) {
        if (this.requestLimit <= 0) {
            return;
        }
        IItemTransporter transporter = TransporterManager.getTransporter(this.inventories.getHandler(this.getFacing()));
        if (transporter == null) {
            return;
        }
        if (this.lastRequest < this.f_58857_.m_46467_() || this.foundItems == null) {
            this.lastRequest = this.f_58857_.m_46467_();
            this.foundItems = transporter.getAllItems(this.getFacing().m_122424_(), this.compareNBT);
        }
        if (this.foundItems.isEmpty()) {
            return;
        }
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.foundItems)) {
            ItemStack stack = (ItemStack)entry.getKey();
            int amount = entry.getIntValue() - this.mapItems().getOrDefault((Object)stack, this.globalKeepItems);
            if (amount <= 0) continue;
            listener.accept(stack, amount);
        }
    }

    @Override
    public int provideItems(ItemStack stack, int amount, DyeColor color, UUID request) {
        ItemStack extracted;
        int found;
        int toRemove;
        if (amount <= 0 || this.requestLimit <= 0 || !this.isValid(stack)) {
            return 0;
        }
        IItemTransporter transporter = TransporterManager.getTransporter(this.inventories.getHandler(this.getFacing()));
        if (transporter == null) {
            return 0;
        }
        if (this.lastRequest < this.f_58857_.m_46467_() || this.foundItems == null) {
            this.lastRequest = this.f_58857_.m_46467_();
            this.foundItems = transporter.getAllItems(this.getFacing().m_122424_(), this.compareNBT);
        }
        if ((toRemove = Math.min(Math.min(found = this.foundItems.getInt((Object)stack) - this.mapItems().getOrDefault((Object)stack, this.globalKeepItems), Math.min(this.requestLimit, amount)), 64)) > 0 && !(extracted = transporter.removeItem(this.compareNBT ? StackFilter.defaultCompare(stack) : new SimpleFilter((ItemLike)stack.m_41720_()), this.getFacing().m_122424_(), toRemove, false)).m_41619_()) {
            stack = StackUtil.copyWithSize(extracted, 1);
            int value = extracted.m_41613_();
            if (found - value > 0) {
                this.foundItems.put((Object)stack, found - value);
            } else {
                this.foundItems.removeInt((Object)stack);
            }
            this.addItem(new TransportedItem(extracted).setColor(color).setRequestId(request).setStartSpeed(15), this.getFacing());
            this.requestLimit -= value;
            return value;
        }
        return 0;
    }

    public boolean isValid(ItemStack stack) {
        if (this.keepMode) {
            return true;
        }
        Object2IntMap<ItemStack> mapped = this.mapItems();
        return mapped.isEmpty() || mapped.containsKey((Object)stack) == this.whiteList;
    }

    public Object2IntMap<ItemStack> mapItems() {
        if (this.mappedFilter == null) {
            this.mappedFilter = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(this.compareNBT));
            int m = this.filters.size();
            for (int i = 0; i < m; ++i) {
                ProvideEntry entry = (ProvideEntry)this.filters.get(i);
                this.mappedFilter.put((Object)entry.getStack(), entry.getAmount());
            }
        }
        return this.mappedFilter;
    }

    public static class ProvideEntry {
        ItemStack stack;
        int keep;

        public ProvideEntry(IInputBuffer buffer) {
            this.stack = buffer.readItemStack();
            this.keep = buffer.readInt();
        }

        public ProvideEntry(ItemStack stack, int amount) {
            this.setData(stack, amount);
        }

        public static ProvideEntry read(CompoundTag nbt) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)nbt);
            return stack.m_41619_() ? null : new ProvideEntry(stack, nbt.m_128451_("amount"));
        }

        public void setData(ItemStack stack, int keep) {
            this.stack = stack;
            this.keep = keep;
        }

        public void write(IOutputBuffer buffer) {
            buffer.writeItemStack(this.stack);
            buffer.writeInt(this.keep);
        }

        public CompoundTag save() {
            CompoundTag nbt = this.stack.m_41739_(new CompoundTag());
            nbt.m_128405_("amount", this.keep);
            return nbt;
        }

        public int getAmount() {
            return this.keep;
        }

        public ItemStack getStack() {
            return this.stack;
        }

        public boolean isValid() {
            return !this.stack.m_41619_();
        }
    }
}

