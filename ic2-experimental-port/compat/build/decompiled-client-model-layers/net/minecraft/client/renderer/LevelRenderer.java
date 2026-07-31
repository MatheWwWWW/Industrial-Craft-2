/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.PrioritizeChunkUpdates;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RunningTrimmedMean;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

public class LevelRenderer
implements ResourceManagerReloadListener,
AutoCloseable {
    private static final Logger f_109453_ = LogUtils.getLogger();
    public static final int f_172937_ = 16;
    private static final int f_194302_ = 8;
    private static final float f_172941_ = 512.0f;
    private static final int f_194303_ = 60;
    private static final double f_194304_ = Math.ceil(Math.sqrt(3.0) * 16.0);
    private static final int f_172942_ = 32;
    private static final int f_172943_ = 10;
    private static final int f_172944_ = 21;
    private static final int f_172945_ = 15;
    private static final int f_194305_ = 500;
    private static final ResourceLocation f_109454_ = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation f_109455_ = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation f_109456_ = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation f_109457_ = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation f_109458_ = new ResourceLocation("textures/misc/forcefield.png");
    private static final ResourceLocation f_109459_ = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation f_109460_ = new ResourceLocation("textures/environment/snow.png");
    public static final Direction[] f_109434_ = Direction.values();
    private final Minecraft f_109461_;
    private final EntityRenderDispatcher f_109463_;
    private final BlockEntityRenderDispatcher f_172946_;
    private final RenderBuffers f_109464_;
    @Nullable
    private ClientLevel f_109465_;
    private final BlockingQueue<ChunkRenderDispatcher.RenderChunk> f_194306_ = new LinkedBlockingQueue<ChunkRenderDispatcher.RenderChunk>();
    private final AtomicReference<RenderChunkStorage> f_194307_ = new AtomicReference();
    private final ObjectArrayList<RenderChunkInfo> f_194297_ = new ObjectArrayList(10000);
    private final Set<BlockEntity> f_109468_ = Sets.newHashSet();
    @Nullable
    private Future<?> f_194298_;
    @Nullable
    private ViewArea f_109469_;
    @Nullable
    private VertexBuffer f_109471_;
    @Nullable
    private VertexBuffer f_109472_;
    @Nullable
    private VertexBuffer f_109473_;
    private boolean f_109474_ = true;
    @Nullable
    private VertexBuffer f_109475_;
    private final RunningTrimmedMean f_109476_ = new RunningTrimmedMean(100);
    private int f_109477_;
    private final Int2ObjectMap<BlockDestructionProgress> f_109408_ = new Int2ObjectOpenHashMap();
    private final Long2ObjectMap<SortedSet<BlockDestructionProgress>> f_109409_ = new Long2ObjectOpenHashMap();
    private final Map<BlockPos, SoundInstance> f_109410_ = Maps.newHashMap();
    @Nullable
    private RenderTarget f_109411_;
    @Nullable
    private PostChain f_109412_;
    @Nullable
    private RenderTarget f_109413_;
    @Nullable
    private RenderTarget f_109414_;
    @Nullable
    private RenderTarget f_109415_;
    @Nullable
    private RenderTarget f_109416_;
    @Nullable
    private RenderTarget f_109417_;
    @Nullable
    private PostChain f_109418_;
    private double f_109419_ = Double.MIN_VALUE;
    private double f_109420_ = Double.MIN_VALUE;
    private double f_109421_ = Double.MIN_VALUE;
    private int f_109422_ = Integer.MIN_VALUE;
    private int f_109423_ = Integer.MIN_VALUE;
    private int f_109424_ = Integer.MIN_VALUE;
    private double f_109425_ = Double.MIN_VALUE;
    private double f_109426_ = Double.MIN_VALUE;
    private double f_109427_ = Double.MIN_VALUE;
    private double f_109428_ = Double.MIN_VALUE;
    private double f_109429_ = Double.MIN_VALUE;
    private int f_109430_ = Integer.MIN_VALUE;
    private int f_109431_ = Integer.MIN_VALUE;
    private int f_109432_ = Integer.MIN_VALUE;
    private Vec3 f_109433_ = Vec3.f_82478_;
    @Nullable
    private CloudStatus f_109435_;
    @Nullable
    private ChunkRenderDispatcher f_109436_;
    private int f_109438_ = -1;
    private int f_109439_;
    private int f_109440_;
    private Frustum f_172938_;
    private boolean f_109441_;
    @Nullable
    private Frustum f_109442_;
    private final Vector4f[] f_109443_ = new Vector4f[8];
    private final Vector3d f_109444_ = new Vector3d(0.0, 0.0, 0.0);
    private double f_109445_;
    private double f_109446_;
    private double f_109447_;
    private boolean f_194300_ = true;
    private final AtomicLong f_194301_ = new AtomicLong(0L);
    private final AtomicBoolean f_194299_ = new AtomicBoolean(false);
    private int f_109450_;
    private final float[] f_109451_ = new float[1024];
    private final float[] f_109452_ = new float[1024];

    public LevelRenderer(Minecraft p_234245_, EntityRenderDispatcher p_234246_, BlockEntityRenderDispatcher p_234247_, RenderBuffers p_234248_) {
        this.f_109461_ = p_234245_;
        this.f_109463_ = p_234246_;
        this.f_172946_ = p_234247_;
        this.f_109464_ = p_234248_;
        for (int $$4 = 0; $$4 < 32; ++$$4) {
            for (int $$5 = 0; $$5 < 32; ++$$5) {
                float $$6 = $$5 - 16;
                float $$7 = $$4 - 16;
                float $$8 = Mth.m_14116_($$6 * $$6 + $$7 * $$7);
                this.f_109451_[$$4 << 5 | $$5] = -$$7 / $$8;
                this.f_109452_[$$4 << 5 | $$5] = $$6 / $$8;
            }
        }
        this.m_109837_();
        this.m_109836_();
        this.m_109835_();
    }

    private void m_109703_(LightTexture p_109704_, float p_109705_, double p_109706_, double p_109707_, double p_109708_) {
        float $$5 = this.f_109461_.f_91073_.m_46722_(p_109705_);
        if ($$5 <= 0.0f) {
            return;
        }
        p_109704_.m_109896_();
        ClientLevel $$6 = this.f_109461_.f_91073_;
        int $$7 = Mth.m_14107_(p_109706_);
        int $$8 = Mth.m_14107_(p_109707_);
        int $$9 = Mth.m_14107_(p_109708_);
        Tesselator $$10 = Tesselator.m_85913_();
        BufferBuilder $$11 = $$10.m_85915_();
        RenderSystem.m_69464_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69482_();
        int $$12 = 5;
        if (Minecraft.m_91405_()) {
            $$12 = 10;
        }
        RenderSystem.m_69458_(Minecraft.m_91085_());
        int $$13 = -1;
        float $$14 = (float)this.f_109477_ + p_109705_;
        RenderSystem.m_157427_(GameRenderer::m_172829_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        BlockPos.MutableBlockPos $$15 = new BlockPos.MutableBlockPos();
        for (int $$16 = $$9 - $$12; $$16 <= $$9 + $$12; ++$$16) {
            for (int $$17 = $$7 - $$12; $$17 <= $$7 + $$12; ++$$17) {
                int $$25;
                int $$18 = ($$16 - $$9 + 16) * 32 + $$17 - $$7 + 16;
                double $$19 = (double)this.f_109451_[$$18] * 0.5;
                double $$20 = (double)this.f_109452_[$$18] * 0.5;
                $$15.m_122169_($$17, p_109707_, $$16);
                Biome $$21 = $$6.m_204166_($$15).m_203334_();
                if ($$21.m_47530_() == Biome.Precipitation.NONE) continue;
                int $$22 = $$6.m_6924_(Heightmap.Types.MOTION_BLOCKING, $$17, $$16);
                int $$23 = $$8 - $$12;
                int $$24 = $$8 + $$12;
                if ($$23 < $$22) {
                    $$23 = $$22;
                }
                if ($$24 < $$22) {
                    $$24 = $$22;
                }
                if (($$25 = $$22) < $$8) {
                    $$25 = $$8;
                }
                if ($$23 == $$24) continue;
                RandomSource $$26 = RandomSource.m_216335_($$17 * $$17 * 3121 + $$17 * 45238971 ^ $$16 * $$16 * 418711 + $$16 * 13761);
                $$15.m_122178_($$17, $$23, $$16);
                if ($$21.m_198906_($$15)) {
                    if ($$13 != 0) {
                        if ($$13 >= 0) {
                            $$10.m_85914_();
                        }
                        $$13 = 0;
                        RenderSystem.m_157456_(0, f_109459_);
                        $$11.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
                    }
                    int $$27 = this.f_109477_ + $$17 * $$17 * 3121 + $$17 * 45238971 + $$16 * $$16 * 418711 + $$16 * 13761 & 0x1F;
                    float $$28 = -((float)$$27 + p_109705_) / 32.0f * (3.0f + $$26.m_188501_());
                    double $$29 = (double)$$17 + 0.5 - p_109706_;
                    double $$30 = (double)$$16 + 0.5 - p_109708_;
                    float $$31 = (float)Math.sqrt($$29 * $$29 + $$30 * $$30) / (float)$$12;
                    float $$32 = ((1.0f - $$31 * $$31) * 0.5f + 0.5f) * $$5;
                    $$15.m_122178_($$17, $$25, $$16);
                    int $$33 = LevelRenderer.m_109541_($$6, $$15);
                    $$11.m_5483_((double)$$17 - p_109706_ - $$19 + 0.5, (double)$$24 - p_109707_, (double)$$16 - p_109708_ - $$20 + 0.5).m_7421_(0.0f, (float)$$23 * 0.25f + $$28).m_85950_(1.0f, 1.0f, 1.0f, $$32).m_85969_($$33).m_5752_();
                    $$11.m_5483_((double)$$17 - p_109706_ + $$19 + 0.5, (double)$$24 - p_109707_, (double)$$16 - p_109708_ + $$20 + 0.5).m_7421_(1.0f, (float)$$23 * 0.25f + $$28).m_85950_(1.0f, 1.0f, 1.0f, $$32).m_85969_($$33).m_5752_();
                    $$11.m_5483_((double)$$17 - p_109706_ + $$19 + 0.5, (double)$$23 - p_109707_, (double)$$16 - p_109708_ + $$20 + 0.5).m_7421_(1.0f, (float)$$24 * 0.25f + $$28).m_85950_(1.0f, 1.0f, 1.0f, $$32).m_85969_($$33).m_5752_();
                    $$11.m_5483_((double)$$17 - p_109706_ - $$19 + 0.5, (double)$$23 - p_109707_, (double)$$16 - p_109708_ - $$20 + 0.5).m_7421_(0.0f, (float)$$24 * 0.25f + $$28).m_85950_(1.0f, 1.0f, 1.0f, $$32).m_85969_($$33).m_5752_();
                    continue;
                }
                if ($$13 != 1) {
                    if ($$13 >= 0) {
                        $$10.m_85914_();
                    }
                    $$13 = 1;
                    RenderSystem.m_157456_(0, f_109460_);
                    $$11.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
                }
                float $$34 = -((float)(this.f_109477_ & 0x1FF) + p_109705_) / 512.0f;
                float $$35 = (float)($$26.m_188500_() + (double)$$14 * 0.01 * (double)((float)$$26.m_188583_()));
                float $$36 = (float)($$26.m_188500_() + (double)($$14 * (float)$$26.m_188583_()) * 0.001);
                double $$37 = (double)$$17 + 0.5 - p_109706_;
                double $$38 = (double)$$16 + 0.5 - p_109708_;
                float $$39 = (float)Math.sqrt($$37 * $$37 + $$38 * $$38) / (float)$$12;
                float $$40 = ((1.0f - $$39 * $$39) * 0.3f + 0.5f) * $$5;
                $$15.m_122178_($$17, $$25, $$16);
                int $$41 = LevelRenderer.m_109541_($$6, $$15);
                int $$42 = $$41 >> 16 & 0xFFFF;
                int $$43 = $$41 & 0xFFFF;
                int $$44 = ($$42 * 3 + 240) / 4;
                int $$45 = ($$43 * 3 + 240) / 4;
                $$11.m_5483_((double)$$17 - p_109706_ - $$19 + 0.5, (double)$$24 - p_109707_, (double)$$16 - p_109708_ - $$20 + 0.5).m_7421_(0.0f + $$35, (float)$$23 * 0.25f + $$34 + $$36).m_85950_(1.0f, 1.0f, 1.0f, $$40).m_7120_($$45, $$44).m_5752_();
                $$11.m_5483_((double)$$17 - p_109706_ + $$19 + 0.5, (double)$$24 - p_109707_, (double)$$16 - p_109708_ + $$20 + 0.5).m_7421_(1.0f + $$35, (float)$$23 * 0.25f + $$34 + $$36).m_85950_(1.0f, 1.0f, 1.0f, $$40).m_7120_($$45, $$44).m_5752_();
                $$11.m_5483_((double)$$17 - p_109706_ + $$19 + 0.5, (double)$$23 - p_109707_, (double)$$16 - p_109708_ + $$20 + 0.5).m_7421_(1.0f + $$35, (float)$$24 * 0.25f + $$34 + $$36).m_85950_(1.0f, 1.0f, 1.0f, $$40).m_7120_($$45, $$44).m_5752_();
                $$11.m_5483_((double)$$17 - p_109706_ - $$19 + 0.5, (double)$$23 - p_109707_, (double)$$16 - p_109708_ - $$20 + 0.5).m_7421_(0.0f + $$35, (float)$$24 * 0.25f + $$34 + $$36).m_85950_(1.0f, 1.0f, 1.0f, $$40).m_7120_($$45, $$44).m_5752_();
            }
        }
        if ($$13 >= 0) {
            $$10.m_85914_();
        }
        RenderSystem.m_69481_();
        RenderSystem.m_69461_();
        p_109704_.m_109891_();
    }

    public void m_109693_(Camera p_109694_) {
        float $$1 = this.f_109461_.f_91073_.m_46722_(1.0f) / (Minecraft.m_91405_() ? 1.0f : 2.0f);
        if ($$1 <= 0.0f) {
            return;
        }
        RandomSource $$2 = RandomSource.m_216335_((long)this.f_109477_ * 312987231L);
        ClientLevel $$3 = this.f_109461_.f_91073_;
        BlockPos $$4 = new BlockPos(p_109694_.m_90583_());
        Vec3i $$5 = null;
        int $$6 = (int)(100.0f * $$1 * $$1) / (this.f_109461_.f_91066_.m_231929_().m_231551_() == ParticleStatus.DECREASED ? 2 : 1);
        for (int $$7 = 0; $$7 < $$6; ++$$7) {
            int $$8 = $$2.m_188503_(21) - 10;
            int $$9 = $$2.m_188503_(21) - 10;
            BlockPos $$10 = $$3.m_5452_(Heightmap.Types.MOTION_BLOCKING, $$4.m_7918_($$8, 0, $$9));
            Biome $$11 = $$3.m_204166_($$10).m_203334_();
            if ($$10.m_123342_() <= $$3.m_141937_() || $$10.m_123342_() > $$4.m_123342_() + 10 || $$10.m_123342_() < $$4.m_123342_() - 10 || $$11.m_47530_() != Biome.Precipitation.RAIN || !$$11.m_198906_($$10)) continue;
            $$5 = $$10.m_7495_();
            if (this.f_109461_.f_91066_.m_231929_().m_231551_() == ParticleStatus.MINIMAL) break;
            double $$12 = $$2.m_188500_();
            double $$13 = $$2.m_188500_();
            BlockState $$14 = $$3.m_8055_((BlockPos)$$5);
            FluidState $$15 = $$3.m_6425_((BlockPos)$$5);
            VoxelShape $$16 = $$14.m_60812_($$3, (BlockPos)$$5);
            double $$17 = $$16.m_83290_(Direction.Axis.Y, $$12, $$13);
            double $$18 = $$15.m_76155_($$3, (BlockPos)$$5);
            double $$19 = Math.max($$17, $$18);
            SimpleParticleType $$20 = $$15.m_205070_(FluidTags.f_13132_) || $$14.m_60713_(Blocks.f_50450_) || CampfireBlock.m_51319_($$14) ? ParticleTypes.f_123762_ : ParticleTypes.f_123761_;
            this.f_109461_.f_91073_.m_7106_($$20, (double)$$5.m_123341_() + $$12, (double)$$5.m_123342_() + $$19, (double)$$5.m_123343_() + $$13, 0.0, 0.0, 0.0);
        }
        if ($$5 != null && $$2.m_188503_(3) < this.f_109450_++) {
            this.f_109450_ = 0;
            if ($$5.m_123342_() > $$4.m_123342_() + 1 && $$3.m_5452_(Heightmap.Types.MOTION_BLOCKING, $$4).m_123342_() > Mth.m_14143_($$4.m_123342_())) {
                this.f_109461_.f_91073_.m_104677_((BlockPos)$$5, SoundEvents.f_12542_, SoundSource.WEATHER, 0.1f, 0.5f, false);
            } else {
                this.f_109461_.f_91073_.m_104677_((BlockPos)$$5, SoundEvents.f_12541_, SoundSource.WEATHER, 0.2f, 1.0f, false);
            }
        }
    }

    @Override
    public void close() {
        if (this.f_109412_ != null) {
            this.f_109412_.close();
        }
        if (this.f_109418_ != null) {
            this.f_109418_.close();
        }
    }

    @Override
    public void m_6213_(ResourceManager p_109513_) {
        this.m_109482_();
        if (Minecraft.m_91085_()) {
            this.m_109833_();
        }
    }

    public void m_109482_() {
        if (this.f_109412_ != null) {
            this.f_109412_.close();
        }
        ResourceLocation $$0 = new ResourceLocation("shaders/post/entity_outline.json");
        try {
            this.f_109412_ = new PostChain(this.f_109461_.m_91097_(), this.f_109461_.m_91098_(), this.f_109461_.m_91385_(), $$0);
            this.f_109412_.m_110025_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
            this.f_109411_ = this.f_109412_.m_110036_("final");
        }
        catch (IOException $$1) {
            f_109453_.warn("Failed to load shader: {}", (Object)$$0, (Object)$$1);
            this.f_109412_ = null;
            this.f_109411_ = null;
        }
        catch (JsonSyntaxException $$2) {
            f_109453_.warn("Failed to parse shader: {}", (Object)$$0, (Object)$$2);
            this.f_109412_ = null;
            this.f_109411_ = null;
        }
    }

    private void m_109833_() {
        this.m_109834_();
        ResourceLocation $$0 = new ResourceLocation("shaders/post/transparency.json");
        try {
            PostChain $$1 = new PostChain(this.f_109461_.m_91097_(), this.f_109461_.m_91098_(), this.f_109461_.m_91385_(), $$0);
            $$1.m_110025_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
            RenderTarget $$2 = $$1.m_110036_("translucent");
            RenderTarget $$3 = $$1.m_110036_("itemEntity");
            RenderTarget $$4 = $$1.m_110036_("particles");
            RenderTarget $$5 = $$1.m_110036_("weather");
            RenderTarget $$6 = $$1.m_110036_("clouds");
            this.f_109418_ = $$1;
            this.f_109413_ = $$2;
            this.f_109414_ = $$3;
            this.f_109415_ = $$4;
            this.f_109416_ = $$5;
            this.f_109417_ = $$6;
        }
        catch (Exception $$7) {
            String $$8 = $$7 instanceof JsonSyntaxException ? "parse" : "load";
            String $$9 = "Failed to " + $$8 + " shader: " + $$0;
            TransparencyShaderException $$10 = new TransparencyShaderException($$9, $$7);
            if (this.f_109461_.m_91099_().m_10523_().size() > 1) {
                Component $$11 = this.f_109461_.m_91098_().m_7536_().findFirst().map(p_234256_ -> Component.m_237113_(p_234256_.m_8017_())).orElse(null);
                this.f_109461_.f_91066_.m_232060_().m_231514_(GraphicsStatus.FANCY);
                this.f_109461_.m_91241_($$10, $$11);
            }
            CrashReport $$12 = this.f_109461_.m_91354_(new CrashReport($$9, $$10));
            this.f_109461_.f_91066_.m_232060_().m_231514_(GraphicsStatus.FANCY);
            this.f_109461_.f_91066_.m_92169_();
            f_109453_.error(LogUtils.FATAL_MARKER, $$9, (Throwable)$$10);
            this.f_109461_.m_91394_();
            Minecraft.m_91332_($$12);
        }
    }

    private void m_109834_() {
        if (this.f_109418_ != null) {
            this.f_109418_.close();
            this.f_109413_.m_83930_();
            this.f_109414_.m_83930_();
            this.f_109415_.m_83930_();
            this.f_109416_.m_83930_();
            this.f_109417_.m_83930_();
            this.f_109418_ = null;
            this.f_109413_ = null;
            this.f_109414_ = null;
            this.f_109415_ = null;
            this.f_109416_ = null;
            this.f_109417_ = null;
        }
    }

    public void m_109769_() {
        if (this.m_109817_()) {
            RenderSystem.m_69478_();
            RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            this.f_109411_.m_83957_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_(), false);
            RenderSystem.m_69461_();
        }
    }

    protected boolean m_109817_() {
        return !this.f_109461_.f_91063_.m_172715_() && this.f_109411_ != null && this.f_109412_ != null && this.f_109461_.f_91074_ != null;
    }

    private void m_109835_() {
        Tesselator $$0 = Tesselator.m_85913_();
        BufferBuilder $$1 = $$0.m_85915_();
        if (this.f_109473_ != null) {
            this.f_109473_.close();
        }
        this.f_109473_ = new VertexBuffer();
        BufferBuilder.RenderedBuffer $$2 = LevelRenderer.m_234267_($$1, -16.0f);
        this.f_109473_.m_85921_();
        this.f_109473_.m_231221_($$2);
        VertexBuffer.m_85931_();
    }

    private void m_109836_() {
        Tesselator $$0 = Tesselator.m_85913_();
        BufferBuilder $$1 = $$0.m_85915_();
        if (this.f_109472_ != null) {
            this.f_109472_.close();
        }
        this.f_109472_ = new VertexBuffer();
        BufferBuilder.RenderedBuffer $$2 = LevelRenderer.m_234267_($$1, 16.0f);
        this.f_109472_.m_85921_();
        this.f_109472_.m_231221_($$2);
        VertexBuffer.m_85931_();
    }

    private static BufferBuilder.RenderedBuffer m_234267_(BufferBuilder p_234268_, float p_234269_) {
        float $$2 = Math.signum(p_234269_) * 512.0f;
        float $$3 = 512.0f;
        RenderSystem.m_157427_(GameRenderer::m_172808_);
        p_234268_.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85814_);
        p_234268_.m_5483_(0.0, p_234269_, 0.0).m_5752_();
        for (int $$4 = -180; $$4 <= 180; $$4 += 45) {
            p_234268_.m_5483_($$2 * Mth.m_14089_((float)$$4 * ((float)Math.PI / 180)), p_234269_, 512.0f * Mth.m_14031_((float)$$4 * ((float)Math.PI / 180))).m_5752_();
        }
        return p_234268_.m_231175_();
    }

    private void m_109837_() {
        Tesselator $$0 = Tesselator.m_85913_();
        BufferBuilder $$1 = $$0.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172808_);
        if (this.f_109471_ != null) {
            this.f_109471_.close();
        }
        this.f_109471_ = new VertexBuffer();
        BufferBuilder.RenderedBuffer $$2 = this.m_234259_($$1);
        this.f_109471_.m_85921_();
        this.f_109471_.m_231221_($$2);
        VertexBuffer.m_85931_();
    }

    private BufferBuilder.RenderedBuffer m_234259_(BufferBuilder p_234260_) {
        RandomSource $$1 = RandomSource.m_216335_(10842L);
        p_234260_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        for (int $$2 = 0; $$2 < 1500; ++$$2) {
            double $$3 = $$1.m_188501_() * 2.0f - 1.0f;
            double $$4 = $$1.m_188501_() * 2.0f - 1.0f;
            double $$5 = $$1.m_188501_() * 2.0f - 1.0f;
            double $$6 = 0.15f + $$1.m_188501_() * 0.1f;
            double $$7 = $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
            if (!($$7 < 1.0) || !($$7 > 0.01)) continue;
            $$7 = 1.0 / Math.sqrt($$7);
            double $$8 = ($$3 *= $$7) * 100.0;
            double $$9 = ($$4 *= $$7) * 100.0;
            double $$10 = ($$5 *= $$7) * 100.0;
            double $$11 = Math.atan2($$3, $$5);
            double $$12 = Math.sin($$11);
            double $$13 = Math.cos($$11);
            double $$14 = Math.atan2(Math.sqrt($$3 * $$3 + $$5 * $$5), $$4);
            double $$15 = Math.sin($$14);
            double $$16 = Math.cos($$14);
            double $$17 = $$1.m_188500_() * Math.PI * 2.0;
            double $$18 = Math.sin($$17);
            double $$19 = Math.cos($$17);
            for (int $$20 = 0; $$20 < 4; ++$$20) {
                double $$26;
                double $$21 = 0.0;
                double $$22 = (double)(($$20 & 2) - 1) * $$6;
                double $$23 = (double)(($$20 + 1 & 2) - 1) * $$6;
                double $$24 = 0.0;
                double $$25 = $$22 * $$19 - $$23 * $$18;
                double $$27 = $$26 = $$23 * $$19 + $$22 * $$18;
                double $$28 = $$25 * $$15 + 0.0 * $$16;
                double $$29 = 0.0 * $$15 - $$25 * $$16;
                double $$30 = $$29 * $$12 - $$27 * $$13;
                double $$31 = $$28;
                double $$32 = $$27 * $$12 + $$29 * $$13;
                p_234260_.m_5483_($$8 + $$30, $$9 + $$31, $$10 + $$32).m_5752_();
            }
        }
        return p_234260_.m_231175_();
    }

    public void m_109701_(@Nullable ClientLevel p_109702_) {
        this.f_109419_ = Double.MIN_VALUE;
        this.f_109420_ = Double.MIN_VALUE;
        this.f_109421_ = Double.MIN_VALUE;
        this.f_109422_ = Integer.MIN_VALUE;
        this.f_109423_ = Integer.MIN_VALUE;
        this.f_109424_ = Integer.MIN_VALUE;
        this.f_109463_.m_114406_(p_109702_);
        this.f_109465_ = p_109702_;
        if (p_109702_ != null) {
            this.m_109818_();
        } else {
            if (this.f_109469_ != null) {
                this.f_109469_.m_110849_();
                this.f_109469_ = null;
            }
            if (this.f_109436_ != null) {
                this.f_109436_.m_112733_();
            }
            this.f_109436_ = null;
            this.f_109468_.clear();
            this.f_194307_.set(null);
            this.f_194297_.clear();
        }
    }

    public void m_173014_() {
        if (Minecraft.m_91085_()) {
            this.m_109833_();
        } else {
            this.m_109834_();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_109818_() {
        if (this.f_109465_ == null) {
            return;
        }
        this.m_173014_();
        this.f_109465_.m_104810_();
        if (this.f_109436_ == null) {
            this.f_109436_ = new ChunkRenderDispatcher(this.f_109465_, this, Util.m_183991_(), this.f_109461_.m_91103_(), this.f_109464_.m_110098_());
        } else {
            this.f_109436_.m_194410_(this.f_109465_);
        }
        this.f_194300_ = true;
        this.f_109474_ = true;
        this.f_194306_.clear();
        ItemBlockRenderTypes.m_109291_(Minecraft.m_91405_());
        this.f_109438_ = this.f_109461_.f_91066_.m_193772_();
        if (this.f_109469_ != null) {
            this.f_109469_.m_110849_();
        }
        this.f_109436_.m_112731_();
        Set<BlockEntity> set = this.f_109468_;
        synchronized (set) {
            this.f_109468_.clear();
        }
        this.f_109469_ = new ViewArea(this.f_109436_, this.f_109465_, this.f_109461_.f_91066_.m_193772_(), this);
        if (this.f_194298_ != null) {
            try {
                this.f_194298_.get();
                this.f_194298_ = null;
            }
            catch (Exception $$0) {
                f_109453_.warn("Full update failed", (Throwable)$$0);
            }
        }
        this.f_194307_.set(new RenderChunkStorage(this.f_109469_.f_110843_.length));
        this.f_194297_.clear();
        Entity $$1 = this.f_109461_.m_91288_();
        if ($$1 != null) {
            this.f_109469_.m_110850_($$1.m_20185_(), $$1.m_20189_());
        }
    }

    public void m_109487_(int p_109488_, int p_109489_) {
        this.m_109826_();
        if (this.f_109412_ != null) {
            this.f_109412_.m_110025_(p_109488_, p_109489_);
        }
        if (this.f_109418_ != null) {
            this.f_109418_.m_110025_(p_109488_, p_109489_);
        }
    }

    public String m_109820_() {
        int $$0 = this.f_109469_.f_110843_.length;
        int $$1 = this.m_109821_();
        return String.format(Locale.ROOT, "C: %d/%d %sD: %d, %s", $$1, $$0, this.f_109461_.f_90980_ ? "(s) " : "", this.f_109438_, this.f_109436_ == null ? "null" : this.f_109436_.m_112719_());
    }

    public ChunkRenderDispatcher m_173015_() {
        return this.f_109436_;
    }

    public double m_173016_() {
        return this.f_109469_.f_110843_.length;
    }

    public double m_173017_() {
        return this.f_109438_;
    }

    public int m_109821_() {
        int $$0 = 0;
        for (RenderChunkInfo $$1 : this.f_194297_) {
            if ($$1.f_109839_.m_112835_().m_112757_()) continue;
            ++$$0;
        }
        return $$0;
    }

    public String m_109822_() {
        return "E: " + this.f_109439_ + "/" + this.f_109465_.m_104813_() + ", B: " + this.f_109440_ + ", SD: " + this.f_109465_.m_194186_();
    }

    private void m_194338_(Camera p_194339_, Frustum p_194340_, boolean p_194341_, boolean p_194342_) {
        Vec3 $$4 = p_194339_.m_90583_();
        if (this.f_109461_.f_91066_.m_193772_() != this.f_109438_) {
            this.m_109818_();
        }
        this.f_109465_.m_46473_().m_6180_("camera");
        double $$5 = this.f_109461_.f_91074_.m_20185_();
        double $$6 = this.f_109461_.f_91074_.m_20186_();
        double $$7 = this.f_109461_.f_91074_.m_20189_();
        int $$8 = SectionPos.m_175552_($$5);
        int $$9 = SectionPos.m_175552_($$6);
        int $$10 = SectionPos.m_175552_($$7);
        if (this.f_109422_ != $$8 || this.f_109423_ != $$9 || this.f_109424_ != $$10) {
            this.f_109419_ = $$5;
            this.f_109420_ = $$6;
            this.f_109421_ = $$7;
            this.f_109422_ = $$8;
            this.f_109423_ = $$9;
            this.f_109424_ = $$10;
            this.f_109469_.m_110850_($$5, $$7);
        }
        this.f_109436_.m_112693_($$4);
        this.f_109465_.m_46473_().m_6182_("cull");
        this.f_109461_.m_91307_().m_6182_("culling");
        BlockPos $$11 = p_194339_.m_90588_();
        double $$12 = Math.floor($$4.f_82479_ / 8.0);
        double $$13 = Math.floor($$4.f_82480_ / 8.0);
        double $$14 = Math.floor($$4.f_82481_ / 8.0);
        this.f_194300_ = this.f_194300_ || $$12 != this.f_109425_ || $$13 != this.f_109426_ || $$14 != this.f_109427_;
        this.f_194301_.updateAndGet(p_234309_ -> {
            if (p_234309_ > 0L && System.currentTimeMillis() > p_234309_) {
                this.f_194300_ = true;
                return 0L;
            }
            return p_234309_;
        });
        this.f_109425_ = $$12;
        this.f_109426_ = $$13;
        this.f_109427_ = $$14;
        this.f_109461_.m_91307_().m_6182_("update");
        boolean $$15 = this.f_109461_.f_90980_;
        if (p_194342_ && this.f_109465_.m_8055_($$11).m_60804_(this.f_109465_, $$11)) {
            $$15 = false;
        }
        if (!p_194341_) {
            if (this.f_194300_ && (this.f_194298_ == null || this.f_194298_.isDone())) {
                this.f_109461_.m_91307_().m_6180_("full_update_schedule");
                this.f_194300_ = false;
                boolean $$16 = $$15;
                this.f_194298_ = Util.m_183991_().submit(() -> {
                    ArrayDeque $$3 = Queues.newArrayDeque();
                    this.m_194343_(p_194339_, $$3);
                    RenderChunkStorage $$4 = new RenderChunkStorage(this.f_109469_.f_110843_.length);
                    this.m_194362_($$4.f_194376_, $$4.f_194375_, $$4, $$3, $$16);
                    this.f_194307_.set($$4);
                    this.f_194299_.set(true);
                });
                this.f_109461_.m_91307_().m_7238_();
            }
            RenderChunkStorage $$17 = this.f_194307_.get();
            if (!this.f_194306_.isEmpty()) {
                this.f_109461_.m_91307_().m_6180_("partial_update");
                ArrayDeque $$18 = Queues.newArrayDeque();
                while (!this.f_194306_.isEmpty()) {
                    ChunkRenderDispatcher.RenderChunk $$19 = (ChunkRenderDispatcher.RenderChunk)this.f_194306_.poll();
                    RenderChunkInfo $$20 = $$17.f_194375_.m_173035_($$19);
                    if ($$20 == null || $$20.f_109839_ != $$19) continue;
                    $$18.add($$20);
                }
                this.m_194362_($$17.f_194376_, $$17.f_194375_, $$4, $$18, $$15);
                this.f_194299_.set(true);
                this.f_109461_.m_91307_().m_7238_();
            }
            double $$21 = Math.floor(p_194339_.m_90589_() / 2.0f);
            double $$22 = Math.floor(p_194339_.m_90590_() / 2.0f);
            if (this.f_194299_.compareAndSet(true, false) || $$21 != this.f_109428_ || $$22 != this.f_109429_) {
                this.m_194354_(new Frustum(p_194340_).m_194441_(8));
                this.f_109428_ = $$21;
                this.f_109429_ = $$22;
            }
        }
        this.f_109461_.m_91307_().m_7238_();
    }

    private void m_194354_(Frustum p_194355_) {
        if (!Minecraft.m_91087_().m_18695_()) {
            throw new IllegalStateException("applyFrustum called from wrong thread: " + Thread.currentThread().getName());
        }
        this.f_109461_.m_91307_().m_6180_("apply_frustum");
        this.f_194297_.clear();
        for (RenderChunkInfo $$1 : this.f_194307_.get().f_194376_) {
            if (!p_194355_.m_113029_($$1.f_109839_.m_202440_())) continue;
            this.f_194297_.add((Object)$$1);
        }
        this.f_109461_.m_91307_().m_7238_();
    }

    private void m_194343_(Camera p_194344_, Queue<RenderChunkInfo> p_194345_) {
        int $$2 = 16;
        Vec3 $$3 = p_194344_.m_90583_();
        BlockPos $$4 = p_194344_.m_90588_();
        ChunkRenderDispatcher.RenderChunk $$5 = this.f_109469_.m_110866_($$4);
        if ($$5 == null) {
            boolean $$6 = $$4.m_123342_() > this.f_109465_.m_141937_();
            int $$7 = $$6 ? this.f_109465_.m_151558_() - 8 : this.f_109465_.m_141937_() + 8;
            int $$8 = Mth.m_14107_($$3.f_82479_ / 16.0) * 16;
            int $$9 = Mth.m_14107_($$3.f_82481_ / 16.0) * 16;
            ArrayList $$10 = Lists.newArrayList();
            for (int $$11 = -this.f_109438_; $$11 <= this.f_109438_; ++$$11) {
                for (int $$12 = -this.f_109438_; $$12 <= this.f_109438_; ++$$12) {
                    ChunkRenderDispatcher.RenderChunk $$13 = this.f_109469_.m_110866_(new BlockPos($$8 + SectionPos.m_175554_($$11, 8), $$7, $$9 + SectionPos.m_175554_($$12, 8)));
                    if ($$13 == null) continue;
                    $$10.add(new RenderChunkInfo($$13, null, 0));
                }
            }
            $$10.sort(Comparator.comparingDouble(p_234303_ -> $$4.m_123331_(p_234303_.f_109839_.m_112839_().m_7918_(8, 8, 8))));
            p_194345_.addAll($$10);
        } else {
            p_194345_.add(new RenderChunkInfo($$5, null, 0));
        }
    }

    public void m_194352_(ChunkRenderDispatcher.RenderChunk p_194353_) {
        this.f_194306_.add(p_194353_);
    }

    private void m_194362_(LinkedHashSet<RenderChunkInfo> p_194363_, RenderInfoMap p_194364_, Vec3 p_194365_, Queue<RenderChunkInfo> p_194366_, boolean p_194367_) {
        int $$5 = 16;
        BlockPos $$6 = new BlockPos(Mth.m_14107_(p_194365_.f_82479_ / 16.0) * 16, Mth.m_14107_(p_194365_.f_82480_ / 16.0) * 16, Mth.m_14107_(p_194365_.f_82481_ / 16.0) * 16);
        BlockPos $$7 = $$6.m_7918_(8, 8, 8);
        Entity.m_20103_(Mth.m_14008_((double)this.f_109461_.f_91066_.m_193772_() / 8.0, 1.0, 2.5) * this.f_109461_.f_91066_.m_232018_().m_231551_());
        while (!p_194366_.isEmpty()) {
            RenderChunkInfo $$8 = p_194366_.poll();
            ChunkRenderDispatcher.RenderChunk $$9 = $$8.f_109839_;
            p_194363_.add($$8);
            boolean $$10 = Math.abs($$9.m_112839_().m_123341_() - $$6.m_123341_()) > 60 || Math.abs($$9.m_112839_().m_123342_() - $$6.m_123342_()) > 60 || Math.abs($$9.m_112839_().m_123343_() - $$6.m_123343_()) > 60;
            for (Direction $$11 : f_109434_) {
                RenderChunkInfo $$22;
                ChunkRenderDispatcher.RenderChunk $$12 = this.m_109728_($$6, $$9, $$11);
                if ($$12 == null || p_194367_ && $$8.m_109859_($$11.m_122424_())) continue;
                if (p_194367_ && $$8.m_173025_()) {
                    ChunkRenderDispatcher.CompiledChunk $$13 = $$9.m_112835_();
                    boolean $$14 = false;
                    for (int $$15 = 0; $$15 < f_109434_.length; ++$$15) {
                        if (!$$8.m_173026_($$15) || !$$13.m_7259_(f_109434_[$$15].m_122424_(), $$11)) continue;
                        $$14 = true;
                        break;
                    }
                    if (!$$14) continue;
                }
                if (p_194367_ && $$10) {
                    BlockPos $$16 = $$12.m_112839_();
                    BlockPos $$17 = $$16.m_7918_(($$11.m_122434_() == Direction.Axis.X ? $$7.m_123341_() > $$16.m_123341_() : $$7.m_123341_() < $$16.m_123341_()) ? 16 : 0, ($$11.m_122434_() == Direction.Axis.Y ? $$7.m_123342_() > $$16.m_123342_() : $$7.m_123342_() < $$16.m_123342_()) ? 16 : 0, ($$11.m_122434_() == Direction.Axis.Z ? $$7.m_123343_() > $$16.m_123343_() : $$7.m_123343_() < $$16.m_123343_()) ? 16 : 0);
                    Vec3 $$18 = new Vec3($$17.m_123341_(), $$17.m_123342_(), $$17.m_123343_());
                    Vec3 $$19 = p_194365_.m_82546_($$18).m_82541_().m_82490_(f_194304_);
                    boolean $$20 = true;
                    while (p_194365_.m_82546_($$18).m_82556_() > 3600.0) {
                        $$18 = $$18.m_82549_($$19);
                        if ($$18.f_82480_ > (double)this.f_109465_.m_151558_() || $$18.f_82480_ < (double)this.f_109465_.m_141937_()) break;
                        ChunkRenderDispatcher.RenderChunk $$21 = this.f_109469_.m_110866_(new BlockPos($$18.f_82479_, $$18.f_82480_, $$18.f_82481_));
                        if ($$21 != null && p_194364_.m_173035_($$21) != null) continue;
                        $$20 = false;
                        break;
                    }
                    if (!$$20) continue;
                }
                if (($$22 = p_194364_.m_173035_($$12)) != null) {
                    $$22.m_173028_($$11);
                    continue;
                }
                if (!$$12.m_112798_()) {
                    if (this.m_194359_($$6, $$9)) continue;
                    this.f_194301_.set(System.currentTimeMillis() + 500L);
                    continue;
                }
                RenderChunkInfo $$23 = new RenderChunkInfo($$12, $$11, $$8.f_109842_ + 1);
                $$23.m_109854_($$8.f_109841_, $$11);
                p_194366_.add($$23);
                p_194364_.m_173037_($$12, $$23);
            }
        }
    }

    @Nullable
    private ChunkRenderDispatcher.RenderChunk m_109728_(BlockPos p_109729_, ChunkRenderDispatcher.RenderChunk p_109730_, Direction p_109731_) {
        BlockPos $$3 = p_109730_.m_112824_(p_109731_);
        if (Mth.m_14040_(p_109729_.m_123341_() - $$3.m_123341_()) > this.f_109438_ * 16) {
            return null;
        }
        if (Mth.m_14040_(p_109729_.m_123342_() - $$3.m_123342_()) > this.f_109438_ * 16 || $$3.m_123342_() < this.f_109465_.m_141937_() || $$3.m_123342_() >= this.f_109465_.m_151558_()) {
            return null;
        }
        if (Mth.m_14040_(p_109729_.m_123343_() - $$3.m_123343_()) > this.f_109438_ * 16) {
            return null;
        }
        return this.f_109469_.m_110866_($$3);
    }

    private boolean m_194359_(BlockPos p_194360_, ChunkRenderDispatcher.RenderChunk p_194361_) {
        int $$6;
        int $$2 = SectionPos.m_123171_(p_194360_.m_123341_());
        int $$3 = SectionPos.m_123171_(p_194360_.m_123343_());
        BlockPos $$4 = p_194361_.m_112839_();
        int $$5 = SectionPos.m_123171_($$4.m_123341_());
        return !ChunkMap.m_200878_($$5, $$6 = SectionPos.m_123171_($$4.m_123343_()), $$2, $$3, this.f_109438_ - 2);
    }

    private void m_109525_(Matrix4f p_109526_, Matrix4f p_109527_, double p_109528_, double p_109529_, double p_109530_, Frustum p_109531_) {
        this.f_109442_ = p_109531_;
        Matrix4f $$6 = p_109527_.m_27658_();
        $$6.m_27644_(p_109526_);
        $$6.m_27657_();
        this.f_109444_.f_86214_ = p_109528_;
        this.f_109444_.f_86215_ = p_109529_;
        this.f_109444_.f_86216_ = p_109530_;
        this.f_109443_[0] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.f_109443_[1] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.f_109443_[2] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.f_109443_[3] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.f_109443_[4] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.f_109443_[5] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.f_109443_[6] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.f_109443_[7] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        for (int $$7 = 0; $$7 < 8; ++$$7) {
            this.f_109443_[$$7].m_123607_($$6);
            this.f_109443_[$$7].m_123621_();
        }
    }

    public void m_172961_(PoseStack p_172962_, Vec3 p_172963_, Matrix4f p_172964_) {
        Matrix4f $$3 = p_172962_.m_85850_().m_85861_();
        double $$4 = p_172963_.m_7096_();
        double $$5 = p_172963_.m_7098_();
        double $$6 = p_172963_.m_7094_();
        this.f_172938_ = new Frustum($$3, p_172964_);
        this.f_172938_.m_113002_($$4, $$5, $$6);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_109599_(PoseStack p_109600_, float p_109601_, long p_109602_, boolean p_109603_, Camera p_109604_, GameRenderer p_109605_, LightTexture p_109606_, Matrix4f p_109607_) {
        Frustum $$17;
        boolean $$15;
        RenderSystem.m_157447_(this.f_109465_.m_46467_(), p_109601_);
        this.f_172946_.m_173564_(this.f_109465_, p_109604_, this.f_109461_.f_91077_);
        this.f_109463_.m_114408_(this.f_109465_, p_109604_, this.f_109461_.f_91076_);
        ProfilerFiller $$8 = this.f_109465_.m_46473_();
        $$8.m_6182_("light_update_queue");
        this.f_109465_.m_194141_();
        $$8.m_6182_("light_updates");
        boolean $$9 = this.f_109465_.m_194173_();
        this.f_109465_.m_7726_().m_7827_().m_5738_(Integer.MAX_VALUE, $$9, true);
        Vec3 $$10 = p_109604_.m_90583_();
        double $$11 = $$10.m_7096_();
        double $$12 = $$10.m_7098_();
        double $$13 = $$10.m_7094_();
        Matrix4f $$14 = p_109600_.m_85850_().m_85861_();
        $$8.m_6182_("culling");
        boolean bl = $$15 = this.f_109442_ != null;
        if ($$15) {
            Frustum $$16 = this.f_109442_;
            $$16.m_113002_(this.f_109444_.f_86214_, this.f_109444_.f_86215_, this.f_109444_.f_86216_);
        } else {
            $$17 = this.f_172938_;
        }
        this.f_109461_.m_91307_().m_6182_("captureFrustum");
        if (this.f_109441_) {
            this.m_109525_($$14, p_109607_, $$10.f_82479_, $$10.f_82480_, $$10.f_82481_, $$15 ? new Frustum($$14, p_109607_) : $$17);
            this.f_109441_ = false;
        }
        $$8.m_6182_("clear");
        FogRenderer.m_109018_(p_109604_, p_109601_, this.f_109461_.f_91073_, this.f_109461_.f_91066_.m_193772_(), p_109605_.m_109131_(p_109601_));
        FogRenderer.m_109036_();
        RenderSystem.m_69421_(16640, Minecraft.f_91002_);
        float $$18 = p_109605_.m_109152_();
        boolean $$19 = this.f_109461_.f_91073_.m_104583_().m_5781_(Mth.m_14107_($$11), Mth.m_14107_($$12)) || this.f_109461_.f_91065_.m_93090_().m_93715_();
        $$8.m_6182_("sky");
        RenderSystem.m_157427_(GameRenderer::m_172808_);
        this.m_202423_(p_109600_, p_109607_, p_109601_, p_109604_, $$19, () -> FogRenderer.m_234172_(p_109604_, FogRenderer.FogMode.FOG_SKY, $$18, $$19, p_109601_));
        $$8.m_6182_("fog");
        FogRenderer.m_234172_(p_109604_, FogRenderer.FogMode.FOG_TERRAIN, Math.max($$18, 32.0f), $$19, p_109601_);
        $$8.m_6182_("terrain_setup");
        this.m_194338_(p_109604_, $$17, $$15, this.f_109461_.f_91074_.m_5833_());
        $$8.m_6182_("compilechunks");
        this.m_194370_(p_109604_);
        $$8.m_6182_("terrain");
        this.m_172993_(RenderType.m_110451_(), p_109600_, $$11, $$12, $$13, p_109607_);
        this.m_172993_(RenderType.m_110457_(), p_109600_, $$11, $$12, $$13, p_109607_);
        this.m_172993_(RenderType.m_110463_(), p_109600_, $$11, $$12, $$13, p_109607_);
        if (this.f_109465_.m_104583_().m_108885_()) {
            Lighting.m_84925_(p_109600_.m_85850_().m_85861_());
        } else {
            Lighting.m_84928_(p_109600_.m_85850_().m_85861_());
        }
        $$8.m_6182_("entities");
        this.f_109439_ = 0;
        this.f_109440_ = 0;
        if (this.f_109414_ != null) {
            this.f_109414_.m_83954_(Minecraft.f_91002_);
            this.f_109414_.m_83945_(this.f_109461_.m_91385_());
            this.f_109461_.m_91385_().m_83947_(false);
        }
        if (this.f_109416_ != null) {
            this.f_109416_.m_83954_(Minecraft.f_91002_);
        }
        if (this.m_109817_()) {
            this.f_109411_.m_83954_(Minecraft.f_91002_);
            this.f_109461_.m_91385_().m_83947_(false);
        }
        boolean $$20 = false;
        MultiBufferSource.BufferSource $$21 = this.f_109464_.m_110104_();
        for (Entity $$22 : this.f_109465_.m_104735_()) {
            MultiBufferSource.BufferSource $$31;
            BlockPos $$23;
            if (!this.f_109463_.m_114397_($$22, $$17, $$11, $$12, $$13) && !$$22.m_20367_(this.f_109461_.f_91074_) || !this.f_109465_.m_151562_(($$23 = $$22.m_20183_()).m_123342_()) && !this.m_202430_($$23) || $$22 == p_109604_.m_90592_() && !p_109604_.m_90594_() && (!(p_109604_.m_90592_() instanceof LivingEntity) || !((LivingEntity)p_109604_.m_90592_()).m_5803_()) || $$22 instanceof LocalPlayer && p_109604_.m_90592_() != $$22) continue;
            ++this.f_109439_;
            if ($$22.f_19797_ == 0) {
                $$22.f_19790_ = $$22.m_20185_();
                $$22.f_19791_ = $$22.m_20186_();
                $$22.f_19792_ = $$22.m_20189_();
            }
            if (this.m_109817_() && this.f_109461_.m_91314_($$22)) {
                OutlineBufferSource $$24;
                $$20 = true;
                OutlineBufferSource $$25 = $$24 = this.f_109464_.m_110109_();
                int $$26 = $$22.m_19876_();
                int $$27 = 255;
                int $$28 = $$26 >> 16 & 0xFF;
                int $$29 = $$26 >> 8 & 0xFF;
                int $$30 = $$26 & 0xFF;
                $$24.m_109929_($$28, $$29, $$30, 255);
            } else {
                $$31 = $$21;
            }
            this.m_109517_($$22, $$11, $$12, $$13, p_109601_, p_109600_, $$31);
        }
        $$21.m_173043_();
        this.m_109588_(p_109600_);
        $$21.m_109912_(RenderType.m_110446_(TextureAtlas.f_118259_));
        $$21.m_109912_(RenderType.m_110452_(TextureAtlas.f_118259_));
        $$21.m_109912_(RenderType.m_110458_(TextureAtlas.f_118259_));
        $$21.m_109912_(RenderType.m_110476_(TextureAtlas.f_118259_));
        $$8.m_6182_("blockentities");
        for (Object $$32 : this.f_194297_) {
            List<BlockEntity> $$33 = ((RenderChunkInfo)$$32).f_109839_.m_112835_().m_112773_();
            if ($$33.isEmpty()) continue;
            for (BlockEntity $$34 : $$33) {
                int $$38;
                BlockPos $$35 = $$34.m_58899_();
                MultiBufferSource $$36 = $$21;
                p_109600_.m_85836_();
                p_109600_.m_85837_((double)$$35.m_123341_() - $$11, (double)$$35.m_123342_() - $$12, (double)$$35.m_123343_() - $$13);
                SortedSet $$37 = (SortedSet)this.f_109409_.get($$35.m_121878_());
                if ($$37 != null && !$$37.isEmpty() && ($$38 = ((BlockDestructionProgress)$$37.last()).m_139988_()) >= 0) {
                    PoseStack.Pose $$39 = p_109600_.m_85850_();
                    SheetedDecalTextureGenerator $$40 = new SheetedDecalTextureGenerator(this.f_109464_.m_110108_().m_6299_(ModelBakery.f_119229_.get($$38)), $$39.m_85861_(), $$39.m_85864_());
                    $$36 = p_234298_ -> {
                        VertexConsumer $$3 = $$21.m_6299_(p_234298_);
                        if (p_234298_.m_110405_()) {
                            return VertexMultiConsumer.m_86168_($$40, $$3);
                        }
                        return $$3;
                    };
                }
                this.f_172946_.m_112267_($$34, p_109601_, p_109600_, $$36);
                p_109600_.m_85849_();
            }
        }
        Set<BlockEntity> set = this.f_109468_;
        synchronized (set) {
            for (BlockEntity $$41 : this.f_109468_) {
                BlockPos $$42 = $$41.m_58899_();
                p_109600_.m_85836_();
                p_109600_.m_85837_((double)$$42.m_123341_() - $$11, (double)$$42.m_123342_() - $$12, (double)$$42.m_123343_() - $$13);
                this.f_172946_.m_112267_($$41, p_109601_, p_109600_, $$21);
                p_109600_.m_85849_();
            }
        }
        this.m_109588_(p_109600_);
        $$21.m_109912_(RenderType.m_110451_());
        $$21.m_109912_(RenderType.m_173239_());
        $$21.m_109912_(RenderType.m_173242_());
        $$21.m_109912_(Sheets.m_110789_());
        $$21.m_109912_(Sheets.m_110790_());
        $$21.m_109912_(Sheets.m_110785_());
        $$21.m_109912_(Sheets.m_110786_());
        $$21.m_109912_(Sheets.m_110787_());
        $$21.m_109912_(Sheets.m_110788_());
        this.f_109464_.m_110109_().m_109928_();
        if ($$20) {
            this.f_109412_.m_110023_(p_109601_);
            this.f_109461_.m_91385_().m_83947_(false);
        }
        $$8.m_6182_("destroyProgress");
        for (Long2ObjectMap.Entry $$43 : this.f_109409_.long2ObjectEntrySet()) {
            SortedSet $$48;
            double $$47;
            double $$46;
            BlockPos $$44 = BlockPos.m_122022_($$43.getLongKey());
            double $$45 = (double)$$44.m_123341_() - $$11;
            if ($$45 * $$45 + ($$46 = (double)$$44.m_123342_() - $$12) * $$46 + ($$47 = (double)$$44.m_123343_() - $$13) * $$47 > 1024.0 || ($$48 = (SortedSet)$$43.getValue()) == null || $$48.isEmpty()) continue;
            int $$49 = ((BlockDestructionProgress)$$48.last()).m_139988_();
            p_109600_.m_85836_();
            p_109600_.m_85837_((double)$$44.m_123341_() - $$11, (double)$$44.m_123342_() - $$12, (double)$$44.m_123343_() - $$13);
            PoseStack.Pose $$50 = p_109600_.m_85850_();
            SheetedDecalTextureGenerator $$51 = new SheetedDecalTextureGenerator(this.f_109464_.m_110108_().m_6299_(ModelBakery.f_119229_.get($$49)), $$50.m_85861_(), $$50.m_85864_());
            this.f_109461_.m_91289_().m_110918_(this.f_109465_.m_8055_($$44), $$44, this.f_109465_, p_109600_, $$51);
            p_109600_.m_85849_();
        }
        this.m_109588_(p_109600_);
        HitResult $$52 = this.f_109461_.f_91077_;
        if (p_109603_ && $$52 != null && $$52.m_6662_() == HitResult.Type.BLOCK) {
            $$8.m_6182_("outline");
            BlockPos $$53 = ((BlockHitResult)$$52).m_82425_();
            BlockState $$54 = this.f_109465_.m_8055_($$53);
            if (!$$54.m_60795_() && this.f_109465_.m_6857_().m_61937_($$53)) {
                VertexConsumer $$55 = $$21.m_6299_(RenderType.m_110504_());
                this.m_109637_(p_109600_, $$55, p_109604_.m_90592_(), $$11, $$12, $$13, $$53, $$54);
            }
        }
        PoseStack $$56 = RenderSystem.m_157191_();
        $$56.m_85836_();
        $$56.m_166854_(p_109600_.m_85850_().m_85861_());
        RenderSystem.m_157182_();
        this.f_109461_.f_91064_.m_113457_(p_109600_, $$21, $$11, $$12, $$13);
        $$56.m_85849_();
        RenderSystem.m_157182_();
        $$21.m_109912_(Sheets.m_110792_());
        $$21.m_109912_(Sheets.m_110762_());
        $$21.m_109912_(Sheets.m_110782_());
        $$21.m_109912_(RenderType.m_110481_());
        $$21.m_109912_(RenderType.m_110484_());
        $$21.m_109912_(RenderType.m_110490_());
        $$21.m_109912_(RenderType.m_110493_());
        $$21.m_109912_(RenderType.m_110487_());
        $$21.m_109912_(RenderType.m_110496_());
        $$21.m_109912_(RenderType.m_110499_());
        $$21.m_109912_(RenderType.m_110478_());
        this.f_109464_.m_110108_().m_109911_();
        if (this.f_109418_ != null) {
            $$21.m_109912_(RenderType.m_110504_());
            $$21.m_109911_();
            this.f_109413_.m_83954_(Minecraft.f_91002_);
            this.f_109413_.m_83945_(this.f_109461_.m_91385_());
            $$8.m_6182_("translucent");
            this.m_172993_(RenderType.m_110466_(), p_109600_, $$11, $$12, $$13, p_109607_);
            $$8.m_6182_("string");
            this.m_172993_(RenderType.m_110503_(), p_109600_, $$11, $$12, $$13, p_109607_);
            this.f_109415_.m_83954_(Minecraft.f_91002_);
            this.f_109415_.m_83945_(this.f_109461_.m_91385_());
            RenderStateShard.f_110126_.m_110185_();
            $$8.m_6182_("particles");
            this.f_109461_.f_91061_.m_107336_(p_109600_, $$21, p_109606_, p_109604_, p_109601_);
            RenderStateShard.f_110126_.m_110188_();
        } else {
            $$8.m_6182_("translucent");
            if (this.f_109413_ != null) {
                this.f_109413_.m_83954_(Minecraft.f_91002_);
            }
            this.m_172993_(RenderType.m_110466_(), p_109600_, $$11, $$12, $$13, p_109607_);
            $$21.m_109912_(RenderType.m_110504_());
            $$21.m_109911_();
            $$8.m_6182_("string");
            this.m_172993_(RenderType.m_110503_(), p_109600_, $$11, $$12, $$13, p_109607_);
            $$8.m_6182_("particles");
            this.f_109461_.f_91061_.m_107336_(p_109600_, $$21, p_109606_, p_109604_, p_109601_);
        }
        $$56.m_85836_();
        $$56.m_166854_(p_109600_.m_85850_().m_85861_());
        RenderSystem.m_157182_();
        if (this.f_109461_.f_91066_.m_92174_() != CloudStatus.OFF) {
            if (this.f_109418_ != null) {
                this.f_109417_.m_83954_(Minecraft.f_91002_);
                RenderStateShard.f_110128_.m_110185_();
                $$8.m_6182_("clouds");
                this.m_172954_(p_109600_, p_109607_, p_109601_, $$11, $$12, $$13);
                RenderStateShard.f_110128_.m_110188_();
            } else {
                $$8.m_6182_("clouds");
                RenderSystem.m_157427_(GameRenderer::m_172838_);
                this.m_172954_(p_109600_, p_109607_, p_109601_, $$11, $$12, $$13);
            }
        }
        if (this.f_109418_ != null) {
            RenderStateShard.f_110127_.m_110185_();
            $$8.m_6182_("weather");
            this.m_109703_(p_109606_, p_109601_, $$11, $$12, $$13);
            this.m_173012_(p_109604_);
            RenderStateShard.f_110127_.m_110188_();
            this.f_109418_.m_110023_(p_109601_);
            this.f_109461_.m_91385_().m_83947_(false);
        } else {
            RenderSystem.m_69458_(false);
            $$8.m_6182_("weather");
            this.m_109703_(p_109606_, p_109601_, $$11, $$12, $$13);
            this.m_173012_(p_109604_);
            RenderSystem.m_69458_(true);
        }
        this.m_109793_(p_109604_);
        RenderSystem.m_69458_(true);
        RenderSystem.m_69461_();
        $$56.m_85849_();
        RenderSystem.m_157182_();
        FogRenderer.m_109017_();
    }

    private void m_109588_(PoseStack p_109589_) {
        if (!p_109589_.m_85851_()) {
            throw new IllegalStateException("Pose stack not empty");
        }
    }

    private void m_109517_(Entity p_109518_, double p_109519_, double p_109520_, double p_109521_, float p_109522_, PoseStack p_109523_, MultiBufferSource p_109524_) {
        double $$7 = Mth.m_14139_(p_109522_, p_109518_.f_19790_, p_109518_.m_20185_());
        double $$8 = Mth.m_14139_(p_109522_, p_109518_.f_19791_, p_109518_.m_20186_());
        double $$9 = Mth.m_14139_(p_109522_, p_109518_.f_19792_, p_109518_.m_20189_());
        float $$10 = Mth.m_14179_(p_109522_, p_109518_.f_19859_, p_109518_.m_146908_());
        this.f_109463_.m_114384_(p_109518_, $$7 - p_109519_, $$8 - p_109520_, $$9 - p_109521_, $$10, p_109522_, p_109523_, p_109524_, this.f_109463_.m_114394_(p_109518_, p_109522_));
    }

    private void m_172993_(RenderType p_172994_, PoseStack p_172995_, double p_172996_, double p_172997_, double p_172998_, Matrix4f p_172999_) {
        RenderSystem.m_187554_();
        p_172994_.m_110185_();
        if (p_172994_ == RenderType.m_110466_()) {
            this.f_109461_.m_91307_().m_6180_("translucent_sort");
            double $$6 = p_172996_ - this.f_109445_;
            double $$7 = p_172997_ - this.f_109446_;
            double $$8 = p_172998_ - this.f_109447_;
            if ($$6 * $$6 + $$7 * $$7 + $$8 * $$8 > 1.0) {
                this.f_109445_ = p_172996_;
                this.f_109446_ = p_172997_;
                this.f_109447_ = p_172998_;
                int $$9 = 0;
                for (RenderChunkInfo $$10 : this.f_194297_) {
                    if ($$9 >= 15 || !$$10.f_109839_.m_112809_(p_172994_, this.f_109436_)) continue;
                    ++$$9;
                }
            }
            this.f_109461_.m_91307_().m_7238_();
        }
        this.f_109461_.m_91307_().m_6180_("filterempty");
        this.f_109461_.m_91307_().m_6523_(() -> "render_" + p_172994_);
        boolean $$11 = p_172994_ != RenderType.m_110466_();
        ObjectListIterator $$12 = this.f_194297_.listIterator($$11 ? 0 : this.f_194297_.size());
        ShaderInstance $$13 = RenderSystem.m_157196_();
        for (int $$14 = 0; $$14 < 12; ++$$14) {
            int $$15 = RenderSystem.m_157203_($$14);
            $$13.m_173350_("Sampler" + $$14, $$15);
        }
        if ($$13.f_173308_ != null) {
            $$13.f_173308_.m_5679_(p_172995_.m_85850_().m_85861_());
        }
        if ($$13.f_173309_ != null) {
            $$13.f_173309_.m_5679_(p_172999_);
        }
        if ($$13.f_173312_ != null) {
            $$13.f_173312_.m_5941_(RenderSystem.m_157197_());
        }
        if ($$13.f_173315_ != null) {
            $$13.f_173315_.m_5985_(RenderSystem.m_157200_());
        }
        if ($$13.f_173316_ != null) {
            $$13.f_173316_.m_5985_(RenderSystem.m_157199_());
        }
        if ($$13.f_173317_ != null) {
            $$13.f_173317_.m_5941_(RenderSystem.m_157198_());
        }
        if ($$13.f_202432_ != null) {
            $$13.f_202432_.m_142617_(RenderSystem.m_202041_().m_202324_());
        }
        if ($$13.f_173310_ != null) {
            $$13.f_173310_.m_5679_(RenderSystem.m_157207_());
        }
        if ($$13.f_173319_ != null) {
            $$13.f_173319_.m_5985_(RenderSystem.m_157201_());
        }
        RenderSystem.m_157461_($$13);
        $$13.m_173363_();
        Uniform $$16 = $$13.f_173320_;
        while ($$11 ? $$12.hasNext() : $$12.hasPrevious()) {
            RenderChunkInfo $$17 = $$11 ? (RenderChunkInfo)$$12.next() : (RenderChunkInfo)$$12.previous();
            ChunkRenderDispatcher.RenderChunk $$18 = $$17.f_109839_;
            if ($$18.m_112835_().m_112758_(p_172994_)) continue;
            VertexBuffer $$19 = $$18.m_112807_(p_172994_);
            BlockPos $$20 = $$18.m_112839_();
            if ($$16 != null) {
                $$16.m_5889_((float)((double)$$20.m_123341_() - p_172996_), (float)((double)$$20.m_123342_() - p_172997_), (float)((double)$$20.m_123343_() - p_172998_));
                $$16.m_85633_();
            }
            $$19.m_85921_();
            $$19.m_166882_();
        }
        if ($$16 != null) {
            $$16.m_142276_(Vector3f.f_176763_);
        }
        $$13.m_173362_();
        VertexBuffer.m_85931_();
        this.f_109461_.m_91307_().m_7238_();
        p_172994_.m_110188_();
    }

    private void m_109793_(Camera p_109794_) {
        Tesselator $$1 = Tesselator.m_85913_();
        BufferBuilder $$2 = $$1.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        if (this.f_109461_.f_90978_ || this.f_109461_.f_90979_) {
            double $$3 = p_109794_.m_90583_().m_7096_();
            double $$4 = p_109794_.m_90583_().m_7098_();
            double $$5 = p_109794_.m_90583_().m_7094_();
            RenderSystem.m_69458_(true);
            RenderSystem.m_69464_();
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            RenderSystem.m_69472_();
            for (RenderChunkInfo $$6 : this.f_194297_) {
                ChunkRenderDispatcher.RenderChunk $$7 = $$6.f_109839_;
                BlockPos $$8 = $$7.m_112839_();
                PoseStack $$9 = RenderSystem.m_157191_();
                $$9.m_85836_();
                $$9.m_85837_((double)$$8.m_123341_() - $$3, (double)$$8.m_123342_() - $$4, (double)$$8.m_123343_() - $$5);
                RenderSystem.m_157182_();
                RenderSystem.m_157427_(GameRenderer::m_172757_);
                if (this.f_109461_.f_90978_) {
                    $$2.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
                    RenderSystem.m_69832_(5.0f);
                    int $$10 = $$6.f_109842_ == 0 ? 0 : Mth.m_14169_((float)$$6.f_109842_ / 50.0f, 0.9f, 0.9f);
                    int $$11 = $$10 >> 16 & 0xFF;
                    int $$12 = $$10 >> 8 & 0xFF;
                    int $$13 = $$10 & 0xFF;
                    for (int $$14 = 0; $$14 < f_109434_.length; ++$$14) {
                        if (!$$6.m_173026_($$14)) continue;
                        Direction $$15 = f_109434_[$$14];
                        $$2.m_5483_(8.0, 8.0, 8.0).m_6122_($$11, $$12, $$13, 255).m_5601_($$15.m_122429_(), $$15.m_122430_(), $$15.m_122431_()).m_5752_();
                        $$2.m_5483_(8 - 16 * $$15.m_122429_(), 8 - 16 * $$15.m_122430_(), 8 - 16 * $$15.m_122431_()).m_6122_($$11, $$12, $$13, 255).m_5601_($$15.m_122429_(), $$15.m_122430_(), $$15.m_122431_()).m_5752_();
                    }
                    $$1.m_85914_();
                    RenderSystem.m_69832_(1.0f);
                }
                if (this.f_109461_.f_90979_ && !$$7.m_112835_().m_112757_()) {
                    $$2.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
                    RenderSystem.m_157427_(GameRenderer::m_172757_);
                    RenderSystem.m_69832_(5.0f);
                    int $$16 = 0;
                    for (Direction $$17 : f_109434_) {
                        for (Direction $$18 : f_109434_) {
                            boolean $$19 = $$7.m_112835_().m_7259_($$17, $$18);
                            if ($$19) continue;
                            ++$$16;
                            $$2.m_5483_(8 + 8 * $$17.m_122429_(), 8 + 8 * $$17.m_122430_(), 8 + 8 * $$17.m_122431_()).m_6122_(255, 0, 0, 255).m_5601_($$17.m_122429_(), $$17.m_122430_(), $$17.m_122431_()).m_5752_();
                            $$2.m_5483_(8 + 8 * $$18.m_122429_(), 8 + 8 * $$18.m_122430_(), 8 + 8 * $$18.m_122431_()).m_6122_(255, 0, 0, 255).m_5601_($$18.m_122429_(), $$18.m_122430_(), $$18.m_122431_()).m_5752_();
                        }
                    }
                    $$1.m_85914_();
                    RenderSystem.m_69832_(1.0f);
                    RenderSystem.m_157427_(GameRenderer::m_172811_);
                    if ($$16 > 0) {
                        $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
                        float $$20 = 0.5f;
                        float $$21 = 0.2f;
                        $$2.m_5483_(0.5, 15.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 15.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 15.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 15.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 0.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 0.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 0.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 0.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 15.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 15.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 0.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 0.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 0.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 0.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 15.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 15.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 0.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 0.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 15.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 15.5, 0.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 15.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 15.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(15.5, 0.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$2.m_5483_(0.5, 0.5, 15.5).m_85950_(0.9f, 0.9f, 0.0f, 0.2f).m_5752_();
                        $$1.m_85914_();
                    }
                }
                $$9.m_85849_();
                RenderSystem.m_157182_();
            }
            RenderSystem.m_69458_(true);
            RenderSystem.m_69461_();
            RenderSystem.m_69481_();
            RenderSystem.m_69493_();
        }
        if (this.f_109442_ != null) {
            RenderSystem.m_69464_();
            RenderSystem.m_69472_();
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            RenderSystem.m_69832_(5.0f);
            RenderSystem.m_157427_(GameRenderer::m_172811_);
            PoseStack $$22 = RenderSystem.m_157191_();
            $$22.m_85836_();
            $$22.m_85837_((float)(this.f_109444_.f_86214_ - p_109794_.m_90583_().f_82479_), (float)(this.f_109444_.f_86215_ - p_109794_.m_90583_().f_82480_), (float)(this.f_109444_.f_86216_ - p_109794_.m_90583_().f_82481_));
            RenderSystem.m_157182_();
            RenderSystem.m_69458_(true);
            $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
            this.m_109668_($$2, 0, 1, 2, 3, 0, 1, 1);
            this.m_109668_($$2, 4, 5, 6, 7, 1, 0, 0);
            this.m_109668_($$2, 0, 1, 5, 4, 1, 1, 0);
            this.m_109668_($$2, 2, 3, 7, 6, 0, 0, 1);
            this.m_109668_($$2, 0, 4, 7, 3, 0, 1, 0);
            this.m_109668_($$2, 1, 5, 6, 2, 1, 0, 1);
            $$1.m_85914_();
            RenderSystem.m_69458_(false);
            RenderSystem.m_157427_(GameRenderer::m_172757_);
            $$2.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            this.m_109665_($$2, 0);
            this.m_109665_($$2, 1);
            this.m_109665_($$2, 1);
            this.m_109665_($$2, 2);
            this.m_109665_($$2, 2);
            this.m_109665_($$2, 3);
            this.m_109665_($$2, 3);
            this.m_109665_($$2, 0);
            this.m_109665_($$2, 4);
            this.m_109665_($$2, 5);
            this.m_109665_($$2, 5);
            this.m_109665_($$2, 6);
            this.m_109665_($$2, 6);
            this.m_109665_($$2, 7);
            this.m_109665_($$2, 7);
            this.m_109665_($$2, 4);
            this.m_109665_($$2, 0);
            this.m_109665_($$2, 4);
            this.m_109665_($$2, 1);
            this.m_109665_($$2, 5);
            this.m_109665_($$2, 2);
            this.m_109665_($$2, 6);
            this.m_109665_($$2, 3);
            this.m_109665_($$2, 7);
            $$1.m_85914_();
            $$22.m_85849_();
            RenderSystem.m_157182_();
            RenderSystem.m_69458_(true);
            RenderSystem.m_69461_();
            RenderSystem.m_69481_();
            RenderSystem.m_69493_();
            RenderSystem.m_69832_(1.0f);
        }
    }

    private void m_109665_(VertexConsumer p_109666_, int p_109667_) {
        p_109666_.m_5483_(this.f_109443_[p_109667_].m_123601_(), this.f_109443_[p_109667_].m_123615_(), this.f_109443_[p_109667_].m_123616_()).m_6122_(0, 0, 0, 255).m_5601_(0.0f, 0.0f, -1.0f).m_5752_();
    }

    private void m_109668_(VertexConsumer p_109669_, int p_109670_, int p_109671_, int p_109672_, int p_109673_, int p_109674_, int p_109675_, int p_109676_) {
        float $$8 = 0.25f;
        p_109669_.m_5483_(this.f_109443_[p_109670_].m_123601_(), this.f_109443_[p_109670_].m_123615_(), this.f_109443_[p_109670_].m_123616_()).m_85950_(p_109674_, p_109675_, p_109676_, 0.25f).m_5752_();
        p_109669_.m_5483_(this.f_109443_[p_109671_].m_123601_(), this.f_109443_[p_109671_].m_123615_(), this.f_109443_[p_109671_].m_123616_()).m_85950_(p_109674_, p_109675_, p_109676_, 0.25f).m_5752_();
        p_109669_.m_5483_(this.f_109443_[p_109672_].m_123601_(), this.f_109443_[p_109672_].m_123615_(), this.f_109443_[p_109672_].m_123616_()).m_85950_(p_109674_, p_109675_, p_109676_, 0.25f).m_5752_();
        p_109669_.m_5483_(this.f_109443_[p_109673_].m_123601_(), this.f_109443_[p_109673_].m_123615_(), this.f_109443_[p_109673_].m_123616_()).m_85950_(p_109674_, p_109675_, p_109676_, 0.25f).m_5752_();
    }

    public void m_173018_() {
        this.f_109441_ = true;
    }

    public void m_173019_() {
        this.f_109442_ = null;
    }

    public void m_109823_() {
        ++this.f_109477_;
        if (this.f_109477_ % 20 != 0) {
            return;
        }
        ObjectIterator $$0 = this.f_109408_.values().iterator();
        while ($$0.hasNext()) {
            BlockDestructionProgress $$1 = (BlockDestructionProgress)$$0.next();
            int $$2 = $$1.m_139991_();
            if (this.f_109477_ - $$2 <= 400) continue;
            $$0.remove();
            this.m_109765_($$1);
        }
    }

    private void m_109765_(BlockDestructionProgress p_109766_) {
        long $$1 = p_109766_.m_139985_().m_121878_();
        Set $$2 = (Set)this.f_109409_.get($$1);
        $$2.remove(p_109766_);
        if ($$2.isEmpty()) {
            this.f_109409_.remove($$1);
        }
    }

    private void m_109780_(PoseStack p_109781_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        RenderSystem.m_157456_(0, f_109457_);
        Tesselator $$1 = Tesselator.m_85913_();
        BufferBuilder $$2 = $$1.m_85915_();
        for (int $$3 = 0; $$3 < 6; ++$$3) {
            p_109781_.m_85836_();
            if ($$3 == 1) {
                p_109781_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
            }
            if ($$3 == 2) {
                p_109781_.m_85845_(Vector3f.f_122223_.m_122240_(-90.0f));
            }
            if ($$3 == 3) {
                p_109781_.m_85845_(Vector3f.f_122223_.m_122240_(180.0f));
            }
            if ($$3 == 4) {
                p_109781_.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
            }
            if ($$3 == 5) {
                p_109781_.m_85845_(Vector3f.f_122227_.m_122240_(-90.0f));
            }
            Matrix4f $$4 = p_109781_.m_85850_().m_85861_();
            $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
            $$2.m_85982_($$4, -100.0f, -100.0f, -100.0f).m_7421_(0.0f, 0.0f).m_6122_(40, 40, 40, 255).m_5752_();
            $$2.m_85982_($$4, -100.0f, -100.0f, 100.0f).m_7421_(0.0f, 16.0f).m_6122_(40, 40, 40, 255).m_5752_();
            $$2.m_85982_($$4, 100.0f, -100.0f, 100.0f).m_7421_(16.0f, 16.0f).m_6122_(40, 40, 40, 255).m_5752_();
            $$2.m_85982_($$4, 100.0f, -100.0f, -100.0f).m_7421_(16.0f, 0.0f).m_6122_(40, 40, 40, 255).m_5752_();
            $$1.m_85914_();
            p_109781_.m_85849_();
        }
        RenderSystem.m_69458_(true);
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }

    public void m_202423_(PoseStack p_202424_, Matrix4f p_202425_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_) {
        p_202429_.run();
        if (p_202428_) {
            return;
        }
        FogType $$6 = p_202427_.m_167685_();
        if ($$6 == FogType.POWDER_SNOW || $$6 == FogType.LAVA || this.m_234310_(p_202427_)) {
            return;
        }
        if (this.f_109461_.f_91073_.m_104583_().m_108883_() == DimensionSpecialEffects.SkyType.END) {
            this.m_109780_(p_202424_);
            return;
        }
        if (this.f_109461_.f_91073_.m_104583_().m_108883_() != DimensionSpecialEffects.SkyType.NORMAL) {
            return;
        }
        RenderSystem.m_69472_();
        Vec3 $$7 = this.f_109465_.m_171660_(this.f_109461_.f_91063_.m_109153_().m_90583_(), p_202426_);
        float $$8 = (float)$$7.f_82479_;
        float $$9 = (float)$$7.f_82480_;
        float $$10 = (float)$$7.f_82481_;
        FogRenderer.m_109036_();
        BufferBuilder $$11 = Tesselator.m_85913_().m_85915_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_157429_($$8, $$9, $$10, 1.0f);
        ShaderInstance $$12 = RenderSystem.m_157196_();
        this.f_109472_.m_85921_();
        this.f_109472_.m_166867_(p_202424_.m_85850_().m_85861_(), p_202425_, $$12);
        VertexBuffer.m_85931_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        float[] $$13 = this.f_109465_.m_104583_().m_7518_(this.f_109465_.m_46942_(p_202426_), p_202426_);
        if ($$13 != null) {
            RenderSystem.m_157427_(GameRenderer::m_172811_);
            RenderSystem.m_69472_();
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            p_202424_.m_85836_();
            p_202424_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
            float $$14 = Mth.m_14031_(this.f_109465_.m_46490_(p_202426_)) < 0.0f ? 180.0f : 0.0f;
            p_202424_.m_85845_(Vector3f.f_122227_.m_122240_($$14));
            p_202424_.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
            float $$15 = $$13[0];
            float $$16 = $$13[1];
            float $$17 = $$13[2];
            Matrix4f $$18 = p_202424_.m_85850_().m_85861_();
            $$11.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
            $$11.m_85982_($$18, 0.0f, 100.0f, 0.0f).m_85950_($$15, $$16, $$17, $$13[3]).m_5752_();
            int $$19 = 16;
            for (int $$20 = 0; $$20 <= 16; ++$$20) {
                float $$21 = (float)$$20 * ((float)Math.PI * 2) / 16.0f;
                float $$22 = Mth.m_14031_($$21);
                float $$23 = Mth.m_14089_($$21);
                $$11.m_85982_($$18, $$22 * 120.0f, $$23 * 120.0f, -$$23 * 40.0f * $$13[3]).m_85950_($$13[0], $$13[1], $$13[2], 0.0f).m_5752_();
            }
            BufferUploader.m_231202_($$11.m_231175_());
            p_202424_.m_85849_();
        }
        RenderSystem.m_69493_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        p_202424_.m_85836_();
        float $$24 = 1.0f - this.f_109465_.m_46722_(p_202426_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, $$24);
        p_202424_.m_85845_(Vector3f.f_122225_.m_122240_(-90.0f));
        p_202424_.m_85845_(Vector3f.f_122223_.m_122240_(this.f_109465_.m_46942_(p_202426_) * 360.0f));
        Matrix4f $$25 = p_202424_.m_85850_().m_85861_();
        float $$26 = 30.0f;
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_109455_);
        $$11.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$11.m_85982_($$25, -$$26, 100.0f, -$$26).m_7421_(0.0f, 0.0f).m_5752_();
        $$11.m_85982_($$25, $$26, 100.0f, -$$26).m_7421_(1.0f, 0.0f).m_5752_();
        $$11.m_85982_($$25, $$26, 100.0f, $$26).m_7421_(1.0f, 1.0f).m_5752_();
        $$11.m_85982_($$25, -$$26, 100.0f, $$26).m_7421_(0.0f, 1.0f).m_5752_();
        BufferUploader.m_231202_($$11.m_231175_());
        $$26 = 20.0f;
        RenderSystem.m_157456_(0, f_109454_);
        int $$27 = this.f_109465_.m_46941_();
        int $$28 = $$27 % 4;
        int $$29 = $$27 / 4 % 2;
        float $$30 = (float)($$28 + 0) / 4.0f;
        float $$31 = (float)($$29 + 0) / 2.0f;
        float $$32 = (float)($$28 + 1) / 4.0f;
        float $$33 = (float)($$29 + 1) / 2.0f;
        $$11.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$11.m_85982_($$25, -$$26, -100.0f, $$26).m_7421_($$32, $$33).m_5752_();
        $$11.m_85982_($$25, $$26, -100.0f, $$26).m_7421_($$30, $$33).m_5752_();
        $$11.m_85982_($$25, $$26, -100.0f, -$$26).m_7421_($$30, $$31).m_5752_();
        $$11.m_85982_($$25, -$$26, -100.0f, -$$26).m_7421_($$32, $$31).m_5752_();
        BufferUploader.m_231202_($$11.m_231175_());
        RenderSystem.m_69472_();
        float $$34 = this.f_109465_.m_104811_(p_202426_) * $$24;
        if ($$34 > 0.0f) {
            RenderSystem.m_157429_($$34, $$34, $$34, $$34);
            FogRenderer.m_109017_();
            this.f_109471_.m_85921_();
            this.f_109471_.m_166867_(p_202424_.m_85850_().m_85861_(), p_202425_, GameRenderer.m_172808_());
            VertexBuffer.m_85931_();
            p_202429_.run();
        }
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69461_();
        p_202424_.m_85849_();
        RenderSystem.m_69472_();
        RenderSystem.m_157429_(0.0f, 0.0f, 0.0f, 1.0f);
        double $$35 = this.f_109461_.f_91074_.m_20299_((float)p_202426_).f_82480_ - this.f_109465_.m_6106_().m_171687_(this.f_109465_);
        if ($$35 < 0.0) {
            p_202424_.m_85836_();
            p_202424_.m_85837_(0.0, 12.0, 0.0);
            this.f_109473_.m_85921_();
            this.f_109473_.m_166867_(p_202424_.m_85850_().m_85861_(), p_202425_, $$12);
            VertexBuffer.m_85931_();
            p_202424_.m_85849_();
        }
        if (this.f_109465_.m_104583_().m_108882_()) {
            RenderSystem.m_157429_($$8 * 0.2f + 0.04f, $$9 * 0.2f + 0.04f, $$10 * 0.6f + 0.1f, 1.0f);
        } else {
            RenderSystem.m_157429_($$8, $$9, $$10, 1.0f);
        }
        RenderSystem.m_69493_();
        RenderSystem.m_69458_(true);
    }

    private boolean m_234310_(Camera p_234311_) {
        Entity entity = p_234311_.m_90592_();
        if (entity instanceof LivingEntity) {
            LivingEntity $$1 = (LivingEntity)entity;
            return $$1.m_21023_(MobEffects.f_19610_) || $$1.m_21023_(MobEffects.f_216964_);
        }
        return false;
    }

    public void m_172954_(PoseStack p_172955_, Matrix4f p_172956_, float p_172957_, double p_172958_, double p_172959_, double p_172960_) {
        float $$6 = this.f_109465_.m_104583_().m_108871_();
        if (Float.isNaN($$6)) {
            return;
        }
        RenderSystem.m_69464_();
        RenderSystem.m_69478_();
        RenderSystem.m_69482_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.m_69458_(true);
        float $$7 = 12.0f;
        float $$8 = 4.0f;
        double $$9 = 2.0E-4;
        double $$10 = ((float)this.f_109477_ + p_172957_) * 0.03f;
        double $$11 = (p_172958_ + $$10) / 12.0;
        double $$12 = $$6 - (float)p_172959_ + 0.33f;
        double $$13 = p_172960_ / 12.0 + (double)0.33f;
        $$11 -= (double)(Mth.m_14107_($$11 / 2048.0) * 2048);
        $$13 -= (double)(Mth.m_14107_($$13 / 2048.0) * 2048);
        float $$14 = (float)($$11 - (double)Mth.m_14107_($$11));
        float $$15 = (float)($$12 / 4.0 - (double)Mth.m_14107_($$12 / 4.0)) * 4.0f;
        float $$16 = (float)($$13 - (double)Mth.m_14107_($$13));
        Vec3 $$17 = this.f_109465_.m_104808_(p_172957_);
        int $$18 = (int)Math.floor($$11);
        int $$19 = (int)Math.floor($$12 / 4.0);
        int $$20 = (int)Math.floor($$13);
        if ($$18 != this.f_109430_ || $$19 != this.f_109431_ || $$20 != this.f_109432_ || this.f_109461_.f_91066_.m_92174_() != this.f_109435_ || this.f_109433_.m_82557_($$17) > 2.0E-4) {
            this.f_109430_ = $$18;
            this.f_109431_ = $$19;
            this.f_109432_ = $$20;
            this.f_109433_ = $$17;
            this.f_109435_ = this.f_109461_.f_91066_.m_92174_();
            this.f_109474_ = true;
        }
        if (this.f_109474_) {
            this.f_109474_ = false;
            BufferBuilder $$21 = Tesselator.m_85913_().m_85915_();
            if (this.f_109475_ != null) {
                this.f_109475_.close();
            }
            this.f_109475_ = new VertexBuffer();
            BufferBuilder.RenderedBuffer $$22 = this.m_234261_($$21, $$11, $$12, $$13, $$17);
            this.f_109475_.m_85921_();
            this.f_109475_.m_231221_($$22);
            VertexBuffer.m_85931_();
        }
        RenderSystem.m_157427_(GameRenderer::m_172838_);
        RenderSystem.m_157456_(0, f_109456_);
        FogRenderer.m_109036_();
        p_172955_.m_85836_();
        p_172955_.m_85841_(12.0f, 1.0f, 12.0f);
        p_172955_.m_85837_(-$$14, $$15, -$$16);
        if (this.f_109475_ != null) {
            int $$23;
            this.f_109475_.m_85921_();
            for (int $$24 = $$23 = this.f_109435_ == CloudStatus.FANCY ? 0 : 1; $$24 < 2; ++$$24) {
                if ($$24 == 0) {
                    RenderSystem.m_69444_(false, false, false, false);
                } else {
                    RenderSystem.m_69444_(true, true, true, true);
                }
                ShaderInstance $$25 = RenderSystem.m_157196_();
                this.f_109475_.m_166867_(p_172955_.m_85850_().m_85861_(), p_172956_, $$25);
            }
            VertexBuffer.m_85931_();
        }
        p_172955_.m_85849_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69481_();
        RenderSystem.m_69461_();
    }

    private BufferBuilder.RenderedBuffer m_234261_(BufferBuilder p_234262_, double p_234263_, double p_234264_, double p_234265_, Vec3 p_234266_) {
        float $$5 = 4.0f;
        float $$6 = 0.00390625f;
        int $$7 = 8;
        int $$8 = 4;
        float $$9 = 9.765625E-4f;
        float $$10 = (float)Mth.m_14107_(p_234263_) * 0.00390625f;
        float $$11 = (float)Mth.m_14107_(p_234265_) * 0.00390625f;
        float $$12 = (float)p_234266_.f_82479_;
        float $$13 = (float)p_234266_.f_82480_;
        float $$14 = (float)p_234266_.f_82481_;
        float $$15 = $$12 * 0.9f;
        float $$16 = $$13 * 0.9f;
        float $$17 = $$14 * 0.9f;
        float $$18 = $$12 * 0.7f;
        float $$19 = $$13 * 0.7f;
        float $$20 = $$14 * 0.7f;
        float $$21 = $$12 * 0.8f;
        float $$22 = $$13 * 0.8f;
        float $$23 = $$14 * 0.8f;
        RenderSystem.m_157427_(GameRenderer::m_172838_);
        p_234262_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85822_);
        float $$24 = (float)Math.floor(p_234264_ / 4.0) * 4.0f;
        if (this.f_109435_ == CloudStatus.FANCY) {
            for (int $$25 = -3; $$25 <= 4; ++$$25) {
                for (int $$26 = -3; $$26 <= 4; ++$$26) {
                    float $$27 = $$25 * 8;
                    float $$28 = $$26 * 8;
                    if ($$24 > -5.0f) {
                        p_234262_.m_5483_($$27 + 0.0f, $$24 + 0.0f, $$28 + 8.0f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$18, $$19, $$20, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 8.0f, $$24 + 0.0f, $$28 + 8.0f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$18, $$19, $$20, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 8.0f, $$24 + 0.0f, $$28 + 0.0f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$18, $$19, $$20, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 0.0f, $$24 + 0.0f, $$28 + 0.0f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$18, $$19, $$20, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                    }
                    if ($$24 <= 5.0f) {
                        p_234262_.m_5483_($$27 + 0.0f, $$24 + 4.0f - 9.765625E-4f, $$28 + 8.0f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 8.0f, $$24 + 4.0f - 9.765625E-4f, $$28 + 8.0f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 8.0f, $$24 + 4.0f - 9.765625E-4f, $$28 + 0.0f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 0.0f, $$24 + 4.0f - 9.765625E-4f, $$28 + 0.0f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
                    }
                    if ($$25 > -1) {
                        for (int $$29 = 0; $$29 < 8; ++$$29) {
                            p_234262_.m_5483_($$27 + (float)$$29 + 0.0f, $$24 + 0.0f, $$28 + 8.0f).m_7421_(($$27 + (float)$$29 + 0.5f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(-1.0f, 0.0f, 0.0f).m_5752_();
                            p_234262_.m_5483_($$27 + (float)$$29 + 0.0f, $$24 + 4.0f, $$28 + 8.0f).m_7421_(($$27 + (float)$$29 + 0.5f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(-1.0f, 0.0f, 0.0f).m_5752_();
                            p_234262_.m_5483_($$27 + (float)$$29 + 0.0f, $$24 + 4.0f, $$28 + 0.0f).m_7421_(($$27 + (float)$$29 + 0.5f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(-1.0f, 0.0f, 0.0f).m_5752_();
                            p_234262_.m_5483_($$27 + (float)$$29 + 0.0f, $$24 + 0.0f, $$28 + 0.0f).m_7421_(($$27 + (float)$$29 + 0.5f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(-1.0f, 0.0f, 0.0f).m_5752_();
                        }
                    }
                    if ($$25 <= 1) {
                        for (int $$30 = 0; $$30 < 8; ++$$30) {
                            p_234262_.m_5483_($$27 + (float)$$30 + 1.0f - 9.765625E-4f, $$24 + 0.0f, $$28 + 8.0f).m_7421_(($$27 + (float)$$30 + 0.5f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
                            p_234262_.m_5483_($$27 + (float)$$30 + 1.0f - 9.765625E-4f, $$24 + 4.0f, $$28 + 8.0f).m_7421_(($$27 + (float)$$30 + 0.5f) * 0.00390625f + $$10, ($$28 + 8.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
                            p_234262_.m_5483_($$27 + (float)$$30 + 1.0f - 9.765625E-4f, $$24 + 4.0f, $$28 + 0.0f).m_7421_(($$27 + (float)$$30 + 0.5f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
                            p_234262_.m_5483_($$27 + (float)$$30 + 1.0f - 9.765625E-4f, $$24 + 0.0f, $$28 + 0.0f).m_7421_(($$27 + (float)$$30 + 0.5f) * 0.00390625f + $$10, ($$28 + 0.0f) * 0.00390625f + $$11).m_85950_($$15, $$16, $$17, 0.8f).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
                        }
                    }
                    if ($$26 > -1) {
                        for (int $$31 = 0; $$31 < 8; ++$$31) {
                            p_234262_.m_5483_($$27 + 0.0f, $$24 + 4.0f, $$28 + (float)$$31 + 0.0f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + (float)$$31 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, -1.0f).m_5752_();
                            p_234262_.m_5483_($$27 + 8.0f, $$24 + 4.0f, $$28 + (float)$$31 + 0.0f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + (float)$$31 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, -1.0f).m_5752_();
                            p_234262_.m_5483_($$27 + 8.0f, $$24 + 0.0f, $$28 + (float)$$31 + 0.0f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + (float)$$31 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, -1.0f).m_5752_();
                            p_234262_.m_5483_($$27 + 0.0f, $$24 + 0.0f, $$28 + (float)$$31 + 0.0f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + (float)$$31 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, -1.0f).m_5752_();
                        }
                    }
                    if ($$26 > 1) continue;
                    for (int $$32 = 0; $$32 < 8; ++$$32) {
                        p_234262_.m_5483_($$27 + 0.0f, $$24 + 4.0f, $$28 + (float)$$32 + 1.0f - 9.765625E-4f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + (float)$$32 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 8.0f, $$24 + 4.0f, $$28 + (float)$$32 + 1.0f - 9.765625E-4f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + (float)$$32 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 8.0f, $$24 + 0.0f, $$28 + (float)$$32 + 1.0f - 9.765625E-4f).m_7421_(($$27 + 8.0f) * 0.00390625f + $$10, ($$28 + (float)$$32 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
                        p_234262_.m_5483_($$27 + 0.0f, $$24 + 0.0f, $$28 + (float)$$32 + 1.0f - 9.765625E-4f).m_7421_(($$27 + 0.0f) * 0.00390625f + $$10, ($$28 + (float)$$32 + 0.5f) * 0.00390625f + $$11).m_85950_($$21, $$22, $$23, 0.8f).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
                    }
                }
            }
        } else {
            boolean $$33 = true;
            int $$34 = 32;
            for (int $$35 = -32; $$35 < 32; $$35 += 32) {
                for (int $$36 = -32; $$36 < 32; $$36 += 32) {
                    p_234262_.m_5483_($$35 + 0, $$24, $$36 + 32).m_7421_((float)($$35 + 0) * 0.00390625f + $$10, (float)($$36 + 32) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                    p_234262_.m_5483_($$35 + 32, $$24, $$36 + 32).m_7421_((float)($$35 + 32) * 0.00390625f + $$10, (float)($$36 + 32) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                    p_234262_.m_5483_($$35 + 32, $$24, $$36 + 0).m_7421_((float)($$35 + 32) * 0.00390625f + $$10, (float)($$36 + 0) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                    p_234262_.m_5483_($$35 + 0, $$24, $$36 + 0).m_7421_((float)($$35 + 0) * 0.00390625f + $$10, (float)($$36 + 0) * 0.00390625f + $$11).m_85950_($$12, $$13, $$14, 0.8f).m_5601_(0.0f, -1.0f, 0.0f).m_5752_();
                }
            }
        }
        return p_234262_.m_231175_();
    }

    private void m_194370_(Camera p_194371_) {
        this.f_109461_.m_91307_().m_6180_("populate_chunks_to_compile");
        RenderRegionCache $$1 = new RenderRegionCache();
        BlockPos $$2 = p_194371_.m_90588_();
        ArrayList $$3 = Lists.newArrayList();
        for (RenderChunkInfo $$4 : this.f_194297_) {
            ChunkRenderDispatcher.RenderChunk $$5 = $$4.f_109839_;
            ChunkPos $$6 = new ChunkPos($$5.m_112839_());
            if (!$$5.m_112841_() || !this.f_109465_.m_6325_($$6.f_45578_, $$6.f_45579_).m_196863_()) continue;
            boolean $$7 = false;
            if (this.f_109461_.f_91066_.m_232080_().m_231551_() == PrioritizeChunkUpdates.NEARBY) {
                BlockPos $$8 = $$5.m_112839_().m_7918_(8, 8, 8);
                $$7 = $$8.m_123331_($$2) < 768.0 || $$5.m_112842_();
            } else if (this.f_109461_.f_91066_.m_232080_().m_231551_() == PrioritizeChunkUpdates.PLAYER_AFFECTED) {
                $$7 = $$5.m_112842_();
            }
            if ($$7) {
                this.f_109461_.m_91307_().m_6180_("build_near_sync");
                this.f_109436_.m_200431_($$5, $$1);
                $$5.m_112840_();
                this.f_109461_.m_91307_().m_7238_();
                continue;
            }
            $$3.add($$5);
        }
        this.f_109461_.m_91307_().m_6182_("upload");
        this.f_109436_.m_194417_();
        this.f_109461_.m_91307_().m_6182_("schedule_async_compile");
        for (ChunkRenderDispatcher.RenderChunk $$9 : $$3) {
            $$9.m_200434_(this.f_109436_, $$1);
            $$9.m_112840_();
        }
        this.f_109461_.m_91307_().m_7238_();
    }

    private void m_173012_(Camera p_173013_) {
        BufferBuilder $$1 = Tesselator.m_85913_().m_85915_();
        WorldBorder $$2 = this.f_109465_.m_6857_();
        double $$3 = this.f_109461_.f_91066_.m_193772_() * 16;
        if (p_173013_.m_90583_().f_82479_ < $$2.m_61957_() - $$3 && p_173013_.m_90583_().f_82479_ > $$2.m_61955_() + $$3 && p_173013_.m_90583_().f_82481_ < $$2.m_61958_() - $$3 && p_173013_.m_90583_().f_82481_ > $$2.m_61956_() + $$3) {
            return;
        }
        double $$4 = 1.0 - $$2.m_61941_(p_173013_.m_90583_().f_82479_, p_173013_.m_90583_().f_82481_) / $$3;
        $$4 = Math.pow($$4, 4.0);
        $$4 = Mth.m_14008_($$4, 0.0, 1.0);
        double $$5 = p_173013_.m_90583_().f_82479_;
        double $$6 = p_173013_.m_90583_().f_82481_;
        double $$7 = this.f_109461_.f_91063_.m_172790_();
        RenderSystem.m_69478_();
        RenderSystem.m_69482_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.m_157456_(0, f_109458_);
        RenderSystem.m_69458_(Minecraft.m_91085_());
        PoseStack $$8 = RenderSystem.m_157191_();
        $$8.m_85836_();
        RenderSystem.m_157182_();
        int $$9 = $$2.m_61954_().m_61901_();
        float $$10 = (float)($$9 >> 16 & 0xFF) / 255.0f;
        float $$11 = (float)($$9 >> 8 & 0xFF) / 255.0f;
        float $$12 = (float)($$9 & 0xFF) / 255.0f;
        RenderSystem.m_157429_($$10, $$11, $$12, (float)$$4);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_69863_(-3.0f, -3.0f);
        RenderSystem.m_69486_();
        RenderSystem.m_69464_();
        float $$13 = (float)(Util.m_137550_() % 3000L) / 3000.0f;
        float $$14 = 0.0f;
        float $$15 = 0.0f;
        float $$16 = (float)($$7 - Mth.m_14185_(p_173013_.m_90583_().f_82480_));
        $$1.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        double $$17 = Math.max((double)Mth.m_14107_($$6 - $$3), $$2.m_61956_());
        double $$18 = Math.min((double)Mth.m_14165_($$6 + $$3), $$2.m_61958_());
        if ($$5 > $$2.m_61957_() - $$3) {
            float $$19 = 0.0f;
            double $$20 = $$17;
            while ($$20 < $$18) {
                double $$21 = Math.min(1.0, $$18 - $$20);
                float $$22 = (float)$$21 * 0.5f;
                $$1.m_5483_($$2.m_61957_() - $$5, -$$7, $$20 - $$6).m_7421_($$13 - $$19, $$13 + $$16).m_5752_();
                $$1.m_5483_($$2.m_61957_() - $$5, -$$7, $$20 + $$21 - $$6).m_7421_($$13 - ($$22 + $$19), $$13 + $$16).m_5752_();
                $$1.m_5483_($$2.m_61957_() - $$5, $$7, $$20 + $$21 - $$6).m_7421_($$13 - ($$22 + $$19), $$13 + 0.0f).m_5752_();
                $$1.m_5483_($$2.m_61957_() - $$5, $$7, $$20 - $$6).m_7421_($$13 - $$19, $$13 + 0.0f).m_5752_();
                $$20 += 1.0;
                $$19 += 0.5f;
            }
        }
        if ($$5 < $$2.m_61955_() + $$3) {
            float $$23 = 0.0f;
            double $$24 = $$17;
            while ($$24 < $$18) {
                double $$25 = Math.min(1.0, $$18 - $$24);
                float $$26 = (float)$$25 * 0.5f;
                $$1.m_5483_($$2.m_61955_() - $$5, -$$7, $$24 - $$6).m_7421_($$13 + $$23, $$13 + $$16).m_5752_();
                $$1.m_5483_($$2.m_61955_() - $$5, -$$7, $$24 + $$25 - $$6).m_7421_($$13 + $$26 + $$23, $$13 + $$16).m_5752_();
                $$1.m_5483_($$2.m_61955_() - $$5, $$7, $$24 + $$25 - $$6).m_7421_($$13 + $$26 + $$23, $$13 + 0.0f).m_5752_();
                $$1.m_5483_($$2.m_61955_() - $$5, $$7, $$24 - $$6).m_7421_($$13 + $$23, $$13 + 0.0f).m_5752_();
                $$24 += 1.0;
                $$23 += 0.5f;
            }
        }
        $$17 = Math.max((double)Mth.m_14107_($$5 - $$3), $$2.m_61955_());
        $$18 = Math.min((double)Mth.m_14165_($$5 + $$3), $$2.m_61957_());
        if ($$6 > $$2.m_61958_() - $$3) {
            float $$27 = 0.0f;
            double $$28 = $$17;
            while ($$28 < $$18) {
                double $$29 = Math.min(1.0, $$18 - $$28);
                float $$30 = (float)$$29 * 0.5f;
                $$1.m_5483_($$28 - $$5, -$$7, $$2.m_61958_() - $$6).m_7421_($$13 + $$27, $$13 + $$16).m_5752_();
                $$1.m_5483_($$28 + $$29 - $$5, -$$7, $$2.m_61958_() - $$6).m_7421_($$13 + $$30 + $$27, $$13 + $$16).m_5752_();
                $$1.m_5483_($$28 + $$29 - $$5, $$7, $$2.m_61958_() - $$6).m_7421_($$13 + $$30 + $$27, $$13 + 0.0f).m_5752_();
                $$1.m_5483_($$28 - $$5, $$7, $$2.m_61958_() - $$6).m_7421_($$13 + $$27, $$13 + 0.0f).m_5752_();
                $$28 += 1.0;
                $$27 += 0.5f;
            }
        }
        if ($$6 < $$2.m_61956_() + $$3) {
            float $$31 = 0.0f;
            double $$32 = $$17;
            while ($$32 < $$18) {
                double $$33 = Math.min(1.0, $$18 - $$32);
                float $$34 = (float)$$33 * 0.5f;
                $$1.m_5483_($$32 - $$5, -$$7, $$2.m_61956_() - $$6).m_7421_($$13 - $$31, $$13 + $$16).m_5752_();
                $$1.m_5483_($$32 + $$33 - $$5, -$$7, $$2.m_61956_() - $$6).m_7421_($$13 - ($$34 + $$31), $$13 + $$16).m_5752_();
                $$1.m_5483_($$32 + $$33 - $$5, $$7, $$2.m_61956_() - $$6).m_7421_($$13 - ($$34 + $$31), $$13 + 0.0f).m_5752_();
                $$1.m_5483_($$32 - $$5, $$7, $$2.m_61956_() - $$6).m_7421_($$13 - $$31, $$13 + 0.0f).m_5752_();
                $$32 += 1.0;
                $$31 += 0.5f;
            }
        }
        BufferUploader.m_231202_($$1.m_231175_());
        RenderSystem.m_69481_();
        RenderSystem.m_69863_(0.0f, 0.0f);
        RenderSystem.m_69469_();
        RenderSystem.m_69461_();
        $$8.m_85849_();
        RenderSystem.m_157182_();
        RenderSystem.m_69458_(true);
    }

    private void m_109637_(PoseStack p_109638_, VertexConsumer p_109639_, Entity p_109640_, double p_109641_, double p_109642_, double p_109643_, BlockPos p_109644_, BlockState p_109645_) {
        LevelRenderer.m_109782_(p_109638_, p_109639_, p_109645_.m_60651_(this.f_109465_, p_109644_, CollisionContext.m_82750_(p_109640_)), (double)p_109644_.m_123341_() - p_109641_, (double)p_109644_.m_123342_() - p_109642_, (double)p_109644_.m_123343_() - p_109643_, 0.0f, 0.0f, 0.0f, 0.4f);
    }

    public static void m_109654_(PoseStack p_109655_, VertexConsumer p_109656_, VoxelShape p_109657_, double p_109658_, double p_109659_, double p_109660_, float p_109661_, float p_109662_, float p_109663_, float p_109664_) {
        List<AABB> $$10 = p_109657_.m_83299_();
        int $$11 = Mth.m_14165_((double)$$10.size() / 3.0);
        for (int $$12 = 0; $$12 < $$10.size(); ++$$12) {
            AABB $$13 = $$10.get($$12);
            float $$14 = ((float)$$12 % (float)$$11 + 1.0f) / (float)$$11;
            float $$15 = $$12 / $$11;
            float $$16 = $$14 * (float)($$15 == 0.0f ? 1 : 0);
            float $$17 = $$14 * (float)($$15 == 1.0f ? 1 : 0);
            float $$18 = $$14 * (float)($$15 == 2.0f ? 1 : 0);
            LevelRenderer.m_109782_(p_109655_, p_109656_, Shapes.m_83064_($$13.m_82386_(0.0, 0.0, 0.0)), p_109658_, p_109659_, p_109660_, $$16, $$17, $$18, 1.0f);
        }
    }

    private static void m_109782_(PoseStack p_109783_, VertexConsumer p_109784_, VoxelShape p_109785_, double p_109786_, double p_109787_, double p_109788_, float p_109789_, float p_109790_, float p_109791_, float p_109792_) {
        PoseStack.Pose $$10 = p_109783_.m_85850_();
        p_109785_.m_83224_((p_234280_, p_234281_, p_234282_, p_234283_, p_234284_, p_234285_) -> {
            float $$15 = (float)(p_234283_ - p_234280_);
            float $$16 = (float)(p_234284_ - p_234281_);
            float $$17 = (float)(p_234285_ - p_234282_);
            float $$18 = Mth.m_14116_($$15 * $$15 + $$16 * $$16 + $$17 * $$17);
            p_109784_.m_85982_($$10.m_85861_(), (float)(p_234280_ + p_109786_), (float)(p_234281_ + p_109787_), (float)(p_234282_ + p_109788_)).m_85950_(p_109789_, p_109790_, p_109791_, p_109792_).m_85977_($$10.m_85864_(), $$15 /= $$18, $$16 /= $$18, $$17 /= $$18).m_5752_();
            p_109784_.m_85982_($$10.m_85861_(), (float)(p_234283_ + p_109786_), (float)(p_234284_ + p_109787_), (float)(p_234285_ + p_109788_)).m_85950_(p_109789_, p_109790_, p_109791_, p_109792_).m_85977_($$10.m_85864_(), $$15, $$16, $$17).m_5752_();
        });
    }

    public static void m_172965_(VertexConsumer p_172966_, double p_172967_, double p_172968_, double p_172969_, double p_172970_, double p_172971_, double p_172972_, float p_172973_, float p_172974_, float p_172975_, float p_172976_) {
        LevelRenderer.m_109621_(new PoseStack(), p_172966_, p_172967_, p_172968_, p_172969_, p_172970_, p_172971_, p_172972_, p_172973_, p_172974_, p_172975_, p_172976_, p_172973_, p_172974_, p_172975_);
    }

    public static void m_109646_(PoseStack p_109647_, VertexConsumer p_109648_, AABB p_109649_, float p_109650_, float p_109651_, float p_109652_, float p_109653_) {
        LevelRenderer.m_109621_(p_109647_, p_109648_, p_109649_.f_82288_, p_109649_.f_82289_, p_109649_.f_82290_, p_109649_.f_82291_, p_109649_.f_82292_, p_109649_.f_82293_, p_109650_, p_109651_, p_109652_, p_109653_, p_109650_, p_109651_, p_109652_);
    }

    public static void m_109608_(PoseStack p_109609_, VertexConsumer p_109610_, double p_109611_, double p_109612_, double p_109613_, double p_109614_, double p_109615_, double p_109616_, float p_109617_, float p_109618_, float p_109619_, float p_109620_) {
        LevelRenderer.m_109621_(p_109609_, p_109610_, p_109611_, p_109612_, p_109613_, p_109614_, p_109615_, p_109616_, p_109617_, p_109618_, p_109619_, p_109620_, p_109617_, p_109618_, p_109619_);
    }

    public static void m_109621_(PoseStack p_109622_, VertexConsumer p_109623_, double p_109624_, double p_109625_, double p_109626_, double p_109627_, double p_109628_, double p_109629_, float p_109630_, float p_109631_, float p_109632_, float p_109633_, float p_109634_, float p_109635_, float p_109636_) {
        Matrix4f $$15 = p_109622_.m_85850_().m_85861_();
        Matrix3f $$16 = p_109622_.m_85850_().m_85864_();
        float $$17 = (float)p_109624_;
        float $$18 = (float)p_109625_;
        float $$19 = (float)p_109626_;
        float $$20 = (float)p_109627_;
        float $$21 = (float)p_109628_;
        float $$22 = (float)p_109629_;
        p_109623_.m_85982_($$15, $$17, $$18, $$19).m_85950_(p_109630_, p_109635_, p_109636_, p_109633_).m_85977_($$16, 1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$18, $$19).m_85950_(p_109630_, p_109635_, p_109636_, p_109633_).m_85977_($$16, 1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$18, $$19).m_85950_(p_109634_, p_109631_, p_109636_, p_109633_).m_85977_($$16, 0.0f, 1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$21, $$19).m_85950_(p_109634_, p_109631_, p_109636_, p_109633_).m_85977_($$16, 0.0f, 1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$18, $$19).m_85950_(p_109634_, p_109635_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, 1.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$18, $$22).m_85950_(p_109634_, p_109635_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, 1.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$18, $$19).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$21, $$19).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$21, $$19).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, -1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$21, $$19).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, -1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$21, $$19).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, 1.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$21, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, 1.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$21, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, -1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$18, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, -1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$18, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$18, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$18, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, -1.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$18, $$19).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, -1.0f).m_5752_();
        p_109623_.m_85982_($$15, $$17, $$21, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$21, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 1.0f, 0.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$18, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$21, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 1.0f, 0.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$21, $$19).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, 1.0f).m_5752_();
        p_109623_.m_85982_($$15, $$20, $$21, $$22).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_($$16, 0.0f, 0.0f, 1.0f).m_5752_();
    }

    public static void m_109556_(BufferBuilder p_109557_, double p_109558_, double p_109559_, double p_109560_, double p_109561_, double p_109562_, double p_109563_, float p_109564_, float p_109565_, float p_109566_, float p_109567_) {
        p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109558_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
        p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
    }

    public void m_109544_(BlockGetter p_109545_, BlockPos p_109546_, BlockState p_109547_, BlockState p_109548_, int p_109549_) {
        this.m_109732_(p_109546_, (p_109549_ & 8) != 0);
    }

    private void m_109732_(BlockPos p_109733_, boolean p_109734_) {
        for (int $$2 = p_109733_.m_123343_() - 1; $$2 <= p_109733_.m_123343_() + 1; ++$$2) {
            for (int $$3 = p_109733_.m_123341_() - 1; $$3 <= p_109733_.m_123341_() + 1; ++$$3) {
                for (int $$4 = p_109733_.m_123342_() - 1; $$4 <= p_109733_.m_123342_() + 1; ++$$4) {
                    this.m_109501_(SectionPos.m_123171_($$3), SectionPos.m_123171_($$4), SectionPos.m_123171_($$2), p_109734_);
                }
            }
        }
    }

    public void m_109494_(int p_109495_, int p_109496_, int p_109497_, int p_109498_, int p_109499_, int p_109500_) {
        for (int $$6 = p_109497_ - 1; $$6 <= p_109500_ + 1; ++$$6) {
            for (int $$7 = p_109495_ - 1; $$7 <= p_109498_ + 1; ++$$7) {
                for (int $$8 = p_109496_ - 1; $$8 <= p_109499_ + 1; ++$$8) {
                    this.m_109770_(SectionPos.m_123171_($$7), SectionPos.m_123171_($$8), SectionPos.m_123171_($$6));
                }
            }
        }
    }

    public void m_109721_(BlockPos p_109722_, BlockState p_109723_, BlockState p_109724_) {
        if (this.f_109461_.m_91304_().m_119415_(p_109723_, p_109724_)) {
            this.m_109494_(p_109722_.m_123341_(), p_109722_.m_123342_(), p_109722_.m_123343_(), p_109722_.m_123341_(), p_109722_.m_123342_(), p_109722_.m_123343_());
        }
    }

    public void m_109490_(int p_109491_, int p_109492_, int p_109493_) {
        for (int $$3 = p_109493_ - 1; $$3 <= p_109493_ + 1; ++$$3) {
            for (int $$4 = p_109491_ - 1; $$4 <= p_109491_ + 1; ++$$4) {
                for (int $$5 = p_109492_ - 1; $$5 <= p_109492_ + 1; ++$$5) {
                    this.m_109770_($$4, $$5, $$3);
                }
            }
        }
    }

    public void m_109770_(int p_109771_, int p_109772_, int p_109773_) {
        this.m_109501_(p_109771_, p_109772_, p_109773_, false);
    }

    private void m_109501_(int p_109502_, int p_109503_, int p_109504_, boolean p_109505_) {
        this.f_109469_.m_110859_(p_109502_, p_109503_, p_109504_, p_109505_);
    }

    public void m_109514_(@Nullable SoundEvent p_109515_, BlockPos p_109516_) {
        SoundInstance $$2 = this.f_109410_.get(p_109516_);
        if ($$2 != null) {
            this.f_109461_.m_91106_().m_120399_($$2);
            this.f_109410_.remove(p_109516_);
        }
        if (p_109515_ != null) {
            RecordItem $$3 = RecordItem.m_43040_(p_109515_);
            if ($$3 != null) {
                this.f_109461_.f_91065_.m_93055_($$3.m_43050_());
            }
            $$2 = SimpleSoundInstance.m_119747_(p_109515_, p_109516_.m_123341_(), p_109516_.m_123342_(), p_109516_.m_123343_());
            this.f_109410_.put(p_109516_, $$2);
            this.f_109461_.m_91106_().m_120367_($$2);
        }
        this.m_109550_(this.f_109465_, p_109516_, p_109515_ != null);
    }

    private void m_109550_(Level p_109551_, BlockPos p_109552_, boolean p_109553_) {
        List<LivingEntity> $$3 = p_109551_.m_45976_(LivingEntity.class, new AABB(p_109552_).m_82400_(3.0));
        for (LivingEntity $$4 : $$3) {
            $$4.m_6818_(p_109552_, p_109553_);
        }
    }

    public void m_109743_(ParticleOptions p_109744_, boolean p_109745_, double p_109746_, double p_109747_, double p_109748_, double p_109749_, double p_109750_, double p_109751_) {
        this.m_109752_(p_109744_, p_109745_, false, p_109746_, p_109747_, p_109748_, p_109749_, p_109750_, p_109751_);
    }

    public void m_109752_(ParticleOptions p_109753_, boolean p_109754_, boolean p_109755_, double p_109756_, double p_109757_, double p_109758_, double p_109759_, double p_109760_, double p_109761_) {
        try {
            this.m_109804_(p_109753_, p_109754_, p_109755_, p_109756_, p_109757_, p_109758_, p_109759_, p_109760_, p_109761_);
        }
        catch (Throwable $$9) {
            CrashReport $$10 = CrashReport.m_127521_($$9, "Exception while adding particle");
            CrashReportCategory $$11 = $$10.m_127514_("Particle being added");
            $$11.m_128159_("ID", Registry.f_122829_.m_7981_(p_109753_.m_6012_()));
            $$11.m_128159_("Parameters", p_109753_.m_5942_());
            $$11.m_128165_("Position", () -> CrashReportCategory.m_178937_(this.f_109465_, p_109756_, p_109757_, p_109758_));
            throw new ReportedException($$10);
        }
    }

    private <T extends ParticleOptions> void m_109735_(T p_109736_, double p_109737_, double p_109738_, double p_109739_, double p_109740_, double p_109741_, double p_109742_) {
        this.m_109743_(p_109736_, p_109736_.m_6012_().m_123742_(), p_109737_, p_109738_, p_109739_, p_109740_, p_109741_, p_109742_);
    }

    @Nullable
    private Particle m_109795_(ParticleOptions p_109796_, boolean p_109797_, double p_109798_, double p_109799_, double p_109800_, double p_109801_, double p_109802_, double p_109803_) {
        return this.m_109804_(p_109796_, p_109797_, false, p_109798_, p_109799_, p_109800_, p_109801_, p_109802_, p_109803_);
    }

    @Nullable
    private Particle m_109804_(ParticleOptions p_109805_, boolean p_109806_, boolean p_109807_, double p_109808_, double p_109809_, double p_109810_, double p_109811_, double p_109812_, double p_109813_) {
        Camera $$9 = this.f_109461_.f_91063_.m_109153_();
        if (this.f_109461_ == null || !$$9.m_90593_() || this.f_109461_.f_91061_ == null) {
            return null;
        }
        ParticleStatus $$10 = this.m_109767_(p_109807_);
        if (p_109806_) {
            return this.f_109461_.f_91061_.m_107370_(p_109805_, p_109808_, p_109809_, p_109810_, p_109811_, p_109812_, p_109813_);
        }
        if ($$9.m_90583_().m_82531_(p_109808_, p_109809_, p_109810_) > 1024.0) {
            return null;
        }
        if ($$10 == ParticleStatus.MINIMAL) {
            return null;
        }
        return this.f_109461_.f_91061_.m_107370_(p_109805_, p_109808_, p_109809_, p_109810_, p_109811_, p_109812_, p_109813_);
    }

    private ParticleStatus m_109767_(boolean p_109768_) {
        ParticleStatus $$1 = this.f_109461_.f_91066_.m_231929_().m_231551_();
        if (p_109768_ && $$1 == ParticleStatus.MINIMAL && this.f_109465_.f_46441_.m_188503_(10) == 0) {
            $$1 = ParticleStatus.DECREASED;
        }
        if ($$1 == ParticleStatus.DECREASED && this.f_109465_.f_46441_.m_188503_(3) == 0) {
            $$1 = ParticleStatus.MINIMAL;
        }
        return $$1;
    }

    public void m_109824_() {
    }

    public void m_109506_(int p_109507_, BlockPos p_109508_, int p_109509_) {
        switch (p_109507_) {
            case 1023: 
            case 1028: 
            case 1038: {
                Camera $$3 = this.f_109461_.f_91063_.m_109153_();
                if (!$$3.m_90593_()) break;
                double $$4 = (double)p_109508_.m_123341_() - $$3.m_90583_().f_82479_;
                double $$5 = (double)p_109508_.m_123342_() - $$3.m_90583_().f_82480_;
                double $$6 = (double)p_109508_.m_123343_() - $$3.m_90583_().f_82481_;
                double $$7 = Math.sqrt($$4 * $$4 + $$5 * $$5 + $$6 * $$6);
                double $$8 = $$3.m_90583_().f_82479_;
                double $$9 = $$3.m_90583_().f_82480_;
                double $$10 = $$3.m_90583_().f_82481_;
                if ($$7 > 0.0) {
                    $$8 += $$4 / $$7 * 2.0;
                    $$9 += $$5 / $$7 * 2.0;
                    $$10 += $$6 / $$7 * 2.0;
                }
                if (p_109507_ == 1023) {
                    this.f_109465_.m_7785_($$8, $$9, $$10, SoundEvents.f_12563_, SoundSource.HOSTILE, 1.0f, 1.0f, false);
                    break;
                }
                if (p_109507_ == 1038) {
                    this.f_109465_.m_7785_($$8, $$9, $$10, SoundEvents.f_11860_, SoundSource.HOSTILE, 1.0f, 1.0f, false);
                    break;
                }
                this.f_109465_.m_7785_($$8, $$9, $$10, SoundEvents.f_11891_, SoundSource.HOSTILE, 5.0f, 1.0f, false);
            }
        }
    }

    public void m_234304_(int p_234305_, BlockPos p_234306_, int p_234307_) {
        RandomSource $$3 = this.f_109465_.f_46441_;
        switch (p_234305_) {
            case 1035: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11772_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1033: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11756_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1034: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11755_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1032: {
                this.f_109461_.m_91106_().m_120367_(SimpleSoundInstance.m_119766_(SoundEvents.f_12287_, $$3.m_188501_() * 0.4f + 0.8f, 0.25f));
                break;
            }
            case 1001: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11797_, SoundSource.BLOCKS, 1.0f, 1.2f, false);
                break;
            }
            case 1000: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11796_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 1003: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11898_, SoundSource.NEUTRAL, 1.0f, 1.2f, false);
                break;
            }
            case 1004: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11933_, SoundSource.NEUTRAL, 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11798_, SoundSource.BLOCKS, 1.0f, 1.2f, false);
                break;
            }
            case 2000: {
                Direction $$4 = Direction.m_122376_(p_234307_);
                int $$5 = $$4.m_122429_();
                int $$6 = $$4.m_122430_();
                int $$7 = $$4.m_122431_();
                double $$8 = (double)p_234306_.m_123341_() + (double)$$5 * 0.6 + 0.5;
                double $$9 = (double)p_234306_.m_123342_() + (double)$$6 * 0.6 + 0.5;
                double $$10 = (double)p_234306_.m_123343_() + (double)$$7 * 0.6 + 0.5;
                for (int $$11 = 0; $$11 < 10; ++$$11) {
                    double $$12 = $$3.m_188500_() * 0.2 + 0.01;
                    double $$13 = $$8 + (double)$$5 * 0.01 + ($$3.m_188500_() - 0.5) * (double)$$7 * 0.5;
                    double $$14 = $$9 + (double)$$6 * 0.01 + ($$3.m_188500_() - 0.5) * (double)$$6 * 0.5;
                    double $$15 = $$10 + (double)$$7 * 0.01 + ($$3.m_188500_() - 0.5) * (double)$$5 * 0.5;
                    double $$16 = (double)$$5 * $$12 + $$3.m_188583_() * 0.01;
                    double $$17 = (double)$$6 * $$12 + $$3.m_188583_() * 0.01;
                    double $$18 = (double)$$7 * $$12 + $$3.m_188583_() * 0.01;
                    this.m_109735_(ParticleTypes.f_123762_, $$13, $$14, $$15, $$16, $$17, $$18);
                }
                break;
            }
            case 2003: {
                double $$19 = (double)p_234306_.m_123341_() + 0.5;
                double $$20 = p_234306_.m_123342_();
                double $$21 = (double)p_234306_.m_123343_() + 0.5;
                for (int $$22 = 0; $$22 < 8; ++$$22) {
                    this.m_109735_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(Items.f_42545_)), $$19, $$20, $$21, $$3.m_188583_() * 0.15, $$3.m_188500_() * 0.2, $$3.m_188583_() * 0.15);
                }
                for (double $$23 = 0.0; $$23 < Math.PI * 2; $$23 += 0.15707963267948966) {
                    this.m_109735_(ParticleTypes.f_123760_, $$19 + Math.cos($$23) * 5.0, $$20 - 0.4, $$21 + Math.sin($$23) * 5.0, Math.cos($$23) * -5.0, 0.0, Math.sin($$23) * -5.0);
                    this.m_109735_(ParticleTypes.f_123760_, $$19 + Math.cos($$23) * 5.0, $$20 - 0.4, $$21 + Math.sin($$23) * 5.0, Math.cos($$23) * -7.0, 0.0, Math.sin($$23) * -7.0);
                }
                break;
            }
            case 2002: 
            case 2007: {
                Vec3 $$24 = Vec3.m_82539_(p_234306_);
                for (int $$25 = 0; $$25 < 8; ++$$25) {
                    this.m_109735_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(Items.f_42736_)), $$24.f_82479_, $$24.f_82480_, $$24.f_82481_, $$3.m_188583_() * 0.15, $$3.m_188500_() * 0.2, $$3.m_188583_() * 0.15);
                }
                float $$26 = (float)(p_234307_ >> 16 & 0xFF) / 255.0f;
                float $$27 = (float)(p_234307_ >> 8 & 0xFF) / 255.0f;
                float $$28 = (float)(p_234307_ >> 0 & 0xFF) / 255.0f;
                SimpleParticleType $$29 = p_234305_ == 2007 ? ParticleTypes.f_123751_ : ParticleTypes.f_123806_;
                for (int $$30 = 0; $$30 < 100; ++$$30) {
                    double $$31 = $$3.m_188500_() * 4.0;
                    double $$32 = $$3.m_188500_() * Math.PI * 2.0;
                    double $$33 = Math.cos($$32) * $$31;
                    double $$34 = 0.01 + $$3.m_188500_() * 0.5;
                    double $$35 = Math.sin($$32) * $$31;
                    Particle $$36 = this.m_109795_($$29, $$29.m_6012_().m_123742_(), $$24.f_82479_ + $$33 * 0.1, $$24.f_82480_ + 0.3, $$24.f_82481_ + $$35 * 0.1, $$33, $$34, $$35);
                    if ($$36 == null) continue;
                    float $$37 = 0.75f + $$3.m_188501_() * 0.25f;
                    $$36.m_107253_($$26 * $$37, $$27 * $$37, $$28 * $$37);
                    $$36.m_107268_((float)$$31);
                }
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12436_, SoundSource.NEUTRAL, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 2001: {
                BlockState $$38 = Block.m_49803_(p_234307_);
                if (!$$38.m_60795_()) {
                    SoundType $$39 = $$38.m_60827_();
                    this.f_109465_.m_104677_(p_234306_, $$39.m_56775_(), SoundSource.BLOCKS, ($$39.m_56773_() + 1.0f) / 2.0f, $$39.m_56774_() * 0.8f, false);
                }
                this.f_109465_.m_142052_(p_234306_, $$38);
                break;
            }
            case 2004: {
                for (int $$40 = 0; $$40 < 20; ++$$40) {
                    double $$41 = (double)p_234306_.m_123341_() + 0.5 + ($$3.m_188500_() - 0.5) * 2.0;
                    double $$42 = (double)p_234306_.m_123342_() + 0.5 + ($$3.m_188500_() - 0.5) * 2.0;
                    double $$43 = (double)p_234306_.m_123343_() + 0.5 + ($$3.m_188500_() - 0.5) * 2.0;
                    this.f_109465_.m_7106_(ParticleTypes.f_123762_, $$41, $$42, $$43, 0.0, 0.0, 0.0);
                    this.f_109465_.m_7106_(ParticleTypes.f_123744_, $$41, $$42, $$43, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 2005: {
                BoneMealItem.m_40638_(this.f_109465_, p_234306_, p_234307_);
                break;
            }
            case 1505: {
                BoneMealItem.m_40638_(this.f_109465_, p_234306_, p_234307_);
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_144074_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 3002: {
                if (p_234307_ >= 0 && p_234307_ < Direction.Axis.f_122448_.length) {
                    ParticleUtils.m_144967_(Direction.Axis.f_122448_[p_234307_], this.f_109465_, p_234306_, 0.125, ParticleTypes.f_175830_, UniformInt.m_146622_(10, 19));
                    break;
                }
                ParticleUtils.m_216313_(this.f_109465_, p_234306_, ParticleTypes.f_175830_, UniformInt.m_146622_(3, 5));
                break;
            }
            case 3006: {
                int $$44 = p_234307_ >> 6;
                if ($$44 > 0) {
                    if ($$3.m_188501_() < 0.3f + (float)$$44 * 0.1f) {
                        float $$45 = 0.15f + 0.02f * (float)$$44 * (float)$$44 * $$3.m_188501_();
                        float $$46 = 0.4f + 0.3f * (float)$$44 * $$3.m_188501_();
                        this.f_109465_.m_104677_(p_234306_, SoundEvents.f_215734_, SoundSource.BLOCKS, $$45, $$46, false);
                    }
                    byte $$47 = (byte)(p_234307_ & 0x3F);
                    UniformInt $$48 = UniformInt.m_146622_(0, $$44);
                    float $$49 = 0.005f;
                    Supplier<Vec3> $$50 = () -> new Vec3(Mth.m_216263_($$3, -0.005f, 0.005f), Mth.m_216263_($$3, -0.005f, 0.005f), Mth.m_216263_($$3, -0.005f, 0.005f));
                    if ($$47 == 0) {
                        for (Direction $$51 : Direction.values()) {
                            float $$52 = $$51 == Direction.DOWN ? (float)Math.PI : 0.0f;
                            double $$53 = $$51.m_122434_() == Direction.Axis.Y ? 0.65 : 0.57;
                            ParticleUtils.m_216318_(this.f_109465_, p_234306_, new SculkChargeParticleOptions($$52), $$48, $$51, $$50, $$53);
                        }
                    } else {
                        for (Direction $$54 : MultifaceBlock.m_221569_($$47)) {
                            float $$55 = $$54 == Direction.UP ? (float)Math.PI : 0.0f;
                            double $$56 = 0.35;
                            ParticleUtils.m_216318_(this.f_109465_, p_234306_, new SculkChargeParticleOptions($$55), $$48, $$54, $$50, 0.35);
                        }
                    }
                } else {
                    this.f_109465_.m_104677_(p_234306_, SoundEvents.f_215734_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                    boolean $$57 = this.f_109465_.m_8055_(p_234306_).m_60838_(this.f_109465_, p_234306_);
                    int $$58 = $$57 ? 40 : 20;
                    float $$59 = $$57 ? 0.45f : 0.25f;
                    float $$60 = 0.07f;
                    for (int $$61 = 0; $$61 < $$58; ++$$61) {
                        float $$62 = 2.0f * $$3.m_188501_() - 1.0f;
                        float $$63 = 2.0f * $$3.m_188501_() - 1.0f;
                        float $$64 = 2.0f * $$3.m_188501_() - 1.0f;
                        this.f_109465_.m_7106_(ParticleTypes.f_235900_, (double)p_234306_.m_123341_() + 0.5 + (double)($$62 * $$59), (double)p_234306_.m_123342_() + 0.5 + (double)($$63 * $$59), (double)p_234306_.m_123343_() + 0.5 + (double)($$64 * $$59), $$62 * 0.07f, $$63 * 0.07f, $$64 * 0.07f);
                    }
                }
                break;
            }
            case 3007: {
                for (int $$65 = 0; $$65 < 10; ++$$65) {
                    this.f_109465_.m_6493_(new ShriekParticleOption($$65 * 5), false, (double)p_234306_.m_123341_() + 0.5, (double)p_234306_.m_123342_() + SculkShriekerBlock.f_222156_, (double)p_234306_.m_123343_() + 0.5, 0.0, 0.0, 0.0);
                }
                this.f_109465_.m_7785_((double)p_234306_.m_123341_() + 0.5, (double)p_234306_.m_123342_() + SculkShriekerBlock.f_222156_, (double)p_234306_.m_123343_() + 0.5, SoundEvents.f_215750_, SoundSource.BLOCKS, 2.0f, 0.6f + this.f_109465_.f_46441_.m_188501_() * 0.4f, false);
                break;
            }
            case 3003: {
                ParticleUtils.m_216313_(this.f_109465_, p_234306_, ParticleTypes.f_175828_, UniformInt.m_146622_(3, 5));
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_144178_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                break;
            }
            case 3004: {
                ParticleUtils.m_216313_(this.f_109465_, p_234306_, ParticleTypes.f_175829_, UniformInt.m_146622_(3, 5));
                break;
            }
            case 3005: {
                ParticleUtils.m_216313_(this.f_109465_, p_234306_, ParticleTypes.f_175831_, UniformInt.m_146622_(3, 5));
                break;
            }
            case 2008: {
                this.f_109465_.m_7106_(ParticleTypes.f_123813_, (double)p_234306_.m_123341_() + 0.5, (double)p_234306_.m_123342_() + 0.5, (double)p_234306_.m_123343_() + 0.5, 0.0, 0.0, 0.0);
                break;
            }
            case 1500: {
                ComposterBlock.m_51923_(this.f_109465_, p_234306_, p_234307_ > 0);
                break;
            }
            case 1504: {
                PointedDripstoneBlock.m_154062_(this.f_109465_, p_234306_, this.f_109465_.m_8055_(p_234306_));
                break;
            }
            case 1501: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12031_, SoundSource.BLOCKS, 0.5f, 2.6f + ($$3.m_188501_() - $$3.m_188501_()) * 0.8f, false);
                for (int $$66 = 0; $$66 < 8; ++$$66) {
                    this.f_109465_.m_7106_(ParticleTypes.f_123755_, (double)p_234306_.m_123341_() + $$3.m_188500_(), (double)p_234306_.m_123342_() + 1.2, (double)p_234306_.m_123343_() + $$3.m_188500_(), 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1502: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12374_, SoundSource.BLOCKS, 0.5f, 2.6f + ($$3.m_188501_() - $$3.m_188501_()) * 0.8f, false);
                for (int $$67 = 0; $$67 < 5; ++$$67) {
                    double $$68 = (double)p_234306_.m_123341_() + $$3.m_188500_() * 0.6 + 0.2;
                    double $$69 = (double)p_234306_.m_123342_() + $$3.m_188500_() * 0.6 + 0.2;
                    double $$70 = (double)p_234306_.m_123343_() + $$3.m_188500_() * 0.6 + 0.2;
                    this.f_109465_.m_7106_(ParticleTypes.f_123762_, $$68, $$69, $$70, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1503: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11859_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                for (int $$71 = 0; $$71 < 16; ++$$71) {
                    double $$72 = (double)p_234306_.m_123341_() + (5.0 + $$3.m_188500_() * 6.0) / 16.0;
                    double $$73 = (double)p_234306_.m_123342_() + 0.8125;
                    double $$74 = (double)p_234306_.m_123343_() + (5.0 + $$3.m_188500_() * 6.0) / 16.0;
                    this.f_109465_.m_7106_(ParticleTypes.f_123762_, $$72, $$73, $$74, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 2006: {
                for (int $$75 = 0; $$75 < 200; ++$$75) {
                    float $$76 = $$3.m_188501_() * 4.0f;
                    float $$77 = $$3.m_188501_() * ((float)Math.PI * 2);
                    double $$78 = Mth.m_14089_($$77) * $$76;
                    double $$79 = 0.01 + $$3.m_188500_() * 0.5;
                    double $$80 = Mth.m_14031_($$77) * $$76;
                    Particle $$81 = this.m_109795_(ParticleTypes.f_123799_, false, (double)p_234306_.m_123341_() + $$78 * 0.1, (double)p_234306_.m_123342_() + 0.3, (double)p_234306_.m_123343_() + $$80 * 0.1, $$78, $$79, $$80);
                    if ($$81 == null) continue;
                    $$81.m_107268_($$76);
                }
                if (p_234307_ != 1) break;
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11892_, SoundSource.HOSTILE, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 2009: {
                for (int $$82 = 0; $$82 < 8; ++$$82) {
                    this.f_109465_.m_7106_(ParticleTypes.f_123796_, (double)p_234306_.m_123341_() + $$3.m_188500_(), (double)p_234306_.m_123342_() + 1.2, (double)p_234306_.m_123343_() + $$3.m_188500_(), 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1012: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12626_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1036: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12011_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1013: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12628_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1014: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11872_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1011: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12055_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1006: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12627_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1007: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12629_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1037: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12012_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1008: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11873_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1005: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12056_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1009: {
                if (p_234307_ == 0) {
                    this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5f, 2.6f + ($$3.m_188501_() - $$3.m_188501_()) * 0.8f, false);
                    break;
                }
                if (p_234307_ != 1) break;
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11914_, SoundSource.BLOCKS, 0.7f, 1.6f + ($$3.m_188501_() - $$3.m_188501_()) * 0.4f, false);
                break;
            }
            case 1029: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11665_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1030: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11671_, SoundSource.BLOCKS, 1.0f, $$3.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1044: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12471_, SoundSource.BLOCKS, 1.0f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1031: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11668_, SoundSource.BLOCKS, 0.3f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1039: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12228_, SoundSource.HOSTILE, 0.3f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1010: {
                if (Item.m_41445_(p_234307_) instanceof RecordItem) {
                    this.m_109514_(((RecordItem)Item.m_41445_(p_234307_)).m_43051_(), p_234306_);
                    break;
                }
                this.m_109514_(null, p_234306_);
                break;
            }
            case 1015: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11924_, SoundSource.HOSTILE, 10.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11896_, SoundSource.HOSTILE, 10.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11923_, SoundSource.HOSTILE, 10.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1019: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12599_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1022: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12555_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1021: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12601_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12600_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1018: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11705_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1024: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12558_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1026: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12609_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1027: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12616_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1040: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12602_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1041: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_12044_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1025: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11735_, SoundSource.NEUTRAL, 0.05f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
                break;
            }
            case 1042: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11998_, SoundSource.BLOCKS, 1.0f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1043: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11713_, SoundSource.BLOCKS, 1.0f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 3000: {
                this.f_109465_.m_6493_(ParticleTypes.f_123812_, true, (double)p_234306_.m_123341_() + 0.5, (double)p_234306_.m_123342_() + 0.5, (double)p_234306_.m_123343_() + 0.5, 0.0, 0.0, 0.0);
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11858_, SoundSource.BLOCKS, 10.0f, (1.0f + (this.f_109465_.f_46441_.m_188501_() - this.f_109465_.f_46441_.m_188501_()) * 0.2f) * 0.7f, false);
                break;
            }
            case 3001: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_11894_, SoundSource.HOSTILE, 64.0f, 0.8f + this.f_109465_.f_46441_.m_188501_() * 0.3f, false);
                break;
            }
            case 1045: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_144126_, SoundSource.BLOCKS, 2.0f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1046: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_144129_, SoundSource.BLOCKS, 2.0f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1047: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_144130_, SoundSource.BLOCKS, 2.0f, this.f_109465_.f_46441_.m_188501_() * 0.1f + 0.9f, false);
                break;
            }
            case 1048: {
                this.f_109465_.m_104677_(p_234306_, SoundEvents.f_144211_, SoundSource.HOSTILE, 2.0f, ($$3.m_188501_() - $$3.m_188501_()) * 0.2f + 1.0f, false);
            }
        }
    }

    public void m_109774_(int p_109775_, BlockPos p_109776_, int p_109777_) {
        if (p_109777_ < 0 || p_109777_ >= 10) {
            BlockDestructionProgress $$3 = (BlockDestructionProgress)this.f_109408_.remove(p_109775_);
            if ($$3 != null) {
                this.m_109765_($$3);
            }
        } else {
            BlockDestructionProgress $$4 = (BlockDestructionProgress)this.f_109408_.get(p_109775_);
            if ($$4 != null) {
                this.m_109765_($$4);
            }
            if ($$4 == null || $$4.m_139985_().m_123341_() != p_109776_.m_123341_() || $$4.m_139985_().m_123342_() != p_109776_.m_123342_() || $$4.m_139985_().m_123343_() != p_109776_.m_123343_()) {
                $$4 = new BlockDestructionProgress(p_109775_, p_109776_);
                this.f_109408_.put(p_109775_, (Object)$$4);
            }
            $$4.m_139981_(p_109777_);
            $$4.m_139986_(this.f_109477_);
            ((SortedSet)this.f_109409_.computeIfAbsent($$4.m_139985_().m_121878_(), p_234254_ -> Sets.newTreeSet())).add($$4);
        }
    }

    public boolean m_109825_() {
        return this.f_109436_.m_112732_();
    }

    public void m_109826_() {
        this.f_194300_ = true;
        this.f_109474_ = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_109762_(Collection<BlockEntity> p_109763_, Collection<BlockEntity> p_109764_) {
        Set<BlockEntity> set = this.f_109468_;
        synchronized (set) {
            this.f_109468_.removeAll(p_109763_);
            this.f_109468_.addAll(p_109764_);
        }
    }

    public static int m_109541_(BlockAndTintGetter p_109542_, BlockPos p_109543_) {
        return LevelRenderer.m_109537_(p_109542_, p_109542_.m_8055_(p_109543_), p_109543_);
    }

    public static int m_109537_(BlockAndTintGetter p_109538_, BlockState p_109539_, BlockPos p_109540_) {
        int $$5;
        if (p_109539_.m_60788_(p_109538_, p_109540_)) {
            return 0xF000F0;
        }
        int $$3 = p_109538_.m_45517_(LightLayer.SKY, p_109540_);
        int $$4 = p_109538_.m_45517_(LightLayer.BLOCK, p_109540_);
        if ($$4 < ($$5 = p_109539_.m_60791_())) {
            $$4 = $$5;
        }
        return $$3 << 20 | $$4 << 4;
    }

    public boolean m_202430_(BlockPos p_202431_) {
        ChunkRenderDispatcher.RenderChunk $$1 = this.f_109469_.m_110866_(p_202431_);
        return $$1 != null && $$1.f_112784_.get() != ChunkRenderDispatcher.CompiledChunk.f_112748_;
    }

    @Nullable
    public RenderTarget m_109827_() {
        return this.f_109411_;
    }

    @Nullable
    public RenderTarget m_109828_() {
        return this.f_109413_;
    }

    @Nullable
    public RenderTarget m_109829_() {
        return this.f_109414_;
    }

    @Nullable
    public RenderTarget m_109830_() {
        return this.f_109415_;
    }

    @Nullable
    public RenderTarget m_109831_() {
        return this.f_109416_;
    }

    @Nullable
    public RenderTarget m_109832_() {
        return this.f_109417_;
    }

    public static class TransparencyShaderException
    extends RuntimeException {
        public TransparencyShaderException(String p_109868_, Throwable p_109869_) {
            super(p_109868_, p_109869_);
        }
    }

    static class RenderChunkStorage {
        public final RenderInfoMap f_194375_;
        public final LinkedHashSet<RenderChunkInfo> f_194376_;

        public RenderChunkStorage(int p_194378_) {
            this.f_194375_ = new RenderInfoMap(p_194378_);
            this.f_194376_ = new LinkedHashSet(p_194378_);
        }
    }

    static class RenderChunkInfo {
        final ChunkRenderDispatcher.RenderChunk f_109839_;
        private byte f_173020_;
        byte f_109841_;
        final int f_109842_;

        RenderChunkInfo(ChunkRenderDispatcher.RenderChunk p_173022_, @Nullable Direction p_173023_, int p_173024_) {
            this.f_109839_ = p_173022_;
            if (p_173023_ != null) {
                this.m_173028_(p_173023_);
            }
            this.f_109842_ = p_173024_;
        }

        public void m_109854_(byte p_109855_, Direction p_109856_) {
            this.f_109841_ = (byte)(this.f_109841_ | (p_109855_ | 1 << p_109856_.ordinal()));
        }

        public boolean m_109859_(Direction p_109860_) {
            return (this.f_109841_ & 1 << p_109860_.ordinal()) > 0;
        }

        public void m_173028_(Direction p_173029_) {
            this.f_173020_ = (byte)(this.f_173020_ | (this.f_173020_ | 1 << p_173029_.ordinal()));
        }

        public boolean m_173026_(int p_173027_) {
            return (this.f_173020_ & 1 << p_173027_) > 0;
        }

        public boolean m_173025_() {
            return this.f_173020_ != 0;
        }

        public int hashCode() {
            return this.f_109839_.m_112839_().hashCode();
        }

        public boolean equals(Object p_194373_) {
            if (!(p_194373_ instanceof RenderChunkInfo)) {
                return false;
            }
            RenderChunkInfo $$1 = (RenderChunkInfo)p_194373_;
            return this.f_109839_.m_112839_().equals($$1.f_109839_.m_112839_());
        }
    }

    static class RenderInfoMap {
        private final RenderChunkInfo[] f_173030_;

        RenderInfoMap(int p_173033_) {
            this.f_173030_ = new RenderChunkInfo[p_173033_];
        }

        public void m_173037_(ChunkRenderDispatcher.RenderChunk p_173038_, RenderChunkInfo p_173039_) {
            this.f_173030_[p_173038_.f_173717_] = p_173039_;
        }

        @Nullable
        public RenderChunkInfo m_173035_(ChunkRenderDispatcher.RenderChunk p_173036_) {
            int $$1 = p_173036_.f_173717_;
            if ($$1 < 0 || $$1 >= this.f_173030_.length) {
                return null;
            }
            return this.f_173030_[$$1];
        }
    }
}

