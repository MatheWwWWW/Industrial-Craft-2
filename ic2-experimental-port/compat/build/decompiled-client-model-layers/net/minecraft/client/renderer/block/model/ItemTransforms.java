/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.block.model.ItemTransform;

public class ItemTransforms {
    public static final ItemTransforms f_111786_ = new ItemTransforms();
    public final ItemTransform f_111787_;
    public final ItemTransform f_111788_;
    public final ItemTransform f_111789_;
    public final ItemTransform f_111790_;
    public final ItemTransform f_111791_;
    public final ItemTransform f_111792_;
    public final ItemTransform f_111793_;
    public final ItemTransform f_111794_;

    private ItemTransforms() {
        this(ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_);
    }

    public ItemTransforms(ItemTransforms p_111807_) {
        this.f_111787_ = p_111807_.f_111787_;
        this.f_111788_ = p_111807_.f_111788_;
        this.f_111789_ = p_111807_.f_111789_;
        this.f_111790_ = p_111807_.f_111790_;
        this.f_111791_ = p_111807_.f_111791_;
        this.f_111792_ = p_111807_.f_111792_;
        this.f_111793_ = p_111807_.f_111793_;
        this.f_111794_ = p_111807_.f_111794_;
    }

    public ItemTransforms(ItemTransform p_111798_, ItemTransform p_111799_, ItemTransform p_111800_, ItemTransform p_111801_, ItemTransform p_111802_, ItemTransform p_111803_, ItemTransform p_111804_, ItemTransform p_111805_) {
        this.f_111787_ = p_111798_;
        this.f_111788_ = p_111799_;
        this.f_111789_ = p_111800_;
        this.f_111790_ = p_111801_;
        this.f_111791_ = p_111802_;
        this.f_111792_ = p_111803_;
        this.f_111793_ = p_111804_;
        this.f_111794_ = p_111805_;
    }

    public ItemTransform m_111808_(TransformType p_111809_) {
        switch (p_111809_) {
            case THIRD_PERSON_LEFT_HAND: {
                return this.f_111787_;
            }
            case THIRD_PERSON_RIGHT_HAND: {
                return this.f_111788_;
            }
            case FIRST_PERSON_LEFT_HAND: {
                return this.f_111789_;
            }
            case FIRST_PERSON_RIGHT_HAND: {
                return this.f_111790_;
            }
            case HEAD: {
                return this.f_111791_;
            }
            case GUI: {
                return this.f_111792_;
            }
            case GROUND: {
                return this.f_111793_;
            }
            case FIXED: {
                return this.f_111794_;
            }
        }
        return ItemTransform.f_111754_;
    }

    public boolean m_111810_(TransformType p_111811_) {
        return this.m_111808_(p_111811_) != ItemTransform.f_111754_;
    }

    public static final class TransformType
    extends Enum<TransformType> {
        public static final /* enum */ TransformType NONE = new TransformType();
        public static final /* enum */ TransformType THIRD_PERSON_LEFT_HAND = new TransformType();
        public static final /* enum */ TransformType THIRD_PERSON_RIGHT_HAND = new TransformType();
        public static final /* enum */ TransformType FIRST_PERSON_LEFT_HAND = new TransformType();
        public static final /* enum */ TransformType FIRST_PERSON_RIGHT_HAND = new TransformType();
        public static final /* enum */ TransformType HEAD = new TransformType();
        public static final /* enum */ TransformType GUI = new TransformType();
        public static final /* enum */ TransformType GROUND = new TransformType();
        public static final /* enum */ TransformType FIXED = new TransformType();
        private static final /* synthetic */ TransformType[] $VALUES;

        public static TransformType[] values() {
            return (TransformType[])$VALUES.clone();
        }

        public static TransformType valueOf(String p_111843_) {
            return Enum.valueOf(TransformType.class, p_111843_);
        }

        public boolean m_111841_() {
            return this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND;
        }

        private static /* synthetic */ TransformType[] m_173494_() {
            return new TransformType[]{NONE, THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND, HEAD, GUI, GROUND, FIXED};
        }

        static {
            $VALUES = TransformType.m_173494_();
        }
    }

    protected static class Deserializer
    implements JsonDeserializer<ItemTransforms> {
        protected Deserializer() {
        }

        public ItemTransforms deserialize(JsonElement p_111820_, Type p_111821_, JsonDeserializationContext p_111822_) throws JsonParseException {
            JsonObject $$3 = p_111820_.getAsJsonObject();
            ItemTransform $$4 = this.m_111815_(p_111822_, $$3, "thirdperson_righthand");
            ItemTransform $$5 = this.m_111815_(p_111822_, $$3, "thirdperson_lefthand");
            if ($$5 == ItemTransform.f_111754_) {
                $$5 = $$4;
            }
            ItemTransform $$6 = this.m_111815_(p_111822_, $$3, "firstperson_righthand");
            ItemTransform $$7 = this.m_111815_(p_111822_, $$3, "firstperson_lefthand");
            if ($$7 == ItemTransform.f_111754_) {
                $$7 = $$6;
            }
            ItemTransform $$8 = this.m_111815_(p_111822_, $$3, "head");
            ItemTransform $$9 = this.m_111815_(p_111822_, $$3, "gui");
            ItemTransform $$10 = this.m_111815_(p_111822_, $$3, "ground");
            ItemTransform $$11 = this.m_111815_(p_111822_, $$3, "fixed");
            return new ItemTransforms($$5, $$4, $$7, $$6, $$8, $$9, $$10, $$11);
        }

        private ItemTransform m_111815_(JsonDeserializationContext p_111816_, JsonObject p_111817_, String p_111818_) {
            if (p_111817_.has(p_111818_)) {
                return (ItemTransform)p_111816_.deserialize(p_111817_.get(p_111818_), ItemTransform.class);
            }
            return ItemTransform.f_111754_;
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

