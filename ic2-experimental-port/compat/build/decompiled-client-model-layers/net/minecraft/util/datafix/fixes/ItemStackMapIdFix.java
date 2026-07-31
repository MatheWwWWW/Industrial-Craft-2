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
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemStackMapIdFix
extends DataFix {
    public ItemStackMapIdFix(Schema p_16088_, boolean p_16089_) {
        super(p_16088_, p_16089_);
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16782_);
        OpticFinder $$1 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        OpticFinder $$2 = $$0.findField("tag");
        return this.fixTypeEverywhereTyped("ItemInstanceMapIdFix", $$0, p_16093_ -> {
            Optional $$3 = p_16093_.getOptional($$1);
            if ($$3.isPresent() && Objects.equals(((Pair)$$3.get()).getSecond(), "minecraft:filled_map")) {
                Dynamic $$4 = (Dynamic)p_16093_.get(DSL.remainderFinder());
                Typed $$5 = p_16093_.getOrCreateTyped($$2);
                Dynamic $$6 = (Dynamic)$$5.get(DSL.remainderFinder());
                $$6 = $$6.set("map", $$6.createInt($$4.get("Damage").asInt(0)));
                return p_16093_.set($$2, $$5.set(DSL.remainderFinder(), (Object)$$6));
            }
            return p_16093_;
        });
    }
}

