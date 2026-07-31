/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.primitives.Floats
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.CommandSigningContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.ChatPreviewCache;
import net.minecraft.network.chat.ChatPreviewThrottler;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.LastSeenMessagesValidator;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.PreviewableCommand;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundChatPreviewPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundChatPreviewPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.server.network.TextFilter;
import net.minecraft.util.FutureChain;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class ServerGamePacketListenerImpl
implements ServerPlayerConnection,
TickablePacketListener,
ServerGamePacketListener {
    static final Logger f_9744_ = LogUtils.getLogger();
    private static final int f_143608_ = 15000;
    public static final double f_215198_ = Mth.m_144952_(6.0);
    private static final int f_215199_ = -1;
    private static final int f_241635_ = 4096;
    public final Connection f_9742_;
    private final MinecraftServer f_9745_;
    public ServerPlayer f_9743_;
    private int f_9746_;
    private int f_215200_ = -1;
    private long f_9747_;
    private boolean f_9748_;
    private long f_9749_;
    private int f_9750_;
    private int f_9751_;
    private double f_9753_;
    private double f_9754_;
    private double f_9755_;
    private double f_9756_;
    private double f_9757_;
    private double f_9758_;
    @Nullable
    private Entity f_9759_;
    private double f_9760_;
    private double f_9761_;
    private double f_9762_;
    private double f_9763_;
    private double f_9764_;
    private double f_9765_;
    @Nullable
    private Vec3 f_9766_;
    private int f_9767_;
    private int f_9735_;
    private boolean f_9736_;
    private int f_9737_;
    private boolean f_9738_;
    private int f_9739_;
    private int f_9740_;
    private int f_9741_;
    private final ChatPreviewCache f_242491_ = new ChatPreviewCache();
    private final ChatPreviewThrottler f_215196_ = new ChatPreviewThrottler();
    private final AtomicReference<Instant> f_215197_ = new AtomicReference<Instant>(Instant.EPOCH);
    private final SignedMessageChain.Decoder f_240889_;
    private final LastSeenMessagesValidator f_241654_ = new LastSeenMessagesValidator();
    private final FutureChain f_241681_;

    public ServerGamePacketListenerImpl(MinecraftServer p_9770_, Connection p_9771_, ServerPlayer p_9772_) {
        this.f_9745_ = p_9770_;
        this.f_9742_ = p_9771_;
        p_9771_.m_129505_(this);
        this.f_9743_ = p_9772_;
        p_9772_.f_8906_ = this;
        this.f_9747_ = Util.m_137550_();
        p_9772_.m_8967_().m_7674_();
        ProfilePublicKey $$3 = p_9772_.m_219760_();
        this.f_240889_ = $$3 != null ? new SignedMessageChain().m_241180_() : SignedMessageChain.Decoder.f_243004_;
        this.f_241681_ = new FutureChain(p_9770_);
    }

    @Override
    public void m_9933_() {
        if (this.f_215200_ > -1) {
            this.m_9829_(new ClientboundBlockChangedAckPacket(this.f_215200_));
            this.f_215200_ = -1;
        }
        this.m_9953_();
        this.f_9743_.f_19854_ = this.f_9743_.m_20185_();
        this.f_9743_.f_19855_ = this.f_9743_.m_20186_();
        this.f_9743_.f_19856_ = this.f_9743_.m_20189_();
        this.f_9743_.m_9240_();
        this.f_9743_.m_19890_(this.f_9753_, this.f_9754_, this.f_9755_, this.f_9743_.m_146908_(), this.f_9743_.m_146909_());
        ++this.f_9746_;
        this.f_9741_ = this.f_9740_;
        if (this.f_9736_ && !this.f_9743_.m_5803_() && !this.f_9743_.m_20159_()) {
            if (++this.f_9737_ > 80) {
                f_9744_.warn("{} was kicked for floating too long!", (Object)this.f_9743_.m_7755_().getString());
                this.m_9942_(Component.m_237115_("multiplayer.disconnect.flying"));
                return;
            }
        } else {
            this.f_9736_ = false;
            this.f_9737_ = 0;
        }
        this.f_9759_ = this.f_9743_.m_20201_();
        if (this.f_9759_ == this.f_9743_ || this.f_9759_.m_6688_() != this.f_9743_) {
            this.f_9759_ = null;
            this.f_9738_ = false;
            this.f_9739_ = 0;
        } else {
            this.f_9760_ = this.f_9759_.m_20185_();
            this.f_9761_ = this.f_9759_.m_20186_();
            this.f_9762_ = this.f_9759_.m_20189_();
            this.f_9763_ = this.f_9759_.m_20185_();
            this.f_9764_ = this.f_9759_.m_20186_();
            this.f_9765_ = this.f_9759_.m_20189_();
            if (this.f_9738_ && this.f_9743_.m_20201_().m_6688_() == this.f_9743_) {
                if (++this.f_9739_ > 80) {
                    f_9744_.warn("{} was kicked for floating a vehicle too long!", (Object)this.f_9743_.m_7755_().getString());
                    this.m_9942_(Component.m_237115_("multiplayer.disconnect.flying"));
                    return;
                }
            } else {
                this.f_9738_ = false;
                this.f_9739_ = 0;
            }
        }
        this.f_9745_.m_129905_().m_6180_("keepAlive");
        long $$0 = Util.m_137550_();
        if ($$0 - this.f_9747_ >= 15000L) {
            if (this.f_9748_) {
                this.m_9942_(Component.m_237115_("disconnect.timeout"));
            } else {
                this.f_9748_ = true;
                this.f_9747_ = $$0;
                this.f_9749_ = $$0;
                this.m_9829_(new ClientboundKeepAlivePacket(this.f_9749_));
            }
        }
        this.f_9745_.m_129905_().m_7238_();
        if (this.f_9750_ > 0) {
            --this.f_9750_;
        }
        if (this.f_9751_ > 0) {
            --this.f_9751_;
        }
        if (this.f_9743_.m_8956_() > 0L && this.f_9745_.m_129924_() > 0 && Util.m_137550_() - this.f_9743_.m_8956_() > (long)(this.f_9745_.m_129924_() * 1000 * 60)) {
            this.m_9942_(Component.m_237115_("multiplayer.disconnect.idling"));
        }
        this.f_215196_.m_236975_();
    }

    public void m_9953_() {
        this.f_9753_ = this.f_9743_.m_20185_();
        this.f_9754_ = this.f_9743_.m_20186_();
        this.f_9755_ = this.f_9743_.m_20189_();
        this.f_9756_ = this.f_9743_.m_20185_();
        this.f_9757_ = this.f_9743_.m_20186_();
        this.f_9758_ = this.f_9743_.m_20189_();
    }

    @Override
    public Connection m_6198_() {
        return this.f_9742_;
    }

    private boolean m_9956_() {
        return this.f_9745_.m_7779_(this.f_9743_.m_36316_());
    }

    public void m_9942_(Component p_9943_) {
        this.f_9742_.m_243124_(new ClientboundDisconnectPacket(p_9943_), PacketSendListener.m_243092_(() -> this.f_9742_.m_129507_(p_9943_)));
        this.f_9742_.m_129540_();
        this.f_9745_.m_18709_(this.f_9742_::m_129541_);
    }

    private <T, R> CompletableFuture<R> m_243080_(T p_243240_, BiFunction<TextFilter, T, CompletableFuture<R>> p_243271_) {
        return p_243271_.apply(this.f_9743_.m_8967_(), (TextFilter)p_243240_).thenApply(p_243192_ -> {
            if (!this.m_6198_().m_129536_()) {
                f_9744_.debug("Ignoring packet due to disconnection");
                throw new CancellationException("disconnected");
            }
            return p_243192_;
        });
    }

    private CompletableFuture<FilteredText> m_243132_(String p_243213_) {
        return this.m_243080_(p_243213_, TextFilter::m_6770_);
    }

    private CompletableFuture<List<FilteredText>> m_243065_(List<String> p_243258_) {
        return this.m_243080_(p_243258_, TextFilter::m_5925_);
    }

    @Override
    public void m_5918_(ServerboundPlayerInputPacket p_9893_) {
        PacketUtils.m_131359_(p_9893_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_8980_(p_9893_.m_134355_(), p_9893_.m_134358_(), p_9893_.m_134359_(), p_9893_.m_134360_());
    }

    private static boolean m_143663_(double p_143664_, double p_143665_, double p_143666_, float p_143667_, float p_143668_) {
        return Double.isNaN(p_143664_) || Double.isNaN(p_143665_) || Double.isNaN(p_143666_) || !Floats.isFinite((float)p_143668_) || !Floats.isFinite((float)p_143667_);
    }

    private static double m_143609_(double p_143610_) {
        return Mth.m_14008_(p_143610_, -3.0E7, 3.0E7);
    }

    private static double m_143653_(double p_143654_) {
        return Mth.m_14008_(p_143654_, -2.0E7, 2.0E7);
    }

    @Override
    public void m_5659_(ServerboundMoveVehiclePacket p_9876_) {
        PacketUtils.m_131359_(p_9876_, this, this.f_9743_.m_9236_());
        if (ServerGamePacketListenerImpl.m_143663_(p_9876_.m_134199_(), p_9876_.m_134202_(), p_9876_.m_134203_(), p_9876_.m_134204_(), p_9876_.m_134205_())) {
            this.m_9942_(Component.m_237115_("multiplayer.disconnect.invalid_vehicle_movement"));
            return;
        }
        Entity $$1 = this.f_9743_.m_20201_();
        if ($$1 != this.f_9743_ && $$1.m_6688_() == this.f_9743_ && $$1 == this.f_9759_) {
            ServerLevel $$2 = this.f_9743_.m_9236_();
            double $$3 = $$1.m_20185_();
            double $$4 = $$1.m_20186_();
            double $$5 = $$1.m_20189_();
            double $$6 = ServerGamePacketListenerImpl.m_143609_(p_9876_.m_134199_());
            double $$7 = ServerGamePacketListenerImpl.m_143653_(p_9876_.m_134202_());
            double $$8 = ServerGamePacketListenerImpl.m_143609_(p_9876_.m_134203_());
            float $$9 = Mth.m_14177_(p_9876_.m_134204_());
            float $$10 = Mth.m_14177_(p_9876_.m_134205_());
            double $$11 = $$6 - this.f_9760_;
            double $$12 = $$7 - this.f_9761_;
            double $$13 = $$8 - this.f_9762_;
            double $$15 = $$11 * $$11 + $$12 * $$12 + $$13 * $$13;
            double $$14 = $$1.m_20184_().m_82556_();
            if ($$15 - $$14 > 100.0 && !this.m_9956_()) {
                f_9744_.warn("{} (vehicle of {}) moved too quickly! {},{},{}", new Object[]{$$1.m_7755_().getString(), this.f_9743_.m_7755_().getString(), $$11, $$12, $$13});
                this.f_9742_.m_129512_(new ClientboundMoveVehiclePacket($$1));
                return;
            }
            boolean $$16 = $$2.m_45756_($$1, $$1.m_20191_().m_82406_(0.0625));
            $$11 = $$6 - this.f_9763_;
            $$12 = $$7 - this.f_9764_ - 1.0E-6;
            $$13 = $$8 - this.f_9765_;
            boolean $$17 = $$1.f_201939_;
            $$1.m_6478_(MoverType.PLAYER, new Vec3($$11, $$12, $$13));
            double $$18 = $$12;
            $$11 = $$6 - $$1.m_20185_();
            $$12 = $$7 - $$1.m_20186_();
            if ($$12 > -0.5 || $$12 < 0.5) {
                $$12 = 0.0;
            }
            $$13 = $$8 - $$1.m_20189_();
            $$15 = $$11 * $$11 + $$12 * $$12 + $$13 * $$13;
            boolean $$19 = false;
            if ($$15 > 0.0625) {
                $$19 = true;
                f_9744_.warn("{} (vehicle of {}) moved wrongly! {}", new Object[]{$$1.m_7755_().getString(), this.f_9743_.m_7755_().getString(), Math.sqrt($$15)});
            }
            $$1.m_19890_($$6, $$7, $$8, $$9, $$10);
            boolean $$20 = $$2.m_45756_($$1, $$1.m_20191_().m_82406_(0.0625));
            if ($$16 && ($$19 || !$$20)) {
                $$1.m_19890_($$3, $$4, $$5, $$9, $$10);
                this.f_9742_.m_129512_(new ClientboundMoveVehiclePacket($$1));
                return;
            }
            this.f_9743_.m_9236_().m_7726_().m_8385_(this.f_9743_);
            this.f_9743_.m_36378_(this.f_9743_.m_20185_() - $$3, this.f_9743_.m_20186_() - $$4, this.f_9743_.m_20189_() - $$5);
            this.f_9738_ = $$18 >= -0.03125 && !$$17 && !this.f_9745_.m_129915_() && !$$1.m_20068_() && this.m_9793_($$1);
            this.f_9763_ = $$1.m_20185_();
            this.f_9764_ = $$1.m_20186_();
            this.f_9765_ = $$1.m_20189_();
        }
    }

    private boolean m_9793_(Entity p_9794_) {
        return p_9794_.f_19853_.m_45556_(p_9794_.m_20191_().m_82400_(0.0625).m_82363_(0.0, -0.55, 0.0)).allMatch(BlockBehaviour.BlockStateBase::m_60795_);
    }

    @Override
    public void m_7376_(ServerboundAcceptTeleportationPacket p_9835_) {
        PacketUtils.m_131359_(p_9835_, this, this.f_9743_.m_9236_());
        if (p_9835_.m_133795_() == this.f_9767_) {
            if (this.f_9766_ == null) {
                this.m_9942_(Component.m_237115_("multiplayer.disconnect.invalid_player_movement"));
                return;
            }
            this.f_9743_.m_19890_(this.f_9766_.f_82479_, this.f_9766_.f_82480_, this.f_9766_.f_82481_, this.f_9743_.m_146908_(), this.f_9743_.m_146909_());
            this.f_9756_ = this.f_9766_.f_82479_;
            this.f_9757_ = this.f_9766_.f_82480_;
            this.f_9758_ = this.f_9766_.f_82481_;
            if (this.f_9743_.m_8958_()) {
                this.f_9743_.m_8959_();
            }
            this.f_9766_ = null;
        }
    }

    @Override
    public void m_7411_(ServerboundRecipeBookSeenRecipePacket p_9897_) {
        PacketUtils.m_131359_(p_9897_, this, this.f_9743_.m_9236_());
        this.f_9745_.m_129894_().m_44043_(p_9897_.m_134390_()).ifPresent(this.f_9743_.m_8952_()::m_12721_);
    }

    @Override
    public void m_7982_(ServerboundRecipeBookChangeSettingsPacket p_9895_) {
        PacketUtils.m_131359_(p_9895_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_8952_().m_12696_(p_9895_.m_134375_(), p_9895_.m_134378_(), p_9895_.m_134379_());
    }

    @Override
    public void m_6947_(ServerboundSeenAdvancementsPacket p_9903_) {
        PacketUtils.m_131359_(p_9903_, this, this.f_9743_.m_9236_());
        if (p_9903_.m_134447_() == ServerboundSeenAdvancementsPacket.Action.OPENED_TAB) {
            ResourceLocation $$1 = p_9903_.m_134448_();
            Advancement $$2 = this.f_9745_.m_129889_().m_136041_($$1);
            if ($$2 != null) {
                this.f_9743_.m_8960_().m_135983_($$2);
            }
        }
    }

    @Override
    public void m_7741_(ServerboundCommandSuggestionPacket p_9847_) {
        PacketUtils.m_131359_(p_9847_, this, this.f_9743_.m_9236_());
        StringReader $$1 = new StringReader(p_9847_.m_133904_());
        if ($$1.canRead() && $$1.peek() == '/') {
            $$1.skip();
        }
        ParseResults $$2 = this.f_9745_.m_129892_().m_82094_().parse($$1, (Object)this.f_9743_.m_20203_());
        this.f_9745_.m_129892_().m_82094_().getCompletionSuggestions($$2).thenAccept(p_238197_ -> this.f_9742_.m_129512_(new ClientboundCommandSuggestionsPacket(p_9847_.m_133901_(), (Suggestions)p_238197_)));
    }

    @Override
    public void m_7192_(ServerboundSetCommandBlockPacket p_9911_) {
        PacketUtils.m_131359_(p_9911_, this, this.f_9743_.m_9236_());
        if (!this.f_9745_.m_6993_()) {
            this.f_9743_.m_213846_(Component.m_237115_("advMode.notEnabled"));
            return;
        }
        if (!this.f_9743_.m_36337_()) {
            this.f_9743_.m_213846_(Component.m_237115_("advMode.notAllowed"));
            return;
        }
        BaseCommandBlock $$1 = null;
        CommandBlockEntity $$2 = null;
        BlockPos $$3 = p_9911_.m_134521_();
        BlockEntity $$4 = this.f_9743_.f_19853_.m_7702_($$3);
        if ($$4 instanceof CommandBlockEntity) {
            $$2 = (CommandBlockEntity)$$4;
            $$1 = $$2.m_59141_();
        }
        String $$5 = p_9911_.m_134524_();
        boolean $$6 = p_9911_.m_134525_();
        if ($$1 != null) {
            BlockState $$12;
            CommandBlockEntity.Mode $$7 = $$2.m_59148_();
            BlockState $$8 = this.f_9743_.f_19853_.m_8055_($$3);
            Direction $$9 = $$8.m_61143_(CommandBlock.f_51793_);
            switch (p_9911_.m_134528_()) {
                case SEQUENCE: {
                    BlockState $$10 = Blocks.f_50448_.m_49966_();
                    break;
                }
                case AUTO: {
                    BlockState $$11 = Blocks.f_50447_.m_49966_();
                    break;
                }
                default: {
                    $$12 = Blocks.f_50272_.m_49966_();
                }
            }
            BlockState $$13 = (BlockState)((BlockState)$$12.m_61124_(CommandBlock.f_51793_, $$9)).m_61124_(CommandBlock.f_51794_, p_9911_.m_134526_());
            if ($$13 != $$8) {
                this.f_9743_.f_19853_.m_7731_($$3, $$13, 2);
                $$4.m_155250_($$13);
                this.f_9743_.f_19853_.m_46745_($$3).m_142169_($$4);
            }
            $$1.m_6590_($$5);
            $$1.m_45428_($$6);
            if (!$$6) {
                $$1.m_45433_(null);
            }
            $$2.m_59137_(p_9911_.m_134527_());
            if ($$7 != p_9911_.m_134528_()) {
                $$2.m_59144_();
            }
            $$1.m_7368_();
            if (!StringUtil.m_14408_($$5)) {
                this.f_9743_.m_213846_(Component.m_237110_("advMode.setCommand.success", $$5));
            }
        }
    }

    @Override
    public void m_6629_(ServerboundSetCommandMinecartPacket p_9913_) {
        PacketUtils.m_131359_(p_9913_, this, this.f_9743_.m_9236_());
        if (!this.f_9745_.m_6993_()) {
            this.f_9743_.m_213846_(Component.m_237115_("advMode.notEnabled"));
            return;
        }
        if (!this.f_9743_.m_36337_()) {
            this.f_9743_.m_213846_(Component.m_237115_("advMode.notAllowed"));
            return;
        }
        BaseCommandBlock $$1 = p_9913_.m_134537_(this.f_9743_.f_19853_);
        if ($$1 != null) {
            $$1.m_6590_(p_9913_.m_134545_());
            $$1.m_45428_(p_9913_.m_134548_());
            if (!p_9913_.m_134548_()) {
                $$1.m_45433_(null);
            }
            $$1.m_7368_();
            this.f_9743_.m_213846_(Component.m_237110_("advMode.setCommand.success", p_9913_.m_134545_()));
        }
    }

    @Override
    public void m_7965_(ServerboundPickItemPacket p_9880_) {
        PacketUtils.m_131359_(p_9880_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_150109_().m_36038_(p_9880_.m_134232_());
        this.f_9743_.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(-2, 0, this.f_9743_.m_150109_().f_35977_, this.f_9743_.m_150109_().m_8020_(this.f_9743_.m_150109_().f_35977_)));
        this.f_9743_.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(-2, 0, p_9880_.m_134232_(), this.f_9743_.m_150109_().m_8020_(p_9880_.m_134232_())));
        this.f_9743_.f_8906_.m_9829_(new ClientboundSetCarriedItemPacket(this.f_9743_.m_150109_().f_35977_));
    }

    @Override
    public void m_5591_(ServerboundRenameItemPacket p_9899_) {
        PacketUtils.m_131359_(p_9899_, this, this.f_9743_.m_9236_());
        AbstractContainerMenu abstractContainerMenu = this.f_9743_.f_36096_;
        if (abstractContainerMenu instanceof AnvilMenu) {
            AnvilMenu $$1 = (AnvilMenu)abstractContainerMenu;
            if (!$$1.m_6875_(this.f_9743_)) {
                f_9744_.debug("Player {} interacted with invalid menu {}", (Object)this.f_9743_, (Object)$$1);
                return;
            }
            String $$2 = SharedConstants.m_136190_(p_9899_.m_134403_());
            if ($$2.length() <= 50) {
                $$1.m_39020_($$2);
            }
        }
    }

    @Override
    public void m_5712_(ServerboundSetBeaconPacket p_9907_) {
        PacketUtils.m_131359_(p_9907_, this, this.f_9743_.m_9236_());
        AbstractContainerMenu abstractContainerMenu = this.f_9743_.f_36096_;
        if (abstractContainerMenu instanceof BeaconMenu) {
            BeaconMenu $$1 = (BeaconMenu)abstractContainerMenu;
            if (!this.f_9743_.f_36096_.m_6875_(this.f_9743_)) {
                f_9744_.debug("Player {} interacted with invalid menu {}", (Object)this.f_9743_, (Object)this.f_9743_.f_36096_);
                return;
            }
            $$1.m_219972_(p_9907_.m_237994_(), p_9907_.m_238000_());
        }
    }

    @Override
    public void m_7424_(ServerboundSetStructureBlockPacket p_9919_) {
        PacketUtils.m_131359_(p_9919_, this, this.f_9743_.m_9236_());
        if (!this.f_9743_.m_36337_()) {
            return;
        }
        BlockPos $$1 = p_9919_.m_134629_();
        BlockState $$2 = this.f_9743_.f_19853_.m_8055_($$1);
        BlockEntity $$3 = this.f_9743_.f_19853_.m_7702_($$1);
        if ($$3 instanceof StructureBlockEntity) {
            StructureBlockEntity $$4 = (StructureBlockEntity)$$3;
            $$4.m_59860_(p_9919_.m_134633_());
            $$4.m_59868_(p_9919_.m_134634_());
            $$4.m_59885_(p_9919_.m_134635_());
            $$4.m_155797_(p_9919_.m_179787_());
            $$4.m_59881_(p_9919_.m_134637_());
            $$4.m_59883_(p_9919_.m_134638_());
            $$4.m_59887_(p_9919_.m_134639_());
            $$4.m_59876_(p_9919_.m_134640_());
            $$4.m_59896_(p_9919_.m_134641_());
            $$4.m_59898_(p_9919_.m_134642_());
            $$4.m_59838_(p_9919_.m_134643_());
            $$4.m_59840_(p_9919_.m_134644_());
            if ($$4.m_59901_()) {
                String $$5 = $$4.m_59895_();
                if (p_9919_.m_134632_() == StructureBlockEntity.UpdateType.SAVE_AREA) {
                    if ($$4.m_59830_()) {
                        this.f_9743_.m_5661_(Component.m_237110_("structure_block.save_success", $$5), false);
                    } else {
                        this.f_9743_.m_5661_(Component.m_237110_("structure_block.save_failure", $$5), false);
                    }
                } else if (p_9919_.m_134632_() == StructureBlockEntity.UpdateType.LOAD_AREA) {
                    if (!$$4.m_59832_()) {
                        this.f_9743_.m_5661_(Component.m_237110_("structure_block.load_not_found", $$5), false);
                    } else if ($$4.m_59842_(this.f_9743_.m_9236_())) {
                        this.f_9743_.m_5661_(Component.m_237110_("structure_block.load_success", $$5), false);
                    } else {
                        this.f_9743_.m_5661_(Component.m_237110_("structure_block.load_prepare", $$5), false);
                    }
                } else if (p_9919_.m_134632_() == StructureBlockEntity.UpdateType.SCAN_AREA) {
                    if ($$4.m_59829_()) {
                        this.f_9743_.m_5661_(Component.m_237110_("structure_block.size_success", $$5), false);
                    } else {
                        this.f_9743_.m_5661_(Component.m_237115_("structure_block.size_failure"), false);
                    }
                }
            } else {
                this.f_9743_.m_5661_(Component.m_237110_("structure_block.invalid_structure_name", p_9919_.m_134634_()), false);
            }
            $$4.m_6596_();
            this.f_9743_.f_19853_.m_7260_($$1, $$2, $$2, 3);
        }
    }

    @Override
    public void m_8019_(ServerboundSetJigsawBlockPacket p_9917_) {
        PacketUtils.m_131359_(p_9917_, this, this.f_9743_.m_9236_());
        if (!this.f_9743_.m_36337_()) {
            return;
        }
        BlockPos $$1 = p_9917_.m_134585_();
        BlockState $$2 = this.f_9743_.f_19853_.m_8055_($$1);
        BlockEntity $$3 = this.f_9743_.f_19853_.m_7702_($$1);
        if ($$3 instanceof JigsawBlockEntity) {
            JigsawBlockEntity $$4 = (JigsawBlockEntity)$$3;
            $$4.m_59435_(p_9917_.m_134588_());
            $$4.m_59438_(p_9917_.m_134589_());
            $$4.m_222763_(ResourceKey.m_135785_(Registry.f_122884_, p_9917_.m_134590_()));
            $$4.m_59431_(p_9917_.m_134591_());
            $$4.m_59424_(p_9917_.m_134592_());
            $$4.m_6596_();
            this.f_9743_.f_19853_.m_7260_($$1, $$2, $$2, 3);
        }
    }

    @Override
    public void m_6449_(ServerboundJigsawGeneratePacket p_9868_) {
        PacketUtils.m_131359_(p_9868_, this, this.f_9743_.m_9236_());
        if (!this.f_9743_.m_36337_()) {
            return;
        }
        BlockPos $$1 = p_9868_.m_134087_();
        BlockEntity $$2 = this.f_9743_.f_19853_.m_7702_($$1);
        if ($$2 instanceof JigsawBlockEntity) {
            JigsawBlockEntity $$3 = (JigsawBlockEntity)$$2;
            $$3.m_59420_(this.f_9743_.m_9236_(), p_9868_.m_134090_(), p_9868_.m_134091_());
        }
    }

    @Override
    public void m_6321_(ServerboundSelectTradePacket p_9905_) {
        PacketUtils.m_131359_(p_9905_, this, this.f_9743_.m_9236_());
        int $$1 = p_9905_.m_134469_();
        AbstractContainerMenu abstractContainerMenu = this.f_9743_.f_36096_;
        if (abstractContainerMenu instanceof MerchantMenu) {
            MerchantMenu $$2 = (MerchantMenu)abstractContainerMenu;
            if (!$$2.m_6875_(this.f_9743_)) {
                f_9744_.debug("Player {} interacted with invalid menu {}", (Object)this.f_9743_, (Object)$$2);
                return;
            }
            $$2.m_40063_($$1);
            $$2.m_40072_($$1);
        }
    }

    @Override
    public void m_6829_(ServerboundEditBookPacket p_9862_) {
        int $$1 = p_9862_.m_134013_();
        if (!Inventory.m_36045_($$1) && $$1 != 40) {
            return;
        }
        ArrayList $$2 = Lists.newArrayList();
        Optional<String> $$3 = p_9862_.m_182761_();
        $$3.ifPresent($$2::add);
        p_9862_.m_182755_().stream().limit(100L).forEach($$2::add);
        Consumer<List> $$4 = $$3.isPresent() ? p_238198_ -> this.m_215208_((FilteredText)p_238198_.get(0), p_238198_.subList(1, p_238198_.size()), $$1) : p_143627_ -> this.m_9812_((List<FilteredText>)p_143627_, $$1);
        this.m_243065_($$2).thenAcceptAsync($$4, (Executor)this.f_9745_);
    }

    private void m_9812_(List<FilteredText> p_9813_, int p_9814_) {
        ItemStack $$2 = this.f_9743_.m_150109_().m_8020_(p_9814_);
        if (!$$2.m_150930_(Items.f_42614_)) {
            return;
        }
        this.m_143634_(p_9813_, UnaryOperator.identity(), $$2);
    }

    private void m_215208_(FilteredText p_215209_, List<FilteredText> p_215210_, int p_215211_) {
        ItemStack $$3 = this.f_9743_.m_150109_().m_8020_(p_215211_);
        if (!$$3.m_150930_(Items.f_42614_)) {
            return;
        }
        ItemStack $$4 = new ItemStack(Items.f_42615_);
        CompoundTag $$5 = $$3.m_41783_();
        if ($$5 != null) {
            $$4.m_41751_($$5.m_6426_());
        }
        $$4.m_41700_("author", StringTag.m_129297_(this.f_9743_.m_7755_().getString()));
        if (this.f_9743_.m_143387_()) {
            $$4.m_41700_("title", StringTag.m_129297_(p_215209_.m_243113_()));
        } else {
            $$4.m_41700_("filtered_title", StringTag.m_129297_(p_215209_.m_243113_()));
            $$4.m_41700_("title", StringTag.m_129297_(p_215209_.f_215168_()));
        }
        this.m_143634_(p_215210_, p_238206_ -> Component.Serializer.m_130703_(Component.m_237113_(p_238206_)), $$4);
        this.f_9743_.m_150109_().m_6836_(p_215211_, $$4);
    }

    private void m_143634_(List<FilteredText> p_143635_, UnaryOperator<String> p_143636_, ItemStack p_143637_) {
        ListTag $$3 = new ListTag();
        if (this.f_9743_.m_143387_()) {
            p_143635_.stream().map(p_238209_ -> StringTag.m_129297_((String)p_143636_.apply(p_238209_.m_243113_()))).forEach($$3::add);
        } else {
            CompoundTag $$4 = new CompoundTag();
            int $$6 = p_143635_.size();
            for (int $$5 = 0; $$5 < $$6; ++$$5) {
                FilteredText $$7 = p_143635_.get($$5);
                String $$8 = $$7.f_215168_();
                $$3.add(StringTag.m_129297_((String)p_143636_.apply($$8)));
                if (!$$7.m_215174_()) continue;
                $$4.m_128359_(String.valueOf($$5), (String)p_143636_.apply($$7.m_243113_()));
            }
            if (!$$4.m_128456_()) {
                p_143637_.m_41700_("filtered_pages", $$4);
            }
        }
        p_143637_.m_41700_("pages", $$3);
    }

    @Override
    public void m_7548_(ServerboundEntityTagQuery p_9864_) {
        PacketUtils.m_131359_(p_9864_, this, this.f_9743_.m_9236_());
        if (!this.f_9743_.m_20310_(2)) {
            return;
        }
        Entity $$1 = this.f_9743_.m_9236_().m_6815_(p_9864_.m_134029_());
        if ($$1 != null) {
            CompoundTag $$2 = $$1.m_20240_(new CompoundTag());
            this.f_9743_.f_8906_.m_9829_(new ClientboundTagQueryPacket(p_9864_.m_134026_(), $$2));
        }
    }

    @Override
    public void m_6780_(ServerboundBlockEntityTagQuery p_9837_) {
        PacketUtils.m_131359_(p_9837_, this, this.f_9743_.m_9236_());
        if (!this.f_9743_.m_20310_(2)) {
            return;
        }
        BlockEntity $$1 = this.f_9743_.m_9236_().m_7702_(p_9837_.m_133813_());
        CompoundTag $$2 = $$1 != null ? $$1.m_187482_() : null;
        this.f_9743_.f_8906_.m_9829_(new ClientboundTagQueryPacket(p_9837_.m_133810_(), $$2));
    }

    @Override
    public void m_7185_(ServerboundMovePlayerPacket p_9874_) {
        boolean $$19;
        PacketUtils.m_131359_(p_9874_, this, this.f_9743_.m_9236_());
        if (ServerGamePacketListenerImpl.m_143663_(p_9874_.m_134129_(0.0), p_9874_.m_134140_(0.0), p_9874_.m_134146_(0.0), p_9874_.m_134131_(0.0f), p_9874_.m_134142_(0.0f))) {
            this.m_9942_(Component.m_237115_("multiplayer.disconnect.invalid_player_movement"));
            return;
        }
        ServerLevel $$1 = this.f_9743_.m_9236_();
        if (this.f_9743_.f_8944_) {
            return;
        }
        if (this.f_9746_ == 0) {
            this.m_9953_();
        }
        if (this.f_9766_ != null) {
            if (this.f_9746_ - this.f_9735_ > 20) {
                this.f_9735_ = this.f_9746_;
                this.m_9774_(this.f_9766_.f_82479_, this.f_9766_.f_82480_, this.f_9766_.f_82481_, this.f_9743_.m_146908_(), this.f_9743_.m_146909_());
            }
            return;
        }
        this.f_9735_ = this.f_9746_;
        double $$2 = ServerGamePacketListenerImpl.m_143609_(p_9874_.m_134129_(this.f_9743_.m_20185_()));
        double $$3 = ServerGamePacketListenerImpl.m_143653_(p_9874_.m_134140_(this.f_9743_.m_20186_()));
        double $$4 = ServerGamePacketListenerImpl.m_143609_(p_9874_.m_134146_(this.f_9743_.m_20189_()));
        float $$5 = Mth.m_14177_(p_9874_.m_134131_(this.f_9743_.m_146908_()));
        float $$6 = Mth.m_14177_(p_9874_.m_134142_(this.f_9743_.m_146909_()));
        if (this.f_9743_.m_20159_()) {
            this.f_9743_.m_19890_(this.f_9743_.m_20185_(), this.f_9743_.m_20186_(), this.f_9743_.m_20189_(), $$5, $$6);
            this.f_9743_.m_9236_().m_7726_().m_8385_(this.f_9743_);
            return;
        }
        double $$7 = this.f_9743_.m_20185_();
        double $$8 = this.f_9743_.m_20186_();
        double $$9 = this.f_9743_.m_20189_();
        double $$10 = this.f_9743_.m_20186_();
        double $$11 = $$2 - this.f_9753_;
        double $$12 = $$3 - this.f_9754_;
        double $$13 = $$4 - this.f_9755_;
        double $$14 = this.f_9743_.m_20184_().m_82556_();
        double $$15 = $$11 * $$11 + $$12 * $$12 + $$13 * $$13;
        if (this.f_9743_.m_5803_()) {
            if ($$15 > 1.0) {
                this.m_9774_(this.f_9743_.m_20185_(), this.f_9743_.m_20186_(), this.f_9743_.m_20189_(), $$5, $$6);
            }
            return;
        }
        ++this.f_9740_;
        int $$16 = this.f_9740_ - this.f_9741_;
        if ($$16 > 5) {
            f_9744_.debug("{} is sending move packets too frequently ({} packets since last tick)", (Object)this.f_9743_.m_7755_().getString(), (Object)$$16);
            $$16 = 1;
        }
        if (!(this.f_9743_.m_8958_() || this.f_9743_.m_9236_().m_46469_().m_46207_(GameRules.f_46148_) && this.f_9743_.m_21255_())) {
            float $$17;
            float f = $$17 = this.f_9743_.m_21255_() ? 300.0f : 100.0f;
            if ($$15 - $$14 > (double)($$17 * (float)$$16) && !this.m_9956_()) {
                f_9744_.warn("{} moved too quickly! {},{},{}", new Object[]{this.f_9743_.m_7755_().getString(), $$11, $$12, $$13});
                this.m_9774_(this.f_9743_.m_20185_(), this.f_9743_.m_20186_(), this.f_9743_.m_20189_(), this.f_9743_.m_146908_(), this.f_9743_.m_146909_());
                return;
            }
        }
        AABB $$18 = this.f_9743_.m_20191_();
        $$11 = $$2 - this.f_9756_;
        $$12 = $$3 - this.f_9757_;
        $$13 = $$4 - this.f_9758_;
        boolean bl = $$19 = $$12 > 0.0;
        if (this.f_9743_.m_20096_() && !p_9874_.m_134139_() && $$19) {
            this.f_9743_.m_6135_();
        }
        boolean $$20 = this.f_9743_.f_201939_;
        this.f_9743_.m_6478_(MoverType.PLAYER, new Vec3($$11, $$12, $$13));
        double $$21 = $$12;
        $$11 = $$2 - this.f_9743_.m_20185_();
        $$12 = $$3 - this.f_9743_.m_20186_();
        if ($$12 > -0.5 || $$12 < 0.5) {
            $$12 = 0.0;
        }
        $$13 = $$4 - this.f_9743_.m_20189_();
        $$15 = $$11 * $$11 + $$12 * $$12 + $$13 * $$13;
        boolean $$22 = false;
        if (!this.f_9743_.m_8958_() && $$15 > 0.0625 && !this.f_9743_.m_5803_() && !this.f_9743_.f_8941_.m_9295_() && this.f_9743_.f_8941_.m_9290_() != GameType.SPECTATOR) {
            $$22 = true;
            f_9744_.warn("{} moved wrongly!", (Object)this.f_9743_.m_7755_().getString());
        }
        this.f_9743_.m_19890_($$2, $$3, $$4, $$5, $$6);
        if (!this.f_9743_.f_19794_ && !this.f_9743_.m_5803_() && ($$22 && $$1.m_45756_(this.f_9743_, $$18) || this.m_9795_($$1, $$18))) {
            this.m_9774_($$7, $$8, $$9, $$5, $$6);
            return;
        }
        this.f_9736_ = $$21 >= -0.03125 && !$$20 && this.f_9743_.f_8941_.m_9290_() != GameType.SPECTATOR && !this.f_9745_.m_129915_() && !this.f_9743_.m_150110_().f_35936_ && !this.f_9743_.m_21023_(MobEffects.f_19620_) && !this.f_9743_.m_21255_() && !this.f_9743_.m_21209_() && this.m_9793_(this.f_9743_);
        this.f_9743_.m_9236_().m_7726_().m_8385_(this.f_9743_);
        this.f_9743_.m_8972_(this.f_9743_.m_20186_() - $$10, p_9874_.m_134139_());
        this.f_9743_.m_6853_(p_9874_.m_134139_());
        if ($$19) {
            this.f_9743_.m_183634_();
        }
        this.f_9743_.m_36378_(this.f_9743_.m_20185_() - $$7, this.f_9743_.m_20186_() - $$8, this.f_9743_.m_20189_() - $$9);
        this.f_9756_ = this.f_9743_.m_20185_();
        this.f_9757_ = this.f_9743_.m_20186_();
        this.f_9758_ = this.f_9743_.m_20189_();
    }

    private boolean m_9795_(LevelReader p_9796_, AABB p_9797_) {
        Iterable<VoxelShape> $$2 = p_9796_.m_186431_(this.f_9743_, this.f_9743_.m_20191_().m_82406_(1.0E-5f));
        VoxelShape $$3 = Shapes.m_83064_(p_9797_.m_82406_(1.0E-5f));
        for (VoxelShape $$4 : $$2) {
            if (Shapes.m_83157_($$4, $$3, BooleanOp.f_82689_)) continue;
            return true;
        }
        return false;
    }

    public void m_143611_(double p_143612_, double p_143613_, double p_143614_, float p_143615_, float p_143616_) {
        this.m_143617_(p_143612_, p_143613_, p_143614_, p_143615_, p_143616_, Collections.emptySet(), true);
    }

    public void m_9774_(double p_9775_, double p_9776_, double p_9777_, float p_9778_, float p_9779_) {
        this.m_143617_(p_9775_, p_9776_, p_9777_, p_9778_, p_9779_, Collections.emptySet(), false);
    }

    public void m_9780_(double p_9781_, double p_9782_, double p_9783_, float p_9784_, float p_9785_, Set<ClientboundPlayerPositionPacket.RelativeArgument> p_9786_) {
        this.m_143617_(p_9781_, p_9782_, p_9783_, p_9784_, p_9785_, p_9786_, false);
    }

    public void m_143617_(double p_143618_, double p_143619_, double p_143620_, float p_143621_, float p_143622_, Set<ClientboundPlayerPositionPacket.RelativeArgument> p_143623_, boolean p_143624_) {
        double $$7 = p_143623_.contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.X) ? this.f_9743_.m_20185_() : 0.0;
        double $$8 = p_143623_.contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.Y) ? this.f_9743_.m_20186_() : 0.0;
        double $$9 = p_143623_.contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.Z) ? this.f_9743_.m_20189_() : 0.0;
        float $$10 = p_143623_.contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT) ? this.f_9743_.m_146908_() : 0.0f;
        float $$11 = p_143623_.contains((Object)ClientboundPlayerPositionPacket.RelativeArgument.X_ROT) ? this.f_9743_.m_146909_() : 0.0f;
        this.f_9766_ = new Vec3(p_143618_, p_143619_, p_143620_);
        if (++this.f_9767_ == Integer.MAX_VALUE) {
            this.f_9767_ = 0;
        }
        this.f_9735_ = this.f_9746_;
        this.f_9743_.m_19890_(p_143618_, p_143619_, p_143620_, p_143621_, p_143622_);
        this.f_9743_.f_8906_.m_9829_(new ClientboundPlayerPositionPacket(p_143618_ - $$7, p_143619_ - $$8, p_143620_ - $$9, p_143621_ - $$10, p_143622_ - $$11, p_143623_, this.f_9767_, p_143624_));
    }

    @Override
    public void m_7502_(ServerboundPlayerActionPacket p_9889_) {
        PacketUtils.m_131359_(p_9889_, this, this.f_9743_.m_9236_());
        BlockPos $$1 = p_9889_.m_134281_();
        this.f_9743_.m_9243_();
        ServerboundPlayerActionPacket.Action $$2 = p_9889_.m_134285_();
        switch ($$2) {
            case SWAP_ITEM_WITH_OFFHAND: {
                if (!this.f_9743_.m_5833_()) {
                    ItemStack $$3 = this.f_9743_.m_21120_(InteractionHand.OFF_HAND);
                    this.f_9743_.m_21008_(InteractionHand.OFF_HAND, this.f_9743_.m_21120_(InteractionHand.MAIN_HAND));
                    this.f_9743_.m_21008_(InteractionHand.MAIN_HAND, $$3);
                    this.f_9743_.m_5810_();
                }
                return;
            }
            case DROP_ITEM: {
                if (!this.f_9743_.m_5833_()) {
                    this.f_9743_.m_182294_(false);
                }
                return;
            }
            case DROP_ALL_ITEMS: {
                if (!this.f_9743_.m_5833_()) {
                    this.f_9743_.m_182294_(true);
                }
                return;
            }
            case RELEASE_USE_ITEM: {
                this.f_9743_.m_21253_();
                return;
            }
            case START_DESTROY_BLOCK: 
            case ABORT_DESTROY_BLOCK: 
            case STOP_DESTROY_BLOCK: {
                this.f_9743_.f_8941_.m_214168_($$1, $$2, p_9889_.m_134284_(), this.f_9743_.f_19853_.m_151558_(), p_9889_.m_237987_());
                this.f_9743_.f_8906_.m_215201_(p_9889_.m_237987_());
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player action");
    }

    private static boolean m_9790_(ServerPlayer p_9791_, ItemStack p_9792_) {
        if (p_9792_.m_41619_()) {
            return false;
        }
        Item $$2 = p_9792_.m_41720_();
        return ($$2 instanceof BlockItem || $$2 instanceof BucketItem) && !p_9791_.m_36335_().m_41519_($$2);
    }

    @Override
    public void m_6371_(ServerboundUseItemOnPacket p_9930_) {
        PacketUtils.m_131359_(p_9930_, this, this.f_9743_.m_9236_());
        this.f_9743_.f_8906_.m_215201_(p_9930_.m_238008_());
        ServerLevel $$1 = this.f_9743_.m_9236_();
        InteractionHand $$2 = p_9930_.m_134703_();
        ItemStack $$3 = this.f_9743_.m_21120_($$2);
        BlockHitResult $$4 = p_9930_.m_134706_();
        Vec3 $$5 = $$4.m_82450_();
        BlockPos $$6 = $$4.m_82425_();
        Vec3 $$7 = Vec3.m_82512_($$6);
        if (this.f_9743_.m_146892_().m_82557_($$7) > f_215198_) {
            return;
        }
        Vec3 $$8 = $$5.m_82546_($$7);
        double $$9 = 1.0000001;
        if (!(Math.abs($$8.m_7096_()) < 1.0000001 && Math.abs($$8.m_7098_()) < 1.0000001 && Math.abs($$8.m_7094_()) < 1.0000001)) {
            f_9744_.warn("Rejecting UseItemOnPacket from {}: Location {} too far away from hit block {}.", new Object[]{this.f_9743_.m_36316_().getName(), $$5, $$6});
            return;
        }
        Direction $$10 = $$4.m_82434_();
        this.f_9743_.m_9243_();
        int $$11 = this.f_9743_.f_19853_.m_151558_();
        if ($$6.m_123342_() < $$11) {
            if (this.f_9766_ == null && this.f_9743_.m_20275_((double)$$6.m_123341_() + 0.5, (double)$$6.m_123342_() + 0.5, (double)$$6.m_123343_() + 0.5) < 64.0 && $$1.m_7966_(this.f_9743_, $$6)) {
                InteractionResult $$12 = this.f_9743_.f_8941_.m_7179_(this.f_9743_, $$1, $$3, $$2, $$4);
                if ($$10 == Direction.UP && !$$12.m_19077_() && $$6.m_123342_() >= $$11 - 1 && ServerGamePacketListenerImpl.m_9790_(this.f_9743_, $$3)) {
                    MutableComponent $$13 = Component.m_237110_("build.tooHigh", $$11 - 1).m_130940_(ChatFormatting.RED);
                    this.f_9743_.m_240418_($$13, true);
                } else if ($$12.m_19080_()) {
                    this.f_9743_.m_21011_($$2, true);
                }
            }
        } else {
            MutableComponent $$14 = Component.m_237110_("build.tooHigh", $$11 - 1).m_130940_(ChatFormatting.RED);
            this.f_9743_.m_240418_($$14, true);
        }
        this.f_9743_.f_8906_.m_9829_(new ClientboundBlockUpdatePacket($$1, $$6));
        this.f_9743_.f_8906_.m_9829_(new ClientboundBlockUpdatePacket($$1, $$6.m_121945_($$10)));
    }

    @Override
    public void m_5760_(ServerboundUseItemPacket p_9932_) {
        PacketUtils.m_131359_(p_9932_, this, this.f_9743_.m_9236_());
        this.m_215201_(p_9932_.m_238013_());
        ServerLevel $$1 = this.f_9743_.m_9236_();
        InteractionHand $$2 = p_9932_.m_134717_();
        ItemStack $$3 = this.f_9743_.m_21120_($$2);
        this.f_9743_.m_9243_();
        if ($$3.m_41619_()) {
            return;
        }
        InteractionResult $$4 = this.f_9743_.f_8941_.m_6261_(this.f_9743_, $$1, $$3, $$2);
        if ($$4.m_19080_()) {
            this.f_9743_.m_21011_($$2, true);
        }
    }

    @Override
    public void m_6936_(ServerboundTeleportToEntityPacket p_9928_) {
        PacketUtils.m_131359_(p_9928_, this, this.f_9743_.m_9236_());
        if (this.f_9743_.m_5833_()) {
            for (ServerLevel $$1 : this.f_9745_.m_129785_()) {
                Entity $$2 = p_9928_.m_134681_($$1);
                if ($$2 == null) continue;
                this.f_9743_.m_8999_($$1, $$2.m_20185_(), $$2.m_20186_(), $$2.m_20189_(), $$2.m_146908_(), $$2.m_146909_());
                return;
            }
        }
    }

    @Override
    public void m_7529_(ServerboundResourcePackPacket p_9901_) {
        PacketUtils.m_131359_(p_9901_, this, this.f_9743_.m_9236_());
        if (p_9901_.m_179741_() == ServerboundResourcePackPacket.Action.DECLINED && this.f_9745_.m_142205_()) {
            f_9744_.info("Disconnecting {} due to resource pack rejection", (Object)this.f_9743_.m_7755_());
            this.m_9942_(Component.m_237115_("multiplayer.requiredTexturePrompt.disconnect"));
        }
    }

    @Override
    public void m_5938_(ServerboundPaddleBoatPacket p_9878_) {
        PacketUtils.m_131359_(p_9878_, this, this.f_9743_.m_9236_());
        Entity $$1 = this.f_9743_.m_20202_();
        if ($$1 instanceof Boat) {
            ((Boat)$$1).m_38339_(p_9878_.m_134218_(), p_9878_.m_134221_());
        }
    }

    @Override
    public void m_142110_(ServerboundPongPacket p_143652_) {
    }

    @Override
    public void m_7026_(Component p_9825_) {
        f_9744_.info("{} lost connection: {}", (Object)this.f_9743_.m_7755_().getString(), (Object)p_9825_.getString());
        this.f_9745_.m_129929_();
        this.f_9745_.m_6846_().m_240416_(Component.m_237110_("multiplayer.player.left", this.f_9743_.m_5446_()).m_130940_(ChatFormatting.YELLOW), false);
        this.f_9743_.m_9231_();
        this.f_9745_.m_6846_().m_11286_(this.f_9743_);
        this.f_9743_.m_8967_().m_7670_();
        if (this.m_9956_()) {
            f_9744_.info("Stopping singleplayer server as player logged out");
            this.f_9745_.m_7570_(false);
        }
    }

    public void m_215201_(int p_215202_) {
        if (p_215202_ < 0) {
            throw new IllegalArgumentException("Expected packet sequence nr >= 0");
        }
        this.f_215200_ = Math.max(p_215202_, this.f_215200_);
    }

    @Override
    public void m_9829_(Packet<?> p_9830_) {
        this.m_243119_(p_9830_, null);
    }

    public void m_243119_(Packet<?> p_243227_, @Nullable PacketSendListener p_243273_) {
        try {
            this.f_9742_.m_243124_(p_243227_, p_243273_);
        }
        catch (Throwable $$2) {
            CrashReport $$3 = CrashReport.m_127521_($$2, "Sending packet");
            CrashReportCategory $$4 = $$3.m_127514_("Packet being sent");
            $$4.m_128165_("Packet class", () -> p_243227_.getClass().getCanonicalName());
            throw new ReportedException($$3);
        }
    }

    @Override
    public void m_7798_(ServerboundSetCarriedItemPacket p_9909_) {
        PacketUtils.m_131359_(p_9909_, this, this.f_9743_.m_9236_());
        if (p_9909_.m_134498_() < 0 || p_9909_.m_134498_() >= Inventory.m_36059_()) {
            f_9744_.warn("{} tried to set an invalid carried item", (Object)this.f_9743_.m_7755_().getString());
            return;
        }
        if (this.f_9743_.m_150109_().f_35977_ != p_9909_.m_134498_() && this.f_9743_.m_7655_() == InteractionHand.MAIN_HAND) {
            this.f_9743_.m_5810_();
        }
        this.f_9743_.m_150109_().f_35977_ = p_9909_.m_134498_();
        this.f_9743_.m_9243_();
    }

    @Override
    public void m_7388_(ServerboundChatPacket p_9841_) {
        if (ServerGamePacketListenerImpl.m_215214_(p_9841_.f_133827_())) {
            this.m_9942_(Component.m_237115_("multiplayer.disconnect.illegal_characters"));
            return;
        }
        if (this.m_241937_(p_9841_.f_133827_(), p_9841_.f_237950_(), p_9841_.f_241662_())) {
            this.f_9745_.m_18707_(() -> {
                PlayerChatMessage $$1 = this.m_242585_(p_9841_);
                if (!this.m_242598_($$1)) {
                    return;
                }
                this.f_241681_.m_241849_(() -> {
                    CompletableFuture<FilteredText> $$1 = this.m_243132_($$1.m_241775_().f_241656_());
                    CompletableFuture<PlayerChatMessage> $$2 = this.f_9745_.m_236742_().m_243107_(this.f_9743_, $$1);
                    return CompletableFuture.allOf($$1, $$2).thenAcceptAsync(p_243197_ -> {
                        FilterMask $$3 = ((FilteredText)$$1.join()).f_243010_();
                        PlayerChatMessage $$4 = ((PlayerChatMessage)$$2.join()).m_243072_($$3);
                        this.m_243086_($$4);
                    }, (Executor)this.f_9745_);
                });
            });
        }
    }

    @Override
    public void m_214047_(ServerboundChatCommandPacket p_215225_) {
        if (ServerGamePacketListenerImpl.m_215214_(p_215225_.f_237922_())) {
            this.m_9942_(Component.m_237115_("multiplayer.disconnect.illegal_characters"));
            return;
        }
        if (this.m_241937_(p_215225_.f_237922_(), p_215225_.f_237923_(), p_215225_.f_241638_())) {
            this.f_9745_.m_18707_(() -> {
                this.m_242600_(p_215225_);
                this.m_215251_();
            });
        }
    }

    private void m_242600_(ServerboundChatCommandPacket p_242859_) {
        ParseResults<CommandSourceStack> $$1 = this.m_242658_(p_242859_.f_237922_());
        Map<String, PlayerChatMessage> $$2 = this.m_242672_(p_242859_, PreviewableCommand.m_242644_($$1));
        for (PlayerChatMessage $$3 : $$2.values()) {
            if (this.m_242598_($$3)) continue;
            return;
        }
        CommandSigningContext.SignedArguments $$4 = new CommandSigningContext.SignedArguments($$2);
        $$1 = Commands.m_242611_($$1, p_242749_ -> p_242749_.m_230893_($$4));
        this.f_9745_.m_129892_().m_242674_($$1, p_242859_.f_237922_());
    }

    private Map<String, PlayerChatMessage> m_242672_(ServerboundChatCommandPacket p_242876_, PreviewableCommand<?> p_242848_) {
        Component $$2 = this.f_242491_.m_242657_(p_242876_.f_237922_());
        MessageSigner $$3 = new MessageSigner(this.f_9743_.m_20148_(), p_242876_.f_237923_(), p_242876_.f_240858_());
        LastSeenMessages $$4 = p_242876_.f_241638_().f_241678_();
        Object2ObjectOpenHashMap $$5 = new Object2ObjectOpenHashMap();
        SignedMessageChain.Decoder $$6 = this.f_9743_.f_8906_.m_241133_();
        for (Pair<String, String> $$7 : ArgumentSignatures.m_242641_(p_242848_)) {
            ChatMessageContent $$12;
            String $$8 = (String)$$7.getFirst();
            String $$9 = (String)$$7.getSecond();
            MessageSignature $$10 = p_242876_.f_237924_().m_240943_($$8);
            if (p_242876_.f_237925_() && $$2 != null) {
                ChatMessageContent $$11 = new ChatMessageContent($$9, $$2);
            } else {
                $$12 = new ChatMessageContent($$9);
            }
            SignedMessageChain.Link $$13 = new SignedMessageChain.Link($$10);
            $$5.put($$8, $$6.m_240945_($$13, $$3, $$12, $$4));
        }
        return $$5;
    }

    private ParseResults<CommandSourceStack> m_242658_(String p_242938_) {
        CommandDispatcher<CommandSourceStack> $$1 = this.f_9745_.m_129892_().m_82094_();
        return $$1.parse(p_242938_, (Object)this.f_9743_.m_20203_());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private boolean m_241937_(String p_242372_, Instant p_242311_, LastSeenMessages.Update p_242217_) {
        void $$4;
        if (!this.m_215236_(p_242311_)) {
            f_9744_.warn("{} sent out-of-order chat: '{}'", (Object)this.f_9743_.m_7755_().getString(), (Object)p_242372_);
            this.m_9942_(Component.m_237115_("multiplayer.disconnect.out_of_order_chat"));
            return false;
        }
        if (this.f_9743_.m_9241_() == ChatVisiblity.HIDDEN) {
            this.m_9829_(new ClientboundSystemChatPacket(Component.m_237115_("chat.disabled.options").m_130940_(ChatFormatting.RED), false));
            return false;
        }
        LastSeenMessagesValidator lastSeenMessagesValidator = this.f_241654_;
        synchronized (lastSeenMessagesValidator) {
            Set<LastSeenMessagesValidator.ErrorCondition> $$3 = this.f_241654_.m_241869_(p_242217_);
        }
        if (!$$4.isEmpty()) {
            this.m_241914_((Set<LastSeenMessagesValidator.ErrorCondition>)$$4);
            return false;
        }
        this.f_9743_.m_9243_();
        return true;
    }

    private boolean m_215236_(Instant p_215237_) {
        Instant $$1;
        do {
            if (!p_215237_.isBefore($$1 = this.f_215197_.get())) continue;
            return false;
        } while (!this.f_215197_.compareAndSet($$1, p_215237_));
        return true;
    }

    private static boolean m_215214_(String p_215215_) {
        for (int $$1 = 0; $$1 < p_215215_.length(); ++$$1) {
            if (SharedConstants.m_136188_(p_215215_.charAt($$1))) continue;
            return true;
        }
        return false;
    }

    private PlayerChatMessage m_242585_(ServerboundChatPacket p_242875_) {
        MessageSigner $$1 = p_242875_.m_240947_(this.f_9743_);
        SignedMessageChain.Link $$2 = new SignedMessageChain.Link(p_242875_.f_240898_());
        LastSeenMessages $$3 = p_242875_.f_241662_().f_241678_();
        ChatMessageContent $$4 = this.m_242671_(p_242875_);
        return this.f_240889_.m_240945_($$2, $$1, $$4, $$3);
    }

    private ChatMessageContent m_242671_(ServerboundChatPacket p_242906_) {
        Component $$1 = this.f_242491_.m_242657_(p_242906_.f_133827_());
        if (p_242906_.f_237952_() && $$1 != null) {
            return new ChatMessageContent(p_242906_.f_133827_(), $$1);
        }
        return new ChatMessageContent(p_242906_.f_133827_());
    }

    private void m_243086_(PlayerChatMessage p_243277_) {
        this.f_9745_.m_6846_().m_243049_(p_243277_, this.f_9743_, ChatType.m_240980_(ChatType.f_130598_, this.f_9743_));
        this.m_215251_();
    }

    private boolean m_242598_(PlayerChatMessage p_242942_) {
        ChatSender $$1 = this.f_9743_.m_217000_();
        ProfilePublicKey $$2 = $$1.f_240874_();
        if ($$2 != null) {
            if ($$2.f_219781_().m_219810_()) {
                this.f_9743_.m_213846_(Component.m_237115_("chat.disabled.expiredProfileKey").m_130940_(ChatFormatting.RED));
                return false;
            }
            if (!p_242942_.m_241006_($$1)) {
                this.m_9942_(Component.m_237115_("multiplayer.disconnect.unsigned_chat"));
                return false;
            }
        }
        if (p_242942_.m_240431_(Instant.now())) {
            f_9744_.warn("{} sent expired chat: '{}'. Is the client/server system time unsynchronized?", (Object)this.f_9743_.m_7755_().getString(), (Object)p_242942_.m_241775_().f_241656_());
        }
        return true;
    }

    private void m_215251_() {
        this.f_9750_ += 20;
        if (this.f_9750_ > 200 && !this.f_9745_.m_6846_().m_11303_(this.f_9743_.m_36316_())) {
            this.m_9942_(Component.m_237115_("disconnect.spam"));
        }
    }

    @Override
    public void m_213866_(ServerboundChatPreviewPacket p_215230_) {
        if (this.m_241863_()) {
            this.f_215196_.m_236976_(() -> {
                int $$1 = p_215230_.f_237962_();
                String $$2 = p_215230_.f_237963_();
                return this.m_215234_($$2).thenAccept(p_238195_ -> this.m_238388_($$1, (Component)p_238195_));
            });
        }
    }

    private boolean m_241863_() {
        return this.f_9745_.m_214000_() || this.f_9742_.m_129531_();
    }

    private void m_238388_(int p_238389_, Component p_238390_) {
        this.m_243119_(new ClientboundChatPreviewPacket(p_238389_, p_238390_), PacketSendListener.m_243073_(() -> new ClientboundChatPreviewPacket(p_238389_, null)));
    }

    private CompletableFuture<Component> m_215234_(String p_215235_) {
        String $$1 = StringUtils.normalizeSpace((String)p_215235_);
        if ($$1.startsWith("/")) {
            return this.m_215248_($$1.substring(1));
        }
        return this.m_215246_(p_215235_);
    }

    private CompletableFuture<Component> m_215246_(String p_215247_) {
        MutableComponent $$1 = Component.m_237113_(p_215247_);
        CompletionStage $$2 = this.f_9745_.m_236742_().m_236961_(this.f_9743_, $$1).thenApply(p_238202_ -> !$$1.equals(p_238202_) ? p_238202_ : null);
        ((CompletableFuture)$$2).thenAcceptAsync(p_242747_ -> this.f_242491_.m_242647_(p_215247_, (Component)p_242747_), (Executor)this.f_9745_);
        return $$2;
    }

    private CompletableFuture<Component> m_215248_(String p_215249_) {
        CommandSourceStack $$1 = this.f_9743_.m_20203_();
        ParseResults $$2 = this.f_9745_.m_129892_().m_82094_().parse(p_215249_, (Object)$$1);
        CompletableFuture<Component> $$3 = this.m_242589_($$1, PreviewableCommand.m_242644_($$2));
        $$3.thenAcceptAsync(p_242743_ -> this.f_242491_.m_242647_(p_215249_, (Component)p_242743_), (Executor)this.f_9745_);
        return $$3;
    }

    private CompletableFuture<Component> m_242589_(CommandSourceStack p_242862_, PreviewableCommand<CommandSourceStack> p_242851_) {
        List<PreviewableCommand.Argument<CommandSourceStack>> $$2 = p_242851_.f_242495_();
        if ($$2.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        for (int $$3 = $$2.size() - 1; $$3 >= 0; --$$3) {
            PreviewableCommand.Argument<CommandSourceStack> $$4 = $$2.get($$3);
            try {
                CompletableFuture<Component> $$5 = $$4.f_242481_().m_242586_(p_242862_, $$4.f_242493_());
                if ($$5 == null) continue;
                return $$5;
            }
            catch (CommandSyntaxException $$6) {
                return CompletableFuture.completedFuture(null);
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @Override
    public void m_241885_(ServerboundChatAckPacket p_242387_) {
        void $$2;
        LastSeenMessagesValidator lastSeenMessagesValidator = this.f_241654_;
        synchronized (lastSeenMessagesValidator) {
            Set<LastSeenMessagesValidator.ErrorCondition> $$1 = this.f_241654_.m_241869_(p_242387_.f_241632_());
        }
        if (!$$2.isEmpty()) {
            this.m_241914_((Set<LastSeenMessagesValidator.ErrorCondition>)$$2);
        }
    }

    private void m_241914_(Set<LastSeenMessagesValidator.ErrorCondition> p_242382_) {
        f_9744_.warn("Failed to validate message from {}, reasons: {}", (Object)this.f_9743_.m_7755_().getString(), (Object)p_242382_.stream().map(LastSeenMessagesValidator.ErrorCondition::m_241951_).collect(Collectors.joining(",")));
        this.m_9942_(Component.m_237115_("multiplayer.disconnect.chat_validation_failed"));
    }

    @Override
    public void m_7953_(ServerboundSwingPacket p_9926_) {
        PacketUtils.m_131359_(p_9926_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9243_();
        this.f_9743_.m_6674_(p_9926_.m_134674_());
    }

    @Override
    public void m_5681_(ServerboundPlayerCommandPacket p_9891_) {
        PacketUtils.m_131359_(p_9891_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9243_();
        switch (p_9891_.m_134320_()) {
            case PRESS_SHIFT_KEY: {
                this.f_9743_.m_20260_(true);
                break;
            }
            case RELEASE_SHIFT_KEY: {
                this.f_9743_.m_20260_(false);
                break;
            }
            case START_SPRINTING: {
                this.f_9743_.m_6858_(true);
                break;
            }
            case STOP_SPRINTING: {
                this.f_9743_.m_6858_(false);
                break;
            }
            case STOP_SLEEPING: {
                if (!this.f_9743_.m_5803_()) break;
                this.f_9743_.m_6145_(false, true);
                this.f_9766_ = this.f_9743_.m_20182_();
                break;
            }
            case START_RIDING_JUMP: {
                if (!(this.f_9743_.m_20202_() instanceof PlayerRideableJumping)) break;
                PlayerRideableJumping $$1 = (PlayerRideableJumping)((Object)this.f_9743_.m_20202_());
                int $$2 = p_9891_.m_134321_();
                if (!$$1.m_7132_() || $$2 <= 0) break;
                $$1.m_7199_($$2);
                break;
            }
            case STOP_RIDING_JUMP: {
                if (!(this.f_9743_.m_20202_() instanceof PlayerRideableJumping)) break;
                PlayerRideableJumping $$3 = (PlayerRideableJumping)((Object)this.f_9743_.m_20202_());
                $$3.m_8012_();
                break;
            }
            case OPEN_INVENTORY: {
                Entity entity = this.f_9743_.m_20202_();
                if (!(entity instanceof HasCustomInventoryScreen)) break;
                HasCustomInventoryScreen $$4 = (HasCustomInventoryScreen)((Object)entity);
                $$4.m_213583_(this.f_9743_);
                break;
            }
            case START_FALL_FLYING: {
                if (this.f_9743_.m_36319_()) break;
                this.f_9743_.m_36321_();
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid client command!");
            }
        }
    }

    public SignedMessageChain.Decoder m_241133_() {
        return this.f_240889_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void m_241992_(PlayerChatMessage p_242439_) {
        void $$3;
        LastSeenMessages.Entry $$1 = p_242439_.m_241960_();
        if ($$1 == null) {
            return;
        }
        LastSeenMessagesValidator lastSeenMessagesValidator = this.f_241654_;
        synchronized (lastSeenMessagesValidator) {
            this.f_241654_.m_241796_($$1);
            int $$2 = this.f_241654_.m_241920_();
        }
        if ($$3 > 4096) {
            this.m_9942_(Component.m_237115_("multiplayer.disconnect.too_many_pending_chats"));
        }
    }

    @Override
    public void m_6946_(ServerboundInteractPacket p_9866_) {
        PacketUtils.m_131359_(p_9866_, this, this.f_9743_.m_9236_());
        ServerLevel $$1 = this.f_9743_.m_9236_();
        final Entity $$2 = p_9866_.m_179603_($$1);
        this.f_9743_.m_9243_();
        this.f_9743_.m_20260_(p_9866_.m_134061_());
        if ($$2 != null) {
            if (!$$1.m_6857_().m_61937_($$2.m_20183_())) {
                return;
            }
            if ($$2.m_20238_(this.f_9743_.m_146892_()) < f_215198_) {
                p_9866_.m_179617_(new ServerboundInteractPacket.Handler(){

                    private void m_143678_(InteractionHand p_143679_, EntityInteraction p_143680_) {
                        ItemStack $$22 = ServerGamePacketListenerImpl.this.f_9743_.m_21120_(p_143679_).m_41777_();
                        InteractionResult $$3 = p_143680_.m_143694_(ServerGamePacketListenerImpl.this.f_9743_, $$2, p_143679_);
                        if ($$3.m_19077_()) {
                            CriteriaTriggers.f_10565_.m_61494_(ServerGamePacketListenerImpl.this.f_9743_, $$22, $$2);
                            if ($$3.m_19080_()) {
                                ServerGamePacketListenerImpl.this.f_9743_.m_21011_(p_143679_, true);
                            }
                        }
                    }

                    @Override
                    public void m_142299_(InteractionHand p_143677_) {
                        this.m_143678_(p_143677_, Player::m_36157_);
                    }

                    @Override
                    public void m_142143_(InteractionHand p_143682_, Vec3 p_143683_) {
                        this.m_143678_(p_143682_, (p_143686_, p_143687_, p_143688_) -> p_143687_.m_7111_(p_143686_, p_143683_, p_143688_));
                    }

                    @Override
                    public void m_141994_() {
                        if ($$2 instanceof ItemEntity || $$2 instanceof ExperienceOrb || $$2 instanceof AbstractArrow || $$2 == ServerGamePacketListenerImpl.this.f_9743_) {
                            ServerGamePacketListenerImpl.this.m_9942_(Component.m_237115_("multiplayer.disconnect.invalid_entity_attacked"));
                            f_9744_.warn("Player {} tried to attack an invalid entity", (Object)ServerGamePacketListenerImpl.this.f_9743_.m_7755_().getString());
                            return;
                        }
                        ServerGamePacketListenerImpl.this.f_9743_.m_5706_($$2);
                    }
                });
            }
        }
    }

    @Override
    public void m_6272_(ServerboundClientCommandPacket p_9843_) {
        PacketUtils.m_131359_(p_9843_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9243_();
        ServerboundClientCommandPacket.Action $$1 = p_9843_.m_133850_();
        switch ($$1) {
            case PERFORM_RESPAWN: {
                if (this.f_9743_.f_8944_) {
                    this.f_9743_.f_8944_ = false;
                    this.f_9743_ = this.f_9745_.m_6846_().m_11236_(this.f_9743_, true);
                    CriteriaTriggers.f_10588_.m_19757_(this.f_9743_, Level.f_46430_, Level.f_46428_);
                    break;
                }
                if (this.f_9743_.m_21223_() > 0.0f) {
                    return;
                }
                this.f_9743_ = this.f_9745_.m_6846_().m_11236_(this.f_9743_, false);
                if (!this.f_9745_.m_7035_()) break;
                this.f_9743_.m_143403_(GameType.SPECTATOR);
                this.f_9743_.m_9236_().m_46469_().m_46170_(GameRules.f_46146_).m_46246_(false, this.f_9745_);
                break;
            }
            case REQUEST_STATS: {
                this.f_9743_.m_8951_().m_12819_(this.f_9743_);
            }
        }
    }

    @Override
    public void m_7951_(ServerboundContainerClosePacket p_9858_) {
        PacketUtils.m_131359_(p_9858_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9230_();
    }

    @Override
    public void m_5914_(ServerboundContainerClickPacket p_9856_) {
        PacketUtils.m_131359_(p_9856_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9243_();
        if (this.f_9743_.f_36096_.f_38840_ != p_9856_.m_133959_()) {
            return;
        }
        if (this.f_9743_.m_5833_()) {
            this.f_9743_.f_36096_.m_150429_();
            return;
        }
        if (!this.f_9743_.f_36096_.m_6875_(this.f_9743_)) {
            f_9744_.debug("Player {} interacted with invalid menu {}", (Object)this.f_9743_, (Object)this.f_9743_.f_36096_);
            return;
        }
        int $$1 = p_9856_.m_133962_();
        if (!this.f_9743_.f_36096_.m_207775_($$1)) {
            f_9744_.debug("Player {} clicked invalid slot index: {}, available slots: {}", new Object[]{this.f_9743_.m_7755_(), $$1, this.f_9743_.f_36096_.f_38839_.size()});
            return;
        }
        boolean $$2 = p_9856_.m_182741_() != this.f_9743_.f_36096_.m_182424_();
        this.f_9743_.f_36096_.m_150443_();
        this.f_9743_.f_36096_.m_150399_($$1, p_9856_.m_133963_(), p_9856_.m_133966_(), this.f_9743_);
        for (Int2ObjectMap.Entry $$3 : Int2ObjectMaps.fastIterable(p_9856_.m_179582_())) {
            this.f_9743_.f_36096_.m_182414_($$3.getIntKey(), (ItemStack)$$3.getValue());
        }
        this.f_9743_.f_36096_.m_150422_(p_9856_.m_179581_());
        this.f_9743_.f_36096_.m_150444_();
        if ($$2) {
            this.f_9743_.f_36096_.m_182423_();
        } else {
            this.f_9743_.f_36096_.m_38946_();
        }
    }

    @Override
    public void m_7191_(ServerboundPlaceRecipePacket p_9882_) {
        PacketUtils.m_131359_(p_9882_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9243_();
        if (this.f_9743_.m_5833_() || this.f_9743_.f_36096_.f_38840_ != p_9882_.m_134249_() || !(this.f_9743_.f_36096_ instanceof RecipeBookMenu)) {
            return;
        }
        if (!this.f_9743_.f_36096_.m_6875_(this.f_9743_)) {
            f_9744_.debug("Player {} interacted with invalid menu {}", (Object)this.f_9743_, (Object)this.f_9743_.f_36096_);
            return;
        }
        this.f_9745_.m_129894_().m_44043_(p_9882_.m_134252_()).ifPresent(p_238200_ -> ((RecipeBookMenu)this.f_9743_.f_36096_).m_6951_(p_9882_.m_134253_(), (Recipe<?>)p_238200_, this.f_9743_));
    }

    @Override
    public void m_6557_(ServerboundContainerButtonClickPacket p_9854_) {
        PacketUtils.m_131359_(p_9854_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9243_();
        if (this.f_9743_.f_36096_.f_38840_ != p_9854_.m_133935_() || this.f_9743_.m_5833_()) {
            return;
        }
        if (!this.f_9743_.f_36096_.m_6875_(this.f_9743_)) {
            f_9744_.debug("Player {} interacted with invalid menu {}", (Object)this.f_9743_, (Object)this.f_9743_.f_36096_);
            return;
        }
        boolean $$1 = this.f_9743_.f_36096_.m_6366_(this.f_9743_, p_9854_.m_133938_());
        if ($$1) {
            this.f_9743_.f_36096_.m_38946_();
        }
    }

    @Override
    public void m_5964_(ServerboundSetCreativeModeSlotPacket p_9915_) {
        PacketUtils.m_131359_(p_9915_, this, this.f_9743_.m_9236_());
        if (this.f_9743_.f_8941_.m_9295_()) {
            boolean $$7;
            BlockEntity $$5;
            BlockPos $$4;
            boolean $$1 = p_9915_.m_134561_() < 0;
            ItemStack $$2 = p_9915_.m_134564_();
            CompoundTag $$3 = BlockItem.m_186336_($$2);
            if (!$$2.m_41619_() && $$3 != null && $$3.m_128441_("x") && $$3.m_128441_("y") && $$3.m_128441_("z") && this.f_9743_.f_19853_.m_46749_($$4 = BlockEntity.m_187472_($$3)) && ($$5 = this.f_9743_.f_19853_.m_7702_($$4)) != null) {
                $$5.m_187476_($$2);
            }
            boolean $$6 = p_9915_.m_134561_() >= 1 && p_9915_.m_134561_() <= 45;
            boolean bl = $$7 = $$2.m_41619_() || $$2.m_41773_() >= 0 && $$2.m_41613_() <= 64 && !$$2.m_41619_();
            if ($$6 && $$7) {
                this.f_9743_.f_36095_.m_38853_(p_9915_.m_134561_()).m_5852_($$2);
                this.f_9743_.f_36095_.m_38946_();
            } else if ($$1 && $$7 && this.f_9751_ < 200) {
                this.f_9751_ += 20;
                this.f_9743_.m_36176_($$2, true);
            }
        }
    }

    @Override
    public void m_5527_(ServerboundSignUpdatePacket p_9921_) {
        List<String> $$1 = Stream.of(p_9921_.m_134663_()).map(ChatFormatting::m_126649_).collect(Collectors.toList());
        this.m_243065_($$1).thenAcceptAsync(p_215245_ -> this.m_9922_(p_9921_, (List<FilteredText>)p_215245_), (Executor)this.f_9745_);
    }

    private void m_9922_(ServerboundSignUpdatePacket p_9923_, List<FilteredText> p_9924_) {
        this.f_9743_.m_9243_();
        ServerLevel $$2 = this.f_9743_.m_9236_();
        BlockPos $$3 = p_9923_.m_134660_();
        if ($$2.m_46805_($$3)) {
            BlockState $$4 = $$2.m_8055_($$3);
            BlockEntity $$5 = $$2.m_7702_($$3);
            if (!($$5 instanceof SignBlockEntity)) {
                return;
            }
            SignBlockEntity $$6 = (SignBlockEntity)$$5;
            if (!$$6.m_59751_() || !this.f_9743_.m_20148_().equals($$6.m_155726_())) {
                f_9744_.warn("Player {} just tried to change non-editable sign", (Object)this.f_9743_.m_7755_().getString());
                return;
            }
            for (int $$7 = 0; $$7 < p_9924_.size(); ++$$7) {
                FilteredText $$8 = p_9924_.get($$7);
                if (this.f_9743_.m_143387_()) {
                    $$6.m_59732_($$7, Component.m_237113_($$8.m_243113_()));
                    continue;
                }
                $$6.m_155702_($$7, Component.m_237113_($$8.f_215168_()), Component.m_237113_($$8.m_243113_()));
            }
            $$6.m_6596_();
            $$2.m_7260_($$3, $$4, $$4, 3);
        }
    }

    @Override
    public void m_5683_(ServerboundKeepAlivePacket p_9870_) {
        if (this.f_9748_ && p_9870_.m_134102_() == this.f_9749_) {
            int $$1 = (int)(Util.m_137550_() - this.f_9747_);
            this.f_9743_.f_8943_ = (this.f_9743_.f_8943_ * 3 + $$1) / 4;
            this.f_9748_ = false;
        } else if (!this.m_9956_()) {
            this.m_9942_(Component.m_237115_("disconnect.timeout"));
        }
    }

    @Override
    public void m_6828_(ServerboundPlayerAbilitiesPacket p_9887_) {
        PacketUtils.m_131359_(p_9887_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_150110_().f_35935_ = p_9887_.m_134264_() && this.f_9743_.m_150110_().f_35936_;
    }

    @Override
    public void m_5617_(ServerboundClientInformationPacket p_9845_) {
        PacketUtils.m_131359_(p_9845_, this, this.f_9743_.m_9236_());
        this.f_9743_.m_9156_(p_9845_);
    }

    @Override
    public void m_7423_(ServerboundCustomPayloadPacket p_9860_) {
    }

    @Override
    public void m_7477_(ServerboundChangeDifficultyPacket p_9839_) {
        PacketUtils.m_131359_(p_9839_, this, this.f_9743_.m_9236_());
        if (!this.f_9743_.m_20310_(2) && !this.m_9956_()) {
            return;
        }
        this.f_9745_.m_129827_(p_9839_.m_133824_(), false);
    }

    @Override
    public void m_7728_(ServerboundLockDifficultyPacket p_9872_) {
        PacketUtils.m_131359_(p_9872_, this, this.f_9743_.m_9236_());
        if (!this.f_9743_.m_20310_(2) && !this.m_9956_()) {
            return;
        }
        this.f_9745_.m_129958_(p_9872_.m_134115_());
    }

    @Override
    public ServerPlayer m_142253_() {
        return this.f_9743_;
    }

    @FunctionalInterface
    static interface EntityInteraction {
        public InteractionResult m_143694_(ServerPlayer var1, Entity var2, InteractionHand var3);
    }
}

