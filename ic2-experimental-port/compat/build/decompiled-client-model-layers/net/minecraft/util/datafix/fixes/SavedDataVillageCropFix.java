/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.util.datafix.fixes.References;

public class SavedDataVillageCropFix
extends DataFix {
    public SavedDataVillageCropFix(Schema p_16882_, boolean p_16883_) {
        super(p_16882_, p_16883_);
    }

    public TypeRewriteRule makeRule() {
        return this.writeFixAndRead("SavedDataVillageCropFix", this.getInputSchema().getType(References.f_16790_), this.getOutputSchema().getType(References.f_16790_), this::m_16884_);
    }

    private <T> Dynamic<T> m_16884_(Dynamic<T> p_16885_) {
        return p_16885_.update("Children", SavedDataVillageCropFix::m_16891_);
    }

    private static <T> Dynamic<T> m_16891_(Dynamic<T> p_16892_) {
        return p_16892_.asStreamOpt().map(SavedDataVillageCropFix::m_16889_).map(arg_0 -> p_16892_.createList(arg_0)).result().orElse(p_16892_);
    }

    private static Stream<? extends Dynamic<?>> m_16889_(Stream<? extends Dynamic<?>> p_16890_) {
        return p_16890_.map(p_16898_ -> {
            String $$1 = p_16898_.get("id").asString("");
            if ("ViF".equals($$1)) {
                return SavedDataVillageCropFix.m_16893_(p_16898_);
            }
            if ("ViDF".equals($$1)) {
                return SavedDataVillageCropFix.m_16895_(p_16898_);
            }
            return p_16898_;
        });
    }

    private static <T> Dynamic<T> m_16893_(Dynamic<T> p_16894_) {
        p_16894_ = SavedDataVillageCropFix.m_16886_(p_16894_, "CA");
        return SavedDataVillageCropFix.m_16886_(p_16894_, "CB");
    }

    private static <T> Dynamic<T> m_16895_(Dynamic<T> p_16896_) {
        p_16896_ = SavedDataVillageCropFix.m_16886_(p_16896_, "CA");
        p_16896_ = SavedDataVillageCropFix.m_16886_(p_16896_, "CB");
        p_16896_ = SavedDataVillageCropFix.m_16886_(p_16896_, "CC");
        return SavedDataVillageCropFix.m_16886_(p_16896_, "CD");
    }

    private static <T> Dynamic<T> m_16886_(Dynamic<T> p_16887_, String p_16888_) {
        if (p_16887_.get(p_16888_).asNumber().result().isPresent()) {
            return p_16887_.set(p_16888_, BlockStateData.m_14952_(p_16887_.get(p_16888_).asInt(0) << 4));
        }
        return p_16887_;
    }
}

