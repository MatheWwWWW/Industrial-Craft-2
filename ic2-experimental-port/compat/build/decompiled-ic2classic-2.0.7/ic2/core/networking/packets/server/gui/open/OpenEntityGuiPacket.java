/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server.gui.open;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasGui;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class OpenEntityGuiPacket
extends IC2Packet {
    int entityId;
    boolean mainHand;
    int windowID;

    public OpenEntityGuiPacket() {
    }

    public OpenEntityGuiPacket(int entityId, boolean mainHand, int windowID) {
        this.entityId = entityId;
        this.mainHand = mainHand;
        this.windowID = windowID;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeBoolean(this.mainHand);
        buffer.writeInt(this.windowID);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.mainHand = buffer.readBoolean();
        this.windowID = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        Entity entity = source.f_19853_.m_6815_(this.entityId);
        if (!(entity instanceof IHasGui)) {
            throw new RuntimeException("Packet Contains invalid Gui Data");
        }
        IHasGui gui = (IHasGui)entity;
        IC2.PLATFORM.launchGuiClient(source, this.mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, Direction.DOWN, gui, this.windowID);
    }
}

