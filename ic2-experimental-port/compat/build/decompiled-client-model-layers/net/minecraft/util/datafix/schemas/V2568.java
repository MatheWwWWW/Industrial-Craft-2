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

public class V2568
extends NamespacedSchema {
    public V2568(int p_17963_, Schema p_17964_) {
        super(p_17963_, p_17964_);
    }

    protected static void m_17967_(Schema p_17968_, Map<String, Supplier<TypeTemplate>> p_17969_, String p_17970_) {
        p_17968_.register(p_17969_, p_17970_, () -> V100.m_17330_(p_17968_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17972_) {
        Map $$1 = super.registerEntities(p_17972_);
        V2568.m_17967_(p_17972_, $$1, "minecraft:piglin_brute");
        return $$1;
    }
}

