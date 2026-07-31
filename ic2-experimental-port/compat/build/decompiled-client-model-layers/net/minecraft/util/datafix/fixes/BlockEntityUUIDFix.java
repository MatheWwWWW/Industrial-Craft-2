/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.References;

public class BlockEntityUUIDFix
extends AbstractUUIDFix {
    public BlockEntityUUIDFix(Schema p_14883_) {
        super(p_14883_, References.f_16781_);
    }

    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.f_14569_), p_14885_ -> {
            p_14885_ = this.m_14574_((Typed<?>)p_14885_, "minecraft:conduit", this::m_14891_);
            p_14885_ = this.m_14574_((Typed<?>)p_14885_, "minecraft:skull", this::m_14889_);
            return p_14885_;
        });
    }

    private Dynamic<?> m_14889_(Dynamic<?> p_14890_) {
        return p_14890_.get("Owner").get().map(p_14894_ -> BlockEntityUUIDFix.m_14590_(p_14894_, "Id", "Id").orElse((Dynamic<?>)p_14894_)).map(p_14888_ -> p_14890_.remove("Owner").set("SkullOwner", p_14888_)).result().orElse(p_14890_);
    }

    private Dynamic<?> m_14891_(Dynamic<?> p_14892_) {
        return BlockEntityUUIDFix.m_14608_(p_14892_, "target_uuid", "Target").orElse(p_14892_);
    }
}

