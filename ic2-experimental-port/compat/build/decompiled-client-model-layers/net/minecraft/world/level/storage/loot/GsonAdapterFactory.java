/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.SerializerType;

public class GsonAdapterFactory {
    public static <E, T extends SerializerType<E>> Builder<E, T> m_78801_(Registry<T> p_78802_, String p_78803_, String p_78804_, Function<E, T> p_78805_) {
        return new Builder<E, T>(p_78802_, p_78803_, p_78804_, p_78805_);
    }

    public static class Builder<E, T extends SerializerType<E>> {
        private final Registry<T> f_78806_;
        private final String f_78807_;
        private final String f_78808_;
        private final Function<E, T> f_78809_;
        @Nullable
        private Pair<T, InlineSerializer<? extends E>> f_164983_;
        @Nullable
        private T f_78810_;

        Builder(Registry<T> p_78812_, String p_78813_, String p_78814_, Function<E, T> p_78815_) {
            this.f_78806_ = p_78812_;
            this.f_78807_ = p_78813_;
            this.f_78808_ = p_78814_;
            this.f_78809_ = p_78815_;
        }

        public Builder<E, T> m_164986_(T p_164987_, InlineSerializer<? extends E> p_164988_) {
            this.f_164983_ = Pair.of(p_164987_, p_164988_);
            return this;
        }

        public Builder<E, T> m_164984_(T p_164985_) {
            this.f_78810_ = p_164985_;
            return this;
        }

        public Object m_78822_() {
            return new JsonAdapter<E, T>(this.f_78806_, this.f_78807_, this.f_78808_, this.f_78809_, this.f_78810_, this.f_164983_);
        }
    }

    public static interface InlineSerializer<T> {
        public JsonElement m_142413_(T var1, JsonSerializationContext var2);

        public T m_142268_(JsonElement var1, JsonDeserializationContext var2);
    }

    static class JsonAdapter<E, T extends SerializerType<E>>
    implements JsonDeserializer<E>,
    JsonSerializer<E> {
        private final Registry<T> f_78829_;
        private final String f_78830_;
        private final String f_78831_;
        private final Function<E, T> f_78832_;
        @Nullable
        private final T f_78833_;
        @Nullable
        private final Pair<T, InlineSerializer<? extends E>> f_164993_;

        JsonAdapter(Registry<T> p_164995_, String p_164996_, String p_164997_, Function<E, T> p_164998_, @Nullable T p_164999_, @Nullable Pair<T, InlineSerializer<? extends E>> p_165000_) {
            this.f_78829_ = p_164995_;
            this.f_78830_ = p_164996_;
            this.f_78831_ = p_164997_;
            this.f_78832_ = p_164998_;
            this.f_78833_ = p_164999_;
            this.f_164993_ = p_165000_;
        }

        public E deserialize(JsonElement p_78848_, Type p_78849_, JsonDeserializationContext p_78850_) throws JsonParseException {
            if (p_78848_.isJsonObject()) {
                SerializerType $$7;
                JsonObject $$3 = GsonHelper.m_13918_(p_78848_, this.f_78830_);
                String $$4 = GsonHelper.m_13851_($$3, this.f_78831_, "");
                if ($$4.isEmpty()) {
                    T $$5 = this.f_78833_;
                } else {
                    ResourceLocation $$6 = new ResourceLocation($$4);
                    $$7 = (SerializerType)this.f_78829_.m_7745_($$6);
                }
                if ($$7 == null) {
                    throw new JsonSyntaxException("Unknown type '" + $$4 + "'");
                }
                return (E)$$7.m_79331_().m_7561_($$3, p_78850_);
            }
            if (this.f_164993_ == null) {
                throw new UnsupportedOperationException("Object " + p_78848_ + " can't be deserialized");
            }
            return (E)((InlineSerializer)this.f_164993_.getSecond()).m_142268_(p_78848_, p_78850_);
        }

        public JsonElement serialize(E p_78852_, Type p_78853_, JsonSerializationContext p_78854_) {
            SerializerType $$3 = (SerializerType)this.f_78832_.apply(p_78852_);
            if (this.f_164993_ != null && this.f_164993_.getFirst() == $$3) {
                return ((InlineSerializer)this.f_164993_.getSecond()).m_142413_(p_78852_, p_78854_);
            }
            if ($$3 == null) {
                throw new JsonSyntaxException("Unknown type: " + p_78852_);
            }
            JsonObject $$4 = new JsonObject();
            $$4.addProperty(this.f_78831_, this.f_78829_.m_7981_($$3).toString());
            $$3.m_79331_().m_6170_($$4, p_78852_, p_78854_);
            return $$4;
        }
    }
}

