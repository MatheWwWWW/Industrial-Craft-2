/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.Hook$HookFunction
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V99;

public class V102
extends Schema {
    public V102(int p_17356_, Schema p_17357_) {
        super(p_17356_, p_17357_);
    }

    public void registerTypes(Schema p_17361_, Map<String, Supplier<TypeTemplate>> p_17362_, Map<String, Supplier<TypeTemplate>> p_17363_) {
        super.registerTypes(p_17361_, p_17362_, p_17363_);
        p_17361_.registerType(true, References.f_16782_, () -> DSL.hook((TypeTemplate)DSL.optionalFields((String)"id", (TypeTemplate)References.f_16788_.in(p_17361_), (String)"tag", (TypeTemplate)DSL.optionalFields((String)"EntityTag", (TypeTemplate)References.f_16785_.in(p_17361_), (String)"BlockEntityTag", (TypeTemplate)References.f_16781_.in(p_17361_), (String)"CanDestroy", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_17361_)), (String)"CanPlaceOn", (TypeTemplate)DSL.list((TypeTemplate)References.f_16787_.in(p_17361_)), (String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17361_)))), (Hook.HookFunction)V99.f_18180_, (Hook.HookFunction)Hook.HookFunction.IDENTITY));
    }
}

