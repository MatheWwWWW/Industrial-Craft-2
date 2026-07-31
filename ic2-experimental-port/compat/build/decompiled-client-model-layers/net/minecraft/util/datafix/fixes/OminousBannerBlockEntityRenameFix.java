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
import java.util.Optional;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public class OminousBannerBlockEntityRenameFix
extends NamedEntityFix {
    public OminousBannerBlockEntityRenameFix(Schema p_16548_, boolean p_16549_) {
        super(p_16548_, p_16549_, "OminousBannerBlockEntityRenameFix", References.f_16781_, "minecraft:banner");
    }

    @Override
    protected Typed<?> m_7504_(Typed<?> p_16551_) {
        return p_16551_.update(DSL.remainderFinder(), this::m_16552_);
    }

    private Dynamic<?> m_16552_(Dynamic<?> p_16553_) {
        Optional $$1 = p_16553_.get("CustomName").asString().result();
        if ($$1.isPresent()) {
            String $$2 = (String)$$1.get();
            $$2 = $$2.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
            return p_16553_.set("CustomName", p_16553_.createString($$2));
        }
        return p_16553_;
    }
}

