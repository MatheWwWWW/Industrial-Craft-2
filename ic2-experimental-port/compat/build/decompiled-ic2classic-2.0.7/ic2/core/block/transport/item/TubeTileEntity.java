/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelData$Builder
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.transport.item;

import ic2.api.blocks.PainterHelper;
import ic2.api.core.IC2Classic;
import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkDataEventListener;
import ic2.api.network.tile.INetworkEventListener;
import ic2.api.network.tile.PacketRange;
import ic2.api.tiles.IAnchorTile;
import ic2.api.tiles.tubes.ITube;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.IC2;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.DirectionalCapability;
import ic2.core.block.base.cache.DirectionalCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.ISpecialWrenchable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CableProperty;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.logic.TransportList;
import ic2.core.block.transport.item.logic.TransportSyncer;
import ic2.core.block.transport.item.logic.TubeSyncManager;
import ic2.core.block.transport.item.logistic.TubeNet;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public abstract class TubeTileEntity
extends BaseTileEntity
implements IClickable,
ITickListener,
ILocation,
ICamouflagable,
ISpecialWrenchable,
IAnchorTile,
ITube,
INetworkEventListener,
INetworkDataEventListener,
IDropProvider {
    public static final int MAX_MANAGED_ITEMS = 200;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    protected int connectivity = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    protected int anchors = 0;
    @NetworkInfo
    public byte foamed = 0;
    @NetworkInfo
    public boolean synchronize = true;
    @NetworkInfo
    public CamouflageStorage storage = new CamouflageStorage(CamouflageShape.FULL_CUBE);
    @NetworkInfo
    public TransportList items = new TransportList(200);
    public TransportSyncer sync = new TransportSyncer();
    public ObjectSet<TransportedItem> itemsToDelete = CollectionUtils.createSet();
    int maxSpeed = 5;
    int halfSpeed = 2;
    protected boolean addedToTubeNet = false;
    public ICache<IItemHandler> inventories = new CapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER);
    public ICache<ITube> tubes = new DirectionalCapabilityCache<ITube>(this, DirectionList.ALL, IC2Classic.TUBE_CAPABILITY, this::canConnectToTube);
    public DirectionalCapability<ITube> self = new DirectionalCapability<TubeTileEntity>(IC2Classic.TUBE_CAPABILITY, this);

    public TubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.clearNetworkFields();
        this.addNetworkFields("items", "connectivity", "anchors", "storage", "foamed", "synchronize");
        if (this.useDefaultCache()) {
            this.addCaches(this.inventories, this.tubes);
        }
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "connections", this.connectivity, 0);
        NBTUtils.putByte(compound, "anchors", this.anchors, 0);
        NBTUtils.putByte(compound, "foamed", (int)this.foamed, 0);
        NBTUtils.putIf(compound, "camouflage", this.storage.save(new CompoundTag()), this.foamed > 0);
        NBTUtils.put(compound, "items", this.items.write());
        NBTUtils.putBoolean(compound, "sync_items", this.synchronize, true);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.connectivity = compound.m_128451_("connection");
        this.anchors = compound.m_128451_("anchors");
        this.items.read(compound.m_128437_("items", 10));
        this.storage.load(compound.m_128469_("camouflage"));
        this.foamed = compound.m_128445_("foamed");
        this.synchronize = NBTUtils.getBoolean(compound, "sync_items", true);
        if (!this.synchronize) {
            this.getNetworkFields().remove("items");
        }
    }

    protected boolean isRotatable() {
        return false;
    }

    public boolean useDefaultCache() {
        return true;
    }

    protected boolean canSetFacingInternal(Direction dir) {
        return false;
    }

    public int getPrioritySide() {
        return 0;
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        for (int i = 0; i < this.items.size(); ++i) {
            ItemStack stack = ((TransportedItem)this.items.get(i)).getServerStack();
            if (stack.m_41619_()) continue;
            StackUtil.expand(stack, stack.m_41613_(), drops);
        }
        int count = DirectionList.ofNumber(this.anchors).size();
        if (count > 0) {
            drops.add(new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT, count));
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == IC2Classic.TUBE_CAPABILITY ? this.self.getCapability(cap, side) : super.getCapability(cap, side);
    }

    @Override
    public boolean needsUpdateTick() {
        return this.isRendering() || this.items.size() > 0 || !this.canLoseUpdateTick();
    }

    public boolean canLoseUpdateTick() {
        return false;
    }

    public DirectionList getExtraFacings() {
        return DirectionList.EMPTY;
    }

    @Override
    public ITube.TubeType getTubeType() {
        return ITube.TubeType.SIMPLE;
    }

    public int getConnectivity() {
        return this.connectivity | this.anchors;
    }

    public int getValidDirections() {
        return this.connectivity;
    }

    public int getAnchors() {
        return this.anchors;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = Mth.m_14045_((int)maxSpeed, (int)1, (int)100);
        this.halfSpeed = Math.max(1, this.maxSpeed / 2);
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public int getHalfSpeed() {
        return this.halfSpeed;
    }

    public void onTubeUpdate() {
    }

    public void onItemEntered(TransportedItem item, Direction dir) {
    }

    public boolean onCenterReached(TransportedItem item) {
        return false;
    }

    public boolean onSideReached(TransportedItem item) {
        return false;
    }

    public boolean onLeftOver(TransportedItem item) {
        return false;
    }

    public DirectionList getValidDirections(TransportedItem item) {
        return DirectionList.ofNumber(this.connectivity).remove(item.getAttemptedDirections());
    }

    public boolean insertIntoInventory(IItemHandler handler, Direction flowDirection, TransportedItem item) {
        ItemStack stack = item.getStack();
        stack.m_41774_(TransporterManager.getTransporter(handler).addItem(stack, flowDirection.m_122424_(), false));
        return item.isInvalid();
    }

    protected int initTick(Level world) {
        if (!this.m_58901_()) {
            this.tick();
        }
        return 0;
    }

    @Override
    public void onTick() {
        if (!this.isLoaded()) {
            return;
        }
        this.onTubeUpdate();
        int m = Math.min(this.items.size(), 200);
        for (int i = 0; i < m; ++i) {
            TransportedItem item = (TransportedItem)this.items.get(i);
            if (item.isInvalid()) {
                this.itemsToDelete.add((Object)item);
                continue;
            }
            item.update();
            if (item.hasReachedCenter()) {
                if (this.calculateNextDirection(item)) continue;
                this.itemsToDelete.add((Object)item);
                continue;
            }
            if (!item.hasReachedEnd() || !this.moveIntoTarget(item)) continue;
            this.itemsToDelete.add((Object)item);
        }
        if (this.itemsToDelete.size() > 0) {
            this.items.removeAll((Collection<?>)this.itemsToDelete);
            this.sync.removeAll((Set<TransportedItem>)this.itemsToDelete);
            this.itemsToDelete.clear();
        }
        if (this.isSimulating()) {
            if (this.sync.hasData()) {
                if (!this.synchronize) {
                    this.sync.clear();
                } else {
                    TubeSyncManager.markDirty(this, this.sync.serialize());
                }
            }
            if (this.items.isEmpty() && this.canLoseUpdateTick()) {
                this.removeFromTick();
            }
        }
    }

    public void onSyncPacketReceived(byte[] data) {
        this.sync.deserialize(data, this, "request", (List<TransportedItem>)((Object)this.items));
    }

    @Override
    public void onDataBufferReceived(Player player, String id, INetworkDataBuffer data, Dist target) {
        if (target.isDedicatedServer() && id.equals("request")) {
            if (!(data instanceof NBTBuffer)) {
                return;
            }
            NBTBuffer buffer = (NBTBuffer)data;
            ByteBuf buf = Unpooled.buffer();
            OutputBuffer writer = new OutputBuffer(buf);
            IntOpenHashSet set = new IntOpenHashSet(buffer.getNBT().m_128465_("data"));
            if (set.isEmpty()) {
                return;
            }
            int amount = 0;
            int m = this.items.size();
            for (int i = 0; i < m; ++i) {
                TransportedItem item = (TransportedItem)this.items.get(i);
                if (!set.contains(item.getId())) continue;
                item.write(writer);
                ++amount;
            }
            byte[] bytes = new byte[buf.writerIndex()];
            buf.readBytes(bytes);
            CompoundTag nbt = new CompoundTag();
            nbt.m_128405_("size", amount);
            nbt.m_128382_("data", bytes);
            this.sendToPlayer(player, "requested_items", new NBTBuffer(nbt));
        } else if (id.equals("requested_items")) {
            if (!(data instanceof NBTBuffer)) {
                return;
            }
            NBTBuffer buffer = (NBTBuffer)data;
            int size = buffer.getNBT().m_128451_("size");
            InputBuffer reader = new InputBuffer(buffer.getNBT().m_128463_("data"));
            for (int i = 0; i < size; ++i) {
                this.items.add(TransportedItem.readFromNetwork(reader));
            }
        }
    }

    @Override
    public void onServerDataReceived(int key, int value) {
        if (key == 1) {
            int m = this.items.size();
            for (int i = 0; i < m; ++i) {
                TransportedItem item = (TransportedItem)this.items.get(i);
                if (item.getId() != value) continue;
                item.invalidate();
                return;
            }
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("connectivity") || fields.contains("anchors") || fields.contains("storage") || fields.contains("foamed")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToTubeNet) {
            this.addedToTubeNet = true;
            TubeNet.INSTANCE.addTube(this);
        }
        if (this.isSimulating()) {
            this.tickFoamed();
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToTubeNet) {
            this.addedToTubeNet = false;
            TubeNet.INSTANCE.removeTube(this);
        }
        this.self.unload();
        super.onUnloaded(chunk);
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.getValue(BlockStateProperties.f_61362_, false).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)this.f_58857_));
        } else if (this.getValue(IC2Properties.LAVA_LOGGED, false).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)this.f_58857_));
        }
    }

    @Override
    protected void onCachesUpdated() {
        this.connectivity = DirectionList.EMPTY.add(this.inventories.getPresentSides()).add(this.tubes.getPresentSides()).remove(DirectionList.ofNumber(this.anchors)).getCode();
        this.updateTileField("connectivity");
    }

    public void applySpeed(TransportedItem item) {
        Direction dir = item.getTravelingDirection();
        if (dir.m_122434_().m_122479_()) {
            item.updateSpeed(this.maxSpeed, 1);
        } else if (dir.m_122421_() == Direction.AxisDirection.NEGATIVE) {
            item.updateSpeed(100, 3);
        } else {
            item.updateSpeed(this.halfSpeed, 1);
        }
    }

    public boolean moveIntoTarget(TransportedItem item) {
        this.applySpeed(item);
        if (this.onSideReached(item)) {
            return false;
        }
        Direction dir = item.getTransferDirection();
        if (this.isRendering()) {
            ITube tube = (ITube)DirectionList.getNeighborCapability(this, dir, IC2Classic.TUBE_CAPABILITY).orElse(null);
            if (tube != null) {
                tube.addItem(item, dir.m_122424_());
            }
            return true;
        }
        ITube tube = this.tubes.getHandler(dir);
        if (tube != null && tube.canAddItem(item, dir.m_122424_())) {
            tube.addItem(item, dir.m_122424_());
            return true;
        }
        IItemHandler handler = this.inventories.getHandler(dir);
        if (handler != null && this.insertIntoInventory(handler, dir, item)) {
            return true;
        }
        if (this.onLeftOver(item)) {
            return item.isInvalid();
        }
        DirectionList list = this.getValidDirections(item);
        if (list.isEmpty()) {
            this.onItemLost(item, false);
            StackUtil.pop(this.f_58857_, this.m_58899_(), item.getStack());
            this.sendToClient(1, item.getId(), PacketRange.CHUNK_TRACKED);
            return true;
        }
        item.setInsertionDirection(item.getTransferDirection());
        this.sync.markDirty(item);
        return false;
    }

    public boolean calculateNextDirection(TransportedItem item) {
        this.applySpeed(item);
        if (this.onCenterReached(item)) {
            return !item.isInvalid();
        }
        if (this.isRendering()) {
            return this.getValidDirections(item).size() > 0;
        }
        DirectionList list = this.getValidDirections(item);
        for (Direction dir : list) {
            ITube tube = this.tubes.getHandler(dir);
            if (tube != null && tube.canAddItem(item, dir.m_122424_()) || this.inventories.getHandler(dir) != null) continue;
            list = list.remove(dir);
        }
        if (list.isEmpty()) {
            this.onItemLost(item, true);
            StackUtil.pop(this.f_58857_, this.m_58899_(), item.getStack());
            this.sendToClient(1, item.getId(), PacketRange.CHUNK_TRACKED);
            return false;
        }
        item.setExportDirection(list.getRandomFacing());
        this.sync.markDirty(item);
        return true;
    }

    public void onItemLost(TransportedItem item, boolean center) {
        if (item.getRequestId() != null && this.isSimulating()) {
            TubeNet.INSTANCE.onRequestLost(this, item.getServerStack(), item.getRequestId());
        }
    }

    @Override
    public void addItem(ItemStack item, Direction side, DyeColor color) {
        if (item.m_41619_()) {
            return;
        }
        this.addItem(new TransportedItem(item).setColor(color), side);
    }

    @Override
    public void addItem(TransportedItem item, Direction side) {
        if (this.items.add(item)) {
            item.clearDirections();
            item.setInsertionDirection(side);
            if (this.isSimulating()) {
                if (this.items.size() < 200 && this.synchronize) {
                    this.sync.markDirty(item);
                }
                if (!this.isTicking() || this.isRemoving()) {
                    if (!this.isAdding()) {
                        IC2.TICK_HANDLER.addWorldCallback(this.f_58857_, this::initTick);
                    }
                    this.addToTick();
                }
            }
            this.applySpeed(item);
            this.onItemEntered(item, side);
        }
    }

    @Override
    public boolean canAddItem(TransportedItem item, Direction side) {
        return true;
    }

    @Override
    public boolean canConnect(ITube other, Direction dir) {
        return (this.anchors & 1 << dir.m_122411_()) == 0;
    }

    protected boolean canConnectToTube(Direction dir, ITube tile) {
        return this.canConnect(tile, dir) && tile.canConnect(this, dir.m_122424_());
    }

    @Override
    public AABB hasSpecialAction(Direction side, Vec3 hit, Player player) {
        Direction clicked = ITubeBlock.isClickingAt(hit, side, 0.1875f);
        if (clicked == null) {
            return null;
        }
        if (this.isRotatable() && this.canSetFacingInternal(clicked) || this.hasAnchor(clicked)) {
            return ITubeBlock.getHitBox(hit, side, 0.1875f);
        }
        return null;
    }

    @Override
    public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        Direction clicked = ITubeBlock.isClickingAt(hit, side, 0.1875f);
        if (clicked == null) {
            return false;
        }
        if (this.isRotatable() && this.canSetFacingInternal(clicked)) {
            this.setFacing(clicked);
            return true;
        }
        if (this.removeAnchor(clicked)) {
            Block.m_49840_((Level)this.f_58857_, (BlockPos)this.m_58899_(), (ItemStack)new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT));
            return true;
        }
        return false;
    }

    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        if (action == TubeAction.VISIBLE_ITEMS) {
            this.toggleSync(!this.synchronize, (LongSet)new LongOpenHashSet());
            if (this.isSimulating()) {
                player.m_213846_((Component)this.translate(this.synchronize ? "tooltip.item.ic2.tube_tool.visible_items.enable" : "tooltip.item.ic2.tube_tool.visible_items.disable"));
            }
            return true;
        }
        return false;
    }

    private void toggleSync(boolean value, LongSet tubes) {
        tubes.add(this.m_58899_().m_121878_());
        this.synchronize = value;
        this.updateTileField("synchronize");
        if (this.synchronize) {
            this.addNetworkFields("items");
            this.updateTileField("items");
        } else {
            this.getNetworkFields().remove("items");
        }
        for (Direction dir : DirectionList.ALL) {
            TubeTileEntity tile = DirectionList.getNeighborInterface(this, dir, TubeTileEntity.class);
            if (tile == null || tubes.contains(tile.m_58899_().m_121878_())) continue;
            tile.toggleSync(value, tubes);
        }
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41720_() == IC2Blocks.MINING_PIPE_SHAFT.m_5456_()) {
            Direction dir = ITubeBlock.isClickingAt(hit.m_82450_().m_82546_(Vec3.m_82528_((Vec3i)hit.m_82425_())), side, 0.1875f);
            if (dir != null && this.addAnchor(dir)) {
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        } else if (stack.m_41720_() == IC2Blocks.CFOAM_WET.m_5456_()) {
            if (this.foamed == 0) {
                this.changeFoam((byte)1, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        } else {
            BlockState dustState;
            if (stack.m_204117_(ItemTags.f_13137_) && this.foamed == 1) {
                this.changeFoam((byte)2, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
            if (this.foamed == 1 && PainterHelper.CONCRETE_DUST.contains((dustState = Block.m_49814_((Item)stack.m_41720_()).m_49966_()).m_60734_())) {
                this.storage.setColor(DirectionList.ALL, PainterHelper.INSTANCE.getColor(dustState));
                this.changeFoam((byte)2, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAnchor(Direction side) {
        return (this.anchors & 1 << side.m_122411_()) != 0;
    }

    @Override
    public boolean addAnchor(Direction dir) {
        if ((this.anchors & 1 << dir.m_122411_()) == 0) {
            this.anchors |= 1 << dir.m_122411_();
            this.self.setActiveDirections(DirectionList.ALL.remove(DirectionList.ofNumber(this.anchors)));
            this.updateTileField("anchors");
            TubeNet.INSTANCE.updateTube(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAnchor(Direction dir) {
        if ((this.anchors & 1 << dir.m_122411_()) != 0) {
            this.anchors &= ~(1 << dir.m_122411_());
            this.self.setActiveDirections(DirectionList.ALL.remove(DirectionList.ofNumber(this.anchors)));
            this.updateTileField("anchors");
            TubeNet.INSTANCE.updateTube(this);
            return true;
        }
        return false;
    }

    @Override
    public CamouflageStorage getStorage() {
        return this.storage;
    }

    public boolean setColor(Direction dir, DyeColor color) {
        if (this.foamed == 2 && this.isSimulating() && this.storage.canApply(dir == null ? DirectionList.ALL : DirectionList.ofFacing(dir), color)) {
            this.storage.setColor(dir == null ? DirectionList.ALL : DirectionList.ofFacings(dir), color);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    @Override
    public boolean applyTexture(Direction dir, RetextureEvent.TextureContainer container) {
        if (this.foamed == 2 && this.storage.canApply(dir, container)) {
            this.storage.setTexture(DirectionList.ofFacing(dir), container);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    @Override
    public boolean isSideEnabled(Direction dir) {
        return this.foamed > 1;
    }

    @Override
    public boolean removeCamouflage() {
        return this.changeFoam((byte)0, false);
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        DirectionList extra;
        ModelData.Builder builder = ModelData.builder().with((ModelProperty)CableProperty.INSTANCE, (Object)(this.connectivity << 6 | this.anchors));
        if (this.foamed == 2) {
            builder.with((ModelProperty)CamouflageProperty.INSTANCE, T -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)T));
        }
        if (!(extra = this.getExtraFacings()).isEmpty()) {
            builder.with((ModelProperty)CableProperty.TUBE_EXTRA_SIDE, (Object)extra.getCode());
        }
        return builder.build();
    }

    public boolean changeFoam(byte level, boolean duringLoad) {
        if (this.foamed == level) {
            return false;
        }
        if (this.isSimulating()) {
            this.foamed = level;
            if (this.foamed == 1) {
                this.storage.setColor(DirectionList.ALL, DyeColor.LIGHT_GRAY);
                if (!duringLoad) {
                    this.updateTileField("storage");
                }
                this.tickFoamed();
            }
            if (!duringLoad) {
                this.updateTileField("foamed");
                this.onStateChanged();
            }
        }
        return true;
    }

    private void tickFoamed() {
        if (this.foamed != 1) {
            return;
        }
        IC2.TICK_HANDLER.addWorldCallback(this.f_58857_, new ToIntFunction<Level>(){

            @Override
            public int applyAsInt(Level world) {
                if (TubeTileEntity.this.m_58901_() || TubeTileEntity.this.foamed != 1) {
                    return 0;
                }
                if (world.m_46472_().equals((Object)Level.f_46429_)) {
                    if (world.f_46441_.m_188503_(5) == 0) {
                        TubeTileEntity.this.changeFoam((byte)2, false);
                        return 0;
                    }
                    return 20;
                }
                if (world.m_46803_(TubeTileEntity.this.f_58858_) * 6 > world.f_46441_.m_188503_(1000)) {
                    TubeTileEntity.this.changeFoam((byte)2, false);
                    return 0;
                }
                return 500;
            }
        });
    }
}

