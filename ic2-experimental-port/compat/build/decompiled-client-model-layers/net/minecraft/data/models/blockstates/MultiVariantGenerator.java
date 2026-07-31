/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Selector;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

public class MultiVariantGenerator
implements BlockStateGenerator {
    private final Block f_125246_;
    private final List<Variant> f_125247_;
    private final Set<Property<?>> f_125248_ = Sets.newHashSet();
    private final List<PropertyDispatch> f_125249_ = Lists.newArrayList();

    private MultiVariantGenerator(Block p_125251_, List<Variant> p_125252_) {
        this.f_125246_ = p_125251_;
        this.f_125247_ = p_125252_;
    }

    public MultiVariantGenerator m_125271_(PropertyDispatch p_125272_) {
        p_125272_.m_7336_().forEach(p_125263_ -> {
            if (this.f_125246_.m_49965_().m_61081_(p_125263_.m_61708_()) != p_125263_) {
                throw new IllegalStateException("Property " + p_125263_ + " is not defined for block " + this.f_125246_);
            }
            if (!this.f_125248_.add((Property<?>)p_125263_)) {
                throw new IllegalStateException("Values of property " + p_125263_ + " already defined for block " + this.f_125246_);
            }
        });
        this.f_125249_.add(p_125272_);
        return this;
    }

    @Override
    public JsonElement get() {
        Stream<Object> $$0 = Stream.of(Pair.of((Object)Selector.m_125485_(), this.f_125247_));
        for (PropertyDispatch $$1 : this.f_125249_) {
            Map<Selector, List<Variant>> $$2 = $$1.m_125293_();
            $$0 = $$0.flatMap(p_125289_ -> $$2.entrySet().stream().map(p_176309_ -> {
                Selector $$2 = ((Selector)p_125289_.getFirst()).m_125488_((Selector)p_176309_.getKey());
                List<Variant> $$3 = MultiVariantGenerator.m_125277_((List)p_125289_.getSecond(), (List)p_176309_.getValue());
                return Pair.of((Object)$$2, $$3);
            }));
        }
        TreeMap $$3 = new TreeMap();
        $$0.forEach(p_125285_ -> $$3.put(((Selector)p_125285_.getFirst()).m_125492_(), Variant.m_125514_((List)p_125285_.getSecond())));
        JsonObject $$4 = new JsonObject();
        $$4.add("variants", (JsonElement)Util.m_137469_(new JsonObject(), p_125282_ -> $$3.forEach((arg_0, arg_1) -> ((JsonObject)p_125282_).add(arg_0, arg_1))));
        return $$4;
    }

    private static List<Variant> m_125277_(List<Variant> p_125278_, List<Variant> p_125279_) {
        ImmutableList.Builder $$2 = ImmutableList.builder();
        p_125278_.forEach(p_125276_ -> p_125279_.forEach(p_176306_ -> $$2.add((Object)Variant.m_125508_(p_125276_, p_176306_))));
        return $$2.build();
    }

    @Override
    public Block m_6968_() {
        return this.f_125246_;
    }

    public static MultiVariantGenerator m_125254_(Block p_125255_) {
        return new MultiVariantGenerator(p_125255_, (List<Variant>)ImmutableList.of((Object)Variant.m_125501_()));
    }

    public static MultiVariantGenerator m_125256_(Block p_125257_, Variant p_125258_) {
        return new MultiVariantGenerator(p_125257_, (List<Variant>)ImmutableList.of((Object)p_125258_));
    }

    public static MultiVariantGenerator m_125259_(Block p_125260_, Variant ... p_125261_) {
        return new MultiVariantGenerator(p_125260_, (List<Variant>)ImmutableList.copyOf((Object[])p_125261_));
    }

    @Override
    public /* synthetic */ Object get() {
        return this.get();
    }
}

