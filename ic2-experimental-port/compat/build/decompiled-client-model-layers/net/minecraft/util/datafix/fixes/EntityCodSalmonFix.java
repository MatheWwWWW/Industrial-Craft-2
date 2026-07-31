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
import net.minecraft.util.datafix.fixes.SimplestEntityRenameFix;

public class EntityCodSalmonFix
extends SimplestEntityRenameFix {
    public static final Map<String, String> f_15389_ = ImmutableMap.builder().put((Object)"minecraft:salmon_mob", (Object)"minecraft:salmon").put((Object)"minecraft:cod_mob", (Object)"minecraft:cod").build();
    public static final Map<String, String> f_15390_ = ImmutableMap.builder().put((Object)"minecraft:salmon_mob_spawn_egg", (Object)"minecraft:salmon_spawn_egg").put((Object)"minecraft:cod_mob_spawn_egg", (Object)"minecraft:cod_spawn_egg").build();

    public EntityCodSalmonFix(Schema p_15393_, boolean p_15394_) {
        super("EntityCodSalmonFix", p_15393_, p_15394_);
    }

    @Override
    protected String m_7476_(String p_15396_) {
        return f_15389_.getOrDefault(p_15396_, p_15396_);
    }
}

