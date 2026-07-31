/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.slf4j.Logger;

public class LocationPredicate {
    private static final Logger f_52593_ = LogUtils.getLogger();
    public static final LocationPredicate f_52592_ = new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, null, null, null, null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
    private final MinMaxBounds.Doubles f_52594_;
    private final MinMaxBounds.Doubles f_52595_;
    private final MinMaxBounds.Doubles f_52596_;
    @Nullable
    private final ResourceKey<Biome> f_52597_;
    @Nullable
    private final ResourceKey<Structure> f_220588_;
    @Nullable
    private final ResourceKey<Level> f_52599_;
    @Nullable
    private final Boolean f_52600_;
    private final LightPredicate f_52601_;
    private final BlockPredicate f_52602_;
    private final FluidPredicate f_52603_;

    public LocationPredicate(MinMaxBounds.Doubles p_207916_, MinMaxBounds.Doubles p_207917_, MinMaxBounds.Doubles p_207918_, @Nullable ResourceKey<Biome> p_207919_, @Nullable ResourceKey<Structure> p_207920_, @Nullable ResourceKey<Level> p_207921_, @Nullable Boolean p_207922_, LightPredicate p_207923_, BlockPredicate p_207924_, FluidPredicate p_207925_) {
        this.f_52594_ = p_207916_;
        this.f_52595_ = p_207917_;
        this.f_52596_ = p_207918_;
        this.f_52597_ = p_207919_;
        this.f_220588_ = p_207920_;
        this.f_52599_ = p_207921_;
        this.f_52600_ = p_207922_;
        this.f_52601_ = p_207923_;
        this.f_52602_ = p_207924_;
        this.f_52603_ = p_207925_;
    }

    public static LocationPredicate m_52634_(ResourceKey<Biome> p_52635_) {
        return new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, p_52635_, null, null, null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
    }

    public static LocationPredicate m_52638_(ResourceKey<Level> p_52639_) {
        return new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, null, null, p_52639_, null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
    }

    public static LocationPredicate m_220589_(ResourceKey<Structure> p_220590_) {
        return new LocationPredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, null, p_220590_, null, null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
    }

    public static LocationPredicate m_187442_(MinMaxBounds.Doubles p_187443_) {
        return new LocationPredicate(MinMaxBounds.Doubles.f_154779_, p_187443_, MinMaxBounds.Doubles.f_154779_, null, null, null, null, LightPredicate.f_51335_, BlockPredicate.f_17902_, FluidPredicate.f_41094_);
    }

    public boolean m_52617_(ServerLevel p_52618_, double p_52619_, double p_52620_, double p_52621_) {
        if (!this.f_52594_.m_154810_(p_52619_)) {
            return false;
        }
        if (!this.f_52595_.m_154810_(p_52620_)) {
            return false;
        }
        if (!this.f_52596_.m_154810_(p_52621_)) {
            return false;
        }
        if (this.f_52599_ != null && this.f_52599_ != p_52618_.m_46472_()) {
            return false;
        }
        BlockPos $$4 = new BlockPos(p_52619_, p_52620_, p_52621_);
        boolean $$5 = p_52618_.m_46749_($$4);
        if (!(this.f_52597_ == null || $$5 && p_52618_.m_204166_($$4).m_203565_(this.f_52597_))) {
            return false;
        }
        if (!(this.f_220588_ == null || $$5 && p_52618_.m_215010_().m_220488_($$4, this.f_220588_).m_73603_())) {
            return false;
        }
        if (!(this.f_52600_ == null || $$5 && this.f_52600_ == CampfireBlock.m_51248_(p_52618_, $$4))) {
            return false;
        }
        if (!this.f_52601_.m_51341_(p_52618_, $$4)) {
            return false;
        }
        if (!this.f_52602_.m_17914_(p_52618_, $$4)) {
            return false;
        }
        return this.f_52603_.m_41104_(p_52618_, $$4);
    }

