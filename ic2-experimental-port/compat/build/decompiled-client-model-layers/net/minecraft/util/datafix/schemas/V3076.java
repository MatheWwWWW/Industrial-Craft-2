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

public class V3076
extends NamespacedSchema {
    public V3076(int p_216764_, Schema p_216765_) {
        super(p_216764_, p_216765_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_216767_) {
        Map $$1 = super.registerBlockEntities(p_216767_);
        p_216767_.registerSimple($$1, "minecraft:sculk_catalyst");
        return $$1;
    }
}

