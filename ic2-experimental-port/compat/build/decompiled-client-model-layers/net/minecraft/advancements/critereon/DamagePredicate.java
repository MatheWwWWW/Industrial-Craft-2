/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;

public class DamagePredicate {
    public static final DamagePredicate f_24902_ = Builder.m_24931_().m_24936_();
    private final MinMaxBounds.Doubles f_24903_;
    private final MinMaxBounds.Doubles f_24904_;
    private final EntityPredicate f_24905_;
    @Nullable
    private final Boolean f_24906_;
    private final DamageSourcePredicate f_24907_;

    public DamagePredicate() {
        this.f_24903_ = MinMaxBounds.Doubles.f_154779_;
        this.f_24904_ = MinMaxBounds.Doubles.f_154779_;
        this.f_24905_ = EntityPredicate.f_36550_;
        this.f_24906_ = null;
        this.f_24907_ = DamageSourcePredicate.f_25420_;
    }

    public DamagePredicate(MinMaxBounds.Doubles p_24911_, MinMaxBounds.Doubles p_24912_, EntityPredicate p_24913_, @Nullable Boolean p_24914_, DamageSourcePredicate p_24915_) {
        this.f_24903_ = p_24911_;
        this.f_24904_ = p_24912_;
        this.f_24905_ = p_24913_;
        this.f_24906_ = p_24914_;
        this.f_24907_ = p_24915_;
    }

    public boolean m_24917_(ServerPlayer p_24918_, DamageSource p_24919_, float p_24920_, float p_24921_, boolean p_24922_) {
        if (this == f_24902_) {
            return true;
        }
        if (!this.f_24903_.m_154810_(p_24920_)) {
            return false;
        }
        if (!this.f_24904_.m_154810_(p_24921_)) {
            return false;
        }
        if (!this.f_24905_.m_36611_(p_24918_, p_24919_.m_7639_())) {
            return false;
        }
        if (this.f_24906_ != null && this.f_24906_ != p_24922_) {
            return false;
        }
        return this.f_24907_.m_25448_(p_24918_, p_24919_);
    }

    public static DamagePredicate m_24923_(@Nullable JsonElement p_24924_) {
        if (p_24924_ == null || p_24924_.isJsonNull()) {
            return f_24902_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_24924_, "damage");
        MinMaxBounds.Doubles $$2 = MinMaxBounds.Doubles.m_154791_($$1.get("dealt"));
        MinMaxBounds.Doubles $$3 = MinMaxBounds.Doubles.m_154791_($$1.get("taken"));
        Boolean $$4 = $$1.has("blocked") ? Boolean.valueOf(GsonHelper.m_13912_($$1, "blocked")) : null;
        EntityPredicate $$5 = EntityPredicate.m_36614_($$1.get("source_entity"));
        DamageSourcePredicate $$6 = DamageSourcePredicate.m_25451_($$1.get("type"));
        return new DamagePredicate($$2, $$3, $$5, $$4, $$6);
    }

    public JsonElement m_24916_() {
        if (this == f_24902_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        $$0.add("dealt", this.f_24903_.m_55328_());
        $$0.add("taken", this.f_24904_.m_55328_());
        $$0.add("source_entity", this.f_24905_.m_36606_());
        $$0.add("type", this.f_24907_.m_25443_());
        if (this.f_24906_ != null) {
            $$0.addProperty("blocked", this.f_24906_);
        }
        return $$0;
    }

    public static class Builder {
        private MinMaxBounds.Doubles f_24925_ = MinMaxBounds.Doubles.f_154779_;
        private MinMaxBounds.Doubles f_24926_ = MinMaxBounds.Doubles.f_154779_;
        private EntityPredicate f_24927_ = EntityPredicate.f_36550_;
        @Nullable
        private Boolean f_24928_;
        private DamageSourcePredicate f_24929_ = DamageSourcePredicate.f_25420_;

        public static Builder m_24931_() {
            return new Builder();
        }

        public Builder m_148145_(MinMaxBounds.Doubles p_148146_) {
            this.f_24925_ = p_148146_;
            return this;
        }

        public Builder m_148147_(MinMaxBounds.Doubles p_148148_) {
            this.f_24926_ = p_148148_;
            return this;
        }

        public Builder m_148143_(EntityPredicate p_148144_) {
            this.f_24927_ = p_148144_;
            return this;
        }

        public Builder m_24934_(Boolean p_24935_) {
            this.f_24928_ = p_24935_;
            return this;
        }

        public Builder m_148141_(DamageSourcePredicate p_148142_) {
            this.f_24929_ = p_148142_;
            return this;
        }

        public Builder m_24932_(DamageSourcePredicate.Builder p_24933_) {
            this.f_24929_ = p_24933_.m_25476_();
            return this;
        }

        public DamagePredicate m_24936_() {
            return new DamagePredicate(this.f_24925_, this.f_24926_, this.f_24927_, this.f_24928_, this.f_24929_);
        }
    }
}

