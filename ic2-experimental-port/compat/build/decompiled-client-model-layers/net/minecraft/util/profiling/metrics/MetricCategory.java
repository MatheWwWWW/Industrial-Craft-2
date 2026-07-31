/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.metrics;

public final class MetricCategory
extends Enum<MetricCategory> {
    public static final /* enum */ MetricCategory PATH_FINDING = new MetricCategory("pathfinding");
    public static final /* enum */ MetricCategory EVENT_LOOPS = new MetricCategory("event-loops");
    public static final /* enum */ MetricCategory MAIL_BOXES = new MetricCategory("mailboxes");
    public static final /* enum */ MetricCategory TICK_LOOP = new MetricCategory("ticking");
    public static final /* enum */ MetricCategory JVM = new MetricCategory("jvm");
    public static final /* enum */ MetricCategory CHUNK_RENDERING = new MetricCategory("chunk rendering");
    public static final /* enum */ MetricCategory CHUNK_RENDERING_DISPATCHING = new MetricCategory("chunk rendering dispatching");
    public static final /* enum */ MetricCategory CPU = new MetricCategory("cpu");
    public static final /* enum */ MetricCategory GPU = new MetricCategory("gpu");
    private final String f_145974_;
    private static final /* synthetic */ MetricCategory[] $VALUES;

    public static MetricCategory[] values() {
        return (MetricCategory[])$VALUES.clone();
    }

    public static MetricCategory valueOf(String p_145984_) {
        return Enum.valueOf(MetricCategory.class, p_145984_);
    }

    private MetricCategory(String p_145980_) {
        this.f_145974_ = p_145980_;
    }

    public String m_145981_() {
        return this.f_145974_;
    }

    private static /* synthetic */ MetricCategory[] m_145982_() {
        return new MetricCategory[]{PATH_FINDING, EVENT_LOOPS, MAIL_BOXES, TICK_LOOP, JVM, CHUNK_RENDERING, CHUNK_RENDERING_DISPATCHING, CPU, GPU};
    }

    static {
        $VALUES = MetricCategory.m_145982_();
    }
}

