/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.client.custom;

import ic2.core.item.renders.features.ToolBoxRenderer;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class ScrollPacket
extends IC2Packet {
    boolean flag;

    public ScrollPacket() {
    }

    public ScrollPacket(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.flag);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.flag = buffer.readBoolean();
    }

    @Override
    public void handlePacket(Player source) {
        ToolBoxRenderer.INSTANCE.onServerScrollToolbox(source, this.flag);
    }
}

