/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.ItemStackTagFix;

public class GoatHornIdFix
extends ItemStackTagFix {
    private static final String[] f_216671_ = new String[]{"minecraft:ponder_goat_horn", "minecraft:sing_goat_horn", "minecraft:seek_goat_horn", "minecraft:feel_goat_horn", "minecraft:admire_goat_horn", "minecraft:call_goat_horn", "minecraft:yearn_goat_horn", "minecraft:dream_goat_horn"};

    public GoatHornIdFix(Schema p_216674_) {
        super(p_216674_, "GoatHornIdFix", p_216678_ -> p_216678_.equals("minecraft:goat_horn"));
    }

    @Override
    protected <T> Dynamic<T> m_213922_(Dynamic<T> p_216676_) {
        int $$1 = p_216676_.get("SoundVariant").asInt(0);
        String $$2 = f_216671_[$$1 >= 0 && $$1 < f_216671_.length ? $$1 : 0];
        return p_216676_.remove("SoundVariant").set("instrument", p_216676_.createString($$2));
    }
}

