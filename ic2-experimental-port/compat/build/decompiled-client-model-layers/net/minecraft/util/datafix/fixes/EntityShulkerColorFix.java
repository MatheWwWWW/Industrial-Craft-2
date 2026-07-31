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
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class EntityShulkerColorFix
extends NamedEntityFix {
    public EntityShulkerColorFix(Schema p_15673_, boolean p_15674_) {
        super(p_15673_, p_15674_, "EntityShulkerColorFix", References.f_16786_, "minecraft:shulker");
    }

    public Dynamic<?> m_15677_(Dynamic<?> p_15678_) {
        if (!p_15678_.get("Color").map(Dynamic::asNumber).result().isPresent()) {
            return p_15678_.set("Color", p_15678_.createByte((byte)10));
        }
        return p_15678_;
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15676_) {
        return p_15676_.update(DSL.remainderFinder(), this::m_15677_);
    }
}

