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

public class V2688
extends NamespacedSchema {
    public V2688(int p_145872_, Schema p_145873_) {
        super(p_145872_, p_145873_);
    }

    protected static void m_145876_(Schema p_145877_, Map<String, Supplier<TypeTemplate>> p_145878_, String p_145879_) {
        p_145877_.register(p_145878_, p_145879_, () -> V100.m_17330_(p_145877_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_145881_) {
        Map $$1 = super.registerEntities(p_145881_);
        V2688.m_145876_(p_145881_, $$1, "minecraft:glow_squid");
        p_145881_.registerSimple($$1, "minecraft:glow_item_frame");
        return $$1;
    }
}

