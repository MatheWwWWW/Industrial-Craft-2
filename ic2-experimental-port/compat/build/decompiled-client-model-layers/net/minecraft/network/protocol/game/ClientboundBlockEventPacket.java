/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.block.Block;

public class ClientboundBlockEventPacket
implements Packet<ClientGamePacketListener> {
    private final BlockPos f_131709_;
    private final int f_131710_;
    private final int f_131711_;
    private final Block f_131712_;

    public ClientboundBlockEventPacket(BlockPos p_131715_, Block p_131716_, int p_131717_, int p_131718_) {
        this.f_131709_ = p_131715_;
        this.f_131712_ = p_131716_;
        this.f_131710_ = p_131717_;
        this.f_131711_ = p_131718_;
    }

    public ClientboundBlockEventPacket(FriendlyByteBuf p_178623_) {
        this.f_131709_ = p_178623_.m_130135_();
        this.f_131710_ = p_178623_.readUnsignedByte();
        this.f_131711_ = p_178623_.readUnsignedByte();
        this.f_131712_ = p_178623_.m_236816_(Registry.f_122824_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131727_) {
        p_131727_.m_130064_(this.f_131709_);
        p_131727_.writeByte(this.f_131710_);
        p_131727_.writeByte(this.f_131711_);
        p_131727_.m_236818_(Registry.f_122824_, this.f_131712_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131724_) {
        p_131724_.m_7364_(this);
    }

    public BlockPos m_131725_() {
        return this.f_131709_;
    }

    public int m_131728_() {
        return this.f_131710_;
    }

    public int m_131729_() {
        return this.f_131711_;
    }

    public Block m_131730_() {
        return this.f_131712_;
    }
}

