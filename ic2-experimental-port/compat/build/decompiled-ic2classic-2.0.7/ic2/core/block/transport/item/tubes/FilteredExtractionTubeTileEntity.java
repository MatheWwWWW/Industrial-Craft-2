/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.items.ITagItem;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.tiles.tubes.ITube;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.cables.AdvancedComparatorBlock;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.FilterExtractionTubeContainer;
import ic2.core.inventory.base.ICamouflageGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.ArrayOrFilter;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.InvertedFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.TransferFilter;
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
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class FilteredExtractionTubeTileEntity
extends TubeTileEntity
implements ICamouflageGui,
IRedstoneListener,
ICopyableSettings {
    public static final int[] TRANSFER_RATE = new int[]{1, 2, 4, 8, 16, 24, 32, 48, 64};
    public static final int[] DELAY = new int[]{20, 16, 12, 10, 8, 6, 4, 2, 1};
    public static final int[] KEEP_ITEMS = new int[]{0, 1, 2, 3, 4, 6, 8, 10, 12, 14, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 80, 96, 112, 128, 160, 192, 224, 256, 384, 512, 768, 1024};
    public int priorityDir = 0;
    public boolean specialConnect = false;
    public boolean pulse = false;
    public boolean comparator = false;
    public int pulseState = 0;
    public SimpleInventory filterInventory = new SimpleInventory(1);
    public SimpleInventory upgradeInventory = new SimpleInventory(2);
    protected IItemTransporter transporter = new Handler();
    @NetworkInfo
    public DirectionList extraFacings = DirectionList.EMPTY;
    @NetworkInfo
    public SynchronizedList<FilterEntry> filters = new SynchronizedList<FilterEntry>(FilterEntry::write, FilterEntry::new);
    @NetworkInfo
    public int flags = 4;
    @NetworkInfo
    public int durability = -1;
    @NetworkInfo
    public boolean whitelist = true;
    @NetworkInfo
    public int keepItems;
    @NetworkInfo
    public int selectedIndex = -1;
    IFilter filter = null;
    boolean useCustomFilter;

    public FilteredExtractionTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("facing", "extraFacings");
        this.addGuiFields("filters", "flags", "durability", "whitelist", "keepItems", "selectedIndex");
        this.sensitive = false;
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        super.addDrops(drops);
        this.upgradeInventory.addToDrops(drops);
    }

    @Override
    public boolean isFoamed() {
        return this.foamed > 0;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FilterExtractionTubeContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        ListTag list = new ListTag();
        for (int i = 0; i < this.filters.size(); ++i) {
            list.add((Object)((FilterEntry)this.filters.get(i)).save());
        }
        NBTUtils.put(compound, "filters", list);
        NBTUtils.putBoolean(compound, "whitelist", this.whitelist, false);
        NBTUtils.putByte(compound, "extra", this.extraFacings.getCode(), 0);
        NBTUtils.putBoolean(compound, "sensitive", this.sensitive, false);
        NBTUtils.put(compound, "upgrades", this.upgradeInventory.save(new CompoundTag()));
        NBTUtils.putByte(compound, "prio", this.priorityDir, 0);
        NBTUtils.putBoolean(compound, "special_connected", this.specialConnect, false);
        NBTUtils.putBoolean(compound, "pulse", this.pulse, false);
        NBTUtils.putByte(compound, "pulse_state", this.pulseState, 0);
        NBTUtils.putBoolean(compound, "comparator", this.comparator, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        for (CompoundTag nbt : NBTListWrapper.wrap(compound.m_128437_("filters", 10), CompoundTag.class)) {
            FilterEntry entry = FilterEntry.read(nbt);
            if (entry == null) continue;
            this.filters.add(entry);
        }
        this.whitelist = compound.m_128471_("whitelist");
        this.extraFacings = DirectionList.ofNumber(compound.m_128451_("extra"));
        this.upgradeInventory.load(compound.m_128469_("upgrades"));
        this.sensitive = compound.m_128471_("sensitive");
        this.priorityDir = NBTUtils.getInt(compound, "prio", 0);
        this.specialConnect = compound.m_128471_("special_connected");
        this.pulse = compound.m_128471_("pulse");
        this.pulseState = compound.m_128451_("pulse_state");
        this.comparator = compound.m_128471_("comparator");
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        ListTag list = new ListTag();
        for (int i = 0; i < this.filters.size(); ++i) {
            list.add((Object)((FilterEntry)this.filters.get(i)).save());
        }
        NBTUtils.put(compound, "filters", list);
        NBTUtils.putBoolean(compound, "whitelist", this.whitelist, false);
        NBTUtils.putByte(compound, "extra", this.extraFacings.getCode(), 0);
        NBTUtils.putBoolean(compound, "sensitive", this.sensitive, false);
        NBTUtils.putByte(compound, "prio", this.priorityDir, 0);
        NBTUtils.putBoolean(compound, "special_connected", this.specialConnect, false);
        NBTUtils.putBoolean(compound, "pulse", this.pulse, false);
        NBTUtils.putByte(compound, "pulse_state", this.pulseState, 0);
        NBTUtils.putBoolean(compound, "comparator", this.comparator, false);
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        this.filters.clear();
        for (CompoundTag nbt : NBTListWrapper.wrap(compound.m_128437_("filters", 10), CompoundTag.class)) {
            FilterEntry entry = FilterEntry.read(nbt);
            if (entry == null) continue;
            this.filters.add(entry);
        }
        this.whitelist = compound.m_128471_("whitelist");
        this.extraFacings = DirectionList.ofNumber(compound.m_128451_("extra"));
        this.sensitive = compound.m_128471_("sensitive");
        this.priorityDir = NBTUtils.getInt(compound, "prio", 0);
        this.specialConnect = compound.m_128471_("special_connected");
        this.pulse = compound.m_128471_("pulse");
        this.pulseState = compound.m_128451_("pulse_state");
        this.comparator = compound.m_128471_("comparator");
        this.updateTileFields("extraFacings");
        this.onStateChanged();
        this.notifyChanges(true, DirectionList.ALL);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FILTERED_EXTRACTION_TUBE;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return this.sensitive;
    }

    @Override
    public boolean canConnect(ITube other, Direction dir) {
        return super.canConnect(other, dir) && other.getTubeType() == ITube.TubeType.SIMPLE;
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.foamed <= 0 || player.m_21120_(hand).m_41619_();
    }

    @Override
    public ITube.TubeType getTubeType() {
        return this.specialConnect ? ITube.TubeType.SIMPLE : ITube.TubeType.EXTRACTION;
    }

    @Override
    public int getPrioritySide() {
        return this.priorityDir;
    }

    @Override
    public boolean canLoseUpdateTick() {
        return false;
    }

    @Override
    public void handleRedstone() {
        if (this.comparator) {
            if (this.needsRedstoneUpdate) {
                this.needsRedstoneUpdate = false;
                byte max = 0;
                for (Direction dir : DirectionList.ALL.remove(this.getFacing()).remove(this.extraFacings).remove(DirectionList.ofNumber(this.anchors))) {
                    byte strength = (byte)Mth.m_14045_((int)AdvancedComparatorBlock.getComparatorOutput(this.f_58857_, this.m_58899_(), dir, false, 0), (int)0, (int)15);
                    max = (byte)Math.max(max, strength);
                    this.sidedSignals[dir.m_122411_()] = strength;
                }
                this.signal = max;
                return;
            }
            return;
        }
        super.handleRedstone();
    }

    @Override
    public void onComparatorUpdate(BlockPos from) {
        if (this.comparator) {
            this.needsRedstoneUpdate = true;
        }
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41720_() == Items.f_42451_) {
            Direction dir = ITubeBlock.isClickingAt(hit.m_82450_().m_82546_(Vec3.m_82528_((Vec3i)hit.m_82425_())), side, 0.1875f);
            if (dir != null && dir != this.getFacing() && this.inventories.getHandler(dir) != null) {
                this.extraFacings = this.extraFacings.flip(dir);
                this.updateTileField("extraFacings");
                return true;
            }
        } else if (stack.m_41720_() == Items.f_41978_) {
            boolean bl = this.sensitive = !this.sensitive;
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate(this.sensitive ? "gui.ic2.tube.extraction.redstone.enable" : "gui.ic2.tube.extraction.redstone.disable"), false);
            }
            return true;
        }
        return super.onRightClick(player, hand, side, hit);
    }

    @Override
    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        if (action == TubeAction.EXTRA_EXTRACION_SIDE) {
            Direction dir = ITubeBlock.isClickingAt(hit, side, 0.1875f);
            if (!(dir == null || dir == this.getFacing() || this.inventories.getHandler(dir) == null || this.hasAnchor(dir) || this.priorityDir != 0 && Direction.m_122376_((int)(this.priorityDir - 1)) == dir)) {
                this.extraFacings = this.extraFacings.flip(dir);
                this.updateTileField("extraFacings");
                return true;
            }
        } else {
            if (action == TubeAction.REDSTONE_CONTROL) {
                boolean bl = this.sensitive = !this.sensitive;
                if (this.isSimulating()) {
                    player.m_5661_((Component)this.translate(this.sensitive ? "gui.ic2.tube.extraction.redstone.enable" : "gui.ic2.tube.extraction.redstone.disable"), false);
                }
                return true;
            }
            if (action == TubeAction.COMPARATOR) {
                boolean bl = this.comparator = !this.comparator;
                if (this.isSimulating()) {
                    player.m_5661_((Component)this.translate(this.comparator ? "gui.ic2.tube.extraction.comparator.enable" : "gui.ic2.tube.extraction.comparator.disable"), false);
                }
                return true;
            }
            if (action == TubeAction.OUTPUT_PRIORITY) {
                Direction clicked = ITubeBlock.isClickingAt(hit, side, 0.1875f);
                if (clicked != null && clicked != this.getFacing() && this.extraFacings.notContains(clicked)) {
                    this.priorityDir = clicked.ordinal() != this.priorityDir - 1 ? clicked.ordinal() + 1 : 0;
                    this.onStateChanged();
                    return true;
                }
            } else {
                if (action == TubeAction.SPECIAL_EXTRACTION_CONNECTIVITY) {
                    this.specialConnect = !this.specialConnect;
                    this.tubes.markDirty();
                    if (this.isSimulating()) {
                        player.m_5661_((Component)this.translate(this.specialConnect ? "gui.ic2.tube.extraction.special.enable" : "gui.ic2.tube.extraction.special.disable"), false);
                    }
                    this.self.setActiveDirections(DirectionList.EMPTY);
                    this.self.setActiveDirections(DirectionList.ALL.remove(DirectionList.ofNumber(this.anchors)));
                    this.tubes.invalidateCache();
                    this.onStateChanged();
                    this.notifyChanges(true, DirectionList.ALL);
                    return true;
                }
                if (action == TubeAction.PULSE) {
                    this.pulse = !this.pulse;
                    this.sensitive |= this.pulse;
                    if (this.isSimulating()) {
                        player.m_5661_((Component)this.translate(this.pulse ? "gui.ic2.tube.extraction.pulse.enable" : "gui.ic2.tube.extraction.pulse.disable"), false);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DirectionList getExtraFacings() {
        return this.extraFacings;
    }

    @Override
    public DirectionList getValidDirections(TransportedItem item) {
        DirectionList newList;
        DirectionList list = super.getValidDirections(item).remove(this.getFacing()).remove(this.extraFacings);
        if (this.priorityDir > 0 && (newList = list.keep(DirectionList.ofFacing(Direction.m_122376_((int)(this.priorityDir - 1))))).size() > 0) {
            return newList;
        }
        return list;
    }

    @Override
    public void setFacing(Direction facing) {
        if (facing != this.getFacing()) {
            this.extraFacings = this.extraFacings.replace(facing, this.getFacing());
        }
        super.setFacing(facing);
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.inventories.getHandler(this.getFacing()) == null && this.inventories.getPresentSides().size() > 0) {
            this.setFacing(this.inventories.getPresentSides().getDefaultFacing());
        }
    }

    @Override
    protected void onCachesUpdated() {
        super.onCachesUpdated();
        int code = this.extraFacings.getCode();
        this.extraFacings = this.extraFacings.keep(this.inventories.getPresentSides());
        if (code != this.extraFacings.getCode()) {
            this.onStateChanged();
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("extraFacings")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onTubeUpdate() {
        if (this.isRendering()) {
            return;
        }
        this.handleRedstone();
        this.updatePulseState();
        if (this.clock(DELAY[Math.min(8, this.upgradeInventory.getStackInSlot(0).m_41613_())]) && (!this.sensitive || (this.pulse ? this.pulseState == 1 : this.isRedstonePowered()))) {
            if (this.pulse) {
                this.pulseState = 2;
            }
            IFilter filter = this.getFilter();
            this.handleExtraction(filter, this.getFacing());
            if (this.extraFacings.size() > 0) {
                for (Direction dir : this.extraFacings) {
                    this.handleExtraction(filter, dir);
                }
            }
        }
    }

    private void updatePulseState() {
        if (!this.pulse) {
            return;
        }
        if (this.pulseState == 0 && this.isRedstonePowered()) {
            this.pulseState = 1;
        } else if (this.pulseState == 2 && !this.isRedstonePowered()) {
            this.pulseState = 0;
        }
    }

    public void handleExtraction(IFilter filter, Direction dir) {
        IItemTransporter transporter;
        IItemTransporter iItemTransporter = transporter = this.useCustomFilter ? this.transporter : TransporterManager.getTransporter(this.inventories.getHandler(dir));
        if (transporter != null) {
            this.addItem(transporter.removeItem(filter, dir.m_122424_(), TRANSFER_RATE[Math.min(8, this.upgradeInventory.getStackInSlot(1).m_41613_())], false), dir);
        }
    }

    public IFilter getFilter() {
        if (this.filter == null) {
            ObjectArrayList data = new ObjectArrayList();
            this.useCustomFilter = false;
            for (int i = 0; i < this.filters.size(); ++i) {
                FilterEntry entry = (FilterEntry)this.filters.get(i);
                if (!entry.isValid()) continue;
                data.add(entry.getFilter());
                this.useCustomFilter |= entry.getKeepItems() > 0;
            }
            this.filter = data.isEmpty() ? (this.whitelist ? SpecialFilters.ALWAYS_FALSE : SpecialFilters.ANY_ITEM) : (this.whitelist ? new ArrayOrFilter((Iterable<IFilter>)data) : new InvertedFilter(new ArrayOrFilter((Iterable<IFilter>)data)));
        }
        return this.filter;
    }

    @Override
    public boolean canSetFacingInternal(Direction dir) {
        return dir != this.getFacing() && (this.isRendering() ? DirectionList.getNeighborCapability(this, dir, ForgeCapabilities.ITEM_HANDLER).isPresent() : this.inventories.getHandler(dir) != null);
    }

    @Override
    public boolean isRotatable() {
        return true;
    }

    @Override
    public void onGuiClosed(Player player) {
        if (this.selectedIndex >= 0 && this.selectedIndex < this.filters.size()) {
            if (this.filterInventory.getStackInSlot(0).m_41619_()) {
                if (((FilterEntry)this.filters.get(this.selectedIndex)).getStack().m_41619_()) {
                    this.filters.remove(this.selectedIndex);
                    this.selectedIndex = -1;
                    this.updateGuiFields("filters", "selectedIndex");
                }
            } else {
                ((FilterEntry)this.filters.get(this.selectedIndex)).setData(this.filterInventory.getStackInSlot(0), this.flags, this.durability, this.keepItems);
                this.filter = null;
                this.selectedIndex = -1;
                this.updateGuiFields("filters", "selectedIndex");
            }
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 1) {
            this.flags ^= value;
            if (this.flags == 256) {
                this.durability = 0;
                this.updateGuiField("durability");
            }
            this.updateGuiField("flags");
        } else if (key == 2) {
            this.durability = Mth.m_14045_((int)value, (int)-1, (int)10);
            this.updateGuiField("durability");
        } else if (key == 3) {
            if (!this.filterInventory.getStackInSlot(0).m_41619_()) {
                this.keepItems = Mth.m_14045_((int)value, (int)0, (int)9999);
                this.updateGuiField("keepItems");
            }
        } else if (key == 4) {
            if (this.selectedIndex != -1 && this.selectedIndex != value && this.selectedIndex < this.filters.size()) {
                if (this.filterInventory.getStackInSlot(0).m_41619_()) {
                    if (((FilterEntry)this.filters.get(this.selectedIndex)).getStack().m_41619_()) {
                        this.filters.remove(this.selectedIndex);
                        this.updateGuiField("filters");
                    }
                } else {
                    ((FilterEntry)this.filters.get(this.selectedIndex)).setData(this.filterInventory.getStackInSlot(0), this.flags, this.durability, this.keepItems);
                    this.filter = null;
                    this.updateGuiField("filters");
                }
            }
            if (value != -1 && value < this.filters.size()) {
                this.selectedIndex = value;
                FilterEntry entry = (FilterEntry)this.filters.get(this.selectedIndex);
                this.filterInventory.setStackInSlot(0, entry.getStack());
                this.flags = entry.getFlags();
                this.durability = entry.getDurability();
                this.keepItems = entry.getKeepItems();
            }
            this.updateGuiFields("selectedIndex", "flags", "durability", "keepItems");
        } else if (key == 5) {
            this.whitelist = value == 0;
            this.filter = null;
            this.updateGuiField("whitelist");
        } else if (key == 6) {
            if (!this.filterInventory.getStackInSlot(0).m_41619_()) {
                if (this.selectedIndex != -1) {
                    ((FilterEntry)this.filters.get(this.selectedIndex)).setData(this.filterInventory.getStackInSlot(0), this.flags, this.durability, this.keepItems);
                    this.filter = null;
                    this.filterInventory.setStackInSlot(0, ItemStack.f_41583_);
                    this.flags = 4;
                    this.durability = -1;
                    this.keepItems = 0;
                    this.selectedIndex = -1;
                } else if (this.filters.size() < 16) {
                    this.filters.add(new FilterEntry(this.filterInventory.getStackInSlot(0), this.flags, this.durability, this.keepItems));
                    this.filterInventory.setStackInSlot(0, ItemStack.f_41583_);
                    this.filter = null;
                    this.flags = 4;
                    this.durability = -1;
                    this.keepItems = 0;
                    this.updateGuiField("filters");
                }
                this.updateGuiFields("filters", "selectedIndex", "flags", "durability", "keepItems");
            }
        } else if (key == 7 && value >= 0 && value < this.filters.size()) {
            if (this.selectedIndex == value) {
                this.selectedIndex = -1;
                this.filterInventory.setStackInSlot(0, ItemStack.f_41583_);
                this.flags = 4;
                this.durability = -1;
                this.keepItems = 0;
            } else if (this.selectedIndex != -1 && value < this.selectedIndex) {
                --this.selectedIndex;
            }
            this.filters.remove(value);
            this.filter = null;
            this.updateGuiFields("filters", "flags", "durability", "keepItems", "selectedIndex");
        }
    }

    public class Handler
    implements IItemTransporter {
        Object2IntOpenHashMap<FilterEntry> filterCounter = new Object2IntOpenHashMap();

        @Override
        public int addItem(ItemStack stack, Direction dir, boolean simulate) {
            return 0;
        }

        @Override
        public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
            IItemHandler handler = (IItemHandler)FilteredExtractionTubeTileEntity.this.inventories.getHandler(dir.m_122424_());
            if (amount <= 0 || handler == null || handler.getSlots() <= 0) {
                return ItemStack.f_41583_;
            }
            this.filterCounter.clear();
            ItemStack result = ItemStack.f_41583_;
            int m = handler.getSlots();
            block0: for (int i = 0; i < m; ++i) {
                ItemStack stack = handler.getStackInSlot(i);
                if (stack.m_41619_()) continue;
                for (int j = 0; j < FilteredExtractionTubeTileEntity.this.filters.size(); ++j) {
                    FilterEntry entry = (FilterEntry)FilteredExtractionTubeTileEntity.this.filters.get(j);
                    if (!entry.isValid() || entry.getFilter().matches(stack) != FilteredExtractionTubeTileEntity.this.whitelist || !result.m_41619_() && !StackUtil.isStackEqual(stack, result)) continue;
                    if (entry.getKeepItems() > 0) {
                        int value = this.filterCounter.getInt((Object)entry);
                        int leftOver = stack.m_41613_();
                        if (value < entry.getKeepItems()) {
                            int toRemove = entry.getKeepItems() - value;
                            this.filterCounter.put((Object)entry, value + toRemove);
                            leftOver -= toRemove;
                        }
                        if (leftOver > 0) {
                            if (result.m_41619_()) {
                                result = handler.extractItem(i, Math.min(amount - result.m_41613_(), leftOver), simulate);
                            } else {
                                result.m_41769_(handler.extractItem(i, Math.min(amount - result.m_41613_(), leftOver), simulate).m_41613_());
                            }
                        }
                    } else if (result.m_41619_()) {
                        result = handler.extractItem(i, amount - result.m_41613_(), simulate);
                    } else {
                        result.m_41769_(handler.extractItem(i, amount - result.m_41613_(), simulate).m_41613_());
                    }
                    if (result.m_41613_() < amount) continue block0;
                    return result;
                }
            }
            return result;
        }

        @Override
        public int getInventorySize(Direction dir) {
            IItemHandler handler = (IItemHandler)FilteredExtractionTubeTileEntity.this.inventories.getHandler(FilteredExtractionTubeTileEntity.this.getFacing());
            return handler == null ? 0 : handler.getSlots();
        }

        @Override
        public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
            IItemHandler handler = (IItemHandler)FilteredExtractionTubeTileEntity.this.inventories.getHandler(dir.m_122424_());
            if (handler == null || handler.getSlots() <= 0) {
                return Object2IntMaps.emptyMap();
            }
            int slots = handler.getSlots();
            Object2IntLinkedOpenCustomHashMap items = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(compareNBT));
            for (int i = 0; i < slots; ++i) {
                ItemStack stack = handler.getStackInSlot(i);
                if (stack.m_41619_()) continue;
                items.addTo((Object)StackUtil.copyWithSize(stack, 1), stack.m_41613_());
            }
            return items;
        }

        @Override
        public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
            IItemTransporter.InvResult result = new IItemTransporter.InvResult(compareNBT);
            IItemHandler handler = (IItemHandler)FilteredExtractionTubeTileEntity.this.inventories.getHandler(dir.m_122424_());
            int slots = handler.getSlots();
            if (slots > 0) {
                for (int i = 0; i < slots; ++i) {
                    result.add(handler.getStackInSlot(i), handler.getSlotLimit(i));
                }
            }
            return result;
        }
    }

    public static class FilterEntry {
        ItemStack stack;
        int flags;
        int durability;
        int keep;
        IFilter filter;

        public FilterEntry(IInputBuffer buffer) {
            this.stack = buffer.readItemStack();
            this.keep = buffer.readVarInt();
        }

        public FilterEntry(ItemStack stack, int flags, int durability, int keep) {
            this.setData(stack, flags, durability, keep);
        }

        public static FilterEntry read(CompoundTag nbt) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)nbt);
            return stack.m_41619_() ? null : new FilterEntry(stack, nbt.m_128451_("filter_tag"), nbt.m_128451_("filter_durability"), nbt.m_128451_("filter_keep"));
        }

        public void setData(ItemStack stack, int flags, int durability, int keep) {
            this.stack = stack;
            this.flags = flags | (stack.m_41720_() instanceof ITagItem ? 32 : 0);
            this.durability = durability;
            this.keep = keep;
            this.filter = new TransferFilter(stack, flags, durability);
        }

        public void write(IOutputBuffer buffer) {
            buffer.writeItemStack(this.stack);
            buffer.writeVarInt(this.keep);
        }

        public IFilter getFilter() {
            return this.filter;
        }

        public boolean isValid() {
            return !this.stack.m_41619_();
        }

        public CompoundTag save() {
            CompoundTag nbt = this.stack.m_41739_(new CompoundTag());
            nbt.m_128405_("filter_tag", this.flags);
            nbt.m_128405_("filter_durability", this.durability);
            nbt.m_128405_("filter_keep", this.keep);
            return nbt;
        }

        public int getFlags() {
            return this.flags;
        }

        public int getKeepItems() {
            return this.keep;
        }

        public int getDurability() {
            return this.durability;
        }

        public ItemStack getStack() {
            return this.stack;
        }
    }
}

