/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.logging.LogUtils
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import io.netty.buffer.Unpooled;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.ClientTelemetryManager;
import net.minecraft.client.DebugQueryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.DemoIntroScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.achievement.StatsUpdateListener;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.ChatPreviewWarningScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.debug.BeeDebugRenderer;
import net.minecraft.client.renderer.debug.BrainDebugRenderer;
import net.minecraft.client.renderer.debug.GoalSelectorDebugRenderer;
import net.minecraft.client.renderer.debug.NeighborsUpdateRenderer;
import net.minecraft.client.renderer.debug.WorldGenAttemptRenderer;
import net.minecraft.client.resources.sounds.BeeAggressiveSoundInstance;
import net.minecraft.client.resources.sounds.BeeFlyingSoundInstance;
import net.minecraft.client.resources.sounds.GuardianAttackSoundInstance;
import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.LastSeenMessagesTracker;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChatPreviewPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatHeaderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayChatPreviewPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.slf4j.Logger;

public class ClientPacketListener
implements ClientGamePacketListener {
    private static final Logger f_104883_ = LogUtils.getLogger();
    private static final Component f_104884_ = Component.m_237115_("disconnect.lost");
    private static final Component f_242953_ = Component.m_237115_("multiplayer.unsecureserver.toast.title");
    private static final Component f_242949_ = Component.m_237115_("multiplayer.unsecureserver.toast");
    private static final int f_241624_ = 64;
    private final Connection f_104885_;
    private final GameProfile f_104886_;
    private final Screen f_104887_;
    private final Minecraft f_104888_;
    private ClientLevel f_104889_;
    private ClientLevel.ClientLevelData f_104890_;
    private final Map<UUID, PlayerInfo> f_104892_ = Maps.newHashMap();
    private final ClientAdvancements f_104893_;
    private final ClientSuggestionProvider f_104894_;
    private final DebugQueryHandler f_104896_ = new DebugQueryHandler(this);
    private int f_104897_ = 3;
    private int f_194190_ = 3;
    private final RandomSource f_104898_ = RandomSource.m_216337_();
    private CommandDispatcher<SharedSuggestionProvider> f_104899_ = new CommandDispatcher();
    private final RecipeManager f_104900_ = new RecipeManager();
    private final UUID f_104901_ = UUID.randomUUID();
    private Set<ResourceKey<Level>> f_104902_;
    private RegistryAccess.Frozen f_104903_ = RegistryAccess.f_123049_.get();
    private final ClientTelemetryManager f_194191_;
    private final SignedMessageChain.Encoder f_240902_ = new SignedMessageChain().m_241042_();
    private final LastSeenMessagesTracker f_241688_ = new LastSeenMessagesTracker(5);
    private Optional<LastSeenMessages.Entry> f_241621_ = Optional.empty();
    private int f_241629_;

    public ClientPacketListener(Minecraft p_194193_, Screen p_194194_, Connection p_194195_, GameProfile p_194196_, ClientTelemetryManager p_194197_) {
        this.f_104888_ = p_194193_;
        this.f_104887_ = p_194194_;
        this.f_104885_ = p_194195_;
        this.f_104886_ = p_194196_;
        this.f_104893_ = new ClientAdvancements(p_194193_);
        this.f_104894_ = new ClientSuggestionProvider(this, p_194193_);
        this.f_194191_ = p_194197_;
    }

    public ClientSuggestionProvider m_105137_() {
        return this.f_104894_;
    }

    public void m_105140_() {
        this.f_104889_ = null;
    }

    public RecipeManager m_105141_() {
        return this.f_104900_;
    }

    @Override
    public void m_5998_(ClientboundLoginPacket p_105030_) {
        ClientLevel.ClientLevelData $$6;
        PacketUtils.m_131363_(p_105030_, this, this.f_104888_);
        this.f_104888_.f_91072_ = new MultiPlayerGameMode(this.f_104888_, this);
        this.f_104903_ = p_105030_.f_132366_();
        if (!this.f_104885_.m_129531_()) {
            this.f_104903_.m_206193_().forEach(p_205542_ -> p_205542_.f_206234_().m_203635_());
        }
        ArrayList $$1 = Lists.newArrayList(p_105030_.f_132365_());
        Collections.shuffle($$1);
        this.f_104902_ = Sets.newLinkedHashSet((Iterable)$$1);
        ResourceKey<Level> $$2 = p_105030_.f_132368_();
        Holder<DimensionType> $$3 = this.f_104903_.m_175515_(Registry.f_122818_).m_206081_(p_105030_.f_132367_());
        this.f_104897_ = p_105030_.f_132370_();
        this.f_194190_ = p_105030_.f_195761_();
        boolean $$4 = p_105030_.f_132373_();
        boolean $$5 = p_105030_.f_132374_();
        this.f_104890_ = $$6 = new ClientLevel.ClientLevelData(Difficulty.NORMAL, p_105030_.f_132362_(), $$5);
        this.f_104889_ = new ClientLevel(this, $$6, $$2, $$3, this.f_104897_, this.f_194190_, this.f_104888_::m_91307_, this.f_104888_.f_91060_, $$4, p_105030_.f_132361_());
        this.f_104888_.m_91156_(this.f_104889_);
        if (this.f_104888_.f_91074_ == null) {
            this.f_104888_.f_91074_ = this.f_104888_.f_91072_.m_105246_(this.f_104889_, new StatsCounter(), new ClientRecipeBook());
            this.f_104888_.f_91074_.m_146922_(-180.0f);
            if (this.f_104888_.m_91092_() != null) {
                this.f_104888_.m_91092_().m_120046_(this.f_104888_.f_91074_.m_20148_());
            }
        }
        this.f_104888_.f_91064_.m_113434_();
        this.f_104888_.f_91074_.m_172530_();
        int $$7 = p_105030_.f_132360_();
        this.f_104888_.f_91074_.m_20234_($$7);
        this.f_104889_.m_104630_($$7, this.f_104888_.f_91074_);
        this.f_104888_.f_91074_.f_108618_ = new KeyboardInput(this.f_104888_.f_91066_);
        this.f_104888_.f_91072_.m_105221_(this.f_104888_.f_91074_);
        this.f_104888_.f_91075_ = this.f_104888_.f_91074_;
        this.f_104888_.m_91152_(new ReceivingLevelScreen());
        this.f_104888_.f_91074_.m_36393_(p_105030_.f_132371_());
        this.f_104888_.f_91074_.m_108711_(p_105030_.f_132372_());
        this.f_104888_.f_91074_.m_219749_(p_105030_.f_238174_());
        this.f_104888_.f_91072_.m_171805_(p_105030_.f_132363_(), p_105030_.f_132364_());
        this.f_104888_.f_91066_.m_193770_(p_105030_.f_132370_());
        this.f_104888_.f_91066_.m_92172_();
        this.f_104885_.m_129512_(new ServerboundCustomPayloadPacket(ServerboundCustomPayloadPacket.f_133979_, new FriendlyByteBuf(Unpooled.buffer()).m_130070_(ClientBrandRetriever.m_129629_())));
        this.f_104888_.m_91309_().m_90739_();
        this.f_194191_.m_193545_(p_105030_.f_132363_(), p_105030_.f_132362_());
    }

    @Override
    public void m_6771_(ClientboundAddEntityPacket p_104958_) {
        PacketUtils.m_131363_(p_104958_, this, this.f_104888_);
        EntityType<?> $$1 = p_104958_.m_131508_();
        Object $$2 = $$1.m_20615_(this.f_104889_);
        if ($$2 != null) {
            ((Entity)$$2).m_141965_(p_104958_);
            int $$3 = p_104958_.m_131496_();
            this.f_104889_.m_104627_($$3, (Entity)$$2);
            this.m_233663_((Entity)$$2);
        } else {
            f_104883_.warn("Skipping Entity with id {}", $$1);
        }
    }

    private void m_233663_(Entity p_233664_) {
        if (p_233664_ instanceof AbstractMinecart) {
            this.f_104888_.m_91106_().m_120367_(new MinecartSoundInstance((AbstractMinecart)p_233664_));
        } else if (p_233664_ instanceof Bee) {
            BeeFlyingSoundInstance $$3;
            boolean $$1 = ((Bee)p_233664_).m_21660_();
            if ($$1) {
                BeeAggressiveSoundInstance $$2 = new BeeAggressiveSoundInstance((Bee)p_233664_);
            } else {
                $$3 = new BeeFlyingSoundInstance((Bee)p_233664_);
            }
            this.f_104888_.m_91106_().m_120372_($$3);
        }
    }

    @Override
    public void m_7708_(ClientboundAddExperienceOrbPacket p_104960_) {
        PacketUtils.m_131363_(p_104960_, this, this.f_104888_);
        double $$1 = p_104960_.m_131527_();
        double $$2 = p_104960_.m_131528_();
        double $$3 = p_104960_.m_131529_();
        ExperienceOrb $$4 = new ExperienceOrb(this.f_104889_, $$1, $$2, $$3, p_104960_.m_131530_());
        $$4.m_217006_($$1, $$2, $$3);
        $$4.m_146922_(0.0f);
        $$4.m_146926_(0.0f);
        $$4.m_20234_(p_104960_.m_131524_());
        this.f_104889_.m_104627_(p_104960_.m_131524_(), $$4);
    }

    @Override
    public void m_8048_(ClientboundSetEntityMotionPacket p_105092_) {
        PacketUtils.m_131363_(p_105092_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105092_.m_133192_());
        if ($$1 == null) {
            return;
        }
        $$1.m_6001_((double)p_105092_.m_133195_() / 8000.0, (double)p_105092_.m_133196_() / 8000.0, (double)p_105092_.m_133197_() / 8000.0);
    }

    @Override
    public void m_6455_(ClientboundSetEntityDataPacket p_105088_) {
        PacketUtils.m_131363_(p_105088_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105088_.m_133159_());
        if ($$1 != null && p_105088_.m_133156_() != null) {
            $$1.m_20088_().m_135356_(p_105088_.m_133156_());
        }
    }

    @Override
    public void m_6482_(ClientboundAddPlayerPacket p_104966_) {
        PacketUtils.m_131363_(p_104966_, this, this.f_104888_);
        PlayerInfo $$1 = this.m_104949_(p_104966_.m_131606_());
        if ($$1 == null) {
            f_104883_.warn("Server attempted to add player prior to sending player info (Player id {})", (Object)p_104966_.m_131606_());
            return;
        }
        double $$2 = p_104966_.m_131607_();
        double $$3 = p_104966_.m_131608_();
        double $$4 = p_104966_.m_131609_();
        float $$5 = (float)(p_104966_.m_131610_() * 360) / 256.0f;
        float $$6 = (float)(p_104966_.m_131611_() * 360) / 256.0f;
        int $$7 = p_104966_.m_131603_();
        RemotePlayer $$8 = new RemotePlayer(this.f_104888_.f_91073_, $$1.m_105312_(), $$1.m_233764_());
        $$8.m_20234_($$7);
        $$8.m_217006_($$2, $$3, $$4);
        $$8.m_19890_($$2, $$3, $$4, $$5, $$6);
        $$8.m_146867_();
        this.f_104889_.m_104630_($$7, $$8);
    }

    @Override
    public void m_6435_(ClientboundTeleportEntityPacket p_105124_) {
        PacketUtils.m_131363_(p_105124_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105124_.m_133545_());
        if ($$1 == null) {
            return;
        }
        double $$2 = p_105124_.m_133548_();
        double $$3 = p_105124_.m_133549_();
        double $$4 = p_105124_.m_133550_();
        $$1.m_217006_($$2, $$3, $$4);
        if (!$$1.m_6109_()) {
            float $$5 = (float)(p_105124_.m_133551_() * 360) / 256.0f;
            float $$6 = (float)(p_105124_.m_133552_() * 360) / 256.0f;
            $$1.m_6453_($$2, $$3, $$4, $$5, $$6, 3, true);
            $$1.m_6853_(p_105124_.m_133553_());
        }
    }

    @Override
    public void m_5612_(ClientboundSetCarriedItemPacket p_105078_) {
        PacketUtils.m_131363_(p_105078_, this, this.f_104888_);
        if (Inventory.m_36045_(p_105078_.m_133079_())) {
            this.f_104888_.f_91074_.m_150109_().f_35977_ = p_105078_.m_133079_();
        }
    }

    @Override
    public void m_7865_(ClientboundMoveEntityPacket p_105036_) {
        PacketUtils.m_131363_(p_105036_, this, this.f_104888_);
        Entity $$1 = p_105036_.m_132519_(this.f_104889_);
        if ($$1 == null) {
            return;
        }
        if (!$$1.m_6109_()) {
            if (p_105036_.m_132534_()) {
                VecDeltaCodec $$2 = $$1.m_217001_();
                Vec3 $$3 = $$2.m_238021_(p_105036_.m_178997_(), p_105036_.m_178998_(), p_105036_.m_178999_());
                $$2.m_238033_($$3);
                float $$4 = p_105036_.m_132533_() ? (float)(p_105036_.m_132531_() * 360) / 256.0f : $$1.m_146908_();
                float $$5 = p_105036_.m_132533_() ? (float)(p_105036_.m_132532_() * 360) / 256.0f : $$1.m_146909_();
                $$1.m_6453_($$3.m_7096_(), $$3.m_7098_(), $$3.m_7094_(), $$4, $$5, 3, false);
            } else if (p_105036_.m_132533_()) {
                float $$6 = (float)(p_105036_.m_132531_() * 360) / 256.0f;
                float $$7 = (float)(p_105036_.m_132532_() * 360) / 256.0f;
                $$1.m_6453_($$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), $$6, $$7, 3, false);
            }
            $$1.m_6853_(p_105036_.m_132535_());
        }
    }

    @Override
    public void m_6176_(ClientboundRotateHeadPacket p_105068_) {
        PacketUtils.m_131363_(p_105068_, this, this.f_104888_);
        Entity $$1 = p_105068_.m_132969_(this.f_104889_);
        if ($$1 == null) {
            return;
        }
        float $$2 = (float)(p_105068_.m_132977_() * 360) / 256.0f;
        $$1.m_6541_($$2, 3);
    }

    @Override
    public void m_182047_(ClientboundRemoveEntitiesPacket p_182633_) {
        PacketUtils.m_131363_(p_182633_, this, this.f_104888_);
        p_182633_.m_182730_().forEach(p_205521_ -> this.f_104889_.m_171642_(p_205521_, Entity.RemovalReason.DISCARDED));
    }

    @Override
    public void m_5682_(ClientboundPlayerPositionPacket p_105056_) {
        double $$17;
        double $$16;
        double $$13;
        double $$12;
        double $$9;
        double $$8;
        PacketUtils.m_131363_(p_105056_, this, this.f_104888_);
        LocalPlayer $$1 = this.f_104888_.f_91074_;
        if (p_105056_.m_179159_()) {
            ((Player)$$1).m_6038_();
        }
        Vec3 $$2 = $$1.m_20184_();
        boolean $$3 = p_105056_.m_132826_().contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.X);
        boolean $$4 = p_105056_.m_132826_().contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.Y);
        boolean $$5 = p_105056_.m_132826_().contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.Z);
        if ($$3) {
            double $$6 = $$2.m_7096_();
            double $$7 = $$1.m_20185_() + p_105056_.m_132818_();
            $$1.f_19790_ += p_105056_.m_132818_();
        } else {
            $$8 = 0.0;
            $$1.f_19790_ = $$9 = p_105056_.m_132818_();
        }
        if ($$4) {
            double $$10 = $$2.m_7098_();
            double $$11 = $$1.m_20186_() + p_105056_.m_132821_();
            $$1.f_19791_ += p_105056_.m_132821_();
        } else {
            $$12 = 0.0;
            $$1.f_19791_ = $$13 = p_105056_.m_132821_();
        }
        if ($$5) {
            double $$14 = $$2.m_7094_();
            double $$15 = $$1.m_20189_() + p_105056_.m_132822_();
            $$1.f_19792_ += p_105056_.m_132822_();
        } else {
            $$16 = 0.0;
            $$1.f_19792_ = $$17 = p_105056_.m_132822_();
        }
        $$1.m_20343_($$9, $$13, $$17);
        $$1.f_19854_ = $$9;
        $$1.f_19855_ = $$13;
        $$1.f_19856_ = $$17;
        $$1.m_20334_($$8, $$12, $$16);
        float $$18 = p_105056_.m_132823_();
        float $$19 = p_105056_.m_132824_();
        if (p_105056_.m_132826_().contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.X_ROT)) {
            $$19 += $$1.m_146909_();
        }
        if (p_105056_.m_132826_().contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT)) {
            $$18 += $$1.m_146908_();
        }
        $$1.m_19890_($$9, $$13, $$17, $$18, $$19);
        this.f_104885_.m_129512_(new ServerboundAcceptTeleportationPacket(p_105056_.m_132825_()));
        this.f_104885_.m_129512_(new ServerboundMovePlayerPacket.PosRot($$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), $$1.m_146908_(), $$1.m_146909_(), false));
    }

    @Override
    public void m_213565_(ClientboundChatPreviewPacket p_233700_) {
        PacketUtils.m_131363_(p_233700_, this, this.f_104888_);
        ChatScreen $$1 = this.f_104888_.f_91065_.m_93076_().m_232476_();
        if ($$1 != null) {
            $$1.m_232726_().m_232413_(p_233700_.f_237594_(), p_233700_.f_237595_());
        }
    }

    @Override
    public void m_214045_(ClientboundSetDisplayChatPreviewPacket p_233706_) {
        PacketUtils.m_131363_(p_233706_, this, this.f_104888_);
        ServerData $$1 = this.f_104888_.m_91089_();
        if ($$1 == null) {
            return;
        }
        $$1.m_233815_(p_233706_.f_237813_());
    }

    @Override
    public void m_5771_(ClientboundSectionBlocksUpdatePacket p_105070_) {
        PacketUtils.m_131363_(p_105070_, this, this.f_104888_);
        int $$1 = 0x13 | (p_105070_.m_133000_() ? 128 : 0);
        p_105070_.m_132992_((p_205524_, p_205525_) -> this.f_104889_.m_233653_((BlockPos)p_205524_, (BlockState)p_205525_, $$1));
    }

    @Override
    public void m_183388_(ClientboundLevelChunkWithLightPacket p_194241_) {
        PacketUtils.m_131363_(p_194241_, this, this.f_104888_);
        this.m_194198_(p_194241_.m_195717_(), p_194241_.m_195718_(), p_194241_.m_195719_());
        this.m_194202_(p_194241_.m_195717_(), p_194241_.m_195718_(), p_194241_.m_195720_());
    }

    private void m_194198_(int p_194199_, int p_194200_, ClientboundLevelChunkPacketData p_194201_) {
        this.f_104889_.m_7726_().m_194116_(p_194199_, p_194200_, p_194201_.m_195656_(), p_194201_.m_195678_(), p_194201_.m_195657_(p_194199_, p_194200_));
    }

    private void m_194202_(int p_194203_, int p_194204_, ClientboundLightUpdatePacketData p_194205_) {
        this.f_104889_.m_194171_(() -> {
            this.m_194248_(p_194203_, p_194204_, p_194205_);
            LevelChunk $$3 = this.f_104889_.m_7726_().m_62227_(p_194203_, p_194204_, false);
            if ($$3 != null) {
                this.m_194212_($$3, p_194203_, p_194204_);
            }
        });
    }

    private void m_194212_(LevelChunk p_194213_, int p_194214_, int p_194215_) {
        LevelLightEngine $$3 = this.f_104889_.m_7726_().m_7827_();
        LevelChunkSection[] $$4 = p_194213_.m_7103_();
        ChunkPos $$5 = p_194213_.m_7697_();
        $$3.m_6460_($$5, true);
        for (int $$6 = 0; $$6 < $$4.length; ++$$6) {
            LevelChunkSection $$7 = $$4[$$6];
            int $$8 = this.f_104889_.m_151568_($$6);
            $$3.m_6191_(SectionPos.m_123196_($$5, $$8), $$7.m_188008_());
            this.f_104889_.m_104793_(p_194214_, $$8, p_194215_);
        }
        this.f_104889_.m_197405_(p_194214_, p_194215_);
    }

    @Override
    public void m_5729_(ClientboundForgetLevelChunkPacket p_105014_) {
        PacketUtils.m_131363_(p_105014_, this, this.f_104888_);
        int $$1 = p_105014_.m_132149_();
        int $$2 = p_105014_.m_132152_();
        ClientChunkCache $$3 = this.f_104889_.m_7726_();
        $$3.m_104455_($$1, $$2);
        this.m_194252_(p_105014_);
    }

    private void m_194252_(ClientboundForgetLevelChunkPacket p_194253_) {
        this.f_104889_.m_194171_(() -> {
            LevelLightEngine $$1 = this.f_104889_.m_5518_();
            for (int $$2 = this.f_104889_.m_151560_(); $$2 < this.f_104889_.m_151561_(); ++$$2) {
                $$1.m_6191_(SectionPos.m_123173_(p_194253_.m_132149_(), $$2, p_194253_.m_132152_()), true);
            }
            $$1.m_6460_(new ChunkPos(p_194253_.m_132149_(), p_194253_.m_132152_()), false);
            this.f_104889_.m_197405_(p_194253_.m_132149_(), p_194253_.m_132152_());
        });
    }

    @Override
    public void m_6773_(ClientboundBlockUpdatePacket p_104980_) {
        PacketUtils.m_131363_(p_104980_, this, this.f_104888_);
        this.f_104889_.m_233653_(p_104980_.m_131749_(), p_104980_.m_131746_(), 19);
    }

    @Override
    public void m_6008_(ClientboundDisconnectPacket p_105008_) {
        this.f_104885_.m_129507_(p_105008_.m_132085_());
    }

    @Override
    public void m_7026_(Component p_104954_) {
        this.f_104888_.m_91399_();
        this.f_194191_.m_193544_();
        if (this.f_104887_ != null) {
            if (this.f_104887_ instanceof RealmsScreen) {
                this.f_104888_.m_91152_(new DisconnectedRealmsScreen(this.f_104887_, f_104884_, p_104954_));
            } else {
                this.f_104888_.m_91152_(new DisconnectedScreen(this.f_104887_, f_104884_, p_104954_));
            }
        } else {
            this.f_104888_.m_91152_(new DisconnectedScreen(new JoinMultiplayerScreen(new TitleScreen()), f_104884_, p_104954_));
        }
    }

    public void m_104955_(Packet<?> p_104956_) {
        this.f_104885_.m_129512_(p_104956_);
    }

    @Override
    public void m_8001_(ClientboundTakeItemEntityPacket p_105122_) {
        PacketUtils.m_131363_(p_105122_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105122_.m_133524_());
        LivingEntity $$2 = (LivingEntity)this.f_104889_.m_6815_(p_105122_.m_133527_());
        if ($$2 == null) {
            $$2 = this.f_104888_.f_91074_;
        }
        if ($$1 != null) {
            if ($$1 instanceof ExperienceOrb) {
                this.f_104889_.m_7785_($$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), SoundEvents.f_11871_, SoundSource.PLAYERS, 0.1f, (this.f_104898_.m_188501_() - this.f_104898_.m_188501_()) * 0.35f + 0.9f, false);
            } else {
                this.f_104889_.m_7785_($$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2f, (this.f_104898_.m_188501_() - this.f_104898_.m_188501_()) * 1.4f + 2.0f, false);
            }
            this.f_104888_.f_91061_.m_107344_(new ItemPickupParticle(this.f_104888_.m_91290_(), this.f_104888_.m_91269_(), this.f_104889_, $$1, $$2));
            if ($$1 instanceof ItemEntity) {
                ItemEntity $$3 = (ItemEntity)$$1;
                ItemStack $$4 = $$3.m_32055_();
                $$4.m_41774_(p_105122_.m_133528_());
                if ($$4.m_41619_()) {
                    this.f_104889_.m_171642_(p_105122_.m_133524_(), Entity.RemovalReason.DISCARDED);
                }
            } else if (!($$1 instanceof ExperienceOrb)) {
                this.f_104889_.m_171642_(p_105122_.m_133524_(), Entity.RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public void m_213990_(ClientboundSystemChatPacket p_233708_) {
        PacketUtils.m_131363_(p_233708_, this, this.f_104888_);
        this.f_104888_.m_240442_().m_240494_(p_233708_.f_237849_(), p_233708_.f_240374_());
    }

    @Override
    public void m_213629_(ClientboundPlayerChatPacket p_233702_) {
        PacketUtils.m_131363_(p_233702_, this, this.f_104888_);
        Optional<ChatType.Bound> $$1 = p_233702_.m_242662_(this.f_104903_);
        if (!$$1.isPresent()) {
            this.f_104885_.m_129507_(Component.m_237115_("multiplayer.disconnect.invalid_packet"));
            return;
        }
        this.f_104888_.m_240442_().m_240975_(p_233702_.f_240869_(), $$1.get());
    }

    @Override
    public void m_240948_(ClientboundPlayerChatHeaderPacket p_241547_) {
        PacketUtils.m_131363_(p_241547_, this, this.f_104888_);
        this.f_104888_.m_240442_().m_241138_(p_241547_.f_240876_(), p_241547_.f_240909_(), p_241547_.f_240888_());
    }

    @Override
    public void m_241037_(ClientboundDeleteChatPacket p_241325_) {
        PacketUtils.m_131363_(p_241325_, this, this.f_104888_);
        MessageSignature $$1 = p_241325_.f_240904_();
        if (!this.f_104888_.m_240442_().m_240956_($$1)) {
            this.f_104888_.f_91065_.m_93076_().m_240953_($$1);
        }
    }

    @Override
    public void m_7791_(ClientboundAnimatePacket p_104968_) {
        PacketUtils.m_131363_(p_104968_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_104968_.m_131624_());
        if ($$1 == null) {
            return;
        }
        if (p_104968_.m_131627_() == 0) {
            LivingEntity $$2 = (LivingEntity)$$1;
            $$2.m_6674_(InteractionHand.MAIN_HAND);
        } else if (p_104968_.m_131627_() == 3) {
            LivingEntity $$3 = (LivingEntity)$$1;
            $$3.m_6674_(InteractionHand.OFF_HAND);
        } else if (p_104968_.m_131627_() == 1) {
            $$1.m_6053_();
        } else if (p_104968_.m_131627_() == 2) {
            Player $$4 = (Player)$$1;
            $$4.m_6145_(false, false);
        } else if (p_104968_.m_131627_() == 4) {
            this.f_104888_.f_91061_.m_107329_($$1, ParticleTypes.f_123797_);
        } else if (p_104968_.m_131627_() == 5) {
            this.f_104888_.f_91061_.m_107329_($$1, ParticleTypes.f_123808_);
        }
    }

    @Override
    public void m_7885_(ClientboundSetTimePacket p_105108_) {
        PacketUtils.m_131363_(p_105108_, this, this.f_104888_);
        this.f_104888_.f_91073_.m_104637_(p_105108_.m_133358_());
        this.f_104888_.f_91073_.m_104746_(p_105108_.m_133361_());
    }

    @Override
    public void m_6571_(ClientboundSetDefaultSpawnPositionPacket p_105084_) {
        PacketUtils.m_131363_(p_105084_, this, this.f_104888_);
        this.f_104888_.f_91073_.m_104752_(p_105084_.m_133123_(), p_105084_.m_133126_());
        Screen screen = this.f_104888_.f_91080_;
        if (screen instanceof ReceivingLevelScreen) {
            ReceivingLevelScreen $$1 = (ReceivingLevelScreen)screen;
            $$1.m_202375_();
        }
    }

    @Override
    public void m_6403_(ClientboundSetPassengersPacket p_105102_) {
        PacketUtils.m_131363_(p_105102_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105102_.m_133286_());
        if ($$1 == null) {
            f_104883_.warn("Received passengers for unknown entity");
            return;
        }
        boolean $$2 = $$1.m_20367_(this.f_104888_.f_91074_);
        $$1.m_20153_();
        for (int $$3 : p_105102_.m_133283_()) {
            Entity $$4 = this.f_104889_.m_6815_($$3);
            if ($$4 == null) continue;
            $$4.m_7998_($$1, true);
            if ($$4 != this.f_104888_.f_91074_ || $$2) continue;
            if ($$1 instanceof Boat) {
                this.f_104888_.f_91074_.f_19859_ = $$1.m_146908_();
                this.f_104888_.f_91074_.m_146922_($$1.m_146908_());
                this.f_104888_.f_91074_.m_5616_($$1.m_146908_());
            }
            MutableComponent $$5 = Component.m_237110_("mount.onboard", this.f_104888_.f_91066_.f_92090_.m_90863_());
            this.f_104888_.f_91065_.m_93063_($$5, false);
            this.f_104888_.m_240477_().m_168785_($$5);
        }
    }

    @Override
    public void m_5599_(ClientboundSetEntityLinkPacket p_105090_) {
        PacketUtils.m_131363_(p_105090_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105090_.m_133172_());
        if ($$1 instanceof Mob) {
            ((Mob)$$1).m_21506_(p_105090_.m_133175_());
        }
    }

    private static ItemStack m_104927_(Player p_104928_) {
        for (InteractionHand $$1 : InteractionHand.values()) {
            ItemStack $$2 = p_104928_.m_21120_($$1);
            if (!$$2.m_150930_(Items.f_42747_)) continue;
            return $$2;
        }
        return new ItemStack(Items.f_42747_);
    }

    @Override
    public void m_7628_(ClientboundEntityEventPacket p_105010_) {
        PacketUtils.m_131363_(p_105010_, this, this.f_104888_);
        Entity $$1 = p_105010_.m_132094_(this.f_104889_);
        if ($$1 != null) {
            if (p_105010_.m_132102_() == 21) {
                this.f_104888_.m_91106_().m_120367_(new GuardianAttackSoundInstance((Guardian)$$1));
            } else if (p_105010_.m_132102_() == 35) {
                int $$2 = 40;
                this.f_104888_.f_91061_.m_107332_($$1, ParticleTypes.f_123767_, 30);
                this.f_104889_.m_7785_($$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), SoundEvents.f_12513_, $$1.m_5720_(), 1.0f, 1.0f, false);
                if ($$1 == this.f_104888_.f_91074_) {
                    this.f_104888_.f_91063_.m_109113_(ClientPacketListener.m_104927_(this.f_104888_.f_91074_));
                }
            } else {
                $$1.m_7822_(p_105010_.m_132102_());
            }
        }
    }

    @Override
    public void m_5547_(ClientboundSetHealthPacket p_105098_) {
        PacketUtils.m_131363_(p_105098_, this, this.f_104888_);
        this.f_104888_.f_91074_.m_108760_(p_105098_.m_133247_());
        this.f_104888_.f_91074_.m_36324_().m_38705_(p_105098_.m_133250_());
        this.f_104888_.f_91074_.m_36324_().m_38717_(p_105098_.m_133251_());
    }

    @Override
    public void m_6747_(ClientboundSetExperiencePacket p_105096_) {
        PacketUtils.m_131363_(p_105096_, this, this.f_104888_);
        this.f_104888_.f_91074_.m_108644_(p_105096_.m_133228_(), p_105096_.m_133231_(), p_105096_.m_133232_());
    }

    @Override
    public void m_7992_(ClientboundRespawnPacket p_105066_) {
        PacketUtils.m_131363_(p_105066_, this, this.f_104888_);
        ResourceKey<Level> $$1 = p_105066_.m_132955_();
        Holder<DimensionType> $$2 = this.f_104903_.m_175515_(Registry.f_122818_).m_206081_(p_105066_.m_237794_());
        LocalPlayer $$3 = this.f_104888_.f_91074_;
        int $$4 = $$3.m_19879_();
        if ($$1 != $$3.f_19853_.m_46472_()) {
            ClientLevel.ClientLevelData $$9;
            Scoreboard $$5 = this.f_104889_.m_6188_();
            Map<String, MapItemSavedData> $$6 = this.f_104889_.m_171684_();
            boolean $$7 = p_105066_.m_132959_();
            boolean $$8 = p_105066_.m_132960_();
            this.f_104890_ = $$9 = new ClientLevel.ClientLevelData(this.f_104890_.m_5472_(), this.f_104890_.m_5466_(), $$8);
            this.f_104889_ = new ClientLevel(this, $$9, $$1, $$2, this.f_104897_, this.f_194190_, this.f_104888_::m_91307_, this.f_104888_.f_91060_, $$7, p_105066_.m_132956_());
            this.f_104889_.m_104669_($$5);
            this.f_104889_.m_171672_($$6);
            this.f_104888_.m_91156_(this.f_104889_);
            this.f_104888_.m_91152_(new ReceivingLevelScreen());
        }
        String $$10 = $$3.m_108629_();
        this.f_104888_.f_91075_ = null;
        if ($$3.m_242612_()) {
            $$3.m_6915_();
        }
        LocalPlayer $$11 = this.f_104888_.f_91072_.m_105250_(this.f_104889_, $$3.m_108630_(), $$3.m_108631_(), $$3.m_6144_(), $$3.m_20142_());
        $$11.m_20234_($$4);
        this.f_104888_.f_91074_ = $$11;
        if ($$1 != $$3.f_19853_.m_46472_()) {
            this.f_104888_.m_91397_().m_120186_();
        }
        this.f_104888_.f_91075_ = $$11;
        $$11.m_20088_().m_135356_($$3.m_20088_().m_135384_());
        if (p_105066_.m_132961_()) {
            $$11.m_21204_().m_22159_($$3.m_21204_());
        }
        $$11.m_172530_();
        $$11.m_108748_($$10);
        this.f_104889_.m_104630_($$4, $$11);
        $$11.m_146922_(-180.0f);
        $$11.f_108618_ = new KeyboardInput(this.f_104888_.f_91066_);
        this.f_104888_.f_91072_.m_105221_($$11);
        $$11.m_36393_($$3.m_36330_());
        $$11.m_108711_($$3.m_108632_());
        $$11.m_219749_(p_105066_.m_237785_());
        if (this.f_104888_.f_91080_ instanceof DeathScreen) {
            this.f_104888_.m_91152_(null);
        }
        this.f_104888_.f_91072_.m_171805_(p_105066_.m_132957_(), p_105066_.m_132958_());
    }

    @Override
    public void m_7345_(ClientboundExplodePacket p_105012_) {
        PacketUtils.m_131363_(p_105012_, this, this.f_104888_);
        Explosion $$1 = new Explosion(this.f_104888_.f_91073_, null, p_105012_.m_132132_(), p_105012_.m_132133_(), p_105012_.m_132134_(), p_105012_.m_132135_(), p_105012_.m_132136_());
        $$1.m_46075_(true);
        this.f_104888_.f_91074_.m_20256_(this.f_104888_.f_91074_.m_20184_().m_82520_(p_105012_.m_132127_(), p_105012_.m_132130_(), p_105012_.m_132131_()));
    }

    @Override
    public void m_6905_(ClientboundHorseScreenOpenPacket p_105018_) {
        PacketUtils.m_131363_(p_105018_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105018_.m_132208_());
        if ($$1 instanceof AbstractHorse) {
            LocalPlayer $$2 = this.f_104888_.f_91074_;
            AbstractHorse $$3 = (AbstractHorse)$$1;
            SimpleContainer $$4 = new SimpleContainer(p_105018_.m_132207_());
            HorseInventoryMenu $$5 = new HorseInventoryMenu(p_105018_.m_132204_(), $$2.m_150109_(), $$4, $$3);
            $$2.f_36096_ = $$5;
            this.f_104888_.m_91152_(new HorseInventoryScreen($$5, $$2.m_150109_(), $$3));
        }
    }

    @Override
    public void m_5980_(ClientboundOpenScreenPacket p_105042_) {
        PacketUtils.m_131363_(p_105042_, this, this.f_104888_);
        MenuScreens.m_96201_(p_105042_.m_132628_(), this.f_104888_, p_105042_.m_132625_(), p_105042_.m_132629_());
    }

    @Override
    public void m_5735_(ClientboundContainerSetSlotPacket p_105000_) {
        PacketUtils.m_131363_(p_105000_, this, this.f_104888_);
        LocalPlayer $$1 = this.f_104888_.f_91074_;
        ItemStack $$2 = p_105000_.m_131995_();
        int $$3 = p_105000_.m_131994_();
        this.f_104888_.m_91301_().m_120568_($$2);
        if (p_105000_.m_131991_() == -1) {
            if (!(this.f_104888_.f_91080_ instanceof CreativeModeInventoryScreen)) {
                $$1.f_36096_.m_142503_($$2);
            }
        } else if (p_105000_.m_131991_() == -2) {
            $$1.m_150109_().m_6836_($$3, $$2);
        } else {
            boolean $$4 = false;
            if (this.f_104888_.f_91080_ instanceof CreativeModeInventoryScreen) {
                CreativeModeInventoryScreen $$5 = (CreativeModeInventoryScreen)this.f_104888_.f_91080_;
                boolean bl = $$4 = $$5.m_98628_() != CreativeModeTab.f_40761_.m_40775_();
            }
            if (p_105000_.m_131991_() == 0 && InventoryMenu.m_150592_($$3)) {
                ItemStack $$6;
                if (!$$2.m_41619_() && (($$6 = $$1.f_36095_.m_38853_($$3).m_7993_()).m_41619_() || $$6.m_41613_() < $$2.m_41613_())) {
                    $$2.m_41754_(5);
                }
                $$1.f_36095_.m_182406_($$3, p_105000_.m_182716_(), $$2);
            } else if (!(p_105000_.m_131991_() != $$1.f_36096_.f_38840_ || p_105000_.m_131991_() == 0 && $$4)) {
                $$1.f_36096_.m_182406_($$3, p_105000_.m_182716_(), $$2);
            }
        }
    }

    @Override
    public void m_6837_(ClientboundContainerSetContentPacket p_104996_) {
        PacketUtils.m_131363_(p_104996_, this, this.f_104888_);
        LocalPlayer $$1 = this.f_104888_.f_91074_;
        if (p_104996_.m_131954_() == 0) {
            $$1.f_36095_.m_182410_(p_104996_.m_182709_(), p_104996_.m_131957_(), p_104996_.m_182708_());
        } else if (p_104996_.m_131954_() == $$1.f_36096_.f_38840_) {
            $$1.f_36096_.m_182410_(p_104996_.m_182709_(), p_104996_.m_131957_(), p_104996_.m_182708_());
        }
    }

    @Override
    public void m_8047_(ClientboundOpenSignEditorPacket p_105044_) {
        PacketUtils.m_131363_(p_105044_, this, this.f_104888_);
        BlockPos $$1 = p_105044_.m_132640_();
        BlockEntity $$2 = this.f_104889_.m_7702_($$1);
        if (!($$2 instanceof SignBlockEntity)) {
            BlockState $$3 = this.f_104889_.m_8055_($$1);
            $$2 = new SignBlockEntity($$1, $$3);
            $$2.m_142339_(this.f_104889_);
        }
        this.f_104888_.f_91074_.m_7739_((SignBlockEntity)$$2);
    }

    @Override
    public void m_7545_(ClientboundBlockEntityDataPacket p_104976_) {
        PacketUtils.m_131363_(p_104976_, this, this.f_104888_);
        BlockPos $$1 = p_104976_.m_131704_();
        this.f_104888_.f_91073_.m_141902_($$1, p_104976_.m_195645_()).ifPresent(p_205557_ -> {
            CompoundTag $$2 = p_104976_.m_131708_();
            if ($$2 != null) {
                p_205557_.m_142466_($$2);
            }
            if (p_205557_ instanceof CommandBlockEntity && this.f_104888_.f_91080_ instanceof CommandBlockEditScreen) {
                ((CommandBlockEditScreen)this.f_104888_.f_91080_).m_98398_();
            }
        });
    }

    @Override
    public void m_7257_(ClientboundContainerSetDataPacket p_104998_) {
        PacketUtils.m_131363_(p_104998_, this, this.f_104888_);
        LocalPlayer $$1 = this.f_104888_.f_91074_;
        if ($$1.f_36096_ != null && $$1.f_36096_.f_38840_ == p_104998_.m_131972_()) {
            $$1.f_36096_.m_7511_(p_104998_.m_131975_(), p_104998_.m_131976_());
        }
    }

    @Override
    public void m_7277_(ClientboundSetEquipmentPacket p_105094_) {
        PacketUtils.m_131363_(p_105094_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105094_.m_133210_());
        if ($$1 != null) {
            p_105094_.m_133213_().forEach(p_205528_ -> $$1.m_8061_((EquipmentSlot)((Object)((Object)p_205528_.getFirst())), (ItemStack)p_205528_.getSecond()));
        }
    }

    @Override
    public void m_7776_(ClientboundContainerClosePacket p_104994_) {
        PacketUtils.m_131363_(p_104994_, this, this.f_104888_);
        this.f_104888_.f_91074_.m_108763_();
    }

    @Override
    public void m_7364_(ClientboundBlockEventPacket p_104978_) {
        PacketUtils.m_131363_(p_104978_, this, this.f_104888_);
        this.f_104888_.f_91073_.m_7696_(p_104978_.m_131725_(), p_104978_.m_131730_(), p_104978_.m_131728_(), p_104978_.m_131729_());
    }

    @Override
    public void m_5943_(ClientboundBlockDestructionPacket p_104974_) {
        PacketUtils.m_131363_(p_104974_, this, this.f_104888_);
        this.f_104888_.f_91073_.m_6801_(p_104974_.m_131685_(), p_104974_.m_131688_(), p_104974_.m_131689_());
    }

    @Override
    public void m_7616_(ClientboundGameEventPacket p_105016_) {
        PacketUtils.m_131363_(p_105016_, this, this.f_104888_);
        LocalPlayer $$1 = this.f_104888_.f_91074_;
        ClientboundGameEventPacket.Type $$2 = p_105016_.m_132178_();
        float $$3 = p_105016_.m_132181_();
        int $$4 = Mth.m_14143_($$3 + 0.5f);
        if ($$2 == ClientboundGameEventPacket.f_132153_) {
            ((Player)$$1).m_5661_(Component.m_237115_("block.minecraft.spawn.not_valid"), false);
        } else if ($$2 == ClientboundGameEventPacket.f_132154_) {
            this.f_104889_.m_6106_().m_5565_(true);
            this.f_104889_.m_46734_(0.0f);
        } else if ($$2 == ClientboundGameEventPacket.f_132155_) {
            this.f_104889_.m_6106_().m_5565_(false);
            this.f_104889_.m_46734_(1.0f);
        } else if ($$2 == ClientboundGameEventPacket.f_132156_) {
            this.f_104888_.f_91072_.m_105279_(GameType.m_46393_($$4));
        } else if ($$2 == ClientboundGameEventPacket.f_132157_) {
            if ($$4 == 0) {
                this.f_104888_.f_91074_.f_108617_.m_104955_(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
                this.f_104888_.m_91152_(new ReceivingLevelScreen());
            } else if ($$4 == 1) {
                this.f_104888_.m_91152_(new WinScreen(true, () -> this.f_104888_.f_91074_.f_108617_.m_104955_(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN))));
            }
        } else if ($$2 == ClientboundGameEventPacket.f_132158_) {
            Options $$5 = this.f_104888_.f_91066_;
            if ($$3 == 0.0f) {
                this.f_104888_.m_91152_(new DemoIntroScreen());
            } else if ($$3 == 101.0f) {
                this.f_104888_.f_91065_.m_93076_().m_93785_(Component.m_237110_("demo.help.movement", $$5.f_92085_.m_90863_(), $$5.f_92086_.m_90863_(), $$5.f_92087_.m_90863_(), $$5.f_92088_.m_90863_()));
            } else if ($$3 == 102.0f) {
                this.f_104888_.f_91065_.m_93076_().m_93785_(Component.m_237110_("demo.help.jump", $$5.f_92089_.m_90863_()));
            } else if ($$3 == 103.0f) {
                this.f_104888_.f_91065_.m_93076_().m_93785_(Component.m_237110_("demo.help.inventory", $$5.f_92092_.m_90863_()));
            } else if ($$3 == 104.0f) {
                this.f_104888_.f_91065_.m_93076_().m_93785_(Component.m_237110_("demo.day.6", $$5.f_92102_.m_90863_()));
            }
        } else if ($$2 == ClientboundGameEventPacket.f_132159_) {
            this.f_104889_.m_6263_($$1, $$1.m_20185_(), $$1.m_20188_(), $$1.m_20189_(), SoundEvents.f_11686_, SoundSource.PLAYERS, 0.18f, 0.45f);
        } else if ($$2 == ClientboundGameEventPacket.f_132160_) {
            this.f_104889_.m_46734_($$3);
        } else if ($$2 == ClientboundGameEventPacket.f_132161_) {
            this.f_104889_.m_46707_($$3);
        } else if ($$2 == ClientboundGameEventPacket.f_132162_) {
            this.f_104889_.m_6263_($$1, $$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), SoundEvents.f_12295_, SoundSource.NEUTRAL, 1.0f, 1.0f);
        } else if ($$2 == ClientboundGameEventPacket.f_132163_) {
            this.f_104889_.m_7106_(ParticleTypes.f_123807_, $$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), 0.0, 0.0, 0.0);
            if ($$4 == 1) {
                this.f_104889_.m_6263_($$1, $$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_(), SoundEvents.f_11880_, SoundSource.HOSTILE, 1.0f, 1.0f);
            }
        } else if ($$2 == ClientboundGameEventPacket.f_132164_) {
            this.f_104888_.f_91074_.m_108711_($$3 == 0.0f);
        }
    }

    @Override
    public void m_7633_(ClientboundMapItemDataPacket p_105032_) {
        PacketUtils.m_131363_(p_105032_, this, this.f_104888_);
        MapRenderer $$1 = this.f_104888_.f_91063_.m_109151_();
        int $$2 = p_105032_.m_132445_();
        String $$3 = MapItem.m_42848_($$2);
        MapItemSavedData $$4 = this.f_104888_.f_91073_.m_7489_($$3);
        if ($$4 == null) {
            $$4 = MapItemSavedData.m_164776_(p_105032_.m_178982_(), p_105032_.m_178983_(), this.f_104888_.f_91073_.m_46472_());
            this.f_104888_.f_91073_.m_142325_($$3, $$4);
        }
        p_105032_.m_132437_($$4);
        $$1.m_168765_($$2, $$4);
    }

    @Override
    public void m_7704_(ClientboundLevelEventPacket p_105024_) {
        PacketUtils.m_131363_(p_105024_, this, this.f_104888_);
        if (p_105024_.m_132274_()) {
            this.f_104888_.f_91073_.m_6798_(p_105024_.m_132277_(), p_105024_.m_132279_(), p_105024_.m_132278_());
        } else {
            this.f_104888_.f_91073_.m_46796_(p_105024_.m_132277_(), p_105024_.m_132279_(), p_105024_.m_132278_());
        }
    }

    @Override
    public void m_5498_(ClientboundUpdateAdvancementsPacket p_105126_) {
        PacketUtils.m_131363_(p_105126_, this, this.f_104888_);
        this.f_104893_.m_104399_(p_105126_);
    }

    @Override
    public void m_7553_(ClientboundSelectAdvancementsTabPacket p_105072_) {
        PacketUtils.m_131363_(p_105072_, this, this.f_104888_);
        ResourceLocation $$1 = p_105072_.m_133013_();
        if ($$1 == null) {
            this.f_104893_.m_104401_(null, false);
        } else {
            Advancement $$2 = this.f_104893_.m_104396_().m_139337_($$1);
            this.f_104893_.m_104401_($$2, false);
        }
    }

    @Override
    public void m_7443_(ClientboundCommandsPacket p_104990_) {
        PacketUtils.m_131363_(p_104990_, this, this.f_104888_);
        this.f_104899_ = new CommandDispatcher(p_104990_.m_237624_(new CommandBuildContext(this.f_104903_)));
    }

    @Override
    public void m_7183_(ClientboundStopSoundPacket p_105116_) {
        PacketUtils.m_131363_(p_105116_, this, this.f_104888_);
        this.f_104888_.m_91106_().m_120386_(p_105116_.m_133476_(), p_105116_.m_133479_());
    }

    @Override
    public void m_7589_(ClientboundCommandSuggestionsPacket p_104988_) {
        PacketUtils.m_131363_(p_104988_, this, this.f_104888_);
        this.f_104894_.m_105171_(p_104988_.m_131854_(), p_104988_.m_131857_());
    }

    @Override
    public void m_6327_(ClientboundUpdateRecipesPacket p_105132_) {
        PacketUtils.m_131363_(p_105132_, this, this.f_104888_);
        this.f_104900_.m_44024_(p_105132_.m_133644_());
        ClientRecipeBook $$1 = this.f_104888_.f_91074_.m_108631_();
        $$1.m_90625_(this.f_104900_.m_44051_());
        this.f_104888_.m_231374_(SearchRegistry.f_119943_, $$1.m_90639_());
    }

    @Override
    public void m_7244_(ClientboundPlayerLookAtPacket p_105054_) {
        PacketUtils.m_131363_(p_105054_, this, this.f_104888_);
        Vec3 $$1 = p_105054_.m_132785_(this.f_104889_);
        if ($$1 != null) {
            this.f_104888_.f_91074_.m_7618_(p_105054_.m_132793_(), $$1);
        }
    }

    @Override
    public void m_6148_(ClientboundTagQueryPacket p_105120_) {
        PacketUtils.m_131363_(p_105120_, this, this.f_104888_);
        if (!this.f_104896_.m_90705_(p_105120_.m_133506_(), p_105120_.m_133509_())) {
            f_104883_.debug("Got unhandled response to tag query {}", (Object)p_105120_.m_133506_());
        }
    }

    @Override
    public void m_7271_(ClientboundAwardStatsPacket p_104970_) {
        PacketUtils.m_131363_(p_104970_, this, this.f_104888_);
        for (Map.Entry<Stat<?>, Integer> $$1 : p_104970_.m_131643_().entrySet()) {
            Stat<?> $$2 = $$1.getKey();
            int $$3 = $$1.getValue();
            this.f_104888_.f_91074_.m_108630_().m_6085_(this.f_104888_.f_91074_, $$2, $$3);
        }
        if (this.f_104888_.f_91080_ instanceof StatsUpdateListener) {
            ((StatsUpdateListener)((Object)this.f_104888_.f_91080_)).m_7819_();
        }
    }

    @Override
    public void m_8076_(ClientboundRecipePacket p_105058_) {
        PacketUtils.m_131363_(p_105058_, this, this.f_104888_);
        ClientRecipeBook $$1 = this.f_104888_.f_91074_.m_108631_();
        $$1.m_12687_(p_105058_.m_132869_());
        ClientboundRecipePacket.State $$2 = p_105058_.m_132870_();
        switch ($$2) {
            case REMOVE: {
                for (ResourceLocation $$3 : p_105058_.m_132865_()) {
                    this.f_104900_.m_44043_($$3).ifPresent($$1::m_12713_);
                }
                break;
            }
            case INIT: {
                for (ResourceLocation $$4 : p_105058_.m_132865_()) {
                    this.f_104900_.m_44043_($$4).ifPresent($$1::m_12700_);
                }
                for (ResourceLocation $$5 : p_105058_.m_132868_()) {
                    this.f_104900_.m_44043_($$5).ifPresent($$1::m_12723_);
                }
                break;
            }
            case ADD: {
                for (ResourceLocation $$6 : p_105058_.m_132865_()) {
                    this.f_104900_.m_44043_($$6).ifPresent(p_205537_ -> {
                        $$1.m_12700_((Recipe<?>)p_205537_);
                        $$1.m_12723_((Recipe<?>)p_205537_);
                        RecipeToast.m_94817_(this.f_104888_.m_91300_(), p_205537_);
                    });
                }
                break;
            }
        }
        $$1.m_90639_().forEach(p_205540_ -> p_205540_.m_100499_($$1));
        if (this.f_104888_.f_91080_ instanceof RecipeUpdateListener) {
            ((RecipeUpdateListener)((Object)this.f_104888_.f_91080_)).m_6916_();
        }
    }

    @Override
    public void m_7915_(ClientboundUpdateMobEffectPacket p_105130_) {
        PacketUtils.m_131363_(p_105130_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105130_.m_133622_());
        if (!($$1 instanceof LivingEntity)) {
            return;
        }
        MobEffect $$2 = p_105130_.m_237878_();
        if ($$2 == null) {
            return;
        }
        MobEffectInstance $$3 = new MobEffectInstance($$2, p_105130_.m_133625_(), p_105130_.m_133624_(), p_105130_.m_133627_(), p_105130_.m_133626_(), p_105130_.m_133628_(), null, Optional.ofNullable(p_105130_.m_237879_()));
        $$3.m_19562_(p_105130_.m_133619_());
        ((LivingEntity)$$1).m_147215_($$3, null);
    }

    @Override
    public void m_5859_(ClientboundUpdateTagsPacket p_105134_) {
        PacketUtils.m_131363_(p_105134_, this, this.f_104888_);
        p_105134_.m_179482_().forEach(this::m_205560_);
        if (!this.f_104885_.m_129531_()) {
            Blocks.m_50758_();
        }
        NonNullList<ItemStack> $$1 = NonNullList.m_122779_();
        for (Item $$2 : Registry.f_122827_) {
            $$2.m_6787_(CreativeModeTab.f_40754_, $$1);
        }
        this.f_104888_.m_231374_(SearchRegistry.f_119941_, $$1);
        this.f_104888_.m_231374_(SearchRegistry.f_119942_, $$1);
    }

    private <T> void m_205560_(ResourceKey<? extends Registry<? extends T>> p_205561_, TagNetworkSerialization.NetworkPayload p_205562_) {
        if (p_205562_.m_203966_()) {
            return;
        }
        Registry $$2 = this.f_104903_.m_6632_(p_205561_).orElseThrow(() -> new IllegalStateException("Unknown registry " + p_205561_));
        ResourceKey $$3 = p_205561_;
        HashMap $$4 = new HashMap();
        TagNetworkSerialization.m_203952_($$3, $$2, p_205562_, $$4::put);
        $$2.m_203652_($$4);
    }

    @Override
    public void m_142234_(ClientboundPlayerCombatEndPacket p_171771_) {
    }

    @Override
    public void m_142058_(ClientboundPlayerCombatEnterPacket p_171773_) {
    }

    @Override
    public void m_142747_(ClientboundPlayerCombatKillPacket p_171775_) {
        PacketUtils.m_131363_(p_171775_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_171775_.m_179078_());
        if ($$1 == this.f_104888_.f_91074_) {
            if (this.f_104888_.f_91074_.m_108632_()) {
                this.f_104888_.m_91152_(new DeathScreen(p_171775_.m_179079_(), this.f_104889_.m_6106_().m_5466_()));
            } else {
                this.f_104888_.f_91074_.m_7583_();
            }
        }
    }

    @Override
    public void m_6664_(ClientboundChangeDifficultyPacket p_104984_) {
        PacketUtils.m_131363_(p_104984_, this, this.f_104888_);
        this.f_104890_.m_104851_(p_104984_.m_131820_());
        this.f_104890_.m_104858_(p_104984_.m_131817_());
    }

    @Override
    public void m_6447_(ClientboundSetCameraPacket p_105076_) {
        PacketUtils.m_131363_(p_105076_, this, this.f_104888_);
        Entity $$1 = p_105076_.m_133059_(this.f_104889_);
        if ($$1 != null) {
            this.f_104888_.m_91118_($$1);
        }
    }

    @Override
    public void m_142237_(ClientboundInitializeBorderPacket p_171767_) {
        PacketUtils.m_131363_(p_171767_, this, this.f_104888_);
        WorldBorder $$1 = this.f_104889_.m_6857_();
        $$1.m_61949_(p_171767_.m_178886_(), p_171767_.m_178887_());
        long $$2 = p_171767_.m_178890_();
        if ($$2 > 0L) {
            $$1.m_61919_(p_171767_.m_178889_(), p_171767_.m_178888_(), $$2);
        } else {
            $$1.m_61917_(p_171767_.m_178888_());
        }
        $$1.m_61923_(p_171767_.m_178891_());
        $$1.m_61952_(p_171767_.m_178893_());
        $$1.m_61944_(p_171767_.m_178892_());
    }

    @Override
    public void m_142612_(ClientboundSetBorderCenterPacket p_171781_) {
        PacketUtils.m_131363_(p_171781_, this, this.f_104888_);
        this.f_104889_.m_6857_().m_61949_(p_171781_.m_179224_(), p_171781_.m_179223_());
    }

    @Override
    public void m_142686_(ClientboundSetBorderLerpSizePacket p_171783_) {
        PacketUtils.m_131363_(p_171783_, this, this.f_104888_);
        this.f_104889_.m_6857_().m_61919_(p_171783_.m_179238_(), p_171783_.m_179239_(), p_171783_.m_179240_());
    }

    @Override
    public void m_142238_(ClientboundSetBorderSizePacket p_171785_) {
        PacketUtils.m_131363_(p_171785_, this, this.f_104888_);
        this.f_104889_.m_6857_().m_61917_(p_171785_.m_179252_());
    }

    @Override
    public void m_142696_(ClientboundSetBorderWarningDistancePacket p_171789_) {
        PacketUtils.m_131363_(p_171789_, this, this.f_104888_);
        this.f_104889_.m_6857_().m_61952_(p_171789_.m_179276_());
    }

    @Override
    public void m_142056_(ClientboundSetBorderWarningDelayPacket p_171787_) {
        PacketUtils.m_131363_(p_171787_, this, this.f_104888_);
        this.f_104889_.m_6857_().m_61944_(p_171787_.m_179264_());
    }

    @Override
    public void m_142766_(ClientboundClearTitlesPacket p_171765_) {
        PacketUtils.m_131363_(p_171765_, this, this.f_104888_);
        this.f_104888_.f_91065_.m_168713_();
        if (p_171765_.m_178788_()) {
            this.f_104888_.f_91065_.m_93006_();
        }
    }

    @Override
    public void m_213672_(ClientboundServerDataPacket p_233704_) {
        ServerData.ChatPreview $$3;
        PacketUtils.m_131363_(p_233704_, this, this.f_104888_);
        ServerData $$1 = this.f_104888_.m_91089_();
        if ($$1 == null) {
            return;
        }
        p_233704_.m_237810_().ifPresent(p_233678_ -> {
            p_233677_.f_105365_ = p_233678_;
        });
        p_233704_.m_237811_().ifPresent(p_233675_ -> {
            try {
                $$1.m_105383_(ServerData.m_233808_(p_233675_));
            }
            catch (ParseException $$2) {
                f_104883_.error("Invalid server icon", (Throwable)$$2);
            }
        });
        $$1.m_233813_(p_233704_.m_237812_());
        $$1.m_242965_(p_233704_.m_242957_());
        ServerList.m_105446_($$1);
        if (!p_233704_.m_242957_()) {
            SystemToast $$2 = SystemToast.m_94847_(this.f_104888_, SystemToast.SystemToastIds.UNSECURE_SERVER_WARNING, f_242953_, f_242949_);
            this.f_104888_.m_91300_().m_94922_($$2);
        }
        if (($$3 = $$1.m_233817_()) != null && !$$3.m_233834_()) {
            this.f_104888_.execute(() -> this.f_104888_.m_91152_(new ChatPreviewWarningScreen(this.f_104888_.f_91080_, $$1)));
        }
    }

    @Override
    public void m_240695_(ClientboundCustomChatCompletionsPacket p_240832_) {
        PacketUtils.m_131363_(p_240832_, this, this.f_104888_);
        this.f_104894_.m_240713_(p_240832_.f_240661_(), p_240832_.f_240663_());
    }

    @Override
    public void m_142456_(ClientboundSetActionBarTextPacket p_171779_) {
        PacketUtils.m_131363_(p_171779_, this, this.f_104888_);
        this.f_104888_.f_91065_.m_93063_(p_171779_.m_179210_(), false);
    }

    @Override
    public void m_142442_(ClientboundSetTitleTextPacket p_171793_) {
        PacketUtils.m_131363_(p_171793_, this, this.f_104888_);
        this.f_104888_.f_91065_.m_168714_(p_171793_.m_179399_());
    }

    @Override
    public void m_141913_(ClientboundSetSubtitleTextPacket p_171791_) {
        PacketUtils.m_131363_(p_171791_, this, this.f_104888_);
        this.f_104888_.f_91065_.m_168711_(p_171791_.m_179385_());
    }

    @Override
    public void m_142185_(ClientboundSetTitlesAnimationPacket p_171795_) {
        PacketUtils.m_131363_(p_171795_, this, this.f_104888_);
        this.f_104888_.f_91065_.m_168684_(p_171795_.m_179415_(), p_171795_.m_179416_(), p_171795_.m_179417_());
    }

    @Override
    public void m_6235_(ClientboundTabListPacket p_105118_) {
        PacketUtils.m_131363_(p_105118_, this, this.f_104888_);
        this.f_104888_.f_91065_.m_93088_().m_94558_(p_105118_.m_133489_().getString().isEmpty() ? null : p_105118_.m_133489_());
        this.f_104888_.f_91065_.m_93088_().m_94554_(p_105118_.m_133492_().getString().isEmpty() ? null : p_105118_.m_133492_());
    }

    @Override
    public void m_6476_(ClientboundRemoveMobEffectPacket p_105062_) {
        PacketUtils.m_131363_(p_105062_, this, this.f_104888_);
        Entity $$1 = p_105062_.m_132901_(this.f_104889_);
        if ($$1 instanceof LivingEntity) {
            ((LivingEntity)$$1).m_6234_(p_105062_.m_132909_());
        }
    }

    @Override
    public void m_7039_(ClientboundPlayerInfoPacket p_105052_) {
        PacketUtils.m_131363_(p_105052_, this, this.f_104888_);
        for (ClientboundPlayerInfoPacket.PlayerUpdate $$1 : p_105052_.m_132732_()) {
            if (p_105052_.m_132735_() == ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER) {
                this.f_104888_.m_91266_().m_100690_($$1.m_132763_().getId());
                this.f_104892_.remove($$1.m_132763_().getId());
                continue;
            }
            PlayerInfo $$2 = this.f_104892_.get($$1.m_132763_().getId());
            if (p_105052_.m_132735_() == ClientboundPlayerInfoPacket.Action.ADD_PLAYER && $$2 == null) {
                boolean $$3 = Util.m_214617_(this.f_104888_.m_91089_(), ServerData::m_242962_, false);
                $$2 = new PlayerInfo($$1, this.f_104888_.m_231417_(), $$3);
                this.f_104892_.put($$2.m_105312_().getId(), $$2);
                this.f_104888_.m_91266_().m_100676_($$2);
            }
            if ($$2 == null) continue;
            switch (p_105052_.m_132735_()) {
                case ADD_PLAYER: {
                    $$2.m_105317_($$1.m_132765_());
                    $$2.m_105313_($$1.m_132764_());
                    $$2.m_105323_($$1.m_132766_());
                    break;
                }
                case UPDATE_GAME_MODE: {
                    $$2.m_105317_($$1.m_132765_());
                    break;
                }
                case UPDATE_LATENCY: {
                    $$2.m_105313_($$1.m_132764_());
                    break;
                }
                case UPDATE_DISPLAY_NAME: {
                    $$2.m_105323_($$1.m_132766_());
                }
            }
        }
    }

    @Override
    public void m_7231_(ClientboundKeepAlivePacket p_105020_) {
        this.m_104955_(new ServerboundKeepAlivePacket(p_105020_.m_132219_()));
    }

    @Override
    public void m_5767_(ClientboundPlayerAbilitiesPacket p_105048_) {
        PacketUtils.m_131363_(p_105048_, this, this.f_104888_);
        LocalPlayer $$1 = this.f_104888_.f_91074_;
        $$1.m_150110_().f_35935_ = p_105048_.m_132677_();
        $$1.m_150110_().f_35937_ = p_105048_.m_132679_();
        $$1.m_150110_().f_35934_ = p_105048_.m_132674_();
        $$1.m_150110_().f_35936_ = p_105048_.m_132678_();
        $$1.m_150110_().m_35943_(p_105048_.m_132680_());
        $$1.m_150110_().m_35948_(p_105048_.m_132681_());
    }

    @Override
    public void m_8068_(ClientboundSoundPacket p_105114_) {
        PacketUtils.m_131363_(p_105114_, this, this.f_104888_);
        this.f_104888_.f_91073_.m_214150_(this.f_104888_.f_91074_, p_105114_.m_133459_(), p_105114_.m_133460_(), p_105114_.m_133461_(), p_105114_.m_133455_(), p_105114_.m_133458_(), p_105114_.m_133462_(), p_105114_.m_133463_(), p_105114_.m_237848_());
    }

    @Override
    public void m_5863_(ClientboundSoundEntityPacket p_105112_) {
        PacketUtils.m_131363_(p_105112_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105112_.m_133430_());
        if ($$1 == null) {
            return;
        }
        this.f_104888_.f_91073_.m_213890_(this.f_104888_.f_91074_, $$1, p_105112_.m_133426_(), p_105112_.m_133429_(), p_105112_.m_133431_(), p_105112_.m_133432_(), p_105112_.m_237837_());
    }

    @Override
    public void m_6490_(ClientboundCustomSoundPacket p_105006_) {
        PacketUtils.m_131363_(p_105006_, this, this.f_104888_);
        this.f_104888_.m_91106_().m_120367_(new SimpleSoundInstance(p_105006_.m_132066_(), p_105006_.m_132069_(), p_105006_.m_132073_(), p_105006_.m_132074_(), RandomSource.m_216335_(p_105006_.m_237705_()), false, 0, SoundInstance.Attenuation.LINEAR, p_105006_.m_132070_(), p_105006_.m_132071_(), p_105006_.m_132072_(), false));
    }

    @Override
    public void m_5587_(ClientboundResourcePackPacket p_105064_) {
        URL $$1 = ClientPacketListener.m_233709_(p_105064_.m_132924_());
        if ($$1 == null) {
            this.m_105135_(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
            return;
        }
        String $$2 = p_105064_.m_132927_();
        boolean $$3 = p_105064_.m_179188_();
        ServerData $$4 = this.f_104888_.m_91089_();
        if ($$4 != null && $$4.m_105387_() == ServerData.ServerPackStatus.ENABLED) {
            this.m_105135_(ServerboundResourcePackPacket.Action.ACCEPTED);
            this.m_104951_(this.f_104888_.m_91100_().m_235005_($$1, $$2, true));
        } else if ($$4 == null || $$4.m_105387_() == ServerData.ServerPackStatus.PROMPT || $$3 && $$4.m_105387_() == ServerData.ServerPackStatus.DISABLED) {
            this.f_104888_.execute(() -> this.f_104888_.m_91152_(new ConfirmScreen(p_233690_ -> {
                this.f_104888_.m_91152_(null);
                ServerData $$4 = this.f_104888_.m_91089_();
                if (p_233690_) {
                    if ($$4 != null) {
                        $$4.m_105379_(ServerData.ServerPackStatus.ENABLED);
                    }
                    this.m_105135_(ServerboundResourcePackPacket.Action.ACCEPTED);
                    this.m_104951_(this.f_104888_.m_91100_().m_235005_($$1, $$2, true));
                } else {
                    this.m_105135_(ServerboundResourcePackPacket.Action.DECLINED);
                    if ($$3) {
                        this.f_104885_.m_129507_(Component.m_237115_("multiplayer.requiredTexturePrompt.disconnect"));
                    } else if ($$4 != null) {
                        $$4.m_105379_(ServerData.ServerPackStatus.DISABLED);
                    }
                }
                if ($$4 != null) {
                    ServerList.m_105446_($$4);
                }
            }, $$3 ? Component.m_237115_("multiplayer.requiredTexturePrompt.line1") : Component.m_237115_("multiplayer.texturePrompt.line1"), ClientPacketListener.m_171759_($$3 ? Component.m_237115_("multiplayer.requiredTexturePrompt.line2").m_130944_(ChatFormatting.YELLOW, ChatFormatting.BOLD) : Component.m_237115_("multiplayer.texturePrompt.line2"), p_105064_.m_179189_()), $$3 ? CommonComponents.f_130659_ : CommonComponents.f_130657_, $$3 ? Component.m_237115_("menu.disconnect") : CommonComponents.f_130658_)));
        } else {
            this.m_105135_(ServerboundResourcePackPacket.Action.DECLINED);
            if ($$3) {
                this.f_104885_.m_129507_(Component.m_237115_("multiplayer.requiredTexturePrompt.disconnect"));
            }
        }
    }

    private static Component m_171759_(Component p_171760_, @Nullable Component p_171761_) {
        if (p_171761_ == null) {
            return p_171760_;
        }
        return Component.m_237110_("multiplayer.texturePrompt.serverPrompt", p_171760_, p_171761_);
    }

    @Nullable
    private static URL m_233709_(String p_233710_) {
        try {
            URL $$1 = new URL(p_233710_);
            String $$2 = $$1.getProtocol();
            if ("http".equals($$2) || "https".equals($$2)) {
                return $$1;
            }
        }
        catch (MalformedURLException $$3) {
            return null;
        }
        return null;
    }

    private void m_104951_(CompletableFuture<?> p_104952_) {
        ((CompletableFuture)p_104952_.thenRun(() -> this.m_105135_(ServerboundResourcePackPacket.Action.SUCCESSFULLY_LOADED))).exceptionally(p_233680_ -> {
            this.m_105135_(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
            return null;
        });
    }

    private void m_105135_(ServerboundResourcePackPacket.Action p_105136_) {
        this.f_104885_.m_129512_(new ServerboundResourcePackPacket(p_105136_));
    }

    @Override
    public void m_7685_(ClientboundBossEventPacket p_104982_) {
        PacketUtils.m_131363_(p_104982_, this, this.f_104888_);
        this.f_104888_.f_91065_.m_93090_().m_93711_(p_104982_);
    }

    @Override
    public void m_7701_(ClientboundCooldownPacket p_105002_) {
        PacketUtils.m_131363_(p_105002_, this, this.f_104888_);
        if (p_105002_.m_132011_() == 0) {
            this.f_104888_.f_91074_.m_36335_().m_41527_(p_105002_.m_132008_());
        } else {
            this.f_104888_.f_91074_.m_36335_().m_41524_(p_105002_.m_132008_(), p_105002_.m_132011_());
        }
    }

    @Override
    public void m_7410_(ClientboundMoveVehiclePacket p_105038_) {
        PacketUtils.m_131363_(p_105038_, this, this.f_104888_);
        Entity $$1 = this.f_104888_.f_91074_.m_20201_();
        if ($$1 != this.f_104888_.f_91074_ && $$1.m_6109_()) {
            $$1.m_19890_(p_105038_.m_132591_(), p_105038_.m_132594_(), p_105038_.m_132595_(), p_105038_.m_132596_(), p_105038_.m_132597_());
            this.f_104885_.m_129512_(new ServerboundMoveVehiclePacket($$1));
        }
    }

    @Override
    public void m_6503_(ClientboundOpenBookPacket p_105040_) {
        PacketUtils.m_131363_(p_105040_, this, this.f_104888_);
        ItemStack $$1 = this.f_104888_.f_91074_.m_21120_(p_105040_.m_132608_());
        if ($$1.m_150930_(Items.f_42615_)) {
            this.f_104888_.m_91152_(new BookViewScreen(new BookViewScreen.WrittenBookAccess($$1)));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void m_7413_(ClientboundCustomPayloadPacket p_105004_) {
        PacketUtils.m_131363_(p_105004_, this, this.f_104888_);
        ResourceLocation $$1 = p_105004_.m_132042_();
        FriendlyByteBuf $$2 = null;
        try {
            $$2 = p_105004_.m_132045_();
            if (ClientboundCustomPayloadPacket.f_132012_.equals($$1)) {
                String $$3 = $$2.m_130277_();
                this.f_104888_.f_91074_.m_108748_($$3);
                this.f_194191_.m_193561_($$3);
            } else if (ClientboundCustomPayloadPacket.f_132013_.equals($$1)) {
                int $$4 = $$2.readInt();
                float $$5 = $$2.readFloat();
                Path $$6 = Path.m_77390_($$2);
                this.f_104888_.f_91064_.f_113413_.m_113611_($$4, $$6, $$5);
            } else if (ClientboundCustomPayloadPacket.f_132014_.equals($$1)) {
                long $$7 = $$2.m_130258_();
                BlockPos $$8 = $$2.m_130135_();
                ((NeighborsUpdateRenderer)this.f_104888_.f_91064_.f_113418_).m_113596_($$7, $$8);
            } else if (ClientboundCustomPayloadPacket.f_132016_.equals($$1)) {
                DimensionType $$9 = this.f_104903_.m_175515_(Registry.f_122818_).m_7745_($$2.m_130281_());
                BoundingBox $$10 = new BoundingBox($$2.readInt(), $$2.readInt(), $$2.readInt(), $$2.readInt(), $$2.readInt(), $$2.readInt());
                int $$11 = $$2.readInt();
                ArrayList $$12 = Lists.newArrayList();
                ArrayList $$13 = Lists.newArrayList();
                for (int $$14 = 0; $$14 < $$11; ++$$14) {
                    $$12.add(new BoundingBox($$2.readInt(), $$2.readInt(), $$2.readInt(), $$2.readInt(), $$2.readInt(), $$2.readInt()));
                    $$13.add($$2.readBoolean());
                }
                this.f_104888_.f_91064_.f_113420_.m_113682_($$10, $$12, $$13, $$9);
            } else if (ClientboundCustomPayloadPacket.f_132017_.equals($$1)) {
                ((WorldGenAttemptRenderer)this.f_104888_.f_91064_.f_113422_).m_113737_($$2.m_130135_(), $$2.readFloat(), $$2.readFloat(), $$2.readFloat(), $$2.readFloat(), $$2.readFloat());
            } else if (ClientboundCustomPayloadPacket.f_132021_.equals($$1)) {
                int $$15 = $$2.readInt();
                for (int $$16 = 0; $$16 < $$15; ++$$16) {
                    this.f_104888_.f_91064_.f_113426_.m_113709_($$2.m_130157_());
                }
                int $$17 = $$2.readInt();
                for (int $$18 = 0; $$18 < $$17; ++$$18) {
                    this.f_104888_.f_91064_.f_113426_.m_113711_($$2.m_130157_());
                }
            } else if (ClientboundCustomPayloadPacket.f_132019_.equals($$1)) {
                BlockPos $$19 = $$2.m_130135_();
                String $$20 = $$2.m_130277_();
                int $$21 = $$2.readInt();
                BrainDebugRenderer.PoiInfo $$22 = new BrainDebugRenderer.PoiInfo($$19, $$20, $$21);
                this.f_104888_.f_91064_.f_113425_.m_113226_($$22);
            } else if (ClientboundCustomPayloadPacket.f_132020_.equals($$1)) {
                BlockPos $$23 = $$2.m_130135_();
                this.f_104888_.f_91064_.f_113425_.m_113228_($$23);
            } else if (ClientboundCustomPayloadPacket.f_132018_.equals($$1)) {
                BlockPos $$24 = $$2.m_130135_();
                int $$25 = $$2.readInt();
                this.f_104888_.f_91064_.f_113425_.m_113230_($$24, $$25);
            } else if (ClientboundCustomPayloadPacket.f_132022_.equals($$1)) {
                BlockPos $$26 = $$2.m_130135_();
                int $$27 = $$2.readInt();
                int $$28 = $$2.readInt();
                ArrayList $$29 = Lists.newArrayList();
                for (int $$30 = 0; $$30 < $$28; ++$$30) {
                    int $$31 = $$2.readInt();
                    boolean $$32 = $$2.readBoolean();
                    String $$33 = $$2.m_130136_(255);
                    $$29.add(new GoalSelectorDebugRenderer.DebugGoal($$26, $$31, $$33, $$32));
                }
                this.f_104888_.f_91064_.f_113429_.m_113548_($$27, $$29);
            } else if (ClientboundCustomPayloadPacket.f_132028_.equals($$1)) {
                int $$34 = $$2.readInt();
                ArrayList $$35 = Lists.newArrayList();
                for (int $$36 = 0; $$36 < $$34; ++$$36) {
                    $$35.add($$2.m_130135_());
                }
                this.f_104888_.f_91064_.f_113428_.m_113663_($$35);
            } else if (ClientboundCustomPayloadPacket.f_132023_.equals($$1)) {
                double $$37 = $$2.readDouble();
                double $$38 = $$2.readDouble();
                double $$39 = $$2.readDouble();
                PositionImpl $$40 = new PositionImpl($$37, $$38, $$39);
                UUID $$41 = $$2.m_130259_();
                int $$42 = $$2.readInt();
                String $$43 = $$2.m_130277_();
                String $$44 = $$2.m_130277_();
                int $$45 = $$2.readInt();
                float $$46 = $$2.readFloat();
                float $$47 = $$2.readFloat();
                String $$48 = $$2.m_130277_();
                Path $$49 = (Path)$$2.m_236868_(Path::m_77390_);
                boolean $$50 = $$2.readBoolean();
                int $$51 = $$2.readInt();
                BrainDebugRenderer.BrainDump $$52 = new BrainDebugRenderer.BrainDump($$41, $$42, $$43, $$44, $$45, $$46, $$47, $$40, $$48, $$49, $$50, $$51);
                int $$53 = $$2.m_130242_();
                for (int $$54 = 0; $$54 < $$53; ++$$54) {
                    String $$55 = $$2.m_130277_();
                    $$52.f_113304_.add($$55);
                }
                int $$56 = $$2.m_130242_();
                for (int $$57 = 0; $$57 < $$56; ++$$57) {
                    String $$58 = $$2.m_130277_();
                    $$52.f_113305_.add($$58);
                }
                int $$59 = $$2.m_130242_();
                for (int $$60 = 0; $$60 < $$59; ++$$60) {
                    String $$61 = $$2.m_130277_();
                    $$52.f_113306_.add($$61);
                }
                int $$62 = $$2.m_130242_();
                for (int $$63 = 0; $$63 < $$62; ++$$63) {
                    BlockPos $$64 = $$2.m_130135_();
                    $$52.f_113308_.add($$64);
                }
                int $$65 = $$2.m_130242_();
                for (int $$66 = 0; $$66 < $$65; ++$$66) {
                    BlockPos $$67 = $$2.m_130135_();
                    $$52.f_113309_.add($$67);
                }
                int $$68 = $$2.m_130242_();
                for (int $$69 = 0; $$69 < $$68; ++$$69) {
                    String $$70 = $$2.m_130277_();
                    $$52.f_113307_.add($$70);
                }
                this.f_104888_.f_91064_.f_113425_.m_113219_($$52);
            } else if (ClientboundCustomPayloadPacket.f_132024_.equals($$1)) {
                double $$71 = $$2.readDouble();
                double $$72 = $$2.readDouble();
                double $$73 = $$2.readDouble();
                PositionImpl $$74 = new PositionImpl($$71, $$72, $$73);
                UUID $$75 = $$2.m_130259_();
                int $$76 = $$2.readInt();
                BlockPos $$77 = (BlockPos)$$2.m_236868_(FriendlyByteBuf::m_130135_);
                BlockPos $$78 = (BlockPos)$$2.m_236868_(FriendlyByteBuf::m_130135_);
                int $$79 = $$2.readInt();
                Path $$80 = (Path)$$2.m_236868_(Path::m_77390_);
                BeeDebugRenderer.BeeInfo $$81 = new BeeDebugRenderer.BeeInfo($$75, $$76, $$74, $$80, $$77, $$78, $$79);
                int $$82 = $$2.m_130242_();
                for (int $$83 = 0; $$83 < $$82; ++$$83) {
                    String $$84 = $$2.m_130277_();
                    $$81.f_113164_.add($$84);
                }
                int $$85 = $$2.m_130242_();
                for (int $$86 = 0; $$86 < $$85; ++$$86) {
                    BlockPos $$87 = $$2.m_130135_();
                    $$81.f_113165_.add($$87);
                }
                this.f_104888_.f_91064_.f_113427_.m_113066_($$81);
            } else if (ClientboundCustomPayloadPacket.f_132025_.equals($$1)) {
                BlockPos $$88 = $$2.m_130135_();
                String $$89 = $$2.m_130277_();
                int $$90 = $$2.readInt();
                int $$91 = $$2.readInt();
                boolean $$92 = $$2.readBoolean();
                BeeDebugRenderer.HiveInfo $$93 = new BeeDebugRenderer.HiveInfo($$88, $$89, $$90, $$91, $$92, this.f_104889_.m_46467_());
                this.f_104888_.f_91064_.f_113427_.m_113071_($$93);
            } else if (ClientboundCustomPayloadPacket.f_132027_.equals($$1)) {
                this.f_104888_.f_91064_.f_113430_.m_5630_();
            } else if (ClientboundCustomPayloadPacket.f_132026_.equals($$1)) {
                BlockPos $$94 = $$2.m_130135_();
                int $$95 = $$2.readInt();
                String $$96 = $$2.m_130277_();
                int $$97 = $$2.readInt();
                this.f_104888_.f_91064_.f_113430_.m_113524_($$94, $$95, $$96, $$97);
            } else if (ClientboundCustomPayloadPacket.f_178832_.equals($$1)) {
                GameEvent $$98 = Registry.f_175412_.m_7745_(new ResourceLocation($$2.m_130277_()));
                Vec3 $$99 = new Vec3($$2.readDouble(), $$2.readDouble(), $$2.readDouble());
                this.f_104888_.f_91064_.f_173815_.m_234513_($$98, $$99);
            } else if (ClientboundCustomPayloadPacket.f_178833_.equals($$1)) {
                ResourceLocation $$100 = $$2.m_130281_();
                Object $$101 = Registry.f_175420_.m_6612_($$100).orElseThrow(() -> new IllegalArgumentException("Unknown position source type " + $$100)).m_142281_($$2);
                int $$102 = $$2.m_130242_();
                this.f_104888_.f_91064_.f_173815_.m_173830_((PositionSource)$$101, $$102);
            } else {
                f_104883_.warn("Unknown custom packed identifier: {}", (Object)$$1);
            }
        }
        finally {
            if ($$2 != null) {
                $$2.release();
            }
        }
    }

    @Override
    public void m_7957_(ClientboundSetObjectivePacket p_105100_) {
        PacketUtils.m_131363_(p_105100_, this, this.f_104888_);
        Scoreboard $$1 = this.f_104889_.m_6188_();
        String $$2 = p_105100_.m_133266_();
        if (p_105100_.m_133270_() == 0) {
            $$1.m_83436_($$2, ObjectiveCriteria.f_83588_, p_105100_.m_133269_(), p_105100_.m_133271_());
        } else if ($$1.m_83459_($$2)) {
            Objective $$3 = $$1.m_83477_($$2);
            if (p_105100_.m_133270_() == 1) {
                $$1.m_83502_($$3);
            } else if (p_105100_.m_133270_() == 2) {
                $$3.m_83314_(p_105100_.m_133271_());
                $$3.m_83316_(p_105100_.m_133269_());
            }
        }
    }

    @Override
    public void m_7519_(ClientboundSetScorePacket p_105106_) {
        PacketUtils.m_131363_(p_105106_, this, this.f_104888_);
        Scoreboard $$1 = this.f_104889_.m_6188_();
        String $$2 = p_105106_.m_133342_();
        switch (p_105106_.m_133344_()) {
            case CHANGE: {
                Objective $$3 = $$1.m_83469_($$2);
                Score $$4 = $$1.m_83471_(p_105106_.m_133339_(), $$3);
                $$4.m_83402_(p_105106_.m_133343_());
                break;
            }
            case REMOVE: {
                $$1.m_83479_(p_105106_.m_133339_(), $$1.m_83477_($$2));
            }
        }
    }

    @Override
    public void m_5556_(ClientboundSetDisplayObjectivePacket p_105086_) {
        PacketUtils.m_131363_(p_105086_, this, this.f_104888_);
        Scoreboard $$1 = this.f_104889_.m_6188_();
        String $$2 = p_105086_.m_133142_();
        Objective $$3 = $$2 == null ? null : $$1.m_83469_($$2);
        $$1.m_7136_(p_105086_.m_133139_(), $$3);
    }

    @Override
    public void m_5582_(ClientboundSetPlayerTeamPacket p_105104_) {
        PlayerTeam $$4;
        PacketUtils.m_131363_(p_105104_, this, this.f_104888_);
        Scoreboard $$1 = this.f_104889_.m_6188_();
        ClientboundSetPlayerTeamPacket.Action $$2 = p_105104_.m_179338_();
        if ($$2 == ClientboundSetPlayerTeamPacket.Action.ADD) {
            PlayerTeam $$3 = $$1.m_83492_(p_105104_.m_133311_());
        } else {
            $$4 = $$1.m_83489_(p_105104_.m_133311_());
            if ($$4 == null) {
                f_104883_.warn("Received packet for unknown team {}: team action: {}, player action: {}", new Object[]{p_105104_.m_133311_(), p_105104_.m_179338_(), p_105104_.m_179335_()});
                return;
            }
        }
        Optional<ClientboundSetPlayerTeamPacket.Parameters> $$5 = p_105104_.m_179339_();
        $$5.ifPresent(p_233670_ -> {
            Team.CollisionRule $$3;
            $$4.m_83353_(p_233670_.m_179363_());
            $$4.m_83351_(p_233670_.m_179367_());
            $$4.m_83342_(p_233670_.m_179366_());
            Team.Visibility $$2 = Team.Visibility.m_83579_(p_233670_.m_179368_());
            if ($$2 != null) {
                $$4.m_83346_($$2);
            }
            if (($$3 = Team.CollisionRule.m_83555_(p_233670_.m_179369_())) != null) {
                $$4.m_83344_($$3);
            }
            $$4.m_83360_(p_233670_.m_179370_());
            $$4.m_83365_(p_233670_.m_179371_());
        });
        ClientboundSetPlayerTeamPacket.Action $$6 = p_105104_.m_179335_();
        if ($$6 == ClientboundSetPlayerTeamPacket.Action.ADD) {
            for (String $$7 : p_105104_.m_133315_()) {
                $$1.m_6546_($$7, $$4);
            }
        } else if ($$6 == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
            for (String $$8 : p_105104_.m_133315_()) {
                $$1.m_6519_($$8, $$4);
            }
        }
        if ($$2 == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
            $$1.m_83475_($$4);
        }
    }

    @Override
    public void m_7406_(ClientboundLevelParticlesPacket p_105026_) {
        PacketUtils.m_131363_(p_105026_, this, this.f_104888_);
        if (p_105026_.m_132321_() == 0) {
            double $$1 = p_105026_.m_132320_() * p_105026_.m_132317_();
            double $$2 = p_105026_.m_132320_() * p_105026_.m_132318_();
            double $$3 = p_105026_.m_132320_() * p_105026_.m_132319_();
            try {
                this.f_104889_.m_6493_(p_105026_.m_132322_(), p_105026_.m_132311_(), p_105026_.m_132314_(), p_105026_.m_132315_(), p_105026_.m_132316_(), $$1, $$2, $$3);
            }
            catch (Throwable $$4) {
                f_104883_.warn("Could not spawn particle effect {}", (Object)p_105026_.m_132322_());
            }
        } else {
            for (int $$5 = 0; $$5 < p_105026_.m_132321_(); ++$$5) {
                double $$6 = this.f_104898_.m_188583_() * (double)p_105026_.m_132317_();
                double $$7 = this.f_104898_.m_188583_() * (double)p_105026_.m_132318_();
                double $$8 = this.f_104898_.m_188583_() * (double)p_105026_.m_132319_();
                double $$9 = this.f_104898_.m_188583_() * (double)p_105026_.m_132320_();
                double $$10 = this.f_104898_.m_188583_() * (double)p_105026_.m_132320_();
                double $$11 = this.f_104898_.m_188583_() * (double)p_105026_.m_132320_();
                try {
                    this.f_104889_.m_6493_(p_105026_.m_132322_(), p_105026_.m_132311_(), p_105026_.m_132314_() + $$6, p_105026_.m_132315_() + $$7, p_105026_.m_132316_() + $$8, $$9, $$10, $$11);
                    continue;
                }
                catch (Throwable $$12) {
                    f_104883_.warn("Could not spawn particle effect {}", (Object)p_105026_.m_132322_());
                    return;
                }
            }
        }
    }

    @Override
    public void m_141955_(ClientboundPingPacket p_171769_) {
        PacketUtils.m_131363_(p_171769_, this, this.f_104888_);
        this.m_104955_(new ServerboundPongPacket(p_171769_.m_179025_()));
    }

    @Override
    public void m_7710_(ClientboundUpdateAttributesPacket p_105128_) {
        PacketUtils.m_131363_(p_105128_, this, this.f_104888_);
        Entity $$1 = this.f_104889_.m_6815_(p_105128_.m_133588_());
        if ($$1 == null) {
            return;
        }
        if (!($$1 instanceof LivingEntity)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + $$1 + ")");
        }
        AttributeMap $$2 = ((LivingEntity)$$1).m_21204_();
        for (ClientboundUpdateAttributesPacket.AttributeSnapshot $$3 : p_105128_.m_133591_()) {
            AttributeInstance $$4 = $$2.m_22146_($$3.m_133601_());
            if ($$4 == null) {
                f_104883_.warn("Entity {} does not have attribute {}", (Object)$$1, (Object)Registry.f_122866_.m_7981_($$3.m_133601_()));
                continue;
            }
            $$4.m_22100_($$3.m_133602_());
            $$4.m_22132_();
            for (AttributeModifier $$5 : $$3.m_133603_()) {
                $$4.m_22118_($$5);
            }
        }
    }

    @Override
    public void m_7339_(ClientboundPlaceGhostRecipePacket p_105046_) {
        PacketUtils.m_131363_(p_105046_, this, this.f_104888_);
        AbstractContainerMenu $$1 = this.f_104888_.f_91074_.f_36096_;
        if ($$1.f_38840_ != p_105046_.m_132658_()) {
            return;
        }
        this.f_104900_.m_44043_(p_105046_.m_132655_()).ifPresent(p_233667_ -> {
            if (this.f_104888_.f_91080_ instanceof RecipeUpdateListener) {
                RecipeBookComponent $$2 = ((RecipeUpdateListener)((Object)this.f_104888_.f_91080_)).m_5564_();
                $$2.m_7173_((Recipe<?>)p_233667_, (List<Slot>)p_233666_.f_38839_);
            }
        });
    }

    @Override
    public void m_183514_(ClientboundLightUpdatePacket p_194243_) {
        PacketUtils.m_131363_(p_194243_, this, this.f_104888_);
        int $$1 = p_194243_.m_132349_();
        int $$2 = p_194243_.m_132352_();
        ClientboundLightUpdatePacketData $$3 = p_194243_.m_195722_();
        this.f_104889_.m_194171_(() -> this.m_194248_($$1, $$2, $$3));
    }

    private void m_194248_(int p_194249_, int p_194250_, ClientboundLightUpdatePacketData p_194251_) {
        LevelLightEngine $$3 = this.f_104889_.m_7726_().m_7827_();
        BitSet $$4 = p_194251_.m_195740_();
        BitSet $$5 = p_194251_.m_195751_();
        Iterator<byte[]> $$6 = p_194251_.m_195754_().iterator();
        this.m_171734_(p_194249_, p_194250_, $$3, LightLayer.SKY, $$4, $$5, $$6, p_194251_.m_195760_());
        BitSet $$7 = p_194251_.m_195757_();
        BitSet $$8 = p_194251_.m_195758_();
        Iterator<byte[]> $$9 = p_194251_.m_195759_().iterator();
        this.m_171734_(p_194249_, p_194250_, $$3, LightLayer.BLOCK, $$7, $$8, $$9, p_194251_.m_195760_());
        this.f_104889_.m_197405_(p_194249_, p_194250_);
    }

    @Override
    public void m_7330_(ClientboundMerchantOffersPacket p_105034_) {
        PacketUtils.m_131363_(p_105034_, this, this.f_104888_);
        AbstractContainerMenu $$1 = this.f_104888_.f_91074_.f_36096_;
        if (p_105034_.m_132468_() == $$1.f_38840_ && $$1 instanceof MerchantMenu) {
            MerchantMenu $$2 = (MerchantMenu)$$1;
            $$2.m_40046_(new MerchantOffers(p_105034_.m_132471_().m_45388_()));
            $$2.m_40066_(p_105034_.m_132473_());
            $$2.m_40069_(p_105034_.m_132472_());
            $$2.m_40048_(p_105034_.m_132474_());
            $$2.m_40058_(p_105034_.m_132475_());
        }
    }

    @Override
    public void m_7299_(ClientboundSetChunkCacheRadiusPacket p_105082_) {
        PacketUtils.m_131363_(p_105082_, this, this.f_104888_);
        this.f_104897_ = p_105082_.m_133108_();
        this.f_104888_.f_91066_.m_193770_(this.f_104897_);
        this.f_104889_.m_7726_().m_104416_(p_105082_.m_133108_());
    }

    @Override
    public void m_183623_(ClientboundSetSimulationDistancePacket p_194245_) {
        PacketUtils.m_131363_(p_194245_, this, this.f_104888_);
        this.f_194190_ = p_194245_.f_195796_();
        this.f_104889_.m_194174_(this.f_194190_);
    }

    @Override
    public void m_8065_(ClientboundSetChunkCacheCenterPacket p_105080_) {
        PacketUtils.m_131363_(p_105080_, this, this.f_104888_);
        this.f_104889_.m_7726_().m_104459_(p_105080_.m_133094_(), p_105080_.m_133097_());
    }

    @Override
    public void m_214108_(ClientboundBlockChangedAckPacket p_233698_) {
        PacketUtils.m_131363_(p_233698_, this, this.f_104888_);
        this.f_104889_.m_233651_(p_233698_.f_237578_());
    }

    private void m_171734_(int p_171735_, int p_171736_, LevelLightEngine p_171737_, LightLayer p_171738_, BitSet p_171739_, BitSet p_171740_, Iterator<byte[]> p_171741_, boolean p_171742_) {
        for (int $$8 = 0; $$8 < p_171737_.m_164446_(); ++$$8) {
            int $$9 = p_171737_.m_164447_() + $$8;
            boolean $$10 = p_171739_.get($$8);
            boolean $$11 = p_171740_.get($$8);
            if (!$$10 && !$$11) continue;
            p_171737_.m_5687_(p_171738_, SectionPos.m_123173_(p_171735_, $$9, p_171736_), $$10 ? new DataLayer((byte[])p_171741_.next().clone()) : new DataLayer(), p_171742_);
            this.f_104889_.m_104793_(p_171735_, $$9, p_171736_);
        }
    }

    @Override
    public Connection m_6198_() {
        return this.f_104885_;
    }

    public Collection<PlayerInfo> m_105142_() {
        return this.f_104892_.values();
    }

    public Collection<UUID> m_105143_() {
        return this.f_104892_.keySet();
    }

    @Nullable
    public PlayerInfo m_104949_(UUID p_104950_) {
        return this.f_104892_.get(p_104950_);
    }

    @Nullable
    public PlayerInfo m_104938_(String p_104939_) {
        for (PlayerInfo $$1 : this.f_104892_.values()) {
            if (!$$1.m_105312_().getName().equals(p_104939_)) continue;
            return $$1;
        }
        return null;
    }

    public GameProfile m_105144_() {
        return this.f_104886_;
    }

    public ClientAdvancements m_105145_() {
        return this.f_104893_;
    }

    public CommandDispatcher<SharedSuggestionProvider> m_105146_() {
        return this.f_104899_;
    }

    public ClientLevel m_105147_() {
        return this.f_104889_;
    }

    public DebugQueryHandler m_105149_() {
        return this.f_104896_;
    }

    public UUID m_105150_() {
        return this.f_104901_;
    }

    public Set<ResourceKey<Level>> m_105151_() {
        return this.f_104902_;
    }

    public RegistryAccess m_105152_() {
        return this.f_104903_;
    }

    public SignedMessageChain.Encoder m_241060_() {
        return this.f_240902_;
    }

    public LastSeenMessages.Update m_241918_() {
        this.f_241629_ = 0;
        return new LastSeenMessages.Update(this.f_241688_.m_242022_(), this.f_241621_);
    }

    public void m_242011_(PlayerChatMessage p_242356_, boolean p_242455_) {
        LastSeenMessages.Entry $$2 = p_242356_.m_241960_();
        if ($$2 == null) {
            return;
        }
        if (p_242455_) {
            this.f_241688_.m_241911_($$2);
            this.f_241621_ = Optional.empty();
        } else {
            this.f_241621_ = Optional.of($$2);
        }
        if (this.f_241629_++ > 64) {
            this.m_104955_(new ServerboundChatAckPacket(this.m_241918_()));
        }
    }
}

