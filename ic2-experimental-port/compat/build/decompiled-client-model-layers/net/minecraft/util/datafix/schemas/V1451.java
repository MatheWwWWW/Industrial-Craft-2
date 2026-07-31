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

public class V1451
extends NamespacedSchema {
    public V1451(int p_17420_, Schema p_17421_) {
        super(p_17420_, p_17421_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17425_) {
        Map $$1 = super.registerBlockEntities(p_17425_);
        p_17425_.register($$1, "minecraft:trapped_chest", () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17425_))));
        return $$1;
    }
}

