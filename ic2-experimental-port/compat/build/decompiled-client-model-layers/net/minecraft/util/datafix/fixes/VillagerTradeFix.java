/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class VillagerTradeFix
extends NamedEntityFix {
    public VillagerTradeFix(Schema p_17116_, boolean p_17117_) {
        super(p_17116_, p_17117_, "Villager trade fix", References.f_16786_, "minecraft:villager");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_17143_) {
        OpticFinder $$1 = p_17143_.getType().findField("Offers");
        OpticFinder $$2 = $$1.type().findField("Recipes");
        Type $$3 = $$2.type();
        if (!($$3 instanceof List.ListType)) {
            throw new IllegalStateException("Recipes are expected to be a list.");
        }
        List.ListType $$4 = (List.ListType)$$3;
        Type $$5 = $$4.getElement();
        OpticFinder $$6 = DSL.typeFinder((Type)$$5);
        OpticFinder $$7 = $$5.findField("buy");
        OpticFinder $$8 = $$5.findField("buyB");
        OpticFinder $$9 = $$5.findField("sell");
        OpticFinder $$10 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        Function<Typed, Typed> $$11 = p_17150_ -> this.m_17133_((OpticFinder<Pair<String, String>>)$$10, (Typed<?>)p_17150_);
        return p_17143_.updateTyped($$1, p_17125_ -> p_17125_.updateTyped($$2, p_145782_ -> p_145782_.updateTyped($$6, p_145788_ -> p_145788_.updateTyped($$7, $$11).updateTyped($$8, $$11).updateTyped($$9, $$11))));
    }

    private Typed<?> m_17133_(OpticFinder<Pair<String, String>> p_17134_, Typed<?> p_17135_) {
        return p_17135_.update(p_17134_, p_17145_ -> p_17145_.mapSecond(p_145790_ -> Objects.equals(p_145790_, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : p_145790_));
    }
}

