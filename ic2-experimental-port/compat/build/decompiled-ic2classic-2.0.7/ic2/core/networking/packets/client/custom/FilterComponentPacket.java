/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.networking.packets.client.custom;

import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilterComponentPacket
extends IC2Packet {
    ItemStack item;
    int slot;

    public FilterComponentPacket() {
    }

    public FilterComponentPacket(ItemStack item, int slot) {
        this.item = item;
        this.slot = slot;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.item, false);
        buffer.writeByte(this.slot);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.item = buffer.m_130267_();
        this.slot = buffer.readByte();
    }

    @Override
    public void handlePacket(Player source) {
        AbstractContainerMenu container = source.f_36096_;
        try {
            Slot slot = container.m_38853_(this.slot);
            if (slot instanceof IGhostSlot) {
                ((IGhostSlot)slot).setFilter(this.item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