    public JsonElement m_52616_() {
        if (this == f_52592_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        if (!(this.f_52594_.m_55327_() && this.f_52595_.m_55327_() && this.f_52596_.m_55327_())) {
            JsonObject $$1 = new JsonObject();
            $$1.add("x", this.f_52594_.m_55328_());
            $$1.add("y", this.f_52595_.m_55328_());
            $$1.add("z", this.f_52596_.m_55328_());
            $$0.add("position", (JsonElement)$$1);
        }
        if (this.f_52599_ != null) {
            Level.f_46427_.encodeStart((DynamicOps)JsonOps.INSTANCE, this.f_52599_).resultOrPartial(arg_0 -> ((Logger)f_52593_).error(arg_0)).ifPresent(p_52633_ -> $$0.add("dimension", p_52633_));
        }
        if (this.f_220588_ != null) {
            $$0.addProperty("structure", this.f_220588_.m_135782_().toString());
        }
        if (this.f_52597_ != null) {
            $$0.addProperty("biome", this.f_52597_.m_135782_().toString());
        }
        if (this.f_52600_ != null) {
            $$0.addProperty("smokey", this.f_52600_);
        }
        $$0.add("light", this.f_52601_.m_51340_());
        $$0.add("block", this.f_52602_.m_17913_());
        $$0.add("fluid", this.f_52603_.m_41103_());
        return $$0;
    }

    public static LocationPredicate m_52629_(@Nullable JsonElement p_52630_) {
        ResourceKey $$6;
        if (p_52630_ == null || p_52630_.isJsonNull()) {
            return f_52592_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_52630_, "location");
        JsonObject $$2 = GsonHelper.m_13841_($$1, "position", new JsonObject());
        MinMaxBounds.Doubles $$3 = MinMaxBounds.Doubles.m_154791_($$2.get("x"));
        MinMaxBounds.Doubles $$4 = MinMaxBounds.Doubles.m_154791_($$2.get("y"));
        MinMaxBounds.Doubles $$5 = MinMaxBounds.Doubles.m_154791_($$2.get("z"));
        ResourceKey resourceKey = $$1.has("dimension") ? (ResourceKey)ResourceLocation.f_135803_.parse((DynamicOps)JsonOps.INSTANCE, (Object)$$1.get("dimension")).resultOrPartial(arg_0 -> ((Logger)f_52593_).error(arg_0)).map(p_52637_ -> ResourceKey.m_135785_(Registry.f_122819_, p_52637_)).orElse(null) : ($$6 = null);
        ResourceKey $$7 = $$1.has("structure") ? (ResourceKey)ResourceLocation.f_135803_.parse((DynamicOps)JsonOps.INSTANCE, (Object)$$1.get("structure")).resultOrPartial(arg_0 -> ((Logger)f_52593_).error(arg_0)).map(p_207927_ -> ResourceKey.m_135785_(Registry.f_235725_, p_207927_)).orElse(null) : null;
        ResourceKey<Biome> $$8 = null;
        if ($$1.has("biome")) {
            ResourceLocation $$9 = new ResourceLocation(GsonHelper.m_13906_($$1, "biome"));
            $$8 = ResourceKey.m_135785_(Registry.f_122885_, $$9);
        }
        Boolean $$10 = $$1.has("smokey") ? Boolean.valueOf($$1.get("smokey").getAsBoolean()) : null;
        LightPredicate $$11 = LightPredicate.m_51344_($$1.get("light"));
        BlockPredicate $$12 = BlockPredicate.m_17917_($$1.get("block"));
        FluidPredicate $$13 = FluidPredicate.m_41107_($$1.get("fluid"));
        return new LocationPredicate($$3, $$4, $$5, $$8, $$7, $$6, $$10, $$11, $$12, $$13);
    }

    public static class Builder {
        private MinMaxBounds.Doubles f_52640_ = MinMaxBounds.Doubles.f_154779_;
        private MinMaxBounds.Doubles f_52641_ = MinMaxBounds.Doubles.f_154779_;
        private MinMaxBounds.Doubles f_52642_ = MinMaxBounds.Doubles.f_154779_;
        @Nullable
        private ResourceKey<Biome> f_52643_;
        @Nullable
        private ResourceKey<Structure> f_220591_;
        @Nullable
        private ResourceKey<Level> f_52645_;
        @Nullable
        private Boolean f_52646_;
        private LightPredicate f_52647_ = LightPredicate.f_51335_;
        private BlockPredicate f_52648_ = BlockPredicate.f_17902_;
        private FluidPredicate f_52649_ = FluidPredicate.f_41094_;

        public static Builder m_52651_() {
            return new Builder();
        }

        public Builder m_153970_(MinMaxBounds.Doubles p_153971_) {
            this.f_52640_ = p_153971_;
            return this;
        }

        public Builder m_153974_(MinMaxBounds.Doubles p_153975_) {
            this.f_52641_ = p_153975_;
            return this;
        }

        public Builder m_153978_(MinMaxBounds.Doubles p_153979_) {
            this.f_52642_ = p_153979_;
            return this;
        }

        public Builder m_52656_(@Nullable ResourceKey<Biome> p_52657_) {
            this.f_52643_ = p_52657_;
            return this;
        }

        public Builder m_220592_(@Nullable ResourceKey<Structure> p_220593_) {
            this.f_220591_ = p_220593_;
            return this;
        }

        public Builder m_153976_(@Nullable ResourceKey<Level> p_153977_) {
            this.f_52645_ = p_153977_;
            return this;
        }

        public Builder m_153968_(LightPredicate p_153969_) {
            this.f_52647_ = p_153969_;
            return this;
        }

        public Builder m_52652_(BlockPredicate p_52653_) {
            this.f_52648_ = p_52653_;
            return this;
        }

        public Builder m_153966_(FluidPredicate p_153967_) {
            this.f_52649_ = p_153967_;
            return this;
        }

        public Builder m_52654_(Boolean p_52655_) {
            this.f_52646_ = p_52655_;
            return this;
        }

        public LocationPredicate m_52658_() {
            return new LocationPredicate(this.f_52640_, this.f_52641_, this.f_52642_, this.f_52643_, this.f_220591_, this.f_52645_, this.f_52646_, this.f_52647_, this.f_52648_, this.f_52649_);
        }
    }
}

