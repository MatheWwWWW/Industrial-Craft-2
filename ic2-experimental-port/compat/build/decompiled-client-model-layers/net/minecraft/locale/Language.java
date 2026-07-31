/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.locale;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringDecomposer;
import org.slf4j.Logger;

public abstract class Language {
    private static final Logger f_128101_ = LogUtils.getLogger();
    private static final Gson f_128102_ = new Gson();
    private static final Pattern f_128103_ = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
    public static final String f_177832_ = "en_us";
    private static volatile Language f_128104_ = Language.m_128118_();

    private static Language m_128118_() {
        ImmutableMap.Builder $$0 = ImmutableMap.builder();
        BiConsumer<String, String> $$1 = (arg_0, arg_1) -> ((ImmutableMap.Builder)$$0).put(arg_0, arg_1);
        String $$2 = "/assets/minecraft/lang/en_us.json";
        try (InputStream $$3 = Language.class.getResourceAsStream("/assets/minecraft/lang/en_us.json");){
            Language.m_128108_($$3, $$1);
        }
        catch (JsonParseException | IOException $$4) {
            f_128101_.error("Couldn't read strings from {}", (Object)"/assets/minecraft/lang/en_us.json", (Object)$$4);
        }
        ImmutableMap $$5 = $$0.build();
        return new Language((Map)$$5){
            final /* synthetic */ Map f_128119_;
            {
                this.f_128119_ = map;
            }

            @Override
            public String m_6834_(String p_128127_) {
                return this.f_128119_.getOrDefault(p_128127_, p_128127_);
            }

            @Override
            public boolean m_6722_(String p_128135_) {
                return this.f_128119_.containsKey(p_128135_);
            }

            @Override
            public boolean m_6627_() {
                return false;
            }

            @Override
            public FormattedCharSequence m_5536_(FormattedText p_128129_) {
                return p_128132_ -> p_128129_.m_7451_((p_177835_, p_177836_) -> StringDecomposer.m_14346_(p_177836_, p_177835_, p_128132_) ? Optional.empty() : FormattedText.f_130759_, Style.f_131099_).isPresent();
            }
        };
    }

    public static void m_128108_(InputStream p_128109_, BiConsumer<String, String> p_128110_) {
        JsonObject $$2 = (JsonObject)f_128102_.fromJson((Reader)new InputStreamReader(p_128109_, StandardCharsets.UTF_8), JsonObject.class);
        for (Map.Entry $$3 : $$2.entrySet()) {
            String $$4 = f_128103_.matcher(GsonHelper.m_13805_((JsonElement)$$3.getValue(), (String)$$3.getKey())).replaceAll("%$1s");
            p_128110_.accept((String)$$3.getKey(), $$4);
        }
    }

    public static Language m_128107_() {
        return f_128104_;
    }

    public static void m_128114_(Language p_128115_) {
        f_128104_ = p_128115_;
    }

    public abstract String m_6834_(String var1);

    public abstract boolean m_6722_(String var1);

    public abstract boolean m_6627_();

    public abstract FormattedCharSequence m_5536_(FormattedText var1);

    public List<FormattedCharSequence> m_128112_(List<FormattedText> p_128113_) {
        return (List)p_128113_.stream().map(this::m_5536_).collect(ImmutableList.toImmutableList());
    }
}

