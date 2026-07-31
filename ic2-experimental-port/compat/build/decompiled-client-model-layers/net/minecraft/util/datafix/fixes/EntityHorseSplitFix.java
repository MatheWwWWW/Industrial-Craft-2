/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.EntityRenameFix;
import net.minecraft.util.datafix.fixes.References;

public class EntityHorseSplitFix
extends EntityRenameFix {
    public EntityHorseSplitFix(Schema p_15447_, boolean p_15448_) {
        super("EntityHorseSplitFix", p_15447_, p_15448_);
    }

    @Override
    protected Pair<String, Typed<?>> m_6911_(String p_15451_, Typed<?> p_15452_) {
        Dynamic $$2 = (Dynamic)p_15452_.get(DSL.remainderFinder());
        if (Objects.equals("EntityHorse", p_15451_)) {
            String $$8;
            int $$3 = $$2.get("Type").asInt(0);
            switch ($$3) {
                default: {
                    String $$4 = "Horse";
                    break;
                }
                case 1: {
                    String $$5 = "Donkey";
                    break;
                }
                case 2: {
                    String $$6 = "Mule";
                    break;
                }
                case 3: {
                    String $$7 = "ZombieHorse";
                    break;
                }
                case 4: {
                    $$8 = "SkeletonHorse";
                }
            }
            $$2.remove("Type");
            Type $$9 = (Type)this.getOutputSchema().findChoiceType(References.f_16786_).types().get($$8);
            return Pair.of((Object)$$8, (Object)((Typed)((Pair)p_15452_.write().flatMap(arg_0 -> ((Type)$$9).readTyped(arg_0)).result().orElseThrow(() -> new IllegalStateException("Could not parse the new horse"))).getFirst()));
        }
        return Pair.of((Object)p_15451_, p_15452_);
    }
}

