/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class EntityShulkerRotationFix
extends NamedEntityFix {
    public EntityShulkerRotationFix(Schema p_15680_) {
        super(p_15680_, false, "EntityShulkerRotationFix", References.f_16786_, "minecraft:shulker");
    }

    public Dynamic<?> m_15683_(Dynamic<?> p_15684_) {
        List $$1 = p_15684_.get("Rotation").asList(p_15686_ -> p_15686_.asDouble(180.0));
        if (!$$1.isEmpty()) {
            $$1.set(0, (Double)$$1.get(0) - 180.0);
            return p_15684_.set("Rotation", p_15684_.createList($$1.stream().map(arg_0 -> p_15684_.createDouble(arg_0))));
        }
        return p_15684_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15682_) {
        return p_15682_.update(DSL.remainderFinder(), this::m_15683_);
    }
}

