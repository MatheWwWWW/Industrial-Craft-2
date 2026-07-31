/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetBannerPatternFunction
extends LootItemConditionalFunction {
    final List<Pair<Holder<BannerPattern>, DyeColor>> f_165272_;
    final boolean f_165273_;

    SetBannerPatternFunction(LootItemCondition[] p_165275_, List<Pair<Holder<BannerPattern>, DyeColor>> p_165276_, boolean p_165277_) {
        super(p_165275_);
        this.f_165272_ = p_165276_;
        this.f_165273_ = p_165277_;
    }

    @Override
    protected ItemStack m_7372_(ItemStack p_165280_, LootContext p_165281_) {
        ListTag $$6;
        CompoundTag $$2 = BlockItem.m_186336_(p_165280_);
        if ($$2 == null) {
            $$2 = new CompoundTag();
        }
        BannerPattern.Builder $$3 = new BannerPattern.Builder();
        this.f_165272_.forEach($$3::m_155048_);
        ListTag $$4 = $$3.m_58587_();
        if (this.f_165273_) {
            ListTag $$5 = $$2.m_128437_("Patterns", 10).m_6426_();
            $$5.addAll($$4);
        } else {
            $$6 = $$4;
        }
        $$2.m_128365_("Patterns", $$6);
        BlockItem.m_186338_(p_165280_, BlockEntityType.f_58935_, $$2);
        return p_165280_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_165222_;
    }

    public static Builder m_165282_(boolean p_165283_) {
        return new Builder(p_165283_);
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final ImmutableList.Builder<Pair<Holder<BannerPattern>, DyeColor>> f_165284_ = ImmutableList.builder();
        private final boolean f_165285_;

        Builder(boolean p_165287_) {
            this.f_165285_ = p_165287_;
        }

        @Override
        protected Builder m_6477_() {
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new SetBannerPatternFunction(this.m_80699_(), (List<Pair<Holder<BannerPattern>, DyeColor>>)this.f_165284_.build(), this.f_165285_);
        }

        public Builder m_230995_(ResourceKey<BannerPattern> p_230996_, DyeColor p_230997_) {
            return this.m_230998_(Registry.f_235736_.m_206081_(p_230996_), p_230997_);
        }

        public Builder m_230998_(Holder<BannerPattern> p_230999_, DyeColor p_231000_) {
            this.f_165284_.add((Object)Pair.of(p_230999_, (Object)p_231000_));
            return this;
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetBannerPatternFunction> {
        @Override
        public void m_6170_(JsonObject p_165307_, SetBannerPatternFunction p_165308_, JsonSerializationContext p_165309_) {
            super.m_6170_(p_165307_, p_165308_, p_165309_);
            JsonArray $$3 = new JsonArray();
            p_165308_.f_165272_.forEach(p_231003_ -> {
                JsonObject $$2 = new JsonObject();
                $$2.addProperty("pattern", ((Holder)p_231003_.getFirst()).m_203543_().orElseThrow(() -> new JsonSyntaxException("Unknown pattern: " + p_231003_.getFirst())).m_135782_().toString());
                $$2.addProperty("color", ((DyeColor)p_231003_.getSecond()).m_41065_());
                $$3.add((JsonElement)$$2);
            });
            p_165307_.add("patterns", (JsonElement)$$3);
            p_165307_.addProperty("append", Boolean.valueOf(p_165308_.f_165273_));
        }

        @Override
        public SetBannerPatternFunction m_6821_(JsonObject p_165299_, JsonDeserializationContext p_165300_, LootItemCondition[] p_165301_) {
            ImmutableList.Builder $$3 = ImmutableList.builder();
            JsonArray $$4 = GsonHelper.m_13933_(p_165299_, "patterns");
            for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
                JsonObject $$6 = GsonHelper.m_13918_($$4.get($$5), "pattern[" + $$5 + "]");
                String $$7 = GsonHelper.m_13906_($$6, "pattern");
                Optional<Holder<BannerPattern>> $$8 = Registry.f_235736_.m_203636_(ResourceKey.m_135785_(Registry.f_235735_, new ResourceLocation($$7)));
                if ($$8.isEmpty()) {
                    throw new JsonSyntaxException("Unknown pattern: " + $$7);
                }
                String $$9 = GsonHelper.m_13906_($$6, "color");
                DyeColor $$10 = DyeColor.m_41057_($$9, null);
                if ($$10 == null) {
                    throw new JsonSyntaxException("Unknown color: " + $$9);
                }
                $$3.add((Object)Pair.of($$8.get(), (Object)$$10));
            }
            boolean $$11 = GsonHelper.m_13912_(p_165299_, "append");
            return new SetBannerPatternFunction(p_165301_, (List<Pair<Holder<BannerPattern>, DyeColor>>)$$3.build(), $$11);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

