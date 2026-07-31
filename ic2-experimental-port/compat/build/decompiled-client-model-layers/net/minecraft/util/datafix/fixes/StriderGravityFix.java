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

public class StriderGravityFix
extends NamedEntityFix {
    public StriderGravityFix(Schema p_16954_, boolean p_16955_) {
        super(p_16954_, p_16955_, "StriderGravityFix", References.f_16786_, "minecraft:strider");
    }

    public Dynamic<?> m_16958_(Dynamic<?> p_16959_) {
        if (p_16959_.get("NoGravity").asBoolean(false)) {
            return p_16959_.set("NoGravity", p_16959_.createBoolean(false));
        }
        return p_16959_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_16957_) {
        return p_16957_.update(DSL.remainderFinder(), this::m_16958_);
    }
}

