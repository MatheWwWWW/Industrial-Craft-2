/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2LongMap
 *  it.unimi.dsi.fastutil.longs.Long2LongMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2LongMaps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.boss.wither.WitherBoss
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Explosion$BlockInteraction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.ForgeEventFactory
 *  net.minecraftforge.event.level.ExplosionEvent$Detonate
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.network.PacketDistributor$TargetPoint
 */
package ic2.core.entity.explosion;

import com.mojang.authlib.GameProfile;
import ic2.api.blocks.ExplosionWhitelist;
import ic2.api.util.IC2DamageSource;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.providers.SimplePosition;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.ExplosionPacket;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Potions;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.network.PacketDistributor;

public class IC2Explosion {
    public static final ResourceLocation NUKE_EXPLOSION = new ResourceLocation("ic2", "sounds/misc/nuke.ogg");
    public static final ResourceLocation ELECTRIC_EXPLOSION = new ResourceLocation("ic2", "sounds/misc/overload.ogg");
    public static final double PI_2 = Math.PI * 2;
    public static final Predicate<LivingEntity> EXPLOSION = T -> {
        Player player;
        if (T instanceof Player && ((player = (Player)T).m_5833_() || player.m_150110_().f_35934_)) {
            return true;
        }
        return T.m_6084_();
    };
    RandomSource rand;
    Level world;
    Vec3 origin;
    Entity exploder;
    DamageSource source;
    GameProfile igniter;
    float power;
    float dropRate;
    Long2LongMap destroyedBlocks = new Long2LongLinkedOpenHashMap();
    List<Entity> affectedEntities = CollectionUtils.createList();
    Int2ObjectMap<Vec3> motions = new Int2ObjectLinkedOpenHashMap();
    public AABB box;
    private Explosion fakeExplosion;
    public boolean isBeacon;

    public IC2Explosion(Level world, Entity exploder, Vec3 origin, float power, float dropRate, DamageSource source) {
        this.world = world;
        this.rand = world.f_46441_;
        this.exploder = exploder;
        this.origin = origin;
        this.source = source;
        this.power = power;
        this.dropRate = dropRate;
        this.fakeExplosion = new Explosion(world, exploder, source, null, origin.f_82479_, origin.f_82480_, origin.f_82481_, power / 0.4f, false, Explosion.BlockInteraction.DESTROY);
        if (this.source == null) {
            this.source = DamageSource.m_19358_((Explosion)this.fakeExplosion);
        }
    }

    public IC2Explosion setIgniter(Player player) {
        return player == null ? this : this.setIgniter(player.m_36316_());
    }

    public IC2Explosion setIgniter(GameProfile profile) {
        this.igniter = profile;
        return this;
    }

