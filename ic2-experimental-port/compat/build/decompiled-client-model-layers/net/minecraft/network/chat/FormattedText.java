/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.network.chat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Unit;

public interface FormattedText {
    public static final Optional<Unit> f_130759_ = Optional.of(Unit.INSTANCE);
    public static final FormattedText f_130760_ = new FormattedText(){

        @Override
        public <T> Optional<T> m_5651_(ContentConsumer<T> p_130779_) {
            return Optional.empty();
        }

        @Override
        public <T> Optional<T> m_7451_(StyledContentConsumer<T> p_130781_, Style p_130782_) {
            return Optional.empty();
        }
    };

    public <T> Optional<T> m_5651_(ContentConsumer<T> var1);

    public <T> Optional<T> m_7451_(StyledContentConsumer<T> var1, Style var2);

    public static FormattedText m_130775_(final String p_130776_) {
        return new FormattedText(){

            @Override
            public <T> Optional<T> m_5651_(ContentConsumer<T> p_130787_) {
                return p_130787_.m_130809_(p_130776_);
            }

            @Override
            public <T> Optional<T> m_7451_(StyledContentConsumer<T> p_130789_, Style p_130790_) {
                return p_130789_.m_7164_(p_130790_, p_130776_);
            }
        };
    }

    public static FormattedText m_130762_(final String p_130763_, final Style p_130764_) {
        return new FormattedText(){

            @Override
            public <T> Optional<T> m_5651_(ContentConsumer<T> p_130797_) {
                return p_130797_.m_130809_(p_130763_);
            }

            @Override
            public <T> Optional<T> m_7451_(StyledContentConsumer<T> p_130799_, Style p_130800_) {
                return p_130799_.m_7164_(p_130764_.m_131146_(p_130800_), p_130763_);
            }
        };
    }

    public static FormattedText m_130773_(FormattedText ... p_130774_) {
        return FormattedText.m_130768_((List<? extends FormattedText>)ImmutableList.copyOf((Object[])p_130774_));
    }

    public static FormattedText m_130768_(final List<? extends FormattedText> p_130769_) {
        return new FormattedText(){

            @Override
            public <T> Optional<T> m_5651_(ContentConsumer<T> p_130805_) {
                for (FormattedText $$1 : p_130769_) {
                    Optional<T> $$2 = $$1.m_5651_(p_130805_);
                    if (!$$2.isPresent()) continue;
                    return $$2;
                }
                return Optional.empty();
            }

            @Override
            public <T> Optional<T> m_7451_(StyledContentConsumer<T> p_130807_, Style p_130808_) {
                for (FormattedText $$2 : p_130769_) {
                    Optional<T> $$3 = $$2.m_7451_(p_130807_, p_130808_);
                    if (!$$3.isPresent()) continue;
                    return $$3;
                }
                return Optional.empty();
            }
        };
    }

    default public String getString() {
        StringBuilder $$0 = new StringBuilder();
        this.m_5651_(p_130767_ -> {
            $$0.append(p_130767_);
            return Optional.empty();
        });
        return $$0.toString();
    }

    public static interface ContentConsumer<T> {
        public Optional<T> m_130809_(String var1);
    }

    public static interface StyledContentConsumer<T> {
        public Optional<T> m_7164_(Style var1, String var2);
    }
}

