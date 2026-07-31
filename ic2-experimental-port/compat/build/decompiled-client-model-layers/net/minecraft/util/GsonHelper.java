/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSyntaxException
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonWriter
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.Contract
 */
package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

public class GsonHelper {
    private static final Gson f_13765_ = new GsonBuilder().create();

    public static boolean m_13813_(JsonObject p_13814_, String p_13815_) {
        if (!GsonHelper.m_13894_(p_13814_, p_13815_)) {
            return false;
        }
        return p_13814_.getAsJsonPrimitive(p_13815_).isString();
    }

    public static boolean m_13803_(JsonElement p_13804_) {
        if (!p_13804_.isJsonPrimitive()) {
            return false;
        }
        return p_13804_.getAsJsonPrimitive().isString();
    }

    public static boolean m_144762_(JsonObject p_144763_, String p_144764_) {
        if (!GsonHelper.m_13894_(p_144763_, p_144764_)) {
            return false;
        }
        return p_144763_.getAsJsonPrimitive(p_144764_).isNumber();
    }

    public static boolean m_13872_(JsonElement p_13873_) {
        if (!p_13873_.isJsonPrimitive()) {
            return false;
        }
        return p_13873_.getAsJsonPrimitive().isNumber();
    }

    public static boolean m_13880_(JsonObject p_13881_, String p_13882_) {
        if (!GsonHelper.m_13894_(p_13881_, p_13882_)) {
            return false;
        }
        return p_13881_.getAsJsonPrimitive(p_13882_).isBoolean();
    }

    public static boolean m_144767_(JsonElement p_144768_) {
        if (!p_144768_.isJsonPrimitive()) {
            return false;
        }
        return p_144768_.getAsJsonPrimitive().isBoolean();
    }

    public static boolean m_13885_(JsonObject p_13886_, String p_13887_) {
        if (!GsonHelper.m_13900_(p_13886_, p_13887_)) {
            return false;
        }
        return p_13886_.get(p_13887_).isJsonArray();
    }

    public static boolean m_144772_(JsonObject p_144773_, String p_144774_) {
        if (!GsonHelper.m_13900_(p_144773_, p_144774_)) {
            return false;
        }
        return p_144773_.get(p_144774_).isJsonObject();
    }

    public static boolean m_13894_(JsonObject p_13895_, String p_13896_) {
        if (!GsonHelper.m_13900_(p_13895_, p_13896_)) {
            return false;
        }
        return p_13895_.get(p_13896_).isJsonPrimitive();
    }

    public static boolean m_13900_(JsonObject p_13901_, String p_13902_) {
        if (p_13901_ == null) {
            return false;
        }
        return p_13901_.get(p_13902_) != null;
    }

    public static String m_13805_(JsonElement p_13806_, String p_13807_) {
        if (p_13806_.isJsonPrimitive()) {
            return p_13806_.getAsString();
        }
        throw new JsonSyntaxException("Expected " + p_13807_ + " to be a string, was " + GsonHelper.m_13883_(p_13806_));
    }

    public static String m_13906_(JsonObject p_13907_, String p_13908_) {
        if (p_13907_.has(p_13908_)) {
            return GsonHelper.m_13805_(p_13907_.get(p_13908_), p_13908_);
        }
        throw new JsonSyntaxException("Missing " + p_13908_ + ", expected to find a string");
    }

    @Nullable
    @Contract(value="_,_,!null->!null;_,_,null->_")
    public static String m_13851_(JsonObject p_13852_, String p_13853_, @Nullable String p_13854_) {
        if (p_13852_.has(p_13853_)) {
            return GsonHelper.m_13805_(p_13852_.get(p_13853_), p_13853_);
        }
        return p_13854_;
    }

    public static Item m_13874_(JsonElement p_13875_, String p_13876_) {
        if (p_13875_.isJsonPrimitive()) {
            String $$2 = p_13875_.getAsString();
            return Registry.f_122827_.m_6612_(new ResourceLocation($$2)).orElseThrow(() -> new JsonSyntaxException("Expected " + p_13876_ + " to be an item, was unknown string '" + $$2 + "'"));
        }
        throw new JsonSyntaxException("Expected " + p_13876_ + " to be an item, was " + GsonHelper.m_13883_(p_13875_));
    }