    public void doExplosion() {
        if (this.power <= 0.0f || ForgeEventFactory.onExplosionStart((Level)this.world, (Explosion)this.fakeExplosion)) {
            return;
        }
        double max = (double)this.power / 0.4;
        this.box = new AABB(new BlockPos(this.origin)).m_82400_(max);
        int steps = (int)Math.ceil(Math.PI / Math.atan(1.0 / max));
        this.affectedEntities.addAll(this.world.m_6443_(LivingEntity.class, this.box, EXPLOSION));
        if (this.exploder != null) {
            this.affectedEntities.remove(this.exploder);
        }
        MinecraftForge.EVENT_BUS.post((Event)new ExplosionEvent.Detonate(this.world, this.fakeExplosion, this.affectedEntities));
        int m = steps * 2;
        for (int i = 0; i < m; ++i) {
            double phi = Math.PI * 2 / (double)steps * (double)i;
            for (int y = 0; y < steps; ++y) {
                double theta = Math.PI / (double)steps * (double)y;
                this.shootRay(this.origin.f_82479_, this.origin.f_82480_, this.origin.f_82481_, phi, theta, this.power, i % 8 == 0 && y % 8 == 0);
            }
        }
        if (this.source == IC2DamageSource.NUKE) {
            for (LivingEntity entity : this.world.m_45976_(LivingEntity.class, new AABB(this.origin.f_82479_ - 100.0, this.origin.f_82480_ - 100.0, this.origin.f_82481_ - 100.0, this.origin.f_82479_ + 100.0, this.origin.f_82480_ + 100.0, this.origin.f_82481_ + 100.0))) {
                if (entity instanceof Player && this.isBeacon) {
                    entity.m_6469_((DamageSource)IC2DamageSource.NUKE, 1000.0f);
                }
                double distance = Math.sqrt(entity.m_20238_(this.origin));
                int hungerLength = (int)(120.0 * (100.0 - distance));
                int poisonLength = (int)(80.0 * (30.0 - distance));
                if (hungerLength >= 0) {
                    entity.m_7292_(new MobEffectInstance(MobEffects.f_19612_, hungerLength, 0));
                }
                if (poisonLength < 0) continue;
                entity.m_7292_(new MobEffectInstance(IC2Potions.RADIATION, poisonLength, 0));
            }
            for (Entity entry : this.affectedEntities) {
                if (!(entry instanceof Player)) continue;
                ((Player)entry).m_36220_(IC2Stats.NUKES_SURVIVED);
            }
        }
        if (this.source == IC2DamageSource.NUKE && this.exploder != null) {
            IC2.AUDIO.playSound(this.exploder, NUKE_EXPLOSION, AudioManager.SoundType.STATIC, 4.0f, 1.0f);
        } else if (this.source == IC2DamageSource.ELECTRICITY) {
            IC2.AUDIO.playSound(new SimplePosition(this.world, this.origin), ELECTRIC_EXPLOSION, AudioManager.SoundType.STATIC, 12.0f, 1.0f);
        } else {
            this.world.m_6263_(null, this.origin.f_82479_, this.origin.f_82480_, this.origin.f_82481_, SoundEvents.f_11913_, SoundSource.BLOCKS, 4.0f, (1.0f + (this.world.f_46441_.m_188501_() - this.world.f_46441_.m_188501_()) * 0.2f) * 0.7f);
        }
        List checkList = this.fakeExplosion.m_46081_();
        MinecraftForge.EVENT_BUS.post((Event)new ExplosionEvent.Detonate(this.world, this.fakeExplosion, CollectionUtils.createList()));
        LongOpenHashSet toKeep = new LongOpenHashSet(checkList.size());
        int i = 0;
        int m2 = checkList.size();
        while (i < m2) {
            toKeep.add(((BlockPos)checkList.get(i++)).m_121878_());
        }
        ObjectList spawn = CollectionUtils.createList();
        Long2ObjectLinkedOpenHashMap drops = new Long2ObjectLinkedOpenHashMap();
        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();
        for (Long2LongMap.Entry entry : Long2LongMaps.fastIterable((Long2LongMap)this.destroyedBlocks)) {
            long posLong = entry.getLongKey();
            if (!toKeep.contains(posLong)) continue;
            BlockPos pos = position.m_122188_(posLong).m_7949_();
            long value = entry.getLongValue();
            BlockState state = Block.m_49803_((int)MathUtils.getIntKey(value));
            if (MathUtils.getIntValue(value) > 0 && state.canDropFromExplosion((BlockGetter)this.world, pos, this.fakeExplosion)) {
                spawn.add((ExplosionPacket.ParticleSpawn)new ExplosionPacket.ParticleSpawn(entry.getLongKey(), this.power));
                if (this.world instanceof ServerLevel && this.rand.m_188501_() <= this.dropRate) {
                    LootContext.Builder builder = new LootContext.Builder((ServerLevel)this.world);
                    builder.m_230911_(this.world.f_46441_).m_78972_(LootContextParams.f_81460_, (Object)Vec3.m_82512_((Vec3i)pos));
                    builder.m_78972_(LootContextParams.f_81463_, (Object)ItemStack.f_41583_).m_78984_(LootContextParams.f_81462_, (Object)this.world.m_7702_(pos));
                    List resultDrops = state.m_60724_(builder);
                    if (resultDrops.size() > 0) {
                        drops.put(entry.getLongKey(), (Object)resultDrops);
                    }
                }
            }
            state.onBlockExploded(this.world, pos, this.fakeExplosion);
        }
        for (Int2ObjectMap.Entry motion : this.motions.int2ObjectEntrySet()) {
            motion.setValue((Object)MathUtils.clamp((Vec3)motion.getValue(), -500.0, 500.0));
        }
        PacketManager.INSTANCE.sendToNearby(new PacketDistributor.TargetPoint(this.origin.f_82479_, this.origin.f_82480_, this.origin.f_82481_, 64.0, this.world.m_46472_()), new ExplosionPacket((List<ExplosionPacket.ParticleSpawn>)spawn, this.motions, this.origin));
        for (Long2LongMap.Entry entry : drops.long2ObjectEntrySet()) {
            position.m_122188_(entry.getLongKey());
            for (ItemStack item : (List)entry.getValue()) {
                if (item.m_41619_()) continue;
                Block.m_49840_((Level)this.world, (BlockPos)position, (ItemStack)item);
            }
        }
    }

