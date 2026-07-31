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
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.datafix.fixes.References;

public class OverreachingTickFix
extends DataFix {
    public OverreachingTickFix(Schema p_207654_) {
        super(p_207654_, false);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        OpticFinder $$1 = $$0.findField("block_ticks");
        return this.fixTypeEverywhereTyped("Handle ticks saved in the wrong chunk", $$0, p_207661_ -> {
            Optional $$2 = p_207661_.getOptionalTyped($$1);
            Optional $$3 = $$2.isPresent() ? ((Typed)$$2.get()).write().result() : Optional.empty();
            return p_207661_.update(DSL.remainderFinder(), p_207670_ -> {
                int $$2 = p_207670_.get("xPos").asInt(0);
                int $$3 = p_207670_.get("zPos").asInt(0);
                Optional $$4 = p_207670_.get("fluid_ticks").get().result();
                p_207670_ = OverreachingTickFix.m_207662_(p_207670_, $$2, $$3, $$3, "neighbor_block_ticks");
                p_207670_ = OverreachingTickFix.m_207662_(p_207670_, $$2, $$3, $$4, "neighbor_fluid_ticks");
                return p_207670_;
            });
        });
    }

    private static Dynamic<?> m_207662_(Dynamic<?> p_207663_, int p_207664_, int p_207665_, Optional<? extends Dynamic<?>> p_207666_, String p_207667_) {
        List<Dynamic> $$5;
        if (p_207666_.isPresent() && !($$5 = p_207666_.get().asStream().filter(p_207658_ -> {
            int $$3 = p_207658_.get("x").asInt(0);
            int $$4 = p_207658_.get("z").asInt(0);
            int $$5 = Math.abs(p_207664_ - ($$3 >> 4));
            int $$6 = Math.abs(p_207665_ - ($$4 >> 4));
            return ($$5 != 0 || $$6 != 0) && $$5 <= 1 && $$6 <= 1;
        }).toList()).isEmpty()) {
            p_207663_ = p_207663_.set("UpgradeData", p_207663_.get("UpgradeData").orElseEmptyMap().set(p_207667_, p_207663_.createList($$5.stream())));
        }
        return p_207663_;
    }
}

