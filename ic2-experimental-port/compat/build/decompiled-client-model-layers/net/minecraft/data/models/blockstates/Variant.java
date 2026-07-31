/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.data.models.blockstates;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.data.models.blockstates.VariantProperty;

public class Variant
implements Supplier<JsonElement> {
    private final Map<VariantProperty<?>, VariantProperty.Value> f_125499_ = Maps.newLinkedHashMap();

    public <T> Variant m_125511_(VariantProperty<T> p_125512_, T p_125513_) {
        VariantProperty.Value $$2 = this.f_125499_.put(p_125512_, p_125512_.m_125553_(p_125513_));
        if ($$2 != null) {
            throw new IllegalStateException("Replacing value of " + $$2 + " with " + p_125513_);
        }
        return this;
    }

    public static Variant m_125501_() {
        return new Variant();
    }

    public static Variant m_125508_(Variant p_125509_, Variant p_125510_) {
        Variant $$2 = new Variant();
        $$2.f_125499_.putAll(p_125509_.f_125499_);
        $$2.f_125499_.putAll(p_125510_.f_125499_);
        return $$2;
    }

    @Override
    public JsonElement get() {
        JsonObject $$0 = new JsonObject();
        this.f_125499_.values().forEach(p_125507_ -> p_125507_.m_125563_($$0));
        return $$0;
    }

    public static JsonElement m_125514_(List<Variant> p_125515_) {
        if (p_125515_.size() == 1) {
            return p_125515_.get(0).get();
        }
        JsonArray $$1 = new JsonArray();
        p_125515_.forEach(p_125504_ -> $$1.add(p_125504_.get()));
        return $$1;
    }

    @Override
    public /* synthetic */ Object get() {
        return this.get();
    }
}

