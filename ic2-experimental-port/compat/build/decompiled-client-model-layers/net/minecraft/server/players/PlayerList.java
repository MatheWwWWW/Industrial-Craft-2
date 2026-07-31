/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.FileUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.OutgoingPlayerChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.IpBanList;
import net.minecraft.server.players.IpBanListEntry;
import net.minecraft.server.players.ServerOpList;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.slf4j.Logger;

public abstract class PlayerList {
    public static final File f_11189_ = new File("banned-players.json");
    public static final File f_11190_ = new File("banned-ips.json");
    public static final File f_11191_ = new File("ops.json");
    public static final File f_11192_ = new File("whitelist.json");
    public static final Component f_243017_ = Component.m_237115_("chat.filtered_full");
    private static final Logger f_11188_ = LogUtils.getLogger();
    private static final int f_143987_ = 600;
    private static final SimpleDateFormat f_11194_ = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private final MinecraftServer f_11195_;
    private final List<ServerPlayer> f_11196_ = Lists.newArrayList();
    private final Map<UUID, ServerPlayer> f_11197_ = Maps.newHashMap();
    private final UserBanList f_11198_ = new UserBanList(f_11189_);
    private final IpBanList f_11199_ = new IpBanList(f_11190_);
    private final ServerOpList f_11200_ = new ServerOpList(f_11191_);
    private final UserWhiteList f_11201_ = new UserWhiteList(f_11192_);
    private final Map<UUID, ServerStatsCounter> f_11202_ = Maps.newHashMap();
    private final Map<UUID, PlayerAdvancements> f_11203_ = Maps.newHashMap();
    private final PlayerDataStorage f_11204_;
    private boolean f_11205_;
    private final RegistryAccess.Frozen f_11206_;
    protected final int f_11193_;
    private int f_11207_;
    private int f_184208_;
    private boolean f_11209_;
    private static final boolean f_143988_ = false;
    private int f_11210_;

    public PlayerList(MinecraftServer p_203842_, RegistryAccess.Frozen p_203843_, PlayerDataStorage p_203844_, int p_203845_) {
        this.f_11195_ = p_203842_;
        this.f_11206_ = p_203843_;
        this.f_11193_ = p_203845_;
        this.f_11204_ = p_203844_;
    }

