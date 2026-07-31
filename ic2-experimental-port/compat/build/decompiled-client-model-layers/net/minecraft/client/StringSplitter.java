/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableFloat
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.ComponentCollector;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.StringDecomposer;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

public class StringSplitter {
    final WidthProvider f_92333_;

    public StringSplitter(WidthProvider p_92335_) {
        this.f_92333_ = p_92335_;
    }

    public float m_92353_(@Nullable String p_92354_) {
        if (p_92354_ == null) {
            return 0.0f;
        }
        MutableFloat $$1 = new MutableFloat();
        StringDecomposer.m_14346_(p_92354_, Style.f_131099_, (p_92429_, p_92430_, p_92431_) -> {
            $$1.add(this.f_92333_.m_92515_(p_92431_, p_92430_));
            return true;
        });
        return $$1.floatValue();
    }

    public float m_92384_(FormattedText p_92385_) {
        MutableFloat $$1 = new MutableFloat();
        StringDecomposer.m_14328_(p_92385_, Style.f_131099_, (p_92420_, p_92421_, p_92422_) -> {
            $$1.add(this.f_92333_.m_92515_(p_92422_, p_92421_));
            return true;
        });
        return $$1.floatValue();
    }

    public float m_92336_(FormattedCharSequence p_92337_) {
        MutableFloat $$1 = new MutableFloat();
        p_92337_.m_13731_((p_92400_, p_92401_, p_92402_) -> {
            $$1.add(this.f_92333_.m_92515_(p_92402_, p_92401_));
            return true;
        });
        return $$1.floatValue();
    }

    public int m_92360_(String p_92361_, int p_92362_, Style p_92363_) {
        WidthLimitedCharSink $$3 = new WidthLimitedCharSink(p_92362_);
        StringDecomposer.m_14317_(p_92361_, p_92363_, $$3);
        return $$3.m_92509_();
    }

    public String m_92410_(String p_92411_, int p_92412_, Style p_92413_) {
        return p_92411_.substring(0, this.m_92360_(p_92411_, p_92412_, p_92413_));
    }

    public String m_92423_(String p_92424_, int p_92425_, Style p_92426_) {
        MutableFloat $$3 = new MutableFloat();
        MutableInt $$4 = new MutableInt(p_92424_.length());
        StringDecomposer.m_14337_(p_92424_, p_92426_, (p_92407_, p_92408_, p_92409_) -> {
            float $$6 = $$3.addAndGet(this.f_92333_.m_92515_(p_92409_, p_92408_));
            if ($$6 > (float)p_92425_) {
                return false;
            }
            $$4.setValue(p_92407_);
            return true;
        });
        return p_92424_.substring($$4.intValue());
    }

    public int m_168626_(String p_168627_, int p_168628_, Style p_168629_) {
        WidthLimitedCharSink $$3 = new WidthLimitedCharSink(p_168628_);
        StringDecomposer.m_14346_(p_168627_, p_168629_, $$3);
        return $$3.m_92509_();
    }

    @Nullable
    public Style m_92386_(FormattedText p_92387_, int p_92388_) {
        WidthLimitedCharSink $$2 = new WidthLimitedCharSink(p_92388_);
        return p_92387_.m_7451_((p_92343_, p_92344_) -> StringDecomposer.m_14346_(p_92344_, p_92343_, $$2) ? Optional.empty() : Optional.of(p_92343_), Style.f_131099_).orElse(null);
    }

    @Nullable
    public Style m_92338_(FormattedCharSequence p_92339_, int p_92340_) {
        WidthLimitedCharSink $$2 = new WidthLimitedCharSink(p_92340_);
        MutableObject $$3 = new MutableObject();
        p_92339_.m_13731_((p_92348_, p_92349_, p_92350_) -> {
            if (!$$2.m_6411_(p_92348_, p_92349_, p_92350_)) {
                $$3.setValue((Object)p_92349_);
                return false;
            }
            return true;
        });
        return (Style)$$3.getValue();
    }

    public String m_168630_(String p_168631_, int p_168632_, Style p_168633_) {
        return p_168631_.substring(0, this.m_168626_(p_168631_, p_168632_, p_168633_));
    }

