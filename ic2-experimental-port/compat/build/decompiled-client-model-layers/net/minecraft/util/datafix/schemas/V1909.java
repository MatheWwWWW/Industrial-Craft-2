/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class V1909
extends NamespacedSchema {
    public V1909(int p_17782_, Schema p_17783_) {
        super(p_17782_, p_17783_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17785_) {
        Map $$1 = super.registerBlockEntities(p_17785_);
        p_17785_.registerSimple($$1, "minecraft:jigsaw");
        return $$1;
    }
}

