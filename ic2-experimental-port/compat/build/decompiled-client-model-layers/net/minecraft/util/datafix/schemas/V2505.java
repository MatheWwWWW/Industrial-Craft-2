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

public class V2505
extends NamespacedSchema {
    public V2505(int p_17870_, Schema p_17871_) {
        super(p_17870_, p_17871_);
    }

    protected static void m_17874_(Schema p_17875_, Map<String, Supplier<TypeTemplate>> p_17876_, String p_17877_) {
        p_17875_.register(p_17876_, p_17877_, () -> V100.m_17330_(p_17875_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17879_) {
        Map $$1 = super.registerEntities(p_17879_);
        V2505.m_17874_(p_17879_, $$1, "minecraft:piglin");
        return $$1;
    }
}

