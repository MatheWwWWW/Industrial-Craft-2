/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 */
package ic2.core.networking.packets.debug;

import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.networking.packets.debug.AnswerServerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class RequestServerData
extends IC2Packet {
    int slot;

    public RequestServerData() {
    }

    public RequestServerData(int slot) {
        this.slot = slot;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.slot);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.slot = buffer.m_130242_();
    }

    @Override
    public void handlePacket(Player source) {
        try {
            Slot s = source.f_36096_.m_38853_(this.slot);
            if (s == null) {
                return;
            }
            PacketManager.INSTANCE.sendToPlayer(new AnswerServerData(s.m_7993_().m_41783_()), source);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

