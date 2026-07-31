/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

public final class Pose
extends Enum<Pose> {
    public static final /* enum */ Pose STANDING = new Pose();
    public static final /* enum */ Pose FALL_FLYING = new Pose();
    public static final /* enum */ Pose SLEEPING = new Pose();
    public static final /* enum */ Pose SWIMMING = new Pose();
    public static final /* enum */ Pose SPIN_ATTACK = new Pose();
    public static final /* enum */ Pose CROUCHING = new Pose();
    public static final /* enum */ Pose LONG_JUMPING = new Pose();
    public static final /* enum */ Pose DYING = new Pose();
    public static final /* enum */ Pose CROAKING = new Pose();
    public static final /* enum */ Pose USING_TONGUE = new Pose();
    public static final /* enum */ Pose ROARING = new Pose();
    public static final /* enum */ Pose SNIFFING = new Pose();
    public static final /* enum */ Pose EMERGING = new Pose();
    public static final /* enum */ Pose DIGGING = new Pose();
    private static final /* synthetic */ Pose[] $VALUES;

    public static Pose[] values() {
        return (Pose[])$VALUES.clone();
    }

    public static Pose valueOf(String p_21710_) {
        return Enum.valueOf(Pose.class, p_21710_);
    }

    private static /* synthetic */ Pose[] m_147289_() {
        return new Pose[]{STANDING, FALL_FLYING, SLEEPING, SWIMMING, SPIN_ATTACK, CROUCHING, LONG_JUMPING, DYING, CROAKING, USING_TONGUE, ROARING, SNIFFING, EMERGING, DIGGING};
    }

    static {
        $VALUES = Pose.m_147289_();
    }
}

