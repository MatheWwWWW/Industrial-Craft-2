/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.datafixers.util.Unit
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class FurnaceRecipeFix
extends DataFix {
    public FurnaceRecipeFix(Schema p_15837_, boolean p_15838_) {
        super(p_15837_, p_15838_);
    }

    protected TypeRewriteRule makeRule() {
        return this.m_15849_(this.getOutputSchema().getTypeRaw(References.f_16793_));
    }

    private <R> TypeRewriteRule m_15849_(Type<R> p_15850_) {
        Type $$1 = DSL.and((Type)DSL.optional((Type)DSL.field((String)"RecipesUsed", (Type)DSL.and((Type)DSL.compoundList(p_15850_, (Type)DSL.intType()), (Type)DSL.remainderType()))), (Type)DSL.remainderType());
        OpticFinder $$2 = DSL.namedChoice((String)"minecraft:furnace", (Type)this.getInputSchema().getChoiceType(References.f_16781_, "minecraft:furnace"));
        OpticFinder $$3 = DSL.namedChoice((String)"minecraft:blast_furnace", (Type)this.getInputSchema().getChoiceType(References.f_16781_, "minecraft:blast_furnace"));
        OpticFinder $$4 = DSL.namedChoice((String)"minecraft:smoker", (Type)this.getInputSchema().getChoiceType(References.f_16781_, "minecraft:smoker"));
        Type $$5 = this.getOutputSchema().getChoiceType(References.f_16781_, "minecraft:furnace");
        Type $$6 = this.getOutputSchema().getChoiceType(References.f_16781_, "minecraft:blast_furnace");
        Type $$7 = this.getOutputSchema().getChoiceType(References.f_16781_, "minecraft:smoker");
        Type $$8 = this.getInputSchema().getType(References.f_16781_);
        Type $$9 = this.getOutputSchema().getType(References.f_16781_);
        return this.fixTypeEverywhereTyped("FurnaceRecipesFix", $$8, $$9, p_15848_ -> p_15848_.updateTyped($$2, $$5, p_145372_ -> this.m_15851_(p_15850_, (Type)$$1, (Typed<?>)p_145372_)).updateTyped($$3, $$6, p_145368_ -> this.m_15851_(p_15850_, (Type)$$1, (Typed<?>)p_145368_)).updateTyped($$4, $$7, p_145364_ -> this.m_15851_(p_15850_, (Type)$$1, (Typed<?>)p_145364_)));
    }

    private <R> Typed<?> m_15851_(Type<R> p_15852_, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> p_15853_, Typed<?> p_15854_) {
        Dynamic $$3 = (Dynamic)p_15854_.getOrCreate(DSL.remainderFinder());
        int $$4 = $$3.get("RecipesUsedSize").asInt(0);
        $$3 = $$3.remove("RecipesUsedSize");
        ArrayList $$5 = Lists.newArrayList();
        for (int $$6 = 0; $$6 < $$4; ++$$6) {
            String $$7 = "RecipeLocation" + $$6;
            String $$8 = "RecipeAmount" + $$6;
            Optional $$9 = $$3.get($$7).result();
            int $$10 = $$3.get($$8).asInt(0);
            if ($$10 > 0) {
                $$9.ifPresent(p_15859_ -> {
                    Optional $$4 = p_15852_.read(p_15859_).result();
                    $$4.ifPresent(p_145360_ -> $$5.add(Pair.of((Object)p_145360_.getFirst(), (Object)$$10)));
                });
            }
            $$3 = $$3.remove($$7).remove($$8);
        }
        return p_15854_.set(DSL.remainderFinder(), p_15853_, (Object)Pair.of((Object)Either.left((Object)Pair.of((Object)$$5, (Object)$$3.emptyMap())), (Object)$$3));
    }
}

