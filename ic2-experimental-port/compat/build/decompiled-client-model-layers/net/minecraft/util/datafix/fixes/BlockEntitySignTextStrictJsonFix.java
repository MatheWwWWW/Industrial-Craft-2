/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.util.datafix.fixes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.lang3.StringUtils;

public class BlockEntitySignTextStrictJsonFix
extends NamedEntityFix {
    public static final Gson f_14861_ = new GsonBuilder().registerTypeAdapter(Component.class, (Object)new JsonDeserializer<Component>(){

        public MutableComponent deserialize(JsonElement p_14875_, Type p_14876_, JsonDeserializationContext p_14877_) throws JsonParseException {
            if (p_14875_.isJsonPrimitive()) {
                return Component.m_237113_(p_14875_.getAsString());
            }
            if (p_14875_.isJsonArray()) {
                JsonArray $$3 = p_14875_.getAsJsonArray();
                MutableComponent $$4 = null;
                for (JsonElement $$5 : $$3) {
                    MutableComponent $$6 = this.deserialize($$5, $$5.getClass(), p_14877_);
                    if ($$4 == null) {
                        $$4 = $$6;
                        continue;
                    }
                    $$4.m_7220_($$6);
                }
                return $$4;
            }
            throw new JsonParseException("Don't know how to turn " + p_14875_ + " into a Component");
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }).create();

    public BlockEntitySignTextStrictJsonFix(Schema p_14864_, boolean p_14865_) {
        super(p_14864_, p_14865_, "BlockEntitySignTextStrictJsonFix", References.f_16781_, "Sign");
    }

    private Dynamic<?> m_14870_(Dynamic<?> p_14871_, String p_14872_) {
        String $$2 = p_14871_.get(p_14872_).asString("");
        Component $$3 = null;
        if ("null".equals($$2) || StringUtils.isEmpty((CharSequence)$$2)) {
            $$3 = CommonComponents.f_237098_;
        } else if ($$2.charAt(0) == '\"' && $$2.charAt($$2.length() - 1) == '\"' || $$2.charAt(0) == '{' && $$2.charAt($$2.length() - 1) == '}') {
            try {
                $$3 = GsonHelper.m_13798_(f_14861_, $$2, Component.class, true);
                if ($$3 == null) {
                    $$3 = CommonComponents.f_237098_;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            if ($$3 == null) {
                try {
                    $$3 = Component.Serializer.m_130701_($$2);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if ($$3 == null) {
                try {
                    $$3 = Component.Serializer.m_130714_($$2);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if ($$3 == null) {
                $$3 = Component.m_237113_($$2);
            }
        } else {
            $$3 = Component.m_237113_($$2);
        }
        return p_14871_.set(p_14872_, p_14871_.createString(Component.Serializer.m_130703_($$3)));
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_14867_) {
        return p_14867_.update(DSL.remainderFinder(), p_14869_ -> {
            p_14869_ = this.m_14870_((Dynamic<?>)p_14869_, "Text1");
            p_14869_ = this.m_14870_((Dynamic<?>)p_14869_, "Text2");
            p_14869_ = this.m_14870_((Dynamic<?>)p_14869_, "Text3");
            p_14869_ = this.m_14870_((Dynamic<?>)p_14869_, "Text4");
            return p_14869_;
        });
    }
}

