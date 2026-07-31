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
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootItemConditionalFunction
implements LootItemFunction {
    protected final LootItemCondition[] f_80676_;
    private final Predicate<LootContext> f_80675_;

    protected LootItemConditionalFunction(LootItemCondition[] p_80678_) {
        this.f_80676_ = p_80678_;
        this.f_80675_ = LootItemConditions.m_81834_(p_80678_);
    }

    @Override
    public final ItemStack apply(ItemStack p_80689_, LootContext p_80690_) {
        return this.f_80675_.test(p_80690_) ? this.m_7372_(p_80689_, p_80690_) : p_80689_;
    }

    protected abstract ItemStack m_7372_(ItemStack var1, LootContext var2);

    @Override
    public void m_6169_(ValidationContext p_80682_) {
        LootItemFunction.super.m_6169_(p_80682_);
        for (int $$1 = 0; $$1 < this.f_80676_.length; ++$$1) {
            this.f_80676_[$$1].m_6169_(p_80682_.m_79365_(".conditions[" + $$1 + "]"));
        }
    }

    protected static Builder<?> m_80683_(Function<LootItemCondition[], LootItemFunction> p_80684_) {
        return new DummyBuilder(p_80684_);
    }

    @Override
    public /* synthetic */ Object apply(Object object, Object object2) {
        return this.apply((ItemStack)object, (LootContext)object2);
    }

    static final class DummyBuilder
    extends Builder<DummyBuilder> {
        private final Function<LootItemCondition[], LootItemFunction> f_80700_;

        public DummyBuilder(Function<LootItemCondition[], LootItemFunction> p_80702_) {
            this.f_80700_ = p_80702_;
        }

        @Override
        protected DummyBuilder m_6477_() {
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return this.f_80700_.apply(this.m_80699_());
        }

        @Override
        protected /* synthetic */ Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static abstract class Serializer<T extends LootItemConditionalFunction>
    implements net.minecraft.world.level.storage.loot.Serializer<T> {
        @Override
        public void m_6170_(JsonObject p_80711_, T p_80712_, JsonSerializationContext p_80713_) {
            if (!ArrayUtils.isEmpty((Object[])((LootItemConditionalFunction)p_80712_).f_80676_)) {
                p_80711_.add("conditions", p_80713_.serialize((Object)((LootItemConditionalFunction)p_80712_).f_80676_));
            }
        }

        @Override
        public final T m_7561_(JsonObject p_80719_, JsonDeserializationContext p_80720_) {
            LootItemCondition[] $$2 = GsonHelper.m_13845_(p_80719_, "conditions", new LootItemCondition[0], p_80720_, LootItemCondition[].class);
            return this.m_6821_(p_80719_, p_80720_, $$2);
        }

        public abstract T m_6821_(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3);

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }

    public static abstract class Builder<T extends Builder<T>>
    implements LootItemFunction.Builder,
    ConditionUserBuilder<T> {
        private final List<LootItemCondition> f_80691_ = Lists.newArrayList();

        @Override
        public T m_79080_(LootItemCondition.Builder p_80694_) {
            this.f_80691_.add(p_80694_.m_6409_());
            return this.m_6477_();
        }

        @Override
        public final T m_79073_() {
            return this.m_6477_();
        }

        protected abstract T m_6477_();

        protected LootItemCondition[] m_80699_() {
            return this.f_80691_.toArray(new LootItemCondition[0]);
        }

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

