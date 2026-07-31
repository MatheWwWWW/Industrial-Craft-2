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

public class V2686
extends NamespacedSchema {
    public V2686(int p_145861_, Schema p_145862_) {
        super(p_145861_, p_145862_);
    }

    protected static void m_145865_(Schema p_145866_, Map<String, Supplier<TypeTemplate>> p_145867_, String p_145868_) {
        p_145866_.register(p_145867_, p_145868_, () -> V100.m_17330_(p_145866_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_145870_) {
        Map $$1 = super.registerEntities(p_145870_);
        V2686.m_145865_(p_145870_, $$1, "minecraft:axolotl");
        return $$1;
    }
}

