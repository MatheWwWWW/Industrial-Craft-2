/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package ic2.curioplugin.core.packet;

import ic2.core.IC2;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.IC2Packet;
import ic2.curioplugin.core.client.CurioComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CurioScrollPacket
extends IC2Packet {
    int scrollOffset;

    public CurioScrollPacket() {
    }

    public CurioScrollPacket(int scrollOffset) {
        this.scrollOffset = scrollOffset;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.scrollOffset);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.scrollOffset = buffer.m_130242_();
    }

    @Override
    public void handlePacket(Player source) {
        AbstractContainerMenu abstractContainerMenu = source.f_36096_;
        if (abstractContainerMenu instanceof ContainerComponent) {
            ContainerComponent container = (ContainerComponent)abstractContainerMenu;
            container.setCurioOffset(this.scrollOffset);
            if (IC2.PLATFORM.isRendering()) {
                CurioComponent comp = container.getComponent(CurioComponent.class);
                if (comp != null) {
                    comp.updateSlots();
                }
            } else {
                PacketManager.INSTANCE.sendToPlayer(new CurioScrollPacket(this.scrollOffset), source);
            }
        }
    }
}

