/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class EntityHorseSaddleFix
extends NamedEntityFix {
    public EntityHorseSaddleFix(Schema p_15442_, boolean p_15443_) {
        super(p_15442_, p_15443_, "EntityHorseSaddleFix", References.f_16786_, "EntityHorse");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15445_) {
        OpticFinder $$1 = DSL.fieldFinder((String)"id", (Type)DSL.named((String)References.f_16788_.typeName(), NamespacedSchema.m_17310_()));
        Type $$2 = this.getInputSchema().getTypeRaw(References.f_16782_);
        OpticFinder $$3 = DSL.fieldFinder((String)"SaddleItem", (Type)$$2);
        Optional $$4 = p_15445_.getOptionalTyped($$3);
        Dynamic $$5 = (Dynamic)p_15445_.get(DSL.remainderFinder());
        if (!$$4.isPresent() && $$5.get("Saddle").asBoolean(false)) {
            Typed $$6 = (Typed)$$2.pointTyped(p_15445_.getOps()).orElseThrow(IllegalStateException::new);
            $$6 = $$6.set($$1, (Object)Pair.of((Object)References.f_16788_.typeName(), (Object)"minecraft:saddle"));
            Dynamic $$7 = $$5.emptyMap();
            $$7 = $$7.set("Count", $$7.createByte((byte)1));
            $$7 = $$7.set("Damage", $$7.createShort((short)0));
            $$6 = $$6.set(DSL.remainderFinder(), (Object)$$7);
            $$5.remove("Saddle");
            return p_15445_.set($$3, $$6).set(DSL.remainderFinder(), (Object)$$5);
        }
        return p_15445_;
    }
}

