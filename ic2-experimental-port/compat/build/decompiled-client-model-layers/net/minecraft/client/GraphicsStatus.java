/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

public final class GraphicsStatus
extends Enum<GraphicsStatus>
implements OptionEnum {
    public static final /* enum */ GraphicsStatus FAST = new GraphicsStatus(0, "options.graphics.fast");
    public static final /* enum */ GraphicsStatus FANCY = new GraphicsStatus(1, "options.graphics.fancy");
    public static final /* enum */ GraphicsStatus FABULOUS = new GraphicsStatus(2, "options.graphics.fabulous");
    private static final GraphicsStatus[] f_90763_;
    private final int f_90764_;
    private final String f_90765_;
    private static final /* synthetic */ GraphicsStatus[] $VALUES;

    public static GraphicsStatus[] values() {
        return (GraphicsStatus[])$VALUES.clone();
    }

    public static GraphicsStatus valueOf(String p_90782_) {
        return Enum.valueOf(GraphicsStatus.class, p_90782_);
    }

    private GraphicsStatus(int p_90771_, String p_90772_) {
        this.f_90764_ = p_90771_;
        this.f_90765_ = p_90772_;
    }

    @Override
    public int m_35965_() {
        return this.f_90764_;
    }

    @Override
    public String m_35968_() {
        return this.f_90765_;
    }

    public String toString() {
        switch (this) {
            case FAST: {
                return "fast";
            }
            case FANCY: {
                return "fancy";
            }
            case FABULOUS: {
                return "fabulous";
            }
        }
        throw new IllegalArgumentException();
    }

    public static GraphicsStatus m_90774_(int p_90775_) {
        return f_90763_[Mth.m_14100_(p_90775_, f_90763_.length)];
    }

    private static /* synthetic */ GraphicsStatus[] m_167803_() {
        return new GraphicsStatus[]{FAST, FANCY, FABULOUS};
    }

    static {
        $VALUES = GraphicsStatus.m_167803_();
        f_90763_ = (GraphicsStatus[])Arrays.stream(GraphicsStatus.values()).sorted(Comparator.comparingInt(GraphicsStatus::m_35965_)).toArray(GraphicsStatus[]::new);
    }
}

