/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  io.netty.buffer.ByteBuf
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.network;

import com.mojang.authlib.GameProfile;
import ic2.api.network.IGrowingBuffer;
import ic2.api.network.INetworkItemEventListener;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Ic2Explosion;
import ic2.core.block.comp.Components;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.item.IHandHeldInventory;
import ic2.core.network.DataEncoder;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.IRpcProvider;
import ic2.core.network.NetworkManager;
import ic2.core.network.RpcHandler;
import ic2.core.network.SubPacketType;
import ic2.core.network.TeUpdate;
import ic2.core.proxy.SideProxyClient;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import io.netty.buffer.ByteBuf;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.zip.InflaterOutputStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

@Environment(value=EnvType.CLIENT)
public class NetworkManagerClient
extends NetworkManager {
    private GrowingBuffer largePacketBuffer;

    @Override
    protected boolean isClient() {
        return true;
    }

    @Override
    public void initiateClientItemEvent(ItemStack itemStack, int n) {
        try {
            GrowingBuffer growingBuffer = new GrowingBuffer(256);
            SubPacketType.ItemEvent.writeTo(growingBuffer);
            DataEncoder.encode(growingBuffer, itemStack, false);
            growingBuffer.writeInt(n);
            growingBuffer.flip();
            this.sendC2SPacket(growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    @Override
    public void initiateKeyUpdate(int n) {
        GrowingBuffer growingBuffer = new GrowingBuffer(5);
        SubPacketType.KeyUpdate.writeTo(growingBuffer);
        growingBuffer.writeInt(n);
        growingBuffer.flip();
        this.sendC2SPacket(growingBuffer);
    }

    @Override
    public void initiateClientTileEntityEvent(BlockEntity blockEntity, int n) {
        try {
            GrowingBuffer growingBuffer = new GrowingBuffer(32);
            SubPacketType.TileEntityEvent.writeTo(growingBuffer);
            DataEncoder.encode(growingBuffer, blockEntity, false);
            growingBuffer.writeInt(n);
            growingBuffer.flip();
            this.sendC2SPacket(growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    @Override
    public void initiateRpc(int n, Class<? extends IRpcProvider<?>> clazz, Object[] objectArray) {
        try {
            GrowingBuffer growingBuffer = new GrowingBuffer(256);
            SubPacketType.Rpc.writeTo(growingBuffer);
            growingBuffer.writeInt(n);
            growingBuffer.writeString(clazz.getName());
            DataEncoder.encode(growingBuffer, objectArray);
            growingBuffer.flip();
            this.sendC2SPacket(growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    @Override
    public void requestGUI(IHasGui iHasGui) {
        try {
            GrowingBuffer growingBuffer = new GrowingBuffer(32);
            SubPacketType.RequestGUI.writeTo(growingBuffer);
            if (iHasGui instanceof BlockEntity) {
                BlockEntity blockEntity = (BlockEntity)iHasGui;
                growingBuffer.writeBoolean(false);
                DataEncoder.encode(growingBuffer, blockEntity, false);
            } else {
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                if (!StackUtil.isEmpty(localPlayer.m_150109_().m_36056_()) && localPlayer.m_150109_().m_36056_().m_41720_() instanceof IHandHeldInventory || !StackUtil.isEmpty(localPlayer.m_21206_()) && localPlayer.m_21206_().m_41720_() instanceof IHandHeldInventory) {
                    growingBuffer.writeBoolean(true);
                } else {
                    IC2.sideProxy.displayError("An unknown GUI type was attempted to be displayed.\nThis could happen due to corrupted data from a player or a bug.\n\n(Technical information: " + iHasGui + ")", new Object[0]);
                }
            }
            growingBuffer.flip();
            this.sendC2SPacket(growingBuffer);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    @Override
    public void onPacket(ByteBuf byteBuf, Player player) {
        assert (player == null || player.f_19853_.f_46443_);
        try {
            this.onPacketData(GrowingBuffer.wrap(byteBuf), player);
        }
        catch (Throwable throwable) {
            IC2.log.warn(LogCategory.Network, throwable, "Network read failed");
            throw new RuntimeException(throwable);
        }
    }

    private void onPacketData(GrowingBuffer growingBuffer, Player player) throws IOException {
        if (!growingBuffer.hasAvailable()) {
            return;
        }
        SubPacketType subPacketType = SubPacketType.read(growingBuffer, false);
        if (subPacketType == null) {
            return;
        }
        switch (subPacketType) {
            case LargePacket: {
                int n = growingBuffer.readUnsignedByte();
                if ((n & 2) != 0) {
                    GrowingBuffer growingBuffer2;
                    if ((n & 1) != 0) {
                        growingBuffer2 = growingBuffer;
                    } else {
                        growingBuffer2 = this.largePacketBuffer;
                        if (growingBuffer2 == null) {
                            throw new IOException("unexpected large packet continuation");
                        }
                        growingBuffer.writeTo(growingBuffer2);
                        growingBuffer2.flip();
                        this.largePacketBuffer = null;
                    }
                    GrowingBuffer growingBuffer3 = new GrowingBuffer(growingBuffer2.available() * 2);
                    InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(growingBuffer3);
                    growingBuffer2.writeTo(inflaterOutputStream);
                    inflaterOutputStream.close();
                    growingBuffer3.flip();
                    switch (n >> 2) {
                        case 0: {
                            TeUpdate.receive(growingBuffer3);
                            break;
                        }
                        case 1: {
                            NetworkManagerClient.processChatPacket(growingBuffer3);
                            break;
                        }
                        case 2: {
                            NetworkManagerClient.processConsolePacket(growingBuffer3);
                        }
                    }
                    break;
                }
                if ((n & 1) != 0) {
                    assert (this.largePacketBuffer == null);
                    this.largePacketBuffer = new GrowingBuffer(32752);
                }
                if (this.largePacketBuffer == null) {
                    throw new IOException("unexpected large packet continuation");
                }
                growingBuffer.writeTo(this.largePacketBuffer);
                break;
            }
            case TileEntityEvent: {
                final Object object = DataEncoder.decodeDeferred(growingBuffer, BlockEntity.class);
                final int n = growingBuffer.readInt();
                IC2.sideProxy.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        BlockEntity blockEntity = (BlockEntity)DataEncoder.getValue(object, null);
                        if (blockEntity instanceof INetworkTileEntityEventListener) {
                            ((INetworkTileEntityEventListener)blockEntity).onNetworkEvent(n);
                        }
                    }
                });
                break;
            }
            case ItemEvent: {
                final GameProfile gameProfile = DataEncoder.decode((IGrowingBuffer)growingBuffer, GameProfile.class);
                final ItemStack itemStack = DataEncoder.decode((IGrowingBuffer)growingBuffer, ItemStack.class);
                final int n = growingBuffer.readInt();
                IC2.sideProxy.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        ClientLevel clientLevel = Minecraft.m_91087_().f_91073_;
                        for (Player player : clientLevel.m_6907_()) {
                            if ((gameProfile.getId() == null || !gameProfile.getId().equals(player.m_36316_().getId())) && (gameProfile.getId() != null || !gameProfile.getName().equals(player.m_36316_().getName()))) continue;
                            if (!(itemStack.m_41720_() instanceof INetworkItemEventListener)) break;
                            ((INetworkItemEventListener)itemStack.m_41720_()).onNetworkEvent(itemStack, player, n);
                            break;
                        }
                    }
                });
                break;
            }
            case ExplosionEffect: {
                Object object = DataEncoder.decodeDeferred(growingBuffer, Level.class);
                Vec3 vec3 = DataEncoder.decode((IGrowingBuffer)growingBuffer, Vec3.class);
                Ic2Explosion.Type type = DataEncoder.decodeEnum(growingBuffer, Ic2Explosion.Type.class);
                IC2.sideProxy.requestTick(false, () -> {
                    Level level = (Level)DataEncoder.getValue(object, null);
                    if (level == null || type == null) {
                        return;
                    }
                    switch (type) {
                        case Normal: {
                            level.m_7785_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, SoundEvents.f_11913_, SoundSource.BLOCKS, 4.0f, (1.0f + (level.f_46441_.m_188501_() - level.f_46441_.m_188501_()) * 0.2f) * 0.7f, true);
                            level.m_7106_((ParticleOptions)ParticleTypes.f_123812_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0.0, 0.0, 0.0);
                            break;
                        }
                        case Electrical: {
                            level.m_7785_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, Ic2SoundEvents.MACHINE_OVERLOAD, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                            level.m_7106_((ParticleOptions)ParticleTypes.f_123812_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0.0, 0.0, 0.0);
                            break;
                        }
                        case Heat: {
                            level.m_7785_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, SoundEvents.f_11937_, SoundSource.BLOCKS, 4.0f, (1.0f + (level.f_46441_.m_188501_() - level.f_46441_.m_188501_()) * 0.2f) * 0.7f, true);
                            level.m_7106_((ParticleOptions)ParticleTypes.f_123812_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0.0, 0.0, 0.0);
                            break;
                        }
                        case Nuclear: {
                            level.m_7785_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, Ic2SoundEvents.BLOCK_NUKE_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                            level.m_7106_((ParticleOptions)ParticleTypes.f_123812_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0.0, 0.0, 0.0);
                        }
                    }
                });
                break;
            }
            case Rpc: {
                RpcHandler.processRpcResponse(growingBuffer);
                break;
            }
            case TileEntityBlockComponent: {
                final ResourceLocation resourceLocation = (ResourceLocation)DataEncoder.getValue(DataEncoder.decode((IGrowingBuffer)growingBuffer, DataEncoder.EncodedType.ResourceLocation), null);
                final BlockPos blockPos = DataEncoder.decode((IGrowingBuffer)growingBuffer, BlockPos.class);
                String string = growingBuffer.readString();
                final Class clazz = Components.getClass(string);
                if (clazz == null) {
                    throw new IOException("invalid component: " + string);
                }
                int n = growingBuffer.readVarInt();
                if (n > 65536) {
                    throw new IOException("data length limit exceeded");
                }
                final byte[] byArray = new byte[n];
                growingBuffer.readFully(byArray);
                IC2.sideProxy.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        ClientLevel clientLevel = Minecraft.m_91087_().f_91073_;
                        if (!Util.getDimId((Level)clientLevel).equals((Object)resourceLocation)) {
                            return;
                        }
                        BlockEntity blockEntity = clientLevel.m_7702_(blockPos);
                        if (!(blockEntity instanceof Ic2TileEntity)) {
                            return;
                        }
                        Object t = ((Ic2TileEntity)blockEntity).getComponent(clazz);
                        if (t == null) {
                            return;
                        }
                        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
                        try {
                            ((TileEntityComponent)t).onNetworkUpdate(dataInputStream);
                        }
                        catch (IOException iOException) {
                            throw new RuntimeException(iOException);
                        }
                    }
                });
                break;
            }
            default: {
                this.onCommonPacketData(subPacketType, false, growingBuffer, player);
            }
        }
    }

    private static void processChatPacket(GrowingBuffer growingBuffer) {
        final String string = growingBuffer.readString();
        IC2.sideProxy.requestTick(false, new Runnable(){

            @Override
            public void run() {
                for (String string2 : string.split("[\\r\\n]+")) {
                    IC2.sideProxy.messagePlayer(null, string2, new Object[0]);
                }
            }
        });
    }

    private static void processConsolePacket(GrowingBuffer growingBuffer) {
        String string = growingBuffer.readString();
        PrintStream printStream = new PrintStream(new FileOutputStream(FileDescriptor.out));
        for (String string2 : string.split("[\\r\\n]+")) {
            printStream.println(string2);
        }
        printStream.flush();
    }

    @Override
    protected final void sendC2SPacket(GrowingBuffer growingBuffer) {
        ClientPacketListener clientPacketListener = SideProxyClient.mc.m_91403_();
        if (clientPacketListener == null) {
            return;
        }
        clientPacketListener.m_6198_().m_129512_((Packet)new ServerboundCustomPayloadPacket(channelId, new FriendlyByteBuf(NetworkManagerClient.makePacket(growingBuffer, true))));
    }
}