    public static Item m_13909_(JsonObject p_13910_, String p_13911_) {
        if (p_13910_.has(p_13911_)) {
            return GsonHelper.m_13874_(p_13910_.get(p_13911_), p_13911_);
        }
        throw new JsonSyntaxException("Missing " + p_13911_ + ", expected to find an item");
    }

    @Nullable
    @Contract(value="_,_,!null->!null;_,_,null->_")
    public static Item m_144746_(JsonObject p_144747_, String p_144748_, @Nullable Item p_144749_) {
        if (p_144747_.has(p_144748_)) {
            return GsonHelper.m_13874_(p_144747_.get(p_144748_), p_144748_);
        }
        return p_144749_;
    }

    public static boolean m_13877_(JsonElement p_13878_, String p_13879_) {
        if (p_13878_.isJsonPrimitive()) {
            return p_13878_.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + p_13879_ + " to be a Boolean, was " + GsonHelper.m_13883_(p_13878_));
    }

    public static boolean m_13912_(JsonObject p_13913_, String p_13914_) {
        if (p_13913_.has(p_13914_)) {
            return GsonHelper.m_13877_(p_13913_.get(p_13914_), p_13914_);
        }
        throw new JsonSyntaxException("Missing " + p_13914_ + ", expected to find a Boolean");
    }

    public static boolean m_13855_(JsonObject p_13856_, String p_13857_, boolean p_13858_) {
        if (p_13856_.has(p_13857_)) {
            return GsonHelper.m_13877_(p_13856_.get(p_13857_), p_13857_);
        }
        return p_13858_;
    }

    public static double m_144769_(JsonElement p_144770_, String p_144771_) {
        if (p_144770_.isJsonPrimitive() && p_144770_.getAsJsonPrimitive().isNumber()) {
            return p_144770_.getAsDouble();
        }
        throw new JsonSyntaxException("Expected " + p_144771_ + " to be a Double, was " + GsonHelper.m_13883_(p_144770_));
    }

    public static double m_144784_(JsonObject p_144785_, String p_144786_) {
        if (p_144785_.has(p_144786_)) {
            return GsonHelper.m_144769_(p_144785_.get(p_144786_), p_144786_);
        }
        throw new JsonSyntaxException("Missing " + p_144786_ + ", expected to find a Double");
    }

    public static double m_144742_(JsonObject p_144743_, String p_144744_, double p_144745_) {
        if (p_144743_.has(p_144744_)) {
            return GsonHelper.m_144769_(p_144743_.get(p_144744_), p_144744_);
        }
        return p_144745_;
    }

    public static float m_13888_(JsonElement p_13889_, String p_13890_) {
        if (p_13889_.isJsonPrimitive() && p_13889_.getAsJsonPrimitive().isNumber()) {
            return p_13889_.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + p_13890_ + " to be a Float, was " + GsonHelper.m_13883_(p_13889_));
    }

    public static float m_13915_(JsonObject p_13916_, String p_13917_) {
        if (p_13916_.has(p_13917_)) {
            return GsonHelper.m_13888_(p_13916_.get(p_13917_), p_13917_);
        }
        throw new JsonSyntaxException("Missing " + p_13917_ + ", expected to find a Float");
    }

    public static float m_13820_(JsonObject p_13821_, String p_13822_, float p_13823_) {
        if (p_13821_.has(p_13822_)) {
            return GsonHelper.m_13888_(p_13821_.get(p_13822_), p_13822_);
        }
        return p_13823_;
    }

    public static long m_13891_(JsonElement p_13892_, String p_13893_) {
        if (p_13892_.isJsonPrimitive() && p_13892_.getAsJsonPrimitive().isNumber()) {
            return p_13892_.getAsLong();
        }
        throw new JsonSyntaxException("Expected " + p_13893_ + " to be a Long, was " + GsonHelper.m_13883_(p_13892_));
    }

