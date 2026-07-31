/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class ColorlessShulkerEntityFix
extends NamedEntityFix {
    public ColorlessShulkerEntityFix(Schema p_15315_, boolean p_15316_) {
        super(p_15315_, p_15316_, "Colorless shulker entity fix", References.f_16786_, "minecraft:shulker");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_15318_) {
        return p_15318_.update(DSL.remainderFinder(), p_15320_ -> {
            if (p_15320_.get("Color").asInt(0) == 10) {
                return p_15320_.set("Color", p_15320_.createByte((byte)16));
            }
            return p_15320_;
        });
    }
}

