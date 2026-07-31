/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.EntityDamageSource
 *  net.minecraft.world.damagesource.IndirectEntityDamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.boss.EnderDragonPart
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.entity.monster.EnderMan
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.item.crafting.SmeltingRecipe
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Explosion$BlockInteraction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.Tags$Blocks
 *  net.minecraftforge.common.util.FakePlayer
 *  net.minecraftforge.common.util.FakePlayerFactory
 *  net.minecraftforge.event.level.BlockEvent$BreakEvent
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.network.NetworkHooks
 */
package ic2.core.entity.misc;

import ic2.api.events.LaserEvent;
import ic2.core.IC2;
import ic2.core.entity.explosion.IC2Explosion;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.network.NetworkHooks;

public class MiningLaserEntity
extends Entity {
    LivingEntity shooter;
    public float range = 0.0f;
    public float power = 0.0f;
    public int blockBreaks = 0;
    public boolean explosive = false;
    public boolean smelt = false;
    public int ticksInAir;
    Predicate<Entity> predicate = T -> T.m_6087_() && (T != this.shooter || this.ticksInAir >= 5);

    public MiningLaserEntity(EntityType<?> type, Level worldIn) {
        super(type, worldIn);
    }

    public MiningLaserEntity(EntityType<?> type, Level world, LivingEntity entityLiving, float range, float power, int blockBreaks, boolean explosive) {
        this(type, world, entityLiving, range, power, blockBreaks, explosive, entityLiving.m_146908_(), entityLiving.m_146909_());
    }

    public MiningLaserEntity(EntityType<?> type, Level world, LivingEntity entityLiving, float range, float power, int blockBreaks, boolean explosive, boolean isSmelt) {
        this(type, world, entityLiving, range, power, blockBreaks, explosive, entityLiving.m_146908_(), entityLiving.m_146909_());
        this.smelt = isSmelt;
    }

    public MiningLaserEntity(EntityType<?> type, Level world, LivingEntity entityLiving, float range, float power, int blockBreaks, boolean explosive, double yawDeg, double pitchDeg) {
        this(type, world, entityLiving, range, power, blockBreaks, explosive, yawDeg, pitchDeg, entityLiving.m_20186_() + (double)entityLiving.m_20192_() - 0.1);
    }

    public MiningLaserEntity(EntityType<?> type, Level world, LivingEntity shooter, float flyRange, float laserPower, int blockBreakLimit, boolean isExplosive, double yawDeg, double pitchDeg, double y) {
        this(type, world);
        this.shooter = shooter;
        double yaw = Math.toRadians(yawDeg);
        double pitch = Math.toRadians(pitchDeg);
        this.m_6034_(shooter.m_20185_() - Math.cos(yaw) * 0.16, y, shooter.m_20189_() - Math.sin(yaw) * 0.16);
        this.setLaserHeading(-Math.sin(yaw) * Math.cos(pitch), -Math.sin(pitch), Math.cos(yaw) * Math.cos(pitch), 1.0);
        this.range = flyRange;
        this.power = laserPower;
        this.blockBreaks = blockBreakLimit;
        this.explosive = isExplosive;
    }

    public void setLaserHeading(Vec3 motion, double speed) {
        this.setLaserHeading(motion.m_7096_(), motion.m_7098_(), motion.m_7094_(), speed);
    }

    public void setLaserHeading(double newMotionX, double newMotionY, double newMotionZ, double speed) {
        double newSpeed = 1.0 / Math.sqrt(newMotionX * newMotionX + newMotionY * newMotionY + newMotionZ * newMotionZ) * speed;
        this.m_20334_(newMotionX * newSpeed, newMotionY * newSpeed, newMotionZ * newSpeed);
        Vec3 motion = this.m_20184_();
        this.f_19859_ = (float)Math.toDegrees(Math.atan2(motion.m_7096_(), motion.m_7094_()));
        this.m_146922_(this.f_19859_);
        this.f_19860_ = (float)Math.toDegrees(Math.atan2(motion.m_7098_(), Math.sqrt(motion.m_7096_() * motion.m_7096_() + motion.m_7094_() * motion.m_7094_())));
        this.m_146926_(this.f_19860_);
    }

    protected void m_8097_() {
    }

    public void m_8119_() {
        super.m_8119_();
        boolean simulating = IC2.PLATFORM.isSimulating();
        if (simulating && (this.range < 1.0f || this.power <= 0.0f || this.blockBreaks <= 0 || this.shooter == null)) {
            if (this.explosive) {
                this.explode();
            }
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            return;
        }
        ++this.ticksInAir;
        if (simulating) {
            Vec3 oldPos;
            BlockHitResult result = this.f_19853_.m_45547_(new ClipContext(oldPos = new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_()), oldPos.m_82549_(this.m_20184_()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this));
            Vec3 newPos = result.m_6662_() == HitResult.Type.MISS ? oldPos.m_82549_(this.m_20184_()) : result.m_82450_();
            EntityHitResult entityResult = ProjectileUtil.m_37304_((Level)this.f_19853_, (Entity)this, (Vec3)oldPos, (Vec3)newPos, (AABB)this.m_20191_().m_82369_(this.m_20184_()), this.predicate);
            if (entityResult != null) {
                result = entityResult;
            }
            if (result.m_6662_() != HitResult.Type.MISS) {
                if (this.explosive) {
                    if (this.canHitPlayer((HitResult)result)) {
                        this.explode();
                    }
                    this.m_142687_(Entity.RemovalReason.DISCARDED);
                    return;
                }
                switch (result.m_6662_()) {
                    case ENTITY: {
                        LaserEvent.LaserEntityHitEvent entityHitEvent = new LaserEvent.LaserEntityHitEvent((LevelAccessor)this.f_19853_, this, this.shooter, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, ((EntityHitResult)result).m_82443_());
                        MinecraftForge.EVENT_BUS.post((Event)entityHitEvent);
                        if (!this.loadEventData(entityHitEvent)) break;
                        int damage = (int)this.power;
                        if (damage > 0 && this.canHitPlayer((HitResult)result)) {
                            EnderDragon dragon;
                            Entity target = entityHitEvent.hitEntity;
                            if (target != null) {
                                target.m_20254_(damage * (this.smelt ? 2 : 1));
                            }
                            if (target instanceof EnderMan) {
                                target.m_6469_(new EntityDamageSource("arrow", (Entity)this).m_19366_(), (float)damage);
                            } else if (target.m_6469_(new IndirectEntityDamageSource("arrow", (Entity)this, (Entity)this.shooter).m_19366_(), (float)damage) && this.shooter instanceof Player && (dragon = this.getDragon(target)) != null && dragon.m_21223_() <= 1.0f) {
                                ((Player)this.shooter).m_36220_(IC2Stats.DRAGONS_SHOT);
                                IC2Advancements.ID_TRIGGER.onTrigger((Player)this.shooter, IC2Stats.DRAGONS_SHOT);
                            }
                            if (!target.m_6084_() && this.shooter instanceof Player) {
                                ((Player)this.shooter).m_36220_(IC2Stats.PLAYERS_SHOT);
                            }
                        }
                        this.m_142687_(Entity.RemovalReason.DISCARDED);
                        break;
                    }
                    case BLOCK: {
                        BlockPos pos;
                        BlockState state;
                        LaserEvent.LaserBlockHitEvent blockHitEvent = new LaserEvent.LaserBlockHitEvent((LevelAccessor)this.f_19853_, this, this.shooter, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, result, true, true, 0.9f);
                        MinecraftForge.EVENT_BUS.post((Event)blockHitEvent);
                        if (!this.loadEventData(blockHitEvent) || (state = this.f_19853_.m_8055_(pos = blockHitEvent.pos)).m_60795_() || state.m_60734_().m_5456_() == Items.f_41852_ || state.m_204336_(Tags.Blocks.GLASS) || state.m_204336_(Tags.Blocks.GLASS_PANES)) break;
                        if (this.canMine(pos, state)) {
                            this.power -= 0.15f * state.getExplosionResistance((BlockGetter)this.f_19853_, pos, new Explosion(this.f_19853_, (Entity)this, DamageSource.f_19318_, null, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, blockHitEvent.power, false, Explosion.BlockInteraction.BREAK));
                            if (!(this.power >= 0.0f)) break;
                            if (state.m_60767_() == Material.f_76273_) {
                                state.onBlockExploded(this.f_19853_, pos, new Explosion(this.f_19853_, (Entity)this, DamageSource.f_19318_, null, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 1.0f, false, Explosion.BlockInteraction.BREAK));
                            } else if (this.smelt) {
                                if (state.m_60767_() == Material.f_76320_) {
                                    blockHitEvent.dropBlock = false;
                                }
                                LootContext.Builder builder = new LootContext.Builder((ServerLevel)this.f_19853_);
                                builder.m_230911_(this.f_19853_.f_46441_).m_78972_(LootContextParams.f_81460_, (Object)Vec3.m_82512_((Vec3i)pos));
                                builder.m_78972_(LootContextParams.f_81463_, (Object)ItemStack.f_41583_).m_78984_(LootContextParams.f_81462_, (Object)this.f_19853_.m_7702_(pos));
                                List resultDrops = state.m_60724_(builder);
                                SimpleContainer inv = new SimpleContainer(1);
                                int i = 0;
                                for (int m = resultDrops.size(); i < m; --m, ++i) {
                                    ItemStack stack = (ItemStack)resultDrops.get(i);
                                    inv.m_6836_(0, StackUtil.copyWithSize(stack, 1));
                                    Optional recipe = IC2.PLATFORM.getRecipes().m_44015_(RecipeType.f_44108_, (Container)inv, this.f_19853_);
                                    if (recipe.isPresent()) {
                                        ItemStack resultStack = ((SmeltingRecipe)recipe.get()).m_5874_((Container)inv);
                                        Block resultBlock = Block.m_49814_((Item)resultStack.m_41720_());
                                        if (resultBlock != Blocks.f_50016_ && resultBlock != state.m_60734_()) {
                                            blockHitEvent.removeBlock = false;
                                            this.f_19853_.m_46597_(pos, resultBlock.m_49966_());
                                        } else {
                                            Block.m_49840_((Level)this.f_19853_, (BlockPos)pos, (ItemStack)StackUtil.copyWithSize(resultStack, resultStack.m_41613_() * stack.m_41613_()));
                                        }
                                        blockHitEvent.dropBlock = false;
                                        this.power = 0.0f;
                                    }
                                    resultDrops.remove(i--);
                                }
                            }
                            if (blockHitEvent.removeBlock) {
                                if (blockHitEvent.dropBlock) {
                                    Block.m_49892_((BlockState)state, (LevelAccessor)this.f_19853_, (BlockPos)pos, (BlockEntity)this.f_19853_.m_7702_(pos));
                                }
                                this.f_19853_.m_7471_(pos, false);
                                if (this.shooter instanceof Player) {
                                    // empty if block
                                }
                                if (this.f_19853_.f_46441_.m_188503_(10) == 0 && state.m_60767_().m_76335_()) {
                                    this.f_19853_.m_46597_(pos, Blocks.f_50083_.m_49966_());
                                }
                            }
                            --this.blockBreaks;
                            break;
                        }
                        this.m_142687_(Entity.RemovalReason.DISCARDED);
                        break;
                    }
                    default: {
                        this.power -= 0.5f;
                    }
                }
            }
        }
        Vec3 motion = this.m_20184_();
        this.m_6034_(this.m_20185_() + motion.m_7096_(), this.m_20186_() + motion.m_7098_(), this.m_20189_() + motion.m_7094_());
        this.range -= 0.02f;
        if (this.m_20069_()) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
        }
    }

    protected void explode() {
        if (IC2.PLATFORM.isSimulating()) {
            LaserEvent.LaserExplodeEvent event = new LaserEvent.LaserExplodeEvent((LevelAccessor)this.f_19853_, this, this.shooter, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, 5.0f, 0.85f);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (this.loadEventData(event)) {
                new IC2Explosion(this.f_19853_, this, this.m_20182_(), event.explosionPower, event.explosionDropRate, DamageSource.f_19318_).setIgniter(this.shooter instanceof Player ? (Player)this.shooter : null).doExplosion();
            }
        }
    }

    public boolean canHitPlayer(HitResult result) {
        if (this.shooter instanceof Player && result instanceof EntityHitResult) {
            Entity hit = ((EntityHitResult)result).m_82443_();
            return !(hit instanceof Player) || ((Player)this.shooter).m_7099_((Player)hit);
        }
        return true;
    }

    public boolean canMine(BlockPos pos, BlockState state) {
        return !MinecraftForge.EVENT_BUS.post((Event)new BlockEvent.BreakEvent(this.f_19853_, pos, state, (Player)(this.shooter instanceof Player ? (Player)this.shooter : FakePlayerFactory.getMinecraft((ServerLevel)((ServerLevel)this.f_19853_))))) && !state.m_204336_(IC2Tags.MINING_LASER_UNBREAKABLE);
    }

    public EnderDragon getDragon(Entity entity) {
        if (entity instanceof EnderDragon) {
            return (EnderDragon)entity;
        }
        if (entity instanceof EnderDragonPart) {
            return ((EnderDragonPart)entity).f_31010_;
        }
        return null;
    }

    public boolean loadEventData(LaserEvent event) {
        this.shooter = event.source;
        this.range = event.range;
        this.power = event.power;
        this.blockBreaks = event.blockBreaks;
        this.explosive = event.explosive;
        this.smelt = event.smelt;
        if (event.isCanceled()) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            return false;
        }
        return true;
    }

    protected void m_7378_(CompoundTag compound) {
        if (!compound.m_128403_("source")) {
            return;
        }
        if (this.f_19853_ instanceof ServerLevel) {
            FakePlayer player;
            UUID id = compound.m_128342_("source");
            this.shooter = (LivingEntity)((ServerLevel)this.f_19853_).m_8791_(id);
            if (this.shooter == null && (player = FakePlayerFactory.getMinecraft((ServerLevel)((ServerLevel)this.f_19853_))).m_20148_().equals(id)) {
                this.shooter = player;
            }
        } else {
            this.shooter = null;
        }
        this.range = compound.m_128457_("range");
        this.power = compound.m_128457_("power");
        this.blockBreaks = compound.m_128451_("broken");
        this.ticksInAir = compound.m_128451_("in_air");
        this.explosive = compound.m_128471_("explosive");
        this.smelt = compound.m_128471_("smelt");
    }

    protected void m_7380_(CompoundTag compound) {
        if (this.shooter == null) {
            return;
        }
        compound.m_128362_("source", this.shooter.m_20148_());
        compound.m_128350_("range", this.range);
        compound.m_128350_("power", this.power);
        compound.m_128405_("broken", this.blockBreaks);
        compound.m_128405_("in_air", this.ticksInAir);
        compound.m_128379_("explosive", this.explosive);
        compound.m_128379_("smelt", this.smelt);
    }

    public Packet<?> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
}

