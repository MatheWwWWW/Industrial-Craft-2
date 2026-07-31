/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.storage.LevelData
 */
package ic2.core.platform.wind.impl;

import ic2.core.IC2;
import ic2.core.platform.wind.impl.RealisticWindCalculation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;

public class OverworldCalculation
extends RealisticWindCalculation {
    public OverworldCalculation(Level world) {
        int m = IC2.CONFIG.windStreams.get();
        for (int i = 0; i <= m; ++i) {
            this.streams.add(new WindStream(world));
        }
    }

    public static class WindStream
    extends RealisticWindCalculation.HorizontalStream {
        int ticksLeft;

        public WindStream(Level world) {
            LevelData info = world.m_6106_();
            RandomSource rand = world.f_46441_;
            this.rotation.set(rand.m_188503_(360));
            this.yLevel.set(Math.min(32 + rand.m_188503_(world.m_151558_()), world.m_151558_()));
            this.thickness.set(Math.max(Math.min(rand.m_188503_(8) - (rand.m_188503_(14) - rand.m_188503_(5)), 7), -7));
            this.ticksLeft = rand.m_188503_(IC2.CONFIG.windChangeTime.get());
            float newSpeed = rand.m_188503_(109);
            if (info.m_6534_()) {
                newSpeed = (float)((double)newSpeed * 1.5);
            } else if (info.m_6533_()) {
                newSpeed = (float)((double)newSpeed * 1.25);
            }
            this.speed.set(newSpeed);
        }

        @Override
        public void onUpdate(Level world) {
            super.onUpdate(world);
            if (this.ticksLeft-- <= 0) {
                LevelData info = world.m_6106_();
                RandomSource rand = world.f_46441_;
                this.rotation.setNext(rand.m_188503_(360));
                this.yLevel.setNext(Math.min(32 + rand.m_188503_(world.m_151558_()), world.m_151558_()));
                this.thickness.setNext(Math.max(Math.min(rand.m_188503_(8) - (rand.m_188503_(14) - rand.m_188503_(5)), 7), -7));
                this.ticksLeft = rand.m_188503_(IC2.CONFIG.windChangeTime.get());
                float newSpeed = rand.m_188503_(109);
                if (info.m_6534_()) {
                    newSpeed = (float)((double)newSpeed * 1.5);
                } else if (info.m_6533_()) {
                    newSpeed = (float)((double)newSpeed * 1.25);
                }
                this.speed.setNext(newSpeed);
            }
        }
    }
}

