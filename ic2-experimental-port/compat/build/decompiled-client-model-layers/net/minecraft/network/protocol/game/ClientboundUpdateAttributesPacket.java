/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ClientboundUpdateAttributesPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133576_;
    private final List<AttributeSnapshot> f_133577_;

    public ClientboundUpdateAttributesPacket(int p_133580_, Collection<AttributeInstance> p_133581_) {
        this.f_133576_ = p_133580_;
        this.f_133577_ = Lists.newArrayList();
        for (AttributeInstance $$2 : p_133581_) {
            this.f_133577_.add(new AttributeSnapshot($$2.m_22099_(), $$2.m_22115_(), $$2.m_22122_()));
        }
    }

    public ClientboundUpdateAttributesPacket(FriendlyByteBuf p_179447_) {
        this.f_133576_ = p_179447_.m_130242_();
        this.f_133577_ = p_179447_.m_236845_(p_179455_ -> {
            ResourceLocation $$1 = p_179455_.m_130281_();
            Attribute $$2 = Registry.f_122866_.m_7745_($$1);
            double $$3 = p_179455_.readDouble();
            List<AttributeModifier> $$4 = p_179455_.m_236845_(p_179457_ -> new AttributeModifier(p_179457_.m_130259_(), "Unknown synced attribute modifier", p_179457_.readDouble(), AttributeModifier.Operation.m_22236_(p_179457_.readByte())));
            return new AttributeSnapshot($$2, $$3, $$4);
        });
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133590_) {
        p_133590_.m_130130_(this.f_133576_);
        p_133590_.m_236828_(this.f_133577_, (p_179452_, p_179453_) -> {
            p_179452_.m_130085_(Registry.f_122866_.m_7981_(p_179453_.m_133601_()));
            p_179452_.writeDouble(p_179453_.m_133602_());
            p_179452_.m_236828_(p_179453_.m_133603_(), (p_179449_, p_179450_) -> {
                p_179449_.m_130077_(p_179450_.m_22209_());
                p_179449_.writeDouble(p_179450_.m_22218_());
                p_179449_.writeByte(p_179450_.m_22217_().m_22235_());
            });
        });
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133587_) {
        p_133587_.m_7710_(this);
    }

    public int m_133588_() {
        return this.f_133576_;
    }

    public List<AttributeSnapshot> m_133591_() {
        return this.f_133577_;
    }

    public static class AttributeSnapshot {
        private final Attribute f_133593_;
        private final double f_133594_;
        private final Collection<AttributeModifier> f_133595_;

        public AttributeSnapshot(Attribute p_179459_, double p_179460_, Collection<AttributeModifier> p_179461_) {
            this.f_133593_ = p_179459_;
            this.f_133594_ = p_179460_;
            this.f_133595_ = p_179461_;
        }

        public Attribute m_133601_() {
            return this.f_133593_;
        }

        public double m_133602_() {
            return this.f_133594_;
        }

        public Collection<AttributeModifier> m_133603_() {
            return this.f_133595_;
        }
    }
}

