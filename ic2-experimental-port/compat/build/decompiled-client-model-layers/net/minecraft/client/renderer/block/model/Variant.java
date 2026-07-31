/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Transformation;
import java.lang.reflect.Type;
import java.util.Objects;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Variant
implements ModelState {
    private final ResourceLocation f_111874_;
    private final Transformation f_111875_;
    private final boolean f_111876_;
    private final int f_111877_;

    public Variant(ResourceLocation p_111879_, Transformation p_111880_, boolean p_111881_, int p_111882_) {
        this.f_111874_ = p_111879_;
        this.f_111875_ = p_111880_;
        this.f_111876_ = p_111881_;
        this.f_111877_ = p_111882_;
    }

    public ResourceLocation m_111883_() {
        return this.f_111874_;
    }

    @Override
    public Transformation m_6189_() {
        return this.f_111875_;
    }

    @Override
    public boolean m_7538_() {
        return this.f_111876_;
    }

    public int m_111886_() {
        return this.f_111877_;
    }

    public String toString() {
        return "Variant{modelLocation=" + this.f_111874_ + ", rotation=" + this.f_111875_ + ", uvLock=" + this.f_111876_ + ", weight=" + this.f_111877_ + "}";
    }

    public boolean equals(Object p_111888_) {
        if (this == p_111888_) {
            return true;
        }
        if (p_111888_ instanceof Variant) {
            Variant $$1 = (Variant)p_111888_;
            return this.f_111874_.equals($$1.f_111874_) && Objects.equals(this.f_111875_, $$1.f_111875_) && this.f_111876_ == $$1.f_111876_ && this.f_111877_ == $$1.f_111877_;
        }
        return false;
    }

    public int hashCode() {
        int $$0 = this.f_111874_.hashCode();
        $$0 = 31 * $$0 + this.f_111875_.hashCode();
        $$0 = 31 * $$0 + Boolean.valueOf(this.f_111876_).hashCode();
        $$0 = 31 * $$0 + this.f_111877_;
        return $$0;
    }

    public static class Deserializer
    implements JsonDeserializer<Variant> {
        @VisibleForTesting
        static final boolean f_173495_ = false;
        @VisibleForTesting
        static final int f_173496_ = 1;
        @VisibleForTesting
        static final int f_173497_ = 0;
        @VisibleForTesting
        static final int f_173498_ = 0;

        public Variant deserialize(JsonElement p_111893_, Type p_111894_, JsonDeserializationContext p_111895_) throws JsonParseException {
            JsonObject $$3 = p_111893_.getAsJsonObject();
            ResourceLocation $$4 = this.m_111898_($$3);
            BlockModelRotation $$5 = this.m_111896_($$3);
            boolean $$6 = this.m_111902_($$3);
            int $$7 = this.m_111900_($$3);
            return new Variant($$4, $$5.m_6189_(), $$6, $$7);
        }

        private boolean m_111902_(JsonObject p_111903_) {
            return GsonHelper.m_13855_(p_111903_, "uvlock", false);
        }

        protected BlockModelRotation m_111896_(JsonObject p_111897_) {
            int $$2;
            int $$1 = GsonHelper.m_13824_(p_111897_, "x", 0);
            BlockModelRotation $$3 = BlockModelRotation.m_119153_($$1, $$2 = GsonHelper.m_13824_(p_111897_, "y", 0));
            if ($$3 == null) {
                throw new JsonParseException("Invalid BlockModelRotation x: " + $$1 + ", y: " + $$2);
            }
            return $$3;
        }

        protected ResourceLocation m_111898_(JsonObject p_111899_) {
            return new ResourceLocation(GsonHelper.m_13906_(p_111899_, "model"));
        }

        protected int m_111900_(JsonObject p_111901_) {
            int $$1 = GsonHelper.m_13824_(p_111901_, "weight", 1);
            if ($$1 < 1) {
                throw new JsonParseException("Invalid weight " + $$1 + " found, expected integer >= 1");
            }
            return $$1;
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

