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

public class EntitySkeletonSplitFix
extends SimpleEntityRenameFix {
    public EntitySkeletonSplitFix(Schema p_15688_, boolean p_15689_) {
        super("EntitySkeletonSplitFix", p_15688_, p_15689_);
    }

    @Override
    protected Pair<String, Dynamic<?>> m_6942_(String p_15691_, Dynamic<?> p_15692_) {
        if (Objects.equals(p_15691_, "Skeleton")) {
            int $$2 = p_15692_.get("SkeletonType").asInt(0);
            if ($$2 == 1) {
                p_15691_ = "WitherSkeleton";
            } else if ($$2 == 2) {
                p_15691_ = "Stray";
            }
        }
        return Pair.of((Object)p_15691_, p_15692_);
    }
}

