/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.server.level;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;

public class ServerBossEvent
extends BossEvent {
    private final Set<ServerPlayer> f_8296_ = Sets.newHashSet();
    private final Set<ServerPlayer> f_8297_ = Collections.unmodifiableSet(this.f_8296_);
    private boolean f_8298_ = true;

    public ServerBossEvent(Component p_8300_, BossEvent.BossBarColor p_8301_, BossEvent.BossBarOverlay p_8302_) {
        super(Mth.m_14002_(), p_8300_, p_8301_, p_8302_);
    }

    @Override
    public void m_142711_(float p_143223_) {
        if (p_143223_ != this.f_146638_) {
            super.m_142711_(p_143223_);
            this.m_143224_(ClientboundBossEventPacket::m_178649_);
        }
    }

    @Override
    public void m_6451_(BossEvent.BossBarColor p_8307_) {
        if (p_8307_ != this.f_18842_) {
            super.m_6451_(p_8307_);
            this.m_143224_(ClientboundBossEventPacket::m_178653_);
        }
    }

    @Override
    public void m_5648_(BossEvent.BossBarOverlay p_8309_) {
        if (p_8309_ != this.f_18843_) {
            super.m_5648_(p_8309_);
            this.m_143224_(ClientboundBossEventPacket::m_178653_);
        }
    }

    @Override
    public BossEvent m_7003_(boolean p_8315_) {
        if (p_8315_ != this.f_18844_) {
            super.m_7003_(p_8315_);
            this.m_143224_(ClientboundBossEventPacket::m_178655_);
        }
        return this;
    }

    @Override
    public BossEvent m_7005_(boolean p_8318_) {
        if (p_8318_ != this.f_18845_) {
            super.m_7005_(p_8318_);
            this.m_143224_(ClientboundBossEventPacket::m_178655_);
        }
        return this;
    }

    @Override
    public BossEvent m_7006_(boolean p_8320_) {
        if (p_8320_ != this.f_18846_) {
            super.m_7006_(p_8320_);
            this.m_143224_(ClientboundBossEventPacket::m_178655_);
        }
        return this;
    }

    @Override
    public void m_6456_(Component p_8311_) {
        if (!Objects.equal((Object)p_8311_, (Object)this.f_18840_)) {
            super.m_6456_(p_8311_);
            this.m_143224_(ClientboundBossEventPacket::m_178651_);
        }
    }

    private void m_143224_(Function<BossEvent, ClientboundBossEventPacket> p_143225_) {
        if (this.f_8298_) {
            ClientboundBossEventPacket $$1 = p_143225_.apply(this);
            for (ServerPlayer $$2 : this.f_8296_) {
                $$2.f_8906_.m_9829_($$1);
            }
        }
    }

    public void m_6543_(ServerPlayer p_8305_) {
        if (this.f_8296_.add(p_8305_) && this.f_8298_) {
            p_8305_.f_8906_.m_9829_(ClientboundBossEventPacket.m_178639_(this));
        }
    }

    public void m_6539_(ServerPlayer p_8316_) {
        if (this.f_8296_.remove(p_8316_) && this.f_8298_) {
            p_8316_.f_8906_.m_9829_(ClientboundBossEventPacket.m_178641_(this.m_18860_()));
        }
    }

    public void m_7706_() {
        if (!this.f_8296_.isEmpty()) {
            for (ServerPlayer $$0 : Lists.newArrayList(this.f_8296_)) {
                this.m_6539_($$0);
            }
        }
    }

    public boolean m_8323_() {
        return this.f_8298_;
    }

    public void m_8321_(boolean p_8322_) {
        if (p_8322_ != this.f_8298_) {
            this.f_8298_ = p_8322_;
            for (ServerPlayer $$1 : this.f_8296_) {
                $$1.f_8906_.m_9829_(p_8322_ ? ClientboundBossEventPacket.m_178639_(this) : ClientboundBossEventPacket.m_178641_(this.m_18860_()));
            }
        }
    }

    public Collection<ServerPlayer> m_8324_() {
        return this.f_8297_;
    }
}

