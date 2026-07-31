/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

public final class CameraType
extends Enum<CameraType> {
    public static final /* enum */ CameraType FIRST_PERSON = new CameraType(true, false);
    public static final /* enum */ CameraType THIRD_PERSON_BACK = new CameraType(false, false);
    public static final /* enum */ CameraType THIRD_PERSON_FRONT = new CameraType(false, true);
    private static final CameraType[] f_90602_;
    private final boolean f_90603_;
    private final boolean f_90604_;
    private static final /* synthetic */ CameraType[] $VALUES;

    public static CameraType[] values() {
        return (CameraType[])$VALUES.clone();
    }

    public static CameraType valueOf(String p_90616_) {
        return Enum.valueOf(CameraType.class, p_90616_);
    }

    private CameraType(boolean p_90610_, boolean p_90611_) {
        this.f_90603_ = p_90610_;
        this.f_90604_ = p_90611_;
    }

    public boolean m_90612_() {
        return this.f_90603_;
    }

    public boolean m_90613_() {
        return this.f_90604_;
    }

    public CameraType m_90614_() {
        return f_90602_[(this.ordinal() + 1) % f_90602_.length];
    }

    private static /* synthetic */ CameraType[] m_167703_() {
        return new CameraType[]{FIRST_PERSON, THIRD_PERSON_BACK, THIRD_PERSON_FRONT};
    }

    static {
        $VALUES = CameraType.m_167703_();
        f_90602_ = CameraType.values();
    }
}

