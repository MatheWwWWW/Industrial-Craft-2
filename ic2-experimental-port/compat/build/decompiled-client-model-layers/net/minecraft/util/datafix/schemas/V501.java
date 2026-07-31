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
import net.minecraft.util.datafix.schemas.V100;

public class V501
extends Schema {
    public V501(int p_17974_, Schema p_17975_) {
        super(p_17974_, p_17975_);
    }

    protected static void m_17978_(Schema p_17979_, Map<String, Supplier<TypeTemplate>> p_17980_, String p_17981_) {
        p_17979_.register(p_17980_, p_17981_, () -> V100.m_17330_(p_17979_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17983_) {
        Map $$1 = super.registerEntities(p_17983_);
        V501.m_17978_(p_17983_, $$1, "PolarBear");
        return $$1;
    }
}

