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
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.phys.Vec3;

public class SlimePredicate
implements EntitySubPredicate {
    private final MinMaxBounds.Ints f_223418_;

    private SlimePredicate(MinMaxBounds.Ints p_223420_) {
        this.f_223418_ = p_223420_;
    }

    public static SlimePredicate m_223426_(MinMaxBounds.Ints p_223427_) {
        return new SlimePredicate(p_223427_);
    }

    public static SlimePredicate m_223428_(JsonObject p_223429_) {
        MinMaxBounds.Ints $$1 = MinMaxBounds.Ints.m_55373_(p_223429_.get("size"));
        return new SlimePredicate($$1);
    }

    @Override
    public JsonObject m_213616_() {
        JsonObject $$0 = new JsonObject();
        $$0.add("size", this.f_223418_.m_55328_());
        return $$0;
    }

    @Override
    public boolean m_153246_(Entity p_223423_, ServerLevel p_223424_, @Nullable Vec3 p_223425_) {
        if (p_223423_ instanceof Slime) {
            Slime $$3 = (Slime)p_223423_;
            return this.f_223418_.m_55390_($$3.m_33632_());
        }
        return false;
    }

    @Override
    public EntitySubPredicate.Type m_213836_() {
        return EntitySubPredicate.Types.f_218851_;
    }
}

