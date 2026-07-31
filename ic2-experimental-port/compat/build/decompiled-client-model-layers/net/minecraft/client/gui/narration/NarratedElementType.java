/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.narration;

public final class NarratedElementType
extends Enum<NarratedElementType> {
    public static final /* enum */ NarratedElementType TITLE = new NarratedElementType();
    public static final /* enum */ NarratedElementType POSITION = new NarratedElementType();
    public static final /* enum */ NarratedElementType HINT = new NarratedElementType();
    public static final /* enum */ NarratedElementType USAGE = new NarratedElementType();
    private static final /* synthetic */ NarratedElementType[] $VALUES;

    public static NarratedElementType[] values() {
        return (NarratedElementType[])$VALUES.clone();
    }

    public static NarratedElementType valueOf(String p_169139_) {
        return Enum.valueOf(NarratedElementType.class, p_169139_);
    }

    private static /* synthetic */ NarratedElementType[] m_169137_() {
        return new NarratedElementType[]{TITLE, POSITION, HINT, USAGE};
    }

    static {
        $VALUES = NarratedElementType.m_169137_();
    }
}

