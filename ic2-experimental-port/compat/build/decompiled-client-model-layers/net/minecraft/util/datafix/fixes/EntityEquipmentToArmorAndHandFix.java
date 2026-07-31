/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.References;

public class EntityEquipmentToArmorAndHandFix
extends DataFix {
    public EntityEquipmentToArmorAndHandFix(Schema p_15417_, boolean p_15418_) {
        super(p_15417_, p_15418_);
    }

    public TypeRewriteRule makeRule() {
        return this.m_15426_(this.getInputSchema().getTypeRaw(References.f_16782_));
    }

    private <IS> TypeRewriteRule m_15426_(Type<IS> p_15427_) {
        Type $$1 = DSL.and((Type)DSL.optional((Type)DSL.field((String)"Equipment", (Type)DSL.list(p_15427_))), (Type)DSL.remainderType());
        Type $$2 = DSL.and((Type)DSL.optional((Type)DSL.field((String)"ArmorItems", (Type)DSL.list(p_15427_))), (Type)DSL.optional((Type)DSL.field((String)"HandItems", (Type)DSL.list(p_15427_))), (Type)DSL.remainderType());
        OpticFinder $$3 = DSL.typeFinder((Type)$$1);
        OpticFinder $$4 = DSL.fieldFinder((String)"Equipment", (Type)DSL.list(p_15427_));
        return this.fixTypeEverywhereTyped("EntityEquipmentToArmorAndHandFix", this.getInputSchema().getType(References.f_16786_), this.getOutputSchema().getType(References.f_16786_), p_15425_ -> {
            Either $$5 = Either.right((Object)DSL.unit());
            Either $$6 = Either.right((Object)DSL.unit());
            Dynamic $$7 = (Dynamic)p_15425_.getOrCreate(DSL.remainderFinder());
            Optional $$8 = p_15425_.getOptional($$4);
            if ($$8.isPresent()) {
                List $$9 = (List)$$8.get();
                Object $$10 = ((Pair)p_15427_.read($$7.emptyMap()).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack."))).getFirst();
                if (!$$9.isEmpty()) {
                    $$5 = Either.left((Object)Lists.newArrayList((Object[])new Object[]{$$9.get(0), $$10}));
                }
                if ($$9.size() > 1) {
                    ArrayList $$11 = Lists.newArrayList((Object[])new Object[]{$$10, $$10, $$10, $$10});
                    for (int $$12 = 1; $$12 < Math.min($$9.size(), 5); ++$$12) {
                        $$11.set($$12 - 1, $$9.get($$12));
                    }
                    $$6 = Either.left((Object)$$11);
                }
            }
            Dynamic $$13 = $$7;
            Optional $$14 = $$7.get("DropChances").asStreamOpt().result();
            if ($$14.isPresent()) {
                Iterator $$15 = Stream.concat((Stream)$$14.get(), Stream.generate(() -> $$13.createInt(0))).iterator();
                float $$16 = ((Dynamic)$$15.next()).asFloat(0.0f);
                if (!$$7.get("HandDropChances").result().isPresent()) {
                    Dynamic $$17 = $$7.createList(Stream.of(Float.valueOf($$16), Float.valueOf(0.0f)).map(arg_0 -> ((Dynamic)$$7).createFloat(arg_0)));
                    $$7 = $$7.set("HandDropChances", $$17);
                }
                if (!$$7.get("ArmorDropChances").result().isPresent()) {
                    Dynamic $$18 = $$7.createList(Stream.of(Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0f))).map(arg_0 -> ((Dynamic)$$7).createFloat(arg_0)));
                    $$7 = $$7.set("ArmorDropChances", $$18);
                }
                $$7 = $$7.remove("DropChances");
            }
            return p_15425_.set($$3, $$2, (Object)Pair.of((Object)$$5, (Object)Pair.of((Object)$$6, (Object)$$7)));
        });
    }
}

