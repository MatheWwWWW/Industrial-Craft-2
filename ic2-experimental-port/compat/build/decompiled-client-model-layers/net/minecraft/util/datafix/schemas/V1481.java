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

public class V1481
extends NamespacedSchema {
    public V1481(int p_17712_, Schema p_17713_) {
        super(p_17712_, p_17713_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17715_) {
        Map $$1 = super.registerBlockEntities(p_17715_);
        p_17715_.registerSimple($$1, "minecraft:conduit");
        return $$1;
    }
}

