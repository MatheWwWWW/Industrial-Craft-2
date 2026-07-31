/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPredicate {
    public static final BlockPredicate f_17902_ = new BlockPredicate(null, null, StatePropertiesPredicate.f_67658_, NbtPredicate.f_57471_);
    @Nullable
    private final TagKey<Block> f_17903_;
    @Nullable
    private final Set<Block> f_146710_;
    private final StatePropertiesPredicate f_17905_;
    private final NbtPredicate f_17906_;

    public BlockPredicate(@Nullable TagKey<Block> p_204023_, @Nullable Set<Block> p_204024_, StatePropertiesPredicate p_204025_, NbtPredicate p_204026_) {
        this.f_17903_ = p_204023_;
        this.f_146710_ = p_204024_;
        this.f_17905_ = p_204025_;
        this.f_17906_ = p_204026_;
    }

    public boolean m_17914_(ServerLevel p_17915_, BlockPos p_17916_) {
        BlockEntity $$3;
        if (this == f_17902_) {
            return true;
        }
        if (!p_17915_.m_46749_(p_17916_)) {
            return false;
        }
        BlockState $$2 = p_17915_.m_8055_(p_17916_);
        if (this.f_17903_ != null && !$$2.m_204336_(this.f_17903_)) {
            return false;
        }
        if (this.f_146710_ != null && !this.f_146710_.contains($$2.m_60734_())) {
            return false;
        }
        if (!this.f_17905_.m_67667_($$2)) {
            return false;
        }
        return this.f_17906_ == NbtPredicate.f_57471_ || ($$3 = p_17915_.m_7702_(p_17916_)) != null && this.f_17906_.m_57483_($$3.m_187480_());
    }

    public static BlockPredicate m_17917_(@Nullable JsonElement p_17918_) {
        if (p_17918_ == null || p_17918_.isJsonNull()) {
            return f_17902_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_17918_, "block");
        NbtPredicate $$2 = NbtPredicate.m_57481_($$1.get("nbt"));
        ImmutableSet $$3 = null;
        JsonArray $$4 = GsonHelper.m_13832_($$1, "blocks", null);
        if ($$4 != null) {
            ImmutableSet.Builder $$5 = ImmutableSet.builder();
            for (JsonElement $$6 : $$4) {
                ResourceLocation $$7 = new ResourceLocation(GsonHelper.m_13805_($$6, "block"));
                $$5.add((Object)Registry.f_122824_.m_6612_($$7).orElseThrow(() -> new JsonSyntaxException("Unknown block id '" + $$7 + "'")));
            }
            $$3 = $$5.build();
        }
        TagKey<Block> $$8 = null;
        if ($$1.has("tag")) {
            ResourceLocation $$9 = new ResourceLocation(GsonHelper.m_13906_($$1, "tag"));
            $$8 = TagKey.m_203882_(Registry.f_122901_, $$9);
        }
        StatePropertiesPredicate $$10 = StatePropertiesPredicate.m_67679_($$1.get("state"));
        return new BlockPredicate($$8, (Set<Block>)$$3, $$10, $$2);
    }

    public JsonElement m_17913_() {
        if (this == f_17902_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        if (this.f_146710_ != null) {
            JsonArray $$1 = new JsonArray();
            for (Block $$2 : this.f_146710_) {
                $$1.add(Registry.f_122824_.m_7981_($$2).toString());
            }
            $$0.add("blocks", (JsonElement)$$1);
        }
        if (this.f_17903_ != null) {
            $$0.addProperty("tag", this.f_17903_.f_203868_().toString());
        }
        $$0.add("nbt", this.f_17906_.m_57476_());
        $$0.add("state", this.f_17905_.m_67666_());
        return $$0;
    }

    public static class Builder {
        @Nullable
        private Set<Block> f_17920_;
        @Nullable
        private TagKey<Block> f_146721_;
        private StatePropertiesPredicate f_17921_ = StatePropertiesPredicate.f_67658_;
        private NbtPredicate f_17922_ = NbtPredicate.f_57471_;

        private Builder() {
        }

        public static Builder m_17924_() {
            return new Builder();
        }

        public Builder m_146726_(Block ... p_146727_) {
            this.f_17920_ = ImmutableSet.copyOf((Object[])p_146727_);
            return this;
        }

        public Builder m_146722_(Iterable<Block> p_146723_) {
            this.f_17920_ = ImmutableSet.copyOf(p_146723_);
            return this;
        }

        public Builder m_204027_(TagKey<Block> p_204028_) {
            this.f_146721_ = p_204028_;
            return this;
        }

        public Builder m_146724_(CompoundTag p_146725_) {
            this.f_17922_ = new NbtPredicate(p_146725_);
            return this;
        }

        public Builder m_17929_(StatePropertiesPredicate p_17930_) {
            this.f_17921_ = p_17930_;
            return this;
        }

        public BlockPredicate m_17931_() {
            return new BlockPredicate(this.f_146721_, this.f_17920_, this.f_17921_, this.f_17922_);
        }
    }
}

