/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.world.level.pathfinder.Path;

public class BeeDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private static final boolean f_173737_ = true;
    private static final boolean f_173738_ = true;
    private static final boolean f_173739_ = true;
    private static final boolean f_173740_ = true;
    private static final boolean f_173741_ = true;
    private static final boolean f_173742_ = false;
    private static final boolean f_173743_ = true;
    private static final boolean f_173744_ = true;
    private static final boolean f_173745_ = true;
    private static final boolean f_173746_ = true;
    private static final boolean f_173747_ = true;
    private static final boolean f_173748_ = true;
    private static final boolean f_173749_ = true;
    private static final boolean f_173750_ = true;
    private static final int f_173751_ = 30;
    private static final int f_173752_ = 30;
    private static final int f_173753_ = 8;
    private static final int f_173754_ = 20;
    private static final float f_173755_ = 0.02f;
    private static final int f_173756_ = -1;
    private static final int f_173757_ = -256;
    private static final int f_173758_ = -23296;
    private static final int f_173759_ = -16711936;
    private static final int f_173760_ = -3355444;
    private static final int f_173761_ = -98404;
    private static final int f_173762_ = -65536;
    private final Minecraft f_113048_;
    private final Map<BlockPos, HiveInfo> f_113049_ = Maps.newHashMap();
    private final Map<UUID, BeeInfo> f_113050_ = Maps.newHashMap();
    private UUID f_113051_;

    public BeeDebugRenderer(Minecraft p_113053_) {
        this.f_113048_ = p_113053_;
    }

    @Override
    public void m_5630_() {
        this.f_113049_.clear();
        this.f_113050_.clear();
        this.f_113051_ = null;
    }

    public void m_113071_(HiveInfo p_113072_) {
        this.f_113049_.put(p_113072_.f_113180_, p_113072_);
    }

    public void m_113066_(BeeInfo p_113067_) {
        this.f_113050_.put(p_113067_.f_113157_, p_113067_);
    }

    public void m_173763_(int p_173764_) {
        this.f_113050_.values().removeIf(p_173767_ -> p_173767_.f_113158_ == p_173764_);
    }

    @Override
    public void m_7790_(PoseStack p_113061_, MultiBufferSource p_113062_, double p_113063_, double p_113064_, double p_113065_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69472_();
        this.m_113136_();
        this.m_113126_();
        this.m_113141_();
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
        if (!this.f_113048_.f_91074_.m_5833_()) {
            this.m_113156_();
        }
    }

    private void m_113126_() {
        this.f_113050_.entrySet().removeIf(p_113132_ -> this.f_113048_.f_91073_.m_6815_(((BeeInfo)p_113132_.getValue()).f_113158_) == null);
    }

    private void m_113136_() {
        long $$0 = this.f_113048_.f_91073_.m_46467_() - 20L;
        this.f_113049_.entrySet().removeIf(p_113057_ -> ((HiveInfo)p_113057_.getValue()).f_113185_ < $$0);
    }

    private void m_113141_() {
        BlockPos $$0 = this.m_113154_().m_90588_();
        this.f_113050_.values().forEach(p_113153_ -> {
            if (this.m_113147_((BeeInfo)p_113153_)) {
                this.m_113137_((BeeInfo)p_113153_);
            }
        });
        this.m_113151_();
        for (BlockPos $$1 : this.f_113049_.keySet()) {
            if (!$$0.m_123314_($$1, 30.0)) continue;
            BeeDebugRenderer.m_113076_($$1);
        }
        Map<BlockPos, Set<UUID>> $$2 = this.m_113146_();
        this.f_113049_.values().forEach(p_113098_ -> {
            if ($$0.m_123314_(p_113098_.f_113180_, 30.0)) {
                Set $$3 = (Set)$$2.get(p_113098_.f_113180_);
                this.m_113073_((HiveInfo)p_113098_, $$3 == null ? Sets.newHashSet() : $$3);
            }
        });
        this.m_113155_().forEach((p_113090_, p_113091_) -> {
            if ($$0.m_123314_((Vec3i)p_113090_, 30.0)) {
                this.m_113092_((BlockPos)p_113090_, (List<String>)p_113091_);
            }
        });
    }

    private Map<BlockPos, Set<UUID>> m_113146_() {
        HashMap $$0 = Maps.newHashMap();
        this.f_113050_.values().forEach(p_113135_ -> p_113135_.f_113165_.forEach(p_173771_ -> $$0.computeIfAbsent(p_173771_, p_173777_ -> Sets.newHashSet()).add(p_113135_.m_113174_())));
        return $$0;
    }

    private void m_113151_() {
        HashMap $$0 = Maps.newHashMap();
        this.f_113050_.values().stream().filter(BeeInfo::m_113178_).forEach(p_113121_ -> $$0.computeIfAbsent(p_113121_.f_113162_, p_173775_ -> Sets.newHashSet()).add(p_113121_.m_113174_()));
        $$0.entrySet().forEach(p_113118_ -> {
            BlockPos $$1 = (BlockPos)p_113118_.getKey();
            Set $$2 = (Set)p_113118_.getValue();
            Set $$3 = $$2.stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
            int $$4 = 1;
            BeeDebugRenderer.m_113110_($$3.toString(), $$1, $$4++, -256);
            BeeDebugRenderer.m_113110_("Flower", $$1, $$4++, -1);
            float $$5 = 0.05f;
            BeeDebugRenderer.m_113078_($$1, 0.05f, 0.8f, 0.8f, 0.0f, 0.3f);
        });
    }

    private static String m_113115_(Collection<UUID> p_113116_) {
        if (p_113116_.isEmpty()) {
            return "-";
        }
        if (p_113116_.size() > 3) {
            return p_113116_.size() + " bees";
        }
        return p_113116_.stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet()).toString();
    }

    private static void m_113076_(BlockPos p_113077_) {
        float $$1 = 0.05f;
        BeeDebugRenderer.m_113078_(p_113077_, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
    }

    private void m_113092_(BlockPos p_113093_, List<String> p_113094_) {
        float $$2 = 0.05f;
        BeeDebugRenderer.m_113078_(p_113093_, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
        BeeDebugRenderer.m_113110_("" + p_113094_, p_113093_, 0, -256);
        BeeDebugRenderer.m_113110_("Ghost Hive", p_113093_, 1, -65536);
    }

    private static void m_113078_(BlockPos p_113079_, float p_113080_, float p_113081_, float p_113082_, float p_113083_, float p_113084_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        DebugRenderer.m_113463_(p_113079_, p_113080_, p_113081_, p_113082_, p_113083_, p_113084_);
    }

    private void m_113073_(HiveInfo p_113074_, Collection<UUID> p_113075_) {
        int $$2 = 0;
        if (!p_113075_.isEmpty()) {
            BeeDebugRenderer.m_113105_("Blacklisted by " + BeeDebugRenderer.m_113115_(p_113075_), p_113074_, $$2++, -65536);
        }
        BeeDebugRenderer.m_113105_("Out: " + BeeDebugRenderer.m_113115_(this.m_113129_(p_113074_.f_113180_)), p_113074_, $$2++, -3355444);
        if (p_113074_.f_113182_ == 0) {
            BeeDebugRenderer.m_113105_("In: -", p_113074_, $$2++, -256);
        } else if (p_113074_.f_113182_ == 1) {
            BeeDebugRenderer.m_113105_("In: 1 bee", p_113074_, $$2++, -256);
        } else {
            BeeDebugRenderer.m_113105_("In: " + p_113074_.f_113182_ + " bees", p_113074_, $$2++, -256);
        }
        BeeDebugRenderer.m_113105_("Honey: " + p_113074_.f_113183_, p_113074_, $$2++, -23296);
        BeeDebugRenderer.m_113105_(p_113074_.f_113181_ + (p_113074_.f_113184_ ? " (sedated)" : ""), p_113074_, $$2++, -1);
    }

    private void m_113127_(BeeInfo p_113128_) {
        if (p_113128_.f_113160_ != null) {
            PathfindingRenderer.m_113620_(p_113128_.f_113160_, 0.5f, false, false, this.m_113154_().m_90583_().m_7096_(), this.m_113154_().m_90583_().m_7098_(), this.m_113154_().m_90583_().m_7094_());
        }
    }

    private void m_113137_(BeeInfo p_113138_) {
        boolean $$1 = this.m_113142_(p_113138_);
        int $$2 = 0;
        BeeDebugRenderer.m_113099_(p_113138_.f_113159_, $$2++, p_113138_.toString(), -1, 0.03f);
        if (p_113138_.f_113161_ == null) {
            BeeDebugRenderer.m_113099_(p_113138_.f_113159_, $$2++, "No hive", -98404, 0.02f);
        } else {
            BeeDebugRenderer.m_113099_(p_113138_.f_113159_, $$2++, "Hive: " + this.m_113068_(p_113138_, p_113138_.f_113161_), -256, 0.02f);
        }
        if (p_113138_.f_113162_ == null) {
            BeeDebugRenderer.m_113099_(p_113138_.f_113159_, $$2++, "No flower", -98404, 0.02f);
        } else {
            BeeDebugRenderer.m_113099_(p_113138_.f_113159_, $$2++, "Flower: " + this.m_113068_(p_113138_, p_113138_.f_113162_), -256, 0.02f);
        }
        for (String $$3 : p_113138_.f_113164_) {
            BeeDebugRenderer.m_113099_(p_113138_.f_113159_, $$2++, $$3, -16711936, 0.02f);
        }
        if ($$1) {
            this.m_113127_(p_113138_);
        }
        if (p_113138_.f_113163_ > 0) {
            int $$4 = p_113138_.f_113163_ < 600 ? -3355444 : -23296;
            BeeDebugRenderer.m_113099_(p_113138_.f_113159_, $$2++, "Travelling: " + p_113138_.f_113163_ + " ticks", $$4, 0.02f);
        }
    }

    private static void m_113105_(String p_113106_, HiveInfo p_113107_, int p_113108_, int p_113109_) {
        BlockPos $$4 = p_113107_.f_113180_;
        BeeDebugRenderer.m_113110_(p_113106_, $$4, p_113108_, p_113109_);
    }

    private static void m_113110_(String p_113111_, BlockPos p_113112_, int p_113113_, int p_113114_) {
        double $$4 = 1.3;
        double $$5 = 0.2;
        double $$6 = (double)p_113112_.m_123341_() + 0.5;
        double $$7 = (double)p_113112_.m_123342_() + 1.3 + (double)p_113113_ * 0.2;
        double $$8 = (double)p_113112_.m_123343_() + 0.5;
        DebugRenderer.m_113490_(p_113111_, $$6, $$7, $$8, p_113114_, 0.02f, true, 0.0f, true);
    }

    private static void m_113099_(Position p_113100_, int p_113101_, String p_113102_, int p_113103_, float p_113104_) {
        double $$5 = 2.4;
        double $$6 = 0.25;
        BlockPos $$7 = new BlockPos(p_113100_);
        double $$8 = (double)$$7.m_123341_() + 0.5;
        double $$9 = p_113100_.m_7098_() + 2.4 + (double)p_113101_ * 0.25;
        double $$10 = (double)$$7.m_123343_() + 0.5;
        float $$11 = 0.5f;
        DebugRenderer.m_113490_(p_113102_, $$8, $$9, $$10, p_113103_, p_113104_, false, 0.5f, true);
    }

    private Camera m_113154_() {
        return this.f_113048_.f_91063_.m_109153_();
    }

    private Set<String> m_173772_(HiveInfo p_173773_) {
        return this.m_113129_(p_173773_.f_113180_).stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
    }

    private String m_113068_(BeeInfo p_113069_, BlockPos p_113070_) {
        double $$2 = Math.sqrt(p_113070_.m_203193_(p_113069_.f_113159_));
        double $$3 = (double)Math.round($$2 * 10.0) / 10.0;
        return p_113070_.m_123344_() + " (dist " + $$3 + ")";
    }

    private boolean m_113142_(BeeInfo p_113143_) {
        return Objects.equals(this.f_113051_, p_113143_.f_113157_);
    }

    private boolean m_113147_(BeeInfo p_113148_) {
        LocalPlayer $$1 = this.f_113048_.f_91074_;
        BlockPos $$2 = new BlockPos($$1.m_20185_(), p_113148_.f_113159_.m_7098_(), $$1.m_20189_());
        BlockPos $$3 = new BlockPos(p_113148_.f_113159_);
        return $$2.m_123314_($$3, 30.0);
    }

    private Collection<UUID> m_113129_(BlockPos p_113130_) {
        return this.f_113050_.values().stream().filter(p_113087_ -> p_113087_.m_113175_(p_113130_)).map(BeeInfo::m_113174_).collect(Collectors.toSet());
    }

    private Map<BlockPos, List<String>> m_113155_() {
        HashMap $$0 = Maps.newHashMap();
        for (BeeInfo $$1 : this.f_113050_.values()) {
            if ($$1.f_113161_ == null || this.f_113049_.containsKey($$1.f_113161_)) continue;
            $$0.computeIfAbsent($$1.f_113161_, p_113140_ -> Lists.newArrayList()).add($$1.m_113177_());
        }
        return $$0;
    }

    private void m_113156_() {
        DebugRenderer.m_113448_(this.f_113048_.m_91288_(), 8).ifPresent(p_113059_ -> {
            this.f_113051_ = p_113059_.m_20148_();
        });
    }

    public static class HiveInfo {
        public final BlockPos f_113180_;
        public final String f_113181_;
        public final int f_113182_;
        public final int f_113183_;
        public final boolean f_113184_;
        public final long f_113185_;

        public HiveInfo(BlockPos p_113187_, String p_113188_, int p_113189_, int p_113190_, boolean p_113191_, long p_113192_) {
            this.f_113180_ = p_113187_;
            this.f_113181_ = p_113188_;
            this.f_113182_ = p_113189_;
            this.f_113183_ = p_113190_;
            this.f_113184_ = p_113191_;
            this.f_113185_ = p_113192_;
        }
    }

    public static class BeeInfo {
        public final UUID f_113157_;
        public final int f_113158_;
        public final Position f_113159_;
        @Nullable
        public final Path f_113160_;
        @Nullable
        public final BlockPos f_113161_;
        @Nullable
        public final BlockPos f_113162_;
        public final int f_113163_;
        public final List<String> f_113164_ = Lists.newArrayList();
        public final Set<BlockPos> f_113165_ = Sets.newHashSet();

        public BeeInfo(UUID p_113167_, int p_113168_, Position p_113169_, @Nullable Path p_113170_, @Nullable BlockPos p_113171_, @Nullable BlockPos p_113172_, int p_113173_) {
            this.f_113157_ = p_113167_;
            this.f_113158_ = p_113168_;
            this.f_113159_ = p_113169_;
            this.f_113160_ = p_113170_;
            this.f_113161_ = p_113171_;
            this.f_113162_ = p_113172_;
            this.f_113163_ = p_113173_;
        }

        public boolean m_113175_(BlockPos p_113176_) {
            return this.f_113161_ != null && this.f_113161_.equals(p_113176_);
        }

        public UUID m_113174_() {
            return this.f_113157_;
        }

        public String m_113177_() {
            return DebugEntityNameGenerator.m_133668_(this.f_113157_);
        }

        public String toString() {
            return this.m_113177_();
        }

        public boolean m_113178_() {
            return this.f_113162_ != null;
        }
    }
}

