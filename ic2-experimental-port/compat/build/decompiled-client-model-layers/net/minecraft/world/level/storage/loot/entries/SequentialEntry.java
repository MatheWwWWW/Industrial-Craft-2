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

public class SequentialEntry
extends CompositeEntryBase {
    SequentialEntry(LootPoolEntryContainer[] p_79812_, LootItemCondition[] p_79813_) {
        super(p_79812_, p_79813_);
    }

    @Override
    public LootPoolEntryType m_6751_() {
        return LootPoolEntries.f_79625_;
    }

    @Override
    protected ComposableEntryContainer m_5690_(ComposableEntryContainer[] p_79816_) {
        switch (p_79816_.length) {
            case 0: {
                return f_79406_;
            }
            case 1: {
                return p_79816_[0];
            }
            case 2: {
                return p_79816_[0].m_79411_(p_79816_[1]);
            }
        }
        return (p_79819_, p_79820_) -> {
            for (ComposableEntryContainer $$3 : p_79816_) {
                if ($$3.m_6562_(p_79819_, p_79820_)) continue;
                return false;
            }
            return true;
        };
    }

    public static Builder m_165152_(LootPoolEntryContainer.Builder<?> ... p_165153_) {
        return new Builder(p_165153_);
    }

    public static class Builder
    extends LootPoolEntryContainer.Builder<Builder> {
        private final List<LootPoolEntryContainer> f_165154_ = Lists.newArrayList();

        public Builder(LootPoolEntryContainer.Builder<?> ... p_165156_) {
            for (LootPoolEntryContainer.Builder<?> $$1 : p_165156_) {
                this.f_165154_.add($$1.m_7512_());
            }
        }

        @Override
        protected Builder m_6897_() {
            return this;
        }

        @Override
        public Builder m_142639_(LootPoolEntryContainer.Builder<?> p_165160_) {
            this.f_165154_.add(p_165160_.m_7512_());
            return this;
        }

        @Override
        public LootPoolEntryContainer m_7512_() {
            return new SequentialEntry(this.f_165154_.toArray(new LootPoolEntryContainer[0]), this.m_79651_());
        }

        @Override
        protected /* synthetic */ LootPoolEntryContainer.Builder m_6897_() {
            return this.m_6897_();
        }
    }
}

