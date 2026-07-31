/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.ibm.icu.text.ArabicShaping
 *  com.ibm.icu.text.ArabicShapingException
 *  com.ibm.icu.text.Bidi
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringDecomposer;

public class Font {
    private static final float f_168643_ = 0.01f;
    private static final Vector3f f_92712_ = new Vector3f(0.0f, 0.0f, 0.03f);
    public static final int f_193827_ = 8;
    public final int f_92710_ = 9;
    public final RandomSource f_92711_ = RandomSource.m_216327_();
    private final Function<ResourceLocation, FontSet> f_92713_;
    final boolean f_242994_;
    private final StringSplitter f_92714_;

    public Font(Function<ResourceLocation, FontSet> p_243253_, boolean p_243245_) {
        this.f_92713_ = p_243253_;
        this.f_242994_ = p_243245_;
        this.f_92714_ = new StringSplitter((p_92722_, p_92723_) -> this.m_92863_(p_92723_.m_131192_()).m_243128_(p_92722_, this.f_242994_).m_83827_(p_92723_.m_131154_()));
    }

    FontSet m_92863_(ResourceLocation p_92864_) {
        return this.f_92713_.apply(p_92864_);
    }

    public int m_92750_(PoseStack p_92751_, String p_92752_, float p_92753_, float p_92754_, int p_92755_) {
        return this.m_92803_(p_92752_, p_92753_, p_92754_, p_92755_, p_92751_.m_85850_().m_85861_(), true, this.m_92718_());
    }

    public int m_92756_(PoseStack p_92757_, String p_92758_, float p_92759_, float p_92760_, int p_92761_, boolean p_92762_) {
        return this.m_92803_(p_92758_, p_92759_, p_92760_, p_92761_, p_92757_.m_85850_().m_85861_(), true, p_92762_);
    }

    public int m_92883_(PoseStack p_92884_, String p_92885_, float p_92886_, float p_92887_, int p_92888_) {
        return this.m_92803_(p_92885_, p_92886_, p_92887_, p_92888_, p_92884_.m_85850_().m_85861_(), false, this.m_92718_());
    }

    public int m_92744_(PoseStack p_92745_, FormattedCharSequence p_92746_, float p_92747_, float p_92748_, int p_92749_) {
        return this.m_92726_(p_92746_, p_92747_, p_92748_, p_92749_, p_92745_.m_85850_().m_85861_(), true);
    }

    public int m_92763_(PoseStack p_92764_, Component p_92765_, float p_92766_, float p_92767_, int p_92768_) {
        return this.m_92726_(p_92765_.m_7532_(), p_92766_, p_92767_, p_92768_, p_92764_.m_85850_().m_85861_(), true);
    }

    public int m_92877_(PoseStack p_92878_, FormattedCharSequence p_92879_, float p_92880_, float p_92881_, int p_92882_) {
        return this.m_92726_(p_92879_, p_92880_, p_92881_, p_92882_, p_92878_.m_85850_().m_85861_(), false);
    }

    public int m_92889_(PoseStack p_92890_, Component p_92891_, float p_92892_, float p_92893_, int p_92894_) {
        return this.m_92726_(p_92891_.m_7532_(), p_92892_, p_92893_, p_92894_, p_92890_.m_85850_().m_85861_(), false);
    }

    public String m_92801_(String p_92802_) {
        try {
            Bidi $$1 = new Bidi(new ArabicShaping(8).shape(p_92802_), 127);
            $$1.setReorderingMode(0);
            return $$1.writeReordered(2);
        }
        catch (ArabicShapingException arabicShapingException) {
            return p_92802_;
        }
    }

    private int m_92803_(String p_92804_, float p_92805_, float p_92806_, int p_92807_, Matrix4f p_92808_, boolean p_92809_, boolean p_92810_) {
        if (p_92804_ == null) {
            return 0;
        }
        MultiBufferSource.BufferSource $$7 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
        int $$8 = this.m_92822_(p_92804_, p_92805_, p_92806_, p_92807_, p_92809_, p_92808_, $$7, false, 0, 0xF000F0, p_92810_);
        $$7.m_109911_();
        return $$8;
    }

