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

public class V1483
extends NamespacedSchema {
    public V1483(int p_17717_, Schema p_17718_) {
        super(p_17717_, p_17718_);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17720_) {
        Map $$1 = super.registerEntities(p_17720_);
        $$1.put("minecraft:pufferfish", (Supplier)$$1.remove("minecraft:puffer_fish"));
        return $$1;
    }
}

