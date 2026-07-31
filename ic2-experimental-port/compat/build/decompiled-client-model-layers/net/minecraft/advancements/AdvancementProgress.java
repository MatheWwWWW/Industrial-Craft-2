/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public class AdvancementProgress
implements Comparable<AdvancementProgress> {
    final Map<String, CriterionProgress> f_8190_;
    private String[][] f_8191_ = new String[0][];

    private AdvancementProgress(Map<String, CriterionProgress> p_144358_) {
        this.f_8190_ = p_144358_;
    }

    public AdvancementProgress() {
        this.f_8190_ = Maps.newHashMap();
    }

    public void m_8198_(Map<String, Criterion> p_8199_, String[][] p_8200_) {
        Set<String> $$2 = p_8199_.keySet();
        this.f_8190_.entrySet().removeIf(p_8203_ -> !$$2.contains(p_8203_.getKey()));
        for (String $$3 : $$2) {
            if (this.f_8190_.containsKey($$3)) continue;
            this.f_8190_.put($$3, new CriterionProgress());
        }
        this.f_8191_ = p_8200_;
    }

    public boolean m_8193_() {
        if (this.f_8191_.length == 0) {
            return false;
        }
        for (String[] $$0 : this.f_8191_) {
            boolean $$1 = false;
            for (String $$2 : $$0) {
                CriterionProgress $$3 = this.m_8214_($$2);
                if ($$3 == null || !$$3.m_12911_()) continue;
                $$1 = true;
                break;
            }
            if ($$1) continue;
            return false;
        }
        return true;
    }

    public boolean m_8206_() {
        for (CriterionProgress $$0 : this.f_8190_.values()) {
            if (!$$0.m_12911_()) continue;
            return true;
        }
        return false;
    }

    public boolean m_8196_(String p_8197_) {
        CriterionProgress $$1 = this.f_8190_.get(p_8197_);
        if ($$1 != null && !$$1.m_12911_()) {
            $$1.m_12916_();
            return true;
        }
        return false;
    }

    public boolean m_8209_(String p_8210_) {
        CriterionProgress $$1 = this.f_8190_.get(p_8210_);
        if ($$1 != null && $$1.m_12911_()) {
            $$1.m_12919_();
            return true;
        }
        return false;
    }

    public String toString() {
        return "AdvancementProgress{criteria=" + this.f_8190_ + ", requirements=" + Arrays.deepToString((Object[])this.f_8191_) + "}";
    }

    public void m_8204_(FriendlyByteBuf p_8205_) {
        p_8205_.m_236831_(this.f_8190_, FriendlyByteBuf::m_130070_, (p_144360_, p_144361_) -> p_144361_.m_12914_((FriendlyByteBuf)((Object)p_144360_)));
    }

    public static AdvancementProgress m_8211_(FriendlyByteBuf p_8212_) {
        Map<String, CriterionProgress> $$1 = p_8212_.m_236847_(FriendlyByteBuf::m_130277_, CriterionProgress::m_12917_);
        return new AdvancementProgress($$1);
    }

    @Nullable
    public CriterionProgress m_8214_(String p_8215_) {
        return this.f_8190_.get(p_8215_);
    }

    public float m_8213_() {
        if (this.f_8190_.isEmpty()) {
            return 0.0f;
        }
        float $$0 = this.f_8191_.length;
        float $$1 = this.m_8222_();
        return $$1 / $$0;
    }

    @Nullable
    public String m_8218_() {
        if (this.f_8190_.isEmpty()) {
            return null;
        }
        int $$0 = this.f_8191_.length;
        if ($$0 <= 1) {
            return null;
        }
        int $$1 = this.m_8222_();
        return $$1 + "/" + $$0;
    }

    private int m_8222_() {
        int $$0 = 0;
        for (String[] $$1 : this.f_8191_) {
            boolean $$2 = false;
            for (String $$3 : $$1) {
                CriterionProgress $$4 = this.m_8214_($$3);
                if ($$4 == null || !$$4.m_12911_()) continue;
                $$2 = true;
                break;
            }
            if (!$$2) continue;
            ++$$0;
        }
        return $$0;
    }

    public Iterable<String> m_8219_() {
        ArrayList $$0 = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> $$1 : this.f_8190_.entrySet()) {
            if ($$1.getValue().m_12911_()) continue;
            $$0.add($$1.getKey());
        }
        return $$0;
    }

    public Iterable<String> m_8220_() {
        ArrayList $$0 = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> $$1 : this.f_8190_.entrySet()) {
            if (!$$1.getValue().m_12911_()) continue;
            $$0.add($$1.getKey());
        }
        return $$0;
    }

    @Nullable
    public Date m_8221_() {
        Date $$0 = null;
        for (CriterionProgress $$1 : this.f_8190_.values()) {
            if (!$$1.m_12911_() || $$0 != null && !$$1.m_12920_().before($$0)) continue;
            $$0 = $$1.m_12920_();
        }
        return $$0;
    }

    @Override
    public int compareTo(AdvancementProgress p_8195_) {
        Date $$1 = this.m_8221_();
        Date $$2 = p_8195_.m_8221_();
        if ($$1 == null && $$2 != null) {
            return 1;
        }
        if ($$1 != null && $$2 == null) {
            return -1;
        }
        if ($$1 == null && $$2 == null) {
            return 0;
        }
        return $$1.compareTo($$2);
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((AdvancementProgress)object);
    }

    public static class Serializer
    implements JsonDeserializer<AdvancementProgress>,
    JsonSerializer<AdvancementProgress> {
        public JsonElement serialize(AdvancementProgress p_8226_, Type p_8227_, JsonSerializationContext p_8228_) {
            JsonObject $$3 = new JsonObject();
            JsonObject $$4 = new JsonObject();
            for (Map.Entry<String, CriterionProgress> $$5 : p_8226_.f_8190_.entrySet()) {
                CriterionProgress $$6 = $$5.getValue();
                if (!$$6.m_12911_()) continue;
                $$4.add($$5.getKey(), $$6.m_12921_());
            }
            if (!$$4.entrySet().isEmpty()) {
                $$3.add("criteria", (JsonElement)$$4);
            }
            $$3.addProperty("done", Boolean.valueOf(p_8226_.m_8193_()));
            return $$3;
        }

        public AdvancementProgress deserialize(JsonElement p_8230_, Type p_8231_, JsonDeserializationContext p_8232_) throws JsonParseException {
            JsonObject $$3 = GsonHelper.m_13918_(p_8230_, "advancement");
            JsonObject $$4 = GsonHelper.m_13841_($$3, "criteria", new JsonObject());
            AdvancementProgress $$5 = new AdvancementProgress();
            for (Map.Entry $$6 : $$4.entrySet()) {
                String $$7 = (String)$$6.getKey();
                $$5.f_8190_.put($$7, CriterionProgress.m_12912_(GsonHelper.m_13805_((JsonElement)$$6.getValue(), $$7)));
            }
            return $$5;
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((AdvancementProgress)object, type, jsonSerializationContext);
        }
    }
}

