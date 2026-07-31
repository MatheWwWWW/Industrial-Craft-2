/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.networking.packets.server;

import ic2.core.block.rendering.world.impl.SonarOverlay;
import ic2.core.item.base.features.ISonarProvider;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SpawnSonarPacket
extends IC2Packet {
    int color;
    int radius;
    long pos;

    public SpawnSonarPacket() {
    }

    public SpawnSonarPacket(int color, int radius, BlockPos pos) {
        this.color = color;
        this.radius = radius;
        this.pos = pos.m_121878_();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(this.color);
        buffer.m_130130_(this.radius);
        buffer.writeLong(this.pos);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.color = buffer.readInt();
        this.radius = buffer.m_130242_();
        this.pos = buffer.readLong();
    }

    @Override
    public void handlePacket(Player source) {
        SonarOverlay.INSTANCE.addSonar(ISonarProvider.simple(this.color, BlockPos.m_122022_((long)this.pos), this.radius), () -> ItemStack.f_41583_);
    }
}

