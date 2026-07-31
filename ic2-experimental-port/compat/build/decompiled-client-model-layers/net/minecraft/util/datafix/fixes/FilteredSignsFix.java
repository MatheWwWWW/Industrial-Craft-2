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

public class FilteredSignsFix
extends NamedEntityFix {
    public FilteredSignsFix(Schema p_216666_) {
        super(p_216666_, false, "Remove filtered text from signs", References.f_16781_, "minecraft:sign");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_216668_) {
        return p_216668_.update(DSL.remainderFinder(), p_216670_ -> p_216670_.remove("FilteredText1").remove("FilteredText2").remove("FilteredText3").remove("FilteredText4"));
    }
}

