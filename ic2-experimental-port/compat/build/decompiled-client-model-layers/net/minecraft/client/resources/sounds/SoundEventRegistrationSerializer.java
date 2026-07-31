/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.client.resources.sounds;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import org.apache.commons.lang3.Validate;

public class SoundEventRegistrationSerializer
implements JsonDeserializer<SoundEventRegistration> {
    private static final FloatProvider f_235148_ = ConstantFloat.m_146458_(1.0f);

    public SoundEventRegistration deserialize(JsonElement p_119827_, Type p_119828_, JsonDeserializationContext p_119829_) throws JsonParseException {
        JsonObject $$3 = GsonHelper.m_13918_(p_119827_, "entry");
        boolean $$4 = GsonHelper.m_13855_($$3, "replace", false);
        String $$5 = GsonHelper.m_13851_($$3, "subtitle", null);
        List<Sound> $$6 = this.m_119830_($$3);
        return new SoundEventRegistration($$6, $$4, $$5);
    }

    private List<Sound> m_119830_(JsonObject p_119831_) {
        ArrayList $$1 = Lists.newArrayList();
        if (p_119831_.has("sounds")) {
            JsonArray $$2 = GsonHelper.m_13933_(p_119831_, "sounds");
            for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
                JsonElement $$4 = $$2.get($$3);
                if (GsonHelper.m_13803_($$4)) {
                    String $$5 = GsonHelper.m_13805_($$4, "sound");
                    $$1.add(new Sound($$5, f_235148_, f_235148_, 1, Sound.Type.FILE, false, false, 16));
                    continue;
                }
                $$1.add(this.m_119835_(GsonHelper.m_13918_($$4, "sound")));
            }
        }
        return $$1;
    }

    private Sound m_119835_(JsonObject p_119836_) {
        String $$1 = GsonHelper.m_13906_(p_119836_, "name");
        Sound.Type $$2 = this.m_119832_(p_119836_, Sound.Type.FILE);
        float $$3 = GsonHelper.m_13820_(p_119836_, "volume", 1.0f);
        Validate.isTrue(($$3 > 0.0f ? 1 : 0) != 0, (String)"Invalid volume", (Object[])new Object[0]);
        float $$4 = GsonHelper.m_13820_(p_119836_, "pitch", 1.0f);
        Validate.isTrue(($$4 > 0.0f ? 1 : 0) != 0, (String)"Invalid pitch", (Object[])new Object[0]);
        int $$5 = GsonHelper.m_13824_(p_119836_, "weight", 1);
        Validate.isTrue(($$5 > 0 ? 1 : 0) != 0, (String)"Invalid weight", (Object[])new Object[0]);
        boolean $$6 = GsonHelper.m_13855_(p_119836_, "preload", false);
        boolean $$7 = GsonHelper.m_13855_(p_119836_, "stream", false);
        int $$8 = GsonHelper.m_13824_(p_119836_, "attenuation_distance", 16);
        return new Sound($$1, ConstantFloat.m_146458_($$3), ConstantFloat.m_146458_($$4), $$5, $$2, $$7, $$6, $$8);
    }

    private Sound.Type m_119832_(JsonObject p_119833_, Sound.Type p_119834_) {
        Sound.Type $$2 = p_119834_;
        if (p_119833_.has("type")) {
            $$2 = Sound.Type.m_119810_(GsonHelper.m_13906_(p_119833_, "type"));
            Validate.notNull((Object)((Object)$$2), (String)"Invalid type", (Object[])new Object[0]);
        }
        return $$2;
    }

    public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
}

