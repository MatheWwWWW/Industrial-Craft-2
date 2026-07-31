/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.FieldFinder
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.CompoundList$CompoundListType
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.datafixers.util.Unit
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Map;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.fixes.WorldGenSettingsFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class MissingDimensionFix
extends DataFix {
    public MissingDimensionFix(Schema p_16420_, boolean p_16421_) {
        super(p_16420_, p_16421_);
    }

    protected static <A> Type<Pair<A, Dynamic<?>>> m_16438_(String p_16439_, Type<A> p_16440_) {
        return DSL.and((Type)DSL.field((String)p_16439_, p_16440_), (Type)DSL.remainderType());
    }

    protected static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> m_16446_(String p_16447_, Type<A> p_16448_) {
        return DSL.and((Type)DSL.optional((Type)DSL.field((String)p_16447_, p_16448_)), (Type)DSL.remainderType());
    }

    protected static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> m_16441_(String p_16442_, Type<A1> p_16443_, String p_16444_, Type<A2> p_16445_) {
        return DSL.and((Type)DSL.optional((Type)DSL.field((String)p_16442_, p_16443_)), (Type)DSL.optional((Type)DSL.field((String)p_16444_, p_16445_)), (Type)DSL.remainderType());
    }

    protected TypeRewriteRule makeRule() {
        Schema $$0 = this.getInputSchema();
        TaggedChoice.TaggedChoiceType $$1 = new TaggedChoice.TaggedChoiceType("type", DSL.string(), (Map)ImmutableMap.of((Object)"minecraft:debug", (Object)DSL.remainderType(), (Object)"minecraft:flat", MissingDimensionFix.m_185130_($$0), (Object)"minecraft:noise", MissingDimensionFix.m_16441_("biome_source", DSL.taggedChoiceType((String)"type", (Type)DSL.string(), (Map)ImmutableMap.of((Object)"minecraft:fixed", MissingDimensionFix.m_16438_("biome", $$0.getType(References.f_16794_)), (Object)"minecraft:multi_noise", (Object)DSL.list(MissingDimensionFix.m_16438_("biome", $$0.getType(References.f_16794_))), (Object)"minecraft:checkerboard", MissingDimensionFix.m_16438_("biomes", DSL.list((Type)$$0.getType(References.f_16794_))), (Object)"minecraft:vanilla_layered", (Object)DSL.remainderType(), (Object)"minecraft:the_end", (Object)DSL.remainderType())), "settings", DSL.or((Type)DSL.string(), MissingDimensionFix.m_16441_("default_block", $$0.getType(References.f_16787_), "default_fluid", $$0.getType(References.f_16787_))))));
        CompoundList.CompoundListType $$2 = DSL.compoundList(NamespacedSchema.m_17310_(), MissingDimensionFix.m_16438_("generator", $$1));
        Type $$3 = DSL.and((Type)$$2, (Type)DSL.remainderType());
        Type $$4 = $$0.getType(References.f_16795_);
        FieldFinder $$5 = new FieldFinder("dimensions", $$3);
        if (!$$4.findFieldType("dimensions").equals((Object)$$3)) {
            throw new IllegalStateException();
        }
        OpticFinder $$6 = $$2.finder();
        return this.fixTypeEverywhereTyped("MissingDimensionFix", $$4, p_16426_ -> p_16426_.updateTyped((OpticFinder)$$5, p_145517_ -> p_145517_.updateTyped($$6, p_145521_ -> {
            if (!(p_145521_.getValue() instanceof List)) {
                throw new IllegalStateException("List exptected");
            }
            if (((List)p_145521_.getValue()).isEmpty()) {
                Dynamic $$3 = (Dynamic)p_16426_.get(DSL.remainderFinder());
                Dynamic $$4 = this.m_16436_($$3);
                return (Typed)DataFixUtils.orElse($$2.readTyped($$4).result().map(Pair::getFirst), (Object)p_145521_);
            }
            return p_145521_;
        })));
    }

    protected static Type<? extends Pair<? extends Either<? extends Pair<? extends Either<?, Unit>, ? extends Pair<? extends Either<? extends List<? extends Pair<? extends Either<?, Unit>, Dynamic<?>>>, Unit>, Dynamic<?>>>, Unit>, Dynamic<?>>> m_185130_(Schema p_185131_) {
        return MissingDimensionFix.m_16446_("settings", MissingDimensionFix.m_16441_("biome", p_185131_.getType(References.f_16794_), "layers", DSL.list(MissingDimensionFix.m_16446_("block", p_185131_.getType(References.f_16787_)))));
    }

    private <T> Dynamic<T> m_16436_(Dynamic<T> p_16437_) {
        long $$1 = p_16437_.get("seed").asLong(0L);
        return new Dynamic(p_16437_.getOps(), WorldGenSettingsFix.m_17190_(p_16437_, $$1, WorldGenSettingsFix.m_17187_(p_16437_, $$1), false));
    }
}

