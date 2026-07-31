/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.advancements;

public final class AdvancementWidgetType
extends Enum<AdvancementWidgetType> {
    public static final /* enum */ AdvancementWidgetType OBTAINED = new AdvancementWidgetType(0);
    public static final /* enum */ AdvancementWidgetType UNOBTAINED = new AdvancementWidgetType(1);
    private final int f_97318_;
    private static final /* synthetic */ AdvancementWidgetType[] $VALUES;

    public static AdvancementWidgetType[] values() {
        return (AdvancementWidgetType[])$VALUES.clone();
    }

    public static AdvancementWidgetType valueOf(String p_97327_) {
        return Enum.valueOf(AdvancementWidgetType.class, p_97327_);
    }

    private AdvancementWidgetType(int p_97324_) {
        this.f_97318_ = p_97324_;
    }

    public int m_97325_() {
        return this.f_97318_;
    }

    private static /* synthetic */ AdvancementWidgetType[] m_169555_() {
        return new AdvancementWidgetType[]{OBTAINED, UNOBTAINED};
    }

    static {
        $VALUES = AdvancementWidgetType.m_169555_();
    }
}

