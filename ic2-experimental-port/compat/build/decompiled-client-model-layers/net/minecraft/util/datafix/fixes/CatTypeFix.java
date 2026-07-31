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

public class CatTypeFix
extends NamedEntityFix {
    public CatTypeFix(Schema p_15007_, boolean p_15008_) {
        super(p_15007_, p_15008_, "CatTypeFix", References.f_16786_, "minecraft:cat");
    }

    public Dynamic<?> m_15011_(Dynamic<?> p_15012_) {
        if (p_15012_.get("CatType").asInt(0) == 9) {
            return p_15012_.set("CatType", p_15012_.createInt(10));
        }
        return p_15012_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15010_) {
        return p_15010_.update(DSL.remainderFinder(), this::m_15011_);
    }
}

