/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

public class SpawnPlacements {
    private static final Map<EntityType<?>, Data> f_21750_ = Maps.newHashMap();

    private static <T extends Mob> void m_21754_(EntityType<T> p_21755_, Type p_21756_, Heightmap.Types p_21757_, SpawnPredicate<T> p_21758_) {
        Data $$4 = f_21750_.put(p_21755_, new Data(p_21757_, p_21756_, p_21758_));
        if ($$4 != null) {
            throw new IllegalStateException("Duplicate registration for type " + Registry.f_122826_.m_7981_(p_21755_));
        }
    }

    public static Type m_21752_(EntityType<?> p_21753_) {
        Data $$1 = f_21750_.get(p_21753_);
        return $$1 == null ? Type.NO_RESTRICTIONS : $$1.f_21768_;
    }

    public static Heightmap.Types m_21765_(@Nullable EntityType<?> p_21766_) {
        Data $$1 = f_21750_.get(p_21766_);
        return $$1 == null ? Heightmap.Types.MOTION_BLOCKING_NO_LEAVES : $$1.f_21767_;
    }

    public static <T extends Entity> boolean m_217074_(EntityType<T> p_217075_, ServerLevelAccessor p_217076_, MobSpawnType p_217077_, BlockPos p_217078_, RandomSource p_217079_) {
        Data $$5 = f_21750_.get(p_217075_);
        return $$5 == null || $$5.f_21769_.m_217080_(p_217075_, p_217076_, p_217077_, p_217078_, p_217079_);
    }

    static {
        SpawnPlacements.m_21754_(EntityType.f_147039_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Axolotl::m_218437_);
        SpawnPlacements.m_21754_(EntityType.f_20556_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::m_218282_);
        SpawnPlacements.m_21754_(EntityType.f_20559_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::m_218282_);
        SpawnPlacements.m_21754_(EntityType.f_20562_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Drowned::m_218955_);
        SpawnPlacements.m_21754_(EntityType.f_20455_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::m_218990_);
        SpawnPlacements.m_21754_(EntityType.f_20516_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::m_218282_);
        SpawnPlacements.m_21754_(EntityType.f_20519_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::m_218282_);
        SpawnPlacements.m_21754_(EntityType.f_20480_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::m_218282_);
        SpawnPlacements.m_21754_(EntityType.f_20489_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TropicalFish::m_218266_);
        SpawnPlacements.m_21754_(EntityType.f_20549_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bat::m_218098_);
        SpawnPlacements.m_21754_(EntityType.f_20551_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219019_);
        SpawnPlacements.m_21754_(EntityType.f_20554_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20555_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20557_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20558_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20560_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20566_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20567_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Endermite::m_218968_);
        SpawnPlacements.m_21754_(EntityType.f_20565_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
        SpawnPlacements.m_21754_(EntityType.f_217012_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frog::m_218511_);
        SpawnPlacements.m_21754_(EntityType.f_20453_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ghast::m_218984_);
        SpawnPlacements.m_21754_(EntityType.f_20454_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_147034_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowSquid::m_217017_);
        SpawnPlacements.m_21754_(EntityType.f_147035_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Goat::m_218752_);
        SpawnPlacements.m_21754_(EntityType.f_20457_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20458_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Husk::m_218996_);
        SpawnPlacements.m_21754_(EntityType.f_20460_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
        SpawnPlacements.m_21754_(EntityType.f_20466_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20468_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagmaCube::m_219002_);
        SpawnPlacements.m_21754_(EntityType.f_20504_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MushroomCow::m_218200_);
        SpawnPlacements.m_21754_(EntityType.f_20503_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20505_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Ocelot::m_218206_);
        SpawnPlacements.m_21754_(EntityType.f_20508_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Parrot::m_218241_);
        SpawnPlacements.m_21754_(EntityType.f_20510_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20456_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hoglin::m_219181_);
        SpawnPlacements.m_21754_(EntityType.f_20511_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Piglin::m_219197_);
        SpawnPlacements.m_21754_(EntityType.f_20513_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::m_219025_);
        SpawnPlacements.m_21754_(EntityType.f_20514_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PolarBear::m_218249_);
        SpawnPlacements.m_21754_(EntityType.f_20517_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Rabbit::m_218255_);
        SpawnPlacements.m_21754_(EntityType.f_20520_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20523_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Silverfish::m_219076_);
        SpawnPlacements.m_21754_(EntityType.f_20524_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20525_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20526_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Slime::m_219112_);
        SpawnPlacements.m_21754_(EntityType.f_20528_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
        SpawnPlacements.m_21754_(EntityType.f_20479_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20481_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stray::m_219120_);
        SpawnPlacements.m_21754_(EntityType.f_20482_, Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Strider::m_219128_);
        SpawnPlacements.m_21754_(EntityType.f_20490_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Turtle::m_218276_);
        SpawnPlacements.m_21754_(EntityType.f_20492_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
        SpawnPlacements.m_21754_(EntityType.f_20495_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20496_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20497_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20499_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wolf::m_218291_);
        SpawnPlacements.m_21754_(EntityType.f_20501_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20502_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20531_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedPiglin::m_219173_);
        SpawnPlacements.m_21754_(EntityType.f_20530_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20553_, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20563_, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::m_218990_);
        SpawnPlacements.m_21754_(EntityType.f_20568_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20452_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fox::m_218175_);
        SpawnPlacements.m_21754_(EntityType.f_20459_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20507_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20509_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
        SpawnPlacements.m_21754_(EntityType.f_20518_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20521_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
        SpawnPlacements.m_21754_(EntityType.f_20488_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_218104_);
        SpawnPlacements.m_21754_(EntityType.f_20491_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20493_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_219013_);
        SpawnPlacements.m_21754_(EntityType.f_20494_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
        SpawnPlacements.m_21754_(EntityType.f_217015_, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
    }

    static class Data {
        final Heightmap.Types f_21767_;
        final Type f_21768_;
        final SpawnPredicate<?> f_21769_;

        public Data(Heightmap.Types p_21771_, Type p_21772_, SpawnPredicate<?> p_21773_) {
            this.f_21767_ = p_21771_;
            this.f_21768_ = p_21772_;
            this.f_21769_ = p_21773_;
        }
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type ON_GROUND = new Type();
        public static final /* enum */ Type IN_WATER = new Type();
        public static final /* enum */ Type NO_RESTRICTIONS = new Type();
        public static final /* enum */ Type IN_LAVA = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_21796_) {
            return Enum.valueOf(Type.class, p_21796_);
        }

        private static /* synthetic */ Type[] m_147336_() {
            return new Type[]{ON_GROUND, IN_WATER, NO_RESTRICTIONS, IN_LAVA};
        }

        static {
            $VALUES = Type.m_147336_();
        }
    }

    @FunctionalInterface
    public static interface SpawnPredicate<T extends Entity> {
        public boolean m_217080_(EntityType<T> var1, ServerLevelAccessor var2, MobSpawnType var3, BlockPos var4, RandomSource var5);
    }
}

