/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.networking.packets.server.gui.open;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OpenItemGuiPacket
extends IC2Packet {
    int windowID;
    int inventorySlot;

    public OpenItemGuiPacket() {
    }

    public OpenItemGuiPacket(int inventorySlot, int windowID) {
        this.windowID = windowID;
        this.inventorySlot = inventorySlot;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.inventorySlot);
        buffer.writeInt(this.windowID);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.inventorySlot = buffer.readInt();
        this.windowID = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        IPortableInventory gui;
        if (source.m_150109_().f_35977_ != this.inventorySlot && this.inventorySlot != -1) {
            return;
        }
        InteractionHand hand = this.inventorySlot == -1 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack stack = source.m_21120_(hand);
        if (stack.m_41720_() instanceof IHasHeldGui && (gui = ((IHasHeldGui)stack.m_41720_()).getInventory(source, hand, stack)) != null) {
            IC2.PLATFORM.launchGuiClient(source, hand, Direction.NORTH, gui, this.windowID);
        }
    }
}

