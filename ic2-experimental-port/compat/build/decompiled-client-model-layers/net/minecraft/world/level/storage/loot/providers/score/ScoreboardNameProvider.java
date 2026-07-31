/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.providers.score;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;

public interface ScoreboardNameProvider {
    @Nullable
    public String m_142600_(LootContext var1);

    public LootScoreProviderType m_142680_();

    public Set<LootContextParam<?>> m_142636_();
}