    private int m_92726_(FormattedCharSequence p_92727_, float p_92728_, float p_92729_, int p_92730_, Matrix4f p_92731_, boolean p_92732_) {
        MultiBufferSource.BufferSource $$6 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
        int $$7 = this.m_92733_(p_92727_, p_92728_, p_92729_, p_92730_, p_92732_, p_92731_, $$6, false, 0, 0xF000F0);
        $$6.m_109911_();
        return $$7;
    }

    public int m_92811_(String p_92812_, float p_92813_, float p_92814_, int p_92815_, boolean p_92816_, Matrix4f p_92817_, MultiBufferSource p_92818_, boolean p_92819_, int p_92820_, int p_92821_) {
        return this.m_92822_(p_92812_, p_92813_, p_92814_, p_92815_, p_92816_, p_92817_, p_92818_, p_92819_, p_92820_, p_92821_, this.m_92718_());
    }

    public int m_92822_(String p_92823_, float p_92824_, float p_92825_, int p_92826_, boolean p_92827_, Matrix4f p_92828_, MultiBufferSource p_92829_, boolean p_92830_, int p_92831_, int p_92832_, boolean p_92833_) {
        return this.m_92908_(p_92823_, p_92824_, p_92825_, p_92826_, p_92827_, p_92828_, p_92829_, p_92830_, p_92831_, p_92832_, p_92833_);
    }

    public int m_92841_(Component p_92842_, float p_92843_, float p_92844_, int p_92845_, boolean p_92846_, Matrix4f p_92847_, MultiBufferSource p_92848_, boolean p_92849_, int p_92850_, int p_92851_) {
        return this.m_92733_(p_92842_.m_7532_(), p_92843_, p_92844_, p_92845_, p_92846_, p_92847_, p_92848_, p_92849_, p_92850_, p_92851_);
    }

    public int m_92733_(FormattedCharSequence p_92734_, float p_92735_, float p_92736_, int p_92737_, boolean p_92738_, Matrix4f p_92739_, MultiBufferSource p_92740_, boolean p_92741_, int p_92742_, int p_92743_) {
        return this.m_92866_(p_92734_, p_92735_, p_92736_, p_92737_, p_92738_, p_92739_, p_92740_, p_92741_, p_92742_, p_92743_);
    }

    public void m_168645_(FormattedCharSequence p_168646_, float p_168647_, float p_168648_, int p_168649_, int p_168650_, Matrix4f p_168651_, MultiBufferSource p_168652_, int p_168653_) {
        int $$8 = Font.m_92719_(p_168650_);
        StringRenderOutput $$9 = new StringRenderOutput(p_168652_, 0.0f, 0.0f, $$8, false, p_168651_, DisplayMode.NORMAL, p_168653_);
        for (int $$10 = -1; $$10 <= 1; ++$$10) {
            for (int $$11 = -1; $$11 <= 1; ++$$11) {
                if ($$10 == 0 && $$11 == 0) continue;
                float[] $$12 = new float[]{p_168647_};
                int $$13 = $$10;
                int $$14 = $$11;
                p_168646_.m_13731_((p_168661_, p_168662_, p_168663_) -> {
                    boolean $$9 = p_168662_.m_131154_();
                    FontSet $$10 = this.m_92863_(p_168662_.m_131192_());
                    GlyphInfo $$11 = $$10.m_243128_(p_168663_, this.f_242994_);
                    p_168655_.f_92948_ = $$12[0] + (float)$$13 * $$11.m_5645_();
                    p_168655_.f_92949_ = p_168648_ + (float)$$14 * $$11.m_5645_();
                    p_168656_[0] = $$12[0] + $$11.m_83827_($$9);
                    return $$9.m_6411_(p_168661_, p_168662_.m_178520_($$8), p_168663_);
                });
            }
        }
        StringRenderOutput $$15 = new StringRenderOutput(p_168652_, p_168647_, p_168648_, Font.m_92719_(p_168649_), false, p_168651_, DisplayMode.POLYGON_OFFSET, p_168653_);
        p_168646_.m_13731_($$15);
        $$15.m_92961_(0, p_168647_);
    }

    private static int m_92719_(int p_92720_) {
        if ((p_92720_ & 0xFC000000) == 0) {
            return p_92720_ | 0xFF000000;
        }
        return p_92720_;
    }

