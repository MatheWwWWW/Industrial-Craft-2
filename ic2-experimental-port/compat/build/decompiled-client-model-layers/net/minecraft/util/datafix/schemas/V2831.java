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

public class V2831
extends NamespacedSchema {
    public V2831(int p_185208_, Schema p_185209_) {
        super(p_185208_, p_185209_);
    }

    public void registerTypes(Schema p_185213_, Map<String, Supplier<TypeTemplate>> p_185214_, Map<String, Supplier<TypeTemplate>> p_185215_) {
        super.registerTypes(p_185213_, p_185214_, p_185215_);
        p_185213_.registerType(true, References.f_16789_, () -> DSL.optionalFields((String)"SpawnPotentials", (TypeTemplate)DSL.list((TypeTemplate)DSL.fields((String)"data", (TypeTemplate)DSL.fields((String)"entity", (TypeTemplate)References.f_16785_.in(p_185213_)))), (String)"SpawnData", (TypeTemplate)DSL.fields((String)"entity", (TypeTemplate)References.f_16785_.in(p_185213_))));
    }
}

