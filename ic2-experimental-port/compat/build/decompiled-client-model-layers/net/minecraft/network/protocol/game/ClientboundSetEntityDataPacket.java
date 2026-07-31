/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;

public class ClientboundSetEntityDataPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133143_;
    @Nullable
    private final List<SynchedEntityData.DataItem<?>> f_133144_;

    public ClientboundSetEntityDataPacket(int p_133147_, SynchedEntityData p_133148_, boolean p_133149_) {
        this.f_133143_ = p_133147_;
        if (p_133149_) {
            this.f_133144_ = p_133148_.m_135384_();
            p_133148_.m_135389_();
        } else {
            this.f_133144_ = p_133148_.m_135378_();
        }
    }

    public ClientboundSetEntityDataPacket(FriendlyByteBuf p_179290_) {
        this.f_133143_ = p_179290_.m_130242_();
        this.f_133144_ = SynchedEntityData.m_135361_(p_179290_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133158_) {
        p_133158_.m_130130_(this.f_133143_);
        SynchedEntityData.m_135358_(this.f_133144_, p_133158_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133155_) {
        p_133155_.m_6455_(this);
    }

    @Nullable
    public List<SynchedEntityData.DataItem<?>> m_133156_() {
        return this.f_133144_;
    }

    public int m_133159_() {
        return this.f_133143_;
    }
}

