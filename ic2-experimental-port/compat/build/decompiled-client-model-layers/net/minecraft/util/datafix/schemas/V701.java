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

public class V701
extends Schema {
    public V701(int p_17996_, Schema p_17997_) {
        super(p_17996_, p_17997_);
    }

    protected static void m_18000_(Schema p_18001_, Map<String, Supplier<TypeTemplate>> p_18002_, String p_18003_) {
        p_18001_.register(p_18002_, p_18003_, () -> V100.m_17330_(p_18001_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18005_) {
        Map $$1 = super.registerEntities(p_18005_);
        V701.m_18000_(p_18005_, $$1, "WitherSkeleton");
        V701.m_18000_(p_18005_, $$1, "Stray");
        return $$1;
    }
}

