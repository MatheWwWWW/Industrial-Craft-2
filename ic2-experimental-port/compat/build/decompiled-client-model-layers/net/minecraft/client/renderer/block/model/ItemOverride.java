/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class ItemOverride {
    private final ResourceLocation f_111713_;
    private final List<Predicate> f_111714_;

    public ItemOverride(ResourceLocation p_173447_, List<Predicate> p_173448_) {
        this.f_111713_ = p_173447_;
        this.f_111714_ = ImmutableList.copyOf(p_173448_);
    }

    public ResourceLocation m_111718_() {
        return this.f_111713_;
    }

    public Stream<Predicate> m_173449_() {
        return this.f_111714_.stream();
    }

    public static class Predicate {
        private final ResourceLocation f_173454_;
        private final float f_173455_;

        public Predicate(ResourceLocation p_173457_, float p_173458_) {
            this.f_173454_ = p_173457_;
            this.f_173455_ = p_173458_;
        }

        public ResourceLocation m_173459_() {
            return this.f_173454_;
        }

        public float m_173460_() {
            return this.f_173455_;
        }
    }

    protected static class Deserializer
    implements JsonDeserializer<ItemOverride> {
        protected Deserializer() {
        }

        public ItemOverride deserialize(JsonElement p_111725_, Type p_111726_, JsonDeserializationContext p_111727_) throws JsonParseException {
            JsonObject $$3 = p_111725_.getAsJsonObject();
            ResourceLocation $$4 = new ResourceLocation(GsonHelper.m_13906_($$3, "model"));
            List<Predicate> $$5 = this.m_173450_($$3);
            return new ItemOverride($$4, $$5);
        }

        protected List<Predicate> m_173450_(JsonObject p_173451_) {
            LinkedHashMap $$1 = Maps.newLinkedHashMap();
            JsonObject $$2 = GsonHelper.m_13930_(p_173451_, "predicate");
            for (Map.Entry $$3 : $$2.entrySet()) {
                $$1.put(new ResourceLocation((String)$$3.getKey()), Float.valueOf(GsonHelper.m_13888_((JsonElement)$$3.getValue(), (String)$$3.getKey())));
            }
            return (List)$$1.entrySet().stream().map(p_173453_ -> new Predicate((ResourceLocation)p_173453_.getKey(), ((Float)p_173453_.getValue()).floatValue())).collect(ImmutableList.toImmutableList());
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

