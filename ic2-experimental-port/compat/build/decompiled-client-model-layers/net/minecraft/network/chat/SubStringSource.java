/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.Int2IntFunction
 */
package net.minecraft.network.chat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringDecomposer;

public class SubStringSource {
    private final String f_131228_;
    private final List<Style> f_131229_;
    private final Int2IntFunction f_131230_;

    private SubStringSource(String p_131232_, List<Style> p_131233_, Int2IntFunction p_131234_) {
        this.f_131228_ = p_131232_;
        this.f_131229_ = ImmutableList.copyOf(p_131233_);
        this.f_131230_ = p_131234_;
    }

    public String m_131235_() {
        return this.f_131228_;
    }

    public List<FormattedCharSequence> m_131236_(int p_131237_, int p_131238_, boolean p_131239_) {
        if (p_131238_ == 0) {
            return ImmutableList.of();
        }
        ArrayList $$3 = Lists.newArrayList();
        Style $$4 = this.f_131229_.get(p_131237_);
        int $$5 = p_131237_;
        for (int $$6 = 1; $$6 < p_131238_; ++$$6) {
            int $$7 = p_131237_ + $$6;
            Style $$8 = this.f_131229_.get($$7);
            if ($$8.equals($$4)) continue;
            String $$9 = this.f_131228_.substring($$5, $$7);
            $$3.add(p_131239_ ? FormattedCharSequence.m_13740_($$9, $$4, this.f_131230_) : FormattedCharSequence.m_13714_($$9, $$4));
            $$4 = $$8;
            $$5 = $$7;
        }
        if ($$5 < p_131237_ + p_131238_) {
            String $$10 = this.f_131228_.substring($$5, p_131237_ + p_131238_);
            $$3.add(p_131239_ ? FormattedCharSequence.m_13740_($$10, $$4, this.f_131230_) : FormattedCharSequence.m_13714_($$10, $$4));
        }
        return p_131239_ ? Lists.reverse((List)$$3) : $$3;
    }

    public static SubStringSource m_178536_(FormattedText p_178537_) {
        return SubStringSource.m_131251_(p_178537_, p_178527_ -> p_178527_, p_178529_ -> p_178529_);
    }

    public static SubStringSource m_131251_(FormattedText p_131252_, Int2IntFunction p_131253_, UnaryOperator<String> p_131254_) {
        StringBuilder $$3 = new StringBuilder();
        ArrayList $$4 = Lists.newArrayList();
        p_131252_.m_7451_((p_131249_, p_131250_) -> {
            StringDecomposer.m_14346_(p_131250_, p_131249_, (p_178533_, p_178534_, p_178535_) -> {
                $$3.appendCodePoint(p_178535_);
                int $$5 = Character.charCount(p_178535_);
                for (int $$6 = 0; $$6 < $$5; ++$$6) {
                    $$4.add(p_178534_);
                }
                return true;
            });
            return Optional.empty();
        }, Style.f_131099_);
        return new SubStringSource((String)p_131254_.apply($$3.toString()), $$4, p_131253_);
    }
}

