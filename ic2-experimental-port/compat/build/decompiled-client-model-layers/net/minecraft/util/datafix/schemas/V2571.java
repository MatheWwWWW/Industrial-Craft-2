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

public class V2571
extends NamespacedSchema {
    public V2571(int p_145845_, Schema p_145846_) {
        super(p_145845_, p_145846_);
    }

    protected static void m_145849_(Schema p_145850_, Map<String, Supplier<TypeTemplate>> p_145851_, String p_145852_) {
        p_145850_.register(p_145851_, p_145852_, () -> V100.m_17330_(p_145850_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_145854_) {
        Map $$1 = super.registerEntities(p_145854_);
        V2571.m_145849_(p_145854_, $$1, "minecraft:goat");
        return $$1;
    }
}

