/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.entries;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;

@FunctionalInterface
interface ComposableEntryContainer {
    public static final ComposableEntryContainer f_79405_ = (p_79418_, p_79419_) -> false;
    public static final ComposableEntryContainer f_79406_ = (p_79409_, p_79410_) -> true;

    public boolean m_6562_(LootContext var1, Consumer<LootPoolEntry> var2);

    default public ComposableEntryContainer m_79411_(ComposableEntryContainer p_79412_) {
        Objects.requireNonNull(p_79412_);
        return (p_79424_, p_79425_) -> this.m_6562_(p_79424_, p_79425_) && p_79412_.m_6562_(p_79424_, p_79425_);
    }

    default public ComposableEntryContainer m_79420_(ComposableEntryContainer p_79421_) {
        Objects.requireNonNull(p_79421_);
        return (p_79415_, p_79416_) -> this.m_6562_(p_79415_, p_79416_) || p_79421_.m_6562_(p_79415_, p_79416_);
    }
}

