/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  it.unimi.dsi.fastutil.ints.Int2DoubleMap
 *  it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.util.profiling.metrics;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.metrics.MetricCategory;

public class MetricSampler {
    private final String f_145987_;
    private final MetricCategory f_145988_;
    private final DoubleSupplier f_145989_;
    private final ByteBuf f_145990_;
    private final ByteBuf f_145991_;
    private volatile boolean f_145992_;
    @Nullable
    private final Runnable f_145993_;
    @Nullable
    final ThresholdTest f_145986_;
    private double f_145994_;

    protected MetricSampler(String p_145996_, MetricCategory p_145997_, DoubleSupplier p_145998_, @Nullable Runnable p_145999_, @Nullable ThresholdTest p_146000_) {
        this.f_145987_ = p_145996_;
        this.f_145988_ = p_145997_;
        this.f_145993_ = p_145999_;
        this.f_145989_ = p_145998_;
        this.f_145986_ = p_146000_;
        this.f_145991_ = ByteBufAllocator.DEFAULT.buffer();
        this.f_145990_ = ByteBufAllocator.DEFAULT.buffer();
        this.f_145992_ = true;
    }

    public static MetricSampler m_146009_(String p_146010_, MetricCategory p_146011_, DoubleSupplier p_146012_) {
        return new MetricSampler(p_146010_, p_146011_, p_146012_, null, null);
    }

    public static <T> MetricSampler m_146004_(String p_146005_, MetricCategory p_146006_, T p_146007_, ToDoubleFunction<T> p_146008_) {
        return MetricSampler.m_146013_(p_146005_, p_146006_, p_146008_, p_146007_).m_146039_();
    }

    public static <T> MetricSamplerBuilder<T> m_146013_(String p_146014_, MetricCategory p_146015_, ToDoubleFunction<T> p_146016_, T p_146017_) {
        return new MetricSamplerBuilder<T>(p_146014_, p_146015_, p_146016_, p_146017_);
    }

    public void m_146001_() {
        if (!this.f_145992_) {
            throw new IllegalStateException("Not running");
        }
        if (this.f_145993_ != null) {
            this.f_145993_.run();
        }
    }

    public void m_146002_(int p_146003_) {
        this.m_146026_();
        this.f_145994_ = this.f_145989_.getAsDouble();
        this.f_145991_.writeDouble(this.f_145994_);
        this.f_145990_.writeInt(p_146003_);
    }

    public void m_146018_() {
        this.m_146026_();
        this.f_145991_.release();
        this.f_145990_.release();
        this.f_145992_ = false;
    }

    private void m_146026_() {
        if (!this.f_145992_) {
            throw new IllegalStateException(String.format(Locale.ROOT, "Sampler for metric %s not started!", this.f_145987_));
        }
    }

    DoubleSupplier m_146019_() {
        return this.f_145989_;
    }

    public String m_146020_() {
        return this.f_145987_;
    }

    public MetricCategory m_146021_() {
        return this.f_145988_;
    }

    public SamplerResult m_146024_() {
        Int2DoubleOpenHashMap $$0 = new Int2DoubleOpenHashMap();
        int $$1 = Integer.MIN_VALUE;
        int $$2 = Integer.MIN_VALUE;
        while (this.f_145991_.isReadable(8)) {
            int $$3 = this.f_145990_.readInt();
            if ($$1 == Integer.MIN_VALUE) {
                $$1 = $$3;
            }
            $$0.put($$3, this.f_145991_.readDouble());
            $$2 = $$3;
        }
        return new SamplerResult($$1, $$2, (Int2DoubleMap)$$0);
    }

    public boolean m_146025_() {
        return this.f_145986_ != null && this.f_145986_.m_142488_(this.f_145994_);
    }

    public boolean equals(Object p_146023_) {
        if (this == p_146023_) {
            return true;
        }
        if (p_146023_ == null || this.getClass() != p_146023_.getClass()) {
            return false;
        }
        MetricSampler $$1 = (MetricSampler)p_146023_;
        return this.f_145987_.equals($$1.f_145987_) && this.f_145988_.equals((Object)$$1.f_145988_);
    }

    public int hashCode() {
        return this.f_145987_.hashCode();
    }

    public static interface ThresholdTest {
        public boolean m_142488_(double var1);
    }

    public static class MetricSamplerBuilder<T> {
        private final String f_146028_;
        private final MetricCategory f_146029_;
        private final DoubleSupplier f_146030_;
        private final T f_146031_;
        @Nullable
        private Runnable f_146032_;
        @Nullable
        private ThresholdTest f_146033_;

        public MetricSamplerBuilder(String p_146035_, MetricCategory p_146036_, ToDoubleFunction<T> p_146037_, T p_146038_) {
            this.f_146028_ = p_146035_;
            this.f_146029_ = p_146036_;
            this.f_146030_ = () -> p_146037_.applyAsDouble(p_146038_);
            this.f_146031_ = p_146038_;
        }

        public MetricSamplerBuilder<T> m_146042_(Consumer<T> p_146043_) {
            this.f_146032_ = () -> p_146043_.accept(this.f_146031_);
            return this;
        }

        public MetricSamplerBuilder<T> m_146040_(ThresholdTest p_146041_) {
            this.f_146033_ = p_146041_;
            return this;
        }

        public MetricSampler m_146039_() {
            return new MetricSampler(this.f_146028_, this.f_146029_, this.f_146030_, this.f_146032_, this.f_146033_);
        }
    }

    public static class SamplerResult {
        private final Int2DoubleMap f_146049_;
        private final int f_146050_;
        private final int f_146051_;

        public SamplerResult(int p_146053_, int p_146054_, Int2DoubleMap p_146055_) {
            this.f_146050_ = p_146053_;
            this.f_146051_ = p_146054_;
            this.f_146049_ = p_146055_;
        }

        public double m_146057_(int p_146058_) {
            return this.f_146049_.get(p_146058_);
        }

        public int m_146056_() {
            return this.f_146050_;
        }

        public int m_146059_() {
            return this.f_146051_;
        }
    }

    public static class ValueIncreasedByPercentage
    implements ThresholdTest {
        private final float f_146061_;
        private double f_146062_ = Double.MIN_VALUE;

        public ValueIncreasedByPercentage(float p_146064_) {
            this.f_146061_ = p_146064_;
        }

        @Override
        public boolean m_142488_(double p_146066_) {
            boolean $$2;
            if (this.f_146062_ == Double.MIN_VALUE || p_146066_ <= this.f_146062_) {
                boolean $$1 = false;
            } else {
                $$2 = (p_146066_ - this.f_146062_) / this.f_146062_ >= (double)this.f_146061_;
            }
            this.f_146062_ = p_146066_;
            return $$2;
        }
    }
}

