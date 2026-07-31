/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.ComposableEntryContainer;
import net.minecraft.world.level.storage.loot.entries.CompositeEntryBase;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public class AlternativesEntry
extends CompositeEntryBase {
    AlternativesEntry(LootPoolEntryContainer[] p_79384_, LootItemCondition[] p_79385_) {
        super(p_79384_, p_79385_);
    }

    @Override
    public LootPoolEntryType m_6751_() {
        return LootPoolEntries.f_79624_;
    }

    @Override
    protected ComposableEntryContainer m_5690_(ComposableEntryContainer[] p_79390_) {
        switch (p_79390_.length) {
            case 0: {
                return f_79405_;
            }
            case 1: {
                return p_79390_[0];
            }
            case 2: {
                return p_79390_[0].m_79420_(p_79390_[1]);
            }
        }
        return (p_79393_, p_79394_) -> {
            for (ComposableEntryContainer $$3 : p_79390_) {
                if (!$$3.m_6562_(p_79393_, p_79394_)) continue;
                return true;
            }
            return false;
        };
    }

    @Override
    public void m_6165_(ValidationContext p_79388_) {
        super.m_6165_(p_79388_);
        for (int $$1 = 0; $$1 < this.f_79428_.length - 1; ++$$1) {
            if (!ArrayUtils.isEmpty((Object[])this.f_79428_[$$1].f_79636_)) continue;
            p_79388_.m_79357_("Unreachable entry!");
        }
    }

    public static Builder m_79395_(LootPoolEntryContainer.Builder<?> ... p_79396_) {
        return new Builder(p_79396_);
    }

    public static <E> Builder m_230933_(Collection<E> p_230934_, Function<E, LootPoolEntryContainer.Builder<?>> p_230935_) {
        return new Builder((LootPoolEntryContainer.Builder[])p_230934_.stream().map(p_230935_::apply).toArray(LootPoolEntryContainer.Builder[]::new));
    }

    public static class Builder
    extends LootPoolEntryContainer.Builder<Builder> {
        private final List<LootPoolEntryContainer> f_79397_ = Lists.newArrayList();

        public Builder(LootPoolEntryContainer.Builder<?> ... p_79399_) {
            for (LootPoolEntryContainer.Builder<?> $$1 : p_79399_) {
                this.f_79397_.add($$1.m_7512_());
            }
        }

        @Override
        protected Builder m_6897_() {
            return this;
        }

        @Override
        public Builder m_7170_(LootPoolEntryContainer.Builder<?> p_79402_) {
            this.f_79397_.add(p_79402_.m_7512_());
            return this;
        }

        @Override
        public LootPoolEntryContainer m_7512_() {
            return new AlternativesEntry(this.f_79397_.toArray(new LootPoolEntryContainer[0]), this.m_79651_());
        }

        @Override
        protected /* synthetic */ LootPoolEntryContainer.Builder m_6897_() {
            return this.m_6897_();
        }
    }
}

