/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 */
package net.minecraft.world.entity.ai.village.poi;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.SectionTracker;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiSection;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.storage.SectionStorage;

public class PoiManager
extends SectionStorage<PoiSection> {
    public static final int f_148565_ = 6;
    public static final int f_148566_ = 1;
    private final DistanceTracker f_27029_;
    private final LongSet f_27030_ = new LongOpenHashSet();

    public PoiManager(Path p_217869_, DataFixer p_217870_, boolean p_217871_, RegistryAccess p_217872_, LevelHeightAccessor p_217873_) {
        super(p_217869_, PoiSection::m_27295_, PoiSection::new, p_217870_, DataFixTypes.POI_CHUNK, p_217871_, p_217872_, p_217873_);
        this.f_27029_ = new DistanceTracker();
    }

    public void m_217919_(BlockPos p_217920_, Holder<PoiType> p_217921_) {
        ((PoiSection)this.m_63827_(SectionPos.m_175568_(p_217920_))).m_218021_(p_217920_, p_217921_);
    }

    public void m_27079_(BlockPos p_27080_) {
        this.m_63823_(SectionPos.m_175568_(p_27080_)).ifPresent(p_148657_ -> p_148657_.m_27279_(p_27080_));
    }

    public long m_27121_(Predicate<Holder<PoiType>> p_27122_, BlockPos p_27123_, int p_27124_, Occupancy p_27125_) {
        return this.m_27181_(p_27122_, p_27123_, p_27124_, p_27125_).count();
    }

    public boolean m_217874_(ResourceKey<PoiType> p_217875_, BlockPos p_217876_) {
        return this.m_27091_(p_217876_, p_217879_ -> p_217879_.m_203565_(p_217875_));
    }

    public Stream<PoiRecord> m_27166_(Predicate<Holder<PoiType>> p_27167_, BlockPos p_27168_, int p_27169_, Occupancy p_27170_) {
        int $$4 = Math.floorDiv(p_27169_, 16) + 1;
        return ChunkPos.m_45596_(new ChunkPos(p_27168_), $$4).flatMap(p_217938_ -> this.m_27117_(p_27167_, (ChunkPos)p_217938_, p_27170_)).filter(p_217971_ -> {
            BlockPos $$3 = p_217971_.m_27257_();
            return Math.abs($$3.m_123341_() - p_27168_.m_123341_()) <= p_27169_ && Math.abs($$3.m_123343_() - p_27168_.m_123343_()) <= p_27169_;
        });
    }

    public Stream<PoiRecord> m_27181_(Predicate<Holder<PoiType>> p_27182_, BlockPos p_27183_, int p_27184_, Occupancy p_27185_) {
        int $$4 = p_27184_ * p_27184_;
        return this.m_27166_(p_27182_, p_27183_, p_27184_, p_27185_).filter(p_217906_ -> p_217906_.m_27257_().m_123331_(p_27183_) <= (double)$$4);
    }

    @VisibleForDebug
    public Stream<PoiRecord> m_27117_(Predicate<Holder<PoiType>> p_27118_, ChunkPos p_27119_, Occupancy p_27120_) {
        return IntStream.range(this.f_156618_.m_151560_(), this.f_156618_.m_151561_()).boxed().map(p_217886_ -> this.m_63823_(SectionPos.m_123196_(p_27119_, p_217886_).m_123252_())).filter(Optional::isPresent).flatMap(p_217942_ -> ((PoiSection)p_217942_.get()).m_27304_(p_27118_, p_27120_));
    }

    public Stream<BlockPos> m_27138_(Predicate<Holder<PoiType>> p_27139_, Predicate<BlockPos> p_27140_, BlockPos p_27141_, int p_27142_, Occupancy p_27143_) {
        return this.m_27181_(p_27139_, p_27141_, p_27142_, p_27143_).map(PoiRecord::m_27257_).filter(p_27140_);
    }

    public Stream<Pair<Holder<PoiType>, BlockPos>> m_217983_(Predicate<Holder<PoiType>> p_217984_, Predicate<BlockPos> p_217985_, BlockPos p_217986_, int p_217987_, Occupancy p_217988_) {
        return this.m_27181_(p_217984_, p_217986_, p_217987_, p_217988_).filter(p_217982_ -> p_217985_.test(p_217982_.m_27257_())).map(p_217990_ -> Pair.of(p_217990_.m_218018_(), (Object)p_217990_.m_27257_()));
    }

    public Stream<Pair<Holder<PoiType>, BlockPos>> m_217994_(Predicate<Holder<PoiType>> p_217995_, Predicate<BlockPos> p_217996_, BlockPos p_217997_, int p_217998_, Occupancy p_217999_) {
        return this.m_217983_(p_217995_, p_217996_, p_217997_, p_217998_, p_217999_).sorted(Comparator.comparingDouble(p_217915_ -> ((BlockPos)p_217915_.getSecond()).m_123331_(p_217997_)));
    }

    public Optional<BlockPos> m_27186_(Predicate<Holder<PoiType>> p_27187_, Predicate<BlockPos> p_27188_, BlockPos p_27189_, int p_27190_, Occupancy p_27191_) {
        return this.m_27138_(p_27187_, p_27188_, p_27189_, p_27190_, p_27191_).findFirst();
    }

    public Optional<BlockPos> m_27192_(Predicate<Holder<PoiType>> p_27193_, BlockPos p_27194_, int p_27195_, Occupancy p_27196_) {
        return this.m_27181_(p_27193_, p_27194_, p_27195_, p_27196_).map(PoiRecord::m_27257_).min(Comparator.comparingDouble(p_217977_ -> p_217977_.m_123331_(p_27194_)));
    }

    public Optional<Pair<Holder<PoiType>, BlockPos>> m_218002_(Predicate<Holder<PoiType>> p_218003_, BlockPos p_218004_, int p_218005_, Occupancy p_218006_) {
        return this.m_27181_(p_218003_, p_218004_, p_218005_, p_218006_).min(Comparator.comparingDouble(p_217909_ -> p_217909_.m_27257_().m_123331_(p_218004_))).map(p_217959_ -> Pair.of(p_217959_.m_218018_(), (Object)p_217959_.m_27257_()));
    }

    public Optional<BlockPos> m_148658_(Predicate<Holder<PoiType>> p_148659_, Predicate<BlockPos> p_148660_, BlockPos p_148661_, int p_148662_, Occupancy p_148663_) {
        return this.m_27181_(p_148659_, p_148661_, p_148662_, p_148663_).map(PoiRecord::m_27257_).filter(p_148660_).min(Comparator.comparingDouble(p_217918_ -> p_217918_.m_123331_(p_148661_)));
    }

    public Optional<BlockPos> m_217946_(Predicate<Holder<PoiType>> p_217947_, BiPredicate<Holder<PoiType>, BlockPos> p_217948_, BlockPos p_217949_, int p_217950_) {
        return this.m_27181_(p_217947_, p_217949_, p_217950_, Occupancy.HAS_SPACE).filter(p_217934_ -> p_217948_.test(p_217934_.m_218018_(), p_217934_.m_27257_())).findFirst().map(p_217881_ -> {
            p_217881_.m_27247_();
            return p_217881_.m_27257_();
        });
    }

    public Optional<BlockPos> m_217951_(Predicate<Holder<PoiType>> p_217952_, Predicate<BlockPos> p_217953_, Occupancy p_217954_, BlockPos p_217955_, int p_217956_, RandomSource p_217957_) {
        List<PoiRecord> $$6 = Util.m_214661_(this.m_27181_(p_217952_, p_217955_, p_217956_, p_217954_), p_217957_);
        return $$6.stream().filter(p_217945_ -> p_217953_.test(p_217945_.m_27257_())).findFirst().map(PoiRecord::m_27257_);
    }

    public boolean m_27154_(BlockPos p_27155_) {
        return this.m_63823_(SectionPos.m_175568_(p_27155_)).map(p_217993_ -> p_217993_.m_27317_(p_27155_)).orElseThrow(() -> Util.m_137570_(new IllegalStateException("POI never registered at " + p_27155_)));
    }

    public boolean m_27091_(BlockPos p_27092_, Predicate<Holder<PoiType>> p_27093_) {
        return this.m_63823_(SectionPos.m_175568_(p_27092_)).map(p_217925_ -> p_217925_.m_27288_(p_27092_, p_27093_)).orElse(false);
    }

    public Optional<Holder<PoiType>> m_27177_(BlockPos p_27178_) {
        return this.m_63823_(SectionPos.m_175568_(p_27178_)).flatMap(p_217974_ -> p_217974_.m_27319_(p_27178_));
    }

    @Deprecated
    @VisibleForDebug
    public int m_148653_(BlockPos p_148654_) {
        return this.m_63823_(SectionPos.m_175568_(p_148654_)).map(p_217912_ -> p_217912_.m_148682_(p_148654_)).orElse(0);
    }

    public int m_27098_(SectionPos p_27099_) {
        this.f_27029_.m_27203_();
        return this.f_27029_.m_6172_(p_27099_.m_123252_());
    }

    boolean m_27197_(long p_27198_) {
        Optional $$1 = this.m_63818_(p_27198_);
        if ($$1 == null) {
            return false;
        }
        return $$1.map(p_217883_ -> p_217883_.m_27304_(p_217927_ -> p_217927_.m_203656_(PoiTypeTags.f_215876_), Occupancy.IS_OCCUPIED).findAny().isPresent()).orElse(false);
    }

    @Override
    public void m_6202_(BooleanSupplier p_27105_) {
        super.m_6202_(p_27105_);
        this.f_27029_.m_27203_();
    }

    @Override
    protected void m_5838_(long p_27036_) {
        super.m_5838_(p_27036_);
        this.f_27029_.m_8288_(p_27036_, this.f_27029_.m_7409_(p_27036_), false);
    }

    @Override
    protected void m_5839_(long p_27145_) {
        this.f_27029_.m_8288_(p_27145_, this.f_27029_.m_7409_(p_27145_), false);
    }

    public void m_27047_(ChunkPos p_27048_, LevelChunkSection p_27049_) {
        SectionPos $$2 = SectionPos.m_123196_(p_27048_, SectionPos.m_123171_(p_27049_.m_63017_()));
        Util.m_137521_(this.m_63823_($$2.m_123252_()), p_217898_ -> p_217898_.m_27302_(p_217967_ -> {
            if (PoiManager.m_27060_(p_27049_)) {
                this.m_27069_(p_27049_, $$2, (BiConsumer<BlockPos, Holder<PoiType>>)p_217967_);
            }
        }), () -> {
            if (PoiManager.m_27060_(p_27049_)) {
                PoiSection $$2 = (PoiSection)this.m_63827_($$2.m_123252_());
                this.m_27069_(p_27049_, $$2, $$2::m_218021_);
            }
        });
    }

    private static boolean m_27060_(LevelChunkSection p_27061_) {
        return p_27061_.m_63002_(PoiTypes.f_218067_::contains);
    }

    private void m_27069_(LevelChunkSection p_27070_, SectionPos p_27071_, BiConsumer<BlockPos, Holder<PoiType>> p_27072_) {
        p_27071_.m_123253_().forEach(p_217902_ -> {
            BlockState $$3 = p_27070_.m_62982_(SectionPos.m_123207_(p_217902_.m_123341_()), SectionPos.m_123207_(p_217902_.m_123342_()), SectionPos.m_123207_(p_217902_.m_123343_()));
            PoiTypes.m_218075_($$3).ifPresent(p_217931_ -> p_27072_.accept((BlockPos)p_217902_, (Holder<PoiType>)p_217931_));
        });
    }

    public void m_27056_(LevelReader p_27057_, BlockPos p_27058_, int p_27059_) {
        SectionPos.m_175557_(new ChunkPos(p_27058_), Math.floorDiv(p_27059_, 16), this.f_156618_.m_151560_(), this.f_156618_.m_151561_()).map(p_217979_ -> Pair.of((Object)p_217979_, this.m_63823_(p_217979_.m_123252_()))).filter(p_217963_ -> ((Optional)p_217963_.getSecond()).map(PoiSection::m_27272_).orElse(false) == false).map(p_217891_ -> ((SectionPos)p_217891_.getFirst()).m_123251_()).filter(p_217961_ -> this.f_27030_.add(p_217961_.m_45588_())).forEach(p_217889_ -> p_27057_.m_46819_(p_217889_.f_45578_, p_217889_.f_45579_, ChunkStatus.f_62314_));
    }

    final class DistanceTracker
    extends SectionTracker {
        private final Long2ByteMap f_27200_;

        protected DistanceTracker() {
            super(7, 16, 256);
            this.f_27200_ = new Long2ByteOpenHashMap();
            this.f_27200_.defaultReturnValue((byte)7);
        }

        @Override
        protected int m_7409_(long p_27208_) {
            return PoiManager.this.m_27197_(p_27208_) ? 0 : 7;
        }

        @Override
        protected int m_6172_(long p_27210_) {
            return this.f_27200_.get(p_27210_);
        }

        @Override
        protected void m_7351_(long p_27205_, int p_27206_) {
            if (p_27206_ > 6) {
                this.f_27200_.remove(p_27205_);
            } else {
                this.f_27200_.put(p_27205_, (byte)p_27206_);
            }
        }

        public void m_27203_() {
            super.m_75588_(Integer.MAX_VALUE);
        }
    }

    public static final class Occupancy
    extends Enum<Occupancy> {
        public static final /* enum */ Occupancy HAS_SPACE = new Occupancy(PoiRecord::m_27253_);
        public static final /* enum */ Occupancy IS_OCCUPIED = new Occupancy(PoiRecord::m_27254_);
        public static final /* enum */ Occupancy ANY = new Occupancy(p_27223_ -> true);
        private final Predicate<? super PoiRecord> f_27214_;
        private static final /* synthetic */ Occupancy[] $VALUES;

        public static Occupancy[] values() {
            return (Occupancy[])$VALUES.clone();
        }

        public static Occupancy valueOf(String p_27225_) {
            return Enum.valueOf(Occupancy.class, p_27225_);
        }

        private Occupancy(Predicate<? super PoiRecord> p_27220_) {
            this.f_27214_ = p_27220_;
        }

        public Predicate<? super PoiRecord> m_27221_() {
            return this.f_27214_;
        }

        private static /* synthetic */ Occupancy[] m_148666_() {
            return new Occupancy[]{HAS_SPACE, IS_OCCUPIED, ANY};
        }

        static {
            $VALUES = Occupancy.m_148666_();
        }
    }
}

