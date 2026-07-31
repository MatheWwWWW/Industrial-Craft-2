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

public class EntityElderGuardianSplitFix
extends SimpleEntityRenameFix {
    public EntityElderGuardianSplitFix(Schema p_15411_, boolean p_15412_) {
        super("EntityElderGuardianSplitFix", p_15411_, p_15412_);
    }

    @Override
    protected Pair<String, Dynamic<?>> m_6942_(String p_15414_, Dynamic<?> p_15415_) {
        return Pair.of((Object)(Objects.equals(p_15414_, "Guardian") && p_15415_.get("Elder").asBoolean(false) ? "ElderGuardian" : p_15414_), p_15415_);
    }
}

