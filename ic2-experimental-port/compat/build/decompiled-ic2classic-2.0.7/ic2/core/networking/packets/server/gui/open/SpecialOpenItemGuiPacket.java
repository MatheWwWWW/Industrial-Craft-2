/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.Direction
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.networking.packets.server.gui.open;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SpecialOpenItemGuiPacket
extends IC2Packet {
    int windowID;
    int slotId;

    public SpecialOpenItemGuiPacket() {
    }

    public SpecialOpenItemGuiPacket(int windowID, int slotId) {
        this.windowID = windowID;
        this.slotId = slotId;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.slotId);
        buffer.writeInt(this.windowID);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.slotId = buffer.readInt();
        this.windowID = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        if (this.slotId == -1) {
            return;
        }
        try {
            Slot slot = source.f_36096_.m_38853_(this.slotId);
            ItemStack stack = slot.m_7993_();
            if (stack.m_41720_() instanceof IHasHeldSlotInventory) {
                IPortableInventory gui;
                IHasHeldSlotInventory inv = (IHasHeldSlotInventory)stack.m_41720_();
                IPortableInventory iPortableInventory = gui = slot.f_40218_ instanceof Inventory ? inv.getInventory(source, InteractionHand.MAIN_HAND, stack) : inv.getInventory(source, stack, slot);
                if (gui != null) {
                    if (source.f_36096_ != source.f_36095_) {
                        PlayerHandler.getClientHandler().cachedGUIs.push(new PlayerHandler.GuiEntry(source.f_36096_, Minecraft.m_91087_().f_91080_));
                    }
                    IC2.PLATFORM.launchGuiClient(source, InteractionHand.MAIN_HAND, Direction.NORTH, gui, this.windowID);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

