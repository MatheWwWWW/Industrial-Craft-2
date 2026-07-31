/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

public class EntityPredicate {
    public static final EntityPredicate f_36550_ = new EntityPredicate(EntityTypePredicate.f_37636_, DistancePredicate.f_26241_, LocationPredicate.f_52592_, LocationPredicate.f_52592_, MobEffectsPredicate.f_56547_, NbtPredicate.f_57471_, EntityFlagsPredicate.f_33682_, EntityEquipmentPredicate.f_32176_, EntitySubPredicate.f_218826_, null);
    private final EntityTypePredicate f_36551_;
    private final DistancePredicate f_36552_;
    private final LocationPredicate f_36553_;
    private final LocationPredicate f_150285_;
    private final MobEffectsPredicate f_36554_;
    private final NbtPredicate f_36555_;
    private final EntityFlagsPredicate f_36556_;
    private final EntityEquipmentPredicate f_36557_;
    private final EntitySubPredicate f_218773_;
    private final EntityPredicate f_36560_;
    private final EntityPredicate f_150287_;
    private final EntityPredicate f_36561_;
    @Nullable
    private final String f_36562_;

    private EntityPredicate(EntityTypePredicate p_218789_, DistancePredicate p_218790_, LocationPredicate p_218791_, LocationPredicate p_218792_, MobEffectsPredicate p_218793_, NbtPredicate p_218794_, EntityFlagsPredicate p_218795_, EntityEquipmentPredicate p_218796_, EntitySubPredicate p_218797_, @Nullable String p_218798_) {
        this.f_36551_ = p_218789_;
        this.f_36552_ = p_218790_;
        this.f_36553_ = p_218791_;
        this.f_150285_ = p_218792_;
        this.f_36554_ = p_218793_;
        this.f_36555_ = p_218794_;
        this.f_36556_ = p_218795_;
        this.f_36557_ = p_218796_;
        this.f_218773_ = p_218797_;
        this.f_150287_ = this;
        this.f_36560_ = this;
        this.f_36561_ = this;
        this.f_36562_ = p_218798_;
    }

    EntityPredicate(EntityTypePredicate p_218775_, DistancePredicate p_218776_, LocationPredicate p_218777_, LocationPredicate p_218778_, MobEffectsPredicate p_218779_, NbtPredicate p_218780_, EntityFlagsPredicate p_218781_, EntityEquipmentPredicate p_218782_, EntitySubPredicate p_218783_, EntityPredicate p_218784_, EntityPredicate p_218785_, EntityPredicate p_218786_, @Nullable String p_218787_) {
        this.f_36551_ = p_218775_;
        this.f_36552_ = p_218776_;
        this.f_36553_ = p_218777_;
        this.f_150285_ = p_218778_;
        this.f_36554_ = p_218779_;
        this.f_36555_ = p_218780_;
        this.f_36556_ = p_218781_;
        this.f_36557_ = p_218782_;
        this.f_218773_ = p_218783_;
        this.f_36560_ = p_218784_;
        this.f_150287_ = p_218785_;
        this.f_36561_ = p_218786_;
        this.f_36562_ = p_218787_;
    }

    public boolean m_36611_(ServerPlayer p_36612_, @Nullable Entity p_36613_) {
        return this.m_36607_(p_36612_.m_9236_(), p_36612_.m_20182_(), p_36613_);
    }

