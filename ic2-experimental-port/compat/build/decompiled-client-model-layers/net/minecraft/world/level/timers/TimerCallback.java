/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.timers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.timers.TimerQueue;

@FunctionalInterface
public interface TimerCallback<T> {
    public void m_5821_(T var1, TimerQueue<T> var2, long var3);

    public static abstract class Serializer<T, C extends TimerCallback<T>> {
        private final ResourceLocation f_82216_;
        private final Class<?> f_82217_;

        public Serializer(ResourceLocation p_82219_, Class<?> p_82220_) {
            this.f_82216_ = p_82219_;
            this.f_82217_ = p_82220_;
        }

        public ResourceLocation m_82221_() {
            return this.f_82216_;
        }

        public Class<?> m_82224_() {
            return this.f_82217_;
        }

        public abstract void m_6585_(CompoundTag var1, C var2);

        public abstract C m_6006_(CompoundTag var1);
    }
}

