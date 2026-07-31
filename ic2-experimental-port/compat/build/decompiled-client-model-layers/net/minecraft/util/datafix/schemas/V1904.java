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

public class V1904
extends NamespacedSchema {
    public V1904(int p_17757_, Schema p_17758_) {
        super(p_17757_, p_17758_);
    }

    protected static void m_17761_(Schema p_17762_, Map<String, Supplier<TypeTemplate>> p_17763_, String p_17764_) {
        p_17762_.register(p_17763_, p_17764_, () -> V100.m_17330_(p_17762_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17766_) {
        Map $$1 = super.registerEntities(p_17766_);
        V1904.m_17761_(p_17766_, $$1, "minecraft:cat");
        return $$1;
    }
}

