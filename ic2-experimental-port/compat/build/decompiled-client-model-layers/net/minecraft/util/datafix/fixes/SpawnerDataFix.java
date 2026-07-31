/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import net.minecraft.util.datafix.fixes.References;

public class SpawnerDataFix
extends DataFix {
    public SpawnerDataFix(Schema p_185133_) {
        super(p_185133_, true);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16789_);
        Type $$1 = this.getOutputSchema().getType(References.f_16789_);
        OpticFinder $$2 = $$0.findField("SpawnData");
        Type $$3 = $$1.findField("SpawnData").type();
        OpticFinder $$4 = $$0.findField("SpawnPotentials");
        Type $$5 = $$1.findField("SpawnPotentials").type();
        return this.fixTypeEverywhereTyped("Fix mob spawner data structure", $$0, $$1, p_185139_ -> p_185139_.updateTyped($$2, $$3, p_185154_ -> this.m_185140_((Type)$$3, (Typed<?>)p_185154_)).updateTyped($$4, $$5, p_185151_ -> this.m_185146_((Type)$$5, (Typed<?>)p_185151_)));
    }

    private <T> Typed<T> m_185140_(Type<T> p_185141_, Typed<?> p_185142_) {
        DynamicOps $$2 = p_185142_.getOps();
        return new Typed(p_185141_, $$2, (Object)Pair.of((Object)p_185142_.getValue(), (Object)new Dynamic($$2)));
    }

    private <T> Typed<T> m_185146_(Type<T> p_185147_, Typed<?> p_185148_) {
        DynamicOps $$2 = p_185148_.getOps();
        List $$3 = (List)p_185148_.getValue();
        List<Pair> $$4 = $$3.stream().map(p_185145_ -> {
            Pair $$2 = (Pair)p_185145_;
            int $$3 = ((Number)((Dynamic)$$2.getSecond()).get("Weight").asNumber().result().orElse(1)).intValue();
            Dynamic $$4 = new Dynamic($$2);
            $$4 = $$4.set("weight", $$4.createInt($$3));
            Dynamic $$5 = ((Dynamic)$$2.getSecond()).remove("Weight").remove("Entity");
            return Pair.of((Object)Pair.of((Object)$$2.getFirst(), (Object)$$5), (Object)$$4);
        }).toList();
        return new Typed(p_185147_, $$2, $$4);
    }
}