    public FormattedText m_92389_(FormattedText p_92390_, int p_92391_, Style p_92392_) {
        final WidthLimitedCharSink $$3 = new WidthLimitedCharSink(p_92391_);
        return p_92390_.m_7451_(new FormattedText.StyledContentConsumer<FormattedText>(){
            private final ComponentCollector f_92438_ = new ComponentCollector();

            @Override
            public Optional<FormattedText> m_7164_(Style p_92443_, String p_92444_) {
                $$3.m_92514_();
                if (!StringDecomposer.m_14346_(p_92444_, p_92443_, $$3)) {
                    String $$2 = p_92444_.substring(0, $$3.m_92509_());
                    if (!$$2.isEmpty()) {
                        this.f_92438_.m_90675_(FormattedText.m_130762_($$2, p_92443_));
                    }
                    return Optional.of(this.f_92438_.m_90677_());
                }
                if (!p_92444_.isEmpty()) {
                    this.f_92438_.m_90675_(FormattedText.m_130762_(p_92444_, p_92443_));
                }
                return Optional.empty();
            }
        }, p_92392_).orElse(p_92390_);
    }

    public List<Span> m_241773_(FormattedCharSequence p_242390_, Predicate<Style> p_242453_) {
        SpanBuilder $$2 = new SpanBuilder(p_242453_);
        p_242390_.m_13731_($$2);
        return $$2.m_241815_();
    }

    public int m_168634_(String p_168635_, int p_168636_, Style p_168637_) {
        LineBreakFinder $$3 = new LineBreakFinder(p_168636_);
        StringDecomposer.m_14346_(p_168635_, p_168637_, $$3);
        return $$3.m_92473_();
    }

    public static int m_92355_(String p_92356_, int p_92357_, int p_92358_, boolean p_92359_) {
        int $$4 = p_92358_;
        boolean $$5 = p_92357_ < 0;
        int $$6 = Math.abs(p_92357_);
        for (int $$7 = 0; $$7 < $$6; ++$$7) {
            if ($$5) {
                while (p_92359_ && $$4 > 0 && (p_92356_.charAt($$4 - 1) == ' ' || p_92356_.charAt($$4 - 1) == '\n')) {
                    --$$4;
                }
                while ($$4 > 0 && p_92356_.charAt($$4 - 1) != ' ' && p_92356_.charAt($$4 - 1) != '\n') {
                    --$$4;
                }
                continue;
            }
            int $$8 = p_92356_.length();
            int $$9 = p_92356_.indexOf(32, $$4);
            int $$10 = p_92356_.indexOf(10, $$4);
            $$4 = $$9 == -1 && $$10 == -1 ? -1 : ($$9 != -1 && $$10 != -1 ? Math.min($$9, $$10) : ($$9 != -1 ? $$9 : $$10));
            if ($$4 == -1) {
                $$4 = $$8;
                continue;
            }
            while (p_92359_ && $$4 < $$8 && (p_92356_.charAt($$4) == ' ' || p_92356_.charAt($$4) == '\n')) {
                ++$$4;
            }
        }
        return $$4;
    }

    public void m_92364_(String p_92365_, int p_92366_, Style p_92367_, boolean p_92368_, LinePosConsumer p_92369_) {
        int $$5 = 0;
        int $$6 = p_92365_.length();
        Style $$7 = p_92367_;
        while ($$5 < $$6) {
            LineBreakFinder $$8 = new LineBreakFinder(p_92366_);
            boolean $$9 = StringDecomposer.m_14311_(p_92365_, $$5, $$7, p_92367_, $$8);
            if ($$9) {
                p_92369_.m_92499_($$7, $$5, $$6);
                break;
            }
            int $$10 = $$8.m_92473_();
            char $$11 = p_92365_.charAt($$10);
            int $$12 = $$11 == '\n' || $$11 == ' ' ? $$10 + 1 : $$10;
            p_92369_.m_92499_($$7, $$5, p_92368_ ? $$12 : $$10);
            $$5 = $$12;
            $$7 = $$8.m_92483_();
        }
    }

    public List<FormattedText> m_92432_(String p_92433_, int p_92434_, Style p_92435_) {
        ArrayList $$3 = Lists.newArrayList();
        this.m_92364_(p_92433_, p_92434_, p_92435_, false, (p_92373_, p_92374_, p_92375_) -> $$3.add(FormattedText.m_130762_(p_92433_.substring(p_92374_, p_92375_), p_92373_)));
        return $$3;
    }

    public List<FormattedText> m_92414_(FormattedText p_92415_, int p_92416_, Style p_92417_) {
        ArrayList $$3 = Lists.newArrayList();
        this.m_92393_(p_92415_, p_92416_, p_92417_, (p_92378_, p_92379_) -> $$3.add(p_92378_));
        return $$3;
    }

    public List<FormattedText> m_168621_(FormattedText p_168622_, int p_168623_, Style p_168624_, FormattedText p_168625_) {
        ArrayList $$4 = Lists.newArrayList();
        this.m_92393_(p_168622_, p_168623_, p_168624_, (p_168619_, p_168620_) -> $$4.add(p_168620_ != false ? FormattedText.m_130773_(p_168625_, p_168619_) : p_168619_));
        return $$4;
    }

