/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class V2684
extends NamespacedSchema {
    public V2684(int p_145856_, Schema p_145857_) {
        super(p_145856_, p_145857_);
    }

    public void registerTypes(Schema p_216760_, Map<String, Supplier<TypeTemplate>> p_216761_, Map<String, Supplier<TypeTemplate>> p_216762_) {
        super.registerTypes(p_216760_, p_216761_, p_216762_);
        p_216760_.registerType(false, References.f_216719_, () -> DSL.constType(V2684.m_17310_()));
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_145859_) {
        Map $$1 = super.registerBlockEntities(p_145859_);
        p_145859_.register($$1, "minecraft:sculk_sensor", () -> DSL.optionalFields((String)"listener", (TypeTemplate)DSL.optionalFields((String)"event", (TypeTemplate)DSL.optionalFields((String)"game_event", (TypeTemplate)References.f_216719_.in(p_145859_)))));
        return $$1;
    }
}

