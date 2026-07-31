/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class AdvancementRewards {
    public static final AdvancementRewards f_9978_ = new AdvancementRewards(0, new ResourceLocation[0], new ResourceLocation[0], CommandFunction.CacheableFunction.f_77990_);
    private final int f_9979_;
    private final ResourceLocation[] f_9980_;
    private final ResourceLocation[] f_9981_;
    private final CommandFunction.CacheableFunction f_9982_;

    public AdvancementRewards(int p_9985_, ResourceLocation[] p_9986_, ResourceLocation[] p_9987_, CommandFunction.CacheableFunction p_9988_) {
        this.f_9979_ = p_9985_;
        this.f_9980_ = p_9986_;
        this.f_9981_ = p_9987_;
        this.f_9982_ = p_9988_;
    }

    public ResourceLocation[] m_144821_() {
        return this.f_9981_;
    }

    public void m_9989_(ServerPlayer p_9990_) {
        p_9990_.m_6756_(this.f_9979_);
        LootContext $$1 = new LootContext.Builder(p_9990_.m_9236_()).m_78972_(LootContextParams.f_81455_, p_9990_).m_78972_(LootContextParams.f_81460_, p_9990_.m_20182_()).m_230911_(p_9990_.m_217043_()).m_78975_(LootContextParamSets.f_81418_);
        boolean $$2 = false;
        for (ResourceLocation $$3 : this.f_9980_) {
            for (ItemStack $$4 : p_9990_.f_8924_.m_129898_().m_79217_($$3).m_230922_($$1)) {
                if (p_9990_.m_36356_($$4)) {
                    p_9990_.f_19853_.m_6263_(null, p_9990_.m_20185_(), p_9990_.m_20186_(), p_9990_.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2f, ((p_9990_.m_217043_().m_188501_() - p_9990_.m_217043_().m_188501_()) * 0.7f + 1.0f) * 2.0f);
                    $$2 = true;
                    continue;
                }
                ItemEntity $$5 = p_9990_.m_36176_($$4, false);
                if ($$5 == null) continue;
                $$5.m_32061_();
                $$5.m_32047_(p_9990_.m_20148_());
            }
        }
        if ($$2) {
            p_9990_.f_36096_.m_38946_();
        }
        if (this.f_9981_.length > 0) {
            p_9990_.m_7902_(this.f_9981_);
        }
        MinecraftServer $$6 = p_9990_.f_8924_;
        this.f_9982_.m_78002_($$6.m_129890_()).ifPresent(p_9996_ -> $$6.m_129890_().m_136112_((CommandFunction)p_9996_, p_9990_.m_20203_().m_81324_().m_81325_(2)));
    }

    public String toString() {
        return "AdvancementRewards{experience=" + this.f_9979_ + ", loot=" + Arrays.toString(this.f_9980_) + ", recipes=" + Arrays.toString(this.f_9981_) + ", function=" + this.f_9982_ + "}";
    }

    public JsonElement m_9997_() {
        if (this == f_9978_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        if (this.f_9979_ != 0) {
            $$0.addProperty("experience", (Number)this.f_9979_);
        }
        if (this.f_9980_.length > 0) {
            JsonArray $$1 = new JsonArray();
            for (ResourceLocation $$2 : this.f_9980_) {
                $$1.add($$2.toString());
            }
            $$0.add("loot", (JsonElement)$$1);
        }
        if (this.f_9981_.length > 0) {
            JsonArray $$3 = new JsonArray();
            for (ResourceLocation $$4 : this.f_9981_) {
                $$3.add($$4.toString());
            }
            $$0.add("recipes", (JsonElement)$$3);
        }
        if (this.f_9982_.m_77999_() != null) {
            $$0.addProperty("function", this.f_9982_.m_77999_().toString());
        }
        return $$0;
    }

    public static AdvancementRewards m_9991_(JsonObject p_9992_) throws JsonParseException {
        CommandFunction.CacheableFunction $$9;
        int $$1 = GsonHelper.m_13824_(p_9992_, "experience", 0);
        JsonArray $$2 = GsonHelper.m_13832_(p_9992_, "loot", new JsonArray());
        ResourceLocation[] $$3 = new ResourceLocation[$$2.size()];
        for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
            $$3[$$4] = new ResourceLocation(GsonHelper.m_13805_($$2.get($$4), "loot[" + $$4 + "]"));
        }
        JsonArray $$5 = GsonHelper.m_13832_(p_9992_, "recipes", new JsonArray());
        ResourceLocation[] $$6 = new ResourceLocation[$$5.size()];
        for (int $$7 = 0; $$7 < $$6.length; ++$$7) {
            $$6[$$7] = new ResourceLocation(GsonHelper.m_13805_($$5.get($$7), "recipes[" + $$7 + "]"));
        }
        if (p_9992_.has("function")) {
            CommandFunction.CacheableFunction $$8 = new CommandFunction.CacheableFunction(new ResourceLocation(GsonHelper.m_13906_(p_9992_, "function")));
        } else {
            $$9 = CommandFunction.CacheableFunction.f_77990_;
        }
        return new AdvancementRewards($$1, $$3, $$6, $$9);
    }

    public static class Builder {
        private int f_9999_;
        private final List<ResourceLocation> f_10000_ = Lists.newArrayList();
        private final List<ResourceLocation> f_10001_ = Lists.newArrayList();
        @Nullable
        private ResourceLocation f_10002_;

        public static Builder m_10005_(int p_10006_) {
            return new Builder().m_10007_(p_10006_);
        }

        public Builder m_10007_(int p_10008_) {
            this.f_9999_ += p_10008_;
            return this;
        }

        public static Builder m_144822_(ResourceLocation p_144823_) {
            return new Builder().m_144824_(p_144823_);
        }

        public Builder m_144824_(ResourceLocation p_144825_) {
            this.f_10000_.add(p_144825_);
            return this;
        }

        public static Builder m_10009_(ResourceLocation p_10010_) {
            return new Builder().m_10011_(p_10010_);
        }

        public Builder m_10011_(ResourceLocation p_10012_) {
            this.f_10001_.add(p_10012_);
            return this;
        }

        public static Builder m_144826_(ResourceLocation p_144827_) {
            return new Builder().m_144828_(p_144827_);
        }

        public Builder m_144828_(ResourceLocation p_144829_) {
            this.f_10002_ = p_144829_;
            return this;
        }

        public AdvancementRewards m_10004_() {
            return new AdvancementRewards(this.f_9999_, this.f_10000_.toArray(new ResourceLocation[0]), this.f_10001_.toArray(new ResourceLocation[0]), this.f_10002_ == null ? CommandFunction.CacheableFunction.f_77990_ : new CommandFunction.CacheableFunction(this.f_10002_));
        }
    }
}

