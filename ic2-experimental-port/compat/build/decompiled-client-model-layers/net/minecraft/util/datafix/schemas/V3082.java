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

public class V3082
extends NamespacedSchema {
    public V3082(int p_216797_, Schema p_216798_) {
        super(p_216797_, p_216798_);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_216803_) {
        Map $$1 = super.registerEntities(p_216803_);
        p_216803_.register($$1, "minecraft:chest_boat", p_216801_ -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_216803_))));
        return $$1;
    }
}