    private int m_92908_(String p_92909_, float p_92910_, float p_92911_, int p_92912_, boolean p_92913_, Matrix4f p_92914_, MultiBufferSource p_92915_, boolean p_92916_, int p_92917_, int p_92918_, boolean p_92919_) {
        if (p_92919_) {
            p_92909_ = this.m_92801_(p_92909_);
        }
        p_92912_ = Font.m_92719_(p_92912_);
        Matrix4f $$11 = p_92914_.m_27658_();
        if (p_92913_) {
            this.m_92897_(p_92909_, p_92910_, p_92911_, p_92912_, true, p_92914_, p_92915_, p_92916_, p_92917_, p_92918_);
            $$11.m_27648_(f_92712_);
        }
        p_92910_ = this.m_92897_(p_92909_, p_92910_, p_92911_, p_92912_, false, $$11, p_92915_, p_92916_, p_92917_, p_92918_);
        return (int)p_92910_ + (p_92913_ ? 1 : 0);
    }

    private int m_92866_(FormattedCharSequence p_92867_, float p_92868_, float p_92869_, int p_92870_, boolean p_92871_, Matrix4f p_92872_, MultiBufferSource p_92873_, boolean p_92874_, int p_92875_, int p_92876_) {
        p_92870_ = Font.m_92719_(p_92870_);
        Matrix4f $$10 = p_92872_.m_27658_();
        if (p_92871_) {
            this.m_92926_(p_92867_, p_92868_, p_92869_, p_92870_, true, p_92872_, p_92873_, p_92874_, p_92875_, p_92876_);
            $$10.m_27648_(f_92712_);
        }
        p_92868_ = this.m_92926_(p_92867_, p_92868_, p_92869_, p_92870_, false, $$10, p_92873_, p_92874_, p_92875_, p_92876_);
        return (int)p_92868_ + (p_92871_ ? 1 : 0);
    }

    private float m_92897_(String p_92898_, float p_92899_, float p_92900_, int p_92901_, boolean p_92902_, Matrix4f p_92903_, MultiBufferSource p_92904_, boolean p_92905_, int p_92906_, int p_92907_) {
        StringRenderOutput $$10 = new StringRenderOutput(p_92904_, p_92899_, p_92900_, p_92901_, p_92902_, p_92903_, p_92905_, p_92907_);
        StringDecomposer.m_14346_(p_92898_, Style.f_131099_, $$10);
        return $$10.m_92961_(p_92906_, p_92899_);
    }

    private float m_92926_(FormattedCharSequence p_92927_, float p_92928_, float p_92929_, int p_92930_, boolean p_92931_, Matrix4f p_92932_, MultiBufferSource p_92933_, boolean p_92934_, int p_92935_, int p_92936_) {
        StringRenderOutput $$10 = new StringRenderOutput(p_92933_, p_92928_, p_92929_, p_92930_, p_92931_, p_92932_, p_92934_, p_92936_);
        p_92927_.m_13731_($$10);
        return $$10.m_92961_(p_92935_, p_92928_);
    }

    void m_92787_(BakedGlyph p_92788_, boolean p_92789_, boolean p_92790_, float p_92791_, float p_92792_, float p_92793_, Matrix4f p_92794_, VertexConsumer p_92795_, float p_92796_, float p_92797_, float p_92798_, float p_92799_, int p_92800_) {
        p_92788_.m_5626_(p_92790_, p_92792_, p_92793_, p_92794_, p_92795_, p_92796_, p_92797_, p_92798_, p_92799_, p_92800_);
        if (p_92789_) {
            p_92788_.m_5626_(p_92790_, p_92792_ + p_92791_, p_92793_, p_92794_, p_92795_, p_92796_, p_92797_, p_92798_, p_92799_, p_92800_);
        }
    }

    public int m_92895_(String p_92896_) {
        return Mth.m_14167_(this.f_92714_.m_92353_(p_92896_));
    }

    public int m_92852_(FormattedText p_92853_) {
        return Mth.m_14167_(this.f_92714_.m_92384_(p_92853_));
    }

    public int m_92724_(FormattedCharSequence p_92725_) {
        return Mth.m_14167_(this.f_92714_.m_92336_(p_92725_));
    }

    public String m_92837_(String p_92838_, int p_92839_, boolean p_92840_) {
        return p_92840_ ? this.f_92714_.m_92423_(p_92838_, p_92839_, Style.f_131099_) : this.f_92714_.m_92410_(p_92838_, p_92839_, Style.f_131099_);
    }

