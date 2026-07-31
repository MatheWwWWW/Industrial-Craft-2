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

public class EntityZombieSplitFix
extends SimpleEntityRenameFix {
    public EntityZombieSplitFix(Schema p_15798_, boolean p_15799_) {
        super("EntityZombieSplitFix", p_15798_, p_15799_);
    }

    @Override
    protected Pair<String, Dynamic<?>> m_6942_(String p_15801_, Dynamic<?> p_15802_) {
        if (Objects.equals("Zombie", p_15801_)) {
            String $$2 = "Zombie";
            int $$3 = p_15802_.get("ZombieType").asInt(0);
            switch ($$3) {
                default: {
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    $$2 = "ZombieVillager";
                    p_15802_ = p_15802_.set("Profession", p_15802_.createInt($$3 - 1));
                    break;
                }
                case 6: {
                    $$2 = "Husk";
                }
            }
            p_15802_ = p_15802_.remove("ZombieType");
            return Pair.of((Object)$$2, (Object)p_15802_);
        }
        return Pair.of((Object)p_15801_, p_15802_);
    }
}

