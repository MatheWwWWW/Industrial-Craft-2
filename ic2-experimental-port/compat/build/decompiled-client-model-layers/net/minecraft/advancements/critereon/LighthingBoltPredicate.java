/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.phys.Vec3;

public class LighthingBoltPredicate
implements EntitySubPredicate {
    private static final String f_153233_ = "blocks_set_on_fire";
    private static final String f_153234_ = "entity_struck";
    private final MinMaxBounds.Ints f_153235_;
    private final EntityPredicate f_153236_;

    private LighthingBoltPredicate(MinMaxBounds.Ints p_153239_, EntityPredicate p_153240_) {
        this.f_153235_ = p_153239_;
        this.f_153236_ = p_153240_;
    }

    public static LighthingBoltPredicate m_153250_(MinMaxBounds.Ints p_153251_) {
        return new LighthingBoltPredicate(p_153251_, EntityPredicate.f_36550_);
    }

    public static LighthingBoltPredicate m_220332_(JsonObject p_220333_) {
        return new LighthingBoltPredicate(MinMaxBounds.Ints.m_55373_(p_220333_.get(f_153233_)), EntityPredicate.m_36614_(p_220333_.get(f_153234_)));
    }

    @Override
    public JsonObject m_213616_() {
        JsonObject $$0 = new JsonObject();
        $$0.add(f_153233_, this.f_153235_.m_55328_());
        $$0.add(f_153234_, this.f_153236_.m_36606_());
        return $$0;
    }

    @Override
    public EntitySubPredicate.Type m_213836_() {
        return EntitySubPredicate.Types.f_218848_;
    }

    @Override
    public boolean m_153246_(Entity p_153247_, ServerLevel p_153248_, @Nullable Vec3 p_153249_) {
        if (!(p_153247_ instanceof LightningBolt)) {
            return false;
        }
        LightningBolt $$3 = (LightningBolt)p_153247_;
        return this.f_153235_.m_55390_($$3.m_147159_()) && (this.f_153236_ == EntityPredicate.f_36550_ || $$3.m_147160_().anyMatch(p_153245_ -> this.f_153236_.m_36607_(p_153248_, p_153249_, (Entity)p_153245_)));
    }
}

