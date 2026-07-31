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
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;

public class DamageSourcePredicate {
    public static final DamageSourcePredicate f_25420_ = Builder.m_25471_().m_25476_();
    @Nullable
    private final Boolean f_25421_;
    @Nullable
    private final Boolean f_25422_;
    @Nullable
    private final Boolean f_25423_;
    @Nullable
    private final Boolean f_25424_;
    @Nullable
    private final Boolean f_25425_;
    @Nullable
    private final Boolean f_25426_;
    @Nullable
    private final Boolean f_25427_;
    @Nullable
    private final Boolean f_25428_;
    private final EntityPredicate f_25429_;
    private final EntityPredicate f_25430_;

    public DamageSourcePredicate(@Nullable Boolean p_25433_, @Nullable Boolean p_25434_, @Nullable Boolean p_25435_, @Nullable Boolean p_25436_, @Nullable Boolean p_25437_, @Nullable Boolean p_25438_, @Nullable Boolean p_25439_, @Nullable Boolean p_25440_, EntityPredicate p_25441_, EntityPredicate p_25442_) {
        this.f_25421_ = p_25433_;
        this.f_25422_ = p_25434_;
        this.f_25423_ = p_25435_;
        this.f_25424_ = p_25436_;
        this.f_25425_ = p_25437_;
        this.f_25426_ = p_25438_;
        this.f_25427_ = p_25439_;
        this.f_25428_ = p_25440_;
        this.f_25429_ = p_25441_;
        this.f_25430_ = p_25442_;
    }

    public boolean m_25448_(ServerPlayer p_25449_, DamageSource p_25450_) {
        return this.m_25444_(p_25449_.m_9236_(), p_25449_.m_20182_(), p_25450_);
    }

    public boolean m_25444_(ServerLevel p_25445_, Vec3 p_25446_, DamageSource p_25447_) {
        if (this == f_25420_) {
            return true;
        }
        if (this.f_25421_ != null && this.f_25421_.booleanValue() != p_25447_.m_19360_()) {
            return false;
        }
        if (this.f_25422_ != null && this.f_25422_.booleanValue() != p_25447_.m_19372_()) {
            return false;
        }
        if (this.f_25423_ != null && this.f_25423_.booleanValue() != p_25447_.m_19376_()) {
            return false;
        }
        if (this.f_25424_ != null && this.f_25424_.booleanValue() != p_25447_.m_19378_()) {
            return false;
        }
        if (this.f_25425_ != null && this.f_25425_.booleanValue() != p_25447_.m_19379_()) {
            return false;
        }
        if (this.f_25426_ != null && this.f_25426_.booleanValue() != p_25447_.m_19384_()) {
            return false;
        }
        if (this.f_25427_ != null && this.f_25427_.booleanValue() != p_25447_.m_19387_()) {
            return false;
        }
        if (this.f_25428_ != null && this.f_25428_ != (p_25447_ == DamageSource.f_19306_)) {
            return false;
        }
        if (!this.f_25429_.m_36607_(p_25445_, p_25446_, p_25447_.m_7640_())) {
            return false;
        }
        return this.f_25430_.m_36607_(p_25445_, p_25446_, p_25447_.m_7639_());
    }

    public static DamageSourcePredicate m_25451_(@Nullable JsonElement p_25452_) {
        if (p_25452_ == null || p_25452_.isJsonNull()) {
            return f_25420_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_25452_, "damage type");
        Boolean $$2 = DamageSourcePredicate.m_25453_($$1, "is_projectile");
        Boolean $$3 = DamageSourcePredicate.m_25453_($$1, "is_explosion");
        Boolean $$4 = DamageSourcePredicate.m_25453_($$1, "bypasses_armor");
        Boolean $$5 = DamageSourcePredicate.m_25453_($$1, "bypasses_invulnerability");
        Boolean $$6 = DamageSourcePredicate.m_25453_($$1, "bypasses_magic");
        Boolean $$7 = DamageSourcePredicate.m_25453_($$1, "is_fire");
        Boolean $$8 = DamageSourcePredicate.m_25453_($$1, "is_magic");
        Boolean $$9 = DamageSourcePredicate.m_25453_($$1, "is_lightning");
        EntityPredicate $$10 = EntityPredicate.m_36614_($$1.get("direct_entity"));
        EntityPredicate $$11 = EntityPredicate.m_36614_($$1.get("source_entity"));
        return new DamageSourcePredicate($$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11);
    }

