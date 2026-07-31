/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.ImmutableBiMap
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.EntityVariantPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.advancements.critereon.LighthingBoltPredicate;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.advancements.critereon.SlimePredicate;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.phys.Vec3;

public interface EntitySubPredicate {
    public static final EntitySubPredicate f_218826_ = new EntitySubPredicate(){

        @Override
        public boolean m_153246_(Entity p_218841_, ServerLevel p_218842_, @Nullable Vec3 p_218843_) {
            return true;
        }

        @Override
        public JsonObject m_213616_() {
            return new JsonObject();
        }

        @Override
        public Type m_213836_() {
            return Types.f_218847_;
        }
    };

    public static EntitySubPredicate m_218835_(@Nullable JsonElement p_218836_) {
        if (p_218836_ == null || p_218836_.isJsonNull()) {
            return f_218826_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_218836_, "type_specific");
        String $$2 = GsonHelper.m_13851_($$1, "type", null);
        if ($$2 == null) {
            return f_218826_;
        }
        Type $$3 = (Type)Types.f_218854_.get((Object)$$2);
        if ($$3 == null) {
            throw new JsonSyntaxException("Unknown sub-predicate type: " + $$2);
        }
        return $$3.m_218845_($$1);
    }

    public boolean m_153246_(Entity var1, ServerLevel var2, @Nullable Vec3 var3);

    public JsonObject m_213616_();

    default public JsonElement m_218837_() {
        if (this.m_213836_() == Types.f_218847_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = this.m_213616_();
        String $$1 = (String)Types.f_218854_.inverse().get((Object)this.m_213836_());
        $$0.addProperty("type", $$1);
        return $$0;
    }

    public Type m_213836_();

    public static EntitySubPredicate m_218831_(CatVariant p_218832_) {
        return Types.f_218852_.m_219096_(p_218832_);
    }

    public static EntitySubPredicate m_218833_(FrogVariant p_218834_) {
        return Types.f_218853_.m_219096_(p_218834_);
    }

    public static final class Types {
        public static final Type f_218847_ = p_218860_ -> f_218826_;
        public static final Type f_218848_ = LighthingBoltPredicate::m_220332_;
        public static final Type f_218849_ = FishingHookPredicate::m_219719_;
        public static final Type f_218850_ = PlayerPredicate::m_222491_;
        public static final Type f_218851_ = SlimePredicate::m_223428_;
        public static final EntityVariantPredicate<CatVariant> f_218852_ = EntityVariantPredicate.m_219093_(Registry.f_235732_, p_218862_ -> {
            Optional<Object> optional;
            if (p_218862_ instanceof Cat) {
                Cat $$1 = (Cat)p_218862_;
                optional = Optional.of($$1.m_218139_());
            } else {
                optional = Optional.empty();
            }
            return optional;
        });
        public static final EntityVariantPredicate<FrogVariant> f_218853_ = EntityVariantPredicate.m_219093_(Registry.f_235734_, p_218858_ -> {
            Optional<Object> optional;
            if (p_218858_ instanceof Frog) {
                Frog $$1 = (Frog)p_218858_;
                optional = Optional.of($$1.m_218524_());
            } else {
                optional = Optional.empty();
            }
            return optional;
        });
        public static final BiMap<String, Type> f_218854_ = ImmutableBiMap.of((Object)"any", (Object)f_218847_, (Object)"lightning", (Object)f_218848_, (Object)"fishing_hook", (Object)f_218849_, (Object)"player", (Object)f_218850_, (Object)"slime", (Object)f_218851_, (Object)"cat", (Object)f_218852_.m_219089_(), (Object)"frog", (Object)f_218853_.m_219089_());
    }

    public static interface Type {
        public EntitySubPredicate m_218845_(JsonObject var1);
    }
}

