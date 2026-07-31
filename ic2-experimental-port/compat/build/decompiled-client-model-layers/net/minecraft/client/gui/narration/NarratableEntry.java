/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.narration;

import net.minecraft.client.gui.narration.NarrationSupplier;

public interface NarratableEntry
extends NarrationSupplier {
    public NarrationPriority m_142684_();

    default public boolean m_142518_() {
        return true;
    }

    public static final class NarrationPriority
    extends Enum<NarrationPriority> {
        public static final /* enum */ NarrationPriority NONE = new NarrationPriority();
        public static final /* enum */ NarrationPriority HOVERED = new NarrationPriority();
        public static final /* enum */ NarrationPriority FOCUSED = new NarrationPriority();
        private static final /* synthetic */ NarrationPriority[] $VALUES;

        public static NarrationPriority[] values() {
            return (NarrationPriority[])$VALUES.clone();
        }

        public static NarrationPriority valueOf(String p_169126_) {
            return Enum.valueOf(NarrationPriority.class, p_169126_);
        }

        public boolean m_169123_() {
            return this == FOCUSED;
        }

        private static /* synthetic */ NarrationPriority[] m_169124_() {
            return new NarrationPriority[]{NONE, HOVERED, FOCUSED};
        }

        static {
            $VALUES = NarrationPriority.m_169124_();
        }
    }
}

