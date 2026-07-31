/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.gossip;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public final class GossipType
extends Enum<GossipType> {
    public static final /* enum */ GossipType MAJOR_NEGATIVE = new GossipType("major_negative", -5, 100, 10, 10);
    public static final /* enum */ GossipType MINOR_NEGATIVE = new GossipType("minor_negative", -1, 200, 20, 20);
    public static final /* enum */ GossipType MINOR_POSITIVE = new GossipType("minor_positive", 1, 200, 1, 5);
    public static final /* enum */ GossipType MAJOR_POSITIVE = new GossipType("major_positive", 5, 100, 0, 100);
    public static final /* enum */ GossipType TRADING = new GossipType("trading", 1, 25, 2, 20);
    public static final int f_148182_ = 25;
    public static final int f_148183_ = 20;
    public static final int f_148184_ = 2;
    public final String f_26273_;
    public final int f_26274_;
    public final int f_26275_;
    public final int f_26276_;
    public final int f_26277_;
    private static final Map<String, GossipType> f_26278_;
    private static final /* synthetic */ GossipType[] $VALUES;

    public static GossipType[] values() {
        return (GossipType[])$VALUES.clone();
    }

    public static GossipType valueOf(String p_26294_) {
        return Enum.valueOf(GossipType.class, p_26294_);
    }

    private GossipType(String p_26284_, int p_26285_, int p_26286_, int p_26287_, int p_26288_) {
        this.f_26273_ = p_26284_;
        this.f_26274_ = p_26285_;
        this.f_26275_ = p_26286_;
        this.f_26276_ = p_26287_;
        this.f_26277_ = p_26288_;
    }

    @Nullable
    public static GossipType m_26291_(String p_26292_) {
        return f_26278_.get(p_26292_);
    }

    private static /* synthetic */ GossipType[] m_148185_() {
        return new GossipType[]{MAJOR_NEGATIVE, MINOR_NEGATIVE, MINOR_POSITIVE, MAJOR_POSITIVE, TRADING};
    }

    static {
        $VALUES = GossipType.m_148185_();
        f_26278_ = (Map)Stream.of(GossipType.values()).collect(ImmutableMap.toImmutableMap(p_26290_ -> p_26290_.f_26273_, Function.identity()));
    }
}

