/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;

public class CopyNbtFunction
extends LootItemConditionalFunction {
    final NbtProvider f_80234_;
    final List<CopyOperation> f_80235_;

    CopyNbtFunction(LootItemCondition[] p_165175_, NbtProvider p_165176_, List<CopyOperation> p_165177_) {
        super(p_165175_);
        this.f_80234_ = p_165176_;
        this.f_80235_ = ImmutableList.copyOf(p_165177_);
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80755_;
    }

    static NbtPathArgument.NbtPath m_80267_(String p_80268_) {
        try {
            return new NbtPathArgument().parse(new StringReader(p_80268_));
        }
        catch (CommandSyntaxException $$1) {
            throw new IllegalArgumentException("Failed to parse path " + p_80268_, $$1);
        }
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_80234_.m_142677_();
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80250_, LootContext p_80251_) {
        Tag $$2 = this.f_80234_.m_142301_(p_80251_);
        if ($$2 != null) {
            this.f_80235_.forEach(p_80255_ -> p_80255_.m_80305_(p_80250_::m_41784_, $$2));
        }
        return p_80250_;
    }

    public static Builder m_165180_(NbtProvider p_165181_) {
        return new Builder(p_165181_);
    }

    public static Builder m_165178_(LootContext.EntityTarget p_165179_) {
        return new Builder(ContextNbtProvider.m_165570_(p_165179_));
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final NbtProvider f_80271_;
        private final List<CopyOperation> f_80272_ = Lists.newArrayList();

        Builder(NbtProvider p_165183_) {
            this.f_80271_ = p_165183_;
        }

        public Builder m_80282_(String p_80283_, String p_80284_, MergeStrategy p_80285_) {
            this.f_80272_.add(new CopyOperation(p_80283_, p_80284_, p_80285_));
            return this;
        }

        public Builder m_80279_(String p_80280_, String p_80281_) {
            return this.m_80282_(p_80280_, p_80281_, MergeStrategy.REPLACE);
        }

        @Override
        protected Builder m_6477_() {
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new CopyNbtFunction(this.m_80699_(), this.f_80271_, this.f_80272_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    static class CopyOperation {
        private final String f_80288_;
        private final NbtPathArgument.NbtPath f_80289_;
        private final String f_80290_;
        private final NbtPathArgument.NbtPath f_80291_;
        private final MergeStrategy f_80292_;

        CopyOperation(String p_80294_, String p_80295_, MergeStrategy p_80296_) {
            this.f_80288_ = p_80294_;
            this.f_80289_ = CopyNbtFunction.m_80267_(p_80294_);
            this.f_80290_ = p_80295_;
            this.f_80291_ = CopyNbtFunction.m_80267_(p_80295_);
            this.f_80292_ = p_80296_;
        }

        public void m_80305_(Supplier<Tag> p_80306_, Tag p_80307_) {
            try {
                List<Tag> $$2 = this.f_80289_.m_99638_(p_80307_);
                if (!$$2.isEmpty()) {
                    this.f_80292_.m_6706_(p_80306_.get(), this.f_80291_, $$2);
                }
            }
            catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
        }

        public JsonObject m_80302_() {
            JsonObject $$0 = new JsonObject();
            $$0.addProperty("source", this.f_80288_);
            $$0.addProperty("target", this.f_80290_);
            $$0.addProperty("op", this.f_80292_.f_80335_);
            return $$0;
        }

        public static CopyOperation m_80303_(JsonObject p_80304_) {
            String $$1 = GsonHelper.m_13906_(p_80304_, "source");
            String $$2 = GsonHelper.m_13906_(p_80304_, "target");
            MergeStrategy $$3 = MergeStrategy.m_80349_(GsonHelper.m_13906_(p_80304_, "op"));
            return new CopyOperation($$1, $$2, $$3);
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<CopyNbtFunction> {
        @Override
        public void m_6170_(JsonObject p_80399_, CopyNbtFunction p_80400_, JsonSerializationContext p_80401_) {
            super.m_6170_(p_80399_, p_80400_, p_80401_);
            p_80399_.add("source", p_80401_.serialize((Object)p_80400_.f_80234_));
            JsonArray $$3 = new JsonArray();
            p_80400_.f_80235_.stream().map(CopyOperation::m_80302_).forEach(arg_0 -> ((JsonArray)$$3).add(arg_0));
            p_80399_.add("ops", (JsonElement)$$3);
        }

        @Override
        public CopyNbtFunction m_6821_(JsonObject p_80395_, JsonDeserializationContext p_80396_, LootItemCondition[] p_80397_) {
            NbtProvider $$3 = GsonHelper.m_13836_(p_80395_, "source", p_80396_, NbtProvider.class);
            ArrayList $$4 = Lists.newArrayList();
            JsonArray $$5 = GsonHelper.m_13933_(p_80395_, "ops");
            for (JsonElement $$6 : $$5) {
                JsonObject $$7 = GsonHelper.m_13918_($$6, "op");
                $$4.add(CopyOperation.m_80303_($$7));
            }
            return new CopyNbtFunction(p_80397_, $$3, $$4);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class MergeStrategy
    extends Enum<MergeStrategy> {
        public static final /* enum */ MergeStrategy REPLACE = new MergeStrategy("replace"){

            @Override
            public void m_6706_(Tag p_80362_, NbtPathArgument.NbtPath p_80363_, List<Tag> p_80364_) throws CommandSyntaxException {
                p_80363_.m_99645_(p_80362_, ((Tag)Iterables.getLast(p_80364_))::m_6426_);
            }
        };
        public static final /* enum */ MergeStrategy APPEND = new MergeStrategy("append"){

            @Override
            public void m_6706_(Tag p_80373_, NbtPathArgument.NbtPath p_80374_, List<Tag> p_80375_) throws CommandSyntaxException {
                List<Tag> $$3 = p_80374_.m_99640_(p_80373_, ListTag::new);
                $$3.forEach(p_80371_ -> {
                    if (p_80371_ instanceof ListTag) {
                        p_80375_.forEach(p_165187_ -> ((ListTag)p_80371_).add(p_165187_.m_6426_()));
                    }
                });
            }
        };
        public static final /* enum */ MergeStrategy MERGE = new MergeStrategy("merge"){

            @Override
            public void m_6706_(Tag p_80387_, NbtPathArgument.NbtPath p_80388_, List<Tag> p_80389_) throws CommandSyntaxException {
                List<Tag> $$3 = p_80388_.m_99640_(p_80387_, CompoundTag::new);
                $$3.forEach(p_80385_ -> {
                    if (p_80385_ instanceof CompoundTag) {
                        p_80389_.forEach(p_165190_ -> {
                            if (p_165190_ instanceof CompoundTag) {
                                ((CompoundTag)p_80385_).m_128391_((CompoundTag)p_165190_);
                            }
                        });
                    }
                });
            }
        };
        final String f_80335_;
        private static final /* synthetic */ MergeStrategy[] $VALUES;

        public static MergeStrategy[] values() {
            return (MergeStrategy[])$VALUES.clone();
        }

        public static MergeStrategy valueOf(String p_80355_) {
            return Enum.valueOf(MergeStrategy.class, p_80355_);
        }

        public abstract void m_6706_(Tag var1, NbtPathArgument.NbtPath var2, List<Tag> var3) throws CommandSyntaxException;

        MergeStrategy(String p_80341_) {
            this.f_80335_ = p_80341_;
        }

        public static MergeStrategy m_80349_(String p_80350_) {
            for (MergeStrategy $$1 : MergeStrategy.values()) {
                if (!$$1.f_80335_.equals(p_80350_)) continue;
                return $$1;
            }
            throw new IllegalArgumentException("Invalid merge strategy" + p_80350_);
        }

        private static /* synthetic */ MergeStrategy[] m_165184_() {
            return new MergeStrategy[]{REPLACE, APPEND, MERGE};
        }

        static {
            $VALUES = MergeStrategy.m_165184_();
        }
    }
}

