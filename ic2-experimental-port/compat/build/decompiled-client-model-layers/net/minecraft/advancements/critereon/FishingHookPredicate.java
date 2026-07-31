/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.phys.Vec3;

public class FishingHookPredicate
implements EntitySubPredicate {
    public static final FishingHookPredicate f_39756_ = new FishingHookPredicate(false);
    private static final String f_150706_ = "in_open_water";
    private final boolean f_39757_;

    private FishingHookPredicate(boolean p_39760_) {
        this.f_39757_ = p_39760_;
    }

    public static FishingHookPredicate m_39766_(boolean p_39767_) {
        return new FishingHookPredicate(p_39767_);
    }

    public static FishingHookPredicate m_219719_(JsonObject p_219720_) {
        JsonElement $$1 = p_219720_.get(f_150706_);
        if ($$1 != null) {
            return new FishingHookPredicate(GsonHelper.m_13877_($$1, f_150706_));
        }
        return f_39756_;
    }

    @Override
    public JsonObject m_213616_() {
        if (this == f_39756_) {
            return new JsonObject();
        }
        JsonObject $$0 = new JsonObject();
        $$0.add(f_150706_, (JsonElement)new JsonPrimitive(Boolean.valueOf(this.f_39757_)));
        return $$0;
    }

    @Override
    public EntitySubPredicate.Type m_213836_() {
        return EntitySubPredicate.Types.f_218849_;
    }

    @Override
    public boolean m_153246_(Entity p_219716_, ServerLevel p_219717_, @Nullable Vec3 p_219718_) {
        if (this == f_39756_) {
            return true;
        }
        if (!(p_219716_ instanceof FishingHook)) {
            return false;
        }
        FishingHook $$3 = (FishingHook)p_219716_;
        return this.f_39757_ == $$3.m_37166_();
    }
}

