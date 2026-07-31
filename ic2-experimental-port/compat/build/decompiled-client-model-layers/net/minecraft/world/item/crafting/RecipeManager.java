/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

public class RecipeManager
extends SimpleJsonResourceReloadListener {
    private static final Gson f_44005_ = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger f_44006_ = LogUtils.getLogger();
    private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> f_44007_ = ImmutableMap.of();
    private Map<ResourceLocation, Recipe<?>> f_199900_ = ImmutableMap.of();
    private boolean f_44008_;

    public RecipeManager() {
        super(f_44005_, "recipes");
    }

    @Override
    protected void m_5787_(Map<ResourceLocation, JsonElement> p_44037_, ResourceManager p_44038_, ProfilerFiller p_44039_) {
        this.f_44008_ = false;
        HashMap $$3 = Maps.newHashMap();
        ImmutableMap.Builder $$4 = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> $$5 : p_44037_.entrySet()) {
            ResourceLocation $$6 = $$5.getKey();
            try {
                Recipe<?> $$7 = RecipeManager.m_44045_($$6, GsonHelper.m_13918_($$5.getValue(), "top element"));
                $$3.computeIfAbsent($$7.m_6671_(), p_44075_ -> ImmutableMap.builder()).put((Object)$$6, $$7);
                $$4.put((Object)$$6, $$7);
            }
            catch (JsonParseException | IllegalArgumentException $$8) {
                f_44006_.error("Parsing error loading recipe {}", (Object)$$6, (Object)$$8);
            }
        }
        this.f_44007_ = (Map)$$3.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, p_44033_ -> ((ImmutableMap.Builder)p_44033_.getValue()).build()));
        this.f_199900_ = $$4.build();
        f_44006_.info("Loaded {} recipes", (Object)$$3.size());
    }

    public boolean m_151269_() {
        return this.f_44008_;
    }

    public <C extends Container, T extends Recipe<C>> Optional<T> m_44015_(RecipeType<T> p_44016_, C p_44017_, Level p_44018_) {
        return this.m_44054_(p_44016_).values().stream().filter(p_220266_ -> p_220266_.m_5818_(p_44017_, p_44018_)).findFirst();
    }

    public <C extends Container, T extends Recipe<C>> Optional<Pair<ResourceLocation, T>> m_220248_(RecipeType<T> p_220249_, C p_220250_, Level p_220251_, @Nullable ResourceLocation p_220252_) {
        Recipe $$5;
        Map<ResourceLocation, T> $$4 = this.m_44054_(p_220249_);
        if (p_220252_ != null && ($$5 = (Recipe)$$4.get(p_220252_)) != null && $$5.m_5818_(p_220250_, p_220251_)) {
            return Optional.of(Pair.of((Object)p_220252_, (Object)$$5));
        }
        return $$4.entrySet().stream().filter(p_220245_ -> ((Recipe)p_220245_.getValue()).m_5818_(p_220250_, p_220251_)).findFirst().map(p_220256_ -> Pair.of((Object)((ResourceLocation)p_220256_.getKey()), (Object)((Recipe)p_220256_.getValue())));
    }

    public <C extends Container, T extends Recipe<C>> List<T> m_44013_(RecipeType<T> p_44014_) {
        return List.copyOf(this.m_44054_(p_44014_).values());
    }

    public <C extends Container, T extends Recipe<C>> List<T> m_44056_(RecipeType<T> p_44057_, C p_44058_, Level p_44059_) {
        return this.m_44054_(p_44057_).values().stream().filter(p_220241_ -> p_220241_.m_5818_(p_44058_, p_44059_)).sorted(Comparator.comparing(p_220247_ -> p_220247_.m_8043_().m_41778_())).collect(Collectors.toList());
    }

    private <C extends Container, T extends Recipe<C>> Map<ResourceLocation, T> m_44054_(RecipeType<T> p_44055_) {
        return this.f_44007_.getOrDefault(p_44055_, Collections.emptyMap());
    }

    public <C extends Container, T extends Recipe<C>> NonNullList<ItemStack> m_44069_(RecipeType<T> p_44070_, C p_44071_, Level p_44072_) {
        Optional<T> $$3 = this.m_44015_(p_44070_, p_44071_, p_44072_);
        if ($$3.isPresent()) {
            return ((Recipe)$$3.get()).m_7457_(p_44071_);
        }
        NonNullList<ItemStack> $$4 = NonNullList.m_122780_(p_44071_.m_6643_(), ItemStack.f_41583_);
        for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
            $$4.set($$5, p_44071_.m_8020_($$5));
        }
        return $$4;
    }

    public Optional<? extends Recipe<?>> m_44043_(ResourceLocation p_44044_) {
        return Optional.ofNullable(this.f_199900_.get(p_44044_));
    }

    public Collection<Recipe<?>> m_44051_() {
        return this.f_44007_.values().stream().flatMap(p_220270_ -> p_220270_.values().stream()).collect(Collectors.toSet());
    }

    public Stream<ResourceLocation> m_44073_() {
        return this.f_44007_.values().stream().flatMap(p_220258_ -> p_220258_.keySet().stream());
    }

    public static Recipe<?> m_44045_(ResourceLocation p_44046_, JsonObject p_44047_) {
        String $$2 = GsonHelper.m_13906_(p_44047_, "type");
        return Registry.f_122865_.m_6612_(new ResourceLocation($$2)).orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe type '" + $$2 + "'")).m_6729_(p_44046_, p_44047_);
    }

    public void m_44024_(Iterable<Recipe<?>> p_44025_) {
        this.f_44008_ = false;
        HashMap $$1 = Maps.newHashMap();
        ImmutableMap.Builder $$2 = ImmutableMap.builder();
        p_44025_.forEach(p_220262_ -> {
            Map $$3 = $$1.computeIfAbsent(p_220262_.m_6671_(), p_220272_ -> Maps.newHashMap());
            ResourceLocation $$4 = p_220262_.m_6423_();
            Recipe $$5 = $$3.put($$4, p_220262_);
            $$2.put((Object)$$4, p_220262_);
            if ($$5 != null) {
                throw new IllegalStateException("Duplicate recipe ignored with ID " + $$4);
            }
        });
        this.f_44007_ = ImmutableMap.copyOf((Map)$$1);
        this.f_199900_ = $$2.build();
    }

    public static <C extends Container, T extends Recipe<C>> CachedCheck<C, T> m_220267_(final RecipeType<T> p_220268_) {
        return new CachedCheck<C, T>(){
            @Nullable
            private ResourceLocation f_220274_;

            @Override
            public Optional<T> m_213657_(C p_220278_, Level p_220279_) {
                RecipeManager $$2 = p_220279_.m_7465_();
                Optional $$3 = $$2.m_220248_(p_220268_, p_220278_, p_220279_, this.f_220274_);
                if ($$3.isPresent()) {
                    Pair $$4 = $$3.get();
                    this.f_220274_ = (ResourceLocation)$$4.getFirst();
                    return Optional.of((Recipe)$$4.getSecond());
                }
                return Optional.empty();
            }
        };
    }

    public static interface CachedCheck<C extends Container, T extends Recipe<C>> {
        public Optional<T> m_213657_(C var1, Level var2);
    }
}