    public static long m_13921_(JsonObject p_13922_, String p_13923_) {
        if (p_13922_.has(p_13923_)) {
            return GsonHelper.m_13891_(p_13922_.get(p_13923_), p_13923_);
        }
        throw new JsonSyntaxException("Missing " + p_13923_ + ", expected to find a Long");
    }

    public static long m_13828_(JsonObject p_13829_, String p_13830_, long p_13831_) {
        if (p_13829_.has(p_13830_)) {
            return GsonHelper.m_13891_(p_13829_.get(p_13830_), p_13830_);
        }
        return p_13831_;
    }

    public static int m_13897_(JsonElement p_13898_, String p_13899_) {
        if (p_13898_.isJsonPrimitive() && p_13898_.getAsJsonPrimitive().isNumber()) {
            return p_13898_.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + p_13899_ + " to be a Int, was " + GsonHelper.m_13883_(p_13898_));
    }

    public static int m_13927_(JsonObject p_13928_, String p_13929_) {
        if (p_13928_.has(p_13929_)) {
            return GsonHelper.m_13897_(p_13928_.get(p_13929_), p_13929_);
        }
        throw new JsonSyntaxException("Missing " + p_13929_ + ", expected to find a Int");
    }

    public static int m_13824_(JsonObject p_13825_, String p_13826_, int p_13827_) {
        if (p_13825_.has(p_13826_)) {
            return GsonHelper.m_13897_(p_13825_.get(p_13826_), p_13826_);
        }
        return p_13827_;
    }

    public static byte m_13903_(JsonElement p_13904_, String p_13905_) {
        if (p_13904_.isJsonPrimitive() && p_13904_.getAsJsonPrimitive().isNumber()) {
            return p_13904_.getAsByte();
        }
        throw new JsonSyntaxException("Expected " + p_13905_ + " to be a Byte, was " + GsonHelper.m_13883_(p_13904_));
    }

    public static byte m_144790_(JsonObject p_144791_, String p_144792_) {
        if (p_144791_.has(p_144792_)) {
            return GsonHelper.m_13903_(p_144791_.get(p_144792_), p_144792_);
        }
        throw new JsonSyntaxException("Missing " + p_144792_ + ", expected to find a Byte");
    }

    public static byte m_13816_(JsonObject p_13817_, String p_13818_, byte p_13819_) {
        if (p_13817_.has(p_13818_)) {
            return GsonHelper.m_13903_(p_13817_.get(p_13818_), p_13818_);
        }
        return p_13819_;
    }

    public static char m_144775_(JsonElement p_144776_, String p_144777_) {
        if (p_144776_.isJsonPrimitive() && p_144776_.getAsJsonPrimitive().isNumber()) {
            return p_144776_.getAsCharacter();
        }
        throw new JsonSyntaxException("Expected " + p_144777_ + " to be a Character, was " + GsonHelper.m_13883_(p_144776_));
    }

    public static char m_144793_(JsonObject p_144794_, String p_144795_) {
        if (p_144794_.has(p_144795_)) {
            return GsonHelper.m_144775_(p_144794_.get(p_144795_), p_144795_);
        }
        throw new JsonSyntaxException("Missing " + p_144795_ + ", expected to find a Character");
    }

    public static char m_144738_(JsonObject p_144739_, String p_144740_, char p_144741_) {
        if (p_144739_.has(p_144740_)) {
            return GsonHelper.m_144775_(p_144739_.get(p_144740_), p_144740_);
        }
        return p_144741_;
    }

    public static BigDecimal m_144778_(JsonElement p_144779_, String p_144780_) {
        if (p_144779_.isJsonPrimitive() && p_144779_.getAsJsonPrimitive().isNumber()) {
            return p_144779_.getAsBigDecimal();
        }
        throw new JsonSyntaxException("Expected " + p_144780_ + " to be a BigDecimal, was " + GsonHelper.m_13883_(p_144779_));
    }

    public static BigDecimal m_144796_(JsonObject p_144797_, String p_144798_) {
        if (p_144797_.has(p_144798_)) {
            return GsonHelper.m_144778_(p_144797_.get(p_144798_), p_144798_);
        }
        throw new JsonSyntaxException("Missing " + p_144798_ + ", expected to find a BigDecimal");
    }

