/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ClientboundRespawnPacket
implements Packet<ClientGamePacketListener> {
    private final ResourceKey<DimensionType> f_132928_;
    private final ResourceKey<Level> f_132929_;
    private final long f_132930_;
    private final GameType f_132931_;
    @Nullable
    private final GameType f_132932_;
    private final boolean f_132933_;
    private final boolean f_132934_;
    private final boolean f_132935_;
    private final Optional<GlobalPos> f_238183_;

    public ClientboundRespawnPacket(ResourceKey<DimensionType> p_238301_, ResourceKey<Level> p_238302_, long p_238303_, GameType p_238304_, @Nullable GameType p_238305_, boolean p_238306_, boolean p_238307_, boolean p_238308_, Optional<GlobalPos> p_238309_) {
        this.f_132928_ = p_238301_;
        this.f_132929_ = p_238302_;
        this.f_132930_ = p_238303_;
        this.f_132931_ = p_238304_;
        this.f_132932_ = p_238305_;
        this.f_132933_ = p_238306_;
        this.f_132934_ = p_238307_;
        this.f_132935_ = p_238308_;
        this.f_238183_ = p_238309_;
    }

    public ClientboundRespawnPacket(FriendlyByteBuf p_179191_) {
        this.f_132928_ = p_179191_.m_236801_(Registry.f_122818_);
        this.f_132929_ = p_179191_.m_236801_(Registry.f_122819_);
        this.f_132930_ = p_179191_.readLong();
        this.f_132931_ = GameType.m_46393_(p_179191_.readUnsignedByte());
        this.f_132932_ = GameType.m_151497_(p_179191_.readByte());
        this.f_132933_ = p_179191_.readBoolean();
        this.f_132934_ = p_179191_.readBoolean();
        this.f_132935_ = p_179191_.readBoolean();
        this.f_238183_ = p_179191_.m_236860_(FriendlyByteBuf::m_236872_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132954_) {
        p_132954_.m_236858_(this.f_132928_);
        p_132954_.m_236858_(this.f_132929_);
        p_132954_.writeLong(this.f_132930_);
        p_132954_.writeByte(this.f_132931_.m_46392_());
        p_132954_.writeByte(GameType.m_151495_(this.f_132932_));
        p_132954_.writeBoolean(this.f_132933_);
        p_132954_.writeBoolean(this.f_132934_);
        p_132954_.writeBoolean(this.f_132935_);
        p_132954_.m_236835_(this.f_238183_, FriendlyByteBuf::m_236814_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132951_) {
        p_132951_.m_7992_(this);
    }

    public ResourceKey<DimensionType> m_237794_() {
        return this.f_132928_;
    }

    public ResourceKey<Level> m_132955_() {
        return this.f_132929_;
    }

    public long m_132956_() {
        return this.f_132930_;
    }

    public GameType m_132957_() {
        return this.f_132931_;
    }

    @Nullable
    public GameType m_132958_() {
        return this.f_132932_;
    }

    public boolean m_132959_() {
        return this.f_132933_;
    }

    public boolean m_132960_() {
        return this.f_132934_;
    }

    public boolean m_132961_() {
        return this.f_132935_;
    }

    public Optional<GlobalPos> m_237785_() {
        return this.f_238183_;
    }
}

