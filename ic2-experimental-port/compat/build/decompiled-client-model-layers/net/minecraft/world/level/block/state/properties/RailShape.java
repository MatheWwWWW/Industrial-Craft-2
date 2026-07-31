/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class RailShape
extends Enum<RailShape>
implements StringRepresentable {
    public static final /* enum */ RailShape NORTH_SOUTH = new RailShape("north_south");
    public static final /* enum */ RailShape EAST_WEST = new RailShape("east_west");
    public static final /* enum */ RailShape ASCENDING_EAST = new RailShape("ascending_east");
    public static final /* enum */ RailShape ASCENDING_WEST = new RailShape("ascending_west");
    public static final /* enum */ RailShape ASCENDING_NORTH = new RailShape("ascending_north");
    public static final /* enum */ RailShape ASCENDING_SOUTH = new RailShape("ascending_south");
    public static final /* enum */ RailShape SOUTH_EAST = new RailShape("south_east");
    public static final /* enum */ RailShape SOUTH_WEST = new RailShape("south_west");
    public static final /* enum */ RailShape NORTH_WEST = new RailShape("north_west");
    public static final /* enum */ RailShape NORTH_EAST = new RailShape("north_east");
    private final String f_61737_;
    private static final /* synthetic */ RailShape[] $VALUES;

    public static RailShape[] values() {
        return (RailShape[])$VALUES.clone();
    }

    public static RailShape valueOf(String p_61748_) {
        return Enum.valueOf(RailShape.class, p_61748_);
    }

    private RailShape(String p_61743_) {
        this.f_61737_ = p_61743_;
    }

    public String m_156038_() {
        return this.f_61737_;
    }

    public String toString() {
        return this.f_61737_;
    }

    public boolean m_61745_() {
        return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
    }

    @Override
    public String m_7912_() {
        return this.f_61737_;
    }

    private static /* synthetic */ RailShape[] m_156039_() {
        return new RailShape[]{NORTH_SOUTH, EAST_WEST, ASCENDING_EAST, ASCENDING_WEST, ASCENDING_NORTH, ASCENDING_SOUTH, SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST};
    }

    static {
        $VALUES = RailShape.m_156039_();
    }
}

