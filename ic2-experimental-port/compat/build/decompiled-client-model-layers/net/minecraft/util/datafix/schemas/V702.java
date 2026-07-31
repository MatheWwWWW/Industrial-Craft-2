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

public class V702
extends Schema {
    public V702(int p_18007_, Schema p_18008_) {
        super(p_18007_, p_18008_);
    }

    protected static void m_18011_(Schema p_18012_, Map<String, Supplier<TypeTemplate>> p_18013_, String p_18014_) {
        p_18012_.register(p_18013_, p_18014_, () -> V100.m_17330_(p_18012_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18016_) {
        Map $$1 = super.registerEntities(p_18016_);
        V702.m_18011_(p_18016_, $$1, "ZombieVillager");
        V702.m_18011_(p_18016_, $$1, "Husk");
        return $$1;
    }
}

