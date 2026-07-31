/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.SimpleEntityRenameFix;

public class EntityCatSplitFix
extends SimpleEntityRenameFix {
    public EntityCatSplitFix(Schema p_15384_, boolean p_15385_) {
        super("EntityCatSplitFix", p_15384_, p_15385_);
    }

    @Override
    protected Pair<String, Dynamic<?>> m_6942_(String p_15387_, Dynamic<?> p_15388_) {
        if (Objects.equals("minecraft:ocelot", p_15387_)) {
            int $$2 = p_15388_.get("CatType").asInt(0);
            if ($$2 == 0) {
                String $$3 = p_15388_.get("Owner").asString("");
                String $$4 = p_15388_.get("OwnerUUID").asString("");
                if ($$3.length() > 0 || $$4.length() > 0) {
                    p_15388_.set("Trusting", p_15388_.createBoolean(true));
                }
            } else if ($$2 > 0 && $$2 < 4) {
                p_15388_ = p_15388_.set("CatType", p_15388_.createInt($$2));
                p_15388_ = p_15388_.set("OwnerUUID", p_15388_.createString(p_15388_.get("OwnerUUID").asString("")));
                return Pair.of((Object)"minecraft:cat", (Object)p_15388_);
            }
        }
        return Pair.of((Object)p_15387_, p_15388_);
    }
}

