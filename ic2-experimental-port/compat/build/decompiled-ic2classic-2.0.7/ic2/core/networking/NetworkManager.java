/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package ic2.core.networking;

import ic2.api.network.INetworkManager;
import ic2.api.network.buffer.EmptyDataBuffer;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkFieldProvider;
import ic2.api.network.tile.PacketRange;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasCustomGui;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.custom.FriendsHandler;
import ic2.core.networking.PacketManager;
import ic2.core.networking.buffers.FieldBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.networking.buffers.data.BaseTeleporterBuffer;
import ic2.core.networking.buffers.data.HighlightBuffer;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.networking.buffers.data.NodeBuffer;
import ic2.core.networking.buffers.data.ObscuratorBuffer;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.networking.buffers.data.VillagerBuffer;
import ic2.core.networking.misc.NetworkUtils;
import ic2.core.networking.packets.client.TileDataBufferEventPacket;
import ic2.core.networking.packets.common.ItemDataBufferPacket;
import ic2.core.networking.packets.common.ItemEventPacket;
import ic2.core.networking.packets.server.gui.open.OpenCustomGuiPacket;
import ic2.core.networking.packets.server.gui.open.OpenEntityGuiPacket;
import ic2.core.networking.packets.server.gui.open.OpenItemGuiPacket;
import ic2.core.networking.packets.server.gui.open.OpenTileGuiPacket;
import ic2.core.networking.packets.server.gui.sync.CustomNetworkPacket;
import ic2.core.networking.packets.server.gui.sync.GuiFieldUpdatePacket;
import ic2.core.networking.packets.server.gui.sync.NetworkFieldUpdatePacket;
import ic2.core.networking.packets.server.gui.sync.ServerTileEventPacket;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.FunctionRegistry;
import ic2.core.utils.collection.SupplierRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.mutable.MutableInt;