    protected void shootRay(double x, double y, double z, double phi, double theta, double power, boolean killEntities) {
        Vec3 vec;
        double deltaX = Math.sin(theta) * Math.cos(phi);
        double deltaY = Math.cos(theta);
        double deltaZ = Math.sin(theta) * Math.sin(phi);
        int step = 0;
        while (this.box.m_82390_(vec = new Vec3(x, y, z))) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = this.world.m_8055_(pos);
            boolean isAir = state.m_60795_();
            double absorption = 0.5;
            if (!isAir) {
                double resistance = (double)state.getExplosionResistance((BlockGetter)this.world, pos, this.fakeExplosion) / 5.0;
                if (resistance < 0.0) break;
                absorption = (resistance + 4.0) * 0.3;
            }
            if (absorption > 1000.0 && !ExplosionWhitelist.isWhitelisted(state.m_60734_())) {
                absorption = 0.5;
            } else {
                long position;
                long entry;
                if (absorption > power) break;
                if (!isAir && ((entry = this.destroyedBlocks.get(position = pos.m_121878_())) == 0L || power > 8.0 && MathUtils.getIntValue(entry) > 0)) {
                    this.destroyedBlocks.put(position, MathUtils.putIntoLong(Block.m_49956_((BlockState)state), power <= 8.0 ? 1L : 0L));
                    if (entry == 0L) {
                        this.fakeExplosion.m_46081_().add(pos);
                    }
                }
            }
            if (killEntities && step % 8 == 0 && this.affectedEntities.size() > 0 && power >= 0.25) {
                ObjectList alive = CollectionUtils.createList();
                boolean nuke = this.source == IC2DamageSource.NUKE;
                for (Entity entity : this.affectedEntities) {
                    if (!entity.m_6128_() && entity.m_20182_().m_82554_(vec) <= power / 1.5) {
                        Vec3 entityDistance = entity.m_20182_().m_82546_(this.origin);
                        double distance = Math.sqrt(entity.m_20238_(this.origin));
                        double distanceFactor = power / 2.0 / (Math.pow(distance, 0.8) + 1.0);
                        entity.m_6469_(this.source, (float)Math.pow(distanceFactor * 3.0, 2.0));
                        if (!entity.m_6084_()) {
                            if (this.igniter != null) {
                                Player player;
                                if (nuke && entity instanceof Player && (player = (Player)entity).m_36316_().equals((Object)this.igniter)) {
                                    IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:nuke_death"));
                                }
                                if (entity instanceof WitherBoss && (player = this.world.m_46003_(this.igniter.getId())) != null) {
                                    player.m_36220_(IC2Stats.WITHERS_NUKED);
                                    IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:nuke_wither"));
                                }
                            }
                            this.motions.remove(entity.m_19879_());
                            continue;
                        }
                        alive.add((Entity)entity);
                        Vec3 motion = (Vec3)this.motions.getOrDefault(entity.m_19879_(), (Object)entity.m_20184_());
                        this.motions.put(entity.m_19879_(), (Object)motion.m_82549_(MathUtils.divide(entityDistance, distance).m_82542_(distanceFactor, distanceFactor, distanceFactor)));
                        continue;
                    }
                    alive.add((Entity)entity);
                }
                this.affectedEntities = alive;
            }
            if (absorption > 10.0) {
                for (int i = 0; i < 5; ++i) {
                    this.shootRay(x, y, z, this.rand.m_188500_() * (Math.PI * 2), this.rand.m_188500_() * Math.PI, power - absorption, false);
                }
            }
            power -= absorption;
            x += deltaX;
            z += deltaZ;
            ++step;
            if ((y += deltaY) < (double)this.world.m_151558_() && y > (double)this.world.m_141937_()) continue;
        }
    }
}

