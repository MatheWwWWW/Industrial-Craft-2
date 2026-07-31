/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.slf4j.Logger;

public class ItemModifierManager
extends SimpleJsonResourceReloadListener {
    private static final Logger f_165079_ = LogUtils.getLogger();
    private static final Gson f_165080_ = Deserializers.m_78799_().create();
    private final PredicateManager f_165081_;
    private final LootTables f_165082_;
    private Map<ResourceLocation, LootItemFunction> f_165083_ = ImmutableMap.of();

    public ItemModifierManager(PredicateManager p_165086_, LootTables p_165087_) {
        super(f_165080_, "item_modifiers");
        this.f_165081_ = p_165086_;
        this.f_165082_ = p_165087_;
    }

    @Nullable
    public LootItemFunction m_165108_(ResourceLocation p_165109_) {
        return this.f_165083_.get(p_165109_);
    }

    public LootItemFunction m_165110_(ResourceLocation p_165111_, LootItemFunction p_165112_) {
        return this.f_165083_.getOrDefault(p_165111_, p_165112_);
    }

    @Override
    protected void m_5787_(Map<ResourceLocation, JsonElement> p_165105_, ResourceManager p_165106_, ProfilerFiller p_165107_) {
        ImmutableMap.Builder $$3 = ImmutableMap.builder();
        p_165105_.forEach((p_165091_, p_165092_) -> {
            try {
                if (p_165092_.isJsonArray()) {
                    LootItemFunction[] $$3 = (LootItemFunction[])f_165080_.fromJson(p_165092_, LootItemFunction[].class);
                    $$3.put(p_165091_, (Object)new FunctionSequence($$3));
                } else {
                    LootItemFunction $$4 = (LootItemFunction)f_165080_.fromJson(p_165092_, LootItemFunction.class);
                    $$3.put(p_165091_, (Object)$$4);
                }
            }
            catch (Exception $$5) {
                f_165079_.error("Couldn't parse item modifier {}", p_165091_, (Object)$$5);
            }
        });
        ImmutableMap $$4 = $$3.build();
        ValidationContext $$5 = new ValidationContext(LootContextParamSets.f_81420_, this.f_165081_::m_79252_, this.f_165082_::m_79217_);
        $$4.forEach((p_165095_, p_165096_) -> p_165096_.m_6169_($$5));
        $$5.m_79352_().forEach((p_165102_, p_165103_) -> f_165079_.warn("Found item modifier validation problem in {}: {}", p_165102_, p_165103_));
        this.f_165083_ = $$4;
    }

    public Set<ResourceLocation> m_165088_() {
        return Collections.unmodifiableSet(this.f_165083_.keySet());
    }

    static class FunctionSequence
    implements LootItemFunction {
        protected final LootItemFunction[] f_165113_;
        private final BiFunction<ItemStack, LootContext, ItemStack> f_165114_;

        public FunctionSequence(LootItemFunction[] p_165116_) {
            this.f_165113_ = p_165116_;
            this.f_165114_ = LootItemFunctions.m_80770_(p_165116_);
        }

        @Override
        public ItemStack apply(ItemStack p_165119_, LootContext p_165120_) {
            return this.f_165114_.apply(p_165119_, p_165120_);
        }

        @Override
        public LootItemFunctionType m_7162_() {
            throw new UnsupportedOperationException();
        }

        @Override
        public /* synthetic */ Object apply(Object object, Object object2) {
            return this.apply((ItemStack)object, (LootContext)object2);
        }
    }
}

