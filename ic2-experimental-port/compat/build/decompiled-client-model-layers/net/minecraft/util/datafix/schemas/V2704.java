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
import net.minecraft.util.datafix.schemas.V100;

public class V2704
extends NamespacedSchema {
    public V2704(int p_145883_, Schema p_145884_) {
        super(p_145883_, p_145884_);
    }

    protected static void m_145887_(Schema p_145888_, Map<String, Supplier<TypeTemplate>> p_145889_, String p_145890_) {
        p_145888_.register(p_145889_, p_145890_, () -> V100.m_17330_(p_145888_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_145892_) {
        Map $$1 = super.registerEntities(p_145892_);
        V2704.m_145887_(p_145892_, $$1, "minecraft:goat");
        return $$1;
    }
}

