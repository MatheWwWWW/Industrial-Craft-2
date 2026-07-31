/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.lang.reflect.Type;
import net.minecraft.util.GsonHelper;

public class ItemTransform {
    public static final ItemTransform f_111754_ = new ItemTransform(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
    public final Vector3f f_111755_;
    public final Vector3f f_111756_;
    public final Vector3f f_111757_;

    public ItemTransform(Vector3f p_111760_, Vector3f p_111761_, Vector3f p_111762_) {
        this.f_111755_ = p_111760_.m_122281_();
        this.f_111756_ = p_111761_.m_122281_();
        this.f_111757_ = p_111762_.m_122281_();
    }

    public void m_111763_(boolean p_111764_, PoseStack p_111765_) {
        if (this == f_111754_) {
            return;
        }
        float $$2 = this.f_111755_.m_122239_();
        float $$3 = this.f_111755_.m_122260_();
        float $$4 = this.f_111755_.m_122269_();
        if (p_111764_) {
            $$3 = -$$3;
            $$4 = -$$4;
        }
        int $$5 = p_111764_ ? -1 : 1;
        p_111765_.m_85837_((float)$$5 * this.f_111756_.m_122239_(), this.f_111756_.m_122260_(), this.f_111756_.m_122269_());
        p_111765_.m_85845_(new Quaternion($$2, $$3, $$4, true));
        p_111765_.m_85841_(this.f_111757_.m_122239_(), this.f_111757_.m_122260_(), this.f_111757_.m_122269_());
    }

    public boolean equals(Object p_111767_) {
        if (this == p_111767_) {
            return true;
        }
        if (this.getClass() == p_111767_.getClass()) {
            ItemTransform $$1 = (ItemTransform)p_111767_;
            return this.f_111755_.equals($$1.f_111755_) && this.f_111757_.equals($$1.f_111757_) && this.f_111756_.equals($$1.f_111756_);
        }
        return false;
    }

    public int hashCode() {
        int $$0 = this.f_111755_.hashCode();
        $$0 = 31 * $$0 + this.f_111756_.hashCode();
        $$0 = 31 * $$0 + this.f_111757_.hashCode();
        return $$0;
    }

    protected static class Deserializer
    implements JsonDeserializer<ItemTransform> {
        private static final Vector3f f_111769_ = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f f_111770_ = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f f_111771_ = new Vector3f(1.0f, 1.0f, 1.0f);
        public static final float f_173492_ = 5.0f;
        public static final float f_173493_ = 4.0f;

        protected Deserializer() {
        }

        public ItemTransform deserialize(JsonElement p_111775_, Type p_111776_, JsonDeserializationContext p_111777_) throws JsonParseException {
            JsonObject $$3 = p_111775_.getAsJsonObject();
            Vector3f $$4 = this.m_111778_($$3, "rotation", f_111769_);
            Vector3f $$5 = this.m_111778_($$3, "translation", f_111770_);
            $$5.m_122261_(0.0625f);
            $$5.m_122242_(-5.0f, 5.0f);
            Vector3f $$6 = this.m_111778_($$3, "scale", f_111771_);
            $$6.m_122242_(-4.0f, 4.0f);
            return new ItemTransform($$4, $$5, $$6);
        }

        private Vector3f m_111778_(JsonObject p_111779_, String p_111780_, Vector3f p_111781_) {
            if (!p_111779_.has(p_111780_)) {
                return p_111781_;
            }
            JsonArray $$3 = GsonHelper.m_13933_(p_111779_, p_111780_);
            if ($$3.size() != 3) {
                throw new JsonParseException("Expected 3 " + p_111780_ + " values, found: " + $$3.size());
            }
            float[] $$4 = new float[3];
            for (int $$5 = 0; $$5 < $$4.length; ++$$5) {
                $$4[$$5] = GsonHelper.m_13888_($$3.get($$5), p_111780_ + "[" + $$5 + "]");
            }
            return new Vector3f($$4[0], $$4[1], $$4[2]);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

