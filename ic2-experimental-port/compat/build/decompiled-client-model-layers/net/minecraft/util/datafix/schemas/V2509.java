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

public class V2509
extends NamespacedSchema {
    public V2509(int p_17881_, Schema p_17882_) {
        super(p_17881_, p_17882_);
    }

    protected static void m_17885_(Schema p_17886_, Map<String, Supplier<TypeTemplate>> p_17887_, String p_17888_) {
        p_17886_.register(p_17887_, p_17888_, () -> V100.m_17330_(p_17886_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17890_) {
        Map $$1 = super.registerEntities(p_17890_);
        $$1.remove("minecraft:zombie_pigman");
        V2509.m_17885_(p_17890_, $$1, "minecraft:zombified_piglin");
        return $$1;
    }
}

