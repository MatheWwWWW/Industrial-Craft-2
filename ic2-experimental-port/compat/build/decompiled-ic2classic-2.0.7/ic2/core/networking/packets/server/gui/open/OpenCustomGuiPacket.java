/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server.gui.open;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasCustomGui;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class OpenCustomGuiPacket
extends IC2Packet {
    ResourceLocation location;
    byte[] initialData;
    int windowId;

    public OpenCustomGuiPacket() {
    }

    public OpenCustomGuiPacket(ResourceLocation location, byte[] initialData, int windowId) {
        this.location = location;
        this.initialData = initialData;
        this.windowId = windowId;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130085_(this.location);
        buffer.m_130087_(this.initialData);
        buffer.writeInt(this.windowId);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.location = buffer.m_130281_();
        this.initialData = buffer.m_130052_();
        this.windowId = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        IHasCustomGui gui = IC2.NETWORKING.get().createCustomGui(source, this.location, new InputBuffer(this.initialData));
        if (gui == null) {
            throw new IllegalStateException("Packet Contains invalid Gui Data");
        }
        IC2.PLATFORM.launchGuiClient(source, gui, this.windowId);
    }
}

