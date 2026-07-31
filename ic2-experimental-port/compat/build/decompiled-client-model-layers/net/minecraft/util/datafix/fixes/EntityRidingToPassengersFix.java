/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class EntityRidingToPassengersFix
extends DataFix {
    public EntityRidingToPassengersFix(Schema p_15638_, boolean p_15639_) {
        super(p_15638_, p_15639_);
    }

    public TypeRewriteRule makeRule() {
        Schema $$0 = this.getInputSchema();
        Schema $$1 = this.getOutputSchema();
        Type $$2 = $$0.getTypeRaw(References.f_16785_);
        Type $$3 = $$1.getTypeRaw(References.f_16785_);
        Type $$4 = $$0.getTypeRaw(References.f_16786_);
        return this.m_15641_($$0, $$1, $$2, $$3, $$4);
    }

    private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule m_15641_(Schema p_15642_, Schema p_15643_, Type<OldEntityTree> p_15644_, Type<NewEntityTree> p_15645_, Type<Entity> p_15646_) {
        Type $$5 = DSL.named((String)References.f_16785_.typeName(), (Type)DSL.and((Type)DSL.optional((Type)DSL.field((String)"Riding", p_15644_)), p_15646_));
        Type $$6 = DSL.named((String)References.f_16785_.typeName(), (Type)DSL.and((Type)DSL.optional((Type)DSL.field((String)"Passengers", (Type)DSL.list(p_15645_))), p_15646_));
        Type $$7 = p_15642_.getType(References.f_16785_);
        Type $$8 = p_15643_.getType(References.f_16785_);
        if (!Objects.equals($$7, $$5)) {
            throw new IllegalStateException("Old entity type is not what was expected.");
        }
        if (!$$8.equals((Object)$$6, true, true)) {
            throw new IllegalStateException("New entity type is not what was expected.");
        }
        OpticFinder $$9 = DSL.typeFinder((Type)$$5);
        OpticFinder $$10 = DSL.typeFinder((Type)$$6);
        OpticFinder $$11 = DSL.typeFinder(p_15645_);
        Type $$12 = p_15642_.getType(References.f_16772_);
        Type $$13 = p_15643_.getType(References.f_16772_);
        return TypeRewriteRule.seq((TypeRewriteRule)this.fixTypeEverywhere("EntityRidingToPassengerFix", $$5, $$6, p_15653_ -> p_145320_ -> {
            Optional<Object> $$7 = Optional.empty();
            Pair $$8 = p_145320_;
            while (true) {
                Either $$9 = (Either)DataFixUtils.orElse($$7.map(p_145326_ -> {
                    Typed $$5 = (Typed)p_15645_.pointTyped(p_15653_).orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
                    Object $$6 = $$5.set($$10, p_145326_).getOptional($$11).orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                    return Either.left((Object)ImmutableList.of($$6));
                }), (Object)Either.right((Object)DSL.unit()));
                $$7 = Optional.of(Pair.of((Object)References.f_16785_.typeName(), (Object)Pair.of((Object)$$9, (Object)((Pair)$$8.getSecond()).getSecond())));
                Optional $$10 = ((Either)((Pair)$$8.getSecond()).getFirst()).left();
                if (!$$10.isPresent()) break;
                $$8 = (Pair)new Typed(p_15644_, p_15653_, $$10.get()).getOptional($$9).orElseThrow(() -> new IllegalStateException("Should always have an entity here"));
            }
            return (Pair)$$7.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
        }), (TypeRewriteRule)this.writeAndRead("player RootVehicle injecter", $$12, $$13));
    }
}

