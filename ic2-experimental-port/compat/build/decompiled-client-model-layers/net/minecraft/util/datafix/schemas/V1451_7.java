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

public class V1451_7
extends NamespacedSchema {
    public V1451_7(int p_17544_, Schema p_17545_) {
        super(p_17544_, p_17545_);
    }

    public void registerTypes(Schema p_17549_, Map<String, Supplier<TypeTemplate>> p_17550_, Map<String, Supplier<TypeTemplate>> p_17551_) {
        super.registerTypes(p_17549_, p_17550_, p_17551_);
        p_17549_.registerType(false, References.f_16790_, () -> DSL.optionalFields((String)"Children", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"CA", (TypeTemplate)References.f_16783_.in(p_17549_), (String)"CB", (TypeTemplate)References.f_16783_.in(p_17549_), (String)"CC", (TypeTemplate)References.f_16783_.in(p_17549_), (String)"CD", (TypeTemplate)References.f_16783_.in(p_17549_)))));
    }
}

