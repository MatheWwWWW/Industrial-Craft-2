/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.Connection
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.ServerGamePacketListenerImpl
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ContainerListener
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.network;

import ic2.api.network.ClientModifiable;
import ic2.api.network.IGrowingBuffer;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkItemEventListener;
import ic2.api.network.INetworkManager;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Ic2Explosion;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.event.WorldData;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.IHandHeldSubInventory;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.network.DataEncoder;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.IPlayerItemDataListener;
import ic2.core.network.IRpcProvider;
import ic2.core.network.RpcHandler;
import ic2.core.network.SubPacketType;
import ic2.core.network.TeUpdate;
import ic2.core.network.TeUpdateDataServer;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class NetworkManager
implements INetworkManager {
    private static final int maxPacketDataLength = 32766;
    public static final ResourceLocation channelId = IC2.getIdentifier("m");

    protected boolean isClient() {
        return false;
    }

    public void onTickEnd(WorldData worldData) {
        try {
            TeUpdate.send(worldData, this);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    public final void sendPlayerItemData(Player player, int n, Object ... objectArray) {
        GrowingBuffer growingBuffer = new GrowingBuffer(256);
        try {
            SubPacketType.PlayerItemData.writeTo(growingBuffer);
            growingBuffer.writeByte(n);
            DataEncoder.encode(growingBuffer, ((ItemStack)player.m_150109_().f_35974_.get(n)).m_41720_(), false);
            growingBuffer.writeVarInt(objectArray.length);
            for (Object object : objectArray) {
                DataEncoder.encode(growingBuffer, object);
            }
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer.flip();
        if (!this.isClient()) {
            this.sendS2CPacket((ServerPlayer)player, growingBuffer, true);
        } else {
            this.sendC2SPacket(growingBuffer);
        }
    }

    @Override
    public final void updateTileEntityField(BlockEntity blockEntity, String string) {
        if (!this.isClient()) {
            NetworkManager.getTeUpdateData(blockEntity).addGlobalField(string);
        } else if (this.getClientModifiableField(blockEntity.getClass(), string) == null) {
            IC2.log.warn(LogCategory.Network, "Field update for %s failed.", blockEntity);
        } else {
            GrowingBuffer growingBuffer = new GrowingBuffer(64);
            try {
                SubPacketType.TileEntityData.writeTo(growingBuffer);
                DataEncoder.encode(growingBuffer, blockEntity, false);
                NetworkManager.writeFieldData(blockEntity, string, growingBuffer);
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            growingBuffer.flip();
            this.sendC2SPacket(growingBuffer);
        }
    }

    private Field getClientModifiableField(Class<?> clazz, String string) {
        Field field = ReflectionUtil.getFieldRecursive(clazz, string);
        if (field == null) {
            IC2.log.warn(LogCategory.Network, "Can't find field %s in %s.", string, clazz.getName());
            return null;
        }
        if (field.getAnnotation(ClientModifiable.class) == null) {
            IC2.log.warn(LogCategory.Network, "The field %s in %s is not modifiable.", string, clazz.getName());
            return null;
        }
        return field;
    }

    private static TeUpdateDataServer getTeUpdateData(BlockEntity blockEntity) {
        assert (IC2.sideProxy.isSimulating());
        if (blockEntity == null) {
            throw new NullPointerException();
        }
        WorldData worldData = WorldData.get(blockEntity.m_58904_());
        TeUpdateDataServer teUpdateDataServer = worldData.tesToUpdate.get(blockEntity);
        if (teUpdateDataServer == null) {
            teUpdateDataServer = new TeUpdateDataServer();
            worldData.tesToUpdate.put(blockEntity, teUpdateDataServer);
        }
        return teUpdateDataServer;
    }

    public final void updateTileEntityFieldTo(BlockEntity blockEntity, String string, ServerPlayer serverPlayer) {
        assert (!this.isClient());
        NetworkManager.getTeUpdateData(blockEntity).addPlayerField(string, serverPlayer);
    }

    public final void sendComponentUpdate(Ic2TileEntity ic2TileEntity, String string, ServerPlayer serverPlayer, GrowingBuffer growingBuffer) {
        assert (!this.isClient());
        if (serverPlayer.m_20193_() != ic2TileEntity.m_58904_()) {
            throw new IllegalArgumentException("mismatched world (te " + ic2TileEntity.m_58904_() + ", player " + serverPlayer.m_20193_() + ")");
        }
        GrowingBuffer growingBuffer2 = new GrowingBuffer(64);
        try {
            SubPacketType.TileEntityBlockComponent.writeTo(growingBuffer2);
            DataEncoder.encode(growingBuffer2, ic2TileEntity, false);
            growingBuffer2.writeString(string);
            growingBuffer2.writeVarInt(growingBuffer.available());
            growingBuffer.writeTo(growingBuffer2);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer2.flip();
        this.sendS2CPacket(serverPlayer, growingBuffer2, true);
    }

    @Override
    public final void initiateTileEntityEvent(BlockEntity blockEntity, int n, boolean bl) {
        assert (!this.isClient());
        if (blockEntity.m_58904_().m_6907_().isEmpty()) {
            return;
        }
        GrowingBuffer growingBuffer = new GrowingBuffer(32);
        try {
            SubPacketType.TileEntityEvent.writeTo(growingBuffer);
            DataEncoder.encode(growingBuffer, blockEntity, false);
            growingBuffer.writeInt(n);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer.flip();
        for (ServerPlayer serverPlayer : NetworkManager.getPlayersInRange(blockEntity.m_58904_(), blockEntity.m_58899_(), new ArrayList())) {
            int n2;
            int n3;
            if (bl && (n3 = (int)((double)blockEntity.m_58899_().m_123341_() + 0.5 - serverPlayer.m_20185_())) * n3 + (n2 = (int)((double)blockEntity.m_58899_().m_123343_() + 0.5 - serverPlayer.m_20189_())) * n2 > 400) continue;
            this.sendS2CPacket(serverPlayer, growingBuffer, false);
        }
    }

    @Override
    public final void initiateItemEvent(Player player, ItemStack itemStack, int n, boolean bl) {
        if (StackUtil.isEmpty(itemStack)) {
            throw new NullPointerException("invalid stack: " + StackUtil.toStringSafe(itemStack));
        }
        assert (!this.isClient());
        GrowingBuffer growingBuffer = new GrowingBuffer(256);
        try {
            SubPacketType.ItemEvent.writeTo(growingBuffer);
            DataEncoder.encode(growingBuffer, player.m_36316_(), false);
            DataEncoder.encode(growingBuffer, itemStack, false);
            growingBuffer.writeInt(n);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        growingBuffer.flip();
        for (ServerPlayer serverPlayer : NetworkManager.getPlayersInRange(player.m_20193_(), player.m_20183_(), new ArrayList())) {
            int n2;
            int n3;
            if (bl && (n3 = (int)(player.m_20185_() - serverPlayer.m_20185_())) * n3 + (n2 = (int)(player.m_20189_() - serverPlayer.m_20189_())) * n2 > 400) continue;
            this.sendS2CPacket(serverPlayer, growingBuffer, false);
        }
    }

    @Override
    public void initiateClientItemEvent(ItemStack itemStack, int n) {
        assert (false);
    }

    @Override
    public void initiateClientTileEntityEvent(BlockEntity blockEntity, int n) {
        assert (false);
    }

    public void initiateRpc(int n, Class<? extends IRpcProvider<?>> clazz, Object[] objectArray) {
        assert (false);
    }

    public void requestGUI(IHasGui iHasGui) {
        assert (false);
    }

    private final void handleSubData(GrowingBuffer growingBuffer, ItemStack itemStack, Integer n) {
        boolean bl = n != null && itemStack.m_41720_() instanceof IHandHeldSubInventory;
        growingBuffer.writeBoolean(bl);
        if (bl) {
            growingBuffer.writeShort(n);
        }
    }

    public final void sendInitialData(BlockEntity blockEntity, ServerPlayer serverPlayer) {
        assert (!this.isClient());
        if (blockEntity instanceof INetworkDataProvider) {
            TeUpdateDataServer teUpdateDataServer = NetworkManager.getTeUpdateData(blockEntity);
            for (String string : ((INetworkDataProvider)blockEntity).getNetworkedFields()) {
                teUpdateDataServer.addPlayerField(string, serverPlayer);
            }
        }
    }

    @Override
    public final void sendInitialData(BlockEntity blockEntity) {
        assert (!this.isClient());
        if (blockEntity instanceof INetworkDataProvider) {
            TeUpdateDataServer teUpdateDataServer = NetworkManager.getTeUpdateData(blockEntity);
            List<String> list = ((INetworkDataProvider)blockEntity).getNetworkedFields();
            for (String string : list) {
                teUpdateDataServer.addGlobalField(string);
            }
            if (TeUpdate.debug) {
                IC2.log.info(LogCategory.Network, "Sending initial TE data for %s (%s).", Util.formatPosition(blockEntity), list);
            }
        }
    }

    public final void sendChat(ServerPlayer serverPlayer, String string) {
        assert (!this.isClient());
        GrowingBuffer growingBuffer = new GrowingBuffer(string.length() * 2);
        growingBuffer.writeString(string);
        growingBuffer.flip();
        this.sendLargePacket(serverPlayer, 1, growingBuffer);
    }

    public final void sendConsole(ServerPlayer serverPlayer, String string) {
        assert (!this.isClient());
        GrowingBuffer growingBuffer = new GrowingBuffer(string.length() * 2);
        growingBuffer.writeString(string);
        growingBuffer.flip();
        this.sendLargePacket(serverPlayer, 2, growingBuffer);
    }

    public final void sendContainerFields(ContainerBase<?> containerBase, String ... stringArray) {
        for (String string : stringArray) {
            this.sendContainerField(containerBase, string);
        }
    }

    public final void sendContainerField(ContainerBase<?> containerBase, String string) {
        if (this.isClient() && this.getClientModifiableField(((Object)containerBase).getClass(), string) == null) {
            IC2.log.warn(LogCategory.Network, "Field update for %s failed.", new Object[]{containerBase});
            return;
        }
        GrowingBuffer growingBuffer = new GrowingBuffer(256);
        try {
            SubPacketType.ContainerData.writeTo(growingBuffer);
            growingBuffer.writeInt(containerBase.f_38840_);
            NetworkManager.writeFieldData(containerBase, string, growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer.flip();
        if (!this.isClient()) {
            for (ContainerListener containerListener : containerBase.getListeners()) {
                if (!(containerListener instanceof ServerPlayer)) continue;
                this.sendS2CPacket((ServerPlayer)containerListener, growingBuffer, false);
            }
        } else {
            this.sendC2SPacket(growingBuffer);
        }
    }

    public final void sendContainerEvent(ContainerBase<?> containerBase, String string) {
        GrowingBuffer growingBuffer = new GrowingBuffer(64);
        SubPacketType.ContainerEvent.writeTo(growingBuffer);
        growingBuffer.writeInt(containerBase.f_38840_);
        growingBuffer.writeString(string);
        growingBuffer.flip();
        if (!this.isClient()) {
            for (ContainerListener containerListener : containerBase.getListeners()) {
                if (!(containerListener instanceof ServerPlayer)) continue;
                this.sendS2CPacket((ServerPlayer)containerListener, growingBuffer, false);
            }
        } else {
            this.sendC2SPacket(growingBuffer);
        }
    }

    public final void sendHandHeldInvField(ContainerBase<?> containerBase, String string) {
        if (!(containerBase.base instanceof HandHeldInventory)) {
            IC2.log.warn(LogCategory.Network, "Invalid container (%s) sent for field update.", new Object[]{containerBase});
            return;
        }
        if (this.isClient() && this.getClientModifiableField(containerBase.base.getClass(), string) == null) {
            IC2.log.warn(LogCategory.Network, "Field update for %s failed.", new Object[]{containerBase});
            return;
        }
        GrowingBuffer growingBuffer = new GrowingBuffer(256);
        try {
            SubPacketType.HandHeldInvData.writeTo(growingBuffer);
            growingBuffer.writeInt(containerBase.f_38840_);
            NetworkManager.writeFieldData(containerBase.base, string, growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer.flip();
        if (!this.isClient()) {
            for (ContainerListener containerListener : containerBase.getListeners()) {
                if (!(containerListener instanceof ServerPlayer)) continue;
                this.sendS2CPacket((ServerPlayer)containerListener, growingBuffer, false);
            }
        } else {
            this.sendC2SPacket(growingBuffer);
        }
    }

    final void sendLargePacket(ServerPlayer serverPlayer, int n, GrowingBuffer growingBuffer) {
        boolean bl;
        GrowingBuffer growingBuffer2 = new GrowingBuffer(16384);
        growingBuffer2.writeShort(0);
        try {
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(growingBuffer2);
            growingBuffer.writeTo(deflaterOutputStream);
            deflaterOutputStream.close();
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        growingBuffer2.flip();
        boolean bl2 = true;
        do {
            boolean bl3 = bl = growingBuffer2.available() <= 32766;
            if (!bl2) {
                growingBuffer2.skipBytes(-2);
            }
            SubPacketType.LargePacket.writeTo(growingBuffer2);
            int n2 = 0;
            if (bl2) {
                n2 |= 1;
            }
            if (bl) {
                n2 |= 2;
            }
            growingBuffer2.write(n2 |= n << 2);
            growingBuffer2.skipBytes(-2);
            if (bl) {
                this.sendS2CPacket(serverPlayer, growingBuffer2, true);
                assert (!growingBuffer2.hasAvailable());
            } else {
                this.sendS2CPacket(serverPlayer, growingBuffer2.copy(32766), true);
            }
            bl2 = false;
        } while (!bl);
    }

    public void onPacket(ByteBuf byteBuf, Player player) {
        assert (!player.f_19853_.f_46443_);
        try {
            this.onPacketData(GrowingBuffer.wrap(byteBuf), player);
        }
        catch (Throwable throwable) {
            IC2.log.warn(LogCategory.Network, throwable, "Network read failed");
            throw new RuntimeException(throwable);
        }
    }

    private void onPacketData(GrowingBuffer growingBuffer, final Player player) throws IOException {
        if (!growingBuffer.hasAvailable()) {
            return;
        }
        SubPacketType subPacketType = SubPacketType.read(growingBuffer, true);
        if (subPacketType == null) {
            return;
        }
        switch (subPacketType) {
            case ItemEvent: {
                final ItemStack itemStack = DataEncoder.decode((IGrowingBuffer)growingBuffer, ItemStack.class);
                final int n = growingBuffer.readInt();
                if (!(itemStack.m_41720_() instanceof INetworkItemEventListener)) break;
                IC2.sideProxy.requestTick(true, new Runnable(){

                    @Override
                    public void run() {
                        ((INetworkItemEventListener)itemStack.m_41720_()).onNetworkEvent(itemStack, player, n);
                    }
                });
                break;
            }
            case KeyUpdate: {
                final int n = growingBuffer.readInt();
                IC2.sideProxy.requestTick(true, new Runnable(){

                    @Override
                    public void run() {
                        IC2.keyboard.processKeyUpdate(player, n);
                    }
                });
                break;
            }
            case TileEntityEvent: {
                final Object object = DataEncoder.decodeDeferred(growingBuffer, BlockEntity.class);
                final int n = growingBuffer.readInt();
                IC2.sideProxy.requestTick(true, new Runnable(){

                    @Override
                    public void run() {
                        BlockEntity blockEntity = (BlockEntity)DataEncoder.getValue(object, player.m_20194_());
                        if (blockEntity instanceof INetworkClientTileEntityEventListener) {
                            ((INetworkClientTileEntityEventListener)blockEntity).onNetworkEvent(player, n);
                        }
                    }
                });
                break;
            }
            case RequestGUI: {
                final boolean bl = growingBuffer.readBoolean();
                Object object = bl ? null : DataEncoder.decodeDeferred(growingBuffer, BlockEntity.class);
                IC2.sideProxy.requestTick(true, new Runnable(){

                    @Override
                    public void run() {
                        if (bl) {
                            for (InteractionHand interactionHand : Util.HANDS) {
                                ItemStack itemStack = player.m_21120_(interactionHand);
                                if (itemStack == null || !(itemStack.m_41720_() instanceof IHandHeldInventory)) continue;
                                IHasGui iHasGui = ((IHandHeldInventory)itemStack.m_41720_()).getInventory(player, interactionHand, itemStack);
                                iHasGui.openManagedItem(player, interactionHand, null);
                                break;
                            }
                        }
                    }
                });
                break;
            }
            case Rpc: {
                RpcHandler.processRpcRequest(growingBuffer, (ServerPlayer)player);
                break;
            }
            default: {
                this.onCommonPacketData(subPacketType, true, growingBuffer, player);
            }
        }
    }

    protected void onCommonPacketData(SubPacketType subPacketType, boolean bl, GrowingBuffer growingBuffer, final Player player) throws IOException {
        switch (subPacketType) {
            case PlayerItemData: {
                final byte by = growingBuffer.readByte();
                final Item item = DataEncoder.decode((IGrowingBuffer)growingBuffer, Item.class);
                int n = growingBuffer.readVarInt();
                final Object[] objectArray = new Object[n];
                for (int i = 0; i < n; ++i) {
                    objectArray[i] = DataEncoder.decode(growingBuffer);
                }
                if (by < 0 || by >= 9) break;
                IC2.sideProxy.requestTick(bl, new Runnable(){

                    @Override
                    public void run() {
                        for (int i = 0; i < objectArray.length; ++i) {
                            objectArray[i] = DataEncoder.getValue(objectArray[i], player.m_20194_());
                        }
                        ItemStack itemStack = (ItemStack)player.m_150109_().f_35974_.get(by);
                        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() == item && item instanceof IPlayerItemDataListener) {
                            ((IPlayerItemDataListener)item).onPlayerItemNetworkData(player, by, objectArray);
                        }
                    }
                });
                break;
            }
            case ContainerData: {
                final int n = growingBuffer.readInt();
                final String string = growingBuffer.readString();
                final Object object = DataEncoder.decode(growingBuffer);
                IC2.sideProxy.requestTick(bl, new Runnable(){

                    @Override
                    public void run() {
                        if (player.f_36096_ instanceof ContainerBase && player.f_36096_.f_38840_ == n && (NetworkManager.this.isClient() || NetworkManager.this.getClientModifiableField(player.f_36096_.getClass(), string) != null)) {
                            ReflectionUtil.setValueRecursive(player.f_36096_, string, DataEncoder.getValue(object, player.m_20194_()));
                        }
                    }
                });
                break;
            }
            case ContainerEvent: {
                final int n = growingBuffer.readInt();
                final String string = growingBuffer.readString();
                IC2.sideProxy.requestTick(bl, new Runnable(){

                    @Override
                    public void run() {
                        if (player.f_36096_ instanceof ContainerBase && player.f_36096_.f_38840_ == n) {
                            ((ContainerBase)player.f_36096_).onContainerEvent(string);
                        }
                    }
                });
                break;
            }
            case HandHeldInvData: {
                final int n = growingBuffer.readInt();
                final String string = growingBuffer.readString();
                final Object object = DataEncoder.decode(growingBuffer);
                IC2.sideProxy.requestTick(bl, new Runnable(){

                    @Override
                    public void run() {
                        if (player.f_36096_ instanceof ContainerBase && player.f_36096_.f_38840_ == n) {
                            ContainerBase containerBase = (ContainerBase)player.f_36096_;
                            if (containerBase.base instanceof HandHeldInventory && (NetworkManager.this.isClient() || NetworkManager.this.getClientModifiableField(containerBase.base.getClass(), string) != null)) {
                                ReflectionUtil.setValueRecursive(containerBase.base, string, DataEncoder.getValue(object, player.m_20194_()));
                            }
                        }
                    }
                });
                break;
            }
            case TileEntityData: {
                final Object object = DataEncoder.decodeDeferred(growingBuffer, BlockEntity.class);
                final String string = growingBuffer.readString();
                final Object object2 = DataEncoder.decode(growingBuffer);
                IC2.sideProxy.requestTick(bl, new Runnable(){

                    @Override
                    public void run() {
                        BlockEntity blockEntity = (BlockEntity)DataEncoder.getValue(object, player.m_20194_());
                        if (blockEntity != null && (NetworkManager.this.isClient() || NetworkManager.this.getClientModifiableField(blockEntity.getClass(), string) != null)) {
                            ReflectionUtil.setValueRecursive(blockEntity, string, DataEncoder.getValue(object2, player.m_20194_()));
                        }
                    }
                });
                break;
            }
            default: {
                IC2.log.warn(LogCategory.Network, "Unhandled packet type: %s", subPacketType.name());
            }
        }
    }

    public void initiateKeyUpdate(int n) {
    }

    public void sendLoginData() {
    }

    public final void initiateExplosionEffect(Level level, Vec3 vec3, Ic2Explosion.Type type) {
        assert (!this.isClient());
        try {
            GrowingBuffer growingBuffer = new GrowingBuffer(32);
            SubPacketType.ExplosionEffect.writeTo(growingBuffer);
            DataEncoder.encode(growingBuffer, level, false);
            DataEncoder.encode(growingBuffer, vec3, false);
            DataEncoder.encode(growingBuffer, (Object)type, false);
            growingBuffer.flip();
            for (Player player : level.m_6907_()) {
                if (!(player instanceof ServerPlayer) || !(player.m_20275_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_) < 128.0)) continue;
                this.sendS2CPacket((ServerPlayer)player, growingBuffer, false);
            }
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    protected void sendC2SPacket(GrowingBuffer growingBuffer) {
        throw new UnsupportedOperationException("can't send c2s packet serverside");
    }

    protected final void sendS2CPacket(ServerPlayer serverPlayer, GrowingBuffer growingBuffer, boolean bl) {
        assert (!this.isClient());
        ByteBuf byteBuf = NetworkManager.makePacket(growingBuffer, bl);
        ServerGamePacketListenerImpl serverGamePacketListenerImpl = serverPlayer.f_8906_;
        if (serverGamePacketListenerImpl == null) {
            return;
        }
        Connection connection = serverGamePacketListenerImpl.m_6198_();
        if (connection == null || !connection.m_129536_()) {
            return;
        }
        ClientboundCustomPayloadPacket clientboundCustomPayloadPacket = new ClientboundCustomPayloadPacket(channelId, new FriendlyByteBuf(byteBuf));
        connection.m_129512_((Packet)clientboundCustomPayloadPacket);
    }

    static <T extends Collection<ServerPlayer>> T getPlayersInRange(Level level, BlockPos blockPos, T t) {
        if (!(level instanceof ServerLevel)) {
            return t;
        }
        ((ServerLevel)level).m_7726_().f_8325_.m_183262_(new ChunkPos(blockPos), false).forEach(t::add);
        return t;
    }

    static void writeFieldData(Object object, String string, GrowingBuffer growingBuffer) throws IOException {
        int n = string.indexOf(61);
        if (n != -1) {
            growingBuffer.writeString(string.substring(0, n));
            DataEncoder.encode(growingBuffer, string.substring(n + 1));
        } else {
            growingBuffer.writeString(string);
            try {
                DataEncoder.encode(growingBuffer, ReflectionUtil.getValueRecursive(object, string));
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new RuntimeException("Can't find field " + string + " in " + object.getClass().getName(), noSuchFieldException);
            }
        }
    }

    protected static ByteBuf makePacket(GrowingBuffer growingBuffer, boolean bl) {
        return growingBuffer.toByteBuf(bl);
    }
}

