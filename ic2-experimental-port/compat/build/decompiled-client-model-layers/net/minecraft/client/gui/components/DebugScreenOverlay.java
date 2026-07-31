/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DataFixUtils
 *  it.unimi.dsi.fastutil.longs.LongSets
 *  it.unimi.dsi.fastutil.longs.LongSets$EmptySet
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DebugScreenOverlay
extends GuiComponent {
    private static final int f_168988_ = 0xE0E0E0;
    private static final int f_168989_ = 2;
    private static final int f_168990_ = 2;
    private static final int f_168991_ = 2;
    private static final Map<Heightmap.Types, String> f_94029_ = Util.m_137469_(new EnumMap(Heightmap.Types.class), p_94070_ -> {
        p_94070_.put(Heightmap.Types.WORLD_SURFACE_WG, "SW");
        p_94070_.put(Heightmap.Types.WORLD_SURFACE, "S");
        p_94070_.put(Heightmap.Types.OCEAN_FLOOR_WG, "OW");
        p_94070_.put(Heightmap.Types.OCEAN_FLOOR, "O");
        p_94070_.put(Heightmap.Types.MOTION_BLOCKING, "M");
        p_94070_.put(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, "ML");
    });
    private final Minecraft f_94030_;
    private final AllocationRateCalculator f_232506_;
    private final Font f_94031_;
    private HitResult f_94032_;
    private HitResult f_94033_;
    @Nullable
    private ChunkPos f_94034_;
    @Nullable
    private LevelChunk f_94035_;
    @Nullable
    private CompletableFuture<LevelChunk> f_94036_;
    private static final int f_168992_ = -65536;
    private static final int f_168993_ = -256;
    private static final int f_168994_ = -16711936;

    public DebugScreenOverlay(Minecraft p_94039_) {
        this.f_94030_ = p_94039_;
        this.f_232506_ = new AllocationRateCalculator();
        this.f_94031_ = p_94039_.f_91062_;
    }

    public void m_94040_() {
        this.f_94036_ = null;
        this.f_94035_ = null;
    }

    public void m_94056_(PoseStack p_94057_) {
        this.f_94030_.m_91307_().m_6180_("debug");
        Entity $$1 = this.f_94030_.m_91288_();
        this.f_94032_ = $$1.m_19907_(20.0, 0.0f, false);
        this.f_94033_ = $$1.m_19907_(20.0, 0.0f, true);
        this.m_94076_(p_94057_);
        this.m_94079_(p_94057_);
        if (this.f_94030_.f_91066_.f_92065_) {
            int $$2 = this.f_94030_.m_91268_().m_85445_();
            this.m_94058_(p_94057_, this.f_94030_.m_91293_(), 0, $$2 / 2, true);
            IntegratedServer $$3 = this.f_94030_.m_91092_();
            if ($$3 != null) {
                this.m_94058_(p_94057_, $$3.m_129904_(), $$2 - Math.min($$2 / 2, 240), $$2 / 2, false);
            }
        }
        this.f_94030_.m_91307_().m_7238_();
    }

    protected void m_94076_(PoseStack p_94077_) {
        List<String> $$1 = this.m_94075_();
        $$1.add("");
        boolean $$2 = this.f_94030_.m_91092_() != null;
        $$1.add("Debug: Pie [shift]: " + (this.f_94030_.f_91066_.f_92064_ ? "visible" : "hidden") + ($$2 ? " FPS + TPS" : " FPS") + " [alt]: " + (this.f_94030_.f_91066_.f_92065_ ? "visible" : "hidden"));
        $$1.add("For help: press F3 + Q");
        for (int $$3 = 0; $$3 < $$1.size(); ++$$3) {
            String $$4 = $$1.get($$3);
            if (Strings.isNullOrEmpty((String)$$4)) continue;
            int $$5 = this.f_94031_.f_92710_;
            int $$6 = this.f_94031_.m_92895_($$4);
            int $$7 = 2;
            int $$8 = 2 + $$5 * $$3;
            DebugScreenOverlay.m_93172_(p_94077_, 1, $$8 - 1, 2 + $$6 + 1, $$8 + $$5 - 1, -1873784752);
            this.f_94031_.m_92883_(p_94077_, $$4, 2.0f, $$8, 0xE0E0E0);
        }
    }

    protected void m_94079_(PoseStack p_94080_) {
        List<String> $$1 = this.m_94078_();
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            String $$3 = $$1.get($$2);
            if (Strings.isNullOrEmpty((String)$$3)) continue;
            int $$4 = this.f_94031_.f_92710_;
            int $$5 = this.f_94031_.m_92895_($$3);
            int $$6 = this.f_94030_.m_91268_().m_85445_() - 2 - $$5;
            int $$7 = 2 + $$4 * $$2;
            DebugScreenOverlay.m_93172_(p_94080_, $$6 - 1, $$7 - 1, $$6 + $$5 + 1, $$7 + $$4 - 1, -1873784752);
            this.f_94031_.m_92883_(p_94080_, $$3, $$6, $$7, 0xE0E0E0);
        }
    }

    protected List<String> m_94075_() {
        PostChain $$39;
        Level $$15;
        String $$13;
        String $$5;
        IntegratedServer $$0 = this.f_94030_.m_91092_();
        Connection $$1 = this.f_94030_.m_91403_().m_6198_();
        float $$2 = $$1.m_129543_();
        float $$3 = $$1.m_129542_();
        if ($$0 != null) {
            String $$4 = String.format(Locale.ROOT, "Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", Float.valueOf($$0.m_129903_()), Float.valueOf($$2), Float.valueOf($$3));
        } else {
            $$5 = String.format(Locale.ROOT, "\"%s\" server, %.0f tx, %.0f rx", this.f_94030_.f_91074_.m_108629_(), Float.valueOf($$2), Float.valueOf($$3));
        }
        BlockPos $$6 = this.f_94030_.m_91288_().m_20183_();
        if (this.f_94030_.m_91299_()) {
            return Lists.newArrayList((Object[])new String[]{"Minecraft " + SharedConstants.m_183709_().getName() + " (" + this.f_94030_.m_91388_() + "/" + ClientBrandRetriever.m_129629_() + ")", this.f_94030_.f_90977_, $$5, this.f_94030_.f_91060_.m_109820_(), this.f_94030_.f_91060_.m_109822_(), "P: " + this.f_94030_.f_91061_.m_107403_() + ". T: " + this.f_94030_.f_91073_.m_104813_(), this.f_94030_.f_91073_.m_46464_(), "", String.format(Locale.ROOT, "Chunk-relative: %d %d %d", $$6.m_123341_() & 0xF, $$6.m_123342_() & 0xF, $$6.m_123343_() & 0xF)});
        }
        Entity $$7 = this.f_94030_.m_91288_();
        Direction $$8 = $$7.m_6350_();
        switch ($$8) {
            case NORTH: {
                String $$9 = "Towards negative Z";
                break;
            }
            case SOUTH: {
                String $$10 = "Towards positive Z";
                break;
            }
            case WEST: {
                String $$11 = "Towards negative X";
                break;
            }
            case EAST: {
                String $$12 = "Towards positive X";
                break;
            }
            default: {
                $$13 = "Invalid";
            }
        }
        ChunkPos $$14 = new ChunkPos($$6);
        if (!Objects.equals(this.f_94034_, $$14)) {
            this.f_94034_ = $$14;
            this.m_94040_();
        }
        LongSets.EmptySet $$16 = ($$15 = this.m_94083_()) instanceof ServerLevel ? ((ServerLevel)$$15).m_8902_() : LongSets.EMPTY_SET;
        ArrayList $$17 = Lists.newArrayList((Object[])new String[]{"Minecraft " + SharedConstants.m_183709_().getName() + " (" + this.f_94030_.m_91388_() + "/" + ClientBrandRetriever.m_129629_() + (String)("release".equalsIgnoreCase(this.f_94030_.m_91389_()) ? "" : "/" + this.f_94030_.m_91389_()) + ")", this.f_94030_.f_90977_, $$5, this.f_94030_.f_91060_.m_109820_(), this.f_94030_.f_91060_.m_109822_(), "P: " + this.f_94030_.f_91061_.m_107403_() + ". T: " + this.f_94030_.f_91073_.m_104813_(), this.f_94030_.f_91073_.m_46464_()});
        String $$18 = this.m_94082_();
        if ($$18 != null) {
            $$17.add($$18);
        }
        $$17.add(this.f_94030_.f_91073_.m_46472_().m_135782_() + " FC: " + $$16.size());
        $$17.add("");
        $$17.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.f_94030_.m_91288_().m_20185_(), this.f_94030_.m_91288_().m_20186_(), this.f_94030_.m_91288_().m_20189_()));
        $$17.add(String.format(Locale.ROOT, "Block: %d %d %d [%d %d %d]", $$6.m_123341_(), $$6.m_123342_(), $$6.m_123343_(), $$6.m_123341_() & 0xF, $$6.m_123342_() & 0xF, $$6.m_123343_() & 0xF));
        $$17.add(String.format(Locale.ROOT, "Chunk: %d %d %d [%d %d in r.%d.%d.mca]", $$14.f_45578_, SectionPos.m_123171_($$6.m_123342_()), $$14.f_45579_, $$14.m_45613_(), $$14.m_45614_(), $$14.m_45610_(), $$14.m_45612_()));
        $$17.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", $$8, $$13, Float.valueOf(Mth.m_14177_($$7.m_146908_())), Float.valueOf(Mth.m_14177_($$7.m_146909_()))));
        LevelChunk $$19 = this.m_94085_();
        if ($$19.m_6430_()) {
            $$17.add("Waiting for chunk...");
        } else {
            int $$20 = this.f_94030_.f_91073_.m_7726_().m_7827_().m_75831_($$6, 0);
            int $$21 = this.f_94030_.f_91073_.m_45517_(LightLayer.SKY, $$6);
            int $$22 = this.f_94030_.f_91073_.m_45517_(LightLayer.BLOCK, $$6);
            $$17.add("Client Light: " + $$20 + " (" + $$21 + " sky, " + $$22 + " block)");
            LevelChunk $$23 = this.m_94084_();
            StringBuilder $$24 = new StringBuilder("CH");
            for (Heightmap.Types $$25 : Heightmap.Types.values()) {
                if (!$$25.m_64297_()) continue;
                $$24.append(" ").append(f_94029_.get($$25)).append(": ").append($$19.m_5885_($$25, $$6.m_123341_(), $$6.m_123343_()));
            }
            $$17.add($$24.toString());
            $$24.setLength(0);
            $$24.append("SH");
            for (Heightmap.Types $$26 : Heightmap.Types.values()) {
                if (!$$26.m_64298_()) continue;
                $$24.append(" ").append(f_94029_.get($$26)).append(": ");
                if ($$23 != null) {
                    $$24.append($$23.m_5885_($$26, $$6.m_123341_(), $$6.m_123343_()));
                    continue;
                }
                $$24.append("??");
            }
            $$17.add($$24.toString());
            if ($$6.m_123342_() >= this.f_94030_.f_91073_.m_141937_() && $$6.m_123342_() < this.f_94030_.f_91073_.m_151558_()) {
                $$17.add("Biome: " + DebugScreenOverlay.m_205374_(this.f_94030_.f_91073_.m_204166_($$6)));
                long $$27 = 0L;
                float $$28 = 0.0f;
                if ($$23 != null) {
                    $$28 = $$15.m_46940_();
                    $$27 = $$23.m_6319_();
                }
                DifficultyInstance $$29 = new DifficultyInstance($$15.m_46791_(), $$15.m_46468_(), $$27, $$28);
                $$17.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", Float.valueOf($$29.m_19056_()), Float.valueOf($$29.m_19057_()), this.f_94030_.f_91073_.m_46468_() / 24000L));
            }
            if ($$23 != null && $$23.m_187675_()) {
                $$17.add("Blending: Old");
            }
        }
        ServerLevel $$30 = this.m_94081_();
        if ($$30 != null) {
            ServerChunkCache $$31 = $$30.m_7726_();
            ChunkGenerator $$32 = $$31.m_8481_();
            RandomState $$33 = $$31.m_214994_();
            $$32.m_213600_($$17, $$33, $$6);
            Climate.Sampler $$34 = $$33.m_224579_();
            BiomeSource $$35 = $$32.m_62218_();
            $$35.m_207301_($$17, $$6, $$34);
            NaturalSpawner.SpawnState $$36 = $$31.m_8485_();
            if ($$36 != null) {
                Object2IntMap<MobCategory> $$37 = $$36.m_47148_();
                int $$38 = $$36.m_47126_();
                $$17.add("SC: " + $$38 + ", " + Stream.of(MobCategory.values()).map(p_94068_ -> Character.toUpperCase(p_94068_.m_21607_().charAt(0)) + ": " + $$37.getInt(p_94068_)).collect(Collectors.joining(", ")));
            } else {
                $$17.add("SC: N/A");
            }
        }
        if (($$39 = this.f_94030_.f_91063_.m_109149_()) != null) {
            $$17.add("Shader: " + $$39.m_110022_());
        }
        $$17.add(this.f_94030_.m_91106_().m_120408_() + String.format(Locale.ROOT, " (Mood %d%%)", Math.round(this.f_94030_.f_91074_.m_108762_() * 100.0f)));
        return $$17;
    }

    private static String m_205374_(Holder<Biome> p_205375_) {
        return (String)p_205375_.m_203439_().map(p_205377_ -> p_205377_.m_135782_().toString(), p_205367_ -> "[unregistered " + p_205367_ + "]");
    }

    @Nullable
    private ServerLevel m_94081_() {
        IntegratedServer $$0 = this.f_94030_.m_91092_();
        if ($$0 != null) {
            return $$0.m_129880_(this.f_94030_.f_91073_.m_46472_());
        }
        return null;
    }

    @Nullable
    private String m_94082_() {
        ServerLevel $$0 = this.m_94081_();
        if ($$0 != null) {
            return $$0.m_46464_();
        }
        return null;
    }

    private Level m_94083_() {
        return (Level)DataFixUtils.orElse(Optional.ofNullable(this.f_94030_.m_91092_()).flatMap(p_205373_ -> Optional.ofNullable(p_205373_.m_129880_(this.f_94030_.f_91073_.m_46472_()))), (Object)this.f_94030_.f_91073_);
    }

    @Nullable
    private LevelChunk m_94084_() {
        if (this.f_94036_ == null) {
            ServerLevel $$0 = this.m_94081_();
            if ($$0 != null) {
                this.f_94036_ = $$0.m_7726_().m_8431_(this.f_94034_.f_45578_, this.f_94034_.f_45579_, ChunkStatus.f_62326_, false).thenApply(p_205369_ -> (LevelChunk)p_205369_.map(p_205371_ -> (LevelChunk)p_205371_, p_205363_ -> null));
            }
            if (this.f_94036_ == null) {
                this.f_94036_ = CompletableFuture.completedFuture(this.m_94085_());
            }
        }
        return this.f_94036_.getNow(null);
    }

    private LevelChunk m_94085_() {
        if (this.f_94035_ == null) {
            this.f_94035_ = this.f_94030_.f_91073_.m_6325_(this.f_94034_.f_45578_, this.f_94034_.f_45579_);
        }
        return this.f_94035_;
    }

    protected List<String> m_94078_() {
        Entity $$11;
        long $$0 = Runtime.getRuntime().maxMemory();
        long $$1 = Runtime.getRuntime().totalMemory();
        long $$2 = Runtime.getRuntime().freeMemory();
        long $$3 = $$1 - $$2;
        ArrayList $$4 = Lists.newArrayList((Object[])new String[]{String.format(Locale.ROOT, "Java: %s %dbit", System.getProperty("java.version"), this.f_94030_.m_91103_() ? 64 : 32), String.format(Locale.ROOT, "Mem: % 2d%% %03d/%03dMB", $$3 * 100L / $$0, DebugScreenOverlay.m_94050_($$3), DebugScreenOverlay.m_94050_($$0)), String.format(Locale.ROOT, "Allocation rate: %03dMB /s", DebugScreenOverlay.m_94050_(this.f_232506_.m_232516_($$3))), String.format(Locale.ROOT, "Allocated: % 2d%% %03dMB", $$1 * 100L / $$0, DebugScreenOverlay.m_94050_($$1)), "", String.format(Locale.ROOT, "CPU: %s", GlUtil.m_84819_()), "", String.format(Locale.ROOT, "Display: %dx%d (%s)", Minecraft.m_91087_().m_91268_().m_85441_(), Minecraft.m_91087_().m_91268_().m_85442_(), GlUtil.m_84818_()), GlUtil.m_84820_(), GlUtil.m_84821_()});
        if (this.f_94030_.m_91299_()) {
            return $$4;
        }
        if (this.f_94032_.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos $$5 = ((BlockHitResult)this.f_94032_).m_82425_();
            BlockState $$6 = this.f_94030_.f_91073_.m_8055_($$5);
            $$4.add("");
            $$4.add(ChatFormatting.UNDERLINE + "Targeted Block: " + $$5.m_123341_() + ", " + $$5.m_123342_() + ", " + $$5.m_123343_());
            $$4.add(String.valueOf(Registry.f_122824_.m_7981_($$6.m_60734_())));
            for (Map.Entry $$7 : $$6.m_61148_().entrySet()) {
                $$4.add(this.m_94071_($$7));
            }
            $$6.m_204343_().map(p_205379_ -> "#" + p_205379_.f_203868_()).forEach($$4::add);
        }
        if (this.f_94033_.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos $$8 = ((BlockHitResult)this.f_94033_).m_82425_();
            FluidState $$9 = this.f_94030_.f_91073_.m_6425_($$8);
            $$4.add("");
            $$4.add(ChatFormatting.UNDERLINE + "Targeted Fluid: " + $$8.m_123341_() + ", " + $$8.m_123342_() + ", " + $$8.m_123343_());
            $$4.add(String.valueOf(Registry.f_122822_.m_7981_($$9.m_76152_())));
            for (Map.Entry $$10 : $$9.m_61148_().entrySet()) {
                $$4.add(this.m_94071_($$10));
            }
            $$9.m_205075_().map(p_205365_ -> "#" + p_205365_.f_203868_()).forEach($$4::add);
        }
        if (($$11 = this.f_94030_.f_91076_) != null) {
            $$4.add("");
            $$4.add(ChatFormatting.UNDERLINE + "Targeted Entity");
            $$4.add(String.valueOf(Registry.f_122826_.m_7981_($$11.m_6095_())));
        }
        return $$4;
    }

    private String m_94071_(Map.Entry<Property<?>, Comparable<?>> p_94072_) {
        Property<?> $$1 = p_94072_.getKey();
        Comparable<?> $$2 = p_94072_.getValue();
        Object $$3 = Util.m_137453_($$1, $$2);
        if (Boolean.TRUE.equals($$2)) {
            $$3 = ChatFormatting.GREEN + (String)$$3;
        } else if (Boolean.FALSE.equals($$2)) {
            $$3 = ChatFormatting.RED + (String)$$3;
        }
        return $$1.m_61708_() + ": " + (String)$$3;
    }

    private void m_94058_(PoseStack p_94059_, FrameTimer p_94060_, int p_94061_, int p_94062_, boolean p_94063_) {
        RenderSystem.m_69465_();
        int $$5 = p_94060_.m_13754_();
        int $$6 = p_94060_.m_13761_();
        long[] $$7 = p_94060_.m_13764_();
        int $$8 = $$5;
        int $$9 = p_94061_;
        int $$10 = Math.max(0, $$7.length - p_94062_);
        int $$11 = $$7.length - $$10;
        $$8 = p_94060_.m_13762_($$8 + $$10);
        long $$12 = 0L;
        int $$13 = Integer.MAX_VALUE;
        int $$14 = Integer.MIN_VALUE;
        for (int $$15 = 0; $$15 < $$11; ++$$15) {
            int $$16 = (int)($$7[p_94060_.m_13762_($$8 + $$15)] / 1000000L);
            $$13 = Math.min($$13, $$16);
            $$14 = Math.max($$14, $$16);
            $$12 += (long)$$16;
        }
        int $$17 = this.f_94030_.m_91268_().m_85446_();
        DebugScreenOverlay.m_93172_(p_94059_, p_94061_, $$17 - 60, p_94061_ + $$11, $$17, -1873784752);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        BufferBuilder $$18 = Tesselator.m_85913_().m_85915_();
        RenderSystem.m_69478_();
        RenderSystem.m_69472_();
        RenderSystem.m_69453_();
        $$18.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Matrix4f $$19 = Transformation.m_121093_().m_121104_();
        while ($$8 != $$6) {
            int $$20 = p_94060_.m_13757_($$7[$$8], p_94063_ ? 30 : 60, p_94063_ ? 60 : 20);
            int $$21 = p_94063_ ? 100 : 60;
            int $$22 = this.m_94045_(Mth.m_14045_($$20, 0, $$21), 0, $$21 / 2, $$21);
            int $$23 = $$22 >> 24 & 0xFF;
            int $$24 = $$22 >> 16 & 0xFF;
            int $$25 = $$22 >> 8 & 0xFF;
            int $$26 = $$22 & 0xFF;
            $$18.m_85982_($$19, $$9 + 1, $$17, 0.0f).m_6122_($$24, $$25, $$26, $$23).m_5752_();
            $$18.m_85982_($$19, $$9 + 1, $$17 - $$20 + 1, 0.0f).m_6122_($$24, $$25, $$26, $$23).m_5752_();
            $$18.m_85982_($$19, $$9, $$17 - $$20 + 1, 0.0f).m_6122_($$24, $$25, $$26, $$23).m_5752_();
            $$18.m_85982_($$19, $$9, $$17, 0.0f).m_6122_($$24, $$25, $$26, $$23).m_5752_();
            ++$$9;
            $$8 = p_94060_.m_13762_($$8 + 1);
        }
        BufferUploader.m_231202_($$18.m_231175_());
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
        if (p_94063_) {
            DebugScreenOverlay.m_93172_(p_94059_, p_94061_ + 1, $$17 - 30 + 1, p_94061_ + 14, $$17 - 30 + 10, -1873784752);
            this.f_94031_.m_92883_(p_94059_, "60 FPS", p_94061_ + 2, $$17 - 30 + 2, 0xE0E0E0);
            this.m_93154_(p_94059_, p_94061_, p_94061_ + $$11 - 1, $$17 - 30, -1);
            DebugScreenOverlay.m_93172_(p_94059_, p_94061_ + 1, $$17 - 60 + 1, p_94061_ + 14, $$17 - 60 + 10, -1873784752);
            this.f_94031_.m_92883_(p_94059_, "30 FPS", p_94061_ + 2, $$17 - 60 + 2, 0xE0E0E0);
            this.m_93154_(p_94059_, p_94061_, p_94061_ + $$11 - 1, $$17 - 60, -1);
        } else {
            DebugScreenOverlay.m_93172_(p_94059_, p_94061_ + 1, $$17 - 60 + 1, p_94061_ + 14, $$17 - 60 + 10, -1873784752);
            this.f_94031_.m_92883_(p_94059_, "20 TPS", p_94061_ + 2, $$17 - 60 + 2, 0xE0E0E0);
            this.m_93154_(p_94059_, p_94061_, p_94061_ + $$11 - 1, $$17 - 60, -1);
        }
        this.m_93154_(p_94059_, p_94061_, p_94061_ + $$11 - 1, $$17 - 1, -1);
        this.m_93222_(p_94059_, p_94061_, $$17 - 60, $$17, -1);
        this.m_93222_(p_94059_, p_94061_ + $$11 - 1, $$17 - 60, $$17, -1);
        int $$27 = this.f_94030_.f_91066_.m_232035_().m_231551_();
        if (p_94063_ && $$27 > 0 && $$27 <= 250) {
            this.m_93154_(p_94059_, p_94061_, p_94061_ + $$11 - 1, $$17 - 1 - (int)(1800.0 / (double)$$27), -16711681);
        }
        String $$28 = $$13 + " ms min";
        String $$29 = $$12 / (long)$$11 + " ms avg";
        String $$30 = $$14 + " ms max";
        this.f_94031_.m_92750_(p_94059_, $$28, p_94061_ + 2, $$17 - 60 - this.f_94031_.f_92710_, 0xE0E0E0);
        this.f_94031_.m_92750_(p_94059_, $$29, p_94061_ + $$11 / 2 - this.f_94031_.m_92895_($$29) / 2, $$17 - 60 - this.f_94031_.f_92710_, 0xE0E0E0);
        this.f_94031_.m_92750_(p_94059_, $$30, p_94061_ + $$11 - this.f_94031_.m_92895_($$30), $$17 - 60 - this.f_94031_.f_92710_, 0xE0E0E0);
        RenderSystem.m_69482_();
    }

    private int m_94045_(int p_94046_, int p_94047_, int p_94048_, int p_94049_) {
        if (p_94046_ < p_94048_) {
            return this.m_94041_(-16711936, -256, (float)p_94046_ / (float)p_94048_);
        }
        return this.m_94041_(-256, -65536, (float)(p_94046_ - p_94048_) / (float)(p_94049_ - p_94048_));
    }

    private int m_94041_(int p_94042_, int p_94043_, float p_94044_) {
        int $$3 = p_94042_ >> 24 & 0xFF;
        int $$4 = p_94042_ >> 16 & 0xFF;
        int $$5 = p_94042_ >> 8 & 0xFF;
        int $$6 = p_94042_ & 0xFF;
        int $$7 = p_94043_ >> 24 & 0xFF;
        int $$8 = p_94043_ >> 16 & 0xFF;
        int $$9 = p_94043_ >> 8 & 0xFF;
        int $$10 = p_94043_ & 0xFF;
        int $$11 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, $$3, $$7), 0, 255);
        int $$12 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, $$4, $$8), 0, 255);
        int $$13 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, $$5, $$9), 0, 255);
        int $$14 = Mth.m_14045_((int)Mth.m_14179_(p_94044_, $$6, $$10), 0, 255);
        return $$11 << 24 | $$12 << 16 | $$13 << 8 | $$14;
    }

    private static long m_94050_(long p_94051_) {
        return p_94051_ / 1024L / 1024L;
    }

    static class AllocationRateCalculator {
        private static final int f_232507_ = 500;
        private static final List<GarbageCollectorMXBean> f_232508_ = ManagementFactory.getGarbageCollectorMXBeans();
        private long f_232509_ = 0L;
        private long f_232510_ = -1L;
        private long f_232511_ = -1L;
        private long f_232512_ = 0L;

        AllocationRateCalculator() {
        }

        long m_232516_(long p_232517_) {
            long $$1 = System.currentTimeMillis();
            if ($$1 - this.f_232509_ < 500L) {
                return this.f_232512_;
            }
            long $$2 = AllocationRateCalculator.m_232515_();
            if (this.f_232509_ != 0L && $$2 == this.f_232511_) {
                double $$3 = (double)TimeUnit.SECONDS.toMillis(1L) / (double)($$1 - this.f_232509_);
                long $$4 = p_232517_ - this.f_232510_;
                this.f_232512_ = Math.round((double)$$4 * $$3);
            }
            this.f_232509_ = $$1;
            this.f_232510_ = p_232517_;
            this.f_232511_ = $$2;
            return this.f_232512_;
        }

        private static long m_232515_() {
            long $$0 = 0L;
            for (GarbageCollectorMXBean $$1 : f_232508_) {
                $$0 += $$1.getCollectionCount();
            }
            return $$0;
        }
    }
}

