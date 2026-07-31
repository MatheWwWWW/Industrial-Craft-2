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

public class V1486
extends NamespacedSchema {
    public V1486(int p_17722_, Schema p_17723_) {
        super(p_17722_, p_17723_);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17725_) {
        Map $$1 = super.registerEntities(p_17725_);
        $$1.put("minecraft:cod", (Supplier)$$1.remove("minecraft:cod_mob"));
        $$1.put("minecraft:salmon", (Supplier)$$1.remove("minecraft:salmon_mob"));
        return $$1;
    }
}

