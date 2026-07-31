/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ClientboundBlockUpdatePacket
implements Packet<ClientGamePacketListener> {
    private final BlockPos f_131731_;
    private final BlockState f_131732_;

    public ClientboundBlockUpdatePacket(BlockPos p_131738_, BlockState p_131739_) {
        this.f_131731_ = p_131738_;
        this.f_131732_ = p_131739_;
    }

    public ClientboundBlockUpdatePacket(BlockGetter p_131735_, BlockPos p_131736_) {
        this(p_131736_, p_131735_.m_8055_(p_131736_));
    }

    public ClientboundBlockUpdatePacket(FriendlyByteBuf p_178628_) {
        this.f_131731_ = p_178628_.m_130135_();
        this.f_131732_ = p_178628_.m_236816_(Block.f_49791_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131748_) {
        p_131748_.m_130064_(this.f_131731_);
        p_131748_.m_236818_(Block.f_49791_, this.f_131732_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131745_) {
        p_131745_.m_6773_(this);
    }

    public BlockState m_131746_() {
        return this.f_131732_;
    }

    public BlockPos m_131749_() {
        return this.f_131731_;
    }
}

