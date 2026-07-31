/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.fixes.ChunkHeightAndBiomeFix;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.lang3.mutable.MutableInt;

public class ChunkProtoTickListFix
extends DataFix {
    private static final int f_184984_ = 16;
    private static final ImmutableSet<String> f_184985_ = ImmutableSet.of((Object)"minecraft:bubble_column", (Object)"minecraft:kelp", (Object)"minecraft:kelp_plant", (Object)"minecraft:seagrass", (Object)"minecraft:tall_seagrass");

    public ChunkProtoTickListFix(Schema p_184988_) {
        super(p_184988_, false);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        OpticFinder $$1 = $$0.findField("Level");
        OpticFinder $$2 = $$1.type().findField("Sections");
        OpticFinder $$3 = ((List.ListType)$$2.type()).getElement().finder();
        OpticFinder $$4 = $$3.type().findField("block_states");
        OpticFinder $$5 = $$3.type().findField("biomes");
        OpticFinder $$6 = $$4.type().findField("palette");
        OpticFinder $$7 = $$1.type().findField("TileTicks");
        return this.fixTypeEverywhereTyped("ChunkProtoTickListFix", $$0, p_185002_ -> p_185002_.updateTyped($$1, p_185010_ -> {
            p_185010_ = p_185010_.update(DSL.remainderFinder(), p_185078_ -> (Dynamic)DataFixUtils.orElse(p_185078_.get("LiquidTicks").result().map(p_185072_ -> p_185078_.set("fluid_ticks", p_185072_).remove("LiquidTicks")), (Object)p_185078_));
            Dynamic $$7 = (Dynamic)p_185010_.get(DSL.remainderFinder());
            MutableInt $$8 = new MutableInt();
            Int2ObjectArrayMap $$9 = new Int2ObjectArrayMap();
            p_185010_.getOptionalTyped($$2).ifPresent(arg_0 -> ChunkProtoTickListFix.m_185011_($$3, $$5, $$8, $$4, (Int2ObjectMap)$$9, $$6, arg_0));
            byte $$10 = $$8.getValue().byteValue();
            p_185010_ = p_185010_.update(DSL.remainderFinder(), p_184991_ -> p_184991_.update("yPos", p_185067_ -> p_185067_.createByte($$10)));
            if (p_185010_.getOptionalTyped($$7).isPresent() || $$7.get("fluid_ticks").result().isPresent()) {
                return p_185010_;
            }
            int $$11 = $$7.get("xPos").asInt(0);
            int $$12 = $$7.get("zPos").asInt(0);
            Dynamic<?> $$13 = this.m_185036_($$7, (Int2ObjectMap<Supplier<PoorMansPalettedContainer>>)$$9, $$10, $$11, $$12, "LiquidsToBeTicked", ChunkProtoTickListFix::m_185068_);
            Dynamic<?> $$14 = this.m_185036_($$7, (Int2ObjectMap<Supplier<PoorMansPalettedContainer>>)$$9, $$10, $$11, $$12, "ToBeTicked", ChunkProtoTickListFix::m_185031_);
            Optional $$15 = $$7.type().readTyped($$14).result();
            if ($$15.isPresent()) {
                p_185010_ = p_185010_.set($$7, (Typed)((Pair)$$15.get()).getFirst());
            }
            return p_185010_.update(DSL.remainderFinder(), p_185035_ -> p_185035_.remove("ToBeTicked").remove("LiquidsToBeTicked").set("fluid_ticks", $$13));
        }));
    }

    private Dynamic<?> m_185036_(Dynamic<?> p_185037_, Int2ObjectMap<Supplier<PoorMansPalettedContainer>> p_185038_, byte p_185039_, int p_185040_, int p_185041_, String p_185042_, Function<Dynamic<?>, String> p_185043_) {
        Stream<Object> $$7 = Stream.empty();
        List $$8 = p_185037_.get(p_185042_).asList(Function.identity());
        for (int $$9 = 0; $$9 < $$8.size(); ++$$9) {
            int $$10 = $$9 + p_185039_;
            Supplier $$11 = (Supplier)p_185038_.get($$10);
            Stream<Dynamic> $$12 = ((Dynamic)$$8.get($$9)).asStream().mapToInt(p_185074_ -> p_185074_.asShort((short)-1)).filter(p_184993_ -> p_184993_ > 0).mapToObj(arg_0 -> this.m_185052_(p_185037_, (Supplier)$$11, p_185040_, $$10, p_185041_, p_185043_, arg_0));
            $$7 = Stream.concat($$7, $$12);
        }
        return p_185037_.createList($$7);
    }

    private static String m_185031_(@Nullable Dynamic<?> p_185032_) {
        return p_185032_ != null ? p_185032_.get("Name").asString("minecraft:air") : "minecraft:air";
    }

    private static String m_185068_(@Nullable Dynamic<?> p_185069_) {
        if (p_185069_ == null) {
            return "minecraft:empty";
        }
        String $$1 = p_185069_.get("Name").asString("");
        if ("minecraft:water".equals($$1)) {
            return p_185069_.get("Properties").get("level").asInt(0) == 0 ? "minecraft:water" : "minecraft:flowing_water";
        }
        if ("minecraft:lava".equals($$1)) {
            return p_185069_.get("Properties").get("level").asInt(0) == 0 ? "minecraft:lava" : "minecraft:flowing_lava";
        }
        if (f_184985_.contains((Object)$$1) || p_185069_.get("Properties").get("waterlogged").asBoolean(false)) {
            return "minecraft:water";
        }
        return "minecraft:empty";
    }

    private Dynamic<?> m_185044_(Dynamic<?> p_185045_, @Nullable Supplier<PoorMansPalettedContainer> p_185046_, int p_185047_, int p_185048_, int p_185049_, int p_185050_, Function<Dynamic<?>, String> p_185051_) {
        int $$7 = p_185050_ & 0xF;
        int $$8 = p_185050_ >>> 4 & 0xF;
        int $$9 = p_185050_ >>> 8 & 0xF;
        String $$10 = p_185051_.apply(p_185046_ != null ? p_185046_.get().m_185090_($$7, $$8, $$9) : null);
        return p_185045_.createMap((Map)ImmutableMap.builder().put((Object)p_185045_.createString("i"), (Object)p_185045_.createString($$10)).put((Object)p_185045_.createString("x"), (Object)p_185045_.createInt(p_185047_ * 16 + $$7)).put((Object)p_185045_.createString("y"), (Object)p_185045_.createInt(p_185048_ * 16 + $$8)).put((Object)p_185045_.createString("z"), (Object)p_185045_.createInt(p_185049_ * 16 + $$9)).put((Object)p_185045_.createString("t"), (Object)p_185045_.createInt(0)).put((Object)p_185045_.createString("p"), (Object)p_185045_.createInt(0)).build());
    }

    private /* synthetic */ Dynamic m_185052_(Dynamic p_185053_, Supplier p_185054_, int p_185055_, int p_185056_, int p_185057_, Function p_185058_, int p_185059_) {
        return this.m_185044_(p_185053_, p_185054_, p_185055_, p_185056_, p_185057_, p_185059_, p_185058_);
    }

    private static /* synthetic */ void m_185011_(OpticFinder p_185012_, OpticFinder p_185013_, MutableInt p_185014_, OpticFinder p_185015_, Int2ObjectMap p_185016_, OpticFinder p_185017_, Typed p_185018_) {
        p_185018_.getAllTyped(p_185012_).forEach(p_185025_ -> {
            Dynamic $$6 = (Dynamic)p_185025_.get(DSL.remainderFinder());
            int $$7 = $$6.get("Y").asInt(Integer.MAX_VALUE);
            if ($$7 == Integer.MAX_VALUE) {
                return;
            }
            if (p_185025_.getOptionalTyped(p_185013_).isPresent()) {
                p_185014_.setValue(Math.min($$7, p_185014_.getValue()));
            }
            p_185025_.getOptionalTyped(p_185015_).ifPresent(p_185064_ -> p_185016_.put($$7, (Object)Suppliers.memoize(() -> {
                List $$2 = p_185064_.getOptionalTyped(p_185017_).map(p_185027_ -> p_185027_.write().result().map(p_185076_ -> p_185076_.asList(Function.identity())).orElse(Collections.emptyList())).orElse(Collections.emptyList());
                long[] $$3 = ((Dynamic)p_185064_.get(DSL.remainderFinder())).get("data").asLongStream().toArray();
                return new PoorMansPalettedContainer($$2, $$3);
            })));
        });
    }

    public static final class PoorMansPalettedContainer {
        private static final long f_185080_ = 4L;
        private final List<? extends Dynamic<?>> f_185081_;
        private final long[] f_185082_;
        private final int f_185083_;
        private final long f_185084_;
        private final int f_185085_;

        public PoorMansPalettedContainer(List<? extends Dynamic<?>> p_185087_, long[] p_185088_) {
            this.f_185081_ = p_185087_;
            this.f_185082_ = p_185088_;
            this.f_185083_ = Math.max(4, ChunkHeightAndBiomeFix.m_184865_(p_185087_.size()));
            this.f_185084_ = (1L << this.f_185083_) - 1L;
            this.f_185085_ = (char)(64 / this.f_185083_);
        }

        @Nullable
        public Dynamic<?> m_185090_(int p_185091_, int p_185092_, int p_185093_) {
            int $$3 = this.f_185081_.size();
            if ($$3 < 1) {
                return null;
            }
            if ($$3 == 1) {
                return this.f_185081_.get(0);
            }
            int $$4 = this.m_185095_(p_185091_, p_185092_, p_185093_);
            int $$5 = $$4 / this.f_185085_;
            if ($$5 < 0 || $$5 >= this.f_185082_.length) {
                return null;
            }
            long $$6 = this.f_185082_[$$5];
            int $$7 = ($$4 - $$5 * this.f_185085_) * this.f_185083_;
            int $$8 = (int)($$6 >> $$7 & this.f_185084_);
            if ($$8 < 0 || $$8 >= $$3) {
                return null;
            }
            return this.f_185081_.get($$8);
        }

        private int m_185095_(int p_185096_, int p_185097_, int p_185098_) {
            return (p_185097_ << 4 | p_185098_) << 4 | p_185096_;
        }

        public List<? extends Dynamic<?>> m_185089_() {
            return this.f_185081_;
        }

        public long[] m_185094_() {
            return this.f_185082_;
        }
    }
}

