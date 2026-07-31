/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.schemas.Schema
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import net.minecraft.util.datafix.fixes.SimplestEntityRenameFix;

public class EntityRavagerRenameFix
extends SimplestEntityRenameFix {
    public static final Map<String, String> f_15591_ = ImmutableMap.builder().put((Object)"minecraft:illager_beast_spawn_egg", (Object)"minecraft:ravager_spawn_egg").build();

    public EntityRavagerRenameFix(Schema p_15594_, boolean p_15595_) {
        super("EntityRavagerRenameFix", p_15594_, p_15595_);
    }

    @Override
    protected String m_7476_(String p_15597_) {
        return Objects.equals("minecraft:illager_beast", p_15597_) ? "minecraft:ravager" : p_15597_;
    }
}

