/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.providers.number;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;

public interface NumberProvider
extends LootContextUser {
    public float m_142688_(LootContext var1);

    default public int m_142683_(LootContext p_165729_) {
        return Math.round(this.m_142688_(p_165729_));
    }

    public LootNumberProviderType m_142587_();
}

