/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

public final class MobSpawnType
extends Enum<MobSpawnType> {
    public static final /* enum */ MobSpawnType NATURAL = new MobSpawnType();
    public static final /* enum */ MobSpawnType CHUNK_GENERATION = new MobSpawnType();
    public static final /* enum */ MobSpawnType SPAWNER = new MobSpawnType();
    public static final /* enum */ MobSpawnType STRUCTURE = new MobSpawnType();
    public static final /* enum */ MobSpawnType BREEDING = new MobSpawnType();
    public static final /* enum */ MobSpawnType MOB_SUMMONED = new MobSpawnType();
    public static final /* enum */ MobSpawnType JOCKEY = new MobSpawnType();
    public static final /* enum */ MobSpawnType EVENT = new MobSpawnType();
    public static final /* enum */ MobSpawnType CONVERSION = new MobSpawnType();
    public static final /* enum */ MobSpawnType REINFORCEMENT = new MobSpawnType();
    public static final /* enum */ MobSpawnType TRIGGERED = new MobSpawnType();
    public static final /* enum */ MobSpawnType BUCKET = new MobSpawnType();
    public static final /* enum */ MobSpawnType SPAWN_EGG = new MobSpawnType();
    public static final /* enum */ MobSpawnType COMMAND = new MobSpawnType();
    public static final /* enum */ MobSpawnType DISPENSER = new MobSpawnType();
    public static final /* enum */ MobSpawnType PATROL = new MobSpawnType();
    private static final /* synthetic */ MobSpawnType[] $VALUES;

    public static MobSpawnType[] values() {
        return (MobSpawnType[])$VALUES.clone();
    }

    public static MobSpawnType valueOf(String p_21638_) {
        return Enum.valueOf(MobSpawnType.class, p_21638_);
    }

    private static /* synthetic */ MobSpawnType[] m_147276_() {
        return new MobSpawnType[]{NATURAL, CHUNK_GENERATION, SPAWNER, STRUCTURE, BREEDING, MOB_SUMMONED, JOCKEY, EVENT, CONVERSION, REINFORCEMENT, TRIGGERED, BUCKET, SPAWN_EGG, COMMAND, DISPENSER, PATROL};
    }

    static {
        $VALUES = MobSpawnType.m_147276_();
    }
}

