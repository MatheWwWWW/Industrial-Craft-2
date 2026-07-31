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

public class V143
extends Schema {
    public V143(int p_17415_, Schema p_17416_) {
        super(p_17415_, p_17416_);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17418_) {
        Map $$1 = super.registerEntities(p_17418_);
        $$1.remove("TippedArrow");
        return $$1;
    }
}

