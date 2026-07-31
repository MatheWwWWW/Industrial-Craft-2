/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public interface LootContextUser {
    default public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of();
    }

    default public void m_6169_(ValidationContext p_79022_) {
        p_79022_.m_79353_(this);
    }
}

