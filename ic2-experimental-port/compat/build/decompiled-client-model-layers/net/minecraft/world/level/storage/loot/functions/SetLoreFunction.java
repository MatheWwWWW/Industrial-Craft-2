/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Streams
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetLoreFunction
extends LootItemConditionalFunction {
    final boolean f_81079_;
    final List<Component> f_81080_;
    @Nullable
    final LootContext.EntityTarget f_81081_;

    public SetLoreFunction(LootItemCondition[] p_81083_, boolean p_81084_, List<Component> p_81085_, @Nullable LootContext.EntityTarget p_81086_) {
        super(p_81083_);
        this.f_81079_ = p_81084_;
        this.f_81080_ = ImmutableList.copyOf(p_81085_);
        this.f_81081_ = p_81086_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80753_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_81081_ != null ? ImmutableSet.of(this.f_81081_.m_79003_()) : ImmutableSet.of();
    }

    @Override
    public ItemStack m_7372_(ItemStack p_81089_, LootContext p_81090_) {
        ListTag $$2 = this.m_81091_(p_81089_, !this.f_81080_.isEmpty());
        if ($$2 != null) {
            if (this.f_81079_) {
                $$2.clear();
            }
            UnaryOperator<Component> $$3 = SetNameFunction.m_81139_(p_81090_, this.f_81081_);
            this.f_81080_.stream().map($$3).map(Component.Serializer::m_130703_).map(StringTag::m_129297_).forEach($$2::add);
        }
        return p_81089_;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private ListTag m_81091_(ItemStack p_81092_, boolean p_81093_) {
        void $$7;
        void $$4;
        if (p_81092_.m_41782_()) {
            CompoundTag $$2 = p_81092_.m_41783_();
        } else if (p_81093_) {
            CompoundTag $$3 = new CompoundTag();
            p_81092_.m_41751_($$3);
        } else {
            return null;
        }
        if ($$4.m_128425_("display", 10)) {
            CompoundTag $$5 = $$4.m_128469_("display");
        } else if (p_81093_) {
            CompoundTag $$6 = new CompoundTag();
            $$4.m_128365_("display", $$6);
        } else {
            return null;
        }
        if ($$7.m_128425_("Lore", 9)) {
            return $$7.m_128437_("Lore", 8);
        }
        if (p_81093_) {
            ListTag $$8 = new ListTag();
            $$7.m_128365_("Lore", $$8);
            return $$8;
        }
        return null;
    }

    public static Builder m_165443_() {
        return new Builder();
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private boolean f_165444_;
        private LootContext.EntityTarget f_165445_;
        private final List<Component> f_165446_ = Lists.newArrayList();

        public Builder m_165453_(boolean p_165454_) {
            this.f_165444_ = p_165454_;
            return this;
        }

        public Builder m_165449_(LootContext.EntityTarget p_165450_) {
            this.f_165445_ = p_165450_;
            return this;
        }

        public Builder m_165451_(Component p_165452_) {
            this.f_165446_.add(p_165452_);
            return this;
        }

        @Override
        protected Builder m_6477_() {
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new SetLoreFunction(this.m_80699_(), this.f_165444_, this.f_165446_, this.f_165445_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetLoreFunction> {
        @Override
        public void m_6170_(JsonObject p_81111_, SetLoreFunction p_81112_, JsonSerializationContext p_81113_) {
            super.m_6170_(p_81111_, p_81112_, p_81113_);
            p_81111_.addProperty("replace", Boolean.valueOf(p_81112_.f_81079_));
            JsonArray $$3 = new JsonArray();
            for (Component $$4 : p_81112_.f_81080_) {
                $$3.add(Component.Serializer.m_130716_($$4));
            }
            p_81111_.add("lore", (JsonElement)$$3);
            if (p_81112_.f_81081_ != null) {
                p_81111_.add("entity", p_81113_.serialize((Object)p_81112_.f_81081_));
            }
        }

        @Override
        public SetLoreFunction m_6821_(JsonObject p_81103_, JsonDeserializationContext p_81104_, LootItemCondition[] p_81105_) {
            boolean $$3 = GsonHelper.m_13855_(p_81103_, "replace", false);
            List $$4 = (List)Streams.stream((Iterable)GsonHelper.m_13933_(p_81103_, "lore")).map(Component.Serializer::m_130691_).collect(ImmutableList.toImmutableList());
            LootContext.EntityTarget $$5 = GsonHelper.m_13845_(p_81103_, "entity", null, p_81104_, LootContext.EntityTarget.class);
            return new SetLoreFunction(p_81105_, $$3, $$4, $$5);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

