/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.login;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ServerLoginPacketListener;

public class ServerboundCustomQueryPacket
implements Packet<ServerLoginPacketListener> {
    private static final int f_179821_ = 0x100000;
    private final int f_134825_;
    @Nullable
    private final FriendlyByteBuf f_134826_;

    public ServerboundCustomQueryPacket(int p_134829_, @Nullable FriendlyByteBuf p_134830_) {
        this.f_134825_ = p_134829_;
        this.f_134826_ = p_134830_;
    }

    public ServerboundCustomQueryPacket(FriendlyByteBuf p_179823_) {
        this.f_134825_ = p_179823_.m_130242_();
        this.f_134826_ = (FriendlyByteBuf)((Object)p_179823_.m_236868_(p_238039_ -> {
            int $$1 = p_238039_.readableBytes();
            if ($$1 < 0 || $$1 > 0x100000) {
                throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
            }
            return new FriendlyByteBuf(p_238039_.readBytes($$1));
        }));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134838_) {
        p_134838_.m_130130_(this.f_134825_);
        p_134838_.m_236821_(this.f_134826_, (p_238036_, p_238037_) -> p_238036_.writeBytes(p_238037_.slice()));
    }

    @Override
    public void m_5797_(ServerLoginPacketListener p_134836_) {
        p_134836_.m_7223_(this);
    }

    public int m_179824_() {
        return this.f_134825_;
    }

    @Nullable
    public FriendlyByteBuf m_179825_() {
        return this.f_134826_;
    }
}

