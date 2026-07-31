/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class ClientboundCustomSoundPacket
implements Packet<ClientGamePacketListener> {
    public static final float f_178837_ = 8.0f;
    private final ResourceLocation f_132046_;
    private final SoundSource f_132047_;
    private final int f_132048_;
    private final int f_132049_;
    private final int f_132050_;
    private final float f_132051_;
    private final float f_132052_;
    private final long f_237697_;

    public ClientboundCustomSoundPacket(ResourceLocation p_237699_, SoundSource p_237700_, Vec3 p_237701_, float p_237702_, float p_237703_, long p_237704_) {
        this.f_132046_ = p_237699_;
        this.f_132047_ = p_237700_;
        this.f_132048_ = (int)(p_237701_.f_82479_ * 8.0);
        this.f_132049_ = (int)(p_237701_.f_82480_ * 8.0);
        this.f_132050_ = (int)(p_237701_.f_82481_ * 8.0);
        this.f_132051_ = p_237702_;
        this.f_132052_ = p_237703_;
        this.f_237697_ = p_237704_;
    }

    public ClientboundCustomSoundPacket(FriendlyByteBuf p_178839_) {
        this.f_132046_ = p_178839_.m_130281_();
        this.f_132047_ = p_178839_.m_130066_(SoundSource.class);
        this.f_132048_ = p_178839_.readInt();
        this.f_132049_ = p_178839_.readInt();
        this.f_132050_ = p_178839_.readInt();
        this.f_132051_ = p_178839_.readFloat();
        this.f_132052_ = p_178839_.readFloat();
        this.f_237697_ = p_178839_.readLong();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132068_) {
        p_132068_.m_130085_(this.f_132046_);
        p_132068_.m_130068_(this.f_132047_);
        p_132068_.writeInt(this.f_132048_);
        p_132068_.writeInt(this.f_132049_);
        p_132068_.writeInt(this.f_132050_);
        p_132068_.writeFloat(this.f_132051_);
        p_132068_.writeFloat(this.f_132052_);
        p_132068_.writeLong(this.f_237697_);
    }

    public ResourceLocation m_132066_() {
        return this.f_132046_;
    }

    public SoundSource m_132069_() {
        return this.f_132047_;
    }

    public double m_132070_() {
        return (float)this.f_132048_ / 8.0f;
    }

    public double m_132071_() {
        return (float)this.f_132049_ / 8.0f;
    }

    public double m_132072_() {
        return (float)this.f_132050_ / 8.0f;
    }

    public float m_132073_() {
        return this.f_132051_;
    }

    public float m_132074_() {
        return this.f_132052_;
    }

    public long m_237705_() {
        return this.f_237697_;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132065_) {
        p_132065_.m_6490_(this);
    }
}

