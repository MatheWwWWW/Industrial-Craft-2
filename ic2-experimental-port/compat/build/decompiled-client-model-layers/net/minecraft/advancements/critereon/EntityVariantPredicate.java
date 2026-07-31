/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class EntityVariantPredicate<V> {
    private static final String f_219082_ = "variant";
    final Registry<V> f_219083_;
    final Function<Entity, Optional<V>> f_219084_;
    final EntitySubPredicate.Type f_219085_;

    public static <V> EntityVariantPredicate<V> m_219093_(Registry<V> p_219094_, Function<Entity, Optional<V>> p_219095_) {
        return new EntityVariantPredicate<V>(p_219094_, p_219095_);
    }

    private EntityVariantPredicate(Registry<V> p_219087_, Function<Entity, Optional<V>> p_219088_) {
        this.f_219083_ = p_219087_;
        this.f_219084_ = p_219088_;
        this.f_219085_ = p_219092_ -> {
            String $$2 = GsonHelper.m_13906_(p_219092_, f_219082_);
            Object $$3 = p_219087_.m_7745_(ResourceLocation.m_135820_($$2));
            if ($$3 == null) {
                throw new JsonSyntaxException("Unknown variant: " + $$2);
            }
            return this.m_219096_($$3);
        };
    }

    public EntitySubPredicate.Type m_219089_() {
        return this.f_219085_;
    }

    public EntitySubPredicate m_219096_(final V p_219097_) {
        return new EntitySubPredicate(){

            @Override
            public boolean m_153246_(Entity p_219105_, ServerLevel p_219106_, @Nullable Vec3 p_219107_) {
                return EntityVariantPredicate.this.f_219084_.apply(p_219105_).filter(p_219110_ -> p_219110_.equals(p_219097_)).isPresent();
            }

            @Override
            public JsonObject m_213616_() {
                JsonObject $$0 = new JsonObject();
                $$0.addProperty(EntityVariantPredicate.f_219082_, EntityVariantPredicate.this.f_219083_.m_7981_(p_219097_).toString());
                return $$0;
            }

            @Override
            public EntitySubPredicate.Type m_213836_() {
                return EntityVariantPredicate.this.f_219085_;
            }
        };
    }
}

