/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Queues
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.minecraft.BanDetails
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.minecraft.UserApiService
 *  com.mojang.authlib.minecraft.UserApiService$UserFlag
 *  com.mojang.authlib.properties.PropertyMap
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2BooleanFunction
 *  javax.annotation.Nullable
 *  org.apache.commons.io.FileUtils
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Queues;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.GlDebug;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.platform.MacosUtil;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.TimerQuery;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix4f;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.FileUtil;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.client.AmbientOcclusionStatus;
import net.minecraft.client.CameraType;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.ClientTelemetryManager;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Game;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.HotbarManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.client.PeriodicNotificationManager;
import net.minecraft.client.Realms32BitWarningStatus;
import net.minecraft.client.ResourceLoadStateTracker;
import net.minecraft.client.Screenshot;
import net.minecraft.client.Timer;
import net.minecraft.client.User;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.screens.BanNoticeScreen;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.OutOfMemoryScreen;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.ProfileKeyPairManager;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.profiling.ClientMetricsSamplersProvider;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.VirtualScreen;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.LegacyPackResourcesAdapter;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.client.resources.PackResourcesAdapterV4;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.searchtree.FullTextSearchTree;
import net.minecraft.client.searchtree.IdSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.KeybindResolver;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.ProcessorChunkProgressListener;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FileZipper;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.ModCheck;
import net.minecraft.util.Mth;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Unit;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.profiling.ContinuousProfiler;
import net.minecraft.util.profiling.EmptyProfileResults;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.io.FileUtils;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;

