/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import java.util.List;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.level.Level;

public class ElderGuardian
extends Guardian {
    public static final float f_32457_ = EntityType.f_20563_.m_20678_() / EntityType.f_20455_.m_20678_();
    private static final int f_218965_ = 1200;
    private static final int f_218961_ = 50;
    private static final int f_218962_ = 6000;
    private static final int f_218963_ = 2;
    private static final int f_218964_ = 1200;

    public ElderGuardian(EntityType<? extends ElderGuardian> p_32460_, Level p_32461_) {
        super((EntityType<? extends Guardian>)p_32460_, p_32461_);
        this.m_21530_();
        if (this.f_32806_ != null) {
            this.f_32806_.m_25746_(400);
        }
    }

    public static AttributeSupplier.Builder m_32471_() {
        return Guardian.m_32853_().m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22281_, 8.0).m_22268_(Attributes.f_22276_, 80.0);
    }

    @Override
    public int m_7552_() {
        return 60;
    }

    @Override
    protected SoundEvent m_7515_() {
        return this.m_20072_() ? SoundEvents.f_11878_ : SoundEvents.f_11879_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32468_) {
        return this.m_20072_() ? SoundEvents.f_11884_ : SoundEvents.f_11885_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return this.m_20072_() ? SoundEvents.f_11881_ : SoundEvents.f_11882_;
    }

    @Override
    protected SoundEvent m_7868_() {
        return SoundEvents.f_11883_;
    }

    @Override
    protected void m_8024_() {
        super.m_8024_();
        if ((this.f_19797_ + this.m_19879_()) % 1200 == 0) {
            MobEffectInstance $$0 = new MobEffectInstance(MobEffects.f_19599_, 6000, 2);
            List<ServerPlayer> $$1 = MobEffectUtil.m_216946_((ServerLevel)this.f_19853_, this, this.m_20182_(), 50.0, $$0, 1200);
            $$1.forEach(p_218967_ -> p_218967_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132163_, this.m_20067_() ? 0.0f : 1.0f)));
        }
        if (!this.m_21536_()) {
            this.m_21446_(this.m_20183_(), 16);
        }
    }
}

