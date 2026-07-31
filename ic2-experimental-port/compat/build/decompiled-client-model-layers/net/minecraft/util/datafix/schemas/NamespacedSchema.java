/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.Const$PrimitiveType
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.resources.ResourceLocation;

public class NamespacedSchema
extends Schema {
    public static final PrimitiveCodec<String> f_17304_ = new PrimitiveCodec<String>(){

        public <T> DataResult<String> read(DynamicOps<T> p_17321_, T p_17322_) {
            return p_17321_.getStringValue(p_17322_).map(NamespacedSchema::m_17311_);
        }

        public <T> T write(DynamicOps<T> p_17318_, String p_17319_) {
            return (T)p_17318_.createString(p_17319_);
        }

        public String toString() {
            return "NamespacedString";
        }

        public /* synthetic */ Object write(DynamicOps dynamicOps, Object object) {
            return this.write(dynamicOps, (String)object);
        }
    };
    private static final Type<String> f_17305_ = new Const.PrimitiveType(f_17304_);

    public NamespacedSchema(int p_17308_, Schema p_17309_) {
        super(p_17308_, p_17309_);
    }

    public static String m_17311_(String p_17312_) {
        ResourceLocation $$1 = ResourceLocation.m_135820_(p_17312_);
        if ($$1 != null) {
            return $$1.toString();
        }
        return p_17312_;
    }

    public static Type<String> m_17310_() {
        return f_17305_;
    }

    public Type<?> getChoiceType(DSL.TypeReference p_17314_, String p_17315_) {
        return super.getChoiceType(p_17314_, NamespacedSchema.m_17311_(p_17315_));
    }
}

