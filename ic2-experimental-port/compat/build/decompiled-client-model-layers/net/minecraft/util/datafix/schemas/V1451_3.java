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
import net.minecraft.util.datafix.schemas.V100;

public class V1451_3
extends NamespacedSchema {
    public V1451_3(int p_17444_, Schema p_17445_) {
        super(p_17444_, p_17445_);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17472_) {
        Map $$1 = super.registerEntities(p_17472_);
        p_17472_.registerSimple($$1, "minecraft:egg");
        p_17472_.registerSimple($$1, "minecraft:ender_pearl");
        p_17472_.registerSimple($$1, "minecraft:fireball");
        p_17472_.register($$1, "minecraft:potion", p_17450_ -> DSL.optionalFields((String)"Potion", (TypeTemplate)References.f_16782_.in(p_17472_)));
        p_17472_.registerSimple($$1, "minecraft:small_fireball");
        p_17472_.registerSimple($$1, "minecraft:snowball");
        p_17472_.registerSimple($$1, "minecraft:wither_skull");
        p_17472_.registerSimple($$1, "minecraft:xp_bottle");
        p_17472_.register($$1, "minecraft:arrow", () -> DSL.optionalFields((String)"inBlockState", (TypeTemplate)References.f_16783_.in(p_17472_)));
        p_17472_.register($$1, "minecraft:enderman", () -> DSL.optionalFields((String)"carriedBlockState", (TypeTemplate)References.f_16783_.in(p_17472_), (TypeTemplate)V100.m_17330_(p_17472_)));
        p_17472_.register($$1, "minecraft:falling_block", () -> DSL.optionalFields((String)"BlockState", (TypeTemplate)References.f_16783_.in(p_17472_), (String)"TileEntityData", (TypeTemplate)References.f_16781_.in(p_17472_)));
        p_17472_.register($$1, "minecraft:spectral_arrow", () -> DSL.optionalFields((String)"inBlockState", (TypeTemplate)References.f_16783_.in(p_17472_)));
        p_17472_.register($$1, "minecraft:chest_minecart", () -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17472_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17472_))));
        p_17472_.register($$1, "minecraft:commandblock_minecart", () -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17472_)));
        p_17472_.register($$1, "minecraft:furnace_minecart", () -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17472_)));
        p_17472_.register($$1, "minecraft:hopper_minecart", () -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17472_), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17472_))));
        p_17472_.register($$1, "minecraft:minecart", () -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17472_)));
        p_17472_.register($$1, "minecraft:spawner_minecart", () -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17472_), (TypeTemplate)References.f_16789_.in(p_17472_)));
        p_17472_.register($$1, "minecraft:tnt_minecart", () -> DSL.optionalFields((String)"DisplayState", (TypeTemplate)References.f_16783_.in(p_17472_)));
        return $$1;
    }
}

