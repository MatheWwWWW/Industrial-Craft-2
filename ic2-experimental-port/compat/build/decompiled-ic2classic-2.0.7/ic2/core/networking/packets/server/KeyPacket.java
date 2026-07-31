/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server;

import ic2.core.IC2;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class KeyPacket
extends IC2Packet {
    int keyCode;

    public KeyPacket() {
    }

    public KeyPacket(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.keyCode);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.keyCode = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        IC2.KEYBOARD.processKeyUpdate(source, this.keyCode);
    }
}

