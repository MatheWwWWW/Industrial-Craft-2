/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class EntityGoatMissingStateFix
extends NamedEntityFix {
    public EntityGoatMissingStateFix(Schema p_238339_) {
        super(p_238339_, false, "EntityGoatMissingStateFix", References.f_16786_, "minecraft:goat");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_238295_) {
        return p_238295_.update(DSL.remainderFinder(), p_238370_ -> p_238370_.set("HasLeftHorn", p_238370_.createBoolean(true)).set("HasRightHorn", p_238370_.createBoolean(true)));
    }
}

