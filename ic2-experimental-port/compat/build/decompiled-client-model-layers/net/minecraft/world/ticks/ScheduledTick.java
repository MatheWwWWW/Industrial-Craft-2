/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  javax.annotation.Nullable
 */
package net.minecraft.world.ticks;

import it.unimi.dsi.fastutil.Hash;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ticks.TickPriority;

public record ScheduledTick<T>(T f_193376_, BlockPos f_193377_, long f_193378_, TickPriority f_193379_, long f_193380_) {
    public static final Comparator<ScheduledTick<?>> f_193373_ = (p_193406_, p_193407_) -> {
        int $$2 = Long.compare(p_193406_.f_193378_, p_193407_.f_193378_);
        if ($$2 != 0) {
            return $$2;
        }
        $$2 = p_193406_.f_193379_.compareTo(p_193407_.f_193379_);
        if ($$2 != 0) {
            return $$2;
        }
        return Long.compare(p_193406_.f_193380_, p_193407_.f_193380_);
    };
    public static final Comparator<ScheduledTick<?>> f_193374_ = (p_193395_, p_193396_) -> {
        int $$2 = p_193395_.f_193379_.compareTo(p_193396_.f_193379_);
        if ($$2 != 0) {
            return $$2;
        }
        return Long.compare(p_193395_.f_193380_, p_193396_.f_193380_);
    };
    public static final Hash.Strategy<ScheduledTick<?>> f_193375_ = new Hash.Strategy<ScheduledTick<?>>(){

        public int hashCode(ScheduledTick<?> p_193417_) {
            return 31 * p_193417_.f_193377_().hashCode() + p_193417_.f_193376_().hashCode();
        }

        public boolean equals(@Nullable ScheduledTick<?> p_193419_, @Nullable ScheduledTick<?> p_193420_) {
            if (p_193419_ == p_193420_) {
                return true;
            }
            if (p_193419_ == null || p_193420_ == null) {
                return false;
            }
            return p_193419_.f_193376_() == p_193420_.f_193376_() && p_193419_.f_193377_().equals(p_193420_.f_193377_());
        }

        public /* synthetic */ boolean equals(@Nullable Object object, @Nullable Object object2) {
            return this.equals((ScheduledTick)object, (ScheduledTick)object2);
        }

        public /* synthetic */ int hashCode(Object object) {
            return this.hashCode((ScheduledTick)object);
        }
    };

    public ScheduledTick(T p_193383_, BlockPos p_193384_, long p_193385_, long p_193386_) {
        this(p_193383_, p_193384_, p_193385_, TickPriority.NORMAL, p_193386_);
    }

    public ScheduledTick {
        f_193377_ = f_193377_.m_7949_();
    }

    public static <T> ScheduledTick<T> m_193397_(T p_193398_, BlockPos p_193399_) {
        return new ScheduledTick<T>(p_193398_, p_193399_, 0L, TickPriority.NORMAL, 0L);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ScheduledTick.class, "type;pos;triggerTick;priority;subTickOrder", "f_193376_", "f_193377_", "f_193378_", "f_193379_", "f_193380_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ScheduledTick.class, "type;pos;triggerTick;priority;subTickOrder", "f_193376_", "f_193377_", "f_193378_", "f_193379_", "f_193380_"}, this);
    }

    @Override
    public final boolean equals(Object p_193412_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ScheduledTick.class, "type;pos;triggerTick;priority;subTickOrder", "f_193376_", "f_193377_", "f_193378_", "f_193379_", "f_193380_"}, this, p_193412_);
    }
}

