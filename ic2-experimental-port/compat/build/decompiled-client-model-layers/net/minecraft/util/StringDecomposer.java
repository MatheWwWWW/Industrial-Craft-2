/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Unit;

public class StringDecomposer {
    private static final char f_144984_ = '\ufffd';
    private static final Optional<Object> f_14298_ = Optional.of(Unit.INSTANCE);

    private static boolean m_14332_(Style p_14333_, FormattedCharSink p_14334_, int p_14335_, char p_14336_) {
        if (Character.isSurrogate(p_14336_)) {
            return p_14334_.m_6411_(p_14335_, p_14333_, 65533);
        }
        return p_14334_.m_6411_(p_14335_, p_14333_, p_14336_);
    }

    public static boolean m_14317_(String p_14318_, Style p_14319_, FormattedCharSink p_14320_) {
        int $$3 = p_14318_.length();
        for (int $$4 = 0; $$4 < $$3; ++$$4) {
            char $$5 = p_14318_.charAt($$4);
            if (Character.isHighSurrogate($$5)) {
                if ($$4 + 1 >= $$3) {
                    if (p_14320_.m_6411_($$4, p_14319_, 65533)) break;
                    return false;
                }
                char $$6 = p_14318_.charAt($$4 + 1);
                if (Character.isLowSurrogate($$6)) {
                    if (!p_14320_.m_6411_($$4, p_14319_, Character.toCodePoint($$5, $$6))) {
                        return false;
                    }
                    ++$$4;
                    continue;
                }
                if (p_14320_.m_6411_($$4, p_14319_, 65533)) continue;
                return false;
            }
            if (StringDecomposer.m_14332_(p_14319_, p_14320_, $$4, $$5)) continue;
            return false;
        }
        return true;
    }

    public static boolean m_14337_(String p_14338_, Style p_14339_, FormattedCharSink p_14340_) {
        int $$3 = p_14338_.length();
        for (int $$4 = $$3 - 1; $$4 >= 0; --$$4) {
            char $$5 = p_14338_.charAt($$4);
            if (Character.isLowSurrogate($$5)) {
                if ($$4 - 1 < 0) {
                    if (p_14340_.m_6411_(0, p_14339_, 65533)) break;
                    return false;
                }
                char $$6 = p_14338_.charAt($$4 - 1);
                if (!(Character.isHighSurrogate($$6) ? !p_14340_.m_6411_(--$$4, p_14339_, Character.toCodePoint($$6, $$5)) : !p_14340_.m_6411_($$4, p_14339_, 65533))) continue;
                return false;
            }
            if (StringDecomposer.m_14332_(p_14339_, p_14340_, $$4, $$5)) continue;
            return false;
        }
        return true;
    }

    public static boolean m_14346_(String p_14347_, Style p_14348_, FormattedCharSink p_14349_) {
        return StringDecomposer.m_14306_(p_14347_, 0, p_14348_, p_14349_);
    }

    public static boolean m_14306_(String p_14307_, int p_14308_, Style p_14309_, FormattedCharSink p_14310_) {
        return StringDecomposer.m_14311_(p_14307_, p_14308_, p_14309_, p_14309_, p_14310_);
    }

    public static boolean m_14311_(String p_14312_, int p_14313_, Style p_14314_, Style p_14315_, FormattedCharSink p_14316_) {
        int $$5 = p_14312_.length();
        Style $$6 = p_14314_;
        for (int $$7 = p_14313_; $$7 < $$5; ++$$7) {
            char $$8 = p_14312_.charAt($$7);
            if ($$8 == '\u00a7') {
                if ($$7 + 1 >= $$5) break;
                char $$9 = p_14312_.charAt($$7 + 1);
                ChatFormatting $$10 = ChatFormatting.m_126645_($$9);
                if ($$10 != null) {
                    $$6 = $$10 == ChatFormatting.RESET ? p_14315_ : $$6.m_131164_($$10);
                }
                ++$$7;
                continue;
            }
            if (Character.isHighSurrogate($$8)) {
                if ($$7 + 1 >= $$5) {
                    if (p_14316_.m_6411_($$7, $$6, 65533)) break;
                    return false;
                }
                char $$11 = p_14312_.charAt($$7 + 1);
                if (Character.isLowSurrogate($$11)) {
                    if (!p_14316_.m_6411_($$7, $$6, Character.toCodePoint($$8, $$11))) {
                        return false;
                    }
                    ++$$7;
                    continue;
                }
                if (p_14316_.m_6411_($$7, $$6, 65533)) continue;
                return false;
            }
            if (StringDecomposer.m_14332_($$6, p_14316_, $$7, $$8)) continue;
            return false;
        }
        return true;
    }

    public static boolean m_14328_(FormattedText p_14329_, Style p_14330_, FormattedCharSink p_14331_) {
        return !p_14329_.m_7451_((p_14302_, p_14303_) -> StringDecomposer.m_14306_(p_14303_, 0, p_14302_, p_14331_) ? Optional.empty() : f_14298_, p_14330_).isPresent();
    }

    public static String m_14304_(String p_14305_) {
        StringBuilder $$1 = new StringBuilder();
        StringDecomposer.m_14317_(p_14305_, Style.f_131099_, (p_14343_, p_14344_, p_14345_) -> {
            $$1.appendCodePoint(p_14345_);
            return true;
        });
        return $$1.toString();
    }

    public static String m_14326_(FormattedText p_14327_) {
        StringBuilder $$1 = new StringBuilder();
        StringDecomposer.m_14328_(p_14327_, Style.f_131099_, (p_14323_, p_14324_, p_14325_) -> {
            $$1.appendCodePoint(p_14325_);
            return true;
        });
        return $$1.toString();
    }
}

