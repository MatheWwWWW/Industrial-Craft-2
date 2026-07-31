/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;
import net.minecraft.resources.ResourceLocation;

public class ClientboundCustomQueryPacket
implements Packet<ClientLoginPacketListener> {
    private static final int f_179804_ = 0x100000;
    private final int f_134745_;
    private final ResourceLocation f_134746_;
    private final FriendlyByteBuf f_134747_;

    public ClientboundCustomQueryPacket(int p_179806_, ResourceLocation p_179807_, FriendlyByteBuf p_179808_) {
        this.f_134745_ = p_179806_;
        this.f_134746_ = p_179807_;
        this.f_134747_ = p_179808_;
    }

    public ClientboundCustomQueryPacket(FriendlyByteBuf p_179810_) {
        this.f_134745_ = p_179810_.m_130242_();
        this.f_134746_ = p_179810_.m_130281_();
        int $$1 = p_179810_.readableBytes();
        if ($$1 < 0 || $$1 > 0x100000) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
        this.f_134747_ = new FriendlyByteBuf(p_179810_.readBytes($$1));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134757_) {
        p_134757_.m_130130_(this.f_134745_);
        p_134757_.m_130085_(this.f_134746_);
        p_134757_.writeBytes(this.f_134747_.copy());
    }

    @Override
    public void m_5797_(ClientLoginPacketListener p_134754_) {
        p_134754_.m_7254_(this);
    }

    public int m_134755_() {
        return this.f_134745_;
    }

    public ResourceLocation m_179811_() {
        return this.f_134746_;
    }

    public FriendlyByteBuf m_179812_() {
        return this.f_134747_;
    }
}

