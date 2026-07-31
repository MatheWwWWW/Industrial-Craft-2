/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.network.NetworkEvent$ClientCustomPayloadEvent
 *  net.minecraftforge.network.NetworkEvent$Context
 *  net.minecraftforge.network.NetworkEvent$ServerCustomPayloadEvent
 */
package ic2.forge;

import ic2.core.IC2;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkEvent;

final class ForgeNetworkHandler {
    ForgeNetworkHandler() {
    }

    @SubscribeEvent
    public void onS2CPacket(NetworkEvent.ServerCustomPayloadEvent serverCustomPayloadEvent) {
        NetworkEvent.Context context = (NetworkEvent.Context)serverCustomPayloadEvent.getSource().get();
        IC2.network.get(false).onPacket((ByteBuf)serverCustomPayloadEvent.getPayload(), IC2.sideProxy.getPlayerInstance());
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public void onC2SPacket(NetworkEvent.ClientCustomPayloadEvent clientCustomPayloadEvent) {
        NetworkEvent.Context context = (NetworkEvent.Context)clientCustomPayloadEvent.getSource().get();
        IC2.network.get(true).onPacket((ByteBuf)clientCustomPayloadEvent.getPayload(), (Player)context.getSender());
        context.setPacketHandled(true);
    }
}

