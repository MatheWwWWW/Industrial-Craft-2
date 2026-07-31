/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.level.storage.loot.entries.ComposableEntryContainer;
import net.minecraft.world.level.storage.loot.entries.CompositeEntryBase;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class EntryGroup
extends CompositeEntryBase {
    EntryGroup(LootPoolEntryContainer[] p_79550_, LootItemCondition[] p_79551_) {
        super(p_79550_, p_79551_);
    }

    @Override
    public LootPoolEntryType m_6751_() {
        return LootPoolEntries.f_79626_;
    }

    @Override
    protected ComposableEntryContainer m_5690_(ComposableEntryContainer[] p_79559_) {
        switch (p_79559_.length) {
            case 0: {
                return f_79406_;
            }
            case 1: {
                return p_79559_[0];
            }
            case 2: {
                ComposableEntryContainer $$1 = p_79559_[0];
                ComposableEntryContainer $$2 = p_79559_[1];
                return (p_79556_, p_79557_) -> {
                    $$1.m_6562_(p_79556_, p_79557_);
                    $$2.m_6562_(p_79556_, p_79557_);
                    return true;
                };
            }
        }
        return (p_79562_, p_79563_) -> {
            for (ComposableEntryContainer $$3 : p_79559_) {
                $$3.m_6562_(p_79562_, p_79563_);
            }
            return true;
        };
    }

    public static Builder m_165137_(LootPoolEntryContainer.Builder<?> ... p_165138_) {
        return new Builder(p_165138_);
    }

    public static class Builder
    extends LootPoolEntryContainer.Builder<Builder> {
        private final List<LootPoolEntryContainer> f_165139_ = Lists.newArrayList();

        public Builder(LootPoolEntryContainer.Builder<?> ... p_165141_) {
            for (LootPoolEntryContainer.Builder<?> $$1 : p_165141_) {
                this.f_165139_.add($$1.m_7512_());
            }
        }

        @Override
        protected Builder m_6897_() {
            return this;
        }

        @Override
        public Builder m_142719_(LootPoolEntryContainer.Builder<?> p_165145_) {
            this.f_165139_.add(p_165145_.m_7512_());
            return this;
        }

        @Override
        public LootPoolEntryContainer m_7512_() {
            return new EntryGroup(this.f_165139_.toArray(new LootPoolEntryContainer[0]), this.m_79651_());
        }

        @Override
        protected /* synthetic */ LootPoolEntryContainer.Builder m_6897_() {
            return this.m_6897_();
        }
    }
}

