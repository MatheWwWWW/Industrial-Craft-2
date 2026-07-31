/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.platform.wind.impl;

import ic2.core.IC2;
import ic2.core.platform.wind.IWindCalculation;
import ic2.core.platform.wind.IWindStream;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SimpleWindCalculation
implements IWindCalculation {
    SimpleStream stream = new SimpleStream();
    Random rand = new Random();
    double current;
    double next;
    int timeToNext;

    public SimpleWindCalculation() {
        this.timeToNext = this.rand.nextInt(IC2.CONFIG.windChangeTime.get());
        this.current = this.next = this.rand.nextDouble() * 200.0 - 100.0;
        this.stream.setSpeed(this.current);
    }

    @Override
    public void update(Level world) {
        if (this.timeToNext-- <= 0) {
            this.timeToNext = this.rand.nextInt(IC2.CONFIG.windChangeTime.get());
            this.next = this.rand.nextDouble() * 200.0 - 100.0;
        }
        if (this.current > this.next) {
            this.current -= 0.1;
        } else if (this.current < this.next) {
            this.current += 0.1;
        }
        this.stream.setSpeed(this.current);
    }

    @Override
    public double getWindSpeed(AABB box, float rotation, float angle) {
        return this.current;
    }

    @Override
    public List<IWindStream> getStreams() {
        return ObjectLists.singleton((Object)this.stream);
    }

    public static class SimpleStream
    implements IWindStream {
        AABB box = new AABB(-2.147483648E9, 0.0, -2.147483648E9, 2.147483647E9, 255.0, 2.147483647E9);
        double speed;

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        @Override
        public void onUpdate(Level world) {
        }

        @Override
        public double getSpeed() {
            return this.speed;
        }

        @Override
        public AABB getAffectedArea() {
            return this.box;
        }

        @Override
        public Tuple<IWindStream.CollisionState, Double> getCollisionState(AABB hitBox, float direction, float angle) {
            return new Tuple((Object)IWindStream.CollisionState.COLLISION, (Object)1.0);
        }

        @Override
        public float getDirection() {
            return 0.0f;
        }

        @Override
        public float getAngle() {
            return 0.0f;
        }
    }
}

