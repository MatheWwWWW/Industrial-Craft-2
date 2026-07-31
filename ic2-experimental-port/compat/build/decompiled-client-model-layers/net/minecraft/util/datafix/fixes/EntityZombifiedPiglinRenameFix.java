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

public class EntityZombifiedPiglinRenameFix
extends SimplestEntityRenameFix {
    public static final Map<String, String> f_15814_ = ImmutableMap.builder().put((Object)"minecraft:zombie_pigman_spawn_egg", (Object)"minecraft:zombified_piglin_spawn_egg").build();

    public EntityZombifiedPiglinRenameFix(Schema p_15817_) {
        super("EntityZombifiedPiglinRenameFix", p_15817_, true);
    }

    @Override
    protected String m_7476_(String p_15819_) {
        return Objects.equals("minecraft:zombie_pigman", p_15819_) ? "minecraft:zombified_piglin" : p_15819_;
    }
}

