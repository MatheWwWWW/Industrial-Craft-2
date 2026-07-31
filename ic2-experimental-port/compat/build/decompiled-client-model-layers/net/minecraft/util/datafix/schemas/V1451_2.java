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

public class V1451_2
extends NamespacedSchema {
    public V1451_2(int p_17436_, Schema p_17437_) {
        super(p_17436_, p_17437_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17442_) {
        Map $$1 = super.registerBlockEntities(p_17442_);
        p_17442_.register($$1, "minecraft:piston", p_17440_ -> DSL.optionalFields((String)"blockState", (TypeTemplate)References.f_16783_.in(p_17442_)));
        return $$1;
    }
}

