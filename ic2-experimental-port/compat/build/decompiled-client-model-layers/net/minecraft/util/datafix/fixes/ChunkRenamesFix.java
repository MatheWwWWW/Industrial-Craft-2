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
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
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
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.function.Function;
import net.minecraft.util.datafix.fixes.References;

public class ChunkRenamesFix
extends DataFix {
    public ChunkRenamesFix(Schema p_185100_) {
        super(p_185100_, true);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        OpticFinder $$1 = $$0.findField("Level");
        OpticFinder $$2 = $$1.type().findField("Structures");
        Type $$3 = this.getOutputSchema().getType(References.f_16773_);
        Type $$4 = $$3.findFieldType("structures");
        return this.fixTypeEverywhereTyped("Chunk Renames; purge Level-tag", $$0, $$3, p_199427_ -> {
            Typed $$4 = p_199427_.getTyped($$1);
            Typed<?> $$5 = ChunkRenamesFix.m_185106_($$4);
            $$5 = $$5.set(DSL.remainderFinder(), ChunkRenamesFix.m_185108_(p_199427_, (Dynamic)$$4.get(DSL.remainderFinder())));
            $$5 = ChunkRenamesFix.m_185111_($$5, "TileEntities", "block_entities");
            $$5 = ChunkRenamesFix.m_185111_($$5, "TileTicks", "block_ticks");
            $$5 = ChunkRenamesFix.m_185111_($$5, "Entities", "entities");
            $$5 = ChunkRenamesFix.m_185111_($$5, "Sections", "sections");
            $$5 = $$5.updateTyped($$2, $$4, p_185128_ -> ChunkRenamesFix.m_185111_(p_185128_, "Starts", "starts"));
            $$5 = ChunkRenamesFix.m_185111_($$5, "Structures", "structures");
            return $$5.update(DSL.remainderFinder(), p_199429_ -> p_199429_.remove("Level"));
        });
    }

    private static Typed<?> m_185111_(Typed<?> p_185112_, String p_185113_, String p_185114_) {
        return ChunkRenamesFix.m_185115_(p_185112_, p_185113_, p_185114_, p_185112_.getType().findFieldType(p_185113_)).update(DSL.remainderFinder(), p_199439_ -> p_199439_.remove(p_185113_));
    }

    private static <A> Typed<?> m_185115_(Typed<?> p_185116_, String p_185117_, String p_185118_, Type<A> p_185119_) {
        Type $$4 = DSL.optional((Type)DSL.field((String)p_185117_, p_185119_));
        Type $$5 = DSL.optional((Type)DSL.field((String)p_185118_, p_185119_));
        return p_185116_.update($$4.finder(), $$5, Function.identity());
    }

    private static <A> Typed<Pair<String, A>> m_185106_(Typed<A> p_185107_) {
        return new Typed(DSL.named((String)"chunk", (Type)p_185107_.getType()), p_185107_.getOps(), (Object)Pair.of((Object)"chunk", (Object)p_185107_.getValue()));
    }

    private static <T> Dynamic<T> m_185108_(Typed<?> p_185109_, Dynamic<T> p_185110_) {
        DynamicOps $$2 = p_185110_.getOps();
        Dynamic $$3 = ((Dynamic)p_185109_.get(DSL.remainderFinder())).convert($$2);
        DataResult $$4 = $$2.getMap(p_185110_.getValue()).flatMap(p_199433_ -> $$2.mergeToMap($$3.getValue(), p_199433_));
        return $$4.result().map(p_199436_ -> new Dynamic($$2, p_199436_)).orElse(p_185110_);
    }
}

