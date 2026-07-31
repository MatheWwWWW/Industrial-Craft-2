/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ClientboundSetObjectivePacket
implements Packet<ClientGamePacketListener> {
    public static final int f_179302_ = 0;
    public static final int f_179303_ = 1;
    public static final int f_179304_ = 2;
    private final String f_133252_;
    private final Component f_133253_;
    private final ObjectiveCriteria.RenderType f_133254_;
    private final int f_133255_;

    public ClientboundSetObjectivePacket(Objective p_133258_, int p_133259_) {
        this.f_133252_ = p_133258_.m_83320_();
        this.f_133253_ = p_133258_.m_83322_();
        this.f_133254_ = p_133258_.m_83324_();
        this.f_133255_ = p_133259_;
    }

    public ClientboundSetObjectivePacket(FriendlyByteBuf p_179306_) {
        this.f_133252_ = p_179306_.m_130277_();
        this.f_133255_ = p_179306_.readByte();
        if (this.f_133255_ == 0 || this.f_133255_ == 2) {
            this.f_133253_ = p_179306_.m_130238_();
            this.f_133254_ = p_179306_.m_130066_(ObjectiveCriteria.RenderType.class);
        } else {
            this.f_133253_ = CommonComponents.f_237098_;
            this.f_133254_ = ObjectiveCriteria.RenderType.INTEGER;
        }
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133268_) {
        p_133268_.m_130070_(this.f_133252_);
        p_133268_.writeByte(this.f_133255_);
        if (this.f_133255_ == 0 || this.f_133255_ == 2) {
            p_133268_.m_130083_(this.f_133253_);
            p_133268_.m_130068_(this.f_133254_);
        }
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133265_) {
        p_133265_.m_7957_(this);
    }

    public String m_133266_() {
        return this.f_133252_;
    }

    public Component m_133269_() {
        return this.f_133253_;
    }

    public int m_133270_() {
        return this.f_133255_;
    }

    public ObjectiveCriteria.RenderType m_133271_() {
        return this.f_133254_;
    }
}

