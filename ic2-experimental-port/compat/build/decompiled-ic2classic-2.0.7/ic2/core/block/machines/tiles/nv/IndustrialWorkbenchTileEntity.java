/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.ByteTag
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.HoverEvent$ItemStackInfo
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.event.ForgeEventFactory
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.templates.EmptyFluidHandler
 *  net.minecraftforge.items.IItemHandler
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.core.IC2Classic;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkDataEventListener;
import ic2.api.tiles.tubes.IRequestTube;
import ic2.api.tiles.tubes.ITube;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.IC2;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.machines.containers.nv.IndustrialWorkbenchContainer;
import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.block.machines.logic.crafter.CraftingCache;
import ic2.core.block.machines.logic.crafter.CraftingList;
import ic2.core.block.machines.logic.crafter.IMemorySlotProvider;
import ic2.core.block.machines.logic.crafter.IWorkbenchExpansion;
import ic2.core.block.machines.logic.crafter.Snapshot;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.block.transport.item.logistic.TubeNet;
import ic2.core.fluid.ArrayFluidHandler;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.IngredientNBTFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.IC2CraftingInventory;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.inventory.inv.InventoryWrapper;
import ic2.core.inventory.inv.ListenerInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.inventory.transporter.transporters.special.DirectionalTransporter;
import ic2.core.inventory.transporter.transporters.special.ListTransporter;
import ic2.core.inventory.transporter.transporters.special.NonEmptyTransporter;
import ic2.core.item.misc.MemoryStickItem;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class IndustrialWorkbenchTileEntity
extends BaseInventoryTileEntity
implements INotifyInventory,
ILocation,
ITileGui,
IMemorySlotProvider,
IWrenchableTile,
IDropProvider,
INetworkDataEventListener,
ITube,
IRequestTube,
ITickListener {
    public static final int CRAFT_RECURSIVE = 1;
    public static final int RESTOCK = 2;
    static final CraftingContainer CRAFTING = new IC2CraftingInventory(3, 3);
    List<Recipe<?>> recipesToUnlock = CollectionUtils.createList();
    List<IWorkbenchExpansion> expansions = CollectionUtils.createList();
    public SimpleInventory garbageSlot = new SimpleInventory(9);
    public SimpleInventory memory = new ListenerInventory(2, this);
    public SimpleInventory crafting_inventory = new SimpleInventory(10);
    @NetworkInfo
    public CraftRecipe currentRecipe = new CraftRecipe();
    public CraftingList recipes = new CraftingList(27).setSlotsSaved(0, 9);
    public List<RequestEntry> toRequest = new ObjectArrayList();
    public List<ItemStack> toInsert = new ObjectArrayList();
    public SimpleInventory requestedItem = new SimpleInventory(1);
    public SimpleInventory requestCache = new SimpleInventory(12);
    public Object2IntLinkedOpenCustomHashMap<ItemStack> requested = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(false));
    public Object2IntLinkedOpenCustomHashMap<ItemStack> awaiting = new Object2IntLinkedOpenCustomHashMap((Hash.Strategy)ItemStackStrategy.INSTANCE);
    public MutableBoolean viewOpen = new MutableBoolean();
    public UUID requestId = UUID.randomUUID();
    @NetworkInfo
    public DyeColor requestColor = null;
    protected boolean addedToTubeNet = false;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int flags = 0;
    @NetworkInfo
    public int enabledSlots = 511;
    protected Snapshot snapshot = null;
    ICache<IItemHandler> items = new CapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER);
    ICache<IFluidHandler> fluids = new CapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER);

    public IndustrialWorkbenchTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 27);
        this.addCaches(this.items, this.fluids);
        this.addGuiFields("flags", "enabledSlots", "requestColor");
        this.addCapability(IC2Classic.TUBE_CAPABILITY, this);
    }

    public static int getExpansionLimit() {
        return IC2.CONFIG.industrialWorkbenchExpansionLimit.get();
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        int[] slots = MathUtils.fromTo(0, 27);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotsForSide(DirectionList.ALL, slots);
        handler.registerSlotAccess(AccessRule.BOTH, slots);
        handler.registerNamedSlot(SlotType.STORAGE, slots);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.INDUSTRIAL_WORKBENCH;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new IndustrialWorkbenchContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "garbage", this.garbageSlot.save(new CompoundTag()));
        NBTUtils.put(compound, "memory", this.memory.save(new CompoundTag()));
        NBTUtils.put(compound, "display", this.crafting_inventory.save(new CompoundTag()));
        NBTUtils.put(compound, "current", this.currentRecipe.writeRecipe(new CompoundTag()));
        NBTUtils.put(compound, "recipes", this.recipes.save(new CompoundTag()));
        NBTUtils.putByte(compound, "flags", (int)((byte)this.flags), 0);
        NBTUtils.putEnum(compound, "request_color", this.requestColor);
        compound.m_128362_("request_id", this.requestId);
        ListTag list = new ListTag();
        for (RequestEntry requestEntry : this.toRequest) {
            CompoundTag data = requestEntry.getKey().m_41739_(new CompoundTag());
            data.m_128405_("awaiting", requestEntry.getIntValue());
            list.add((Object)data);
        }
        NBTUtils.put(compound, "awaiting", list);
        list = new ListTag();
        for (ItemStack itemStack : this.toInsert) {
            list.add((Object)itemStack.m_41739_(new CompoundTag()));
        }
        NBTUtils.put(compound, "toInsert", list);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        ItemStack stack;
        super.m_142466_(compound);
        this.garbageSlot.load(compound.m_128469_("garbage"));
        this.memory.load(compound.m_128469_("memory"));
        this.crafting_inventory.load(compound.m_128469_("display"));
        this.currentRecipe.readRecipe(compound.m_128469_("current"));
        this.recipes.load(compound.m_128469_("recipes"));
        this.flags = compound.m_128451_("flags");
        this.requestColor = NBTUtils.getEnum(compound, "request_color", DyeColor.class);
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("awaiting", 10), CompoundTag.class)) {
            stack = ItemStack.m_41712_((CompoundTag)data);
            if (stack.m_41619_()) continue;
            this.toRequest.add(new RequestEntry(stack, data.m_128451_("awaiting")));
        }
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("toInsert", 10), CompoundTag.class)) {
            stack = ItemStack.m_41712_((CompoundTag)data);
            if (stack.m_41619_()) continue;
            this.toInsert.add(stack);
        }
        this.syncRequests();
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        super.addDrops(drops);
        this.memory.addToDrops(drops);
        this.garbageSlot.addToDrops(drops);
    }

    @Override
    public void onDataBufferReceived(Player player, String id, INetworkDataBuffer data, Dist target) {
        if (target.isDedicatedServer() && id.equalsIgnoreCase("jei") && data instanceof NBTBuffer) {
            NBTBuffer buffer = (NBTBuffer)data;
            this.crafting_inventory.clear();
            for (CompoundTag compound : NBTListWrapper.wrap(buffer.getNBT().m_128437_("RecipeData", 10), CompoundTag.class)) {
                int slot = compound.m_128451_("SlotID");
                this.crafting_inventory.setStackInSlot(slot, ItemStack.m_41712_((CompoundTag)compound));
            }
            this.updateRecipe();
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            this.currentRecipe.flags = value;
            this.currentRecipe.clearFilters();
            this.updateGuiField("currentRecipe");
        } else if (key == 1) {
            this.flags = value;
            this.updateGuiField("flags");
        } else if (key == 2) {
            if (value == 0) {
                IndustrialWorkbenchContainer container = (IndustrialWorkbenchContainer)this.getContainer(entity, IndustrialWorkbenchContainer.class);
                if (container == null || !this.currentRecipe.isValid()) {
                    return;
                }
                if (container.isSaving()) {
                    container.m_142503_(ItemStack.f_41583_);
                    container.setSaving(false);
                } else {
                    container.m_142503_(this.currentRecipe.getDisplayItem());
                    container.setSaving(true);
                }
                ((ServerPlayer)entity).f_8906_.m_9829_((Packet)new ClientboundContainerSetSlotPacket(-1, container.m_182425_(), -1, container.m_142621_()));
            } else {
                this.crafting_inventory.clear();
                this.currentRecipe.clear();
                entity.f_36096_.m_38946_();
            }
        } else if (key == 3) {
            this.requestColor = value == 0 ? null : DyeColor.m_41053_((int)(value - 1));
            this.updateGuiField("requestColor");
        } else if (key == 4) {
            if (this.toRequest.size() + this.requested.size() >= 12 || value <= 0 || value > 512 || this.requestedItem.getStackInSlot(0).m_41619_()) {
                return;
            }
            this.toRequest.add(new RequestEntry(StackUtil.copyWithSize(this.requestedItem.getStackInSlot(0), 1), value));
            this.syncRequests();
        } else if (key == 5) {
            this.removeRequest(value);
        } else if (key == 6) {
            this.tryRequestingItems(entity);
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isRendering()) {
            return;
        }
        this.updateSlots();
        this.recipes.validateRecipes(this.m_58904_());
        if (this.currentRecipe.validate(this.m_58904_())) {
            this.crafting_inventory.setStackInSlot(9, this.currentRecipe.getFakeOutput(this.m_58904_()));
        } else {
            this.currentRecipe.clear();
            this.crafting_inventory.setStackInSlot(9, ItemStack.f_41583_);
        }
        AABBUtil.countBlocks(this.f_58857_, this.f_58858_, IndustrialWorkbenchTileEntity.getExpansionLimit(), this.createFilter(), 0, DirectionList.ALL);
        if (!this.addedToTubeNet) {
            this.addedToTubeNet = true;
            TubeNet.INSTANCE.addTube(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToTubeNet) {
            this.addedToTubeNet = false;
            TubeNet.INSTANCE.removeTube(this);
        }
        super.onUnloaded(chunk);
    }

    public void addExpansion(IWorkbenchExpansion expansion) {
        if (this.expansions.size() < IndustrialWorkbenchTileEntity.getExpansionLimit() && expansion.addUnloadListener(this)) {
            this.expansions.add(expansion);
        }
    }

    public void onExpansionUnloaded(IWorkbenchExpansion expansion) {
        this.expansions.remove(expansion);
    }

    protected AABBUtil.IBlockFilter createFilter() {
        return new AABBUtil.IBlockFilter(){

            @Override
            public boolean isValid(LevelReader world, BlockPos pos) {
                BlockEntity tile = world.m_7702_(pos);
                if (tile instanceof IWorkbenchExpansion) {
                    IndustrialWorkbenchTileEntity.this.addExpansion((IWorkbenchExpansion)tile);
                    return IndustrialWorkbenchTileEntity.this.expansions.size() < IndustrialWorkbenchTileEntity.getExpansionLimit();
                }
                return false;
            }

            @Override
            public boolean isValid(BlockState state) {
                return false;
            }
        };
    }

    public void createSnapshot() {
        if (this.snapshot != null) {
            throw new IllegalStateException("Snapshot already present");
        }
        this.snapshot = new Snapshot(2);
        this.snapshot.set(0, SimpleInventory.createCopy(this));
        this.snapshot.set(1, SimpleInventory.createCopy(this.garbageSlot));
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            this.expansions.get(i).createSnapshot();
        }
    }

    public void releaseSnapshot() {
        if (this.snapshot == null) {
            throw new IllegalStateException("Snapshot is missing");
        }
        this.snapshot.get(0, SimpleInventory.class).overrideTo(this);
        this.snapshot.get(1, SimpleInventory.class).overrideTo(this.garbageSlot);
        this.snapshot = null;
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            this.expansions.get(i).releaseSnapshot();
        }
    }

    public void destroySnapshot() {
        if (this.snapshot == null) {
            throw new IllegalStateException("Snapshot is missing");
        }
        this.snapshot = null;
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            this.expansions.get(i).destroySnapshot();
        }
    }

    public Snapshot[] takeSnapshot() {
        if (this.snapshot == null) {
            return null;
        }
        Snapshot shot = this.snapshot;
        this.snapshot = null;
        Snapshot[] data = new Snapshot[1 + this.expansions.size()];
        data[0] = shot;
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            data[1 + i] = this.expansions.get(i).takeSnapshot();
        }
        return data;
    }

    public void putSnapshot(Snapshot[] snapshot) {
        if (this.snapshot != null) {
            throw new IllegalStateException("Snapshot already present");
        }
        this.snapshot = snapshot[0];
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            this.expansions.get(i).setSnapshot(snapshot[1 + i]);
        }
    }

    public IItemTransporter getTransporter() {
        ObjectList transporter = CollectionUtils.createList();
        transporter.add((IItemTransporter)TransporterManager.getTransporter(this.garbageSlot));
        ObjectList helper = CollectionUtils.createList();
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            this.expansions.get(i).createTrashTransporter(false, (List<IItemTransporter>)helper, (List<IItemTransporter>)transporter);
        }
        for (Direction dir : this.items) {
            IItemHandler handler = this.items.getHandler(dir);
            if (handler == null) continue;
            transporter.add((DirectionalTransporter)new DirectionalTransporter(TransporterManager.getTransporter(handler), dir.m_122424_()));
        }
        return new ListTransporter((List<IItemTransporter>)transporter);
    }

    public IFluidHandler getFluids() {
        ObjectList list = CollectionUtils.createList();
        for (Direction dir : this.fluids) {
            IFluidHandler handler = this.fluids.getHandler(dir);
            if (handler == null) continue;
            list.add((IFluidHandler)handler);
        }
        return list.isEmpty() ? EmptyFluidHandler.INSTANCE : new ArrayFluidHandler((List<IFluidHandler>)list);
    }

    public void triggerRestock(Player player) {
        int i;
        if (this.snapshot == null) {
            return;
        }
        if ((this.flags & 2) == 0) {
            this.destroySnapshot();
            return;
        }
        IItemTransporter transporter = this.getTransporter();
        IFluidHandler handler = this.getFluids();
        NonNullList<ItemStack> toAdd = IWorkbenchExpansion.restockInventory(transporter, this, this.snapshot.get(0, SimpleInventory.class).getDifference(this, true));
        IItemTransporter trans = TransporterManager.getTransporter(new InventoryWrapper(this));
        int m = toAdd.size();
        for (i = 0; i < m; ++i) {
            ItemStack stack = (ItemStack)toAdd.get(i);
            stack.m_41774_(trans.addItem(stack, null, false));
            StackUtil.addOrDrop(player, stack);
        }
        m = this.expansions.size();
        for (i = 0; i < m; ++i) {
            this.expansions.get(i).triggerRestock(transporter, handler, player);
        }
        this.destroySnapshot();
    }

    public boolean saveRecipe(int index) {
        if ((this.enabledSlots & 1 << index) == 0) {
            return false;
        }
        CraftRecipe recipe = this.currentRecipe.copy();
        if (recipe.validate(this.m_58904_())) {
            ItemStack stack;
            this.recipes.saveRecipe(index, recipe);
            if (index >= 9 && (stack = this.memory.getStackInSlot((index - 9) / 9)).m_41720_() instanceof MemoryStickItem) {
                MemoryStickItem.saveRecipe(stack, index % 9, recipe);
            }
            return true;
        }
        return false;
    }

    public void deleteRecipe(int index) {
        ItemStack stack;
        this.recipes.removeRecipe(index);
        if (index >= 9 && (stack = this.memory.getStackInSlot((index - 9) / 9)).m_41720_() instanceof MemoryStickItem) {
            MemoryStickItem.deleteRecipe(stack, index % 9);
        }
    }

    public void setRecipe(int index) {
        this.setRecipe(this.recipes.getCopy(index));
    }

    public void setRecipe(CraftRecipe recipe) {
        if (recipe == null || !recipe.validate(this.f_58857_)) {
            this.currentRecipe.clear();
            this.crafting_inventory.clear();
            this.updateGuiField("currentRecipe");
            return;
        }
        this.currentRecipe = recipe;
        this.currentRecipe.applyTo(this.crafting_inventory, this.m_58904_());
        this.updateGuiField("currentRecipe");
    }

    @Override
    public void onNotify(IHasInventory inventory, int slot) {
        if (inventory != this.memory || this.isRendering()) {
            return;
        }
        ItemStack stack = inventory.getStackInSlot(slot);
        if (stack.m_41619_()) {
            for (int i = 0; i < 9; ++i) {
                int index = 9 + slot * 9 + i;
                this.enabledSlots &= ~(1 << index);
                this.recipes.removeRecipe(index);
            }
        } else if (stack.m_41720_() instanceof MemoryStickItem) {
            for (int i = 0; i < 9; ++i) {
                int index = 9 + slot * 9 + i;
                this.enabledSlots |= 1 << index;
                CraftRecipe recipe = MemoryStickItem.loadRecipe(stack, i);
                if (recipe != null && recipe.validate(this.m_58904_())) {
                    this.recipes.saveRecipe(index, recipe);
                    continue;
                }
                this.recipes.removeRecipe(index);
            }
        }
        this.updateGuiField("enabledSlots");
    }

    public void updateSlots() {
        this.onNotify(this.memory, 0);
        this.onNotify(this.memory, 1);
        for (int i = 0; i < 2; ++i) {
            ItemStack stick = this.memory.getStackInSlot(i);
            if (stick.m_41720_() instanceof MemoryStickItem) {
                this.enabledSlots |= 511 << 9 * (i + 1);
                continue;
            }
            this.enabledSlots &= ~(511 << 9 * (i + 1));
        }
        this.updateGuiField("enabledSlots");
    }

    public void updateRecipe() {
        for (int i = 0; i < 9; ++i) {
            CRAFTING.m_6836_(i, this.crafting_inventory.getStackInSlot(i).m_41777_());
        }
        CraftingRecipe recipe = IC2.PLATFORM.getRecipes().m_44015_(RecipeType.f_44107_, (Container)CRAFTING, this.f_58857_).orElse(null);
        if (recipe != null) {
            this.currentRecipe.setRecipe(recipe, this.crafting_inventory);
            this.crafting_inventory.setStackInSlot(9, this.currentRecipe.getOutput(CRAFTING, this.m_58904_()));
        } else {
            this.currentRecipe.clear();
            this.crafting_inventory.setStackInSlot(9, ItemStack.f_41583_);
        }
        CRAFTING.m_6211_();
    }

    protected List<IWorkbenchExpansion> createExpansions() {
        ObjectList expand = CollectionUtils.createList();
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            IWorkbenchExpansion work = this.expansions.get(i);
            if (!work.needsRecipeInfo()) continue;
            expand.add((IWorkbenchExpansion)work);
        }
        return expand;
    }

    protected List<CraftingList> createRecipes() {
        ObjectList expand = CollectionUtils.createList();
        expand.add((CraftingList)this.recipes);
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            CraftingList recipe = this.expansions.get(i).getExpansionRecipeList();
            if (recipe == null) continue;
            expand.add((CraftingList)recipe);
        }
        return expand;
    }

    protected CraftingCache createCache(boolean simulate) {
        List<K>[] transporters = CollectionUtils.createLists(4);
        if (simulate) {
            SimpleInventory inventory = SimpleInventory.createCopy(this);
            SimpleInventory trash = SimpleInventory.createCopy(this.garbageSlot);
            transporters[0].add(new NonEmptyTransporter(inventory));
            transporters[1].add(TransporterManager.getTransporter(inventory));
            IItemTransporter transporter = TransporterManager.getTransporter(trash);
            transporters[2].add(transporter);
            transporters[3].add(transporter);
        } else {
            transporters[0].add(new NonEmptyTransporter(this));
            transporters[1].add(TransporterManager.getTransporter(new InventoryWrapper(this)));
            IItemTransporter transporter = TransporterManager.getTransporter(this.garbageSlot);
            transporters[2].add(transporter);
            transporters[3].add(transporter);
        }
        int m = this.expansions.size();
        for (int i = 0; i < m; ++i) {
            IWorkbenchExpansion work = this.expansions.get(i);
            work.createMainTransporter(simulate, transporters[0], transporters[1]);
            work.createTrashTransporter(simulate, transporters[2], transporters[3]);
        }
        return new CraftingCache(new ListTransporter(new ListTransporter(transporters[0]), new ListTransporter(transporters[2])), new ListTransporter(new ListTransporter(transporters[3]), new ListTransporter(transporters[1])), this.createRecipes(), this.createExpansions());
    }

    public int getCraftLimit() {
        if (!this.currentRecipe.isValid()) {
            return 0;
        }
        int max = this.currentRecipe.getDisplayItem().m_41741_();
        return max == 1 ? 64 : max;
    }

    public boolean canCraft(Player player) {
        if (this.currentRecipe.isValid()) {
            return !this.tryCraft(player, this.currentRecipe, this.createCache(true), false, true).m_41619_();
        }
        return false;
    }

    public ItemStack craft(Player player) {
        if (this.currentRecipe.isValid()) {
            return this.tryCraft(player, this.currentRecipe, this.createCache(false), true, false);
        }
        return ItemStack.f_41583_;
    }

    public ItemStack tryCraft(Player player, CraftRecipe recipe, CraftingCache craftingData, boolean doCraft, boolean doLog) {
        int i;
        ItemStack stack;
        MutableInt craftsLeft = new MutableInt(512);
        SimpleInventory tempJunkJard = new SimpleInventory(15);
        CraftingCache actualCache = craftingData.createSubCache(tempJunkJard);
        NonNullList craftItems = NonNullList.m_122780_((int)9, (Object)ItemStack.f_41583_);
        int[] toConsume = new int[9];
        for (Object2ObjectMap.Entry items : Object2ObjectMaps.fastIterable(recipe.getFilters())) {
            CraftRecipe superRecipe;
            int[] stackInfo = (int[])items.getValue();
            actualCache.setActiveRecipe(recipe, stackInfo[0]);
            ItemStack provided = actualCache.getProvider().removeItem((IFilter)items.getKey(), null, stackInfo[1], false);
            actualCache.clearActiveRecipe();
            this.merge(recipe.getToConsume(), toConsume);
            if (provided.m_41619_()) {
                if ((this.flags & 1) == 0) {
                    if (doLog) {
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.subcrafting_disabled"), false);
                    }
                    craftingData.setMissingItem((IFilter)items.getKey(), stackInfo[1]);
                    return ItemStack.f_41583_;
                }
                superRecipe = craftingData.getRecipeFromFilter((IFilter)items.getKey());
                if (superRecipe == null) {
                    if (doLog) {
                        stack = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.no_recipe", new Object[]{stack.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack)))), false);
                    }
                    craftingData.setMissingItem((IFilter)items.getKey(), stackInfo[1]);
                    return ItemStack.f_41583_;
                }
                provided = this.trySubCraft(player, superRecipe, craftingData, 6, craftsLeft, doCraft, doLog);
                if (provided.m_41619_()) {
                    if (doLog) {
                        stack = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.missing_materials", new Object[]{stack.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack)))), false);
                    }
                    craftingData.setMissingItem((IFilter)items.getKey(), stackInfo[1]);
                    return ItemStack.f_41583_;
                }
                this.recipesToUnlock.add(superRecipe.getRecipe());
            }
            while (provided.m_41613_() < stackInfo[1]) {
                if ((this.flags & 1) == 0) {
                    if (doLog) {
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.subcrafting_disabled"), false);
                    }
                    craftingData.setMissingItem((IFilter)items.getKey(), stackInfo[1] - provided.m_41613_());
                    return ItemStack.f_41583_;
                }
                superRecipe = craftingData.getRecipeFromFilter((IFilter)items.getKey());
                if (superRecipe == null) {
                    if (doLog) {
                        stack = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.no_recipe", new Object[]{stack.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack)))), false);
                    }
                    craftingData.setMissingItem((IFilter)items.getKey(), stackInfo[1] - provided.m_41613_());
                    return ItemStack.f_41583_;
                }
                ItemStack crafted = this.trySubCraft(player, superRecipe, craftingData, 6, craftsLeft, doCraft, doLog);
                if (crafted.m_41619_()) {
                    if (doLog) {
                        ItemStack stack2 = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.missing_materials", new Object[]{stack2.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack2)))), false);
                    }
                    craftingData.setMissingItem((IFilter)items.getKey(), stackInfo[1] - provided.m_41613_());
                    return ItemStack.f_41583_;
                }
                provided.m_41769_(crafted.m_41613_());
                this.recipesToUnlock.add(superRecipe.getRecipe());
            }
            if (provided.m_41613_() > stackInfo[1]) {
                actualCache.getOutput().addItem(provided.m_41620_(provided.m_41613_() - stackInfo[1]), null, false);
            }
            craftItems.set(stackInfo[0], (Object)provided);
        }
        CRAFTING.m_6211_();
        for (int i2 = 0; i2 < 9; ++i2) {
            CRAFTING.m_6836_(i2, ((ItemStack)craftItems.get(i2)).m_41777_());
        }
        ItemStack output = recipe.getOutput(CRAFTING, this.f_58857_);
        if (output.m_41619_()) {
            if (doLog) {
                player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.no_output", new Object[]{recipe.getDisplayItem().m_41786_()}), false);
            }
            return ItemStack.f_41583_;
        }
        if (doCraft) {
            ForgeEventFactory.firePlayerCraftingEvent((Player)player, (ItemStack)output, (Container)CRAFTING);
            this.recipesToUnlock.add(recipe.getRecipe());
            player.m_7281_(this.recipesToUnlock);
            this.recipesToUnlock.clear();
        }
        ForgeHooks.setCraftingPlayer((Player)player);
        NonNullList leftovers = NonNullList.m_122779_();
        NonNullList<ItemStack> remaining = recipe.getRemainingItems(CRAFTING, this.f_58857_);
        ForgeHooks.setCraftingPlayer(null);
        for (i = 0; i < 9; ++i) {
            ItemStack subStack = CRAFTING.m_8016_(i);
            if (!subStack.m_41619_()) {
                subStack.m_41774_(1);
                if (!subStack.m_41619_()) {
                    leftovers.add((Object)subStack);
                }
            }
            subStack = (ItemStack)remaining.get(i);
            if (toConsume[i] > 0) {
                subStack.m_41774_(toConsume[i]);
            }
            if (subStack.m_41619_()) continue;
            leftovers.add((Object)subStack);
        }
        int m = tempJunkJard.getSlotCount();
        for (i = 0; i < m; ++i) {
            stack = tempJunkJard.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            leftovers.add((Object)stack);
        }
        m = leftovers.size();
        for (i = 0; i < m; ++i) {
            stack = (ItemStack)leftovers.get(i);
            if (stack.m_41619_() || craftingData.getOutput().addItem(stack, null, false) >= stack.m_41613_()) continue;
            if (doLog) {
                player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.junkyard_clearing_error"), false);
            }
            craftingData.setMissingItem(null, 0);
            return ItemStack.f_41583_;
        }
        return output;
    }

    public ItemStack trySubCraft(Player player, CraftRecipe recipe, CraftingCache cache, int layer, MutableInt crafts, boolean doCraft, boolean doLog) {
        int i;
        ItemStack stack;
        if (layer <= 0) {
            if (doLog) {
                player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.to_complex"), false);
            }
            return ItemStack.f_41583_;
        }
        if (crafts.decrementAndGet() < 0) {
            if (doLog) {
                player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.to_many_crafts"), false);
            }
            return ItemStack.f_41583_;
        }
        int[] toConsume = new int[9];
        NonNullList craftItems = NonNullList.m_122780_((int)9, (Object)ItemStack.f_41583_);
        for (Object2ObjectMap.Entry items : Object2ObjectMaps.fastIterable(recipe.getFilters())) {
            CraftRecipe superRecipe;
            int[] stackInfo = (int[])items.getValue();
            cache.setActiveRecipe(recipe, stackInfo[0]);
            ItemStack provided = cache.getProvider().removeItem((IFilter)items.getKey(), null, stackInfo[1], false);
            cache.clearActiveRecipe();
            this.merge(recipe.getToConsume(), toConsume);
            if (provided.m_41619_()) {
                superRecipe = cache.getRecipeFromFilter((IFilter)items.getKey());
                if (superRecipe == null) {
                    if (doLog) {
                        stack = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.no_recipe", new Object[]{stack.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack)))), false);
                    }
                    cache.setMissingItem((IFilter)items.getKey(), stackInfo[1]);
                    return ItemStack.f_41583_;
                }
                provided = this.trySubCraft(player, superRecipe, cache, layer - 1, crafts, doCraft, doLog);
                if (provided.m_41619_()) {
                    if (doLog) {
                        stack = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.missing_materials", new Object[]{stack.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack)))), false);
                    }
                    cache.setMissingItem((IFilter)items.getKey(), stackInfo[1]);
                    return ItemStack.f_41583_;
                }
                this.recipesToUnlock.add(superRecipe.getRecipe());
            }
            while (provided.m_41613_() < stackInfo[1]) {
                superRecipe = cache.getRecipeFromFilter((IFilter)items.getKey());
                if (superRecipe == null) {
                    if (doLog) {
                        stack = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.no_recipe", new Object[]{stack.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack)))), false);
                    }
                    cache.setMissingItem((IFilter)items.getKey(), stackInfo[1] - provided.m_41613_());
                    return ItemStack.f_41583_;
                }
                ItemStack crafted = this.trySubCraft(player, superRecipe, cache, 6, crafts, doCraft, doLog);
                if (crafted.m_41619_()) {
                    if (doLog) {
                        ItemStack stack2 = recipe.getFilter((IFilter)items.getKey());
                        player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.missing_materials", new Object[]{stack2.m_41786_()}).m_130948_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(stack2)))), false);
                    }
                    cache.setMissingItem((IFilter)items.getKey(), stackInfo[1] - provided.m_41613_());
                    return ItemStack.f_41583_;
                }
                provided.m_41769_(crafted.m_41613_());
                this.recipesToUnlock.add(superRecipe.getRecipe());
            }
            if (provided.m_41613_() > stackInfo[1]) {
                cache.getOutput().addItem(provided.m_41620_(provided.m_41613_() - stackInfo[1]), null, false);
            }
            craftItems.set(stackInfo[0], (Object)provided);
        }
        CRAFTING.m_6211_();
        for (int i2 = 0; i2 < 9; ++i2) {
            CRAFTING.m_6836_(i2, ((ItemStack)craftItems.get(i2)).m_41777_());
        }
        ItemStack output = recipe.getOutput(CRAFTING, this.f_58857_);
        if (output.m_41619_()) {
            if (doLog) {
                player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.no_output", new Object[]{recipe.getDisplayItem().m_41786_()}), false);
            }
            return ItemStack.f_41583_;
        }
        if (doCraft) {
            ForgeEventFactory.firePlayerCraftingEvent((Player)player, (ItemStack)output, (Container)CRAFTING);
        }
        ForgeHooks.setCraftingPlayer((Player)player);
        NonNullList leftOvers = NonNullList.m_122779_();
        NonNullList<ItemStack> remaining = recipe.getRemainingItems(CRAFTING, this.f_58857_);
        ForgeHooks.setCraftingPlayer(null);
        for (i = 0; i < 9; ++i) {
            ItemStack subStack = CRAFTING.m_8016_(i);
            if (!subStack.m_41619_()) {
                subStack.m_41774_(1);
                if (!subStack.m_41619_()) {
                    leftOvers.add((Object)subStack);
                }
            }
            subStack = (ItemStack)remaining.get(i);
            if (toConsume[i] > 0) {
                subStack.m_41774_(toConsume[i]);
            }
            if (subStack.m_41619_()) continue;
            leftOvers.add((Object)subStack);
        }
        int m = leftOvers.size();
        for (i = 0; i < m; ++i) {
            stack = (ItemStack)leftOvers.get(i);
            if (stack.m_41619_() || cache.getOutput().addItem(stack, null, false) >= stack.m_41613_()) continue;
            if (doLog) {
                player.m_5661_((Component)this.translate("info.block.ic2.industrial_workbench.junkyard_full"), false);
            }
            cache.setMissingItem(null, 0);
            return ItemStack.f_41583_;
        }
        return output;
    }

    @Override
    public int getEnabledSlots() {
        return this.enabledSlots;
    }

    @Override
    public boolean isServerSided() {
        return this.isSimulating();
    }

    @Override
    public CraftingList getRecipes() {
        return this.recipes;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }

    protected void merge(int[] from, int[] to) {
        int min = Math.min(from.length, to.length);
        for (int i = 0; i < min; ++i) {
            int n = i;
            to[n] = to[n] + from[i];
        }
    }

    @Override
    public boolean needsUpdateTick() {
        return super.needsUpdateTick() && !this.toInsert.isEmpty();
    }

    @Override
    public void onTick() {
        if (this.clock(20) && this.insertItems()) {
            this.removeFromTick();
        }
    }

    protected boolean insertItems() {
        IItemTransporter transporter = TransporterManager.getTransporter(new InventoryWrapper(this));
        Iterator<ItemStack> iter = this.toInsert.iterator();
        while (iter.hasNext()) {
            ItemStack next = iter.next();
            next.m_41774_(transporter.addItem(next, null, false));
            if (!next.m_41619_()) continue;
            iter.remove();
        }
        return this.toInsert.isEmpty();
    }

    public InteractionResult canAdvancedCraft(Player player) {
        if (this.currentRecipe.isValid()) {
            CraftingCache cache = this.createCache(true);
            if (!this.tryCraft(player, this.currentRecipe, cache, false, false).m_41619_()) {
                return InteractionResult.SUCCESS;
            }
            if (cache.getMissingItem() == null) {
                return InteractionResult.SUCCESS;
            }
            return cache.getMissingItem().getKey() == null ? InteractionResult.FAIL : InteractionResult.PASS;
        }
        return InteractionResult.CONSUME;
    }

    public void tryRequestingItems(Player player) {
        CraftingCache cache;
        if (!this.toRequest.isEmpty()) {
            player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.already_requesting"), false);
            return;
        }
        InteractionResult craftResult = this.canAdvancedCraft(player);
        if (craftResult == InteractionResult.CONSUME) {
            player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.no_recipe"), false);
            return;
        }
        if (craftResult == InteractionResult.FAIL) {
            player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.out_of_room"), false);
            return;
        }
        if (craftResult != InteractionResult.PASS) {
            player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.already_valid"), false);
            return;
        }
        Object2IntMap<Item> items = TubeNet.INSTANCE.getPotentialItems(this);
        if (items.isEmpty()) {
            player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.empty_tube_net"), false);
            return;
        }
        SimpleInventory inventory = new SimpleInventory(15);
        Object2IntLinkedOpenCustomHashMap requests = new Object2IntLinkedOpenCustomHashMap((Hash.Strategy)ItemStackStrategy.SIMPLE_INSTANCE);
        Object2IntLinkedOpenCustomHashMap missingItems = new Object2IntLinkedOpenCustomHashMap((Hash.Strategy)ItemStackStrategy.SIMPLE_INSTANCE);
        IItemTransporter transporter = TransporterManager.getTransporter(inventory);
        for (int i = 0; i < 32 && this.tryCraft(player, this.currentRecipe, cache = this.createCache(true).createSubCache(SimpleInventory.createCopy(inventory)), false, false).m_41619_(); ++i) {
            if (!(cache.getMissingItem().getKey() instanceof IngredientNBTFilter)) {
                this.finishRequests((Object2IntMap<ItemStack>)requests, player);
                player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.out_of_room"), false);
                return;
            }
            int count = cache.getMissingItem().getIntValue();
            if (count <= 0) continue;
            IngredientNBTFilter nbt = (IngredientNBTFilter)cache.getMissingItem().getKey();
            ItemStack result = ItemStack.f_41583_;
            for (ItemStack stack : nbt.getItem().m_43908_()) {
                if (items.getInt((Object)stack.m_41720_()) < count) continue;
                result = stack;
                break;
            }
            if (result.m_41619_()) {
                result = nbt.getItem().m_43908_()[0];
                if (transporter.addItem(StackUtil.copyWithSize(result, count), null, false) >= count) {
                    missingItems.addTo((Object)result, count);
                    continue;
                }
                player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.stack_limit"), false);
                return;
            }
            if (transporter.addItem(StackUtil.copyWithSize(result, count), null, false) >= count) {
                requests.addTo((Object)result, count);
                continue;
            }
            this.finishRequests((Object2IntMap<ItemStack>)requests, player);
            player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.stack_limit"), false);
            return;
        }
        this.finishRequests((Object2IntMap<ItemStack>)requests, player);
        if (!missingItems.isEmpty()) {
            MutableComponent text = this.string();
            ObjectIterator iter = Object2IntMaps.fastIterator((Object2IntMap)missingItems);
            while (iter.hasNext()) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry)iter.next();
                text.m_130946_(entry.getIntValue() + "x ").m_7220_(((ItemStack)entry.getKey()).m_41611_());
                if (!iter.hasNext()) continue;
                text.m_130946_(", ");
            }
            player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.missing_items", new Object[]{text}), false);
        }
    }

    public void finishRequests(Object2IntMap<ItemStack> toRequest, Player player) {
        MutableComponent text = this.string();
        ObjectIterator iter = Object2IntMaps.fastIterator(toRequest);
        while (iter.hasNext()) {
            Object2IntMap.Entry entry = (Object2IntMap.Entry)iter.next();
            text.m_130946_(entry.getIntValue() + "x ").m_7220_(((ItemStack)entry.getKey()).m_41611_());
            if (iter.hasNext()) {
                text.m_130946_(", ");
            }
            this.toRequest.add(new RequestEntry((ItemStack)entry.getKey(), entry.getIntValue()));
        }
        player.m_5661_((Component)this.translate("gui.ic2.industrial_workbench.requested_items", new Object[]{text}), false);
    }

    protected void syncRequests() {
        this.requestCache.clear();
        int slot = 0;
        int max = this.requestCache.getSlotCount();
        int m = this.toRequest.size();
        for (int i = 0; i < m && i < max; ++i) {
            RequestEntry entry = this.toRequest.get(i);
            int size = entry.getIntValue();
            ItemStack stack = StackUtil.copyWithSize(entry.getKey(), Math.min(size, 64));
            stack.m_41700_("amount", (Tag)IntTag.m_128679_((int)size));
            if (this.awaiting.containsKey((Object)entry.getKey())) {
                stack.m_41700_("state", (Tag)ByteTag.m_128266_((byte)2));
                stack.m_41700_("awaiting", (Tag)IntTag.m_128679_((int)this.awaiting.getInt((Object)entry.getKey())));
            } else if (this.requested.containsKey((Object)entry.getKey())) {
                stack.m_41700_("state", (Tag)ByteTag.m_128266_((byte)1));
            } else {
                stack.m_41700_("state", (Tag)ByteTag.m_128266_((byte)0));
            }
            this.requestCache.setStackInSlot(slot, stack);
            ++slot;
        }
    }

    @Override
    public void addItem(ItemStack item, Direction side, DyeColor color) {
        this.toInsert.add(item);
        if (!this.insertItems()) {
            this.addToTick();
        }
    }

    @Override
    public void addItem(TransportedItem item, Direction side) {
        if (this.isRendering()) {
            return;
        }
        ItemStack stack = item.getServerStack();
        int awaited = stack.m_41613_();
        int m = this.toRequest.size();
        for (int i = 0; i < m; ++i) {
            RequestEntry entry = this.toRequest.get(i);
            if (!StackUtil.isStackEqual(entry.getKey(), stack)) continue;
            entry.setValue(entry.getIntValue() - awaited);
            if (entry.getIntValue() > 0) continue;
            this.toRequest.remove(i--);
            --m;
        }
        int requesting = this.awaiting.getInt((Object)stack);
        if (requesting > 0) {
            this.awaiting.merge((Object)stack, awaited, MathUtils.REMOVAL);
        }
        if ((requesting = this.requested.getInt((Object)stack)) > 0) {
            this.requested.merge((Object)stack, awaited, MathUtils.REMOVAL);
        }
        this.toInsert.add(stack);
        if (!this.insertItems()) {
            this.addToTick();
        }
        this.syncRequests();
    }

    @Override
    public boolean canAddItem(TransportedItem item, Direction side) {
        return this.requestId.equals(item.getRequestId());
    }

    @Override
    public boolean canConnect(ITube other, Direction dir) {
        return true;
    }

    @Override
    public ITube.TubeType getTubeType() {
        return ITube.TubeType.SIMPLE;
    }

    @Override
    public long getRequestSource() {
        return this.m_58899_().m_121878_();
    }

    @Override
    public void provideRequests(IRequestTube.ITubeRequester requested) {
        if (this.toRequest.isEmpty()) {
            return;
        }
        boolean changed = false;
        int m = this.toRequest.size();
        for (int i = 0; i < m; ++i) {
            RequestEntry entry = this.toRequest.get(i);
            ItemStack stack = entry.getKey();
            int amount = entry.getIntValue() - this.requested.getInt((Object)stack);
            if (amount <= 0) continue;
            requested.requestItems(stack, amount, this.requestColor, this.requestId);
            this.requested.addTo((Object)stack, amount);
            changed = true;
        }
        if (changed) {
            this.syncRequests();
        }
    }

    @Override
    public void onRequestsReset() {
        this.requested.clear();
        this.awaiting.clear();
        this.syncRequests();
    }

    @Override
    public void onRequestFulfilled(ItemStack stack, int amount) {
        this.awaiting.put((Object)stack, amount);
        this.syncRequests();
    }

    @Override
    public UUID getRequestId() {
        return this.requestId;
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
        this.syncRequests();
    }

    public void removeRequest(int slot) {
        if (slot < 0 || slot >= this.requestCache.getSlotCount()) {
            return;
        }
        ItemStack toRemove = this.requestCache.getStackInSlot(slot);
        boolean found = false;
        Iterator<RequestEntry> iter = this.toRequest.iterator();
        while (iter.hasNext()) {
            ItemStack next = iter.next().getKey();
            if (!next.m_41656_(toRemove)) continue;
            iter.remove();
            toRemove = next;
            found = true;
            break;
        }
        if (!found) {
            return;
        }
        this.awaiting.removeInt((Object)toRemove);
        this.requested.clear();
        this.requested.putAll(this.awaiting);
        TubeNet.INSTANCE.clearRequests(this);
        this.syncRequests();
    }

    public static class RequestEntry {
        ItemStack key;
        int value;

        public RequestEntry(ItemStack key, int value) {
            this.key = key;
            this.value = value;
        }

        public ItemStack getKey() {
            return this.key;
        }

        public int getIntValue() {
            return this.value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}

