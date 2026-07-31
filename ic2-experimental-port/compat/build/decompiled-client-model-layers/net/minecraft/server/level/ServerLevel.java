/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.longs.LongSets
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockEventData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.storage.EntityStorage;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.saveddata.maps.MapIndex;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.LevelTicks;
import org.slf4j.Logger;

public class ServerLevel
extends Level
implements WorldGenLevel {
    public static final BlockPos f_8562_ = new BlockPos(100, 50, 0);
    private static final int f_184049_ = 12000;
    private static final int f_184039_ = 180000;
    private static final int f_184040_ = 12000;
    private static final int f_184041_ = 24000;
    private static final int f_184042_ = 12000;
    private static final int f_184043_ = 180000;
    private static final int f_184044_ = 3600;
    private static final int f_184045_ = 15600;
    private static final Logger f_8566_ = LogUtils.getLogger();
    private static final int f_143242_ = 300;
    private static final int f_184046_ = 65536;
    final List<ServerPlayer> f_8546_ = Lists.newArrayList();
    private final ServerChunkCache f_8547_;
    private final MinecraftServer f_8548_;
    private final ServerLevelData f_8549_;
    final EntityTickList f_143243_ = new EntityTickList();
    private final PersistentEntitySectionManager<Entity> f_143244_;
    public boolean f_8564_;
    private final SleepStatus f_143245_;
    private int f_8551_;
    private final PortalForcer f_8552_;
    private final LevelTicks<Block> f_8553_ = new LevelTicks(this::m_184110_, this.m_46658_());
    private final LevelTicks<Fluid> f_184047_ = new LevelTicks(this::m_184110_, this.m_46658_());
    final Set<Mob> f_143246_ = new ObjectOpenHashSet();
    volatile boolean f_200893_;
    protected final Raids f_8565_;
    private final ObjectLinkedOpenHashSet<BlockEventData> f_8556_ = new ObjectLinkedOpenHashSet();
    private final List<BlockEventData> f_184048_ = new ArrayList<BlockEventData>(64);
    private List<GameEvent.Message> f_214996_ = new ArrayList<GameEvent.Message>();
    private boolean f_8557_;
    private final List<CustomSpawner> f_8558_;
    @Nullable
    private final EndDragonFight f_8559_;
    final Int2ObjectMap<EnderDragonPart> f_143247_ = new Int2ObjectOpenHashMap();
    private final StructureManager f_214997_;
    private final StructureCheck f_196556_;
    private final boolean f_8561_;

    public ServerLevel(MinecraftServer p_214999_, Executor p_215000_, LevelStorageSource.LevelStorageAccess p_215001_, ServerLevelData p_215002_, ResourceKey<Level> p_215003_, LevelStem p_215004_, ChunkProgressListener p_215005_, boolean p_215006_, long p_215007_, List<CustomSpawner> p_215008_, boolean p_215009_) {
        super(p_215002_, p_215003_, p_215004_.m_204521_(), p_214999_::m_129905_, false, p_215006_, p_215007_, p_214999_.m_213994_());
        this.f_8561_ = p_215009_;
        this.f_8548_ = p_214999_;
        this.f_8558_ = p_215008_;
        this.f_8549_ = p_215002_;
        ChunkGenerator $$11 = p_215004_.m_63990_();
        boolean $$12 = p_214999_.m_6365_();
        DataFixer $$13 = p_214999_.m_129933_();
        EntityStorage $$14 = new EntityStorage(this, p_215001_.m_197394_(p_215003_).resolve("entities"), $$13, $$12, p_214999_);
        this.f_143244_ = new PersistentEntitySectionManager<Entity>(Entity.class, new EntityCallbacks(), $$14);
        this.f_8547_ = new ServerChunkCache(this, p_215001_, $$13, p_214999_.m_236738_(), p_215000_, $$11, p_214999_.m_6846_().m_11312_(), p_214999_.m_6846_().m_184213_(), $$12, p_215005_, this.f_143244_::m_157524_, () -> p_214999_.m_129783_().m_8895_());
        $$11.m_223100_(this.f_8547_.m_214994_());
        this.f_8552_ = new PortalForcer(this);
        this.m_46465_();
        this.m_46466_();
        this.m_6857_().m_61923_(p_214999_.m_6329_());
        this.f_8565_ = this.m_8895_().m_164861_(p_184095_ -> Raids.m_150235_(this, p_184095_), () -> new Raids(this), Raids.m_211596_(this.m_204156_()));
        if (!p_214999_.m_129792_()) {
            p_215002_.m_5458_(p_214999_.m_130008_());
        }
        long $$15 = p_214999_.m_129910_().m_5961_().m_64619_();
        this.f_196556_ = new StructureCheck(this.f_8547_.m_196555_(), this.m_5962_(), p_214999_.m_236738_(), p_215003_, $$11, this.f_8547_.m_214994_(), this, $$11.m_62218_(), $$15, $$13);
        this.f_214997_ = new StructureManager(this, p_214999_.m_129910_().m_5961_(), this.f_196556_);
        this.f_8559_ = this.m_46472_() == Level.f_46430_ && this.m_204156_().m_203565_(BuiltinDimensionTypes.f_223540_) ? new EndDragonFight(this, $$15, p_214999_.m_129910_().m_6564_()) : null;
        this.f_143245_ = new SleepStatus();
    }

    public void m_8606_(int p_8607_, int p_8608_, boolean p_8609_, boolean p_8610_) {
        this.f_8549_.m_6393_(p_8607_);
        this.f_8549_.m_6399_(p_8608_);
        this.f_8549_.m_6398_(p_8608_);
        this.f_8549_.m_5565_(p_8609_);
        this.f_8549_.m_5557_(p_8610_);
    }

    @Override
    public Holder<Biome> m_203675_(int p_203775_, int p_203776_, int p_203777_) {
        return this.m_7726_().m_8481_().m_62218_().m_203407_(p_203775_, p_203776_, p_203777_, this.m_7726_().m_214994_().m_224579_());
    }

    public StructureManager m_215010_() {
        return this.f_214997_;
    }

    public void m_8793_(BooleanSupplier p_8794_) {
        boolean $$5;
        ProfilerFiller $$1 = this.m_46473_();
        this.f_8557_ = true;
        $$1.m_6180_("world border");
        this.m_6857_().m_61969_();
        $$1.m_6182_("weather");
        this.m_184096_();
        int $$2 = this.m_46469_().m_46215_(GameRules.f_151486_);
        if (this.f_143245_.m_144002_($$2) && this.f_143245_.m_144004_($$2, this.f_8546_)) {
            if (this.m_46469_().m_46207_(GameRules.f_46140_)) {
                long $$3 = this.f_46442_.m_6792_() + 24000L;
                this.m_8615_($$3 - $$3 % 24000L);
            }
            this.m_8804_();
            if (this.m_46469_().m_46207_(GameRules.f_46150_) && this.m_46471_()) {
                this.m_184097_();
            }
        }
        this.m_46465_();
        this.m_8809_();
        $$1.m_6182_("tickPending");
        if (!this.m_46659_()) {
            long $$4 = this.m_46467_();
            $$1.m_6180_("blockTicks");
            this.f_8553_.m_193225_($$4, 65536, this::m_184112_);
            $$1.m_6182_("fluidTicks");
            this.f_184047_.m_193225_($$4, 65536, this::m_184076_);
            $$1.m_7238_();
        }
        $$1.m_6182_("raid");
        this.f_8565_.m_37957_();
        $$1.m_6182_("chunkSource");
        this.m_7726_().m_201698_(p_8794_, true);
        $$1.m_6182_("blockEvents");
        this.m_8807_();
        this.f_8557_ = false;
        $$1.m_7238_();
        boolean bl = $$5 = !this.f_8546_.isEmpty() || !this.m_8902_().isEmpty();
        if ($$5) {
            this.m_8886_();
        }
        if ($$5 || this.f_8551_++ < 300) {
            $$1.m_6180_("entities");
            if (this.f_8559_ != null) {
                $$1.m_6180_("dragonFight");
                this.f_8559_.m_64095_();
                $$1.m_7238_();
            }
            this.f_143243_.m_156910_(p_184065_ -> {
                if (p_184065_.m_213877_()) {
                    return;
                }
                if (this.m_143342_((Entity)p_184065_)) {
                    p_184065_.m_146870_();
                    return;
                }
                $$1.m_6180_("checkDespawn");
                p_184065_.m_6043_();
                $$1.m_7238_();
                if (!this.f_8547_.f_8325_.m_143145_().m_183913_(p_184065_.m_146902_().m_45588_())) {
                    return;
                }
                Entity $$2 = p_184065_.m_20202_();
                if ($$2 != null) {
                    if ($$2.m_213877_() || !$$2.m_20363_((Entity)p_184065_)) {
                        p_184065_.m_8127_();
                    } else {
                        return;
                    }
                }
                $$1.m_6180_("tick");
                this.m_46653_(this::m_8647_, p_184065_);
                $$1.m_7238_();
            });
            $$1.m_7238_();
            this.m_46463_();
        }
        $$1.m_6180_("entityManagement");
        this.f_143244_.m_157506_();
        $$1.m_6182_("gameEvents");
        this.m_215075_();
        $$1.m_7238_();
    }

    @Override
    public boolean m_183438_(long p_184059_) {
        return this.f_8547_.f_8325_.m_143145_().m_183916_(p_184059_);
    }

    protected void m_8809_() {
        if (!this.f_8561_) {
            return;
        }
        long $$0 = this.f_46442_.m_6793_() + 1L;
        this.f_8549_.m_6253_($$0);
        this.f_8549_.m_7540_().m_82256_(this.f_8548_, $$0);
        if (this.f_46442_.m_5470_().m_46207_(GameRules.f_46140_)) {
            this.m_8615_(this.f_46442_.m_6792_() + 1L);
        }
    }

    public void m_8615_(long p_8616_) {
        this.f_8549_.m_6247_(p_8616_);
    }

    public void m_8799_(boolean p_8800_, boolean p_8801_) {
        for (CustomSpawner $$2 : this.f_8558_) {
            $$2.m_7995_(this, p_8800_, p_8801_);
        }
    }

    private boolean m_143342_(Entity p_143343_) {
        if (!this.f_8548_.m_6998_() && (p_143343_ instanceof Animal || p_143343_ instanceof WaterAnimal)) {
            return true;
        }
        return !this.f_8548_.m_6997_() && p_143343_ instanceof Npc;
    }

    private void m_8804_() {
        this.f_143245_.m_144001_();
        this.f_8546_.stream().filter(LivingEntity::m_5803_).collect(Collectors.toList()).forEach(p_184116_ -> p_184116_.m_6145_(false, false));
    }

    public void m_8714_(LevelChunk p_8715_, int p_8716_) {
        BlockPos $$7;
        ChunkPos $$2 = p_8715_.m_7697_();
        boolean $$3 = this.m_46471_();
        int $$4 = $$2.m_45604_();
        int $$5 = $$2.m_45605_();
        ProfilerFiller $$6 = this.m_46473_();
        $$6.m_6180_("thunder");
        if ($$3 && this.m_46470_() && this.f_46441_.m_188503_(100000) == 0 && this.m_46758_($$7 = this.m_143288_(this.m_46496_($$4, 0, $$5, 15)))) {
            boolean $$9;
            DifficultyInstance $$8 = this.m_6436_($$7);
            boolean bl = $$9 = this.m_46469_().m_46207_(GameRules.f_46134_) && this.f_46441_.m_188500_() < (double)$$8.m_19056_() * 0.01 && !this.m_8055_($$7.m_7495_()).m_60713_(Blocks.f_152587_);
            if ($$9) {
                SkeletonHorse $$10 = EntityType.f_20525_.m_20615_(this);
                $$10.m_30923_(true);
                $$10.m_146762_(0);
                $$10.m_6034_($$7.m_123341_(), $$7.m_123342_(), $$7.m_123343_());
                this.m_7967_($$10);
            }
            LightningBolt $$11 = EntityType.f_20465_.m_20615_(this);
            $$11.m_20219_(Vec3.m_82539_($$7));
            $$11.m_20874_($$9);
            this.m_7967_($$11);
        }
        $$6.m_6182_("iceandsnow");
        if (this.f_46441_.m_188503_(16) == 0) {
            BlockPos $$12 = this.m_5452_(Heightmap.Types.MOTION_BLOCKING, this.m_46496_($$4, 0, $$5, 15));
            BlockPos $$13 = $$12.m_7495_();
            Biome $$14 = this.m_204166_($$12).m_203334_();
            if ($$14.m_47477_(this, $$13)) {
                this.m_46597_($$13, Blocks.f_50126_.m_49966_());
            }
            if ($$3) {
                if ($$14.m_47519_(this, $$12)) {
                    this.m_46597_($$12, Blocks.f_50125_.m_49966_());
                }
                BlockState $$15 = this.m_8055_($$13);
                Biome.Precipitation $$16 = $$14.m_47530_();
                if ($$16 == Biome.Precipitation.RAIN && $$14.m_198904_($$13)) {
                    $$16 = Biome.Precipitation.SNOW;
                }
                $$15.m_60734_().m_141997_($$15, this, $$13, $$16);
            }
        }
        $$6.m_6182_("tickBlocks");
        if (p_8716_ > 0) {
            for (LevelChunkSection $$17 : p_8715_.m_7103_()) {
                if (!$$17.m_63014_()) continue;
                int $$18 = $$17.m_63017_();
                for (int $$19 = 0; $$19 < p_8716_; ++$$19) {
                    FluidState $$22;
                    BlockPos $$20 = this.m_46496_($$4, $$18, $$5, 15);
                    $$6.m_6180_("randomTick");
                    BlockState $$21 = $$17.m_62982_($$20.m_123341_() - $$4, $$20.m_123342_() - $$18, $$20.m_123343_() - $$5);
                    if ($$21.m_60823_()) {
                        $$21.m_222972_(this, $$20, this.f_46441_);
                    }
                    if (($$22 = $$21.m_60819_()).m_76187_()) {
                        $$22.m_230562_(this, $$20, this.f_46441_);
                    }
                    $$6.m_7238_();
                }
            }
        }
        $$6.m_7238_();
    }

    private Optional<BlockPos> m_143248_(BlockPos p_143249_) {
        Optional<BlockPos> $$1 = this.m_8904_().m_148658_(p_215059_ -> p_215059_.m_203565_(PoiTypes.f_218066_), p_184055_ -> p_184055_.m_123342_() == this.m_6924_(Heightmap.Types.WORLD_SURFACE, p_184055_.m_123341_(), p_184055_.m_123343_()) - 1, p_143249_, 128, PoiManager.Occupancy.ANY);
        return $$1.map(p_184053_ -> p_184053_.m_6630_(1));
    }

    protected BlockPos m_143288_(BlockPos p_143289_) {
        BlockPos $$1 = this.m_5452_(Heightmap.Types.MOTION_BLOCKING, p_143289_);
        Optional<BlockPos> $$2 = this.m_143248_($$1);
        if ($$2.isPresent()) {
            return $$2.get();
        }
        AABB $$3 = new AABB($$1, new BlockPos($$1.m_123341_(), this.m_151558_(), $$1.m_123343_())).m_82400_(3.0);
        List<LivingEntity> $$4 = this.m_6443_(LivingEntity.class, $$3, p_184067_ -> p_184067_ != null && p_184067_.m_6084_() && this.m_45527_(p_184067_.m_20183_()));
        if (!$$4.isEmpty()) {
            return $$4.get(this.f_46441_.m_188503_($$4.size())).m_20183_();
        }
        if ($$1.m_123342_() == this.m_141937_() - 1) {
            $$1 = $$1.m_6630_(2);
        }
        return $$1;
    }

    public boolean m_8874_() {
        return this.f_8557_;
    }

    public boolean m_143333_() {
        return this.m_46469_().m_46215_(GameRules.f_151486_) <= 100;
    }

    private void m_143315_() {
        MutableComponent $$2;
        if (!this.m_143333_()) {
            return;
        }
        if (this.m_7654_().m_129792_() && !this.m_7654_().m_6992_()) {
            return;
        }
        int $$0 = this.m_46469_().m_46215_(GameRules.f_151486_);
        if (this.f_143245_.m_144002_($$0)) {
            MutableComponent $$1 = Component.m_237115_("sleep.skipping_night");
        } else {
            $$2 = Component.m_237110_("sleep.players_sleeping", this.f_143245_.m_144009_(), this.f_143245_.m_144010_($$0));
        }
        for (ServerPlayer $$3 : this.f_8546_) {
            $$3.m_5661_($$2, true);
        }
    }

    public void m_8878_() {
        if (!this.f_8546_.isEmpty() && this.f_143245_.m_144007_(this.f_8546_)) {
            this.m_143315_();
        }
    }

    @Override
    public ServerScoreboard m_6188_() {
        return this.f_8548_.m_129896_();
    }

    private void m_184096_() {
        boolean $$0 = this.m_46471_();
        if (this.m_6042_().f_223549_()) {
            if (this.m_46469_().m_46207_(GameRules.f_46150_)) {
                int $$1 = this.f_8549_.m_6537_();
                int $$2 = this.f_8549_.m_6558_();
                int $$3 = this.f_8549_.m_6531_();
                boolean $$4 = this.f_46442_.m_6534_();
                boolean $$5 = this.f_46442_.m_6533_();
                if ($$1 > 0) {
                    --$$1;
                    $$2 = $$4 ? 0 : 1;
                    $$3 = $$5 ? 0 : 1;
                    $$4 = false;
                    $$5 = false;
                } else {
                    if ($$2 > 0) {
                        if (--$$2 == 0) {
                            $$4 = !$$4;
                        }
                    } else {
                        $$2 = $$4 ? Mth.m_216287_(this.f_46441_, 3600, 15600) : Mth.m_216287_(this.f_46441_, 12000, 180000);
                    }
                    if ($$3 > 0) {
                        if (--$$3 == 0) {
                            $$5 = !$$5;
                        }
                    } else {
                        $$3 = $$5 ? Mth.m_216287_(this.f_46441_, 12000, 24000) : Mth.m_216287_(this.f_46441_, 12000, 180000);
                    }
                }
                this.f_8549_.m_6398_($$2);
                this.f_8549_.m_6399_($$3);
                this.f_8549_.m_6393_($$1);
                this.f_8549_.m_5557_($$4);
                this.f_8549_.m_5565_($$5);
            }
            this.f_46439_ = this.f_46440_;
            this.f_46440_ = this.f_46442_.m_6534_() ? (this.f_46440_ += 0.01f) : (this.f_46440_ -= 0.01f);
            this.f_46440_ = Mth.m_14036_(this.f_46440_, 0.0f, 1.0f);
            this.f_46437_ = this.f_46438_;
            this.f_46438_ = this.f_46442_.m_6533_() ? (this.f_46438_ += 0.01f) : (this.f_46438_ -= 0.01f);
            this.f_46438_ = Mth.m_14036_(this.f_46438_, 0.0f, 1.0f);
        }
        if (this.f_46437_ != this.f_46438_) {
            this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132160_, this.f_46438_), this.m_46472_());
        }
        if (this.f_46439_ != this.f_46440_) {
            this.f_8548_.m_6846_().m_11270_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132161_, this.f_46440_), this.m_46472_());
        }
        if ($$0 != this.m_46471_()) {
            if ($$0) {
                this.f_8548_.m_6846_().m_11268_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132155_, 0.0f));
            } else {
                this.f_8548_.m_6846_().m_11268_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132154_, 0.0f));
            }
            this.f_8548_.m_6846_().m_11268_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132160_, this.f_46438_));
            this.f_8548_.m_6846_().m_11268_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132161_, this.f_46440_));
        }
    }

    private void m_184097_() {
        this.f_8549_.m_6399_(0);
        this.f_8549_.m_5565_(false);
        this.f_8549_.m_6398_(0);
        this.f_8549_.m_5557_(false);
    }

    public void m_8886_() {
        this.f_8551_ = 0;
    }

    private void m_184076_(BlockPos p_184077_, Fluid p_184078_) {
        FluidState $$2 = this.m_6425_(p_184077_);
        if ($$2.m_192917_(p_184078_)) {
            $$2.m_76163_(this, p_184077_);
        }
    }

    private void m_184112_(BlockPos p_184113_, Block p_184114_) {
        BlockState $$2 = this.m_8055_(p_184113_);
        if ($$2.m_60713_(p_184114_)) {
            $$2.m_222963_(this, p_184113_, this.f_46441_);
        }
    }

    public void m_8647_(Entity p_8648_) {
        p_8648_.m_146867_();
        ProfilerFiller $$1 = this.m_46473_();
        ++p_8648_.f_19797_;
        this.m_46473_().m_6521_(() -> Registry.f_122826_.m_7981_(p_8648_.m_6095_()).toString());
        $$1.m_6174_("tickNonPassenger");
        p_8648_.m_8119_();
        this.m_46473_().m_7238_();
        for (Entity $$2 : p_8648_.m_20197_()) {
            this.m_8662_(p_8648_, $$2);
        }
    }

    private void m_8662_(Entity p_8663_, Entity p_8664_) {
        if (p_8664_.m_213877_() || p_8664_.m_20202_() != p_8663_) {
            p_8664_.m_8127_();
            return;
        }
        if (!(p_8664_ instanceof Player) && !this.f_143243_.m_156914_(p_8664_)) {
            return;
        }
        p_8664_.m_146867_();
        ++p_8664_.f_19797_;
        ProfilerFiller $$2 = this.m_46473_();
        $$2.m_6521_(() -> Registry.f_122826_.m_7981_(p_8664_.m_6095_()).toString());
        $$2.m_6174_("tickPassenger");
        p_8664_.m_6083_();
        $$2.m_7238_();
        for (Entity $$3 : p_8664_.m_20197_()) {
            this.m_8662_(p_8664_, $$3);
        }
    }

    @Override
    public boolean m_7966_(Player p_8696_, BlockPos p_8697_) {
        return !this.f_8548_.m_7762_(this, p_8697_, p_8696_) && this.m_6857_().m_61937_(p_8697_);
    }

    public void m_8643_(@Nullable ProgressListener p_8644_, boolean p_8645_, boolean p_8646_) {
        ServerChunkCache $$3 = this.m_7726_();
        if (p_8646_) {
            return;
        }
        if (p_8644_ != null) {
            p_8644_.m_6309_(Component.m_237115_("menu.savingLevel"));
        }
        this.m_8806_();
        if (p_8644_ != null) {
            p_8644_.m_6307_(Component.m_237115_("menu.savingChunks"));
        }
        $$3.m_8419_(p_8645_);
        if (p_8645_) {
            this.f_143244_.m_157561_();
        } else {
            this.f_143244_.m_157554_();
        }
    }

    private void m_8806_() {
        if (this.f_8559_ != null) {
            this.f_8548_.m_129910_().m_5915_(this.f_8559_.m_64081_());
        }
        this.m_7726_().m_8483_().m_78151_();
    }

    public <T extends Entity> List<? extends T> m_143280_(EntityTypeTest<Entity, T> p_143281_, Predicate<? super T> p_143282_) {
        ArrayList $$2 = Lists.newArrayList();
        this.m_142646_().m_142690_(p_143281_, p_184091_ -> {
            if (p_143282_.test(p_184091_)) {
                $$2.add(p_184091_);
            }
        });
        return $$2;
    }

    public List<? extends EnderDragon> m_8857_() {
        return this.m_143280_(EntityType.f_20565_, LivingEntity::m_6084_);
    }

    public List<ServerPlayer> m_8795_(Predicate<? super ServerPlayer> p_8796_) {
        ArrayList $$1 = Lists.newArrayList();
        for (ServerPlayer $$2 : this.f_8546_) {
            if (!p_8796_.test($$2)) continue;
            $$1.add($$2);
        }
        return $$1;
    }

    @Nullable
    public ServerPlayer m_8890_() {
        List<ServerPlayer> $$0 = this.m_8795_(LivingEntity::m_6084_);
        if ($$0.isEmpty()) {
            return null;
        }
        return $$0.get(this.f_46441_.m_188503_($$0.size()));
    }

    @Override
    public boolean m_7967_(Entity p_8837_) {
        return this.m_8872_(p_8837_);
    }

    public boolean m_8847_(Entity p_8848_) {
        return this.m_8872_(p_8848_);
    }

    public void m_143334_(Entity p_143335_) {
        this.m_8872_(p_143335_);
    }

    public void m_8622_(ServerPlayer p_8623_) {
        this.m_8853_(p_8623_);
    }

    public void m_8817_(ServerPlayer p_8818_) {
        this.m_8853_(p_8818_);
    }

    public void m_8834_(ServerPlayer p_8835_) {
        this.m_8853_(p_8835_);
    }

    public void m_8845_(ServerPlayer p_8846_) {
        this.m_8853_(p_8846_);
    }

    private void m_8853_(ServerPlayer p_8854_) {
        Entity $$1 = this.m_142646_().m_142694_(p_8854_.m_20148_());
        if ($$1 != null) {
            f_8566_.warn("Force-added player with duplicate UUID {}", (Object)p_8854_.m_20148_().toString());
            $$1.m_19877_();
            this.m_143261_((ServerPlayer)$$1, Entity.RemovalReason.DISCARDED);
        }
        this.f_143244_.m_157533_(p_8854_);
    }

    private boolean m_8872_(Entity p_8873_) {
        if (p_8873_.m_213877_()) {
            f_8566_.warn("Tried to add entity {} but it was marked as removed already", (Object)EntityType.m_20613_(p_8873_.m_6095_()));
            return false;
        }
        return this.f_143244_.m_157533_(p_8873_);
    }

    public boolean m_8860_(Entity p_8861_) {
        if (p_8861_.m_20199_().map(Entity::m_20148_).anyMatch(this.f_143244_::m_157550_)) {
            return false;
        }
        this.m_47205_(p_8861_);
        return true;
    }

    public void m_8712_(LevelChunk p_8713_) {
        p_8713_.m_187957_();
        p_8713_.m_187979_(this);
    }

    public void m_143261_(ServerPlayer p_143262_, Entity.RemovalReason p_143263_) {
        p_143262_.m_142687_(p_143263_);
    }

    @Override
    public void m_6801_(int p_8612_, BlockPos p_8613_, int p_8614_) {
        for (ServerPlayer $$3 : this.f_8548_.m_6846_().m_11314_()) {
            double $$6;
            double $$5;
            double $$4;
            if ($$3 == null || $$3.f_19853_ != this || $$3.m_19879_() == p_8612_ || !(($$4 = (double)p_8613_.m_123341_() - $$3.m_20185_()) * $$4 + ($$5 = (double)p_8613_.m_123342_() - $$3.m_20186_()) * $$5 + ($$6 = (double)p_8613_.m_123343_() - $$3.m_20189_()) * $$6 < 1024.0)) continue;
            $$3.f_8906_.m_9829_(new ClientboundBlockDestructionPacket(p_8612_, p_8613_, p_8614_));
        }
    }

    @Override
    public void m_214150_(@Nullable Player p_215017_, double p_215018_, double p_215019_, double p_215020_, SoundEvent p_215021_, SoundSource p_215022_, float p_215023_, float p_215024_, long p_215025_) {
        this.f_8548_.m_6846_().m_11241_(p_215017_, p_215018_, p_215019_, p_215020_, p_215021_.m_215668_(p_215023_), this.m_46472_(), new ClientboundSoundPacket(p_215021_, p_215022_, p_215018_, p_215019_, p_215020_, p_215023_, p_215024_, p_215025_));
    }

    @Override
    public void m_213890_(@Nullable Player p_215027_, Entity p_215028_, SoundEvent p_215029_, SoundSource p_215030_, float p_215031_, float p_215032_, long p_215033_) {
        this.f_8548_.m_6846_().m_11241_(p_215027_, p_215028_.m_20185_(), p_215028_.m_20186_(), p_215028_.m_20189_(), p_215029_.m_215668_(p_215031_), this.m_46472_(), new ClientboundSoundEntityPacket(p_215029_, p_215030_, p_215028_, p_215031_, p_215032_, p_215033_));
    }

    @Override
    public void m_6798_(int p_8811_, BlockPos p_8812_, int p_8813_) {
        this.f_8548_.m_6846_().m_11268_(new ClientboundLevelEventPacket(p_8811_, p_8812_, p_8813_, true));
    }

    @Override
    public void m_5898_(@Nullable Player p_8684_, int p_8685_, BlockPos p_8686_, int p_8687_) {
        this.f_8548_.m_6846_().m_11241_(p_8684_, p_8686_.m_123341_(), p_8686_.m_123342_(), p_8686_.m_123343_(), 64.0, this.m_46472_(), new ClientboundLevelEventPacket(p_8685_, p_8686_, p_8687_, false));
    }

    public int m_143344_() {
        return this.m_6042_().f_63865_();
    }

    @Override
    public void m_214171_(GameEvent p_215041_, Vec3 p_215042_, GameEvent.Context p_215043_) {
        int $$3 = p_215041_.m_157827_();
        BlockPos $$4 = new BlockPos(p_215042_);
        int $$5 = SectionPos.m_123171_($$4.m_123341_() - $$3);
        int $$6 = SectionPos.m_123171_($$4.m_123342_() - $$3);
        int $$7 = SectionPos.m_123171_($$4.m_123343_() - $$3);
        int $$8 = SectionPos.m_123171_($$4.m_123341_() + $$3);
        int $$9 = SectionPos.m_123171_($$4.m_123342_() + $$3);
        int $$10 = SectionPos.m_123171_($$4.m_123343_() + $$3);
        ArrayList<GameEvent.Message> $$11 = new ArrayList<GameEvent.Message>();
        boolean $$12 = false;
        for (int $$13 = $$5; $$13 <= $$8; ++$$13) {
            for (int $$14 = $$7; $$14 <= $$10; ++$$14) {
                LevelChunk $$15 = this.m_7726_().m_7131_($$13, $$14);
                if ($$15 == null) continue;
                for (int $$16 = $$6; $$16 <= $$9; ++$$16) {
                    $$12 |= ((ChunkAccess)$$15).m_142336_($$16).m_213682_(p_215041_, p_215042_, p_215043_, (p_215067_, p_215068_) -> (p_215067_.m_214054_() ? $$11 : this.f_214996_).add(new GameEvent.Message(p_215041_, p_215042_, p_215043_, (GameEventListener)p_215067_, (Vec3)p_215068_)));
                }
            }
        }
        if (!$$11.isEmpty()) {
            this.m_215060_($$11);
        }
        if ($$12) {
            DebugPackets.m_237887_(this, p_215041_, p_215042_);
        }
    }

    private void m_215075_() {
        if (this.f_214996_.isEmpty()) {
            return;
        }
        List<GameEvent.Message> $$0 = this.f_214996_;
        this.f_214996_ = new ArrayList<GameEvent.Message>();
        this.m_215060_($$0);
    }

    private void m_215060_(List<GameEvent.Message> p_215061_) {
        Collections.sort(p_215061_);
        for (GameEvent.Message $$1 : p_215061_) {
            GameEventListener $$2 = $$1.m_223747_();
            $$2.m_214068_(this, $$1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void m_7260_(BlockPos p_8755_, BlockState p_8756_, BlockState p_8757_, int p_8758_) {
        if (this.f_200893_) {
            String $$4 = "recursive call to sendBlockUpdated";
            Util.m_200890_("recursive call to sendBlockUpdated", new IllegalStateException("recursive call to sendBlockUpdated"));
        }
        this.m_7726_().m_8450_(p_8755_);
        VoxelShape $$5 = p_8756_.m_60812_(this, p_8755_);
        VoxelShape $$6 = p_8757_.m_60812_(this, p_8755_);
        if (!Shapes.m_83157_($$5, $$6, BooleanOp.f_82687_)) {
            return;
        }
        ObjectArrayList $$7 = new ObjectArrayList();
        for (Mob $$8 : this.f_143246_) {
            PathNavigation $$9 = $$8.m_21573_();
            if (!$$9.m_200903_(p_8755_)) continue;
            $$7.add($$9);
        }
        try {
            this.f_200893_ = true;
            for (PathNavigation $$10 : $$7) {
                $$10.m_26569_();
            }
        }
        finally {
            this.f_200893_ = false;
        }
    }

    @Override
    public void m_46672_(BlockPos p_215045_, Block p_215046_) {
        this.f_220350_.m_214152_(p_215045_, p_215046_, null);
    }

    @Override
    public void m_46590_(BlockPos p_215052_, Block p_215053_, Direction p_215054_) {
        this.f_220350_.m_214152_(p_215052_, p_215053_, p_215054_);
    }

    @Override
    public void m_46586_(BlockPos p_215048_, Block p_215049_, BlockPos p_215050_) {
        this.f_220350_.m_214026_(p_215048_, p_215049_, p_215050_);
    }

    @Override
    public void m_213960_(BlockState p_215035_, BlockPos p_215036_, Block p_215037_, BlockPos p_215038_, boolean p_215039_) {
        this.f_220350_.m_213858_(p_215035_, p_215036_, p_215037_, p_215038_, p_215039_);
    }

    @Override
    public void m_7605_(Entity p_8650_, byte p_8651_) {
        this.m_7726_().m_8394_(p_8650_, new ClientboundEntityEventPacket(p_8650_, p_8651_));
    }

    @Override
    public ServerChunkCache m_7726_() {
        return this.f_8547_;
    }

    @Override
    public Explosion m_7703_(@Nullable Entity p_8653_, @Nullable DamageSource p_8654_, @Nullable ExplosionDamageCalculator p_8655_, double p_8656_, double p_8657_, double p_8658_, float p_8659_, boolean p_8660_, Explosion.BlockInteraction p_8661_) {
        Explosion $$9 = new Explosion(this, p_8653_, p_8654_, p_8655_, p_8656_, p_8657_, p_8658_, p_8659_, p_8660_, p_8661_);
        $$9.m_46061_();
        $$9.m_46075_(false);
        if (p_8661_ == Explosion.BlockInteraction.NONE) {
            $$9.m_46080_();
        }
        for (ServerPlayer $$10 : this.f_8546_) {
            if (!($$10.m_20275_(p_8656_, p_8657_, p_8658_) < 4096.0)) continue;
            $$10.f_8906_.m_9829_(new ClientboundExplodePacket(p_8656_, p_8657_, p_8658_, p_8659_, $$9.m_46081_(), $$9.m_46078_().get($$10)));
        }
        return $$9;
    }

    @Override
    public void m_7696_(BlockPos p_8746_, Block p_8747_, int p_8748_, int p_8749_) {
        this.f_8556_.add((Object)new BlockEventData(p_8746_, p_8747_, p_8748_, p_8749_));
    }

    private void m_8807_() {
        this.f_184048_.clear();
        while (!this.f_8556_.isEmpty()) {
            BlockEventData $$0 = (BlockEventData)this.f_8556_.removeFirst();
            if (this.m_220393_($$0.f_45529_())) {
                if (!this.m_8698_($$0)) continue;
                this.f_8548_.m_6846_().m_11241_(null, $$0.f_45529_().m_123341_(), $$0.f_45529_().m_123342_(), $$0.f_45529_().m_123343_(), 64.0, this.m_46472_(), new ClientboundBlockEventPacket($$0.f_45529_(), $$0.f_45530_(), $$0.f_45531_(), $$0.f_45532_()));
                continue;
            }
            this.f_184048_.add($$0);
        }
        this.f_8556_.addAll(this.f_184048_);
    }

    private boolean m_8698_(BlockEventData p_8699_) {
        BlockState $$1 = this.m_8055_(p_8699_.f_45529_());
        if ($$1.m_60713_(p_8699_.f_45530_())) {
            return $$1.m_60677_(this, p_8699_.f_45529_(), p_8699_.f_45531_(), p_8699_.f_45532_());
        }
        return false;
    }

    public LevelTicks<Block> m_183326_() {
        return this.f_8553_;
    }

    public LevelTicks<Fluid> m_183324_() {
        return this.f_184047_;
    }

    @Override
    @Nonnull
    public MinecraftServer m_7654_() {
        return this.f_8548_;
    }

    public PortalForcer m_8871_() {
        return this.f_8552_;
    }

    public StructureTemplateManager m_215082_() {
        return this.f_8548_.m_236738_();
    }

    public <T extends ParticleOptions> int m_8767_(T p_8768_, double p_8769_, double p_8770_, double p_8771_, int p_8772_, double p_8773_, double p_8774_, double p_8775_, double p_8776_) {
        ClientboundLevelParticlesPacket $$9 = new ClientboundLevelParticlesPacket(p_8768_, false, p_8769_, p_8770_, p_8771_, (float)p_8773_, (float)p_8774_, (float)p_8775_, (float)p_8776_, p_8772_);
        int $$10 = 0;
        for (int $$11 = 0; $$11 < this.f_8546_.size(); ++$$11) {
            ServerPlayer $$12 = this.f_8546_.get($$11);
            if (!this.m_8636_($$12, false, p_8769_, p_8770_, p_8771_, $$9)) continue;
            ++$$10;
        }
        return $$10;
    }

    public <T extends ParticleOptions> boolean m_8624_(ServerPlayer p_8625_, T p_8626_, boolean p_8627_, double p_8628_, double p_8629_, double p_8630_, int p_8631_, double p_8632_, double p_8633_, double p_8634_, double p_8635_) {
        ClientboundLevelParticlesPacket $$11 = new ClientboundLevelParticlesPacket(p_8626_, p_8627_, p_8628_, p_8629_, p_8630_, (float)p_8632_, (float)p_8633_, (float)p_8634_, (float)p_8635_, p_8631_);
        return this.m_8636_(p_8625_, p_8627_, p_8628_, p_8629_, p_8630_, $$11);
    }

    private boolean m_8636_(ServerPlayer p_8637_, boolean p_8638_, double p_8639_, double p_8640_, double p_8641_, Packet<?> p_8642_) {
        if (p_8637_.m_9236_() != this) {
            return false;
        }
        BlockPos $$6 = p_8637_.m_20183_();
        if ($$6.m_203195_(new Vec3(p_8639_, p_8640_, p_8641_), p_8638_ ? 512.0 : 32.0)) {
            p_8637_.f_8906_.m_9829_(p_8642_);
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public Entity m_6815_(int p_8597_) {
        return this.m_142646_().m_142597_(p_8597_);
    }

    @Deprecated
    @Nullable
    public Entity m_143317_(int p_143318_) {
        Entity $$1 = this.m_142646_().m_142597_(p_143318_);
        if ($$1 != null) {
            return $$1;
        }
        return (Entity)this.f_143247_.get(p_143318_);
    }

    @Nullable
    public Entity m_8791_(UUID p_8792_) {
        return this.m_142646_().m_142694_(p_8792_);
    }

    @Nullable
    public BlockPos m_215011_(TagKey<Structure> p_215012_, BlockPos p_215013_, int p_215014_, boolean p_215015_) {
        if (!this.f_8548_.m_129910_().m_5961_().m_224677_()) {
            return null;
        }
        Optional<HolderSet.Named<Structure>> $$4 = this.m_5962_().m_175515_(Registry.f_235725_).m_203431_(p_215012_);
        if ($$4.isEmpty()) {
            return null;
        }
        Pair<BlockPos, Holder<Structure>> $$5 = this.m_7726_().m_8481_().m_223037_(this, (HolderSet<Structure>)$$4.get(), p_215013_, p_215014_, p_215015_);
        return $$5 != null ? (BlockPos)$$5.getFirst() : null;
    }

    @Nullable
    public Pair<BlockPos, Holder<Biome>> m_215069_(Predicate<Holder<Biome>> p_215070_, BlockPos p_215071_, int p_215072_, int p_215073_, int p_215074_) {
        return this.m_7726_().m_8481_().m_62218_().m_214004_(p_215071_, p_215072_, p_215073_, p_215074_, p_215070_, this.m_7726_().m_214994_().m_224579_(), this);
    }

    @Override
    public RecipeManager m_7465_() {
        return this.f_8548_.m_129894_();
    }

    @Override
    public boolean m_7441_() {
        return this.f_8564_;
    }

    @Override
    public RegistryAccess m_5962_() {
        return this.f_8548_.m_206579_();
    }

    public DimensionDataStorage m_8895_() {
        return this.m_7726_().m_8483_();
    }

    @Override
    @Nullable
    public MapItemSavedData m_7489_(String p_8785_) {
        return this.m_7654_().m_129783_().m_8895_().m_164858_(MapItemSavedData::m_164807_, p_8785_);
    }

    @Override
    public void m_142325_(String p_143305_, MapItemSavedData p_143306_) {
        this.m_7654_().m_129783_().m_8895_().m_164855_(p_143305_, p_143306_);
    }

    @Override
    public int m_7354_() {
        return this.m_7654_().m_129783_().m_8895_().m_164861_(MapIndex::m_164762_, MapIndex::new, "idcounts").m_77880_();
    }

    public void m_8733_(BlockPos p_8734_, float p_8735_) {
        ChunkPos $$2 = new ChunkPos(new BlockPos(this.f_46442_.m_6789_(), 0, this.f_46442_.m_6526_()));
        this.f_46442_.m_7250_(p_8734_, p_8735_);
        this.m_7726_().m_8438_(TicketType.f_9442_, $$2, 11, Unit.INSTANCE);
        this.m_7726_().m_8387_(TicketType.f_9442_, new ChunkPos(p_8734_), 11, Unit.INSTANCE);
        this.m_7654_().m_6846_().m_11268_(new ClientboundSetDefaultSpawnPositionPacket(p_8734_, p_8735_));
    }

    public LongSet m_8902_() {
        ForcedChunksSavedData $$0 = this.m_8895_().m_164858_(ForcedChunksSavedData::m_151483_, "chunks");
        return $$0 != null ? LongSets.unmodifiable((LongSet)$$0.m_46116_()) : LongSets.EMPTY_SET;
    }

    public boolean m_8602_(int p_8603_, int p_8604_, boolean p_8605_) {
        boolean $$7;
        ForcedChunksSavedData $$3 = this.m_8895_().m_164861_(ForcedChunksSavedData::m_151483_, ForcedChunksSavedData::new, "chunks");
        ChunkPos $$4 = new ChunkPos(p_8603_, p_8604_);
        long $$5 = $$4.m_45588_();
        if (p_8605_) {
            boolean $$6 = $$3.m_46116_().add($$5);
            if ($$6) {
                this.m_6325_(p_8603_, p_8604_);
            }
        } else {
            $$7 = $$3.m_46116_().remove($$5);
        }
        $$3.m_77760_($$7);
        if ($$7) {
            this.m_7726_().m_6692_($$4, p_8605_);
        }
        return $$7;
    }

    public List<ServerPlayer> m_6907_() {
        return this.f_8546_;
    }

    @Override
    public void m_6559_(BlockPos p_8751_, BlockState p_8752_, BlockState p_8753_) {
        Optional<Holder<PoiType>> $$4;
        Optional<Holder<PoiType>> $$3 = PoiTypes.m_218075_(p_8752_);
        if (Objects.equals($$3, $$4 = PoiTypes.m_218075_(p_8753_))) {
            return;
        }
        BlockPos $$5 = p_8751_.m_7949_();
        $$3.ifPresent(p_215081_ -> this.m_7654_().execute(() -> {
            this.m_8904_().m_27079_($$5);
            DebugPackets.m_133716_(this, $$5);
        }));
        $$4.ifPresent(p_215057_ -> this.m_7654_().execute(() -> {
            this.m_8904_().m_217919_($$5, (Holder<PoiType>)p_215057_);
            DebugPackets.m_133679_(this, $$5);
        }));
    }

    public PoiManager m_8904_() {
        return this.m_7726_().m_8484_();
    }

    public boolean m_8802_(BlockPos p_8803_) {
        return this.m_8736_(p_8803_, 1);
    }

    public boolean m_8762_(SectionPos p_8763_) {
        return this.m_8802_(p_8763_.m_123250_());
    }

    public boolean m_8736_(BlockPos p_8737_, int p_8738_) {
        if (p_8738_ > 6) {
            return false;
        }
        return this.m_8828_(SectionPos.m_123199_(p_8737_)) <= p_8738_;
    }

    public int m_8828_(SectionPos p_8829_) {
        return this.m_8904_().m_27098_(p_8829_);
    }

    public Raids m_8905_() {
        return this.f_8565_;
    }

    @Nullable
    public Raid m_8832_(BlockPos p_8833_) {
        return this.f_8565_.m_37970_(p_8833_, 9216);
    }

    public boolean m_8843_(BlockPos p_8844_) {
        return this.m_8832_(p_8844_) != null;
    }

    public void m_8670_(ReputationEventType p_8671_, Entity p_8672_, ReputationEventHandler p_8673_) {
        p_8673_.m_6814_(p_8671_, p_8672_);
    }

    public void m_8786_(Path p_8787_) throws IOException {
        ChunkMap $$1 = this.m_7726_().f_8325_;
        try (BufferedWriter $$2 = Files.newBufferedWriter(p_8787_.resolve("stats.txt"), new OpenOption[0]);){
            $$2.write(String.format(Locale.ROOT, "spawning_chunks: %d\n", $$1.m_143145_().m_140816_()));
            NaturalSpawner.SpawnState $$3 = this.m_7726_().m_8485_();
            if ($$3 != null) {
                for (Object2IntMap.Entry $$4 : $$3.m_47148_().object2IntEntrySet()) {
                    $$2.write(String.format(Locale.ROOT, "spawn_count.%s: %d\n", ((MobCategory)$$4.getKey()).m_21607_(), $$4.getIntValue()));
                }
            }
            $$2.write(String.format(Locale.ROOT, "entities: %s\n", this.f_143244_.m_157572_()));
            $$2.write(String.format(Locale.ROOT, "block_entity_tickers: %d\n", this.f_151512_.size()));
            $$2.write(String.format(Locale.ROOT, "block_ticks: %d\n", ((LevelTicks)this.m_183326_()).m_183574_()));
            $$2.write(String.format(Locale.ROOT, "fluid_ticks: %d\n", ((LevelTicks)this.m_183324_()).m_183574_()));
            $$2.write("distance_manager: " + $$1.m_143145_().m_140837_() + "\n");
            $$2.write(String.format(Locale.ROOT, "pending_tasks: %d\n", this.m_7726_().m_8480_()));
        }
        CrashReport $$5 = new CrashReport("Level dump", new Exception("dummy"));
        this.m_6026_($$5);
        try (BufferedWriter $$6 = Files.newBufferedWriter(p_8787_.resolve("example_crash.txt"), new OpenOption[0]);){
            $$6.write($$5.m_127526_());
        }
        Path $$7 = p_8787_.resolve("chunks.csv");
        try (BufferedWriter $$8 = Files.newBufferedWriter($$7, new OpenOption[0]);){
            $$1.m_140274_($$8);
        }
        Path $$9 = p_8787_.resolve("entity_chunks.csv");
        try (BufferedWriter $$10 = Files.newBufferedWriter($$9, new OpenOption[0]);){
            this.f_143244_.m_157548_($$10);
        }
        Path $$11 = p_8787_.resolve("entities.csv");
        try (BufferedWriter $$12 = Files.newBufferedWriter($$11, new OpenOption[0]);){
            ServerLevel.m_8781_($$12, this.m_142646_().m_142273_());
        }
        Path $$13 = p_8787_.resolve("block_entities.csv");
        try (BufferedWriter $$14 = Files.newBufferedWriter($$13, new OpenOption[0]);){
            this.m_143299_($$14);
        }
    }

    private static void m_8781_(Writer p_8782_, Iterable<Entity> p_8783_) throws IOException {
        CsvOutput $$2 = CsvOutput.m_13619_().m_13630_("x").m_13630_("y").m_13630_("z").m_13630_("uuid").m_13630_("type").m_13630_("alive").m_13630_("display_name").m_13630_("custom_name").m_13628_(p_8782_);
        for (Entity $$3 : p_8783_) {
            Component $$4 = $$3.m_7770_();
            Component $$5 = $$3.m_5446_();
            $$2.m_13624_($$3.m_20185_(), $$3.m_20186_(), $$3.m_20189_(), $$3.m_20148_(), Registry.f_122826_.m_7981_($$3.m_6095_()), $$3.m_6084_(), $$5.getString(), $$4 != null ? $$4.getString() : null);
        }
    }

    private void m_143299_(Writer p_143300_) throws IOException {
        CsvOutput $$1 = CsvOutput.m_13619_().m_13630_("x").m_13630_("y").m_13630_("z").m_13630_("type").m_13628_(p_143300_);
        for (TickingBlockEntity $$2 : this.f_151512_) {
            BlockPos $$3 = $$2.m_142689_();
            $$1.m_13624_($$3.m_123341_(), $$3.m_123342_(), $$3.m_123343_(), $$2.m_142280_());
        }
    }

    @VisibleForTesting
    public void m_8722_(BoundingBox p_8723_) {
        this.f_8556_.removeIf(p_207568_ -> p_8723_.m_71051_(p_207568_.f_45529_()));
    }

    @Override
    public void m_6289_(BlockPos p_8743_, Block p_8744_) {
        if (!this.m_46659_()) {
            this.m_46672_(p_8743_, p_8744_);
        }
    }

    @Override
    public float m_7717_(Direction p_8760_, boolean p_8761_) {
        return 1.0f;
    }

    public Iterable<Entity> m_8583_() {
        return this.m_142646_().m_142273_();
    }

    public String toString() {
        return "ServerLevel[" + this.f_8549_.m_5462_() + "]";
    }

    public boolean m_8584_() {
        return this.f_8548_.m_129910_().m_5961_().m_64669_();
    }

    @Override
    public long m_7328_() {
        return this.f_8548_.m_129910_().m_5961_().m_64619_();
    }

    @Nullable
    public EndDragonFight m_8586_() {
        return this.f_8559_;
    }

    @Override
    public ServerLevel m_6018_() {
        return this;
    }

    @VisibleForTesting
    public String m_8590_() {
        return String.format(Locale.ROOT, "players: %s, entities: %s [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s", this.f_8546_.size(), this.f_143244_.m_157572_(), ServerLevel.m_143301_(this.f_143244_.m_157567_().m_142273_(), p_207585_ -> Registry.f_122826_.m_7981_(p_207585_.m_6095_()).toString()), this.f_151512_.size(), ServerLevel.m_143301_(this.f_151512_, TickingBlockEntity::m_142280_), ((LevelTicks)this.m_183326_()).m_183574_(), ((LevelTicks)this.m_183324_()).m_183574_(), this.m_46464_());
    }

    private static <T> String m_143301_(Iterable<T> p_143302_, Function<T, String> p_143303_) {
        try {
            Object2IntOpenHashMap $$2 = new Object2IntOpenHashMap();
            for (T $$3 : p_143302_) {
                String $$4 = p_143303_.apply($$3);
                $$2.addTo((Object)$$4, 1);
            }
            return $$2.object2IntEntrySet().stream().sorted(Comparator.comparing(Object2IntMap.Entry::getIntValue).reversed()).limit(5L).map(p_207570_ -> (String)p_207570_.getKey() + ":" + p_207570_.getIntValue()).collect(Collectors.joining(","));
        }
        catch (Exception $$5) {
            return "";
        }
    }

    public static void m_8617_(ServerLevel p_8618_) {
        BlockPos $$1 = f_8562_;
        int $$2 = $$1.m_123341_();
        int $$3 = $$1.m_123342_() - 2;
        int $$4 = $$1.m_123343_();
        BlockPos.m_121976_($$2 - 2, $$3 + 1, $$4 - 2, $$2 + 2, $$3 + 3, $$4 + 2).forEach(p_207578_ -> p_8618_.m_46597_((BlockPos)p_207578_, Blocks.f_50016_.m_49966_()));
        BlockPos.m_121976_($$2 - 2, $$3, $$4 - 2, $$2 + 2, $$3, $$4 + 2).forEach(p_184101_ -> p_8618_.m_46597_((BlockPos)p_184101_, Blocks.f_50080_.m_49966_()));
    }

    @Override
    protected LevelEntityGetter<Entity> m_142646_() {
        return this.f_143244_.m_157567_();
    }

    public void m_143311_(Stream<Entity> p_143312_) {
        this.f_143244_.m_157552_(p_143312_);
    }

    public void m_143327_(Stream<Entity> p_143328_) {
        this.f_143244_.m_157559_(p_143328_);
    }

    public void m_184102_(LevelChunk p_184103_) {
        p_184103_.m_187985_(this.m_6106_().m_6793_());
    }

    public void m_196557_(ChunkAccess p_196558_) {
        this.f_8548_.execute(() -> this.f_196556_.m_197282_(p_196558_.m_7697_(), p_196558_.m_6633_()));
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.f_143244_.close();
    }

    @Override
    public String m_46464_() {
        return "Chunks[S] W: " + this.f_8547_.m_6754_() + " E: " + this.f_143244_.m_157572_();
    }

    public boolean m_143319_(long p_143320_) {
        return this.f_143244_.m_157507_(p_143320_);
    }

    private boolean m_184110_(long p_184111_) {
        return this.m_143319_(p_184111_) && this.f_8547_.m_143239_(p_184111_);
    }

    public boolean m_143340_(BlockPos p_143341_) {
        return this.f_143244_.m_202167_(p_143341_) && this.f_8547_.f_8325_.m_143145_().m_183913_(ChunkPos.m_151388_(p_143341_));
    }

    public boolean m_201918_(BlockPos p_201919_) {
        return this.f_143244_.m_202167_(p_201919_);
    }

    public boolean m_201916_(ChunkPos p_201917_) {
        return this.f_143244_.m_202165_(p_201917_);
    }

    @Override
    public /* synthetic */ Scoreboard m_6188_() {
        return this.m_6188_();
    }

    @Override
    public /* synthetic */ ChunkSource m_7726_() {
        return this.m_7726_();
    }

    public /* synthetic */ LevelTickAccess m_183324_() {
        return this.m_183324_();
    }

    public /* synthetic */ LevelTickAccess m_183326_() {
        return this.m_183326_();
    }

    final class EntityCallbacks
    implements LevelCallback<Entity> {
        EntityCallbacks() {
        }

        @Override
        public void m_141989_(Entity p_143355_) {
        }

        @Override
        public void m_141986_(Entity p_143359_) {
            ServerLevel.this.m_6188_().m_83420_(p_143359_);
        }

        @Override
        public void m_141987_(Entity p_143363_) {
            ServerLevel.this.f_143243_.m_156908_(p_143363_);
        }

        @Override
        public void m_141983_(Entity p_143367_) {
            ServerLevel.this.f_143243_.m_156912_(p_143367_);
        }

        @Override
        public void m_141985_(Entity p_143371_) {
            ServerLevel.this.m_7726_().m_8463_(p_143371_);
            if (p_143371_ instanceof ServerPlayer) {
                ServerPlayer $$1 = (ServerPlayer)p_143371_;
                ServerLevel.this.f_8546_.add($$1);
                ServerLevel.this.m_8878_();
            }
            if (p_143371_ instanceof Mob) {
                Mob $$2 = (Mob)p_143371_;
                if (ServerLevel.this.f_200893_) {
                    String $$3 = "onTrackingStart called during navigation iteration";
                    Util.m_200890_("onTrackingStart called during navigation iteration", new IllegalStateException("onTrackingStart called during navigation iteration"));
                }
                ServerLevel.this.f_143246_.add($$2);
            }
            if (p_143371_ instanceof EnderDragon) {
                EnderDragon $$4 = (EnderDragon)p_143371_;
                for (EnderDragonPart $$5 : $$4.m_31156_()) {
                    ServerLevel.this.f_143247_.put($$5.m_19879_(), (Object)$$5);
                }
            }
            p_143371_.m_213651_(DynamicGameEventListener::m_223617_);
        }

        @Override
        public void m_141981_(Entity p_143375_) {
            ServerLevel.this.m_7726_().m_8443_(p_143375_);
            if (p_143375_ instanceof ServerPlayer) {
                ServerPlayer $$1 = (ServerPlayer)p_143375_;
                ServerLevel.this.f_8546_.remove($$1);
                ServerLevel.this.m_8878_();
            }
            if (p_143375_ instanceof Mob) {
                Mob $$2 = (Mob)p_143375_;
                if (ServerLevel.this.f_200893_) {
                    String $$3 = "onTrackingStart called during navigation iteration";
                    Util.m_200890_("onTrackingStart called during navigation iteration", new IllegalStateException("onTrackingStart called during navigation iteration"));
                }
                ServerLevel.this.f_143246_.remove($$2);
            }
            if (p_143375_ instanceof EnderDragon) {
                EnderDragon $$4 = (EnderDragon)p_143375_;
                for (EnderDragonPart $$5 : $$4.m_31156_()) {
                    ServerLevel.this.f_143247_.remove($$5.m_19879_());
                }
            }
            p_143375_.m_213651_(DynamicGameEventListener::m_223634_);
        }

        @Override
        public void m_214006_(Entity p_215086_) {
            p_215086_.m_213651_(DynamicGameEventListener::m_223641_);
        }

        @Override
        public /* synthetic */ void m_214006_(Object object) {
            this.m_214006_((Entity)object);
        }

        @Override
        public /* synthetic */ void m_141981_(Object object) {
            this.m_141981_((Entity)object);
        }

        @Override
        public /* synthetic */ void m_141985_(Object object) {
            this.m_141985_((Entity)object);
        }

        @Override
        public /* synthetic */ void m_141987_(Object object) {
            this.m_141987_((Entity)object);
        }

        @Override
        public /* synthetic */ void m_141986_(Object object) {
            this.m_141986_((Entity)object);
        }

        @Override
        public /* synthetic */ void m_141989_(Object object) {
            this.m_141989_((Entity)object);
        }
    }
}

