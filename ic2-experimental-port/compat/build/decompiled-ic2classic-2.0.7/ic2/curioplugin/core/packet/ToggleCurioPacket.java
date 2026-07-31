/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.curioplugin.core.packet;

import ic2.core.inventory.container.ContainerComponent;
import ic2.core.networking.packets.IC2Packet;
import ic2.curioplugin.core.client.CurioComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class ToggleCurioPacket
extends IC2Packet {
    boolean newValue;

    public ToggleCurioPacket() {
    }

    public ToggleCurioPacket(boolean newValue) {
        this.newValue = newValue;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.newValue);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.newValue = buffer.readBoolean();
    }

    @Override
    public void handlePacket(Player source) {
        CurioComponent comp;
        if (source.f_36096_ instanceof ContainerComponent && (comp = ((ContainerComponent)source.f_36096_).getComponent(CurioComponent.class)) != null) {
            comp.getVisible().setValue(this.newValue);
        }
    }
}

