/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Splitter
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 *  org.apache.commons.lang3.math.NumberUtils
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.util.datafix.fixes.EntityBlockStateFix;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.lang3.math.NumberUtils;

public class LevelFlatGeneratorInfoFix
extends DataFix {
    private static final String f_145489_ = "generatorOptions";
    @VisibleForTesting
    static final String f_145488_ = "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
    private static final Splitter f_16337_ = Splitter.on((char)';').limit(5);
    private static final Splitter f_16338_ = Splitter.on((char)',');
    private static final Splitter f_16339_ = Splitter.on((char)'x').limit(2);
    private static final Splitter f_16340_ = Splitter.on((char)'*').limit(2);
    private static final Splitter f_16341_ = Splitter.on((char)':').limit(3);

    public LevelFlatGeneratorInfoFix(Schema p_16344_, boolean p_16345_) {
        super(p_16344_, p_16345_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", this.getInputSchema().getType(References.f_16771_), p_16351_ -> p_16351_.update(DSL.remainderFinder(), this::m_16352_));
    }

    private Dynamic<?> m_16352_(Dynamic<?> p_16353_) {
        if (p_16353_.get("generatorName").asString("").equalsIgnoreCase("flat")) {
            return p_16353_.update(f_145489_, p_16357_ -> (Dynamic)DataFixUtils.orElse((Optional)p_16357_.asString().map(this::m_16354_).map(arg_0 -> ((Dynamic)p_16357_).createString(arg_0)).result(), (Object)p_16357_));
        }
        return p_16353_;
    }

    @VisibleForTesting
    String m_16354_(String p_16355_) {
        String $$6;
        int $$5;
        if (p_16355_.isEmpty()) {
            return f_145488_;
        }
        Iterator $$1 = f_16337_.split((CharSequence)p_16355_).iterator();
        String $$2 = (String)$$1.next();
        if ($$1.hasNext()) {
            int $$3 = NumberUtils.toInt((String)$$2, (int)0);
            String $$4 = (String)$$1.next();
        } else {
            $$5 = 0;
            $$6 = $$2;
        }
        if ($$5 < 0 || $$5 > 3) {
            return f_145488_;
        }
        StringBuilder $$7 = new StringBuilder();
        Splitter $$8 = $$5 < 3 ? f_16339_ : f_16340_;
        $$7.append(StreamSupport.stream(f_16338_.split((CharSequence)$$6).spliterator(), false).map(p_16349_ -> {
            String $$7;
            int $$6;
            List $$3 = $$8.splitToList((CharSequence)p_16349_);
            if ($$3.size() == 2) {
                int $$4 = NumberUtils.toInt((String)((String)$$3.get(0)));
                String $$5 = (String)$$3.get(1);
            } else {
                $$6 = 1;
                $$7 = (String)$$3.get(0);
            }
            List $$8 = f_16341_.splitToList((CharSequence)$$7);
            int $$9 = ((String)$$8.get(0)).equals("minecraft") ? 1 : 0;
            String $$10 = (String)$$8.get($$9);
            int $$11 = $$5 == 3 ? EntityBlockStateFix.m_15365_("minecraft:" + $$10) : NumberUtils.toInt((String)$$10, (int)0);
            int $$12 = $$9 + 1;
            int $$13 = $$8.size() > $$12 ? NumberUtils.toInt((String)((String)$$8.get($$12)), (int)0) : 0;
            return (String)($$6 == 1 ? "" : $$6 + "*") + BlockStateData.m_14952_($$11 << 4 | $$13).get("Name").asString("");
        }).collect(Collectors.joining(",")));
        while ($$1.hasNext()) {
            $$7.append(';').append((String)$$1.next());
        }
        return $$7.toString();
    }
}

