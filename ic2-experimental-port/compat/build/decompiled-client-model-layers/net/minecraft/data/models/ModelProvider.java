/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.data.models;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

public class ModelProvider
implements DataProvider {
    private static final Logger f_125095_ = LogUtils.getLogger();
    private final DataGenerator.PathProvider f_236325_;
    private final DataGenerator.PathProvider f_236326_;

    public ModelProvider(DataGenerator p_125100_) {
        this.f_236325_ = p_125100_.m_236036_(DataGenerator.Target.RESOURCE_PACK, "blockstates");
        this.f_236326_ = p_125100_.m_236036_(DataGenerator.Target.RESOURCE_PACK, "models");
    }

    @Override
    public void m_213708_(CachedOutput p_236330_) {
        HashMap $$1 = Maps.newHashMap();
        Consumer<BlockStateGenerator> $$2 = p_125120_ -> {
            Block $$2 = p_125120_.m_6968_();
            BlockStateGenerator $$3 = $$1.put($$2, p_125120_);
            if ($$3 != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + $$2);
            }
        };
        HashMap $$3 = Maps.newHashMap();
        HashSet $$4 = Sets.newHashSet();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> $$5 = (p_125123_, p_125124_) -> {
            Supplier $$3 = $$3.put(p_125123_, p_125124_);
            if ($$3 != null) {
                throw new IllegalStateException("Duplicate model definition for " + p_125123_);
            }
        };
        Consumer<Item> $$6 = $$4::add;
        new BlockModelGenerators($$2, $$5, $$6).m_124510_();
        new ItemModelGenerators($$5).m_125083_();
        List<Block> $$7 = Registry.f_122824_.m_123024_().filter(p_125117_ -> !$$1.containsKey(p_125117_)).toList();
        if (!$$7.isEmpty()) {
            throw new IllegalStateException("Missing blockstate definitions for: " + $$7);
        }
        Registry.f_122824_.forEach(p_125128_ -> {
            Item $$3 = Item.f_41373_.get(p_125128_);
            if ($$3 != null) {
                if ($$4.contains($$3)) {
                    return;
                }
                ResourceLocation $$4 = ModelLocationUtils.m_125571_($$3);
                if (!$$3.containsKey($$4)) {
                    $$3.put($$4, new DelegatedModel(ModelLocationUtils.m_125576_(p_125128_)));
                }
            }
        });
        this.m_236331_(p_236330_, $$1, p_236328_ -> this.f_236325_.m_236048_(p_236328_.m_204297_().m_205785_().m_135782_()));
        this.m_236331_(p_236330_, $$3, this.f_236326_::m_236048_);
    }

    private <T> void m_236331_(CachedOutput p_236332_, Map<T, ? extends Supplier<JsonElement>> p_236333_, Function<T, Path> p_236334_) {
        p_236333_.forEach((p_236338_, p_236339_) -> {
            Path $$4 = (Path)p_236334_.apply(p_236338_);
            try {
                DataProvider.m_236072_(p_236332_, (JsonElement)p_236339_.get(), $$4);
            }
            catch (Exception $$5) {
                f_125095_.error("Couldn't save {}", (Object)$$4, (Object)$$5);
            }
        });
    }

    @Override
    public String m_6055_() {
        return "Block State Definitions";
    }
}

