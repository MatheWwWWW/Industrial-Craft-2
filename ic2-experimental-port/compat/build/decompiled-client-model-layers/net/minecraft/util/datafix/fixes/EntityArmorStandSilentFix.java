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

public class EntityArmorStandSilentFix
extends NamedEntityFix {
    public EntityArmorStandSilentFix(Schema p_15324_, boolean p_15325_) {
        super(p_15324_, p_15325_, "EntityArmorStandSilentFix", References.f_16786_, "ArmorStand");
    }

    public Dynamic<?> m_15328_(Dynamic<?> p_15329_) {
        if (p_15329_.get("Silent").asBoolean(false) && !p_15329_.get("Marker").asBoolean(false)) {
            return p_15329_.remove("Silent");
        }
        return p_15329_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15327_) {
        return p_15327_.update(DSL.remainderFinder(), this::m_15328_);
    }
}

