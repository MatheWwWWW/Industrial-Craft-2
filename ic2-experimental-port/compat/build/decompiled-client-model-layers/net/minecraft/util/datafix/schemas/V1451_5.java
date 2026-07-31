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

public class V1451_5
extends NamespacedSchema {
    public V1451_5(int p_17527_, Schema p_17528_) {
        super(p_17527_, p_17528_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17530_) {
        Map $$1 = super.registerBlockEntities(p_17530_);
        $$1.remove("minecraft:flower_pot");
        $$1.remove("minecraft:noteblock");
        return $$1;
    }
}

