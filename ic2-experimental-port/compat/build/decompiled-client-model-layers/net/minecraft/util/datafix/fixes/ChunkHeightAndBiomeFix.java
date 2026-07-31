/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.OptionalDynamic
 *  it.unimi.dsi.fastutil.ints.Int2IntFunction
 *  it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.ChunkProtoTickListFix;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;

public class ChunkHeightAndBiomeFix
extends DataFix {
    public static final String f_184843_ = "__context";
    private static final String f_184845_ = "ChunkHeightAndBiomeFix";
    private static final int f_184846_ = 16;
    private static final int f_184847_ = 24;
    private static final int f_184848_ = -4;
    public static final int f_196581_ = 4096;
    private static final int f_196582_ = 64;
    private static final int f_184850_ = 9;
    private static final long f_184851_ = 511L;
    private static final int f_184852_ = 64;
    private static final String[] f_184853_ = new String[]{"WORLD_SURFACE_WG", "WORLD_SURFACE", "WORLD_SURFACE_IGNORE_SNOW", "OCEAN_FLOOR_WG", "OCEAN_FLOOR", "MOTION_BLOCKING", "MOTION_BLOCKING_NO_LEAVES"};
    private static final Set<String> f_184854_ = Set.of("surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
    private static final Set<String> f_184855_ = Set.of("noise", "surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
    private static final Set<String> f_184856_ = Set.of("minecraft:air", "minecraft:basalt", "minecraft:bedrock", "minecraft:blackstone", "minecraft:calcite", "minecraft:cave_air", "minecraft:coarse_dirt", "minecraft:crimson_nylium", "minecraft:dirt", "minecraft:end_stone", "minecraft:grass_block", "minecraft:gravel", "minecraft:ice", "minecraft:lava", "minecraft:mycelium", "minecraft:nether_wart_block", "minecraft:netherrack", "minecraft:orange_terracotta", "minecraft:packed_ice", "minecraft:podzol", "minecraft:powder_snow", "minecraft:red_sand", "minecraft:red_sandstone", "minecraft:sand", "minecraft:sandstone", "minecraft:snow_block", "minecraft:soul_sand", "minecraft:soul_soil", "minecraft:stone", "minecraft:terracotta", "minecraft:warped_nylium", "minecraft:warped_wart_block", "minecraft:water", "minecraft:white_terracotta");
    private static final int f_184857_ = 16;
    private static final int f_184858_ = 64;
    private static final int f_184859_ = 1008;
    public static final String f_184844_ = "minecraft:plains";
    private static final Int2ObjectMap<String> f_184860_ = new Int2ObjectOpenHashMap();

    public ChunkHeightAndBiomeFix(Schema p_184863_) {
        super(p_184863_, true);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        OpticFinder $$1 = $$0.findField("Level");
        OpticFinder $$2 = $$1.type().findField("Sections");
        Schema $$3 = this.getOutputSchema();
        Type $$4 = $$3.getType(References.f_16773_);
        Type $$5 = $$4.findField("Level").type();
        Type $$6 = $$5.findField("Sections").type();
        return this.fixTypeEverywhereTyped(f_184845_, $$0, $$4, p_184879_ -> p_184879_.updateTyped($$1, $$5, p_184884_ -> {
            Dynamic $$4 = (Dynamic)p_184884_.get(DSL.remainderFinder());
            OptionalDynamic $$5 = ((Dynamic)p_184879_.get(DSL.remainderFinder())).get(f_184843_);
            String $$6 = $$5.get("dimension").asString().result().orElse("");
            String $$7 = $$5.get("generator").asString().result().orElse("");
            boolean $$8 = "minecraft:overworld".equals($$6);
            MutableBoolean $$9 = new MutableBoolean();
            int $$10 = $$8 ? -4 : 0;
            Dynamic<?>[] $$11 = ChunkHeightAndBiomeFix.m_184906_($$4, $$8, $$10, $$9);
            Dynamic<?> $$12 = ChunkHeightAndBiomeFix.m_184969_($$4.createList(Stream.of($$4.createMap((Map)ImmutableMap.of((Object)$$4.createString("Name"), (Object)$$4.createString("minecraft:air"))))));
            HashSet $$13 = Sets.newHashSet();
            MutableObject $$14 = new MutableObject(() -> null);
            p_184884_ = p_184884_.updateTyped($$2, $$6, p_184936_ -> {
                IntOpenHashSet $$8 = new IntOpenHashSet();
                Dynamic $$9 = (Dynamic)p_184936_.write().result().orElseThrow(() -> new IllegalStateException("Malformed Chunk.Level.Sections"));
                List $$10 = $$9.asStream().map(arg_0 -> ChunkHeightAndBiomeFix.m_184920_($$13, $$12, $$10, $$11, (IntSet)$$8, $$14, arg_0)).collect(Collectors.toCollection(ArrayList::new));
                for (int $$11 = 0; $$11 < $$11.length; ++$$11) {
                    int $$12 = $$11 + $$10;
                    if (!$$8.add($$12)) continue;
                    Dynamic $$13 = $$4.createMap(Map.of($$4.createString("Y"), $$4.createInt($$12)));
                    $$13 = $$13.set("block_states", $$12);
                    $$13 = $$13.set("biomes", $$11[$$11]);
                    $$10.add($$13);
                }
                return (Typed)((Pair)$$6.readTyped($$4.createList($$10.stream())).result().orElseThrow(() -> new IllegalStateException("ChunkHeightAndBiomeFix failed."))).getFirst();
            });
            return p_184884_.update(DSL.remainderFinder(), p_184947_ -> {
                if ($$8) {
                    p_184947_ = this.m_184903_((Dynamic<?>)p_184947_, $$13);
                }
                return ChunkHeightAndBiomeFix.m_184911_(p_184947_, $$8, $$9.booleanValue(), "minecraft:noise".equals($$7), (Supplier)$$14.getValue());
            });
        }));
    }

    private Dynamic<?> m_184903_(Dynamic<?> p_184904_, Set<String> p_184905_) {
        return p_184904_.update("Status", p_184919_ -> {
            boolean $$4;
            String $$2 = p_184919_.asString("empty");
            if (f_184854_.contains($$2)) {
                return p_184919_;
            }
            p_184905_.remove("minecraft:air");
            boolean $$3 = !p_184905_.isEmpty();
            p_184905_.removeAll(f_184856_);
            boolean bl = $$4 = !p_184905_.isEmpty();
            if ($$4) {
                return p_184919_.createString("liquid_carvers");
            }
            if ("noise".equals($$2) || $$3) {
                return p_184919_.createString("noise");
            }
            if ("biomes".equals($$2)) {
                return p_184919_.createString("structure_references");
            }
            return p_184919_;
        });
    }

    private static Dynamic<?>[] m_184906_(Dynamic<?> p_184907_, boolean p_184908_, int p_184909_, MutableBoolean p_184910_) {
        Object[] $$4 = new Dynamic[p_184908_ ? 24 : 16];
        int[] $$5 = p_184907_.get("Biomes").asIntStreamOpt().result().map(IntStream::toArray).orElse(null);
        if ($$5 != null && $$5.length == 1536) {
            p_184910_.setValue(true);
            for (int $$6 = 0; $$6 < 24; ++$$6) {
                int $$7 = $$6;
                $$4[$$6] = ChunkHeightAndBiomeFix.m_184894_(p_184907_, p_184967_ -> ChunkHeightAndBiomeFix.m_184948_($$5, $$7 * 64 + p_184967_));
            }
        } else if ($$5 != null && $$5.length == 1024) {
            int $$8 = 0;
            while ($$8 < 16) {
                int $$9 = $$8 - p_184909_;
                int $$10 = $$8++;
                $$4[$$9] = ChunkHeightAndBiomeFix.m_184894_(p_184907_, p_184954_ -> ChunkHeightAndBiomeFix.m_184948_($$5, $$10 * 64 + p_184954_));
            }
            if (p_184908_) {
                Dynamic<?> $$11 = ChunkHeightAndBiomeFix.m_184894_(p_184907_, p_184976_ -> ChunkHeightAndBiomeFix.m_184948_($$5, p_184976_ % 16));
                Dynamic<?> $$12 = ChunkHeightAndBiomeFix.m_184894_(p_184907_, p_184963_ -> ChunkHeightAndBiomeFix.m_184948_($$5, p_184963_ % 16 + 1008));
                for (int $$13 = 0; $$13 < 4; ++$$13) {
                    $$4[$$13] = $$11;
                }
                for (int $$14 = 20; $$14 < 24; ++$$14) {
                    $$4[$$14] = $$12;
                }
            }
        } else {
            Arrays.fill($$4, ChunkHeightAndBiomeFix.m_184969_(p_184907_.createList(Stream.of(p_184907_.createString(f_184844_)))));
        }
        return $$4;
    }

    private static int m_184948_(int[] p_184949_, int p_184950_) {
        return p_184949_[p_184950_] & 0xFF;
    }

    private static Dynamic<?> m_184911_(Dynamic<?> p_184912_, boolean p_184913_, boolean p_184914_, boolean p_184915_, Supplier<ChunkProtoTickListFix.PoorMansPalettedContainer> p_184916_) {
        Dynamic $$6;
        String $$7;
        p_184912_ = p_184912_.remove("Biomes");
        if (!p_184913_) {
            return ChunkHeightAndBiomeFix.m_184887_(p_184912_, 16, 0);
        }
        if (p_184914_) {
            return ChunkHeightAndBiomeFix.m_184887_(p_184912_, 24, 0);
        }
        p_184912_ = ChunkHeightAndBiomeFix.m_184885_(p_184912_);
        p_184912_ = ChunkHeightAndBiomeFix.m_184900_(p_184912_, "Lights");
        p_184912_ = ChunkHeightAndBiomeFix.m_184900_(p_184912_, "LiquidsToBeTicked");
        p_184912_ = ChunkHeightAndBiomeFix.m_184900_(p_184912_, "PostProcessing");
        p_184912_ = ChunkHeightAndBiomeFix.m_184900_(p_184912_, "ToBeTicked");
        p_184912_ = ChunkHeightAndBiomeFix.m_184887_(p_184912_, 24, 4);
        p_184912_ = p_184912_.update("UpgradeData", ChunkHeightAndBiomeFix::m_196590_);
        if (!p_184915_) {
            return p_184912_;
        }
        Optional $$5 = p_184912_.get("Status").result();
        if ($$5.isPresent() && !"empty".equals($$7 = ($$6 = (Dynamic)$$5.get()).asString(""))) {
            p_184912_ = p_184912_.set("blending_data", p_184912_.createMap((Map)ImmutableMap.of((Object)p_184912_.createString("old_noise"), (Object)p_184912_.createBoolean(f_184855_.contains($$7)))));
            ChunkProtoTickListFix.PoorMansPalettedContainer $$8 = p_184916_.get();
            if ($$8 != null) {
                BitSet $$9 = new BitSet(256);
                boolean $$10 = $$7.equals("noise");
                for (int $$11 = 0; $$11 < 16; ++$$11) {
                    for (int $$12 = 0; $$12 < 16; ++$$12) {
                        boolean $$15;
                        Dynamic<?> $$13 = $$8.m_185090_($$12, 0, $$11);
                        boolean $$14 = $$13 != null && "minecraft:bedrock".equals($$13.get("Name").asString(""));
                        boolean bl = $$15 = $$13 != null && "minecraft:air".equals($$13.get("Name").asString(""));
                        if ($$15) {
                            $$9.set($$11 * 16 + $$12);
                        }
                        $$10 |= $$14;
                    }
                }
                if ($$10 && $$9.cardinality() != $$9.size()) {
                    Dynamic $$16 = "full".equals($$7) ? p_184912_.createString("heightmaps") : $$6;
                    p_184912_ = p_184912_.set("below_zero_retrogen", p_184912_.createMap((Map)ImmutableMap.of((Object)p_184912_.createString("target_status"), (Object)$$16, (Object)p_184912_.createString("missing_bedrock"), (Object)p_184912_.createLongList(LongStream.of($$9.toLongArray())))));
                    p_184912_ = p_184912_.set("Status", p_184912_.createString("empty"));
                }
                p_184912_ = p_184912_.set("isLightOn", p_184912_.createBoolean(false));
            }
        }
        return p_184912_;
    }

    private static <T> Dynamic<T> m_196590_(Dynamic<T> p_196591_) {
        return p_196591_.update("Indices", p_196614_ -> {
            HashMap $$1 = new HashMap();
            p_196614_.getMapValues().result().ifPresent(p_196610_ -> p_196610_.forEach((p_196601_, p_196602_) -> {
                try {
                    p_196601_.asString().result().map(Integer::parseInt).ifPresent(p_196607_ -> {
                        int $$4 = p_196607_ - -4;
                        $$1.put(p_196601_.createString(Integer.toString($$4)), p_196602_);
                    });
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }));
            return p_196614_.createMap($$1);
        });
    }

    private static Dynamic<?> m_184887_(Dynamic<?> p_184888_, int p_184889_, int p_184890_) {
        Dynamic $$3 = p_184888_.get("CarvingMasks").orElseEmptyMap();
        $$3 = $$3.updateMapValues(p_196587_ -> {
            long[] $$4 = BitSet.valueOf(((Dynamic)p_196587_.getSecond()).asByteBuffer().array()).toLongArray();
            long[] $$5 = new long[64 * p_184889_];
            System.arraycopy($$4, 0, $$5, 64 * p_184890_, $$4.length);
            return Pair.of((Object)((Dynamic)p_196587_.getFirst()), (Object)p_184888_.createLongList(LongStream.of($$5)));
        });
        return p_184888_.set("CarvingMasks", $$3);
    }

    private static Dynamic<?> m_184900_(Dynamic<?> p_184901_, String p_184902_) {
        List $$2 = p_184901_.get(p_184902_).orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
        if ($$2.size() == 24) {
            return p_184901_;
        }
        Dynamic $$3 = p_184901_.emptyList();
        for (int $$4 = 0; $$4 < 4; ++$$4) {
            $$2.add(0, $$3);
            $$2.add($$3);
        }
        return p_184901_.set(p_184902_, p_184901_.createList($$2.stream()));
    }

    private static Dynamic<?> m_184885_(Dynamic<?> p_184886_) {
        return p_184886_.update("Heightmaps", p_196612_ -> {
            for (String $$1 : f_184853_) {
                p_196612_ = p_196612_.update($$1, ChunkHeightAndBiomeFix::m_184956_);
            }
            return p_196612_;
        });
    }

    private static Dynamic<?> m_184956_(Dynamic<?> p_184957_) {
        return p_184957_.createLongList(p_184957_.asLongStream().map(p_196589_ -> {
            long $$1 = 0L;
            int $$2 = 0;
            while ($$2 + 9 <= 64) {
                long $$5;
                long $$3 = p_196589_ >> $$2 & 0x1FFL;
                if ($$3 == 0L) {
                    long $$4 = 0L;
                } else {
                    $$5 = Math.min($$3 + 64L, 511L);
                }
                $$1 |= $$5 << $$2;
                $$2 += 9;
            }
            return $$1;
        }));
    }

    private static Dynamic<?> m_184894_(Dynamic<?> p_184895_, Int2IntFunction p_184896_) {
        Int2IntLinkedOpenHashMap $$2 = new Int2IntLinkedOpenHashMap();
        for (int $$3 = 0; $$3 < 64; ++$$3) {
            int $$4 = p_184896_.applyAsInt($$3);
            if ($$2.containsKey($$4)) continue;
            $$2.put($$4, $$2.size());
        }
        Dynamic $$5 = p_184895_.createList($$2.keySet().stream().map(p_196598_ -> p_184895_.createString((String)f_184860_.getOrDefault(p_196598_.intValue(), (Object)f_184844_))));
        int $$6 = ChunkHeightAndBiomeFix.m_184865_($$2.size());
        if ($$6 == 0) {
            return ChunkHeightAndBiomeFix.m_184969_($$5);
        }
        int $$7 = 64 / $$6;
        int $$8 = (64 + $$7 - 1) / $$7;
        long[] $$9 = new long[$$8];
        int $$10 = 0;
        int $$11 = 0;
        for (int $$12 = 0; $$12 < 64; ++$$12) {
            int $$13 = p_184896_.applyAsInt($$12);
            int n = $$10++;
            $$9[n] = $$9[n] | (long)$$2.get($$13) << $$11;
            if (($$11 += $$6) + $$6 <= 64) continue;
            $$11 = 0;
        }
        Dynamic $$14 = p_184895_.createLongList(Arrays.stream($$9));
        return ChunkHeightAndBiomeFix.m_184891_($$5, $$14);
    }

    private static Dynamic<?> m_184969_(Dynamic<?> p_184970_) {
        return p_184970_.createMap((Map)ImmutableMap.of((Object)p_184970_.createString("palette"), p_184970_));
    }

    private static Dynamic<?> m_184891_(Dynamic<?> p_184892_, Dynamic<?> p_184893_) {
        return p_184892_.createMap((Map)ImmutableMap.of((Object)p_184892_.createString("palette"), p_184892_, (Object)p_184892_.createString("data"), p_184893_));
    }

    private static Dynamic<?> m_184958_(Dynamic<?> p_184959_, Dynamic<?> p_184960_) {
        List $$2 = p_184959_.asStream().collect(Collectors.toCollection(ArrayList::new));
        if ($$2.size() == 1) {
            return ChunkHeightAndBiomeFix.m_184969_(p_184959_);
        }
        p_184959_ = ChunkHeightAndBiomeFix.m_196592_(p_184959_, p_184960_, $$2);
        return ChunkHeightAndBiomeFix.m_184891_(p_184959_, p_184960_);
    }

    private static Dynamic<?> m_196592_(Dynamic<?> p_196593_, Dynamic<?> p_196594_, List<Dynamic<?>> p_196595_) {
        int $$5;
        int $$6;
        long $$3 = p_196594_.asLongStream().count() * 64L;
        long $$4 = $$3 / 4096L;
        if ($$4 > (long)($$6 = ChunkHeightAndBiomeFix.m_184865_($$5 = p_196595_.size()))) {
            Dynamic $$7 = p_196593_.createMap((Map)ImmutableMap.of((Object)p_196593_.createString("Name"), (Object)p_196593_.createString("minecraft:air")));
            int $$8 = (1 << (int)($$4 - 1L)) + 1;
            int $$9 = $$8 - $$5;
            for (int $$10 = 0; $$10 < $$9; ++$$10) {
                p_196595_.add($$7);
            }
            return p_196593_.createList(p_196595_.stream());
        }
        return p_196593_;
    }

    public static int m_184865_(int p_184866_) {
        if (p_184866_ == 0) {
            return 0;
        }
        return (int)Math.ceil(Math.log(p_184866_) / Math.log(2.0));
    }

    private static /* synthetic */ Dynamic m_184920_(Set p_184921_, Dynamic p_184922_, int p_184923_, Dynamic[] p_184924_, IntSet p_184925_, MutableObject p_184926_, Dynamic p_184927_) {
        int $$7 = p_184927_.get("Y").asInt(0);
        Dynamic $$8 = (Dynamic)DataFixUtils.orElse(p_184927_.get("Palette").result().flatMap(p_184940_ -> {
            p_184940_.asStream().map(p_184982_ -> p_184982_.get("Name").asString("minecraft:air")).forEach(p_184921_::add);
            return p_184927_.get("BlockStates").result().map(p_184973_ -> ChunkHeightAndBiomeFix.m_184958_(p_184940_, p_184973_));
        }), (Object)p_184922_);
        Dynamic $$9 = p_184927_;
        int $$10 = $$7 - p_184923_;
        if ($$10 >= 0 && $$10 < p_184924_.length) {
            $$9 = $$9.set("biomes", p_184924_[$$10]);
        }
        p_184925_.add($$7);
        if (p_184927_.get("Y").asInt(Integer.MAX_VALUE) == 0) {
            p_184926_.setValue(() -> {
                List $$1 = $$8.get("palette").asList(Function.identity());
                long[] $$2 = $$8.get("data").asLongStream().toArray();
                return new ChunkProtoTickListFix.PoorMansPalettedContainer($$1, $$2);
            });
        }
        return $$9.set("block_states", $$8).remove("Palette").remove("BlockStates");
    }

    static {
        f_184860_.put(0, (Object)"minecraft:ocean");
        f_184860_.put(1, (Object)f_184844_);
        f_184860_.put(2, (Object)"minecraft:desert");
        f_184860_.put(3, (Object)"minecraft:mountains");
        f_184860_.put(4, (Object)"minecraft:forest");
        f_184860_.put(5, (Object)"minecraft:taiga");
        f_184860_.put(6, (Object)"minecraft:swamp");
        f_184860_.put(7, (Object)"minecraft:river");
        f_184860_.put(8, (Object)"minecraft:nether_wastes");
        f_184860_.put(9, (Object)"minecraft:the_end");
        f_184860_.put(10, (Object)"minecraft:frozen_ocean");
        f_184860_.put(11, (Object)"minecraft:frozen_river");
        f_184860_.put(12, (Object)"minecraft:snowy_tundra");
        f_184860_.put(13, (Object)"minecraft:snowy_mountains");
        f_184860_.put(14, (Object)"minecraft:mushroom_fields");
        f_184860_.put(15, (Object)"minecraft:mushroom_field_shore");
        f_184860_.put(16, (Object)"minecraft:beach");
        f_184860_.put(17, (Object)"minecraft:desert_hills");
        f_184860_.put(18, (Object)"minecraft:wooded_hills");
        f_184860_.put(19, (Object)"minecraft:taiga_hills");
        f_184860_.put(20, (Object)"minecraft:mountain_edge");
        f_184860_.put(21, (Object)"minecraft:jungle");
        f_184860_.put(22, (Object)"minecraft:jungle_hills");
        f_184860_.put(23, (Object)"minecraft:jungle_edge");
        f_184860_.put(24, (Object)"minecraft:deep_ocean");
        f_184860_.put(25, (Object)"minecraft:stone_shore");
        f_184860_.put(26, (Object)"minecraft:snowy_beach");
        f_184860_.put(27, (Object)"minecraft:birch_forest");
        f_184860_.put(28, (Object)"minecraft:birch_forest_hills");
        f_184860_.put(29, (Object)"minecraft:dark_forest");
        f_184860_.put(30, (Object)"minecraft:snowy_taiga");
        f_184860_.put(31, (Object)"minecraft:snowy_taiga_hills");
        f_184860_.put(32, (Object)"minecraft:giant_tree_taiga");
        f_184860_.put(33, (Object)"minecraft:giant_tree_taiga_hills");
        f_184860_.put(34, (Object)"minecraft:wooded_mountains");
        f_184860_.put(35, (Object)"minecraft:savanna");
        f_184860_.put(36, (Object)"minecraft:savanna_plateau");
        f_184860_.put(37, (Object)"minecraft:badlands");
        f_184860_.put(38, (Object)"minecraft:wooded_badlands_plateau");
        f_184860_.put(39, (Object)"minecraft:badlands_plateau");
        f_184860_.put(40, (Object)"minecraft:small_end_islands");
        f_184860_.put(41, (Object)"minecraft:end_midlands");
        f_184860_.put(42, (Object)"minecraft:end_highlands");
        f_184860_.put(43, (Object)"minecraft:end_barrens");
        f_184860_.put(44, (Object)"minecraft:warm_ocean");
        f_184860_.put(45, (Object)"minecraft:lukewarm_ocean");
        f_184860_.put(46, (Object)"minecraft:cold_ocean");
        f_184860_.put(47, (Object)"minecraft:deep_warm_ocean");
        f_184860_.put(48, (Object)"minecraft:deep_lukewarm_ocean");
        f_184860_.put(49, (Object)"minecraft:deep_cold_ocean");
        f_184860_.put(50, (Object)"minecraft:deep_frozen_ocean");
        f_184860_.put(127, (Object)"minecraft:the_void");
        f_184860_.put(129, (Object)"minecraft:sunflower_plains");
        f_184860_.put(130, (Object)"minecraft:desert_lakes");
        f_184860_.put(131, (Object)"minecraft:gravelly_mountains");
        f_184860_.put(132, (Object)"minecraft:flower_forest");
        f_184860_.put(133, (Object)"minecraft:taiga_mountains");
        f_184860_.put(134, (Object)"minecraft:swamp_hills");
        f_184860_.put(140, (Object)"minecraft:ice_spikes");
        f_184860_.put(149, (Object)"minecraft:modified_jungle");
        f_184860_.put(151, (Object)"minecraft:modified_jungle_edge");
        f_184860_.put(155, (Object)"minecraft:tall_birch_forest");
        f_184860_.put(156, (Object)"minecraft:tall_birch_hills");
        f_184860_.put(157, (Object)"minecraft:dark_forest_hills");
        f_184860_.put(158, (Object)"minecraft:snowy_taiga_mountains");
        f_184860_.put(160, (Object)"minecraft:giant_spruce_taiga");
        f_184860_.put(161, (Object)"minecraft:giant_spruce_taiga_hills");
        f_184860_.put(162, (Object)"minecraft:modified_gravelly_mountains");
        f_184860_.put(163, (Object)"minecraft:shattered_savanna");
        f_184860_.put(164, (Object)"minecraft:shattered_savanna_plateau");
        f_184860_.put(165, (Object)"minecraft:eroded_badlands");
        f_184860_.put(166, (Object)"minecraft:modified_wooded_badlands_plateau");
        f_184860_.put(167, (Object)"minecraft:modified_badlands_plateau");
        f_184860_.put(168, (Object)"minecraft:bamboo_jungle");
        f_184860_.put(169, (Object)"minecraft:bamboo_jungle_hills");
        f_184860_.put(170, (Object)"minecraft:soul_sand_valley");
        f_184860_.put(171, (Object)"minecraft:crimson_forest");
        f_184860_.put(172, (Object)"minecraft:warped_forest");
        f_184860_.put(173, (Object)"minecraft:basalt_deltas");
        f_184860_.put(174, (Object)"minecraft:dripstone_caves");
        f_184860_.put(175, (Object)"minecraft:lush_caves");
        f_184860_.put(177, (Object)"minecraft:meadow");
        f_184860_.put(178, (Object)"minecraft:grove");
        f_184860_.put(179, (Object)"minecraft:snowy_slopes");
        f_184860_.put(180, (Object)"minecraft:snowcapped_peaks");
        f_184860_.put(181, (Object)"minecraft:lofty_peaks");
        f_184860_.put(182, (Object)"minecraft:stony_peaks");
    }
}