    public boolean m_36607_(ServerLevel p_36608_, @Nullable Vec3 p_36609_, @Nullable Entity p_36610_) {
        Team $$4;
        Vec3 $$3;
        if (this == f_36550_) {
            return true;
        }
        if (p_36610_ == null) {
            return false;
        }
        if (!this.f_36551_.m_7484_(p_36610_.m_6095_())) {
            return false;
        }
        if (p_36609_ == null ? this.f_36552_ != DistancePredicate.f_26241_ : !this.f_36552_.m_26255_(p_36609_.f_82479_, p_36609_.f_82480_, p_36609_.f_82481_, p_36610_.m_20185_(), p_36610_.m_20186_(), p_36610_.m_20189_())) {
            return false;
        }
        if (!this.f_36553_.m_52617_(p_36608_, p_36610_.m_20185_(), p_36610_.m_20186_(), p_36610_.m_20189_())) {
            return false;
        }
        if (this.f_150285_ != LocationPredicate.f_52592_ && !this.f_150285_.m_52617_(p_36608_, ($$3 = Vec3.m_82512_(p_36610_.m_216999_())).m_7096_(), $$3.m_7098_(), $$3.m_7094_())) {
            return false;
        }
        if (!this.f_36554_.m_56555_(p_36610_)) {
            return false;
        }
        if (!this.f_36555_.m_57477_(p_36610_)) {
            return false;
        }
        if (!this.f_36556_.m_33696_(p_36610_)) {
            return false;
        }
        if (!this.f_36557_.m_32193_(p_36610_)) {
            return false;
        }
        if (!this.f_218773_.m_153246_(p_36610_, p_36608_, p_36609_)) {
            return false;
        }
        if (!this.f_36560_.m_36607_(p_36608_, p_36609_, p_36610_.m_20202_())) {
            return false;
        }
        if (this.f_150287_ != f_36550_ && p_36610_.m_20197_().stream().noneMatch(p_150322_ -> this.f_150287_.m_36607_(p_36608_, p_36609_, (Entity)p_150322_))) {
            return false;
        }
        if (!this.f_36561_.m_36607_(p_36608_, p_36609_, p_36610_ instanceof Mob ? ((Mob)p_36610_).m_5448_() : null)) {
            return false;
        }
        return this.f_36562_ == null || ($$4 = p_36610_.m_5647_()) != null && this.f_36562_.equals($$4.m_5758_());
    }

    public static EntityPredicate m_36614_(@Nullable JsonElement p_36615_) {
        if (p_36615_ == null || p_36615_.isJsonNull()) {
            return f_36550_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_36615_, "entity");
        EntityTypePredicate $$2 = EntityTypePredicate.m_37643_($$1.get("type"));
        DistancePredicate $$3 = DistancePredicate.m_26264_($$1.get("distance"));
        LocationPredicate $$4 = LocationPredicate.m_52629_($$1.get("location"));
        LocationPredicate $$5 = LocationPredicate.m_52629_($$1.get("stepping_on"));
        MobEffectsPredicate $$6 = MobEffectsPredicate.m_56559_($$1.get("effects"));
        NbtPredicate $$7 = NbtPredicate.m_57481_($$1.get("nbt"));
        EntityFlagsPredicate $$8 = EntityFlagsPredicate.m_33698_($$1.get("flags"));
        EntityEquipmentPredicate $$9 = EntityEquipmentPredicate.m_32195_($$1.get("equipment"));
        EntitySubPredicate $$10 = EntitySubPredicate.m_218835_($$1.get("type_specific"));
        EntityPredicate $$11 = EntityPredicate.m_36614_($$1.get("vehicle"));
        EntityPredicate $$12 = EntityPredicate.m_36614_($$1.get("passenger"));
        EntityPredicate $$13 = EntityPredicate.m_36614_($$1.get("targeted_entity"));
        String $$14 = GsonHelper.m_13851_($$1, "team", null);
        return new Builder().m_36646_($$2).m_36638_($$3).m_36650_($$4).m_150330_($$5).m_36652_($$6).m_36654_($$7).m_36642_($$8).m_36640_($$9).m_218800_($$10).m_36658_($$14).m_36644_($$11).m_150328_($$12).m_36663_($$13).m_36662_();
    }

