/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.platform.wind;

import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public interface IWindStream {
    public void onUpdate(Level var1);

    public AABB getAffectedArea();

    public Tuple<CollisionState, Double> getCollisionState(AABB var1, float var2, float var3);

    public float getDirection();

    public float getAngle();

    public double getSpeed();

    public static enum CollisionState {
        MISSING,
        COLLISION,
        INVERTED_COLLISION;

    }
}

