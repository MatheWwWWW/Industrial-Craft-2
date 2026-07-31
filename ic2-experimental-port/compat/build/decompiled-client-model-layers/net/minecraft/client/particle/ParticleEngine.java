/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.EvictingQueue
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.AshParticle;
import net.minecraft.client.particle.AttackSweepParticle;
import net.minecraft.client.particle.BlockMarker;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.BubbleColumnUpParticle;
import net.minecraft.client.particle.BubbleParticle;
import net.minecraft.client.particle.BubblePopParticle;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.client.particle.DragonBreathParticle;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.DustColorTransitionParticle;
import net.minecraft.client.particle.DustParticle;
import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.FallingDustParticle;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.HugeExplosionSeedParticle;
import net.minecraft.client.particle.LargeSmokeParticle;
import net.minecraft.client.particle.LavaParticle;
import net.minecraft.client.particle.MobAppearanceParticle;
import net.minecraft.client.particle.NoteParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleDescription;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.ReversePortalParticle;
import net.minecraft.client.particle.SculkChargeParticle;
import net.minecraft.client.particle.SculkChargePopParticle;
import net.minecraft.client.particle.ShriekParticle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.client.particle.SonicBoomParticle;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpitParticle;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SquidInkParticle;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.particle.TrackingEmitter;
import net.minecraft.client.particle.VibrationSignalParticle;
import net.minecraft.client.particle.WakeParticle;
import net.minecraft.client.particle.WaterCurrentDownParticle;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.client.particle.WhiteAshParticle;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ParticleEngine
implements PreparableReloadListener {
    private static final int f_172264_ = 16384;
    private static final List<ParticleRenderType> f_107288_ = ImmutableList.of((Object)ParticleRenderType.f_107429_, (Object)ParticleRenderType.f_107430_, (Object)ParticleRenderType.f_107432_, (Object)ParticleRenderType.f_107431_, (Object)ParticleRenderType.f_107433_);
    protected ClientLevel f_107287_;
    private final Map<ParticleRenderType, Queue<Particle>> f_107289_ = Maps.newIdentityHashMap();
    private final Queue<TrackingEmitter> f_107290_ = Queues.newArrayDeque();
    private final TextureManager f_107291_;
    private final RandomSource f_107292_ = RandomSource.m_216327_();
    private final Int2ObjectMap<ParticleProvider<?>> f_107293_ = new Int2ObjectOpenHashMap();
    private final Queue<Particle> f_107294_ = Queues.newArrayDeque();
    private final Map<ResourceLocation, MutableSpriteSet> f_107295_ = Maps.newHashMap();
    private final TextureAtlas f_107296_;
    private final Object2IntOpenHashMap<ParticleGroup> f_172265_ = new Object2IntOpenHashMap();

    public ParticleEngine(ClientLevel p_107299_, TextureManager p_107300_) {
        this.f_107296_ = new TextureAtlas(TextureAtlas.f_118260_);
        p_107300_.m_118495_(this.f_107296_.m_118330_(), this.f_107296_);
        this.f_107287_ = p_107299_;
        this.f_107291_ = p_107300_;
        this.m_107404_();
    }

    private void m_107404_() {
        this.m_107378_(ParticleTypes.f_123770_, SpellParticle.AmbientMobProvider::new);
        this.m_107378_(ParticleTypes.f_123792_, HeartParticle.AngryVillagerProvider::new);
        this.m_107381_(ParticleTypes.f_194652_, new BlockMarker.Provider());
        this.m_107381_(ParticleTypes.f_123794_, new TerrainParticle.Provider());
        this.m_107378_(ParticleTypes.f_123795_, BubbleParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123774_, BubbleColumnUpParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123772_, BubblePopParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123777_, CampfireSmokeParticle.CosyProvider::new);
        this.m_107378_(ParticleTypes.f_123778_, CampfireSmokeParticle.SignalProvider::new);
        this.m_107378_(ParticleTypes.f_123796_, PlayerCloudParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123749_, SuspendedTownParticle.ComposterFillProvider::new);
        this.m_107378_(ParticleTypes.f_123797_, CritParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123773_, WaterCurrentDownParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123798_, CritParticle.DamageIndicatorProvider::new);
        this.m_107378_(ParticleTypes.f_123799_, DragonBreathParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123776_, SuspendedTownParticle.DolphinSpeedProvider::new);
        this.m_107378_(ParticleTypes.f_123800_, DripParticle.LavaHangProvider::new);
        this.m_107378_(ParticleTypes.f_123801_, DripParticle.LavaFallProvider::new);
        this.m_107378_(ParticleTypes.f_123802_, DripParticle.LavaLandProvider::new);
        this.m_107378_(ParticleTypes.f_123803_, DripParticle.WaterHangProvider::new);
        this.m_107378_(ParticleTypes.f_123804_, DripParticle.WaterFallProvider::new);
        this.m_107378_(ParticleTypes.f_123805_, DustParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_175836_, DustColorTransitionParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123806_, SpellParticle.Provider::new);
        this.m_107381_(ParticleTypes.f_123807_, new MobAppearanceParticle.Provider());
        this.m_107378_(ParticleTypes.f_123808_, CritParticle.MagicProvider::new);
        this.m_107378_(ParticleTypes.f_123809_, EnchantmentTableParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123810_, EndRodParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123811_, SpellParticle.MobProvider::new);
        this.m_107381_(ParticleTypes.f_123812_, new HugeExplosionSeedParticle.Provider());
        this.m_107378_(ParticleTypes.f_123813_, HugeExplosionParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_235902_, SonicBoomParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123814_, FallingDustParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123815_, FireworkParticles.SparkProvider::new);
        this.m_107378_(ParticleTypes.f_123816_, WakeParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123744_, FlameParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_235898_, SoulParticle.EmissiveProvider::new);
        this.m_107378_(ParticleTypes.f_235899_, SculkChargeParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_235900_, SculkChargePopParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123746_, SoulParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123745_, FlameParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123747_, FireworkParticles.FlashProvider::new);
        this.m_107378_(ParticleTypes.f_123748_, SuspendedTownParticle.HappyVillagerProvider::new);
        this.m_107378_(ParticleTypes.f_123750_, HeartParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123751_, SpellParticle.InstantProvider::new);
        this.m_107381_(ParticleTypes.f_123752_, new BreakingItemParticle.Provider());
        this.m_107381_(ParticleTypes.f_123753_, new BreakingItemParticle.SlimeProvider());
        this.m_107381_(ParticleTypes.f_123754_, new BreakingItemParticle.SnowballProvider());
        this.m_107378_(ParticleTypes.f_123755_, LargeSmokeParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123756_, LavaParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123757_, SuspendedTownParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123775_, EnchantmentTableParticle.NautilusProvider::new);
        this.m_107378_(ParticleTypes.f_123758_, NoteParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123759_, ExplodeParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123760_, PortalParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123761_, WaterDropParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123762_, SmokeParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123763_, PlayerCloudParticle.SneezeProvider::new);
        this.m_107378_(ParticleTypes.f_175821_, SnowflakeParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123764_, SpitParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123766_, AttackSweepParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123767_, TotemParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123765_, SquidInkParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123768_, SuspendedParticle.UnderwaterProvider::new);
        this.m_107378_(ParticleTypes.f_123769_, SplashParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123771_, SpellParticle.WitchProvider::new);
        this.m_107378_(ParticleTypes.f_123779_, DripParticle.HoneyHangProvider::new);
        this.m_107378_(ParticleTypes.f_123780_, DripParticle.HoneyFallProvider::new);
        this.m_107378_(ParticleTypes.f_123781_, DripParticle.HoneyLandProvider::new);
        this.m_107378_(ParticleTypes.f_123782_, DripParticle.NectarFallProvider::new);
        this.m_107378_(ParticleTypes.f_175832_, DripParticle.SporeBlossomFallProvider::new);
        this.m_107378_(ParticleTypes.f_175833_, SuspendedParticle.SporeBlossomAirProvider::new);
        this.m_107378_(ParticleTypes.f_123783_, AshParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_123784_, SuspendedParticle.CrimsonSporeProvider::new);
        this.m_107378_(ParticleTypes.f_123785_, SuspendedParticle.WarpedSporeProvider::new);
        this.m_107378_(ParticleTypes.f_123786_, DripParticle.ObsidianTearHangProvider::new);
        this.m_107378_(ParticleTypes.f_123787_, DripParticle.ObsidianTearFallProvider::new);
        this.m_107378_(ParticleTypes.f_123788_, DripParticle.ObsidianTearLandProvider::new);
        this.m_107378_(ParticleTypes.f_123789_, ReversePortalParticle.ReversePortalProvider::new);
        this.m_107378_(ParticleTypes.f_123790_, WhiteAshParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_175834_, FlameParticle.SmallFlameProvider::new);
        this.m_107378_(ParticleTypes.f_175824_, DripParticle.DripstoneWaterHangProvider::new);
        this.m_107378_(ParticleTypes.f_175825_, DripParticle.DripstoneWaterFallProvider::new);
        this.m_107378_(ParticleTypes.f_175822_, DripParticle.DripstoneLavaHangProvider::new);
        this.m_107378_(ParticleTypes.f_175823_, DripParticle.DripstoneLavaFallProvider::new);
        this.m_107378_(ParticleTypes.f_175820_, VibrationSignalParticle.Provider::new);
        this.m_107378_(ParticleTypes.f_175826_, SquidInkParticle.GlowInkProvider::new);
        this.m_107378_(ParticleTypes.f_175827_, GlowParticle.GlowSquidProvider::new);
        this.m_107378_(ParticleTypes.f_175828_, GlowParticle.WaxOnProvider::new);
        this.m_107378_(ParticleTypes.f_175829_, GlowParticle.WaxOffProvider::new);
        this.m_107378_(ParticleTypes.f_175830_, GlowParticle.ElectricSparkProvider::new);
        this.m_107378_(ParticleTypes.f_175831_, GlowParticle.ScrapeProvider::new);
        this.m_107378_(ParticleTypes.f_235901_, ShriekParticle.Provider::new);
    }

    private <T extends ParticleOptions> void m_107381_(ParticleType<T> p_107382_, ParticleProvider<T> p_107383_) {
        this.f_107293_.put(Registry.f_122829_.m_7447_(p_107382_), p_107383_);
    }

    private <T extends ParticleOptions> void m_107378_(ParticleType<T> p_107379_, SpriteParticleRegistration<T> p_107380_) {
        MutableSpriteSet $$2 = new MutableSpriteSet();
        this.f_107295_.put(Registry.f_122829_.m_7981_(p_107379_), $$2);
        this.f_107293_.put(Registry.f_122829_.m_7447_(p_107379_), p_107380_.m_107419_($$2));
    }

    @Override
    public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_107305_, ResourceManager p_107306_, ProfilerFiller p_107307_, ProfilerFiller p_107308_, Executor p_107309_, Executor p_107310_) {
        ConcurrentMap $$6 = Maps.newConcurrentMap();
        CompletableFuture[] $$7 = (CompletableFuture[])Registry.f_122829_.m_6566_().stream().map(p_107315_ -> CompletableFuture.runAsync(() -> this.m_107316_(p_107306_, (ResourceLocation)p_107315_, $$6), p_107309_)).toArray(CompletableFuture[]::new);
        return ((CompletableFuture)((CompletableFuture)CompletableFuture.allOf($$7).thenApplyAsync(p_107324_ -> {
            p_107307_.m_7242_();
            p_107307_.m_6180_("stitching");
            TextureAtlas.Preparations $$4 = this.f_107296_.m_118307_(p_107306_, $$6.values().stream().flatMap(Collection::stream), p_107307_, 0);
            p_107307_.m_7238_();
            p_107307_.m_7241_();
            return $$4;
        }, p_107309_)).thenCompose(p_107305_::m_6769_)).thenAcceptAsync(p_107328_ -> {
            this.f_107289_.clear();
            p_107308_.m_7242_();
            p_107308_.m_6180_("upload");
            this.f_107296_.m_118312_((TextureAtlas.Preparations)p_107328_);
            p_107308_.m_6182_("bindSpriteSets");
            TextureAtlasSprite $$3 = this.f_107296_.m_118316_(MissingTextureAtlasSprite.m_118071_());
            $$6.forEach((p_172268_, p_172269_) -> {
                ImmutableList $$3 = p_172269_.isEmpty() ? ImmutableList.of((Object)$$3) : (ImmutableList)p_172269_.stream().map(this.f_107296_::m_118316_).collect(ImmutableList.toImmutableList());
                this.f_107295_.get(p_172268_).m_107415_((List<TextureAtlasSprite>)$$3);
            });
            p_107308_.m_7238_();
            p_107308_.m_7241_();
        }, p_107310_);
    }

    public void m_107301_() {
        this.f_107296_.m_118329_();
    }

    private void m_107316_(ResourceManager p_107317_, ResourceLocation p_107318_, Map<ResourceLocation, List<ResourceLocation>> p_107319_) {
        ResourceLocation $$3 = new ResourceLocation(p_107318_.m_135827_(), "particles/" + p_107318_.m_135815_() + ".json");
        try (BufferedReader $$4 = p_107317_.m_215597_($$3);){
            ParticleDescription $$5 = ParticleDescription.m_107285_(GsonHelper.m_13859_($$4));
            List<ResourceLocation> $$6 = $$5.m_107282_();
            boolean $$7 = this.f_107295_.containsKey(p_107318_);
            if ($$6 == null) {
                if ($$7) {
                    throw new IllegalStateException("Missing texture list for particle " + p_107318_);
                }
            } else {
                if (!$$7) {
                    throw new IllegalStateException("Redundant texture list for particle " + p_107318_);
                }
                p_107319_.put(p_107318_, $$6.stream().map(p_107387_ -> new ResourceLocation(p_107387_.m_135827_(), "particle/" + p_107387_.m_135815_())).collect(Collectors.toList()));
            }
        }
        catch (IOException $$8) {
            throw new IllegalStateException("Failed to load description for particle " + p_107318_, $$8);
        }
    }

    public void m_107329_(Entity p_107330_, ParticleOptions p_107331_) {
        this.f_107290_.add(new TrackingEmitter(this.f_107287_, p_107330_, p_107331_));
    }

    public void m_107332_(Entity p_107333_, ParticleOptions p_107334_, int p_107335_) {
        this.f_107290_.add(new TrackingEmitter(this.f_107287_, p_107333_, p_107334_, p_107335_));
    }

    @Nullable
    public Particle m_107370_(ParticleOptions p_107371_, double p_107372_, double p_107373_, double p_107374_, double p_107375_, double p_107376_, double p_107377_) {
        Particle $$7 = this.m_107395_(p_107371_, p_107372_, p_107373_, p_107374_, p_107375_, p_107376_, p_107377_);
        if ($$7 != null) {
            this.m_107344_($$7);
            return $$7;
        }
        return null;
    }

    @Nullable
    private <T extends ParticleOptions> Particle m_107395_(T p_107396_, double p_107397_, double p_107398_, double p_107399_, double p_107400_, double p_107401_, double p_107402_) {
        ParticleProvider $$7 = (ParticleProvider)this.f_107293_.get(Registry.f_122829_.m_7447_(p_107396_.m_6012_()));
        if ($$7 == null) {
            return null;
        }
        return $$7.m_6966_(p_107396_, this.f_107287_, p_107397_, p_107398_, p_107399_, p_107400_, p_107401_, p_107402_);
    }

    public void m_107344_(Particle p_107345_) {
        Optional<ParticleGroup> $$1 = p_107345_.m_142654_();
        if ($$1.isPresent()) {
            if (this.m_172279_($$1.get())) {
                this.f_107294_.add(p_107345_);
                this.m_172281_($$1.get(), 1);
            }
        } else {
            this.f_107294_.add(p_107345_);
        }
    }

    public void m_107388_() {
        this.f_107289_.forEach((p_107349_, p_107350_) -> {
            this.f_107287_.m_46473_().m_6180_(p_107349_.toString());
            this.m_107384_((Collection<Particle>)p_107350_);
            this.f_107287_.m_46473_().m_7238_();
        });
        if (!this.f_107290_.isEmpty()) {
            ArrayList $$0 = Lists.newArrayList();
            for (TrackingEmitter $$1 : this.f_107290_) {
                $$1.m_5989_();
                if ($$1.m_107276_()) continue;
                $$0.add($$1);
            }
            this.f_107290_.removeAll($$0);
        }
        if (!this.f_107294_.isEmpty()) {
            Particle $$2;
            while (($$2 = this.f_107294_.poll()) != null) {
                this.f_107289_.computeIfAbsent($$2.m_7556_(), p_107347_ -> EvictingQueue.create((int)16384)).add($$2);
            }
        }
    }

    private void m_107384_(Collection<Particle> p_107385_) {
        if (!p_107385_.isEmpty()) {
            Iterator<Particle> $$1 = p_107385_.iterator();
            while ($$1.hasNext()) {
                Particle $$2 = $$1.next();
                this.m_107393_($$2);
                if ($$2.m_107276_()) continue;
                $$2.m_142654_().ifPresent(p_172289_ -> this.m_172281_((ParticleGroup)p_172289_, -1));
                $$1.remove();
            }
        }
    }

    private void m_172281_(ParticleGroup p_172282_, int p_172283_) {
        this.f_172265_.addTo((Object)p_172282_, p_172283_);
    }

    private void m_107393_(Particle p_107394_) {
        try {
            p_107394_.m_5989_();
        }
        catch (Throwable $$1) {
            CrashReport $$2 = CrashReport.m_127521_($$1, "Ticking Particle");
            CrashReportCategory $$3 = $$2.m_127514_("Particle being ticked");
            $$3.m_128165_("Particle", p_107394_::toString);
            $$3.m_128165_("Particle Type", p_107394_.m_7556_()::toString);
            throw new ReportedException($$2);
        }
    }

    public void m_107336_(PoseStack p_107337_, MultiBufferSource.BufferSource p_107338_, LightTexture p_107339_, Camera p_107340_, float p_107341_) {
        p_107339_.m_109896_();
        RenderSystem.m_69482_();
        PoseStack $$5 = RenderSystem.m_157191_();
        $$5.m_85836_();
        $$5.m_166854_(p_107337_.m_85850_().m_85861_());
        RenderSystem.m_157182_();
        for (ParticleRenderType $$6 : f_107288_) {
            Iterable $$7 = this.f_107289_.get($$6);
            if ($$7 == null) continue;
            RenderSystem.m_157427_(GameRenderer::m_172829_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            Tesselator $$8 = Tesselator.m_85913_();
            BufferBuilder $$9 = $$8.m_85915_();
            $$6.m_6505_($$9, this.f_107291_);
            for (Particle $$10 : $$7) {
                try {
                    $$10.m_5744_($$9, p_107340_, p_107341_);
                }
                catch (Throwable $$11) {
                    CrashReport $$12 = CrashReport.m_127521_($$11, "Rendering Particle");
                    CrashReportCategory $$13 = $$12.m_127514_("Particle being rendered");
                    $$13.m_128165_("Particle", $$10::toString);
                    $$13.m_128165_("Particle Type", $$6::toString);
                    throw new ReportedException($$12);
                }
            }
            $$6.m_6294_($$8);
        }
        $$5.m_85849_();
        RenderSystem.m_157182_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69461_();
        p_107339_.m_109891_();
    }

    public void m_107342_(@Nullable ClientLevel p_107343_) {
        this.f_107287_ = p_107343_;
        this.f_107289_.clear();
        this.f_107290_.clear();
        this.f_172265_.clear();
    }

    public void m_107355_(BlockPos p_107356_, BlockState p_107357_) {
        if (p_107357_.m_60795_()) {
            return;
        }
        VoxelShape $$2 = p_107357_.m_60808_(this.f_107287_, p_107356_);
        double $$3 = 0.25;
        $$2.m_83286_((p_172273_, p_172274_, p_172275_, p_172276_, p_172277_, p_172278_) -> {
            double $$8 = Math.min(1.0, p_172276_ - p_172273_);
            double $$9 = Math.min(1.0, p_172277_ - p_172274_);
            double $$10 = Math.min(1.0, p_172278_ - p_172275_);
            int $$11 = Math.max(2, Mth.m_14165_($$8 / 0.25));
            int $$12 = Math.max(2, Mth.m_14165_($$9 / 0.25));
            int $$13 = Math.max(2, Mth.m_14165_($$10 / 0.25));
            for (int $$14 = 0; $$14 < $$11; ++$$14) {
                for (int $$15 = 0; $$15 < $$12; ++$$15) {
                    for (int $$16 = 0; $$16 < $$13; ++$$16) {
                        double $$17 = ((double)$$14 + 0.5) / (double)$$11;
                        double $$18 = ((double)$$15 + 0.5) / (double)$$12;
                        double $$19 = ((double)$$16 + 0.5) / (double)$$13;
                        double $$20 = $$17 * $$8 + p_172273_;
                        double $$21 = $$18 * $$9 + p_172274_;
                        double $$22 = $$19 * $$10 + p_172275_;
                        this.m_107344_(new TerrainParticle(this.f_107287_, (double)p_107356_.m_123341_() + $$20, (double)p_107356_.m_123342_() + $$21, (double)p_107356_.m_123343_() + $$22, $$17 - 0.5, $$18 - 0.5, $$19 - 0.5, p_107357_, p_107356_));
                    }
                }
            }
        });
    }

    public void m_107367_(BlockPos p_107368_, Direction p_107369_) {
        BlockState $$2 = this.f_107287_.m_8055_(p_107368_);
        if ($$2.m_60799_() == RenderShape.INVISIBLE) {
            return;
        }
        int $$3 = p_107368_.m_123341_();
        int $$4 = p_107368_.m_123342_();
        int $$5 = p_107368_.m_123343_();
        float $$6 = 0.1f;
        AABB $$7 = $$2.m_60808_(this.f_107287_, p_107368_).m_83215_();
        double $$8 = (double)$$3 + this.f_107292_.m_188500_() * ($$7.f_82291_ - $$7.f_82288_ - (double)0.2f) + (double)0.1f + $$7.f_82288_;
        double $$9 = (double)$$4 + this.f_107292_.m_188500_() * ($$7.f_82292_ - $$7.f_82289_ - (double)0.2f) + (double)0.1f + $$7.f_82289_;
        double $$10 = (double)$$5 + this.f_107292_.m_188500_() * ($$7.f_82293_ - $$7.f_82290_ - (double)0.2f) + (double)0.1f + $$7.f_82290_;
        if (p_107369_ == Direction.DOWN) {
            $$9 = (double)$$4 + $$7.f_82289_ - (double)0.1f;
        }
        if (p_107369_ == Direction.UP) {
            $$9 = (double)$$4 + $$7.f_82292_ + (double)0.1f;
        }
        if (p_107369_ == Direction.NORTH) {
            $$10 = (double)$$5 + $$7.f_82290_ - (double)0.1f;
        }
        if (p_107369_ == Direction.SOUTH) {
            $$10 = (double)$$5 + $$7.f_82293_ + (double)0.1f;
        }
        if (p_107369_ == Direction.WEST) {
            $$8 = (double)$$3 + $$7.f_82288_ - (double)0.1f;
        }
        if (p_107369_ == Direction.EAST) {
            $$8 = (double)$$3 + $$7.f_82291_ + (double)0.1f;
        }
        this.m_107344_(new TerrainParticle(this.f_107287_, $$8, $$9, $$10, 0.0, 0.0, 0.0, $$2, p_107368_).m_107268_(0.2f).m_6569_(0.6f));
    }

    public String m_107403_() {
        return String.valueOf(this.f_107289_.values().stream().mapToInt(Collection::size).sum());
    }

    private boolean m_172279_(ParticleGroup p_172280_) {
        return this.f_172265_.getInt((Object)p_172280_) < p_172280_.m_175819_();
    }

    @FunctionalInterface
    static interface SpriteParticleRegistration<T extends ParticleOptions> {
        public ParticleProvider<T> m_107419_(SpriteSet var1);
    }

    static class MutableSpriteSet
    implements SpriteSet {
        private List<TextureAtlasSprite> f_107406_;

        MutableSpriteSet() {
        }

        @Override
        public TextureAtlasSprite m_5819_(int p_107413_, int p_107414_) {
            return this.f_107406_.get(p_107413_ * (this.f_107406_.size() - 1) / p_107414_);
        }

        @Override
        public TextureAtlasSprite m_213979_(RandomSource p_233889_) {
            return this.f_107406_.get(p_233889_.m_188503_(this.f_107406_.size()));
        }

        public void m_107415_(List<TextureAtlasSprite> p_107416_) {
            this.f_107406_ = ImmutableList.copyOf(p_107416_);
        }
    }
}

