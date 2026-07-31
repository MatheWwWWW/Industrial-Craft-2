/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ContainerLevelAccess {
    public static final ContainerLevelAccess f_39287_ = new ContainerLevelAccess(){

        @Override
        public <T> Optional<T> m_6721_(BiFunction<Level, BlockPos, T> p_39304_) {
            return Optional.empty();
        }
    };

    public static ContainerLevelAccess m_39289_(final Level p_39290_, final BlockPos p_39291_) {
        return new ContainerLevelAccess(){

            @Override
            public <T> Optional<T> m_6721_(BiFunction<Level, BlockPos, T> p_39311_) {
                return Optional.of(p_39311_.apply(p_39290_, p_39291_));
            }
        };
    }

    public <T> Optional<T> m_6721_(BiFunction<Level, BlockPos, T> var1);

    default public <T> T m_39299_(BiFunction<Level, BlockPos, T> p_39300_, T p_39301_) {
        return this.m_6721_(p_39300_).orElse(p_39301_);
    }

    default public void m_39292_(BiConsumer<Level, BlockPos> p_39293_) {
        this.m_6721_((p_39296_, p_39297_) -> {
            p_39293_.accept((Level)p_39296_, (BlockPos)p_39297_);
            return Optional.empty();
        });
    }
}

