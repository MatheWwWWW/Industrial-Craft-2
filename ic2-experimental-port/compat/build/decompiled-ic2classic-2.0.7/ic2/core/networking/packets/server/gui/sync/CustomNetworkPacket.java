/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.networking.packets.server.gui.sync;

import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.inventory.base.IHasCustomGui;
import ic2.core.networking.NetworkManager;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;

public class CustomNetworkPacket
extends IC2Packet {
    String id;
    boolean client;
    INetworkDataBuffer dataBuffer;

    public CustomNetworkPacket() {
    }

    public CustomNetworkPacket(String id, boolean client, INetworkDataBuffer dataBuffer) {
        this.id = id;
        this.client = client;
        this.dataBuffer = dataBuffer;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130070_(this.id);
        buffer.writeBoolean(this.client);
        buffer.m_130085_(NetworkManager.getID(this.dataBuffer));
        this.dataBuffer.write(new OutputBuffer(buffer));
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.id = buffer.m_130277_();
        this.client = buffer.readBoolean();
        this.dataBuffer = NetworkManager.createBuffer(buffer.m_130281_());
        this.dataBuffer.read(new InputBuffer(buffer));
    }

    @Override
    public void handlePacket(Player source) {
        IHasCustomGui gui = PlayerHandler.getHandler((Player)source).customGui;
        if (gui != null) {
            gui.receiveInitialData(this.id, this.dataBuffer, this.client ? Dist.CLIENT : Dist.DEDICATED_SERVER);
        }
    }
}

