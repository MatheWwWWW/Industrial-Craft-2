/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.client.custom;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class PlayerConfigSetting
extends IC2Packet {
    public boolean boostOnSprint;

    public PlayerConfigSetting() {
    }

    public PlayerConfigSetting(boolean boostOnSprint) {
        this.boostOnSprint = boostOnSprint;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.boostOnSprint);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.boostOnSprint = buffer.readBoolean();
    }

    @Override
    public void handlePacket(Player source) {
        PlayerHandler.getHandler((Player)source).doSpringBoost = this.boostOnSprint;
    }
}

