/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.resources.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.resources.language.FormattedBidiReorder;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FormattedCharSequence;
import org.slf4j.Logger;

public class ClientLanguage
extends Language {
    private static final Logger f_118909_ = LogUtils.getLogger();
    private final Map<String, String> f_118910_;
    private final boolean f_118911_;

    private ClientLanguage(Map<String, String> p_118914_, boolean p_118915_) {
        this.f_118910_ = p_118914_;
        this.f_118911_ = p_118915_;
    }

    public static ClientLanguage m_118916_(ResourceManager p_118917_, List<LanguageInfo> p_118918_) {
        HashMap $$2 = Maps.newHashMap();
        boolean $$3 = false;
        for (LanguageInfo $$4 : p_118918_) {
            $$3 |= $$4.m_118952_();
            String $$5 = $$4.getCode();
            String $$6 = String.format(Locale.ROOT, "lang/%s.json", $$5);
            for (String $$7 : p_118917_.m_7187_()) {
                try {
                    ResourceLocation $$8 = new ResourceLocation($$7, $$6);
                    ClientLanguage.m_235035_($$5, p_118917_.m_213829_($$8), $$2);
                }
                catch (Exception $$9) {
                    f_118909_.warn("Skipped language file: {}:{} ({})", new Object[]{$$7, $$6, $$9.toString()});
                }
            }
        }
        return new ClientLanguage((Map<String, String>)ImmutableMap.copyOf((Map)$$2), $$3);
    }

    private static void m_235035_(String p_235036_, List<Resource> p_235037_, Map<String, String> p_235038_) {
        for (Resource $$3 : p_235037_) {
            try {
                InputStream $$4 = $$3.m_215507_();
                try {
                    Language.m_128108_($$4, p_235038_::put);
                }
                finally {
                    if ($$4 == null) continue;
                    $$4.close();
                }
            }
            catch (IOException $$5) {
                f_118909_.warn("Failed to load translations for {} from pack {}", new Object[]{p_235036_, $$3.m_215506_(), $$5});
            }
        }
    }

    @Override
    public String m_6834_(String p_118920_) {
        return this.f_118910_.getOrDefault(p_118920_, p_118920_);
    }

    @Override
    public boolean m_6722_(String p_118928_) {
        return this.f_118910_.containsKey(p_118928_);
    }

    @Override
    public boolean m_6627_() {
        return this.f_118911_;
    }

    @Override
    public FormattedCharSequence m_5536_(FormattedText p_118925_) {
        return FormattedBidiReorder.m_118931_(p_118925_, this.f_118911_);
    }
}

