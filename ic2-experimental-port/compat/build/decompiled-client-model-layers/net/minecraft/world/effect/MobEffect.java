/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.effect;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class MobEffect {
    private final Map<Attribute, AttributeModifier> f_19446_ = Maps.newHashMap();
    private final MobEffectCategory f_19447_;
    private final int f_19448_;
    @Nullable
    private String f_19449_;
    private Supplier<MobEffectInstance.FactorData> f_216878_ = () -> null;

    @Nullable
    public static MobEffect m_19453_(int p_19454_) {
        return (MobEffect)Registry.f_122823_.m_7942_(p_19454_);
    }

    public static int m_19459_(MobEffect p_19460_) {
        return Registry.f_122823_.m_7447_(p_19460_);
    }

    public static int m_216882_(@Nullable MobEffect p_216883_) {
        return Registry.f_122823_.m_7447_(p_216883_);
    }

    protected MobEffect(MobEffectCategory p_19451_, int p_19452_) {
        this.f_19447_ = p_19451_;
        this.f_19448_ = p_19452_;
    }

    public Optional<MobEffectInstance.FactorData> m_216881_() {
        return Optional.ofNullable(this.f_216878_.get());
    }

    public void m_6742_(LivingEntity p_19467_, int p_19468_) {
        if (this == MobEffects.f_19605_) {
            if (p_19467_.m_21223_() < p_19467_.m_21233_()) {
                p_19467_.m_5634_(1.0f);
            }
        } else if (this == MobEffects.f_19614_) {
            if (p_19467_.m_21223_() > 1.0f) {
                p_19467_.m_6469_(DamageSource.f_19319_, 1.0f);
            }
        } else if (this == MobEffects.f_19615_) {
            p_19467_.m_6469_(DamageSource.f_19320_, 1.0f);
        } else if (this == MobEffects.f_19612_ && p_19467_ instanceof Player) {
            ((Player)p_19467_).m_36399_(0.005f * (float)(p_19468_ + 1));
        } else if (this == MobEffects.f_19618_ && p_19467_ instanceof Player) {
            if (!p_19467_.f_19853_.f_46443_) {
                ((Player)p_19467_).m_36324_().m_38707_(p_19468_ + 1, 1.0f);
            }
        } else if (this == MobEffects.f_19601_ && !p_19467_.m_21222_() || this == MobEffects.f_19602_ && p_19467_.m_21222_()) {
            p_19467_.m_5634_(Math.max(4 << p_19468_, 0));
        } else if (this == MobEffects.f_19602_ && !p_19467_.m_21222_() || this == MobEffects.f_19601_ && p_19467_.m_21222_()) {
            p_19467_.m_6469_(DamageSource.f_19319_, 6 << p_19468_);
        }
    }

    public void m_19461_(@Nullable Entity p_19462_, @Nullable Entity p_19463_, LivingEntity p_19464_, int p_19465_, double p_19466_) {
        if (this == MobEffects.f_19601_ && !p_19464_.m_21222_() || this == MobEffects.f_19602_ && p_19464_.m_21222_()) {
            int $$5 = (int)(p_19466_ * (double)(4 << p_19465_) + 0.5);
            p_19464_.m_5634_($$5);
        } else if (this == MobEffects.f_19602_ && !p_19464_.m_21222_() || this == MobEffects.f_19601_ && p_19464_.m_21222_()) {
            int $$6 = (int)(p_19466_ * (double)(6 << p_19465_) + 0.5);
            if (p_19462_ == null) {
                p_19464_.m_6469_(DamageSource.f_19319_, $$6);
            } else {
                p_19464_.m_6469_(DamageSource.m_19367_(p_19462_, p_19463_), $$6);
            }
        } else {
            this.m_6742_(p_19464_, p_19465_);
        }
    }

    public boolean m_6584_(int p_19455_, int p_19456_) {
        if (this == MobEffects.f_19605_) {
            int $$2 = 50 >> p_19456_;
            if ($$2 > 0) {
                return p_19455_ % $$2 == 0;
            }
            return true;
        }
        if (this == MobEffects.f_19614_) {
            int $$3 = 25 >> p_19456_;
            if ($$3 > 0) {
                return p_19455_ % $$3 == 0;
            }
            return true;
        }
        if (this == MobEffects.f_19615_) {
            int $$4 = 40 >> p_19456_;
            if ($$4 > 0) {
                return p_19455_ % $$4 == 0;
            }
            return true;
        }
        return this == MobEffects.f_19612_;
    }

    public boolean m_8093_() {
        return false;
    }

    protected String m_19477_() {
        if (this.f_19449_ == null) {
            this.f_19449_ = Util.m_137492_("effect", Registry.f_122823_.m_7981_(this));
        }
        return this.f_19449_;
    }

    public String m_19481_() {
        return this.m_19477_();
    }

    public Component m_19482_() {
        return Component.m_237115_(this.m_19481_());
    }

    public MobEffectCategory m_19483_() {
        return this.f_19447_;
    }

    public int m_19484_() {
        return this.f_19448_;
    }

    public MobEffect m_19472_(Attribute p_19473_, String p_19474_, double p_19475_, AttributeModifier.Operation p_19476_) {
        AttributeModifier $$4 = new AttributeModifier(UUID.fromString(p_19474_), this::m_19481_, p_19475_, p_19476_);
        this.f_19446_.put(p_19473_, $$4);
        return this;
    }

    public MobEffect m_216879_(Supplier<MobEffectInstance.FactorData> p_216880_) {
        this.f_216878_ = p_216880_;
        return this;
    }

    public Map<Attribute, AttributeModifier> m_19485_() {
        return this.f_19446_;
    }

    public void m_6386_(LivingEntity p_19469_, AttributeMap p_19470_, int p_19471_) {
        for (Map.Entry<Attribute, AttributeModifier> $$3 : this.f_19446_.entrySet()) {
            AttributeInstance $$4 = p_19470_.m_22146_($$3.getKey());
            if ($$4 == null) continue;
            $$4.m_22130_($$3.getValue());
        }
    }

    public void m_6385_(LivingEntity p_19478_, AttributeMap p_19479_, int p_19480_) {
        for (Map.Entry<Attribute, AttributeModifier> $$3 : this.f_19446_.entrySet()) {
            AttributeInstance $$4 = p_19479_.m_22146_($$3.getKey());
            if ($$4 == null) continue;
            AttributeModifier $$5 = $$3.getValue();
            $$4.m_22130_($$5);
            $$4.m_22125_(new AttributeModifier($$5.m_22209_(), this.m_19481_() + " " + p_19480_, this.m_7048_(p_19480_, $$5), $$5.m_22217_()));
        }
    }

    public double m_7048_(int p_19457_, AttributeModifier p_19458_) {
        return p_19458_.m_22218_() * (double)(p_19457_ + 1);
    }

    public boolean m_19486_() {
        return this.f_19447_ == MobEffectCategory.BENEFICIAL;
    }
}

