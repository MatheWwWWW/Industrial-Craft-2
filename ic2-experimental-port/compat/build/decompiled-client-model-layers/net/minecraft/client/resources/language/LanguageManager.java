/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.resources.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.metadata.language.LanguageMetadataSection;
import net.minecraft.locale.Language;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.slf4j.Logger;

public class LanguageManager
implements ResourceManagerReloadListener {
    private static final Logger f_118964_ = LogUtils.getLogger();
    public static final String f_174854_ = "en_us";
    private static final LanguageInfo f_118965_ = new LanguageInfo("en_us", "US", "English", false);
    private Map<String, LanguageInfo> f_118966_ = ImmutableMap.of((Object)"en_us", (Object)f_118965_);
    private String f_118967_;
    private LanguageInfo f_118968_ = f_118965_;

    public LanguageManager(String p_118971_) {
        this.f_118967_ = p_118971_;
    }

    private static Map<String, LanguageInfo> m_118981_(Stream<PackResources> p_118982_) {
        HashMap $$1 = Maps.newHashMap();
        p_118982_.forEach(p_118980_ -> {
            try {
                LanguageMetadataSection $$2 = p_118980_.m_5550_(LanguageMetadataSection.f_119096_);
                if ($$2 != null) {
                    for (LanguageInfo $$3 : $$2.m_119101_()) {
                        $$1.putIfAbsent($$3.getCode(), $$3);
                    }
                }
            }
            catch (IOException | RuntimeException $$4) {
                f_118964_.warn("Unable to parse language metadata section of resourcepack: {}", (Object)p_118980_.m_8017_(), (Object)$$4);
            }
        });
        return ImmutableMap.copyOf((Map)$$1);
    }

    @Override
    public void m_6213_(ResourceManager p_118973_) {
        this.f_118966_ = LanguageManager.m_118981_(p_118973_.m_7536_());
        LanguageInfo $$1 = this.f_118966_.getOrDefault(f_174854_, f_118965_);
        this.f_118968_ = this.f_118966_.getOrDefault(this.f_118967_, $$1);
        ArrayList $$2 = Lists.newArrayList((Object[])new LanguageInfo[]{$$1});
        if (this.f_118968_ != $$1) {
            $$2.add(this.f_118968_);
        }
        ClientLanguage $$3 = ClientLanguage.m_118916_(p_118973_, $$2);
        I18n.m_118941_($$3);
        Language.m_128114_($$3);
    }

    public void m_118974_(LanguageInfo p_118975_) {
        this.f_118967_ = p_118975_.getCode();
        this.f_118968_ = p_118975_;
    }

    public LanguageInfo m_118983_() {
        return this.f_118968_;
    }

    public SortedSet<LanguageInfo> m_118984_() {
        return Sets.newTreeSet(this.f_118966_.values());
    }

    public LanguageInfo m_118976_(String p_118977_) {
        return this.f_118966_.get(p_118977_);
    }
}

