/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArraySet
 */
package net.minecraft.world.level.block.state.properties;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;
import java.util.stream.Stream;

public class WoodType {
    private static final Set<WoodType> f_61838_ = new ObjectArraySet();
    public static final WoodType f_61830_ = WoodType.m_61844_(new WoodType("oak"));
    public static final WoodType f_61831_ = WoodType.m_61844_(new WoodType("spruce"));
    public static final WoodType f_61832_ = WoodType.m_61844_(new WoodType("birch"));
    public static final WoodType f_61833_ = WoodType.m_61844_(new WoodType("acacia"));
    public static final WoodType f_61834_ = WoodType.m_61844_(new WoodType("jungle"));
    public static final WoodType f_61835_ = WoodType.m_61844_(new WoodType("dark_oak"));
    public static final WoodType f_61836_ = WoodType.m_61844_(new WoodType("crimson"));
    public static final WoodType f_61837_ = WoodType.m_61844_(new WoodType("warped"));
    public static final WoodType f_223002_ = WoodType.m_61844_(new WoodType("mangrove"));
    private final String f_61839_;

    protected WoodType(String p_61842_) {
        this.f_61839_ = p_61842_;
    }

    private static WoodType m_61844_(WoodType p_61845_) {
        f_61838_.add(p_61845_);
        return p_61845_;
    }

    public static Stream<WoodType> m_61843_() {
        return f_61838_.stream();
    }

    public String m_61846_() {
        return this.f_61839_;
    }
}

