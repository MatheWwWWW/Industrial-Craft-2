/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.sensing;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.AdultSensor;
import net.minecraft.world.entity.ai.sensing.AxolotlAttackablesSensor;
import net.minecraft.world.entity.ai.sensing.DummySensor;
import net.minecraft.world.entity.ai.sensing.FrogAttackablesSensor;
import net.minecraft.world.entity.ai.sensing.GolemSensor;
import net.minecraft.world.entity.ai.sensing.HoglinSpecificSensor;
import net.minecraft.world.entity.ai.sensing.HurtBySensor;
import net.minecraft.world.entity.ai.sensing.IsInWaterSensor;
import net.minecraft.world.entity.ai.sensing.NearestBedSensor;
import net.minecraft.world.entity.ai.sensing.NearestItemSensor;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.PiglinBruteSpecificSensor;
import net.minecraft.world.entity.ai.sensing.PiglinSpecificSensor;
import net.minecraft.world.entity.ai.sensing.PlayerSensor;
import net.minecraft.world.entity.ai.sensing.SecondaryPoiSensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraft.world.entity.ai.sensing.VillagerBabiesSensor;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import net.minecraft.world.entity.ai.sensing.WardenEntitySensor;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.animal.frog.FrogAi;
import net.minecraft.world.entity.animal.goat.GoatAi;

public class SensorType<U extends Sensor<?>> {
    public static final SensorType<DummySensor> f_26809_ = SensorType.m_26828_("dummy", DummySensor::new);
    public static final SensorType<NearestItemSensor> f_26810_ = SensorType.m_26828_("nearest_items", NearestItemSensor::new);
    public static final SensorType<NearestLivingEntitySensor<LivingEntity>> f_26811_ = SensorType.m_26828_("nearest_living_entities", NearestLivingEntitySensor::new);
    public static final SensorType<PlayerSensor> f_26812_ = SensorType.m_26828_("nearest_players", PlayerSensor::new);
    public static final SensorType<NearestBedSensor> f_26813_ = SensorType.m_26828_("nearest_bed", NearestBedSensor::new);
    public static final SensorType<HurtBySensor> f_26814_ = SensorType.m_26828_("hurt_by", HurtBySensor::new);
    public static final SensorType<VillagerHostilesSensor> f_26815_ = SensorType.m_26828_("villager_hostiles", VillagerHostilesSensor::new);
    public static final SensorType<VillagerBabiesSensor> f_26816_ = SensorType.m_26828_("villager_babies", VillagerBabiesSensor::new);
    public static final SensorType<SecondaryPoiSensor> f_26817_ = SensorType.m_26828_("secondary_pois", SecondaryPoiSensor::new);
    public static final SensorType<GolemSensor> f_26818_ = SensorType.m_26828_("golem_detected", GolemSensor::new);
    public static final SensorType<PiglinSpecificSensor> f_26819_ = SensorType.m_26828_("piglin_specific_sensor", PiglinSpecificSensor::new);
    public static final SensorType<PiglinBruteSpecificSensor> f_26820_ = SensorType.m_26828_("piglin_brute_specific_sensor", PiglinBruteSpecificSensor::new);
    public static final SensorType<HoglinSpecificSensor> f_26821_ = SensorType.m_26828_("hoglin_specific_sensor", HoglinSpecificSensor::new);
    public static final SensorType<AdultSensor> f_26822_ = SensorType.m_26828_("nearest_adult", AdultSensor::new);
    public static final SensorType<AxolotlAttackablesSensor> f_148315_ = SensorType.m_26828_("axolotl_attackables", AxolotlAttackablesSensor::new);
    public static final SensorType<TemptingSensor> f_148316_ = SensorType.m_26828_("axolotl_temptations", () -> new TemptingSensor(AxolotlAi.m_149287_()));
    public static final SensorType<TemptingSensor> f_148317_ = SensorType.m_26828_("goat_temptations", () -> new TemptingSensor(GoatAi.m_149444_()));
    public static final SensorType<TemptingSensor> f_217822_ = SensorType.m_26828_("frog_temptations", () -> new TemptingSensor(FrogAi.m_218572_()));
    public static final SensorType<FrogAttackablesSensor> f_217823_ = SensorType.m_26828_("frog_attackables", FrogAttackablesSensor::new);
    public static final SensorType<IsInWaterSensor> f_217824_ = SensorType.m_26828_("is_in_water", IsInWaterSensor::new);
    public static final SensorType<WardenEntitySensor> f_217825_ = SensorType.m_26828_("warden_entity_sensor", WardenEntitySensor::new);
    private final Supplier<U> f_26823_;

    private SensorType(Supplier<U> p_26826_) {
        this.f_26823_ = p_26826_;
    }

    public U m_26827_() {
        return (U)((Sensor)this.f_26823_.get());
    }

    private static <U extends Sensor<?>> SensorType<U> m_26828_(String p_26829_, Supplier<U> p_26830_) {
        return Registry.m_122965_(Registry.f_122872_, new ResourceLocation(p_26829_), new SensorType<U>(p_26830_));
    }
}

