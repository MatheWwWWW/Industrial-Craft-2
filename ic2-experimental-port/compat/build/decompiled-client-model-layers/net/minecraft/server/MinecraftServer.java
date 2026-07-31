/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.GameProfileRepository
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.invoke.MethodHandle;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.runtime.ObjectMethods;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.network.chat.ChatDecorator;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.Services;
import net.minecraft.server.TickTask;
import net.minecraft.server.WorldStem;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.DemoMode;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.network.ServerConnectionListener;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.ModCheck;
import net.minecraft.util.Mth;
import net.minecraft.util.NativeModuleLister;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.EmptyProfileResults;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.CommandStorage;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public abstract class MinecraftServer
extends ReentrantBlockableEventLoop<TickTask>
implements CommandSource,
AutoCloseable {
    private static final Logger f_129750_ = LogUtils.getLogger();
    public static final String f_195495_ = "vanilla";
    private static final float f_177884_ = 0.8f;
    private static final int f_177885_ = 100;
    public static final int f_177878_ = 50;
    private static final int f_177887_ = 2000;
    private static final int f_177888_ = 15000;
    private static final long f_177889_ = 5000000000L;
    private static final int f_177890_ = 12;
    public static final int f_177882_ = 11;
    private static final int f_177891_ = 441;
    private static final int f_177892_ = 6000;
    private static final int f_177893_ = 3;
    public static final int f_177883_ = 29999984;
    public static final LevelSettings f_129743_ = new LevelSettings("Demo World", GameType.SURVIVAL, false, Difficulty.NORMAL, false, new GameRules(), DataPackConfig.f_45842_);
    private static final long f_177871_ = 50L;
    public static final GameProfile f_195496_ = new GameProfile(Util.f_137441_, "Anonymous Player");
    protected final LevelStorageSource.LevelStorageAccess f_129744_;
    protected final PlayerDataStorage f_129745_;
    private final List<Runnable> f_129752_ = Lists.newArrayList();
    private MetricsRecorder f_177872_ = InactiveMetricsRecorder.f_146153_;
    private ProfilerFiller f_129754_ = this.f_177872_.m_142610_();
    private Consumer<ProfileResults> f_177873_ = p_177903_ -> this.m_177928_();
    private Consumer<Path> f_177874_ = p_177954_ -> {};
    private boolean f_177875_;
    @Nullable
    private TimeProfiler f_177876_;
    private boolean f_177877_;
    private final ServerConnectionListener f_129755_;
    private final ChunkProgressListenerFactory f_129756_;
    private final ServerStatus f_129757_ = new ServerStatus();
    private final RandomSource f_129758_ = RandomSource.m_216327_();
    private final DataFixer f_129759_;
    private String f_129760_;
    private int f_129761_ = -1;
    private final RegistryAccess.Frozen f_129746_;
    private final Map<ResourceKey<Level>, ServerLevel> f_129762_ = Maps.newLinkedHashMap();
    private PlayerList f_129763_;
    private volatile boolean f_129764_ = true;
    private boolean f_129765_;
    private int f_129766_;
    protected final Proxy f_129747_;
    private boolean f_129705_;
    private boolean f_129706_;
    private boolean f_129707_;
    private boolean f_129708_;
    @Nullable
    private String f_129709_;
    private int f_129711_;
    public final long[] f_129748_ = new long[100];
    @Nullable
    private KeyPair f_129712_;
    @Nullable
    private GameProfile f_236719_;
    private boolean f_129714_;
    private volatile boolean f_129717_;
    private long f_129718_;
    protected final Services f_236721_;
    private long f_129724_;
    private final Thread f_129725_;
    private long f_129726_ = Util.m_137550_();
    private long f_129727_;
    private boolean f_129728_;
    private final PackRepository f_129730_;
    private final ServerScoreboard f_129731_ = new ServerScoreboard(this);
    @Nullable
    private CommandStorage f_129732_;
    private final CustomBossEvents f_129733_ = new CustomBossEvents();
    private final ServerFunctionManager f_129734_;
    private final FrameTimer f_129735_ = new FrameTimer();
    private boolean f_129736_;
    private float f_129737_;
    private final Executor f_129738_;
    @Nullable
    private String f_129739_;
    private ReloadableResources f_129740_;
    private final StructureTemplateManager f_236720_;
    protected final WorldData f_129749_;
    private volatile boolean f_195494_;

    public static <S extends MinecraftServer> S m_129872_(Function<Thread, S> p_129873_) {
        AtomicReference<MinecraftServer> $$1 = new AtomicReference<MinecraftServer>();
        Thread $$2 = new Thread(() -> ((MinecraftServer)$$1.get()).m_130011_(), "Server thread");
        $$2.setUncaughtExceptionHandler((p_177909_, p_177910_) -> f_129750_.error("Uncaught exception in server thread", p_177910_));
        if (Runtime.getRuntime().availableProcessors() > 4) {
            $$2.setPriority(8);
        }
        MinecraftServer $$3 = (MinecraftServer)p_129873_.apply($$2);
        $$1.set($$3);
        $$2.start();
        return (S)$$3;
    }

    public MinecraftServer(Thread p_236723_, LevelStorageSource.LevelStorageAccess p_236724_, PackRepository p_236725_, WorldStem p_236726_, Proxy p_236727_, DataFixer p_236728_, Services p_236729_, ChunkProgressListenerFactory p_236730_) {
        super("Server");
        this.f_129746_ = p_236726_.f_206894_();
        this.f_129749_ = p_236726_.f_206895_();
        if (!this.f_129749_.m_5961_().m_204655_().m_142003_(LevelStem.f_63971_)) {
            throw new IllegalStateException("Missing Overworld dimension data");
        }
        this.f_129747_ = p_236727_;
        this.f_129730_ = p_236725_;
        this.f_129740_ = new ReloadableResources(p_236726_.f_206892_(), p_236726_.f_206893_());
        this.f_236721_ = p_236729_;
        if (p_236729_.f_214336_() != null) {
            p_236729_.f_214336_().m_143974_(this);
        }
        this.f_129755_ = new ServerConnectionListener(this);
        this.f_129756_ = p_236730_;
        this.f_129744_ = p_236724_;
        this.f_129745_ = p_236724_.m_78301_();
        this.f_129759_ = p_236728_;
        this.f_129734_ = new ServerFunctionManager(this, this.f_129740_.f_206585_.m_206860_());
        this.f_236720_ = new StructureTemplateManager(p_236726_.f_206892_(), p_236724_, p_236728_);
        this.f_129725_ = p_236723_;
        this.f_129738_ = Util.m_183991_();
    }

    private void m_129841_(DimensionDataStorage p_129842_) {
        p_129842_.m_164861_(this.m_129896_()::m_180013_, this.m_129896_()::m_180015_, "scoreboard");
    }

    protected abstract boolean m_7038_() throws IOException;

    protected void m_130006_() {
        if (!JvmProfiler.f_185340_.m_183608_()) {
            // empty if block
        }
        boolean $$0 = false;
        ProfiledDuration $$1 = JvmProfiler.f_185340_.m_183494_();
        this.f_129749_.m_7955_(this.m_130001_(), this.m_183471_().m_184597_());
        ChunkProgressListener $$2 = this.f_129756_.m_9620_(11);
        this.m_129815_($$2);
        this.m_7044_();
        this.m_129940_($$2);
        if ($$1 != null) {
            $$1.m_185413_();
        }
        if ($$0) {
            try {
                JvmProfiler.f_185340_.m_183243_();
            }
            catch (Throwable $$3) {
                f_129750_.warn("Failed to stop JFR profiling", $$3);
            }
        }
    }

    protected void m_7044_() {
    }

    protected void m_129815_(ChunkProgressListener p_129816_) {
        ServerLevelData $$1 = this.f_129749_.m_5996_();
        WorldGenSettings $$2 = this.f_129749_.m_5961_();
        boolean $$3 = $$2.m_64668_();
        long $$4 = $$2.m_64619_();
        long $$5 = BiomeManager.m_47877_($$4);
        ImmutableList $$6 = ImmutableList.of((Object)new PhantomSpawner(), (Object)new PatrolSpawner(), (Object)new CatSpawner(), (Object)new VillageSiege(), (Object)new WanderingTraderSpawner($$1));
        Registry<LevelStem> $$7 = $$2.m_204655_();
        LevelStem $$8 = $$7.m_6246_(LevelStem.f_63971_);
        ServerLevel $$9 = new ServerLevel(this, this.f_129738_, this.f_129744_, $$1, Level.f_46428_, $$8, p_129816_, $$3, $$5, (List<CustomSpawner>)$$6, true);
        this.f_129762_.put(Level.f_46428_, $$9);
        DimensionDataStorage $$10 = $$9.m_8895_();
        this.m_129841_($$10);
        this.f_129732_ = new CommandStorage($$10);
        WorldBorder $$11 = $$9.m_6857_();
        if (!$$1.m_6535_()) {
            try {
                MinecraftServer.m_177896_($$9, $$1, $$2.m_64660_(), $$3);
                $$1.m_5555_(true);
                if ($$3) {
                    this.m_129847_(this.f_129749_);
                }
            }
            catch (Throwable $$12) {
                CrashReport $$13 = CrashReport.m_127521_($$12, "Exception initializing level");
                try {
                    $$9.m_6026_($$13);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                throw new ReportedException($$13);
            }
            $$1.m_5555_(true);
        }
        this.m_6846_().m_184209_($$9);
        if (this.f_129749_.m_6587_() != null) {
            this.m_129901_().m_136295_(this.f_129749_.m_6587_());
        }
        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> $$14 : $$7.m_6579_()) {
            ResourceKey<LevelStem> $$15 = $$14.getKey();
            if ($$15 == LevelStem.f_63971_) continue;
            ResourceKey<Level> $$16 = ResourceKey.m_135785_(Registry.f_122819_, $$15.m_135782_());
            DerivedLevelData $$17 = new DerivedLevelData(this.f_129749_, $$1);
            ServerLevel $$18 = new ServerLevel(this, this.f_129738_, this.f_129744_, $$17, $$16, $$14.getValue(), p_129816_, $$3, $$5, (List<CustomSpawner>)ImmutableList.of(), false);
            $$11.m_61929_(new BorderChangeListener.DelegateBorderChangeListener($$18.m_6857_()));
            this.f_129762_.put($$16, $$18);
        }
        $$11.m_61931_($$1.m_5813_());
    }

    private static void m_177896_(ServerLevel p_177897_, ServerLevelData p_177898_, boolean p_177899_, boolean p_177900_) {
        if (p_177900_) {
            p_177898_.m_7250_(BlockPos.f_121853_.m_6630_(80), 0.0f);
            return;
        }
        ServerChunkCache $$4 = p_177897_.m_7726_();
        ChunkPos $$5 = new ChunkPos($$4.m_214994_().m_224579_().m_183230_());
        int $$6 = $$4.m_8481_().m_142051_(p_177897_);
        if ($$6 < p_177897_.m_141937_()) {
            BlockPos $$7 = $$5.m_45615_();
            $$6 = p_177897_.m_6924_(Heightmap.Types.WORLD_SURFACE, $$7.m_123341_() + 8, $$7.m_123343_() + 8);
        }
        p_177898_.m_7250_($$5.m_45615_().m_7918_(8, $$6, 8), 0.0f);
        int $$8 = 0;
        int $$9 = 0;
        int $$10 = 0;
        int $$11 = -1;
        int $$12 = 5;
        for (int $$13 = 0; $$13 < Mth.m_144944_(11); ++$$13) {
            BlockPos $$14;
            if ($$8 >= -5 && $$8 <= 5 && $$9 >= -5 && $$9 <= 5 && ($$14 = PlayerRespawnLogic.m_183932_(p_177897_, new ChunkPos($$5.f_45578_ + $$8, $$5.f_45579_ + $$9))) != null) {
                p_177898_.m_7250_($$14, 0.0f);
                break;
            }
            if ($$8 == $$9 || $$8 < 0 && $$8 == -$$9 || $$8 > 0 && $$8 == 1 - $$9) {
                int $$15 = $$10;
                $$10 = -$$11;
                $$11 = $$15;
            }
            $$8 += $$10;
            $$9 += $$11;
        }
        if (p_177899_) {
            ConfiguredFeature<NoneFeatureConfiguration, ?> $$16 = MiscOverworldFeatures.f_195021_.m_203334_();
            $$16.m_224953_(p_177897_, $$4.m_8481_(), p_177897_.f_46441_, new BlockPos(p_177898_.m_6789_(), p_177898_.m_6527_(), p_177898_.m_6526_()));
        }
    }

    private void m_129847_(WorldData p_129848_) {
        p_129848_.m_6166_(Difficulty.PEACEFUL);
        p_129848_.m_5560_(true);
        ServerLevelData $$1 = p_129848_.m_5996_();
        $$1.m_5565_(false);
        $$1.m_5557_(false);
        $$1.m_6393_(1000000000);
        $$1.m_6247_(6000L);
        $$1.m_5458_(GameType.SPECTATOR);
    }

    private void m_129940_(ChunkProgressListener p_129941_) {
        ServerLevel $$1 = this.m_129783_();
        f_129750_.info("Preparing start region for dimension {}", (Object)$$1.m_46472_().m_135782_());
        BlockPos $$2 = $$1.m_220360_();
        p_129941_.m_7647_(new ChunkPos($$2));
        ServerChunkCache $$3 = $$1.m_7726_();
        $$3.m_7827_().m_9310_(500);
        this.f_129726_ = Util.m_137550_();
        $$3.m_8387_(TicketType.f_9442_, new ChunkPos($$2), 11, Unit.INSTANCE);
        while ($$3.m_8427_() != 441) {
            this.f_129726_ = Util.m_137550_() + 10L;
            this.m_130012_();
        }
        this.f_129726_ = Util.m_137550_() + 10L;
        this.m_130012_();
        for (ServerLevel $$4 : this.f_129762_.values()) {
            ForcedChunksSavedData $$5 = $$4.m_8895_().m_164858_(ForcedChunksSavedData::m_151483_, "chunks");
            if ($$5 == null) continue;
            LongIterator $$6 = $$5.m_46116_().iterator();
            while ($$6.hasNext()) {
                long $$7 = $$6.nextLong();
                ChunkPos $$8 = new ChunkPos($$7);
                $$4.m_7726_().m_6692_($$8, true);
            }
        }
        this.f_129726_ = Util.m_137550_() + 10L;
        this.m_130012_();
        p_129941_.m_7646_();
        $$3.m_7827_().m_9310_(5);
        this.m_129962_();
    }

    public GameType m_130008_() {
        return this.f_129749_.m_5464_();
    }

    public boolean m_7035_() {
        return this.f_129749_.m_5466_();
    }

    public abstract int m_7022_();

    public abstract int m_7034_();

    public abstract boolean m_6983_();

    public boolean m_129885_(boolean p_129886_, boolean p_129887_, boolean p_129888_) {
        boolean $$3 = false;
        for (ServerLevel $$4 : this.m_129785_()) {
            if (!p_129886_) {
                f_129750_.info("Saving chunks for level '{}'/{}", (Object)$$4, (Object)$$4.m_46472_().m_135782_());
            }
            $$4.m_8643_(null, p_129887_, $$4.f_8564_ && !p_129888_);
            $$3 = true;
        }
        ServerLevel $$5 = this.m_129783_();
        ServerLevelData $$6 = this.f_129749_.m_5996_();
        $$6.m_7831_($$5.m_6857_().m_61970_());
        this.f_129749_.m_5917_(this.m_129901_().m_136307_());
        this.f_129744_.m_78290_(this.m_206579_(), this.f_129749_, this.m_6846_().m_6960_());
        if (p_129887_) {
            for (ServerLevel $$7 : this.m_129785_()) {
                f_129750_.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", (Object)$$7.m_7726_().f_8325_.m_182285_());
            }
            f_129750_.info("ThreadedAnvilChunkStorage: All dimensions are saved");
        }
        return $$3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean m_195514_(boolean p_195515_, boolean p_195516_, boolean p_195517_) {
        try {
            this.f_195494_ = true;
            this.m_6846_().m_11302_();
            boolean bl = this.m_129885_(p_195515_, p_195516_, p_195517_);
            return bl;
        }
        finally {
            this.f_195494_ = false;
        }
    }

    @Override
    public void close() {
        this.m_7041_();
    }

    public void m_7041_() {
        if (this.f_177872_.m_142763_()) {
            this.m_236737_();
        }
        f_129750_.info("Stopping server");
        if (this.m_129919_() != null) {
            this.m_129919_().m_9718_();
        }
        this.f_195494_ = true;
        if (this.f_129763_ != null) {
            f_129750_.info("Saving players");
            this.f_129763_.m_11302_();
            this.f_129763_.m_11313_();
        }
        f_129750_.info("Saving worlds");
        for (ServerLevel $$0 : this.m_129785_()) {
            if ($$0 == null) continue;
            $$0.f_8564_ = false;
        }
        while (this.f_129762_.values().stream().anyMatch(p_202480_ -> p_202480_.m_7726_().f_8325_.m_201907_())) {
            this.f_129726_ = Util.m_137550_() + 1L;
            for (ServerLevel $$1 : this.m_129785_()) {
                $$1.m_7726_().m_201915_();
                $$1.m_7726_().m_201698_(() -> true, false);
            }
            this.m_130012_();
        }
        this.m_129885_(false, true, false);
        for (ServerLevel $$2 : this.m_129785_()) {
            if ($$2 == null) continue;
            try {
                $$2.close();
            }
            catch (IOException $$3) {
                f_129750_.error("Exception closing the level", (Throwable)$$3);
            }
        }
        this.f_195494_ = false;
        this.f_129740_.close();
        try {
            this.f_129744_.close();
        }
        catch (IOException $$4) {
            f_129750_.error("Failed to unlock level {}", (Object)this.f_129744_.m_78277_(), (Object)$$4);
        }
    }

    public String m_130009_() {
        return this.f_129760_;
    }

    public void m_129913_(String p_129914_) {
        this.f_129760_ = p_129914_;
    }

    public boolean m_130010_() {
        return this.f_129764_;
    }

    public void m_7570_(boolean p_129884_) {
        this.f_129764_ = false;
        if (p_129884_) {
            try {
                this.f_129725_.join();
            }
            catch (InterruptedException $$1) {
                f_129750_.error("Error while shutting down", (Throwable)$$1);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void m_130011_() {
        block25: {
            try {
                if (this.m_7038_()) {
                    this.f_129726_ = Util.m_137550_();
                    this.f_129757_.m_134908_(Component.m_237113_(this.f_129709_));
                    this.f_129757_.m_134912_(new ServerStatus.Version(SharedConstants.m_183709_().getName(), SharedConstants.m_183709_().getProtocolVersion()));
                    this.f_129757_.m_238078_(this.m_214000_());
                    this.f_129757_.m_242958_(this.m_214005_());
                    this.m_129878_(this.f_129757_);
                    while (this.f_129764_) {
                        long $$0 = Util.m_137550_() - this.f_129726_;
                        if ($$0 > 2000L && this.f_129726_ - this.f_129718_ >= 15000L) {
                            long $$1 = $$0 / 50L;
                            f_129750_.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", (Object)$$0, (Object)$$1);
                            this.f_129726_ += $$1 * 50L;
                            this.f_129718_ = this.f_129726_;
                        }
                        if (this.f_177877_) {
                            this.f_177877_ = false;
                            this.f_177876_ = new TimeProfiler(Util.m_137569_(), this.f_129766_);
                        }
                        this.f_129726_ += 50L;
                        this.m_177945_();
                        this.f_129754_.m_6180_("tick");
                        this.m_5705_(this::m_129960_);
                        this.f_129754_.m_6182_("nextTickWait");
                        this.f_129728_ = true;
                        this.f_129727_ = Math.max(Util.m_137550_() + 50L, this.f_129726_);
                        this.m_130012_();
                        this.f_129754_.m_7238_();
                        this.m_177946_();
                        this.f_129717_ = true;
                        JvmProfiler.f_185340_.m_183597_(this.f_129737_);
                    }
                    break block25;
                }
                throw new IllegalStateException("Failed to initialize server");
            }
            catch (Throwable $$3) {
                f_129750_.error("Encountered an unexpected exception", $$3);
                CrashReport $$4 = MinecraftServer.m_206568_($$3);
                this.m_177935_($$4.m_178626_());
                File $$5 = new File(new File(this.m_6237_(), "crash-reports"), "crash-" + Util.m_241986_() + "-server.txt");
                if ($$4.m_127512_($$5)) {
                    f_129750_.error("This crash report has been saved to: {}", (Object)$$5.getAbsolutePath());
                } else {
                    f_129750_.error("We were unable to save this crash report to disk.");
                }
                this.m_7268_($$4);
            }
            finally {
                try {
                    this.f_129765_ = true;
                    this.m_7041_();
                }
                catch (Throwable $$2) {
                    f_129750_.error("Exception stopping the server", $$2);
                }
                finally {
                    if (this.f_236721_.f_214336_() != null) {
                        this.f_236721_.f_214336_().m_196559_();
                    }
                    this.m_6988_();
                }
            }
        }
    }

    private static CrashReport m_206568_(Throwable p_206569_) {
        CrashReport $$5;
        ReportedException $$1 = null;
        for (Throwable $$2 = p_206569_; $$2 != null; $$2 = $$2.getCause()) {
            ReportedException $$3;
            if (!($$2 instanceof ReportedException)) continue;
            $$1 = $$3 = (ReportedException)$$2;
        }
        if ($$1 != null) {
            CrashReport $$4 = $$1.m_134761_();
            if ($$1 != p_206569_) {
                $$4.m_127514_("Wrapped in").m_128162_("Wrapping exception", p_206569_);
            }
        } else {
            $$5 = new CrashReport("Exception in server tick loop", p_206569_);
        }
        return $$5;
    }

    private boolean m_129960_() {
        return this.m_18767_() || Util.m_137550_() < (this.f_129728_ ? this.f_129727_ : this.f_129726_);
    }

    protected void m_130012_() {
        this.m_18699_();
        this.m_18701_(() -> !this.m_129960_());
    }

    @Override
    protected TickTask m_6681_(Runnable p_129852_) {
        return new TickTask(this.f_129766_, p_129852_);
    }

    @Override
    protected boolean m_6362_(TickTask p_129883_) {
        return p_129883_.m_136254_() + 3 < this.f_129766_ || this.m_129960_();
    }

    @Override
    public boolean m_7245_() {
        boolean $$0;
        this.f_129728_ = $$0 = this.m_129961_();
        return $$0;
    }

    private boolean m_129961_() {
        if (super.m_7245_()) {
            return true;
        }
        if (this.m_129960_()) {
            for (ServerLevel $$0 : this.m_129785_()) {
                if (!$$0.m_7726_().m_8466_()) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void m_6367_(TickTask p_129957_) {
        this.m_129905_().m_6174_("runTask");
        super.m_6367_(p_129957_);
    }

    private void m_129878_(ServerStatus p_129879_) {
        Optional<File> $$1 = Optional.of(this.m_129971_("server-icon.png")).filter(File::isFile);
        if (!$$1.isPresent()) {
            $$1 = this.f_129744_.m_182514_().map(Path::toFile).filter(File::isFile);
        }
        $$1.ifPresent(p_202470_ -> {
            try {
                BufferedImage $$2 = ImageIO.read(p_202470_);
                Validate.validState(($$2.getWidth() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                Validate.validState(($$2.getHeight() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels high", (Object[])new Object[0]);
                ByteArrayOutputStream $$3 = new ByteArrayOutputStream();
                ImageIO.write((RenderedImage)$$2, "PNG", $$3);
                byte[] $$4 = Base64.getEncoder().encode($$3.toByteArray());
                p_129879_.m_134906_("data:image/png;base64," + new String($$4, StandardCharsets.UTF_8));
            }
            catch (Exception $$5) {
                f_129750_.error("Couldn't load server icon", (Throwable)$$5);
            }
        });
    }

    public Optional<Path> m_182649_() {
        return this.f_129744_.m_182514_();
    }

    public File m_6237_() {
        return new File(".");
    }

    public void m_7268_(CrashReport p_129874_) {
    }

    public void m_6988_() {
    }

    public void m_5705_(BooleanSupplier p_129871_) {
        long $$1 = Util.m_137569_();
        ++this.f_129766_;
        this.m_5703_(p_129871_);
        if ($$1 - this.f_129724_ >= 5000000000L) {
            this.f_129724_ = $$1;
            this.f_129757_.m_134910_(new ServerStatus.Players(this.m_7418_(), this.m_7416_()));
            if (!this.m_183306_()) {
                GameProfile[] $$2 = new GameProfile[Math.min(this.m_7416_(), 12)];
                int $$3 = Mth.m_216271_(this.f_129758_, 0, this.m_7416_() - $$2.length);
                for (int $$4 = 0; $$4 < $$2.length; ++$$4) {
                    ServerPlayer $$5 = this.f_129763_.m_11314_().get($$3 + $$4);
                    $$2[$$4] = $$5.m_184128_() ? $$5.m_36316_() : f_195496_;
                }
                Collections.shuffle(Arrays.asList($$2));
                this.f_129757_.m_134914_().m_134924_($$2);
            }
        }
        if (this.f_129766_ % 6000 == 0) {
            f_129750_.debug("Autosave started");
            this.f_129754_.m_6180_("save");
            this.m_195514_(true, false, false);
            this.f_129754_.m_7238_();
            f_129750_.debug("Autosave finished");
        }
        this.f_129754_.m_6180_("tallying");
        long l = Util.m_137569_() - $$1;
        this.f_129748_[this.f_129766_ % 100] = l;
        long $$6 = l;
        this.f_129737_ = this.f_129737_ * 0.8f + (float)$$6 / 1000000.0f * 0.19999999f;
        long $$7 = Util.m_137569_();
        this.f_129735_.m_13755_($$7 - $$1);
        this.f_129754_.m_7238_();
    }

    public void m_5703_(BooleanSupplier p_129954_) {
        this.f_129754_.m_6180_("commandFunctions");
        this.m_129890_().m_136128_();
        this.f_129754_.m_6182_("levels");
        for (ServerLevel $$1 : this.m_129785_()) {
            this.f_129754_.m_6521_(() -> $$1 + " " + $$1.m_46472_().m_135782_());
            if (this.f_129766_ % 20 == 0) {
                this.f_129754_.m_6180_("timeSync");
                this.f_129763_.m_11270_(new ClientboundSetTimePacket($$1.m_46467_(), $$1.m_46468_(), $$1.m_46469_().m_46207_(GameRules.f_46140_)), $$1.m_46472_());
                this.f_129754_.m_7238_();
            }
            this.f_129754_.m_6180_("tick");
            try {
                $$1.m_8793_(p_129954_);
            }
            catch (Throwable $$2) {
                CrashReport $$3 = CrashReport.m_127521_($$2, "Exception ticking world");
                $$1.m_6026_($$3);
                throw new ReportedException($$3);
            }
            this.f_129754_.m_7238_();
            this.f_129754_.m_7238_();
        }
        this.f_129754_.m_6182_("connection");
        this.m_129919_().m_9721_();
        this.f_129754_.m_6182_("players");
        this.f_129763_.m_11288_();
        if (SharedConstants.f_136183_) {
            GameTestTicker.f_177648_.m_127790_();
        }
        this.f_129754_.m_6182_("server gui refresh");
        for (int $$4 = 0; $$4 < this.f_129752_.size(); ++$$4) {
            this.f_129752_.get($$4).run();
        }
        this.f_129754_.m_7238_();
    }

    public boolean m_7079_() {
        return true;
    }

    public void m_129946_(Runnable p_129947_) {
        this.f_129752_.add(p_129947_);
    }

    protected void m_129948_(String p_129949_) {
        this.f_129739_ = p_129949_;
    }

    public boolean m_129782_() {
        return !this.f_129725_.isAlive();
    }

    public File m_129971_(String p_129972_) {
        return new File(this.m_6237_(), p_129972_);
    }

    public final ServerLevel m_129783_() {
        return this.f_129762_.get(Level.f_46428_);
    }

    @Nullable
    public ServerLevel m_129880_(ResourceKey<Level> p_129881_) {
        return this.f_129762_.get(p_129881_);
    }

    public Set<ResourceKey<Level>> m_129784_() {
        return this.f_129762_.keySet();
    }

    public Iterable<ServerLevel> m_129785_() {
        return this.f_129762_.values();
    }

    public String m_7630_() {
        return SharedConstants.m_183709_().getName();
    }

    public int m_7416_() {
        return this.f_129763_.m_11309_();
    }

    public int m_7418_() {
        return this.f_129763_.m_11310_();
    }

    public String[] m_7641_() {
        return this.f_129763_.m_11291_();
    }

    @DontObfuscate
    public String m_130001_() {
        return f_195495_;
    }

    public SystemReport m_177935_(SystemReport p_177936_) {
        p_177936_.m_143522_("Server Running", () -> Boolean.toString(this.f_129764_));
        if (this.f_129763_ != null) {
            p_177936_.m_143522_("Player Count", () -> this.f_129763_.m_11309_() + " / " + this.f_129763_.m_11310_() + "; " + this.f_129763_.m_11314_());
        }
        p_177936_.m_143522_("Data Packs", () -> {
            StringBuilder $$0 = new StringBuilder();
            for (Pack $$1 : this.f_129730_.m_10524_()) {
                if ($$0.length() > 0) {
                    $$0.append(", ");
                }
                $$0.append($$1.m_10446_());
                if ($$1.m_10443_().m_10489_()) continue;
                $$0.append(" (incompatible)");
            }
            return $$0.toString();
        });
        p_177936_.m_143522_("World Generation", () -> this.f_129749_.m_5754_().toString());
        if (this.f_129739_ != null) {
            p_177936_.m_143522_("Server Id", () -> this.f_129739_);
        }
        return this.m_142424_(p_177936_);
    }

    public abstract SystemReport m_142424_(SystemReport var1);

    public ModCheck m_183471_() {
        return ModCheck.m_184600_(f_195495_, this::m_130001_, "Server", MinecraftServer.class);
    }

    @Override
    public void m_213846_(Component p_236736_) {
        f_129750_.info(p_236736_.getString());
    }

    public KeyPair m_129790_() {
        return this.f_129712_;
    }

    public int m_7010_() {
        return this.f_129761_;
    }

    public void m_129801_(int p_129802_) {
        this.f_129761_ = p_129802_;
    }

    @Nullable
    public GameProfile m_236731_() {
        return this.f_236719_;
    }

    public void m_236740_(@Nullable GameProfile p_236741_) {
        this.f_236719_ = p_236741_;
    }

    public boolean m_129792_() {
        return this.f_236719_ != null;
    }

    protected void m_129793_() {
        f_129750_.info("Generating keypair");
        try {
            this.f_129712_ = Crypt.m_13604_();
        }
        catch (CryptException $$0) {
            throw new IllegalStateException("Failed to generate key pair", $$0);
        }
    }

    public void m_129827_(Difficulty p_129828_, boolean p_129829_) {
        if (!p_129829_ && this.f_129749_.m_5474_()) {
            return;
        }
        this.f_129749_.m_6166_(this.f_129749_.m_5466_() ? Difficulty.HARD : p_129828_);
        this.m_129962_();
        this.m_6846_().m_11314_().forEach(this::m_129938_);
    }

    public int m_7186_(int p_129935_) {
        return p_129935_;
    }

    private void m_129962_() {
        for (ServerLevel $$0 : this.m_129785_()) {
            $$0.m_46703_(this.m_7004_(), this.m_6998_());
        }
    }

    public void m_129958_(boolean p_129959_) {
        this.f_129749_.m_5560_(p_129959_);
        this.m_6846_().m_11314_().forEach(this::m_129938_);
    }

    private void m_129938_(ServerPlayer p_129939_) {
        LevelData $$1 = p_129939_.m_9236_().m_6106_();
        p_129939_.f_8906_.m_9829_(new ClientboundChangeDifficultyPacket($$1.m_5472_(), $$1.m_5474_()));
    }

    public boolean m_7004_() {
        return this.f_129749_.m_5472_() != Difficulty.PEACEFUL;
    }

    public boolean m_129794_() {
        return this.f_129714_;
    }

    public void m_129975_(boolean p_129976_) {
        this.f_129714_ = p_129976_;
    }

    public Optional<ServerResourcePackInfo> m_214042_() {
        return Optional.empty();
    }

    public boolean m_142205_() {
        return this.m_214042_().filter(ServerResourcePackInfo::f_236745_).isPresent();
    }

    public abstract boolean m_6982_();

    public abstract int m_7032_();

    public boolean m_129797_() {
        return this.f_129705_;
    }

    public void m_129985_(boolean p_129986_) {
        this.f_129705_ = p_129986_;
    }

    public boolean m_129798_() {
        return this.f_129706_;
    }

    public void m_129993_(boolean p_129994_) {
        this.f_129706_ = p_129994_;
    }

    public boolean m_6998_() {
        return true;
    }

    public boolean m_6997_() {
        return true;
    }

    public abstract boolean m_6994_();

    public boolean m_129799_() {
        return this.f_129707_;
    }

    public void m_129997_(boolean p_129998_) {
        this.f_129707_ = p_129998_;
    }

    public boolean m_129915_() {
        return this.f_129708_;
    }

    public void m_129999_(boolean p_130000_) {
        this.f_129708_ = p_130000_;
    }

    public abstract boolean m_6993_();

    public String m_129916_() {
        return this.f_129709_;
    }

    public void m_129989_(String p_129990_) {
        this.f_129709_ = p_129990_;
    }

    public boolean m_214000_() {
        return false;
    }

    public boolean m_129918_() {
        return this.f_129765_;
    }

    public PlayerList m_6846_() {
        return this.f_129763_;
    }

    public void m_129823_(PlayerList p_129824_) {
        this.f_129763_ = p_129824_;
    }

    public abstract boolean m_6992_();

    public void m_7835_(GameType p_129832_) {
        this.f_129749_.m_5458_(p_129832_);
    }

    @Nullable
    public ServerConnectionListener m_129919_() {
        return this.f_129755_;
    }

    public boolean m_129920_() {
        return this.f_129717_;
    }

    public boolean m_6370_() {
        return false;
    }

    public boolean m_7386_(@Nullable GameType p_129833_, boolean p_129834_, int p_129835_) {
        return false;
    }

    public int m_129921_() {
        return this.f_129766_;
    }

    public int m_6396_() {
        return 16;
    }

    public boolean m_7762_(ServerLevel p_129811_, BlockPos p_129812_, Player p_129813_) {
        return false;
    }

    public boolean m_6373_() {
        return true;
    }

    public boolean m_183306_() {
        return false;
    }

    public Proxy m_177930_() {
        return this.f_129747_;
    }

    public int m_129924_() {
        return this.f_129711_;
    }

    public void m_7196_(int p_129978_) {
        this.f_129711_ = p_129978_;
    }

    public MinecraftSessionService m_129925_() {
        return this.f_236721_.f_214333_();
    }

    public SignatureValidator m_236739_() {
        return this.f_236721_.f_214334_();
    }

    public GameProfileRepository m_129926_() {
        return this.f_236721_.f_214335_();
    }

    public GameProfileCache m_129927_() {
        return this.f_236721_.f_214336_();
    }

    public ServerStatus m_129928_() {
        return this.f_129757_;
    }

    public void m_129929_() {
        this.f_129724_ = 0L;
    }

    public int m_6329_() {
        return 29999984;
    }

    @Override
    public boolean m_5660_() {
        return super.m_5660_() && !this.m_129918_();
    }

    @Override
    public void m_201446_(Runnable p_202482_) {
        if (this.m_129918_()) {
            throw new RejectedExecutionException("Server already shutting down");
        }
        super.m_201446_(p_202482_);
    }

    @Override
    public Thread m_6304_() {
        return this.f_129725_;
    }

    public int m_6328_() {
        return 256;
    }

    public boolean m_214005_() {
        return false;
    }

    public long m_129932_() {
        return this.f_129726_;
    }

    public DataFixer m_129933_() {
        return this.f_129759_;
    }

    public int m_129803_(@Nullable ServerLevel p_129804_) {
        if (p_129804_ != null) {
            return p_129804_.m_46469_().m_46215_(GameRules.f_46147_);
        }
        return 10;
    }

    public ServerAdvancementManager m_129889_() {
        return this.f_129740_.f_206585_.m_206889_();
    }

    public ServerFunctionManager m_129890_() {
        return this.f_129734_;
    }

    public CompletableFuture<Void> m_129861_(Collection<String> p_129862_) {
        RegistryAccess.Frozen $$1 = this.m_206579_();
        CompletionStage $$2 = ((CompletableFuture)CompletableFuture.supplyAsync(() -> (ImmutableList)p_129862_.stream().map(this.f_129730_::m_10507_).filter(Objects::nonNull).map(Pack::m_10445_).collect(ImmutableList.toImmutableList()), this).thenCompose(p_212913_ -> {
            MultiPackResourceManager $$2 = new MultiPackResourceManager(PackType.SERVER_DATA, (List<PackResources>)p_212913_);
            return ((CompletableFuture)ReloadableServerResources.m_206861_($$2, $$1, this.m_6982_() ? Commands.CommandSelection.DEDICATED : Commands.CommandSelection.INTEGRATED, this.m_7034_(), this.f_129738_, this).whenComplete((p_212907_, p_212908_) -> {
                if (p_212908_ != null) {
                    $$2.close();
                }
            })).thenApply(p_212904_ -> new ReloadableResources($$2, (ReloadableServerResources)p_212904_));
        })).thenAcceptAsync(p_212919_ -> {
            this.f_129740_.close();
            this.f_129740_ = p_212919_;
            this.f_129730_.m_10509_(p_129862_);
            this.f_129749_.m_6645_(MinecraftServer.m_129817_(this.f_129730_));
            this.f_129740_.f_206585_.m_206868_(this.m_206579_());
            this.m_6846_().m_11302_();
            this.m_6846_().m_11315_();
            this.f_129734_.m_136120_(this.f_129740_.f_206585_.m_206860_());
            this.f_236720_.m_230370_(this.f_129740_.f_206584_);
        }, (Executor)this);
        if (this.m_18695_()) {
            this.m_18701_(((CompletableFuture)$$2)::isDone);
        }
        return $$2;
    }

    public static DataPackConfig m_129819_(PackRepository p_129820_, DataPackConfig p_129821_, boolean p_129822_) {
        p_129820_.m_10506_();
        if (p_129822_) {
            p_129820_.m_10509_(Collections.singleton(f_195495_));
            return DataPackConfig.f_45842_;
        }
        LinkedHashSet $$3 = Sets.newLinkedHashSet();
        for (String $$4 : p_129821_.m_45850_()) {
            if (p_129820_.m_10515_($$4)) {
                $$3.add($$4);
                continue;
            }
            f_129750_.warn("Missing data pack {}", (Object)$$4);
        }
        for (Pack $$5 : p_129820_.m_10519_()) {
            String $$6 = $$5.m_10446_();
            if (p_129821_.m_45855_().contains($$6) || $$3.contains($$6)) continue;
            f_129750_.info("Found new data pack {}, loading it automatically", (Object)$$6);
            $$3.add($$6);
        }
        if ($$3.isEmpty()) {
            f_129750_.info("No datapacks selected, forcing vanilla");
            $$3.add(f_195495_);
        }
        p_129820_.m_10509_($$3);
        return MinecraftServer.m_129817_(p_129820_);
    }

    private static DataPackConfig m_129817_(PackRepository p_129818_) {
        Collection<String> $$1 = p_129818_.m_10523_();
        ImmutableList $$2 = ImmutableList.copyOf($$1);
        List $$3 = (List)p_129818_.m_10514_().stream().filter(p_212916_ -> !$$1.contains(p_212916_)).collect(ImmutableList.toImmutableList());
        return new DataPackConfig((List<String>)$$2, $$3);
    }

    public void m_129849_(CommandSourceStack p_129850_) {
        if (!this.m_129902_()) {
            return;
        }
        PlayerList $$1 = p_129850_.m_81377_().m_6846_();
        UserWhiteList $$2 = $$1.m_11305_();
        ArrayList $$3 = Lists.newArrayList($$1.m_11314_());
        for (ServerPlayer $$4 : $$3) {
            if ($$2.m_11453_($$4.m_36316_())) continue;
            $$4.f_8906_.m_9942_(Component.m_237115_("multiplayer.disconnect.not_whitelisted"));
        }
    }

    public PackRepository m_129891_() {
        return this.f_129730_;
    }

    public Commands m_129892_() {
        return this.f_129740_.f_206585_.m_206888_();
    }

    public CommandSourceStack m_129893_() {
        ServerLevel $$0 = this.m_129783_();
        return new CommandSourceStack(this, $$0 == null ? Vec3.f_82478_ : Vec3.m_82528_($$0.m_220360_()), Vec2.f_82462_, $$0, 4, "Server", Component.m_237113_("Server"), this, null);
    }

    @Override
    public boolean m_6999_() {
        return true;
    }

    @Override
    public boolean m_7028_() {
        return true;
    }

    @Override
    public abstract boolean m_6102_();

    public RecipeManager m_129894_() {
        return this.f_129740_.f_206585_.m_206887_();
    }

    public ServerScoreboard m_129896_() {
        return this.f_129731_;
    }

    public CommandStorage m_129897_() {
        if (this.f_129732_ == null) {
            throw new NullPointerException("Called before server init");
        }
        return this.f_129732_;
    }

    public LootTables m_129898_() {
        return this.f_129740_.f_206585_.m_206885_();
    }

    public PredicateManager m_129899_() {
        return this.f_129740_.f_206585_.m_206881_();
    }

    public ItemModifierManager m_177926_() {
        return this.f_129740_.f_206585_.m_206886_();
    }

    public GameRules m_129900_() {
        return this.m_129783_().m_46469_();
    }

    public CustomBossEvents m_129901_() {
        return this.f_129733_;
    }

    public boolean m_129902_() {
        return this.f_129736_;
    }

    public void m_130004_(boolean p_130005_) {
        this.f_129736_ = p_130005_;
    }

    public float m_129903_() {
        return this.f_129737_;
    }

    public int m_129944_(GameProfile p_129945_) {
        if (this.m_6846_().m_11303_(p_129945_)) {
            ServerOpListEntry $$1 = (ServerOpListEntry)this.m_6846_().m_11307_().m_11388_(p_129945_);
            if ($$1 != null) {
                return $$1.m_11363_();
            }
            if (this.m_7779_(p_129945_)) {
                return 4;
            }
            if (this.m_129792_()) {
                return this.m_6846_().m_11316_() ? 4 : 0;
            }
            return this.m_7022_();
        }
        return 0;
    }

    public FrameTimer m_129904_() {
        return this.f_129735_;
    }

    public ProfilerFiller m_129905_() {
        return this.f_129754_;
    }

    public abstract boolean m_7779_(GameProfile var1);

    public void m_142116_(Path p_177911_) throws IOException {
    }

    private void m_129859_(Path p_129860_) {
        Path $$1 = p_129860_.resolve("levels");
        try {
            for (Map.Entry<ResourceKey<Level>, ServerLevel> $$2 : this.f_129762_.entrySet()) {
                ResourceLocation $$3 = $$2.getKey().m_135782_();
                Path $$4 = $$1.resolve($$3.m_135827_()).resolve($$3.m_135815_());
                Files.createDirectories($$4, new FileAttribute[0]);
                $$2.getValue().m_8786_($$4);
            }
            this.m_129983_(p_129860_.resolve("gamerules.txt"));
            this.m_129991_(p_129860_.resolve("classpath.txt"));
            this.m_129950_(p_129860_.resolve("stats.txt"));
            this.m_129995_(p_129860_.resolve("threads.txt"));
            this.m_142116_(p_129860_.resolve("server.properties.txt"));
            this.m_195521_(p_129860_.resolve("modules.txt"));
        }
        catch (IOException $$5) {
            f_129750_.warn("Failed to save debug report", (Throwable)$$5);
        }
    }

    private void m_129950_(Path p_129951_) throws IOException {
        try (BufferedWriter $$1 = Files.newBufferedWriter(p_129951_, new OpenOption[0]);){
            $$1.write(String.format(Locale.ROOT, "pending_tasks: %d\n", this.m_18696_()));
            $$1.write(String.format(Locale.ROOT, "average_tick_time: %f\n", Float.valueOf(this.m_129903_())));
            $$1.write(String.format(Locale.ROOT, "tick_times: %s\n", Arrays.toString(this.f_129748_)));
            $$1.write(String.format(Locale.ROOT, "queue: %s\n", Util.m_183991_()));
        }
    }

    private void m_129983_(Path p_129984_) throws IOException {
        try (BufferedWriter $$1 = Files.newBufferedWriter(p_129984_, new OpenOption[0]);){
            final ArrayList $$2 = Lists.newArrayList();
            final GameRules $$3 = this.m_129900_();
            GameRules.m_46164_(new GameRules.GameRuleTypeVisitor(){

                @Override
                public <T extends GameRules.Value<T>> void m_6889_(GameRules.Key<T> p_195531_, GameRules.Type<T> p_195532_) {
                    $$2.add(String.format(Locale.ROOT, "%s=%s\n", p_195531_.m_46328_(), $$3.m_46170_(p_195531_)));
                }
            });
            for (String $$4 : $$2) {
                $$1.write($$4);
            }
        }
    }

    private void m_129991_(Path p_129992_) throws IOException {
        try (BufferedWriter $$1 = Files.newBufferedWriter(p_129992_, new OpenOption[0]);){
            String $$2 = System.getProperty("java.class.path");
            String $$3 = System.getProperty("path.separator");
            for (String $$4 : Splitter.on((String)$$3).split((CharSequence)$$2)) {
                $$1.write($$4);
                $$1.write("\n");
            }
        }
    }

    private void m_129995_(Path p_129996_) throws IOException {
        ThreadMXBean $$1 = ManagementFactory.getThreadMXBean();
        ThreadInfo[] $$2 = $$1.dumpAllThreads(true, true);
        Arrays.sort($$2, Comparator.comparing(ThreadInfo::getThreadName));
        try (BufferedWriter $$3 = Files.newBufferedWriter(p_129996_, new OpenOption[0]);){
            for (ThreadInfo $$4 : $$2) {
                $$3.write($$4.toString());
                ((Writer)$$3).write(10);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private void m_195521_(Path p_195522_) throws IOException {
        BufferedWriter $$1 = Files.newBufferedWriter(p_195522_, new OpenOption[0]);
        try {
            void $$4;
            try {
                ArrayList $$2 = Lists.newArrayList(NativeModuleLister.m_184666_());
            }
            catch (Throwable $$3) {
                f_129750_.warn("Failed to list native modules", $$3);
                if ($$1 != null) {
                    ((Writer)$$1).close();
                }
                return;
            }
            $$4.sort(Comparator.comparing(p_212910_ -> p_212910_.f_184690_));
            for (NativeModuleLister.NativeModuleInfo $$5 : $$4) {
                $$1.write($$5.toString());
                ((Writer)$$1).write(10);
            }
        }
        finally {
            if ($$1 != null) {
                try {
                    ((Writer)$$1).close();
                }
                catch (Throwable throwable) {
                    Throwable throwable2;
                    throwable2.addSuppressed(throwable);
                }
            }
        }
    }

    private void m_177945_() {
        if (this.f_177875_) {
            this.f_177872_ = ActiveMetricsRecorder.m_146132_(new ServerMetricsSamplersProvider(Util.f_137440_, this.m_6982_()), Util.f_137440_, Util.m_183992_(), new MetricsPersister("server"), this.f_177873_, p_212927_ -> {
                this.m_18709_(() -> this.m_129859_(p_212927_.resolve("server")));
                this.f_177874_.accept((Path)p_212927_);
            });
            this.f_177875_ = false;
        }
        this.f_129754_ = SingleTickProfiler.m_18629_(this.f_177872_.m_142610_(), SingleTickProfiler.m_18632_("Server"));
        this.f_177872_.m_142759_();
        this.f_129754_.m_7242_();
    }

    private void m_177946_() {
        this.f_129754_.m_7241_();
        this.f_177872_.m_142758_();
    }

    public boolean m_177927_() {
        return this.f_177872_.m_142763_();
    }

    public void m_177923_(Consumer<ProfileResults> p_177924_, Consumer<Path> p_177925_) {
        this.f_177873_ = p_212922_ -> {
            this.m_177928_();
            p_177924_.accept((ProfileResults)p_212922_);
        };
        this.f_177874_ = p_177925_;
        this.f_177875_ = true;
    }

    public void m_177928_() {
        this.f_177872_ = InactiveMetricsRecorder.f_146153_;
    }

    public void m_177929_() {
        this.f_177872_.m_142760_();
    }

    public void m_236737_() {
        this.f_177872_.m_213832_();
        this.f_129754_ = this.f_177872_.m_142610_();
    }

    public Path m_129843_(LevelResource p_129844_) {
        return this.f_129744_.m_78283_(p_129844_);
    }

    public boolean m_6365_() {
        return true;
    }

    public StructureTemplateManager m_236738_() {
        return this.f_236720_;
    }

    public WorldData m_129910_() {
        return this.f_129749_;
    }

    public RegistryAccess.Frozen m_206579_() {
        return this.f_129746_;
    }

    public TextFilter m_7950_(ServerPlayer p_129814_) {
        return TextFilter.f_143703_;
    }

    public ServerPlayerGameMode m_177933_(ServerPlayer p_177934_) {
        return this.m_129794_() ? new DemoMode(p_177934_) : new ServerPlayerGameMode(p_177934_);
    }

    @Nullable
    public GameType m_142359_() {
        return null;
    }

    public ResourceManager m_177941_() {
        return this.f_129740_.f_206584_;
    }

    public boolean m_195518_() {
        return this.f_195494_;
    }

    public boolean m_177942_() {
        return this.f_177877_ || this.f_177876_ != null;
    }

    public void m_177943_() {
        this.f_177877_ = true;
    }

    public ProfileResults m_177944_() {
        if (this.f_177876_ == null) {
            return EmptyProfileResults.f_18441_;
        }
        ProfileResults $$0 = this.f_177876_.m_177960_(Util.m_137569_(), this.f_129766_);
        this.f_177876_ = null;
        return $$0;
    }

    public int m_213994_() {
        return 1000000;
    }

    public void m_241158_(Component p_241503_, ChatType.Bound p_241402_, @Nullable String p_241481_) {
        String $$3 = p_241402_.m_240977_(p_241503_).getString();
        if (p_241481_ != null) {
            f_129750_.info("[{}] {}", (Object)p_241481_, (Object)$$3);
        } else {
            f_129750_.info("{}", (Object)$$3);
        }
    }

    public ChatDecorator m_236742_() {
        return ChatDecorator.f_236947_;
    }

    @Override
    public /* synthetic */ void m_6367_(Runnable runnable) {
        this.m_6367_((TickTask)runnable);
    }

    @Override
    public /* synthetic */ boolean m_6362_(Runnable runnable) {
        return this.m_6362_((TickTask)runnable);
    }

    @Override
    public /* synthetic */ Runnable m_6681_(Runnable runnable) {
        return this.m_6681_(runnable);
    }

    record ReloadableResources(CloseableResourceManager f_206584_, ReloadableServerResources f_206585_) implements AutoCloseable
    {
        @Override
        public void close() {
            this.f_206584_.close();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ReloadableResources.class, "resourceManager;managers", "f_206584_", "f_206585_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ReloadableResources.class, "resourceManager;managers", "f_206584_", "f_206585_"}, this);
        }

        @Override
        public final boolean equals(Object p_206593_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ReloadableResources.class, "resourceManager;managers", "f_206584_", "f_206585_"}, this, p_206593_);
        }
    }

    static class TimeProfiler {
        final long f_177955_;
        final int f_177956_;

        TimeProfiler(long p_177958_, int p_177959_) {
            this.f_177955_ = p_177958_;
            this.f_177956_ = p_177959_;
        }

        ProfileResults m_177960_(final long p_177961_, final int p_177962_) {
            return new ProfileResults(){

                @Override
                public List<ResultField> m_6412_(String p_177972_) {
                    return Collections.emptyList();
                }

                @Override
                public boolean m_142444_(Path p_177974_) {
                    return false;
                }

                @Override
                public long m_7229_() {
                    return f_177955_;
                }

                @Override
                public int m_7230_() {
                    return f_177956_;
                }

                @Override
                public long m_7236_() {
                    return p_177961_;
                }

                @Override
                public int m_7317_() {
                    return p_177962_;
                }

                @Override
                public String m_142368_() {
                    return "";
                }
            };
        }
    }

    public record ServerResourcePackInfo(String f_236743_, String f_236744_, boolean f_236745_, @Nullable Component f_236746_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ServerResourcePackInfo.class, "url;hash;isRequired;prompt", "f_236743_", "f_236744_", "f_236745_", "f_236746_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ServerResourcePackInfo.class, "url;hash;isRequired;prompt", "f_236743_", "f_236744_", "f_236745_", "f_236746_"}, this);
        }

        @Override
        public final boolean equals(Object p_236757_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ServerResourcePackInfo.class, "url;hash;isRequired;prompt", "f_236743_", "f_236744_", "f_236745_", "f_236746_"}, this, p_236757_);
        }
    }
}

