/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class V1466
extends NamespacedSchema {
    public V1466(int p_17685_, Schema p_17686_) {
        super(p_17685_, p_17686_);
    }

    public void registerTypes(Schema p_17694_, Map<String, Supplier<TypeTemplate>> p_17695_, Map<String, Supplier<TypeTemplate>> p_17696_) {
        super.registerTypes(p_17694_, p_17695_, p_17696_);
        p_17694_.registerType(false, References.f_16773_, () -> DSL.fields((String)"Level", (TypeTemplate)DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_17694_)), (String)"TileEntities", (TypeTemplate)DSL.list((TypeTemplate)DSL.or((TypeTemplate)References.f_16781_.in(p_17694_), (TypeTemplate)DSL.remainder())), (String)"TileTicks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)References.f_16787_.in(p_17694_))), (String)"Sections", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"Palette", (TypeTemplate)DSL.list((TypeTemplate)References.f_16783_.in(p_17694_)))), (String)"Structures", (TypeTemplate)DSL.optionalFields((String)"Starts", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16790_.in(p_17694_))))));
        p_17694_.registerType(false, References.f_16790_, () -> DSL.optionalFields((String)"Children", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"CA", (TypeTemplate)References.f_16783_.in(p_17694_), (String)"CB", (TypeTemplate)References.f_16783_.in(p_17694_), (String)"CC", (TypeTemplate)References.f_16783_.in(p_17694_), (String)"CD", (TypeTemplate)References.f_16783_.in(p_17694_))), (String)"biome", (TypeTemplate)References.f_16794_.in(p_17694_)));
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17692_) {
        Map $$1 = super.registerBlockEntities(p_17692_);
        $$1.put("DUMMY", DSL::remainder);
        return $$1;
    }
}

