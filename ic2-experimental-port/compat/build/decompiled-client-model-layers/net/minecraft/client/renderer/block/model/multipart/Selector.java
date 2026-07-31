/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Streams
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.multipart.AndCondition;
import net.minecraft.client.renderer.block.model.multipart.Condition;
import net.minecraft.client.renderer.block.model.multipart.KeyValueCondition;
import net.minecraft.client.renderer.block.model.multipart.OrCondition;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class Selector {
    private final Condition f_112015_;
    private final MultiVariant f_112016_;

    public Selector(Condition p_112018_, MultiVariant p_112019_) {
        if (p_112018_ == null) {
            throw new IllegalArgumentException("Missing condition for selector");
        }
        if (p_112019_ == null) {
            throw new IllegalArgumentException("Missing variant for selector");
        }
        this.f_112015_ = p_112018_;
        this.f_112016_ = p_112019_;
    }

    public MultiVariant m_112020_() {
        return this.f_112016_;
    }

    public Predicate<BlockState> m_112021_(StateDefinition<Block, BlockState> p_112022_) {
        return this.f_112015_.m_7289_(p_112022_);
    }

    public boolean equals(Object p_112024_) {
        return this == p_112024_;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public static class Deserializer
    implements JsonDeserializer<Selector> {
        public Selector deserialize(JsonElement p_112030_, Type p_112031_, JsonDeserializationContext p_112032_) throws JsonParseException {
            JsonObject $$3 = p_112030_.getAsJsonObject();
            return new Selector(this.m_112039_($$3), (MultiVariant)p_112032_.deserialize($$3.get("apply"), MultiVariant.class));
        }

        private Condition m_112039_(JsonObject p_112040_) {
            if (p_112040_.has("when")) {
                return Deserializer.m_112033_(GsonHelper.m_13930_(p_112040_, "when"));
            }
            return Condition.f_111922_;
        }

        @VisibleForTesting
        static Condition m_112033_(JsonObject p_112034_) {
            Set $$1 = p_112034_.entrySet();
            if ($$1.isEmpty()) {
                throw new JsonParseException("No elements found in selector");
            }
            if ($$1.size() == 1) {
                if (p_112034_.has("OR")) {
                    List $$2 = Streams.stream((Iterable)GsonHelper.m_13933_(p_112034_, "OR")).map(p_112038_ -> Deserializer.m_112033_(p_112038_.getAsJsonObject())).collect(Collectors.toList());
                    return new OrCondition($$2);
                }
                if (p_112034_.has("AND")) {
                    List $$3 = Streams.stream((Iterable)GsonHelper.m_13933_(p_112034_, "AND")).map(p_112028_ -> Deserializer.m_112033_(p_112028_.getAsJsonObject())).collect(Collectors.toList());
                    return new AndCondition($$3);
                }
                return Deserializer.m_112035_((Map.Entry)$$1.iterator().next());
            }
            return new AndCondition($$1.stream().map(Deserializer::m_112035_).collect(Collectors.toList()));
        }

        private static Condition m_112035_(Map.Entry<String, JsonElement> p_112036_) {
            return new KeyValueCondition(p_112036_.getKey(), p_112036_.getValue().getAsString());
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

