/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

public interface TooltipFlag {
    public boolean m_7050_();

    public static final class Default
    extends Enum<Default>
    implements TooltipFlag {
        public static final /* enum */ Default NORMAL = new Default(false);
        public static final /* enum */ Default ADVANCED = new Default(true);
        private final boolean f_43368_;
        private static final /* synthetic */ Default[] $VALUES;

        public static Default[] values() {
            return (Default[])$VALUES.clone();
        }

        public static Default valueOf(String p_43377_) {
            return Enum.valueOf(Default.class, p_43377_);
        }

        private Default(boolean p_43374_) {
            this.f_43368_ = p_43374_;
        }

        @Override
        public boolean m_7050_() {
            return this.f_43368_;
        }

        private static /* synthetic */ Default[] m_151229_() {
            return new Default[]{NORMAL, ADVANCED};
        }

        static {
            $VALUES = Default.m_151229_();
        }
    }
}