    public void m_11261_(Connection p_11262_, ServerPlayer p_11263_) {
        CompoundTag $$21;
        Entity $$22;
        MutableComponent $$18;
        ServerLevel $$10;
        GameProfile $$2 = p_11263_.m_36316_();
        GameProfileCache $$3 = this.f_11195_.m_129927_();
        Optional<GameProfile> $$4 = $$3.m_11002_($$2.getId());
        String $$5 = $$4.map(GameProfile::getName).orElse($$2.getName());
        $$3.m_10991_($$2);
        CompoundTag $$6 = this.m_11224_(p_11263_);
        ResourceKey<Level> $$7 = $$6 != null ? DimensionType.m_63911_(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)$$6.m_128423_("Dimension"))).resultOrPartial(arg_0 -> ((Logger)f_11188_).error(arg_0)).orElse(Level.f_46428_) : Level.f_46428_;
        ServerLevel $$8 = this.f_11195_.m_129880_($$7);
        if ($$8 == null) {
            f_11188_.warn("Unknown respawn dimension {}, defaulting to overworld", $$7);
            ServerLevel $$9 = this.f_11195_.m_129783_();
        } else {
            $$10 = $$8;
        }
        p_11263_.m_143425_($$10);
        String $$11 = "local";
        if (p_11262_.m_129523_() != null) {
            $$11 = p_11262_.m_129523_().toString();
        }
        f_11188_.info("{}[{}] logged in with entity id {} at ({}, {}, {})", new Object[]{p_11263_.m_7755_().getString(), $$11, p_11263_.m_19879_(), p_11263_.m_20185_(), p_11263_.m_20186_(), p_11263_.m_20189_()});
        LevelData $$12 = $$10.m_6106_();
        p_11263_.m_143427_($$6);
        ServerGamePacketListenerImpl $$13 = new ServerGamePacketListenerImpl(this.f_11195_, p_11262_, p_11263_);
        GameRules $$14 = $$10.m_46469_();
        boolean $$15 = $$14.m_46207_(GameRules.f_46156_);
        boolean $$16 = $$14.m_46207_(GameRules.f_46145_);
        $$13.m_9829_(new ClientboundLoginPacket(p_11263_.m_19879_(), $$12.m_5466_(), p_11263_.f_8941_.m_9290_(), p_11263_.f_8941_.m_9293_(), this.f_11195_.m_129784_(), this.f_11206_, $$10.m_220362_(), $$10.m_46472_(), BiomeManager.m_47877_($$10.m_7328_()), this.m_11310_(), this.f_11207_, this.f_184208_, $$16, !$$15, $$10.m_46659_(), $$10.m_8584_(), p_11263_.m_219759_()));
        $$13.m_9829_(new ClientboundCustomPayloadPacket(ClientboundCustomPayloadPacket.f_132012_, new FriendlyByteBuf(Unpooled.buffer()).m_130070_(this.m_7873_().m_130001_())));
        $$13.m_9829_(new ClientboundChangeDifficultyPacket($$12.m_5472_(), $$12.m_5474_()));
        $$13.m_9829_(new ClientboundPlayerAbilitiesPacket(p_11263_.m_150110_()));
        $$13.m_9829_(new ClientboundSetCarriedItemPacket(p_11263_.m_150109_().f_35977_));
        $$13.m_9829_(new ClientboundUpdateRecipesPacket(this.f_11195_.m_129894_().m_44051_()));
        $$13.m_9829_(new ClientboundUpdateTagsPacket(TagNetworkSerialization.m_203950_(this.f_11206_)));
        this.m_11289_(p_11263_);
        p_11263_.m_8951_().m_12850_();
        p_11263_.m_8952_().m_12789_(p_11263_);
        this.m_11273_($$10.m_6188_(), p_11263_);
        this.f_11195_.m_129929_();
        if (p_11263_.m_36316_().getName().equalsIgnoreCase($$5)) {
            MutableComponent $$17 = Component.m_237110_("multiplayer.player.joined", p_11263_.m_5446_());
        } else {
            $$18 = Component.m_237110_("multiplayer.player.joined.renamed", p_11263_.m_5446_(), $$5);
        }
        this.m_240416_($$18.m_130940_(ChatFormatting.YELLOW), false);
        $$13.m_9774_(p_11263_.m_20185_(), p_11263_.m_20186_(), p_11263_.m_20189_(), p_11263_.m_146908_(), p_11263_.m_146909_());
        this.f_11196_.add(p_11263_);
        this.f_11197_.put(p_11263_.m_20148_(), p_11263_);
        this.m_11268_(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, p_11263_));
        for (int $$19 = 0; $$19 < this.f_11196_.size(); ++$$19) {
            p_11263_.f_8906_.m_9829_(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, this.f_11196_.get($$19)));
        }
        $$10.m_8834_(p_11263_);
        this.f_11195_.m_129901_().m_136293_(p_11263_);
        this.m_11229_(p_11263_, $$10);
        this.f_11195_.m_214042_().ifPresent(p_215606_ -> p_11263_.m_143408_(p_215606_.f_236743_(), p_215606_.f_236744_(), p_215606_.f_236745_(), p_215606_.f_236746_()));
        p_11263_.m_215109_(this.f_11195_.m_129928_());
        for (MobEffectInstance $$20 : p_11263_.m_21220_()) {
            $$13.m_9829_(new ClientboundUpdateMobEffectPacket(p_11263_.m_19879_(), $$20));
        }
        if ($$6 != null && $$6.m_128425_("RootVehicle", 10) && ($$22 = EntityType.m_20645_(($$21 = $$6.m_128469_("RootVehicle")).m_128469_("Entity"), $$10, p_215603_ -> {
            if (!$$10.m_8847_((Entity)p_215603_)) {
                return null;
            }
            return p_215603_;
        })) != null) {
            Object $$24;
            if ($$21.m_128403_("Attach")) {
                UUID $$23 = $$21.m_128342_("Attach");
            } else {
                $$24 = null;
            }
            if ($$22.m_20148_().equals($$24)) {
                p_11263_.m_7998_($$22, true);
            } else {
                for (Entity $$25 : $$22.m_146897_()) {
                    if (!$$25.m_20148_().equals($$24)) continue;
                    p_11263_.m_7998_($$25, true);
                    break;
                }
            }
            if (!p_11263_.m_20159_()) {
                f_11188_.warn("Couldn't reattach entity to player");
                $$22.m_146870_();
                for (Entity $$26 : $$22.m_146897_()) {
                    $$26.m_146870_();
                }
            }
        }
        p_11263_.m_143429_();
    }

    protected void m_11273_(ServerScoreboard p_11274_, ServerPlayer p_11275_) {
        HashSet $$2 = Sets.newHashSet();
        for (PlayerTeam $$3 : p_11274_.m_83491_()) {
            p_11275_.f_8906_.m_9829_(ClientboundSetPlayerTeamPacket.m_179332_($$3, true));
        }
        for (int $$4 = 0; $$4 < 19; ++$$4) {
            Objective $$5 = p_11274_.m_83416_($$4);
            if ($$5 == null || $$2.contains($$5)) continue;
            List<Packet<?>> $$6 = p_11274_.m_136229_($$5);
            for (Packet<?> $$7 : $$6) {
                p_11275_.f_8906_.m_9829_($$7);
            }
            $$2.add($$5);
        }
    }

    public void m_184209_(ServerLevel p_184210_) {
        p_184210_.m_6857_().m_61929_(new BorderChangeListener(){

            @Override
            public void m_6312_(WorldBorder p_11321_, double p_11322_) {
                PlayerList.this.m_11268_(new ClientboundSetBorderSizePacket(p_11321_));
            }

            @Override
            public void m_6689_(WorldBorder p_11328_, double p_11329_, double p_11330_, long p_11331_) {
                PlayerList.this.m_11268_(new ClientboundSetBorderLerpSizePacket(p_11328_));
            }

            @Override
            public void m_7721_(WorldBorder p_11324_, double p_11325_, double p_11326_) {
                PlayerList.this.m_11268_(new ClientboundSetBorderCenterPacket(p_11324_));
            }

            @Override
            public void m_5904_(WorldBorder p_11333_, int p_11334_) {
                PlayerList.this.m_11268_(new ClientboundSetBorderWarningDelayPacket(p_11333_));
            }

            @Override
            public void m_5903_(WorldBorder p_11339_, int p_11340_) {
                PlayerList.this.m_11268_(new ClientboundSetBorderWarningDistancePacket(p_11339_));
            }

            @Override
            public void m_6315_(WorldBorder p_11336_, double p_11337_) {
            }

            @Override
            public void m_6313_(WorldBorder p_11342_, double p_11343_) {
            }
        });
    }

    @Nullable
    public CompoundTag m_11224_(ServerPlayer p_11225_) {
        CompoundTag $$3;
        CompoundTag $$1 = this.f_11195_.m_129910_().m_6614_();
        if (this.f_11195_.m_7779_(p_11225_.m_36316_()) && $$1 != null) {
            CompoundTag $$2 = $$1;
            p_11225_.m_20258_($$2);
            f_11188_.debug("loading single player");
        } else {
            $$3 = this.f_11204_.m_78435_(p_11225_);
        }
        return $$3;
    }

    protected void m_6765_(ServerPlayer p_11277_) {
        PlayerAdvancements $$2;
        this.f_11204_.m_78433_(p_11277_);
        ServerStatsCounter $$1 = this.f_11202_.get(p_11277_.m_20148_());
        if ($$1 != null) {
            $$1.m_12818_();
        }
        if (($$2 = this.f_11203_.get(p_11277_.m_20148_())) != null) {
            $$2.m_135991_();
        }
    }

    public void m_11286_(ServerPlayer p_11287_) {
        Entity $$2;
        ServerLevel $$1 = p_11287_.m_9236_();
        p_11287_.m_36220_(Stats.f_12989_);
        this.m_6765_(p_11287_);
        if (p_11287_.m_20159_() && ($$2 = p_11287_.m_20201_()).m_146898_()) {
            f_11188_.debug("Removing player mount");
            p_11287_.m_8127_();
            $$2.m_142429_().forEach(p_215620_ -> p_215620_.m_142467_(Entity.RemovalReason.UNLOADED_WITH_PLAYER));
        }
        p_11287_.m_19877_();
        $$1.m_143261_(p_11287_, Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        p_11287_.m_8960_().m_135978_();
        this.f_11196_.remove(p_11287_);
        this.f_11195_.m_129901_().m_136305_(p_11287_);
        UUID $$3 = p_11287_.m_20148_();
        ServerPlayer $$4 = this.f_11197_.get($$3);
        if ($$4 == p_11287_) {
            this.f_11197_.remove($$3);
            this.f_11202_.remove($$3);
            this.f_11203_.remove($$3);
        }
        this.m_11268_(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, p_11287_));
    }

    @Nullable
    public Component m_6418_(SocketAddress p_11257_, GameProfile p_11258_) {
        if (this.f_11198_.m_11406_(p_11258_)) {
            UserBanListEntry $$2 = (UserBanListEntry)this.f_11198_.m_11388_(p_11258_);
            MutableComponent $$3 = Component.m_237110_("multiplayer.disconnect.banned.reason", $$2.m_10962_());
            if ($$2.m_10961_() != null) {
                $$3.m_7220_(Component.m_237110_("multiplayer.disconnect.banned.expiration", f_11194_.format($$2.m_10961_())));
            }
            return $$3;
        }
        if (!this.m_5764_(p_11258_)) {
            return Component.m_237115_("multiplayer.disconnect.not_whitelisted");
        }
        if (this.f_11199_.m_11041_(p_11257_)) {
            IpBanListEntry $$4 = this.f_11199_.m_11043_(p_11257_);
            MutableComponent $$5 = Component.m_237110_("multiplayer.disconnect.banned_ip.reason", $$4.m_10962_());
            if ($$4.m_10961_() != null) {
                $$5.m_7220_(Component.m_237110_("multiplayer.disconnect.banned_ip.expiration", f_11194_.format($$4.m_10961_())));
            }
            return $$5;
        }
        if (this.f_11196_.size() >= this.f_11193_ && !this.m_5765_(p_11258_)) {
            return Component.m_237115_("multiplayer.disconnect.server_full");
        }
        return null;
    }

    public ServerPlayer m_215624_(GameProfile p_215625_, @Nullable ProfilePublicKey p_215626_) {
        UUID $$2 = UUIDUtil.m_235875_(p_215625_);
        ArrayList $$3 = Lists.newArrayList();
        for (int $$4 = 0; $$4 < this.f_11196_.size(); ++$$4) {
            ServerPlayer $$5 = this.f_11196_.get($$4);
            if (!$$5.m_20148_().equals($$2)) continue;
            $$3.add($$5);
        }
        ServerPlayer $$6 = this.f_11197_.get(p_215625_.getId());
        if ($$6 != null && !$$3.contains($$6)) {
            $$3.add($$6);
        }
        for (ServerPlayer $$7 : $$3) {
            $$7.f_8906_.m_9942_(Component.m_237115_("multiplayer.disconnect.duplicate_login"));
        }
        return new ServerPlayer(this.f_11195_, this.f_11195_.m_129783_(), p_215625_, p_215626_);
    }

    public ServerPlayer m_11236_(ServerPlayer p_11237_, boolean p_11238_) {
        Optional $$7;
        this.f_11196_.remove(p_11237_);
        p_11237_.m_9236_().m_143261_(p_11237_, Entity.RemovalReason.DISCARDED);
        BlockPos $$2 = p_11237_.m_8961_();
        float $$3 = p_11237_.m_8962_();
        boolean $$4 = p_11237_.m_8964_();
        ServerLevel $$5 = this.f_11195_.m_129880_(p_11237_.m_8963_());
        if ($$5 != null && $$2 != null) {
            Optional<Vec3> $$6 = Player.m_36130_($$5, $$2, $$3, $$4, p_11238_);
        } else {
            $$7 = Optional.empty();
        }
        ServerLevel $$8 = $$5 != null && $$7.isPresent() ? $$5 : this.f_11195_.m_129783_();
        ServerPlayer $$9 = new ServerPlayer(this.f_11195_, $$8, p_11237_.m_36316_(), p_11237_.m_219760_());
        $$9.f_8906_ = p_11237_.f_8906_;
        $$9.m_9015_(p_11237_, p_11238_);
        $$9.m_20234_(p_11237_.m_19879_());
        $$9.m_36163_(p_11237_.m_5737_());
        for (String $$10 : p_11237_.m_19880_()) {
            $$9.m_20049_($$10);
        }
        boolean $$11 = false;
        if ($$7.isPresent()) {
            float $$17;
            BlockState $$12 = $$8.m_8055_($$2);
            boolean $$13 = $$12.m_60713_(Blocks.f_50724_);
            Vec3 $$14 = (Vec3)$$7.get();
            if ($$12.m_204336_(BlockTags.f_13038_) || $$13) {
                Vec3 $$15 = Vec3.m_82539_($$2).m_82546_($$14).m_82541_();
                float $$16 = (float)Mth.m_14175_(Mth.m_14136_($$15.f_82481_, $$15.f_82479_) * 57.2957763671875 - 90.0);
            } else {
                $$17 = $$3;
            }
            $$9.m_7678_($$14.f_82479_, $$14.f_82480_, $$14.f_82481_, $$17, 0.0f);
            $$9.m_9158_($$8.m_46472_(), $$2, $$3, $$4, false);
            $$11 = !p_11238_ && $$13;
        } else if ($$2 != null) {
            $$9.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132153_, 0.0f));
        }
        while (!$$8.m_45786_($$9) && $$9.m_20186_() < (double)$$8.m_151558_()) {
            $$9.m_6034_($$9.m_20185_(), $$9.m_20186_() + 1.0, $$9.m_20189_());
        }
        LevelData $$18 = $$9.f_19853_.m_6106_();
        $$9.f_8906_.m_9829_(new ClientboundRespawnPacket($$9.f_19853_.m_220362_(), $$9.f_19853_.m_46472_(), BiomeManager.m_47877_($$9.m_9236_().m_7328_()), $$9.f_8941_.m_9290_(), $$9.f_8941_.m_9293_(), $$9.m_9236_().m_46659_(), $$9.m_9236_().m_8584_(), p_11238_, $$9.m_219759_()));
        $$9.f_8906_.m_9774_($$9.m_20185_(), $$9.m_20186_(), $$9.m_20189_(), $$9.m_146908_(), $$9.m_146909_());
        $$9.f_8906_.m_9829_(new ClientboundSetDefaultSpawnPositionPacket($$8.m_220360_(), $$8.m_220361_()));
        $$9.f_8906_.m_9829_(new ClientboundChangeDifficultyPacket($$18.m_5472_(), $$18.m_5474_()));
        $$9.f_8906_.m_9829_(new ClientboundSetExperiencePacket($$9.f_36080_, $$9.f_36079_, $$9.f_36078_));
        this.m_11229_($$9, $$8);
        this.m_11289_($$9);
        $$8.m_8845_($$9);
        this.f_11196_.add($$9);
        this.f_11197_.put($$9.m_20148_(), $$9);
        $$9.m_143429_();
        $$9.m_21153_($$9.m_21223_());
        if ($$11) {
            $$9.f_8906_.m_9829_(new ClientboundSoundPacket(SoundEvents.f_12377_, SoundSource.BLOCKS, $$2.m_123341_(), $$2.m_123342_(), $$2.m_123343_(), 1.0f, 1.0f, $$8.m_213780_().m_188505_()));
        }
        return $$9;
    }

    public void m_11289_(ServerPlayer p_11290_) {
        GameProfile $$1 = p_11290_.m_36316_();
        int $$2 = this.f_11195_.m_129944_($$1);
        this.m_11226_(p_11290_, $$2);
    }

    public void m_11288_() {
        if (++this.f_11210_ > 600) {
            this.m_11268_(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_LATENCY, this.f_11196_));
            this.f_11210_ = 0;
        }
    }

    public void m_11268_(Packet<?> p_11269_) {
        for (ServerPlayer $$1 : this.f_11196_) {
            $$1.f_8906_.m_9829_(p_11269_);
        }
    }

    public void m_11270_(Packet<?> p_11271_, ResourceKey<Level> p_11272_) {
        for (ServerPlayer $$2 : this.f_11196_) {
            if ($$2.f_19853_.m_46472_() != p_11272_) continue;
            $$2.f_8906_.m_9829_(p_11271_);
        }
    }

    public void m_215621_(Player p_215622_, Component p_215623_) {
        Team $$2 = p_215622_.m_5647_();
        if ($$2 == null) {
            return;
        }
        Collection<String> $$3 = $$2.m_6809_();
        for (String $$4 : $$3) {
            ServerPlayer $$5 = this.m_11255_($$4);
            if ($$5 == null || $$5 == p_215622_) continue;
            $$5.m_213846_(p_215623_);
        }
    }

    public void m_215649_(Player p_215650_, Component p_215651_) {
        Team $$2 = p_215650_.m_5647_();
        if ($$2 == null) {
            this.m_240416_(p_215651_, false);
            return;
        }
        for (int $$3 = 0; $$3 < this.f_11196_.size(); ++$$3) {
            ServerPlayer $$4 = this.f_11196_.get($$3);
            if ($$4.m_5647_() == $$2) continue;
            $$4.m_213846_(p_215651_);
        }
    }

    public String[] m_11291_() {
        String[] $$0 = new String[this.f_11196_.size()];
        for (int $$1 = 0; $$1 < this.f_11196_.size(); ++$$1) {
            $$0[$$1] = this.f_11196_.get($$1).m_36316_().getName();
        }
        return $$0;
    }

    public UserBanList m_11295_() {
        return this.f_11198_;
    }

    public IpBanList m_11299_() {
        return this.f_11199_;
    }

    public void m_5749_(GameProfile p_11254_) {
        this.f_11200_.m_11381_(new ServerOpListEntry(p_11254_, this.f_11195_.m_7022_(), this.f_11200_.m_11351_(p_11254_)));
        ServerPlayer $$1 = this.m_11259_(p_11254_.getId());
        if ($$1 != null) {
            this.m_11289_($$1);
        }
    }

    public void m_5750_(GameProfile p_11281_) {
        this.f_11200_.m_11393_(p_11281_);
        ServerPlayer $$1 = this.m_11259_(p_11281_.getId());
        if ($$1 != null) {
            this.m_11289_($$1);
        }
    }

    private void m_11226_(ServerPlayer p_11227_, int p_11228_) {
        if (p_11227_.f_8906_ != null) {
            byte $$4;
            if (p_11228_ <= 0) {
                int $$2 = 24;
            } else if (p_11228_ >= 4) {
                int $$3 = 28;
            } else {
                $$4 = (byte)(24 + p_11228_);
            }
            p_11227_.f_8906_.m_9829_(new ClientboundEntityEventPacket(p_11227_, $$4));
        }
        this.f_11195_.m_129892_().m_82095_(p_11227_);
    }

    public boolean m_5764_(GameProfile p_11294_) {
        return !this.f_11205_ || this.f_11200_.m_11396_(p_11294_) || this.f_11201_.m_11396_(p_11294_);
    }

    public boolean m_11303_(GameProfile p_11304_) {
        return this.f_11200_.m_11396_(p_11304_) || this.f_11195_.m_7779_(p_11304_) && this.f_11195_.m_129910_().m_5468_() || this.f_11209_;
    }

    @Nullable
    public ServerPlayer m_11255_(String p_11256_) {
        for (ServerPlayer $$1 : this.f_11196_) {
            if (!$$1.m_36316_().getName().equalsIgnoreCase(p_11256_)) continue;
            return $$1;
        }
        return null;
    }

    public void m_11241_(@Nullable Player p_11242_, double p_11243_, double p_11244_, double p_11245_, double p_11246_, ResourceKey<Level> p_11247_, Packet<?> p_11248_) {
        for (int $$7 = 0; $$7 < this.f_11196_.size(); ++$$7) {
            double $$11;
            double $$10;
            double $$9;
            ServerPlayer $$8 = this.f_11196_.get($$7);
            if ($$8 == p_11242_ || $$8.f_19853_.m_46472_() != p_11247_ || !(($$9 = p_11243_ - $$8.m_20185_()) * $$9 + ($$10 = p_11244_ - $$8.m_20186_()) * $$10 + ($$11 = p_11245_ - $$8.m_20189_()) * $$11 < p_11246_ * p_11246_)) continue;
            $$8.f_8906_.m_9829_(p_11248_);
        }
    }

    public void m_11302_() {
        for (int $$0 = 0; $$0 < this.f_11196_.size(); ++$$0) {
            this.m_6765_(this.f_11196_.get($$0));
        }
    }

    public UserWhiteList m_11305_() {
        return this.f_11201_;
    }

    public String[] m_11306_() {
        return this.f_11201_.m_5875_();
    }

    public ServerOpList m_11307_() {
        return this.f_11200_;
    }

    public String[] m_11308_() {
        return this.f_11200_.m_5875_();
    }

    public void m_7542_() {
    }

    public void m_11229_(ServerPlayer p_11230_, ServerLevel p_11231_) {
        WorldBorder $$2 = this.f_11195_.m_129783_().m_6857_();
        p_11230_.f_8906_.m_9829_(new ClientboundInitializeBorderPacket($$2));
        p_11230_.f_8906_.m_9829_(new ClientboundSetTimePacket(p_11231_.m_46467_(), p_11231_.m_46468_(), p_11231_.m_46469_().m_46207_(GameRules.f_46140_)));
        p_11230_.f_8906_.m_9829_(new ClientboundSetDefaultSpawnPositionPacket(p_11231_.m_220360_(), p_11231_.m_220361_()));
        if (p_11231_.m_46471_()) {
            p_11230_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132154_, 0.0f));
            p_11230_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132160_, p_11231_.m_46722_(1.0f)));
            p_11230_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132161_, p_11231_.m_46661_(1.0f)));
        }
    }

    public void m_11292_(ServerPlayer p_11293_) {
        p_11293_.f_36095_.m_150429_();
        p_11293_.m_9233_();
        p_11293_.f_8906_.m_9829_(new ClientboundSetCarriedItemPacket(p_11293_.m_150109_().f_35977_));
    }

    public int m_11309_() {
        return this.f_11196_.size();
    }

    public int m_11310_() {
        return this.f_11193_;
    }

    public boolean m_11311_() {
        return this.f_11205_;
    }

    public void m_6628_(boolean p_11276_) {
        this.f_11205_ = p_11276_;
    }

    public List<ServerPlayer> m_11282_(String p_11283_) {
        ArrayList $$1 = Lists.newArrayList();
        for (ServerPlayer $$2 : this.f_11196_) {
            if (!$$2.m_9239_().equals(p_11283_)) continue;
            $$1.add($$2);
        }
        return $$1;
    }

    public int m_11312_() {
        return this.f_11207_;
    }

    public int m_184213_() {
        return this.f_184208_;
    }

    public MinecraftServer m_7873_() {
        return this.f_11195_;
    }

    @Nullable
    public CompoundTag m_6960_() {
        return null;
    }

    public void m_11284_(boolean p_11285_) {
        this.f_11209_ = p_11285_;
    }

    public void m_11313_() {
        for (int $$0 = 0; $$0 < this.f_11196_.size(); ++$$0) {
            this.f_11196_.get((int)$$0).f_8906_.m_9942_(Component.m_237115_("multiplayer.disconnect.server_shutdown"));
        }
    }

    public void m_240416_(Component p_240618_, boolean p_240644_) {
        this.m_240502_(p_240618_, p_215639_ -> p_240618_, p_240644_);
    }

    public void m_240502_(Component p_240526_, Function<ServerPlayer, Component> p_240594_, boolean p_240648_) {
        this.f_11195_.m_213846_(p_240526_);
        for (ServerPlayer $$3 : this.f_11196_) {
            Component $$4 = p_240594_.apply($$3);
            if ($$4 == null) continue;
            $$3.m_240418_($$4, p_240648_);
        }
    }

    public void m_243063_(PlayerChatMessage p_243229_, CommandSourceStack p_243254_, ChatType.Bound p_243255_) {
        this.m_243140_(p_243229_, p_243254_::m_243061_, p_243254_.m_230896_(), p_243254_.m_230895_(), p_243255_);
    }

    public void m_243049_(PlayerChatMessage p_243264_, ServerPlayer p_243234_, ChatType.Bound p_243204_) {
        this.m_243140_(p_243264_, p_243234_::m_143421_, p_243234_, p_243234_.m_217000_(), p_243204_);
    }

    private void m_243140_(PlayerChatMessage p_243322_, Predicate<ServerPlayer> p_243313_, @Nullable ServerPlayer p_243233_, ChatSender p_243228_, ChatType.Bound p_243291_) {
        boolean $$5 = this.m_241091_(p_243322_, p_243228_);
        this.f_11195_.m_241158_(p_243322_.m_237220_(), p_243291_, $$5 ? null : "Not Secure");
        OutgoingPlayerChatMessage $$6 = OutgoingPlayerChatMessage.m_242676_(p_243322_);
        boolean $$7 = p_243322_.m_243059_();
        boolean $$8 = false;
        for (ServerPlayer $$9 : this.f_11196_) {
            boolean $$10 = p_243313_.test($$9);
            $$9.m_243093_($$6, $$10, p_243291_);
            if (p_243233_ == $$9) continue;
            $$8 |= $$7 && $$10;
        }
        if ($$8 && p_243233_ != null) {
            p_243233_.m_213846_(f_243017_);
        }
        $$6.m_241051_(this);
    }

    public void m_241163_(PlayerChatMessage p_241532_, Set<ServerPlayer> p_241429_) {
        byte[] $$2 = p_241532_.f_240885_().m_241131_().asBytes();
        for (ServerPlayer $$3 : this.f_11196_) {
            if (p_241429_.contains($$3)) continue;
            $$3.m_241183_(p_241532_.f_240875_(), p_241532_.f_240893_(), $$2);
        }
    }

    private boolean m_241091_(PlayerChatMessage p_241287_, ChatSender p_241412_) {
        return !p_241287_.m_240431_(Instant.now()) && p_241287_.m_241006_(p_241412_);
    }

    public ServerStatsCounter m_11239_(Player p_11240_) {
        UUID $$1 = p_11240_.m_20148_();
        ServerStatsCounter $$2 = this.f_11202_.get($$1);
        if ($$2 == null) {
            File $$5;
            Path $$6;
            File $$3 = this.f_11195_.m_129843_(LevelResource.f_78175_).toFile();
            File $$4 = new File($$3, $$1 + ".json");
            if (!$$4.exists() && FileUtil.m_133728_($$6 = ($$5 = new File($$3, p_11240_.m_7755_().getString() + ".json")).toPath()) && FileUtil.m_133734_($$6) && $$6.startsWith($$3.getPath()) && $$5.isFile()) {
                $$5.renameTo($$4);
            }
            $$2 = new ServerStatsCounter(this.f_11195_, $$4);
            this.f_11202_.put($$1, $$2);
        }
        return $$2;
    }

    public PlayerAdvancements m_11296_(ServerPlayer p_11297_) {
        UUID $$1 = p_11297_.m_20148_();
        PlayerAdvancements $$2 = this.f_11203_.get($$1);
        if ($$2 == null) {
            File $$3 = this.f_11195_.m_129843_(LevelResource.f_78174_).toFile();
            File $$4 = new File($$3, $$1 + ".json");
            $$2 = new PlayerAdvancements(this.f_11195_.m_129933_(), this, this.f_11195_.m_129889_(), $$4, p_11297_);
            this.f_11203_.put($$1, $$2);
        }
        $$2.m_135979_(p_11297_);
        return $$2;
    }

    public void m_11217_(int p_11218_) {
        this.f_11207_ = p_11218_;
        this.m_11268_(new ClientboundSetChunkCacheRadiusPacket(p_11218_));
        for (ServerLevel $$1 : this.f_11195_.m_129785_()) {
            if ($$1 == null) continue;
            $$1.m_7726_().m_8354_(p_11218_);
        }
    }

    public void m_184211_(int p_184212_) {
        this.f_184208_ = p_184212_;
        this.m_11268_(new ClientboundSetSimulationDistancePacket(p_184212_));
        for (ServerLevel $$1 : this.f_11195_.m_129785_()) {
            if ($$1 == null) continue;
            $$1.m_7726_().m_184026_(p_184212_);
        }
    }

    public List<ServerPlayer> m_11314_() {
        return this.f_11196_;
    }

    @Nullable
    public ServerPlayer m_11259_(UUID p_11260_) {
        return this.f_11197_.get(p_11260_);
    }

    public boolean m_5765_(GameProfile p_11298_) {
        return false;
    }

    public void m_11315_() {
        for (PlayerAdvancements $$0 : this.f_11203_.values()) {
            $$0.m_135981_(this.f_11195_.m_129889_());
        }
        this.m_11268_(new ClientboundUpdateTagsPacket(TagNetworkSerialization.m_203950_(this.f_11206_)));
        ClientboundUpdateRecipesPacket $$1 = new ClientboundUpdateRecipesPacket(this.f_11195_.m_129894_().m_44051_());
        for (ServerPlayer $$2 : this.f_11196_) {
            $$2.f_8906_.m_9829_($$1);
            $$2.m_8952_().m_12789_($$2);
        }
    }

    public boolean m_11316_() {
        return this.f_11209_;
    }
}

