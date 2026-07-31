/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.ai.attributes;

import com.mojang.logging.LogUtils;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;

public class AttributeModifier {
    private static final Logger f_22189_ = LogUtils.getLogger();
    private final double f_22190_;
    private final Operation f_22191_;
    private final Supplier<String> f_22192_;
    private final UUID f_22193_;

    public AttributeModifier(String p_22196_, double p_22197_, Operation p_22198_) {
        this(Mth.m_216261_(RandomSource.m_216343_()), () -> p_22196_, p_22197_, p_22198_);
    }

    public AttributeModifier(UUID p_22200_, String p_22201_, double p_22202_, Operation p_22203_) {
        this(p_22200_, () -> p_22201_, p_22202_, p_22203_);
    }

    public AttributeModifier(UUID p_22205_, Supplier<String> p_22206_, double p_22207_, Operation p_22208_) {
        this.f_22193_ = p_22205_;
        this.f_22192_ = p_22206_;
        this.f_22190_ = p_22207_;
        this.f_22191_ = p_22208_;
    }

    public UUID m_22209_() {
        return this.f_22193_;
    }

    public String m_22214_() {
        return this.f_22192_.get();
    }

    public Operation m_22217_() {
        return this.f_22191_;
    }

    public double m_22218_() {
        return this.f_22190_;
    }

    public boolean equals(Object p_22221_) {
        if (this == p_22221_) {
            return true;
        }
        if (p_22221_ == null || this.getClass() != p_22221_.getClass()) {
            return false;
        }
        AttributeModifier $$1 = (AttributeModifier)p_22221_;
        return Objects.equals(this.f_22193_, $$1.f_22193_);
    }

    public int hashCode() {
        return this.f_22193_.hashCode();
    }

    public String toString() {
        return "AttributeModifier{amount=" + this.f_22190_ + ", operation=" + this.f_22191_ + ", name='" + this.f_22192_.get() + "', id=" + this.f_22193_ + "}";
    }

    public CompoundTag m_22219_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128359_("Name", this.m_22214_());
        $$0.m_128347_("Amount", this.f_22190_);
        $$0.m_128405_("Operation", this.f_22191_.m_22235_());
        $$0.m_128362_("UUID", this.f_22193_);
        return $$0;
    }

    @Nullable
    public static AttributeModifier m_22212_(CompoundTag p_22213_) {
        try {
            UUID $$1 = p_22213_.m_128342_("UUID");
            Operation $$2 = Operation.m_22236_(p_22213_.m_128451_("Operation"));
            return new AttributeModifier($$1, p_22213_.m_128461_("Name"), p_22213_.m_128459_("Amount"), $$2);
        }
        catch (Exception $$3) {
            f_22189_.warn("Unable to create attribute: {}", (Object)$$3.getMessage());
            return null;
        }
    }

    public static final class Operation
    extends Enum<Operation> {
        public static final /* enum */ Operation ADDITION = new Operation(0);
        public static final /* enum */ Operation MULTIPLY_BASE = new Operation(1);
        public static final /* enum */ Operation MULTIPLY_TOTAL = new Operation(2);
        private static final Operation[] f_22227_;
        private final int f_22228_;
        private static final /* synthetic */ Operation[] $VALUES;

        public static Operation[] values() {
            return (Operation[])$VALUES.clone();
        }

        public static Operation valueOf(String p_22239_) {
            return Enum.valueOf(Operation.class, p_22239_);
        }

        private Operation(int p_22234_) {
            this.f_22228_ = p_22234_;
        }

        public int m_22235_() {
            return this.f_22228_;
        }

        public static Operation m_22236_(int p_22237_) {
            if (p_22237_ < 0 || p_22237_ >= f_22227_.length) {
                throw new IllegalArgumentException("No operation with value " + p_22237_);
            }
            return f_22227_[p_22237_];
        }

        private static /* synthetic */ Operation[] m_147358_() {
            return new Operation[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
        }

        static {
            $VALUES = Operation.m_147358_();
            f_22227_ = new Operation[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
        }
    }
}

