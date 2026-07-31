/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.providers.nbt;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.StorageNbtProvider;

public class NbtProviders {
    public static final LootNbtProviderType f_165623_ = NbtProviders.m_165628_("storage", new StorageNbtProvider.Serializer());
    public static final LootNbtProviderType f_165624_ = NbtProviders.m_165628_("context", new ContextNbtProvider.Serializer());

    private static LootNbtProviderType m_165628_(String p_165629_, Serializer<? extends NbtProvider> p_165630_) {
        return Registry.m_122965_(Registry.f_175422_, new ResourceLocation(p_165629_), new LootNbtProviderType(p_165630_));
    }

    public static Object m_165627_() {
        return GsonAdapterFactory.m_78801_(Registry.f_175422_, "provider", "type", NbtProvider::m_142624_).m_164986_(f_165624_, new ContextNbtProvider.InlineSerializer()).m_78822_();
    }
}

