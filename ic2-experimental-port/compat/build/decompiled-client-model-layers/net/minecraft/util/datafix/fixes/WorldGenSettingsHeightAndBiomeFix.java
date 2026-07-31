/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.OptionalDynamic
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class WorldGenSettingsHeightAndBiomeFix
extends DataFix {
    private static final String f_185172_ = "WorldGenSettingsHeightAndBiomeFix";
    public static final String f_185171_ = "has_increased_height_already";

    public WorldGenSettingsHeightAndBiomeFix(Schema p_185174_) {
        super(p_185174_, true);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16795_);
        OpticFinder $$1 = $$0.findField("dimensions");
        Type $$2 = this.getOutputSchema().getType(References.f_16795_);
        Type $$3 = $$2.findFieldType("dimensions");
        return this.fixTypeEverywhereTyped(f_185172_, $$0, $$2, p_185179_ -> {
            OptionalDynamic $$3 = ((Dynamic)p_185179_.get(DSL.remainderFinder())).get(f_185171_);
            boolean $$4 = $$3.result().isEmpty();
            boolean $$5 = $$3.asBoolean(true);
            return p_185179_.update(DSL.remainderFinder(), p_185205_ -> p_185205_.remove(f_185171_)).updateTyped($$1, $$3, p_185190_ -> {
                Dynamic $$4 = (Dynamic)p_185190_.write().result().orElseThrow(() -> new IllegalStateException("Malformed WorldGenSettings.dimensions"));
                $$4 = $$4.update("minecraft:overworld", p_185194_ -> p_185194_.update("generator", p_185201_ -> {
                    String $$3 = p_185201_.get("type").asString("");
                    if ("minecraft:noise".equals($$3)) {
                        MutableBoolean $$4 = new MutableBoolean();
                        p_185201_ = p_185201_.update("biome_source", p_185185_ -> {
                            String $$3 = p_185185_.get("type").asString("");
                            if ("minecraft:vanilla_layered".equals($$3) || $$4 && "minecraft:multi_noise".equals($$3)) {
                                if (p_185185_.get("large_biomes").asBoolean(false)) {
                                    $$4.setTrue();
                                }
                                return p_185185_.createMap((Map)ImmutableMap.of((Object)p_185185_.createString("preset"), (Object)p_185185_.createString("minecraft:overworld"), (Object)p_185185_.createString("type"), (Object)p_185185_.createString("minecraft:multi_noise")));
                            }
                            return p_185185_;
                        });
                        if ($$4.booleanValue()) {
                            return p_185201_.update("settings", p_185203_ -> {
                                if ("minecraft:overworld".equals(p_185203_.asString(""))) {
                                    return p_185203_.createString("minecraft:large_biomes");
                                }
                                return p_185203_;
                            });
                        }
                        return p_185201_;
                    }
                    if ("minecraft:flat".equals($$3)) {
                        if ($$5) {
                            return p_185201_;
                        }
                        return p_185201_.update("settings", p_185197_ -> p_185197_.update("layers", WorldGenSettingsHeightAndBiomeFix::m_185180_));
                    }
                    return p_185201_;
                }));
                return (Typed)((Pair)$$3.readTyped($$4).result().orElseThrow(() -> new IllegalStateException("WorldGenSettingsHeightAndBiomeFix failed."))).getFirst();
            });
        });
    }

    private static Dynamic<?> m_185180_(Dynamic<?> p_185181_) {
        Dynamic $$1 = p_185181_.createMap((Map)ImmutableMap.of((Object)p_185181_.createString("height"), (Object)p_185181_.createInt(64), (Object)p_185181_.createString("block"), (Object)p_185181_.createString("minecraft:air")));
        return p_185181_.createList(Stream.concat(Stream.of($$1), p_185181_.asStream()));
    }
}

