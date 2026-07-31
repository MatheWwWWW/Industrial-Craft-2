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

public class BlockEntityKeepPacked
extends NamedEntityFix {
    public BlockEntityKeepPacked(Schema p_14848_, boolean p_14849_) {
        super(p_14848_, p_14849_, "BlockEntityKeepPacked", References.f_16781_, "DUMMY");
    }

    private static Dynamic<?> m_14852_(Dynamic<?> p_14853_) {
        return p_14853_.set("keepPacked", p_14853_.createBoolean(true));
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_14851_) {
        return p_14851_.update(DSL.remainderFinder(), BlockEntityKeepPacked::m_14852_);
    }
}

