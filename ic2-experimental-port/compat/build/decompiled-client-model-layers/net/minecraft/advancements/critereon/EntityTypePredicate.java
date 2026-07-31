/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;

public abstract class EntityTypePredicate {
    public static final EntityTypePredicate f_37636_ = new EntityTypePredicate(){

        @Override
        public boolean m_7484_(EntityType<?> p_37652_) {
            return true;
        }

        @Override
        public JsonElement m_5908_() {
            return JsonNull.INSTANCE;
        }
    };
    private static final Joiner f_37637_ = Joiner.on((String)", ");

    public abstract boolean m_7484_(EntityType<?> var1);

    public abstract JsonElement m_5908_();

    public static EntityTypePredicate m_37643_(@Nullable JsonElement p_37644_) {
        if (p_37644_ == null || p_37644_.isJsonNull()) {
            return f_37636_;
        }
        String $$1 = GsonHelper.m_13805_(p_37644_, "type");
        if ($$1.startsWith("#")) {
            ResourceLocation $$2 = new ResourceLocation($$1.substring(1));
            return new TagPredicate(TagKey.m_203882_(Registry.f_122903_, $$2));
        }
        ResourceLocation $$3 = new ResourceLocation($$1);
        EntityType<?> $$4 = Registry.f_122826_.m_6612_($$3).orElseThrow(() -> new JsonSyntaxException("Unknown entity type '" + $$3 + "', valid types are: " + f_37637_.join(Registry.f_122826_.m_6566_())));
        return new TypePredicate($$4);
    }

    public static EntityTypePredicate m_37647_(EntityType<?> p_37648_) {
        return new TypePredicate(p_37648_);
    }

    public static EntityTypePredicate m_204081_(TagKey<EntityType<?>> p_204082_) {
        return new TagPredicate(p_204082_);
    }

    static class TagPredicate
    extends EntityTypePredicate {
        private final TagKey<EntityType<?>> f_37653_;

        public TagPredicate(TagKey<EntityType<?>> p_204084_) {
            this.f_37653_ = p_204084_;
        }

        @Override
        public boolean m_7484_(EntityType<?> p_37658_) {
            return p_37658_.m_204039_(this.f_37653_);
        }

        @Override
        public JsonElement m_5908_() {
            return new JsonPrimitive("#" + this.f_37653_.f_203868_());
        }
    }

    static class TypePredicate
    extends EntityTypePredicate {
        private final EntityType<?> f_37659_;

        public TypePredicate(EntityType<?> p_37661_) {
            this.f_37659_ = p_37661_;
        }

        @Override
        public boolean m_7484_(EntityType<?> p_37664_) {
            return this.f_37659_ == p_37664_;
        }

        @Override
        public JsonElement m_5908_() {
            return new JsonPrimitive(Registry.f_122826_.m_7981_(this.f_37659_).toString());
        }
    }
}

