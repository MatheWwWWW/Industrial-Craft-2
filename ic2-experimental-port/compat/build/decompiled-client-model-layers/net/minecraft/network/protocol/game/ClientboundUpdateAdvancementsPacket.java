/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Sets
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;

public class ClientboundUpdateAdvancementsPacket
implements Packet<ClientGamePacketListener> {
    private final boolean f_133554_;
    private final Map<ResourceLocation, Advancement.Builder> f_133555_;
    private final Set<ResourceLocation> f_133556_;
    private final Map<ResourceLocation, AdvancementProgress> f_133557_;

    public ClientboundUpdateAdvancementsPacket(boolean p_133560_, Collection<Advancement> p_133561_, Set<ResourceLocation> p_133562_, Map<ResourceLocation, AdvancementProgress> p_133563_) {
        this.f_133554_ = p_133560_;
        ImmutableMap.Builder $$4 = ImmutableMap.builder();
        for (Advancement $$5 : p_133561_) {
            $$4.put((Object)$$5.m_138327_(), (Object)$$5.m_138313_());
        }
        this.f_133555_ = $$4.build();
        this.f_133556_ = ImmutableSet.copyOf(p_133562_);
        this.f_133557_ = ImmutableMap.copyOf(p_133563_);
    }

    public ClientboundUpdateAdvancementsPacket(FriendlyByteBuf p_179439_) {
        this.f_133554_ = p_179439_.readBoolean();
        this.f_133555_ = p_179439_.m_236847_(FriendlyByteBuf::m_130281_, Advancement.Builder::m_138401_);
        this.f_133556_ = p_179439_.m_236838_(Sets::newLinkedHashSetWithExpectedSize, FriendlyByteBuf::m_130281_);
        this.f_133557_ = p_179439_.m_236847_(FriendlyByteBuf::m_130281_, AdvancementProgress::m_8211_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133572_) {
        p_133572_.writeBoolean(this.f_133554_);
        p_133572_.m_236831_(this.f_133555_, FriendlyByteBuf::m_130085_, (p_179441_, p_179442_) -> p_179442_.m_138394_((FriendlyByteBuf)((Object)p_179441_)));
        p_133572_.m_236828_(this.f_133556_, FriendlyByteBuf::m_130085_);
        p_133572_.m_236831_(this.f_133557_, FriendlyByteBuf::m_130085_, (p_179444_, p_179445_) -> p_179445_.m_8204_((FriendlyByteBuf)((Object)p_179444_)));
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133569_) {
        p_133569_.m_5498_(this);
    }

    public Map<ResourceLocation, Advancement.Builder> m_133570_() {
        return this.f_133555_;
    }

    public Set<ResourceLocation> m_133573_() {
        return this.f_133556_;
    }

    public Map<ResourceLocation, AdvancementProgress> m_133574_() {
        return this.f_133557_;
    }

    public boolean m_133575_() {
        return this.f_133554_;
    }
}

