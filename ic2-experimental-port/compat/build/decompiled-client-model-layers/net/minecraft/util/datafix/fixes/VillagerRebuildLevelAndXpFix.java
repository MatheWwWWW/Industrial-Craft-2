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
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.References;

public class VillagerRebuildLevelAndXpFix
extends DataFix {
    private static final int f_145761_ = 2;
    private static final int[] f_17074_ = new int[]{0, 10, 50, 100, 150};

    public static int m_17079_(int p_17080_) {
        return f_17074_[Mth.m_14045_(p_17080_ - 1, 0, f_17074_.length - 1)];
    }

    public VillagerRebuildLevelAndXpFix(Schema p_17077_, boolean p_17078_) {
        super(p_17077_, p_17078_);
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getChoiceType(References.f_16786_, "minecraft:villager");
        OpticFinder $$1 = DSL.namedChoice((String)"minecraft:villager", (Type)$$0);
        OpticFinder $$2 = $$0.findField("Offers");
        Type $$3 = $$2.type();
        OpticFinder $$4 = $$3.findField("Recipes");
        List.ListType $$5 = (List.ListType)$$4.type();
        OpticFinder $$6 = $$5.getElement().finder();
        return this.fixTypeEverywhereTyped("Villager level and xp rebuild", this.getInputSchema().getType(References.f_16786_), p_17098_ -> p_17098_.updateTyped($$1, $$0, p_145766_ -> {
            Optional $$8;
            int $$7;
            Dynamic $$4 = (Dynamic)p_145766_.get(DSL.remainderFinder());
            int $$5 = $$4.get("VillagerData").get("level").asInt(0);
            Typed<?> $$6 = p_145766_;
            if (($$5 == 0 || $$5 == 1) && ($$5 = Mth.m_14045_(($$7 = p_145766_.getOptionalTyped($$2).flatMap(p_145772_ -> p_145772_.getOptionalTyped($$4)).map(p_145769_ -> p_145769_.getAllTyped($$6).size()).orElse(0).intValue()) / 2, 1, 5)) > 1) {
                $$6 = VillagerRebuildLevelAndXpFix.m_17099_($$6, $$5);
            }
            if (!($$8 = $$4.get("Xp").asNumber().result()).isPresent()) {
                $$6 = VillagerRebuildLevelAndXpFix.m_17108_($$6, $$5);
            }
            return $$6;
        }));
    }

    private static Typed<?> m_17099_(Typed<?> p_17100_, int p_17101_) {
        return p_17100_.update(DSL.remainderFinder(), p_17104_ -> p_17104_.update("VillagerData", p_145775_ -> p_145775_.set("level", p_145775_.createInt(p_17101_))));
    }

    private static Typed<?> m_17108_(Typed<?> p_17109_, int p_17110_) {
        int $$2 = VillagerRebuildLevelAndXpFix.m_17079_(p_17110_);
        return p_17109_.update(DSL.remainderFinder(), p_17083_ -> p_17083_.set("Xp", p_17083_.createInt($$2)));
    }
}