public class NetworkManager
implements INetworkManager {
    public static final String IC2_DATA = "ic2cData";
    static Map<Class<? extends BlockEntity>, FieldBuffer> FIELD_BUFFERS = CollectionUtils.createMap();
    static SupplierRegistry<INetworkDataBuffer, Supplier<INetworkDataBuffer>> BUFFER_REGISTRY = CollectionUtils.createSupplierRegistry();
    static FunctionRegistry<IHasCustomGui, IInputBuffer, Function<IInputBuffer, IHasCustomGui>> CUSTOM_GUI = new FunctionRegistry();
    static Map<Level, FieldStorage> NETWORK_FIELDS = CollectionUtils.createMap();
    static Map<Level, FieldStorage> GUI_FIELDS = CollectionUtils.createMap();
    static Map<Level, MutableInt> DELAYS = CollectionUtils.createMap();
    final Function<Level, FieldStorage> creatorFunction = W -> new FieldStorage();

    public void init() {
        PacketManager.INSTANCE.init();
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "nbt_buffer"), NBTBuffer::new, NBTBuffer.class);
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "string_buffer"), StringDataBuffer::new, StringDataBuffer.class);
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "obscurator_buffer"), ObscuratorBuffer::new, ObscuratorBuffer.class);
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "node"), NodeBuffer::new, NodeBuffer.class);
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "base_teleporter"), BaseTeleporterBuffer::new, BaseTeleporterBuffer.class);
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "highlight"), HighlightBuffer::new, HighlightBuffer.class);
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "empty"), () -> EmptyDataBuffer.INSTANCE, EmptyDataBuffer.class);
        BUFFER_REGISTRY.register(new ResourceLocation("ic2", "villager"), VillagerBuffer::new, VillagerBuffer.class);
        CUSTOM_GUI.register(new ResourceLocation("ic2", "friends"), T -> new FriendsHandler(), FriendsHandler.class);
    }

    public static ResourceLocation getID(INetworkDataBuffer buffer) {
        return BUFFER_REGISTRY.getKey(buffer);
    }

    public static INetworkDataBuffer createBuffer(ResourceLocation location) {
        return (INetworkDataBuffer)((Supplier)BUFFER_REGISTRY.getValue(location)).get();
    }

    public void onServerStopped() {
        NETWORK_FIELDS.clear();
        GUI_FIELDS.clear();
        DELAYS.clear();
    }

    public void onWorldUnload(Level world) {
        DELAYS.remove(world);
        NETWORK_FIELDS.remove(world);
        GUI_FIELDS.remove(world);
    }

    public void onWorldTick(Level world) {
        MutableInt counter = DELAYS.get(world);
        if (counter == null) {
            counter = new MutableInt(5);
            DELAYS.put(world, counter);
        }
        if (counter.decrementAndGet() <= 0) {
            counter.setValue(5);
            this.processNetworkPackets(world);
        }
    }

    public void onServerTick() {
        for (FieldStorage storage : GUI_FIELDS.values()) {
            storage.clear();
        }
    }

    public void processNetworkPackets(Level world) {
        FieldStorage storage = NETWORK_FIELDS.get(world);
        if (storage == null || storage.isEmpty()) {
            return;
        }
        for (Map.Entry<ChunkPos, Map<BlockPos, List<FieldData>>> data : storage.getData().entrySet()) {
            ChunkPos pos = data.getKey();
            if (!world.m_7232_(pos.f_45578_, pos.f_45579_)) continue;
            PacketManager.INSTANCE.sendToAllChunkWatchers(world.m_6325_(pos.f_45578_, pos.f_45579_), new NetworkFieldUpdatePacket(data.getValue()));
        }
    }

    public void registerCustomGui(ResourceLocation location, Class<? extends IHasCustomGui> clz, Function<IInputBuffer, IHasCustomGui> creator) {
        CUSTOM_GUI.register(location, creator, clz);
    }

    @Override
    public void registerDataBuffer(ResourceLocation location, Class<? extends INetworkDataBuffer> clz, Supplier<INetworkDataBuffer> creator) {
        BUFFER_REGISTRY.register(location, creator, clz);
    }

    public void sendCustomUIPacket(Player player, IHasCustomGui gui, int windowId) {
        ResourceLocation id = CUSTOM_GUI.getKey(gui);
        if (id != null) {
            ByteBuf buf = Unpooled.buffer();
            gui.sendInitialData(new OutputBuffer(new FriendlyByteBuf(buf)));
            byte[] data = new byte[buf.writerIndex()];
            buf.readBytes(data);
            PacketManager.INSTANCE.sendToPlayer(new OpenCustomGuiPacket(id, data, windowId), player);
        }
    }

    public IHasCustomGui createCustomGui(Player player, ResourceLocation gui, IInputBuffer buffer) {
        Function function = (Function)CUSTOM_GUI.getValue(gui);
        return function == null ? null : (IHasCustomGui)function.apply(buffer);
    }

    public void sendUIPacket(Player player, InteractionHand hand, Direction dir, IHasGui gui, int windowID) {
        hand = hand == null ? InteractionHand.MAIN_HAND : hand;
        Direction direction = dir = dir == null ? Direction.NORTH : dir;
        if (gui instanceof BlockEntity) {
            BlockEntity tile = (BlockEntity)gui;
            PacketManager.INSTANCE.sendToPlayer(new OpenTileGuiPacket(tile.m_58899_(), hand, dir, windowID), player);
            if (tile instanceof INetworkFieldProvider) {
                this.sendInitialGuiData((INetworkFieldProvider)tile, player);
            }
            this.startGuiTracking(tile, player);
            return;
        }
        if (gui instanceof Entity) {
            Entity entity = (Entity)gui;
            PacketManager.INSTANCE.sendToPlayer(new OpenEntityGuiPacket(entity.m_19879_(), hand == InteractionHand.MAIN_HAND, windowID), player);
            return;
        }
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41720_() instanceof IHasHeldGui) {
            int slot = hand == InteractionHand.OFF_HAND ? -1 : player.m_150109_().f_35977_;
            PacketManager.INSTANCE.sendToPlayer(new OpenItemGuiPacket(slot, windowID), player);
        }
    }

    @Override
    public void startGuiTracking(BlockEntity tile, Player player) {
        PlayerHandler.getHandler((Player)player).trackedTile = tile;
    }

    @Override
    public void sendInitialGuiData(INetworkFieldProvider prov, Player player) {
        if (!(prov instanceof BlockEntity)) {
            return;
        }
        BlockEntity tile = (BlockEntity)prov;
        FieldBuffer buffer = NetworkManager.getFieldBuffer(tile);
        ArrayList<FieldData> data = new ArrayList<FieldData>();
        for (String s : prov.getGuiFields()) {
            FieldBuffer.FieldInfo info = buffer.getInfo(s);
            if (info == null) continue;
            data.add(new FieldData(tile, info));
        }
        PacketManager.INSTANCE.sendToPlayer(new GuiFieldUpdatePacket(tile, data), player);
    }

    @Override
    public void updateGuiData(BlockEntity tile, Player player) {
        FieldStorage storage = GUI_FIELDS.get(tile.m_58904_());
        if (storage == null || storage.isEmpty()) {
            return;
        }
        List<FieldData> data = storage.getFields(tile);
        if (data.isEmpty()) {
            return;
        }
        PacketManager.INSTANCE.sendToPlayer(new GuiFieldUpdatePacket(tile, data), player);
    }

    @Override
    public void updateTileField(BlockEntity tile, String field) {
        NETWORK_FIELDS.computeIfAbsent(tile.m_58904_(), this.creatorFunction).addTileToUpdate(tile, field);
    }

    @Override
    public void updateTileFields(BlockEntity tile, String ... fields) {
        NETWORK_FIELDS.computeIfAbsent(tile.m_58904_(), this.creatorFunction).addTileToUpdate(tile, fields);
    }

    @Override
    public void updateGuiField(BlockEntity tile, String field) {
        GUI_FIELDS.computeIfAbsent(tile.m_58904_(), this.creatorFunction).addTileToUpdate(tile, field);
    }

    @Override
    public void updateGuiFields(BlockEntity tile, String ... fields) {
        GUI_FIELDS.computeIfAbsent(tile.m_58904_(), this.creatorFunction).addTileToUpdate(tile, fields);
    }

    @Override
    public void sendInitialData(INetworkFieldProvider prov, CompoundTag nbt) {
        List<FieldData> data = NetworkUtils.createFieldData(prov);
        if (data.isEmpty()) {
            return;
        }
        ByteBuf buf = Unpooled.buffer();
        NetworkUtils.writeEntries(new OutputBuffer(new FriendlyByteBuf(buf)), data);
        byte[] result = new byte[buf.writerIndex()];
        buf.readBytes(result);
        nbt.m_128382_(IC2_DATA, result);
    }

    @Override
    public void handleInitialChange(BlockEntity prov, CompoundTag nbt) {
    }

    @Override
    public void requestInitialData(INetworkFieldProvider prov) {
        if (!(prov instanceof BlockEntity)) {
            return;
        }
        BlockEntity tile = (BlockEntity)prov;
        for (String s : prov.getNetworkFields()) {
            this.updateTileField(tile, s);
        }
    }

    @Override
    public void sendTileEvent(BlockEntity tile, int key, int value, PacketRange target) {
        switch (target) {
            case SHORT_RANGE: {
                PacketManager.INSTANCE.sendToNearby(tile.m_58904_(), tile.m_58899_(), 64, new ServerTileEventPacket(tile, key, value));
                break;
            }
            case LONG_RANGE: {
                PacketManager.INSTANCE.sendToNearby(tile.m_58904_(), tile.m_58899_(), 256, new ServerTileEventPacket(tile, key, value));
                break;
            }
            case CHUNK_TRACKED: {
                PacketManager.INSTANCE.sendToAllChunkWatchers(tile.m_58904_().m_46745_(tile.m_58899_()), new ServerTileEventPacket(tile, key, value));
                break;
            }
            case ALL_DIM: {
                PacketManager.INSTANCE.sendToAllDim(tile.m_58904_(), new ServerTileEventPacket(tile, key, value));
                break;
            }
            case ALL_SERVER: {
                PacketManager.INSTANCE.sendToAllPlayers(new ServerTileEventPacket(tile, key, value));
            }
        }
    }

    @Override
    public void sendTileDataBufferEvent(BlockEntity tile, String id, INetworkDataBuffer buffer, PacketRange target) {
        if (NetworkManager.getID(buffer) == null) {
            IC2.LOGGER.info("DataBuffer wasn't registered. Ignoring Packet Request");
            return;
        }
        switch (target) {
            case SHORT_RANGE: {
                PacketManager.INSTANCE.sendToNearby(tile.m_58904_(), tile.m_58899_(), 64, new TileDataBufferEventPacket(tile, id, buffer, true));
                break;
            }
            case LONG_RANGE: {
                PacketManager.INSTANCE.sendToNearby(tile.m_58904_(), tile.m_58899_(), 256, new TileDataBufferEventPacket(tile, id, buffer, true));
                break;
            }
            case CHUNK_TRACKED: {
                PacketManager.INSTANCE.sendToAllChunkWatchers(tile.m_58904_().m_46745_(tile.m_58899_()), new TileDataBufferEventPacket(tile, id, buffer, true));
                break;
            }
            case ALL_DIM: {
                PacketManager.INSTANCE.sendToAllDim(tile.m_58904_(), new TileDataBufferEventPacket(tile, id, buffer, true));
                break;
            }
            case ALL_SERVER: {
                PacketManager.INSTANCE.sendToAllPlayers(new TileDataBufferEventPacket(tile, id, buffer, true));
            }
        }
    }

    @Override
    public void sendClientTileEvent(BlockEntity tile, int key, int value) {
    }

    @Override
    public void sendClientTileDataBufferEvent(BlockEntity tile, String id, INetworkDataBuffer buffer) {
    }

    @Override
    public void sendItemEvent(Player player, ItemStack stack, int key, int value) {
        PacketManager.INSTANCE.sendToPlayer(new ItemEventPacket(stack, key, value, true), player);
    }

    @Override
    public void sendItemBuffer(Player player, ItemStack stack, String id, INetworkDataBuffer buffer) {
        PacketManager.INSTANCE.sendToPlayer(new ItemDataBufferPacket(true, id, buffer, stack), player);
    }

    @Override
    public void sendClientItemEvent(ItemStack stack, int key, int value) {
    }

    @Override
    public void sendClientItemBuffer(ItemStack stack, String id, INetworkDataBuffer buffer) {
    }

    public void sendCustomBuffer(Player player, String id, INetworkDataBuffer buffer) {
        PacketManager.INSTANCE.sendToPlayer(new CustomNetworkPacket(id, true, buffer), player);
    }

    public void sendCustomClientBuffer(String id, INetworkDataBuffer buffer) {
    }

    public static FieldBuffer getFieldBuffer(BlockEntity tile) {
        FieldBuffer buffer = FIELD_BUFFERS.get(tile.getClass());
        if (buffer == null) {
            buffer = new FieldBuffer(tile.getClass());
            FIELD_BUFFERS.put(tile.getClass(), buffer);
            buffer.initClass(tile);
        }
        return buffer;
    }

    public static long countNetworkBytes(BlockEntity tile, boolean all, boolean gui) {
        if (!(tile instanceof INetworkFieldProvider)) {
            return 0L;
        }
        FieldBuffer buffer = NetworkManager.getFieldBuffer(tile);
        INetworkFieldProvider provider = (INetworkFieldProvider)tile;
        ObjectList data = CollectionUtils.createList();
        ByteBuf buf = Unpooled.buffer();
        if (all) {
            for (FieldBuffer.FieldInfo info : buffer.getAllInfos()) {
                data.add((FieldData)new FieldData(tile, info));
            }
        } else {
            for (String s : gui ? provider.getGuiFields() : provider.getNetworkFields()) {
                FieldBuffer.FieldInfo info = buffer.getInfo(s);
                if (info == null || info.isDefault(provider)) continue;
                data.add((FieldData)new FieldData(tile, info));
            }
        }
        NetworkUtils.writeEntries(new OutputBuffer(buf), data);
        return buf.writerIndex();
    }

    public static class FieldStorage {
        Map<BlockEntity, Set<String>> fieldsToSync = CollectionUtils.createLinkedMap();

        public boolean isEmpty() {
            return this.fieldsToSync.isEmpty();
        }

        public void addTileToUpdate(BlockEntity tile, String ... id) {
            ObjectLinkedOpenHashSet set = this.fieldsToSync.get(tile);
            if (set == null) {
                set = new ObjectLinkedOpenHashSet();
                this.fieldsToSync.put(tile, (Set<String>)set);
            }
            set.addAll(Arrays.asList(id));
        }

        public void addTileToUpdate(BlockEntity tile, String id) {
            ObjectLinkedOpenHashSet set = this.fieldsToSync.get(tile);
            if (set == null) {
                set = new ObjectLinkedOpenHashSet();
                this.fieldsToSync.put(tile, (Set<String>)set);
            }
            set.add((String)id);
        }

        public void clear() {
            this.fieldsToSync.clear();
        }

        public List<FieldData> getFields(BlockEntity tile) {
            Set<String> set = this.fieldsToSync.get(tile);
            if (set == null || set.isEmpty()) {
                return Collections.emptyList();
            }
            FieldBuffer buffer = NetworkManager.getFieldBuffer(tile);
            ArrayList<FieldData> data = new ArrayList<FieldData>();
            for (String s : set) {
                FieldBuffer.FieldInfo info = buffer.getInfo(s);
                if (info == null) continue;
                data.add(new FieldData(tile, info));
            }
            return data;
        }

        public Map<ChunkPos, Map<BlockPos, List<FieldData>>> getData() {
            Object2ObjectSortedMap map = CollectionUtils.createLinkedMap();
            for (Map.Entry<BlockEntity, Set<String>> entry : this.fieldsToSync.entrySet()) {
                BlockEntity tile = entry.getKey();
                if (tile.m_58901_()) continue;
                BlockPos pos = tile.m_58899_();
                ChunkPos chunkPos = new ChunkPos(pos);
                Object2ObjectSortedMap infos = (Object2ObjectSortedMap)map.get(chunkPos);
                if (infos == null) {
                    infos = CollectionUtils.createLinkedMap();
                    map.put(chunkPos, infos);
                }
                FieldBuffer buffer = NetworkManager.getFieldBuffer(tile);
                ArrayList<FieldData> data = new ArrayList<FieldData>();
                for (String s : entry.getValue()) {
                    FieldBuffer.FieldInfo info = buffer.getInfo(s);
                    if (info == null) continue;
                    data.add(new FieldData(tile, info));
                }
                if (data.isEmpty()) continue;
                infos.put(pos, data);
            }
            this.fieldsToSync.clear();
            return map;
        }
    }

    public static class FieldData {
        public String fieldName;
        public int index;
        public boolean isNew;
        public NetworkInfo.BitLevel bitLevel;
        public Object data;

        public FieldData(String field, Object theData) {
            this.fieldName = field;
            this.index = -1;
            this.isNew = false;
            this.bitLevel = NetworkInfo.BitLevel.BIT_0;
            this.data = theData;
        }

        public FieldData(int id, NetworkInfo.BitLevel level, Object theData) {
            this.index = id;
            this.isNew = true;
            this.bitLevel = level;
            this.data = theData;
        }

        public FieldData(BlockEntity tile, FieldBuffer.FieldInfo info) {
            this.fieldName = info.getFieldID();
            this.index = info.getIndex();
            this.isNew = info.isNew();
            this.bitLevel = info.getBits();
            this.data = info.getData(tile);
        }
    }
}

