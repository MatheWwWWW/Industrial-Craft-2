/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.providers.score;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.providers.score.ContextScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.FixedScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;

public class ScoreboardNameProviders {
    public static final LootScoreProviderType f_165868_ = ScoreboardNameProviders.m_165873_("fixed", new FixedScoreboardNameProvider.Serializer());
    public static final LootScoreProviderType f_165869_ = ScoreboardNameProviders.m_165873_("context", new ContextScoreboardNameProvider.Serializer());

    private static LootScoreProviderType m_165873_(String p_165874_, Serializer<? extends ScoreboardNameProvider> p_165875_) {
        return Registry.m_122965_(Registry.f_175413_, new ResourceLocation(p_165874_), new LootScoreProviderType(p_165875_));
    }

    public static Object m_165872_() {
        return GsonAdapterFactory.m_78801_(Registry.f_175413_, "provider", "type", ScoreboardNameProvider::m_142680_).m_164986_(f_165869_, new ContextScoreboardNameProvider.InlineSerializer()).m_78822_();
    }
}

