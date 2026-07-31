/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.level.block.entity.CommandBlockEntity;

public class ServerboundSetCommandBlockPacket
implements Packet<ServerGamePacketListener> {
    private static final int f_179752_ = 1;
    private static final int f_179753_ = 2;
    private static final int f_179754_ = 4;
    private final BlockPos f_134501_;
    private final String f_134502_;
    private final boolean f_134503_;
    private final boolean f_134504_;
    private final boolean f_134505_;
    private final CommandBlockEntity.Mode f_134506_;

    public ServerboundSetCommandBlockPacket(BlockPos p_134509_, String p_134510_, CommandBlockEntity.Mode p_134511_, boolean p_134512_, boolean p_134513_, boolean p_134514_) {
        this.f_134501_ = p_134509_;
        this.f_134502_ = p_134510_;
        this.f_134503_ = p_134512_;
        this.f_134504_ = p_134513_;
        this.f_134505_ = p_134514_;
        this.f_134506_ = p_134511_;
    }

    public ServerboundSetCommandBlockPacket(FriendlyByteBuf p_179756_) {
        this.f_134501_ = p_179756_.m_130135_();
        this.f_134502_ = p_179756_.m_130277_();
        this.f_134506_ = p_179756_.m_130066_(CommandBlockEntity.Mode.class);
        byte $$1 = p_179756_.readByte();
        this.f_134503_ = ($$1 & 1) != 0;
        this.f_134504_ = ($$1 & 2) != 0;
        this.f_134505_ = ($$1 & 4) != 0;
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134523_) {
        p_134523_.m_130064_(this.f_134501_);
        p_134523_.m_130070_(this.f_134502_);
        p_134523_.m_130068_(this.f_134506_);
        int $$1 = 0;
        if (this.f_134503_) {
            $$1 |= 1;
        }
        if (this.f_134504_) {
            $$1 |= 2;
        }
        if (this.f_134505_) {
            $$1 |= 4;
        }
        p_134523_.writeByte($$1);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134520_) {
        p_134520_.m_7192_(this);
    }

    public BlockPos m_134521_() {
        return this.f_134501_;
    }

    public String m_134524_() {
        return this.f_134502_;
    }

    public boolean m_134525_() {
        return this.f_134503_;
    }

    public boolean m_134526_() {
        return this.f_134504_;
    }

    public boolean m_134527_() {
        return this.f_134505_;
    }

    public CommandBlockEntity.Mode m_134528_() {
        return this.f_134506_;
    }
}

