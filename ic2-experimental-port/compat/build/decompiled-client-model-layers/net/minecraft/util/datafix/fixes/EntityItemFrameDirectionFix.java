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

public class EntityItemFrameDirectionFix
extends NamedEntityFix {
    public EntityItemFrameDirectionFix(Schema p_15468_, boolean p_15469_) {
        super(p_15468_, p_15469_, "EntityItemFrameDirectionFix", References.f_16786_, "minecraft:item_frame");
    }

    public Dynamic<?> m_15474_(Dynamic<?> p_15475_) {
        return p_15475_.set("Facing", p_15475_.createByte(EntityItemFrameDirectionFix.m_15470_(p_15475_.get("Facing").asByte((byte)0))));
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15473_) {
        return p_15473_.update(DSL.remainderFinder(), this::m_15474_);
    }

    private static byte m_15470_(byte p_15471_) {
        switch (p_15471_) {
            default: {
                return 2;
            }
            case 0: {
                return 3;
            }
            case 1: {
                return 4;
            }
            case 3: 
        }
        return 5;
    }
}

