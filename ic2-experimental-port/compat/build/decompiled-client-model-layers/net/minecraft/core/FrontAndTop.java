/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 */
package net.minecraft.core;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public final class FrontAndTop
extends Enum<FrontAndTop>
implements StringRepresentable {
    public static final /* enum */ FrontAndTop DOWN_EAST = new FrontAndTop("down_east", Direction.DOWN, Direction.EAST);
    public static final /* enum */ FrontAndTop DOWN_NORTH = new FrontAndTop("down_north", Direction.DOWN, Direction.NORTH);
    public static final /* enum */ FrontAndTop DOWN_SOUTH = new FrontAndTop("down_south", Direction.DOWN, Direction.SOUTH);
    public static final /* enum */ FrontAndTop DOWN_WEST = new FrontAndTop("down_west", Direction.DOWN, Direction.WEST);
    public static final /* enum */ FrontAndTop UP_EAST = new FrontAndTop("up_east", Direction.UP, Direction.EAST);
    public static final /* enum */ FrontAndTop UP_NORTH = new FrontAndTop("up_north", Direction.UP, Direction.NORTH);
    public static final /* enum */ FrontAndTop UP_SOUTH = new FrontAndTop("up_south", Direction.UP, Direction.SOUTH);
    public static final /* enum */ FrontAndTop UP_WEST = new FrontAndTop("up_west", Direction.UP, Direction.WEST);
    public static final /* enum */ FrontAndTop WEST_UP = new FrontAndTop("west_up", Direction.WEST, Direction.UP);
    public static final /* enum */ FrontAndTop EAST_UP = new FrontAndTop("east_up", Direction.EAST, Direction.UP);
    public static final /* enum */ FrontAndTop NORTH_UP = new FrontAndTop("north_up", Direction.NORTH, Direction.UP);
    public static final /* enum */ FrontAndTop SOUTH_UP = new FrontAndTop("south_up", Direction.SOUTH, Direction.UP);
    private static final Int2ObjectMap<FrontAndTop> f_122609_;
    private final String f_122610_;
    private final Direction f_122611_;
    private final Direction f_122612_;
    private static final /* synthetic */ FrontAndTop[] $VALUES;

    public static FrontAndTop[] values() {
        return (FrontAndTop[])$VALUES.clone();
    }

    public static FrontAndTop valueOf(String p_122631_) {
        return Enum.valueOf(FrontAndTop.class, p_122631_);
    }

    private static int m_122626_(Direction p_122627_, Direction p_122628_) {
        return p_122628_.ordinal() << 3 | p_122627_.ordinal();
    }

    private FrontAndTop(String p_122618_, Direction p_122619_, Direction p_122620_) {
        this.f_122610_ = p_122618_;
        this.f_122612_ = p_122619_;
        this.f_122611_ = p_122620_;
    }

    @Override
    public String m_7912_() {
        return this.f_122610_;
    }

    public static FrontAndTop m_122622_(Direction p_122623_, Direction p_122624_) {
        int $$2 = FrontAndTop.m_122626_(p_122623_, p_122624_);
        return (FrontAndTop)f_122609_.get($$2);
    }

    public Direction m_122625_() {
        return this.f_122612_;
    }

    public Direction m_122629_() {
        return this.f_122611_;
    }

    private static /* synthetic */ FrontAndTop[] m_175378_() {
        return new FrontAndTop[]{DOWN_EAST, DOWN_NORTH, DOWN_SOUTH, DOWN_WEST, UP_EAST, UP_NORTH, UP_SOUTH, UP_WEST, WEST_UP, EAST_UP, NORTH_UP, SOUTH_UP};
    }

    static {
        $VALUES = FrontAndTop.m_175378_();
        f_122609_ = (Int2ObjectMap)Util.m_137469_(new Int2ObjectOpenHashMap(FrontAndTop.values().length), p_175377_ -> {
            for (FrontAndTop $$1 : FrontAndTop.values()) {
                p_175377_.put(FrontAndTop.m_122626_($$1.f_122612_, $$1.f_122611_), (Object)$$1);
            }
        });
    }
}

