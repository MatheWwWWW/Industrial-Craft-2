/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.References;

public class EntityPaintingItemFrameDirectionFix
extends DataFix {
    private static final int[][] f_15496_ = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

    public EntityPaintingItemFrameDirectionFix(Schema p_15499_, boolean p_15500_) {
        super(p_15499_, p_15500_);
    }

    private Dynamic<?> m_15509_(Dynamic<?> p_15510_, boolean p_15511_, boolean p_15512_) {
        if ((p_15511_ || p_15512_) && !p_15510_.get("Facing").asNumber().result().isPresent()) {
            int $$5;
            if (p_15510_.get("Direction").asNumber().result().isPresent()) {
                int $$3 = p_15510_.get("Direction").asByte((byte)0) % f_15496_.length;
                int[] $$4 = f_15496_[$$3];
                p_15510_ = p_15510_.set("TileX", p_15510_.createInt(p_15510_.get("TileX").asInt(0) + $$4[0]));
                p_15510_ = p_15510_.set("TileY", p_15510_.createInt(p_15510_.get("TileY").asInt(0) + $$4[1]));
                p_15510_ = p_15510_.set("TileZ", p_15510_.createInt(p_15510_.get("TileZ").asInt(0) + $$4[2]));
                p_15510_ = p_15510_.remove("Direction");
                if (p_15512_ && p_15510_.get("ItemRotation").asNumber().result().isPresent()) {
                    p_15510_ = p_15510_.set("ItemRotation", p_15510_.createByte((byte)(p_15510_.get("ItemRotation").asByte((byte)0) * 2)));
                }
            } else {
                $$5 = p_15510_.get("Dir").asByte((byte)0) % f_15496_.length;
                p_15510_ = p_15510_.remove("Dir");
            }
            p_15510_ = p_15510_.set("Facing", p_15510_.createByte((byte)$$5));
        }
        return p_15510_;
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getChoiceType(References.f_16786_, "Painting");
        OpticFinder $$1 = DSL.namedChoice((String)"Painting", (Type)$$0);
        Type $$2 = this.getInputSchema().getChoiceType(References.f_16786_, "ItemFrame");
        OpticFinder $$3 = DSL.namedChoice((String)"ItemFrame", (Type)$$2);
        Type $$4 = this.getInputSchema().getType(References.f_16786_);
        TypeRewriteRule $$5 = this.fixTypeEverywhereTyped("EntityPaintingFix", $$4, p_15516_ -> p_15516_.updateTyped($$1, $$0, p_145300_ -> p_145300_.update(DSL.remainderFinder(), p_145302_ -> this.m_15509_((Dynamic<?>)p_145302_, true, false))));
        TypeRewriteRule $$6 = this.fixTypeEverywhereTyped("EntityItemFrameFix", $$4, p_15504_ -> p_15504_.updateTyped($$3, $$2, p_145296_ -> p_145296_.update(DSL.remainderFinder(), p_145298_ -> this.m_15509_((Dynamic<?>)p_145298_, false, true))));
        return TypeRewriteRule.seq((TypeRewriteRule)$$5, (TypeRewriteRule)$$6);
    }
}

