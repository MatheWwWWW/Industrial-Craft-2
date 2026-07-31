/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;

public class LightPredicate {
    public static final LightPredicate f_51335_ = new LightPredicate(MinMaxBounds.Ints.f_55364_);
    private final MinMaxBounds.Ints f_51336_;

    LightPredicate(MinMaxBounds.Ints p_51339_) {
        this.f_51336_ = p_51339_;
    }

    public boolean m_51341_(ServerLevel p_51342_, BlockPos p_51343_) {
        if (this == f_51335_) {
            return true;
        }
        if (!p_51342_.m_46749_(p_51343_)) {
            return false;
        }
        return this.f_51336_.m_55390_(p_51342_.m_46803_(p_51343_));
    }

    public JsonElement m_51340_() {
        if (this == f_51335_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        $$0.add("light", this.f_51336_.m_55328_());
        return $$0;
    }

    public static LightPredicate m_51344_(@Nullable JsonElement p_51345_) {
        if (p_51345_ == null || p_51345_.isJsonNull()) {
            return f_51335_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_51345_, "light");
        MinMaxBounds.Ints $$2 = MinMaxBounds.Ints.m_55373_($$1.get("light"));
        return new LightPredicate($$2);
    }

    public static class Builder {
        private MinMaxBounds.Ints f_153101_ = MinMaxBounds.Ints.f_55364_;

        public static Builder m_153103_() {
            return new Builder();
        }

        public Builder m_153104_(MinMaxBounds.Ints p_153105_) {
            this.f_153101_ = p_153105_;
            return this;
        }

        public LightPredicate m_153106_() {
            return new LightPredicate(this.f_153101_);
        }
    }
}

