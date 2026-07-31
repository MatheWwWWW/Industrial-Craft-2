/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.networking.packets.config;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.utils.config.gui.api.IRequestScreen;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ConfigAnswerPacket
extends IC2Packet {
    UUID id;
    byte[] data;

    public ConfigAnswerPacket() {
    }

    public ConfigAnswerPacket(UUID id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130077_(this.id);
        buffer.m_130087_(this.data);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.id = buffer.m_130259_();
        this.data = buffer.m_130052_();
    }

    @Override
    public void handlePacket(Player player) {
        this.processClient();
    }

    @OnlyIn(value=Dist.CLIENT)
    private void processClient() {
        Screen screen = Minecraft.m_91087_().f_91080_;
        if (screen instanceof IRequestScreen) {
            ((IRequestScreen)screen).receiveConfigData(this.id, new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])this.data)));
        }
    }
}