public class Minecraft
extends ReentrantBlockableEventLoop<Runnable>
implements WindowEventHandler {
    static Minecraft f_90981_;
    private static final Logger f_90982_;
    public static final boolean f_91002_;
    private static final int f_167843_ = 10;
    public static final ResourceLocation f_91055_;
    public static final ResourceLocation f_91058_;
    public static final ResourceLocation f_91059_;
    private static final ResourceLocation f_205119_;
    private static final CompletableFuture<Unit> f_90983_;
    private static final Component f_90984_;
    public static final String f_167848_ = "Please make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).";
    private final File f_90985_;
    private final PropertyMap f_90986_;
    private final TextureManager f_90987_;
    private final DataFixer f_90988_;
    private final VirtualScreen f_90989_;
    private final Window f_90990_;
    private final Timer f_90991_ = new Timer(20.0f, 0L);
    private final RenderBuffers f_90993_;
    public final LevelRenderer f_91060_;
    private final EntityRenderDispatcher f_90994_;
    private final ItemRenderer f_90995_;
    public final ParticleEngine f_91061_;
    private final SearchRegistry f_90997_ = new SearchRegistry();
    private final User f_90998_;
    public final Font f_91062_;
    public final Font f_243022_;
    public final GameRenderer f_91063_;
    public final DebugRenderer f_91064_;
    private final AtomicReference<StoringChunkProgressListener> f_90999_ = new AtomicReference();
    public final Gui f_91065_;
    public final Options f_91066_;
    private final HotbarManager f_91000_;
    public final MouseHandler f_91067_;
    public final KeyboardHandler f_91068_;
    public final File f_91069_;
    private final String f_91001_;
    private final String f_91029_;
    private final Proxy f_91030_;
    private final LevelStorageSource f_91031_;
    public final FrameTimer f_91070_ = new FrameTimer();
    private final boolean f_91032_;
    private final boolean f_91033_;
    private final boolean f_91034_;
    private final boolean f_91035_;
    private final ReloadableResourceManager f_91036_;
    private final ClientPackSource f_91037_;
    private final PackRepository f_91038_;
    private final LanguageManager f_91039_;
    private final BlockColors f_91040_;
    private final ItemColors f_91041_;
    private final RenderTarget f_91042_;
    private final SoundManager f_91043_;
    private final MusicManager f_91044_;
    private final FontManager f_91045_;
    private final SplashManager f_91046_;
    private final GpuWarnlistManager f_91047_;
    private final PeriodicNotificationManager f_205120_ = new PeriodicNotificationManager(f_205119_, (Object2BooleanFunction<String>)((Object2BooleanFunction)Minecraft::m_210782_));
    private final YggdrasilAuthenticationService f_231338_;
    private final MinecraftSessionService f_91048_;
    private final SignatureValidator f_231339_;
    private final UserApiService f_193584_;
    private final SkinManager f_91050_;
    private final ModelManager f_91051_;
    private final BlockRenderDispatcher f_91052_;
    private final PaintingTextureManager f_91053_;
    private final MobEffectTextureManager f_91054_;
    private final ToastComponent f_91003_;
    private final Game f_91004_ = new Game(this);
    private final Tutorial f_91005_;
    private final PlayerSocialManager f_91006_;
    private final EntityModelSet f_167844_;
    private final BlockEntityRenderDispatcher f_167845_;
    private final UUID f_193582_ = UUID.randomUUID();
    private final ProfileKeyPairManager f_231337_;
    private final RealmsDataFetcher f_238717_;
    @Nullable
    public MultiPlayerGameMode f_91072_;
    @Nullable
    public ClientLevel f_91073_;
    @Nullable
    public LocalPlayer f_91074_;
    @Nullable
    private IntegratedServer f_91007_;
    @Nullable
    private ServerData f_91008_;
    @Nullable
    private Connection f_91009_;
    private boolean f_91010_;
    @Nullable
    public Entity f_91075_;
    @Nullable
    public Entity f_91076_;
    @Nullable
    public HitResult f_91077_;
    private int f_91011_;
    protected int f_91078_;
    private volatile boolean f_91012_;
    private float f_91013_;
    private long f_91014_ = Util.m_137569_();
    private long f_91015_;
    private int f_91016_;
    public boolean f_91079_;
    @Nullable
    public Screen f_91080_;
    @Nullable
    private Overlay f_91081_;
    private boolean f_91017_;
    private Thread f_91018_;
    private volatile boolean f_91019_;
    @Nullable
    private Supplier<CrashReport> f_91020_;
    private static int f_91021_;
    public String f_90977_ = "";
    public boolean f_167842_;
    public boolean f_90978_;
    public boolean f_90979_;
    public boolean f_90980_ = true;
    private boolean f_91022_;
    private final Queue<Runnable> f_91023_ = Queues.newConcurrentLinkedQueue();
    @Nullable
    private CompletableFuture<Void> f_91024_;
    @Nullable
    private TutorialToast f_91025_;
    private ProfilerFiller f_91026_ = InactiveProfiler.f_18554_;
    private int f_91027_;
    private final ContinuousProfiler f_91028_ = new ContinuousProfiler(Util.f_137440_, () -> this.f_91027_);
    @Nullable
    private ProfileResults f_91056_;
    private MetricsRecorder f_167846_ = InactiveMetricsRecorder.f_146153_;
    private final ResourceLoadStateTracker f_167847_ = new ResourceLoadStateTracker();
    private long f_231340_;
    private double f_231341_;
    @Nullable
    private TimerQuery.FrameProfile f_231342_;
    private final Realms32BitWarningStatus f_231343_;
    private final GameNarrator f_240365_;
    private final ChatListener f_240378_;
    private ReportingContext f_238638_;
    private String f_91057_ = "root";

    public Minecraft(GameConfig p_91084_) {
        super("Client");
        DisplayData $$7;
        int $$5;
        String $$4;
        f_90981_ = this;
        this.f_91069_ = p_91084_.f_101907_.f_101916_;
        File $$1 = p_91084_.f_101907_.f_101918_;
        this.f_90985_ = p_91084_.f_101907_.f_101917_;
        this.f_91001_ = p_91084_.f_101908_.f_101927_;
        this.f_91029_ = p_91084_.f_101908_.f_101928_;
        this.f_90986_ = p_91084_.f_101905_.f_101944_;
        this.f_91037_ = new ClientPackSource(new File(this.f_91069_, "server-resource-packs"), p_91084_.f_101907_.m_101925_());
        this.f_91038_ = new PackRepository(Minecraft::m_167933_, this.f_91037_, new FolderRepositorySource(this.f_90985_, PackSource.f_10527_));
        this.f_91030_ = p_91084_.f_101905_.f_101945_;
        this.f_231338_ = new YggdrasilAuthenticationService(this.f_91030_);
        this.f_91048_ = this.f_231338_.createMinecraftSessionService();
        this.f_193584_ = this.m_193585_(this.f_231338_, p_91084_);
        this.f_231339_ = SignatureValidator.m_216358_(this.f_231338_.getServicesKey());
        this.f_90998_ = p_91084_.f_101905_.f_101942_;
        f_90982_.info("Setting user: {}", (Object)this.f_90998_.m_92546_());
        f_90982_.debug("(Session ID is {})", (Object)this.f_90998_.m_92544_());
        this.f_91033_ = p_91084_.f_101908_.f_101926_;
        this.f_91034_ = !p_91084_.f_101908_.f_101929_;
        this.f_91035_ = !p_91084_.f_101908_.f_101930_;
        this.f_91032_ = Minecraft.m_91272_();
        this.f_91007_ = null;
        if (this.m_91400_() && p_91084_.f_101909_.f_101937_ != null) {
            String $$2 = p_91084_.f_101909_.f_101937_;
            int $$3 = p_91084_.f_101909_.f_101938_;
        } else {
            $$4 = null;
            $$5 = 0;
        }
        KeybindResolver.m_237364_(KeyMapping::m_90842_);
        this.f_90988_ = DataFixers.m_14512_();
        this.f_91003_ = new ToastComponent(this);
        this.f_91018_ = Thread.currentThread();
        this.f_91066_ = new Options(this, this.f_91069_);
        this.f_91019_ = true;
        this.f_91005_ = new Tutorial(this, this.f_91066_);
        this.f_91000_ = new HotbarManager(this.f_91069_, this.f_90988_);
        f_90982_.info("Backend library: {}", (Object)RenderSystem.m_69517_());
        if (this.f_91066_.f_92129_ > 0 && this.f_91066_.f_92128_ > 0) {
            DisplayData $$6 = new DisplayData(this.f_91066_.f_92128_, this.f_91066_.f_92129_, p_91084_.f_101906_.f_84007_, p_91084_.f_101906_.f_84008_, p_91084_.f_101906_.f_84009_);
        } else {
            $$7 = p_91084_.f_101906_;
        }
        Util.f_137440_ = RenderSystem.m_240147_();
        this.f_90989_ = new VirtualScreen(this);
        this.f_90990_ = this.f_90989_.m_110872_($$7, this.f_91066_.f_92123_, this.m_91270_());
        this.m_7440_(true);
        try {
            if (f_91002_) {
                InputStream $$8 = this.m_91100_().m_118555_().m_8031_(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/minecraft.icns"));
                MacosUtil.m_231133_($$8);
            } else {
                InputStream $$9 = this.m_91100_().m_118555_().m_8031_(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_16x16.png"));
                InputStream $$10 = this.m_91100_().m_118555_().m_8031_(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_32x32.png"));
                this.f_90990_.m_85395_($$9, $$10);
            }
        }
        catch (IOException $$11) {
            f_90982_.error("Couldn't set icon", (Throwable)$$11);
        }
        this.f_90990_.m_85380_(this.f_91066_.m_232035_().m_231551_());
        this.f_91067_ = new MouseHandler(this);
        this.f_91067_.m_91524_(this.f_90990_.m_85439_());
        this.f_91068_ = new KeyboardHandler(this);
        this.f_91068_.m_90887_(this.f_90990_.m_85439_());
        RenderSystem.m_69580_(this.f_91066_.f_92035_, false);
        this.f_91042_ = new MainTarget(this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
        this.f_91042_.m_83931_(0.0f, 0.0f, 0.0f, 0.0f);
        this.f_91042_.m_83954_(f_91002_);
        this.f_91036_ = new ReloadableResourceManager(PackType.CLIENT_RESOURCES);
        this.f_91038_.m_10506_();
        this.f_91066_.m_92145_(this.f_91038_);
        this.f_91039_ = new LanguageManager(this.f_91066_.f_92075_);
        this.f_91036_.m_7217_(this.f_91039_);
        this.f_90987_ = new TextureManager(this.f_91036_);
        this.f_91036_.m_7217_(this.f_90987_);
        this.f_91050_ = new SkinManager(this.f_90987_, new File($$1, "skins"), this.f_91048_);
        this.f_91031_ = new LevelStorageSource(this.f_91069_.toPath().resolve("saves"), this.f_91069_.toPath().resolve("backups"), this.f_90988_);
        this.f_91043_ = new SoundManager(this.f_91036_, this.f_91066_);
        this.f_91036_.m_7217_(this.f_91043_);
        this.f_91046_ = new SplashManager(this.f_90998_);
        this.f_91036_.m_7217_(this.f_91046_);
        this.f_91044_ = new MusicManager(this);
        this.f_91045_ = new FontManager(this.f_90987_);
        this.f_91062_ = this.f_91045_.m_95006_();
        this.f_243022_ = this.f_91045_.m_243082_();
        this.f_91036_.m_7217_(this.f_91045_.m_95015_());
        this.m_91336_(this.m_91390_());
        this.f_91036_.m_7217_(new GrassColorReloadListener());
        this.f_91036_.m_7217_(new FoliageColorReloadListener());
        this.f_90990_.m_85403_("Startup");
        RenderSystem.m_69902_(0, 0, this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
        this.f_90990_.m_85403_("Post startup");
        this.f_91040_ = BlockColors.m_92574_();
        this.f_91041_ = ItemColors.m_92683_(this.f_91040_);
        this.f_91051_ = new ModelManager(this.f_90987_, this.f_91040_, this.f_91066_.m_232119_().m_231551_());
        this.f_91036_.m_7217_(this.f_91051_);
        this.f_167844_ = new EntityModelSet();
        this.f_91036_.m_7217_(this.f_167844_);
        this.f_167845_ = new BlockEntityRenderDispatcher(this.f_91062_, this.f_167844_, this::m_91289_, this::m_91291_, this::m_91290_);
        this.f_91036_.m_7217_(this.f_167845_);
        BlockEntityWithoutLevelRenderer $$12 = new BlockEntityWithoutLevelRenderer(this.f_167845_, this.f_167844_);
        this.f_91036_.m_7217_($$12);
        this.f_90995_ = new ItemRenderer(this.f_90987_, this.f_91051_, this.f_91041_, $$12);
        this.f_91036_.m_7217_(this.f_90995_);
        this.f_90993_ = new RenderBuffers();
        this.f_91006_ = new PlayerSocialManager(this, this.f_193584_);
        this.f_91052_ = new BlockRenderDispatcher(this.f_91051_.m_119430_(), $$12, this.f_91040_);
        this.f_91036_.m_7217_(this.f_91052_);
        this.f_90994_ = new EntityRenderDispatcher(this, this.f_90987_, this.f_90995_, this.f_91052_, this.f_91062_, this.f_91066_, this.f_167844_);
        this.f_91036_.m_7217_(this.f_90994_);
        this.f_91063_ = new GameRenderer(this, this.f_90994_.m_234586_(), this.f_91036_, this.f_90993_);
        this.f_91036_.m_7217_(this.f_91063_);
        this.f_91060_ = new LevelRenderer(this, this.f_90994_, this.f_167845_, this.f_90993_);
        this.f_91036_.m_7217_(this.f_91060_);
        this.m_91271_();
        this.f_91036_.m_7217_(this.f_90997_);
        this.f_91061_ = new ParticleEngine(this.f_91073_, this.f_90987_);
        this.f_91036_.m_7217_(this.f_91061_);
        this.f_91053_ = new PaintingTextureManager(this.f_90987_);
        this.f_91036_.m_7217_(this.f_91053_);
        this.f_91054_ = new MobEffectTextureManager(this.f_90987_);
        this.f_91036_.m_7217_(this.f_91054_);
        this.f_91047_ = new GpuWarnlistManager();
        this.f_91036_.m_7217_(this.f_91047_);
        this.f_91036_.m_7217_(this.f_205120_);
        this.f_91065_ = new Gui(this, this.f_90995_);
        this.f_91064_ = new DebugRenderer(this);
        this.f_238717_ = new RealmsDataFetcher(RealmsClient.m_239151_(this));
        RenderSystem.m_69900_(this::m_91113_);
        if (this.f_91042_.f_83915_ != this.f_90990_.m_85441_() || this.f_91042_.f_83916_ != this.f_90990_.m_85442_()) {
            StringBuilder $$13 = new StringBuilder("Recovering from unsupported resolution (" + this.f_90990_.m_85441_() + "x" + this.f_90990_.m_85442_() + ").\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).");
            if (GlDebug.m_166226_()) {
                $$13.append("\n\nReported GL debug messages:\n").append(String.join((CharSequence)"\n", GlDebug.m_166225_()));
            }
            this.f_90990_.m_166447_(this.f_91042_.f_83915_, this.f_91042_.f_83916_);
            TinyFileDialogs.tinyfd_messageBox((CharSequence)"Minecraft", (CharSequence)$$13.toString(), (CharSequence)"ok", (CharSequence)"error", (boolean)false);
        } else if (this.f_91066_.m_231829_().m_231551_().booleanValue() && !this.f_90990_.m_85440_()) {
            this.f_90990_.m_85438_();
            this.f_91066_.m_231829_().m_231514_(this.f_90990_.m_85440_());
        }
        this.f_90990_.m_85409_(this.f_91066_.m_231817_().m_231551_());
        this.f_90990_.m_85424_(this.f_91066_.m_232123_().m_231551_());
        this.f_90990_.m_85426_();
        this.m_5741_();
        this.f_91063_.m_172722_(this.m_91100_().m_118555_().m_215363_());
        this.f_231337_ = new ProfileKeyPairManager(this.f_193584_, this.f_90998_.m_92548_().getId(), this.f_91069_.toPath());
        this.f_231343_ = new Realms32BitWarningStatus(this);
        this.f_240365_ = new GameNarrator(this);
        this.f_240378_ = new ChatListener(this);
        this.f_240378_.m_240692_(this.f_91066_.m_232118_().m_231551_());
        this.f_238638_ = ReportingContext.m_239685_(ReportEnvironment.m_239898_(), this.f_193584_);
        LoadingOverlay.m_96189_(this);
        List<PackResources> $$14 = this.f_91038_.m_10525_();
        this.f_167847_.m_168557_(ResourceLoadStateTracker.ReloadReason.INITIAL, $$14);
        ReloadInstance $$15 = this.f_91036_.m_142463_(Util.m_183991_(), this, f_90983_, $$14);
        this.m_91150_(new LoadingOverlay(this, $$15, p_210745_ -> Util.m_137521_(p_210745_, this::m_91239_, () -> {
            if (SharedConstants.f_136183_) {
                this.m_91273_();
            }
            this.f_167847_.m_168556_();
        }), false));
        if ($$4 != null) {
            $$15.m_7237_().thenRunAsync(() -> ConnectScreen.m_169267_(new TitleScreen(), this, new ServerAddress($$4, $$5), null), this);
        } else if (this.m_239929_()) {
            this.m_91152_(BanNoticeScreen.m_239967_(p_238185_ -> {
                if (p_238185_) {
                    Util.m_137581_().m_137646_("https://aka.ms/mcjavamoderation");
                }
                this.m_91152_(new TitleScreen(true));
            }, this.m_239210_()));
        } else {
            this.m_91152_(new TitleScreen(true));
        }
    }

    private static boolean m_210782_(Object p_210783_) {
        try {
            return Locale.getDefault().getISO3Country().equals(p_210783_);
        }
        catch (MissingResourceException $$1) {
            return false;
        }
    }

    public void m_91341_() {
        this.f_90990_.m_85422_(this.m_91270_());
    }

    private String m_91270_() {
        StringBuilder $$0 = new StringBuilder("Minecraft");
        if (Minecraft.m_193589_().m_184597_()) {
            $$0.append("*");
        }
        $$0.append(" ");
        $$0.append(SharedConstants.m_183709_().getName());
        ClientPacketListener $$1 = this.m_91403_();
        if ($$1 != null && $$1.m_6198_().m_129536_()) {
            $$0.append(" - ");
            if (this.f_91007_ != null && !this.f_91007_.m_6992_()) {
                $$0.append(I18n.m_118938_("title.singleplayer", new Object[0]));
            } else if (this.m_91294_()) {
                $$0.append(I18n.m_118938_("title.multiplayer.realms", new Object[0]));
            } else if (this.f_91007_ != null || this.f_91008_ != null && this.f_91008_.m_105389_()) {
                $$0.append(I18n.m_118938_("title.multiplayer.lan", new Object[0]));
            } else {
                $$0.append(I18n.m_118938_("title.multiplayer.other", new Object[0]));
            }
        }
        return $$0.toString();
    }

    private UserApiService m_193585_(YggdrasilAuthenticationService p_193586_, GameConfig p_193587_) {
        try {
            return p_193586_.createUserApiService(p_193587_.f_101905_.f_101942_.m_92547_());
        }
        catch (AuthenticationException $$2) {
            f_90982_.error("Failed to verify authentication", (Throwable)$$2);
            return UserApiService.OFFLINE;
        }
    }

    public static ModCheck m_193589_() {
        return ModCheck.m_184600_("vanilla", ClientBrandRetriever::m_129629_, "Client", Minecraft.class);
    }

    private void m_91239_(Throwable p_91240_) {
        if (this.f_91038_.m_10523_().size() > 1) {
            this.m_91241_(p_91240_, null);
        } else {
            Util.m_137559_(p_91240_);
        }
    }

    public void m_91241_(Throwable p_91242_, @Nullable Component p_91243_) {
        f_90982_.info("Caught error loading resourcepacks, removing all selected resourcepacks", p_91242_);
        this.f_167847_.m_168560_(p_91242_);
        this.f_91038_.m_10509_(Collections.emptyList());
        this.f_91066_.f_92117_.clear();
        this.f_91066_.f_92118_.clear();
        this.f_91066_.m_92169_();
        this.m_168019_(true).thenRun(() -> {
            ToastComponent $$1 = this.m_91300_();
            SystemToast.m_94869_($$1, SystemToast.SystemToastIds.PACK_LOAD_FAILURE, Component.m_237115_("resourcePack.load_fail"), p_91243_);
        });
    }

    public void m_91374_() {
        this.f_91018_ = Thread.currentThread();
        if (Runtime.getRuntime().availableProcessors() > 4) {
            this.f_91018_.setPriority(10);
        }
        try {
            boolean $$0 = false;
            while (this.f_91019_) {
                if (this.f_91020_ != null) {
                    Minecraft.m_91332_(this.f_91020_.get());
                    return;
                }
                try {
                    SingleTickProfiler $$1 = SingleTickProfiler.m_18632_("Renderer");
                    boolean $$2 = this.m_91274_();
                    this.f_91026_ = this.m_167970_($$2, $$1);
                    this.f_91026_.m_7242_();
                    this.f_167846_.m_142759_();
                    this.m_91383_(!$$0);
                    this.f_167846_.m_142758_();
                    this.f_91026_.m_7241_();
                    this.m_91338_($$2, $$1);
                }
                catch (OutOfMemoryError $$3) {
                    if ($$0) {
                        throw $$3;
                    }
                    this.m_91394_();
                    this.m_91152_(new OutOfMemoryScreen());
                    System.gc();
                    f_90982_.error(LogUtils.FATAL_MARKER, "Out of memory", (Throwable)$$3);
                    $$0 = true;
                }
            }
        }
        catch (ReportedException $$4) {
            this.m_91354_($$4.m_134761_());
            this.m_91394_();
            f_90982_.error(LogUtils.FATAL_MARKER, "Reported exception thrown!", (Throwable)$$4);
            Minecraft.m_91332_($$4.m_134761_());
        }
        catch (Throwable $$5) {
            CrashReport $$6 = this.m_91354_(new CrashReport("Unexpected error", $$5));
            f_90982_.error(LogUtils.FATAL_MARKER, "Unreported exception thrown!", $$5);
            this.m_91394_();
            Minecraft.m_91332_($$6);
        }
    }

    void m_91336_(boolean p_91337_) {
        this.f_91045_.m_95011_((Map<ResourceLocation, ResourceLocation>)(p_91337_ ? ImmutableMap.of((Object)f_91055_, (Object)f_91058_) : ImmutableMap.of()));
    }

    private void m_91271_() {
        this.f_90997_.m_235232_(SearchRegistry.f_119941_, p_231451_ -> new FullTextSearchTree<ItemStack>(p_210797_ -> p_210797_.m_41651_(null, TooltipFlag.Default.NORMAL).stream().map(p_210807_ -> ChatFormatting.m_126649_(p_210807_.getString()).trim()).filter(p_210809_ -> !p_210809_.isEmpty()), p_91317_ -> Stream.of(Registry.f_122827_.m_7981_(p_91317_.m_41720_())), (List<ItemStack>)p_231451_));
        this.f_90997_.m_235232_(SearchRegistry.f_119942_, p_231430_ -> new IdSearchTree<ItemStack>(p_231353_ -> p_231353_.m_204131_().map(TagKey::f_203868_), (List<ItemStack>)p_231430_));
        this.f_90997_.m_235232_(SearchRegistry.f_119943_, p_231389_ -> new FullTextSearchTree<RecipeCollection>(p_231423_ -> p_231423_.m_100516_().stream().flatMap(p_231421_ -> p_231421_.m_8043_().m_41651_(null, TooltipFlag.Default.NORMAL).stream()).map(p_231455_ -> ChatFormatting.m_126649_(p_231455_.getString()).trim()).filter(p_231449_ -> !p_231449_.isEmpty()), p_231369_ -> p_231369_.m_100516_().stream().map(p_238858_ -> Registry.f_122827_.m_7981_(p_238858_.m_8043_().m_41720_())), (List<RecipeCollection>)p_231389_));
    }

    private void m_91113_(int p_91114_, long p_91115_) {
        this.f_91066_.m_231817_().m_231514_(false);
        this.f_91066_.m_92169_();
    }

    private static boolean m_91272_() {
        String[] $$0;
        for (String $$1 : $$0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"}) {
            String $$2 = System.getProperty($$1);
            if ($$2 == null || !$$2.contains("64")) continue;
            return true;
        }
        return false;
    }

    public RenderTarget m_91385_() {
        return this.f_91042_;
    }

    public String m_91388_() {
        return this.f_91001_;
    }

    public String m_91389_() {
        return this.f_91029_;
    }

    public void m_231412_(CrashReport p_231413_) {
        this.f_91020_ = () -> this.m_91354_(p_231413_);
    }

    public void m_231439_(CrashReport p_231440_) {
        this.f_91020_ = () -> p_231440_;
    }

    public static void m_91332_(CrashReport p_91333_) {
        File $$1 = new File(Minecraft.m_91087_().f_91069_, "crash-reports");
        File $$2 = new File($$1, "crash-" + Util.m_241986_() + "-client.txt");
        Bootstrap.m_135875_(p_91333_.m_127526_());
        if (p_91333_.m_127527_() != null) {
            Bootstrap.m_135875_("#@!@# Game crashed! Crash report saved to: #@!@# " + p_91333_.m_127527_());
            System.exit(-1);
        } else if (p_91333_.m_127512_($$2)) {
            Bootstrap.m_135875_("#@!@# Game crashed! Crash report saved to: #@!@# " + $$2.getAbsolutePath());
            System.exit(-1);
        } else {
            Bootstrap.m_135875_("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }

    public boolean m_91390_() {
        return this.f_91066_.m_231819_().m_231551_();
    }

    public CompletableFuture<Void> m_91391_() {
        return this.m_168019_(false);
    }

    private CompletableFuture<Void> m_168019_(boolean p_168020_) {
        if (this.f_91024_ != null) {
            return this.f_91024_;
        }
        CompletableFuture<Void> $$1 = new CompletableFuture<Void>();
        if (!p_168020_ && this.f_91081_ instanceof LoadingOverlay) {
            this.f_91024_ = $$1;
            return $$1;
        }
        this.f_91038_.m_10506_();
        List<PackResources> $$2 = this.f_91038_.m_10525_();
        if (!p_168020_) {
            this.f_167847_.m_168557_(ResourceLoadStateTracker.ReloadReason.MANUAL, $$2);
        }
        this.m_91150_(new LoadingOverlay(this, this.f_91036_.m_142463_(Util.m_183991_(), this, f_90983_, $$2), p_231394_ -> Util.m_137521_(p_231394_, this::m_91239_, () -> {
            this.f_91060_.m_109818_();
            this.f_167847_.m_168556_();
            $$1.complete(null);
        }), true));
        return $$1;
    }

    private void m_91273_() {
        boolean $$0 = false;
        BlockModelShaper $$1 = this.m_91289_().m_110907_();
        BakedModel $$2 = $$1.m_110881_().m_119409_();
        for (Block block : Registry.f_122824_) {
            for (BlockState $$4 : block.m_49965_().m_61056_()) {
                BakedModel $$5;
                if ($$4.m_60799_() != RenderShape.MODEL || ($$5 = $$1.m_110893_($$4)) != $$2) continue;
                f_90982_.debug("Missing model for: {}", (Object)$$4);
                $$0 = true;
            }
        }
        TextureAtlasSprite $$6 = $$2.m_6160_();
        for (Block $$7 : Registry.f_122824_) {
            for (BlockState $$8 : $$7.m_49965_().m_61056_()) {
                TextureAtlasSprite $$9 = $$1.m_110882_($$8);
                if ($$8.m_60795_() || $$9 != $$6) continue;
                f_90982_.debug("Missing particle icon for: {}", (Object)$$8);
                $$0 = true;
            }
        }
        NonNullList<ItemStack> nonNullList = NonNullList.m_122779_();
        for (Item $$11 : Registry.f_122827_) {
            nonNullList.clear();
            $$11.m_6787_(CreativeModeTab.f_40754_, nonNullList);
            for (ItemStack $$12 : nonNullList) {
                String $$13 = $$12.m_41778_();
                String $$14 = Component.m_237115_($$13).getString();
                if (!$$14.toLowerCase(Locale.ROOT).equals($$11.m_5524_())) continue;
                f_90982_.debug("Missing translation for: {} {} {}", new Object[]{$$12, $$13, $$12.m_41720_()});
            }
        }
        $$0 |= MenuScreens.m_96198_();
        if ($$0 |= EntityRenderers.m_174035_()) {
            throw new IllegalStateException("Your game data is foobar, fix the errors above!");
        }
    }

    public LevelStorageSource m_91392_() {
        return this.f_91031_;
    }

    private void m_91326_(String p_91327_) {
        ChatStatus $$1 = this.m_168022_();
        if (!$$1.m_142594_(this.m_91090_())) {
            if (this.f_91065_.m_238351_()) {
                this.f_91065_.m_238397_(false);
                this.m_91152_(new ConfirmLinkScreen(p_243338_ -> {
                    if (p_243338_) {
                        Util.m_137581_().m_137646_("https://aka.ms/JavaAccountSettings");
                    }
                    this.m_91152_(null);
                }, ChatStatus.f_238170_, "https://aka.ms/JavaAccountSettings", true));
            } else {
                Component $$2 = $$1.m_168034_();
                this.f_91065_.m_93063_($$2, false);
                this.f_240365_.m_168785_($$2);
                this.f_91065_.m_238397_($$1 == ChatStatus.DISABLED_BY_PROFILE);
            }
        } else {
            this.m_91152_(new ChatScreen(p_91327_));
        }
    }

    public void m_91152_(@Nullable Screen p_91153_) {
        if (SharedConstants.f_136183_ && Thread.currentThread() != this.f_91018_) {
            f_90982_.error("setScreen called from non-game thread");
        }
        if (this.f_91080_ != null) {
            this.f_91080_.m_7861_();
        }
        if (p_91153_ == null && this.f_91073_ == null) {
            p_91153_ = new TitleScreen();
        } else if (p_91153_ == null && this.f_91074_.m_21224_()) {
            if (this.f_91074_.m_108632_()) {
                p_91153_ = new DeathScreen(null, this.f_91073_.m_6106_().m_5466_());
            } else {
                this.f_91074_.m_7583_();
            }
        }
        this.f_91080_ = p_91153_;
        BufferUploader.m_166835_();
        if (p_91153_ != null) {
            this.f_91067_.m_91602_();
            KeyMapping.m_90847_();
            p_91153_.m_6575_(this, this.f_90990_.m_85445_(), this.f_90990_.m_85446_());
            this.f_91079_ = false;
        } else {
            this.f_91043_.m_120407_();
            this.f_91067_.m_91601_();
        }
        this.m_91341_();
    }

    public void m_91150_(@Nullable Overlay p_91151_) {
        this.f_91081_ = p_91151_;
    }

    public void m_91393_() {
        try {
            f_90982_.info("Stopping!");
            try {
                this.f_240365_.m_93329_();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                if (this.f_91073_ != null) {
                    this.f_91073_.m_7462_();
                }
                this.m_91399_();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (this.f_91080_ != null) {
                this.f_91080_.m_7861_();
            }
            this.close();
        }
        finally {
            Util.f_137440_ = System::nanoTime;
            if (this.f_91020_ == null) {
                System.exit(0);
            }
        }
    }

    @Override
    public void close() {
        if (this.f_231342_ != null) {
            this.f_231342_.m_231149_();
        }
        try {
            this.f_205120_.close();
            this.f_91051_.close();
            this.f_91045_.close();
            this.f_91063_.close();
            this.f_91060_.close();
            this.f_91043_.m_120406_();
            this.f_91061_.m_107301_();
            this.f_91054_.close();
            this.f_91053_.close();
            this.f_90987_.close();
            this.f_91036_.close();
            Util.m_137580_();
        }
        catch (Throwable $$0) {
            f_90982_.error("Shutdown failure!", $$0);
            throw $$0;
        }
        finally {
            this.f_90989_.close();
            this.f_90990_.close();
        }
    }

    private void m_91383_(boolean p_91384_) {
        boolean $$10;
        boolean $$7;
        Runnable $$3;
        this.f_90990_.m_85403_("Pre render");
        long $$1 = Util.m_137569_();
        if (this.f_90990_.m_85411_()) {
            this.m_91395_();
        }
        if (this.f_91024_ != null && !(this.f_91081_ instanceof LoadingOverlay)) {
            CompletableFuture<Void> $$2 = this.f_91024_;
            this.f_91024_ = null;
            this.m_91391_().thenRun(() -> $$2.complete(null));
        }
        while (($$3 = this.f_91023_.poll()) != null) {
            $$3.run();
        }
        if (p_91384_) {
            int $$4 = this.f_90991_.m_92525_(Util.m_137550_());
            this.f_91026_.m_6180_("scheduledExecutables");
            this.m_18699_();
            this.f_91026_.m_7238_();
            this.f_91026_.m_6180_("tick");
            for (int $$5 = 0; $$5 < Math.min(10, $$4); ++$$5) {
                this.f_91026_.m_6174_("clientTick");
                this.m_91398_();
            }
            this.f_91026_.m_7238_();
        }
        this.f_91067_.m_91523_();
        this.f_90990_.m_85403_("Render");
        this.f_91026_.m_6180_("sound");
        this.f_91043_.m_120361_(this.f_91063_.m_109153_());
        this.f_91026_.m_7238_();
        this.f_91026_.m_6180_("render");
        if (this.f_91066_.f_92063_ || this.f_167846_.m_142763_()) {
            boolean $$6;
            boolean bl = $$6 = this.f_231342_ == null || this.f_231342_.m_231150_();
            if ($$6) {
                TimerQuery.m_231140_().ifPresent(TimerQuery::m_231141_);
            }
        } else {
            $$7 = false;
            this.f_231341_ = 0.0;
        }
        PoseStack $$8 = RenderSystem.m_157191_();
        $$8.m_85836_();
        RenderSystem.m_157182_();
        RenderSystem.m_69421_(16640, f_91002_);
        this.f_91042_.m_83947_(true);
        FogRenderer.m_109017_();
        this.f_91026_.m_6180_("display");
        RenderSystem.m_69493_();
        RenderSystem.m_69481_();
        this.f_91026_.m_7238_();
        if (!this.f_91079_) {
            this.f_91026_.m_6182_("gameRenderer");
            this.f_91063_.m_109093_(this.f_91012_ ? this.f_91013_ : this.f_90991_.f_92518_, $$1, p_91384_);
            this.f_91026_.m_6182_("toasts");
            this.f_91003_.m_94920_(new PoseStack());
            this.f_91026_.m_7238_();
        }
        if (this.f_91056_ != null) {
            this.f_91026_.m_6180_("fpsPie");
            this.m_91140_(new PoseStack(), this.f_91056_);
            this.f_91026_.m_7238_();
        }
        this.f_91026_.m_6180_("blit");
        this.f_91042_.m_83970_();
        $$8.m_85849_();
        $$8.m_85836_();
        RenderSystem.m_157182_();
        this.f_91042_.m_83938_(this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
        if ($$7) {
            TimerQuery.m_231140_().ifPresent(p_231363_ -> {
                this.f_231342_ = p_231363_.m_231142_();
            });
        }
        $$8.m_85849_();
        RenderSystem.m_157182_();
        this.f_91026_.m_6182_("updateDisplay");
        this.f_90990_.m_85435_();
        int $$9 = this.m_91275_();
        if ($$9 < 260) {
            RenderSystem.m_69830_($$9);
        }
        this.f_91026_.m_6182_("yield");
        Thread.yield();
        this.f_91026_.m_7238_();
        this.f_90990_.m_85403_("Post render");
        ++this.f_91016_;
        boolean bl = $$10 = this.m_91091_() && (this.f_91080_ != null && this.f_91080_.m_7043_() || this.f_91081_ != null && this.f_91081_.m_7859_()) && !this.f_91007_.m_6992_();
        if (this.f_91012_ != $$10) {
            if (this.f_91012_) {
                this.f_91013_ = this.f_90991_.f_92518_;
            } else {
                this.f_90991_.f_92518_ = this.f_91013_;
            }
            this.f_91012_ = $$10;
        }
        long $$11 = Util.m_137569_();
        long $$12 = $$11 - this.f_91014_;
        if ($$7) {
            this.f_231340_ = $$12;
        }
        this.f_91070_.m_13755_($$12);
        this.f_91014_ = $$11;
        this.f_91026_.m_6180_("fpsUpdate");
        if (this.f_231342_ != null && this.f_231342_.m_231150_()) {
            this.f_231341_ = (double)this.f_231342_.m_231151_() * 100.0 / (double)this.f_231340_;
        }
        while (Util.m_137550_() >= this.f_91015_ + 1000L) {
            String $$14;
            if (this.f_231341_ > 0.0) {
                String $$13 = " GPU: " + (this.f_231341_ > 100.0 ? ChatFormatting.RED + "100%" : Math.round(this.f_231341_) + "%");
            } else {
                $$14 = "";
            }
            f_91021_ = this.f_91016_;
            this.f_90977_ = String.format(Locale.ROOT, "%d fps T: %s%s%s%s B: %d%s", f_91021_, $$9 == 260 ? "inf" : Integer.valueOf($$9), this.f_91066_.m_231817_().m_231551_() != false ? " vsync" : "", this.f_91066_.m_232060_().m_231551_(), this.f_91066_.m_232050_().m_231551_() == CloudStatus.OFF ? "" : (this.f_91066_.m_232050_().m_231551_() == CloudStatus.FAST ? " fast-clouds" : " fancy-clouds"), this.f_91066_.m_232121_().m_231551_(), $$14);
            this.f_91015_ += 1000L;
            this.f_91016_ = 0;
        }
        this.f_91026_.m_7238_();
    }

    private boolean m_91274_() {
        return this.f_91066_.f_92063_ && this.f_91066_.f_92064_ && !this.f_91066_.f_92062_;
    }

    private ProfilerFiller m_167970_(boolean p_167971_, @Nullable SingleTickProfiler p_167972_) {
        ProfilerFiller $$3;
        if (!p_167971_) {
            this.f_91028_.m_18437_();
            if (!this.f_167846_.m_142763_() && p_167972_ == null) {
                return InactiveProfiler.f_18554_;
            }
        }
        if (p_167971_) {
            if (!this.f_91028_.m_18436_()) {
                this.f_91027_ = 0;
                this.f_91028_.m_18438_();
            }
            ++this.f_91027_;
            ProfilerFiller $$2 = this.f_91028_.m_18439_();
        } else {
            $$3 = InactiveProfiler.f_18554_;
        }
        if (this.f_167846_.m_142763_()) {
            $$3 = ProfilerFiller.m_18578_($$3, this.f_167846_.m_142610_());
        }
        return SingleTickProfiler.m_18629_($$3, p_167972_);
    }

    private void m_91338_(boolean p_91339_, @Nullable SingleTickProfiler p_91340_) {
        if (p_91340_ != null) {
            p_91340_.m_18634_();
        }
        this.f_91056_ = p_91339_ ? this.f_91028_.m_18440_() : null;
        this.f_91026_ = this.f_91028_.m_18439_();
    }

    @Override
    public void m_5741_() {
        int $$0 = this.f_90990_.m_85385_(this.f_91066_.m_231928_().m_231551_(), this.m_91390_());
        this.f_90990_.m_85378_($$0);
        if (this.f_91080_ != null) {
            this.f_91080_.m_6574_(this, this.f_90990_.m_85445_(), this.f_90990_.m_85446_());
        }
        RenderTarget $$1 = this.m_91385_();
        $$1.m_83941_(this.f_90990_.m_85441_(), this.f_90990_.m_85442_(), f_91002_);
        this.f_91063_.m_109097_(this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
        this.f_91067_.m_91599_();
    }

    @Override
    public void m_5740_() {
        this.f_91067_.m_91603_();
    }

    private int m_91275_() {
        if (this.f_91073_ == null && (this.f_91080_ != null || this.f_91081_ != null)) {
            return 60;
        }
        return this.f_90990_.m_85434_();
    }

    public void m_91394_() {
        try {
            MemoryReserve.m_182328_();
            this.f_91060_.m_109824_();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            System.gc();
            if (this.f_91010_ && this.f_91007_ != null) {
                this.f_91007_.m_7570_(true);
            }
            this.m_91320_(new GenericDirtMessageScreen(Component.m_237115_("menu.savingLevel")));
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        System.gc();
    }

    public boolean m_167946_(Consumer<Component> p_167947_) {
        Consumer<Path> $$8;
        if (this.f_167846_.m_142763_()) {
            this.m_167975_();
            return false;
        }
        Consumer<ProfileResults> $$1 = p_231435_ -> {
            if (p_231435_ == EmptyProfileResults.f_18441_) {
                return;
            }
            int $$2 = p_231435_.m_7315_();
            double $$3 = (double)p_231435_.m_18577_() / (double)TimeUtil.f_145016_;
            this.execute(() -> p_167947_.accept(Component.m_237110_("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", $$3), $$2, String.format(Locale.ROOT, "%.2f", (double)$$2 / $$3))));
        };
        Consumer<Path> $$2 = p_231438_ -> {
            MutableComponent $$2 = Component.m_237113_(p_231438_.toString()).m_130940_(ChatFormatting.UNDERLINE).m_130938_(p_231387_ -> p_231387_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, p_231438_.toFile().getParent())));
            this.execute(() -> p_167947_.accept(Component.m_237110_("debug.profiling.stop", $$2)));
        };
        SystemReport $$3 = Minecraft.m_167850_(new SystemReport(), this, this.f_91039_, this.f_91001_, this.f_91066_);
        Consumer<List> $$4 = p_231349_ -> {
            Path $$3 = this.m_167856_($$3, (List<Path>)p_231349_);
            $$2.accept($$3);
        };
        if (this.f_91007_ == null) {
            Consumer<Path> $$5 = p_231404_ -> $$4.accept((List)ImmutableList.of((Object)p_231404_));
        } else {
            this.f_91007_.m_177935_($$3);
            CompletableFuture $$6 = new CompletableFuture();
            CompletableFuture $$7 = new CompletableFuture();
            CompletableFuture.allOf($$6, $$7).thenRunAsync(() -> $$4.accept((List)ImmutableList.of((Object)((Path)$$6.join()), (Object)((Path)$$7.join()))), Util.m_183992_());
            this.f_91007_.m_177923_(p_231351_ -> {}, $$7::complete);
            $$8 = $$6::complete;
        }
        this.f_167846_ = ActiveMetricsRecorder.m_146132_(new ClientMetricsSamplersProvider(Util.f_137440_, this.f_91060_), Util.f_137440_, Util.m_183992_(), new MetricsPersister("client"), p_231401_ -> {
            this.f_167846_ = InactiveMetricsRecorder.f_146153_;
            $$1.accept((ProfileResults)p_231401_);
        }, $$8);
        return true;
    }

    private void m_167975_() {
        this.f_167846_.m_142760_();
        if (this.f_91007_ != null) {
            this.f_91007_.m_177929_();
        }
    }

    private void m_231418_() {
        this.f_167846_.m_213832_();
        if (this.f_91007_ != null) {
            this.f_91007_.m_236737_();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private Path m_167856_(SystemReport p_167857_, List<Path> p_167858_) {
        void $$8;
        String $$3;
        if (this.m_91090_()) {
            String $$2 = this.m_91092_().m_129910_().m_5462_();
        } else {
            $$3 = this.m_91089_().f_105362_;
        }
        try {
            String $$4 = String.format(Locale.ROOT, "%s-%s-%s", Util.m_241986_(), $$3, SharedConstants.m_183709_().getId());
            String $$5 = FileUtil.m_133730_(MetricsPersister.f_146209_, $$4, ".zip");
            Path $$6 = MetricsPersister.f_146209_.resolve($$5);
        }
        catch (IOException $$7) {
            throw new UncheckedIOException($$7);
        }
        try (FileZipper $$9 = new FileZipper((Path)$$8);){
            $$9.m_144703_(Paths.get("system.txt", new String[0]), p_167857_.m_143515_());
            $$9.m_144703_(Paths.get("client", new String[0]).resolve(this.f_91066_.m_168450_().getName()), this.f_91066_.m_168451_());
            p_167858_.forEach($$9::m_144698_);
        }
        finally {
            for (Path $$10 : p_167858_) {
                try {
                    FileUtils.forceDelete((File)$$10.toFile());
                }
                catch (IOException $$11) {
                    f_90982_.warn("Failed to delete temporary profiling result {}", (Object)$$10, (Object)$$11);
                }
            }
        }
        return $$8;
    }

    public void m_91111_(int p_91112_) {
        if (this.f_91056_ == null) {
            return;
        }
        List<ResultField> $$1 = this.f_91056_.m_6412_(this.f_91057_);
        if ($$1.isEmpty()) {
            return;
        }
        ResultField $$2 = $$1.remove(0);
        if (p_91112_ == 0) {
            int $$3;
            if (!$$2.f_18610_.isEmpty() && ($$3 = this.f_91057_.lastIndexOf(30)) >= 0) {
                this.f_91057_ = this.f_91057_.substring(0, $$3);
            }
        } else if (--p_91112_ < $$1.size() && !"unspecified".equals($$1.get((int)p_91112_).f_18610_)) {
            if (!this.f_91057_.isEmpty()) {
                this.f_91057_ = this.f_91057_ + "\u001e";
            }
            this.f_91057_ = this.f_91057_ + $$1.get((int)p_91112_).f_18610_;
        }
    }

    private void m_91140_(PoseStack p_91141_, ProfileResults p_91142_) {
        List<ResultField> $$2 = p_91142_.m_6412_(this.f_91057_);
        ResultField $$3 = $$2.remove(0);
        RenderSystem.m_69421_(256, f_91002_);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        Matrix4f $$4 = Matrix4f.m_162203_(0.0f, this.f_90990_.m_85441_(), 0.0f, this.f_90990_.m_85442_(), 1000.0f, 3000.0f);
        RenderSystem.m_157425_($$4);
        PoseStack $$5 = RenderSystem.m_157191_();
        $$5.m_166856_();
        $$5.m_85837_(0.0, 0.0, -2000.0);
        RenderSystem.m_157182_();
        RenderSystem.m_69832_(1.0f);
        RenderSystem.m_69472_();
        Tesselator $$6 = Tesselator.m_85913_();
        BufferBuilder $$7 = $$6.m_85915_();
        int $$8 = 160;
        int $$9 = this.f_90990_.m_85441_() - 160 - 10;
        int $$10 = this.f_90990_.m_85442_() - 320;
        RenderSystem.m_69478_();
        $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        $$7.m_5483_((float)$$9 - 176.0f, (float)$$10 - 96.0f - 16.0f, 0.0).m_6122_(200, 0, 0, 0).m_5752_();
        $$7.m_5483_((float)$$9 - 176.0f, $$10 + 320, 0.0).m_6122_(200, 0, 0, 0).m_5752_();
        $$7.m_5483_((float)$$9 + 176.0f, $$10 + 320, 0.0).m_6122_(200, 0, 0, 0).m_5752_();
        $$7.m_5483_((float)$$9 + 176.0f, (float)$$10 - 96.0f - 16.0f, 0.0).m_6122_(200, 0, 0, 0).m_5752_();
        $$6.m_85914_();
        RenderSystem.m_69461_();
        double $$11 = 0.0;
        for (ResultField $$12 : $$2) {
            int $$13 = Mth.m_14107_($$12.f_18607_ / 4.0) + 1;
            $$7.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
            int $$14 = $$12.m_18616_();
            int $$15 = $$14 >> 16 & 0xFF;
            int $$16 = $$14 >> 8 & 0xFF;
            int $$17 = $$14 & 0xFF;
            $$7.m_5483_($$9, $$10, 0.0).m_6122_($$15, $$16, $$17, 255).m_5752_();
            for (int $$18 = $$13; $$18 >= 0; --$$18) {
                float $$19 = (float)(($$11 + $$12.f_18607_ * (double)$$18 / (double)$$13) * 6.2831854820251465 / 100.0);
                float $$20 = Mth.m_14031_($$19) * 160.0f;
                float $$21 = Mth.m_14089_($$19) * 160.0f * 0.5f;
                $$7.m_5483_((float)$$9 + $$20, (float)$$10 - $$21, 0.0).m_6122_($$15, $$16, $$17, 255).m_5752_();
            }
            $$6.m_85914_();
            $$7.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
            for (int $$22 = $$13; $$22 >= 0; --$$22) {
                float $$23 = (float)(($$11 + $$12.f_18607_ * (double)$$22 / (double)$$13) * 6.2831854820251465 / 100.0);
                float $$24 = Mth.m_14031_($$23) * 160.0f;
                float $$25 = Mth.m_14089_($$23) * 160.0f * 0.5f;
                if ($$25 > 0.0f) continue;
                $$7.m_5483_((float)$$9 + $$24, (float)$$10 - $$25, 0.0).m_6122_($$15 >> 1, $$16 >> 1, $$17 >> 1, 255).m_5752_();
                $$7.m_5483_((float)$$9 + $$24, (float)$$10 - $$25 + 10.0f, 0.0).m_6122_($$15 >> 1, $$16 >> 1, $$17 >> 1, 255).m_5752_();
            }
            $$6.m_85914_();
            $$11 += $$12.f_18607_;
        }
        DecimalFormat $$26 = new DecimalFormat("##0.00");
        $$26.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        RenderSystem.m_69493_();
        String $$27 = ProfileResults.m_18575_($$3.f_18610_);
        Object $$28 = "";
        if (!"unspecified".equals($$27)) {
            $$28 = (String)$$28 + "[0] ";
        }
        $$28 = $$27.isEmpty() ? (String)$$28 + "ROOT " : (String)$$28 + $$27 + " ";
        int $$29 = 0xFFFFFF;
        this.f_91062_.m_92750_(p_91141_, (String)$$28, $$9 - 160, $$10 - 80 - 16, 0xFFFFFF);
        $$28 = $$26.format($$3.f_18608_) + "%";
        this.f_91062_.m_92750_(p_91141_, (String)$$28, $$9 + 160 - this.f_91062_.m_92895_((String)$$28), $$10 - 80 - 16, 0xFFFFFF);
        for (int $$30 = 0; $$30 < $$2.size(); ++$$30) {
            ResultField $$31 = $$2.get($$30);
            StringBuilder $$32 = new StringBuilder();
            if ("unspecified".equals($$31.f_18610_)) {
                $$32.append("[?] ");
            } else {
                $$32.append("[").append($$30 + 1).append("] ");
            }
            Object $$33 = $$32.append($$31.f_18610_).toString();
            this.f_91062_.m_92750_(p_91141_, (String)$$33, $$9 - 160, $$10 + 80 + $$30 * 8 + 20, $$31.m_18616_());
            $$33 = $$26.format($$31.f_18607_) + "%";
            this.f_91062_.m_92750_(p_91141_, (String)$$33, $$9 + 160 - 50 - this.f_91062_.m_92895_((String)$$33), $$10 + 80 + $$30 * 8 + 20, $$31.m_18616_());
            $$33 = $$26.format($$31.f_18608_) + "%";
            this.f_91062_.m_92750_(p_91141_, (String)$$33, $$9 + 160 - this.f_91062_.m_92895_((String)$$33), $$10 + 80 + $$30 * 8 + 20, $$31.m_18616_());
        }
    }

    public void m_91395_() {
        this.f_91019_ = false;
    }

    public boolean m_91396_() {
        return this.f_91019_;
    }

    public void m_91358_(boolean p_91359_) {
        boolean $$1;
        if (this.f_91080_ != null) {
            return;
        }
        boolean bl = $$1 = this.m_91091_() && !this.f_91007_.m_6992_();
        if ($$1) {
            this.m_91152_(new PauseScreen(!p_91359_));
            this.f_91043_.m_120391_();
        } else {
            this.m_91152_(new PauseScreen(true));
        }
    }

    private void m_91386_(boolean p_91387_) {
        if (!p_91387_) {
            this.f_91078_ = 0;
        }
        if (this.f_91078_ > 0 || this.f_91074_.m_6117_()) {
            return;
        }
        if (p_91387_ && this.f_91077_ != null && this.f_91077_.m_6662_() == HitResult.Type.BLOCK) {
            Direction $$3;
            BlockHitResult $$1 = (BlockHitResult)this.f_91077_;
            BlockPos $$2 = $$1.m_82425_();
            if (!this.f_91073_.m_8055_($$2).m_60795_() && this.f_91072_.m_105283_($$2, $$3 = $$1.m_82434_())) {
                this.f_91061_.m_107367_($$2, $$3);
                this.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
            }
            return;
        }
        this.f_91072_.m_105276_();
    }

    private boolean m_202354_() {
        if (this.f_91078_ > 0) {
            return false;
        }
        if (this.f_91077_ == null) {
            f_90982_.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.f_91072_.m_105289_()) {
                this.f_91078_ = 10;
            }
            return false;
        }
        if (this.f_91074_.m_108637_()) {
            return false;
        }
        boolean $$0 = false;
        switch (this.f_91077_.m_6662_()) {
            case ENTITY: {
                this.f_91072_.m_105223_(this.f_91074_, ((EntityHitResult)this.f_91077_).m_82443_());
                break;
            }
            case BLOCK: {
                BlockHitResult $$1 = (BlockHitResult)this.f_91077_;
                BlockPos $$2 = $$1.m_82425_();
                if (!this.f_91073_.m_8055_($$2).m_60795_()) {
                    this.f_91072_.m_105269_($$2, $$1.m_82434_());
                    if (!this.f_91073_.m_8055_($$2).m_60795_()) break;
                    $$0 = true;
                    break;
                }
            }
            case MISS: {
                if (this.f_91072_.m_105289_()) {
                    this.f_91078_ = 10;
                }
                this.f_91074_.m_36334_();
            }
        }
        this.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
        return $$0;
    }

    private void m_91277_() {
        if (this.f_91072_.m_105296_()) {
            return;
        }
        this.f_91011_ = 4;
        if (this.f_91074_.m_108637_()) {
            return;
        }
        if (this.f_91077_ == null) {
            f_90982_.warn("Null returned as 'hitResult', this shouldn't happen!");
        }
        for (InteractionHand $$0 : InteractionHand.values()) {
            InteractionResult $$8;
            ItemStack $$1 = this.f_91074_.m_21120_($$0);
            if (this.f_91077_ != null) {
                switch (this.f_91077_.m_6662_()) {
                    case ENTITY: {
                        EntityHitResult $$2 = (EntityHitResult)this.f_91077_;
                        Entity $$3 = $$2.m_82443_();
                        if (!this.f_91073_.m_6857_().m_61937_($$3.m_20183_())) {
                            return;
                        }
                        InteractionResult $$4 = this.f_91072_.m_105230_(this.f_91074_, $$3, $$2, $$0);
                        if (!$$4.m_19077_()) {
                            $$4 = this.f_91072_.m_105226_(this.f_91074_, $$3, $$0);
                        }
                        if (!$$4.m_19077_()) break;
                        if ($$4.m_19080_()) {
                            this.f_91074_.m_6674_($$0);
                        }
                        return;
                    }
                    case BLOCK: {
                        BlockHitResult $$5 = (BlockHitResult)this.f_91077_;
                        int $$6 = $$1.m_41613_();
                        InteractionResult $$7 = this.f_91072_.m_233732_(this.f_91074_, $$0, $$5);
                        if ($$7.m_19077_()) {
                            if ($$7.m_19080_()) {
                                this.f_91074_.m_6674_($$0);
                                if (!$$1.m_41619_() && ($$1.m_41613_() != $$6 || this.f_91072_.m_105290_())) {
                                    this.f_91063_.f_109055_.m_109320_($$0);
                                }
                            }
                            return;
                        }
                        if ($$7 != InteractionResult.FAIL) break;
                        return;
                    }
                }
            }
            if ($$1.m_41619_() || !($$8 = this.f_91072_.m_233721_(this.f_91074_, $$0)).m_19077_()) continue;
            if ($$8.m_19080_()) {
                this.f_91074_.m_6674_($$0);
            }
            this.f_91063_.f_109055_.m_109320_($$0);
            return;
        }
    }

    public MusicManager m_91397_() {
        return this.f_91044_;
    }

    public void m_91398_() {
        if (this.f_91011_ > 0) {
            --this.f_91011_;
        }
        this.f_91026_.m_6180_("gui");
        this.f_240378_.m_240688_();
        this.f_91065_.m_193832_(this.f_91012_);
        this.f_91026_.m_7238_();
        this.f_91063_.m_109087_(1.0f);
        this.f_91005_.m_120578_(this.f_91073_, this.f_91077_);
        this.f_91026_.m_6180_("gameMode");
        if (!this.f_91012_ && this.f_91073_ != null) {
            this.f_91072_.m_105287_();
        }
        this.f_91026_.m_6182_("textures");
        if (this.f_91073_ != null) {
            this.f_90987_.m_7673_();
        }
        if (this.f_91080_ == null && this.f_91074_ != null) {
            if (this.f_91074_.m_21224_() && !(this.f_91080_ instanceof DeathScreen)) {
                this.m_91152_(null);
            } else if (this.f_91074_.m_5803_() && this.f_91073_ != null) {
                this.m_91152_(new InBedChatScreen());
            }
        } else {
            Screen screen = this.f_91080_;
            if (screen instanceof InBedChatScreen) {
                InBedChatScreen $$0 = (InBedChatScreen)screen;
                if (!this.f_91074_.m_5803_()) {
                    $$0.m_193839_();
                }
            }
        }
        if (this.f_91080_ != null) {
            this.f_91078_ = 10000;
        }
        if (this.f_91080_ != null) {
            Screen.m_96579_(() -> this.f_91080_.m_86600_(), "Ticking screen", this.f_91080_.getClass().getCanonicalName());
        }
        if (!this.f_91066_.f_92063_) {
            this.f_91065_.m_93091_();
        }
        if (this.f_91081_ == null && (this.f_91080_ == null || this.f_91080_.f_96546_)) {
            this.f_91026_.m_6182_("Keybindings");
            this.m_91279_();
            if (this.f_91078_ > 0) {
                --this.f_91078_;
            }
        }
        if (this.f_91073_ != null) {
            this.f_91026_.m_6182_("gameRenderer");
            if (!this.f_91012_) {
                this.f_91063_.m_109148_();
            }
            this.f_91026_.m_6182_("levelRenderer");
            if (!this.f_91012_) {
                this.f_91060_.m_109823_();
            }
            this.f_91026_.m_6182_("level");
            if (!this.f_91012_) {
                if (this.f_91073_.m_104819_() > 0) {
                    this.f_91073_.m_6580_(this.f_91073_.m_104819_() - 1);
                }
                this.f_91073_.m_104804_();
            }
        } else if (this.f_91063_.m_109149_() != null) {
            this.f_91063_.m_109086_();
        }
        if (!this.f_91012_) {
            this.f_91044_.m_120183_();
        }
        this.f_91043_.m_120389_(this.f_91012_);
        if (this.f_91073_ != null) {
            if (!this.f_91012_) {
                if (!this.f_91066_.f_92031_ && this.m_91278_()) {
                    MutableComponent $$1 = Component.m_237115_("tutorial.socialInteractions.title");
                    MutableComponent $$2 = Component.m_237110_("tutorial.socialInteractions.description", Tutorial.m_120592_("socialInteractions"));
                    this.f_91025_ = new TutorialToast(TutorialToast.Icons.SOCIAL_INTERACTIONS, $$1, $$2, true);
                    this.f_91005_.m_120572_(this.f_91025_, 160);
                    this.f_91066_.f_92031_ = true;
                    this.f_91066_.m_92169_();
                }
                this.f_91005_.m_120596_();
                try {
                    this.f_91073_.m_104726_(() -> true);
                }
                catch (Throwable $$3) {
                    CrashReport $$4 = CrashReport.m_127521_($$3, "Exception in world tick");
                    if (this.f_91073_ == null) {
                        CrashReportCategory $$5 = $$4.m_127514_("Affected level");
                        $$5.m_128159_("Problem", "Level is null!");
                    } else {
                        this.f_91073_.m_6026_($$4);
                    }
                    throw new ReportedException($$4);
                }
            }
            this.f_91026_.m_6182_("animateTick");
            if (!this.f_91012_ && this.f_91073_ != null) {
                this.f_91073_.m_104784_(this.f_91074_.m_146903_(), this.f_91074_.m_146904_(), this.f_91074_.m_146907_());
            }
            this.f_91026_.m_6182_("particles");
            if (!this.f_91012_) {
                this.f_91061_.m_107388_();
            }
        } else if (this.f_91009_ != null) {
            this.f_91026_.m_6182_("pendingConnection");
            this.f_91009_.m_129483_();
        }
        this.f_91026_.m_6182_("keyboard");
        this.f_91068_.m_90931_();
        this.f_91026_.m_7238_();
    }

    private boolean m_91278_() {
        return !this.f_91010_ || this.f_91007_ != null && this.f_91007_.m_6992_();
    }

    private void m_91279_() {
        while (this.f_91066_.f_92103_.m_90859_()) {
            CameraType $$0 = this.f_91066_.m_92176_();
            this.f_91066_.m_92157_(this.f_91066_.m_92176_().m_90614_());
            if ($$0.m_90612_() != this.f_91066_.m_92176_().m_90612_()) {
                this.f_91063_.m_109106_(this.f_91066_.m_92176_().m_90612_() ? this.m_91288_() : null);
            }
            this.f_91060_.m_109826_();
        }
        while (this.f_91066_.f_92104_.m_90859_()) {
            this.f_91066_.f_92067_ = !this.f_91066_.f_92067_;
        }
        for (int $$1 = 0; $$1 < 9; ++$$1) {
            boolean $$2 = this.f_91066_.f_92057_.m_90857_();
            boolean $$3 = this.f_91066_.f_92058_.m_90857_();
            if (!this.f_91066_.f_92056_[$$1].m_90859_()) continue;
            if (this.f_91074_.m_5833_()) {
                this.f_91065_.m_93085_().m_94771_($$1);
                continue;
            }
            if (this.f_91074_.m_7500_() && this.f_91080_ == null && ($$3 || $$2)) {
                CreativeModeInventoryScreen.m_98598_(this, $$1, $$3, $$2);
                continue;
            }
            this.f_91074_.m_150109_().f_35977_ = $$1;
        }
        while (this.f_91066_.f_92101_.m_90859_()) {
            if (!this.m_91278_()) {
                this.f_91074_.m_5661_(f_90984_, true);
                this.f_240365_.m_168785_(f_90984_);
                continue;
            }
            if (this.f_91025_ != null) {
                this.f_91005_.m_120570_(this.f_91025_);
                this.f_91025_ = null;
            }
            this.m_91152_(new SocialInteractionsScreen());
        }
        while (this.f_91066_.f_92092_.m_90859_()) {
            if (this.f_91072_.m_105292_()) {
                this.f_91074_.m_108628_();
                continue;
            }
            this.f_91005_.m_120564_();
            this.m_91152_(new InventoryScreen(this.f_91074_));
        }
        while (this.f_91066_.f_92055_.m_90859_()) {
            this.m_91152_(new AdvancementsScreen(this.f_91074_.f_108617_.m_105145_()));
        }
        while (this.f_91066_.f_92093_.m_90859_()) {
            if (this.f_91074_.m_5833_()) continue;
            this.m_91403_().m_104955_(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.f_121853_, Direction.DOWN));
        }
        while (this.f_91066_.f_92094_.m_90859_()) {
            if (this.f_91074_.m_5833_() || !this.f_91074_.m_108700_(Screen.m_96637_())) continue;
            this.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
        }
        while (this.f_91066_.f_92098_.m_90859_()) {
            this.m_91326_("");
        }
        if (this.f_91080_ == null && this.f_91081_ == null && this.f_91066_.f_92100_.m_90859_()) {
            this.m_91326_("/");
        }
        boolean $$4 = false;
        if (this.f_91074_.m_6117_()) {
            if (!this.f_91066_.f_92095_.m_90857_()) {
                this.f_91072_.m_105277_(this.f_91074_);
            }
            while (this.f_91066_.f_92096_.m_90859_()) {
            }
            while (this.f_91066_.f_92095_.m_90859_()) {
            }
            while (this.f_91066_.f_92097_.m_90859_()) {
            }
        } else {
            while (this.f_91066_.f_92096_.m_90859_()) {
                $$4 |= this.m_202354_();
            }
            while (this.f_91066_.f_92095_.m_90859_()) {
                this.m_91277_();
            }
            while (this.f_91066_.f_92097_.m_90859_()) {
                this.m_91280_();
            }
        }
        if (this.f_91066_.f_92095_.m_90857_() && this.f_91011_ == 0 && !this.f_91074_.m_6117_()) {
            this.m_91277_();
        }
        this.m_91386_(this.f_91080_ == null && !$$4 && this.f_91066_.f_92096_.m_90857_() && this.f_91067_.m_91600_());
    }

    public ClientTelemetryManager m_193590_() {
        return new ClientTelemetryManager(this, this.f_193584_, this.f_90998_.m_193806_(), this.f_90998_.m_193805_(), this.f_193582_);
    }

    public double m_231464_() {
        return this.f_231341_;
    }

    public ProfileKeyPairManager m_231465_() {
        return this.f_231337_;
    }

    public WorldOpenFlows m_231466_() {
        return new WorldOpenFlows(this, this.f_91031_);
    }

    public void m_231380_(String p_231381_, LevelStorageSource.LevelStorageAccess p_231382_, PackRepository p_231383_, WorldStem p_231384_) {
        CompletableFuture<Optional<ProfilePublicKey.Data>> $$4 = this.f_231337_.m_243369_();
        this.m_91399_();
        this.f_90999_.set(null);
        try {
            p_231382_.m_78287_(p_231384_.f_206894_(), p_231384_.f_206895_());
            Services $$5 = Services.m_214344_(this.f_231338_, this.f_91069_);
            $$5.f_214336_().m_143974_(this);
            SkullBlockEntity.m_222885_($$5, this);
            GameProfileCache.m_11004_(false);
            this.f_91007_ = MinecraftServer.m_129872_(p_231361_ -> new IntegratedServer((Thread)p_231361_, this, p_231382_, p_231383_, p_231384_, $$5, p_231447_ -> {
                StoringChunkProgressListener $$1 = new StoringChunkProgressListener(p_231447_ + 0);
                this.f_90999_.set($$1);
                return ProcessorChunkProgressListener.m_143583_($$1, this.f_91023_::add);
            }));
            this.f_91010_ = true;
            this.m_239476_(ReportEnvironment.m_239898_());
        }
        catch (Throwable $$6) {
            CrashReport $$7 = CrashReport.m_127521_($$6, "Starting integrated server");
            CrashReportCategory $$8 = $$7.m_127514_("Starting integrated server");
            $$8.m_128159_("Level ID", p_231381_);
            $$8.m_128165_("Level Name", () -> p_231384_.f_206895_().m_5462_());
            throw new ReportedException($$7);
        }
        while (this.f_90999_.get() == null) {
            Thread.yield();
        }
        LevelLoadingScreen $$9 = new LevelLoadingScreen(this.f_90999_.get());
        this.m_91152_($$9);
        this.f_91026_.m_6180_("waitForServer");
        while (!this.f_91007_.m_129920_()) {
            $$9.m_86600_();
            this.m_91383_(false);
            try {
                Thread.sleep(16L);
            }
            catch (InterruptedException $$7) {
                // empty catch block
            }
            if (this.f_91020_ == null) continue;
            Minecraft.m_91332_(this.f_91020_.get());
            return;
        }
        this.f_91026_.m_7238_();
        SocketAddress $$10 = this.f_91007_.m_129919_().m_9708_();
        Connection $$11 = Connection.m_129493_($$10);
        $$11.m_129505_(new ClientHandshakePacketListenerImpl($$11, this, null, p_231442_ -> {}));
        $$11.m_129512_(new ClientIntentionPacket($$10.toString(), 0, ConnectionProtocol.LOGIN));
        $$11.m_129512_(new ServerboundHelloPacket(this.m_91094_().m_92546_(), $$4.join(), Optional.ofNullable(this.m_91094_().m_240411_())));
        this.f_91009_ = $$11;
    }

    public void m_91156_(ClientLevel p_91157_) {
        ProgressScreen $$1 = new ProgressScreen(true);
        $$1.m_6309_(Component.m_237115_("connect.joining"));
        this.m_91362_($$1);
        this.f_91073_ = p_91157_;
        this.m_91324_(p_91157_);
        if (!this.f_91010_) {
            Services $$2 = Services.m_214344_(this.f_231338_, this.f_91069_);
            $$2.f_214336_().m_143974_(this);
            SkullBlockEntity.m_222885_($$2, this);
            GameProfileCache.m_11004_(false);
        }
    }

    public void m_91399_() {
        this.m_91320_(new ProgressScreen(true));
    }

    public void m_91320_(Screen p_91321_) {
        ClientPacketListener $$1 = this.m_91403_();
        if ($$1 != null) {
            this.m_18698_();
            $$1.m_105140_();
        }
        this.f_91006_.m_194060_();
        if (this.f_167846_.m_142763_()) {
            this.m_231418_();
        }
        IntegratedServer $$2 = this.f_91007_;
        this.f_91007_ = null;
        this.f_91063_.m_109150_();
        this.f_91072_ = null;
        this.f_240365_.m_93328_();
        this.m_91362_(p_91321_);
        if (this.f_91073_ != null) {
            if ($$2 != null) {
                this.f_91026_.m_6180_("waitForServer");
                while (!$$2.m_129782_()) {
                    this.m_91383_(false);
                }
                this.f_91026_.m_7238_();
            }
            this.f_91037_.m_235009_();
            this.f_91065_.m_93089_();
            this.f_91008_ = null;
            this.f_91010_ = false;
            this.f_91004_.m_90740_();
        }
        this.f_91073_ = null;
        this.m_91324_(null);
        this.f_91074_ = null;
        SkullBlockEntity.m_196704_();
    }

    private void m_91362_(Screen p_91363_) {
        this.f_91026_.m_6180_("forcedTick");
        this.f_91043_.m_120405_();
        this.f_91075_ = null;
        this.f_91009_ = null;
        this.m_91152_(p_91363_);
        this.m_91383_(false);
        this.f_91026_.m_7238_();
    }

    public void m_91346_(Screen p_91347_) {
        this.f_91026_.m_6180_("forcedTick");
        this.m_91152_(p_91347_);
        this.m_91383_(false);
        this.f_91026_.m_7238_();
    }

    private void m_91324_(@Nullable ClientLevel p_91325_) {
        this.f_91060_.m_109701_(p_91325_);
        this.f_91061_.m_107342_(p_91325_);
        this.f_167845_.m_112257_(p_91325_);
        this.m_91341_();
    }

    public boolean m_91400_() {
        return this.f_91034_ && this.f_193584_.properties().flag(UserApiService.UserFlag.SERVERS_ALLOWED) && this.m_239210_() == null;
    }

    public boolean m_168021_() {
        return this.f_193584_.properties().flag(UserApiService.UserFlag.REALMS_ALLOWED) && this.m_239210_() == null;
    }

    public boolean m_239929_() {
        return this.m_239210_() != null;
    }

    @Nullable
    public BanDetails m_239210_() {
        return (BanDetails)this.f_193584_.properties().bannedScopes().get("MULTIPLAYER");
    }

    public boolean m_91246_(UUID p_91247_) {
        if (!this.m_168022_().m_142594_(false)) {
            return (this.f_91074_ == null || !p_91247_.equals(this.f_91074_.m_20148_())) && !p_91247_.equals(Util.f_137441_);
        }
        return this.f_91006_.m_100684_(p_91247_);
    }

    public ChatStatus m_168022_() {
        if (this.f_91066_.m_232090_().m_231551_() == ChatVisiblity.HIDDEN) {
            return ChatStatus.DISABLED_BY_OPTIONS;
        }
        if (!this.f_91035_) {
            return ChatStatus.DISABLED_BY_LAUNCHER;
        }
        if (!this.f_193584_.properties().flag(UserApiService.UserFlag.CHAT_ALLOWED)) {
            return ChatStatus.DISABLED_BY_PROFILE;
        }
        return ChatStatus.ENABLED;
    }

    public final boolean m_91402_() {
        return this.f_91033_;
    }

    @Nullable
    public ClientPacketListener m_91403_() {
        return this.f_91074_ == null ? null : this.f_91074_.f_108617_;
    }

    public static boolean m_91404_() {
        return !Minecraft.f_90981_.f_91066_.f_92062_;
    }

    public static boolean m_91405_() {
        return Minecraft.f_90981_.f_91066_.m_232060_().m_231551_().m_35965_() >= GraphicsStatus.FANCY.m_35965_();
    }

    public static boolean m_91085_() {
        return !Minecraft.f_90981_.f_91063_.m_172715_() && Minecraft.f_90981_.f_91066_.m_232060_().m_231551_().m_35965_() >= GraphicsStatus.FABULOUS.m_35965_();
    }

    public static boolean m_91086_() {
        return Minecraft.f_90981_.f_91066_.m_232070_().m_231551_() != AmbientOcclusionStatus.OFF;
    }

    /*
     * WARNING - void declaration
     */
    private void m_91280_() {
        void $$9;
        if (this.f_91077_ == null || this.f_91077_.m_6662_() == HitResult.Type.MISS) {
            return;
        }
        boolean $$0 = this.f_91074_.m_150110_().f_35937_;
        BlockEntity $$1 = null;
        HitResult.Type $$2 = this.f_91077_.m_6662_();
        if ($$2 == HitResult.Type.BLOCK) {
            BlockPos $$3 = ((BlockHitResult)this.f_91077_).m_82425_();
            BlockState $$4 = this.f_91073_.m_8055_($$3);
            if ($$4.m_60795_()) {
                return;
            }
            Block $$5 = $$4.m_60734_();
            ItemStack $$6 = $$5.m_7397_(this.f_91073_, $$3, $$4);
            if ($$6.m_41619_()) {
                return;
            }
            if ($$0 && Screen.m_96637_() && $$4.m_155947_()) {
                $$1 = this.f_91073_.m_7702_($$3);
            }
        } else if ($$2 == HitResult.Type.ENTITY && $$0) {
            Entity $$7 = ((EntityHitResult)this.f_91077_).m_82443_();
            ItemStack $$8 = $$7.m_142340_();
            if ($$8 == null) {
                return;
            }
        } else {
            return;
        }
        if ($$9.m_41619_()) {
            String $$10 = "";
            if ($$2 == HitResult.Type.BLOCK) {
                $$10 = Registry.f_122824_.m_7981_(this.f_91073_.m_8055_(((BlockHitResult)this.f_91077_).m_82425_()).m_60734_()).toString();
            } else if ($$2 == HitResult.Type.ENTITY) {
                $$10 = Registry.f_122826_.m_7981_(((EntityHitResult)this.f_91077_).m_82443_().m_6095_()).toString();
            }
            f_90982_.warn("Picking on: [{}] {} gave null item", (Object)$$2, (Object)$$10);
            return;
        }
        Inventory $$11 = this.f_91074_.m_150109_();
        if ($$1 != null) {
            this.m_91122_((ItemStack)$$9, $$1);
        }
        int $$12 = $$11.m_36030_((ItemStack)$$9);
        if ($$0) {
            $$11.m_36012_((ItemStack)$$9);
            this.f_91072_.m_105241_(this.f_91074_.m_21120_(InteractionHand.MAIN_HAND), 36 + $$11.f_35977_);
        } else if ($$12 != -1) {
            if (Inventory.m_36045_($$12)) {
                $$11.f_35977_ = $$12;
            } else {
                this.f_91072_.m_105206_($$12);
            }
        }
    }

    private ItemStack m_91122_(ItemStack p_91123_, BlockEntity p_91124_) {
        CompoundTag $$2 = p_91124_.m_187480_();
        if (p_91123_.m_41720_() instanceof PlayerHeadItem && $$2.m_128441_("SkullOwner")) {
            CompoundTag $$3 = $$2.m_128469_("SkullOwner");
            p_91123_.m_41784_().m_128365_("SkullOwner", $$3);
            return p_91123_;
        }
        BlockItem.m_186338_(p_91123_, p_91124_.m_58903_(), $$2);
        CompoundTag $$4 = new CompoundTag();
        ListTag $$5 = new ListTag();
        $$5.add(StringTag.m_129297_("\"(+NBT)\""));
        $$4.m_128365_("Lore", $$5);
        p_91123_.m_41700_("display", $$4);
        return p_91123_;
    }

    public CrashReport m_91354_(CrashReport p_91355_) {
        SystemReport $$1 = p_91355_.m_178626_();
        Minecraft.m_167850_($$1, this, this.f_91039_, this.f_91001_, this.f_91066_);
        if (this.f_91073_ != null) {
            this.f_91073_.m_6026_(p_91355_);
        }
        if (this.f_91007_ != null) {
            this.f_91007_.m_177935_($$1);
        }
        this.f_167847_.m_168562_(p_91355_);
        return p_91355_;
    }

    public static void m_167872_(@Nullable Minecraft p_167873_, @Nullable LanguageManager p_167874_, String p_167875_, @Nullable Options p_167876_, CrashReport p_167877_) {
        SystemReport $$5 = p_167877_.m_178626_();
        Minecraft.m_167850_($$5, p_167873_, p_167874_, p_167875_, p_167876_);
    }

    private static SystemReport m_167850_(SystemReport p_167851_, @Nullable Minecraft p_167852_, @Nullable LanguageManager p_167853_, String p_167854_, Options p_167855_) {
        p_167851_.m_143522_("Launched Version", () -> p_167854_);
        p_167851_.m_143522_("Backend library", RenderSystem::m_69517_);
        p_167851_.m_143522_("Backend API", RenderSystem::m_69516_);
        p_167851_.m_143522_("Window size", () -> p_167852_ != null ? p_231365_.f_90990_.m_85441_() + "x" + p_231365_.f_90990_.m_85442_() : "<not initialized>");
        p_167851_.m_143522_("GL Caps", RenderSystem::m_69518_);
        p_167851_.m_143522_("GL debug messages", () -> GlDebug.m_166226_() ? String.join((CharSequence)"\n", GlDebug.m_166225_()) : "<disabled>");
        p_167851_.m_143522_("Using VBOs", () -> "Yes");
        p_167851_.m_143522_("Is Modded", () -> Minecraft.m_193589_().m_184605_());
        p_167851_.m_143519_("Type", "Client (map_client.txt)");
        if (p_167855_ != null) {
            String $$5;
            if (f_90981_ != null && ($$5 = f_90981_.m_91105_().m_109256_()) != null) {
                p_167851_.m_143519_("GPU Warnings", $$5);
            }
            p_167851_.m_143519_("Graphics mode", p_167855_.m_232060_().m_231551_().toString());
            p_167851_.m_143522_("Resource Packs", () -> {
                StringBuilder $$1 = new StringBuilder();
                for (String $$2 : p_231367_.f_92117_) {
                    if ($$1.length() > 0) {
                        $$1.append(", ");
                    }
                    $$1.append($$2);
                    if (!p_231367_.f_92118_.contains($$2)) continue;
                    $$1.append(" (incompatible)");
                }
                return $$1.toString();
            });
        }
        if (p_167853_ != null) {
            p_167851_.m_143522_("Current Language", () -> p_167853_.m_118983_().toString());
        }
        p_167851_.m_143522_("CPU", GlUtil::m_84819_);
        return p_167851_;
    }

    public static Minecraft m_91087_() {
        return f_90981_;
    }

    public CompletableFuture<Void> m_91088_() {
        return this.m_18691_(this::m_91391_).thenCompose(p_231391_ -> p_231391_);
    }

    public void m_91158_(@Nullable ServerData p_91159_) {
        this.f_91008_ = p_91159_;
        ReportEnvironment $$1 = p_91159_ != null ? ReportEnvironment.m_238998_(p_91159_.f_105363_) : ReportEnvironment.m_239898_();
        this.m_239476_($$1);
    }

    public void m_239026_(RealmsServer p_239027_, String p_239028_) {
        this.f_91008_ = p_239027_.m_87522_(p_239028_);
        this.m_239476_(ReportEnvironment.m_239764_(p_239027_));
    }

    private void m_239476_(ReportEnvironment p_239477_) {
        if (!this.f_238638_.m_239733_(p_239477_)) {
            this.f_238638_ = ReportingContext.m_239685_(p_239477_, this.f_193584_);
        }
    }

    @Nullable
    public ServerData m_91089_() {
        return this.f_91008_;
    }

    public boolean m_91090_() {
        return this.f_91010_;
    }

    public boolean m_91091_() {
        return this.f_91010_ && this.f_91007_ != null;
    }

    @Nullable
    public IntegratedServer m_91092_() {
        return this.f_91007_;
    }

    public User m_91094_() {
        return this.f_90998_;
    }

    public PropertyMap m_91095_() {
        if (this.f_90986_.isEmpty()) {
            GameProfile $$0 = this.m_91108_().fillProfileProperties(this.f_90998_.m_92548_(), false);
            this.f_90986_.putAll((Multimap)$$0.getProperties());
        }
        return this.f_90986_;
    }

    public Proxy m_91096_() {
        return this.f_91030_;
    }

    public TextureManager m_91097_() {
        return this.f_90987_;
    }

    public ResourceManager m_91098_() {
        return this.f_91036_;
    }

    public PackRepository m_91099_() {
        return this.f_91038_;
    }

    public ClientPackSource m_91100_() {
        return this.f_91037_;
    }

    public File m_91101_() {
        return this.f_90985_;
    }

    public LanguageManager m_91102_() {
        return this.f_91039_;
    }

    public Function<ResourceLocation, TextureAtlasSprite> m_91258_(ResourceLocation p_91259_) {
        return this.f_91051_.m_119428_(p_91259_)::m_118316_;
    }

    public boolean m_91103_() {
        return this.f_91032_;
    }

    public boolean m_91104_() {
        return this.f_91012_;
    }

    public GpuWarnlistManager m_91105_() {
        return this.f_91047_;
    }

    public SoundManager m_91106_() {
        return this.f_91043_;
    }

    public Music m_91107_() {
        if (this.f_91080_ instanceof WinScreen) {
            return Musics.f_11647_;
        }
        if (this.f_91074_ != null) {
            if (this.f_91074_.f_19853_.m_46472_() == Level.f_46430_) {
                if (this.f_91065_.m_93090_().m_93713_()) {
                    return Musics.f_11648_;
                }
                return Musics.f_11649_;
            }
            Holder<Biome> $$0 = this.f_91074_.f_19853_.m_204166_(this.f_91074_.m_20183_());
            if (this.f_91044_.m_120187_(Musics.f_11650_) || this.f_91074_.m_5842_() && $$0.m_203656_(BiomeTags.f_215801_)) {
                return Musics.f_11650_;
            }
            if (this.f_91074_.f_19853_.m_46472_() != Level.f_46429_ && this.f_91074_.m_150110_().f_35937_ && this.f_91074_.m_150110_().f_35936_) {
                return Musics.f_11646_;
            }
            return $$0.m_203334_().m_47566_().orElse(Musics.f_11651_);
        }
        return Musics.f_11645_;
    }

    public MinecraftSessionService m_91108_() {
        return this.f_91048_;
    }

    public SkinManager m_91109_() {
        return this.f_91050_;
    }

    @Nullable
    public Entity m_91288_() {
        return this.f_91075_;
    }

    public void m_91118_(Entity p_91119_) {
        this.f_91075_ = p_91119_;
        this.f_91063_.m_109106_(p_91119_);
    }

    public boolean m_91314_(Entity p_91315_) {
        return p_91315_.m_142038_() || this.f_91074_ != null && this.f_91074_.m_5833_() && this.f_91066_.f_92054_.m_90857_() && p_91315_.m_6095_() == EntityType.f_20532_;
    }

    @Override
    protected Thread m_6304_() {
        return this.f_91018_;
    }

    @Override
    protected Runnable m_6681_(Runnable p_91376_) {
        return p_91376_;
    }

    @Override
    protected boolean m_6362_(Runnable p_91365_) {
        return true;
    }

    public BlockRenderDispatcher m_91289_() {
        return this.f_91052_;
    }

    public EntityRenderDispatcher m_91290_() {
        return this.f_90994_;
    }

    public BlockEntityRenderDispatcher m_167982_() {
        return this.f_167845_;
    }

    public ItemRenderer m_91291_() {
        return this.f_90995_;
    }

    public <T> SearchTree<T> m_231372_(SearchRegistry.Key<T> p_231373_) {
        return this.f_90997_.m_235230_(p_231373_);
    }

    public <T> void m_231374_(SearchRegistry.Key<T> p_231375_, List<T> p_231376_) {
        this.f_90997_.m_235235_(p_231375_, p_231376_);
    }

    public FrameTimer m_91293_() {
        return this.f_91070_;
    }

    public boolean m_91294_() {
        return this.f_91017_;
    }

    public void m_91372_(boolean p_91373_) {
        this.f_91017_ = p_91373_;
    }

    public DataFixer m_91295_() {
        return this.f_90988_;
    }

    public float m_91296_() {
        return this.f_90991_.f_92518_;
    }

    public float m_91297_() {
        return this.f_90991_.f_92519_;
    }

    public BlockColors m_91298_() {
        return this.f_91040_;
    }

    public boolean m_91299_() {
        return this.f_91074_ != null && this.f_91074_.m_36330_() || this.f_91066_.m_231824_().m_231551_() != false;
    }

    public ToastComponent m_91300_() {
        return this.f_91003_;
    }

    public Tutorial m_91301_() {
        return this.f_91005_;
    }

    public boolean m_91302_() {
        return this.f_91022_;
    }

    public HotbarManager m_91303_() {
        return this.f_91000_;
    }

    public ModelManager m_91304_() {
        return this.f_91051_;
    }

    public PaintingTextureManager m_91305_() {
        return this.f_91053_;
    }

    public MobEffectTextureManager m_91306_() {
        return this.f_91054_;
    }

    @Override
    public void m_7440_(boolean p_91261_) {
        this.f_91022_ = p_91261_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Component m_167899_(File p_167900_, int p_167901_, int p_167902_) {
        int $$3 = this.f_90990_.m_85441_();
        int $$4 = this.f_90990_.m_85442_();
        TextureTarget $$5 = new TextureTarget(p_167901_, p_167902_, true, f_91002_);
        float $$6 = this.f_91074_.m_146909_();
        float $$7 = this.f_91074_.m_146908_();
        float $$8 = this.f_91074_.f_19860_;
        float $$9 = this.f_91074_.f_19859_;
        this.f_91063_.m_172775_(false);
        try {
            this.f_91063_.m_172779_(true);
            this.f_91060_.m_173014_();
            this.f_90990_.m_166450_(p_167901_);
            this.f_90990_.m_166452_(p_167902_);
            for (int $$10 = 0; $$10 < 6; ++$$10) {
                switch ($$10) {
                    case 0: {
                        this.f_91074_.m_146922_($$7);
                        this.f_91074_.m_146926_(0.0f);
                        break;
                    }
                    case 1: {
                        this.f_91074_.m_146922_(($$7 + 90.0f) % 360.0f);
                        this.f_91074_.m_146926_(0.0f);
                        break;
                    }
                    case 2: {
                        this.f_91074_.m_146922_(($$7 + 180.0f) % 360.0f);
                        this.f_91074_.m_146926_(0.0f);
                        break;
                    }
                    case 3: {
                        this.f_91074_.m_146922_(($$7 - 90.0f) % 360.0f);
                        this.f_91074_.m_146926_(0.0f);
                        break;
                    }
                    case 4: {
                        this.f_91074_.m_146922_($$7);
                        this.f_91074_.m_146926_(-90.0f);
                        break;
                    }
                    default: {
                        this.f_91074_.m_146922_($$7);
                        this.f_91074_.m_146926_(90.0f);
                    }
                }
                this.f_91074_.f_19859_ = this.f_91074_.m_146908_();
                this.f_91074_.f_19860_ = this.f_91074_.m_146909_();
                $$5.m_83947_(true);
                this.f_91063_.m_109089_(1.0f, 0L, new PoseStack());
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                Screenshot.m_92295_(p_167900_, "panorama_" + $$10 + ".png", $$5, p_231415_ -> {});
            }
            MutableComponent $$11 = Component.m_237113_(p_167900_.getName()).m_130940_(ChatFormatting.UNDERLINE).m_130938_(p_231426_ -> p_231426_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, p_167900_.getAbsolutePath())));
            MutableComponent mutableComponent = Component.m_237110_("screenshot.success", $$11);
            return mutableComponent;
        }
        catch (Exception $$12) {
            f_90982_.error("Couldn't save image", (Throwable)$$12);
            MutableComponent mutableComponent = Component.m_237110_("screenshot.failure", $$12.getMessage());
            return mutableComponent;
        }
        finally {
            this.f_91074_.m_146926_($$6);
            this.f_91074_.m_146922_($$7);
            this.f_91074_.f_19860_ = $$8;
            this.f_91074_.f_19859_ = $$9;
            this.f_91063_.m_172775_(true);
            this.f_90990_.m_166450_($$3);
            this.f_90990_.m_166452_($$4);
            $$5.m_83930_();
            this.f_91063_.m_172779_(false);
            this.f_91060_.m_173014_();
            this.m_91385_().m_83947_(true);
        }
    }

    private Component m_167903_(File p_167904_, int p_167905_, int p_167906_, int p_167907_, int p_167908_) {
        try {
            ByteBuffer $$5 = GlUtil.m_166247_(p_167905_ * p_167906_ * 3);
            Screenshot $$6 = new Screenshot(p_167904_, p_167907_, p_167908_, p_167906_);
            float $$7 = (float)p_167907_ / (float)p_167905_;
            float $$8 = (float)p_167908_ / (float)p_167906_;
            float $$9 = $$7 > $$8 ? $$7 : $$8;
            for (int $$10 = (p_167908_ - 1) / p_167906_ * p_167906_; $$10 >= 0; $$10 -= p_167906_) {
                for (int $$11 = 0; $$11 < p_167907_; $$11 += p_167905_) {
                    RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
                    float $$12 = (float)(p_167907_ - p_167905_) / 2.0f * 2.0f - (float)($$11 * 2);
                    float $$13 = (float)(p_167908_ - p_167906_) / 2.0f * 2.0f - (float)($$10 * 2);
                    this.f_91063_.m_172718_($$9, $$12 /= (float)p_167905_, $$13 /= (float)p_167906_);
                    $$5.clear();
                    RenderSystem.m_69854_(3333, 1);
                    RenderSystem.m_69854_(3317, 1);
                    RenderSystem.m_69871_(0, 0, p_167905_, p_167906_, 32992, 5121, $$5);
                    $$6.m_168609_($$5, $$11, $$10, p_167905_, p_167906_);
                }
                $$6.m_168605_();
            }
            File $$14 = $$6.m_168615_();
            GlUtil.m_166251_($$5);
            MutableComponent $$15 = Component.m_237113_($$14.getName()).m_130940_(ChatFormatting.UNDERLINE).m_130938_(p_231379_ -> p_231379_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, $$14.getAbsolutePath())));
            return Component.m_237110_("screenshot.success", $$15);
        }
        catch (Exception $$16) {
            f_90982_.warn("Couldn't save screenshot", (Throwable)$$16);
            return Component.m_237110_("screenshot.failure", $$16.getMessage());
        }
    }

    public ProfilerFiller m_91307_() {
        return this.f_91026_;
    }

    public Game m_91309_() {
        return this.f_91004_;
    }

    @Nullable
    public StoringChunkProgressListener m_167983_() {
        return this.f_90999_.get();
    }

    public SplashManager m_91310_() {
        return this.f_91046_;
    }

    @Nullable
    public Overlay m_91265_() {
        return this.f_91081_;
    }

    public PlayerSocialManager m_91266_() {
        return this.f_91006_;
    }

    public boolean m_91267_() {
        return false;
    }

    public Window m_91268_() {
        return this.f_90990_;
    }

    public RenderBuffers m_91269_() {
        return this.f_90993_;
    }

    private static Pack m_167933_(String p_167934_, Component p_167935_, boolean p_167936_, Supplier<PackResources> p_167937_, PackMetadataSection p_167938_, Pack.Position p_167939_, PackSource p_167940_) {
        int $$7 = p_167938_.m_10374_();
        Supplier<PackResources> $$8 = p_167937_;
        if ($$7 <= 3) {
            $$8 = Minecraft.m_91330_($$8);
        }
        if ($$7 <= 4) {
            $$8 = Minecraft.m_91352_($$8);
        }
        return new Pack(p_167934_, p_167935_, p_167936_, $$8, p_167938_, PackType.CLIENT_RESOURCES, p_167939_, p_167940_);
    }

    private static Supplier<PackResources> m_91330_(Supplier<PackResources> p_91331_) {
        return () -> new LegacyPackResourcesAdapter((PackResources)p_91331_.get(), LegacyPackResourcesAdapter.f_118690_);
    }

    private static Supplier<PackResources> m_91352_(Supplier<PackResources> p_91353_) {
        return () -> new PackResourcesAdapterV4((PackResources)p_91353_.get());
    }

    public void m_91312_(int p_91313_) {
        this.f_91051_.m_119410_(p_91313_);
    }

    public EntityModelSet m_167973_() {
        return this.f_167844_;
    }

    public boolean m_167974_() {
        return this.f_193584_.properties().flag(UserApiService.UserFlag.PROFANITY_FILTER_ENABLED);
    }

    public void m_193588_() {
        this.f_91006_.m_194059_();
    }

    public Realms32BitWarningStatus m_231416_() {
        return this.f_231343_;
    }

    public SignatureValidator m_231417_() {
        return this.f_231339_;
    }

    public GameNarrator m_240477_() {
        return this.f_240365_;
    }

    public ChatListener m_240442_() {
        return this.f_240378_;
    }

    public ReportingContext m_239211_() {
        return this.f_238638_;
    }

    public RealmsDataFetcher m_239420_() {
        return this.f_238717_;
    }

    static {
        f_90982_ = LogUtils.getLogger();
        f_91002_ = Util.m_137581_() == Util.OS.OSX;
        f_91055_ = new ResourceLocation("default");
        f_91058_ = new ResourceLocation("uniform");
        f_91059_ = new ResourceLocation("alt");
        f_205119_ = new ResourceLocation("regional_compliancies.json");
        f_90983_ = CompletableFuture.completedFuture(Unit.INSTANCE);
        f_90984_ = Component.m_237115_("multiplayer.socialInteractions.not_available");
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class ChatStatus
    extends Enum<ChatStatus> {
        public static final /* enum */ ChatStatus ENABLED = new ChatStatus(CommonComponents.f_237098_){

            @Override
            public boolean m_142594_(boolean p_168045_) {
                return true;
            }
        };
        public static final /* enum */ ChatStatus DISABLED_BY_OPTIONS = new ChatStatus(Component.m_237115_("chat.disabled.options").m_130940_(ChatFormatting.RED)){

            @Override
            public boolean m_142594_(boolean p_168051_) {
                return false;
            }
        };
        public static final /* enum */ ChatStatus DISABLED_BY_LAUNCHER = new ChatStatus(Component.m_237115_("chat.disabled.launcher").m_130940_(ChatFormatting.RED)){

            @Override
            public boolean m_142594_(boolean p_168057_) {
                return p_168057_;
            }
        };
        public static final /* enum */ ChatStatus DISABLED_BY_PROFILE = new ChatStatus(Component.m_237110_("chat.disabled.profile", Component.m_237117_(Minecraft.f_90981_.f_91066_.f_92098_.m_90860_())).m_130940_(ChatFormatting.RED)){

            @Override
            public boolean m_142594_(boolean p_168063_) {
                return p_168063_;
            }
        };
        static final Component f_238170_;
        private static final String f_238169_ = "https://aka.ms/JavaAccountSettings";
        private final Component f_168027_;
        private static final /* synthetic */ ChatStatus[] $VALUES;

        public static ChatStatus[] values() {
            return (ChatStatus[])$VALUES.clone();
        }

        public static ChatStatus valueOf(String p_168038_) {
            return Enum.valueOf(ChatStatus.class, p_168038_);
        }

        ChatStatus(Component p_168033_) {
            this.f_168027_ = p_168033_;
        }

        public Component m_168034_() {
            return this.f_168027_;
        }

        public abstract boolean m_142594_(boolean var1);

        private static /* synthetic */ ChatStatus[] m_168036_() {
            return new ChatStatus[]{ENABLED, DISABLED_BY_OPTIONS, DISABLED_BY_LAUNCHER, DISABLED_BY_PROFILE};
        }

        static {
            $VALUES = ChatStatus.m_168036_();
            f_238170_ = Component.m_237115_("chat.disabled.profile.moreInfo");
        }
    }
}

