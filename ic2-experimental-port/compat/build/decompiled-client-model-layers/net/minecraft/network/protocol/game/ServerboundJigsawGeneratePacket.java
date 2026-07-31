/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundJigsawGeneratePacket
implements Packet<ServerGamePacketListener> {
    private final BlockPos f_134073_;
    private final int f_134074_;
    private final boolean f_134075_;

    public ServerboundJigsawGeneratePacket(BlockPos p_134078_, int p_134079_, boolean p_134080_) {
        this.f_134073_ = p_134078_;
        this.f_134074_ = p_134079_;
        this.f_134075_ = p_134080_;
    }

    public ServerboundJigsawGeneratePacket(FriendlyByteBuf p_179669_) {
        this.f_134073_ = p_179669_.m_130135_();
        this.f_134074_ = p_179669_.m_130242_();
        this.f_134075_ = p_179669_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134089_) {
        p_134089_.m_130064_(this.f_134073_);
        p_134089_.m_130130_(this.f_134074_);
        p_134089_.writeBoolean(this.f_134075_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134086_) {
        p_134086_.m_6449_(this);
    }

    public BlockPos m_134087_() {
        return this.f_134073_;
    }

    public int m_134090_() {
        return this.f_134074_;
    }

    public boolean m_134091_() {
        return this.f_134075_;
    }
}

