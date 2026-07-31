/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetContainerContents
extends LootItemConditionalFunction {
    final List<LootPoolEntryContainer> f_80902_;
    final BlockEntityType<?> f_193031_;

    SetContainerContents(LootItemCondition[] p_193033_, BlockEntityType<?> p_193034_, List<LootPoolEntryContainer> p_193035_) {
        super(p_193033_);
        this.f_193031_ = p_193034_;
        this.f_80902_ = ImmutableList.copyOf(p_193035_);
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80748_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80911_, LootContext p_80912_) {
        if (p_80911_.m_41619_()) {
            return p_80911_;
        }
        NonNullList<ItemStack> $$2 = NonNullList.m_122779_();
        this.f_80902_.forEach(p_80916_ -> p_80916_.m_6562_(p_80912_, p_165321_ -> p_165321_.m_6941_(LootTable.m_79142_($$2::add), p_80912_)));
        CompoundTag $$3 = new CompoundTag();
        ContainerHelper.m_18973_($$3, $$2);
        CompoundTag $$4 = BlockItem.m_186336_(p_80911_);
        if ($$4 == null) {
            $$4 = $$3;
        } else {
            $$4.m_128391_($$3);
        }
        BlockItem.m_186338_(p_80911_, this.f_193031_, $$4);
        return p_80911_;
    }

    @Override
    public void m_6169_(ValidationContext p_80918_) {
        super.m_6169_(p_80918_);
        for (int $$1 = 0; $$1 < this.f_80902_.size(); ++$$1) {
            this.f_80902_.get($$1).m_6165_(p_80918_.m_79365_(".entry[" + $$1 + "]"));
        }
    }

    public static Builder m_193036_(BlockEntityType<?> p_193037_) {
        return new Builder(p_193037_);
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final List<LootPoolEntryContainer> f_80927_ = Lists.newArrayList();
        private final BlockEntityType<?> f_193038_;

        public Builder(BlockEntityType<?> p_193040_) {
            this.f_193038_ = p_193040_;
        }

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_80930_(LootPoolEntryContainer.Builder<?> p_80931_) {
            this.f_80927_.add(p_80931_.m_7512_());
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new SetContainerContents(this.m_80699_(), this.f_193038_, this.f_80927_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetContainerContents> {
        @Override
        public void m_6170_(JsonObject p_80944_, SetContainerContents p_80945_, JsonSerializationContext p_80946_) {
            super.m_6170_(p_80944_, p_80945_, p_80946_);
            p_80944_.addProperty("type", Registry.f_122830_.m_7981_(p_80945_.f_193031_).toString());
            p_80944_.add("entries", p_80946_.serialize(p_80945_.f_80902_));
        }

        @Override
        public SetContainerContents m_6821_(JsonObject p_80936_, JsonDeserializationContext p_80937_, LootItemCondition[] p_80938_) {
            LootPoolEntryContainer[] $$3 = GsonHelper.m_13836_(p_80936_, "entries", p_80937_, LootPoolEntryContainer[].class);
            ResourceLocation $$4 = new ResourceLocation(GsonHelper.m_13906_(p_80936_, "type"));
            BlockEntityType<?> $$5 = Registry.f_122830_.m_6612_($$4).orElseThrow(() -> new JsonSyntaxException("Unknown block entity type id '" + $$4 + "'"));
            return new SetContainerContents(p_80938_, $$5, Arrays.asList($$3));
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