    public void m_92393_(FormattedText p_92394_, int p_92395_, Style p_92396_, BiConsumer<FormattedText, Boolean> p_92397_) {
        ArrayList $$4 = Lists.newArrayList();
        p_92394_.m_7451_((p_92382_, p_92383_) -> {
            if (!p_92383_.isEmpty()) {
                $$4.add(new LineComponent(p_92383_, p_92382_));
            }
            return Optional.empty();
        }, p_92396_);
        FlatComponents $$5 = new FlatComponents($$4);
        boolean $$6 = true;
        boolean $$7 = false;
        boolean $$8 = false;
        block0: while ($$6) {
            $$6 = false;
            LineBreakFinder $$9 = new LineBreakFinder(p_92395_);
            for (LineComponent $$10 : $$5.f_92445_) {
                boolean $$11 = StringDecomposer.m_14311_($$10.f_92485_, 0, $$10.f_92486_, p_92396_, $$9);
                if (!$$11) {
                    int $$12 = $$9.m_92473_();
                    Style $$13 = $$9.m_92483_();
                    char $$14 = $$5.m_92450_($$12);
                    boolean $$15 = $$14 == '\n';
                    boolean $$16 = $$15 || $$14 == ' ';
                    $$7 = $$15;
                    FormattedText $$17 = $$5.m_92452_($$12, $$16 ? 1 : 0, $$13);
                    p_92397_.accept($$17, $$8);
                    $$8 = !$$15;
                    $$6 = true;
                    continue block0;
                }
                $$9.m_92474_($$10.f_92485_.length());
            }
        }
        FormattedText $$18 = $$5.m_92449_();
        if ($$18 != null) {
            p_92397_.accept($$18, $$8);
        } else if ($$7) {
            p_92397_.accept(FormattedText.f_130760_, false);
        }
    }

    @FunctionalInterface
    public static interface WidthProvider {
        public float m_92515_(int var1, Style var2);
    }

    class WidthLimitedCharSink
    implements FormattedCharSink {
        private float f_92504_;
        private int f_92505_;

        public WidthLimitedCharSink(float p_92508_) {
            this.f_92504_ = p_92508_;
        }

        @Override
        public boolean m_6411_(int p_92511_, Style p_92512_, int p_92513_) {
            this.f_92504_ -= StringSplitter.this.f_92333_.m_92515_(p_92513_, p_92512_);
            if (this.f_92504_ >= 0.0f) {
                this.f_92505_ = p_92511_ + Character.charCount(p_92513_);
                return true;
            }
            return false;
        }

        public int m_92509_() {
            return this.f_92505_;
        }

        public void m_92514_() {
            this.f_92505_ = 0;
        }
    }

    class SpanBuilder
    implements FormattedCharSink {
        private final Predicate<Style> f_241665_;
        private float f_241702_;
        private final ImmutableList.Builder<Span> f_241636_ = ImmutableList.builder();
        private float f_241606_;
        private boolean f_241703_;

        SpanBuilder(Predicate<Style> p_242437_) {
            this.f_241665_ = p_242437_;
        }

        @Override
        public boolean m_6411_(int p_242323_, Style p_242193_, int p_242276_) {
            boolean $$3 = this.f_241665_.test(p_242193_);
            if (this.f_241703_ != $$3) {
                if ($$3) {
                    this.m_241841_();
                } else {
                    this.m_242019_();
                }
            }
            this.f_241702_ += StringSplitter.this.f_92333_.m_92515_(p_242276_, p_242193_);
            return true;
        }

        private void m_241841_() {
            this.f_241703_ = true;
            this.f_241606_ = this.f_241702_;
        }

        private void m_242019_() {
            float $$0 = this.f_241702_;
            this.f_241636_.add((Object)new Span(this.f_241606_, $$0));
            this.f_241703_ = false;
        }

        public List<Span> m_241815_() {
            if (this.f_241703_) {
                this.m_242019_();
            }
            return this.f_241636_.build();
        }
    }

