/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Criterion {
    @Nullable
    private final CriterionTriggerInstance f_11412_;

    public Criterion(CriterionTriggerInstance p_11415_) {
        this.f_11412_ = p_11415_;
    }

    public Criterion() {
        this.f_11412_ = null;
    }

    public void m_11423_(FriendlyByteBuf p_11424_) {
    }

    public static Criterion m_11417_(JsonObject p_11418_, DeserializationContext p_11419_) {
        ResourceLocation $$2 = new ResourceLocation(GsonHelper.m_13906_(p_11418_, "trigger"));
        CriterionTrigger $$3 = CriteriaTriggers.m_10597_($$2);
        if ($$3 == null) {
            throw new JsonSyntaxException("Invalid criterion trigger: " + $$2);
        }
        Object $$4 = $$3.m_5868_(GsonHelper.m_13841_(p_11418_, "conditions", new JsonObject()), p_11419_);
        return new Criterion((CriterionTriggerInstance)$$4);
    }

    public static Criterion m_11429_(FriendlyByteBuf p_11430_) {
        return new Criterion();
    }

    public static Map<String, Criterion> m_11426_(JsonObject p_11427_, DeserializationContext p_11428_) {
        HashMap $$2 = Maps.newHashMap();
        for (Map.Entry $$3 : p_11427_.entrySet()) {
            $$2.put((String)$$3.getKey(), Criterion.m_11417_(GsonHelper.m_13918_((JsonElement)$$3.getValue(), "criterion"), p_11428_));
        }
        return $$2;
    }

    public static Map<String, Criterion> m_11431_(FriendlyByteBuf p_11432_) {
        return p_11432_.m_236847_(FriendlyByteBuf::m_130277_, Criterion::m_11429_);
    }

    public static void m_11420_(Map<String, Criterion> p_11421_, FriendlyByteBuf p_11422_) {
        p_11422_.m_236831_(p_11421_, FriendlyByteBuf::m_130070_, (p_145258_, p_145259_) -> p_145259_.m_11423_((FriendlyByteBuf)((Object)p_145258_)));
    }

    @Nullable
    public CriterionTriggerInstance m_11416_() {
        return this.f_11412_;
    }

    public JsonElement m_11425_() {
        if (this.f_11412_ == null) {
            throw new JsonSyntaxException("Missing trigger");
        }
        JsonObject $$0 = new JsonObject();
        $$0.addProperty("trigger", this.f_11412_.m_7294_().toString());
        JsonObject $$1 = this.f_11412_.m_7683_(SerializationContext.f_64768_);
        if ($$1.size() != 0) {
            $$0.add("conditions", (JsonElement)$$1);
        }
        return $$0;
    }
}

