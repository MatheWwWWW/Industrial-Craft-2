/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import net.minecraft.util.OptionEnum;

public final class CloudStatus
extends Enum<CloudStatus>
implements OptionEnum {
    public static final /* enum */ CloudStatus OFF = new CloudStatus(0, "options.off");
    public static final /* enum */ CloudStatus FAST = new CloudStatus(1, "options.clouds.fast");
    public static final /* enum */ CloudStatus FANCY = new CloudStatus(2, "options.clouds.fancy");
    private final int f_231330_;
    private final String f_90655_;
    private static final /* synthetic */ CloudStatus[] $VALUES;

    public static CloudStatus[] values() {
        return (CloudStatus[])$VALUES.clone();
    }

    public static CloudStatus valueOf(String p_90670_) {
        return Enum.valueOf(CloudStatus.class, p_90670_);
    }

    private CloudStatus(int p_231334_, String p_231335_) {
        this.f_231330_ = p_231334_;
        this.f_90655_ = p_231335_;
    }

    @Override
    public int m_35965_() {
        return this.f_231330_;
    }

    @Override
    public String m_35968_() {
        return this.f_90655_;
    }

    private static /* synthetic */ CloudStatus[] m_167711_() {
        return new CloudStatus[]{OFF, FAST, FANCY};
    }

    static {
        $VALUES = CloudStatus.m_167711_();
    }
}

