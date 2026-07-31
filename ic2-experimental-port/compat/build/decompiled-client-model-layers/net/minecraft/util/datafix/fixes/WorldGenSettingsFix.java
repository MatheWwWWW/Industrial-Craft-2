/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicLike
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.OptionalDynamic
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class WorldGenSettingsFix
extends DataFix {
    private static final String f_145791_ = "minecraft:village";
    private static final String f_145792_ = "minecraft:desert_pyramid";
    private static final String f_145793_ = "minecraft:igloo";
    private static final String f_145794_ = "minecraft:jungle_pyramid";
    private static final String f_145795_ = "minecraft:swamp_hut";
    private static final String f_145796_ = "minecraft:pillager_outpost";
    private static final String f_145797_ = "minecraft:endcity";
    private static final String f_145798_ = "minecraft:mansion";
    private static final String f_145799_ = "minecraft:monument";
    private static final ImmutableMap<String, StructureFeatureConfiguration> f_17170_ = ImmutableMap.builder().put((Object)"minecraft:village", (Object)new StructureFeatureConfiguration(32, 8, 10387312)).put((Object)"minecraft:desert_pyramid", (Object)new StructureFeatureConfiguration(32, 8, 14357617)).put((Object)"minecraft:igloo", (Object)new StructureFeatureConfiguration(32, 8, 14357618)).put((Object)"minecraft:jungle_pyramid", (Object)new StructureFeatureConfiguration(32, 8, 14357619)).put((Object)"minecraft:swamp_hut", (Object)new StructureFeatureConfiguration(32, 8, 14357620)).put((Object)"minecraft:pillager_outpost", (Object)new StructureFeatureConfiguration(32, 8, 165745296)).put((Object)"minecraft:monument", (Object)new StructureFeatureConfiguration(32, 5, 10387313)).put((Object)"minecraft:endcity", (Object)new StructureFeatureConfiguration(20, 11, 10387313)).put((Object)"minecraft:mansion", (Object)new StructureFeatureConfiguration(80, 20, 10387319)).build();

    public WorldGenSettingsFix(Schema p_17173_) {
        super(p_17173_, true);
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("WorldGenSettings building", this.getInputSchema().getType(References.f_16795_), p_17184_ -> p_17184_.update(DSL.remainderFinder(), WorldGenSettingsFix::m_17185_));
    }

    private static <T> Dynamic<T> m_17174_(long p_17175_, DynamicLike<T> p_17176_, Dynamic<T> p_17177_, Dynamic<T> p_17178_) {
        return p_17176_.createMap((Map)ImmutableMap.of((Object)p_17176_.createString("type"), (Object)p_17176_.createString("minecraft:noise"), (Object)p_17176_.createString("biome_source"), p_17178_, (Object)p_17176_.createString("seed"), (Object)p_17176_.createLong(p_17175_), (Object)p_17176_.createString("settings"), p_17177_));
    }

    private static <T> Dynamic<T> m_17195_(Dynamic<T> p_17196_, long p_17197_, boolean p_17198_, boolean p_17199_) {
        ImmutableMap.Builder $$4 = ImmutableMap.builder().put((Object)p_17196_.createString("type"), (Object)p_17196_.createString("minecraft:vanilla_layered")).put((Object)p_17196_.createString("seed"), (Object)p_17196_.createLong(p_17197_)).put((Object)p_17196_.createString("large_biomes"), (Object)p_17196_.createBoolean(p_17199_));
        if (p_17198_) {
            $$4.put((Object)p_17196_.createString("legacy_biome_init_layer"), (Object)p_17196_.createBoolean(p_17198_));
        }
        return p_17196_.createMap((Map)$$4.build());
    }

    private static <T> Dynamic<T> m_17185_(Dynamic<T> p_17186_) {
        Dynamic<T> $$27;
        DynamicOps $$1 = p_17186_.getOps();
        long $$2 = p_17186_.get("RandomSeed").asLong(0L);
        Optional $$3 = p_17186_.get("generatorName").asString().map(p_17227_ -> p_17227_.toLowerCase(Locale.ROOT)).result();
        Optional $$4 = p_17186_.get("legacy_custom_options").asString().result().map(Optional::of).orElseGet(() -> {
            if ($$3.equals(Optional.of("customized"))) {
                return p_17186_.get("generatorOptions").asString().result();
            }
            return Optional.empty();
        });
        boolean $$5 = false;
        if ($$3.equals(Optional.of("customized"))) {
            Dynamic<T> $$6 = WorldGenSettingsFix.m_17187_(p_17186_, $$2);
        } else if (!$$3.isPresent()) {
            Dynamic<T> $$7 = WorldGenSettingsFix.m_17187_(p_17186_, $$2);
        } else {
            switch ((String)$$3.get()) {
                case "flat": {
                    OptionalDynamic $$8 = p_17186_.get("generatorOptions");
                    Map<Dynamic<T>, Dynamic<T>> $$9 = WorldGenSettingsFix.m_17217_($$1, $$8);
                    Dynamic $$10 = p_17186_.createMap((Map)ImmutableMap.of((Object)p_17186_.createString("type"), (Object)p_17186_.createString("minecraft:flat"), (Object)p_17186_.createString("settings"), (Object)p_17186_.createMap((Map)ImmutableMap.of((Object)p_17186_.createString("structures"), (Object)p_17186_.createMap($$9), (Object)p_17186_.createString("layers"), (Object)$$8.get("layers").result().orElseGet(() -> p_17186_.createList(Stream.of(p_17186_.createMap((Map)ImmutableMap.of((Object)p_17186_.createString("height"), (Object)p_17186_.createInt(1), (Object)p_17186_.createString("block"), (Object)p_17186_.createString("minecraft:bedrock"))), p_17186_.createMap((Map)ImmutableMap.of((Object)p_17186_.createString("height"), (Object)p_17186_.createInt(2), (Object)p_17186_.createString("block"), (Object)p_17186_.createString("minecraft:dirt"))), p_17186_.createMap((Map)ImmutableMap.of((Object)p_17186_.createString("height"), (Object)p_17186_.createInt(1), (Object)p_17186_.createString("block"), (Object)p_17186_.createString("minecraft:grass_block")))))), (Object)p_17186_.createString("biome"), (Object)p_17186_.createString($$8.get("biome").asString("minecraft:plains"))))));
                    break;
                }
                case "debug_all_block_states": {
                    Dynamic $$11 = p_17186_.createMap((Map)ImmutableMap.of((Object)p_17186_.createString("type"), (Object)p_17186_.createString("minecraft:debug")));
                    break;
                }
                case "buffet": {
                    Dynamic $$21;
                    Dynamic $$17;
                    OptionalDynamic $$12 = p_17186_.get("generatorOptions");
                    OptionalDynamic $$13 = $$12.get("chunk_generator");
                    Optional $$14 = $$13.get("type").asString().result();
                    if (Objects.equals($$14, Optional.of("minecraft:caves"))) {
                        Dynamic $$15 = p_17186_.createString("minecraft:caves");
                        $$5 = true;
                    } else if (Objects.equals($$14, Optional.of("minecraft:floating_islands"))) {
                        Dynamic $$16 = p_17186_.createString("minecraft:floating_islands");
                    } else {
                        $$17 = p_17186_.createString("minecraft:overworld");
                    }
                    Dynamic $$18 = $$12.get("biome_source").result().orElseGet(() -> p_17186_.createMap((Map)ImmutableMap.of((Object)p_17186_.createString("type"), (Object)p_17186_.createString("minecraft:fixed"))));
                    if ($$18.get("type").asString().result().equals(Optional.of("minecraft:fixed"))) {
                        String $$19 = $$18.get("options").get("biomes").asStream().findFirst().flatMap(p_17259_ -> p_17259_.asString().result()).orElse("minecraft:ocean");
                        Dynamic $$20 = $$18.remove("options").set("biome", p_17186_.createString($$19));
                    } else {
                        $$21 = $$18;
                    }
                    Dynamic<T> $$22 = WorldGenSettingsFix.m_17174_($$2, p_17186_, $$17, $$21);
                    break;
                }
                default: {
                    boolean $$23 = ((String)$$3.get()).equals("default");
                    boolean $$24 = ((String)$$3.get()).equals("default_1_1") || $$23 && p_17186_.get("generatorVersion").asInt(0) == 0;
                    boolean $$25 = ((String)$$3.get()).equals("amplified");
                    boolean $$26 = ((String)$$3.get()).equals("largebiomes");
                    $$27 = WorldGenSettingsFix.m_17174_($$2, p_17186_, p_17186_.createString($$25 ? "minecraft:amplified" : "minecraft:overworld"), WorldGenSettingsFix.m_17195_(p_17186_, $$2, $$24, $$26));
                }
            }
        }
        boolean $$28 = p_17186_.get("MapFeatures").asBoolean(true);
        boolean $$29 = p_17186_.get("BonusChest").asBoolean(false);
        ImmutableMap.Builder $$30 = ImmutableMap.builder();
        $$30.put($$1.createString("seed"), $$1.createLong($$2));
        $$30.put($$1.createString("generate_features"), $$1.createBoolean($$28));
        $$30.put($$1.createString("bonus_chest"), $$1.createBoolean($$29));
        $$30.put($$1.createString("dimensions"), WorldGenSettingsFix.m_17190_(p_17186_, $$2, $$27, $$5));
        $$4.ifPresent(p_17182_ -> $$30.put($$1.createString("legacy_custom_options"), $$1.createString(p_17182_)));
        return new Dynamic($$1, $$1.createMap((Map)$$30.build()));
    }

    protected static <T> Dynamic<T> m_17187_(Dynamic<T> p_17188_, long p_17189_) {
        return WorldGenSettingsFix.m_17174_(p_17189_, p_17188_, p_17188_.createString("minecraft:overworld"), WorldGenSettingsFix.m_17195_(p_17188_, p_17189_, false, false));
    }

    protected static <T> T m_17190_(Dynamic<T> p_17191_, long p_17192_, Dynamic<T> p_17193_, boolean p_17194_) {
        DynamicOps $$4 = p_17191_.getOps();
        return (T)$$4.createMap((Map)ImmutableMap.of((Object)$$4.createString("minecraft:overworld"), (Object)$$4.createMap((Map)ImmutableMap.of((Object)$$4.createString("type"), (Object)$$4.createString("minecraft:overworld" + (p_17194_ ? "_caves" : "")), (Object)$$4.createString("generator"), (Object)p_17193_.getValue())), (Object)$$4.createString("minecraft:the_nether"), (Object)$$4.createMap((Map)ImmutableMap.of((Object)$$4.createString("type"), (Object)$$4.createString("minecraft:the_nether"), (Object)$$4.createString("generator"), (Object)WorldGenSettingsFix.m_17174_(p_17192_, p_17191_, p_17191_.createString("minecraft:nether"), p_17191_.createMap((Map)ImmutableMap.of((Object)p_17191_.createString("type"), (Object)p_17191_.createString("minecraft:multi_noise"), (Object)p_17191_.createString("seed"), (Object)p_17191_.createLong(p_17192_), (Object)p_17191_.createString("preset"), (Object)p_17191_.createString("minecraft:nether")))).getValue())), (Object)$$4.createString("minecraft:the_end"), (Object)$$4.createMap((Map)ImmutableMap.of((Object)$$4.createString("type"), (Object)$$4.createString("minecraft:the_end"), (Object)$$4.createString("generator"), (Object)WorldGenSettingsFix.m_17174_(p_17192_, p_17191_, p_17191_.createString("minecraft:end"), p_17191_.createMap((Map)ImmutableMap.of((Object)p_17191_.createString("type"), (Object)p_17191_.createString("minecraft:the_end"), (Object)p_17191_.createString("seed"), (Object)p_17191_.createLong(p_17192_)))).getValue()))));
    }

    private static <T> Map<Dynamic<T>, Dynamic<T>> m_17217_(DynamicOps<T> p_17218_, OptionalDynamic<T> p_17219_) {
        MutableInt $$2 = new MutableInt(32);
        MutableInt $$3 = new MutableInt(3);
        MutableInt $$4 = new MutableInt(128);
        MutableBoolean $$5 = new MutableBoolean(false);
        HashMap $$6 = Maps.newHashMap();
        if (!p_17219_.result().isPresent()) {
            $$5.setTrue();
            $$6.put(f_145791_, (StructureFeatureConfiguration)f_17170_.get((Object)f_145791_));
        }
        p_17219_.get("structures").flatMap(Dynamic::getMapValues).result().ifPresent(p_17257_ -> p_17257_.forEach((p_145823_, p_145824_) -> p_145824_.getMapValues().result().ifPresent(p_145816_ -> p_145816_.forEach((p_145807_, p_145808_) -> {
            String $$8 = p_145823_.asString("");
            String $$9 = p_145807_.asString("");
            String $$10 = p_145808_.asString("");
            if ("stronghold".equals($$8)) {
                $$5.setTrue();
                switch ($$9) {
                    case "distance": {
                        $$2.setValue(WorldGenSettingsFix.m_17231_($$10, $$2.getValue(), 1));
                        return;
                    }
                    case "spread": {
                        $$3.setValue(WorldGenSettingsFix.m_17231_($$10, $$3.getValue(), 1));
                        return;
                    }
                    case "count": {
                        $$4.setValue(WorldGenSettingsFix.m_17231_($$10, $$4.getValue(), 1));
                        return;
                    }
                }
                return;
            }
            switch ($$9) {
                case "distance": {
                    switch ($$8) {
                        case "village": {
                            WorldGenSettingsFix.m_17235_($$6, f_145791_, $$10, 9);
                            return;
                        }
                        case "biome_1": {
                            WorldGenSettingsFix.m_17235_($$6, f_145792_, $$10, 9);
                            WorldGenSettingsFix.m_17235_($$6, f_145793_, $$10, 9);
                            WorldGenSettingsFix.m_17235_($$6, f_145794_, $$10, 9);
                            WorldGenSettingsFix.m_17235_($$6, f_145795_, $$10, 9);
                            WorldGenSettingsFix.m_17235_($$6, f_145796_, $$10, 9);
                            return;
                        }
                        case "endcity": {
                            WorldGenSettingsFix.m_17235_($$6, f_145797_, $$10, 1);
                            return;
                        }
                        case "mansion": {
                            WorldGenSettingsFix.m_17235_($$6, f_145798_, $$10, 1);
                            return;
                        }
                    }
                    return;
                }
                case "separation": {
                    if ("oceanmonument".equals($$8)) {
                        StructureFeatureConfiguration $$11 = $$6.getOrDefault(f_145799_, (StructureFeatureConfiguration)f_17170_.get((Object)f_145799_));
                        int $$12 = WorldGenSettingsFix.m_17231_($$10, $$11.f_17267_, 1);
                        $$6.put(f_145799_, new StructureFeatureConfiguration($$12, $$11.f_17267_, $$11.f_17268_));
                    }
                    return;
                }
                case "spacing": {
                    if ("oceanmonument".equals($$8)) {
                        WorldGenSettingsFix.m_17235_($$6, f_145799_, $$10, 1);
                    }
                    return;
                }
            }
        }))));
        ImmutableMap.Builder $$7 = ImmutableMap.builder();
        $$7.put((Object)p_17219_.createString("structures"), (Object)p_17219_.createMap($$6.entrySet().stream().collect(Collectors.toMap(p_17225_ -> p_17219_.createString((String)p_17225_.getKey()), p_17222_ -> ((StructureFeatureConfiguration)p_17222_.getValue()).m_17276_(p_17218_)))));
        if ($$5.isTrue()) {
            $$7.put((Object)p_17219_.createString("stronghold"), (Object)p_17219_.createMap((Map)ImmutableMap.of((Object)p_17219_.createString("distance"), (Object)p_17219_.createInt($$2.getValue().intValue()), (Object)p_17219_.createString("spread"), (Object)p_17219_.createInt($$3.getValue().intValue()), (Object)p_17219_.createString("count"), (Object)p_17219_.createInt($$4.getValue().intValue()))));
        }
        return $$7.build();
    }

    private static int m_17228_(String p_17229_, int p_17230_) {
        return NumberUtils.toInt((String)p_17229_, (int)p_17230_);
    }

    private static int m_17231_(String p_17232_, int p_17233_, int p_17234_) {
        return Math.max(p_17234_, WorldGenSettingsFix.m_17228_(p_17232_, p_17233_));
    }

    private static void m_17235_(Map<String, StructureFeatureConfiguration> p_17236_, String p_17237_, String p_17238_, int p_17239_) {
        StructureFeatureConfiguration $$4 = p_17236_.getOrDefault(p_17237_, (StructureFeatureConfiguration)f_17170_.get((Object)p_17237_));
        int $$5 = WorldGenSettingsFix.m_17231_(p_17238_, $$4.f_17266_, p_17239_);
        p_17236_.put(p_17237_, new StructureFeatureConfiguration($$5, $$4.f_17267_, $$4.f_17268_));
    }

    static final class StructureFeatureConfiguration {
        public static final Codec<StructureFeatureConfiguration> f_17265_ = RecordCodecBuilder.create(p_17279_ -> p_17279_.group((App)Codec.INT.fieldOf("spacing").forGetter(p_145830_ -> p_145830_.f_17266_), (App)Codec.INT.fieldOf("separation").forGetter(p_145828_ -> p_145828_.f_17267_), (App)Codec.INT.fieldOf("salt").forGetter(p_145826_ -> p_145826_.f_17268_)).apply((Applicative)p_17279_, StructureFeatureConfiguration::new));
        final int f_17266_;
        final int f_17267_;
        final int f_17268_;

        public StructureFeatureConfiguration(int p_17271_, int p_17272_, int p_17273_) {
            this.f_17266_ = p_17271_;
            this.f_17267_ = p_17272_;
            this.f_17268_ = p_17273_;
        }

        public <T> Dynamic<T> m_17276_(DynamicOps<T> p_17277_) {
            return new Dynamic(p_17277_, f_17265_.encodeStart(p_17277_, (Object)this).result().orElse(p_17277_.emptyMap()));
        }
    }
}

