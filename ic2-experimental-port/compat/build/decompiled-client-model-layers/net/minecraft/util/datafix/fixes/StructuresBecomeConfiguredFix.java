/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.objects.Object2IntArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.fixes.References;

public class StructuresBecomeConfiguredFix
extends DataFix {
    private static final Map<String, Conversion> f_207676_ = ImmutableMap.builder().put((Object)"mineshaft", (Object)Conversion.m_207750_(Map.of(List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands"), "minecraft:mineshaft_mesa"), "minecraft:mineshaft")).put((Object)"shipwreck", (Object)Conversion.m_207750_(Map.of(List.of("minecraft:beach", "minecraft:snowy_beach"), "minecraft:shipwreck_beached"), "minecraft:shipwreck")).put((Object)"ocean_ruin", (Object)Conversion.m_207750_(Map.of(List.of("minecraft:warm_ocean", "minecraft:lukewarm_ocean", "minecraft:deep_lukewarm_ocean"), "minecraft:ocean_ruin_warm"), "minecraft:ocean_ruin_cold")).put((Object)"village", (Object)Conversion.m_207750_(Map.of(List.of("minecraft:desert"), "minecraft:village_desert", List.of("minecraft:savanna"), "minecraft:village_savanna", List.of("minecraft:snowy_plains"), "minecraft:village_snowy", List.of("minecraft:taiga"), "minecraft:village_taiga"), "minecraft:village_plains")).put((Object)"ruined_portal", (Object)Conversion.m_207750_(Map.of(List.of("minecraft:desert"), "minecraft:ruined_portal_desert", List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands", "minecraft:windswept_hills", "minecraft:windswept_forest", "minecraft:windswept_gravelly_hills", "minecraft:savanna_plateau", "minecraft:windswept_savanna", "minecraft:stony_shore", "minecraft:meadow", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:snowy_slopes"), "minecraft:ruined_portal_mountain", List.of("minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle"), "minecraft:ruined_portal_jungle", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean"), "minecraft:ruined_portal_ocean"), "minecraft:ruined_portal")).put((Object)"pillager_outpost", (Object)Conversion.m_207746_("minecraft:pillager_outpost")).put((Object)"mansion", (Object)Conversion.m_207746_("minecraft:mansion")).put((Object)"jungle_pyramid", (Object)Conversion.m_207746_("minecraft:jungle_pyramid")).put((Object)"desert_pyramid", (Object)Conversion.m_207746_("minecraft:desert_pyramid")).put((Object)"igloo", (Object)Conversion.m_207746_("minecraft:igloo")).put((Object)"swamp_hut", (Object)Conversion.m_207746_("minecraft:swamp_hut")).put((Object)"stronghold", (Object)Conversion.m_207746_("minecraft:stronghold")).put((Object)"monument", (Object)Conversion.m_207746_("minecraft:monument")).put((Object)"fortress", (Object)Conversion.m_207746_("minecraft:fortress")).put((Object)"endcity", (Object)Conversion.m_207746_("minecraft:end_city")).put((Object)"buried_treasure", (Object)Conversion.m_207746_("minecraft:buried_treasure")).put((Object)"nether_fossil", (Object)Conversion.m_207746_("minecraft:nether_fossil")).put((Object)"bastion_remnant", (Object)Conversion.m_207746_("minecraft:bastion_remnant")).build();

    public StructuresBecomeConfiguredFix(Schema p_207679_) {
        super(p_207679_, false);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        Type $$1 = this.getInputSchema().getType(References.f_16773_);
        return this.writeFixAndRead("StucturesToConfiguredStructures", $$0, $$1, this::m_207691_);
    }

    private Dynamic<?> m_207691_(Dynamic<?> p_207692_) {
        return p_207692_.update("structures", p_207728_ -> p_207728_.update("starts", p_207734_ -> this.m_207699_((Dynamic<?>)p_207734_, p_207692_)).update("References", p_207731_ -> this.m_207716_((Dynamic<?>)p_207731_, p_207692_)));
    }

    private Dynamic<?> m_207699_(Dynamic<?> p_207700_, Dynamic<?> p_207701_) {
        Map $$2 = (Map)p_207700_.getMapValues().result().get();
        ArrayList $$3 = new ArrayList();
        $$2.forEach((p_207721_, p_207722_) -> {
            if (p_207722_.get("id").asString("INVALID").equals("INVALID")) {
                $$3.add(p_207721_);
            }
        });
        for (Dynamic $$4 : $$3) {
            p_207700_ = p_207700_.remove($$4.asString(""));
        }
        return p_207700_.updateMapValues(p_207715_ -> this.m_207684_((Pair<Dynamic<?>, Dynamic<?>>)p_207715_, p_207701_));
    }

    private Pair<Dynamic<?>, Dynamic<?>> m_207684_(Pair<Dynamic<?>, Dynamic<?>> p_207685_, Dynamic<?> p_207686_) {
        Dynamic<?> $$2 = this.m_207723_(p_207685_, p_207686_);
        return new Pair($$2, (Object)((Dynamic)p_207685_.getSecond()).set("id", $$2));
    }

    private Dynamic<?> m_207716_(Dynamic<?> p_207717_, Dynamic<?> p_207718_) {
        Map $$2 = (Map)p_207717_.getMapValues().result().get();
        ArrayList $$3 = new ArrayList();
        $$2.forEach((p_207704_, p_207705_) -> {
            if (p_207705_.asLongStream().count() == 0L) {
                $$3.add(p_207704_);
            }
        });
        for (Dynamic $$4 : $$3) {
            p_207717_ = p_207717_.remove($$4.asString(""));
        }
        return p_207717_.updateMapValues(p_207698_ -> this.m_207710_((Pair<Dynamic<?>, Dynamic<?>>)p_207698_, p_207718_));
    }

    private Pair<Dynamic<?>, Dynamic<?>> m_207710_(Pair<Dynamic<?>, Dynamic<?>> p_207711_, Dynamic<?> p_207712_) {
        return p_207711_.mapFirst(p_207690_ -> this.m_207723_(p_207711_, p_207712_));
    }

    private Dynamic<?> m_207723_(Pair<Dynamic<?>, Dynamic<?>> p_207724_, Dynamic<?> p_207725_) {
        Optional<String> $$6;
        String $$2 = ((Dynamic)p_207724_.getFirst()).asString("UNKNOWN").toLowerCase(Locale.ROOT);
        Conversion $$3 = f_207676_.get($$2);
        if ($$3 == null) {
            throw new IllegalStateException("Found unknown structure: " + $$2);
        }
        Dynamic $$4 = (Dynamic)p_207724_.getSecond();
        String $$5 = $$3.f_207737_;
        if (!$$3.f_207736_().isEmpty() && ($$6 = this.m_207693_(p_207725_, $$3)).isPresent()) {
            $$5 = $$6.get();
        }
        Dynamic $$7 = $$4.createString($$5);
        return $$7;
    }

    private Optional<String> m_207693_(Dynamic<?> p_207694_, Conversion p_207695_) {
        Object2IntArrayMap $$2 = new Object2IntArrayMap();
        p_207694_.get("sections").asList(Function.identity()).forEach(p_207683_ -> p_207683_.get("biomes").get("palette").asList(Function.identity()).forEach(p_207709_ -> {
            String $$3 = p_207695_.f_207736_().get(p_207709_.asString(""));
            if ($$3 != null) {
                $$2.mergeInt((Object)$$3, 1, Integer::sum);
            }
        }));
        return $$2.object2IntEntrySet().stream().max(Comparator.comparingInt(Object2IntMap.Entry::getIntValue)).map(Map.Entry::getKey);
    }

    record Conversion(Map<String, String> f_207736_, String f_207737_) {
        public static Conversion m_207746_(String p_207747_) {
            return new Conversion(Map.of(), p_207747_);
        }

        public static Conversion m_207750_(Map<List<String>, String> p_207751_, String p_207752_) {
            return new Conversion(Conversion.m_207748_(p_207751_), p_207752_);
        }

        private static Map<String, String> m_207748_(Map<List<String>, String> p_207749_) {
            ImmutableMap.Builder $$1 = ImmutableMap.builder();
            for (Map.Entry<List<String>, String> $$2 : p_207749_.entrySet()) {
                $$2.getKey().forEach(p_207745_ -> $$1.put(p_207745_, (Object)((String)$$2.getValue())));
            }
            return $$1.build();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Conversion.class, "biomeMapping;fallback", "f_207736_", "f_207737_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Conversion.class, "biomeMapping;fallback", "f_207736_", "f_207737_"}, this);
        }

        @Override
        public final boolean equals(Object p_207755_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Conversion.class, "biomeMapping;fallback", "f_207736_", "f_207737_"}, this, p_207755_);
        }
    }
}

