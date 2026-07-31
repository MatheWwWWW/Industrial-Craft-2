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
import net.minecraft.util.datafix.fixes.ItemIdFix;
import net.minecraft.util.datafix.fixes.ItemStackTheFlatteningFix;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class BlockEntityJukeboxFix
extends NamedEntityFix {
    public BlockEntityJukeboxFix(Schema p_14842_, boolean p_14843_) {
        super(p_14842_, p_14843_, "BlockEntityJukeboxFix", References.f_16781_, "minecraft:jukebox");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_14846_) {
        Type $$1 = this.getInputSchema().getChoiceType(References.f_16781_, "minecraft:jukebox");
        Type $$2 = $$1.findFieldType("RecordItem");
        OpticFinder $$3 = DSL.fieldFinder((String)"RecordItem", (Type)$$2);
        Dynamic $$4 = (Dynamic)p_14846_.get(DSL.remainderFinder());
        int $$5 = $$4.get("Record").asInt(0);
        if ($$5 > 0) {
            $$4.remove("Record");
            String $$6 = ItemStackTheFlatteningFix.m_16122_(ItemIdFix.m_15942_($$5), 0);
            if ($$6 != null) {
                Dynamic $$7 = $$4.emptyMap();
                $$7 = $$7.set("id", $$7.createString($$6));
                $$7 = $$7.set("Count", $$7.createByte((byte)1));
                return p_14846_.set($$3, (Typed)((Pair)$$2.readTyped($$7).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack."))).getFirst()).set(DSL.remainderFinder(), (Object)$$4);
            }
        }
        return p_14846_;
    }
}

