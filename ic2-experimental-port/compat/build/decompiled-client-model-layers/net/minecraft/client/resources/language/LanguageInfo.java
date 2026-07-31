/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.bridge.game.Language
 */
package net.minecraft.client.resources.language;

import com.mojang.bridge.game.Language;
import java.util.Locale;

public class LanguageInfo
implements Language,
Comparable<LanguageInfo> {
    private final String f_118943_;
    private final String f_118944_;
    private final String f_118945_;
    private final boolean f_118946_;

    public LanguageInfo(String p_118948_, String p_118949_, String p_118950_, boolean p_118951_) {
        this.f_118943_ = p_118948_;
        this.f_118944_ = p_118949_;
        this.f_118945_ = p_118950_;
        this.f_118946_ = p_118951_;
    }

    public String getCode() {
        return this.f_118943_;
    }

    public String getName() {
        return this.f_118945_;
    }

    public String getRegion() {
        return this.f_118944_;
    }

    public boolean m_118952_() {
        return this.f_118946_;
    }

    public String toString() {
        return String.format(Locale.ROOT, "%s (%s)", this.f_118945_, this.f_118944_);
    }

    public boolean equals(Object p_118958_) {
        if (this == p_118958_) {
            return true;
        }
        if (!(p_118958_ instanceof LanguageInfo)) {
            return false;
        }
        return this.f_118943_.equals(((LanguageInfo)p_118958_).f_118943_);
    }

    public int hashCode() {
        return this.f_118943_.hashCode();
    }

    @Override
    public int compareTo(LanguageInfo p_118954_) {
        return this.f_118943_.compareTo(p_118954_.f_118943_);
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((LanguageInfo)object);
    }
}

