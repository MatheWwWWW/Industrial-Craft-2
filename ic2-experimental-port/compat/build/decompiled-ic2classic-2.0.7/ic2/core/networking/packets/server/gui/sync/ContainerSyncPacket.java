/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package ic2.core.networking.packets.server.gui.sync;

import ic2.api.network.container.IContainerDataEvent;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ContainerSyncPacket
extends IC2Packet {
    int key;
    int value;

    public ContainerSyncPacket() {
    }

    public ContainerSyncPacket(int key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.key);
        buffer.writeInt(this.value);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.key = buffer.readInt();
        this.value = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        AbstractContainerMenu abstractContainerMenu = source.f_36096_;
        if (abstractContainerMenu instanceof IContainerDataEvent) {
            IContainerDataEvent event = (IContainerDataEvent)abstractContainerMenu;
            event.onDataReceived(this.key, this.value);
        }
    }
}

