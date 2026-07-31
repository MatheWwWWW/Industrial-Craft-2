/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.ComposableEntryContainer;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.SequentialEntry;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootPoolEntryContainer
implements ComposableEntryContainer {
    protected final LootItemCondition[] f_79636_;
    private final Predicate<LootContext> f_79635_;

    protected LootPoolEntryContainer(LootItemCondition[] p_79638_) {
        this.f_79636_ = p_79638_;
        this.f_79635_ = LootItemConditions.m_81834_(p_79638_);
    }

    public void m_6165_(ValidationContext p_79641_) {
        for (int $$1 = 0; $$1 < this.f_79636_.length; ++$$1) {
            this.f_79636_[$$1].m_6169_(p_79641_.m_79365_(".condition[" + $$1 + "]"));
        }
    }

    protected final boolean m_79639_(LootContext p_79640_) {
        return this.f_79635_.test(p_79640_);
    }

    public abstract LootPoolEntryType m_6751_();

    public static abstract class Serializer<T extends LootPoolEntryContainer>
    implements net.minecraft.world.level.storage.loot.Serializer<T> {
        @Override
        public final void m_6170_(JsonObject p_79670_, T p_79671_, JsonSerializationContext p_79672_) {
            if (!ArrayUtils.isEmpty((Object[])((LootPoolEntryContainer)p_79671_).f_79636_)) {
                p_79670_.add("conditions", p_79672_.serialize((Object)((LootPoolEntryContainer)p_79671_).f_79636_));
            }
            this.m_7219_(p_79670_, p_79671_, p_79672_);
        }

        @Override
        public final T m_7561_(JsonObject p_79664_, JsonDeserializationContext p_79665_) {
            LootItemCondition[] $$2 = GsonHelper.m_13845_(p_79664_, "conditions", new LootItemCondition[0], p_79665_, LootItemCondition[].class);
            return this.m_5921_(p_79664_, p_79665_, $$2);
        }

        public abstract void m_7219_(JsonObject var1, T var2, JsonSerializationContext var3);

        public abstract T m_5921_(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3);

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }

        @Override
        public /* synthetic */ void m_6170_(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.m_6170_(jsonObject, (T)((LootPoolEntryContainer)object), jsonSerializationContext);
        }
    }

    public static abstract class Builder<T extends Builder<T>>
    implements ConditionUserBuilder<T> {
        private final List<LootItemCondition> f_79642_ = Lists.newArrayList();

        protected abstract T m_6897_();

        @Override
        public T m_79080_(LootItemCondition.Builder p_79646_) {
            this.f_79642_.add(p_79646_.m_6409_());
            return this.m_6897_();
        }

        @Override
        public final T m_79073_() {
            return this.m_6897_();
        }

        protected LootItemCondition[] m_79651_() {
            return this.f_79642_.toArray(new LootItemCondition[0]);
        }

        public AlternativesEntry.Builder m_7170_(Builder<?> p_79644_) {
            return new AlternativesEntry.Builder(this, p_79644_);
        }

        public EntryGroup.Builder m_142719_(Builder<?> p_165148_) {
            return new EntryGroup.Builder(this, p_165148_);
        }

        public SequentialEntry.Builder m_142639_(Builder<?> p_165149_) {
            return new SequentialEntry.Builder(this, p_165149_);
        }

        public abstract LootPoolEntryContainer m_7512_();

        @Override
        public /* synthetic */ ConditionUserBuilder m_79073_() {
            return this.m_79073_();
        }

        @Override
        public /* synthetic */ ConditionUserBuilder m_79080_(LootItemCondition.Builder builder) {
            return this.m_79080_(builder);
        }
    }
}

