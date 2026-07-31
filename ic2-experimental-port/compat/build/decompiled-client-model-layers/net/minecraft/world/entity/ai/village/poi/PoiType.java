/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.village.poi;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.state.BlockState;

public record PoiType(Set<BlockState> f_27325_, int f_27326_, int f_27328_) {
    public static final Predicate<Holder<PoiType>> f_218034_ = p_218041_ -> false;

    public PoiType {
        f_27325_ = Set.copyOf(f_27325_);
    }

    public boolean m_148692_(BlockState p_148693_) {
        return this.f_27325_.contains(p_148693_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{PoiType.class, "matchingStates;maxTickets;validRange", "f_27325_", "f_27326_", "f_27328_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PoiType.class, "matchingStates;maxTickets;validRange", "f_27325_", "f_27326_", "f_27328_"}, this);
    }

    @Override
    public final boolean equals(Object p_218045_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PoiType.class, "matchingStates;maxTickets;validRange", "f_27325_", "f_27326_", "f_27328_"}, this, p_218045_);
    }
}

