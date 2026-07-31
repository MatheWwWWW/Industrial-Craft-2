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

public class V2842
extends NamespacedSchema {
    public V2842(int p_185238_, Schema p_185239_) {
        super(p_185238_, p_185239_);
    }

    public void registerTypes(Schema p_185243_, Map<String, Supplier<TypeTemplate>> p_185244_, Map<String, Supplier<TypeTemplate>> p_185245_) {
        super.registerTypes(p_185243_, p_185244_, p_185245_);
        p_185243_.registerType(false, References.f_16773_, () -> DSL.optionalFields((String)"entities", (TypeTemplate)DSL.list((TypeTemplate)References.f_16785_.in(p_185243_)), (String)"block_entities", (TypeTemplate)DSL.list((TypeTemplate)DSL.or((TypeTemplate)References.f_16781_.in(p_185243_), (TypeTemplate)DSL.remainder())), (String)"block_ticks", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"i", (TypeTemplate)References.f_16787_.in(p_185243_))), (String)"sections", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"biomes", (TypeTemplate)DSL.optionalFields((String)"palette", (TypeTemplate)DSL.list((TypeTemplate)References.f_16794_.in(p_185243_))), (String)"block_states", (TypeTemplate)DSL.optionalFields((String)"palette", (TypeTemplate)DSL.list((TypeTemplate)References.f_16783_.in(p_185243_))))), (String)"structures", (TypeTemplate)DSL.optionalFields((String)"starts", (TypeTemplate)DSL.compoundList((TypeTemplate)References.f_16790_.in(p_185243_)))));
    }
}

