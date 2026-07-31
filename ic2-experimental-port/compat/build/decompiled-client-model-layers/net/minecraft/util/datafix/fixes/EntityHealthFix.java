/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.datafix.fixes.References;

public class EntityHealthFix
extends DataFix {
    private static final Set<String> f_15431_ = Sets.newHashSet((Object[])new String[]{"ArmorStand", "Bat", "Blaze", "CaveSpider", "Chicken", "Cow", "Creeper", "EnderDragon", "Enderman", "Endermite", "EntityHorse", "Ghast", "Giant", "Guardian", "LavaSlime", "MushroomCow", "Ozelot", "Pig", "PigZombie", "Rabbit", "Sheep", "Shulker", "Silverfish", "Skeleton", "Slime", "SnowMan", "Spider", "Squid", "Villager", "VillagerGolem", "Witch", "WitherBoss", "Wolf", "Zombie"});

    public EntityHealthFix(Schema p_15434_, boolean p_15435_) {
        super(p_15434_, p_15435_);
    }

    /*
     * WARNING - void declaration
     */
    public Dynamic<?> m_15438_(Dynamic<?> p_15439_) {
        void $$5;
        Optional $$1 = p_15439_.get("HealF").asNumber().result();
        Optional $$2 = p_15439_.get("Health").asNumber().result();
        if ($$1.isPresent()) {
            float $$3 = ((Number)$$1.get()).floatValue();
            p_15439_ = p_15439_.remove("HealF");
        } else if ($$2.isPresent()) {
            float $$4 = ((Number)$$2.get()).floatValue();
        } else {
            return p_15439_;
        }
        return p_15439_.set("Health", p_15439_.createFloat((float)$$5));
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("EntityHealthFix", this.getInputSchema().getType(References.f_16786_), p_15437_ -> p_15437_.update(DSL.remainderFinder(), this::m_15438_));
    }
}

