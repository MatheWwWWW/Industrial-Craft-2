/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.providers.nbt;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;

public interface NbtProvider {
    @Nullable
    public Tag m_142301_(LootContext var1);

    public Set<LootContextParam<?>> m_142677_();

    public LootNbtProviderType m_142624_();
}