    public static BigDecimal m_144750_(JsonObject p_144751_, String p_144752_, BigDecimal p_144753_) {
        if (p_144751_.has(p_144752_)) {
            return GsonHelper.m_144778_(p_144751_.get(p_144752_), p_144752_);
        }
        return p_144753_;
    }

    public static BigInteger m_144781_(JsonElement p_144782_, String p_144783_) {
        if (p_144782_.isJsonPrimitive() && p_144782_.getAsJsonPrimitive().isNumber()) {
            return p_144782_.getAsBigInteger();
        }
        throw new JsonSyntaxException("Expected " + p_144783_ + " to be a BigInteger, was " + GsonHelper.m_13883_(p_144782_));
    }

    public static BigInteger m_144799_(JsonObject p_144800_, String p_144801_) {
        if (p_144800_.has(p_144801_)) {
            return GsonHelper.m_144781_(p_144800_.get(p_144801_), p_144801_);
        }
        throw new JsonSyntaxException("Missing " + p_144801_ + ", expected to find a BigInteger");
    }

    public static BigInteger m_144754_(JsonObject p_144755_, String p_144756_, BigInteger p_144757_) {
        if (p_144755_.has(p_144756_)) {
            return GsonHelper.m_144781_(p_144755_.get(p_144756_), p_144756_);
        }
        return p_144757_;
    }

    public static short m_144787_(JsonElement p_144788_, String p_144789_) {
        if (p_144788_.isJsonPrimitive() && p_144788_.getAsJsonPrimitive().isNumber()) {
            return p_144788_.getAsShort();
        }
        throw new JsonSyntaxException("Expected " + p_144789_ + " to be a Short, was " + GsonHelper.m_13883_(p_144788_));
    }

    public static short m_144802_(JsonObject p_144803_, String p_144804_) {
        if (p_144803_.has(p_144804_)) {
            return GsonHelper.m_144787_(p_144803_.get(p_144804_), p_144804_);
        }
        throw new JsonSyntaxException("Missing " + p_144804_ + ", expected to find a Short");
    }

    public static short m_144758_(JsonObject p_144759_, String p_144760_, short p_144761_) {
        if (p_144759_.has(p_144760_)) {
            return GsonHelper.m_144787_(p_144759_.get(p_144760_), p_144760_);
        }
        return p_144761_;
    }

    public static JsonObject m_13918_(JsonElement p_13919_, String p_13920_) {
        if (p_13919_.isJsonObject()) {
            return p_13919_.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + p_13920_ + " to be a JsonObject, was " + GsonHelper.m_13883_(p_13919_));
    }

    public static JsonObject m_13930_(JsonObject p_13931_, String p_13932_) {
        if (p_13931_.has(p_13932_)) {
            return GsonHelper.m_13918_(p_13931_.get(p_13932_), p_13932_);
        }
        throw new JsonSyntaxException("Missing " + p_13932_ + ", expected to find a JsonObject");
    }

    @Nullable
    @Contract(value="_,_,!null->!null;_,_,null->_")
    public static JsonObject m_13841_(JsonObject p_13842_, String p_13843_, @Nullable JsonObject p_13844_) {
        if (p_13842_.has(p_13843_)) {
            return GsonHelper.m_13918_(p_13842_.get(p_13843_), p_13843_);
        }
        return p_13844_;
    }

    public static JsonArray m_13924_(JsonElement p_13925_, String p_13926_) {
        if (p_13925_.isJsonArray()) {
            return p_13925_.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + p_13926_ + " to be a JsonArray, was " + GsonHelper.m_13883_(p_13925_));
    }

    public static JsonArray m_13933_(JsonObject p_13934_, String p_13935_) {
        if (p_13934_.has(p_13935_)) {
            return GsonHelper.m_13924_(p_13934_.get(p_13935_), p_13935_);
        }
        throw new JsonSyntaxException("Missing " + p_13935_ + ", expected to find a JsonArray");
    }

