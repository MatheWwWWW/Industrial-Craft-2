/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.resources;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;

public class ResourceLocation
implements Comparable<ResourceLocation> {
    public static final Codec<ResourceLocation> f_135803_ = Codec.STRING.comapFlatMap(ResourceLocation::m_135837_, ResourceLocation::toString).stable();
    private static final SimpleCommandExceptionType f_135806_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.id.invalid"));
    public static final char f_179907_ = ':';
    public static final String f_179908_ = "minecraft";
    public static final String f_179909_ = "realms";
    protected final String f_135804_;
    protected final String f_135805_;

    protected ResourceLocation(String[] p_135814_) {
        this.f_135804_ = StringUtils.isEmpty((CharSequence)p_135814_[0]) ? f_179908_ : p_135814_[0];
        this.f_135805_ = p_135814_[1];
        if (!ResourceLocation.m_135843_(this.f_135804_)) {
            throw new ResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + this.f_135804_ + ":" + this.f_135805_);
        }
        if (!ResourceLocation.m_135841_(this.f_135805_)) {
            throw new ResourceLocationException("Non [a-z0-9/._-] character in path of location: " + this.f_135804_ + ":" + this.f_135805_);
        }
    }

    public ResourceLocation(String p_135809_) {
        this(ResourceLocation.m_135832_(p_135809_, ':'));
    }

    public ResourceLocation(String p_135811_, String p_135812_) {
        this(new String[]{p_135811_, p_135812_});
    }

    public static ResourceLocation m_135822_(String p_135823_, char p_135824_) {
        return new ResourceLocation(ResourceLocation.m_135832_(p_135823_, p_135824_));
    }

    @Nullable
    public static ResourceLocation m_135820_(String p_135821_) {
        try {
            return new ResourceLocation(p_135821_);
        }
        catch (ResourceLocationException $$1) {
            return null;
        }
    }

    @Nullable
    public static ResourceLocation m_214293_(String p_214294_, String p_214295_) {
        try {
            return new ResourceLocation(p_214294_, p_214295_);
        }
        catch (ResourceLocationException $$2) {
            return null;
        }
    }

    protected static String[] m_135832_(String p_135833_, char p_135834_) {
        String[] $$2 = new String[]{f_179908_, p_135833_};
        int $$3 = p_135833_.indexOf(p_135834_);
        if ($$3 >= 0) {
            $$2[1] = p_135833_.substring($$3 + 1, p_135833_.length());
            if ($$3 >= 1) {
                $$2[0] = p_135833_.substring(0, $$3);
            }
        }
        return $$2;
    }

    public static DataResult<ResourceLocation> m_135837_(String p_135838_) {
        try {
            return DataResult.success((Object)new ResourceLocation(p_135838_));
        }
        catch (ResourceLocationException $$1) {
            return DataResult.error((String)("Not a valid resource location: " + p_135838_ + " " + $$1.getMessage()));
        }
    }

    public String m_135815_() {
        return this.f_135805_;
    }

    public String m_135827_() {
        return this.f_135804_;
    }

    public String toString() {
        return this.f_135804_ + ":" + this.f_135805_;
    }

    public boolean equals(Object p_135846_) {
        if (this == p_135846_) {
            return true;
        }
        if (p_135846_ instanceof ResourceLocation) {
            ResourceLocation $$1 = (ResourceLocation)p_135846_;
            return this.f_135804_.equals($$1.f_135804_) && this.f_135805_.equals($$1.f_135805_);
        }
        return false;
    }

    public int hashCode() {
        return 31 * this.f_135804_.hashCode() + this.f_135805_.hashCode();
    }

    @Override
    public int compareTo(ResourceLocation p_135826_) {
        int $$1 = this.f_135805_.compareTo(p_135826_.f_135805_);
        if ($$1 == 0) {
            $$1 = this.f_135804_.compareTo(p_135826_.f_135804_);
        }
        return $$1;
    }

    public String m_179910_() {
        return this.toString().replace('/', '_').replace(':', '_');
    }

    public String m_214298_() {
        return this.f_135804_ + "." + this.f_135805_;
    }

    public String m_214299_() {
        return this.f_135804_.equals(f_179908_) ? this.f_135805_ : this.m_214298_();
    }

    public String m_214296_(String p_214297_) {
        return p_214297_ + "." + this.m_214298_();
    }

    public static ResourceLocation m_135818_(StringReader p_135819_) throws CommandSyntaxException {
        int $$1 = p_135819_.getCursor();
        while (p_135819_.canRead() && ResourceLocation.m_135816_(p_135819_.peek())) {
            p_135819_.skip();
        }
        String $$2 = p_135819_.getString().substring($$1, p_135819_.getCursor());
        try {
            return new ResourceLocation($$2);
        }
        catch (ResourceLocationException $$3) {
            p_135819_.setCursor($$1);
            throw f_135806_.createWithContext((ImmutableStringReader)p_135819_);
        }
    }

    public static boolean m_135816_(char p_135817_) {
        return p_135817_ >= '0' && p_135817_ <= '9' || p_135817_ >= 'a' && p_135817_ <= 'z' || p_135817_ == '_' || p_135817_ == ':' || p_135817_ == '/' || p_135817_ == '.' || p_135817_ == '-';
    }

    private static boolean m_135841_(String p_135842_) {
        for (int $$1 = 0; $$1 < p_135842_.length(); ++$$1) {
            if (ResourceLocation.m_135828_(p_135842_.charAt($$1))) continue;
            return false;
        }
        return true;
    }

    private static boolean m_135843_(String p_135844_) {
        for (int $$1 = 0; $$1 < p_135844_.length(); ++$$1) {
            if (ResourceLocation.m_135835_(p_135844_.charAt($$1))) continue;
            return false;
        }
        return true;
    }

    public static boolean m_135828_(char p_135829_) {
        return p_135829_ == '_' || p_135829_ == '-' || p_135829_ >= 'a' && p_135829_ <= 'z' || p_135829_ >= '0' && p_135829_ <= '9' || p_135829_ == '/' || p_135829_ == '.';
    }

    private static boolean m_135835_(char p_135836_) {
        return p_135836_ == '_' || p_135836_ == '-' || p_135836_ >= 'a' && p_135836_ <= 'z' || p_135836_ >= '0' && p_135836_ <= '9' || p_135836_ == '.';
    }

    public static boolean m_135830_(String p_135831_) {
        String[] $$1 = ResourceLocation.m_135832_(p_135831_, ':');
        return ResourceLocation.m_135843_(StringUtils.isEmpty((CharSequence)$$1[0]) ? f_179908_ : $$1[0]) && ResourceLocation.m_135841_($$1[1]);
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((ResourceLocation)object);
    }

    public static class Serializer
    implements JsonDeserializer<ResourceLocation>,
    JsonSerializer<ResourceLocation> {
        public ResourceLocation deserialize(JsonElement p_135851_, Type p_135852_, JsonDeserializationContext p_135853_) throws JsonParseException {
            return new ResourceLocation(GsonHelper.m_13805_(p_135851_, "location"));
        }

        public JsonElement serialize(ResourceLocation p_135855_, Type p_135856_, JsonSerializationContext p_135857_) {
            return new JsonPrimitive(p_135855_.toString());
        }

        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ResourceLocation)object, type, jsonSerializationContext);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

