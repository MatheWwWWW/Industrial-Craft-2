/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.References;

public class RedstoneWireConnectionsFix
extends DataFix {
    public RedstoneWireConnectionsFix(Schema p_16749_) {
        super(p_16749_, false);
    }

    protected TypeRewriteRule makeRule() {
        Schema $$0 = this.getInputSchema();
        return this.fixTypeEverywhereTyped("RedstoneConnectionsFix", $$0.getType(References.f_16783_), p_16751_ -> p_16751_.update(DSL.remainderFinder(), this::m_16752_));
    }

    private <T> Dynamic<T> m_16752_(Dynamic<T> p_16753_) {
        boolean $$1 = p_16753_.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
        if (!$$1) {
            return p_16753_;
        }
        return p_16753_.update("Properties", p_16760_ -> {
            String $$1 = p_16760_.get("east").asString("none");
            String $$2 = p_16760_.get("west").asString("none");
            String $$3 = p_16760_.get("north").asString("none");
            String $$4 = p_16760_.get("south").asString("none");
            boolean $$5 = RedstoneWireConnectionsFix.m_16754_($$1) || RedstoneWireConnectionsFix.m_16754_($$2);
            boolean $$6 = RedstoneWireConnectionsFix.m_16754_($$3) || RedstoneWireConnectionsFix.m_16754_($$4);
            String $$7 = !RedstoneWireConnectionsFix.m_16754_($$1) && !$$6 ? "side" : $$1;
            String $$8 = !RedstoneWireConnectionsFix.m_16754_($$2) && !$$6 ? "side" : $$2;
            String $$9 = !RedstoneWireConnectionsFix.m_16754_($$3) && !$$5 ? "side" : $$3;
            String $$10 = !RedstoneWireConnectionsFix.m_16754_($$4) && !$$5 ? "side" : $$4;
            return p_16760_.update("east", p_145627_ -> p_145627_.createString($$7)).update("west", p_145624_ -> p_145624_.createString($$8)).update("north", p_145621_ -> p_145621_.createString($$9)).update("south", p_145618_ -> p_145618_.createString($$10));
        });
    }

    private static boolean m_16754_(String p_16755_) {
        return !"none".equals(p_16755_);
    }
}

