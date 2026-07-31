/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.ai.village.poi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.slf4j.Logger;

public class PoiSection {
    private static final Logger f_27260_ = LogUtils.getLogger();
    private final Short2ObjectMap<PoiRecord> f_27261_ = new Short2ObjectOpenHashMap();
    private final Map<Holder<PoiType>, Set<PoiRecord>> f_27262_ = Maps.newHashMap();
    private final Runnable f_27263_;
    private boolean f_27264_;

    public static Codec<PoiSection> m_27295_(Runnable p_27296_) {
        return RecordCodecBuilder.create(p_27299_ -> p_27299_.group((App)RecordCodecBuilder.point((Object)p_27296_), (App)Codec.BOOL.optionalFieldOf("Valid", (Object)false).forGetter(p_148681_ -> p_148681_.f_27264_), (App)PoiRecord.m_27242_(p_27296_).listOf().fieldOf("Records").forGetter(p_148675_ -> ImmutableList.copyOf((Collection)p_148675_.f_27261_.values()))).apply((Applicative)p_27299_, PoiSection::new)).orElseGet(Util.m_137489_("Failed to read POI section: ", arg_0 -> ((Logger)f_27260_).error(arg_0)), () -> new PoiSection(p_27296_, false, (List<PoiRecord>)ImmutableList.of()));
    }

    public PoiSection(Runnable p_27267_) {
        this(p_27267_, true, (List<PoiRecord>)ImmutableList.of());
    }

    private PoiSection(Runnable p_27269_, boolean p_27270_, List<PoiRecord> p_27271_) {
        this.f_27263_ = p_27269_;
        this.f_27264_ = p_27270_;
        p_27271_.forEach(this::m_27273_);
    }

    public Stream<PoiRecord> m_27304_(Predicate<Holder<PoiType>> p_27305_, PoiManager.Occupancy p_27306_) {
        return this.f_27262_.entrySet().stream().filter(p_27309_ -> p_27305_.test((Holder)p_27309_.getKey())).flatMap(p_27301_ -> ((Set)p_27301_.getValue()).stream()).filter(p_27306_.m_27221_());
    }

    public void m_218021_(BlockPos p_218022_, Holder<PoiType> p_218023_) {
        if (this.m_27273_(new PoiRecord(p_218022_, p_218023_, this.f_27263_))) {
            f_27260_.debug("Added POI of type {} @ {}", (Object)p_218023_.m_203543_().map(p_218020_ -> p_218020_.m_135782_().toString()).orElse("[unregistered]"), (Object)p_218022_);
            this.f_27263_.run();
        }
    }

    private boolean m_27273_(PoiRecord p_27274_) {
        BlockPos $$1 = p_27274_.m_27257_();
        Holder<PoiType> $$2 = p_27274_.m_218018_();
        short $$3 = SectionPos.m_123218_($$1);
        PoiRecord $$4 = (PoiRecord)this.f_27261_.get($$3);
        if ($$4 != null) {
            if ($$2.equals($$4.m_218018_())) {
                return false;
            }
            Util.m_143785_("POI data mismatch: already registered at " + $$1);
        }
        this.f_27261_.put($$3, (Object)p_27274_);
        this.f_27262_.computeIfAbsent($$2, p_218029_ -> Sets.newHashSet()).add(p_27274_);
        return true;
    }

    public void m_27279_(BlockPos p_27280_) {
        PoiRecord $$1 = (PoiRecord)this.f_27261_.remove(SectionPos.m_123218_(p_27280_));
        if ($$1 == null) {
            f_27260_.error("POI data mismatch: never registered at {}", (Object)p_27280_);
            return;
        }
        this.f_27262_.get($$1.m_218018_()).remove($$1);
        f_27260_.debug("Removed POI of type {} @ {}", LogUtils.defer($$1::m_218018_), LogUtils.defer($$1::m_27257_));
        this.f_27263_.run();
    }

    @Deprecated
    @VisibleForDebug
    public int m_148682_(BlockPos p_148683_) {
        return this.m_148684_(p_148683_).map(PoiRecord::m_148667_).orElse(0);
    }

    public boolean m_27317_(BlockPos p_27318_) {
        PoiRecord $$1 = (PoiRecord)this.f_27261_.get(SectionPos.m_123218_(p_27318_));
        if ($$1 == null) {
            throw Util.m_137570_(new IllegalStateException("POI never registered at " + p_27318_));
        }
        boolean $$2 = $$1.m_27250_();
        this.f_27263_.run();
        return $$2;
    }

    public boolean m_27288_(BlockPos p_27289_, Predicate<Holder<PoiType>> p_27290_) {
        return this.m_27319_(p_27289_).filter(p_27290_).isPresent();
    }

    public Optional<Holder<PoiType>> m_27319_(BlockPos p_27320_) {
        return this.m_148684_(p_27320_).map(PoiRecord::m_218018_);
    }

    private Optional<PoiRecord> m_148684_(BlockPos p_148685_) {
        return Optional.ofNullable((PoiRecord)this.f_27261_.get(SectionPos.m_123218_(p_148685_)));
    }

    public void m_27302_(Consumer<BiConsumer<BlockPos, Holder<PoiType>>> p_27303_) {
        if (!this.f_27264_) {
            Short2ObjectOpenHashMap $$1 = new Short2ObjectOpenHashMap(this.f_27261_);
            this.m_27310_();
            p_27303_.accept((arg_0, arg_1) -> this.m_218030_((Short2ObjectMap)$$1, arg_0, arg_1));
            this.f_27264_ = true;
            this.f_27263_.run();
        }
    }

    private void m_27310_() {
        this.f_27261_.clear();
        this.f_27262_.clear();
    }

    boolean m_27272_() {
        return this.f_27264_;
    }

    private /* synthetic */ void m_218030_(Short2ObjectMap p_218031_, BlockPos p_218032_, Holder p_218033_) {
        short $$3 = SectionPos.m_123218_(p_218032_);
        PoiRecord $$4 = (PoiRecord)p_218031_.computeIfAbsent($$3, p_218027_ -> new PoiRecord(p_218032_, p_218033_, this.f_27263_));
        this.m_27273_($$4);
    }
}