    public String m_92834_(String p_92835_, int p_92836_) {
        return this.f_92714_.m_92410_(p_92835_, p_92836_, Style.f_131099_);
    }

    public FormattedText m_92854_(FormattedText p_92855_, int p_92856_) {
        return this.f_92714_.m_92389_(p_92855_, p_92856_, Style.f_131099_);
    }

    public void m_92857_(FormattedText p_92858_, int p_92859_, int p_92860_, int p_92861_, int p_92862_) {
        Matrix4f $$5 = Transformation.m_121093_().m_121104_();
        for (FormattedCharSequence $$6 : this.m_92923_(p_92858_, p_92861_)) {
            this.m_92726_($$6, p_92859_, p_92860_, p_92862_, $$5, false);
            p_92860_ += 9;
        }
    }

    public int m_92920_(String p_92921_, int p_92922_) {
        return 9 * this.f_92714_.m_92432_(p_92921_, p_92922_, Style.f_131099_).size();
    }

    public int m_239133_(FormattedText p_239134_, int p_239135_) {
        return 9 * this.f_92714_.m_92414_(p_239134_, p_239135_, Style.f_131099_).size();
    }

    public List<FormattedCharSequence> m_92923_(FormattedText p_92924_, int p_92925_) {
        return Language.m_128107_().m_128112_(this.f_92714_.m_92414_(p_92924_, p_92925_, Style.f_131099_));
    }

    public boolean m_92718_() {
        return Language.m_128107_().m_6627_();
    }

    public StringSplitter m_92865_() {
        return this.f_92714_;
    }

