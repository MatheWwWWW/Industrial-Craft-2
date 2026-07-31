/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.OutgoingPlayerChatMessage;
import net.minecraft.network.chat.SignedMessageHeader;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatHeaderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ComplexItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ServerItemCooldowns;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.slf4j.Logger;

public class ServerPlayer
extends Player {
    private static final Logger f_8907_ = LogUtils.getLogger();
    private static final int f_143381_ = 32;
    private static final int f_143382_ = 10;
    public ServerGamePacketListenerImpl f_8906_;
    public final MinecraftServer f_8924_;
    public final ServerPlayerGameMode f_8941_;
    private final PlayerAdvancements f_8909_;
    private final ServerStatsCounter f_8910_;
    private float f_8911_ = Float.MIN_VALUE;
    private int f_8912_ = Integer.MIN_VALUE;
    private int f_8913_ = Integer.MIN_VALUE;
    private int f_8914_ = Integer.MIN_VALUE;
    private int f_8915_ = Integer.MIN_VALUE;
    private int f_8916_ = Integer.MIN_VALUE;
    private float f_8917_ = -1.0E8f;
    private int f_8918_ = -99999999;
    private boolean f_8919_ = true;
    private int f_8920_ = -99999999;
    private int f_8921_ = 60;
    private ChatVisiblity f_8922_ = ChatVisiblity.FULL;
    private boolean f_8923_ = true;
    private long f_8925_ = Util.m_137550_();
    @Nullable
    private Entity f_8926_;
    private boolean f_8927_;
    private boolean f_8928_;
    private final ServerRecipeBook f_8929_ = new ServerRecipeBook();
    @Nullable
    private Vec3 f_8930_;
    private int f_8931_;
    private boolean f_8932_;
    @Nullable
    private Vec3 f_184125_;
    @Nullable
    private Vec3 f_8933_;
    @Nullable
    private Vec3 f_184126_;
    private SectionPos f_8934_ = SectionPos.m_123173_(0, 0, 0);
    private ResourceKey<Level> f_8935_ = Level.f_46428_;
    @Nullable
    private BlockPos f_8936_;
    private boolean f_8937_;
    private float f_8938_;
    private final TextFilter f_8939_;
    private boolean f_143378_;
    private boolean f_184127_ = true;
    private final ContainerSynchronizer f_143379_ = new ContainerSynchronizer(){

        @Override
        public void m_142589_(AbstractContainerMenu p_143448_, NonNullList<ItemStack> p_143449_, ItemStack p_143450_, int[] p_143451_) {
            ServerPlayer.this.f_8906_.m_9829_(new ClientboundContainerSetContentPacket(p_143448_.f_38840_, p_143448_.m_182425_(), p_143449_, p_143450_));
            for (int $$4 = 0; $$4 < p_143451_.length; ++$$4) {
                this.m_143454_(p_143448_, $$4, p_143451_[$$4]);
            }
        }

        @Override
        public void m_142074_(AbstractContainerMenu p_143441_, int p_143442_, ItemStack p_143443_) {
            ServerPlayer.this.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(p_143441_.f_38840_, p_143441_.m_182425_(), p_143442_, p_143443_));
        }

        @Override
        public void m_142529_(AbstractContainerMenu p_143445_, ItemStack p_143446_) {
            ServerPlayer.this.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(-1, p_143445_.m_182425_(), -1, p_143446_));
        }

        @Override
        public void m_142145_(AbstractContainerMenu p_143437_, int p_143438_, int p_143439_) {
            this.m_143454_(p_143437_, p_143438_, p_143439_);
        }

        private void m_143454_(AbstractContainerMenu p_143455_, int p_143456_, int p_143457_) {
            ServerPlayer.this.f_8906_.m_9829_(new ClientboundContainerSetDataPacket(p_143455_.f_38840_, p_143456_, p_143457_));
        }
    };
    private final ContainerListener f_143380_ = new ContainerListener(){

        @Override
        public void m_7934_(AbstractContainerMenu p_143466_, int p_143467_, ItemStack p_143468_) {
            Slot $$3 = p_143466_.m_38853_(p_143467_);
            if ($$3 instanceof ResultSlot) {
                return;
            }
            if ($$3.f_40218_ == ServerPlayer.this.m_150109_()) {
                CriteriaTriggers.f_10571_.m_43149_(ServerPlayer.this, ServerPlayer.this.m_150109_(), p_143468_);
            }
        }

        @Override
        public void m_142153_(AbstractContainerMenu p_143462_, int p_143463_, int p_143464_) {
        }
    };
    private int f_8940_;
    public int f_8943_;
    public boolean f_8944_;

    public ServerPlayer(MinecraftServer p_215088_, ServerLevel p_215089_, GameProfile p_215090_, @Nullable ProfilePublicKey p_215091_) {
        super(p_215089_, p_215089_.m_220360_(), p_215089_.m_220361_(), p_215090_, p_215091_);
        this.f_8939_ = p_215088_.m_7950_(this);
        this.f_8941_ = p_215088_.m_177933_(this);
        this.f_8924_ = p_215088_;
        this.f_8910_ = p_215088_.m_6846_().m_11239_(this);
        this.f_8909_ = p_215088_.m_6846_().m_11296_(this);
        this.f_19793_ = 1.0f;
        this.m_9201_(p_215089_);
    }

    private void m_9201_(ServerLevel p_9202_) {
        BlockPos $$1 = p_9202_.m_220360_();
        if (p_9202_.m_6042_().f_223549_() && p_9202_.m_7654_().m_129910_().m_5464_() != GameType.ADVENTURE) {
            long $$4;
            long $$5;
            int $$2 = Math.max(0, this.f_8924_.m_129803_(p_9202_));
            int $$3 = Mth.m_14107_(p_9202_.m_6857_().m_61941_($$1.m_123341_(), $$1.m_123343_()));
            if ($$3 < $$2) {
                $$2 = $$3;
            }
            if ($$3 <= 1) {
                $$2 = 1;
            }
            int $$6 = ($$5 = ($$4 = (long)($$2 * 2 + 1)) * $$4) > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)$$5;
            int $$7 = this.m_9237_($$6);
            int $$8 = RandomSource.m_216327_().m_188503_($$6);
            for (int $$9 = 0; $$9 < $$6; ++$$9) {
                int $$10 = ($$8 + $$7 * $$9) % $$6;
                int $$11 = $$10 % ($$2 * 2 + 1);
                int $$12 = $$10 / ($$2 * 2 + 1);
                BlockPos $$13 = PlayerRespawnLogic.m_183928_(p_9202_, $$1.m_123341_() + $$11 - $$2, $$1.m_123343_() + $$12 - $$2);
                if ($$13 == null) continue;
                this.m_20035_($$13, 0.0f, 0.0f);
                if (!p_9202_.m_45786_(this)) {
                    continue;
                }
                break;
            }
        } else {
            this.m_20035_($$1, 0.0f, 0.0f);
            while (!p_9202_.m_45786_(this) && this.m_20186_() < (double)(p_9202_.m_151558_() - 1)) {
                this.m_6034_(this.m_20185_(), this.m_20186_() + 1.0, this.m_20189_());
            }
        }
    }

    private int m_9237_(int p_9238_) {
        return p_9238_ <= 16 ? p_9238_ - 1 : 17;
    }

    @Override
    public void m_7378_(CompoundTag p_9131_) {
        super.m_7378_(p_9131_);
        if (p_9131_.m_128425_("enteredNetherPosition", 10)) {
            CompoundTag $$1 = p_9131_.m_128469_("enteredNetherPosition");
            this.f_8933_ = new Vec3($$1.m_128459_("x"), $$1.m_128459_("y"), $$1.m_128459_("z"));
        }
        this.f_8928_ = p_9131_.m_128471_("seenCredits");
        if (p_9131_.m_128425_("recipeBook", 10)) {
            this.f_8929_.m_12794_(p_9131_.m_128469_("recipeBook"), this.f_8924_.m_129894_());
        }
        if (this.m_5803_()) {
            this.m_5796_();
        }
        if (p_9131_.m_128425_("SpawnX", 99) && p_9131_.m_128425_("SpawnY", 99) && p_9131_.m_128425_("SpawnZ", 99)) {
            this.f_8936_ = new BlockPos(p_9131_.m_128451_("SpawnX"), p_9131_.m_128451_("SpawnY"), p_9131_.m_128451_("SpawnZ"));
            this.f_8937_ = p_9131_.m_128471_("SpawnForced");
            this.f_8938_ = p_9131_.m_128457_("SpawnAngle");
            if (p_9131_.m_128441_("SpawnDimension")) {
                this.f_8935_ = Level.f_46427_.parse((DynamicOps)NbtOps.f_128958_, (Object)p_9131_.m_128423_("SpawnDimension")).resultOrPartial(arg_0 -> ((Logger)f_8907_).error(arg_0)).orElse(Level.f_46428_);
            }
        }
    }

    @Override
    public void m_7380_(CompoundTag p_9197_) {
        super.m_7380_(p_9197_);
        this.m_143430_(p_9197_);
        p_9197_.m_128379_("seenCredits", this.f_8928_);
        if (this.f_8933_ != null) {
            CompoundTag $$1 = new CompoundTag();
            $$1.m_128347_("x", this.f_8933_.f_82479_);
            $$1.m_128347_("y", this.f_8933_.f_82480_);
            $$1.m_128347_("z", this.f_8933_.f_82481_);
            p_9197_.m_128365_("enteredNetherPosition", $$1);
        }
        Entity $$2 = this.m_20201_();
        Entity $$3 = this.m_20202_();
        if ($$3 != null && $$2 != this && $$2.m_146898_()) {
            CompoundTag $$4 = new CompoundTag();
            CompoundTag $$5 = new CompoundTag();
            $$2.m_20223_($$5);
            $$4.m_128362_("Attach", $$3.m_20148_());
            $$4.m_128365_("Entity", $$5);
            p_9197_.m_128365_("RootVehicle", $$4);
        }
        p_9197_.m_128365_("recipeBook", this.f_8929_.m_12805_());
        p_9197_.m_128359_("Dimension", this.f_19853_.m_46472_().m_135782_().toString());
        if (this.f_8936_ != null) {
            p_9197_.m_128405_("SpawnX", this.f_8936_.m_123341_());
            p_9197_.m_128405_("SpawnY", this.f_8936_.m_123342_());
            p_9197_.m_128405_("SpawnZ", this.f_8936_.m_123343_());
            p_9197_.m_128379_("SpawnForced", this.f_8937_);
            p_9197_.m_128350_("SpawnAngle", this.f_8938_);
            ResourceLocation.f_135803_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_8935_.m_135782_()).resultOrPartial(arg_0 -> ((Logger)f_8907_).error(arg_0)).ifPresent(p_9134_ -> p_9197_.m_128365_("SpawnDimension", (Tag)p_9134_));
        }
    }

    public void m_8985_(int p_8986_) {
        float $$1 = this.m_36323_();
        float $$2 = ($$1 - 1.0f) / $$1;
        this.f_36080_ = Mth.m_14036_((float)p_8986_ / $$1, 0.0f, $$2);
        this.f_8920_ = -1;
    }

    public void m_9174_(int p_9175_) {
        this.f_36078_ = p_9175_;
        this.f_8920_ = -1;
    }

    @Override
    public void m_6749_(int p_9200_) {
        super.m_6749_(p_9200_);
        this.f_8920_ = -1;
    }

    @Override
    public void m_7408_(ItemStack p_9079_, int p_9080_) {
        super.m_7408_(p_9079_, p_9080_);
        this.f_8920_ = -1;
    }

    private void m_143399_(AbstractContainerMenu p_143400_) {
        p_143400_.m_38893_(this.f_143380_);
        p_143400_.m_150416_(this.f_143379_);
    }

    public void m_143429_() {
        this.m_143399_(this.f_36095_);
    }

    @Override
    public void m_8108_() {
        super.m_8108_();
        this.f_8906_.m_9829_(new ClientboundPlayerCombatEnterPacket());
    }

    @Override
    public void m_8098_() {
        super.m_8098_();
        this.f_8906_.m_9829_(new ClientboundPlayerCombatEndPacket(this.m_21231_()));
    }

    @Override
    protected void m_6763_(BlockState p_9103_) {
        CriteriaTriggers.f_10570_.m_31269_(this, p_9103_);
    }

    @Override
    protected ItemCooldowns m_7478_() {
        return new ServerItemCooldowns(this);
    }

    @Override
    public void m_8119_() {
        Entity $$0;
        this.f_8941_.m_7712_();
        --this.f_8921_;
        if (this.f_19802_ > 0) {
            --this.f_19802_;
        }
        this.f_36096_.m_38946_();
        if (!this.f_19853_.f_46443_ && !this.f_36096_.m_6875_(this)) {
            this.m_6915_();
            this.f_36096_ = this.f_36095_;
        }
        if (($$0 = this.m_8954_()) != this) {
            if ($$0.m_6084_()) {
                this.m_19890_($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_(), $$0.m_146908_(), $$0.m_146909_());
                this.m_9236_().m_7726_().m_8385_(this);
                if (this.m_36342_()) {
                    this.m_9213_(this);
                }
            } else {
                this.m_9213_(this);
            }
        }
        CriteriaTriggers.f_10589_.m_222618_(this);
        if (this.f_8930_ != null) {
            CriteriaTriggers.f_10587_.m_49116_(this, this.f_8930_, this.f_19797_ - this.f_8931_);
        }
        this.m_184139_();
        this.m_184140_();
        this.f_8909_.m_135992_(this);
    }

    public void m_9240_() {
        try {
            if (!this.m_5833_() || !this.m_146899_()) {
                super.m_8119_();
            }
            for (int $$0 = 0; $$0 < this.m_150109_().m_6643_(); ++$$0) {
                Packet<?> $$2;
                ItemStack $$1 = this.m_150109_().m_8020_($$0);
                if (!$$1.m_41720_().m_7807_() || ($$2 = ((ComplexItem)$$1.m_41720_()).m_7233_($$1, this.f_19853_, this)) == null) continue;
                this.f_8906_.m_9829_($$2);
            }
            if (this.m_21223_() != this.f_8917_ || this.f_8918_ != this.f_36097_.m_38702_() || this.f_36097_.m_38722_() == 0.0f != this.f_8919_) {
                this.f_8906_.m_9829_(new ClientboundSetHealthPacket(this.m_21223_(), this.f_36097_.m_38702_(), this.f_36097_.m_38722_()));
                this.f_8917_ = this.m_21223_();
                this.f_8918_ = this.f_36097_.m_38702_();
                boolean bl = this.f_8919_ = this.f_36097_.m_38722_() == 0.0f;
            }
            if (this.m_21223_() + this.m_6103_() != this.f_8911_) {
                this.f_8911_ = this.m_21223_() + this.m_6103_();
                this.m_9104_(ObjectiveCriteria.f_83593_, Mth.m_14167_(this.f_8911_));
            }
            if (this.f_36097_.m_38702_() != this.f_8912_) {
                this.f_8912_ = this.f_36097_.m_38702_();
                this.m_9104_(ObjectiveCriteria.f_83594_, Mth.m_14167_(this.f_8912_));
            }
            if (this.m_20146_() != this.f_8913_) {
                this.f_8913_ = this.m_20146_();
                this.m_9104_(ObjectiveCriteria.f_83595_, Mth.m_14167_(this.f_8913_));
            }
            if (this.m_21230_() != this.f_8914_) {
                this.f_8914_ = this.m_21230_();
                this.m_9104_(ObjectiveCriteria.f_83596_, Mth.m_14167_(this.f_8914_));
            }
            if (this.f_36079_ != this.f_8916_) {
                this.f_8916_ = this.f_36079_;
                this.m_9104_(ObjectiveCriteria.f_83597_, Mth.m_14167_(this.f_8916_));
            }
            if (this.f_36078_ != this.f_8915_) {
                this.f_8915_ = this.f_36078_;
                this.m_9104_(ObjectiveCriteria.f_83598_, Mth.m_14167_(this.f_8915_));
            }
            if (this.f_36079_ != this.f_8920_) {
                this.f_8920_ = this.f_36079_;
                this.f_8906_.m_9829_(new ClientboundSetExperiencePacket(this.f_36080_, this.f_36079_, this.f_36078_));
            }
            if (this.f_19797_ % 20 == 0) {
                CriteriaTriggers.f_10582_.m_222618_(this);
            }
        }
        catch (Throwable $$3) {
            CrashReport $$4 = CrashReport.m_127521_($$3, "Ticking player");
            CrashReportCategory $$5 = $$4.m_127514_("Player being ticked");
            this.m_7976_($$5);
            throw new ReportedException($$4);
        }
    }

    @Override
    public void m_183634_() {
        if (this.m_21223_() > 0.0f && this.f_184125_ != null) {
            CriteriaTriggers.f_184759_.m_186165_(this, this.f_184125_);
        }
        this.f_184125_ = null;
        super.m_183634_();
    }

    public void m_184139_() {
        if (this.f_19789_ > 0.0f && this.f_184125_ == null) {
            this.f_184125_ = this.m_20182_();
        }
    }

    public void m_184140_() {
        if (this.m_20202_() != null && this.m_20202_().m_20077_()) {
            if (this.f_184126_ == null) {
                this.f_184126_ = this.m_20182_();
            } else {
                CriteriaTriggers.f_184760_.m_186165_(this, this.f_184126_);
            }
        }
        if (!(this.f_184126_ == null || this.m_20202_() != null && this.m_20202_().m_20077_())) {
            this.f_184126_ = null;
        }
    }

    private void m_9104_(ObjectiveCriteria p_9105_, int p_9106_) {
        this.m_36329_().m_83427_(p_9105_, this.m_6302_(), p_9178_ -> p_9178_.m_83402_(p_9106_));
    }

    @Override
    public void m_6667_(DamageSource p_9035_) {
        this.m_146850_(GameEvent.f_223707_);
        boolean $$1 = this.f_19853_.m_46469_().m_46207_(GameRules.f_46142_);
        if ($$1) {
            Component $$2 = this.m_21231_().m_19293_();
            this.f_8906_.m_243119_(new ClientboundPlayerCombatKillPacket(this.m_21231_(), $$2), PacketSendListener.m_243073_(() -> {
                int $$1 = 256;
                String $$2 = $$2.m_130668_(256);
                MutableComponent $$3 = Component.m_237110_("death.attack.message_too_long", Component.m_237113_($$2).m_130940_(ChatFormatting.YELLOW));
                MutableComponent $$4 = Component.m_237110_("death.attack.even_more_magic", this.m_5446_()).m_130938_(p_143420_ -> p_143420_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, $$3)));
                return new ClientboundPlayerCombatKillPacket(this.m_21231_(), $$4);
            }));
            Team $$3 = this.m_5647_();
            if ($$3 == null || $$3.m_7468_() == Team.Visibility.ALWAYS) {
                this.f_8924_.m_6846_().m_240416_($$2, false);
            } else if ($$3.m_7468_() == Team.Visibility.HIDE_FOR_OTHER_TEAMS) {
                this.f_8924_.m_6846_().m_215621_(this, $$2);
            } else if ($$3.m_7468_() == Team.Visibility.HIDE_FOR_OWN_TEAM) {
                this.f_8924_.m_6846_().m_215649_(this, $$2);
            }
        } else {
            this.f_8906_.m_9829_(new ClientboundPlayerCombatKillPacket(this.m_21231_(), CommonComponents.f_237098_));
        }
        this.m_36328_();
        if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46126_)) {
            this.m_9215_();
        }
        if (!this.m_5833_()) {
            this.m_6668_(p_9035_);
        }
        this.m_36329_().m_83427_(ObjectiveCriteria.f_83590_, this.m_6302_(), Score::m_83392_);
        LivingEntity $$4 = this.m_21232_();
        if ($$4 != null) {
            this.m_36246_(Stats.f_12987_.m_12902_($$4.m_6095_()));
            $$4.m_5993_(this, this.f_20897_, p_9035_);
            this.m_21268_($$4);
        }
        this.f_19853_.m_7605_(this, (byte)3);
        this.m_36220_(Stats.f_12935_);
        this.m_7166_(Stats.f_12988_.m_12902_(Stats.f_12991_));
        this.m_7166_(Stats.f_12988_.m_12902_(Stats.f_12992_));
        this.m_20095_();
        this.m_146917_(0);
        this.m_146868_(false);
        this.m_21231_().m_19296_();
        this.m_219749_(Optional.of(GlobalPos.m_122643_(this.f_19853_.m_46472_(), this.m_20183_())));
    }

    private void m_9215_() {
        AABB $$0 = new AABB(this.m_20183_()).m_82377_(32.0, 10.0, 32.0);
        this.f_19853_.m_6443_(Mob.class, $$0, EntitySelector.f_20408_).stream().filter(p_9188_ -> p_9188_ instanceof NeutralMob).forEach(p_9057_ -> ((NeutralMob)((Object)p_9057_)).m_21676_(this));
    }

    @Override
    public void m_5993_(Entity p_9050_, int p_9051_, DamageSource p_9052_) {
        if (p_9050_ == this) {
            return;
        }
        super.m_5993_(p_9050_, p_9051_, p_9052_);
        this.m_36401_(p_9051_);
        String $$3 = this.m_6302_();
        String $$4 = p_9050_.m_6302_();
        this.m_36329_().m_83427_(ObjectiveCriteria.f_83592_, $$3, Score::m_83392_);
        if (p_9050_ instanceof Player) {
            this.m_36220_(Stats.f_12938_);
            this.m_36329_().m_83427_(ObjectiveCriteria.f_83591_, $$3, Score::m_83392_);
        } else {
            this.m_36220_(Stats.f_12936_);
        }
        this.m_9124_($$3, $$4, ObjectiveCriteria.f_83599_);
        this.m_9124_($$4, $$3, ObjectiveCriteria.f_83600_);
        CriteriaTriggers.f_10568_.m_48104_(this, p_9050_, p_9052_);
    }

    private void m_9124_(String p_9125_, String p_9126_, ObjectiveCriteria[] p_9127_) {
        int $$4;
        PlayerTeam $$3 = this.m_36329_().m_83500_(p_9126_);
        if ($$3 != null && ($$4 = $$3.m_7414_().m_126656_()) >= 0 && $$4 < p_9127_.length) {
            this.m_36329_().m_83427_(p_9127_[$$4], p_9125_, Score::m_83392_);
        }
    }

    @Override
    public boolean m_6469_(DamageSource p_9037_, float p_9038_) {
        boolean $$2;
        if (this.m_6673_(p_9037_)) {
            return false;
        }
        boolean bl = $$2 = this.f_8924_.m_6982_() && this.m_9216_() && "fall".equals(p_9037_.f_19326_);
        if (!$$2 && this.f_8921_ > 0 && p_9037_ != DamageSource.f_19317_) {
            return false;
        }
        if (p_9037_ instanceof EntityDamageSource) {
            AbstractArrow $$4;
            Entity $$5;
            Entity $$3 = p_9037_.m_7639_();
            if ($$3 instanceof Player && !this.m_7099_((Player)$$3)) {
                return false;
            }
            if ($$3 instanceof AbstractArrow && ($$5 = ($$4 = (AbstractArrow)$$3).m_37282_()) instanceof Player && !this.m_7099_((Player)$$5)) {
                return false;
            }
        }
        return super.m_6469_(p_9037_, p_9038_);
    }

    @Override
    public boolean m_7099_(Player p_9064_) {
        if (!this.m_9216_()) {
            return false;
        }
        return super.m_7099_(p_9064_);
    }

    private boolean m_9216_() {
        return this.f_8924_.m_129799_();
    }

    @Override
    @Nullable
    protected PortalInfo m_7937_(ServerLevel p_8998_) {
        PortalInfo $$1 = super.m_7937_(p_8998_);
        if ($$1 != null && this.f_19853_.m_46472_() == Level.f_46428_ && p_8998_.m_46472_() == Level.f_46430_) {
            Vec3 $$2 = $$1.f_77676_.m_82520_(0.0, -1.0, 0.0);
            return new PortalInfo($$2, Vec3.f_82478_, 90.0f, 0.0f);
        }
        return $$1;
    }

    @Override
    @Nullable
    public Entity m_5489_(ServerLevel p_9180_) {
        this.f_8927_ = true;
        ServerLevel $$1 = this.m_9236_();
        ResourceKey<Level> $$2 = $$1.m_46472_();
        if ($$2 == Level.f_46430_ && p_9180_.m_46472_() == Level.f_46428_) {
            this.m_19877_();
            this.m_9236_().m_143261_(this, Entity.RemovalReason.CHANGED_DIMENSION);
            if (!this.f_8944_) {
                this.f_8944_ = true;
                this.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132157_, this.f_8928_ ? 0.0f : 1.0f));
                this.f_8928_ = true;
            }
            return this;
        }
        LevelData $$3 = p_9180_.m_6106_();
        this.f_8906_.m_9829_(new ClientboundRespawnPacket(p_9180_.m_220362_(), p_9180_.m_46472_(), BiomeManager.m_47877_(p_9180_.m_7328_()), this.f_8941_.m_9290_(), this.f_8941_.m_9293_(), p_9180_.m_46659_(), p_9180_.m_8584_(), true, this.m_219759_()));
        this.f_8906_.m_9829_(new ClientboundChangeDifficultyPacket($$3.m_5472_(), $$3.m_5474_()));
        PlayerList $$4 = this.f_8924_.m_6846_();
        $$4.m_11289_(this);
        $$1.m_143261_(this, Entity.RemovalReason.CHANGED_DIMENSION);
        this.m_146912_();
        PortalInfo $$5 = this.m_7937_(p_9180_);
        if ($$5 != null) {
            $$1.m_46473_().m_6180_("moving");
            if ($$2 == Level.f_46428_ && p_9180_.m_46472_() == Level.f_46429_) {
                this.f_8933_ = this.m_20182_();
            } else if (p_9180_.m_46472_() == Level.f_46430_) {
                this.m_9006_(p_9180_, new BlockPos($$5.f_77676_));
            }
            $$1.m_46473_().m_7238_();
            $$1.m_46473_().m_6180_("placing");
            this.m_143425_(p_9180_);
            p_9180_.m_8817_(this);
            this.m_19915_($$5.f_77678_, $$5.f_77679_);
            this.m_6027_($$5.f_77676_.f_82479_, $$5.f_77676_.f_82480_, $$5.f_77676_.f_82481_);
            $$1.m_46473_().m_7238_();
            this.m_9209_($$1);
            this.f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(this.m_150110_()));
            $$4.m_11229_(this, p_9180_);
            $$4.m_11292_(this);
            for (MobEffectInstance $$6 : this.m_21220_()) {
                this.f_8906_.m_9829_(new ClientboundUpdateMobEffectPacket(this.m_19879_(), $$6));
            }
            this.f_8906_.m_9829_(new ClientboundLevelEventPacket(1032, BlockPos.f_121853_, 0, false));
            this.f_8920_ = -1;
            this.f_8917_ = -1.0f;
            this.f_8918_ = -1;
        }
        return this;
    }

    private void m_9006_(ServerLevel p_9007_, BlockPos p_9008_) {
        BlockPos.MutableBlockPos $$2 = p_9008_.m_122032_();
        for (int $$3 = -2; $$3 <= 2; ++$$3) {
            for (int $$4 = -2; $$4 <= 2; ++$$4) {
                for (int $$5 = -1; $$5 < 3; ++$$5) {
                    BlockState $$6 = $$5 == -1 ? Blocks.f_50080_.m_49966_() : Blocks.f_50016_.m_49966_();
                    p_9007_.m_46597_($$2.m_122190_(p_9008_).m_122184_($$4, $$5, $$3), $$6);
                }
            }
        }
    }

    @Override
    protected Optional<BlockUtil.FoundRectangle> m_183318_(ServerLevel p_184131_, BlockPos p_184132_, boolean p_184133_, WorldBorder p_184134_) {
        Optional<BlockUtil.FoundRectangle> $$4 = super.m_183318_(p_184131_, p_184132_, p_184133_, p_184134_);
        if ($$4.isPresent()) {
            return $$4;
        }
        Direction.Axis $$5 = this.f_19853_.m_8055_(this.f_19819_).m_61145_(NetherPortalBlock.f_54904_).orElse(Direction.Axis.X);
        Optional<BlockUtil.FoundRectangle> $$6 = p_184131_.m_8871_().m_77666_(p_184132_, $$5);
        if (!$$6.isPresent()) {
            f_8907_.error("Unable to create a portal, likely target out of worldborder");
        }
        return $$6;
    }

    private void m_9209_(ServerLevel p_9210_) {
        ResourceKey<Level> $$1 = p_9210_.m_46472_();
        ResourceKey<Level> $$2 = this.f_19853_.m_46472_();
        CriteriaTriggers.f_10588_.m_19757_(this, $$1, $$2);
        if ($$1 == Level.f_46429_ && $$2 == Level.f_46428_ && this.f_8933_ != null) {
            CriteriaTriggers.f_10552_.m_186165_(this, this.f_8933_);
        }
        if ($$2 != Level.f_46429_) {
            this.f_8933_ = null;
        }
    }

    @Override
    public boolean m_6459_(ServerPlayer p_9014_) {
        if (p_9014_.m_5833_()) {
            return this.m_8954_() == this;
        }
        if (this.m_5833_()) {
            return false;
        }
        return super.m_6459_(p_9014_);
    }

    @Override
    public void m_7938_(Entity p_9047_, int p_9048_) {
        super.m_7938_(p_9047_, p_9048_);
        this.f_36096_.m_38946_();
    }

    @Override
    public Either<Player.BedSleepingProblem, Unit> m_7720_(BlockPos p_9115_) {
        Direction $$1 = this.f_19853_.m_8055_(p_9115_).m_61143_(HorizontalDirectionalBlock.f_54117_);
        if (this.m_5803_() || !this.m_6084_()) {
            return Either.left((Object)((Object)Player.BedSleepingProblem.OTHER_PROBLEM));
        }
        if (!this.f_19853_.m_6042_().f_63858_()) {
            return Either.left((Object)((Object)Player.BedSleepingProblem.NOT_POSSIBLE_HERE));
        }
        if (!this.m_9116_(p_9115_, $$1)) {
            return Either.left((Object)((Object)Player.BedSleepingProblem.TOO_FAR_AWAY));
        }
        if (this.m_9191_(p_9115_, $$1)) {
            return Either.left((Object)((Object)Player.BedSleepingProblem.OBSTRUCTED));
        }
        this.m_9158_(this.f_19853_.m_46472_(), p_9115_, this.m_146908_(), false, true);
        if (this.f_19853_.m_46461_()) {
            return Either.left((Object)((Object)Player.BedSleepingProblem.NOT_POSSIBLE_NOW));
        }
        if (!this.m_7500_()) {
            double $$2 = 8.0;
            double $$3 = 5.0;
            Vec3 $$4 = Vec3.m_82539_(p_9115_);
            List<Monster> $$5 = this.f_19853_.m_6443_(Monster.class, new AABB($$4.m_7096_() - 8.0, $$4.m_7098_() - 5.0, $$4.m_7094_() - 8.0, $$4.m_7096_() + 8.0, $$4.m_7098_() + 5.0, $$4.m_7094_() + 8.0), p_9062_ -> p_9062_.m_6935_(this));
            if (!$$5.isEmpty()) {
                return Either.left((Object)((Object)Player.BedSleepingProblem.NOT_SAFE));
            }
        }
        Either $$6 = super.m_7720_(p_9115_).ifRight(p_9029_ -> {
            this.m_36220_(Stats.f_12969_);
            CriteriaTriggers.f_10583_.m_222618_(this);
        });
        if (!this.m_9236_().m_143333_()) {
            this.m_5661_(Component.m_237115_("sleep.not_possible"), true);
        }
        ((ServerLevel)this.f_19853_).m_8878_();
        return $$6;
    }

    @Override
    public void m_5802_(BlockPos p_9190_) {
        this.m_7166_(Stats.f_12988_.m_12902_(Stats.f_12992_));
        super.m_5802_(p_9190_);
    }

    private boolean m_9116_(BlockPos p_9117_, Direction p_9118_) {
        return this.m_9222_(p_9117_) || this.m_9222_(p_9117_.m_121945_(p_9118_.m_122424_()));
    }

    private boolean m_9222_(BlockPos p_9223_) {
        Vec3 $$1 = Vec3.m_82539_(p_9223_);
        return Math.abs(this.m_20185_() - $$1.m_7096_()) <= 3.0 && Math.abs(this.m_20186_() - $$1.m_7098_()) <= 2.0 && Math.abs(this.m_20189_() - $$1.m_7094_()) <= 3.0;
    }

    private boolean m_9191_(BlockPos p_9192_, Direction p_9193_) {
        BlockPos $$2 = p_9192_.m_7494_();
        return !this.m_36350_($$2) || !this.m_36350_($$2.m_121945_(p_9193_.m_122424_()));
    }

    @Override
    public void m_6145_(boolean p_9165_, boolean p_9166_) {
        if (this.m_5803_()) {
            this.m_9236_().m_7726_().m_8394_(this, new ClientboundAnimatePacket(this, 2));
        }
        super.m_6145_(p_9165_, p_9166_);
        if (this.f_8906_ != null) {
            this.f_8906_.m_9774_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
        }
    }

    @Override
    public boolean m_7998_(Entity p_9054_, boolean p_9055_) {
        Entity $$2 = this.m_20202_();
        if (!super.m_7998_(p_9054_, p_9055_)) {
            return false;
        }
        Entity $$3 = this.m_20202_();
        if ($$3 != $$2 && this.f_8906_ != null) {
            this.f_8906_.m_9774_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
        }
        return true;
    }

    @Override
    public void m_8127_() {
        Entity $$0 = this.m_20202_();
        super.m_8127_();
        Entity $$1 = this.m_20202_();
        if ($$1 != $$0 && this.f_8906_ != null) {
            this.f_8906_.m_143611_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
        }
    }

    @Override
    public void m_142098_(double p_143389_, double p_143390_, double p_143391_) {
        this.m_6038_();
        if (this.f_8906_ != null) {
            this.f_8906_.m_143611_(p_143389_, p_143390_, p_143391_, this.m_146908_(), this.m_146909_());
        }
    }

    @Override
    public boolean m_6673_(DamageSource p_9182_) {
        return super.m_6673_(p_9182_) || this.m_8958_() || this.m_150110_().f_35934_ && p_9182_ == DamageSource.f_19320_;
    }

    @Override
    protected void m_7840_(double p_8976_, boolean p_8977_, BlockState p_8978_, BlockPos p_8979_) {
    }

    @Override
    protected void m_5806_(BlockPos p_9206_) {
        if (!this.m_5833_()) {
            super.m_5806_(p_9206_);
        }
    }

    public void m_8972_(double p_8973_, boolean p_8974_) {
        if (this.m_146899_()) {
            return;
        }
        BlockPos $$2 = this.m_216999_();
        super.m_7840_(p_8973_, p_8974_, this.f_19853_.m_8055_($$2), $$2);
    }

    @Override
    public void m_7739_(SignBlockEntity p_9101_) {
        p_9101_.m_155713_(this.m_20148_());
        this.f_8906_.m_9829_(new ClientboundBlockUpdatePacket(this.f_19853_, p_9101_.m_58899_()));
        this.f_8906_.m_9829_(new ClientboundOpenSignEditorPacket(p_9101_.m_58899_()));
    }

    private void m_9217_() {
        this.f_8940_ = this.f_8940_ % 100 + 1;
    }

    @Override
    public OptionalInt m_5893_(@Nullable MenuProvider p_9033_) {
        if (p_9033_ == null) {
            return OptionalInt.empty();
        }
        if (this.f_36096_ != this.f_36095_) {
            this.m_6915_();
        }
        this.m_9217_();
        AbstractContainerMenu $$1 = p_9033_.m_7208_(this.f_8940_, this.m_150109_(), this);
        if ($$1 == null) {
            if (this.m_5833_()) {
                this.m_5661_(Component.m_237115_("container.spectatorCantOpen").m_130940_(ChatFormatting.RED), true);
            }
            return OptionalInt.empty();
        }
        this.f_8906_.m_9829_(new ClientboundOpenScreenPacket($$1.f_38840_, $$1.m_6772_(), p_9033_.m_5446_()));
        this.m_143399_($$1);
        this.f_36096_ = $$1;
        return OptionalInt.of(this.f_8940_);
    }

    @Override
    public void m_7662_(int p_8988_, MerchantOffers p_8989_, int p_8990_, int p_8991_, boolean p_8992_, boolean p_8993_) {
        this.f_8906_.m_9829_(new ClientboundMerchantOffersPacket(p_8988_, p_8989_, p_8990_, p_8991_, p_8992_, p_8993_));
    }

    @Override
    public void m_6658_(AbstractHorse p_9059_, Container p_9060_) {
        if (this.f_36096_ != this.f_36095_) {
            this.m_6915_();
        }
        this.m_9217_();
        this.f_8906_.m_9829_(new ClientboundHorseScreenOpenPacket(this.f_8940_, p_9060_.m_6643_(), p_9059_.m_19879_()));
        this.f_36096_ = new HorseInventoryMenu(this.f_8940_, this.m_150109_(), p_9060_, p_9059_);
        this.m_143399_(this.f_36096_);
    }

    @Override
    public void m_6986_(ItemStack p_9082_, InteractionHand p_9083_) {
        if (p_9082_.m_150930_(Items.f_42615_)) {
            if (WrittenBookItem.m_43461_(p_9082_, this.m_20203_(), this)) {
                this.f_36096_.m_38946_();
            }
            this.f_8906_.m_9829_(new ClientboundOpenBookPacket(p_9083_));
        }
    }

    @Override
    public void m_7698_(CommandBlockEntity p_9099_) {
        this.f_8906_.m_9829_(ClientboundBlockEntityDataPacket.m_195642_(p_9099_, BlockEntity::m_187482_));
    }

    @Override
    public void m_6915_() {
        this.f_8906_.m_9829_(new ClientboundContainerClosePacket(this.f_36096_.f_38840_));
        this.m_9230_();
    }

    public void m_9230_() {
        this.f_36096_.m_6877_(this);
        this.f_36095_.m_150414_(this.f_36096_);
        this.f_36096_ = this.f_36095_;
    }

    public void m_8980_(float p_8981_, float p_8982_, boolean p_8983_, boolean p_8984_) {
        if (this.m_20159_()) {
            if (p_8981_ >= -1.0f && p_8981_ <= 1.0f) {
                this.f_20900_ = p_8981_;
            }
            if (p_8982_ >= -1.0f && p_8982_ <= 1.0f) {
                this.f_20902_ = p_8982_;
            }
            this.f_20899_ = p_8983_;
            this.m_20260_(p_8984_);
        }
    }

    @Override
    public void m_6278_(Stat<?> p_9026_, int p_9027_) {
        this.f_8910_.m_13023_(this, p_9026_, p_9027_);
        this.m_36329_().m_83427_(p_9026_, this.m_6302_(), p_8996_ -> p_8996_.m_83393_(p_9027_));
    }

    @Override
    public void m_7166_(Stat<?> p_9024_) {
        this.f_8910_.m_6085_(this, p_9024_, 0);
        this.m_36329_().m_83427_(p_9024_, this.m_6302_(), Score::m_83401_);
    }

    @Override
    public int m_7281_(Collection<Recipe<?>> p_9129_) {
        return this.f_8929_.m_12791_(p_9129_, this);
    }

    @Override
    public void m_7902_(ResourceLocation[] p_9168_) {
        ArrayList $$1 = Lists.newArrayList();
        for (ResourceLocation $$2 : p_9168_) {
            this.f_8924_.m_129894_().m_44043_($$2).ifPresent($$1::add);
        }
        this.m_7281_($$1);
    }

    @Override
    public int m_7279_(Collection<Recipe<?>> p_9195_) {
        return this.f_8929_.m_12806_(p_9195_, this);
    }

    @Override
    public void m_6756_(int p_9208_) {
        super.m_6756_(p_9208_);
        this.f_8920_ = -1;
    }

    public void m_9231_() {
        this.f_8932_ = true;
        this.m_20153_();
        if (this.m_5803_()) {
            this.m_6145_(true, false);
        }
    }

    public boolean m_9232_() {
        return this.f_8932_;
    }

    public void m_9233_() {
        this.f_8917_ = -1.0E8f;
    }

    @Override
    public void m_5661_(Component p_9154_, boolean p_9155_) {
        this.m_240418_(p_9154_, p_9155_);
    }

    @Override
    protected void m_8095_() {
        if (!this.f_20935_.m_41619_() && this.m_6117_()) {
            this.f_8906_.m_9829_(new ClientboundEntityEventPacket(this, 9));
            super.m_8095_();
        }
    }

    @Override
    public void m_7618_(EntityAnchorArgument.Anchor p_9112_, Vec3 p_9113_) {
        super.m_7618_(p_9112_, p_9113_);
        this.f_8906_.m_9829_(new ClientboundPlayerLookAtPacket(p_9112_, p_9113_.f_82479_, p_9113_.f_82480_, p_9113_.f_82481_));
    }

    public void m_9107_(EntityAnchorArgument.Anchor p_9108_, Entity p_9109_, EntityAnchorArgument.Anchor p_9110_) {
        Vec3 $$3 = p_9110_.m_90377_(p_9109_);
        super.m_7618_(p_9108_, $$3);
        this.f_8906_.m_9829_(new ClientboundPlayerLookAtPacket(p_9108_, p_9109_, p_9110_));
    }

    public void m_9015_(ServerPlayer p_9016_, boolean p_9017_) {
        this.f_143378_ = p_9016_.f_143378_;
        this.f_8941_.m_9273_(p_9016_.f_8941_.m_9290_(), p_9016_.f_8941_.m_9293_());
        if (p_9017_) {
            this.m_150109_().m_36006_(p_9016_.m_150109_());
            this.m_21153_(p_9016_.m_21223_());
            this.f_36097_ = p_9016_.f_36097_;
            this.f_36078_ = p_9016_.f_36078_;
            this.f_36079_ = p_9016_.f_36079_;
            this.f_36080_ = p_9016_.f_36080_;
            this.m_36397_(p_9016_.m_36344_());
            this.f_19819_ = p_9016_.f_19819_;
        } else if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46133_) || p_9016_.m_5833_()) {
            this.m_150109_().m_36006_(p_9016_.m_150109_());
            this.f_36078_ = p_9016_.f_36078_;
            this.f_36079_ = p_9016_.f_36079_;
            this.f_36080_ = p_9016_.f_36080_;
            this.m_36397_(p_9016_.m_36344_());
        }
        this.f_36081_ = p_9016_.f_36081_;
        this.f_36094_ = p_9016_.f_36094_;
        this.m_20088_().m_135381_(f_36089_, (Byte)p_9016_.m_20088_().m_135370_(f_36089_));
        this.f_8920_ = -1;
        this.f_8917_ = -1.0f;
        this.f_8918_ = -1;
        this.f_8929_.m_12685_(p_9016_.f_8929_);
        this.f_8928_ = p_9016_.f_8928_;
        this.f_8933_ = p_9016_.f_8933_;
        this.m_36362_(p_9016_.m_36331_());
        this.m_36364_(p_9016_.m_36332_());
        this.m_219749_(p_9016_.m_219759_());
    }

    @Override
    protected void m_142540_(MobEffectInstance p_143393_, @Nullable Entity p_143394_) {
        super.m_142540_(p_143393_, p_143394_);
        this.f_8906_.m_9829_(new ClientboundUpdateMobEffectPacket(this.m_19879_(), p_143393_));
        if (p_143393_.m_19544_() == MobEffects.f_19620_) {
            this.f_8931_ = this.f_19797_;
            this.f_8930_ = this.m_20182_();
        }
        CriteriaTriggers.f_10550_.m_149262_(this, p_143394_);
    }

    @Override
    protected void m_141973_(MobEffectInstance p_143396_, boolean p_143397_, @Nullable Entity p_143398_) {
        super.m_141973_(p_143396_, p_143397_, p_143398_);
        this.f_8906_.m_9829_(new ClientboundUpdateMobEffectPacket(this.m_19879_(), p_143396_));
        CriteriaTriggers.f_10550_.m_149262_(this, p_143398_);
    }

    @Override
    protected void m_7285_(MobEffectInstance p_9184_) {
        super.m_7285_(p_9184_);
        this.f_8906_.m_9829_(new ClientboundRemoveMobEffectPacket(this.m_19879_(), p_9184_.m_19544_()));
        if (p_9184_.m_19544_() == MobEffects.f_19620_) {
            this.f_8930_ = null;
        }
        CriteriaTriggers.f_10550_.m_149262_(this, null);
    }

    @Override
    public void m_6021_(double p_8969_, double p_8970_, double p_8971_) {
        this.f_8906_.m_9774_(p_8969_, p_8970_, p_8971_, this.m_146908_(), this.m_146909_());
    }

    @Override
    public void m_6027_(double p_9171_, double p_9172_, double p_9173_) {
        this.m_6021_(p_9171_, p_9172_, p_9173_);
        this.f_8906_.m_9953_();
    }

    @Override
    public void m_5704_(Entity p_9045_) {
        this.m_9236_().m_7726_().m_8394_(this, new ClientboundAnimatePacket(p_9045_, 4));
    }

    @Override
    public void m_5700_(Entity p_9186_) {
        this.m_9236_().m_7726_().m_8394_(this, new ClientboundAnimatePacket(p_9186_, 5));
    }

    @Override
    public void m_6885_() {
        if (this.f_8906_ == null) {
            return;
        }
        this.f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(this.m_150110_()));
        this.m_8034_();
    }

    @Override
    public ServerLevel m_9236_() {
        return (ServerLevel)this.f_19853_;
    }

    public boolean m_143403_(GameType p_143404_) {
        if (!this.f_8941_.m_143473_(p_143404_)) {
            return false;
        }
        this.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132156_, p_143404_.m_46392_()));
        if (p_143404_ == GameType.SPECTATOR) {
            this.m_36328_();
            this.m_8127_();
        } else {
            this.m_9213_(this);
        }
        this.m_6885_();
        this.m_21210_();
        return true;
    }

    @Override
    public boolean m_5833_() {
        return this.f_8941_.m_9290_() == GameType.SPECTATOR;
    }

    @Override
    public boolean m_7500_() {
        return this.f_8941_.m_9290_() == GameType.CREATIVE;
    }

    @Override
    public void m_213846_(Component p_215097_) {
        this.m_240418_(p_215097_, false);
    }

    public void m_240418_(Component p_240560_, boolean p_240545_) {
        if (!this.m_240399_(p_240545_)) {
            return;
        }
        this.f_8906_.m_243119_(new ClientboundSystemChatPacket(p_240560_, p_240545_), PacketSendListener.m_243073_(() -> {
            if (this.m_240399_(false)) {
                int $$1 = 256;
                String $$2 = p_240560_.m_130668_(256);
                MutableComponent $$3 = Component.m_237113_($$2).m_130940_(ChatFormatting.YELLOW);
                return new ClientboundSystemChatPacket(Component.m_237110_("multiplayer.message_not_delivered", $$3).m_130940_(ChatFormatting.RED), false);
            }
            return null;
        }));
    }

    public void m_243093_(OutgoingPlayerChatMessage p_243284_, boolean p_243285_, ChatType.Bound p_243314_) {
        if (this.m_240422_()) {
            p_243284_.m_241045_(this, p_243285_, p_243314_);
        }
    }

    public void m_241183_(SignedMessageHeader p_241477_, MessageSignature p_241450_, byte[] p_241356_) {
        if (this.m_240422_()) {
            this.f_8906_.m_9829_(new ClientboundPlayerChatHeaderPacket(p_241477_, p_241450_, p_241356_));
        }
    }

    public String m_9239_() {
        String $$0 = this.f_8906_.f_9742_.m_129523_().toString();
        $$0 = $$0.substring($$0.indexOf("/") + 1);
        $$0 = $$0.substring(0, $$0.indexOf(":"));
        return $$0;
    }

    public void m_9156_(ServerboundClientInformationPacket p_9157_) {
        this.f_8922_ = p_9157_.f_133865_();
        this.f_8923_ = p_9157_.f_133866_();
        this.f_143378_ = p_9157_.f_179550_();
        this.f_184127_ = p_9157_.f_195812_();
        this.m_20088_().m_135381_(f_36089_, (byte)p_9157_.f_133867_());
        this.m_20088_().m_135381_(f_36090_, (byte)(p_9157_.f_133868_() != HumanoidArm.LEFT ? 1 : 0));
    }

    public boolean m_143432_() {
        return this.f_8923_;
    }

    public ChatVisiblity m_9241_() {
        return this.f_8922_;
    }

    private boolean m_240399_(boolean p_240568_) {
        if (this.f_8922_ == ChatVisiblity.HIDDEN) {
            return p_240568_;
        }
        return true;
    }

    private boolean m_240422_() {
        return this.f_8922_ == ChatVisiblity.FULL;
    }

    public void m_143408_(String p_143409_, String p_143410_, boolean p_143411_, @Nullable Component p_143412_) {
        this.f_8906_.m_9829_(new ClientboundResourcePackPacket(p_143409_, p_143410_, p_143411_, p_143412_));
    }

    public void m_215109_(ServerStatus p_215110_) {
        this.f_8906_.m_9829_(new ClientboundServerDataPacket(p_215110_.m_134905_(), p_215110_.m_134916_(), p_215110_.m_238080_(), p_215110_.m_242963_()));
    }

    @Override
    protected int m_8088_() {
        return this.f_8924_.m_129944_(this.m_36316_());
    }

    public void m_9243_() {
        this.f_8925_ = Util.m_137550_();
    }

    public ServerStatsCounter m_8951_() {
        return this.f_8910_;
    }

    public ServerRecipeBook m_8952_() {
        return this.f_8929_;
    }

    @Override
    protected void m_8034_() {
        if (this.m_5833_()) {
            this.m_21218_();
            this.m_6842_(true);
        } else {
            super.m_8034_();
        }
    }

    public Entity m_8954_() {
        return this.f_8926_ == null ? this : this.f_8926_;
    }

    public void m_9213_(@Nullable Entity p_9214_) {
        Entity $$1 = this.m_8954_();
        Entity entity = this.f_8926_ = p_9214_ == null ? this : p_9214_;
        if ($$1 != this.f_8926_) {
            this.f_8906_.m_9829_(new ClientboundSetCameraPacket(this.f_8926_));
            this.m_6021_(this.f_8926_.m_20185_(), this.f_8926_.m_20186_(), this.f_8926_.m_20189_());
        }
    }

    @Override
    protected void m_8021_() {
        if (!this.f_8927_) {
            super.m_8021_();
        }
    }

    @Override
    public void m_5706_(Entity p_9220_) {
        if (this.f_8941_.m_9290_() == GameType.SPECTATOR) {
            this.m_9213_(p_9220_);
        } else {
            super.m_5706_(p_9220_);
        }
    }

    public long m_8956_() {
        return this.f_8925_;
    }

    @Nullable
    public Component m_8957_() {
        return null;
    }

    @Override
    public void m_6674_(InteractionHand p_9031_) {
        super.m_6674_(p_9031_);
        this.m_36334_();
    }

    public boolean m_8958_() {
        return this.f_8927_;
    }

    public void m_8959_() {
        this.f_8927_ = false;
    }

    public PlayerAdvancements m_8960_() {
        return this.f_8909_;
    }

    public void m_8999_(ServerLevel p_9000_, double p_9001_, double p_9002_, double p_9003_, float p_9004_, float p_9005_) {
        this.m_9213_(this);
        this.m_8127_();
        if (p_9000_ == this.f_19853_) {
            this.f_8906_.m_9774_(p_9001_, p_9002_, p_9003_, p_9004_, p_9005_);
        } else {
            ServerLevel $$6 = this.m_9236_();
            LevelData $$7 = p_9000_.m_6106_();
            this.f_8906_.m_9829_(new ClientboundRespawnPacket(p_9000_.m_220362_(), p_9000_.m_46472_(), BiomeManager.m_47877_(p_9000_.m_7328_()), this.f_8941_.m_9290_(), this.f_8941_.m_9293_(), p_9000_.m_46659_(), p_9000_.m_8584_(), true, this.m_219759_()));
            this.f_8906_.m_9829_(new ClientboundChangeDifficultyPacket($$7.m_5472_(), $$7.m_5474_()));
            this.f_8924_.m_6846_().m_11289_(this);
            $$6.m_143261_(this, Entity.RemovalReason.CHANGED_DIMENSION);
            this.m_146912_();
            this.m_7678_(p_9001_, p_9002_, p_9003_, p_9004_, p_9005_);
            this.m_143425_(p_9000_);
            p_9000_.m_8622_(this);
            this.m_9209_($$6);
            this.f_8906_.m_9774_(p_9001_, p_9002_, p_9003_, p_9004_, p_9005_);
            this.f_8924_.m_6846_().m_11229_(this, p_9000_);
            this.f_8924_.m_6846_().m_11292_(this);
        }
    }

    @Nullable
    public BlockPos m_8961_() {
        return this.f_8936_;
    }

    public float m_8962_() {
        return this.f_8938_;
    }

    public ResourceKey<Level> m_8963_() {
        return this.f_8935_;
    }

    public boolean m_8964_() {
        return this.f_8937_;
    }

    public void m_9158_(ResourceKey<Level> p_9159_, @Nullable BlockPos p_9160_, float p_9161_, boolean p_9162_, boolean p_9163_) {
        if (p_9160_ != null) {
            boolean $$5;
            boolean bl = $$5 = p_9160_.equals(this.f_8936_) && p_9159_.equals(this.f_8935_);
            if (p_9163_ && !$$5) {
                this.m_213846_(Component.m_237115_("block.minecraft.set_spawn"));
            }
            this.f_8936_ = p_9160_;
            this.f_8935_ = p_9159_;
            this.f_8938_ = p_9161_;
            this.f_8937_ = p_9162_;
        } else {
            this.f_8936_ = null;
            this.f_8935_ = Level.f_46428_;
            this.f_8938_ = 0.0f;
            this.f_8937_ = false;
        }
    }

    public void m_184135_(ChunkPos p_184136_, Packet<?> p_184137_) {
        this.f_8906_.m_9829_(p_184137_);
    }

    public void m_9088_(ChunkPos p_9089_) {
        if (this.m_6084_()) {
            this.f_8906_.m_9829_(new ClientboundForgetLevelChunkPacket(p_9089_.f_45578_, p_9089_.f_45579_));
        }
    }

    public SectionPos m_8965_() {
        return this.f_8934_;
    }

    public void m_9119_(SectionPos p_9120_) {
        this.f_8934_ = p_9120_;
    }

    @Override
    public void m_6330_(SoundEvent p_9019_, SoundSource p_9020_, float p_9021_, float p_9022_) {
        this.f_8906_.m_9829_(new ClientboundSoundPacket(p_9019_, p_9020_, this.m_20185_(), this.m_20186_(), this.m_20189_(), p_9021_, p_9022_, this.f_19796_.m_188505_()));
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddPlayerPacket(this);
    }

    @Override
    public ItemEntity m_7197_(ItemStack p_9085_, boolean p_9086_, boolean p_9087_) {
        ItemEntity $$3 = super.m_7197_(p_9085_, p_9086_, p_9087_);
        if ($$3 == null) {
            return null;
        }
        this.f_19853_.m_7967_($$3);
        ItemStack $$4 = $$3.m_32055_();
        if (p_9087_) {
            if (!$$4.m_41619_()) {
                this.m_6278_(Stats.f_12985_.m_12902_($$4.m_41720_()), p_9085_.m_41613_());
            }
            this.m_36220_(Stats.f_12927_);
        }
        return $$3;
    }

    public TextFilter m_8967_() {
        return this.f_8939_;
    }

    public void m_143425_(ServerLevel p_143426_) {
        this.f_19853_ = p_143426_;
        this.f_8941_.m_9260_(p_143426_);
    }

    @Nullable
    private static GameType m_143413_(@Nullable CompoundTag p_143414_, String p_143415_) {
        return p_143414_ != null && p_143414_.m_128425_(p_143415_, 99) ? GameType.m_46393_(p_143414_.m_128451_(p_143415_)) : null;
    }

    private GameType m_143423_(@Nullable GameType p_143424_) {
        GameType $$1 = this.f_8924_.m_142359_();
        if ($$1 != null) {
            return $$1;
        }
        return p_143424_ != null ? p_143424_ : this.f_8924_.m_130008_();
    }

    public void m_143427_(@Nullable CompoundTag p_143428_) {
        this.f_8941_.m_9273_(this.m_143423_(ServerPlayer.m_143413_(p_143428_, "playerGameType")), ServerPlayer.m_143413_(p_143428_, "previousPlayerGameType"));
    }

    private void m_143430_(CompoundTag p_143431_) {
        p_143431_.m_128405_("playerGameType", this.f_8941_.m_9290_().m_46392_());
        GameType $$1 = this.f_8941_.m_9293_();
        if ($$1 != null) {
            p_143431_.m_128405_("previousPlayerGameType", $$1.m_46392_());
        }
    }

    public boolean m_143387_() {
        return this.f_143378_;
    }

    public boolean m_143421_(ServerPlayer p_143422_) {
        if (p_143422_ == this) {
            return false;
        }
        return this.f_143378_ || p_143422_.f_143378_;
    }

    @Override
    public boolean m_142265_(Level p_143406_, BlockPos p_143407_) {
        return super.m_142265_(p_143406_, p_143407_) && p_143406_.m_7966_(this, p_143407_);
    }

    @Override
    protected void m_142106_(ItemStack p_143402_) {
        CriteriaTriggers.f_145090_.m_163865_(this, p_143402_);
        super.m_142106_(p_143402_);
    }

    public boolean m_182294_(boolean p_182295_) {
        Inventory $$1 = this.m_150109_();
        ItemStack $$2 = $$1.m_182403_(p_182295_);
        this.f_36096_.m_182417_($$1, $$1.f_35977_).ifPresent(p_182293_ -> this.f_36096_.m_150404_(p_182293_, $$1.m_36056_()));
        return this.m_7197_($$2, false, true) != null;
    }

    public boolean m_184128_() {
        return this.f_184127_;
    }

    @Override
    public void m_21053_(ItemEntity p_215095_) {
        Entity $$1;
        super.m_21053_(p_215095_);
        Entity entity = $$1 = p_215095_.m_32057_() != null ? this.m_9236_().m_8791_(p_215095_.m_32057_()) : null;
        if ($$1 != null) {
            CriteriaTriggers.f_215655_.m_221298_(this, p_215095_.m_32055_(), $$1);
        }
    }

    @Override
    public /* synthetic */ Level m_9236_() {
        return this.m_9236_();
    }
}

