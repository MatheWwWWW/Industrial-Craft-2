/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetContainerLootTable
extends LootItemConditionalFunction {
    final ResourceLocation f_80955_;
    final long f_80956_;
    final BlockEntityType<?> f_193043_;

    SetContainerLootTable(LootItemCondition[] p_193045_, ResourceLocation p_193046_, long p_193047_, BlockEntityType<?> p_193048_) {
        super(p_193045_);
        this.f_80955_ = p_193046_;
        this.f_80956_ = p_193047_;
        this.f_193043_ = p_193048_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80751_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80967_, LootContext p_80968_) {
        if (p_80967_.m_41619_()) {
            return p_80967_;
        }
        CompoundTag $$2 = BlockItem.m_186336_(p_80967_);
        if ($$2 == null) {
            $$2 = new CompoundTag();
        }
        $$2.m_128359_("LootTable", this.f_80955_.toString());
        if (this.f_80956_ != 0L) {
            $$2.m_128356_("LootTableSeed", this.f_80956_);
        }
        BlockItem.m_186338_(p_80967_, this.f_193043_, $$2);
        return p_80967_;
    }

    @Override
    public void m_6169_(ValidationContext p_80970_) {
        if (p_80970_.m_79362_(this.f_80955_)) {
            p_80970_.m_79357_("Table " + this.f_80955_ + " is recursively called");
            return;
        }
        super.m_6169_(p_80970_);
        LootTable $$1 = p_80970_.m_79375_(this.f_80955_);
        if ($$1 == null) {
            p_80970_.m_79357_("Unknown loot table called " + this.f_80955_);
        } else {
            $$1.m_79136_(p_80970_.m_79359_("->{" + this.f_80955_ + "}", this.f_80955_));
        }
    }

    public static LootItemConditionalFunction.Builder<?> m_193049_(BlockEntityType<?> p_193050_, ResourceLocation p_193051_) {
        return SetContainerLootTable.m_80683_(p_193064_ -> new SetContainerLootTable((LootItemCondition[])p_193064_, p_193051_, 0L, p_193050_));
    }

    public static LootItemConditionalFunction.Builder<?> m_193052_(BlockEntityType<?> p_193053_, ResourceLocation p_193054_, long p_193055_) {
        return SetContainerLootTable.m_80683_(p_193060_ -> new SetContainerLootTable((LootItemCondition[])p_193060_, p_193054_, p_193055_, p_193053_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetContainerLootTable> {
        @Override
        public void m_6170_(JsonObject p_80986_, SetContainerLootTable p_80987_, JsonSerializationContext p_80988_) {
            super.m_6170_(p_80986_, p_80987_, p_80988_);
            p_80986_.addProperty("name", p_80987_.f_80955_.toString());
            p_80986_.addProperty("type", Registry.f_122830_.m_7981_(p_80987_.f_193043_).toString());
            if (p_80987_.f_80956_ != 0L) {
                p_80986_.addProperty("seed", (Number)p_80987_.f_80956_);
            }
        }

        @Override
        public SetContainerLootTable m_6821_(JsonObject p_80978_, JsonDeserializationContext p_80979_, LootItemCondition[] p_80980_) {
            ResourceLocation $$3 = new ResourceLocation(GsonHelper.m_13906_(p_80978_, "name"));
            long $$4 = GsonHelper.m_13828_(p_80978_, "seed", 0L);
            ResourceLocation $$5 = new ResourceLocation(GsonHelper.m_13906_(p_80978_, "type"));
            BlockEntityType<?> $$6 = Registry.f_122830_.m_6612_($$5).orElseThrow(() -> new JsonSyntaxException("Unknown block entity type id '" + $$5 + "'"));
            return new SetContainerLootTable(p_80980_, $$3, $$4, $$6);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

