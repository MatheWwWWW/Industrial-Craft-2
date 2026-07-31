/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.networking.packets.common;

import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.core.networking.NetworkManager;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;

public class ItemDataBufferPacket
extends IC2Packet {
    String id;
    INetworkDataBuffer data;
    ItemStack stack;
    boolean client;

    public ItemDataBufferPacket() {
    }

    public ItemDataBufferPacket(boolean client, String id, INetworkDataBuffer data, ItemStack stack) {
        this.client = client;
        this.id = id;
        this.data = data;
        this.stack = stack;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.client);
        buffer.m_130055_(this.stack);
        buffer.m_130070_(this.id);
        buffer.m_130085_(NetworkManager.getID(this.data));
        this.data.write(new OutputBuffer(buffer));
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.client = buffer.readBoolean();
        this.stack = buffer.m_130267_();
        this.id = buffer.m_130136_(Short.MAX_VALUE);
        this.data = NetworkManager.createBuffer(buffer.m_130281_());
        this.data.read(new InputBuffer(buffer));
    }

    @Override
    public void handlePacket(Player source) {
        if (!(this.stack.m_41720_() instanceof INetworkItemBufferEvent)) {
            return;
        }
        ((INetworkItemBufferEvent)this.stack.m_41720_()).onDataBufferReceived(this.stack, source, this.id, this.data, this.client ? Dist.CLIENT : Dist.DEDICATED_SERVER);
    }
}

