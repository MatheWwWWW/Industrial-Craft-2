/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.client.player;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.ProfilePublicKey;

public class RemotePlayer
extends AbstractClientPlayer {
    public RemotePlayer(ClientLevel p_234159_, GameProfile p_234160_, @Nullable ProfilePublicKey p_234161_) {
        super(p_234159_, p_234160_, p_234161_);
        this.f_19793_ = 1.0f;
        this.f_19794_ = true;
    }

    @Override
    public boolean m_6783_(double p_108770_) {
        double $$1 = this.m_20191_().m_82309_() * 10.0;
        if (Double.isNaN($$1)) {
            $$1 = 1.0;
        }
        return p_108770_ < ($$1 *= 64.0 * RemotePlayer.m_20150_()) * $$1;
    }

    @Override
    public boolean m_6469_(DamageSource p_108772_, float p_108773_) {
        return true;
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        this.m_21043_(this, false);
    }

    @Override
    public void m_8107_() {
        float $$4;
        if (this.f_20903_ > 0) {
            double $$0 = this.m_20185_() + (this.f_20904_ - this.m_20185_()) / (double)this.f_20903_;
            double $$1 = this.m_20186_() + (this.f_20905_ - this.m_20186_()) / (double)this.f_20903_;
            double $$2 = this.m_20189_() + (this.f_20906_ - this.m_20189_()) / (double)this.f_20903_;
            this.m_146922_(this.m_146908_() + (float)Mth.m_14175_(this.f_20907_ - (double)this.m_146908_()) / (float)this.f_20903_);
            this.m_146926_(this.m_146909_() + (float)(this.f_20908_ - (double)this.m_146909_()) / (float)this.f_20903_);
            --this.f_20903_;
            this.m_6034_($$0, $$1, $$2);
            this.m_19915_(this.m_146908_(), this.m_146909_());
        }
        if (this.f_20934_ > 0) {
            this.f_20885_ += (float)(Mth.m_14175_(this.f_20933_ - (double)this.f_20885_) / (double)this.f_20934_);
            --this.f_20934_;
        }
        this.f_36099_ = this.f_36100_;
        this.m_21203_();
        if (!this.f_19861_ || this.m_21224_()) {
            float $$3 = 0.0f;
        } else {
            $$4 = (float)Math.min(0.1, this.m_20184_().m_165924_());
        }
        this.f_36100_ += ($$4 - this.f_36100_) * 0.4f;
        this.f_19853_.m_46473_().m_6180_("push");
        this.m_6138_();
        this.f_19853_.m_46473_().m_7238_();
    }

    @Override
    protected void m_7594_() {
    }

    @Override
    public void m_213846_(Component p_234163_) {
        Minecraft $$1 = Minecraft.m_91087_();
        $$1.f_91065_.m_93076_().m_93785_(p_234163_);
    }
}

