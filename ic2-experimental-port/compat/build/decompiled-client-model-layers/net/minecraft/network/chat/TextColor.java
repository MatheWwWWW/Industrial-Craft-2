/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;

public final class TextColor {
    private static final String f_178538_ = "#";
    public static final Codec<TextColor> f_237295_ = Codec.STRING.comapFlatMap(p_237299_ -> {
        TextColor $$1 = TextColor.m_131268_(p_237299_);
        return $$1 != null ? DataResult.success((Object)$$1) : DataResult.error((String)"String is not a valid color name or hex color code");
    }, TextColor::m_131274_);
    private static final Map<ChatFormatting, TextColor> f_131255_ = (Map)Stream.of(ChatFormatting.values()).filter(ChatFormatting::m_126664_).collect(ImmutableMap.toImmutableMap(Function.identity(), p_237301_ -> new TextColor(p_237301_.m_126665_(), p_237301_.m_126666_())));
    private static final Map<String, TextColor> f_131256_ = (Map)f_131255_.values().stream().collect(ImmutableMap.toImmutableMap(p_237297_ -> p_237297_.f_131258_, Function.identity()));
    private final int f_131257_;
    @Nullable
    private final String f_131258_;

    private TextColor(int p_131263_, String p_131264_) {
        this.f_131257_ = p_131263_;
        this.f_131258_ = p_131264_;
    }

    private TextColor(int p_131261_) {
        this.f_131257_ = p_131261_;
        this.f_131258_ = null;
    }

    public int m_131265_() {
        return this.f_131257_;
    }

    public String m_131274_() {
        if (this.f_131258_ != null) {
            return this.f_131258_;
        }
        return this.m_131277_();
    }

    private String m_131277_() {
        return String.format(Locale.ROOT, "#%06X", this.f_131257_);
    }

    public boolean equals(Object p_131279_) {
        if (this == p_131279_) {
            return true;
        }
        if (p_131279_ == null || this.getClass() != p_131279_.getClass()) {
            return false;
        }
        TextColor $$1 = (TextColor)p_131279_;
        return this.f_131257_ == $$1.f_131257_;
    }

    public int hashCode() {
        return Objects.hash(this.f_131257_, this.f_131258_);
    }

    public String toString() {
        return this.f_131258_ != null ? this.f_131258_ : this.m_131277_();
    }

    @Nullable
    public static TextColor m_131270_(ChatFormatting p_131271_) {
        return f_131255_.get(p_131271_);
    }

    public static TextColor m_131266_(int p_131267_) {
        return new TextColor(p_131267_);
    }

    @Nullable
    public static TextColor m_131268_(String p_131269_) {
        if (p_131269_.startsWith(f_178538_)) {
            try {
                int $$1 = Integer.parseInt(p_131269_.substring(1), 16);
                return TextColor.m_131266_($$1);
            }
            catch (NumberFormatException $$2) {
                return null;
            }
        }
        return f_131256_.get(p_131269_);
    }
}

