/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.ticks.BlackholeTickAccess;
import net.minecraft.world.ticks.LevelTickAccess;
import org.slf4j.Logger;

public class ClientLevel
extends Level {
    private static final Logger f_233600_ = LogUtils.getLogger();
    private static final double f_171629_ = 0.05;
    private static final int f_194125_ = 10;
    private static final int f_194126_ = 1000;
    final EntityTickList f_171630_ = new EntityTickList();
    private final TransientEntitySectionManager<Entity> f_171631_ = new TransientEntitySectionManager<Entity>(Entity.class, new EntityCallbacks());
    private final ClientPacketListener f_104561_;
    private final LevelRenderer f_104562_;
    private final ClientLevelData f_104563_;
    private final DimensionSpecialEffects f_104564_;
    private final Minecraft f_104565_ = Minecraft.m_91087_();
    final List<AbstractClientPlayer> f_104566_ = Lists.newArrayList();
    private Scoreboard f_104555_ = new Scoreboard();
    private final Map<String, MapItemSavedData> f_104556_ = Maps.newHashMap();
    private static final long f_171628_ = 0xFFFFFFL;
    private int f_104557_;
    private final Object2ObjectArrayMap<ColorResolver, BlockTintCache> f_104558_ = Util.m_137469_(new Object2ObjectArrayMap(3), p_194170_ -> {
        p_194170_.put((Object)BiomeColors.f_108789_, (Object)new BlockTintCache(p_194181_ -> this.m_104762_((BlockPos)p_194181_, BiomeColors.f_108789_)));
        p_194170_.put((Object)BiomeColors.f_108790_, (Object)new BlockTintCache(p_194177_ -> this.m_104762_((BlockPos)p_194177_, BiomeColors.f_108790_)));
        p_194170_.put((Object)BiomeColors.f_108791_, (Object)new BlockTintCache(p_194168_ -> this.m_104762_((BlockPos)p_194168_, BiomeColors.f_108791_)));
    });
    private final ClientChunkCache f_104559_;
    private final Deque<Runnable> f_194122_ = Queues.newArrayDeque();
    private int f_194123_;
    private final BlockStatePredictionHandler f_233599_ = new BlockStatePredictionHandler();
    private static final Set<Item> f_194124_ = Set.of(Items.f_42127_, Items.f_151033_);

    public void m_233651_(int p_233652_) {
        this.f_233599_.m_233856_(p_233652_, this);
    }

    public void m_233653_(BlockPos p_233654_, BlockState p_233655_, int p_233656_) {
        if (!this.f_233599_.m_233864_(p_233654_, p_233655_)) {
            super.m_6933_(p_233654_, p_233655_, p_233656_, 512);
        }
    }

    public void m_233647_(BlockPos p_233648_, BlockState p_233649_, Vec3 p_233650_) {
        BlockState $$3 = this.m_8055_(p_233648_);
        if ($$3 != p_233649_) {
            this.m_7731_(p_233648_, p_233649_, 19);
            LocalPlayer $$4 = this.f_104565_.f_91074_;
            if (this == $$4.f_19853_ && $$4.m_20039_(p_233648_, p_233649_)) {
                $$4.m_20248_(p_233650_.f_82479_, p_233650_.f_82480_, p_233650_.f_82481_);
            }
        }
    }

    BlockStatePredictionHandler m_233601_() {
        return this.f_233599_;
    }

    @Override
    public boolean m_6933_(BlockPos p_233643_, BlockState p_233644_, int p_233645_, int p_233646_) {
        if (this.f_233599_.m_233872_()) {
            BlockState $$4 = this.m_8055_(p_233643_);
            boolean $$5 = super.m_6933_(p_233643_, p_233644_, p_233645_, p_233646_);
            if ($$5) {
                this.f_233599_.m_233867_(p_233643_, $$4, this.f_104565_.f_91074_);
            }
            return $$5;
        }
        return super.m_6933_(p_233643_, p_233644_, p_233645_, p_233646_);
    }

    public ClientLevel(ClientPacketListener p_205505_, ClientLevelData p_205506_, ResourceKey<Level> p_205507_, Holder<DimensionType> p_205508_, int p_205509_, int p_205510_, Supplier<ProfilerFiller> p_205511_, LevelRenderer p_205512_, boolean p_205513_, long p_205514_) {
        super(p_205506_, p_205507_, p_205508_, p_205511_, true, p_205513_, p_205514_, 1000000);
        this.f_104561_ = p_205505_;
        this.f_104559_ = new ClientChunkCache(this, p_205509_);
        this.f_104563_ = p_205506_;
        this.f_104562_ = p_205512_;
        this.f_104564_ = DimensionSpecialEffects.m_108876_(p_205508_.m_203334_());
        this.m_104752_(new BlockPos(8, 64, 8), 0.0f);
        this.f_194123_ = p_205510_;
        this.m_46465_();
        this.m_46466_();
    }

    public void m_194171_(Runnable p_194172_) {
        this.f_194122_.add(p_194172_);
    }

    public void m_194141_() {
        Runnable $$3;
        int $$0 = this.f_194122_.size();
        int $$1 = $$0 < 1000 ? Math.max(10, $$0 / 10) : $$0;
        for (int $$2 = 0; $$2 < $$1 && ($$3 = this.f_194122_.poll()) != null; ++$$2) {
            $$3.run();
        }
    }

    public boolean m_194173_() {
        return this.f_194122_.isEmpty();
    }

    public DimensionSpecialEffects m_104583_() {
        return this.f_104564_;
    }

    public void m_104726_(BooleanSupplier p_104727_) {
        this.m_6857_().m_61969_();
        this.m_104826_();
        this.m_46473_().m_6180_("blocks");
        this.f_104559_.m_201698_(p_104727_, true);
        this.m_46473_().m_7238_();
    }

    private void m_104826_() {
        this.m_104637_(this.f_46442_.m_6793_() + 1L);
        if (this.f_46442_.m_5470_().m_46207_(GameRules.f_46140_)) {
            this.m_104746_(this.f_46442_.m_6792_() + 1L);
        }
    }

    public void m_104637_(long p_104638_) {
        this.f_104563_.m_104849_(p_104638_);
    }

    public void m_104746_(long p_104747_) {
        if (p_104747_ < 0L) {
            p_104747_ = -p_104747_;
            this.m_46469_().m_46170_(GameRules.f_46140_).m_46246_(false, null);
        } else {
            this.m_46469_().m_46170_(GameRules.f_46140_).m_46246_(true, null);
        }
        this.f_104563_.m_104863_(p_104747_);
    }

    public Iterable<Entity> m_104735_() {
        return this.m_142646_().m_142273_();
    }

    public void m_104804_() {
        ProfilerFiller $$0 = this.m_46473_();
        $$0.m_6180_("entities");
        this.f_171630_.m_156910_(p_194183_ -> {
            if (p_194183_.m_213877_() || p_194183_.m_20159_()) {
                return;
            }
            this.m_46653_(this::m_104639_, p_194183_);
        });
        $$0.m_7238_();
        this.m_46463_();
    }

    @Override
    public boolean m_183599_(Entity p_194185_) {
        return p_194185_.m_146902_().m_45594_(this.f_104565_.f_91074_.m_146902_()) <= this.f_194123_;
    }

    public void m_104639_(Entity p_104640_) {
        p_104640_.m_146867_();
        ++p_104640_.f_19797_;
        this.m_46473_().m_6521_(() -> Registry.f_122826_.m_7981_(p_104640_.m_6095_()).toString());
        p_104640_.m_8119_();
        this.m_46473_().m_7238_();
        for (Entity $$1 : p_104640_.m_20197_()) {
            this.m_104641_(p_104640_, $$1);
        }
    }

    private void m_104641_(Entity p_104642_, Entity p_104643_) {
        if (p_104643_.m_213877_() || p_104643_.m_20202_() != p_104642_) {
            p_104643_.m_8127_();
            return;
        }
        if (!(p_104643_ instanceof Player) && !this.f_171630_.m_156914_(p_104643_)) {
            return;
        }
        p_104643_.m_146867_();
        ++p_104643_.f_19797_;
        p_104643_.m_6083_();
        for (Entity $$2 : p_104643_.m_20197_()) {
            this.m_104641_(p_104643_, $$2);
        }
    }

    public void m_104665_(LevelChunk p_104666_) {
        p_104666_.m_187957_();
        this.f_104559_.m_7827_().m_6460_(p_104666_.m_7697_(), false);
        this.f_171631_.m_157658_(p_104666_.m_7697_());
    }

    public void m_171649_(ChunkPos p_171650_) {
        this.f_104558_.forEach((p_194154_, p_194155_) -> p_194155_.m_92655_(p_194153_.f_45578_, p_194153_.f_45579_));
        this.f_171631_.m_157651_(p_171650_);
    }

    public void m_104810_() {
        this.f_104558_.forEach((p_194157_, p_194158_) -> p_194158_.m_92654_());
    }

    @Override
    public boolean m_7232_(int p_104737_, int p_104738_) {
        return true;
    }

    public int m_104813_() {
        return this.f_171631_.m_157657_();
    }

    public void m_104630_(int p_104631_, AbstractClientPlayer p_104632_) {
        this.m_104739_(p_104631_, p_104632_);
    }

    public void m_104627_(int p_104628_, Entity p_104629_) {
        this.m_104739_(p_104628_, p_104629_);
    }

    private void m_104739_(int p_104740_, Entity p_104741_) {
        this.m_171642_(p_104740_, Entity.RemovalReason.DISCARDED);
        this.f_171631_.m_157653_(p_104741_);
    }

    public void m_171642_(int p_171643_, Entity.RemovalReason p_171644_) {
        Entity $$2 = this.m_142646_().m_142597_(p_171643_);
        if ($$2 != null) {
            $$2.m_142467_(p_171644_);
            $$2.m_142036_();
        }
    }

    @Override
    @Nullable
    public Entity m_6815_(int p_104609_) {
        return this.m_142646_().m_142597_(p_104609_);
    }

    @Override
    public void m_7462_() {
        this.f_104561_.m_6198_().m_129507_(Component.m_237115_("multiplayer.status.quitting"));
    }

    public void m_104784_(int p_104785_, int p_104786_, int p_104787_) {
        int $$3 = 32;
        RandomSource $$4 = RandomSource.m_216327_();
        Block $$5 = this.m_194187_();
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        for (int $$7 = 0; $$7 < 667; ++$$7) {
            this.m_233612_(p_104785_, p_104786_, p_104787_, 16, $$4, $$5, $$6);
            this.m_233612_(p_104785_, p_104786_, p_104787_, 32, $$4, $$5, $$6);
        }
    }

    @Nullable
    private Block m_194187_() {
        ItemStack $$0;
        Item $$1;
        if (this.f_104565_.f_91072_.m_105295_() == GameType.CREATIVE && f_194124_.contains($$1 = ($$0 = this.f_104565_.f_91074_.m_21205_()).m_41720_()) && $$1 instanceof BlockItem) {
            BlockItem $$2 = (BlockItem)$$1;
            return $$2.m_40614_();
        }
        return null;
    }

    public void m_233612_(int p_233613_, int p_233614_, int p_233615_, int p_233616_, RandomSource p_233617_, @Nullable Block p_233618_, BlockPos.MutableBlockPos p_233619_) {
        int $$7 = p_233613_ + this.f_46441_.m_188503_(p_233616_) - this.f_46441_.m_188503_(p_233616_);
        int $$8 = p_233614_ + this.f_46441_.m_188503_(p_233616_) - this.f_46441_.m_188503_(p_233616_);
        int $$9 = p_233615_ + this.f_46441_.m_188503_(p_233616_) - this.f_46441_.m_188503_(p_233616_);
        p_233619_.m_122178_($$7, $$8, $$9);
        BlockState $$10 = this.m_8055_(p_233619_);
        $$10.m_60734_().m_214162_($$10, this, p_233619_, p_233617_);
        FluidState $$11 = this.m_6425_(p_233619_);
        if (!$$11.m_76178_()) {
            $$11.m_230558_(this, p_233619_, p_233617_);
            ParticleOptions $$12 = $$11.m_76189_();
            if ($$12 != null && this.f_46441_.m_188503_(10) == 0) {
                boolean $$13 = $$10.m_60783_(this, p_233619_, Direction.DOWN);
                Vec3i $$14 = p_233619_.m_7495_();
                this.m_104689_((BlockPos)$$14, this.m_8055_((BlockPos)$$14), $$12, $$13);
            }
        }
        if (p_233618_ == $$10.m_60734_()) {
            this.m_7106_(new BlockParticleOption(ParticleTypes.f_194652_, $$10), (double)$$7 + 0.5, (double)$$8 + 0.5, (double)$$9 + 0.5, 0.0, 0.0, 0.0);
        }
        if (!$$10.m_60838_(this, p_233619_)) {
            this.m_204166_(p_233619_).m_203334_().m_47562_().ifPresent(p_194166_ -> {
                if (p_194166_.m_220527_(this.f_46441_)) {
                    this.m_7106_(p_194166_.m_47419_(), (double)p_233619_.m_123341_() + this.f_46441_.m_188500_(), (double)p_233619_.m_123342_() + this.f_46441_.m_188500_(), (double)p_233619_.m_123343_() + this.f_46441_.m_188500_(), 0.0, 0.0, 0.0);
                }
            });
        }
    }

    private void m_104689_(BlockPos p_104690_, BlockState p_104691_, ParticleOptions p_104692_, boolean p_104693_) {
        if (!p_104691_.m_60819_().m_76178_()) {
            return;
        }
        VoxelShape $$4 = p_104691_.m_60812_(this, p_104690_);
        double $$5 = $$4.m_83297_(Direction.Axis.Y);
        if ($$5 < 1.0) {
            if (p_104693_) {
                this.m_104592_(p_104690_.m_123341_(), p_104690_.m_123341_() + 1, p_104690_.m_123343_(), p_104690_.m_123343_() + 1, (double)(p_104690_.m_123342_() + 1) - 0.05, p_104692_);
            }
        } else if (!p_104691_.m_204336_(BlockTags.f_13049_)) {
            double $$6 = $$4.m_83288_(Direction.Axis.Y);
            if ($$6 > 0.0) {
                this.m_104694_(p_104690_, p_104692_, $$4, (double)p_104690_.m_123342_() + $$6 - 0.05);
            } else {
                BlockPos $$7 = p_104690_.m_7495_();
                BlockState $$8 = this.m_8055_($$7);
                VoxelShape $$9 = $$8.m_60812_(this, $$7);
                double $$10 = $$9.m_83297_(Direction.Axis.Y);
                if ($$10 < 1.0 && $$8.m_60819_().m_76178_()) {
                    this.m_104694_(p_104690_, p_104692_, $$4, (double)p_104690_.m_123342_() - 0.05);
                }
            }
        }
    }

    private void m_104694_(BlockPos p_104695_, ParticleOptions p_104696_, VoxelShape p_104697_, double p_104698_) {
        this.m_104592_((double)p_104695_.m_123341_() + p_104697_.m_83288_(Direction.Axis.X), (double)p_104695_.m_123341_() + p_104697_.m_83297_(Direction.Axis.X), (double)p_104695_.m_123343_() + p_104697_.m_83288_(Direction.Axis.Z), (double)p_104695_.m_123343_() + p_104697_.m_83297_(Direction.Axis.Z), p_104698_, p_104696_);
    }

    private void m_104592_(double p_104593_, double p_104594_, double p_104595_, double p_104596_, double p_104597_, ParticleOptions p_104598_) {
        this.m_7106_(p_104598_, Mth.m_14139_(this.f_46441_.m_188500_(), p_104593_, p_104594_), p_104597_, Mth.m_14139_(this.f_46441_.m_188500_(), p_104595_, p_104596_), 0.0, 0.0, 0.0);
    }

    @Override
    public CrashReportCategory m_6026_(CrashReport p_104729_) {
        CrashReportCategory $$1 = super.m_6026_(p_104729_);
        $$1.m_128165_("Server brand", () -> this.f_104565_.f_91074_.m_108629_());
        $$1.m_128165_("Server type", () -> this.f_104565_.m_91092_() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server");
        return $$1;
    }

    @Override
    public void m_214150_(@Nullable Player p_233621_, double p_233622_, double p_233623_, double p_233624_, SoundEvent p_233625_, SoundSource p_233626_, float p_233627_, float p_233628_, long p_233629_) {
        if (p_233621_ == this.f_104565_.f_91074_) {
            this.m_233602_(p_233622_, p_233623_, p_233624_, p_233625_, p_233626_, p_233627_, p_233628_, false, p_233629_);
        }
    }

    @Override
    public void m_213890_(@Nullable Player p_233631_, Entity p_233632_, SoundEvent p_233633_, SoundSource p_233634_, float p_233635_, float p_233636_, long p_233637_) {
        if (p_233631_ == this.f_104565_.f_91074_) {
            this.f_104565_.m_91106_().m_120367_(new EntityBoundSoundInstance(p_233633_, p_233634_, p_233635_, p_233636_, p_233632_, p_233637_));
        }
    }

    public void m_104677_(BlockPos p_104678_, SoundEvent p_104679_, SoundSource p_104680_, float p_104681_, float p_104682_, boolean p_104683_) {
        this.m_7785_((double)p_104678_.m_123341_() + 0.5, (double)p_104678_.m_123342_() + 0.5, (double)p_104678_.m_123343_() + 0.5, p_104679_, p_104680_, p_104681_, p_104682_, p_104683_);
    }

    @Override
    public void m_7785_(double p_104600_, double p_104601_, double p_104602_, SoundEvent p_104603_, SoundSource p_104604_, float p_104605_, float p_104606_, boolean p_104607_) {
        this.m_233602_(p_104600_, p_104601_, p_104602_, p_104603_, p_104604_, p_104605_, p_104606_, p_104607_, this.f_46441_.m_188505_());
    }

    private void m_233602_(double p_233603_, double p_233604_, double p_233605_, SoundEvent p_233606_, SoundSource p_233607_, float p_233608_, float p_233609_, boolean p_233610_, long p_233611_) {
        double $$9 = this.f_104565_.f_91063_.m_109153_().m_90583_().m_82531_(p_233603_, p_233604_, p_233605_);
        SimpleSoundInstance $$10 = new SimpleSoundInstance(p_233606_, p_233607_, p_233608_, p_233609_, RandomSource.m_216335_(p_233611_), p_233603_, p_233604_, p_233605_);
        if (p_233610_ && $$9 > 100.0) {
            double $$11 = Math.sqrt($$9) / 40.0;
            this.f_104565_.m_91106_().m_120369_($$10, (int)($$11 * 20.0));
        } else {
            this.f_104565_.m_91106_().m_120367_($$10);
        }
    }

    @Override
    public void m_7228_(double p_104585_, double p_104586_, double p_104587_, double p_104588_, double p_104589_, double p_104590_, @Nullable CompoundTag p_104591_) {
        this.f_104565_.f_91061_.m_107344_(new FireworkParticles.Starter(this, p_104585_, p_104586_, p_104587_, p_104588_, p_104589_, p_104590_, this.f_104565_.f_91061_, p_104591_));
    }

    @Override
    public void m_5503_(Packet<?> p_104734_) {
        this.f_104561_.m_104955_(p_104734_);
    }

    @Override
    public RecipeManager m_7465_() {
        return this.f_104561_.m_105141_();
    }

    public void m_104669_(Scoreboard p_104670_) {
        this.f_104555_ = p_104670_;
    }

    @Override
    public LevelTickAccess<Block> m_183326_() {
        return BlackholeTickAccess.m_193145_();
    }

    @Override
    public LevelTickAccess<Fluid> m_183324_() {
        return BlackholeTickAccess.m_193145_();
    }

    @Override
    public ClientChunkCache m_7726_() {
        return this.f_104559_;
    }

    @Override
    @Nullable
    public MapItemSavedData m_7489_(String p_104725_) {
        return this.f_104556_.get(p_104725_);
    }

    @Override
    public void m_142325_(String p_171670_, MapItemSavedData p_171671_) {
        this.f_104556_.put(p_171670_, p_171671_);
    }

    @Override
    public int m_7354_() {
        return 0;
    }

    @Override
    public Scoreboard m_6188_() {
        return this.f_104555_;
    }

    @Override
    public RegistryAccess m_5962_() {
        return this.f_104561_.m_105152_();
    }

    @Override
    public void m_7260_(BlockPos p_104685_, BlockState p_104686_, BlockState p_104687_, int p_104688_) {
        this.f_104562_.m_109544_(this, p_104685_, p_104686_, p_104687_, p_104688_);
    }

    @Override
    public void m_6550_(BlockPos p_104759_, BlockState p_104760_, BlockState p_104761_) {
        this.f_104562_.m_109721_(p_104759_, p_104760_, p_104761_);
    }

    public void m_104793_(int p_104794_, int p_104795_, int p_104796_) {
        this.f_104562_.m_109490_(p_104794_, p_104795_, p_104796_);
    }

    public void m_197405_(int p_197406_, int p_197407_) {
        LevelChunk $$2 = this.f_104559_.m_62227_(p_197406_, p_197407_, false);
        if ($$2 != null) {
            $$2.m_196864_(true);
        }
    }

    @Override
    public void m_6801_(int p_104634_, BlockPos p_104635_, int p_104636_) {
        this.f_104562_.m_109774_(p_104634_, p_104635_, p_104636_);
    }

    @Override
    public void m_6798_(int p_104743_, BlockPos p_104744_, int p_104745_) {
        this.f_104562_.m_109506_(p_104743_, p_104744_, p_104745_);
    }

    @Override
    public void m_5898_(@Nullable Player p_104654_, int p_104655_, BlockPos p_104656_, int p_104657_) {
        try {
            this.f_104562_.m_234304_(p_104655_, p_104656_, p_104657_);
        }
        catch (Throwable $$4) {
            CrashReport $$5 = CrashReport.m_127521_($$4, "Playing level event");
            CrashReportCategory $$6 = $$5.m_127514_("Level event being played");
            $$6.m_128159_("Block coordinates", CrashReportCategory.m_178947_(this, p_104656_));
            $$6.m_128159_("Event source", p_104654_);
            $$6.m_128159_("Event type", p_104655_);
            $$6.m_128159_("Event data", p_104657_);
            throw new ReportedException($$5);
        }
    }

    @Override
    public void m_7106_(ParticleOptions p_104706_, double p_104707_, double p_104708_, double p_104709_, double p_104710_, double p_104711_, double p_104712_) {
        this.f_104562_.m_109743_(p_104706_, p_104706_.m_6012_().m_123742_(), p_104707_, p_104708_, p_104709_, p_104710_, p_104711_, p_104712_);
    }

    @Override
    public void m_6493_(ParticleOptions p_104714_, boolean p_104715_, double p_104716_, double p_104717_, double p_104718_, double p_104719_, double p_104720_, double p_104721_) {
        this.f_104562_.m_109743_(p_104714_, p_104714_.m_6012_().m_123742_() || p_104715_, p_104716_, p_104717_, p_104718_, p_104719_, p_104720_, p_104721_);
    }

    @Override
    public void m_7107_(ParticleOptions p_104766_, double p_104767_, double p_104768_, double p_104769_, double p_104770_, double p_104771_, double p_104772_) {
        this.f_104562_.m_109752_(p_104766_, false, true, p_104767_, p_104768_, p_104769_, p_104770_, p_104771_, p_104772_);
    }

    @Override
    public void m_6485_(ParticleOptions p_104774_, boolean p_104775_, double p_104776_, double p_104777_, double p_104778_, double p_104779_, double p_104780_, double p_104781_) {
        this.f_104562_.m_109752_(p_104774_, p_104774_.m_6012_().m_123742_() || p_104775_, true, p_104776_, p_104777_, p_104778_, p_104779_, p_104780_, p_104781_);
    }

    public List<AbstractClientPlayer> m_6907_() {
        return this.f_104566_;
    }

    @Override
    public Holder<Biome> m_203675_(int p_205516_, int p_205517_, int p_205518_) {
        return this.m_5962_().m_175515_(Registry.f_122885_).m_206081_(Biomes.f_48202_);
    }

    public float m_104805_(float p_104806_) {
        float $$1 = this.m_46942_(p_104806_);
        float $$2 = 1.0f - (Mth.m_14089_($$1 * ((float)Math.PI * 2)) * 2.0f + 0.2f);
        $$2 = Mth.m_14036_($$2, 0.0f, 1.0f);
        $$2 = 1.0f - $$2;
        $$2 *= 1.0f - this.m_46722_(p_104806_) * 5.0f / 16.0f;
        return ($$2 *= 1.0f - this.m_46661_(p_104806_) * 5.0f / 16.0f) * 0.8f + 0.2f;
    }

    public Vec3 m_171660_(Vec3 p_171661_, float p_171662_) {
        float $$13;
        float $$2 = this.m_46942_(p_171662_);
        Vec3 $$3 = p_171661_.m_82492_(2.0, 2.0, 2.0).m_82490_(0.25);
        BiomeManager $$4 = this.m_7062_();
        Vec3 $$5 = CubicSampler.m_130038_($$3, (p_194161_, p_194162_, p_194163_) -> Vec3.m_82501_($$4.m_204210_(p_194161_, p_194162_, p_194163_).m_203334_().m_47463_()));
        float $$6 = Mth.m_14089_($$2 * ((float)Math.PI * 2)) * 2.0f + 0.5f;
        $$6 = Mth.m_14036_($$6, 0.0f, 1.0f);
        float $$7 = (float)$$5.f_82479_ * $$6;
        float $$8 = (float)$$5.f_82480_ * $$6;
        float $$9 = (float)$$5.f_82481_ * $$6;
        float $$10 = this.m_46722_(p_171662_);
        if ($$10 > 0.0f) {
            float $$11 = ($$7 * 0.3f + $$8 * 0.59f + $$9 * 0.11f) * 0.6f;
            float $$12 = 1.0f - $$10 * 0.75f;
            $$7 = $$7 * $$12 + $$11 * (1.0f - $$12);
            $$8 = $$8 * $$12 + $$11 * (1.0f - $$12);
            $$9 = $$9 * $$12 + $$11 * (1.0f - $$12);
        }
        if (($$13 = this.m_46661_(p_171662_)) > 0.0f) {
            float $$14 = ($$7 * 0.3f + $$8 * 0.59f + $$9 * 0.11f) * 0.2f;
            float $$15 = 1.0f - $$13 * 0.75f;
            $$7 = $$7 * $$15 + $$14 * (1.0f - $$15);
            $$8 = $$8 * $$15 + $$14 * (1.0f - $$15);
            $$9 = $$9 * $$15 + $$14 * (1.0f - $$15);
        }
        if (!this.f_104565_.f_91066_.m_231935_().m_231551_().booleanValue() && this.f_104557_ > 0) {
            float $$16 = (float)this.f_104557_ - p_171662_;
            if ($$16 > 1.0f) {
                $$16 = 1.0f;
            }
            $$7 = $$7 * (1.0f - ($$16 *= 0.45f)) + 0.8f * $$16;
            $$8 = $$8 * (1.0f - $$16) + 0.8f * $$16;
            $$9 = $$9 * (1.0f - $$16) + 1.0f * $$16;
        }
        return new Vec3($$7, $$8, $$9);
    }

    public Vec3 m_104808_(float p_104809_) {
        float $$1 = this.m_46942_(p_104809_);
        float $$2 = Mth.m_14089_($$1 * ((float)Math.PI * 2)) * 2.0f + 0.5f;
        $$2 = Mth.m_14036_($$2, 0.0f, 1.0f);
        float $$3 = 1.0f;
        float $$4 = 1.0f;
        float $$5 = 1.0f;
        float $$6 = this.m_46722_(p_104809_);
        if ($$6 > 0.0f) {
            float $$7 = ($$3 * 0.3f + $$4 * 0.59f + $$5 * 0.11f) * 0.6f;
            float $$8 = 1.0f - $$6 * 0.95f;
            $$3 = $$3 * $$8 + $$7 * (1.0f - $$8);
            $$4 = $$4 * $$8 + $$7 * (1.0f - $$8);
            $$5 = $$5 * $$8 + $$7 * (1.0f - $$8);
        }
        $$3 *= $$2 * 0.9f + 0.1f;
        $$4 *= $$2 * 0.9f + 0.1f;
        $$5 *= $$2 * 0.85f + 0.15f;
        float $$9 = this.m_46661_(p_104809_);
        if ($$9 > 0.0f) {
            float $$10 = ($$3 * 0.3f + $$4 * 0.59f + $$5 * 0.11f) * 0.2f;
            float $$11 = 1.0f - $$9 * 0.95f;
            $$3 = $$3 * $$11 + $$10 * (1.0f - $$11);
            $$4 = $$4 * $$11 + $$10 * (1.0f - $$11);
            $$5 = $$5 * $$11 + $$10 * (1.0f - $$11);
        }
        return new Vec3($$3, $$4, $$5);
    }

    public float m_104811_(float p_104812_) {
        float $$1 = this.m_46942_(p_104812_);
        float $$2 = 1.0f - (Mth.m_14089_($$1 * ((float)Math.PI * 2)) * 2.0f + 0.25f);
        $$2 = Mth.m_14036_($$2, 0.0f, 1.0f);
        return $$2 * $$2 * 0.5f;
    }

    public int m_104819_() {
        return this.f_104557_;
    }

    @Override
    public void m_6580_(int p_104783_) {
        this.f_104557_ = p_104783_;
    }

    @Override
    public float m_7717_(Direction p_104703_, boolean p_104704_) {
        boolean $$2 = this.m_104583_().m_108885_();
        if (!p_104704_) {
            return $$2 ? 0.9f : 1.0f;
        }
        switch (p_104703_) {
            case DOWN: {
                return $$2 ? 0.9f : 0.5f;
            }
            case UP: {
                return $$2 ? 0.9f : 1.0f;
            }
            case NORTH: 
            case SOUTH: {
                return 0.8f;
            }
            case WEST: 
            case EAST: {
                return 0.6f;
            }
        }
        return 1.0f;
    }

    @Override
    public int m_6171_(BlockPos p_104700_, ColorResolver p_104701_) {
        BlockTintCache $$2 = (BlockTintCache)this.f_104558_.get((Object)p_104701_);
        return $$2.m_193812_(p_104700_);
    }

    public int m_104762_(BlockPos p_104763_, ColorResolver p_104764_) {
        int $$2 = Minecraft.m_91087_().f_91066_.m_232121_().m_231551_();
        if ($$2 == 0) {
            return p_104764_.m_130045_(this.m_204166_(p_104763_).m_203334_(), p_104763_.m_123341_(), p_104763_.m_123343_());
        }
        int $$3 = ($$2 * 2 + 1) * ($$2 * 2 + 1);
        int $$4 = 0;
        int $$5 = 0;
        int $$6 = 0;
        Cursor3D $$7 = new Cursor3D(p_104763_.m_123341_() - $$2, p_104763_.m_123342_(), p_104763_.m_123343_() - $$2, p_104763_.m_123341_() + $$2, p_104763_.m_123342_(), p_104763_.m_123343_() + $$2);
        BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
        while ($$7.m_122304_()) {
            $$8.m_122178_($$7.m_122305_(), $$7.m_122306_(), $$7.m_122307_());
            int $$9 = p_104764_.m_130045_(this.m_204166_($$8).m_203334_(), $$8.m_123341_(), $$8.m_123343_());
            $$4 += ($$9 & 0xFF0000) >> 16;
            $$5 += ($$9 & 0xFF00) >> 8;
            $$6 += $$9 & 0xFF;
        }
        return ($$4 / $$3 & 0xFF) << 16 | ($$5 / $$3 & 0xFF) << 8 | $$6 / $$3 & 0xFF;
    }

    public void m_104752_(BlockPos p_104753_, float p_104754_) {
        this.f_46442_.m_7250_(p_104753_, p_104754_);
    }

    public String toString() {
        return "ClientLevel";
    }

    @Override
    public ClientLevelData m_6106_() {
        return this.f_104563_;
    }

    @Override
    public void m_214171_(GameEvent p_233639_, Vec3 p_233640_, GameEvent.Context p_233641_) {
    }

    protected Map<String, MapItemSavedData> m_171684_() {
        return ImmutableMap.copyOf(this.f_104556_);
    }

    protected void m_171672_(Map<String, MapItemSavedData> p_171673_) {
        this.f_104556_.putAll(p_171673_);
    }

    @Override
    protected LevelEntityGetter<Entity> m_142646_() {
        return this.f_171631_.m_157645_();
    }

    @Override
    public String m_46464_() {
        return "Chunks[C] W: " + this.f_104559_.m_6754_() + " E: " + this.f_171631_.m_157664_();
    }

    @Override
    public void m_142052_(BlockPos p_171667_, BlockState p_171668_) {
        this.f_104565_.f_91061_.m_107355_(p_171667_, p_171668_);
    }

    public void m_194174_(int p_194175_) {
        this.f_194123_ = p_194175_;
    }

    public int m_194186_() {
        return this.f_194123_;
    }

    @Override
    public /* synthetic */ LevelData m_6106_() {
        return this.m_6106_();
    }

    @Override
    public /* synthetic */ ChunkSource m_7726_() {
        return this.m_7726_();
    }

    final class EntityCallbacks
    implements LevelCallback<Entity> {
        EntityCallbacks() {
        }

        @Override
        public void m_141989_(Entity p_171696_) {
        }

        @Override
        public void m_141986_(Entity p_171700_) {
        }

        @Override
        public void m_141987_(Entity p_171704_) {
            ClientLevel.this.f_171630_.m_156908_(p_171704_);
        }

        @Override
        public void m_141983_(Entity p_171708_) {
            ClientLevel.this.f_171630_.m_156912_(p_171708_);
        }

        @Override
        public void m_141985_(Entity p_171712_) {
            if (p_171712_ instanceof AbstractClientPlayer) {
                ClientLevel.this.f_104566_.add((AbstractClientPlayer)p_171712_);
            }
        }

        @Override
        public void m_141981_(Entity p_171716_) {
            p_171716_.m_19877_();
            ClientLevel.this.f_104566_.remove(p_171716_);
        }

        @Override
        public void m_214006_(Entity p_233660_) {
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

    public static class ClientLevelData
    implements WritableLevelData {
        private final boolean f_104830_;
        private final GameRules f_104831_;
        private final boolean f_104832_;
        private int f_104833_;
        private int f_104834_;
        private int f_104835_;
        private float f_104836_;
        private long f_104837_;
        private long f_104838_;
        private boolean f_104839_;
        private Difficulty f_104840_;
        private boolean f_104841_;

        public ClientLevelData(Difficulty p_104843_, boolean p_104844_, boolean p_104845_) {
            this.f_104840_ = p_104843_;
            this.f_104830_ = p_104844_;
            this.f_104832_ = p_104845_;
            this.f_104831_ = new GameRules();
        }

        @Override
        public int m_6789_() {
            return this.f_104833_;
        }

        @Override
        public int m_6527_() {
            return this.f_104834_;
        }

        @Override
        public int m_6526_() {
            return this.f_104835_;
        }

        @Override
        public float m_6790_() {
            return this.f_104836_;
        }

        @Override
        public long m_6793_() {
            return this.f_104837_;
        }

        @Override
        public long m_6792_() {
            return this.f_104838_;
        }

        @Override
        public void m_6395_(int p_104862_) {
            this.f_104833_ = p_104862_;
        }

        @Override
        public void m_6397_(int p_104869_) {
            this.f_104834_ = p_104869_;
        }

        @Override
        public void m_6400_(int p_104872_) {
            this.f_104835_ = p_104872_;
        }

        @Override
        public void m_7113_(float p_104848_) {
            this.f_104836_ = p_104848_;
        }

        public void m_104849_(long p_104850_) {
            this.f_104837_ = p_104850_;
        }

        public void m_104863_(long p_104864_) {
            this.f_104838_ = p_104864_;
        }

        @Override
        public void m_7250_(BlockPos p_104854_, float p_104855_) {
            this.f_104833_ = p_104854_.m_123341_();
            this.f_104834_ = p_104854_.m_123342_();
            this.f_104835_ = p_104854_.m_123343_();
            this.f_104836_ = p_104855_;
        }

        @Override
        public boolean m_6534_() {
            return false;
        }

        @Override
        public boolean m_6533_() {
            return this.f_104839_;
        }

        @Override
        public void m_5565_(boolean p_104866_) {
            this.f_104839_ = p_104866_;
        }

        @Override
        public boolean m_5466_() {
            return this.f_104830_;
        }

        @Override
        public GameRules m_5470_() {
            return this.f_104831_;
        }

        @Override
        public Difficulty m_5472_() {
            return this.f_104840_;
        }

        @Override
        public boolean m_5474_() {
            return this.f_104841_;
        }

        @Override
        public void m_142471_(CrashReportCategory p_171690_, LevelHeightAccessor p_171691_) {
            WritableLevelData.super.m_142471_(p_171690_, p_171691_);
        }

        public void m_104851_(Difficulty p_104852_) {
            this.f_104840_ = p_104852_;
        }

        public void m_104858_(boolean p_104859_) {
            this.f_104841_ = p_104859_;
        }

        public double m_171687_(LevelHeightAccessor p_171688_) {
            if (this.f_104832_) {
                return p_171688_.m_141937_();
            }
            return 63.0;
        }

        public float m_205519_() {
            if (this.f_104832_) {
                return 1.0f;
            }
            return 0.03125f;
        }
    }
}

