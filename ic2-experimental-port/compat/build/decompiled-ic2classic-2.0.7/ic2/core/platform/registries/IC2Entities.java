/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.entity.vehicle.AbstractMinecart
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.platform.registries;

import ic2.core.entity.explosion.DynamiteEntity;
import ic2.core.entity.explosion.ITNTEntity;
import ic2.core.entity.explosion.NukeEntity;
import ic2.core.entity.minecarts.BatboxMinecart;
import ic2.core.entity.minecarts.ESUMinecart;
import ic2.core.entity.minecarts.ISUMinecart;
import ic2.core.entity.minecarts.MFEMinecart;
import ic2.core.entity.minecarts.MFSUMinecart;
import ic2.core.entity.minecarts.PESUMinecart;
import ic2.core.entity.misc.CarbonBoat;
import ic2.core.entity.misc.MiningLaserEntity;
import ic2.core.entity.misc.RubberBoat;
import ic2.core.entity.misc.RubberwoodBoat;
import ic2.core.entity.misc.TrackingMiningLaserEntity;
import ic2.core.entity.rockets.MiningRocketEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

public class IC2Entities {
    public static EntityType<ITNTEntity> ITNT;
    public static EntityType<NukeEntity> NUKE;
    public static EntityType<RubberwoodBoat> RUBBERWOOD_BOAT;
    public static EntityType<RubberBoat> RUBBER_BOAT;
    public static EntityType<CarbonBoat> CARBON_BOAT;
    public static EntityType<DynamiteEntity> DYNAMITE;
    public static EntityType<MiningLaserEntity> MINING_LASER;
    public static EntityType<TrackingMiningLaserEntity> TRACKING_MINING_LASER;
    public static EntityType<MiningRocketEntity> ROCKET_MINER;
    public static EntityType<AbstractMinecart> BATBOX;
    public static EntityType<AbstractMinecart> MFE;
    public static EntityType<AbstractMinecart> MFSU;
    public static EntityType<AbstractMinecart> ESU;
    public static EntityType<AbstractMinecart> ISU;
    public static EntityType<AbstractMinecart> PESU;

    public static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        ResourceLocation id = GameData.checkPrefix((String)name, (boolean)false);
        ForgeRegistries.ENTITY_TYPES.register(id, type);
        return type;
    }

    public static void init() {
        EntityType.Builder itnt = EntityType.Builder.m_20704_(ITNTEntity::new, (MobCategory)MobCategory.MISC);
        ITNT = IC2Entities.register("itnt", itnt.setTrackingRange(160).setUpdateInterval(5).m_20699_(0.98f, 0.98f).m_20712_("tnt"));
        EntityType.Builder nuke = EntityType.Builder.m_20704_(NukeEntity::new, (MobCategory)MobCategory.MISC);
        NUKE = IC2Entities.register("nuke", nuke.setTrackingRange(160).setUpdateInterval(5).m_20699_(0.98f, 0.98f).m_20712_("tnt"));
        EntityType.Builder rubberwood_boat = EntityType.Builder.m_20704_(RubberwoodBoat::new, (MobCategory)MobCategory.MISC);
        RUBBERWOOD_BOAT = IC2Entities.register("rubberwood_boat", rubberwood_boat.setTrackingRange(80).setUpdateInterval(1).m_20699_(1.375f, 0.5625f).m_20712_("boat"));
        EntityType.Builder rubber_boat = EntityType.Builder.m_20704_(RubberBoat::new, (MobCategory)MobCategory.MISC);
        RUBBER_BOAT = IC2Entities.register("rubber_boat", rubber_boat.setTrackingRange(80).setUpdateInterval(1).m_20699_(1.375f, 0.5625f).m_20712_("boat"));
        EntityType.Builder carbon_boat = EntityType.Builder.m_20704_(CarbonBoat::new, (MobCategory)MobCategory.MISC);
        CARBON_BOAT = IC2Entities.register("carbon_boat", carbon_boat.setTrackingRange(80).setUpdateInterval(1).m_20699_(1.375f, 0.5625f).m_20712_("boat"));
        EntityType.Builder dynamite = EntityType.Builder.m_20704_(DynamiteEntity::new, (MobCategory)MobCategory.MISC);
        DYNAMITE = IC2Entities.register("dynamite", dynamite.setTrackingRange(80).setUpdateInterval(1).m_20699_(0.5f, 0.5f).m_20712_("tnt"));
        EntityType.Builder laser = EntityType.Builder.m_20704_(MiningLaserEntity::new, (MobCategory)MobCategory.MISC);
        MINING_LASER = IC2Entities.register("mininglaser", laser.setTrackingRange(160).setUpdateInterval(1).m_20698_().m_20719_().m_20699_(0.85f, 0.85f).m_20712_("tnt"));
        EntityType.Builder tracking = EntityType.Builder.m_20704_(TrackingMiningLaserEntity::new, (MobCategory)MobCategory.MISC);
        TRACKING_MINING_LASER = IC2Entities.register("tracking_mininglaser", tracking.setTrackingRange(160).setUpdateInterval(1).m_20698_().m_20719_().m_20699_(0.85f, 0.85f).m_20712_("tnt"));
        EntityType.Builder rocket = EntityType.Builder.m_20704_(MiningRocketEntity::new, (MobCategory)MobCategory.MISC);
        ROCKET_MINER = IC2Entities.register("rocket_miner", rocket.setTrackingRange(160).setUpdateInterval(5).m_20698_().m_20719_().m_20699_(0.85f, 0.85f).m_20712_("tnt"));
        EntityType.Builder batbox = EntityType.Builder.m_20704_(BatboxMinecart::new, (MobCategory)MobCategory.MISC);
        BATBOX = IC2Entities.register("batbox", batbox.m_20699_(0.98f, 0.7f).m_20702_(8).m_20712_("minecart"));
        EntityType.Builder mfe = EntityType.Builder.m_20704_(MFEMinecart::new, (MobCategory)MobCategory.MISC);
        MFE = IC2Entities.register("mfe", mfe.m_20699_(0.98f, 0.7f).m_20702_(8).m_20712_("minecart"));
        EntityType.Builder mfsu = EntityType.Builder.m_20704_(MFSUMinecart::new, (MobCategory)MobCategory.MISC);
        MFSU = IC2Entities.register("mfsu", mfsu.m_20699_(0.98f, 0.7f).m_20702_(8).m_20712_("minecart"));
        EntityType.Builder esu = EntityType.Builder.m_20704_(ESUMinecart::new, (MobCategory)MobCategory.MISC);
        ESU = IC2Entities.register("esu", esu.m_20699_(0.98f, 0.7f).m_20702_(8).m_20712_("minecart"));
        EntityType.Builder isu = EntityType.Builder.m_20704_(ISUMinecart::new, (MobCategory)MobCategory.MISC);
        ISU = IC2Entities.register("isu", isu.m_20699_(0.98f, 0.7f).m_20702_(8).m_20712_("minecart"));
        EntityType.Builder pesu = EntityType.Builder.m_20704_(PESUMinecart::new, (MobCategory)MobCategory.MISC);
        PESU = IC2Entities.register("pesu", pesu.m_20699_(0.98f, 0.7f).m_20702_(8).m_20712_("minecart"));
    }
}

