/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Vector3f;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;

public class BlockElement {
    private static final boolean f_173411_ = false;
    private static final float f_173412_ = -16.0f;
    private static final float f_173413_ = 32.0f;
    public final Vector3f f_111308_;
    public final Vector3f f_111309_;
    public final Map<Direction, BlockElementFace> f_111310_;
    public final BlockElementRotation f_111311_;
    public final boolean f_111312_;

    public BlockElement(Vector3f p_111314_, Vector3f p_111315_, Map<Direction, BlockElementFace> p_111316_, @Nullable BlockElementRotation p_111317_, boolean p_111318_) {
        this.f_111308_ = p_111314_;
        this.f_111309_ = p_111315_;
        this.f_111310_ = p_111316_;
        this.f_111311_ = p_111317_;
        this.f_111312_ = p_111318_;
        this.m_111319_();
    }

    private void m_111319_() {
        for (Map.Entry<Direction, BlockElementFace> $$0 : this.f_111310_.entrySet()) {
            float[] $$1 = this.m_111320_($$0.getKey());
            $$0.getValue().f_111357_.m_111394_($$1);
        }
    }

    private float[] m_111320_(Direction p_111321_) {
        switch (p_111321_) {
            case DOWN: {
                return new float[]{this.f_111308_.m_122239_(), 16.0f - this.f_111309_.m_122269_(), this.f_111309_.m_122239_(), 16.0f - this.f_111308_.m_122269_()};
            }
            case UP: {
                return new float[]{this.f_111308_.m_122239_(), this.f_111308_.m_122269_(), this.f_111309_.m_122239_(), this.f_111309_.m_122269_()};
            }
            default: {
                return new float[]{16.0f - this.f_111309_.m_122239_(), 16.0f - this.f_111309_.m_122260_(), 16.0f - this.f_111308_.m_122239_(), 16.0f - this.f_111308_.m_122260_()};
            }
            case SOUTH: {
                return new float[]{this.f_111308_.m_122239_(), 16.0f - this.f_111309_.m_122260_(), this.f_111309_.m_122239_(), 16.0f - this.f_111308_.m_122260_()};
            }
            case WEST: {
                return new float[]{this.f_111308_.m_122269_(), 16.0f - this.f_111309_.m_122260_(), this.f_111309_.m_122269_(), 16.0f - this.f_111308_.m_122260_()};
            }
            case EAST: 
        }
        return new float[]{16.0f - this.f_111309_.m_122269_(), 16.0f - this.f_111309_.m_122260_(), 16.0f - this.f_111308_.m_122269_(), 16.0f - this.f_111308_.m_122260_()};
    }

    protected static class Deserializer
    implements JsonDeserializer<BlockElement> {
        private static final boolean f_173414_ = true;

        protected Deserializer() {
        }

        public BlockElement deserialize(JsonElement p_111329_, Type p_111330_, JsonDeserializationContext p_111331_) throws JsonParseException {
            JsonObject $$3 = p_111329_.getAsJsonObject();
            Vector3f $$4 = this.m_111352_($$3);
            Vector3f $$5 = this.m_111346_($$3);
            BlockElementRotation $$6 = this.m_111332_($$3);
            Map<Direction, BlockElementFace> $$7 = this.m_111325_(p_111331_, $$3);
            if ($$3.has("shade") && !GsonHelper.m_13880_($$3, "shade")) {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            boolean $$8 = GsonHelper.m_13855_($$3, "shade", true);
            return new BlockElement($$4, $$5, $$7, $$6, $$8);
        }

        @Nullable
        private BlockElementRotation m_111332_(JsonObject p_111333_) {
            BlockElementRotation $$1 = null;
            if (p_111333_.has("rotation")) {
                JsonObject $$2 = GsonHelper.m_13930_(p_111333_, "rotation");
                Vector3f $$3 = this.m_111334_($$2, "origin");
                $$3.m_122261_(0.0625f);
                Direction.Axis $$4 = this.m_111344_($$2);
                float $$5 = this.m_111342_($$2);
                boolean $$6 = GsonHelper.m_13855_($$2, "rescale", false);
                $$1 = new BlockElementRotation($$3, $$4, $$5, $$6);
            }
            return $$1;
        }

        private float m_111342_(JsonObject p_111343_) {
            float $$1 = GsonHelper.m_13915_(p_111343_, "angle");
            if ($$1 != 0.0f && Mth.m_14154_($$1) != 22.5f && Mth.m_14154_($$1) != 45.0f) {
                throw new JsonParseException("Invalid rotation " + $$1 + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            return $$1;
        }

        private Direction.Axis m_111344_(JsonObject p_111345_) {
            String $$1 = GsonHelper.m_13906_(p_111345_, "axis");
            Direction.Axis $$2 = Direction.Axis.m_122473_($$1.toLowerCase(Locale.ROOT));
            if ($$2 == null) {
                throw new JsonParseException("Invalid rotation axis: " + $$1);
            }
            return $$2;
        }

        private Map<Direction, BlockElementFace> m_111325_(JsonDeserializationContext p_111326_, JsonObject p_111327_) {
            Map<Direction, BlockElementFace> $$2 = this.m_111339_(p_111326_, p_111327_);
            if ($$2.isEmpty()) {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            return $$2;
        }

        private Map<Direction, BlockElementFace> m_111339_(JsonDeserializationContext p_111340_, JsonObject p_111341_) {
            EnumMap $$2 = Maps.newEnumMap(Direction.class);
            JsonObject $$3 = GsonHelper.m_13930_(p_111341_, "faces");
            for (Map.Entry $$4 : $$3.entrySet()) {
                Direction $$5 = this.m_111337_((String)$$4.getKey());
                $$2.put($$5, (BlockElementFace)p_111340_.deserialize((JsonElement)$$4.getValue(), BlockElementFace.class));
            }
            return $$2;
        }

        private Direction m_111337_(String p_111338_) {
            Direction $$1 = Direction.m_122402_(p_111338_);
            if ($$1 == null) {
                throw new JsonParseException("Unknown facing: " + p_111338_);
            }
            return $$1;
        }

        private Vector3f m_111346_(JsonObject p_111347_) {
            Vector3f $$1 = this.m_111334_(p_111347_, "to");
            if ($$1.m_122239_() < -16.0f || $$1.m_122260_() < -16.0f || $$1.m_122269_() < -16.0f || $$1.m_122239_() > 32.0f || $$1.m_122260_() > 32.0f || $$1.m_122269_() > 32.0f) {
                throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + $$1);
            }
            return $$1;
        }

        private Vector3f m_111352_(JsonObject p_111353_) {
            Vector3f $$1 = this.m_111334_(p_111353_, "from");
            if ($$1.m_122239_() < -16.0f || $$1.m_122260_() < -16.0f || $$1.m_122269_() < -16.0f || $$1.m_122239_() > 32.0f || $$1.m_122260_() > 32.0f || $$1.m_122269_() > 32.0f) {
                throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + $$1);
            }
            return $$1;
        }

        private Vector3f m_111334_(JsonObject p_111335_, String p_111336_) {
            JsonArray $$2 = GsonHelper.m_13933_(p_111335_, p_111336_);
            if ($$2.size() != 3) {
                throw new JsonParseException("Expected 3 " + p_111336_ + " values, found: " + $$2.size());
            }
            float[] $$3 = new float[3];
            for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
                $$3[$$4] = GsonHelper.m_13888_($$2.get($$4), p_111336_ + "[" + $$4 + "]");
            }
            return new Vector3f($$3[0], $$3[1], $$3[2]);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

