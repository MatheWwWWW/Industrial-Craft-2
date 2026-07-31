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

public class V1931
extends NamespacedSchema {
    public V1931(int p_17822_, Schema p_17823_) {
        super(p_17822_, p_17823_);
    }

    protected static void m_17826_(Schema p_17827_, Map<String, Supplier<TypeTemplate>> p_17828_, String p_17829_) {
        p_17827_.register(p_17828_, p_17829_, () -> V100.m_17330_(p_17827_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17831_) {
        Map $$1 = super.registerEntities(p_17831_);
        V1931.m_17826_(p_17831_, $$1, "minecraft:fox");
        return $$1;
    }
}

