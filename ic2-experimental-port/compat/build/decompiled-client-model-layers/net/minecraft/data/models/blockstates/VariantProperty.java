/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.data.models.blockstates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Function;

public class VariantProperty<T> {
    final String f_125546_;
    final Function<T, JsonElement> f_125547_;

    public VariantProperty(String p_125549_, Function<T, JsonElement> p_125550_) {
        this.f_125546_ = p_125549_;
        this.f_125547_ = p_125550_;
    }

    public Value m_125553_(T p_125554_) {
        return new Value(p_125554_);
    }

    public String toString() {
        return this.f_125546_;
    }

    public class Value {
        private final T f_125559_;

        public Value(T p_125562_) {
            this.f_125559_ = p_125562_;
        }

        public VariantProperty<T> m_176453_() {
            return VariantProperty.this;
        }

        public void m_125563_(JsonObject p_125564_) {
            p_125564_.add(VariantProperty.this.f_125546_, VariantProperty.this.f_125547_.apply(this.f_125559_));
        }

        public String toString() {
            return VariantProperty.this.f_125546_ + "=" + this.f_125559_;
        }
    }
}

