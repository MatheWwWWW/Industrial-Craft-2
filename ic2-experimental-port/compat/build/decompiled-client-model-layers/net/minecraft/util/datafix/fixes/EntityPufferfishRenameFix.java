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

public class EntityPufferfishRenameFix
extends SimplestEntityRenameFix {
    public static final Map<String, String> f_15584_ = ImmutableMap.builder().put((Object)"minecraft:puffer_fish_spawn_egg", (Object)"minecraft:pufferfish_spawn_egg").build();

    public EntityPufferfishRenameFix(Schema p_15587_, boolean p_15588_) {
        super("EntityPufferfishRenameFix", p_15587_, p_15588_);
    }

    @Override
    protected String m_7476_(String p_15590_) {
        return Objects.equals("minecraft:puffer_fish", p_15590_) ? "minecraft:pufferfish" : p_15590_;
    }
}

