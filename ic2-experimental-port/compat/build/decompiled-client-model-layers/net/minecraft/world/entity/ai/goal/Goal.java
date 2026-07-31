/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.Mth;

public abstract class Goal {
    private final EnumSet<Flag> f_25326_ = EnumSet.noneOf(Flag.class);

    public abstract boolean m_8036_();

    public boolean m_8045_() {
        return this.m_8036_();
    }

    public boolean m_6767_() {
        return true;
    }

    public void m_8056_() {
    }

    public void m_8041_() {
    }

    public boolean m_183429_() {
        return false;
    }

    public void m_8037_() {
    }

    public void m_7021_(EnumSet<Flag> p_25328_) {
        this.f_25326_.clear();
        this.f_25326_.addAll(p_25328_);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public EnumSet<Flag> m_7684_() {
        return this.f_25326_;
    }

    protected int m_183277_(int p_186072_) {
        return this.m_183429_() ? p_186072_ : Goal.m_186073_(p_186072_);
    }

    protected static int m_186073_(int p_186074_) {
        return Mth.m_184652_(p_186074_, 2);
    }

    public static final class Flag
    extends Enum<Flag> {
        public static final /* enum */ Flag MOVE = new Flag();
        public static final /* enum */ Flag LOOK = new Flag();
        public static final /* enum */ Flag JUMP = new Flag();
        public static final /* enum */ Flag TARGET = new Flag();
        private static final /* synthetic */ Flag[] $VALUES;

        public static Flag[] values() {
            return (Flag[])$VALUES.clone();
        }

        public static Flag valueOf(String p_25340_) {
            return Enum.valueOf(Flag.class, p_25340_);
        }

        private static /* synthetic */ Flag[] m_148094_() {
            return new Flag[]{MOVE, LOOK, JUMP, TARGET};
        }

        static {
            $VALUES = Flag.m_148094_();
        }
    }
}

