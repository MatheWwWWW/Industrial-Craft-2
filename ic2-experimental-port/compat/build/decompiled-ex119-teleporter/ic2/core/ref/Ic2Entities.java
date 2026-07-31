/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 */
package ic2.core.ref;

import ic2.core.IC2;
import ic2.core.entity.LaserBulletEntity;
import ic2.core.entity.block.ITntEntity;
import ic2.core.entity.block.NukeEntity;
import ic2.core.entity.boat.CarbonBoatEntity;
import ic2.core.entity.boat.ElectricBoatEntity;
import ic2.core.entity.boat.RubberBoatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class Ic2Entities {
    public static final EntityType<ITntEntity> ITNT = Ic2Entities.register("itnt", EntityType.Builder.m_20704_(ITntEntity::new, (MobCategory)MobCategory.MISC).m_20719_().m_20699_(0.98f, 0.98f).m_20702_(10).m_20717_(10));
    public static final EntityType<NukeEntity> NUKE = Ic2Entities.register("nuke", EntityType.Builder.m_20704_(NukeEntity::new, (MobCategory)MobCategory.MISC).m_20719_().m_20699_(0.98f, 0.98f).m_20702_(10).m_20717_(10));
    public static final EntityType<LaserBulletEntity> LASER_BULLET = Ic2Entities.register("laser_bullet", EntityType.Builder.m_20704_(LaserBulletEntity::new, (MobCategory)MobCategory.MISC).m_20719_().m_20699_(0.8f, 0.8f).m_20702_(8).m_20717_(8));
    public static final EntityType<RubberBoatEntity> RUBBER_BOAT = Ic2Entities.register("rubber_boat", EntityType.Builder.m_20704_(RubberBoatEntity::new, (MobCategory)MobCategory.MISC).m_20699_(1.375f, 0.5625f).m_20702_(10));
    public static final EntityType<ElectricBoatEntity> ELECTRIC_BOAT = Ic2Entities.register("electric_boat", EntityType.Builder.m_20704_(ElectricBoatEntity::new, (MobCategory)MobCategory.MISC).m_20699_(1.375f, 0.5625f).m_20702_(10));
    public static final EntityType<CarbonBoatEntity> CARBON_BOAT = Ic2Entities.register("carbon_boat", EntityType.Builder.m_20704_(CarbonBoatEntity::new, (MobCategory)MobCategory.MISC).m_20699_(1.375f, 0.5625f).m_20702_(10));

    public static void init() {
    }

    private static <T extends Entity> EntityType<T> register(String string, EntityType.Builder<T> builder) {
        EntityType entityType = builder.m_20712_(IC2.getIdentifier(string).toString());
        IC2.envProxy.registerEntity(IC2.getIdentifier(string), entityType);
        return entityType;
    }
}

