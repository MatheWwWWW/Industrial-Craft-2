/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol;

public final class PacketFlow
extends Enum<PacketFlow> {
    public static final /* enum */ PacketFlow SERVERBOUND = new PacketFlow();
    public static final /* enum */ PacketFlow CLIENTBOUND = new PacketFlow();
    private static final /* synthetic */ PacketFlow[] $VALUES;

    public static PacketFlow[] values() {
        return (PacketFlow[])$VALUES.clone();
    }

    public static PacketFlow valueOf(String p_131352_) {
        return Enum.valueOf(PacketFlow.class, p_131352_);
    }

    public PacketFlow m_178539_() {
        return this == CLIENTBOUND ? SERVERBOUND : CLIENTBOUND;
    }

    private static /* synthetic */ PacketFlow[] m_178540_() {
        return new PacketFlow[]{SERVERBOUND, CLIENTBOUND};
    }

    static {
        $VALUES = PacketFlow.m_178540_();
    }
}

