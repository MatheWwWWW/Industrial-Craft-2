/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.StringRepresentable;

public final class ChatFormatting
extends Enum<ChatFormatting>
implements StringRepresentable {
    public static final /* enum */ ChatFormatting BLACK = new ChatFormatting("BLACK", '0', 0, 0);
    public static final /* enum */ ChatFormatting DARK_BLUE = new ChatFormatting("DARK_BLUE", '1', 1, 170);
    public static final /* enum */ ChatFormatting DARK_GREEN = new ChatFormatting("DARK_GREEN", '2', 2, 43520);
    public static final /* enum */ ChatFormatting DARK_AQUA = new ChatFormatting("DARK_AQUA", '3', 3, 43690);
    public static final /* enum */ ChatFormatting DARK_RED = new ChatFormatting("DARK_RED", '4', 4, 0xAA0000);
    public static final /* enum */ ChatFormatting DARK_PURPLE = new ChatFormatting("DARK_PURPLE", '5', 5, 0xAA00AA);
    public static final /* enum */ ChatFormatting GOLD = new ChatFormatting("GOLD", '6', 6, 0xFFAA00);
    public static final /* enum */ ChatFormatting GRAY = new ChatFormatting("GRAY", '7', 7, 0xAAAAAA);
    public static final /* enum */ ChatFormatting DARK_GRAY = new ChatFormatting("DARK_GRAY", '8', 8, 0x555555);
    public static final /* enum */ ChatFormatting BLUE = new ChatFormatting("BLUE", '9', 9, 0x5555FF);
    public static final /* enum */ ChatFormatting GREEN = new ChatFormatting("GREEN", 'a', 10, 0x55FF55);
    public static final /* enum */ ChatFormatting AQUA = new ChatFormatting("AQUA", 'b', 11, 0x55FFFF);
    public static final /* enum */ ChatFormatting RED = new ChatFormatting("RED", 'c', 12, 0xFF5555);
    public static final /* enum */ ChatFormatting LIGHT_PURPLE = new ChatFormatting("LIGHT_PURPLE", 'd', 13, 0xFF55FF);
    public static final /* enum */ ChatFormatting YELLOW = new ChatFormatting("YELLOW", 'e', 14, 0xFFFF55);
    public static final /* enum */ ChatFormatting WHITE = new ChatFormatting("WHITE", 'f', 15, 0xFFFFFF);
    public static final /* enum */ ChatFormatting OBFUSCATED = new ChatFormatting("OBFUSCATED", 'k', true);
    public static final /* enum */ ChatFormatting BOLD = new ChatFormatting("BOLD", 'l', true);
    public static final /* enum */ ChatFormatting STRIKETHROUGH = new ChatFormatting("STRIKETHROUGH", 'm', true);
    public static final /* enum */ ChatFormatting UNDERLINE = new ChatFormatting("UNDERLINE", 'n', true);
    public static final /* enum */ ChatFormatting ITALIC = new ChatFormatting("ITALIC", 'o', true);
    public static final /* enum */ ChatFormatting RESET = new ChatFormatting("RESET", 'r', -1, null);
    public static final Codec<ChatFormatting> f_236796_;
    public static final char f_178509_ = '\u00a7';
    private static final Map<String, ChatFormatting> f_126619_;
    private static final Pattern f_126620_;
    private final String f_126621_;
    private final char f_126622_;
    private final boolean f_126592_;
    private final String f_126593_;
    private final int f_126594_;
    @Nullable
    private final Integer f_126595_;
    private static final /* synthetic */ ChatFormatting[] $VALUES;

    public static ChatFormatting[] values() {
        return (ChatFormatting[])$VALUES.clone();
    }

    public static ChatFormatting valueOf(String p_126669_) {
        return Enum.valueOf(ChatFormatting.class, p_126669_);
    }

    private static String m_126662_(String p_126663_) {
        return p_126663_.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    private ChatFormatting(String p_126627_, char p_126628_, int p_126629_, Integer p_126630_) {
        this(p_126627_, p_126628_, false, p_126629_, p_126630_);
    }

    private ChatFormatting(String p_126634_, char p_126635_, boolean p_126636_) {
        this(p_126634_, p_126635_, p_126636_, -1, null);
    }

    private ChatFormatting(@Nullable String p_126640_, char p_126641_, boolean p_126642_, int p_126643_, Integer p_126644_) {
        this.f_126621_ = p_126640_;
        this.f_126622_ = p_126641_;
        this.f_126592_ = p_126642_;
        this.f_126594_ = p_126643_;
        this.f_126595_ = p_126644_;
        this.f_126593_ = "\u00a7" + p_126641_;
    }

    public char m_178510_() {
        return this.f_126622_;
    }

    public int m_126656_() {
        return this.f_126594_;
    }

    public boolean m_126661_() {
        return this.f_126592_;
    }

    public boolean m_126664_() {
        return !this.f_126592_ && this != RESET;
    }

    @Nullable
    public Integer m_126665_() {
        return this.f_126595_;
    }

    public String m_126666_() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.f_126593_;
    }

    @Nullable
    public static String m_126649_(@Nullable String p_126650_) {
        return p_126650_ == null ? null : f_126620_.matcher(p_126650_).replaceAll("");
    }

    @Nullable
    public static ChatFormatting m_126657_(@Nullable String p_126658_) {
        if (p_126658_ == null) {
            return null;
        }
        return f_126619_.get(ChatFormatting.m_126662_(p_126658_));
    }

    @Nullable
    public static ChatFormatting m_126647_(int p_126648_) {
        if (p_126648_ < 0) {
            return RESET;
        }
        for (ChatFormatting $$1 : ChatFormatting.values()) {
            if ($$1.m_126656_() != p_126648_) continue;
            return $$1;
        }
        return null;
    }

    @Nullable
    public static ChatFormatting m_126645_(char p_126646_) {
        char $$1 = Character.toString(p_126646_).toLowerCase(Locale.ROOT).charAt(0);
        for (ChatFormatting $$2 : ChatFormatting.values()) {
            if ($$2.f_126622_ != $$1) continue;
            return $$2;
        }
        return null;
    }

    public static Collection<String> m_126653_(boolean p_126654_, boolean p_126655_) {
        ArrayList $$2 = Lists.newArrayList();
        for (ChatFormatting $$3 : ChatFormatting.values()) {
            if ($$3.m_126664_() && !p_126654_ || $$3.m_126661_() && !p_126655_) continue;
            $$2.add($$3.m_126666_());
        }
        return $$2;
    }

    @Override
    public String m_7912_() {
        return this.m_126666_();
    }

    private static /* synthetic */ ChatFormatting[] m_178511_() {
        return new ChatFormatting[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET};
    }

    static {
        $VALUES = ChatFormatting.m_178511_();
        f_236796_ = StringRepresentable.m_216439_(ChatFormatting::values);
        f_126619_ = Arrays.stream(ChatFormatting.values()).collect(Collectors.toMap(p_126660_ -> ChatFormatting.m_126662_(p_126660_.f_126621_), p_126652_ -> p_126652_));
        f_126620_ = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    }
}