    @Nullable
    private static Boolean m_25453_(JsonObject p_25454_, String p_25455_) {
        return p_25454_.has(p_25455_) ? Boolean.valueOf(GsonHelper.m_13912_(p_25454_, p_25455_)) : null;
    }

    public JsonElement m_25443_() {
        if (this == f_25420_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        this.m_25456_($$0, "is_projectile", this.f_25421_);
        this.m_25456_($$0, "is_explosion", this.f_25422_);
        this.m_25456_($$0, "bypasses_armor", this.f_25423_);
        this.m_25456_($$0, "bypasses_invulnerability", this.f_25424_);
        this.m_25456_($$0, "bypasses_magic", this.f_25425_);
        this.m_25456_($$0, "is_fire", this.f_25426_);
        this.m_25456_($$0, "is_magic", this.f_25427_);
        this.m_25456_($$0, "is_lightning", this.f_25428_);
        $$0.add("direct_entity", this.f_25429_.m_36606_());
        $$0.add("source_entity", this.f_25430_.m_36606_());
        return $$0;
    }

    private void m_25456_(JsonObject p_25457_, String p_25458_, @Nullable Boolean p_25459_) {
        if (p_25459_ != null) {
            p_25457_.addProperty(p_25458_, p_25459_);
        }
    }

    public static class Builder {
        @Nullable
        private Boolean f_25460_;
        @Nullable
        private Boolean f_25461_;
        @Nullable
        private Boolean f_25462_;
        @Nullable
        private Boolean f_25463_;
        @Nullable
        private Boolean f_25464_;
        @Nullable
        private Boolean f_25465_;
        @Nullable
        private Boolean f_25466_;
        @Nullable
        private Boolean f_25467_;
        private EntityPredicate f_25468_ = EntityPredicate.f_36550_;
        private EntityPredicate f_25469_ = EntityPredicate.f_36550_;

        public static Builder m_25471_() {
            return new Builder();
        }

        public Builder m_25474_(Boolean p_25475_) {
            this.f_25460_ = p_25475_;
            return this;
        }

        public Builder m_148235_(Boolean p_148236_) {
            this.f_25461_ = p_148236_;
            return this;
        }

        public Builder m_148237_(Boolean p_148238_) {
            this.f_25462_ = p_148238_;
            return this;
        }

        public Builder m_148239_(Boolean p_148240_) {
            this.f_25463_ = p_148240_;
            return this;
        }

        public Builder m_148241_(Boolean p_148242_) {
            this.f_25464_ = p_148242_;
            return this;
        }

        public Builder m_148243_(Boolean p_148244_) {
            this.f_25465_ = p_148244_;
            return this;
        }

        public Builder m_148245_(Boolean p_148246_) {
            this.f_25466_ = p_148246_;
            return this;
        }

        public Builder m_25477_(Boolean p_25478_) {
            this.f_25467_ = p_25478_;
            return this;
        }

        public Builder m_148229_(EntityPredicate p_148230_) {
            this.f_25468_ = p_148230_;
            return this;
        }

        public Builder m_25472_(EntityPredicate.Builder p_25473_) {
            this.f_25468_ = p_25473_.m_36662_();
            return this;
        }

        public Builder m_148233_(EntityPredicate p_148234_) {
            this.f_25469_ = p_148234_;
            return this;
        }

        public Builder m_148231_(EntityPredicate.Builder p_148232_) {
            this.f_25469_ = p_148232_.m_36662_();
            return this;
        }

        public DamageSourcePredicate m_25476_() {
            return new DamageSourcePredicate(this.f_25460_, this.f_25461_, this.f_25462_, this.f_25463_, this.f_25464_, this.f_25465_, this.f_25466_, this.f_25467_, this.f_25468_, this.f_25469_);
        }
    }
}