    class StringRenderOutput
    implements FormattedCharSink {
        final MultiBufferSource f_92937_;
        private final boolean f_92939_;
        private final float f_92940_;
        private final float f_92941_;
        private final float f_92942_;
        private final float f_92943_;
        private final float f_92944_;
        private final Matrix4f f_92945_;
        private final DisplayMode f_181362_;
        private final int f_92947_;
        float f_92948_;
        float f_92949_;
        @Nullable
        private List<BakedGlyph.Effect> f_92950_;

        private void m_92964_(BakedGlyph.Effect p_92965_) {
            if (this.f_92950_ == null) {
                this.f_92950_ = Lists.newArrayList();
            }
            this.f_92950_.add(p_92965_);
        }

        public StringRenderOutput(MultiBufferSource p_92953_, float p_92954_, float p_92955_, int p_92956_, boolean p_92957_, Matrix4f p_92958_, boolean p_92959_, int p_92960_) {
            this(p_92953_, p_92954_, p_92955_, p_92956_, p_92957_, p_92958_, p_92959_ ? DisplayMode.SEE_THROUGH : DisplayMode.NORMAL, p_92960_);
        }

        public StringRenderOutput(MultiBufferSource p_181365_, float p_181366_, float p_181367_, int p_181368_, boolean p_181369_, Matrix4f p_181370_, DisplayMode p_181371_, int p_181372_) {
            this.f_92937_ = p_181365_;
            this.f_92948_ = p_181366_;
            this.f_92949_ = p_181367_;
            this.f_92939_ = p_181369_;
            this.f_92940_ = p_181369_ ? 0.25f : 1.0f;
            this.f_92941_ = (float)(p_181368_ >> 16 & 0xFF) / 255.0f * this.f_92940_;
            this.f_92942_ = (float)(p_181368_ >> 8 & 0xFF) / 255.0f * this.f_92940_;
            this.f_92943_ = (float)(p_181368_ & 0xFF) / 255.0f * this.f_92940_;
            this.f_92944_ = (float)(p_181368_ >> 24 & 0xFF) / 255.0f;
            this.f_92945_ = p_181370_;
            this.f_181362_ = p_181371_;
            this.f_92947_ = p_181372_;
        }

        @Override
        public boolean m_6411_(int p_92967_, Style p_92968_, int p_92969_) {
            float $$20;
            float $$15;
            float $$14;
            float $$13;
            FontSet $$3 = Font.this.m_92863_(p_92968_.m_131192_());
            GlyphInfo $$4 = $$3.m_243128_(p_92969_, Font.this.f_242994_);
            BakedGlyph $$5 = p_92968_.m_131176_() && p_92969_ != 32 ? $$3.m_95067_($$4) : $$3.m_95078_(p_92969_);
            boolean $$6 = p_92968_.m_131154_();
            float $$7 = this.f_92944_;
            TextColor $$8 = p_92968_.m_131135_();
            if ($$8 != null) {
                int $$9 = $$8.m_131265_();
                float $$10 = (float)($$9 >> 16 & 0xFF) / 255.0f * this.f_92940_;
                float $$11 = (float)($$9 >> 8 & 0xFF) / 255.0f * this.f_92940_;
                float $$12 = (float)($$9 & 0xFF) / 255.0f * this.f_92940_;
            } else {
                $$13 = this.f_92941_;
                $$14 = this.f_92942_;
                $$15 = this.f_92943_;
            }
            if (!($$5 instanceof EmptyGlyph)) {
                float $$16 = $$6 ? $$4.m_5619_() : 0.0f;
                float $$17 = this.f_92939_ ? $$4.m_5645_() : 0.0f;
                VertexConsumer $$18 = this.f_92937_.m_6299_($$5.m_181387_(this.f_181362_));
                Font.this.m_92787_($$5, $$6, p_92968_.m_131161_(), $$16, this.f_92948_ + $$17, this.f_92949_ + $$17, this.f_92945_, $$18, $$13, $$14, $$15, $$7, this.f_92947_);
            }
            float $$19 = $$4.m_83827_($$6);
            float f = $$20 = this.f_92939_ ? 1.0f : 0.0f;
            if (p_92968_.m_131168_()) {
                this.m_92964_(new BakedGlyph.Effect(this.f_92948_ + $$20 - 1.0f, this.f_92949_ + $$20 + 4.5f, this.f_92948_ + $$20 + $$19, this.f_92949_ + $$20 + 4.5f - 1.0f, 0.01f, $$13, $$14, $$15, $$7));
            }
            if (p_92968_.m_131171_()) {
                this.m_92964_(new BakedGlyph.Effect(this.f_92948_ + $$20 - 1.0f, this.f_92949_ + $$20 + 9.0f, this.f_92948_ + $$20 + $$19, this.f_92949_ + $$20 + 9.0f - 1.0f, 0.01f, $$13, $$14, $$15, $$7));
            }
            this.f_92948_ += $$19;
            return true;
        }

        public float m_92961_(int p_92962_, float p_92963_) {
            if (p_92962_ != 0) {
                float $$2 = (float)(p_92962_ >> 24 & 0xFF) / 255.0f;
                float $$3 = (float)(p_92962_ >> 16 & 0xFF) / 255.0f;
                float $$4 = (float)(p_92962_ >> 8 & 0xFF) / 255.0f;
                float $$5 = (float)(p_92962_ & 0xFF) / 255.0f;
                this.m_92964_(new BakedGlyph.Effect(p_92963_ - 1.0f, this.f_92949_ + 9.0f, this.f_92948_ + 1.0f, this.f_92949_ - 1.0f, 0.01f, $$3, $$4, $$5, $$2));
            }
            if (this.f_92950_ != null) {
                BakedGlyph $$6 = Font.this.m_92863_(Style.f_131100_).m_95064_();
                VertexConsumer $$7 = this.f_92937_.m_6299_($$6.m_181387_(this.f_181362_));
                for (BakedGlyph.Effect $$8 : this.f_92950_) {
                    $$6.m_95220_($$8, this.f_92945_, $$7, this.f_92947_);
                }
            }
            return this.f_92948_;
        }
    }

    public static final class DisplayMode
    extends Enum<DisplayMode> {
        public static final /* enum */ DisplayMode NORMAL = new DisplayMode();
        public static final /* enum */ DisplayMode SEE_THROUGH = new DisplayMode();
        public static final /* enum */ DisplayMode POLYGON_OFFSET = new DisplayMode();
        private static final /* synthetic */ DisplayMode[] $VALUES;

        public static DisplayMode[] values() {
            return (DisplayMode[])$VALUES.clone();
        }

        public static DisplayMode valueOf(String p_181360_) {
            return Enum.valueOf(DisplayMode.class, p_181360_);
        }

        private static /* synthetic */ DisplayMode[] m_181358_() {
            return new DisplayMode[]{NORMAL, SEE_THROUGH, POLYGON_OFFSET};
        }

        static {
            $VALUES = DisplayMode.m_181358_();
        }
    }
}

