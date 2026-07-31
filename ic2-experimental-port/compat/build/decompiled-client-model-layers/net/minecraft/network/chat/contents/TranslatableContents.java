/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat.contents;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableFormatException;
import net.minecraft.world.entity.Entity;

public class TranslatableContents
implements ComponentContents {
    private static final Object[] f_237494_ = new Object[0];
    private static final FormattedText f_237495_ = FormattedText.m_130775_("%");
    private static final FormattedText f_237496_ = FormattedText.m_130775_("null");
    private final String f_237497_;
    private final Object[] f_237498_;
    @Nullable
    private Language f_237499_;
    private List<FormattedText> f_237500_ = ImmutableList.of();
    private static final Pattern f_237501_ = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    public TranslatableContents(String p_237504_) {
        this.f_237497_ = p_237504_;
        this.f_237498_ = f_237494_;
    }

    public TranslatableContents(String p_237506_, Object ... p_237507_) {
        this.f_237497_ = p_237506_;
        this.f_237498_ = p_237507_;
    }

    private void m_237524_() {
        Language $$0 = Language.m_128107_();
        if ($$0 == this.f_237499_) {
            return;
        }
        this.f_237499_ = $$0;
        String $$1 = $$0.m_6834_(this.f_237497_);
        try {
            ImmutableList.Builder $$2 = ImmutableList.builder();
            this.m_237515_($$1, arg_0 -> ((ImmutableList.Builder)$$2).add(arg_0));
            this.f_237500_ = $$2.build();
        }
        catch (TranslatableFormatException $$3) {
            this.f_237500_ = ImmutableList.of((Object)FormattedText.m_130775_($$1));
        }
    }

    private void m_237515_(String p_237516_, Consumer<FormattedText> p_237517_) {
        Matcher $$2 = f_237501_.matcher(p_237516_);
        try {
            int $$3 = 0;
            int $$4 = 0;
            while ($$2.find($$4)) {
                int $$5 = $$2.start();
                int $$6 = $$2.end();
                if ($$5 > $$4) {
                    String $$7 = p_237516_.substring($$4, $$5);
                    if ($$7.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }
                    p_237517_.accept(FormattedText.m_130775_($$7));
                }
                String $$8 = $$2.group(2);
                String $$9 = p_237516_.substring($$5, $$6);
                if ("%".equals($$8) && "%%".equals($$9)) {
                    p_237517_.accept(f_237495_);
                } else if ("s".equals($$8)) {
                    int $$11;
                    String $$10 = $$2.group(1);
                    int n = $$11 = $$10 != null ? Integer.parseInt($$10) - 1 : $$3++;
                    if ($$11 < this.f_237498_.length) {
                        p_237517_.accept(this.m_237509_($$11));
                    }
                } else {
                    throw new TranslatableFormatException(this, "Unsupported format: '" + $$9 + "'");
                }
                $$4 = $$6;
            }
            if ($$4 < p_237516_.length()) {
                String $$12 = p_237516_.substring($$4);
                if ($$12.indexOf(37) != -1) {
                    throw new IllegalArgumentException();
                }
                p_237517_.accept(FormattedText.m_130775_($$12));
            }
        }
        catch (IllegalArgumentException $$13) {
            throw new TranslatableFormatException(this, (Throwable)$$13);
        }
    }

    private FormattedText m_237509_(int p_237510_) {
        if (p_237510_ >= this.f_237498_.length) {
            throw new TranslatableFormatException(this, p_237510_);
        }
        Object $$1 = this.f_237498_[p_237510_];
        if ($$1 instanceof Component) {
            return (Component)$$1;
        }
        return $$1 == null ? f_237496_ : FormattedText.m_130775_($$1.toString());
    }

    @Override
    public <T> Optional<T> m_213724_(FormattedText.StyledContentConsumer<T> p_237521_, Style p_237522_) {
        this.m_237524_();
        for (FormattedText $$2 : this.f_237500_) {
            Optional<T> $$3 = $$2.m_7451_(p_237521_, p_237522_);
            if (!$$3.isPresent()) continue;
            return $$3;
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> m_213874_(FormattedText.ContentConsumer<T> p_237519_) {
        this.m_237524_();
        for (FormattedText $$1 : this.f_237500_) {
            Optional<T> $$2 = $$1.m_5651_(p_237519_);
            if (!$$2.isPresent()) continue;
            return $$2;
        }
        return Optional.empty();
    }

    @Override
    public MutableComponent m_213698_(@Nullable CommandSourceStack p_237512_, @Nullable Entity p_237513_, int p_237514_) throws CommandSyntaxException {
        Object[] $$3 = new Object[this.f_237498_.length];
        for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
            Object $$5 = this.f_237498_[$$4];
            $$3[$$4] = $$5 instanceof Component ? ComponentUtils.m_130731_(p_237512_, (Component)$$5, p_237513_, p_237514_) : $$5;
        }
        return MutableComponent.m_237204_(new TranslatableContents(this.f_237497_, $$3));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object p_237526_) {
        if (this == p_237526_) {
            return true;
        }
        if (!(p_237526_ instanceof TranslatableContents)) return false;
        TranslatableContents $$1 = (TranslatableContents)p_237526_;
        if (!this.f_237497_.equals($$1.f_237497_)) return false;
        if (!Arrays.equals(this.f_237498_, $$1.f_237498_)) return false;
        return true;
    }

    public int hashCode() {
        int $$0 = this.f_237497_.hashCode();
        $$0 = 31 * $$0 + Arrays.hashCode(this.f_237498_);
        return $$0;
    }

    public String toString() {
        return "translation{key='" + this.f_237497_ + "', args=" + Arrays.toString(this.f_237498_) + "}";
    }

    public String m_237508_() {
        return this.f_237497_;
    }

    public Object[] m_237523_() {
        return this.f_237498_;
    }
}

