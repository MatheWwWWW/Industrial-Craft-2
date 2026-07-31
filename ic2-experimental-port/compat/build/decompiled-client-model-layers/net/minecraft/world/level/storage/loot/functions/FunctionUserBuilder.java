/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.functions;

import java.util.Arrays;
import java.util.function.Function;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public interface FunctionUserBuilder<T extends FunctionUserBuilder<T>> {
    public T m_79078_(LootItemFunction.Builder var1);

    default public <E> T m_230984_(Iterable<E> p_230985_, Function<E, LootItemFunction.Builder> p_230986_) {
        T $$2 = this.m_79073_();
        for (E $$3 : p_230985_) {
            $$2 = $$2.m_79078_(p_230986_.apply($$3));
        }
        return $$2;
    }

    default public <E> T m_230987_(E[] p_230988_, Function<E, LootItemFunction.Builder> p_230989_) {
        return this.m_230984_(Arrays.asList(p_230988_), p_230989_);
    }

    public T m_79073_();
}

