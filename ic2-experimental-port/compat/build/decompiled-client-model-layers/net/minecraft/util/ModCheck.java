/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ObjectUtils
 */
package net.minecraft.util;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;

public record ModCheck(Confidence f_184592_, String f_184593_) {
    public static ModCheck m_184600_(String p_184601_, Supplier<String> p_184602_, String p_184603_, Class<?> p_184604_) {
        String $$4 = p_184602_.get();
        if (!p_184601_.equals($$4)) {
            return new ModCheck(Confidence.DEFINITELY, p_184603_ + " brand changed to '" + $$4 + "'");
        }
        if (p_184604_.getSigners() == null) {
            return new ModCheck(Confidence.VERY_LIKELY, p_184603_ + " jar signature invalidated");
        }
        return new ModCheck(Confidence.PROBABLY_NOT, p_184603_ + " jar signature and brand is untouched");
    }

    public boolean m_184597_() {
        return this.f_184592_.f_184616_;
    }

    public ModCheck m_184598_(ModCheck p_184599_) {
        return new ModCheck((Confidence)((Object)ObjectUtils.max((Comparable[])new Confidence[]{this.f_184592_, p_184599_.f_184592_})), this.f_184593_ + "; " + p_184599_.f_184593_);
    }

    public String m_184605_() {
        return this.f_184592_.f_184615_ + " " + this.f_184593_;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ModCheck.class, "confidence;description", "f_184592_", "f_184593_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ModCheck.class, "confidence;description", "f_184592_", "f_184593_"}, this);
    }

    @Override
    public final boolean equals(Object p_184609_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ModCheck.class, "confidence;description", "f_184592_", "f_184593_"}, this, p_184609_);
    }

    public static final class Confidence
    extends Enum<Confidence> {
        public static final /* enum */ Confidence PROBABLY_NOT = new Confidence("Probably not.", false);
        public static final /* enum */ Confidence VERY_LIKELY = new Confidence("Very likely;", true);
        public static final /* enum */ Confidence DEFINITELY = new Confidence("Definitely;", true);
        final String f_184615_;
        final boolean f_184616_;
        private static final /* synthetic */ Confidence[] $VALUES;

        public static Confidence[] values() {
            return (Confidence[])$VALUES.clone();
        }

        public static Confidence valueOf(String p_184626_) {
            return Enum.valueOf(Confidence.class, p_184626_);
        }

        private Confidence(String p_184622_, boolean p_184623_) {
            this.f_184615_ = p_184622_;
            this.f_184616_ = p_184623_;
        }

        private static /* synthetic */ Confidence[] m_184624_() {
            return new Confidence[]{PROBABLY_NOT, VERY_LIKELY, DEFINITELY};
        }

        static {
            $VALUES = Confidence.m_184624_();
        }
    }
}

