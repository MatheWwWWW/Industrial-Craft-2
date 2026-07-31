/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class JigsawPropertiesFix
extends NamedEntityFix {
    public JigsawPropertiesFix(Schema p_16182_, boolean p_16183_) {
        super(p_16182_, p_16183_, "JigsawPropertiesFix", References.f_16781_, "minecraft:jigsaw");
    }

    private static Dynamic<?> m_16186_(Dynamic<?> p_16187_) {
        String $$1 = p_16187_.get("attachement_type").asString("minecraft:empty");
        String $$2 = p_16187_.get("target_pool").asString("minecraft:empty");
        return p_16187_.set("name", p_16187_.createString($$1)).set("target", p_16187_.createString($$1)).remove("attachement_type").set("pool", p_16187_.createString($$2)).remove("target_pool");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_16185_) {
        return p_16185_.update(DSL.remainderFinder(), JigsawPropertiesFix::m_16186_);
    }
}

