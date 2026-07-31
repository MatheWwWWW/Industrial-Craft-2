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
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;

public class DistancePredicate {
    public static final DistancePredicate f_26241_ = new DistancePredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_);
    private final MinMaxBounds.Doubles f_26242_;
    private final MinMaxBounds.Doubles f_26243_;
    private final MinMaxBounds.Doubles f_26244_;
    private final MinMaxBounds.Doubles f_26245_;
    private final MinMaxBounds.Doubles f_26246_;

    public DistancePredicate(MinMaxBounds.Doubles p_26249_, MinMaxBounds.Doubles p_26250_, MinMaxBounds.Doubles p_26251_, MinMaxBounds.Doubles p_26252_, MinMaxBounds.Doubles p_26253_) {
        this.f_26242_ = p_26249_;
        this.f_26243_ = p_26250_;
        this.f_26244_ = p_26251_;
        this.f_26245_ = p_26252_;
        this.f_26246_ = p_26253_;
    }

    public static DistancePredicate m_148836_(MinMaxBounds.Doubles p_148837_) {
        return new DistancePredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, p_148837_, MinMaxBounds.Doubles.f_154779_);
    }

    public static DistancePredicate m_148838_(MinMaxBounds.Doubles p_148839_) {
        return new DistancePredicate(MinMaxBounds.Doubles.f_154779_, p_148839_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_);
    }

    public static DistancePredicate m_148840_(MinMaxBounds.Doubles p_148841_) {
        return new DistancePredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, p_148841_);
    }

    public boolean m_26255_(double p_26256_, double p_26257_, double p_26258_, double p_26259_, double p_26260_, double p_26261_) {
        float $$6 = (float)(p_26256_ - p_26259_);
        float $$7 = (float)(p_26257_ - p_26260_);
        float $$8 = (float)(p_26258_ - p_26261_);
        if (!(this.f_26242_.m_154810_(Mth.m_14154_($$6)) && this.f_26243_.m_154810_(Mth.m_14154_($$7)) && this.f_26244_.m_154810_(Mth.m_14154_($$8)))) {
            return false;
        }
        if (!this.f_26245_.m_154812_($$6 * $$6 + $$8 * $$8)) {
            return false;
        }
        return this.f_26246_.m_154812_($$6 * $$6 + $$7 * $$7 + $$8 * $$8);
    }

    public static DistancePredicate m_26264_(@Nullable JsonElement p_26265_) {
        if (p_26265_ == null || p_26265_.isJsonNull()) {
            return f_26241_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_26265_, "distance");
        MinMaxBounds.Doubles $$2 = MinMaxBounds.Doubles.m_154791_($$1.get("x"));
        MinMaxBounds.Doubles $$3 = MinMaxBounds.Doubles.m_154791_($$1.get("y"));
        MinMaxBounds.Doubles $$4 = MinMaxBounds.Doubles.m_154791_($$1.get("z"));
        MinMaxBounds.Doubles $$5 = MinMaxBounds.Doubles.m_154791_($$1.get("horizontal"));
        MinMaxBounds.Doubles $$6 = MinMaxBounds.Doubles.m_154791_($$1.get("absolute"));
        return new DistancePredicate($$2, $$3, $$4, $$5, $$6);
    }

    public JsonElement m_26254_() {
        if (this == f_26241_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        $$0.add("x", this.f_26242_.m_55328_());
        $$0.add("y", this.f_26243_.m_55328_());
        $$0.add("z", this.f_26244_.m_55328_());
        $$0.add("horizontal", this.f_26245_.m_55328_());
        $$0.add("absolute", this.f_26246_.m_55328_());
        return $$0;
    }
}

