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

public class V106
extends Schema {
    public V106(int p_17377_, Schema p_17378_) {
        super(p_17377_, p_17378_);
    }

    public void registerTypes(Schema p_17382_, Map<String, Supplier<TypeTemplate>> p_17383_, Map<String, Supplier<TypeTemplate>> p_17384_) {
        super.registerTypes(p_17382_, p_17383_, p_17384_);
        p_17382_.registerType(true, References.f_16789_, () -> DSL.optionalFields((String)"SpawnPotentials", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"Entity", (TypeTemplate)References.f_16785_.in(p_17382_))), (String)"SpawnData", (TypeTemplate)References.f_16785_.in(p_17382_)));
    }
}

