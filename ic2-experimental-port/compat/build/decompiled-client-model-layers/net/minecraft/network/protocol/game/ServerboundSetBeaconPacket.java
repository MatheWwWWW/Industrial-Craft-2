/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.effect.MobEffect;

public class ServerboundSetBeaconPacket
implements Packet<ServerGamePacketListener> {
    private final Optional<MobEffect> f_134472_;
    private final Optional<MobEffect> f_134473_;

    public ServerboundSetBeaconPacket(Optional<MobEffect> p_237989_, Optional<MobEffect> p_237990_) {
        this.f_134472_ = p_237989_;
        this.f_134473_ = p_237990_;
    }

    public ServerboundSetBeaconPacket(FriendlyByteBuf p_179749_) {
        this.f_134472_ = p_179749_.m_236860_(p_238002_ -> p_238002_.m_236816_(Registry.f_122823_));
        this.f_134473_ = p_179749_.m_236860_(p_237996_ -> p_237996_.m_236816_(Registry.f_122823_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134486_) {
        p_134486_.m_236835_(this.f_134472_, (p_237998_, p_237999_) -> p_237998_.m_236818_(Registry.f_122823_, p_237999_));
        p_134486_.m_236835_(this.f_134473_, (p_237992_, p_237993_) -> p_237992_.m_236818_(Registry.f_122823_, p_237993_));
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134483_) {
        p_134483_.m_5712_(this);
    }

    public Optional<MobEffect> m_237994_() {
        return this.f_134472_;
    }

    public Optional<MobEffect> m_238000_() {
        return this.f_134473_;
    }
}

