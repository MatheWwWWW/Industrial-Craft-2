/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.networking.packets.client;

import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.networking.packets.server.gui.open.SpecialOpenItemGuiPacket;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OpenSlotPacket
extends IC2Packet {
    int slot;

    public OpenSlotPacket() {
    }

    public OpenSlotPacket(int slot) {
        this.slot = slot;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.slot);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.slot = buffer.readInt();
    }

    @Override
    public void handlePacket(Player player) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        int slotId = this.slot < 0 ? -this.slot - 1 : this.slot;
        Slot slot = player.f_36096_.m_38853_(slotId);
        ItemStack stack = slot.m_7993_();
        if (stack.m_41720_() instanceof IHasHeldSlotInventory) {
            IHasHeldSlotInventory inv = (IHasHeldSlotInventory)stack.m_41720_();
            if (slot.f_40218_ instanceof Inventory) {
                this.create(inv.getInventory(player, InteractionHand.MAIN_HAND, stack), (ServerPlayer)player, slotId);
            } else {
                this.create(inv.getInventory(player, stack, slot), (ServerPlayer)player, slotId);
            }
        }
    }

    protected void create(IHasGui gui, ServerPlayer player, int slot) {
        if (player.f_36096_ != player.f_36095_) {
            PlayerHandler.getHandler((Player)player).cachedGUIs.push(new PlayerHandler.GuiEntry(player.f_36096_));
        }
        player.m_9217_();
        player.m_9230_();
        PacketManager.INSTANCE.sendToPlayer(new SpecialOpenItemGuiPacket(player.f_8940_, slot), (Player)player);
        player.f_36096_ = gui.createContainer((Player)player, InteractionHand.MAIN_HAND, Direction.NORTH, player.f_8940_);
        player.m_143399_(player.f_36096_);
    }
}

