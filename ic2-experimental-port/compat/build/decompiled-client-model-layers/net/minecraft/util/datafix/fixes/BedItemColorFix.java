/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
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
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BedItemColorFix
extends DataFix {
    public BedItemColorFix(Schema p_14720_, boolean p_14721_) {
        super(p_14720_, p_14721_);
    }

    public TypeRewriteRule makeRule() {
        OpticFinder $$0 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        return this.fixTypeEverywhereTyped("BedItemColorFix", this.getInputSchema().getType(References.f_16782_), p_14724_ -> {
            Dynamic $$3;
            Optional $$2 = p_14724_.getOptional($$0);
            if ($$2.isPresent() && Objects.equals(((Pair)$$2.get()).getSecond(), "minecraft:bed") && ($$3 = (Dynamic)p_14724_.get(DSL.remainderFinder())).get("Damage").asInt(0) == 0) {
                return p_14724_.set(DSL.remainderFinder(), (Object)$$3.set("Damage", $$3.createShort((short)14)));
            }
            return p_14724_;
        });
    }
}

