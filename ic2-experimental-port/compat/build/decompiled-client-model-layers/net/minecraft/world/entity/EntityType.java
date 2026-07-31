/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

public class EntityType<T extends Entity>
implements EntityTypeTest<Entity, T> {
    private static final Logger f_20534_ = LogUtils.getLogger();
    public static final String f_147037_ = "EntityTag";
    private final Holder.Reference<EntityType<?>> f_204038_ = Registry.f_122826_.m_203693_(this);
    private static final float f_147038_ = 1.3964844f;
    public static final EntityType<Allay> f_217014_ = EntityType.m_20634_("allay", Builder.m_20704_(Allay::new, MobCategory.CREATURE).m_20699_(0.35f, 0.6f).m_20702_(8).m_20717_(2));
    public static final EntityType<AreaEffectCloud> f_20476_ = EntityType.m_20634_("area_effect_cloud", Builder.m_20704_(AreaEffectCloud::new, MobCategory.MISC).m_20719_().m_20699_(6.0f, 0.5f).m_20702_(10).m_20717_(Integer.MAX_VALUE));
    public static final EntityType<ArmorStand> f_20529_ = EntityType.m_20634_("armor_stand", Builder.m_20704_(ArmorStand::new, MobCategory.MISC).m_20699_(0.5f, 1.975f).m_20702_(10));
    public static final EntityType<Arrow> f_20548_ = EntityType.m_20634_("arrow", Builder.m_20704_(Arrow::new, MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20702_(4).m_20717_(20));
    public static final EntityType<Axolotl> f_147039_ = EntityType.m_20634_("axolotl", Builder.m_20704_(Axolotl::new, MobCategory.AXOLOTLS).m_20699_(0.75f, 0.42f).m_20702_(10));
    public static final EntityType<Bat> f_20549_ = EntityType.m_20634_("bat", Builder.m_20704_(Bat::new, MobCategory.AMBIENT).m_20699_(0.5f, 0.9f).m_20702_(5));
    public static final EntityType<Bee> f_20550_ = EntityType.m_20634_("bee", Builder.m_20704_(Bee::new, MobCategory.CREATURE).m_20699_(0.7f, 0.6f).m_20702_(8));
    public static final EntityType<Blaze> f_20551_ = EntityType.m_20634_("blaze", Builder.m_20704_(Blaze::new, MobCategory.MONSTER).m_20719_().m_20699_(0.6f, 1.8f).m_20702_(8));
    public static final EntityType<Boat> f_20552_ = EntityType.m_20634_("boat", Builder.m_20704_(Boat::new, MobCategory.MISC).m_20699_(1.375f, 0.5625f).m_20702_(10));
    public static final EntityType<ChestBoat> f_217016_ = EntityType.m_20634_("chest_boat", Builder.m_20704_(ChestBoat::new, MobCategory.MISC).m_20699_(1.375f, 0.5625f).m_20702_(10));
    public static final EntityType<Cat> f_20553_ = EntityType.m_20634_("cat", Builder.m_20704_(Cat::new, MobCategory.CREATURE).m_20699_(0.6f, 0.7f).m_20702_(8));
    public static final EntityType<CaveSpider> f_20554_ = EntityType.m_20634_("cave_spider", Builder.m_20704_(CaveSpider::new, MobCategory.MONSTER).m_20699_(0.7f, 0.5f).m_20702_(8));
    public static final EntityType<Chicken> f_20555_ = EntityType.m_20634_("chicken", Builder.m_20704_(Chicken::new, MobCategory.CREATURE).m_20699_(0.4f, 0.7f).m_20702_(10));
    public static final EntityType<Cod> f_20556_ = EntityType.m_20634_("cod", Builder.m_20704_(Cod::new, MobCategory.WATER_AMBIENT).m_20699_(0.5f, 0.3f).m_20702_(4));
    public static final EntityType<Cow> f_20557_ = EntityType.m_20634_("cow", Builder.m_20704_(Cow::new, MobCategory.CREATURE).m_20699_(0.9f, 1.4f).m_20702_(10));
    public static final EntityType<Creeper> f_20558_ = EntityType.m_20634_("creeper", Builder.m_20704_(Creeper::new, MobCategory.MONSTER).m_20699_(0.6f, 1.7f).m_20702_(8));
    public static final EntityType<Dolphin> f_20559_ = EntityType.m_20634_("dolphin", Builder.m_20704_(Dolphin::new, MobCategory.WATER_CREATURE).m_20699_(0.9f, 0.6f));
    public static final EntityType<Donkey> f_20560_ = EntityType.m_20634_("donkey", Builder.m_20704_(Donkey::new, MobCategory.CREATURE).m_20699_(1.3964844f, 1.5f).m_20702_(10));
    public static final EntityType<DragonFireball> f_20561_ = EntityType.m_20634_("dragon_fireball", Builder.m_20704_(DragonFireball::new, MobCategory.MISC).m_20699_(1.0f, 1.0f).m_20702_(4).m_20717_(10));
    public static final EntityType<Drowned> f_20562_ = EntityType.m_20634_("drowned", Builder.m_20704_(Drowned::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<ElderGuardian> f_20563_ = EntityType.m_20634_("elder_guardian", Builder.m_20704_(ElderGuardian::new, MobCategory.MONSTER).m_20699_(1.9975f, 1.9975f).m_20702_(10));
    public static final EntityType<EndCrystal> f_20564_ = EntityType.m_20634_("end_crystal", Builder.m_20704_(EndCrystal::new, MobCategory.MISC).m_20699_(2.0f, 2.0f).m_20702_(16).m_20717_(Integer.MAX_VALUE));
    public static final EntityType<EnderDragon> f_20565_ = EntityType.m_20634_("ender_dragon", Builder.m_20704_(EnderDragon::new, MobCategory.MONSTER).m_20719_().m_20699_(16.0f, 8.0f).m_20702_(10));
    public static final EntityType<EnderMan> f_20566_ = EntityType.m_20634_("enderman", Builder.m_20704_(EnderMan::new, MobCategory.MONSTER).m_20699_(0.6f, 2.9f).m_20702_(8));
    public static final EntityType<Endermite> f_20567_ = EntityType.m_20634_("endermite", Builder.m_20704_(Endermite::new, MobCategory.MONSTER).m_20699_(0.4f, 0.3f).m_20702_(8));
    public static final EntityType<Evoker> f_20568_ = EntityType.m_20634_("evoker", Builder.m_20704_(Evoker::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<EvokerFangs> f_20569_ = EntityType.m_20634_("evoker_fangs", Builder.m_20704_(EvokerFangs::new, MobCategory.MISC).m_20699_(0.5f, 0.8f).m_20702_(6).m_20717_(2));
    public static final EntityType<ExperienceOrb> f_20570_ = EntityType.m_20634_("experience_orb", Builder.m_20704_(ExperienceOrb::new, MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20702_(6).m_20717_(20));
    public static final EntityType<EyeOfEnder> f_20571_ = EntityType.m_20634_("eye_of_ender", Builder.m_20704_(EyeOfEnder::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(4));
    public static final EntityType<FallingBlockEntity> f_20450_ = EntityType.m_20634_("falling_block", Builder.m_20704_(FallingBlockEntity::new, MobCategory.MISC).m_20699_(0.98f, 0.98f).m_20702_(10).m_20717_(20));
    public static final EntityType<FireworkRocketEntity> f_20451_ = EntityType.m_20634_("firework_rocket", Builder.m_20704_(FireworkRocketEntity::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(10));
    public static final EntityType<Fox> f_20452_ = EntityType.m_20634_("fox", Builder.m_20704_(Fox::new, MobCategory.CREATURE).m_20699_(0.6f, 0.7f).m_20702_(8).m_20714_(Blocks.f_50685_));
    public static final EntityType<Frog> f_217012_ = EntityType.m_20634_("frog", Builder.m_20704_(Frog::new, MobCategory.CREATURE).m_20699_(0.5f, 0.5f).m_20702_(10));
    public static final EntityType<Ghast> f_20453_ = EntityType.m_20634_("ghast", Builder.m_20704_(Ghast::new, MobCategory.MONSTER).m_20719_().m_20699_(4.0f, 4.0f).m_20702_(10));
    public static final EntityType<Giant> f_20454_ = EntityType.m_20634_("giant", Builder.m_20704_(Giant::new, MobCategory.MONSTER).m_20699_(3.6f, 12.0f).m_20702_(10));
    public static final EntityType<GlowItemFrame> f_147033_ = EntityType.m_20634_("glow_item_frame", Builder.m_20704_(GlowItemFrame::new, MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20702_(10).m_20717_(Integer.MAX_VALUE));
    public static final EntityType<GlowSquid> f_147034_ = EntityType.m_20634_("glow_squid", Builder.m_20704_(GlowSquid::new, MobCategory.UNDERGROUND_WATER_CREATURE).m_20699_(0.8f, 0.8f).m_20702_(10));
    public static final EntityType<Goat> f_147035_ = EntityType.m_20634_("goat", Builder.m_20704_(Goat::new, MobCategory.CREATURE).m_20699_(0.9f, 1.3f).m_20702_(10));
    public static final EntityType<Guardian> f_20455_ = EntityType.m_20634_("guardian", Builder.m_20704_(Guardian::new, MobCategory.MONSTER).m_20699_(0.85f, 0.85f).m_20702_(8));
    public static final EntityType<Hoglin> f_20456_ = EntityType.m_20634_("hoglin", Builder.m_20704_(Hoglin::new, MobCategory.MONSTER).m_20699_(1.3964844f, 1.4f).m_20702_(8));
    public static final EntityType<Horse> f_20457_ = EntityType.m_20634_("horse", Builder.m_20704_(Horse::new, MobCategory.CREATURE).m_20699_(1.3964844f, 1.6f).m_20702_(10));
    public static final EntityType<Husk> f_20458_ = EntityType.m_20634_("husk", Builder.m_20704_(Husk::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<Illusioner> f_20459_ = EntityType.m_20634_("illusioner", Builder.m_20704_(Illusioner::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<IronGolem> f_20460_ = EntityType.m_20634_("iron_golem", Builder.m_20704_(IronGolem::new, MobCategory.MISC).m_20699_(1.4f, 2.7f).m_20702_(10));
    public static final EntityType<ItemEntity> f_20461_ = EntityType.m_20634_("item", Builder.m_20704_(ItemEntity::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(6).m_20717_(20));
    public static final EntityType<ItemFrame> f_20462_ = EntityType.m_20634_("item_frame", Builder.m_20704_(ItemFrame::new, MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20702_(10).m_20717_(Integer.MAX_VALUE));
    public static final EntityType<LargeFireball> f_20463_ = EntityType.m_20634_("fireball", Builder.m_20704_(LargeFireball::new, MobCategory.MISC).m_20699_(1.0f, 1.0f).m_20702_(4).m_20717_(10));
    public static final EntityType<LeashFenceKnotEntity> f_20464_ = EntityType.m_20634_("leash_knot", Builder.m_20704_(LeashFenceKnotEntity::new, MobCategory.MISC).m_20716_().m_20699_(0.375f, 0.5f).m_20702_(10).m_20717_(Integer.MAX_VALUE));
    public static final EntityType<LightningBolt> f_20465_ = EntityType.m_20634_("lightning_bolt", Builder.m_20704_(LightningBolt::new, MobCategory.MISC).m_20716_().m_20699_(0.0f, 0.0f).m_20702_(16).m_20717_(Integer.MAX_VALUE));
    public static final EntityType<Llama> f_20466_ = EntityType.m_20634_("llama", Builder.m_20704_(Llama::new, MobCategory.CREATURE).m_20699_(0.9f, 1.87f).m_20702_(10));
    public static final EntityType<LlamaSpit> f_20467_ = EntityType.m_20634_("llama_spit", Builder.m_20704_(LlamaSpit::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(10));
    public static final EntityType<MagmaCube> f_20468_ = EntityType.m_20634_("magma_cube", Builder.m_20704_(MagmaCube::new, MobCategory.MONSTER).m_20719_().m_20699_(2.04f, 2.04f).m_20702_(8));
    public static final EntityType<Marker> f_147036_ = EntityType.m_20634_("marker", Builder.m_20704_(Marker::new, MobCategory.MISC).m_20699_(0.0f, 0.0f).m_20702_(0));
    public static final EntityType<Minecart> f_20469_ = EntityType.m_20634_("minecart", Builder.m_20704_(Minecart::new, MobCategory.MISC).m_20699_(0.98f, 0.7f).m_20702_(8));
    public static final EntityType<MinecartChest> f_20470_ = EntityType.m_20634_("chest_minecart", Builder.m_20704_(MinecartChest::new, MobCategory.MISC).m_20699_(0.98f, 0.7f).m_20702_(8));
    public static final EntityType<MinecartCommandBlock> f_20471_ = EntityType.m_20634_("command_block_minecart", Builder.m_20704_(MinecartCommandBlock::new, MobCategory.MISC).m_20699_(0.98f, 0.7f).m_20702_(8));
    public static final EntityType<MinecartFurnace> f_20472_ = EntityType.m_20634_("furnace_minecart", Builder.m_20704_(MinecartFurnace::new, MobCategory.MISC).m_20699_(0.98f, 0.7f).m_20702_(8));
    public static final EntityType<MinecartHopper> f_20473_ = EntityType.m_20634_("hopper_minecart", Builder.m_20704_(MinecartHopper::new, MobCategory.MISC).m_20699_(0.98f, 0.7f).m_20702_(8));
    public static final EntityType<MinecartSpawner> f_20474_ = EntityType.m_20634_("spawner_minecart", Builder.m_20704_(MinecartSpawner::new, MobCategory.MISC).m_20699_(0.98f, 0.7f).m_20702_(8));
    public static final EntityType<MinecartTNT> f_20475_ = EntityType.m_20634_("tnt_minecart", Builder.m_20704_(MinecartTNT::new, MobCategory.MISC).m_20699_(0.98f, 0.7f).m_20702_(8));
    public static final EntityType<Mule> f_20503_ = EntityType.m_20634_("mule", Builder.m_20704_(Mule::new, MobCategory.CREATURE).m_20699_(1.3964844f, 1.6f).m_20702_(8));
    public static final EntityType<MushroomCow> f_20504_ = EntityType.m_20634_("mooshroom", Builder.m_20704_(MushroomCow::new, MobCategory.CREATURE).m_20699_(0.9f, 1.4f).m_20702_(10));
    public static final EntityType<Ocelot> f_20505_ = EntityType.m_20634_("ocelot", Builder.m_20704_(Ocelot::new, MobCategory.CREATURE).m_20699_(0.6f, 0.7f).m_20702_(10));
    public static final EntityType<Painting> f_20506_ = EntityType.m_20634_("painting", Builder.m_20704_(Painting::new, MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20702_(10).m_20717_(Integer.MAX_VALUE));
    public static final EntityType<Panda> f_20507_ = EntityType.m_20634_("panda", Builder.m_20704_(Panda::new, MobCategory.CREATURE).m_20699_(1.3f, 1.25f).m_20702_(10));
    public static final EntityType<Parrot> f_20508_ = EntityType.m_20634_("parrot", Builder.m_20704_(Parrot::new, MobCategory.CREATURE).m_20699_(0.5f, 0.9f).m_20702_(8));
    public static final EntityType<Phantom> f_20509_ = EntityType.m_20634_("phantom", Builder.m_20704_(Phantom::new, MobCategory.MONSTER).m_20699_(0.9f, 0.5f).m_20702_(8));
    public static final EntityType<Pig> f_20510_ = EntityType.m_20634_("pig", Builder.m_20704_(Pig::new, MobCategory.CREATURE).m_20699_(0.9f, 0.9f).m_20702_(10));
    public static final EntityType<Piglin> f_20511_ = EntityType.m_20634_("piglin", Builder.m_20704_(Piglin::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<PiglinBrute> f_20512_ = EntityType.m_20634_("piglin_brute", Builder.m_20704_(PiglinBrute::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<Pillager> f_20513_ = EntityType.m_20634_("pillager", Builder.m_20704_(Pillager::new, MobCategory.MONSTER).m_20720_().m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<PolarBear> f_20514_ = EntityType.m_20634_("polar_bear", Builder.m_20704_(PolarBear::new, MobCategory.CREATURE).m_20714_(Blocks.f_152499_).m_20699_(1.4f, 1.4f).m_20702_(10));
    public static final EntityType<PrimedTnt> f_20515_ = EntityType.m_20634_("tnt", Builder.m_20704_(PrimedTnt::new, MobCategory.MISC).m_20719_().m_20699_(0.98f, 0.98f).m_20702_(10).m_20717_(10));
    public static final EntityType<Pufferfish> f_20516_ = EntityType.m_20634_("pufferfish", Builder.m_20704_(Pufferfish::new, MobCategory.WATER_AMBIENT).m_20699_(0.7f, 0.7f).m_20702_(4));
    public static final EntityType<Rabbit> f_20517_ = EntityType.m_20634_("rabbit", Builder.m_20704_(Rabbit::new, MobCategory.CREATURE).m_20699_(0.4f, 0.5f).m_20702_(8));
    public static final EntityType<Ravager> f_20518_ = EntityType.m_20634_("ravager", Builder.m_20704_(Ravager::new, MobCategory.MONSTER).m_20699_(1.95f, 2.2f).m_20702_(10));
    public static final EntityType<Salmon> f_20519_ = EntityType.m_20634_("salmon", Builder.m_20704_(Salmon::new, MobCategory.WATER_AMBIENT).m_20699_(0.7f, 0.4f).m_20702_(4));
    public static final EntityType<Sheep> f_20520_ = EntityType.m_20634_("sheep", Builder.m_20704_(Sheep::new, MobCategory.CREATURE).m_20699_(0.9f, 1.3f).m_20702_(10));
    public static final EntityType<Shulker> f_20521_ = EntityType.m_20634_("shulker", Builder.m_20704_(Shulker::new, MobCategory.MONSTER).m_20719_().m_20720_().m_20699_(1.0f, 1.0f).m_20702_(10));
    public static final EntityType<ShulkerBullet> f_20522_ = EntityType.m_20634_("shulker_bullet", Builder.m_20704_(ShulkerBullet::new, MobCategory.MISC).m_20699_(0.3125f, 0.3125f).m_20702_(8));
    public static final EntityType<Silverfish> f_20523_ = EntityType.m_20634_("silverfish", Builder.m_20704_(Silverfish::new, MobCategory.MONSTER).m_20699_(0.4f, 0.3f).m_20702_(8));
    public static final EntityType<Skeleton> f_20524_ = EntityType.m_20634_("skeleton", Builder.m_20704_(Skeleton::new, MobCategory.MONSTER).m_20699_(0.6f, 1.99f).m_20702_(8));
    public static final EntityType<SkeletonHorse> f_20525_ = EntityType.m_20634_("skeleton_horse", Builder.m_20704_(SkeletonHorse::new, MobCategory.CREATURE).m_20699_(1.3964844f, 1.6f).m_20702_(10));
    public static final EntityType<Slime> f_20526_ = EntityType.m_20634_("slime", Builder.m_20704_(Slime::new, MobCategory.MONSTER).m_20699_(2.04f, 2.04f).m_20702_(10));
    public static final EntityType<SmallFireball> f_20527_ = EntityType.m_20634_("small_fireball", Builder.m_20704_(SmallFireball::new, MobCategory.MISC).m_20699_(0.3125f, 0.3125f).m_20702_(4).m_20717_(10));
    public static final EntityType<SnowGolem> f_20528_ = EntityType.m_20634_("snow_golem", Builder.m_20704_(SnowGolem::new, MobCategory.MISC).m_20714_(Blocks.f_152499_).m_20699_(0.7f, 1.9f).m_20702_(8));
    public static final EntityType<Snowball> f_20477_ = EntityType.m_20634_("snowball", Builder.m_20704_(Snowball::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(10));
    public static final EntityType<SpectralArrow> f_20478_ = EntityType.m_20634_("spectral_arrow", Builder.m_20704_(SpectralArrow::new, MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20702_(4).m_20717_(20));
    public static final EntityType<Spider> f_20479_ = EntityType.m_20634_("spider", Builder.m_20704_(Spider::new, MobCategory.MONSTER).m_20699_(1.4f, 0.9f).m_20702_(8));
    public static final EntityType<Squid> f_20480_ = EntityType.m_20634_("squid", Builder.m_20704_(Squid::new, MobCategory.WATER_CREATURE).m_20699_(0.8f, 0.8f).m_20702_(8));
    public static final EntityType<Stray> f_20481_ = EntityType.m_20634_("stray", Builder.m_20704_(Stray::new, MobCategory.MONSTER).m_20699_(0.6f, 1.99f).m_20714_(Blocks.f_152499_).m_20702_(8));
    public static final EntityType<Strider> f_20482_ = EntityType.m_20634_("strider", Builder.m_20704_(Strider::new, MobCategory.CREATURE).m_20719_().m_20699_(0.9f, 1.7f).m_20702_(10));
    public static final EntityType<Tadpole> f_217013_ = EntityType.m_20634_("tadpole", Builder.m_20704_(Tadpole::new, MobCategory.CREATURE).m_20699_(Tadpole.f_218681_, Tadpole.f_218682_).m_20702_(10));
    public static final EntityType<ThrownEgg> f_20483_ = EntityType.m_20634_("egg", Builder.m_20704_(ThrownEgg::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(10));
    public static final EntityType<ThrownEnderpearl> f_20484_ = EntityType.m_20634_("ender_pearl", Builder.m_20704_(ThrownEnderpearl::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(10));
    public static final EntityType<ThrownExperienceBottle> f_20485_ = EntityType.m_20634_("experience_bottle", Builder.m_20704_(ThrownExperienceBottle::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(10));
    public static final EntityType<ThrownPotion> f_20486_ = EntityType.m_20634_("potion", Builder.m_20704_(ThrownPotion::new, MobCategory.MISC).m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(10));
    public static final EntityType<ThrownTrident> f_20487_ = EntityType.m_20634_("trident", Builder.m_20704_(ThrownTrident::new, MobCategory.MISC).m_20699_(0.5f, 0.5f).m_20702_(4).m_20717_(20));
    public static final EntityType<TraderLlama> f_20488_ = EntityType.m_20634_("trader_llama", Builder.m_20704_(TraderLlama::new, MobCategory.CREATURE).m_20699_(0.9f, 1.87f).m_20702_(10));
    public static final EntityType<TropicalFish> f_20489_ = EntityType.m_20634_("tropical_fish", Builder.m_20704_(TropicalFish::new, MobCategory.WATER_AMBIENT).m_20699_(0.5f, 0.4f).m_20702_(4));
    public static final EntityType<Turtle> f_20490_ = EntityType.m_20634_("turtle", Builder.m_20704_(Turtle::new, MobCategory.CREATURE).m_20699_(1.2f, 0.4f).m_20702_(10));
    public static final EntityType<Vex> f_20491_ = EntityType.m_20634_("vex", Builder.m_20704_(Vex::new, MobCategory.MONSTER).m_20719_().m_20699_(0.4f, 0.8f).m_20702_(8));
    public static final EntityType<Villager> f_20492_ = EntityType.m_20634_("villager", Builder.m_20704_(Villager::new, MobCategory.MISC).m_20699_(0.6f, 1.95f).m_20702_(10));
    public static final EntityType<Vindicator> f_20493_ = EntityType.m_20634_("vindicator", Builder.m_20704_(Vindicator::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<WanderingTrader> f_20494_ = EntityType.m_20634_("wandering_trader", Builder.m_20704_(WanderingTrader::new, MobCategory.CREATURE).m_20699_(0.6f, 1.95f).m_20702_(10));
    public static final EntityType<Warden> f_217015_ = EntityType.m_20634_("warden", Builder.m_20704_(Warden::new, MobCategory.MONSTER).m_20699_(0.9f, 2.9f).m_20702_(16).m_20719_());
    public static final EntityType<Witch> f_20495_ = EntityType.m_20634_("witch", Builder.m_20704_(Witch::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<WitherBoss> f_20496_ = EntityType.m_20634_("wither", Builder.m_20704_(WitherBoss::new, MobCategory.MONSTER).m_20719_().m_20714_(Blocks.f_50070_).m_20699_(0.9f, 3.5f).m_20702_(10));
    public static final EntityType<WitherSkeleton> f_20497_ = EntityType.m_20634_("wither_skeleton", Builder.m_20704_(WitherSkeleton::new, MobCategory.MONSTER).m_20719_().m_20714_(Blocks.f_50070_).m_20699_(0.7f, 2.4f).m_20702_(8));
    public static final EntityType<WitherSkull> f_20498_ = EntityType.m_20634_("wither_skull", Builder.m_20704_(WitherSkull::new, MobCategory.MISC).m_20699_(0.3125f, 0.3125f).m_20702_(4).m_20717_(10));
    public static final EntityType<Wolf> f_20499_ = EntityType.m_20634_("wolf", Builder.m_20704_(Wolf::new, MobCategory.CREATURE).m_20699_(0.6f, 0.85f).m_20702_(10));
    public static final EntityType<Zoglin> f_20500_ = EntityType.m_20634_("zoglin", Builder.m_20704_(Zoglin::new, MobCategory.MONSTER).m_20719_().m_20699_(1.3964844f, 1.4f).m_20702_(8));
    public static final EntityType<Zombie> f_20501_ = EntityType.m_20634_("zombie", Builder.m_20704_(Zombie::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<ZombieHorse> f_20502_ = EntityType.m_20634_("zombie_horse", Builder.m_20704_(ZombieHorse::new, MobCategory.CREATURE).m_20699_(1.3964844f, 1.6f).m_20702_(10));
    public static final EntityType<ZombieVillager> f_20530_ = EntityType.m_20634_("zombie_villager", Builder.m_20704_(ZombieVillager::new, MobCategory.MONSTER).m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<ZombifiedPiglin> f_20531_ = EntityType.m_20634_("zombified_piglin", Builder.m_20704_(ZombifiedPiglin::new, MobCategory.MONSTER).m_20719_().m_20699_(0.6f, 1.95f).m_20702_(8));
    public static final EntityType<Player> f_20532_ = EntityType.m_20634_("player", Builder.m_20710_(MobCategory.MISC).m_20716_().m_20698_().m_20699_(0.6f, 1.8f).m_20702_(32).m_20717_(2));
    public static final EntityType<FishingHook> f_20533_ = EntityType.m_20634_("fishing_bobber", Builder.m_20704_(FishingHook::new, MobCategory.MISC).m_20716_().m_20698_().m_20699_(0.25f, 0.25f).m_20702_(4).m_20717_(5));
    private final EntityFactory<T> f_20535_;
    private final MobCategory f_20536_;
    private final ImmutableSet<Block> f_20537_;
    private final boolean f_20538_;
    private final boolean f_20539_;
    private final boolean f_20540_;
    private final boolean f_20541_;
    private final int f_20542_;
    private final int f_20543_;
    @Nullable
    private String f_20544_;
    @Nullable
    private Component f_20545_;
    @Nullable
    private ResourceLocation f_20546_;
    private final EntityDimensions f_20547_;

    private static <T extends Entity> EntityType<T> m_20634_(String p_20635_, Builder<T> p_20636_) {
        return Registry.m_122961_(Registry.f_122826_, p_20635_, p_20636_.m_20712_(p_20635_));
    }

    public static ResourceLocation m_20613_(EntityType<?> p_20614_) {
        return Registry.f_122826_.m_7981_(p_20614_);
    }

    public static Optional<EntityType<?>> m_20632_(String p_20633_) {
        return Registry.f_122826_.m_6612_(ResourceLocation.m_135820_(p_20633_));
    }

    public EntityType(EntityFactory<T> p_20574_, MobCategory p_20575_, boolean p_20576_, boolean p_20577_, boolean p_20578_, boolean p_20579_, ImmutableSet<Block> p_20580_, EntityDimensions p_20581_, int p_20582_, int p_20583_) {
        this.f_20535_ = p_20574_;
        this.f_20536_ = p_20575_;
        this.f_20541_ = p_20579_;
        this.f_20538_ = p_20576_;
        this.f_20539_ = p_20577_;
        this.f_20540_ = p_20578_;
        this.f_20537_ = p_20580_;
        this.f_20547_ = p_20581_;
        this.f_20542_ = p_20582_;
        this.f_20543_ = p_20583_;
    }

    @Nullable
    public Entity m_20592_(ServerLevel p_20593_, @Nullable ItemStack p_20594_, @Nullable Player p_20595_, BlockPos p_20596_, MobSpawnType p_20597_, boolean p_20598_, boolean p_20599_) {
        return this.m_20600_(p_20593_, p_20594_ == null ? null : p_20594_.m_41783_(), p_20594_ != null && p_20594_.m_41788_() ? p_20594_.m_41786_() : null, p_20595_, p_20596_, p_20597_, p_20598_, p_20599_);
    }

    @Nullable
    public T m_20600_(ServerLevel p_20601_, @Nullable CompoundTag p_20602_, @Nullable Component p_20603_, @Nullable Player p_20604_, BlockPos p_20605_, MobSpawnType p_20606_, boolean p_20607_, boolean p_20608_) {
        T $$8 = this.m_20655_(p_20601_, p_20602_, p_20603_, p_20604_, p_20605_, p_20606_, p_20607_, p_20608_);
        if ($$8 != null) {
            p_20601_.m_47205_((Entity)$$8);
        }
        return $$8;
    }

    @Nullable
    public T m_20655_(ServerLevel p_20656_, @Nullable CompoundTag p_20657_, @Nullable Component p_20658_, @Nullable Player p_20659_, BlockPos p_20660_, MobSpawnType p_20661_, boolean p_20662_, boolean p_20663_) {
        double $$10;
        T $$8 = this.m_20615_(p_20656_);
        if ($$8 == null) {
            return null;
        }
        if (p_20662_) {
            ((Entity)$$8).m_6034_((double)p_20660_.m_123341_() + 0.5, p_20660_.m_123342_() + 1, (double)p_20660_.m_123343_() + 0.5);
            double $$9 = EntityType.m_20625_(p_20656_, p_20660_, p_20663_, ((Entity)$$8).m_20191_());
        } else {
            $$10 = 0.0;
        }
        ((Entity)$$8).m_7678_((double)p_20660_.m_123341_() + 0.5, (double)p_20660_.m_123342_() + $$10, (double)p_20660_.m_123343_() + 0.5, Mth.m_14177_(p_20656_.f_46441_.m_188501_() * 360.0f), 0.0f);
        if ($$8 instanceof Mob) {
            Mob $$11 = (Mob)$$8;
            $$11.f_20885_ = $$11.m_146908_();
            $$11.f_20883_ = $$11.m_146908_();
            $$11.m_6518_(p_20656_, p_20656_.m_6436_($$11.m_20183_()), p_20661_, null, p_20657_);
            $$11.m_8032_();
        }
        if (p_20658_ != null && $$8 instanceof LivingEntity) {
            ((Entity)$$8).m_6593_(p_20658_);
        }
        EntityType.m_20620_(p_20656_, p_20659_, $$8, p_20657_);
        return $$8;
    }

    protected static double m_20625_(LevelReader p_20626_, BlockPos p_20627_, boolean p_20628_, AABB p_20629_) {
        AABB $$4 = new AABB(p_20627_);
        if (p_20628_) {
            $$4 = $$4.m_82363_(0.0, -1.0, 0.0);
        }
        Iterable<VoxelShape> $$5 = p_20626_.m_186431_(null, $$4);
        return 1.0 + Shapes.m_193135_(Direction.Axis.Y, p_20629_, $$5, p_20628_ ? -2.0 : -1.0);
    }

    public static void m_20620_(Level p_20621_, @Nullable Player p_20622_, @Nullable Entity p_20623_, @Nullable CompoundTag p_20624_) {
        if (p_20624_ == null || !p_20624_.m_128425_(f_147037_, 10)) {
            return;
        }
        MinecraftServer $$4 = p_20621_.m_7654_();
        if ($$4 == null || p_20623_ == null) {
            return;
        }
        if (!(p_20621_.f_46443_ || !p_20623_.m_6127_() || p_20622_ != null && $$4.m_6846_().m_11303_(p_20622_.m_36316_()))) {
            return;
        }
        CompoundTag $$5 = p_20623_.m_20240_(new CompoundTag());
        UUID $$6 = p_20623_.m_20148_();
        $$5.m_128391_(p_20624_.m_128469_(f_147037_));
        p_20623_.m_20084_($$6);
        p_20623_.m_20258_($$5);
    }

    public boolean m_20584_() {
        return this.f_20538_;
    }

    public boolean m_20654_() {
        return this.f_20539_;
    }

    public boolean m_20672_() {
        return this.f_20540_;
    }

    public boolean m_20673_() {
        return this.f_20541_;
    }

    public MobCategory m_20674_() {
        return this.f_20536_;
    }

    public String m_20675_() {
        if (this.f_20544_ == null) {
            this.f_20544_ = Util.m_137492_("entity", Registry.f_122826_.m_7981_(this));
        }
        return this.f_20544_;
    }

    public Component m_20676_() {
        if (this.f_20545_ == null) {
            this.f_20545_ = Component.m_237115_(this.m_20675_());
        }
        return this.f_20545_;
    }

    public String toString() {
        return this.m_20675_();
    }

    public String m_147048_() {
        int $$0 = this.m_20675_().lastIndexOf(46);
        return $$0 == -1 ? this.m_20675_() : this.m_20675_().substring($$0 + 1);
    }

    public ResourceLocation m_20677_() {
        if (this.f_20546_ == null) {
            ResourceLocation $$0 = Registry.f_122826_.m_7981_(this);
            this.f_20546_ = new ResourceLocation($$0.m_135827_(), "entities/" + $$0.m_135815_());
        }
        return this.f_20546_;
    }

    public float m_20678_() {
        return this.f_20547_.f_20377_;
    }

    public float m_20679_() {
        return this.f_20547_.f_20378_;
    }

    @Nullable
    public T m_20615_(Level p_20616_) {
        return this.f_20535_.m_20721_(this, p_20616_);
    }

    public static Optional<Entity> m_20642_(CompoundTag p_20643_, Level p_20644_) {
        return Util.m_137521_(EntityType.m_20637_(p_20643_).map(p_185998_ -> p_185998_.m_20615_(p_20644_)), p_185990_ -> p_185990_.m_20258_(p_20643_), () -> f_20534_.warn("Skipping Entity with id {}", (Object)p_20643_.m_128461_("id")));
    }

    public AABB m_20585_(double p_20586_, double p_20587_, double p_20588_) {
        float $$3 = this.m_20678_() / 2.0f;
        return new AABB(p_20586_ - (double)$$3, p_20587_, p_20588_ - (double)$$3, p_20586_ + (double)$$3, p_20587_ + (double)this.m_20679_(), p_20588_ + (double)$$3);
    }

    public boolean m_20630_(BlockState p_20631_) {
        if (this.f_20537_.contains((Object)p_20631_.m_60734_())) {
            return false;
        }
        if (!this.f_20540_ && WalkNodeEvaluator.m_77622_(p_20631_)) {
            return true;
        }
        return p_20631_.m_60713_(Blocks.f_50070_) || p_20631_.m_60713_(Blocks.f_50685_) || p_20631_.m_60713_(Blocks.f_50128_) || p_20631_.m_60713_(Blocks.f_152499_);
    }

    public EntityDimensions m_20680_() {
        return this.f_20547_;
    }

    public static Optional<EntityType<?>> m_20637_(CompoundTag p_20638_) {
        return Registry.f_122826_.m_6612_(new ResourceLocation(p_20638_.m_128461_("id")));
    }

    @Nullable
    public static Entity m_20645_(CompoundTag p_20646_, Level p_20647_, Function<Entity, Entity> p_20648_) {
        return EntityType.m_20669_(p_20646_, p_20647_).map(p_20648_).map(p_185995_ -> {
            if (p_20646_.m_128425_("Passengers", 9)) {
                ListTag $$4 = p_20646_.m_128437_("Passengers", 10);
                for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
                    Entity $$6 = EntityType.m_20645_($$4.m_128728_($$5), p_20647_, p_20648_);
                    if ($$6 == null) continue;
                    $$6.m_7998_((Entity)p_185995_, true);
                }
            }
            return p_185995_;
        }).orElse(null);
    }

    public static Stream<Entity> m_147045_(final List<? extends Tag> p_147046_, final Level p_147047_) {
        final Spliterator<? extends Tag> $$2 = p_147046_.spliterator();
        return StreamSupport.stream(new Spliterator<Entity>(){

            @Override
            public boolean tryAdvance(Consumer<? super Entity> p_147066_) {
                return $$2.tryAdvance((? super T p_147059_) -> EntityType.m_20645_((CompoundTag)p_147059_, p_147047_, p_147062_ -> {
                    p_147066_.accept((Entity)p_147062_);
                    return p_147062_;
                }));
            }

            @Override
            public Spliterator<Entity> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return p_147046_.size();
            }

            @Override
            public int characteristics() {
                return 1297;
            }
        }, false);
    }

    private static Optional<Entity> m_20669_(CompoundTag p_20670_, Level p_20671_) {
        try {
            return EntityType.m_20642_(p_20670_, p_20671_);
        }
        catch (RuntimeException $$2) {
            f_20534_.warn("Exception loading entity: ", (Throwable)$$2);
            return Optional.empty();
        }
    }

    public int m_20681_() {
        return this.f_20542_;
    }

    public int m_20682_() {
        return this.f_20543_;
    }

    public boolean m_20683_() {
        return this != f_20532_ && this != f_20467_ && this != f_20496_ && this != f_20549_ && this != f_20462_ && this != f_147033_ && this != f_20464_ && this != f_20506_ && this != f_20564_ && this != f_20569_;
    }

    public boolean m_204039_(TagKey<EntityType<?>> p_204040_) {
        return this.f_204038_.m_203656_(p_204040_);
    }

    @Override
    @Nullable
    public T m_141992_(Entity p_147042_) {
        return (T)(p_147042_.m_6095_() == this ? p_147042_ : null);
    }

    @Override
    public Class<? extends Entity> m_142225_() {
        return Entity.class;
    }

    @Deprecated
    public Holder.Reference<EntityType<?>> m_204041_() {
        return this.f_204038_;
    }

    public static class Builder<T extends Entity> {
        private final EntityFactory<T> f_20685_;
        private final MobCategory f_20686_;
        private ImmutableSet<Block> f_20687_ = ImmutableSet.of();
        private boolean f_20688_ = true;
        private boolean f_20689_ = true;
        private boolean f_20690_;
        private boolean f_20691_;
        private int f_20692_ = 5;
        private int f_20693_ = 3;
        private EntityDimensions f_20694_ = EntityDimensions.m_20395_(0.6f, 1.8f);

        private Builder(EntityFactory<T> p_20696_, MobCategory p_20697_) {
            this.f_20685_ = p_20696_;
            this.f_20686_ = p_20697_;
            this.f_20691_ = p_20697_ == MobCategory.CREATURE || p_20697_ == MobCategory.MISC;
        }

        public static <T extends Entity> Builder<T> m_20704_(EntityFactory<T> p_20705_, MobCategory p_20706_) {
            return new Builder<T>(p_20705_, p_20706_);
        }

        public static <T extends Entity> Builder<T> m_20710_(MobCategory p_20711_) {
            return new Builder<Entity>((p_20708_, p_20709_) -> null, p_20711_);
        }

        public Builder<T> m_20699_(float p_20700_, float p_20701_) {
            this.f_20694_ = EntityDimensions.m_20395_(p_20700_, p_20701_);
            return this;
        }

        public Builder<T> m_20698_() {
            this.f_20689_ = false;
            return this;
        }

        public Builder<T> m_20716_() {
            this.f_20688_ = false;
            return this;
        }

        public Builder<T> m_20719_() {
            this.f_20690_ = true;
            return this;
        }

        public Builder<T> m_20714_(Block ... p_20715_) {
            this.f_20687_ = ImmutableSet.copyOf((Object[])p_20715_);
            return this;
        }

        public Builder<T> m_20720_() {
            this.f_20691_ = true;
            return this;
        }

        public Builder<T> m_20702_(int p_20703_) {
            this.f_20692_ = p_20703_;
            return this;
        }

        public Builder<T> m_20717_(int p_20718_) {
            this.f_20693_ = p_20718_;
            return this;
        }

        public EntityType<T> m_20712_(String p_20713_) {
            if (this.f_20688_) {
                Util.m_137456_(References.f_16785_, p_20713_);
            }
            return new EntityType<T>(this.f_20685_, this.f_20686_, this.f_20688_, this.f_20689_, this.f_20690_, this.f_20691_, this.f_20687_, this.f_20694_, this.f_20692_, this.f_20693_);
        }
    }

    public static interface EntityFactory<T extends Entity> {
        public T m_20721_(EntityType<T> var1, Level var2);
    }
}

