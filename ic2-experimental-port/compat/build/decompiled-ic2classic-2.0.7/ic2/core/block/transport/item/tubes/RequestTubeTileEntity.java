/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.transport.item.tubes;

import com.google.common.collect.Lists;
import ic2.api.items.ITagItem;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.tiles.tubes.IRequestTube;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.RequestTubeContainer;
import ic2.core.block.transport.item.logistic.TubeNet;
import ic2.core.inventory.base.ICamouflageGui;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.inv.ListenerInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.inv.SimulatedIItemHandler;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.collection.SynchronizedList;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class RequestTubeTileEntity
extends TubeTileEntity
implements IRequestTube,
ICamouflageGui,
ICopyableSettings {
    public SimpleInventory filterInv = new ListenerInventory(1, this::onInserted);
    @NetworkInfo
    public boolean nbt;
    @NetworkInfo
    public boolean dump;
    @NetworkInfo
    public int amountRequested;
    @NetworkInfo
    public DyeColor color;
    @NetworkInfo
    public int selectedIndex = -1;
    public boolean redstoneRequest = false;
    public UUID requestId = UUID.randomUUID();
    @NetworkInfo
    public SynchronizedList<RequestEntry> filters = new SynchronizedList<RequestEntry>(RequestEntry::write, RequestEntry::new);
    public Object2IntLinkedOpenCustomHashMap<ItemStack> requested = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(this.nbt));
    public Object2IntLinkedOpenCustomHashMap<ItemStack> awaiting = new Object2IntLinkedOpenCustomHashMap((Hash.Strategy)ItemStackStrategy.INSTANCE);
    public List<ItemStack> toInsert = new LinkedList<ItemStack>();

    public RequestTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("nbt", "dump", "amountRequested", "color", "selectedIndex", "filters");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.REQUEST_TUBE;
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
        return new RequestTubeContainer(this, player, windowID);
    }

    @Override
    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        if (action == TubeAction.REDSTONE_CONTROL) {
            boolean bl = this.redstoneRequest = !this.redstoneRequest;
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate(this.redstoneRequest ? "gui.ic2.tube.extraction.redstone.enable" : "gui.ic2.tube.extraction.redstone.disable"), false);
                TubeNet.INSTANCE.clearRequests(this);
            }
            return true;
        }
        return super.doTubeAction(side, hit, player, action);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putBoolean(compound, "nbt", this.nbt, false);
        NBTUtils.putBoolean(compound, "dump", this.dump, false);
        NBTUtils.putBoolean(compound, "redstone", this.redstoneRequest, false);
        NBTUtils.putEnum(compound, "request_color", this.color);
        compound.m_128362_("request_id", this.requestId);
        ListTag list = new ListTag();
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            list.add((Object)((RequestEntry)this.filters.get(i)).save());
        }
        NBTUtils.put(compound, "requests", list);
        list = new ListTag();
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.awaiting)) {
            CompoundTag data = ((ItemStack)entry.getKey()).m_41739_(new CompoundTag());
            data.m_128405_("awaiting", entry.getIntValue());
            list.add((Object)data);
        }
        NBTUtils.put(compound, "awaiting", list);
        list = new ListTag();
        for (ItemStack entry : this.toInsert) {
            list.add((Object)entry.m_41739_(new CompoundTag()));
        }
        NBTUtils.put(compound, "toInsert", list);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        ItemStack stack;
        super.m_142466_(compound);
        this.nbt = compound.m_128471_("nbt");
        this.dump = compound.m_128471_("dump");
        this.redstoneRequest = compound.m_128471_("redstone");
        if (compound.m_128403_("request_id")) {
            this.requestId = compound.m_128342_("request_id");
        }
        this.color = NBTUtils.getEnum(compound, "request_color", DyeColor.class);
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("requests", 10), CompoundTag.class)) {
            RequestEntry entry = RequestEntry.read(data, this.nbt);
            if (entry == null || !entry.isValid()) continue;
            this.filters.add(entry);
        }
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("awaiting", 10), CompoundTag.class)) {
            stack = ItemStack.m_41712_((CompoundTag)data);
            if (stack.m_41619_()) continue;
            int amount = data.m_128451_("awaiting");
            this.awaiting.addTo((Object)stack, amount);
            this.requested.addTo((Object)stack, amount);
        }
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("toInsert", 10), CompoundTag.class)) {
            stack = ItemStack.m_41712_((CompoundTag)data);
            if (stack.m_41619_()) continue;
            this.toInsert.add(stack);
        }
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        NBTUtils.putBoolean(compound, "nbt", this.nbt, false);
        NBTUtils.putBoolean(compound, "dump", this.dump, false);
        NBTUtils.putBoolean(compound, "redstone", this.redstoneRequest, false);
        NBTUtils.putEnum(compound, "request_color", this.color);
        ListTag list = new ListTag();
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            list.add((Object)((RequestEntry)this.filters.get(i)).save());
        }
        NBTUtils.put(compound, "requests", list);
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        this.nbt = compound.m_128471_("nbt");
        this.dump = compound.m_128471_("dump");
        this.redstoneRequest = compound.m_128471_("redstone");
        this.color = NBTUtils.getEnum(compound, "request_color", DyeColor.class);
        this.filters.clear();
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("requests", 10), CompoundTag.class)) {
            RequestEntry entry = RequestEntry.read(data, this.nbt);
            if (entry == null || !entry.isValid()) continue;
            this.filters.add(entry);
        }
        this.amountRequested = 0;
        this.selectedIndex = -1;
        this.filterInv.setStackInSlot(0, ItemStack.f_41583_);
        this.updateGuiFields("nbt", "dump", "amountRequested", "color", "selectedIndex", "filters");
    }

    public void onInserted(IHasInventory inventory, int slot) {
        if (this.amountRequested > 0) {
            return;
        }
        ItemStack stack = inventory.getStackInSlot(slot);
        if (stack.m_41619_()) {
            return;
        }
        this.amountRequested = stack.m_41741_();
        this.updateGuiField("amountRequested");
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            if (!this.filterInv.getStackInSlot(0).m_41619_()) {
                if (this.selectedIndex != -1) {
                    ((RequestEntry)this.filters.get(this.selectedIndex)).setData(this.filterInv.getStackInSlot(0).m_41777_(), this.amountRequested, this.nbt);
                } else if (this.filters.size() < 18) {
                    this.filters.add(new RequestEntry(this.filterInv.getStackInSlot(0).m_41777_(), this.amountRequested, this.nbt));
                }
                this.amountRequested = 0;
                this.selectedIndex = -1;
                this.filterInv.setStackInSlot(0, ItemStack.f_41583_);
                this.updateGuiFields("amountRequested", "selectedIndex", "filters");
            }
        } else if (key == 1) {
            this.nbt = !this.nbt;
            for (int i = 0; i < this.filters.size(); ++i) {
                ((RequestEntry)this.filters.get(i)).updateNBT(this.nbt);
            }
            this.updateGuiField("nbt");
            this.recreateTodoList();
        } else if (key == 3) {
            this.dump = !this.dump;
            this.updateGuiField("dump");
            this.recreateTodoList();
        } else if (key == 4) {
            this.amountRequested = Mth.m_14045_((int)value, (int)0, (int)1000000);
            this.updateGuiField("amountRequested");
        } else if (key == 5) {
            if (value < 0 || value >= this.filters.size()) {
                return;
            }
            this.selectedIndex = value;
            RequestEntry entry = (RequestEntry)this.filters.get(value);
            this.filterInv.setStackInSlot(0, entry.getStack());
            this.amountRequested = entry.getAmount();
            this.updateGuiFields("selectedIndex", "amountRequested");
        } else if (key == 6) {
            if (value < 0 || value >= this.filters.size()) {
                return;
            }
            this.filters.remove(value);
            this.amountRequested = 0;
            this.selectedIndex = -1;
            this.filterInv.setStackInSlot(0, ItemStack.f_41583_);
            this.updateGuiFields("amountRequested", "selectedIndex", "filters");
        } else if (key == 7) {
            this.color = value == 0 ? null : DyeColor.m_41053_((int)(value - 1));
            this.updateGuiField("color");
        } else if (key == 8) {
            this.awaiting.clear();
            this.requested.clear();
            TubeNet.INSTANCE.clearRequests(this);
            if (this.toInsert.size() > 0) {
                for (ItemStack stack : this.toInsert) {
                    if (entity.m_150109_().m_36054_(stack)) continue;
                    Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)stack);
                }
                this.toInsert.clear();
            }
        }
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
    public DirectionList getValidDirections(TransportedItem item) {
        DirectionList original = super.getValidDirections(item);
        if (this.requestId.equals(item.getRequestId())) {
            DirectionList override = original.keep(DirectionList.ofFacing(this.getFacing()));
            return override.isEmpty() ? original : override;
        }
        return original.remove(this.getFacing());
    }

    @Override
    public void onTubeUpdate() {
        IItemTransporter transporter;
        this.handleRedstone();
        if (!this.toInsert.isEmpty() && this.clock(200) && (transporter = TransporterManager.getTransporter(this.inventories.getHandler(this.getFacing()))) != null) {
            Iterator<ItemStack> iter = this.toInsert.iterator();
            while (iter.hasNext()) {
                ItemStack stack = iter.next();
                stack.m_41774_(transporter.addItem(stack, this.getFacing().m_122424_(), false));
                if (!stack.m_41619_()) continue;
                iter.remove();
            }
        }
    }

    @Override
    public boolean onSideReached(TransportedItem item) {
        if (this.isSimulating() && item.getTransferDirection() == this.getFacing() && this.requestId.equals(item.getRequestId())) {
            ItemStack stack = item.getServerStack();
            int awaited = Math.min(this.awaiting.getInt((Object)stack), stack.m_41613_());
            int requesting = this.awaiting.getInt((Object)stack);
            if (awaited > 0) {
                this.awaiting.merge((Object)stack, awaited, MathUtils.REMOVAL);
            }
            if ((requesting = this.requested.getInt((Object)stack)) > 0) {
                this.requested.merge((Object)stack, awaited, MathUtils.REMOVAL);
            }
        }
        return false;
    }

    @Override
    public boolean onLeftOver(TransportedItem item) {
        this.toInsert.add(item.getServerStack().m_41777_());
        item.invalidate();
        return true;
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.inventories.getHandler(this.getFacing()) == null && this.inventories.getPresentSides().size() > 0) {
            this.setFacing(this.inventories.getPresentSides().getDefaultFacing());
        }
    }

    public void recreateTodoList() {
        if (this.addedToTubeNet) {
            TubeNet.INSTANCE.clearRequests(this);
        }
        this.requested = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(this.nbt));
    }

    @Override
    public UUID getRequestId() {
        return this.requestId;
    }

    public void getMissing(NonNullList<ItemStack> items) {
        if (this.filters.isEmpty()) {
            return;
        }
        IItemHandler handler = (IItemHandler)this.inventories.getHandler(this.getFacing());
        if (handler == null) {
            return;
        }
        IItemTransporter transporter = TransporterManager.getTransporter(SimulatedIItemHandler.createSimulation(handler, this.getFacing(), this.requested));
        if (transporter == null) {
            return;
        }
        Object2IntMap<ItemStack> found = transporter.getAllItems(this.getFacing(), this.nbt);
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            int request;
            RequestEntry entry = (RequestEntry)this.filters.get(i);
            int n = request = this.dump ? Integer.MAX_VALUE : entry.getAmount() - found.getInt((Object)entry.getStack());
            if (request <= 0) continue;
            items.add((Object)StackUtil.copyWithSize(entry.getStack(), request));
        }
    }

    @Override
    public long getRequestSource() {
        return this.m_58899_().m_121945_(this.getFacing()).m_121878_();
    }

    private Set<Item> getBlockedItems() {
        if (this.toInsert.isEmpty()) {
            return Collections.emptySet();
        }
        ObjectSet items = CollectionUtils.createSet();
        items.addAll(Lists.transform(this.toInsert, ItemStack::m_41720_));
        return items;
    }

    @Override
    public void provideRequests(IRequestTube.ITubeRequester requester) {
        if (this.filters.isEmpty() || this.redstoneRequest && !this.isRedstonePowered()) {
            return;
        }
        IItemHandler handler = (IItemHandler)this.inventories.getHandler(this.getFacing());
        if (handler == null) {
            return;
        }
        IItemTransporter transporter = TransporterManager.getTransporter(SimulatedIItemHandler.createSimulation(handler, this.getFacing(), this.requested));
        if (transporter == null) {
            return;
        }
        Object2IntMap<ItemStack> found = transporter.getAllItems(this.getFacing(), this.nbt);
        Set<Item> invalidItems = this.getBlockedItems();
        int m = this.filters.size();
        for (int i = 0; i < m; ++i) {
            int request;
            RequestEntry entry = (RequestEntry)this.filters.get(i);
            int n = request = this.dump ? Integer.MAX_VALUE : entry.getAmount() - found.getInt((Object)entry.getStack());
            if (request <= 0 || invalidItems.contains(entry.getStack().m_41720_()) || (request = requester.validateRequest(entry.getStack(), request, this.color)) <= 0 || (request = transporter.addItem(StackUtil.copyWithSize(entry.getStack(), request), this.getFacing().m_122424_(), false)) <= 0) continue;
            this.requested.addTo((Object)entry.getStack(), request);
            requester.requestItems(entry.getStack(), request, this.color, this.requestId);
        }
    }

    @Override
    public void onRequestsReset() {
        this.requested.clear();
    }

    @Override
    public void onRequestLost(ItemStack stack, int amount) {
        int awaited = Math.min(this.awaiting.getInt((Object)stack), amount);
        int requesting = this.awaiting.getInt((Object)stack);
        if (awaited > 0) {
            this.awaiting.merge((Object)stack, awaited, MathUtils.REMOVAL);
        }
        if ((requesting = this.requested.getInt((Object)stack)) > 0) {
            this.requested.merge((Object)stack, awaited, MathUtils.REMOVAL);
        }
    }

    @Override
    public void onRequestFulfilled(ItemStack stack, int amount) {
        this.awaiting.addTo((Object)stack, amount);
    }

    public static class RequestEntry {
        ItemStack stack;
        int amount;
        IFilter filter;

        public RequestEntry(IInputBuffer buffer) {
            this.stack = buffer.readItemStack();
            this.amount = buffer.readInt();
        }

        public RequestEntry(ItemStack stack, int amount, boolean nbt) {
            this.setData(stack, amount, nbt);
        }

        public static RequestEntry read(CompoundTag nbt, boolean isNBT) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)nbt);
            return stack.m_41619_() ? null : new RequestEntry(stack, nbt.m_128451_("amount"), isNBT);
        }

        public void setData(ItemStack stack, int amount, boolean nbt) {
            this.stack = stack;
            this.amount = amount;
            this.updateNBT(nbt);
        }

        public void updateNBT(boolean nbt) {
            this.filter = new StackFilter(this.stack, 4 | (nbt ? 16 : 0) | (this.stack.m_41720_() instanceof ITagItem ? 32 : 0));
        }

        public void write(IOutputBuffer buffer) {
            buffer.writeItemStack(this.stack);
            buffer.writeInt(this.amount);
        }

        public IFilter getFilter() {
            return this.filter;
        }

        public CompoundTag save() {
            CompoundTag nbt = this.stack.m_41739_(new CompoundTag());
            nbt.m_128405_("amount", this.amount);
            return nbt;
        }

        public int getAmount() {
            return this.amount;
        }

        public ItemStack getStack() {
            return this.stack;
        }

        public boolean isValid() {
            return !this.stack.m_41619_();
        }
    }
}