    @Nullable
    @Contract(value="_,_,!null->!null;_,_,null->_")
    public static JsonArray m_13832_(JsonObject p_13833_, String p_13834_, @Nullable JsonArray p_13835_) {
        if (p_13833_.has(p_13834_)) {
            return GsonHelper.m_13924_(p_13833_.get(p_13834_), p_13834_);
        }
        return p_13835_;
    }

    public static <T> T m_13808_(@Nullable JsonElement p_13809_, String p_13810_, JsonDeserializationContext p_13811_, Class<? extends T> p_13812_) {
        if (p_13809_ != null) {
            return (T)p_13811_.deserialize(p_13809_, p_13812_);
        }
        throw new JsonSyntaxException("Missing " + p_13810_);
    }

    public static <T> T m_13836_(JsonObject p_13837_, String p_13838_, JsonDeserializationContext p_13839_, Class<? extends T> p_13840_) {
        if (p_13837_.has(p_13838_)) {
            return GsonHelper.m_13808_(p_13837_.get(p_13838_), p_13838_, p_13839_, p_13840_);
        }
        throw new JsonSyntaxException("Missing " + p_13838_);
    }

    @Nullable
    @Contract(value="_,_,!null,_,_->!null;_,_,null,_,_->_")
    public static <T> T m_13845_(JsonObject p_13846_, String p_13847_, @Nullable T p_13848_, JsonDeserializationContext p_13849_, Class<? extends T> p_13850_) {
        if (p_13846_.has(p_13847_)) {
            return GsonHelper.m_13808_(p_13846_.get(p_13847_), p_13847_, p_13849_, p_13850_);
        }
        return p_13848_;
    }

    public static String m_13883_(@Nullable JsonElement p_13884_) {
        String $$1 = StringUtils.abbreviateMiddle((String)String.valueOf(p_13884_), (String)"...", (int)10);
        if (p_13884_ == null) {
            return "null (missing)";
        }
        if (p_13884_.isJsonNull()) {
            return "null (json)";
        }
        if (p_13884_.isJsonArray()) {
            return "an array (" + $$1 + ")";
        }
        if (p_13884_.isJsonObject()) {
            return "an object (" + $$1 + ")";
        }
        if (p_13884_.isJsonPrimitive()) {
            JsonPrimitive $$2 = p_13884_.getAsJsonPrimitive();
            if ($$2.isNumber()) {
                return "a number (" + $$1 + ")";
            }
            if ($$2.isBoolean()) {
                return "a boolean (" + $$1 + ")";
            }
        }
        return $$1;
    }

    @Nullable
    public static <T> T m_13780_(Gson p_13781_, Reader p_13782_, Class<T> p_13783_, boolean p_13784_) {
        try {
            JsonReader $$4 = new JsonReader(p_13782_);
            $$4.setLenient(p_13784_);
            return (T)p_13781_.getAdapter(p_13783_).read($$4);
        }
        catch (IOException $$5) {
            throw new JsonParseException((Throwable)$$5);
        }
    }

    @Nullable
    public static <T> T m_13771_(Gson p_13772_, Reader p_13773_, TypeToken<T> p_13774_, boolean p_13775_) {
        try {
            JsonReader $$4 = new JsonReader(p_13773_);
            $$4.setLenient(p_13775_);
            return (T)p_13772_.getAdapter(p_13774_).read($$4);
        }
        catch (IOException $$5) {
            throw new JsonParseException((Throwable)$$5);
        }
    }

    @Nullable
    public static <T> T m_13789_(Gson p_13790_, String p_13791_, TypeToken<T> p_13792_, boolean p_13793_) {
        return GsonHelper.m_13771_(p_13790_, new StringReader(p_13791_), p_13792_, p_13793_);
    }

    @Nullable
    public static <T> T m_13798_(Gson p_13799_, String p_13800_, Class<T> p_13801_, boolean p_13802_) {
        return GsonHelper.m_13780_(p_13799_, new StringReader(p_13800_), p_13801_, p_13802_);
    }

    @Nullable
    public static <T> T m_13767_(Gson p_13768_, Reader p_13769_, TypeToken<T> p_13770_) {
        return GsonHelper.m_13771_(p_13768_, p_13769_, p_13770_, false);
    }

