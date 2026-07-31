/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Consumer;

public class VertexMultiConsumer {
    public static VertexConsumer m_167060_() {
        throw new IllegalArgumentException();
    }

    public static VertexConsumer m_167061_(VertexConsumer p_167062_) {
        return p_167062_;
    }

    public static VertexConsumer m_86168_(VertexConsumer p_86169_, VertexConsumer p_86170_) {
        return new Double(p_86169_, p_86170_);
    }

    public static VertexConsumer m_167063_(VertexConsumer ... p_167064_) {
        return new Multiple(p_167064_);
    }

    static class Double
    implements VertexConsumer {
        private final VertexConsumer f_86171_;
        private final VertexConsumer f_86172_;

        public Double(VertexConsumer p_86174_, VertexConsumer p_86175_) {
            if (p_86174_ == p_86175_) {
                throw new IllegalArgumentException("Duplicate delegates");
            }
            this.f_86171_ = p_86174_;
            this.f_86172_ = p_86175_;
        }

        @Override
        public VertexConsumer m_5483_(double p_86177_, double p_86178_, double p_86179_) {
            this.f_86171_.m_5483_(p_86177_, p_86178_, p_86179_);
            this.f_86172_.m_5483_(p_86177_, p_86178_, p_86179_);
            return this;
        }

        @Override
        public VertexConsumer m_6122_(int p_86202_, int p_86203_, int p_86204_, int p_86205_) {
            this.f_86171_.m_6122_(p_86202_, p_86203_, p_86204_, p_86205_);
            this.f_86172_.m_6122_(p_86202_, p_86203_, p_86204_, p_86205_);
            return this;
        }

        @Override
        public VertexConsumer m_7421_(float p_86181_, float p_86182_) {
            this.f_86171_.m_7421_(p_86181_, p_86182_);
            this.f_86172_.m_7421_(p_86181_, p_86182_);
            return this;
        }

        @Override
        public VertexConsumer m_7122_(int p_86199_, int p_86200_) {
            this.f_86171_.m_7122_(p_86199_, p_86200_);
            this.f_86172_.m_7122_(p_86199_, p_86200_);
            return this;
        }

        @Override
        public VertexConsumer m_7120_(int p_86211_, int p_86212_) {
            this.f_86171_.m_7120_(p_86211_, p_86212_);
            this.f_86172_.m_7120_(p_86211_, p_86212_);
            return this;
        }

        @Override
        public VertexConsumer m_5601_(float p_86207_, float p_86208_, float p_86209_) {
            this.f_86171_.m_5601_(p_86207_, p_86208_, p_86209_);
            this.f_86172_.m_5601_(p_86207_, p_86208_, p_86209_);
            return this;
        }

        @Override
        public void m_5954_(float p_86184_, float p_86185_, float p_86186_, float p_86187_, float p_86188_, float p_86189_, float p_86190_, float p_86191_, float p_86192_, int p_86193_, int p_86194_, float p_86195_, float p_86196_, float p_86197_) {
            this.f_86171_.m_5954_(p_86184_, p_86185_, p_86186_, p_86187_, p_86188_, p_86189_, p_86190_, p_86191_, p_86192_, p_86193_, p_86194_, p_86195_, p_86196_, p_86197_);
            this.f_86172_.m_5954_(p_86184_, p_86185_, p_86186_, p_86187_, p_86188_, p_86189_, p_86190_, p_86191_, p_86192_, p_86193_, p_86194_, p_86195_, p_86196_, p_86197_);
        }

        @Override
        public void m_5752_() {
            this.f_86171_.m_5752_();
            this.f_86172_.m_5752_();
        }

        @Override
        public void m_7404_(int p_167066_, int p_167067_, int p_167068_, int p_167069_) {
            this.f_86171_.m_7404_(p_167066_, p_167067_, p_167068_, p_167069_);
            this.f_86172_.m_7404_(p_167066_, p_167067_, p_167068_, p_167069_);
        }

        @Override
        public void m_141991_() {
            this.f_86171_.m_141991_();
            this.f_86172_.m_141991_();
        }
    }

    static class Multiple
    implements VertexConsumer {
        private final VertexConsumer[] f_167071_;

        public Multiple(VertexConsumer[] p_167073_) {
            for (int $$1 = 0; $$1 < p_167073_.length; ++$$1) {
                for (int $$2 = $$1 + 1; $$2 < p_167073_.length; ++$$2) {
                    if (p_167073_[$$1] != p_167073_[$$2]) continue;
                    throw new IllegalArgumentException("Duplicate delegates");
                }
            }
            this.f_167071_ = p_167073_;
        }

        private void m_167144_(Consumer<VertexConsumer> p_167145_) {
            for (VertexConsumer $$1 : this.f_167071_) {
                p_167145_.accept($$1);
            }
        }

        @Override
        public VertexConsumer m_5483_(double p_167075_, double p_167076_, double p_167077_) {
            this.m_167144_(p_167082_ -> p_167082_.m_5483_(p_167075_, p_167076_, p_167077_));
            return this;
        }

        @Override
        public VertexConsumer m_6122_(int p_167130_, int p_167131_, int p_167132_, int p_167133_) {
            this.m_167144_(p_167163_ -> p_167163_.m_6122_(p_167130_, p_167131_, p_167132_, p_167133_));
            return this;
        }

        @Override
        public VertexConsumer m_7421_(float p_167084_, float p_167085_) {
            this.m_167144_(p_167125_ -> p_167125_.m_7421_(p_167084_, p_167085_));
            return this;
        }

        @Override
        public VertexConsumer m_7122_(int p_167127_, int p_167128_) {
            this.m_167144_(p_167167_ -> p_167167_.m_7122_(p_167127_, p_167128_));
            return this;
        }

        @Override
        public VertexConsumer m_7120_(int p_167151_, int p_167152_) {
            this.m_167144_(p_167143_ -> p_167143_.m_7120_(p_167151_, p_167152_));
            return this;
        }

        @Override
        public VertexConsumer m_5601_(float p_167147_, float p_167148_, float p_167149_) {
            this.m_167144_(p_167121_ -> p_167121_.m_5601_(p_167147_, p_167148_, p_167149_));
            return this;
        }

        @Override
        public void m_5954_(float p_167087_, float p_167088_, float p_167089_, float p_167090_, float p_167091_, float p_167092_, float p_167093_, float p_167094_, float p_167095_, int p_167096_, int p_167097_, float p_167098_, float p_167099_, float p_167100_) {
            this.m_167144_(p_167116_ -> p_167116_.m_5954_(p_167087_, p_167088_, p_167089_, p_167090_, p_167091_, p_167092_, p_167093_, p_167094_, p_167095_, p_167096_, p_167097_, p_167098_, p_167099_, p_167100_));
        }

        @Override
        public void m_5752_() {
            this.m_167144_(VertexConsumer::m_5752_);
        }

        @Override
        public void m_7404_(int p_167154_, int p_167155_, int p_167156_, int p_167157_) {
            this.m_167144_(p_167139_ -> p_167139_.m_7404_(p_167154_, p_167155_, p_167156_, p_167157_));
        }

        @Override
        public void m_141991_() {
            this.m_167144_(VertexConsumer::m_141991_);
        }
    }
}

