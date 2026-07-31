/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class MobEffectsPredicate {
    public static final MobEffectsPredicate f_56547_ = new MobEffectsPredicate(Collections.emptyMap());
    private final Map<MobEffect, MobEffectInstancePredicate> f_56548_;

    public MobEffectsPredicate(Map<MobEffect, MobEffectInstancePredicate> p_56551_) {
        this.f_56548_ = p_56551_;
    }

    public static MobEffectsPredicate m_56552_() {
        return new MobEffectsPredicate(Maps.newLinkedHashMap());
    }

    public MobEffectsPredicate m_56553_(MobEffect p_56554_) {
        this.f_56548_.put(p_56554_, new MobEffectInstancePredicate());
        return this;
    }

    public MobEffectsPredicate m_154977_(MobEffect p_154978_, MobEffectInstancePredicate p_154979_) {
        this.f_56548_.put(p_154978_, p_154979_);
        return this;
    }

    public boolean m_56555_(Entity p_56556_) {
        if (this == f_56547_) {
            return true;
        }
        if (p_56556_ instanceof LivingEntity) {
            return this.m_56561_(((LivingEntity)p_56556_).m_21221_());
        }
        return false;
    }

    public boolean m_56557_(LivingEntity p_56558_) {
        if (this == f_56547_) {
            return true;
        }
        return this.m_56561_(p_56558_.m_21221_());
    }

    public boolean m_56561_(Map<MobEffect, MobEffectInstance> p_56562_) {
        if (this == f_56547_) {
            return true;
        }
        for (Map.Entry<MobEffect, MobEffectInstancePredicate> $$1 : this.f_56548_.entrySet()) {
            MobEffectInstance $$2 = p_56562_.get($$1.getKey());
            if ($$1.getValue().m_56577_($$2)) continue;
            return false;
        }
        return true;
    }

    public static MobEffectsPredicate m_56559_(@Nullable JsonElement p_56560_) {
        if (p_56560_ == null || p_56560_.isJsonNull()) {
            return f_56547_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_56560_, "effects");
        LinkedHashMap $$2 = Maps.newLinkedHashMap();
        for (Map.Entry $$3 : $$1.entrySet()) {
            ResourceLocation $$4 = new ResourceLocation((String)$$3.getKey());
            MobEffect $$5 = Registry.f_122823_.m_6612_($$4).orElseThrow(() -> new JsonSyntaxException("Unknown effect '" + $$4 + "'"));
            MobEffectInstancePredicate $$6 = MobEffectInstancePredicate.m_56579_(GsonHelper.m_13918_((JsonElement)$$3.getValue(), (String)$$3.getKey()));
            $$2.put($$5, $$6);
        }
        return new MobEffectsPredicate($$2);
    }

    public JsonElement m_56565_() {
        if (this == f_56547_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        for (Map.Entry<MobEffect, MobEffectInstancePredicate> $$1 : this.f_56548_.entrySet()) {
            $$0.add(Registry.f_122823_.m_7981_($$1.getKey()).toString(), $$1.getValue().m_56576_());
        }
        return $$0;
    }

    public static class MobEffectInstancePredicate {
        private final MinMaxBounds.Ints f_56566_;
        private final MinMaxBounds.Ints f_56567_;
        @Nullable
        private final Boolean f_56568_;
        @Nullable
        private final Boolean f_56569_;

        public MobEffectInstancePredicate(MinMaxBounds.Ints p_56572_, MinMaxBounds.Ints p_56573_, @Nullable Boolean p_56574_, @Nullable Boolean p_56575_) {
            this.f_56566_ = p_56572_;
            this.f_56567_ = p_56573_;
            this.f_56568_ = p_56574_;
            this.f_56569_ = p_56575_;
        }

        public MobEffectInstancePredicate() {
            this(MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, null, null);
        }

        public boolean m_56577_(@Nullable MobEffectInstance p_56578_) {
            if (p_56578_ == null) {
                return false;
            }
            if (!this.f_56566_.m_55390_(p_56578_.m_19564_())) {
                return false;
            }
            if (!this.f_56567_.m_55390_(p_56578_.m_19557_())) {
                return false;
            }
            if (this.f_56568_ != null && this.f_56568_.booleanValue() != p_56578_.m_19571_()) {
                return false;
            }
            return this.f_56569_ == null || this.f_56569_.booleanValue() == p_56578_.m_19572_();
        }

        public JsonElement m_56576_() {
            JsonObject $$0 = new JsonObject();
            $$0.add("amplifier", this.f_56566_.m_55328_());
            $$0.add("duration", this.f_56567_.m_55328_());
            $$0.addProperty("ambient", this.f_56568_);
            $$0.addProperty("visible", this.f_56569_);
            return $$0;
        }

        public static MobEffectInstancePredicate m_56579_(JsonObject p_56580_) {
            MinMaxBounds.Ints $$1 = MinMaxBounds.Ints.m_55373_(p_56580_.get("amplifier"));
            MinMaxBounds.Ints $$2 = MinMaxBounds.Ints.m_55373_(p_56580_.get("duration"));
            Boolean $$3 = p_56580_.has("ambient") ? Boolean.valueOf(GsonHelper.m_13912_(p_56580_, "ambient")) : null;
            Boolean $$4 = p_56580_.has("visible") ? Boolean.valueOf(GsonHelper.m_13912_(p_56580_, "visible")) : null;
            return new MobEffectInstancePredicate($$1, $$2, $$3, $$4);
        }
    }
}