    @Nullable
    public static <T> T m_13785_(Gson p_13786_, String p_13787_, TypeToken<T> p_13788_) {
        return GsonHelper.m_13789_(p_13786_, p_13787_, p_13788_, false);
    }

    @Nullable
    public static <T> T m_13776_(Gson p_13777_, Reader p_13778_, Class<T> p_13779_) {
        return GsonHelper.m_13780_(p_13777_, p_13778_, p_13779_, false);
    }

    @Nullable
    public static <T> T m_13794_(Gson p_13795_, String p_13796_, Class<T> p_13797_) {
        return GsonHelper.m_13798_(p_13795_, p_13796_, p_13797_, false);
    }

    public static JsonObject m_13869_(String p_13870_, boolean p_13871_) {
        return GsonHelper.m_13861_(new StringReader(p_13870_), p_13871_);
    }

    public static JsonObject m_13861_(Reader p_13862_, boolean p_13863_) {
        return GsonHelper.m_13780_(f_13765_, p_13862_, JsonObject.class, p_13863_);
    }

    public static JsonObject m_13864_(String p_13865_) {
        return GsonHelper.m_13869_(p_13865_, false);
    }

    public static JsonObject m_13859_(Reader p_13860_) {
        return GsonHelper.m_13861_(p_13860_, false);
    }

    public static JsonArray m_216214_(String p_216215_) {
        return GsonHelper.m_144765_(new StringReader(p_216215_));
    }

    public static JsonArray m_144765_(Reader p_144766_) {
        return GsonHelper.m_13780_(f_13765_, p_144766_, JsonArray.class, false);
    }

    public static String m_216216_(JsonElement p_216217_) {
        StringWriter $$1 = new StringWriter();
        JsonWriter $$2 = new JsonWriter((Writer)$$1);
        try {
            GsonHelper.m_216207_($$2, p_216217_, Comparator.naturalOrder());
        }
        catch (IOException $$3) {
            throw new AssertionError((Object)$$3);
        }
        return $$1.toString();
    }

    public static void m_216207_(JsonWriter p_216208_, @Nullable JsonElement p_216209_, @Nullable Comparator<String> p_216210_) throws IOException {
        if (p_216209_ == null || p_216209_.isJsonNull()) {
            p_216208_.nullValue();
        } else if (p_216209_.isJsonPrimitive()) {
            JsonPrimitive $$3 = p_216209_.getAsJsonPrimitive();
            if ($$3.isNumber()) {
                p_216208_.value($$3.getAsNumber());
            } else if ($$3.isBoolean()) {
                p_216208_.value($$3.getAsBoolean());
            } else {
                p_216208_.value($$3.getAsString());
            }
        } else if (p_216209_.isJsonArray()) {
            p_216208_.beginArray();
            for (JsonElement $$4 : p_216209_.getAsJsonArray()) {
                GsonHelper.m_216207_(p_216208_, $$4, p_216210_);
            }
            p_216208_.endArray();
        } else if (p_216209_.isJsonObject()) {
            p_216208_.beginObject();
            for (Map.Entry<String, JsonElement> $$5 : GsonHelper.m_216211_(p_216209_.getAsJsonObject().entrySet(), p_216210_)) {
                p_216208_.name($$5.getKey());
                GsonHelper.m_216207_(p_216208_, $$5.getValue(), p_216210_);
            }
            p_216208_.endObject();
        } else {
            throw new IllegalArgumentException("Couldn't write " + p_216209_.getClass());
        }
    }

    private static Collection<Map.Entry<String, JsonElement>> m_216211_(Collection<Map.Entry<String, JsonElement>> p_216212_, @Nullable Comparator<String> p_216213_) {
        if (p_216213_ == null) {
            return p_216212_;
        }
        ArrayList<Map.Entry<String, JsonElement>> $$2 = new ArrayList<Map.Entry<String, JsonElement>>(p_216212_);
        $$2.sort(Map.Entry.comparingByKey(p_216213_));
        return $$2;
    }
}

