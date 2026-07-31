/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.predicates;

import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ConditionReference;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.EntityHasScoreCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck;
import net.minecraft.world.level.storage.loot.predicates.ValueCheckCondition;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;

public class LootItemConditions {
    public static final LootItemConditionType f_81811_ = LootItemConditions.m_81831_("inverted", new InvertedLootItemCondition.Serializer());
    public static final LootItemConditionType f_81812_ = LootItemConditions.m_81831_("alternative", new AlternativeLootItemCondition.Serializer());
    public static final LootItemConditionType f_81813_ = LootItemConditions.m_81831_("random_chance", new LootItemRandomChanceCondition.Serializer());
    public static final LootItemConditionType f_81814_ = LootItemConditions.m_81831_("random_chance_with_looting", new LootItemRandomChanceWithLootingCondition.Serializer());
    public static final LootItemConditionType f_81815_ = LootItemConditions.m_81831_("entity_properties", new LootItemEntityPropertyCondition.Serializer());
    public static final LootItemConditionType f_81816_ = LootItemConditions.m_81831_("killed_by_player", new LootItemKilledByPlayerCondition.Serializer());
    public static final LootItemConditionType f_81817_ = LootItemConditions.m_81831_("entity_scores", new EntityHasScoreCondition.Serializer());
    public static final LootItemConditionType f_81818_ = LootItemConditions.m_81831_("block_state_property", new LootItemBlockStatePropertyCondition.Serializer());
    public static final LootItemConditionType f_81819_ = LootItemConditions.m_81831_("match_tool", new MatchTool.Serializer());
    public static final LootItemConditionType f_81820_ = LootItemConditions.m_81831_("table_bonus", new BonusLevelTableCondition.Serializer());
    public static final LootItemConditionType f_81821_ = LootItemConditions.m_81831_("survives_explosion", new ExplosionCondition.Serializer());
    public static final LootItemConditionType f_81822_ = LootItemConditions.m_81831_("damage_source_properties", new DamageSourceCondition.Serializer());
    public static final LootItemConditionType f_81823_ = LootItemConditions.m_81831_("location_check", new LocationCheck.Serializer());
    public static final LootItemConditionType f_81824_ = LootItemConditions.m_81831_("weather_check", new WeatherCheck.Serializer());
    public static final LootItemConditionType f_81825_ = LootItemConditions.m_81831_("reference", new ConditionReference.Serializer());
    public static final LootItemConditionType f_81826_ = LootItemConditions.m_81831_("time_check", new TimeCheck.Serializer());
    public static final LootItemConditionType f_165504_ = LootItemConditions.m_81831_("value_check", new ValueCheckCondition.Serializer());

    private static LootItemConditionType m_81831_(String p_81832_, Serializer<? extends LootItemCondition> p_81833_) {
        return Registry.m_122965_(Registry.f_122877_, new ResourceLocation(p_81832_), new LootItemConditionType(p_81833_));
    }

    public static Object m_81828_() {
        return GsonAdapterFactory.m_78801_(Registry.f_122877_, "condition", "condition", LootItemCondition::m_7940_).m_78822_();
    }

    public static <T> Predicate<T> m_81834_(Predicate<T>[] p_81835_) {
        switch (p_81835_.length) {
            case 0: {
                return p_81840_ -> true;
            }
            case 1: {
                return p_81835_[0];
            }
            case 2: {
                return p_81835_[0].and(p_81835_[1]);
            }
        }
        return p_81845_ -> {
            for (Predicate $$2 : p_81835_) {
                if ($$2.test(p_81845_)) continue;
                return false;
            }
            return true;
        };
    }

    public static <T> Predicate<T> m_81841_(Predicate<T>[] p_81842_) {
        switch (p_81842_.length) {
            case 0: {
                return p_81830_ -> false;
            }
            case 1: {
                return p_81842_[0];
            }
            case 2: {
                return p_81842_[0].or(p_81842_[1]);
            }
        }
        return p_81838_ -> {
            for (Predicate $$2 : p_81842_) {
                if (!$$2.test(p_81838_)) continue;
                return true;
            }
            return false;
        };
    }
}

