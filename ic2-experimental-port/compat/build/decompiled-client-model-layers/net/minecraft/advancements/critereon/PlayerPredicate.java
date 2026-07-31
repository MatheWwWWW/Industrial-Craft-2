/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PlayerPredicate
implements EntitySubPredicate {
    public static final int f_156743_ = 100;
    private final MinMaxBounds.Ints f_62245_;
    @Nullable
    private final GameType f_62246_;
    private final Map<Stat<?>, MinMaxBounds.Ints> f_62247_;
    private final Object2BooleanMap<ResourceLocation> f_62248_;
    private final Map<ResourceLocation, AdvancementPredicate> f_62249_;
    private final EntityPredicate f_156744_;

    private static AdvancementPredicate m_62289_(JsonElement p_62290_) {
        if (p_62290_.isJsonPrimitive()) {
            boolean $$1 = p_62290_.getAsBoolean();
            return new AdvancementDonePredicate($$1);
        }
        Object2BooleanOpenHashMap $$2 = new Object2BooleanOpenHashMap();
        JsonObject $$3 = GsonHelper.m_13918_(p_62290_, "criterion data");
        $$3.entrySet().forEach(arg_0 -> PlayerPredicate.m_62286_((Object2BooleanMap)$$2, arg_0));
        return new AdvancementCriterionsPredicate((Object2BooleanMap<String>)$$2);
    }

    PlayerPredicate(MinMaxBounds.Ints p_156746_, @Nullable GameType p_156747_, Map<Stat<?>, MinMaxBounds.Ints> p_156748_, Object2BooleanMap<ResourceLocation> p_156749_, Map<ResourceLocation, AdvancementPredicate> p_156750_, EntityPredicate p_156751_) {
        this.f_62245_ = p_156746_;
        this.f_62246_ = p_156747_;
        this.f_62247_ = p_156748_;
        this.f_62248_ = p_156749_;
        this.f_62249_ = p_156750_;
        this.f_156744_ = p_156751_;
    }

    @Override
    public boolean m_153246_(Entity p_222484_, ServerLevel p_222485_, @Nullable Vec3 p_222486_) {
        if (!(p_222484_ instanceof ServerPlayer)) {
            return false;
        }
        ServerPlayer $$3 = (ServerPlayer)p_222484_;
        if (!this.f_62245_.m_55390_($$3.f_36078_)) {
            return false;
        }
        if (this.f_62246_ != null && this.f_62246_ != $$3.f_8941_.m_9290_()) {
            return false;
        }
        ServerStatsCounter $$4 = $$3.m_8951_();
        for (Map.Entry<Stat<?>, MinMaxBounds.Ints> entry : this.f_62247_.entrySet()) {
            int $$6 = $$4.m_13015_(entry.getKey());
            if (entry.getValue().m_55390_($$6)) continue;
            return false;
        }
        ServerRecipeBook $$7 = $$3.m_8952_();
        for (Object2BooleanMap.Entry $$8 : this.f_62248_.object2BooleanEntrySet()) {
            if ($$7.m_12711_((ResourceLocation)$$8.getKey()) == $$8.getBooleanValue()) continue;
            return false;
        }
        if (!this.f_62249_.isEmpty()) {
            PlayerAdvancements playerAdvancements = $$3.m_8960_();
            ServerAdvancementManager $$10 = $$3.m_20194_().m_129889_();
            for (Map.Entry<ResourceLocation, AdvancementPredicate> $$11 : this.f_62249_.entrySet()) {
                Advancement $$12 = $$10.m_136041_($$11.getKey());
                if ($$12 != null && $$11.getValue().test(playerAdvancements.m_135996_($$12))) continue;
                return false;
            }
        }
        if (this.f_156744_ != EntityPredicate.f_36550_) {
            Vec3 vec3 = $$3.m_146892_();
            Vec3 $$14 = $$3.m_20252_(1.0f);
            Vec3 $$15 = vec3.m_82520_($$14.f_82479_ * 100.0, $$14.f_82480_ * 100.0, $$14.f_82481_ * 100.0);
            EntityHitResult $$16 = ProjectileUtil.m_150175_($$3.f_19853_, $$3, vec3, $$15, new AABB(vec3, $$15).m_82400_(1.0), p_156765_ -> !p_156765_.m_5833_(), 0.0f);
            if ($$16 == null || $$16.m_6662_() != HitResult.Type.ENTITY) {
                return false;
            }
            Entity $$17 = $$16.m_82443_();
            if (!this.f_156744_.m_36611_($$3, $$17) || !$$3.m_142582_($$17)) {
                return false;
            }
        }
        return true;
    }

    public static PlayerPredicate m_222491_(JsonObject p_222492_) {
        MinMaxBounds.Ints $$1 = MinMaxBounds.Ints.m_55373_(p_222492_.get("level"));
        String $$2 = GsonHelper.m_13851_(p_222492_, "gamemode", "");
        GameType $$3 = GameType.m_46402_($$2, null);
        HashMap $$4 = Maps.newHashMap();
        JsonArray $$5 = GsonHelper.m_13832_(p_222492_, "stats", null);
        if ($$5 != null) {
            for (JsonElement $$6 : $$5) {
                JsonObject $$7 = GsonHelper.m_13918_($$6, "stats entry");
                ResourceLocation $$8 = new ResourceLocation(GsonHelper.m_13906_($$7, "type"));
                StatType<?> $$9 = Registry.f_122867_.m_7745_($$8);
                if ($$9 == null) {
                    throw new JsonParseException("Invalid stat type: " + $$8);
                }
                ResourceLocation $$10 = new ResourceLocation(GsonHelper.m_13906_($$7, "stat"));
                Stat<?> $$11 = PlayerPredicate.m_62267_($$9, $$10);
                MinMaxBounds.Ints $$12 = MinMaxBounds.Ints.m_55373_($$7.get("value"));
                $$4.put($$11, $$12);
            }
        }
        Object2BooleanOpenHashMap $$13 = new Object2BooleanOpenHashMap();
        JsonObject $$14 = GsonHelper.m_13841_(p_222492_, "recipes", new JsonObject());
        for (Map.Entry $$15 : $$14.entrySet()) {
            ResourceLocation $$16 = new ResourceLocation((String)$$15.getKey());
            boolean $$17 = GsonHelper.m_13877_((JsonElement)$$15.getValue(), "recipe present");
            $$13.put((Object)$$16, $$17);
        }
        HashMap $$18 = Maps.newHashMap();
        JsonObject $$19 = GsonHelper.m_13841_(p_222492_, "advancements", new JsonObject());
        for (Map.Entry $$20 : $$19.entrySet()) {
            ResourceLocation $$21 = new ResourceLocation((String)$$20.getKey());
            AdvancementPredicate $$22 = PlayerPredicate.m_62289_((JsonElement)$$20.getValue());
            $$18.put($$21, $$22);
        }
        EntityPredicate $$23 = EntityPredicate.m_36614_(p_222492_.get("looking_at"));
        return new PlayerPredicate($$1, $$3, $$4, (Object2BooleanMap<ResourceLocation>)$$13, $$18, $$23);
    }

    private static <T> Stat<T> m_62267_(StatType<T> p_62268_, ResourceLocation p_62269_) {
        Registry<T> $$2 = p_62268_.m_12893_();
        T $$3 = $$2.m_7745_(p_62269_);
        if ($$3 == null) {
            throw new JsonParseException("Unknown object " + p_62269_ + " for stat type " + Registry.f_122867_.m_7981_(p_62268_));
        }
        return p_62268_.m_12902_($$3);
    }

    private static <T> ResourceLocation m_62265_(Stat<T> p_62266_) {
        return p_62266_.m_12859_().m_12893_().m_7981_(p_62266_.m_12867_());
    }

    @Override
    public JsonObject m_213616_() {
        JsonObject $$0 = new JsonObject();
        $$0.add("level", this.f_62245_.m_55328_());
        if (this.f_62246_ != null) {
            $$0.addProperty("gamemode", this.f_62246_.m_46405_());
        }
        if (!this.f_62247_.isEmpty()) {
            JsonArray $$1 = new JsonArray();
            this.f_62247_.forEach((p_222489_, p_222490_) -> {
                JsonObject $$3 = new JsonObject();
                $$3.addProperty("type", Registry.f_122867_.m_7981_(p_222489_.m_12859_()).toString());
                $$3.addProperty("stat", PlayerPredicate.m_62265_(p_222489_).toString());
                $$3.add("value", p_222490_.m_55328_());
                $$1.add((JsonElement)$$3);
            });
            $$0.add("stats", (JsonElement)$$1);
        }
        if (!this.f_62248_.isEmpty()) {
            JsonObject $$2 = new JsonObject();
            this.f_62248_.forEach((p_222499_, p_222500_) -> $$2.addProperty(p_222499_.toString(), p_222500_));
            $$0.add("recipes", (JsonElement)$$2);
        }
        if (!this.f_62249_.isEmpty()) {
            JsonObject $$3 = new JsonObject();
            this.f_62249_.forEach((p_222495_, p_222496_) -> $$3.add(p_222495_.toString(), p_222496_.m_7943_()));
            $$0.add("advancements", (JsonElement)$$3);
        }
        $$0.add("looking_at", this.f_156744_.m_36606_());
        return $$0;
    }

    @Override
    public EntitySubPredicate.Type m_213836_() {
        return EntitySubPredicate.Types.f_218850_;
    }

    private static /* synthetic */ void m_62286_(Object2BooleanMap p_62287_, Map.Entry p_62288_) {
        boolean $$2 = GsonHelper.m_13877_((JsonElement)p_62288_.getValue(), "criterion test");
        p_62287_.put((Object)((String)p_62288_.getKey()), $$2);
    }

    static class AdvancementDonePredicate
    implements AdvancementPredicate {
        private final boolean f_62299_;

        public AdvancementDonePredicate(boolean p_62301_) {
            this.f_62299_ = p_62301_;
        }

        @Override
        public JsonElement m_7943_() {
            return new JsonPrimitive(Boolean.valueOf(this.f_62299_));
        }

        @Override
        public boolean test(AdvancementProgress p_62304_) {
            return p_62304_.m_8193_() == this.f_62299_;
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((AdvancementProgress)object);
        }
    }

    static class AdvancementCriterionsPredicate
    implements AdvancementPredicate {
        private final Object2BooleanMap<String> f_62291_;

        public AdvancementCriterionsPredicate(Object2BooleanMap<String> p_62293_) {
            this.f_62291_ = p_62293_;
        }

        @Override
        public JsonElement m_7943_() {
            JsonObject $$0 = new JsonObject();
            this.f_62291_.forEach((arg_0, arg_1) -> ((JsonObject)$$0).addProperty(arg_0, arg_1));
            return $$0;
        }

        @Override
        public boolean test(AdvancementProgress p_62296_) {
            for (Object2BooleanMap.Entry $$1 : this.f_62291_.object2BooleanEntrySet()) {
                CriterionProgress $$2 = p_62296_.m_8214_((String)$$1.getKey());
                if ($$2 != null && $$2.m_12911_() == $$1.getBooleanValue()) continue;
                return false;
            }
            return true;
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((AdvancementProgress)object);
        }
    }

    static interface AdvancementPredicate
    extends Predicate<AdvancementProgress> {
        public JsonElement m_7943_();
    }

    public static class Builder {
        private MinMaxBounds.Ints f_62307_ = MinMaxBounds.Ints.f_55364_;
        @Nullable
        private GameType f_62308_;
        private final Map<Stat<?>, MinMaxBounds.Ints> f_62309_ = Maps.newHashMap();
        private final Object2BooleanMap<ResourceLocation> f_62310_ = new Object2BooleanOpenHashMap();
        private final Map<ResourceLocation, AdvancementPredicate> f_62311_ = Maps.newHashMap();
        private EntityPredicate f_156766_ = EntityPredicate.f_36550_;

        public static Builder m_156767_() {
            return new Builder();
        }

        public Builder m_156775_(MinMaxBounds.Ints p_156776_) {
            this.f_62307_ = p_156776_;
            return this;
        }

        public Builder m_156768_(Stat<?> p_156769_, MinMaxBounds.Ints p_156770_) {
            this.f_62309_.put(p_156769_, p_156770_);
            return this;
        }

        public Builder m_156780_(ResourceLocation p_156781_, boolean p_156782_) {
            this.f_62310_.put((Object)p_156781_, p_156782_);
            return this;
        }

        public Builder m_156773_(GameType p_156774_) {
            this.f_62308_ = p_156774_;
            return this;
        }

        public Builder m_156771_(EntityPredicate p_156772_) {
            this.f_156766_ = p_156772_;
            return this;
        }

        public Builder m_156783_(ResourceLocation p_156784_, boolean p_156785_) {
            this.f_62311_.put(p_156784_, new AdvancementDonePredicate(p_156785_));
            return this;
        }

        public Builder m_156777_(ResourceLocation p_156778_, Map<String, Boolean> p_156779_) {
            this.f_62311_.put(p_156778_, new AdvancementCriterionsPredicate((Object2BooleanMap<String>)new Object2BooleanOpenHashMap(p_156779_)));
            return this;
        }

        public PlayerPredicate m_62313_() {
            return new PlayerPredicate(this.f_62307_, this.f_62308_, this.f_62309_, this.f_62310_, this.f_62311_, this.f_156766_);
        }
    }
}

