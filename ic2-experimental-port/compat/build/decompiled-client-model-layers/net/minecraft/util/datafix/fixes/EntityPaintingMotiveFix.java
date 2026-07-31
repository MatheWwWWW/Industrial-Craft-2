/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class EntityPaintingMotiveFix
extends NamedEntityFix {
    private static final Map<String, String> f_15522_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_15532_ -> {
        p_15532_.put("donkeykong", "donkey_kong");
        p_15532_.put("burningskull", "burning_skull");
        p_15532_.put("skullandroses", "skull_and_roses");
    });

    public EntityPaintingMotiveFix(Schema p_15525_, boolean p_15526_) {
        super(p_15525_, p_15526_, "EntityPaintingMotiveFix", References.f_16786_, "minecraft:painting");
    }

    public Dynamic<?> m_15529_(Dynamic<?> p_15530_) {
        Optional $$1 = p_15530_.get("Motive").asString().result();
        if ($$1.isPresent()) {
            String $$2 = ((String)$$1.get()).toLowerCase(Locale.ROOT);
            return p_15530_.set("Motive", p_15530_.createString(new ResourceLocation(f_15522_.getOrDefault($$2, $$2)).toString()));
        }
        return p_15530_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15528_) {
        return p_15528_.update(DSL.remainderFinder(), this::m_15529_);
    }
}

