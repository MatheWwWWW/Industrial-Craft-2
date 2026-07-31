/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.networking.packets.server.gui.open;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasGui;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class OpenTileGuiPacket
extends IC2Packet {
    BlockPos pos;
    boolean mainHand;
    Direction side;
    int windowID;

    public OpenTileGuiPacket() {
    }

    public OpenTileGuiPacket(BlockPos pos, InteractionHand hand, Direction side, int windowID) {
        this.pos = pos;
        this.mainHand = hand == InteractionHand.MAIN_HAND;
        this.side = side;
        this.windowID = windowID;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.pos.m_121878_());
        buffer.writeBoolean(this.mainHand);
        buffer.writeByte(this.side.m_122411_());
        buffer.writeInt(this.windowID);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = BlockPos.m_122022_((long)buffer.readLong());
        this.mainHand = buffer.readBoolean();
        this.side = Direction.m_122376_((int)buffer.readByte());
        this.windowID = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        BlockEntity tile = source.f_19853_.m_7702_(this.pos);
        if (!(tile instanceof IHasGui)) {
            throw new RuntimeException("Packet Contains invalid Gui Data");
        }
        IC2.PLATFORM.launchGuiClient(source, this.mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, this.side, (IHasGui)tile, this.windowID);
    }
}

