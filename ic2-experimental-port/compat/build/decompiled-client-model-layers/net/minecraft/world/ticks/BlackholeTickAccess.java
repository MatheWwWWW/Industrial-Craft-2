/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.ticks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickContainerAccess;

public class BlackholeTickAccess {
    private static final TickContainerAccess<Object> f_193140_ = new TickContainerAccess<Object>(){

        @Override
        public void m_183393_(ScheduledTick<Object> p_193149_) {
        }

        @Override
        public boolean m_183582_(BlockPos p_193151_, Object p_193152_) {
            return false;
        }

        @Override
        public int m_183574_() {
            return 0;
        }
    };
    private static final LevelTickAccess<Object> f_193141_ = new LevelTickAccess<Object>(){

        @Override
        public void m_183393_(ScheduledTick<Object> p_193156_) {
        }

        @Override
        public boolean m_183582_(BlockPos p_193158_, Object p_193159_) {
            return false;
        }

        @Override
        public boolean m_183588_(BlockPos p_193161_, Object p_193162_) {
            return false;
        }

        @Override
        public int m_183574_() {
            return 0;
        }
    };

    public static <T> TickContainerAccess<T> m_193144_() {
        return f_193140_;
    }

    public static <T> LevelTickAccess<T> m_193145_() {
        return f_193141_;
    }
}

