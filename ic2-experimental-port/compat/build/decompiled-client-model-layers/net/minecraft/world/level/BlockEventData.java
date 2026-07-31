/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public record BlockEventData(BlockPos f_45529_, Block f_45530_, int f_45531_, int f_45532_) {
    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{BlockEventData.class, "pos;block;paramA;paramB", "f_45529_", "f_45530_", "f_45531_", "f_45532_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BlockEventData.class, "pos;block;paramA;paramB", "f_45529_", "f_45530_", "f_45531_", "f_45532_"}, this);
    }

    @Override
    public final boolean equals(Object p_45543_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BlockEventData.class, "pos;block;paramA;paramB", "f_45529_", "f_45530_", "f_45531_", "f_45532_"}, this, p_45543_);
    }
}

