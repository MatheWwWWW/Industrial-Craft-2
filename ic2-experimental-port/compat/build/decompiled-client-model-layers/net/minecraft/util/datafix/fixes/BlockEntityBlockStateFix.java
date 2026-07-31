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
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class BlockEntityBlockStateFix
extends NamedEntityFix {
    public BlockEntityBlockStateFix(Schema p_14810_, boolean p_14811_) {
        super(p_14810_, p_14811_, "BlockEntityBlockStateFix", References.f_16781_, "minecraft:piston");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_14814_) {
        Type $$1 = this.getOutputSchema().getChoiceType(References.f_16781_, "minecraft:piston");
        Type $$2 = $$1.findFieldType("blockState");
        OpticFinder $$3 = DSL.fieldFinder((String)"blockState", (Type)$$2);
        Dynamic $$4 = (Dynamic)p_14814_.get(DSL.remainderFinder());
        int $$5 = $$4.get("blockId").asInt(0);
        $$4 = $$4.remove("blockId");
        int $$6 = $$4.get("blockData").asInt(0) & 0xF;
        $$4 = $$4.remove("blockData");
        Dynamic<?> $$7 = BlockStateData.m_14952_($$5 << 4 | $$6);
        Typed $$8 = (Typed)$$1.pointTyped(p_14814_.getOps()).orElseThrow(() -> new IllegalStateException("Could not create new piston block entity."));
        return $$8.set(DSL.remainderFinder(), (Object)$$4).set($$3, (Typed)((Pair)$$2.readTyped($$7).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created block state tag."))).getFirst());
    }
}

