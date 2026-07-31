/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.SimplestEntityRenameFix;

public class EntityTippedArrowFix
extends SimplestEntityRenameFix {
    public EntityTippedArrowFix(Schema p_15711_, boolean p_15712_) {
        super("EntityTippedArrowFix", p_15711_, p_15712_);
    }

    @Override
    protected String m_7476_(String p_15714_) {
        return Objects.equals(p_15714_, "TippedArrow") ? "Arrow" : p_15714_;
    }
}

