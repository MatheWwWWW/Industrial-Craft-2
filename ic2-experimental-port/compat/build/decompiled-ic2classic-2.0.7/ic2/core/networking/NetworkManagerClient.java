/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.networking;

import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.tile.INetworkFieldProvider;
import ic2.api.network.tile.PacketRange;
import ic2.core.IC2;
import ic2.core.networking.NetworkManager;
import ic2.core.networking.PacketManager;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.misc.NetworkUtils;
import ic2.core.networking.packets.client.ClientTileEventPacket;
import ic2.core.networking.packets.client.RequestFieldUpdate;
import ic2.core.networking.packets.client.TileDataBufferEventPacket;
import ic2.core.networking.packets.common.ItemDataBufferPacket;
import ic2.core.networking.packets.common.ItemEventPacket;
import ic2.core.networking.packets.server.gui.sync.CustomNetworkPacket;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class NetworkManagerClient
extends NetworkManager {
    @Override
    public void startGuiTracking(BlockEntity tile, Player player) {
    }

    @Override
    public void sendInitialGuiData(INetworkFieldProvider prov, Player player) {
        if (!(prov instanceof BlockEntity)) {
            return;
        }
        PacketManager.INSTANCE.sendToServer(new RequestFieldUpdate((BlockEntity)prov, true));
    }

    @Override
    public void handleInitialChange(BlockEntity prov, CompoundTag nbt) {
        if (nbt.m_128441_("ic2cData")) {
            List<NetworkManager.FieldData> data = NetworkUtils.readEntries(new InputBuffer(new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])nbt.m_128463_("ic2cData")))));
            if (data.isEmpty()) {
                return;
            }
            NetworkUtils.applyFields(prov, data, false, IC2.PLATFORM.getClientPlayerInstance());
        }
    }

    @Override
    public void updateGuiData(BlockEntity tile, Player player) {
    }

    @Override
    public void updateTileField(BlockEntity tile, String field) {
    }

    @Override
    public void updateGuiField(BlockEntity tile, String field) {
    }

    @Override
    public void requestInitialData(INetworkFieldProvider prov) {
        if (!(prov instanceof BlockEntity)) {
            return;
        }
        PacketManager.INSTANCE.sendToServer(new RequestFieldUpdate((BlockEntity)prov, false));
    }

    @Override
    public void sendTileEvent(BlockEntity tile, int key, int value, PacketRange target) {
    }

    @Override
    public void sendTileDataBufferEvent(BlockEntity tile, String id, INetworkDataBuffer buffer, PacketRange target) {
    }

    @Override
    public void sendClientTileEvent(BlockEntity tile, int key, int value) {
        PacketManager.INSTANCE.sendToServer(new ClientTileEventPacket(tile, key, value));
    }

    @Override
    public void sendClientTileDataBufferEvent(BlockEntity tile, String id, INetworkDataBuffer buffer) {
        if (NetworkManagerClient.getID(buffer) == null) {
            IC2.LOGGER.info("DataBuffer wasn't registered. Ignoring Packet Request");
            return;
        }
        PacketManager.INSTANCE.sendToServer(new TileDataBufferEventPacket(tile, id, buffer, false));
    }

    @Override
    public void sendItemEvent(Player player, ItemStack stack, int key, int value) {
    }

    @Override
    public void sendItemBuffer(Player player, ItemStack stack, String id, INetworkDataBuffer buffer) {
    }

    @Override
    public void sendClientItemEvent(ItemStack stack, int key, int value) {
        PacketManager.INSTANCE.sendToServer(new ItemEventPacket(stack, key, value, false));
    }

    @Override
    public void sendClientItemBuffer(ItemStack stack, String id, INetworkDataBuffer buffer) {
        PacketManager.INSTANCE.sendToServer(new ItemDataBufferPacket(false, id, buffer, stack));
    }

    @Override
    public void sendCustomBuffer(Player player, String id, INetworkDataBuffer buffer) {
    }

    @Override
    public void sendCustomClientBuffer(String id, INetworkDataBuffer buffer) {
        PacketManager.INSTANCE.sendToServer(new CustomNetworkPacket(id, false, buffer));
    }
}

