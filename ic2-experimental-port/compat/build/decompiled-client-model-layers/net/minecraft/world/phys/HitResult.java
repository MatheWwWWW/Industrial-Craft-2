/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public abstract class HitResult {
    protected final Vec3 f_82445_;

    protected HitResult(Vec3 p_82447_) {
        this.f_82445_ = p_82447_;
    }

    public double m_82448_(Entity p_82449_) {
        double $$1 = this.f_82445_.f_82479_ - p_82449_.m_20185_();
        double $$2 = this.f_82445_.f_82480_ - p_82449_.m_20186_();
        double $$3 = this.f_82445_.f_82481_ - p_82449_.m_20189_();
        return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
    }

    public abstract Type m_6662_();

    public Vec3 m_82450_() {
        return this.f_82445_;
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type MISS = new Type();
        public static final /* enum */ Type BLOCK = new Type();
        public static final /* enum */ Type ENTITY = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_82460_) {
            return Enum.valueOf(Type.class, p_82460_);
        }

        private static /* synthetic */ Type[] m_165901_() {
            return new Type[]{MISS, BLOCK, ENTITY};
        }

        static {
            $VALUES = Type.m_165901_();
        }
    }
}

