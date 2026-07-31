/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemWaterPotionFix
extends DataFix {
    public ItemWaterPotionFix(Schema p_16156_, boolean p_16157_) {
        super(p_16156_, p_16157_);
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        OpticFinder $$2 = $$0.findField("tag");
        return this.fixTypeEverywhereTyped("ItemWaterPotionFix", $$0, p_16161_ -> {
            String $$4;
            Optional $$3 = p_16161_.getOptional($$1);
            if ($$3.isPresent() && ("minecraft:potion".equals($$4 = (String)((Pair)$$3.get()).getSecond()) || "minecraft:splash_potion".equals($$4) || "minecraft:lingering_potion".equals($$4) || "minecraft:tipped_arrow".equals($$4))) {
                Typed $$5 = p_16161_.getOrCreateTyped($$2);
                Dynamic $$6 = (Dynamic)$$5.get(DSL.remainderFinder());
                if (!$$6.get("Potion").asString().result().isPresent()) {
                    $$6 = $$6.set("Potion", $$6.createString("minecraft:water"));
                }
                return p_16161_.set($$2, $$5.set(DSL.remainderFinder(), (Object)$$6));
            }
            return p_16161_;
        });
    }
}

