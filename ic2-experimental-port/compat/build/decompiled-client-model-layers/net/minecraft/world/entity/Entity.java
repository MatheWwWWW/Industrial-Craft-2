/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2DoubleMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.slf4j.Logger;

public abstract class Entity
implements Nameable,
EntityAccess,
CommandSource {
    private static final Logger f_19849_ = LogUtils.getLogger();
    public static final String f_146815_ = "id";
    public static final String f_146816_ = "Passengers";
    private static final AtomicInteger f_19843_ = new AtomicInteger();
    private static final List<ItemStack> f_19844_ = Collections.emptyList();
    public static final int f_146817_ = 60;
    public static final int f_146818_ = 300;
    public static final int f_146819_ = 1024;
    public static final double f_146820_ = 0.5000001;
    public static final float f_146821_ = 0.11111111f;
    public static final int f_146822_ = 140;
    public static final int f_146823_ = 40;
    private static final AABB f_19845_ = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    private static final double f_146814_ = 0.014;
    private static final double f_146811_ = 0.007;
    private static final double f_146812_ = 0.0023333333333333335;
    public static final String f_146824_ = "UUID";
    private static double f_19846_ = 1.0;
    private final EntityType<?> f_19847_;
    private int f_19848_ = f_19843_.incrementAndGet();
    public boolean f_19850_;
    private ImmutableList<Entity> f_19823_ = ImmutableList.of();
    protected int f_19851_;
    @Nullable
    private Entity f_19824_;
    public Level f_19853_;
    public double f_19854_;
    public double f_19855_;
    public double f_19856_;
    private Vec3 f_19825_;
    private BlockPos f_19826_;
    private ChunkPos f_185933_;
    private Vec3 f_19827_ = Vec3.f_82478_;
    private float f_19857_;
    private float f_19858_;
    public float f_19859_;
    public float f_19860_;
    private AABB f_19828_ = f_19845_;
    protected boolean f_19861_;
    public boolean f_19862_;
    public boolean f_19863_;
    public boolean f_201939_;
    public boolean f_185931_;
    public boolean f_19864_;
    protected Vec3 f_19865_ = Vec3.f_82478_;
    @Nullable
    private RemovalReason f_146795_;
    public static final float f_146792_ = 0.6f;
    public static final float f_146793_ = 1.8f;
    public float f_19867_;
    public float f_19787_;
    public float f_19788_;
    public float f_146794_;
    public float f_19789_;
    private float f_19829_ = 1.0f;
    public double f_19790_;
    public double f_19791_;
    public double f_19792_;
    public float f_19793_;
    public boolean f_19794_;
    protected final RandomSource f_19796_ = RandomSource.m_216327_();
    public int f_19797_;
    private int f_19831_ = -this.m_6101_();
    protected boolean f_19798_;
    protected Object2DoubleMap<TagKey<Fluid>> f_19799_ = new Object2DoubleArrayMap(2);
    protected boolean f_19800_;
    private final Set<TagKey<Fluid>> f_19801_ = new HashSet<TagKey<Fluid>>();
    public int f_19802_;
    protected boolean f_19803_ = true;
    protected final SynchedEntityData f_19804_;
    protected static final EntityDataAccessor<Byte> f_19805_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135027_);
    protected static final int f_146805_ = 0;
    private static final int f_146796_ = 1;
    private static final int f_146797_ = 3;
    private static final int f_146798_ = 4;
    private static final int f_146799_ = 5;
    protected static final int f_146806_ = 6;
    protected static final int f_146807_ = 7;
    private static final EntityDataAccessor<Integer> f_19832_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Optional<Component>> f_19833_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135032_);
    private static final EntityDataAccessor<Boolean> f_19834_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_19835_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_19836_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135035_);
    protected static final EntityDataAccessor<Pose> f_19806_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135045_);
    private static final EntityDataAccessor<Integer> f_146800_ = SynchedEntityData.m_135353_(Entity.class, EntityDataSerializers.f_135028_);
    private EntityInLevelCallback f_146801_ = EntityInLevelCallback.f_156799_;
    private final VecDeltaCodec f_216985_ = new VecDeltaCodec();
    public boolean f_19811_;
    public boolean f_19812_;
    private int f_19839_;
    protected boolean f_19817_;
    protected int f_19818_;
    protected BlockPos f_19819_;
    private boolean f_19840_;
    protected UUID f_19820_ = Mth.m_216261_(this.f_19796_);
    protected String f_19821_ = this.f_19820_.toString();
    private boolean f_146802_;
    private final Set<String> f_19841_ = Sets.newHashSet();
    private final double[] f_19813_ = new double[]{0.0, 0.0, 0.0};
    private long f_19814_;
    private EntityDimensions f_19815_;
    private float f_19816_;
    public boolean f_146808_;
    public boolean f_146809_;
    public boolean f_146810_;
    private float f_146803_;
    private int f_146804_;
    private boolean f_146813_;
    @Nullable
    private BlockState f_185934_ = null;

    public Entity(EntityType<?> p_19870_, Level p_19871_) {
        this.f_19847_ = p_19870_;
        this.f_19853_ = p_19871_;
        this.f_19815_ = p_19870_.m_20680_();
        this.f_19825_ = Vec3.f_82478_;
        this.f_19826_ = BlockPos.f_121853_;
        this.f_185933_ = ChunkPos.f_186419_;
        this.f_19804_ = new SynchedEntityData(this);
        this.f_19804_.m_135372_(f_19805_, (byte)0);
        this.f_19804_.m_135372_(f_19832_, this.m_6062_());
        this.f_19804_.m_135372_(f_19834_, false);
        this.f_19804_.m_135372_(f_19833_, Optional.empty());
        this.f_19804_.m_135372_(f_19835_, false);
        this.f_19804_.m_135372_(f_19836_, false);
        this.f_19804_.m_135372_(f_19806_, Pose.STANDING);
        this.f_19804_.m_135372_(f_146800_, 0);
        this.m_8097_();
        this.m_6034_(0.0, 0.0, 0.0);
        this.f_19816_ = this.m_6380_(Pose.STANDING, this.f_19815_);
    }

    public boolean m_20039_(BlockPos p_20040_, BlockState p_20041_) {
        VoxelShape $$2 = p_20041_.m_60742_(this.f_19853_, p_20040_, CollisionContext.m_82750_(this));
        VoxelShape $$3 = $$2.m_83216_(p_20040_.m_123341_(), p_20040_.m_123342_(), p_20040_.m_123343_());
        return Shapes.m_83157_($$3, Shapes.m_83064_(this.m_20191_()), BooleanOp.f_82689_);
    }

    public int m_19876_() {
        Team $$0 = this.m_5647_();
        if ($$0 != null && $$0.m_7414_().m_126665_() != null) {
            return $$0.m_7414_().m_126665_();
        }
        return 0xFFFFFF;
    }

    public boolean m_5833_() {
        return false;
    }

    public final void m_19877_() {
        if (this.m_20160_()) {
            this.m_20153_();
        }
        if (this.m_20159_()) {
            this.m_8127_();
        }
    }

    public void m_217006_(double p_217007_, double p_217008_, double p_217009_) {
        this.f_216985_.m_238033_(new Vec3(p_217007_, p_217008_, p_217009_));
    }

    public VecDeltaCodec m_217001_() {
        return this.f_216985_;
    }

    public EntityType<?> m_6095_() {
        return this.f_19847_;
    }

    @Override
    public int m_19879_() {
        return this.f_19848_;
    }

    public void m_20234_(int p_20235_) {
        this.f_19848_ = p_20235_;
    }

    public Set<String> m_19880_() {
        return this.f_19841_;
    }

    public boolean m_20049_(String p_20050_) {
        if (this.f_19841_.size() >= 1024) {
            return false;
        }
        return this.f_19841_.add(p_20050_);
    }

    public boolean m_20137_(String p_20138_) {
        return this.f_19841_.remove(p_20138_);
    }

    public void m_6074_() {
        this.m_142687_(RemovalReason.KILLED);
        this.m_146850_(GameEvent.f_223707_);
    }

    public final void m_146870_() {
        this.m_142687_(RemovalReason.DISCARDED);
    }

    protected abstract void m_8097_();

    public SynchedEntityData m_20088_() {
        return this.f_19804_;
    }

    public boolean equals(Object p_20245_) {
        if (p_20245_ instanceof Entity) {
            return ((Entity)p_20245_).f_19848_ == this.f_19848_;
        }
        return false;
    }

    public int hashCode() {
        return this.f_19848_;
    }

    public void m_142687_(RemovalReason p_146834_) {
        this.m_142467_(p_146834_);
    }

    public void m_142036_() {
    }

    public void m_20124_(Pose p_20125_) {
        this.f_19804_.m_135381_(f_19806_, p_20125_);
    }

    public Pose m_20089_() {
        return this.f_19804_.m_135370_(f_19806_);
    }

    public boolean m_217003_(Pose p_217004_) {
        return this.m_20089_() == p_217004_;
    }

    public boolean m_19950_(Entity p_19951_, double p_19952_) {
        return this.m_20182_().m_82509_(p_19951_.m_20182_(), p_19952_);
    }

    public boolean m_216992_(Entity p_216993_, double p_216994_, double p_216995_) {
        double $$3 = p_216993_.m_20185_() - this.m_20185_();
        double $$4 = p_216993_.m_20186_() - this.m_20186_();
        double $$5 = p_216993_.m_20189_() - this.m_20189_();
        return Mth.m_211589_($$3, $$5) < Mth.m_144952_(p_216994_) && Mth.m_144952_($$4) < Mth.m_144952_(p_216995_);
    }

    protected void m_19915_(float p_19916_, float p_19917_) {
        this.m_146922_(p_19916_ % 360.0f);
        this.m_146926_(p_19917_ % 360.0f);
    }

    public final void m_146884_(Vec3 p_146885_) {
        this.m_6034_(p_146885_.m_7096_(), p_146885_.m_7098_(), p_146885_.m_7094_());
    }

    public void m_6034_(double p_20210_, double p_20211_, double p_20212_) {
        this.m_20343_(p_20210_, p_20211_, p_20212_);
        this.m_20011_(this.m_142242_());
    }

    protected AABB m_142242_() {
        return this.f_19815_.m_20393_(this.f_19825_);
    }

    protected void m_20090_() {
        this.m_6034_(this.f_19825_.f_82479_, this.f_19825_.f_82480_, this.f_19825_.f_82481_);
    }

    public void m_19884_(double p_19885_, double p_19886_) {
        float $$2 = (float)p_19886_ * 0.15f;
        float $$3 = (float)p_19885_ * 0.15f;
        this.m_146926_(this.m_146909_() + $$2);
        this.m_146922_(this.m_146908_() + $$3);
        this.m_146926_(Mth.m_14036_(this.m_146909_(), -90.0f, 90.0f));
        this.f_19860_ += $$2;
        this.f_19859_ += $$3;
        this.f_19860_ = Mth.m_14036_(this.f_19860_, -90.0f, 90.0f);
        if (this.f_19824_ != null) {
            this.f_19824_.m_7340_(this);
        }
    }

    public void m_8119_() {
        this.m_6075_();
    }

    public void m_6075_() {
        this.f_19853_.m_46473_().m_6180_("entityBaseTick");
        this.f_185934_ = null;
        if (this.m_20159_() && this.m_20202_().m_213877_()) {
            this.m_8127_();
        }
        if (this.f_19851_ > 0) {
            --this.f_19851_;
        }
        this.f_19867_ = this.f_19787_;
        this.f_19860_ = this.m_146909_();
        this.f_19859_ = this.m_146908_();
        this.m_20157_();
        if (this.m_5843_()) {
            this.m_20076_();
        }
        this.f_146809_ = this.f_146808_;
        this.f_146808_ = false;
        this.m_20073_();
        this.m_20323_();
        this.m_5844_();
        if (this.f_19853_.f_46443_) {
            this.m_20095_();
        } else if (this.f_19831_ > 0) {
            if (this.m_5825_()) {
                this.m_7311_(this.f_19831_ - 4);
                if (this.f_19831_ < 0) {
                    this.m_20095_();
                }
            } else {
                if (this.f_19831_ % 20 == 0 && !this.m_20077_()) {
                    this.m_6469_(DamageSource.f_19307_, 1.0f);
                }
                this.m_7311_(this.f_19831_ - 1);
            }
            if (this.m_146888_() > 0) {
                this.m_146917_(0);
                this.f_19853_.m_5898_(null, 1009, this.f_19826_, 1);
            }
        }
        if (this.m_20077_()) {
            this.m_20093_();
            this.f_19789_ *= 0.5f;
        }
        this.m_146871_();
        if (!this.f_19853_.f_46443_) {
            this.m_146868_(this.f_19831_ > 0);
        }
        this.f_19803_ = false;
        this.f_19853_.m_46473_().m_7238_();
    }

    public void m_146868_(boolean p_146869_) {
        this.m_20115_(0, p_146869_ || this.f_146813_);
    }

    public void m_146871_() {
        if (this.m_20186_() < (double)(this.f_19853_.m_141937_() - 64)) {
            this.m_6088_();
        }
    }

    public void m_20091_() {
        this.f_19839_ = this.m_6045_();
    }

    public boolean m_20092_() {
        return this.f_19839_ > 0;
    }

    protected void m_8021_() {
        if (this.m_20092_()) {
            --this.f_19839_;
        }
    }

    public int m_6078_() {
        return 0;
    }

    public void m_20093_() {
        if (this.m_5825_()) {
            return;
        }
        this.m_20254_(15);
        if (this.m_6469_(DamageSource.f_19308_, 4.0f)) {
            this.m_5496_(SoundEvents.f_11909_, 0.4f, 2.0f + this.f_19796_.m_188501_() * 0.4f);
        }
    }

    public void m_20254_(int p_20255_) {
        int $$1 = p_20255_ * 20;
        if (this instanceof LivingEntity) {
            $$1 = ProtectionEnchantment.m_45138_((LivingEntity)this, $$1);
        }
        if (this.f_19831_ < $$1) {
            this.m_7311_($$1);
        }
    }

    public void m_7311_(int p_20269_) {
        this.f_19831_ = p_20269_;
    }

    public int m_20094_() {
        return this.f_19831_;
    }

    public void m_20095_() {
        this.m_7311_(0);
    }

    protected void m_6088_() {
        this.m_146870_();
    }

    public boolean m_20229_(double p_20230_, double p_20231_, double p_20232_) {
        return this.m_20131_(this.m_20191_().m_82386_(p_20230_, p_20231_, p_20232_));
    }

    private boolean m_20131_(AABB p_20132_) {
        return this.f_19853_.m_45756_(this, p_20132_) && !this.f_19853_.m_46855_(p_20132_);
    }

    public void m_6853_(boolean p_20181_) {
        this.f_19861_ = p_20181_;
    }

    public boolean m_20096_() {
        return this.f_19861_;
    }

    public void m_6478_(MoverType p_19973_, Vec3 p_19974_) {
        MovementEmission $$11;
        Vec3 $$2;
        double $$3;
        if (this.f_19794_) {
            this.m_6034_(this.m_20185_() + p_19974_.f_82479_, this.m_20186_() + p_19974_.f_82480_, this.m_20189_() + p_19974_.f_82481_);
            return;
        }
        this.f_146810_ = this.m_6060_();
        if (p_19973_ == MoverType.PISTON && (p_19974_ = this.m_20133_(p_19974_)).equals(Vec3.f_82478_)) {
            return;
        }
        this.f_19853_.m_46473_().m_6180_("move");
        if (this.f_19865_.m_82556_() > 1.0E-7) {
            p_19974_ = p_19974_.m_82559_(this.f_19865_);
            this.f_19865_ = Vec3.f_82478_;
            this.m_20256_(Vec3.f_82478_);
        }
        if (($$3 = ($$2 = this.m_20272_(p_19974_ = this.m_5763_(p_19974_, p_19973_))).m_82556_()) > 1.0E-7) {
            BlockHitResult $$4;
            if (this.f_19789_ != 0.0f && $$3 >= 1.0 && ($$4 = this.f_19853_.m_45547_(new ClipContext(this.m_20182_(), this.m_20182_().m_82549_($$2), ClipContext.Block.FALLDAMAGE_RESETTING, ClipContext.Fluid.WATER, this))).m_6662_() != HitResult.Type.MISS) {
                this.m_183634_();
            }
            this.m_6034_(this.m_20185_() + $$2.f_82479_, this.m_20186_() + $$2.f_82480_, this.m_20189_() + $$2.f_82481_);
        }
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("rest");
        boolean $$5 = !Mth.m_14082_(p_19974_.f_82479_, $$2.f_82479_);
        boolean $$6 = !Mth.m_14082_(p_19974_.f_82481_, $$2.f_82481_);
        this.f_19862_ = $$5 || $$6;
        this.f_19863_ = p_19974_.f_82480_ != $$2.f_82480_;
        this.f_201939_ = this.f_19863_ && p_19974_.f_82480_ < 0.0;
        this.f_185931_ = this.f_19862_ ? this.m_196406_($$2) : false;
        this.f_19861_ = this.f_19863_ && p_19974_.f_82480_ < 0.0;
        BlockPos $$7 = this.m_216999_();
        BlockState $$8 = this.f_19853_.m_8055_($$7);
        this.m_7840_($$2.f_82480_, this.f_19861_, $$8, $$7);
        if (this.m_213877_()) {
            this.f_19853_.m_46473_().m_7238_();
            return;
        }
        if (this.f_19862_) {
            Vec3 $$9 = this.m_20184_();
            this.m_20334_($$5 ? 0.0 : $$9.f_82479_, $$9.f_82480_, $$6 ? 0.0 : $$9.f_82481_);
        }
        Block $$10 = $$8.m_60734_();
        if (p_19974_.f_82480_ != $$2.f_82480_) {
            $$10.m_5548_(this.f_19853_, this);
        }
        if (this.f_19861_) {
            $$10.m_141947_(this.f_19853_, $$7, $$8, this);
        }
        if (($$11 = this.m_142319_()).m_146944_() && !this.m_20159_()) {
            boolean $$15;
            double $$12 = $$2.f_82479_;
            double $$13 = $$2.f_82480_;
            double $$14 = $$2.f_82481_;
            this.f_146794_ += (float)($$2.m_82553_() * 0.6);
            boolean bl = $$15 = $$8.m_204336_(BlockTags.f_13082_) || $$8.m_60713_(Blocks.f_152499_);
            if (!$$15) {
                $$13 = 0.0;
            }
            this.f_19787_ += (float)$$2.m_165924_() * 0.6f;
            this.f_19788_ += (float)Math.sqrt($$12 * $$12 + $$13 * $$13 + $$14 * $$14) * 0.6f;
            if (this.f_19788_ > this.f_19829_ && !$$8.m_60795_()) {
                this.f_19829_ = this.m_6059_();
                if (this.m_20069_()) {
                    if ($$11.m_146946_()) {
                        Entity $$16 = this.m_20160_() && this.m_6688_() != null ? this.m_6688_() : this;
                        float $$17 = $$16 == this ? 0.35f : 0.4f;
                        Vec3 $$18 = $$16.m_20184_();
                        float $$19 = Math.min(1.0f, (float)Math.sqrt($$18.f_82479_ * $$18.f_82479_ * (double)0.2f + $$18.f_82480_ * $$18.f_82480_ + $$18.f_82481_ * $$18.f_82481_ * (double)0.2f) * $$17);
                        this.m_5625_($$19);
                    }
                    if ($$11.m_146945_()) {
                        this.m_146850_(GameEvent.f_157786_);
                    }
                } else {
                    if ($$11.m_146946_()) {
                        this.m_146882_($$8);
                        this.m_7355_($$7, $$8);
                    }
                    if ($$11.m_146945_() && (this.f_19861_ || p_19974_.f_82480_ == 0.0 || this.f_146808_ || $$15)) {
                        this.f_19853_.m_214171_(GameEvent.f_157785_, this.f_19825_, GameEvent.Context.m_223719_(this, this.m_20075_()));
                    }
                }
            } else if ($$8.m_60795_()) {
                this.m_146874_();
            }
        }
        this.m_146872_();
        float $$20 = this.m_6041_();
        this.m_20256_(this.m_20184_().m_82542_($$20, 1.0, $$20));
        if (this.f_19853_.m_46847_(this.m_20191_().m_82406_(1.0E-6)).noneMatch(p_20127_ -> p_20127_.m_204336_(BlockTags.f_13076_) || p_20127_.m_60713_(Blocks.f_49991_))) {
            if (this.f_19831_ <= 0) {
                this.m_7311_(-this.m_6101_());
            }
            if (this.f_146810_ && (this.f_146808_ || this.m_20071_())) {
                this.m_146873_();
            }
        }
        if (this.m_6060_() && (this.f_146808_ || this.m_20071_())) {
            this.m_7311_(-this.m_6101_());
        }
        this.f_19853_.m_46473_().m_7238_();
    }

    protected boolean m_196406_(Vec3 p_196625_) {
        return false;
    }

    protected void m_146872_() {
        try {
            this.m_20101_();
        }
        catch (Throwable $$0) {
            CrashReport $$1 = CrashReport.m_127521_($$0, "Checking entity block collision");
            CrashReportCategory $$2 = $$1.m_127514_("Entity being checked for collision");
            this.m_7976_($$2);
            throw new ReportedException($$1);
        }
    }

    protected void m_146873_() {
        this.m_5496_(SoundEvents.f_11914_, 0.7f, 1.6f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.4f);
    }

    protected void m_146874_() {
        if (this.m_142039_()) {
            this.m_142043_();
            if (this.m_142319_().m_146945_()) {
                this.m_146850_(GameEvent.f_157815_);
            }
        }
    }

    @Deprecated
    public BlockPos m_216999_() {
        return this.m_216986_(0.2f);
    }

    public BlockPos m_20097_() {
        return this.m_216986_(1.0E-5f);
    }

    private BlockPos m_216986_(float p_216987_) {
        BlockPos $$5;
        BlockState $$6;
        int $$3;
        int $$2;
        int $$1 = Mth.m_14107_(this.f_19825_.f_82479_);
        BlockPos $$4 = new BlockPos($$1, $$2 = Mth.m_14107_(this.f_19825_.f_82480_ - (double)p_216987_), $$3 = Mth.m_14107_(this.f_19825_.f_82481_));
        if (this.f_19853_.m_8055_($$4).m_60795_() && (($$6 = this.f_19853_.m_8055_($$5 = $$4.m_7495_())).m_204336_(BlockTags.f_13039_) || $$6.m_204336_(BlockTags.f_13032_) || $$6.m_60734_() instanceof FenceGateBlock)) {
            return $$5;
        }
        return $$4;
    }

    protected float m_20098_() {
        float $$0 = this.f_19853_.m_8055_(this.m_20183_()).m_60734_().m_49964_();
        float $$1 = this.f_19853_.m_8055_(this.m_20099_()).m_60734_().m_49964_();
        return (double)$$0 == 1.0 ? $$1 : $$0;
    }

    protected float m_6041_() {
        BlockState $$0 = this.f_19853_.m_8055_(this.m_20183_());
        float $$1 = $$0.m_60734_().m_49961_();
        if ($$0.m_60713_(Blocks.f_49990_) || $$0.m_60713_(Blocks.f_50628_)) {
            return $$1;
        }
        return (double)$$1 == 1.0 ? this.f_19853_.m_8055_(this.m_20099_()).m_60734_().m_49961_() : $$1;
    }

    protected BlockPos m_20099_() {
        return new BlockPos(this.f_19825_.f_82479_, this.m_20191_().f_82289_ - 0.5000001, this.f_19825_.f_82481_);
    }

    protected Vec3 m_5763_(Vec3 p_20019_, MoverType p_20020_) {
        return p_20019_;
    }

    protected Vec3 m_20133_(Vec3 p_20134_) {
        if (p_20134_.m_82556_() <= 1.0E-7) {
            return p_20134_;
        }
        long $$1 = this.f_19853_.m_46467_();
        if ($$1 != this.f_19814_) {
            Arrays.fill(this.f_19813_, 0.0);
            this.f_19814_ = $$1;
        }
        if (p_20134_.f_82479_ != 0.0) {
            double $$2 = this.m_20042_(Direction.Axis.X, p_20134_.f_82479_);
            return Math.abs($$2) <= (double)1.0E-5f ? Vec3.f_82478_ : new Vec3($$2, 0.0, 0.0);
        }
        if (p_20134_.f_82480_ != 0.0) {
            double $$3 = this.m_20042_(Direction.Axis.Y, p_20134_.f_82480_);
            return Math.abs($$3) <= (double)1.0E-5f ? Vec3.f_82478_ : new Vec3(0.0, $$3, 0.0);
        }
        if (p_20134_.f_82481_ != 0.0) {
            double $$4 = this.m_20042_(Direction.Axis.Z, p_20134_.f_82481_);
            return Math.abs($$4) <= (double)1.0E-5f ? Vec3.f_82478_ : new Vec3(0.0, 0.0, $$4);
        }
        return Vec3.f_82478_;
    }

    private double m_20042_(Direction.Axis p_20043_, double p_20044_) {
        int $$2 = p_20043_.ordinal();
        double $$3 = Mth.m_14008_(p_20044_ + this.f_19813_[$$2], -0.51, 0.51);
        p_20044_ = $$3 - this.f_19813_[$$2];
        this.f_19813_[$$2] = $$3;
        return p_20044_;
    }

    private Vec3 m_20272_(Vec3 p_20273_) {
        boolean $$7;
        AABB $$1 = this.m_20191_();
        List<VoxelShape> $$2 = this.f_19853_.m_183134_(this, $$1.m_82369_(p_20273_));
        Vec3 $$3 = p_20273_.m_82556_() == 0.0 ? p_20273_ : Entity.m_198894_(this, p_20273_, $$1, this.f_19853_, $$2);
        boolean $$4 = p_20273_.f_82479_ != $$3.f_82479_;
        boolean $$5 = p_20273_.f_82480_ != $$3.f_82480_;
        boolean $$6 = p_20273_.f_82481_ != $$3.f_82481_;
        boolean bl = $$7 = this.f_19861_ || $$5 && p_20273_.f_82480_ < 0.0;
        if (this.f_19793_ > 0.0f && $$7 && ($$4 || $$6)) {
            Vec3 $$10;
            Vec3 $$8 = Entity.m_198894_(this, new Vec3(p_20273_.f_82479_, this.f_19793_, p_20273_.f_82481_), $$1, this.f_19853_, $$2);
            Vec3 $$9 = Entity.m_198894_(this, new Vec3(0.0, this.f_19793_, 0.0), $$1.m_82363_(p_20273_.f_82479_, 0.0, p_20273_.f_82481_), this.f_19853_, $$2);
            if ($$9.f_82480_ < (double)this.f_19793_ && ($$10 = Entity.m_198894_(this, new Vec3(p_20273_.f_82479_, 0.0, p_20273_.f_82481_), $$1.m_82383_($$9), this.f_19853_, $$2).m_82549_($$9)).m_165925_() > $$8.m_165925_()) {
                $$8 = $$10;
            }
            if ($$8.m_165925_() > $$3.m_165925_()) {
                return $$8.m_82549_(Entity.m_198894_(this, new Vec3(0.0, -$$8.f_82480_ + p_20273_.f_82480_, 0.0), $$1.m_82383_($$8), this.f_19853_, $$2));
            }
        }
        return $$3;
    }

    public static Vec3 m_198894_(@Nullable Entity p_198895_, Vec3 p_198896_, AABB p_198897_, Level p_198898_, List<VoxelShape> p_198899_) {
        boolean $$7;
        ImmutableList.Builder $$5 = ImmutableList.builderWithExpectedSize((int)(p_198899_.size() + 1));
        if (!p_198899_.isEmpty()) {
            $$5.addAll(p_198899_);
        }
        WorldBorder $$6 = p_198898_.m_6857_();
        boolean bl = $$7 = p_198895_ != null && $$6.m_187566_(p_198895_, p_198897_.m_82369_(p_198896_));
        if ($$7) {
            $$5.add((Object)$$6.m_61946_());
        }
        $$5.addAll(p_198898_.m_186434_(p_198895_, p_198897_.m_82369_(p_198896_)));
        return Entity.m_198900_(p_198896_, p_198897_, (List<VoxelShape>)$$5.build());
    }

    private static Vec3 m_198900_(Vec3 p_198901_, AABB p_198902_, List<VoxelShape> p_198903_) {
        boolean $$6;
        if (p_198903_.isEmpty()) {
            return p_198901_;
        }
        double $$3 = p_198901_.f_82479_;
        double $$4 = p_198901_.f_82480_;
        double $$5 = p_198901_.f_82481_;
        if ($$4 != 0.0 && ($$4 = Shapes.m_193135_(Direction.Axis.Y, p_198902_, p_198903_, $$4)) != 0.0) {
            p_198902_ = p_198902_.m_82386_(0.0, $$4, 0.0);
        }
        boolean bl = $$6 = Math.abs($$3) < Math.abs($$5);
        if ($$6 && $$5 != 0.0 && ($$5 = Shapes.m_193135_(Direction.Axis.Z, p_198902_, p_198903_, $$5)) != 0.0) {
            p_198902_ = p_198902_.m_82386_(0.0, 0.0, $$5);
        }
        if ($$3 != 0.0) {
            $$3 = Shapes.m_193135_(Direction.Axis.X, p_198902_, p_198903_, $$3);
            if (!$$6 && $$3 != 0.0) {
                p_198902_ = p_198902_.m_82386_($$3, 0.0, 0.0);
            }
        }
        if (!$$6 && $$5 != 0.0) {
            $$5 = Shapes.m_193135_(Direction.Axis.Z, p_198902_, p_198903_, $$5);
        }
        return new Vec3($$3, $$4, $$5);
    }

    protected float m_6059_() {
        return (int)this.f_19788_ + 1;
    }

    protected SoundEvent m_5501_() {
        return SoundEvents.f_11918_;
    }

    protected SoundEvent m_5509_() {
        return SoundEvents.f_11917_;
    }

    protected SoundEvent m_5508_() {
        return SoundEvents.f_11917_;
    }

    protected void m_20101_() {
        AABB $$0 = this.m_20191_();
        BlockPos $$1 = new BlockPos($$0.f_82288_ + 0.001, $$0.f_82289_ + 0.001, $$0.f_82290_ + 0.001);
        BlockPos $$2 = new BlockPos($$0.f_82291_ - 0.001, $$0.f_82292_ - 0.001, $$0.f_82293_ - 0.001);
        if (this.f_19853_.m_46832_($$1, $$2)) {
            BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
            for (int $$4 = $$1.m_123341_(); $$4 <= $$2.m_123341_(); ++$$4) {
                for (int $$5 = $$1.m_123342_(); $$5 <= $$2.m_123342_(); ++$$5) {
                    for (int $$6 = $$1.m_123343_(); $$6 <= $$2.m_123343_(); ++$$6) {
                        $$3.m_122178_($$4, $$5, $$6);
                        BlockState $$7 = this.f_19853_.m_8055_($$3);
                        try {
                            $$7.m_60682_(this.f_19853_, $$3, this);
                            this.m_6763_($$7);
                            continue;
                        }
                        catch (Throwable $$8) {
                            CrashReport $$9 = CrashReport.m_127521_($$8, "Colliding entity with block");
                            CrashReportCategory $$10 = $$9.m_127514_("Block being collided with");
                            CrashReportCategory.m_178950_($$10, this.f_19853_, $$3, $$7);
                            throw new ReportedException($$9);
                        }
                    }
                }
            }
        }
    }

    protected void m_6763_(BlockState p_20005_) {
    }

    public void m_146852_(GameEvent p_146853_, @Nullable Entity p_146854_) {
        this.f_19853_.m_220400_(p_146854_, p_146853_, this.f_19825_);
    }

    public void m_146850_(GameEvent p_146851_) {
        this.m_146852_(p_146851_, this);
    }

    protected void m_7355_(BlockPos p_20135_, BlockState p_20136_) {
        if (p_20136_.m_60767_().m_76332_()) {
            return;
        }
        BlockState $$2 = this.f_19853_.m_8055_(p_20135_.m_7494_());
        SoundType $$3 = $$2.m_204336_(BlockTags.f_144271_) ? $$2.m_60827_() : p_20136_.m_60827_();
        this.m_5496_($$3.m_56776_(), $$3.m_56773_() * 0.15f, $$3.m_56774_());
    }

    private void m_146882_(BlockState p_146883_) {
        if (p_146883_.m_204336_(BlockTags.f_144270_) && this.f_19797_ >= this.f_146804_ + 20) {
            this.f_146803_ *= (float)Math.pow(0.997, this.f_19797_ - this.f_146804_);
            this.f_146803_ = Math.min(1.0f, this.f_146803_ + 0.07f);
            float $$1 = 0.5f + this.f_146803_ * this.f_19796_.m_188501_() * 1.2f;
            float $$2 = 0.1f + this.f_146803_ * 1.2f;
            this.m_5496_(SoundEvents.f_144243_, $$2, $$1);
            this.f_146804_ = this.f_19797_;
        }
    }

    protected void m_5625_(float p_20213_) {
        this.m_5496_(this.m_5501_(), p_20213_, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.4f);
    }

    protected void m_142043_() {
    }

    protected boolean m_142039_() {
        return false;
    }

    public void m_5496_(SoundEvent p_19938_, float p_19939_, float p_19940_) {
        if (!this.m_20067_()) {
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), p_19938_, this.m_5720_(), p_19939_, p_19940_);
        }
    }

    public void m_216990_(SoundEvent p_216991_) {
        if (!this.m_20067_()) {
            this.m_5496_(p_216991_, 1.0f, 1.0f);
        }
    }

    public boolean m_20067_() {
        return this.f_19804_.m_135370_(f_19835_);
    }

    public void m_20225_(boolean p_20226_) {
        this.f_19804_.m_135381_(f_19835_, p_20226_);
    }

    public boolean m_20068_() {
        return this.f_19804_.m_135370_(f_19836_);
    }

    public void m_20242_(boolean p_20243_) {
        this.f_19804_.m_135381_(f_19836_, p_20243_);
    }

    protected MovementEmission m_142319_() {
        return MovementEmission.ALL;
    }

    public boolean m_213854_() {
        return false;
    }

    protected void m_7840_(double p_19911_, boolean p_19912_, BlockState p_19913_, BlockPos p_19914_) {
        if (p_19912_) {
            if (this.f_19789_ > 0.0f) {
                p_19913_.m_60734_().m_142072_(this.f_19853_, p_19913_, p_19914_, this, this.f_19789_);
                this.f_19853_.m_214171_(GameEvent.f_157770_, this.f_19825_, GameEvent.Context.m_223719_(this, this.m_20075_()));
            }
            this.m_183634_();
        } else if (p_19911_ < 0.0) {
            this.f_19789_ -= (float)p_19911_;
        }
    }

    public boolean m_5825_() {
        return this.m_6095_().m_20672_();
    }

    public boolean m_142535_(float p_146828_, float p_146829_, DamageSource p_146830_) {
        if (this.m_20160_()) {
            for (Entity $$3 : this.m_20197_()) {
                $$3.m_142535_(p_146828_, p_146829_, p_146830_);
            }
        }
        return false;
    }

    public boolean m_20069_() {
        return this.f_19798_;
    }

    private boolean m_20285_() {
        BlockPos $$0 = this.m_20183_();
        return this.f_19853_.m_46758_($$0) || this.f_19853_.m_46758_(new BlockPos((double)$$0.m_123341_(), this.m_20191_().f_82292_, (double)$$0.m_123343_()));
    }

    private boolean m_20305_() {
        return this.f_19853_.m_8055_(this.m_20183_()).m_60713_(Blocks.f_50628_);
    }

    public boolean m_20070_() {
        return this.m_20069_() || this.m_20285_();
    }

    public boolean m_20071_() {
        return this.m_20069_() || this.m_20285_() || this.m_20305_();
    }

    public boolean m_20072_() {
        return this.m_20069_() || this.m_20305_();
    }

    public boolean m_5842_() {
        return this.f_19800_ && this.m_20069_();
    }

    public ChatSender m_217000_() {
        return ChatSender.f_240891_;
    }

    public void m_5844_() {
        if (this.m_6069_()) {
            this.m_20282_(this.m_20142_() && this.m_20069_() && !this.m_20159_());
        } else {
            this.m_20282_(this.m_20142_() && this.m_5842_() && !this.m_20159_() && this.f_19853_.m_6425_(this.f_19826_).m_205070_(FluidTags.f_13131_));
        }
    }

    protected boolean m_20073_() {
        this.f_19799_.clear();
        this.m_20074_();
        double $$0 = this.f_19853_.m_6042_().f_63857_() ? 0.007 : 0.0023333333333333335;
        boolean $$1 = this.m_204031_(FluidTags.f_13132_, $$0);
        return this.m_20069_() || $$1;
    }

    void m_20074_() {
        if (this.m_20202_() instanceof Boat) {
            this.f_19798_ = false;
        } else if (this.m_204031_(FluidTags.f_13131_, 0.014)) {
            if (!this.f_19798_ && !this.f_19803_) {
                this.m_5841_();
            }
            this.m_183634_();
            this.f_19798_ = true;
            this.m_20095_();
        } else {
            this.f_19798_ = false;
        }
    }

    private void m_20323_() {
        Boat $$2;
        this.f_19800_ = this.m_204029_(FluidTags.f_13131_);
        this.f_19801_.clear();
        double $$0 = this.m_20188_() - 0.1111111119389534;
        Entity $$1 = this.m_20202_();
        if ($$1 instanceof Boat && !($$2 = (Boat)$$1).m_5842_() && $$2.m_20191_().f_82292_ >= $$0 && $$2.m_20191_().f_82289_ <= $$0) {
            return;
        }
        BlockPos $$3 = new BlockPos(this.m_20185_(), $$0, this.m_20189_());
        FluidState $$4 = this.f_19853_.m_6425_($$3);
        double $$5 = (float)$$3.m_123342_() + $$4.m_76155_(this.f_19853_, $$3);
        if ($$5 > $$0) {
            $$4.m_205075_().forEach(this.f_19801_::add);
        }
    }

    protected void m_5841_() {
        Entity $$0 = this.m_20160_() && this.m_6688_() != null ? this.m_6688_() : this;
        float $$1 = $$0 == this ? 0.2f : 0.9f;
        Vec3 $$2 = $$0.m_20184_();
        float $$3 = Math.min(1.0f, (float)Math.sqrt($$2.f_82479_ * $$2.f_82479_ * (double)0.2f + $$2.f_82480_ * $$2.f_82480_ + $$2.f_82481_ * $$2.f_82481_ * (double)0.2f) * $$1);
        if ($$3 < 0.25f) {
            this.m_5496_(this.m_5509_(), $$3, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.4f);
        } else {
            this.m_5496_(this.m_5508_(), $$3, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.4f);
        }
        float $$4 = Mth.m_14107_(this.m_20186_());
        int $$5 = 0;
        while ((float)$$5 < 1.0f + this.f_19815_.f_20377_ * 20.0f) {
            double $$6 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.f_19815_.f_20377_;
            double $$7 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.f_19815_.f_20377_;
            this.f_19853_.m_7106_(ParticleTypes.f_123795_, this.m_20185_() + $$6, $$4 + 1.0f, this.m_20189_() + $$7, $$2.f_82479_, $$2.f_82480_ - this.f_19796_.m_188500_() * (double)0.2f, $$2.f_82481_);
            ++$$5;
        }
        int $$8 = 0;
        while ((float)$$8 < 1.0f + this.f_19815_.f_20377_ * 20.0f) {
            double $$9 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.f_19815_.f_20377_;
            double $$10 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.f_19815_.f_20377_;
            this.f_19853_.m_7106_(ParticleTypes.f_123769_, this.m_20185_() + $$9, $$4 + 1.0f, this.m_20189_() + $$10, $$2.f_82479_, $$2.f_82480_, $$2.f_82481_);
            ++$$8;
        }
        this.m_146850_(GameEvent.f_157784_);
    }

    @Deprecated
    protected BlockState m_217002_() {
        return this.f_19853_.m_8055_(this.m_216999_());
    }

    public BlockState m_20075_() {
        return this.f_19853_.m_8055_(this.m_20097_());
    }

    public boolean m_5843_() {
        return this.m_20142_() && !this.m_20069_() && !this.m_5833_() && !this.m_6047_() && !this.m_20077_() && this.m_6084_();
    }

    protected void m_20076_() {
        int $$2;
        int $$1;
        int $$0 = Mth.m_14107_(this.m_20185_());
        BlockPos $$3 = new BlockPos($$0, $$1 = Mth.m_14107_(this.m_20186_() - (double)0.2f), $$2 = Mth.m_14107_(this.m_20189_()));
        BlockState $$4 = this.f_19853_.m_8055_($$3);
        if ($$4.m_60799_() != RenderShape.INVISIBLE) {
            Vec3 $$5 = this.m_20184_();
            this.f_19853_.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, $$4), this.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * (double)this.f_19815_.f_20377_, this.m_20186_() + 0.1, this.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * (double)this.f_19815_.f_20377_, $$5.f_82479_ * -4.0, 1.5, $$5.f_82481_ * -4.0);
        }
    }

    public boolean m_204029_(TagKey<Fluid> p_204030_) {
        return this.f_19801_.contains(p_204030_);
    }

    public boolean m_20077_() {
        return !this.f_19803_ && this.f_19799_.getDouble(FluidTags.f_13132_) > 0.0;
    }

    public void m_19920_(float p_19921_, Vec3 p_19922_) {
        Vec3 $$2 = Entity.m_20015_(p_19922_, p_19921_, this.m_146908_());
        this.m_20256_(this.m_20184_().m_82549_($$2));
    }

    private static Vec3 m_20015_(Vec3 p_20016_, float p_20017_, float p_20018_) {
        double $$3 = p_20016_.m_82556_();
        if ($$3 < 1.0E-7) {
            return Vec3.f_82478_;
        }
        Vec3 $$4 = ($$3 > 1.0 ? p_20016_.m_82541_() : p_20016_).m_82490_(p_20017_);
        float $$5 = Mth.m_14031_(p_20018_ * ((float)Math.PI / 180));
        float $$6 = Mth.m_14089_(p_20018_ * ((float)Math.PI / 180));
        return new Vec3($$4.f_82479_ * (double)$$6 - $$4.f_82481_ * (double)$$5, $$4.f_82480_, $$4.f_82481_ * (double)$$6 + $$4.f_82479_ * (double)$$5);
    }

    @Deprecated
    public float m_213856_() {
        if (this.f_19853_.m_151577_(this.m_146903_(), this.m_146907_())) {
            return this.f_19853_.m_220417_(new BlockPos(this.m_20185_(), this.m_20188_(), this.m_20189_()));
        }
        return 0.0f;
    }

    public void m_19890_(double p_19891_, double p_19892_, double p_19893_, float p_19894_, float p_19895_) {
        this.m_20248_(p_19891_, p_19892_, p_19893_);
        this.m_146922_(p_19894_ % 360.0f);
        this.m_146926_(Mth.m_14036_(p_19895_, -90.0f, 90.0f) % 360.0f);
        this.f_19859_ = this.m_146908_();
        this.f_19860_ = this.m_146909_();
    }

    public void m_20248_(double p_20249_, double p_20250_, double p_20251_) {
        double $$3 = Mth.m_14008_(p_20249_, -3.0E7, 3.0E7);
        double $$4 = Mth.m_14008_(p_20251_, -3.0E7, 3.0E7);
        this.f_19854_ = $$3;
        this.f_19855_ = p_20250_;
        this.f_19856_ = $$4;
        this.m_6034_($$3, p_20250_, $$4);
    }

    public void m_20219_(Vec3 p_20220_) {
        this.m_6027_(p_20220_.f_82479_, p_20220_.f_82480_, p_20220_.f_82481_);
    }

    public void m_6027_(double p_20105_, double p_20106_, double p_20107_) {
        this.m_7678_(p_20105_, p_20106_, p_20107_, this.m_146908_(), this.m_146909_());
    }

    public void m_20035_(BlockPos p_20036_, float p_20037_, float p_20038_) {
        this.m_7678_((double)p_20036_.m_123341_() + 0.5, p_20036_.m_123342_(), (double)p_20036_.m_123343_() + 0.5, p_20037_, p_20038_);
    }

    public void m_7678_(double p_20108_, double p_20109_, double p_20110_, float p_20111_, float p_20112_) {
        this.m_20343_(p_20108_, p_20109_, p_20110_);
        this.m_146922_(p_20111_);
        this.m_146926_(p_20112_);
        this.m_146867_();
        this.m_20090_();
    }

    public final void m_146867_() {
        double $$0 = this.m_20185_();
        double $$1 = this.m_20186_();
        double $$2 = this.m_20189_();
        this.f_19854_ = $$0;
        this.f_19855_ = $$1;
        this.f_19856_ = $$2;
        this.f_19790_ = $$0;
        this.f_19791_ = $$1;
        this.f_19792_ = $$2;
        this.f_19859_ = this.m_146908_();
        this.f_19860_ = this.m_146909_();
    }

    public float m_20270_(Entity p_20271_) {
        float $$1 = (float)(this.m_20185_() - p_20271_.m_20185_());
        float $$2 = (float)(this.m_20186_() - p_20271_.m_20186_());
        float $$3 = (float)(this.m_20189_() - p_20271_.m_20189_());
        return Mth.m_14116_($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
    }

    public double m_20275_(double p_20276_, double p_20277_, double p_20278_) {
        double $$3 = this.m_20185_() - p_20276_;
        double $$4 = this.m_20186_() - p_20277_;
        double $$5 = this.m_20189_() - p_20278_;
        return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
    }

    public double m_20280_(Entity p_20281_) {
        return this.m_20238_(p_20281_.m_20182_());
    }

    public double m_20238_(Vec3 p_20239_) {
        double $$1 = this.m_20185_() - p_20239_.f_82479_;
        double $$2 = this.m_20186_() - p_20239_.f_82480_;
        double $$3 = this.m_20189_() - p_20239_.f_82481_;
        return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
    }

    public void m_6123_(Player p_20081_) {
    }

    public void m_7334_(Entity p_20293_) {
        double $$2;
        if (this.m_20365_(p_20293_)) {
            return;
        }
        if (p_20293_.f_19794_ || this.f_19794_) {
            return;
        }
        double $$1 = p_20293_.m_20185_() - this.m_20185_();
        double $$3 = Mth.m_14005_($$1, $$2 = p_20293_.m_20189_() - this.m_20189_());
        if ($$3 >= (double)0.01f) {
            $$3 = Math.sqrt($$3);
            $$1 /= $$3;
            $$2 /= $$3;
            double $$4 = 1.0 / $$3;
            if ($$4 > 1.0) {
                $$4 = 1.0;
            }
            $$1 *= $$4;
            $$2 *= $$4;
            $$1 *= (double)0.05f;
            $$2 *= (double)0.05f;
            if (!this.m_20160_() && this.m_6094_()) {
                this.m_5997_(-$$1, 0.0, -$$2);
            }
            if (!p_20293_.m_20160_() && p_20293_.m_6094_()) {
                p_20293_.m_5997_($$1, 0.0, $$2);
            }
        }
    }

    public void m_5997_(double p_20286_, double p_20287_, double p_20288_) {
        this.m_20256_(this.m_20184_().m_82520_(p_20286_, p_20287_, p_20288_));
        this.f_19812_ = true;
    }

    protected void m_5834_() {
        this.f_19864_ = true;
    }

    public boolean m_6469_(DamageSource p_19946_, float p_19947_) {
        if (this.m_6673_(p_19946_)) {
            return false;
        }
        this.m_5834_();
        return false;
    }

    public final Vec3 m_20252_(float p_20253_) {
        return this.m_20171_(this.m_5686_(p_20253_), this.m_5675_(p_20253_));
    }

    public float m_5686_(float p_20268_) {
        if (p_20268_ == 1.0f) {
            return this.m_146909_();
        }
        return Mth.m_14179_(p_20268_, this.f_19860_, this.m_146909_());
    }

    public float m_5675_(float p_20279_) {
        if (p_20279_ == 1.0f) {
            return this.m_146908_();
        }
        return Mth.m_14179_(p_20279_, this.f_19859_, this.m_146908_());
    }

    protected final Vec3 m_20171_(float p_20172_, float p_20173_) {
        float $$2 = p_20172_ * ((float)Math.PI / 180);
        float $$3 = -p_20173_ * ((float)Math.PI / 180);
        float $$4 = Mth.m_14089_($$3);
        float $$5 = Mth.m_14031_($$3);
        float $$6 = Mth.m_14089_($$2);
        float $$7 = Mth.m_14031_($$2);
        return new Vec3($$5 * $$6, -$$7, $$4 * $$6);
    }

    public final Vec3 m_20289_(float p_20290_) {
        return this.m_20214_(this.m_5686_(p_20290_), this.m_5675_(p_20290_));
    }

    protected final Vec3 m_20214_(float p_20215_, float p_20216_) {
        return this.m_20171_(p_20215_ - 90.0f, p_20216_);
    }

    public final Vec3 m_146892_() {
        return new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
    }

    public final Vec3 m_20299_(float p_20300_) {
        double $$1 = Mth.m_14139_(p_20300_, this.f_19854_, this.m_20185_());
        double $$2 = Mth.m_14139_(p_20300_, this.f_19855_, this.m_20186_()) + (double)this.m_20192_();
        double $$3 = Mth.m_14139_(p_20300_, this.f_19856_, this.m_20189_());
        return new Vec3($$1, $$2, $$3);
    }

    public Vec3 m_7371_(float p_20309_) {
        return this.m_20299_(p_20309_);
    }

    public final Vec3 m_20318_(float p_20319_) {
        double $$1 = Mth.m_14139_(p_20319_, this.f_19854_, this.m_20185_());
        double $$2 = Mth.m_14139_(p_20319_, this.f_19855_, this.m_20186_());
        double $$3 = Mth.m_14139_(p_20319_, this.f_19856_, this.m_20189_());
        return new Vec3($$1, $$2, $$3);
    }

    public HitResult m_19907_(double p_19908_, float p_19909_, boolean p_19910_) {
        Vec3 $$3 = this.m_20299_(p_19909_);
        Vec3 $$4 = this.m_20252_(p_19909_);
        Vec3 $$5 = $$3.m_82520_($$4.f_82479_ * p_19908_, $$4.f_82480_ * p_19908_, $$4.f_82481_ * p_19908_);
        return this.f_19853_.m_45547_(new ClipContext($$3, $$5, ClipContext.Block.OUTLINE, p_19910_ ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, this));
    }

    public boolean m_6087_() {
        return false;
    }

    public boolean m_6094_() {
        return false;
    }

    public void m_5993_(Entity p_19953_, int p_19954_, DamageSource p_19955_) {
        if (p_19953_ instanceof ServerPlayer) {
            CriteriaTriggers.f_10569_.m_48104_((ServerPlayer)p_19953_, this, p_19955_);
        }
    }

    public boolean m_6000_(double p_20296_, double p_20297_, double p_20298_) {
        double $$3 = this.m_20185_() - p_20296_;
        double $$4 = this.m_20186_() - p_20297_;
        double $$5 = this.m_20189_() - p_20298_;
        double $$6 = $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
        return this.m_6783_($$6);
    }

    public boolean m_6783_(double p_19883_) {
        double $$1 = this.m_20191_().m_82309_();
        if (Double.isNaN($$1)) {
            $$1 = 1.0;
        }
        return p_19883_ < ($$1 *= 64.0 * f_19846_) * $$1;
    }

    public boolean m_20086_(CompoundTag p_20087_) {
        if (this.f_146795_ != null && !this.f_146795_.m_146966_()) {
            return false;
        }
        String $$1 = this.m_20078_();
        if ($$1 == null) {
            return false;
        }
        p_20087_.m_128359_(f_146815_, $$1);
        this.m_20240_(p_20087_);
        return true;
    }

    public boolean m_20223_(CompoundTag p_20224_) {
        if (this.m_20159_()) {
            return false;
        }
        return this.m_20086_(p_20224_);
    }

    public CompoundTag m_20240_(CompoundTag p_20241_) {
        try {
            int $$3;
            if (this.f_19824_ != null) {
                p_20241_.m_128365_("Pos", this.m_20063_(this.f_19824_.m_20185_(), this.m_20186_(), this.f_19824_.m_20189_()));
            } else {
                p_20241_.m_128365_("Pos", this.m_20063_(this.m_20185_(), this.m_20186_(), this.m_20189_()));
            }
            Vec3 $$1 = this.m_20184_();
            p_20241_.m_128365_("Motion", this.m_20063_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_));
            p_20241_.m_128365_("Rotation", this.m_20065_(this.m_146908_(), this.m_146909_()));
            p_20241_.m_128350_("FallDistance", this.f_19789_);
            p_20241_.m_128376_("Fire", (short)this.f_19831_);
            p_20241_.m_128376_("Air", (short)this.m_20146_());
            p_20241_.m_128379_("OnGround", this.f_19861_);
            p_20241_.m_128379_("Invulnerable", this.f_19840_);
            p_20241_.m_128405_("PortalCooldown", this.f_19839_);
            p_20241_.m_128362_(f_146824_, this.m_20148_());
            Component $$2 = this.m_7770_();
            if ($$2 != null) {
                p_20241_.m_128359_("CustomName", Component.Serializer.m_130703_($$2));
            }
            if (this.m_20151_()) {
                p_20241_.m_128379_("CustomNameVisible", this.m_20151_());
            }
            if (this.m_20067_()) {
                p_20241_.m_128379_("Silent", this.m_20067_());
            }
            if (this.m_20068_()) {
                p_20241_.m_128379_("NoGravity", this.m_20068_());
            }
            if (this.f_146802_) {
                p_20241_.m_128379_("Glowing", true);
            }
            if (($$3 = this.m_146888_()) > 0) {
                p_20241_.m_128405_("TicksFrozen", this.m_146888_());
            }
            if (this.f_146813_) {
                p_20241_.m_128379_("HasVisualFire", this.f_146813_);
            }
            if (!this.f_19841_.isEmpty()) {
                ListTag $$4 = new ListTag();
                for (String $$5 : this.f_19841_) {
                    $$4.add(StringTag.m_129297_($$5));
                }
                p_20241_.m_128365_("Tags", $$4);
            }
            this.m_7380_(p_20241_);
            if (this.m_20160_()) {
                ListTag $$6 = new ListTag();
                for (Entity $$7 : this.m_20197_()) {
                    CompoundTag $$8;
                    if (!$$7.m_20086_($$8 = new CompoundTag())) continue;
                    $$6.add($$8);
                }
                if (!$$6.isEmpty()) {
                    p_20241_.m_128365_(f_146816_, $$6);
                }
            }
        }
        catch (Throwable $$9) {
            CrashReport $$10 = CrashReport.m_127521_($$9, "Saving entity NBT");
            CrashReportCategory $$11 = $$10.m_127514_("Entity being saved");
            this.m_7976_($$11);
            throw new ReportedException($$10);
        }
        return p_20241_;
    }

    public void m_20258_(CompoundTag p_20259_) {
        try {
            ListTag $$1 = p_20259_.m_128437_("Pos", 6);
            ListTag $$2 = p_20259_.m_128437_("Motion", 6);
            ListTag $$3 = p_20259_.m_128437_("Rotation", 5);
            double $$4 = $$2.m_128772_(0);
            double $$5 = $$2.m_128772_(1);
            double $$6 = $$2.m_128772_(2);
            this.m_20334_(Math.abs($$4) > 10.0 ? 0.0 : $$4, Math.abs($$5) > 10.0 ? 0.0 : $$5, Math.abs($$6) > 10.0 ? 0.0 : $$6);
            double $$7 = 3.0000512E7;
            this.m_20343_(Mth.m_14008_($$1.m_128772_(0), -3.0000512E7, 3.0000512E7), Mth.m_14008_($$1.m_128772_(1), -2.0E7, 2.0E7), Mth.m_14008_($$1.m_128772_(2), -3.0000512E7, 3.0000512E7));
            this.m_146922_($$3.m_128775_(0));
            this.m_146926_($$3.m_128775_(1));
            this.m_146867_();
            this.m_5616_(this.m_146908_());
            this.m_5618_(this.m_146908_());
            this.f_19789_ = p_20259_.m_128457_("FallDistance");
            this.f_19831_ = p_20259_.m_128448_("Fire");
            if (p_20259_.m_128441_("Air")) {
                this.m_20301_(p_20259_.m_128448_("Air"));
            }
            this.f_19861_ = p_20259_.m_128471_("OnGround");
            this.f_19840_ = p_20259_.m_128471_("Invulnerable");
            this.f_19839_ = p_20259_.m_128451_("PortalCooldown");
            if (p_20259_.m_128403_(f_146824_)) {
                this.f_19820_ = p_20259_.m_128342_(f_146824_);
                this.f_19821_ = this.f_19820_.toString();
            }
            if (!(Double.isFinite(this.m_20185_()) && Double.isFinite(this.m_20186_()) && Double.isFinite(this.m_20189_()))) {
                throw new IllegalStateException("Entity has invalid position");
            }
            if (!Double.isFinite(this.m_146908_()) || !Double.isFinite(this.m_146909_())) {
                throw new IllegalStateException("Entity has invalid rotation");
            }
            this.m_20090_();
            this.m_19915_(this.m_146908_(), this.m_146909_());
            if (p_20259_.m_128425_("CustomName", 8)) {
                String $$8 = p_20259_.m_128461_("CustomName");
                try {
                    this.m_6593_(Component.Serializer.m_130701_($$8));
                }
                catch (Exception $$9) {
                    f_19849_.warn("Failed to parse entity custom name {}", (Object)$$8, (Object)$$9);
                }
            }
            this.m_20340_(p_20259_.m_128471_("CustomNameVisible"));
            this.m_20225_(p_20259_.m_128471_("Silent"));
            this.m_20242_(p_20259_.m_128471_("NoGravity"));
            this.m_146915_(p_20259_.m_128471_("Glowing"));
            this.m_146917_(p_20259_.m_128451_("TicksFrozen"));
            this.f_146813_ = p_20259_.m_128471_("HasVisualFire");
            if (p_20259_.m_128425_("Tags", 9)) {
                this.f_19841_.clear();
                ListTag $$10 = p_20259_.m_128437_("Tags", 8);
                int $$11 = Math.min($$10.size(), 1024);
                for (int $$12 = 0; $$12 < $$11; ++$$12) {
                    this.f_19841_.add($$10.m_128778_($$12));
                }
            }
            this.m_7378_(p_20259_);
            if (this.m_6093_()) {
                this.m_20090_();
            }
        }
        catch (Throwable $$13) {
            CrashReport $$14 = CrashReport.m_127521_($$13, "Loading entity NBT");
            CrashReportCategory $$15 = $$14.m_127514_("Entity being loaded");
            this.m_7976_($$15);
            throw new ReportedException($$14);
        }
    }

    protected boolean m_6093_() {
        return true;
    }

    @Nullable
    protected final String m_20078_() {
        EntityType<?> $$0 = this.m_6095_();
        ResourceLocation $$1 = EntityType.m_20613_($$0);
        return !$$0.m_20584_() || $$1 == null ? null : $$1.toString();
    }

    protected abstract void m_7378_(CompoundTag var1);

    protected abstract void m_7380_(CompoundTag var1);

    protected ListTag m_20063_(double ... p_20064_) {
        ListTag $$1 = new ListTag();
        for (double $$2 : p_20064_) {
            $$1.add(DoubleTag.m_128500_($$2));
        }
        return $$1;
    }

    protected ListTag m_20065_(float ... p_20066_) {
        ListTag $$1 = new ListTag();
        for (float $$2 : p_20066_) {
            $$1.add(FloatTag.m_128566_($$2));
        }
        return $$1;
    }

    @Nullable
    public ItemEntity m_19998_(ItemLike p_19999_) {
        return this.m_20000_(p_19999_, 0);
    }

    @Nullable
    public ItemEntity m_20000_(ItemLike p_20001_, int p_20002_) {
        return this.m_5552_(new ItemStack(p_20001_), p_20002_);
    }

    @Nullable
    public ItemEntity m_19983_(ItemStack p_19984_) {
        return this.m_5552_(p_19984_, 0.0f);
    }

    @Nullable
    public ItemEntity m_5552_(ItemStack p_19985_, float p_19986_) {
        if (p_19985_.m_41619_()) {
            return null;
        }
        if (this.f_19853_.f_46443_) {
            return null;
        }
        ItemEntity $$2 = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_() + (double)p_19986_, this.m_20189_(), p_19985_);
        $$2.m_32060_();
        this.f_19853_.m_7967_($$2);
        return $$2;
    }

    public boolean m_6084_() {
        return !this.m_213877_();
    }

    public boolean m_5830_() {
        if (this.f_19794_) {
            return false;
        }
        float $$0 = this.f_19815_.f_20377_ * 0.8f;
        AABB $$1 = AABB.m_165882_(this.m_146892_(), $$0, 1.0E-6, $$0);
        return BlockPos.m_121921_($$1).anyMatch(p_201942_ -> {
            BlockState $$2 = this.f_19853_.m_8055_((BlockPos)p_201942_);
            return !$$2.m_60795_() && $$2.m_60828_(this.f_19853_, (BlockPos)p_201942_) && Shapes.m_83157_($$2.m_60812_(this.f_19853_, (BlockPos)p_201942_).m_83216_(p_201942_.m_123341_(), p_201942_.m_123342_(), p_201942_.m_123343_()), Shapes.m_83064_($$1), BooleanOp.f_82689_);
        });
    }

    public InteractionResult m_6096_(Player p_19978_, InteractionHand p_19979_) {
        return InteractionResult.PASS;
    }

    public boolean m_7337_(Entity p_20303_) {
        return p_20303_.m_5829_() && !this.m_20365_(p_20303_);
    }

    public boolean m_5829_() {
        return false;
    }

    public void m_6083_() {
        this.m_20256_(Vec3.f_82478_);
        this.m_8119_();
        if (!this.m_20159_()) {
            return;
        }
        this.m_20202_().m_7332_(this);
    }

    public void m_7332_(Entity p_20312_) {
        this.m_19956_(p_20312_, Entity::m_6034_);
    }

    private void m_19956_(Entity p_19957_, MoveFunction p_19958_) {
        if (!this.m_20363_(p_19957_)) {
            return;
        }
        double $$2 = this.m_20186_() + this.m_6048_() + p_19957_.m_6049_();
        p_19958_.m_20372_(p_19957_, this.m_20185_(), $$2, this.m_20189_());
    }

    public void m_7340_(Entity p_20320_) {
    }

    public double m_6049_() {
        return 0.0;
    }

    public double m_6048_() {
        return (double)this.f_19815_.f_20378_ * 0.75;
    }

    public boolean m_20329_(Entity p_20330_) {
        return this.m_7998_(p_20330_, false);
    }

    public boolean m_20152_() {
        return this instanceof LivingEntity;
    }

    public boolean m_7998_(Entity p_19966_, boolean p_19967_) {
        if (p_19966_ == this.f_19824_) {
            return false;
        }
        Entity $$2 = p_19966_;
        while ($$2.f_19824_ != null) {
            if ($$2.f_19824_ == this) {
                return false;
            }
            $$2 = $$2.f_19824_;
        }
        if (!(p_19967_ || this.m_7341_(p_19966_) && p_19966_.m_7310_(this))) {
            return false;
        }
        if (this.m_20159_()) {
            this.m_8127_();
        }
        this.m_20124_(Pose.STANDING);
        this.f_19824_ = p_19966_;
        this.f_19824_.m_20348_(this);
        p_19966_.m_146920_().filter(p_185984_ -> p_185984_ instanceof ServerPlayer).forEach(p_185982_ -> CriteriaTriggers.f_145088_.m_160387_((ServerPlayer)p_185982_));
        return true;
    }

    protected boolean m_7341_(Entity p_20339_) {
        return !this.m_6144_() && this.f_19851_ <= 0;
    }

    protected boolean m_20175_(Pose p_20176_) {
        return this.f_19853_.m_45756_(this, this.m_20217_(p_20176_).m_82406_(1.0E-7));
    }

    public void m_20153_() {
        for (int $$0 = this.f_19823_.size() - 1; $$0 >= 0; --$$0) {
            ((Entity)this.f_19823_.get($$0)).m_8127_();
        }
    }

    public void m_6038_() {
        if (this.f_19824_ != null) {
            Entity $$0 = this.f_19824_;
            this.f_19824_ = null;
            $$0.m_20351_(this);
        }
    }

    public void m_8127_() {
        this.m_6038_();
    }

    protected void m_20348_(Entity p_20349_) {
        if (p_20349_.m_20202_() != this) {
            throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
        }
        if (this.f_19823_.isEmpty()) {
            this.f_19823_ = ImmutableList.of((Object)p_20349_);
        } else {
            ArrayList $$1 = Lists.newArrayList(this.f_19823_);
            if (!this.f_19853_.f_46443_ && p_20349_ instanceof Player && !(this.m_6688_() instanceof Player)) {
                $$1.add(0, p_20349_);
            } else {
                $$1.add(p_20349_);
            }
            this.f_19823_ = ImmutableList.copyOf((Collection)$$1);
        }
    }

    protected void m_20351_(Entity p_20352_) {
        if (p_20352_.m_20202_() == this) {
            throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
        }
        this.f_19823_ = this.f_19823_.size() == 1 && this.f_19823_.get(0) == p_20352_ ? ImmutableList.of() : (ImmutableList)this.f_19823_.stream().filter(p_185980_ -> p_185980_ != p_20352_).collect(ImmutableList.toImmutableList());
        p_20352_.f_19851_ = 60;
    }

    protected boolean m_7310_(Entity p_20354_) {
        return this.f_19823_.isEmpty();
    }

    public void m_6453_(double p_19896_, double p_19897_, double p_19898_, float p_19899_, float p_19900_, int p_19901_, boolean p_19902_) {
        this.m_6034_(p_19896_, p_19897_, p_19898_);
        this.m_19915_(p_19899_, p_19900_);
    }

    public void m_6541_(float p_19918_, int p_19919_) {
        this.m_5616_(p_19918_);
    }

    public float m_6143_() {
        return 0.0f;
    }

    public Vec3 m_20154_() {
        return this.m_20171_(this.m_146909_(), this.m_146908_());
    }

    public Vec3 m_204034_(Item p_204035_) {
        Entity entity = this;
        if (entity instanceof Player) {
            Player $$1 = (Player)entity;
            boolean $$2 = $$1.m_21206_().m_150930_(p_204035_) && !$$1.m_21205_().m_150930_(p_204035_);
            HumanoidArm $$3 = $$2 ? $$1.m_5737_().m_20828_() : $$1.m_5737_();
            return this.m_20171_(0.0f, this.m_146908_() + (float)($$3 == HumanoidArm.RIGHT ? 80 : -80)).m_82490_(0.5);
        }
        return Vec3.f_82478_;
    }

    public Vec2 m_20155_() {
        return new Vec2(this.m_146909_(), this.m_146908_());
    }

    public Vec3 m_20156_() {
        return Vec3.m_82503_(this.m_20155_());
    }

    public void m_20221_(BlockPos p_20222_) {
        if (this.m_20092_()) {
            this.m_20091_();
            return;
        }
        if (!this.f_19853_.f_46443_ && !p_20222_.equals(this.f_19819_)) {
            this.f_19819_ = p_20222_.m_7949_();
        }
        this.f_19817_ = true;
    }

    protected void m_20157_() {
        if (!(this.f_19853_ instanceof ServerLevel)) {
            return;
        }
        int $$0 = this.m_6078_();
        ServerLevel $$1 = (ServerLevel)this.f_19853_;
        if (this.f_19817_) {
            ResourceKey<Level> $$3;
            MinecraftServer $$2 = $$1.m_7654_();
            ServerLevel $$4 = $$2.m_129880_($$3 = this.f_19853_.m_46472_() == Level.f_46429_ ? Level.f_46428_ : Level.f_46429_);
            if ($$4 != null && $$2.m_7079_() && !this.m_20159_() && this.f_19818_++ >= $$0) {
                this.f_19853_.m_46473_().m_6180_("portal");
                this.f_19818_ = $$0;
                this.m_20091_();
                this.m_5489_($$4);
                this.f_19853_.m_46473_().m_7238_();
            }
            this.f_19817_ = false;
        } else {
            if (this.f_19818_ > 0) {
                this.f_19818_ -= 4;
            }
            if (this.f_19818_ < 0) {
                this.f_19818_ = 0;
            }
        }
        this.m_8021_();
    }

    public int m_6045_() {
        return 300;
    }

    public void m_6001_(double p_20306_, double p_20307_, double p_20308_) {
        this.m_20334_(p_20306_, p_20307_, p_20308_);
    }

    public void m_7822_(byte p_19882_) {
        switch (p_19882_) {
            case 53: {
                HoneyBlock.m_53986_(this);
            }
        }
    }

    public void m_6053_() {
    }

    public Iterable<ItemStack> m_6167_() {
        return f_19844_;
    }

    public Iterable<ItemStack> m_6168_() {
        return f_19844_;
    }

    public Iterable<ItemStack> m_20158_() {
        return Iterables.concat(this.m_6167_(), this.m_6168_());
    }

    public void m_8061_(EquipmentSlot p_19968_, ItemStack p_19969_) {
    }

    public boolean m_6060_() {
        boolean $$0 = this.f_19853_ != null && this.f_19853_.f_46443_;
        return !this.m_5825_() && (this.f_19831_ > 0 || $$0 && this.m_20291_(0));
    }

    public boolean m_20159_() {
        return this.m_20202_() != null;
    }

    public boolean m_20160_() {
        return !this.f_19823_.isEmpty();
    }

    public boolean m_6146_() {
        return true;
    }

    public void m_20260_(boolean p_20261_) {
        this.m_20115_(1, p_20261_);
    }

    public boolean m_6144_() {
        return this.m_20291_(1);
    }

    public boolean m_20161_() {
        return this.m_6144_();
    }

    public boolean m_20162_() {
        return this.m_6144_();
    }

    public boolean m_20163_() {
        return this.m_6144_();
    }

    public boolean m_20164_() {
        return this.m_6144_();
    }

    public boolean m_6047_() {
        return this.m_217003_(Pose.CROUCHING);
    }

    public boolean m_20142_() {
        return this.m_20291_(3);
    }

    public void m_6858_(boolean p_20274_) {
        this.m_20115_(3, p_20274_);
    }

    public boolean m_6069_() {
        return this.m_20291_(4);
    }

    public boolean m_6067_() {
        return this.m_217003_(Pose.SWIMMING);
    }

    public boolean m_20143_() {
        return this.m_6067_() && !this.m_20069_();
    }

    public void m_20282_(boolean p_20283_) {
        this.m_20115_(4, p_20283_);
    }

    public final boolean m_146886_() {
        return this.f_146802_;
    }

    public final void m_146915_(boolean p_146916_) {
        this.f_146802_ = p_146916_;
        this.m_20115_(6, this.m_142038_());
    }

    public boolean m_142038_() {
        if (this.f_19853_.m_5776_()) {
            return this.m_20291_(6);
        }
        return this.f_146802_;
    }

    public boolean m_20145_() {
        return this.m_20291_(5);
    }

    public boolean m_20177_(Player p_20178_) {
        if (p_20178_.m_5833_()) {
            return false;
        }
        Team $$1 = this.m_5647_();
        if ($$1 != null && p_20178_ != null && p_20178_.m_5647_() == $$1 && $$1.m_6259_()) {
            return false;
        }
        return this.m_20145_();
    }

    public void m_213651_(BiConsumer<DynamicGameEventListener<?>, ServerLevel> p_216996_) {
    }

    @Nullable
    public Team m_5647_() {
        return this.f_19853_.m_6188_().m_83500_(this.m_6302_());
    }

    public boolean m_7307_(Entity p_20355_) {
        return this.m_20031_(p_20355_.m_5647_());
    }

    public boolean m_20031_(Team p_20032_) {
        if (this.m_5647_() != null) {
            return this.m_5647_().m_83536_(p_20032_);
        }
        return false;
    }

    public void m_6842_(boolean p_20304_) {
        this.m_20115_(5, p_20304_);
    }

    protected boolean m_20291_(int p_20292_) {
        return (this.f_19804_.m_135370_(f_19805_) & 1 << p_20292_) != 0;
    }

    protected void m_20115_(int p_20116_, boolean p_20117_) {
        byte $$2 = this.f_19804_.m_135370_(f_19805_);
        if (p_20117_) {
            this.f_19804_.m_135381_(f_19805_, (byte)($$2 | 1 << p_20116_));
        } else {
            this.f_19804_.m_135381_(f_19805_, (byte)($$2 & ~(1 << p_20116_)));
        }
    }

    public int m_6062_() {
        return 300;
    }

    public int m_20146_() {
        return this.f_19804_.m_135370_(f_19832_);
    }

    public void m_20301_(int p_20302_) {
        this.f_19804_.m_135381_(f_19832_, p_20302_);
    }

    public int m_146888_() {
        return this.f_19804_.m_135370_(f_146800_);
    }

    public void m_146917_(int p_146918_) {
        this.f_19804_.m_135381_(f_146800_, p_146918_);
    }

    public float m_146889_() {
        int $$0 = this.m_146891_();
        return (float)Math.min(this.m_146888_(), $$0) / (float)$$0;
    }

    public boolean m_146890_() {
        return this.m_146888_() >= this.m_146891_();
    }

    public int m_146891_() {
        return 140;
    }

    public void m_8038_(ServerLevel p_19927_, LightningBolt p_19928_) {
        this.m_7311_(this.f_19831_ + 1);
        if (this.f_19831_ == 0) {
            this.m_20254_(8);
        }
        this.m_6469_(DamageSource.f_19306_, 5.0f);
    }

    public void m_6845_(boolean p_20313_) {
        double $$3;
        Vec3 $$1 = this.m_20184_();
        if (p_20313_) {
            double $$2 = Math.max(-0.9, $$1.f_82480_ - 0.03);
        } else {
            $$3 = Math.min(1.8, $$1.f_82480_ + 0.1);
        }
        this.m_20334_($$1.f_82479_, $$3, $$1.f_82481_);
    }

    public void m_20321_(boolean p_20322_) {
        double $$3;
        Vec3 $$1 = this.m_20184_();
        if (p_20322_) {
            double $$2 = Math.max(-0.3, $$1.f_82480_ - 0.03);
        } else {
            $$3 = Math.min(0.7, $$1.f_82480_ + 0.06);
        }
        this.m_20334_($$1.f_82479_, $$3, $$1.f_82481_);
        this.m_183634_();
    }

    public boolean m_214076_(ServerLevel p_216988_, LivingEntity p_216989_) {
        return true;
    }

    public void m_183634_() {
        this.f_19789_ = 0.0f;
    }

    protected void m_20314_(double p_20315_, double p_20316_, double p_20317_) {
        BlockPos $$3 = new BlockPos(p_20315_, p_20316_, p_20317_);
        Vec3 $$4 = new Vec3(p_20315_ - (double)$$3.m_123341_(), p_20316_ - (double)$$3.m_123342_(), p_20317_ - (double)$$3.m_123343_());
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        Direction $$6 = Direction.UP;
        double $$7 = Double.MAX_VALUE;
        for (Direction $$8 : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP}) {
            double $$10;
            $$5.m_122159_($$3, $$8);
            if (this.f_19853_.m_8055_($$5).m_60838_(this.f_19853_, $$5)) continue;
            double $$9 = $$4.m_82507_($$8.m_122434_());
            double d = $$10 = $$8.m_122421_() == Direction.AxisDirection.POSITIVE ? 1.0 - $$9 : $$9;
            if (!($$10 < $$7)) continue;
            $$7 = $$10;
            $$6 = $$8;
        }
        float $$11 = this.f_19796_.m_188501_() * 0.2f + 0.1f;
        float $$12 = $$6.m_122421_().m_122540_();
        Vec3 $$13 = this.m_20184_().m_82490_(0.75);
        if ($$6.m_122434_() == Direction.Axis.X) {
            this.m_20334_($$12 * $$11, $$13.f_82480_, $$13.f_82481_);
        } else if ($$6.m_122434_() == Direction.Axis.Y) {
            this.m_20334_($$13.f_82479_, $$12 * $$11, $$13.f_82481_);
        } else if ($$6.m_122434_() == Direction.Axis.Z) {
            this.m_20334_($$13.f_82479_, $$13.f_82480_, $$12 * $$11);
        }
    }

    public void m_7601_(BlockState p_20006_, Vec3 p_20007_) {
        this.m_183634_();
        this.f_19865_ = p_20007_;
    }

    private static Component m_20140_(Component p_20141_) {
        MutableComponent $$1 = p_20141_.m_6879_().m_6270_(p_20141_.m_7383_().m_131142_(null));
        for (Component $$2 : p_20141_.m_7360_()) {
            $$1.m_7220_(Entity.m_20140_($$2));
        }
        return $$1;
    }

    @Override
    public Component m_7755_() {
        Component $$0 = this.m_7770_();
        if ($$0 != null) {
            return Entity.m_20140_($$0);
        }
        return this.m_5677_();
    }

    protected Component m_5677_() {
        return this.f_19847_.m_20676_();
    }

    public boolean m_7306_(Entity p_20356_) {
        return this == p_20356_;
    }

    public float m_6080_() {
        return 0.0f;
    }

    public void m_5616_(float p_20328_) {
    }

    public void m_5618_(float p_20338_) {
    }

    public boolean m_6097_() {
        return true;
    }

    public boolean m_7313_(Entity p_20357_) {
        return false;
    }

    public String toString() {
        String $$0;
        String string = $$0 = this.f_19853_ == null ? "~NULL~" : this.f_19853_.toString();
        if (this.f_146795_ != null) {
            return String.format(Locale.ROOT, "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f, removed=%s]", new Object[]{this.getClass().getSimpleName(), this.m_7755_().getString(), this.f_19848_, $$0, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.f_146795_});
        }
        return String.format(Locale.ROOT, "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.m_7755_().getString(), this.f_19848_, $$0, this.m_20185_(), this.m_20186_(), this.m_20189_());
    }

    public boolean m_6673_(DamageSource p_20122_) {
        return this.m_213877_() || this.f_19840_ && p_20122_ != DamageSource.f_19317_ && !p_20122_.m_19390_() || p_20122_.m_19384_() && this.m_5825_();
    }

    public boolean m_20147_() {
        return this.f_19840_;
    }

    public void m_20331_(boolean p_20332_) {
        this.f_19840_ = p_20332_;
    }

    public void m_20359_(Entity p_20360_) {
        this.m_7678_(p_20360_.m_20185_(), p_20360_.m_20186_(), p_20360_.m_20189_(), p_20360_.m_146908_(), p_20360_.m_146909_());
    }

    public void m_20361_(Entity p_20362_) {
        CompoundTag $$1 = p_20362_.m_20240_(new CompoundTag());
        $$1.m_128473_("Dimension");
        this.m_20258_($$1);
        this.f_19839_ = p_20362_.f_19839_;
        this.f_19819_ = p_20362_.f_19819_;
    }

    @Nullable
    public Entity m_5489_(ServerLevel p_20118_) {
        if (!(this.f_19853_ instanceof ServerLevel) || this.m_213877_()) {
            return null;
        }
        this.f_19853_.m_46473_().m_6180_("changeDimension");
        this.m_19877_();
        this.f_19853_.m_46473_().m_6180_("reposition");
        PortalInfo $$1 = this.m_7937_(p_20118_);
        if ($$1 == null) {
            return null;
        }
        this.f_19853_.m_46473_().m_6182_("reloading");
        Object $$2 = this.m_6095_().m_20615_(p_20118_);
        if ($$2 != null) {
            ((Entity)$$2).m_20361_(this);
            ((Entity)$$2).m_7678_($$1.f_77676_.f_82479_, $$1.f_77676_.f_82480_, $$1.f_77676_.f_82481_, $$1.f_77678_, ((Entity)$$2).m_146909_());
            ((Entity)$$2).m_20256_($$1.f_77677_);
            p_20118_.m_143334_((Entity)$$2);
            if (p_20118_.m_46472_() == Level.f_46430_) {
                ServerLevel.m_8617_(p_20118_);
            }
        }
        this.m_6089_();
        this.f_19853_.m_46473_().m_7238_();
        ((ServerLevel)this.f_19853_).m_8886_();
        p_20118_.m_8886_();
        this.f_19853_.m_46473_().m_7238_();
        return $$2;
    }

    protected void m_6089_() {
        this.m_142467_(RemovalReason.CHANGED_DIMENSION);
    }

    @Nullable
    protected PortalInfo m_7937_(ServerLevel p_19923_) {
        boolean $$5;
        boolean $$2;
        boolean $$1 = this.f_19853_.m_46472_() == Level.f_46430_ && p_19923_.m_46472_() == Level.f_46428_;
        boolean bl = $$2 = p_19923_.m_46472_() == Level.f_46430_;
        if ($$1 || $$2) {
            BlockPos $$4;
            if ($$2) {
                BlockPos $$3 = ServerLevel.f_8562_;
            } else {
                $$4 = p_19923_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, p_19923_.m_220360_());
            }
            return new PortalInfo(new Vec3((double)$$4.m_123341_() + 0.5, $$4.m_123342_(), (double)$$4.m_123343_() + 0.5), this.m_20184_(), this.m_146908_(), this.m_146909_());
        }
        boolean bl2 = $$5 = p_19923_.m_46472_() == Level.f_46429_;
        if (this.f_19853_.m_46472_() != Level.f_46429_ && !$$5) {
            return null;
        }
        WorldBorder $$6 = p_19923_.m_6857_();
        double $$7 = DimensionType.m_63908_(this.f_19853_.m_6042_(), p_19923_.m_6042_());
        BlockPos $$8 = $$6.m_187569_(this.m_20185_() * $$7, this.m_20186_(), this.m_20189_() * $$7);
        return this.m_183318_(p_19923_, $$8, $$5, $$6).map(p_185941_ -> {
            Vec3 $$7;
            Direction.Axis $$6;
            BlockState $$2 = this.f_19853_.m_8055_(this.f_19819_);
            if ($$2.m_61138_(BlockStateProperties.f_61364_)) {
                Direction.Axis $$3 = $$2.m_61143_(BlockStateProperties.f_61364_);
                BlockUtil.FoundRectangle $$4 = BlockUtil.m_124334_(this.f_19819_, $$3, 21, Direction.Axis.Y, 21, p_185959_ -> this.f_19853_.m_8055_((BlockPos)p_185959_) == $$2);
                Vec3 $$5 = this.m_7643_($$3, $$4);
            } else {
                $$6 = Direction.Axis.X;
                $$7 = new Vec3(0.5, 0.0, 0.0);
            }
            return PortalShape.m_77699_(p_19923_, p_185941_, $$6, $$7, this.m_6972_(this.m_20089_()), this.m_20184_(), this.m_146908_(), this.m_146909_());
        }).orElse(null);
    }

    protected Vec3 m_7643_(Direction.Axis p_20045_, BlockUtil.FoundRectangle p_20046_) {
        return PortalShape.m_77738_(p_20046_, p_20045_, this.m_20182_(), this.m_6972_(this.m_20089_()));
    }

    protected Optional<BlockUtil.FoundRectangle> m_183318_(ServerLevel p_185935_, BlockPos p_185936_, boolean p_185937_, WorldBorder p_185938_) {
        return p_185935_.m_8871_().m_192985_(p_185936_, p_185937_, p_185938_);
    }

    public boolean m_6072_() {
        return true;
    }

    public float m_7077_(Explosion p_19992_, BlockGetter p_19993_, BlockPos p_19994_, BlockState p_19995_, FluidState p_19996_, float p_19997_) {
        return p_19997_;
    }

    public boolean m_7349_(Explosion p_19987_, BlockGetter p_19988_, BlockPos p_19989_, BlockState p_19990_, float p_19991_) {
        return true;
    }

    public int m_6056_() {
        return 3;
    }

    public boolean m_6090_() {
        return false;
    }

    public void m_7976_(CrashReportCategory p_20051_) {
        p_20051_.m_128165_("Entity Type", () -> EntityType.m_20613_(this.m_6095_()) + " (" + this.getClass().getCanonicalName() + ")");
        p_20051_.m_128159_("Entity ID", this.f_19848_);
        p_20051_.m_128165_("Entity Name", () -> this.m_7755_().getString());
        p_20051_.m_128159_("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.m_20185_(), this.m_20186_(), this.m_20189_()));
        p_20051_.m_128159_("Entity's Block location", CrashReportCategory.m_178942_(this.f_19853_, Mth.m_14107_(this.m_20185_()), Mth.m_14107_(this.m_20186_()), Mth.m_14107_(this.m_20189_())));
        Vec3 $$1 = this.m_20184_();
        p_20051_.m_128159_("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", $$1.f_82479_, $$1.f_82480_, $$1.f_82481_));
        p_20051_.m_128165_("Entity's Passengers", () -> this.m_20197_().toString());
        p_20051_.m_128165_("Entity's Vehicle", () -> String.valueOf(this.m_20202_()));
    }

    public boolean m_6051_() {
        return this.m_6060_() && !this.m_5833_();
    }

    public void m_20084_(UUID p_20085_) {
        this.f_19820_ = p_20085_;
        this.f_19821_ = this.f_19820_.toString();
    }

    @Override
    public UUID m_20148_() {
        return this.f_19820_;
    }

    public String m_20149_() {
        return this.f_19821_;
    }

    public String m_6302_() {
        return this.f_19821_;
    }

    public boolean m_6063_() {
        return true;
    }

    public static double m_20150_() {
        return f_19846_;
    }

    public static void m_20103_(double p_20104_) {
        f_19846_ = p_20104_;
    }

    @Override
    public Component m_5446_() {
        return PlayerTeam.m_83348_(this.m_5647_(), this.m_7755_()).m_130938_(p_185975_ -> p_185975_.m_131144_(this.m_20190_()).m_131138_(this.m_20149_()));
    }

    public void m_6593_(@Nullable Component p_20053_) {
        this.f_19804_.m_135381_(f_19833_, Optional.ofNullable(p_20053_));
    }

    @Override
    @Nullable
    public Component m_7770_() {
        return this.f_19804_.m_135370_(f_19833_).orElse(null);
    }

    @Override
    public boolean m_8077_() {
        return this.f_19804_.m_135370_(f_19833_).isPresent();
    }

    public void m_20340_(boolean p_20341_) {
        this.f_19804_.m_135381_(f_19834_, p_20341_);
    }

    public boolean m_20151_() {
        return this.f_19804_.m_135370_(f_19834_);
    }

    public final void m_20324_(double p_20325_, double p_20326_, double p_20327_) {
        if (!(this.f_19853_ instanceof ServerLevel)) {
            return;
        }
        ChunkPos $$3 = new ChunkPos(new BlockPos(p_20325_, p_20326_, p_20327_));
        ((ServerLevel)this.f_19853_).m_7726_().m_8387_(TicketType.f_9448_, $$3, 0, this.m_19879_());
        this.f_19853_.m_6325_($$3.f_45578_, $$3.f_45579_);
        this.m_6021_(p_20325_, p_20326_, p_20327_);
    }

    public void m_142098_(double p_146825_, double p_146826_, double p_146827_) {
        this.m_6021_(p_146825_, p_146826_, p_146827_);
    }

    public void m_6021_(double p_19887_, double p_19888_, double p_19889_) {
        if (!(this.f_19853_ instanceof ServerLevel)) {
            return;
        }
        this.m_7678_(p_19887_, p_19888_, p_19889_, this.m_146908_(), this.m_146909_());
        this.m_20199_().forEach(p_185977_ -> {
            for (Entity $$1 : p_185977_.f_19823_) {
                p_185977_.m_19956_($$1, Entity::m_6027_);
            }
        });
    }

    public boolean m_6052_() {
        return this.m_20151_();
    }

    public void m_7350_(EntityDataAccessor<?> p_20059_) {
        if (f_19806_.equals(p_20059_)) {
            this.m_6210_();
        }
    }

    public void m_6210_() {
        boolean $$3;
        EntityDimensions $$2;
        EntityDimensions $$0 = this.f_19815_;
        Pose $$1 = this.m_20089_();
        this.f_19815_ = $$2 = this.m_6972_($$1);
        this.f_19816_ = this.m_6380_($$1, $$2);
        this.m_20090_();
        boolean bl = $$3 = (double)$$2.f_20377_ <= 4.0 && (double)$$2.f_20378_ <= 4.0;
        if (!(this.f_19853_.f_46443_ || this.f_19803_ || this.f_19794_ || !$$3 || !($$2.f_20377_ > $$0.f_20377_) && !($$2.f_20378_ > $$0.f_20378_) || this instanceof Player)) {
            Vec3 $$4 = this.m_20182_().m_82520_(0.0, (double)$$0.f_20378_ / 2.0, 0.0);
            double $$5 = (double)Math.max(0.0f, $$2.f_20377_ - $$0.f_20377_) + 1.0E-6;
            double $$6 = (double)Math.max(0.0f, $$2.f_20378_ - $$0.f_20378_) + 1.0E-6;
            VoxelShape $$7 = Shapes.m_83064_(AABB.m_165882_($$4, $$5, $$6, $$5));
            this.f_19853_.m_151418_(this, $$7, $$4, $$2.f_20377_, $$2.f_20378_, $$2.f_20377_).ifPresent(p_185956_ -> this.m_146884_(p_185956_.m_82520_(0.0, (double)(-p_185955_.f_20378_) / 2.0, 0.0)));
        }
    }

    public Direction m_6350_() {
        return Direction.m_122364_(this.m_146908_());
    }

    public Direction m_6374_() {
        return this.m_6350_();
    }

    protected HoverEvent m_20190_() {
        return new HoverEvent(HoverEvent.Action.f_130833_, new HoverEvent.EntityTooltipInfo(this.m_6095_(), this.m_20148_(), this.m_7755_()));
    }

    public boolean m_6459_(ServerPlayer p_19937_) {
        return true;
    }

    @Override
    public final AABB m_20191_() {
        return this.f_19828_;
    }

    public AABB m_6921_() {
        return this.m_20191_();
    }

    protected AABB m_20217_(Pose p_20218_) {
        EntityDimensions $$1 = this.m_6972_(p_20218_);
        float $$2 = $$1.f_20377_ / 2.0f;
        Vec3 $$3 = new Vec3(this.m_20185_() - (double)$$2, this.m_20186_(), this.m_20189_() - (double)$$2);
        Vec3 $$4 = new Vec3(this.m_20185_() + (double)$$2, this.m_20186_() + (double)$$1.f_20378_, this.m_20189_() + (double)$$2);
        return new AABB($$3, $$4);
    }

    public final void m_20011_(AABB p_20012_) {
        this.f_19828_ = p_20012_;
    }

    protected float m_6380_(Pose p_19976_, EntityDimensions p_19977_) {
        return p_19977_.f_20378_ * 0.85f;
    }

    public float m_20236_(Pose p_20237_) {
        return this.m_6380_(p_20237_, this.m_6972_(p_20237_));
    }

    public final float m_20192_() {
        return this.f_19816_;
    }

    public Vec3 m_7939_() {
        return new Vec3(0.0, this.m_20192_(), this.m_20205_() * 0.4f);
    }

    public SlotAccess m_141942_(int p_146919_) {
        return SlotAccess.f_147290_;
    }

    @Override
    public void m_213846_(Component p_216998_) {
    }

    public Level m_20193_() {
        return this.f_19853_;
    }

    @Nullable
    public MinecraftServer m_20194_() {
        return this.f_19853_.m_7654_();
    }

    public InteractionResult m_7111_(Player p_19980_, Vec3 p_19981_, InteractionHand p_19982_) {
        return InteractionResult.PASS;
    }

    public boolean m_6128_() {
        return false;
    }

    public void m_19970_(LivingEntity p_19971_, Entity p_19972_) {
        if (p_19972_ instanceof LivingEntity) {
            EnchantmentHelper.m_44823_((LivingEntity)p_19972_, p_19971_);
        }
        EnchantmentHelper.m_44896_(p_19971_, p_19972_);
    }

    public void m_6457_(ServerPlayer p_20119_) {
    }

    public void m_6452_(ServerPlayer p_20174_) {
    }

    public float m_7890_(Rotation p_20004_) {
        float $$1 = Mth.m_14177_(this.m_146908_());
        switch (p_20004_) {
            case CLOCKWISE_180: {
                return $$1 + 180.0f;
            }
            case COUNTERCLOCKWISE_90: {
                return $$1 + 270.0f;
            }
            case CLOCKWISE_90: {
                return $$1 + 90.0f;
            }
        }
        return $$1;
    }

    public float m_6961_(Mirror p_20003_) {
        float $$1 = Mth.m_14177_(this.m_146908_());
        switch (p_20003_) {
            case FRONT_BACK: {
                return -$$1;
            }
            case LEFT_RIGHT: {
                return 180.0f - $$1;
            }
        }
        return $$1;
    }

    public boolean m_6127_() {
        return false;
    }

    @Nullable
    public Entity m_6688_() {
        return null;
    }

    public final boolean m_217005_() {
        return this.m_6688_() != null;
    }

    public final List<Entity> m_20197_() {
        return this.f_19823_;
    }

    @Nullable
    public Entity m_146895_() {
        return this.f_19823_.isEmpty() ? null : (Entity)this.f_19823_.get(0);
    }

    public boolean m_20363_(Entity p_20364_) {
        return this.f_19823_.contains((Object)p_20364_);
    }

    public boolean m_146862_(Predicate<Entity> p_146863_) {
        for (Entity $$1 : this.f_19823_) {
            if (!p_146863_.test($$1)) continue;
            return true;
        }
        return false;
    }

    private Stream<Entity> m_146920_() {
        return this.f_19823_.stream().flatMap(Entity::m_20199_);
    }

    public Stream<Entity> m_20199_() {
        return Stream.concat(Stream.of(this), this.m_146920_());
    }

    public Stream<Entity> m_142429_() {
        return Stream.concat(this.f_19823_.stream().flatMap(Entity::m_142429_), Stream.of(this));
    }

    public Iterable<Entity> m_146897_() {
        return () -> this.m_146920_().iterator();
    }

    public boolean m_146898_() {
        return this.m_146920_().filter(p_185943_ -> p_185943_ instanceof Player).count() == 1L;
    }

    public Entity m_20201_() {
        Entity $$0 = this;
        while ($$0.m_20159_()) {
            $$0 = $$0.m_20202_();
        }
        return $$0;
    }

    public boolean m_20365_(Entity p_20366_) {
        return this.m_20201_() == p_20366_.m_20201_();
    }

    public boolean m_20367_(Entity p_20368_) {
        return this.m_146920_().anyMatch(p_185946_ -> p_185946_ == p_20368_);
    }

    public boolean m_6109_() {
        Entity $$0 = this.m_6688_();
        if ($$0 instanceof Player) {
            return ((Player)$$0).m_7578_();
        }
        return !this.f_19853_.f_46443_;
    }

    protected static Vec3 m_19903_(double p_19904_, double p_19905_, float p_19906_) {
        double $$3 = (p_19904_ + p_19905_ + (double)1.0E-5f) / 2.0;
        float $$4 = -Mth.m_14031_(p_19906_ * ((float)Math.PI / 180));
        float $$5 = Mth.m_14089_(p_19906_ * ((float)Math.PI / 180));
        float $$6 = Math.max(Math.abs($$4), Math.abs($$5));
        return new Vec3((double)$$4 * $$3 / (double)$$6, 0.0, (double)$$5 * $$3 / (double)$$6);
    }

    public Vec3 m_7688_(LivingEntity p_20123_) {
        return new Vec3(this.m_20185_(), this.m_20191_().f_82292_, this.m_20189_());
    }

    @Nullable
    public Entity m_20202_() {
        return this.f_19824_;
    }

    public PushReaction m_7752_() {
        return PushReaction.NORMAL;
    }

    public SoundSource m_5720_() {
        return SoundSource.NEUTRAL;
    }

    protected int m_6101_() {
        return 1;
    }

    public CommandSourceStack m_20203_() {
        return new CommandSourceStack(this, this.m_20182_(), this.m_20155_(), this.f_19853_ instanceof ServerLevel ? (ServerLevel)this.f_19853_ : null, this.m_8088_(), this.m_7755_().getString(), this.m_5446_(), this.f_19853_.m_7654_(), this);
    }

    protected int m_8088_() {
        return 0;
    }

    public boolean m_20310_(int p_20311_) {
        return this.m_8088_() >= p_20311_;
    }

    @Override
    public boolean m_6999_() {
        return this.f_19853_.m_46469_().m_46207_(GameRules.f_46144_);
    }

    @Override
    public boolean m_7028_() {
        return true;
    }

    @Override
    public boolean m_6102_() {
        return true;
    }

    public void m_7618_(EntityAnchorArgument.Anchor p_20033_, Vec3 p_20034_) {
        Vec3 $$2 = p_20033_.m_90377_(this);
        double $$3 = p_20034_.f_82479_ - $$2.f_82479_;
        double $$4 = p_20034_.f_82480_ - $$2.f_82480_;
        double $$5 = p_20034_.f_82481_ - $$2.f_82481_;
        double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
        this.m_146926_(Mth.m_14177_((float)(-(Mth.m_14136_($$4, $$6) * 57.2957763671875))));
        this.m_146922_(Mth.m_14177_((float)(Mth.m_14136_($$5, $$3) * 57.2957763671875) - 90.0f));
        this.m_5616_(this.m_146908_());
        this.f_19860_ = this.m_146909_();
        this.f_19859_ = this.m_146908_();
    }

    public boolean m_204031_(TagKey<Fluid> p_204032_, double p_204033_) {
        if (this.m_146899_()) {
            return false;
        }
        AABB $$2 = this.m_20191_().m_82406_(0.001);
        int $$3 = Mth.m_14107_($$2.f_82288_);
        int $$4 = Mth.m_14165_($$2.f_82291_);
        int $$5 = Mth.m_14107_($$2.f_82289_);
        int $$6 = Mth.m_14165_($$2.f_82292_);
        int $$7 = Mth.m_14107_($$2.f_82290_);
        int $$8 = Mth.m_14165_($$2.f_82293_);
        double $$9 = 0.0;
        boolean $$10 = this.m_6063_();
        boolean $$11 = false;
        Vec3 $$12 = Vec3.f_82478_;
        int $$13 = 0;
        BlockPos.MutableBlockPos $$14 = new BlockPos.MutableBlockPos();
        for (int $$15 = $$3; $$15 < $$4; ++$$15) {
            for (int $$16 = $$5; $$16 < $$6; ++$$16) {
                for (int $$17 = $$7; $$17 < $$8; ++$$17) {
                    double $$19;
                    $$14.m_122178_($$15, $$16, $$17);
                    FluidState $$18 = this.f_19853_.m_6425_($$14);
                    if (!$$18.m_205070_(p_204032_) || !(($$19 = (double)((float)$$16 + $$18.m_76155_(this.f_19853_, $$14))) >= $$2.f_82289_)) continue;
                    $$11 = true;
                    $$9 = Math.max($$19 - $$2.f_82289_, $$9);
                    if (!$$10) continue;
                    Vec3 $$20 = $$18.m_76179_(this.f_19853_, $$14);
                    if ($$9 < 0.4) {
                        $$20 = $$20.m_82490_($$9);
                    }
                    $$12 = $$12.m_82549_($$20);
                    ++$$13;
                }
            }
        }
        if ($$12.m_82553_() > 0.0) {
            if ($$13 > 0) {
                $$12 = $$12.m_82490_(1.0 / (double)$$13);
            }
            if (!(this instanceof Player)) {
                $$12 = $$12.m_82541_();
            }
            Vec3 $$21 = this.m_20184_();
            $$12 = $$12.m_82490_(p_204033_ * 1.0);
            double $$22 = 0.003;
            if (Math.abs($$21.f_82479_) < 0.003 && Math.abs($$21.f_82481_) < 0.003 && $$12.m_82553_() < 0.0045000000000000005) {
                $$12 = $$12.m_82541_().m_82490_(0.0045000000000000005);
            }
            this.m_20256_(this.m_20184_().m_82549_($$12));
        }
        this.f_19799_.put(p_204032_, $$9);
        return $$11;
    }

    public boolean m_146899_() {
        int $$4;
        AABB $$0 = this.m_20191_().m_82400_(1.0);
        int $$1 = Mth.m_14107_($$0.f_82288_);
        int $$2 = Mth.m_14165_($$0.f_82291_);
        int $$3 = Mth.m_14107_($$0.f_82290_);
        return !this.f_19853_.m_151572_($$1, $$3, $$2, $$4 = Mth.m_14165_($$0.f_82293_));
    }

    public double m_204036_(TagKey<Fluid> p_204037_) {
        return this.f_19799_.getDouble(p_204037_);
    }

    public double m_20204_() {
        return (double)this.m_20192_() < 0.4 ? 0.0 : 0.4;
    }

    public final float m_20205_() {
        return this.f_19815_.f_20377_;
    }

    public final float m_20206_() {
        return this.f_19815_.f_20378_;
    }

    public abstract Packet<?> m_5654_();

    public EntityDimensions m_6972_(Pose p_19975_) {
        return this.f_19847_.m_20680_();
    }

    public Vec3 m_20182_() {
        return this.f_19825_;
    }

    public Vec3 m_213870_() {
        return this.m_20182_();
    }

    @Override
    public BlockPos m_20183_() {
        return this.f_19826_;
    }

    public BlockState m_146900_() {
        if (this.f_185934_ == null) {
            this.f_185934_ = this.f_19853_.m_8055_(this.m_20183_());
        }
        return this.f_185934_;
    }

    public ChunkPos m_146902_() {
        return this.f_185933_;
    }

    public Vec3 m_20184_() {
        return this.f_19827_;
    }

    public void m_20256_(Vec3 p_20257_) {
        this.f_19827_ = p_20257_;
    }

    public void m_20334_(double p_20335_, double p_20336_, double p_20337_) {
        this.m_20256_(new Vec3(p_20335_, p_20336_, p_20337_));
    }

    public final int m_146903_() {
        return this.f_19826_.m_123341_();
    }

    public final double m_20185_() {
        return this.f_19825_.f_82479_;
    }

    public double m_20165_(double p_20166_) {
        return this.f_19825_.f_82479_ + (double)this.m_20205_() * p_20166_;
    }

    public double m_20208_(double p_20209_) {
        return this.m_20165_((2.0 * this.f_19796_.m_188500_() - 1.0) * p_20209_);
    }

    public final int m_146904_() {
        return this.f_19826_.m_123342_();
    }

    public final double m_20186_() {
        return this.f_19825_.f_82480_;
    }

    public double m_20227_(double p_20228_) {
        return this.f_19825_.f_82480_ + (double)this.m_20206_() * p_20228_;
    }

    public double m_20187_() {
        return this.m_20227_(this.f_19796_.m_188500_());
    }

    public double m_20188_() {
        return this.f_19825_.f_82480_ + (double)this.f_19816_;
    }

    public final int m_146907_() {
        return this.f_19826_.m_123343_();
    }

    public final double m_20189_() {
        return this.f_19825_.f_82481_;
    }

    public double m_20246_(double p_20247_) {
        return this.f_19825_.f_82481_ + (double)this.m_20205_() * p_20247_;
    }

    public double m_20262_(double p_20263_) {
        return this.m_20246_((2.0 * this.f_19796_.m_188500_() - 1.0) * p_20263_);
    }

    public final void m_20343_(double p_20344_, double p_20345_, double p_20346_) {
        if (this.f_19825_.f_82479_ != p_20344_ || this.f_19825_.f_82480_ != p_20345_ || this.f_19825_.f_82481_ != p_20346_) {
            this.f_19825_ = new Vec3(p_20344_, p_20345_, p_20346_);
            int $$3 = Mth.m_14107_(p_20344_);
            int $$4 = Mth.m_14107_(p_20345_);
            int $$5 = Mth.m_14107_(p_20346_);
            if ($$3 != this.f_19826_.m_123341_() || $$4 != this.f_19826_.m_123342_() || $$5 != this.f_19826_.m_123343_()) {
                this.f_19826_ = new BlockPos($$3, $$4, $$5);
                this.f_185934_ = null;
                if (SectionPos.m_123171_($$3) != this.f_185933_.f_45578_ || SectionPos.m_123171_($$5) != this.f_185933_.f_45579_) {
                    this.f_185933_ = new ChunkPos(this.f_19826_);
                }
            }
            this.f_146801_.m_142044_();
        }
    }

    public void m_6043_() {
    }

    public Vec3 m_7398_(float p_20347_) {
        return this.m_20318_(p_20347_).m_82520_(0.0, (double)this.f_19816_ * 0.7, 0.0);
    }

    public void m_141965_(ClientboundAddEntityPacket p_146866_) {
        int $$1 = p_146866_.m_131496_();
        double $$2 = p_146866_.m_131500_();
        double $$3 = p_146866_.m_131501_();
        double $$4 = p_146866_.m_131502_();
        this.m_217006_($$2, $$3, $$4);
        this.m_6027_($$2, $$3, $$4);
        this.m_146926_(p_146866_.m_237566_());
        this.m_146922_(p_146866_.m_237567_());
        this.m_20234_($$1);
        this.m_20084_(p_146866_.m_131499_());
    }

    @Nullable
    public ItemStack m_142340_() {
        return null;
    }

    public void m_146924_(boolean p_146925_) {
        this.f_146808_ = p_146925_;
    }

    public boolean m_142079_() {
        return !this.m_6095_().m_204039_(EntityTypeTags.f_144294_);
    }

    public boolean m_203117_() {
        return (this.f_146808_ || this.f_146809_) && this.m_142079_();
    }

    public float m_146908_() {
        return this.f_19857_;
    }

    public float m_213816_() {
        return this.m_146908_();
    }

    public void m_146922_(float p_146923_) {
        if (!Float.isFinite(p_146923_)) {
            Util.m_143785_("Invalid entity rotation: " + p_146923_ + ", discarding.");
            return;
        }
        this.f_19857_ = p_146923_;
    }

    public float m_146909_() {
        return this.f_19858_;
    }

    public void m_146926_(float p_146927_) {
        if (!Float.isFinite(p_146927_)) {
            Util.m_143785_("Invalid entity rotation: " + p_146927_ + ", discarding.");
            return;
        }
        this.f_19858_ = p_146927_;
    }

    public final boolean m_213877_() {
        return this.f_146795_ != null;
    }

    @Nullable
    public RemovalReason m_146911_() {
        return this.f_146795_;
    }

    @Override
    public final void m_142467_(RemovalReason p_146876_) {
        if (this.f_146795_ == null) {
            this.f_146795_ = p_146876_;
        }
        if (this.f_146795_.m_146965_()) {
            this.m_8127_();
        }
        this.m_20197_().forEach(Entity::m_8127_);
        this.f_146801_.m_142472_(p_146876_);
    }

    protected void m_146912_() {
        this.f_146795_ = null;
    }

    @Override
    public void m_141960_(EntityInLevelCallback p_146849_) {
        this.f_146801_ = p_146849_;
    }

    @Override
    public boolean m_142391_() {
        if (this.f_146795_ != null && !this.f_146795_.m_146966_()) {
            return false;
        }
        if (this.m_20159_()) {
            return false;
        }
        return !this.m_20160_() || !this.m_146898_();
    }

    @Override
    public boolean m_142389_() {
        return false;
    }

    public boolean m_142265_(Level p_146843_, BlockPos p_146844_) {
        return true;
    }

    public Level m_9236_() {
        return this.f_19853_;
    }

    public static final class RemovalReason
    extends Enum<RemovalReason> {
        public static final /* enum */ RemovalReason KILLED = new RemovalReason(true, false);
        public static final /* enum */ RemovalReason DISCARDED = new RemovalReason(true, false);
        public static final /* enum */ RemovalReason UNLOADED_TO_CHUNK = new RemovalReason(false, true);
        public static final /* enum */ RemovalReason UNLOADED_WITH_PLAYER = new RemovalReason(false, false);
        public static final /* enum */ RemovalReason CHANGED_DIMENSION = new RemovalReason(false, false);
        private final boolean f_146956_;
        private final boolean f_146957_;
        private static final /* synthetic */ RemovalReason[] $VALUES;

        public static RemovalReason[] values() {
            return (RemovalReason[])$VALUES.clone();
        }

        public static RemovalReason valueOf(String p_146969_) {
            return Enum.valueOf(RemovalReason.class, p_146969_);
        }

        private RemovalReason(boolean p_146963_, boolean p_146964_) {
            this.f_146956_ = p_146963_;
            this.f_146957_ = p_146964_;
        }

        public boolean m_146965_() {
            return this.f_146956_;
        }

        public boolean m_146966_() {
            return this.f_146957_;
        }

        private static /* synthetic */ RemovalReason[] m_146967_() {
            return new RemovalReason[]{KILLED, DISCARDED, UNLOADED_TO_CHUNK, UNLOADED_WITH_PLAYER, CHANGED_DIMENSION};
        }

        static {
            $VALUES = RemovalReason.m_146967_();
        }
    }

    public static final class MovementEmission
    extends Enum<MovementEmission> {
        public static final /* enum */ MovementEmission NONE = new MovementEmission(false, false);
        public static final /* enum */ MovementEmission SOUNDS = new MovementEmission(true, false);
        public static final /* enum */ MovementEmission EVENTS = new MovementEmission(false, true);
        public static final /* enum */ MovementEmission ALL = new MovementEmission(true, true);
        final boolean f_146935_;
        final boolean f_146936_;
        private static final /* synthetic */ MovementEmission[] $VALUES;

        public static MovementEmission[] values() {
            return (MovementEmission[])$VALUES.clone();
        }

        public static MovementEmission valueOf(String p_146949_) {
            return Enum.valueOf(MovementEmission.class, p_146949_);
        }

        private MovementEmission(boolean p_146942_, boolean p_146943_) {
            this.f_146935_ = p_146942_;
            this.f_146936_ = p_146943_;
        }

        public boolean m_146944_() {
            return this.f_146936_ || this.f_146935_;
        }

        public boolean m_146945_() {
            return this.f_146936_;
        }

        public boolean m_146946_() {
            return this.f_146935_;
        }

        private static /* synthetic */ MovementEmission[] m_146947_() {
            return new MovementEmission[]{NONE, SOUNDS, EVENTS, ALL};
        }

        static {
            $VALUES = MovementEmission.m_146947_();
        }
    }

    @FunctionalInterface
    public static interface MoveFunction {
        public void m_20372_(Entity var1, double var2, double var4, double var6);
    }
}

