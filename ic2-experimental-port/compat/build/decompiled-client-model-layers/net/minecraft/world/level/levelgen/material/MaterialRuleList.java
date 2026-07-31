/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.material;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;

public record MaterialRuleList(List<NoiseChunk.BlockStateFiller> f_191545_) implements NoiseChunk.BlockStateFiller
{
    @Override
    @Nullable
    public BlockState m_207387_(DensityFunction.FunctionContext p_209815_) {
        for (NoiseChunk.BlockStateFiller $$1 : this.f_191545_) {
            BlockState $$2 = $$1.m_207387_(p_209815_);
            if ($$2 == null) continue;
            return $$2;
        }
        return null;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{MaterialRuleList.class, "materialRuleList", "f_191545_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{MaterialRuleList.class, "materialRuleList", "f_191545_"}, this);
    }

    @Override
    public final boolean equals(Object p_209817_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{MaterialRuleList.class, "materialRuleList", "f_191545_"}, this, p_209817_);
    }
}

