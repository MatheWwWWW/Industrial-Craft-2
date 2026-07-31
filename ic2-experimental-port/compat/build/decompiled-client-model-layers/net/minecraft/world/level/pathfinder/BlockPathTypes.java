/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.pathfinder;

public final class BlockPathTypes
extends Enum<BlockPathTypes> {
    public static final /* enum */ BlockPathTypes BLOCKED = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes OPEN = new BlockPathTypes(0.0f);
    public static final /* enum */ BlockPathTypes WALKABLE = new BlockPathTypes(0.0f);
    public static final /* enum */ BlockPathTypes WALKABLE_DOOR = new BlockPathTypes(0.0f);
    public static final /* enum */ BlockPathTypes TRAPDOOR = new BlockPathTypes(0.0f);
    public static final /* enum */ BlockPathTypes POWDER_SNOW = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes DANGER_POWDER_SNOW = new BlockPathTypes(0.0f);
    public static final /* enum */ BlockPathTypes FENCE = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes LAVA = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes WATER = new BlockPathTypes(8.0f);
    public static final /* enum */ BlockPathTypes WATER_BORDER = new BlockPathTypes(8.0f);
    public static final /* enum */ BlockPathTypes RAIL = new BlockPathTypes(0.0f);
    public static final /* enum */ BlockPathTypes UNPASSABLE_RAIL = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes DANGER_FIRE = new BlockPathTypes(8.0f);
    public static final /* enum */ BlockPathTypes DAMAGE_FIRE = new BlockPathTypes(16.0f);
    public static final /* enum */ BlockPathTypes DANGER_CACTUS = new BlockPathTypes(8.0f);
    public static final /* enum */ BlockPathTypes DAMAGE_CACTUS = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes DANGER_OTHER = new BlockPathTypes(8.0f);
    public static final /* enum */ BlockPathTypes DAMAGE_OTHER = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes DOOR_OPEN = new BlockPathTypes(0.0f);
    public static final /* enum */ BlockPathTypes DOOR_WOOD_CLOSED = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes DOOR_IRON_CLOSED = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes BREACH = new BlockPathTypes(4.0f);
    public static final /* enum */ BlockPathTypes LEAVES = new BlockPathTypes(-1.0f);
    public static final /* enum */ BlockPathTypes STICKY_HONEY = new BlockPathTypes(8.0f);
    public static final /* enum */ BlockPathTypes COCOA = new BlockPathTypes(0.0f);
    private final float f_77117_;
    private static final /* synthetic */ BlockPathTypes[] $VALUES;

    public static BlockPathTypes[] values() {
        return (BlockPathTypes[])$VALUES.clone();
    }

    public static BlockPathTypes valueOf(String p_77126_) {
        return Enum.valueOf(BlockPathTypes.class, p_77126_);
    }

    private BlockPathTypes(float p_77123_) {
        this.f_77117_ = p_77123_;
    }

    public float m_77124_() {
        return this.f_77117_;
    }

    private static /* synthetic */ BlockPathTypes[] m_164686_() {
        return new BlockPathTypes[]{BLOCKED, OPEN, WALKABLE, WALKABLE_DOOR, TRAPDOOR, POWDER_SNOW, DANGER_POWDER_SNOW, FENCE, LAVA, WATER, WATER_BORDER, RAIL, UNPASSABLE_RAIL, DANGER_FIRE, DAMAGE_FIRE, DANGER_CACTUS, DAMAGE_CACTUS, DANGER_OTHER, DAMAGE_OTHER, DOOR_OPEN, DOOR_WOOD_CLOSED, DOOR_IRON_CLOSED, BREACH, LEAVES, STICKY_HONEY, COCOA};
    }

    static {
        $VALUES = BlockPathTypes.m_164686_();
    }
}

