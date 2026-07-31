/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import net.minecraft.world.level.redstone.NeighborUpdater;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Scoreboard;

public abstract class Level
implements LevelAccessor,
AutoCloseable {
    public static final Codec<ResourceKey<Level>> f_46427_ = ResourceKey.m_195966_(Registry.f_122819_);
    public static final ResourceKey<Level> f_46428_ = ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation("overworld"));
    public static final ResourceKey<Level> f_46429_ = ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation("the_nether"));
    public static final ResourceKey<Level> f_46430_ = ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation("the_end"));
    public static final int f_151505_ = 30000000;
    public static final int f_151506_ = 512;
    public static final int f_151507_ = 32;
    private static final Direction[] f_46422_ = Direction.values();
    public static final int f_151508_ = 15;
    public static final int f_151509_ = 24000;
    public static final int f_151510_ = 20000000;
    public static final int f_151511_ = -20000000;
    protected final List<TickingBlockEntity> f_151512_ = Lists.newArrayList();
    protected final NeighborUpdater f_220350_;
    private final List<TickingBlockEntity> f_151503_ = Lists.newArrayList();
    private boolean f_151504_;
    private final Thread f_46423_;
    private final boolean f_46424_;
    private int f_46425_;
    protected int f_46435_ = RandomSource.m_216327_().m_188502_();
    protected final int f_46436_ = 1013904223;
    protected float f_46437_;
    protected float f_46438_;
    protected float f_46439_;
    protected float f_46440_;
    public final RandomSource f_46441_ = RandomSource.m_216327_();
    @Deprecated
    private final RandomSource f_220348_ = RandomSource.m_216337_();
    private final ResourceKey<DimensionType> f_220349_;
    private final Holder<DimensionType> f_204147_;
    protected final WritableLevelData f_46442_;
    private final Supplier<ProfilerFiller> f_46446_;
    public final boolean f_46443_;
    private final WorldBorder f_46447_;
    private final BiomeManager f_46420_;
    private final ResourceKey<Level> f_46421_;
    private long f_186455_;

    protected Level(WritableLevelData p_220352_, ResourceKey<Level> p_220353_, Holder<DimensionType> p_220354_, Supplier<ProfilerFiller> p_220355_, boolean p_220356_, boolean p_220357_, long p_220358_, int p_220359_) {
        this.f_46446_ = p_220355_;
        this.f_46442_ = p_220352_;
        this.f_204147_ = p_220354_;
        this.f_220349_ = p_220354_.m_203543_().orElseThrow(() -> new IllegalArgumentException("Dimension must be registered, got " + p_220354_));
        final DimensionType $$8 = p_220354_.m_203334_();
        this.f_46421_ = p_220353_;
        this.f_46443_ = p_220356_;
        this.f_46447_ = $$8.f_63859_() != 1.0 ? new WorldBorder(){

            @Override
            public double m_6347_() {
                return super.m_6347_() / $$8.f_63859_();
            }

            @Override
            public double m_6345_() {
                return super.m_6345_() / $$8.f_63859_();
            }
        } : new WorldBorder();
        this.f_46423_ = Thread.currentThread();
        this.f_46420_ = new BiomeManager(this, p_220358_);
        this.f_46424_ = p_220357_;
        this.f_220350_ = new CollectingNeighborUpdater(this, p_220359_);
    }

    @Override
    public boolean m_5776_() {
        return this.f_46443_;
    }

    @Override
    @Nullable
    public MinecraftServer m_7654_() {
        return null;
    }

    public boolean m_46739_(BlockPos p_46740_) {
        return !this.m_151570_(p_46740_) && Level.m_46457_(p_46740_);
    }

    public static boolean m_46741_(BlockPos p_46742_) {
        return !Level.m_46724_(p_46742_.m_123342_()) && Level.m_46457_(p_46742_);
    }

    private static boolean m_46457_(BlockPos p_46458_) {
        return p_46458_.m_123341_() >= -30000000 && p_46458_.m_123343_() >= -30000000 && p_46458_.m_123341_() < 30000000 && p_46458_.m_123343_() < 30000000;
    }

    private static boolean m_46724_(int p_46725_) {
        return p_46725_ < -20000000 || p_46725_ >= 20000000;
    }

    public LevelChunk m_46745_(BlockPos p_46746_) {
        return this.m_6325_(SectionPos.m_123171_(p_46746_.m_123341_()), SectionPos.m_123171_(p_46746_.m_123343_()));
    }

    @Override
    public LevelChunk m_6325_(int p_46727_, int p_46728_) {
        return (LevelChunk)this.m_46819_(p_46727_, p_46728_, ChunkStatus.f_62326_);
    }

    @Override
    @Nullable
    public ChunkAccess m_6522_(int p_46502_, int p_46503_, ChunkStatus p_46504_, boolean p_46505_) {
        ChunkAccess $$4 = this.m_7726_().m_7587_(p_46502_, p_46503_, p_46504_, p_46505_);
        if ($$4 == null && p_46505_) {
            throw new IllegalStateException("Should always be able to create a chunk!");
        }
        return $$4;
    }

    @Override
    public boolean m_7731_(BlockPos p_46601_, BlockState p_46602_, int p_46603_) {
        return this.m_6933_(p_46601_, p_46602_, p_46603_, 512);
    }

    @Override
    public boolean m_6933_(BlockPos p_46605_, BlockState p_46606_, int p_46607_, int p_46608_) {
        if (this.m_151570_(p_46605_)) {
            return false;
        }
        if (!this.f_46443_ && this.m_46659_()) {
            return false;
        }
        LevelChunk $$4 = this.m_46745_(p_46605_);
        Block $$5 = p_46606_.m_60734_();
        BlockState $$6 = $$4.m_6978_(p_46605_, p_46606_, (p_46607_ & 0x40) != 0);
        if ($$6 != null) {
            BlockState $$7 = this.m_8055_(p_46605_);
            if ((p_46607_ & 0x80) == 0 && $$7 != $$6 && ($$7.m_60739_(this, p_46605_) != $$6.m_60739_(this, p_46605_) || $$7.m_60791_() != $$6.m_60791_() || $$7.m_60787_() || $$6.m_60787_())) {
                this.m_46473_().m_6180_("queueCheckLight");
                this.m_7726_().m_7827_().m_7174_(p_46605_);
                this.m_46473_().m_7238_();
            }
            if ($$7 == p_46606_) {
                if ($$6 != $$7) {
                    this.m_6550_(p_46605_, $$6, $$7);
                }
                if ((p_46607_ & 2) != 0 && (!this.f_46443_ || (p_46607_ & 4) == 0) && (this.f_46443_ || $$4.m_6708_() != null && $$4.m_6708_().m_140114_(ChunkHolder.FullChunkStatus.TICKING))) {
                    this.m_7260_(p_46605_, $$6, p_46606_, p_46607_);
                }
                if ((p_46607_ & 1) != 0) {
                    this.m_6289_(p_46605_, $$6.m_60734_());
                    if (!this.f_46443_ && p_46606_.m_60807_()) {
                        this.m_46717_(p_46605_, $$5);
                    }
                }
                if ((p_46607_ & 0x10) == 0 && p_46608_ > 0) {
                    int $$8 = p_46607_ & 0xFFFFFFDE;
                    $$6.m_60762_(this, p_46605_, $$8, p_46608_ - 1);
                    p_46606_.m_60705_(this, p_46605_, $$8, p_46608_ - 1);
                    p_46606_.m_60762_(this, p_46605_, $$8, p_46608_ - 1);
                }
                this.m_6559_(p_46605_, $$6, $$7);
            }
            return true;
        }
        return false;
    }

    public void m_6559_(BlockPos p_46609_, BlockState p_46610_, BlockState p_46611_) {
    }

    @Override
    public boolean m_7471_(BlockPos p_46623_, boolean p_46624_) {
        FluidState $$2 = this.m_6425_(p_46623_);
        return this.m_7731_(p_46623_, $$2.m_76188_(), 3 | (p_46624_ ? 64 : 0));
    }

    @Override
    public boolean m_7740_(BlockPos p_46626_, boolean p_46627_, @Nullable Entity p_46628_, int p_46629_) {
        boolean $$7;
        BlockState $$4 = this.m_8055_(p_46626_);
        if ($$4.m_60795_()) {
            return false;
        }
        FluidState $$5 = this.m_6425_(p_46626_);
        if (!($$4.m_60734_() instanceof BaseFireBlock)) {
            this.m_46796_(2001, p_46626_, Block.m_49956_($$4));
        }
        if (p_46627_) {
            BlockEntity $$6 = $$4.m_155947_() ? this.m_7702_(p_46626_) : null;
            Block.m_49881_($$4, this, p_46626_, $$6, p_46628_, ItemStack.f_41583_);
        }
        if ($$7 = this.m_6933_(p_46626_, $$5.m_76188_(), 3, p_46629_)) {
            this.m_220407_(GameEvent.f_157794_, p_46626_, GameEvent.Context.m_223719_(p_46628_, $$4));
        }
        return $$7;
    }

    public void m_142052_(BlockPos p_151531_, BlockState p_151532_) {
    }

    public boolean m_46597_(BlockPos p_46598_, BlockState p_46599_) {
        return this.m_7731_(p_46598_, p_46599_, 3);
    }

    public abstract void m_7260_(BlockPos var1, BlockState var2, BlockState var3, int var4);

    public void m_6550_(BlockPos p_46678_, BlockState p_46679_, BlockState p_46680_) {
    }

    public void m_46672_(BlockPos p_46673_, Block p_46674_) {
    }

    public void m_46590_(BlockPos p_46591_, Block p_46592_, Direction p_46593_) {
    }

    public void m_46586_(BlockPos p_46587_, Block p_46588_, BlockPos p_46589_) {
    }

    public void m_213960_(BlockState p_220379_, BlockPos p_220380_, Block p_220381_, BlockPos p_220382_, boolean p_220383_) {
    }

    @Override
    public void m_213683_(Direction p_220385_, BlockState p_220386_, BlockPos p_220387_, BlockPos p_220388_, int p_220389_, int p_220390_) {
        this.f_220350_.m_213547_(p_220385_, p_220386_, p_220387_, p_220388_, p_220389_, p_220390_);
    }

    @Override
    public int m_6924_(Heightmap.Types p_46571_, int p_46572_, int p_46573_) {
        int $$5;
        if (p_46572_ < -30000000 || p_46573_ < -30000000 || p_46572_ >= 30000000 || p_46573_ >= 30000000) {
            int $$3 = this.m_5736_() + 1;
        } else if (this.m_7232_(SectionPos.m_123171_(p_46572_), SectionPos.m_123171_(p_46573_))) {
            int $$4 = this.m_6325_(SectionPos.m_123171_(p_46572_), SectionPos.m_123171_(p_46573_)).m_5885_(p_46571_, p_46572_ & 0xF, p_46573_ & 0xF) + 1;
        } else {
            $$5 = this.m_141937_();
        }
        return $$5;
    }

    @Override
    public LevelLightEngine m_5518_() {
        return this.m_7726_().m_7827_();
    }

    @Override
    public BlockState m_8055_(BlockPos p_46732_) {
        if (this.m_151570_(p_46732_)) {
            return Blocks.f_50626_.m_49966_();
        }
        LevelChunk $$1 = this.m_6325_(SectionPos.m_123171_(p_46732_.m_123341_()), SectionPos.m_123171_(p_46732_.m_123343_()));
        return $$1.m_8055_(p_46732_);
    }

    @Override
    public FluidState m_6425_(BlockPos p_46671_) {
        if (this.m_151570_(p_46671_)) {
            return Fluids.f_76191_.m_76145_();
        }
        LevelChunk $$1 = this.m_46745_(p_46671_);
        return $$1.m_6425_(p_46671_);
    }

    public boolean m_46461_() {
        return !this.m_6042_().m_63967_() && this.f_46425_ < 4;
    }

    public boolean m_46462_() {
        return !this.m_6042_().m_63967_() && !this.m_46461_();
    }

    @Override
    public void m_5594_(@Nullable Player p_46560_, BlockPos p_46561_, SoundEvent p_46562_, SoundSource p_46563_, float p_46564_, float p_46565_) {
        this.m_6263_(p_46560_, (double)p_46561_.m_123341_() + 0.5, (double)p_46561_.m_123342_() + 0.5, (double)p_46561_.m_123343_() + 0.5, p_46562_, p_46563_, p_46564_, p_46565_);
    }

    public abstract void m_214150_(@Nullable Player var1, double var2, double var4, double var6, SoundEvent var8, SoundSource var9, float var10, float var11, long var12);

    public abstract void m_213890_(@Nullable Player var1, Entity var2, SoundEvent var3, SoundSource var4, float var5, float var6, long var7);

    public void m_6263_(@Nullable Player p_46543_, double p_46544_, double p_46545_, double p_46546_, SoundEvent p_46547_, SoundSource p_46548_, float p_46549_, float p_46550_) {
        this.m_214150_(p_46543_, p_46544_, p_46545_, p_46546_, p_46547_, p_46548_, p_46549_, p_46550_, this.f_220348_.m_188505_());
    }

    public void m_6269_(@Nullable Player p_46551_, Entity p_46552_, SoundEvent p_46553_, SoundSource p_46554_, float p_46555_, float p_46556_) {
        this.m_213890_(p_46551_, p_46552_, p_46553_, p_46554_, p_46555_, p_46556_, this.f_220348_.m_188505_());
    }

    public void m_7785_(double p_46482_, double p_46483_, double p_46484_, SoundEvent p_46485_, SoundSource p_46486_, float p_46487_, float p_46488_, boolean p_46489_) {
    }

    @Override
    public void m_7106_(ParticleOptions p_46631_, double p_46632_, double p_46633_, double p_46634_, double p_46635_, double p_46636_, double p_46637_) {
    }

    public void m_6493_(ParticleOptions p_46638_, boolean p_46639_, double p_46640_, double p_46641_, double p_46642_, double p_46643_, double p_46644_, double p_46645_) {
    }

    public void m_7107_(ParticleOptions p_46684_, double p_46685_, double p_46686_, double p_46687_, double p_46688_, double p_46689_, double p_46690_) {
    }

    public void m_6485_(ParticleOptions p_46691_, boolean p_46692_, double p_46693_, double p_46694_, double p_46695_, double p_46696_, double p_46697_, double p_46698_) {
    }

    public float m_46490_(float p_46491_) {
        float $$1 = this.m_46942_(p_46491_);
        return $$1 * ((float)Math.PI * 2);
    }

    public void m_151525_(TickingBlockEntity p_151526_) {
        (this.f_151504_ ? this.f_151503_ : this.f_151512_).add(p_151526_);
    }

    protected void m_46463_() {
        ProfilerFiller $$0 = this.m_46473_();
        $$0.m_6180_("blockEntities");
        this.f_151504_ = true;
        if (!this.f_151503_.isEmpty()) {
            this.f_151512_.addAll(this.f_151503_);
            this.f_151503_.clear();
        }
        Iterator<TickingBlockEntity> $$1 = this.f_151512_.iterator();
        while ($$1.hasNext()) {
            TickingBlockEntity $$2 = $$1.next();
            if ($$2.m_142220_()) {
                $$1.remove();
                continue;
            }
            if (!this.m_220393_($$2.m_142689_())) continue;
            $$2.m_142224_();
        }
        this.f_151504_ = false;
        $$0.m_7238_();
    }

    public <T extends Entity> void m_46653_(Consumer<T> p_46654_, T p_46655_) {
        try {
            p_46654_.accept(p_46655_);
        }
        catch (Throwable $$2) {
            CrashReport $$3 = CrashReport.m_127521_($$2, "Ticking entity");
            CrashReportCategory $$4 = $$3.m_127514_("Entity being ticked");
            p_46655_.m_7976_($$4);
            throw new ReportedException($$3);
        }
    }

    public boolean m_183599_(Entity p_186458_) {
        return true;
    }

    public boolean m_183438_(long p_186456_) {
        return true;
    }

    public boolean m_220393_(BlockPos p_220394_) {
        return this.m_183438_(ChunkPos.m_151388_(p_220394_));
    }

    public Explosion m_46511_(@Nullable Entity p_46512_, double p_46513_, double p_46514_, double p_46515_, float p_46516_, Explosion.BlockInteraction p_46517_) {
        return this.m_7703_(p_46512_, null, null, p_46513_, p_46514_, p_46515_, p_46516_, false, p_46517_);
    }

    public Explosion m_46518_(@Nullable Entity p_46519_, double p_46520_, double p_46521_, double p_46522_, float p_46523_, boolean p_46524_, Explosion.BlockInteraction p_46525_) {
        return this.m_7703_(p_46519_, null, null, p_46520_, p_46521_, p_46522_, p_46523_, p_46524_, p_46525_);
    }

    public Explosion m_7703_(@Nullable Entity p_46526_, @Nullable DamageSource p_46527_, @Nullable ExplosionDamageCalculator p_46528_, double p_46529_, double p_46530_, double p_46531_, float p_46532_, boolean p_46533_, Explosion.BlockInteraction p_46534_) {
        Explosion $$9 = new Explosion(this, p_46526_, p_46527_, p_46528_, p_46529_, p_46530_, p_46531_, p_46532_, p_46533_, p_46534_);
        $$9.m_46061_();
        $$9.m_46075_(true);
        return $$9;
    }

    public abstract String m_46464_();

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_46716_) {
        if (this.m_151570_(p_46716_)) {
            return null;
        }
        if (!this.f_46443_ && Thread.currentThread() != this.f_46423_) {
            return null;
        }
        return this.m_46745_(p_46716_).m_5685_(p_46716_, LevelChunk.EntityCreationType.IMMEDIATE);
    }

    public void m_151523_(BlockEntity p_151524_) {
        BlockPos $$1 = p_151524_.m_58899_();
        if (this.m_151570_($$1)) {
            return;
        }
        this.m_46745_($$1).m_142170_(p_151524_);
    }

    public void m_46747_(BlockPos p_46748_) {
        if (this.m_151570_(p_46748_)) {
            return;
        }
        this.m_46745_(p_46748_).m_8114_(p_46748_);
    }

    public boolean m_46749_(BlockPos p_46750_) {
        if (this.m_151570_(p_46750_)) {
            return false;
        }
        return this.m_7726_().m_5563_(SectionPos.m_123171_(p_46750_.m_123341_()), SectionPos.m_123171_(p_46750_.m_123343_()));
    }

    public boolean m_46578_(BlockPos p_46579_, Entity p_46580_, Direction p_46581_) {
        if (this.m_151570_(p_46579_)) {
            return false;
        }
        ChunkAccess $$3 = this.m_6522_(SectionPos.m_123171_(p_46579_.m_123341_()), SectionPos.m_123171_(p_46579_.m_123343_()), ChunkStatus.f_62326_, false);
        if ($$3 == null) {
            return false;
        }
        return $$3.m_8055_(p_46579_).m_60638_(this, p_46579_, p_46580_, p_46581_);
    }

    public boolean m_46575_(BlockPos p_46576_, Entity p_46577_) {
        return this.m_46578_(p_46576_, p_46577_, Direction.UP);
    }

    public void m_46465_() {
        double $$0 = 1.0 - (double)(this.m_46722_(1.0f) * 5.0f) / 16.0;
        double $$1 = 1.0 - (double)(this.m_46661_(1.0f) * 5.0f) / 16.0;
        double $$2 = 0.5 + 2.0 * Mth.m_14008_(Mth.m_14089_(this.m_46942_(1.0f) * ((float)Math.PI * 2)), -0.25, 0.25);
        this.f_46425_ = (int)((1.0 - $$2 * $$0 * $$1) * 11.0);
    }

    public void m_46703_(boolean p_46704_, boolean p_46705_) {
        this.m_7726_().m_6707_(p_46704_, p_46705_);
    }

    public BlockPos m_220360_() {
        BlockPos $$0 = new BlockPos(this.f_46442_.m_6789_(), this.f_46442_.m_6527_(), this.f_46442_.m_6526_());
        if (!this.m_6857_().m_61937_($$0)) {
            $$0 = this.m_5452_(Heightmap.Types.MOTION_BLOCKING, new BlockPos(this.m_6857_().m_6347_(), 0.0, this.m_6857_().m_6345_()));
        }
        return $$0;
    }

    public float m_220361_() {
        return this.f_46442_.m_6790_();
    }

    protected void m_46466_() {
        if (this.f_46442_.m_6533_()) {
            this.f_46438_ = 1.0f;
            if (this.f_46442_.m_6534_()) {
                this.f_46440_ = 1.0f;
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.m_7726_().close();
    }

    @Override
    @Nullable
    public BlockGetter m_7925_(int p_46711_, int p_46712_) {
        return this.m_6522_(p_46711_, p_46712_, ChunkStatus.f_62326_, false);
    }

    @Override
    public List<Entity> m_6249_(@Nullable Entity p_46536_, AABB p_46537_, Predicate<? super Entity> p_46538_) {
        this.m_46473_().m_6174_("getEntities");
        ArrayList $$3 = Lists.newArrayList();
        this.m_142646_().m_142232_(p_46537_, p_151522_ -> {
            if (p_151522_ != p_46536_ && p_46538_.test((Entity)p_151522_)) {
                $$3.add(p_151522_);
            }
            if (p_151522_ instanceof EnderDragon) {
                for (EnderDragonPart $$4 : ((EnderDragon)p_151522_).m_31156_()) {
                    if (p_151522_ == p_46536_ || !p_46538_.test($$4)) continue;
                    $$3.add($$4);
                }
            }
        });
        return $$3;
    }

    @Override
    public <T extends Entity> List<T> m_142425_(EntityTypeTest<Entity, T> p_151528_, AABB p_151529_, Predicate<? super T> p_151530_) {
        this.m_46473_().m_6174_("getEntities");
        ArrayList $$3 = Lists.newArrayList();
        this.m_142646_().m_142137_(p_151528_, p_151529_, p_151539_ -> {
            if (p_151530_.test(p_151539_)) {
                $$3.add(p_151539_);
            }
            if (p_151539_ instanceof EnderDragon) {
                EnderDragon $$4 = (EnderDragon)p_151539_;
                for (EnderDragonPart $$5 : $$4.m_31156_()) {
                    Entity $$6 = (Entity)p_151528_.m_141992_($$5);
                    if ($$6 == null || !p_151530_.test($$6)) continue;
                    $$3.add($$6);
                }
            }
        });
        return $$3;
    }

    @Nullable
    public abstract Entity m_6815_(int var1);

    public void m_151543_(BlockPos p_151544_) {
        if (this.m_46805_(p_151544_)) {
            this.m_46745_(p_151544_).m_8092_(true);
        }
    }

    @Override
    public int m_5736_() {
        return 63;
    }

    public int m_46751_(BlockPos p_46752_) {
        int $$1 = 0;
        if (($$1 = Math.max($$1, this.m_46852_(p_46752_.m_7495_(), Direction.DOWN))) >= 15) {
            return $$1;
        }
        if (($$1 = Math.max($$1, this.m_46852_(p_46752_.m_7494_(), Direction.UP))) >= 15) {
            return $$1;
        }
        if (($$1 = Math.max($$1, this.m_46852_(p_46752_.m_122012_(), Direction.NORTH))) >= 15) {
            return $$1;
        }
        if (($$1 = Math.max($$1, this.m_46852_(p_46752_.m_122019_(), Direction.SOUTH))) >= 15) {
            return $$1;
        }
        if (($$1 = Math.max($$1, this.m_46852_(p_46752_.m_122024_(), Direction.WEST))) >= 15) {
            return $$1;
        }
        if (($$1 = Math.max($$1, this.m_46852_(p_46752_.m_122029_(), Direction.EAST))) >= 15) {
            return $$1;
        }
        return $$1;
    }

    public boolean m_46616_(BlockPos p_46617_, Direction p_46618_) {
        return this.m_46681_(p_46617_, p_46618_) > 0;
    }

    public int m_46681_(BlockPos p_46682_, Direction p_46683_) {
        BlockState $$2 = this.m_8055_(p_46682_);
        int $$3 = $$2.m_60746_(this, p_46682_, p_46683_);
        if ($$2.m_60796_(this, p_46682_)) {
            return Math.max($$3, this.m_46751_(p_46682_));
        }
        return $$3;
    }

    public boolean m_46753_(BlockPos p_46754_) {
        if (this.m_46681_(p_46754_.m_7495_(), Direction.DOWN) > 0) {
            return true;
        }
        if (this.m_46681_(p_46754_.m_7494_(), Direction.UP) > 0) {
            return true;
        }
        if (this.m_46681_(p_46754_.m_122012_(), Direction.NORTH) > 0) {
            return true;
        }
        if (this.m_46681_(p_46754_.m_122019_(), Direction.SOUTH) > 0) {
            return true;
        }
        if (this.m_46681_(p_46754_.m_122024_(), Direction.WEST) > 0) {
            return true;
        }
        return this.m_46681_(p_46754_.m_122029_(), Direction.EAST) > 0;
    }

    public int m_46755_(BlockPos p_46756_) {
        int $$1 = 0;
        for (Direction $$2 : f_46422_) {
            int $$3 = this.m_46681_(p_46756_.m_121945_($$2), $$2);
            if ($$3 >= 15) {
                return 15;
            }
            if ($$3 <= $$1) continue;
            $$1 = $$3;
        }
        return $$1;
    }

    public void m_7462_() {
    }

    public long m_46467_() {
        return this.f_46442_.m_6793_();
    }

    public long m_46468_() {
        return this.f_46442_.m_6792_();
    }

    public boolean m_7966_(Player p_46557_, BlockPos p_46558_) {
        return true;
    }

    public void m_7605_(Entity p_46509_, byte p_46510_) {
    }

    public void m_7696_(BlockPos p_46582_, Block p_46583_, int p_46584_, int p_46585_) {
        this.m_8055_(p_46582_).m_60677_(this, p_46582_, p_46584_, p_46585_);
    }

    @Override
    public LevelData m_6106_() {
        return this.f_46442_;
    }

    public GameRules m_46469_() {
        return this.f_46442_.m_5470_();
    }

    public float m_46661_(float p_46662_) {
        return Mth.m_14179_(p_46662_, this.f_46439_, this.f_46440_) * this.m_46722_(p_46662_);
    }

    public void m_46707_(float p_46708_) {
        float $$1;
        this.f_46439_ = $$1 = Mth.m_14036_(p_46708_, 0.0f, 1.0f);
        this.f_46440_ = $$1;
    }

    public float m_46722_(float p_46723_) {
        return Mth.m_14179_(p_46723_, this.f_46437_, this.f_46438_);
    }

    public void m_46734_(float p_46735_) {
        float $$1;
        this.f_46437_ = $$1 = Mth.m_14036_(p_46735_, 0.0f, 1.0f);
        this.f_46438_ = $$1;
    }

    public boolean m_46470_() {
        if (!this.m_6042_().f_223549_() || this.m_6042_().f_63856_()) {
            return false;
        }
        return (double)this.m_46661_(1.0f) > 0.9;
    }

    public boolean m_46471_() {
        return (double)this.m_46722_(1.0f) > 0.2;
    }

    public boolean m_46758_(BlockPos p_46759_) {
        if (!this.m_46471_()) {
            return false;
        }
        if (!this.m_45527_(p_46759_)) {
            return false;
        }
        if (this.m_5452_(Heightmap.Types.MOTION_BLOCKING, p_46759_).m_123342_() > p_46759_.m_123342_()) {
            return false;
        }
        Biome $$1 = this.m_204166_(p_46759_).m_203334_();
        return $$1.m_47530_() == Biome.Precipitation.RAIN && $$1.m_198906_(p_46759_);
    }

    public boolean m_46761_(BlockPos p_46762_) {
        Biome $$1 = this.m_204166_(p_46762_).m_203334_();
        return $$1.m_47533_();
    }

    @Nullable
    public abstract MapItemSavedData m_7489_(String var1);

    public abstract void m_142325_(String var1, MapItemSavedData var2);

    public abstract int m_7354_();

    public void m_6798_(int p_46665_, BlockPos p_46666_, int p_46667_) {
    }

    public CrashReportCategory m_6026_(CrashReport p_46656_) {
        CrashReportCategory $$1 = p_46656_.m_127516_("Affected level", 1);
        $$1.m_128165_("All players", () -> this.m_6907_().size() + " total; " + this.m_6907_());
        $$1.m_128165_("Chunk stats", this.m_7726_()::m_6754_);
        $$1.m_128165_("Level dimension", () -> this.m_46472_().m_135782_().toString());
        try {
            this.f_46442_.m_142471_($$1, this);
        }
        catch (Throwable $$2) {
            $$1.m_128162_("Level Data Unobtainable", $$2);
        }
        return $$1;
    }

    public abstract void m_6801_(int var1, BlockPos var2, int var3);

    public void m_7228_(double p_46475_, double p_46476_, double p_46477_, double p_46478_, double p_46479_, double p_46480_, @Nullable CompoundTag p_46481_) {
    }

    public abstract Scoreboard m_6188_();

    public void m_46717_(BlockPos p_46718_, Block p_46719_) {
        for (Direction $$2 : Direction.Plane.HORIZONTAL) {
            BlockPos $$3 = p_46718_.m_121945_($$2);
            if (!this.m_46805_($$3)) continue;
            BlockState $$4 = this.m_8055_($$3);
            if ($$4.m_60713_(Blocks.f_50328_)) {
                this.m_213960_($$4, $$3, p_46719_, p_46718_, false);
                continue;
            }
            if (!$$4.m_60796_(this, $$3) || !($$4 = this.m_8055_($$3 = $$3.m_121945_($$2))).m_60713_(Blocks.f_50328_)) continue;
            this.m_213960_($$4, $$3, p_46719_, p_46718_, false);
        }
    }

    @Override
    public DifficultyInstance m_6436_(BlockPos p_46730_) {
        long $$1 = 0L;
        float $$2 = 0.0f;
        if (this.m_46805_(p_46730_)) {
            $$2 = this.m_46940_();
            $$1 = this.m_46745_(p_46730_).m_6319_();
        }
        return new DifficultyInstance(this.m_46791_(), this.m_46468_(), $$1, $$2);
    }

    @Override
    public int m_7445_() {
        return this.f_46425_;
    }

    public void m_6580_(int p_46709_) {
    }

    @Override
    public WorldBorder m_6857_() {
        return this.f_46447_;
    }

    public void m_5503_(Packet<?> p_46657_) {
        throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
    }

    @Override
    public DimensionType m_6042_() {
        return this.f_204147_.m_203334_();
    }

    public ResourceKey<DimensionType> m_220362_() {
        return this.f_220349_;
    }

    public Holder<DimensionType> m_204156_() {
        return this.f_204147_;
    }

    public ResourceKey<Level> m_46472_() {
        return this.f_46421_;
    }

    @Override
    public RandomSource m_213780_() {
        return this.f_46441_;
    }

    @Override
    public boolean m_7433_(BlockPos p_46620_, Predicate<BlockState> p_46621_) {
        return p_46621_.test(this.m_8055_(p_46620_));
    }

    @Override
    public boolean m_142433_(BlockPos p_151541_, Predicate<FluidState> p_151542_) {
        return p_151542_.test(this.m_6425_(p_151541_));
    }

    public abstract RecipeManager m_7465_();

    public BlockPos m_46496_(int p_46497_, int p_46498_, int p_46499_, int p_46500_) {
        this.f_46435_ = this.f_46435_ * 3 + 1013904223;
        int $$4 = this.f_46435_ >> 2;
        return new BlockPos(p_46497_ + ($$4 & 0xF), p_46498_ + ($$4 >> 16 & p_46500_), p_46499_ + ($$4 >> 8 & 0xF));
    }

    public boolean m_7441_() {
        return false;
    }

    public ProfilerFiller m_46473_() {
        return this.f_46446_.get();
    }

    public Supplier<ProfilerFiller> m_46658_() {
        return this.f_46446_;
    }

    @Override
    public BiomeManager m_7062_() {
        return this.f_46420_;
    }

    public final boolean m_46659_() {
        return this.f_46424_;
    }

    protected abstract LevelEntityGetter<Entity> m_142646_();

    @Override
    public long m_183596_() {
        return this.f_186455_++;
    }

    @Override
    public /* synthetic */ ChunkAccess m_6325_(int n, int n2) {
        return this.m_6325_(n, n2);
    }
}

