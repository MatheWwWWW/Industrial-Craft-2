/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.predicates;

import java.util.function.Function;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public interface ConditionUserBuilder<T extends ConditionUserBuilder<T>> {
    public T m_79080_(LootItemCondition.Builder var1);

    default public <E> T m_231040_(Iterable<E> p_231041_, Function<E, LootItemCondition.Builder> p_231042_) {
        T $$2 = this.m_79073_();
        for (E $$3 : p_231041_) {
            $$2 = $$2.m_79080_(p_231042_.apply($$3));
        }
        return $$2;
    }

    public T m_79073_();
}

