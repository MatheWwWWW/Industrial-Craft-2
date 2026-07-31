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
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;

public class BlockFaceUV {
    public float[] f_111387_;
    public final int f_111388_;

    public BlockFaceUV(@Nullable float[] p_111390_, int p_111391_) {
        this.f_111387_ = p_111390_;
        this.f_111388_ = p_111391_;
    }

    public float m_111392_(int p_111393_) {
        if (this.f_111387_ == null) {
            throw new NullPointerException("uvs");
        }
        int $$1 = this.m_111400_(p_111393_);
        return this.f_111387_[$$1 == 0 || $$1 == 1 ? 0 : 2];
    }

    public float m_111396_(int p_111397_) {
        if (this.f_111387_ == null) {
            throw new NullPointerException("uvs");
        }
        int $$1 = this.m_111400_(p_111397_);
        return this.f_111387_[$$1 == 0 || $$1 == 3 ? 1 : 3];
    }

    private int m_111400_(int p_111401_) {
        return (p_111401_ + this.f_111388_ / 90) % 4;
    }

    public int m_111398_(int p_111399_) {
        return (p_111399_ + 4 - this.f_111388_ / 90) % 4;
    }

    public void m_111394_(float[] p_111395_) {
        if (this.f_111387_ == null) {
            this.f_111387_ = p_111395_;
        }
    }

    protected static class Deserializer
    implements JsonDeserializer<BlockFaceUV> {
        private static final int f_173417_ = 0;

        protected Deserializer() {
        }

        public BlockFaceUV deserialize(JsonElement p_111404_, Type p_111405_, JsonDeserializationContext p_111406_) throws JsonParseException {
            JsonObject $$3 = p_111404_.getAsJsonObject();
            float[] $$4 = this.m_111409_($$3);
            int $$5 = this.m_111407_($$3);
            return new BlockFaceUV($$4, $$5);
        }

        protected int m_111407_(JsonObject p_111408_) {
            int $$1 = GsonHelper.m_13824_(p_111408_, "rotation", 0);
            if ($$1 < 0 || $$1 % 90 != 0 || $$1 / 90 > 3) {
                throw new JsonParseException("Invalid rotation " + $$1 + " found, only 0/90/180/270 allowed");
            }
            return $$1;
        }

        @Nullable
        private float[] m_111409_(JsonObject p_111410_) {
            if (!p_111410_.has("uv")) {
                return null;
            }
            JsonArray $$1 = GsonHelper.m_13933_(p_111410_, "uv");
            if ($$1.size() != 4) {
                throw new JsonParseException("Expected 4 uv values, found: " + $$1.size());
            }
            float[] $$2 = new float[4];
            for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
                $$2[$$3] = GsonHelper.m_13888_($$1.get($$3), "uv[" + $$3 + "]");
            }
            return $$2;
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

