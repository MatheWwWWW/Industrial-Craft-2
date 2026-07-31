/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.predicates;

import java.util.function.Predicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public interface LootItemCondition
extends LootContextUser,
Predicate<LootContext> {
    public LootItemConditionType m_7940_();

    @FunctionalInterface
    public static interface Builder {
        public LootItemCondition m_6409_();

        default public Builder m_81807_() {
            return InvertedLootItemCondition.m_81694_(this);
        }

        default public AlternativeLootItemCondition.Builder m_7818_(Builder p_81808_) {
            return AlternativeLootItemCondition.m_81481_(this, p_81808_);
        }
    }
}

