/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.pathfinder.Path;
import org.slf4j.Logger;

public class BrainDebugRenderer
implements DebugRenderer.SimpleDebugRenderer {
    private static final Logger f_113193_ = LogUtils.getLogger();
    private static final boolean f_173785_ = true;
    private static final boolean f_173786_ = false;
    private static final boolean f_173787_ = false;
    private static final boolean f_173788_ = false;
    private static final boolean f_173789_ = false;
    private static final boolean f_173790_ = false;
    private static final boolean f_173791_ = false;
    private static final boolean f_173792_ = false;
    private static final boolean f_173793_ = true;
    private static final boolean f_234493_ = false;
    private static final boolean f_173794_ = true;
    private static final boolean f_173795_ = true;
    private static final boolean f_173796_ = true;
    private static final boolean f_173797_ = true;
    private static final boolean f_173798_ = true;
    private static final boolean f_173799_ = true;
    private static final boolean f_173800_ = true;
    private static final boolean f_173801_ = true;
    private static final boolean f_173802_ = true;
    private static final boolean f_173803_ = true;
    private static final boolean f_234494_ = true;
    private static final boolean f_173804_ = true;
    private static final int f_173805_ = 30;
    private static final int f_173806_ = 30;
    private static final int f_173807_ = 8;
    private static final float f_173808_ = 0.02f;
    private static final int f_173809_ = -1;
    private static final int f_173778_ = -256;
    private static final int f_173779_ = -16711681;
    private static final int f_173780_ = -16711936;
    private static final int f_173781_ = -3355444;
    private static final int f_173782_ = -98404;
    private static final int f_173783_ = -65536;
    private static final int f_173784_ = -23296;
    private final Minecraft f_113194_;
    private final Map<BlockPos, PoiInfo> f_113195_ = Maps.newHashMap();
    private final Map<UUID, BrainDump> f_113196_ = Maps.newHashMap();
    @Nullable
    private UUID f_113197_;

    public BrainDebugRenderer(Minecraft p_113200_) {
        this.f_113194_ = p_113200_;
    }

    @Override
    public void m_5630_() {
        this.f_113195_.clear();
        this.f_113196_.clear();
        this.f_113197_ = null;
    }

    public void m_113226_(PoiInfo p_113227_) {
        this.f_113195_.put(p_113227_.f_113333_, p_113227_);
    }

    public void m_113228_(BlockPos p_113229_) {
        this.f_113195_.remove(p_113229_);
    }

    public void m_113230_(BlockPos p_113231_, int p_113232_) {
        PoiInfo $$2 = this.f_113195_.get(p_113231_);
        if ($$2 == null) {
            f_113193_.warn("Strange, setFreeTicketCount was called for an unknown POI: {}", (Object)p_113231_);
            return;
        }
        $$2.f_113335_ = p_113232_;
    }

    public void m_113219_(BrainDump p_113220_) {
        this.f_113196_.put(p_113220_.f_113293_, p_113220_);
    }

    public void m_173810_(int p_173811_) {
        this.f_113196_.values().removeIf(p_173814_ -> p_173814_.f_113294_ == p_173811_);
    }

    @Override
    public void m_7790_(PoseStack p_113214_, MultiBufferSource p_113215_, double p_113216_, double p_113217_, double p_113218_) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69472_();
        this.m_113264_();
        this.m_113202_(p_113216_, p_113217_, p_113218_);
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
        if (!this.f_113194_.f_91074_.m_5833_()) {
            this.m_113286_();
        }
    }

    private void m_113264_() {
        this.f_113196_.entrySet().removeIf(p_113263_ -> {
            Entity $$1 = this.f_113194_.f_91073_.m_6815_(((BrainDump)p_113263_.getValue()).f_113294_);
            return $$1 == null || $$1.m_213877_();
        });
    }

    private void m_113202_(double p_113203_, double p_113204_, double p_113205_) {
        BlockPos $$3 = new BlockPos(p_113203_, p_113204_, p_113205_);
        this.f_113196_.values().forEach(p_113210_ -> {
            if (this.m_113280_((BrainDump)p_113210_)) {
                this.m_113267_((BrainDump)p_113210_, p_113203_, p_113204_, p_113205_);
            }
        });
        for (BlockPos $$4 : this.f_113195_.keySet()) {
            if (!$$3.m_123314_($$4, 30.0)) continue;
            BrainDebugRenderer.m_113274_($$4);
        }
        this.f_113195_.values().forEach(p_113238_ -> {
            if ($$3.m_123314_(p_113238_.f_113333_, 30.0)) {
                this.m_113272_((PoiInfo)p_113238_);
            }
        });
        this.m_113279_().forEach((p_113241_, p_113242_) -> {
            if ($$3.m_123314_((Vec3i)p_113241_, 30.0)) {
                this.m_113243_((BlockPos)p_113241_, (List<String>)p_113242_);
            }
        });
    }

    private static void m_113274_(BlockPos p_113275_) {
        float $$1 = 0.05f;
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        DebugRenderer.m_113463_(p_113275_, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
    }

    private void m_113243_(BlockPos p_113244_, List<String> p_113245_) {
        float $$2 = 0.05f;
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        DebugRenderer.m_113463_(p_113244_, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
        BrainDebugRenderer.m_113257_("" + p_113245_, p_113244_, 0, -256);
        BrainDebugRenderer.m_113257_("Ghost POI", p_113244_, 1, -65536);
    }

    private void m_113272_(PoiInfo p_113273_) {
        int $$1 = 0;
        Set<String> $$2 = this.m_113282_(p_113273_);
        if ($$2.size() < 4) {
            BrainDebugRenderer.m_113252_("Owners: " + $$2, p_113273_, $$1, -256);
        } else {
            BrainDebugRenderer.m_113252_($$2.size() + " ticket holders", p_113273_, $$1, -256);
        }
        ++$$1;
        Set<String> $$3 = this.m_113287_(p_113273_);
        if ($$3.size() < 4) {
            BrainDebugRenderer.m_113252_("Candidates: " + $$3, p_113273_, $$1, -23296);
        } else {
            BrainDebugRenderer.m_113252_($$3.size() + " potential owners", p_113273_, $$1, -23296);
        }
        BrainDebugRenderer.m_113252_("Free tickets: " + p_113273_.f_113335_, p_113273_, ++$$1, -256);
        BrainDebugRenderer.m_113252_(p_113273_.f_113334_, p_113273_, ++$$1, -1);
    }

    private void m_113221_(BrainDump p_113222_, double p_113223_, double p_113224_, double p_113225_) {
        if (p_113222_.f_113302_ != null) {
            PathfindingRenderer.m_113620_(p_113222_.f_113302_, 0.5f, false, false, p_113223_, p_113224_, p_113225_);
        }
    }

    private void m_113267_(BrainDump p_113268_, double p_113269_, double p_113270_, double p_113271_) {
        boolean $$4 = this.m_113265_(p_113268_);
        int $$5 = 0;
        BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, p_113268_.f_113295_, -1, 0.03f);
        ++$$5;
        if ($$4) {
            BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, p_113268_.f_113296_ + " " + p_113268_.f_113297_ + " xp", -1, 0.02f);
            ++$$5;
        }
        if ($$4) {
            int $$6 = p_113268_.f_113298_ < p_113268_.f_113299_ ? -23296 : -1;
            BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, "health: " + String.format(Locale.ROOT, "%.1f", Float.valueOf(p_113268_.f_113298_)) + " / " + String.format(Locale.ROOT, "%.1f", Float.valueOf(p_113268_.f_113299_)), $$6, 0.02f);
            ++$$5;
        }
        if ($$4 && !p_113268_.f_113301_.equals("")) {
            BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, p_113268_.f_113301_, -98404, 0.02f);
            ++$$5;
        }
        if ($$4) {
            for (String $$7 : p_113268_.f_113305_) {
                BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, $$7, -16711681, 0.02f);
                ++$$5;
            }
        }
        if ($$4) {
            for (String $$8 : p_113268_.f_113304_) {
                BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, $$8, -16711936, 0.02f);
                ++$$5;
            }
        }
        if (p_113268_.f_113303_) {
            BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, "Wants Golem", -23296, 0.02f);
            ++$$5;
        }
        if ($$4 && p_113268_.f_234495_ != -1) {
            BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, "Anger Level: " + p_113268_.f_234495_, -98404, 0.02f);
            ++$$5;
        }
        if ($$4) {
            for (String $$9 : p_113268_.f_113307_) {
                if ($$9.startsWith(p_113268_.f_113295_)) {
                    BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, $$9, -1, 0.02f);
                } else {
                    BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, $$9, -23296, 0.02f);
                }
                ++$$5;
            }
        }
        if ($$4) {
            for (String $$10 : Lists.reverse(p_113268_.f_113306_)) {
                BrainDebugRenderer.m_113246_(p_113268_.f_113300_, $$5, $$10, -3355444, 0.02f);
                ++$$5;
            }
        }
        if ($$4) {
            this.m_113221_(p_113268_, p_113269_, p_113270_, p_113271_);
        }
    }

    private static void m_113252_(String p_113253_, PoiInfo p_113254_, int p_113255_, int p_113256_) {
        BlockPos $$4 = p_113254_.f_113333_;
        BrainDebugRenderer.m_113257_(p_113253_, $$4, p_113255_, p_113256_);
    }

    private static void m_113257_(String p_113258_, BlockPos p_113259_, int p_113260_, int p_113261_) {
        double $$4 = 1.3;
        double $$5 = 0.2;
        double $$6 = (double)p_113259_.m_123341_() + 0.5;
        double $$7 = (double)p_113259_.m_123342_() + 1.3 + (double)p_113260_ * 0.2;
        double $$8 = (double)p_113259_.m_123343_() + 0.5;
        DebugRenderer.m_113490_(p_113258_, $$6, $$7, $$8, p_113261_, 0.02f, true, 0.0f, true);
    }

    private static void m_113246_(Position p_113247_, int p_113248_, String p_113249_, int p_113250_, float p_113251_) {
        double $$5 = 2.4;
        double $$6 = 0.25;
        BlockPos $$7 = new BlockPos(p_113247_);
        double $$8 = (double)$$7.m_123341_() + 0.5;
        double $$9 = p_113247_.m_7098_() + 2.4 + (double)p_113248_ * 0.25;
        double $$10 = (double)$$7.m_123343_() + 0.5;
        float $$11 = 0.5f;
        DebugRenderer.m_113490_(p_113249_, $$8, $$9, $$10, p_113250_, p_113251_, false, 0.5f, true);
    }

    private Set<String> m_113282_(PoiInfo p_113283_) {
        return this.m_113284_(p_113283_.f_113333_).stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
    }

    private Set<String> m_113287_(PoiInfo p_113288_) {
        return this.m_113289_(p_113288_.f_113333_).stream().map(DebugEntityNameGenerator::m_133668_).collect(Collectors.toSet());
    }

    private boolean m_113265_(BrainDump p_113266_) {
        return Objects.equals(this.f_113197_, p_113266_.f_113293_);
    }

    private boolean m_113280_(BrainDump p_113281_) {
        LocalPlayer $$1 = this.f_113194_.f_91074_;
        BlockPos $$2 = new BlockPos($$1.m_20185_(), p_113281_.f_113300_.m_7098_(), $$1.m_20189_());
        BlockPos $$3 = new BlockPos(p_113281_.f_113300_);
        return $$2.m_123314_($$3, 30.0);
    }

    private Collection<UUID> m_113284_(BlockPos p_113285_) {
        return this.f_113196_.values().stream().filter(p_113278_ -> p_113278_.m_113326_(p_113285_)).map(BrainDump::m_113322_).collect(Collectors.toSet());
    }

    private Collection<UUID> m_113289_(BlockPos p_113290_) {
        return this.f_113196_.values().stream().filter(p_113235_ -> p_113235_.m_113331_(p_113290_)).map(BrainDump::m_113322_).collect(Collectors.toSet());
    }

    private Map<BlockPos, List<String>> m_113279_() {
        HashMap $$0 = Maps.newHashMap();
        for (BrainDump $$1 : this.f_113196_.values()) {
            for (BlockPos $$2 : Iterables.concat($$1.f_113308_, $$1.f_113309_)) {
                if (this.f_113195_.containsKey($$2)) continue;
                $$0.computeIfAbsent($$2, p_113292_ -> Lists.newArrayList()).add($$1.f_113295_);
            }
        }
        return $$0;
    }

    private void m_113286_() {
        DebugRenderer.m_113448_(this.f_113194_.m_91288_(), 8).ifPresent(p_113212_ -> {
            this.f_113197_ = p_113212_.m_20148_();
        });
    }

    public static class PoiInfo {
        public final BlockPos f_113333_;
        public String f_113334_;
        public int f_113335_;

        public PoiInfo(BlockPos p_113337_, String p_113338_, int p_113339_) {
            this.f_113333_ = p_113337_;
            this.f_113334_ = p_113338_;
            this.f_113335_ = p_113339_;
        }
    }

    public static class BrainDump {
        public final UUID f_113293_;
        public final int f_113294_;
        public final String f_113295_;
        public final String f_113296_;
        public final int f_113297_;
        public final float f_113298_;
        public final float f_113299_;
        public final Position f_113300_;
        public final String f_113301_;
        public final Path f_113302_;
        public final boolean f_113303_;
        public final int f_234495_;
        public final List<String> f_113304_ = Lists.newArrayList();
        public final List<String> f_113305_ = Lists.newArrayList();
        public final List<String> f_113306_ = Lists.newArrayList();
        public final List<String> f_113307_ = Lists.newArrayList();
        public final Set<BlockPos> f_113308_ = Sets.newHashSet();
        public final Set<BlockPos> f_113309_ = Sets.newHashSet();

        public BrainDump(UUID p_234497_, int p_234498_, String p_234499_, String p_234500_, int p_234501_, float p_234502_, float p_234503_, Position p_234504_, String p_234505_, @Nullable Path p_234506_, boolean p_234507_, int p_234508_) {
            this.f_113293_ = p_234497_;
            this.f_113294_ = p_234498_;
            this.f_113295_ = p_234499_;
            this.f_113296_ = p_234500_;
            this.f_113297_ = p_234501_;
            this.f_113298_ = p_234502_;
            this.f_113299_ = p_234503_;
            this.f_113300_ = p_234504_;
            this.f_113301_ = p_234505_;
            this.f_113302_ = p_234506_;
            this.f_113303_ = p_234507_;
            this.f_234495_ = p_234508_;
        }

        boolean m_113326_(BlockPos p_113327_) {
            return this.f_113308_.stream().anyMatch(p_113327_::equals);
        }

        boolean m_113331_(BlockPos p_113332_) {
            return this.f_113309_.contains(p_113332_);
        }

        public UUID m_113322_() {
            return this.f_113293_;
        }
    }
}

