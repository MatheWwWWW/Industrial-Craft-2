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

public class V1451_1
extends NamespacedSchema {
    public V1451_1(int p_17427_, Schema p_17428_) {
        super(p_17427_, p_17428_);
    }

    public void registerTypes(Schema p_17432_, Map<String, Supplier<TypeTemplate>> p_17433_, Map<String, Supplier<TypeTemplate>> p_17434_) {
        super.registerTypes(p_17432_, p_17433_, p_17434_);
        p_17432_.registerType(false, References.f_16773_, () -> DSL.fields((String)"Level", (TypeTemplate)DSL.optionalFields((String)"Entities", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_17432_)), (String)"TileEntities", (TypeTemplate)DSL.list((TypeTemplate)DSL.or((TypeTemplate)References.f_16781_.in(p_17432_), (TypeTemplate)DSL.remainder())), (String)"TileTicks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)References.f_16787_.in(p_17432_))), (String)"Sections", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"Palette", (TypeTemplate)DSL.list((TypeTemplate)References.f_16783_.in(p_17432_)))))));
    }
}

