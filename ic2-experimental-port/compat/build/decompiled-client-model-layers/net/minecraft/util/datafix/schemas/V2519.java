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

public class V2519
extends NamespacedSchema {
    public V2519(int p_17892_, Schema p_17893_) {
        super(p_17892_, p_17893_);
    }

    protected static void m_17896_(Schema p_17897_, Map<String, Supplier<TypeTemplate>> p_17898_, String p_17899_) {
        p_17897_.register(p_17898_, p_17899_, () -> V100.m_17330_(p_17897_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17901_) {
        Map $$1 = super.registerEntities(p_17901_);
        V2519.m_17896_(p_17901_, $$1, "minecraft:strider");
        return $$1;
    }
}

