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
import org.apache.commons.lang3.Validate;

public class ClientboundSoundPacket
implements Packet<ClientGamePacketListener> {
    public static final float f_179420_ = 8.0f;
    private final SoundEvent f_133433_;
    private final SoundSource f_133434_;
    private final int f_133435_;
    private final int f_133436_;
    private final int f_133437_;
    private final float f_133438_;
    private final float f_133439_;
    private final long f_237838_;

    public ClientboundSoundPacket(SoundEvent p_237840_, SoundSource p_237841_, double p_237842_, double p_237843_, double p_237844_, float p_237845_, float p_237846_, long p_237847_) {
        Validate.notNull((Object)p_237840_, (String)"sound", (Object[])new Object[0]);
        this.f_133433_ = p_237840_;
        this.f_133434_ = p_237841_;
        this.f_133435_ = (int)(p_237842_ * 8.0);
        this.f_133436_ = (int)(p_237843_ * 8.0);
        this.f_133437_ = (int)(p_237844_ * 8.0);
        this.f_133438_ = p_237845_;
        this.f_133439_ = p_237846_;
        this.f_237838_ = p_237847_;
    }

    public ClientboundSoundPacket(FriendlyByteBuf p_179422_) {
        this.f_133433_ = p_179422_.m_236816_(Registry.f_122821_);
        this.f_133434_ = p_179422_.m_130066_(SoundSource.class);
        this.f_133435_ = p_179422_.readInt();
        this.f_133436_ = p_179422_.readInt();
        this.f_133437_ = p_179422_.readInt();
        this.f_133438_ = p_179422_.readFloat();
        this.f_133439_ = p_179422_.readFloat();
        this.f_237838_ = p_179422_.readLong();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133457_) {
        p_133457_.m_236818_(Registry.f_122821_, this.f_133433_);
        p_133457_.m_130068_(this.f_133434_);
        p_133457_.writeInt(this.f_133435_);
        p_133457_.writeInt(this.f_133436_);
        p_133457_.writeInt(this.f_133437_);
        p_133457_.writeFloat(this.f_133438_);
        p_133457_.writeFloat(this.f_133439_);
        p_133457_.writeLong(this.f_237838_);
    }

    public SoundEvent m_133455_() {
        return this.f_133433_;
    }

    public SoundSource m_133458_() {
        return this.f_133434_;
    }

    public double m_133459_() {
        return (float)this.f_133435_ / 8.0f;
    }

    public double m_133460_() {
        return (float)this.f_133436_ / 8.0f;
    }

    public double m_133461_() {
        return (float)this.f_133437_ / 8.0f;
    }

    public float m_133462_() {
        return this.f_133438_;
    }

    public float m_133463_() {
        return this.f_133439_;
    }

    public long m_237848_() {
        return this.f_237838_;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133454_) {
        p_133454_.m_8068_(this);
    }
}

