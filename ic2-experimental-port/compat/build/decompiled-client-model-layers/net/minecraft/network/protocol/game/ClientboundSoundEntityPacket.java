/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.Validate;

public class ClientboundSoundEntityPacket
implements Packet<ClientGamePacketListener> {
    private final SoundEvent f_133408_;
    private final SoundSource f_133409_;
    private final int f_133410_;
    private final float f_133411_;
    private final float f_133412_;
    private final long f_237829_;

    public ClientboundSoundEntityPacket(SoundEvent p_237831_, SoundSource p_237832_, Entity p_237833_, float p_237834_, float p_237835_, long p_237836_) {
        Validate.notNull((Object)p_237831_, (String)"sound", (Object[])new Object[0]);
        this.f_133408_ = p_237831_;
        this.f_133409_ = p_237832_;
        this.f_133410_ = p_237833_.m_19879_();
        this.f_133411_ = p_237834_;
        this.f_133412_ = p_237835_;
        this.f_237829_ = p_237836_;
    }

    public ClientboundSoundEntityPacket(FriendlyByteBuf p_179419_) {
        this.f_133408_ = p_179419_.m_236816_(Registry.f_122821_);
        this.f_133409_ = p_179419_.m_130066_(SoundSource.class);
        this.f_133410_ = p_179419_.m_130242_();
        this.f_133411_ = p_179419_.readFloat();
        this.f_133412_ = p_179419_.readFloat();
        this.f_237829_ = p_179419_.readLong();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133428_) {
        p_133428_.m_236818_(Registry.f_122821_, this.f_133408_);
        p_133428_.m_130068_(this.f_133409_);
        p_133428_.m_130130_(this.f_133410_);
        p_133428_.writeFloat(this.f_133411_);
        p_133428_.writeFloat(this.f_133412_);
        p_133428_.writeLong(this.f_237829_);
    }

    public SoundEvent m_133426_() {
        return this.f_133408_;
    }

    public SoundSource m_133429_() {
        return this.f_133409_;
    }

    public int m_133430_() {
        return this.f_133410_;
    }

    public float m_133431_() {
        return this.f_133411_;
    }

    public float m_133432_() {
        return this.f_133412_;
    }

    public long m_237837_() {
        return this.f_237829_;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133425_) {
        p_133425_.m_5863_(this);
    }
}

