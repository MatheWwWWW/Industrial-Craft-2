/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

public class DefaultAttributes {
    private static final Logger f_22293_ = LogUtils.getLogger();
    private static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> f_22294_ = ImmutableMap.builder().put(EntityType.f_217014_, (Object)Allay.m_218388_().m_22265_()).put(EntityType.f_20529_, (Object)LivingEntity.m_21183_().m_22265_()).put(EntityType.f_147039_, (Object)Axolotl.m_149176_().m_22265_()).put(EntityType.f_20549_, (Object)Bat.m_27455_().m_22265_()).put(EntityType.f_20550_, (Object)Bee.m_27858_().m_22265_()).put(EntityType.f_20551_, (Object)Blaze.m_32238_().m_22265_()).put(EntityType.f_20553_, (Object)Cat.m_28168_().m_22265_()).put(EntityType.f_20554_, (Object)CaveSpider.m_32267_().m_22265_()).put(EntityType.f_20555_, (Object)Chicken.m_28263_().m_22265_()).put(EntityType.f_20556_, (Object)AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20557_, (Object)Cow.m_28307_().m_22265_()).put(EntityType.f_20558_, (Object)Creeper.m_32318_().m_22265_()).put(EntityType.f_20559_, (Object)Dolphin.m_28379_().m_22265_()).put(EntityType.f_20560_, (Object)AbstractChestedHorse.m_30501_().m_22265_()).put(EntityType.f_20562_, (Object)Zombie.m_34328_().m_22265_()).put(EntityType.f_20563_, (Object)ElderGuardian.m_32471_().m_22265_()).put(EntityType.f_20566_, (Object)EnderMan.m_32541_().m_22265_()).put(EntityType.f_20567_, (Object)Endermite.m_32619_().m_22265_()).put(EntityType.f_20565_, (Object)EnderDragon.m_31167_().m_22265_()).put(EntityType.f_20568_, (Object)Evoker.m_32657_().m_22265_()).put(EntityType.f_20452_, (Object)Fox.m_28553_().m_22265_()).put(EntityType.f_217012_, (Object)Frog.m_218525_().m_22265_()).put(EntityType.f_20453_, (Object)Ghast.m_32752_().m_22265_()).put(EntityType.f_20454_, (Object)Giant.m_32796_().m_22265_()).put(EntityType.f_147034_, (Object)GlowSquid.m_29988_().m_22265_()).put(EntityType.f_147035_, (Object)Goat.m_149401_().m_22265_()).put(EntityType.f_20455_, (Object)Guardian.m_32853_().m_22265_()).put(EntityType.f_20456_, (Object)Hoglin.m_34551_().m_22265_()).put(EntityType.f_20457_, (Object)AbstractHorse.m_30627_().m_22265_()).put(EntityType.f_20458_, (Object)Zombie.m_34328_().m_22265_()).put(EntityType.f_20459_, (Object)Illusioner.m_32931_().m_22265_()).put(EntityType.f_20460_, (Object)IronGolem.m_28883_().m_22265_()).put(EntityType.f_20466_, (Object)Llama.m_30824_().m_22265_()).put(EntityType.f_20468_, (Object)MagmaCube.m_33000_().m_22265_()).put(EntityType.f_20504_, (Object)Cow.m_28307_().m_22265_()).put(EntityType.f_20503_, (Object)AbstractChestedHorse.m_30501_().m_22265_()).put(EntityType.f_20505_, (Object)Ocelot.m_29036_().m_22265_()).put(EntityType.f_20507_, (Object)Panda.m_29157_().m_22265_()).put(EntityType.f_20508_, (Object)Parrot.m_29438_().m_22265_()).put(EntityType.f_20509_, (Object)Monster.m_33035_().m_22265_()).put(EntityType.f_20510_, (Object)Pig.m_29503_().m_22265_()).put(EntityType.f_20511_, (Object)Piglin.m_34770_().m_22265_()).put(EntityType.f_20512_, (Object)PiglinBrute.m_35075_().m_22265_()).put(EntityType.f_20513_, (Object)Pillager.m_33307_().m_22265_()).put(EntityType.f_20532_, (Object)Player.m_36340_().m_22265_()).put(EntityType.f_20514_, (Object)PolarBear.m_29560_().m_22265_()).put(EntityType.f_20516_, (Object)AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20517_, (Object)Rabbit.m_29717_().m_22265_()).put(EntityType.f_20518_, (Object)Ravager.m_33371_().m_22265_()).put(EntityType.f_20519_, (Object)AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20520_, (Object)Sheep.m_29873_().m_22265_()).put(EntityType.f_20521_, (Object)Shulker.m_33477_().m_22265_()).put(EntityType.f_20523_, (Object)Silverfish.m_33551_().m_22265_()).put(EntityType.f_20524_, (Object)AbstractSkeleton.m_32166_().m_22265_()).put(EntityType.f_20525_, (Object)SkeletonHorse.m_30918_().m_22265_()).put(EntityType.f_20526_, (Object)Monster.m_33035_().m_22265_()).put(EntityType.f_20528_, (Object)SnowGolem.m_29934_().m_22265_()).put(EntityType.f_20479_, (Object)Spider.m_33815_().m_22265_()).put(EntityType.f_20480_, (Object)Squid.m_29988_().m_22265_()).put(EntityType.f_20481_, (Object)AbstractSkeleton.m_32166_().m_22265_()).put(EntityType.f_20482_, (Object)Strider.m_33937_().m_22265_()).put(EntityType.f_217013_, (Object)Tadpole.m_218720_().m_22265_()).put(EntityType.f_20488_, (Object)Llama.m_30824_().m_22265_()).put(EntityType.f_20489_, (Object)AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20490_, (Object)Turtle.m_30207_().m_22265_()).put(EntityType.f_20491_, (Object)Vex.m_34040_().m_22265_()).put(EntityType.f_20492_, (Object)Villager.m_35503_().m_22265_()).put(EntityType.f_20493_, (Object)Vindicator.m_34104_().m_22265_()).put(EntityType.f_217015_, (Object)Warden.m_219463_().m_22265_()).put(EntityType.f_20494_, (Object)Mob.m_21552_().m_22265_()).put(EntityType.f_20495_, (Object)Witch.m_34155_().m_22265_()).put(EntityType.f_20496_, (Object)WitherBoss.m_31501_().m_22265_()).put(EntityType.f_20497_, (Object)AbstractSkeleton.m_32166_().m_22265_()).put(EntityType.f_20499_, (Object)Wolf.m_30425_().m_22265_()).put(EntityType.f_20500_, (Object)Zoglin.m_34257_().m_22265_()).put(EntityType.f_20501_, (Object)Zombie.m_34328_().m_22265_()).put(EntityType.f_20502_, (Object)ZombieHorse.m_31008_().m_22265_()).put(EntityType.f_20530_, (Object)Zombie.m_34328_().m_22265_()).put(EntityType.f_20531_, (Object)ZombifiedPiglin.m_34470_().m_22265_()).build();

    public static AttributeSupplier m_22297_(EntityType<? extends LivingEntity> p_22298_) {
        return f_22294_.get(p_22298_);
    }

    public static boolean m_22301_(EntityType<?> p_22302_) {
        return f_22294_.containsKey(p_22302_);
    }

    public static void m_22296_() {
        Registry.f_122826_.m_123024_().filter(p_22306_ -> p_22306_.m_20674_() != MobCategory.MISC).filter(p_22304_ -> !DefaultAttributes.m_22301_(p_22304_)).map(Registry.f_122826_::m_7981_).forEach(p_22300_ -> Util.m_143785_("Entity " + p_22300_ + " has no attributes"));
    }
}

