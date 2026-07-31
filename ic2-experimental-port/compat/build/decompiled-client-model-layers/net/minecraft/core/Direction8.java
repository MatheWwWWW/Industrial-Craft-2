/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.core;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Set;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

public final class Direction8
extends Enum<Direction8> {
    public static final /* enum */ Direction8 NORTH = new Direction8(Direction.NORTH);
    public static final /* enum */ Direction8 NORTH_EAST = new Direction8(Direction.NORTH, Direction.EAST);
    public static final /* enum */ Direction8 EAST = new Direction8(Direction.EAST);
    public static final /* enum */ Direction8 SOUTH_EAST = new Direction8(Direction.SOUTH, Direction.EAST);
    public static final /* enum */ Direction8 SOUTH = new Direction8(Direction.SOUTH);
    public static final /* enum */ Direction8 SOUTH_WEST = new Direction8(Direction.SOUTH, Direction.WEST);
    public static final /* enum */ Direction8 WEST = new Direction8(Direction.WEST);
    public static final /* enum */ Direction8 NORTH_WEST = new Direction8(Direction.NORTH, Direction.WEST);
    private final Set<Direction> f_122586_;
    private final Vec3i f_235696_;
    private static final /* synthetic */ Direction8[] $VALUES;

    public static Direction8[] values() {
        return (Direction8[])$VALUES.clone();
    }

    public static Direction8 valueOf(String p_122595_) {
        return Enum.valueOf(Direction8.class, p_122595_);
    }

    private Direction8(Direction ... p_122592_) {
        this.f_122586_ = Sets.immutableEnumSet(Arrays.asList(p_122592_));
        this.f_235696_ = new Vec3i(0, 0, 0);
        for (Direction $$1 : p_122592_) {
            this.f_235696_.m_142451_(this.f_235696_.m_123341_() + $$1.m_122429_()).m_142448_(this.f_235696_.m_123342_() + $$1.m_122430_()).m_142443_(this.f_235696_.m_123343_() + $$1.m_122431_());
        }
    }

    public Set<Direction> m_122593_() {
        return this.f_122586_;
    }

    public int m_235697_() {
        return this.f_235696_.m_123341_();
    }

    public int m_235698_() {
        return this.f_235696_.m_123343_();
    }

    private static /* synthetic */ Direction8[] m_175375_() {
        return new Direction8[]{NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST};
    }

    static {
        $VALUES = Direction8.m_175375_();
    }
}

