/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundSignUpdatePacket
implements Packet<ServerGamePacketListener> {
    private static final int f_179788_ = 384;
    private final BlockPos f_134645_;
    private final String[] f_134646_;

    public ServerboundSignUpdatePacket(BlockPos p_134649_, String p_134650_, String p_134651_, String p_134652_, String p_134653_) {
        this.f_134645_ = p_134649_;
        this.f_134646_ = new String[]{p_134650_, p_134651_, p_134652_, p_134653_};
    }

    public ServerboundSignUpdatePacket(FriendlyByteBuf p_179790_) {
        this.f_134645_ = p_179790_.m_130135_();
        this.f_134646_ = new String[4];
        for (int $$1 = 0; $$1 < 4; ++$$1) {
            this.f_134646_[$$1] = p_179790_.m_130136_(384);
        }
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134662_) {
        p_134662_.m_130064_(this.f_134645_);
        for (int $$1 = 0; $$1 < 4; ++$$1) {
            p_134662_.m_130070_(this.f_134646_[$$1]);
        }
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134659_) {
        p_134659_.m_5527_(this);
    }

    public BlockPos m_134660_() {
        return this.f_134645_;
    }

    public String[] m_134663_() {
        return this.f_134646_;
    }
}