    class LineBreakFinder
    implements FormattedCharSink {
        private final float f_92461_;
        private int f_92462_ = -1;
        private Style f_92463_ = Style.f_131099_;
        private boolean f_92464_;
        private float f_92465_;
        private int f_92466_ = -1;
        private Style f_92467_ = Style.f_131099_;
        private int f_92468_;
        private int f_92469_;

        public LineBreakFinder(float p_92472_) {
            this.f_92461_ = Math.max(p_92472_, 1.0f);
        }

        @Override
        public boolean m_6411_(int p_92480_, Style p_92481_, int p_92482_) {
            int $$3 = p_92480_ + this.f_92469_;
            switch (p_92482_) {
                case 10: {
                    return this.m_92476_($$3, p_92481_);
                }
                case 32: {
                    this.f_92466_ = $$3;
                    this.f_92467_ = p_92481_;
                }
            }
            float $$4 = StringSplitter.this.f_92333_.m_92515_(p_92482_, p_92481_);
            this.f_92465_ += $$4;
            if (this.f_92464_ && this.f_92465_ > this.f_92461_) {
                if (this.f_92466_ != -1) {
                    return this.m_92476_(this.f_92466_, this.f_92467_);
                }
                return this.m_92476_($$3, p_92481_);
            }
            this.f_92464_ |= $$4 != 0.0f;
            this.f_92468_ = $$3 + Character.charCount(p_92482_);
            return true;
        }

        private boolean m_92476_(int p_92477_, Style p_92478_) {
            this.f_92462_ = p_92477_;
            this.f_92463_ = p_92478_;
            return false;
        }

        private boolean m_92484_() {
            return this.f_92462_ != -1;
        }

        public int m_92473_() {
            return this.m_92484_() ? this.f_92462_ : this.f_92468_;
        }

        public Style m_92483_() {
            return this.f_92463_;
        }

        public void m_92474_(int p_92475_) {
            this.f_92469_ += p_92475_;
        }
    }

    @FunctionalInterface
    public static interface LinePosConsumer {
        public void m_92499_(Style var1, int var2, int var3);
    }

    static class FlatComponents {
        final List<LineComponent> f_92445_;
        private String f_92446_;

        public FlatComponents(List<LineComponent> p_92448_) {
            this.f_92445_ = p_92448_;
            this.f_92446_ = p_92448_.stream().map(p_92459_ -> p_92459_.f_92485_).collect(Collectors.joining());
        }

        public char m_92450_(int p_92451_) {
            return this.f_92446_.charAt(p_92451_);
        }

        public FormattedText m_92452_(int p_92453_, int p_92454_, Style p_92455_) {
            ComponentCollector $$3 = new ComponentCollector();
            ListIterator<LineComponent> $$4 = this.f_92445_.listIterator();
            int $$5 = p_92453_;
            boolean $$6 = false;
            while ($$4.hasNext()) {
                LineComponent $$7 = $$4.next();
                String $$8 = $$7.f_92485_;
                int $$9 = $$8.length();
                if (!$$6) {
                    if ($$5 > $$9) {
                        $$3.m_90675_($$7);
                        $$4.remove();
                        $$5 -= $$9;
                    } else {
                        String $$10 = $$8.substring(0, $$5);
                        if (!$$10.isEmpty()) {
                            $$3.m_90675_(FormattedText.m_130762_($$10, $$7.f_92486_));
                        }
                        $$5 += p_92454_;
                        $$6 = true;
                    }
                }
                if (!$$6) continue;
                if ($$5 > $$9) {
                    $$4.remove();
                    $$5 -= $$9;
                    continue;
                }
                String $$11 = $$8.substring($$5);
                if ($$11.isEmpty()) {
                    $$4.remove();
                    break;
                }
                $$4.set(new LineComponent($$11, p_92455_));
                break;
            }
            this.f_92446_ = this.f_92446_.substring(p_92453_ + p_92454_);
            return $$3.m_90677_();
        }

        @Nullable
        public FormattedText m_92449_() {
            ComponentCollector $$0 = new ComponentCollector();
            this.f_92445_.forEach($$0::m_90675_);
            this.f_92445_.clear();
            return $$0.m_90674_();
        }
    }

    static class LineComponent
    implements FormattedText {
        final String f_92485_;
        final Style f_92486_;

        public LineComponent(String p_92488_, Style p_92489_) {
            this.f_92485_ = p_92488_;
            this.f_92486_ = p_92489_;
        }

        @Override
        public <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_92493_) {
            return p_92493_.m_130809_(this.f_92485_);
        }

        @Override
        public <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_92495_, Style p_92496_) {
            return p_92495_.m_7164_(this.f_92486_.m_131146_(p_92496_), this.f_92485_);
        }
    }

    public record Span(float f_241699_, float f_241679_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Span.class, "left;right", "f_241699_", "f_241679_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Span.class, "left;right", "f_241699_", "f_241679_"}, this);
        }

        @Override
        public final boolean equals(Object p_242438_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Span.class, "left;right", "f_241699_", "f_241679_"}, this, p_242438_);
        }
    }
}

