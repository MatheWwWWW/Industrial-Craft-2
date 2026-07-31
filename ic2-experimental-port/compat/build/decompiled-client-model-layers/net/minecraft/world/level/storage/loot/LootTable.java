/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class LootTable {
    static final Logger f_79107_ = LogUtils.getLogger();
    public static final LootTable f_79105_ = new LootTable(LootContextParamSets.f_81410_, new LootPool[0], new LootItemFunction[0]);
    public static final LootContextParamSet f_79106_ = LootContextParamSets.f_81420_;
    final LootContextParamSet f_79108_;
    final LootPool[] f_79109_;
    final LootItemFunction[] f_79110_;
    private final BiFunction<ItemStack, LootContext, ItemStack> f_79111_;

    LootTable(LootContextParamSet p_79114_, LootPool[] p_79115_, LootItemFunction[] p_79116_) {
        this.f_79108_ = p_79114_;
        this.f_79109_ = p_79115_;
        this.f_79110_ = p_79116_;
        this.f_79111_ = LootItemFunctions.m_80770_(p_79116_);
    }

    public static Consumer<ItemStack> m_79142_(Consumer<ItemStack> p_79143_) {
        return p_79146_ -> {
            if (p_79146_.m_41613_() < p_79146_.m_41741_()) {
                p_79143_.accept((ItemStack)p_79146_);
            } else {
                ItemStack $$3;
                for (int $$2 = p_79146_.m_41613_(); $$2 > 0; $$2 -= $$3.m_41613_()) {
                    $$3 = p_79146_.m_41777_();
                    $$3.m_41764_(Math.min(p_79146_.m_41741_(), $$2));
                    p_79143_.accept($$3);
                }
            }
        };
    }

    public void m_79131_(LootContext p_79132_, Consumer<ItemStack> p_79133_) {
        if (p_79132_.m_78934_(this)) {
            Consumer<ItemStack> $$2 = LootItemFunction.m_80724_(this.f_79111_, p_79133_, p_79132_);
            for (LootPool $$3 : this.f_79109_) {
                $$3.m_79053_($$2, p_79132_);
            }
            p_79132_.m_78946_(this);
        } else {
            f_79107_.warn("Detected infinite loop in loot tables");
        }
    }

    public void m_79148_(LootContext p_79149_, Consumer<ItemStack> p_79150_) {
        this.m_79131_(p_79149_, LootTable.m_79142_(p_79150_));
    }

    public ObjectArrayList<ItemStack> m_230922_(LootContext p_230923_) {
        ObjectArrayList $$1 = new ObjectArrayList();
        this.m_79148_(p_230923_, arg_0 -> ((ObjectArrayList)$$1).add(arg_0));
        return $$1;
    }

    public LootContextParamSet m_79122_() {
        return this.f_79108_;
    }

    public void m_79136_(ValidationContext p_79137_) {
        for (int $$1 = 0; $$1 < this.f_79109_.length; ++$$1) {
            this.f_79109_[$$1].m_79051_(p_79137_.m_79365_(".pools[" + $$1 + "]"));
        }
        for (int $$2 = 0; $$2 < this.f_79110_.length; ++$$2) {
            this.f_79110_[$$2].m_6169_(p_79137_.m_79365_(".functions[" + $$2 + "]"));
        }
    }

    public void m_79123_(Container p_79124_, LootContext p_79125_) {
        ObjectArrayList<ItemStack> $$2 = this.m_230922_(p_79125_);
        RandomSource $$3 = p_79125_.m_230907_();
        List<Integer> $$4 = this.m_230919_(p_79124_, $$3);
        this.m_230924_($$2, $$4.size(), $$3);
        for (ItemStack $$5 : $$2) {
            if ($$4.isEmpty()) {
                f_79107_.warn("Tried to over-fill a container");
                return;
            }
            if ($$5.m_41619_()) {
                p_79124_.m_6836_($$4.remove($$4.size() - 1), ItemStack.f_41583_);
                continue;
            }
            p_79124_.m_6836_($$4.remove($$4.size() - 1), $$5);
        }
    }

    private void m_230924_(ObjectArrayList<ItemStack> p_230925_, int p_230926_, RandomSource p_230927_) {
        ArrayList $$3 = Lists.newArrayList();
        ObjectListIterator $$4 = p_230925_.iterator();
        while ($$4.hasNext()) {
            ItemStack $$5 = (ItemStack)$$4.next();
            if ($$5.m_41619_()) {
                $$4.remove();
                continue;
            }
            if ($$5.m_41613_() <= 1) continue;
            $$3.add($$5);
            $$4.remove();
        }
        while (p_230926_ - p_230925_.size() - $$3.size() > 0 && !$$3.isEmpty()) {
            ItemStack $$6 = (ItemStack)$$3.remove(Mth.m_216271_(p_230927_, 0, $$3.size() - 1));
            int $$7 = Mth.m_216271_(p_230927_, 1, $$6.m_41613_() / 2);
            ItemStack $$8 = $$6.m_41620_($$7);
            if ($$6.m_41613_() > 1 && p_230927_.m_188499_()) {
                $$3.add($$6);
            } else {
                p_230925_.add((Object)$$6);
            }
            if ($$8.m_41613_() > 1 && p_230927_.m_188499_()) {
                $$3.add($$8);
                continue;
            }
            p_230925_.add((Object)$$8);
        }
        p_230925_.addAll((Collection)$$3);
        Util.m_214673_(p_230925_, p_230927_);
    }

    private List<Integer> m_230919_(Container p_230920_, RandomSource p_230921_) {
        ObjectArrayList $$2 = new ObjectArrayList();
        for (int $$3 = 0; $$3 < p_230920_.m_6643_(); ++$$3) {
            if (!p_230920_.m_8020_($$3).m_41619_()) continue;
            $$2.add((Object)$$3);
        }
        Util.m_214673_($$2, p_230921_);
        return $$2;
    }

    public static Builder m_79147_() {
        return new Builder();
    }

    public static class Builder
    implements FunctionUserBuilder<Builder> {
        private final List<LootPool> f_79156_ = Lists.newArrayList();
        private final List<LootItemFunction> f_79157_ = Lists.newArrayList();
        private LootContextParamSet f_79158_ = f_79106_;

        public Builder m_79161_(LootPool.Builder p_79162_) {
            this.f_79156_.add(p_79162_.m_79082_());
            return this;
        }

        public Builder m_79165_(LootContextParamSet p_79166_) {
            this.f_79158_ = p_79166_;
            return this;
        }

        @Override
        public Builder m_79078_(LootItemFunction.Builder p_79164_) {
            this.f_79157_.add(p_79164_.m_7453_());
            return this;
        }

        @Override
        public Builder m_79073_() {
            return this;
        }

        public LootTable m_79167_() {
            return new LootTable(this.f_79158_, this.f_79156_.toArray(new LootPool[0]), this.f_79157_.toArray(new LootItemFunction[0]));
        }

        @Override
        public /* synthetic */ FunctionUserBuilder m_79073_() {
            return this.m_79073_();
        }

        @Override
        public /* synthetic */ FunctionUserBuilder m_79078_(LootItemFunction.Builder builder) {
            return this.m_79078_(builder);
        }
    }

    public static class Serializer
    implements JsonDeserializer<LootTable>,
    JsonSerializer<LootTable> {
        public LootTable deserialize(JsonElement p_79173_, Type p_79174_, JsonDeserializationContext p_79175_) throws JsonParseException {
            JsonObject $$3 = GsonHelper.m_13918_(p_79173_, "loot table");
            LootPool[] $$4 = GsonHelper.m_13845_($$3, "pools", new LootPool[0], p_79175_, LootPool[].class);
            LootContextParamSet $$5 = null;
            if ($$3.has("type")) {
                String $$6 = GsonHelper.m_13906_($$3, "type");
                $$5 = LootContextParamSets.m_81431_(new ResourceLocation($$6));
            }
            LootItemFunction[] $$7 = GsonHelper.m_13845_($$3, "functions", new LootItemFunction[0], p_79175_, LootItemFunction[].class);
            return new LootTable($$5 != null ? $$5 : LootContextParamSets.f_81420_, $$4, $$7);
        }

        public JsonElement serialize(LootTable p_79177_, Type p_79178_, JsonSerializationContext p_79179_) {
            JsonObject $$3 = new JsonObject();
            if (p_79177_.f_79108_ != f_79106_) {
                ResourceLocation $$4 = LootContextParamSets.m_81426_(p_79177_.f_79108_);
                if ($$4 != null) {
                    $$3.addProperty("type", $$4.toString());
                } else {
                    f_79107_.warn("Failed to find id for param set {}", (Object)p_79177_.f_79108_);
                }
            }
            if (p_79177_.f_79109_.length > 0) {
                $$3.add("pools", p_79179_.serialize((Object)p_79177_.f_79109_));
            }
            if (!ArrayUtils.isEmpty((Object[])p_79177_.f_79110_)) {
                $$3.add("functions", p_79179_.serialize((Object)p_79177_.f_79110_));
            }
            return $$3;
        }

        public /* synthetic */ JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((LootTable)object, type, jsonSerializationContext);
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