    public JsonElement m_36606_() {
        if (this == f_36550_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        $$0.add("type", this.f_36551_.m_5908_());
        $$0.add("distance", this.f_36552_.m_26254_());
        $$0.add("location", this.f_36553_.m_52616_());
        $$0.add("stepping_on", this.f_150285_.m_52616_());
        $$0.add("effects", this.f_36554_.m_56565_());
        $$0.add("nbt", this.f_36555_.m_57476_());
        $$0.add("flags", this.f_36556_.m_33695_());
        $$0.add("equipment", this.f_36557_.m_32192_());
        $$0.add("type_specific", this.f_218773_.m_218837_());
        $$0.add("vehicle", this.f_36560_.m_36606_());
        $$0.add("passenger", this.f_150287_.m_36606_());
        $$0.add("targeted_entity", this.f_36561_.m_36606_());
        $$0.addProperty("team", this.f_36562_);
        return $$0;
    }

    public static LootContext m_36616_(ServerPlayer p_36617_, Entity p_36618_) {
        return new LootContext.Builder(p_36617_.m_9236_()).m_78972_(LootContextParams.f_81455_, p_36618_).m_78972_(LootContextParams.f_81460_, p_36617_.m_20182_()).m_230911_(p_36617_.m_217043_()).m_78975_(LootContextParamSets.f_81419_);
    }

    public static class Builder {
        private EntityTypePredicate f_36619_ = EntityTypePredicate.f_37636_;
        private DistancePredicate f_36620_ = DistancePredicate.f_26241_;
        private LocationPredicate f_36621_ = LocationPredicate.f_52592_;
        private LocationPredicate f_150323_ = LocationPredicate.f_52592_;
        private MobEffectsPredicate f_36622_ = MobEffectsPredicate.f_56547_;
        private NbtPredicate f_36623_ = NbtPredicate.f_57471_;
        private EntityFlagsPredicate f_36624_ = EntityFlagsPredicate.f_33682_;
        private EntityEquipmentPredicate f_36625_ = EntityEquipmentPredicate.f_32176_;
        private EntitySubPredicate f_218799_ = EntitySubPredicate.f_218826_;
        private EntityPredicate f_36628_ = f_36550_;
        private EntityPredicate f_150325_ = f_36550_;
        private EntityPredicate f_36629_ = f_36550_;
        @Nullable
        private String f_36630_;

        public static Builder m_36633_() {
            return new Builder();
        }

        public Builder m_36636_(EntityType<?> p_36637_) {
            this.f_36619_ = EntityTypePredicate.m_37647_(p_36637_);
            return this;
        }

        public Builder m_204077_(TagKey<EntityType<?>> p_204078_) {
            this.f_36619_ = EntityTypePredicate.m_204081_(p_204078_);
            return this;
        }

        public Builder m_36646_(EntityTypePredicate p_36647_) {
            this.f_36619_ = p_36647_;
            return this;
        }

        public Builder m_36638_(DistancePredicate p_36639_) {
            this.f_36620_ = p_36639_;
            return this;
        }

        public Builder m_36650_(LocationPredicate p_36651_) {
            this.f_36621_ = p_36651_;
            return this;
        }

        public Builder m_150330_(LocationPredicate p_150331_) {
            this.f_150323_ = p_150331_;
            return this;
        }

        public Builder m_36652_(MobEffectsPredicate p_36653_) {
            this.f_36622_ = p_36653_;
            return this;
        }

        public Builder m_36654_(NbtPredicate p_36655_) {
            this.f_36623_ = p_36655_;
            return this;
        }

        public Builder m_36642_(EntityFlagsPredicate p_36643_) {
            this.f_36624_ = p_36643_;
            return this;
        }

        public Builder m_36640_(EntityEquipmentPredicate p_36641_) {
            this.f_36625_ = p_36641_;
            return this;
        }

        public Builder m_218800_(EntitySubPredicate p_218801_) {
            this.f_218799_ = p_218801_;
            return this;
        }

        public Builder m_36644_(EntityPredicate p_36645_) {
            this.f_36628_ = p_36645_;
            return this;
        }

        public Builder m_150328_(EntityPredicate p_150329_) {
            this.f_150325_ = p_150329_;
            return this;
        }

        public Builder m_36663_(EntityPredicate p_36664_) {
            this.f_36629_ = p_36664_;
            return this;
        }

        public Builder m_36658_(@Nullable String p_36659_) {
            this.f_36630_ = p_36659_;
            return this;
        }

        public EntityPredicate m_36662_() {
            return new EntityPredicate(this.f_36619_, this.f_36620_, this.f_36621_, this.f_150323_, this.f_36622_, this.f_36623_, this.f_36624_, this.f_36625_, this.f_218799_, this.f_36628_, this.f_150325_, this.f_36629_, this.f_36630_);
        }
    }

    public static class Composite {
        public static final Composite f_36667_ = new Composite(new LootItemCondition[0]);
        private final LootItemCondition[] f_36668_;
        private final Predicate<LootContext> f_36669_;

        private Composite(LootItemCondition[] p_36672_) {
            this.f_36668_ = p_36672_;
            this.f_36669_ = LootItemConditions.m_81834_(p_36672_);
        }

        public static Composite m_36690_(LootItemCondition ... p_36691_) {
            return new Composite(p_36691_);
        }

        public static Composite m_36677_(JsonObject p_36678_, String p_36679_, DeserializationContext p_36680_) {
            JsonElement $$3 = p_36678_.get(p_36679_);
            return Composite.m_36683_(p_36679_, p_36680_, $$3);
        }

        public static Composite[] m_36692_(JsonObject p_36693_, String p_36694_, DeserializationContext p_36695_) {
            JsonElement $$3 = p_36693_.get(p_36694_);
            if ($$3 == null || $$3.isJsonNull()) {
                return new Composite[0];
            }
            JsonArray $$4 = GsonHelper.m_13924_($$3, p_36694_);
            Composite[] $$5 = new Composite[$$4.size()];
            for (int $$6 = 0; $$6 < $$4.size(); ++$$6) {
                $$5[$$6] = Composite.m_36683_(p_36694_ + "[" + $$6 + "]", p_36695_, $$4.get($$6));
            }
            return $$5;
        }

        private static Composite m_36683_(String p_36684_, DeserializationContext p_36685_, @Nullable JsonElement p_36686_) {
            if (p_36686_ != null && p_36686_.isJsonArray()) {
                LootItemCondition[] $$3 = p_36685_.m_25874_(p_36686_.getAsJsonArray(), p_36685_.m_25873_() + "/" + p_36684_, LootContextParamSets.f_81419_);
                return new Composite($$3);
            }
            EntityPredicate $$4 = EntityPredicate.m_36614_(p_36686_);
            return Composite.m_36673_($$4);
        }

        public static Composite m_36673_(EntityPredicate p_36674_) {
            if (p_36674_ == f_36550_) {
                return f_36667_;
            }
            LootItemCondition $$1 = LootItemEntityPropertyCondition.m_81867_(LootContext.EntityTarget.THIS, p_36674_).m_6409_();
            return new Composite(new LootItemCondition[]{$$1});
        }

        public boolean m_36681_(LootContext p_36682_) {
            return this.f_36669_.test(p_36682_);
        }

        public JsonElement m_36675_(SerializationContext p_36676_) {
            if (this.f_36668_.length == 0) {
                return JsonNull.INSTANCE;
            }
            return p_36676_.m_64772_(this.f_36668_);
        }

        public static JsonElement m_36687_(Composite[] p_36688_, SerializationContext p_36689_) {
            if (p_36688_.length == 0) {
                return JsonNull.INSTANCE;
            }
            JsonArray $$2 = new JsonArray();
            for (Composite $$3 : p_36688_) {
                $$2.add($$3.m_36675_(p_36689_));
            }
            return $$2;
        }
    }
}

