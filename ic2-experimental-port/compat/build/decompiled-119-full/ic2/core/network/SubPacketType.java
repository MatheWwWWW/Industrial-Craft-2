/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.network;

import ic2.core.IC2;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.LogCategory;

public enum SubPacketType {
    Rpc(true, true),
    TileEntityEvent(true, true),
    ItemEvent(true, true),
    PlayerItemData(true, true),
    ContainerData(true, true),
    ContainerEvent(true, true),
    HandHeldInvData(true, true),
    LargePacket(true, false),
    GuiDisplay(true, false),
    ExplosionEffect(true, false),
    TileEntityBlockComponent(true, false),
    TileEntityBlockLandEffect(true, false),
    TileEntityBlockRunEffect(true, false),
    KeyUpdate(false, true),
    TileEntityData(false, true),
    RequestGUI(false, true);

    private boolean serverToClient;
    private boolean clientToServer;
    private static final SubPacketType[] values;

    private SubPacketType(boolean bl, boolean bl2) {
        this.serverToClient = bl;
        this.clientToServer = bl2;
    }

    public void writeTo(GrowingBuffer growingBuffer) {
        growingBuffer.writeByte(this.getId());
    }

    public int getId() {
        return this.ordinal() + 1;
    }

    public static SubPacketType read(GrowingBuffer growingBuffer, boolean bl) {
        int n = growingBuffer.readUnsignedByte() - 1;
        if (n < 0 || n >= values.length) {
            IC2.log.warn(LogCategory.Network, "Invalid sub packet type: %d", n);
            return null;
        }
        SubPacketType subPacketType = values[n];
        if (bl && !subPacketType.clientToServer || !bl && !subPacketType.serverToClient) {
            IC2.log.warn(LogCategory.Network, "Invalid sub packet type %s for side %s", subPacketType.name(), bl ? "server" : "client");
            return null;
        }
        return subPacketType;
    }

    static {
        values = SubPacketType.values();
        if (values.length > 255) {
            throw new RuntimeException("too many sub packet types");
        }
    }
}

