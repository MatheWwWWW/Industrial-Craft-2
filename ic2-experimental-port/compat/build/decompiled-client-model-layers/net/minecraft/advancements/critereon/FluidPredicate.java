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
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class FluidPredicate {
    public static final FluidPredicate f_41094_ = new FluidPredicate(null, null, StatePropertiesPredicate.f_67658_);
    @Nullable
    private final TagKey<Fluid> f_41095_;
    @Nullable
    private final Fluid f_41096_;
    private final StatePropertiesPredicate f_41097_;

    public FluidPredicate(@Nullable TagKey<Fluid> p_204102_, @Nullable Fluid p_204103_, StatePropertiesPredicate p_204104_) {
        this.f_41095_ = p_204102_;
        this.f_41096_ = p_204103_;
        this.f_41097_ = p_204104_;
    }

    public boolean m_41104_(ServerLevel p_41105_, BlockPos p_41106_) {
        if (this == f_41094_) {
            return true;
        }
        if (!p_41105_.m_46749_(p_41106_)) {
            return false;
        }
        FluidState $$2 = p_41105_.m_6425_(p_41106_);
        if (this.f_41095_ != null && !$$2.m_205070_(this.f_41095_)) {
            return false;
        }
        if (this.f_41096_ != null && !$$2.m_192917_(this.f_41096_)) {
            return false;
        }
        return this.f_41097_.m_67684_($$2);
    }

    public static FluidPredicate m_41107_(@Nullable JsonElement p_41108_) {
        if (p_41108_ == null || p_41108_.isJsonNull()) {
            return f_41094_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_41108_, "fluid");
        Fluid $$2 = null;
        if ($$1.has("fluid")) {
            ResourceLocation $$3 = new ResourceLocation(GsonHelper.m_13906_($$1, "fluid"));
            $$2 = Registry.f_122822_.m_7745_($$3);
        }
        TagKey<Fluid> $$4 = null;
        if ($$1.has("tag")) {
            ResourceLocation $$5 = new ResourceLocation(GsonHelper.m_13906_($$1, "tag"));
            $$4 = TagKey.m_203882_(Registry.f_122899_, $$5);
        }
        StatePropertiesPredicate $$6 = StatePropertiesPredicate.m_67679_($$1.get("state"));
        return new FluidPredicate($$4, $$2, $$6);
    }

    public JsonElement m_41103_() {
        if (this == f_41094_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        if (this.f_41096_ != null) {
            $$0.addProperty("fluid", Registry.f_122822_.m_7981_(this.f_41096_).toString());
        }
        if (this.f_41095_ != null) {
            $$0.addProperty("tag", this.f_41095_.f_203868_().toString());
        }
        $$0.add("state", this.f_41097_.m_67666_());
        return $$0;
    }

    public static class Builder {
        @Nullable
        private Fluid f_151162_;
        @Nullable
        private TagKey<Fluid> f_151163_;
        private StatePropertiesPredicate f_151164_ = StatePropertiesPredicate.f_67658_;

        private Builder() {
        }

        public static Builder m_151166_() {
            return new Builder();
        }

        public Builder m_151171_(Fluid p_151172_) {
            this.f_151162_ = p_151172_;
            return this;
        }

        public Builder m_204105_(TagKey<Fluid> p_204106_) {
            this.f_151163_ = p_204106_;
            return this;
        }

        public Builder m_151169_(StatePropertiesPredicate p_151170_) {
            this.f_151164_ = p_151170_;
            return this;
        }

        public FluidPredicate m_151173_() {
            return new FluidPredicate(this.f_151163_, this.f_151162_, this.f_151164_);
        }
    }
}

