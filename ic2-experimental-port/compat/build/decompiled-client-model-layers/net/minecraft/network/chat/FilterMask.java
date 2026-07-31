/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.util.BitSet;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.Component;

public class FilterMask {
    public static final FilterMask f_243007_ = new FilterMask(new BitSet(0), Type.FULLY_FILTERED);
    public static final FilterMask f_242999_ = new FilterMask(new BitSet(0), Type.PASS_THROUGH);
    private static final char f_243009_ = '#';
    private final BitSet f_242988_;
    private final Type f_242996_;

    private FilterMask(BitSet p_243243_, Type p_243249_) {
        this.f_242988_ = p_243243_;
        this.f_242996_ = p_243249_;
    }

    public FilterMask(int p_243210_) {
        this(new BitSet(p_243210_), Type.PARTIALLY_FILTERED);
    }

    public static FilterMask m_243104_(FriendlyByteBuf p_243205_) {
        Type $$1 = p_243205_.m_130066_(Type.class);
        return switch ($$1) {
            default -> throw new IncompatibleClassChangeError();
            case Type.PASS_THROUGH -> f_242999_;
            case Type.FULLY_FILTERED -> f_243007_;
            case Type.PARTIALLY_FILTERED -> new FilterMask(p_243205_.m_178384_(), Type.PARTIALLY_FILTERED);
        };
    }

    public static void m_243105_(FriendlyByteBuf p_243308_, FilterMask p_243231_) {
        p_243308_.m_130068_(p_243231_.f_242996_);
        if (p_243231_.f_242996_ == Type.PARTIALLY_FILTERED) {
            p_243308_.m_178350_(p_243231_.f_242988_);
        }
    }

    public void m_243123_(int p_243202_) {
        this.f_242988_.set(p_243202_);
    }

    @Nullable
    public String m_243114_(String p_243317_) {
        return switch (this.f_242996_) {
            default -> throw new IncompatibleClassChangeError();
            case Type.FULLY_FILTERED -> null;
            case Type.PASS_THROUGH -> p_243317_;
            case Type.PARTIALLY_FILTERED -> {
                char[] $$1 = p_243317_.toCharArray();
                for (int $$2 = 0; $$2 < $$1.length && $$2 < this.f_242988_.length(); ++$$2) {
                    if (!this.f_242988_.get($$2)) continue;
                    $$1[$$2] = 35;
                }
                yield new String($$1);
            }
        };
    }

    @Nullable
    public Component m_243081_(ChatMessageContent p_243242_) {
        String $$1 = p_243242_.f_241656_();
        return Util.m_214614_(this.m_243114_($$1), Component::m_237113_);
    }

    public boolean m_243095_() {
        return this.f_242996_ == Type.PASS_THROUGH;
    }

    public boolean m_243067_() {
        return this.f_242996_ == Type.FULLY_FILTERED;
    }

    static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type PASS_THROUGH = new Type();
        public static final /* enum */ Type FULLY_FILTERED = new Type();
        public static final /* enum */ Type PARTIALLY_FILTERED = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_243219_) {
            return Enum.valueOf(Type.class, p_243219_);
        }

        private static /* synthetic */ Type[] m_243133_() {
            return new Type[]{PASS_THROUGH, FULLY_FILTERED, PARTIALLY_FILTERED};
        }

        static {
            $VALUES = Type.m_243133_();
        }
    }
}

