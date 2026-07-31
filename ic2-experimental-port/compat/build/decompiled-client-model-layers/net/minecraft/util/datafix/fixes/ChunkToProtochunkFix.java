/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.shorts.ShortArrayList
 *  it.unimi.dsi.fastutil.shorts.ShortList
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.util.datafix.fixes.References;

public class ChunkToProtochunkFix
extends DataFix {
    private static final int f_145239_ = 16;

    public ChunkToProtochunkFix(Schema p_15285_, boolean p_15286_) {
        super(p_15285_, p_15286_);
    }

    public TypeRewriteRule makeRule() {
        return TypeRewriteRule.seq((TypeRewriteRule)this.writeFixAndRead("ChunkToProtoChunkFix", this.getInputSchema().getType(References.f_16773_), this.getOutputSchema().getType(References.f_16773_), p_199886_ -> p_199886_.update("Level", ChunkToProtochunkFix::m_199855_)), (TypeRewriteRule)this.writeAndRead("Structure biome inject", this.getInputSchema().getType(References.f_16790_), this.getOutputSchema().getType(References.f_16790_)));
    }

    private static <T> Dynamic<T> m_199855_(Dynamic<T> p_199856_) {
        String $$5;
        boolean $$2;
        boolean $$1 = p_199856_.get("TerrainPopulated").asBoolean(false);
        boolean bl = $$2 = p_199856_.get("LightPopulated").asNumber().result().isEmpty() || p_199856_.get("LightPopulated").asBoolean(false);
        if ($$1) {
            if ($$2) {
                String $$3 = "mobs_spawned";
            } else {
                String $$4 = "decorated";
            }
        } else {
            $$5 = "carved";
        }
        return ChunkToProtochunkFix.m_199881_(ChunkToProtochunkFix.m_199879_(p_199856_)).set("Status", p_199856_.createString($$5)).set("hasLegacyStructureData", p_199856_.createBoolean(true));
    }

    private static <T> Dynamic<T> m_199879_(Dynamic<T> p_199880_) {
        return p_199880_.update("Biomes", p_199862_ -> (Dynamic)DataFixUtils.orElse(p_199862_.asByteBufferOpt().result().map(p_199868_ -> {
            int[] $$2 = new int[256];
            for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
                if ($$3 >= p_199868_.capacity()) continue;
                $$2[$$3] = p_199868_.get($$3) & 0xFF;
            }
            return p_199880_.createIntList(Arrays.stream($$2));
        }), (Object)p_199862_));
    }

    private static <T> Dynamic<T> m_199881_(Dynamic<T> p_199882_) {
        return (Dynamic)DataFixUtils.orElse(p_199882_.get("TileTicks").asStreamOpt().result().map(p_199871_ -> {
            List $$2 = IntStream.range(0, 16).mapToObj(p_199850_ -> new ShortArrayList()).collect(Collectors.toList());
            p_199871_.forEach(p_199874_ -> {
                int $$2 = p_199874_.get("x").asInt(0);
                int $$3 = p_199874_.get("y").asInt(0);
                int $$4 = p_199874_.get("z").asInt(0);
                short $$5 = ChunkToProtochunkFix.m_15290_($$2, $$3, $$4);
                ((ShortList)$$2.get($$3 >> 4)).add($$5);
            });
            return p_199882_.remove("TileTicks").set("ToBeTicked", p_199882_.createList($$2.stream().map(p_199865_ -> p_199882_.createList(p_199865_.intStream().mapToObj(p_199859_ -> p_199882_.createShort((short)p_199859_))))));
        }), p_199882_);
    }

    private static short m_15290_(int p_15291_, int p_15292_, int p_15293_) {
        return (short)(p_15291_ & 0xF | (p_15292_ & 0xF) << 4 | (p_15293_ & 0xF) << 8);
    }
}

