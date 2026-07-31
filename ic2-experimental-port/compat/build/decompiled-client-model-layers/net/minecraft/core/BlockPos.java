/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractIterator
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  javax.annotation.concurrent.Immutable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.core;

import com.google.common.collect.AbstractIterator;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.concurrent.Immutable;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

@Immutable
public class BlockPos
extends Vec3i {
    public static final Codec<BlockPos> f_121852_ = Codec.INT_STREAM.comapFlatMap(p_121967_ -> Util.m_137539_(p_121967_, 3).map(p_175270_ -> new BlockPos(p_175270_[0], p_175270_[1], p_175270_[2])), p_121924_ -> IntStream.of(p_121924_.m_123341_(), p_121924_.m_123342_(), p_121924_.m_123343_())).stable();
    private static final Logger f_121854_ = LogUtils.getLogger();
    public static final BlockPos f_121853_ = new BlockPos(0, 0, 0);
    private static final int f_121855_;
    private static final int f_121856_;
    public static final int f_121857_;
    private static final long f_121858_;
    private static final long f_121859_;
    private static final long f_121860_;
    private static final int f_175261_ = 0;
    private static final int f_121861_;
    private static final int f_121862_;

    public BlockPos(int p_121869_, int p_121870_, int p_121871_) {
        super(p_121869_, p_121870_, p_121871_);
    }

    public BlockPos(double p_121865_, double p_121866_, double p_121867_) {
        super(p_121865_, p_121866_, p_121867_);
    }

    public BlockPos(Vec3 p_121873_) {
        this(p_121873_.f_82479_, p_121873_.f_82480_, p_121873_.f_82481_);
    }

    public BlockPos(Position p_121875_) {
        this(p_121875_.m_7096_(), p_121875_.m_7098_(), p_121875_.m_7094_());
    }

    public BlockPos(Vec3i p_121877_) {
        this(p_121877_.m_123341_(), p_121877_.m_123342_(), p_121877_.m_123343_());
    }

    public static long m_121915_(long p_121916_, Direction p_121917_) {
        return BlockPos.m_121910_(p_121916_, p_121917_.m_122429_(), p_121917_.m_122430_(), p_121917_.m_122431_());
    }

    public static long m_121910_(long p_121911_, int p_121912_, int p_121913_, int p_121914_) {
        return BlockPos.m_121882_(BlockPos.m_121983_(p_121911_) + p_121912_, BlockPos.m_122008_(p_121911_) + p_121913_, BlockPos.m_122015_(p_121911_) + p_121914_);
    }

    public static int m_121983_(long p_121984_) {
        return (int)(p_121984_ << 64 - f_121862_ - f_121855_ >> 64 - f_121855_);
    }

    public static int m_122008_(long p_122009_) {
        return (int)(p_122009_ << 64 - f_121857_ >> 64 - f_121857_);
    }

    public static int m_122015_(long p_122016_) {
        return (int)(p_122016_ << 64 - f_121861_ - f_121856_ >> 64 - f_121856_);
    }

    public static BlockPos m_122022_(long p_122023_) {
        return new BlockPos(BlockPos.m_121983_(p_122023_), BlockPos.m_122008_(p_122023_), BlockPos.m_122015_(p_122023_));
    }

    public long m_121878_() {
        return BlockPos.m_121882_(this.m_123341_(), this.m_123342_(), this.m_123343_());
    }

    public static long m_121882_(int p_121883_, int p_121884_, int p_121885_) {
        long $$3 = 0L;
        $$3 |= ((long)p_121883_ & f_121858_) << f_121862_;
        $$3 |= ((long)p_121884_ & f_121859_) << 0;
        return $$3 |= ((long)p_121885_ & f_121860_) << f_121861_;
    }

    public static long m_122027_(long p_122028_) {
        return p_122028_ & 0xFFFFFFFFFFFFFFF0L;
    }

    @Override
    public BlockPos m_7637_(double p_121879_, double p_121880_, double p_121881_) {
        if (p_121879_ == 0.0 && p_121880_ == 0.0 && p_121881_ == 0.0) {
            return this;
        }
        return new BlockPos((double)this.m_123341_() + p_121879_, (double)this.m_123342_() + p_121880_, (double)this.m_123343_() + p_121881_);
    }

    @Override
    public BlockPos m_7918_(int p_121973_, int p_121974_, int p_121975_) {
        if (p_121973_ == 0 && p_121974_ == 0 && p_121975_ == 0) {
            return this;
        }
        return new BlockPos(this.m_123341_() + p_121973_, this.m_123342_() + p_121974_, this.m_123343_() + p_121975_);
    }

    @Override
    public BlockPos m_121955_(Vec3i p_121956_) {
        return this.m_7918_(p_121956_.m_123341_(), p_121956_.m_123342_(), p_121956_.m_123343_());
    }

    @Override
    public BlockPos m_121996_(Vec3i p_121997_) {
        return this.m_7918_(-p_121997_.m_123341_(), -p_121997_.m_123342_(), -p_121997_.m_123343_());
    }

    @Override
    public BlockPos m_142393_(int p_175263_) {
        if (p_175263_ == 1) {
            return this;
        }
        if (p_175263_ == 0) {
            return f_121853_;
        }
        return new BlockPos(this.m_123341_() * p_175263_, this.m_123342_() * p_175263_, this.m_123343_() * p_175263_);
    }

    @Override
    public BlockPos m_7494_() {
        return this.m_121945_(Direction.UP);
    }

    @Override
    public BlockPos m_6630_(int p_121972_) {
        return this.m_5484_(Direction.UP, p_121972_);
    }

    @Override
    public BlockPos m_7495_() {
        return this.m_121945_(Direction.DOWN);
    }

    @Override
    public BlockPos m_6625_(int p_122000_) {
        return this.m_5484_(Direction.DOWN, p_122000_);
    }

    @Override
    public BlockPos m_122012_() {
        return this.m_121945_(Direction.NORTH);
    }

    @Override
    public BlockPos m_122013_(int p_122014_) {
        return this.m_5484_(Direction.NORTH, p_122014_);
    }

    @Override
    public BlockPos m_122019_() {
        return this.m_121945_(Direction.SOUTH);
    }

    @Override
    public BlockPos m_122020_(int p_122021_) {
        return this.m_5484_(Direction.SOUTH, p_122021_);
    }

    @Override
    public BlockPos m_122024_() {
        return this.m_121945_(Direction.WEST);
    }

    @Override
    public BlockPos m_122025_(int p_122026_) {
        return this.m_5484_(Direction.WEST, p_122026_);
    }

    @Override
    public BlockPos m_122029_() {
        return this.m_121945_(Direction.EAST);
    }

    @Override
    public BlockPos m_122030_(int p_122031_) {
        return this.m_5484_(Direction.EAST, p_122031_);
    }

    @Override
    public BlockPos m_121945_(Direction p_121946_) {
        return new BlockPos(this.m_123341_() + p_121946_.m_122429_(), this.m_123342_() + p_121946_.m_122430_(), this.m_123343_() + p_121946_.m_122431_());
    }

    @Override
    public BlockPos m_5484_(Direction p_121948_, int p_121949_) {
        if (p_121949_ == 0) {
            return this;
        }
        return new BlockPos(this.m_123341_() + p_121948_.m_122429_() * p_121949_, this.m_123342_() + p_121948_.m_122430_() * p_121949_, this.m_123343_() + p_121948_.m_122431_() * p_121949_);
    }

    @Override
    public BlockPos m_5487_(Direction.Axis p_121943_, int p_121944_) {
        if (p_121944_ == 0) {
            return this;
        }
        int $$2 = p_121943_ == Direction.Axis.X ? p_121944_ : 0;
        int $$3 = p_121943_ == Direction.Axis.Y ? p_121944_ : 0;
        int $$4 = p_121943_ == Direction.Axis.Z ? p_121944_ : 0;
        return new BlockPos(this.m_123341_() + $$2, this.m_123342_() + $$3, this.m_123343_() + $$4);
    }

    public BlockPos m_7954_(Rotation p_121918_) {
        switch (p_121918_) {
            default: {
                return this;
            }
            case CLOCKWISE_90: {
                return new BlockPos(-this.m_123343_(), this.m_123342_(), this.m_123341_());
            }
            case CLOCKWISE_180: {
                return new BlockPos(-this.m_123341_(), this.m_123342_(), -this.m_123343_());
            }
            case COUNTERCLOCKWISE_90: 
        }
        return new BlockPos(this.m_123343_(), this.m_123342_(), -this.m_123341_());
    }

    @Override
    public BlockPos m_7724_(Vec3i p_122011_) {
        return new BlockPos(this.m_123342_() * p_122011_.m_123343_() - this.m_123343_() * p_122011_.m_123342_(), this.m_123343_() * p_122011_.m_123341_() - this.m_123341_() * p_122011_.m_123343_(), this.m_123341_() * p_122011_.m_123342_() - this.m_123342_() * p_122011_.m_123341_());
    }

    public BlockPos m_175288_(int p_175289_) {
        return new BlockPos(this.m_123341_(), p_175289_, this.m_123343_());
    }

    public BlockPos m_7949_() {
        return this;
    }

    public MutableBlockPos m_122032_() {
        return new MutableBlockPos(this.m_123341_(), this.m_123342_(), this.m_123343_());
    }

    public static Iterable<BlockPos> m_235650_(RandomSource p_235651_, int p_235652_, BlockPos p_235653_, int p_235654_) {
        return BlockPos.m_235641_(p_235651_, p_235652_, p_235653_.m_123341_() - p_235654_, p_235653_.m_123342_() - p_235654_, p_235653_.m_123343_() - p_235654_, p_235653_.m_123341_() + p_235654_, p_235653_.m_123342_() + p_235654_, p_235653_.m_123343_() + p_235654_);
    }

    public static Iterable<BlockPos> m_235641_(final RandomSource p_235642_, final int p_235643_, final int p_235644_, final int p_235645_, final int p_235646_, int p_235647_, int p_235648_, int p_235649_) {
        final int $$8 = p_235647_ - p_235644_ + 1;
        final int $$9 = p_235648_ - p_235645_ + 1;
        final int $$10 = p_235649_ - p_235646_ + 1;
        return () -> new AbstractIterator<BlockPos>(){
            final MutableBlockPos f_122039_ = new MutableBlockPos();
            int f_122040_ = p_235643_;

            protected BlockPos computeNext() {
                if (this.f_122040_ <= 0) {
                    return (BlockPos)this.endOfData();
                }
                MutableBlockPos $$0 = this.f_122039_.m_122178_(p_235644_ + p_235642_.m_188503_($$8), p_235645_ + p_235642_.m_188503_($$9), p_235646_ + p_235642_.m_188503_($$10));
                --this.f_122040_;
                return $$0;
            }

            protected /* synthetic */ Object computeNext() {
                return this.computeNext();
            }
        };
    }

    public static Iterable<BlockPos> m_121925_(BlockPos p_121926_, final int p_121927_, final int p_121928_, final int p_121929_) {
        final int $$4 = p_121927_ + p_121928_ + p_121929_;
        final int $$5 = p_121926_.m_123341_();
        final int $$6 = p_121926_.m_123342_();
        final int $$7 = p_121926_.m_123343_();
        return () -> new AbstractIterator<BlockPos>(){
            private final MutableBlockPos f_122067_ = new MutableBlockPos();
            private int f_122068_;
            private int f_122069_;
            private int f_122070_;
            private int f_122071_;
            private int f_122072_;
            private boolean f_122073_;

            protected BlockPos computeNext() {
                if (this.f_122073_) {
                    this.f_122073_ = false;
                    this.f_122067_.m_142443_($$7 - (this.f_122067_.m_123343_() - $$7));
                    return this.f_122067_;
                }
                MutableBlockPos $$0 = null;
                while ($$0 == null) {
                    if (this.f_122072_ > this.f_122070_) {
                        ++this.f_122071_;
                        if (this.f_122071_ > this.f_122069_) {
                            ++this.f_122068_;
                            if (this.f_122068_ > $$4) {
                                return (BlockPos)this.endOfData();
                            }
                            this.f_122069_ = Math.min(p_121927_, this.f_122068_);
                            this.f_122071_ = -this.f_122069_;
                        }
                        this.f_122070_ = Math.min(p_121928_, this.f_122068_ - Math.abs(this.f_122071_));
                        this.f_122072_ = -this.f_122070_;
                    }
                    int $$1 = this.f_122071_;
                    int $$2 = this.f_122072_;
                    int $$3 = this.f_122068_ - Math.abs($$1) - Math.abs($$2);
                    if ($$3 <= p_121929_) {
                        this.f_122073_ = $$3 != 0;
                        $$0 = this.f_122067_.m_122178_($$5 + $$1, $$6 + $$2, $$7 + $$3);
                    }
                    ++this.f_122072_;
                }
                return $$0;
            }

            protected /* synthetic */ Object computeNext() {
                return this.computeNext();
            }
        };
    }

    public static Optional<BlockPos> m_121930_(BlockPos p_121931_, int p_121932_, int p_121933_, Predicate<BlockPos> p_121934_) {
        for (BlockPos $$4 : BlockPos.m_121925_(p_121931_, p_121932_, p_121933_, p_121932_)) {
            if (!p_121934_.test($$4)) continue;
            return Optional.of($$4);
        }
        return Optional.empty();
    }

    public static Stream<BlockPos> m_121985_(BlockPos p_121986_, int p_121987_, int p_121988_, int p_121989_) {
        return StreamSupport.stream(BlockPos.m_121925_(p_121986_, p_121987_, p_121988_, p_121989_).spliterator(), false);
    }

    public static Iterable<BlockPos> m_121940_(BlockPos p_121941_, BlockPos p_121942_) {
        return BlockPos.m_121976_(Math.min(p_121941_.m_123341_(), p_121942_.m_123341_()), Math.min(p_121941_.m_123342_(), p_121942_.m_123342_()), Math.min(p_121941_.m_123343_(), p_121942_.m_123343_()), Math.max(p_121941_.m_123341_(), p_121942_.m_123341_()), Math.max(p_121941_.m_123342_(), p_121942_.m_123342_()), Math.max(p_121941_.m_123343_(), p_121942_.m_123343_()));
    }

    public static Stream<BlockPos> m_121990_(BlockPos p_121991_, BlockPos p_121992_) {
        return StreamSupport.stream(BlockPos.m_121940_(p_121991_, p_121992_).spliterator(), false);
    }

    public static Stream<BlockPos> m_121919_(BoundingBox p_121920_) {
        return BlockPos.m_121886_(Math.min(p_121920_.m_162395_(), p_121920_.m_162399_()), Math.min(p_121920_.m_162396_(), p_121920_.m_162400_()), Math.min(p_121920_.m_162398_(), p_121920_.m_162401_()), Math.max(p_121920_.m_162395_(), p_121920_.m_162399_()), Math.max(p_121920_.m_162396_(), p_121920_.m_162400_()), Math.max(p_121920_.m_162398_(), p_121920_.m_162401_()));
    }

    public static Stream<BlockPos> m_121921_(AABB p_121922_) {
        return BlockPos.m_121886_(Mth.m_14107_(p_121922_.f_82288_), Mth.m_14107_(p_121922_.f_82289_), Mth.m_14107_(p_121922_.f_82290_), Mth.m_14107_(p_121922_.f_82291_), Mth.m_14107_(p_121922_.f_82292_), Mth.m_14107_(p_121922_.f_82293_));
    }

    public static Stream<BlockPos> m_121886_(int p_121887_, int p_121888_, int p_121889_, int p_121890_, int p_121891_, int p_121892_) {
        return StreamSupport.stream(BlockPos.m_121976_(p_121887_, p_121888_, p_121889_, p_121890_, p_121891_, p_121892_).spliterator(), false);
    }

    public static Iterable<BlockPos> m_121976_(final int p_121977_, final int p_121978_, final int p_121979_, int p_121980_, int p_121981_, int p_121982_) {
        final int $$6 = p_121980_ - p_121977_ + 1;
        final int $$7 = p_121981_ - p_121978_ + 1;
        int $$8 = p_121982_ - p_121979_ + 1;
        final int $$9 = $$6 * $$7 * $$8;
        return () -> new AbstractIterator<BlockPos>(){
            private final MutableBlockPos f_122090_ = new MutableBlockPos();
            private int f_122091_;

            protected BlockPos computeNext() {
                if (this.f_122091_ == $$9) {
                    return (BlockPos)this.endOfData();
                }
                int $$0 = this.f_122091_ % $$6;
                int $$1 = this.f_122091_ / $$6;
                int $$2 = $$1 % $$7;
                int $$3 = $$1 / $$7;
                ++this.f_122091_;
                return this.f_122090_.m_122178_(p_121977_ + $$0, p_121978_ + $$2, p_121979_ + $$3);
            }

            protected /* synthetic */ Object computeNext() {
                return this.computeNext();
            }
        };
    }

    public static Iterable<MutableBlockPos> m_121935_(final BlockPos p_121936_, final int p_121937_, final Direction p_121938_, final Direction p_121939_) {
        Validate.validState((p_121938_.m_122434_() != p_121939_.m_122434_() ? 1 : 0) != 0, (String)"The two directions cannot be on the same axis", (Object[])new Object[0]);
        return () -> new AbstractIterator<MutableBlockPos>(){
            private final Direction[] f_122105_;
            private final MutableBlockPos f_122106_;
            private final int f_122107_;
            private int f_122108_;
            private int f_122109_;
            private int f_122110_;
            private int f_122111_;
            private int f_122112_;
            private int f_122113_;
            {
                this.f_122105_ = new Direction[]{p_121938_, p_121939_, p_121938_.m_122424_(), p_121939_.m_122424_()};
                this.f_122106_ = p_121936_.m_122032_().m_122173_(p_121939_);
                this.f_122107_ = 4 * p_121937_;
                this.f_122108_ = -1;
                this.f_122111_ = this.f_122106_.m_123341_();
                this.f_122112_ = this.f_122106_.m_123342_();
                this.f_122113_ = this.f_122106_.m_123343_();
            }

            protected MutableBlockPos computeNext() {
                this.f_122106_.m_122178_(this.f_122111_, this.f_122112_, this.f_122113_).m_122173_(this.f_122105_[(this.f_122108_ + 4) % 4]);
                this.f_122111_ = this.f_122106_.m_123341_();
                this.f_122112_ = this.f_122106_.m_123342_();
                this.f_122113_ = this.f_122106_.m_123343_();
                if (this.f_122110_ >= this.f_122109_) {
                    if (this.f_122108_ >= this.f_122107_) {
                        return (MutableBlockPos)this.endOfData();
                    }
                    ++this.f_122108_;
                    this.f_122110_ = 0;
                    this.f_122109_ = this.f_122108_ / 2 + 1;
                }
                ++this.f_122110_;
                return this.f_122106_;
            }

            protected /* synthetic */ Object computeNext() {
                return this.computeNext();
            }
        };
    }

    @Override
    public /* synthetic */ Vec3i m_7724_(Vec3i vec3i) {
        return this.m_7724_(vec3i);
    }

    @Override
    public /* synthetic */ Vec3i m_5487_(Direction.Axis axis, int n) {
        return this.m_5487_(axis, n);
    }

    @Override
    public /* synthetic */ Vec3i m_5484_(Direction direction, int n) {
        return this.m_5484_(direction, n);
    }

    @Override
    public /* synthetic */ Vec3i m_121945_(Direction direction) {
        return this.m_121945_(direction);
    }

    @Override
    public /* synthetic */ Vec3i m_122030_(int n) {
        return this.m_122030_(n);
    }

    @Override
    public /* synthetic */ Vec3i m_122029_() {
        return this.m_122029_();
    }

    @Override
    public /* synthetic */ Vec3i m_122025_(int n) {
        return this.m_122025_(n);
    }

    @Override
    public /* synthetic */ Vec3i m_122024_() {
        return this.m_122024_();
    }

    @Override
    public /* synthetic */ Vec3i m_122020_(int n) {
        return this.m_122020_(n);
    }

    @Override
    public /* synthetic */ Vec3i m_122019_() {
        return this.m_122019_();
    }

    @Override
    public /* synthetic */ Vec3i m_122013_(int n) {
        return this.m_122013_(n);
    }

    @Override
    public /* synthetic */ Vec3i m_122012_() {
        return this.m_122012_();
    }

    @Override
    public /* synthetic */ Vec3i m_6625_(int n) {
        return this.m_6625_(n);
    }

    @Override
    public /* synthetic */ Vec3i m_7495_() {
        return this.m_7495_();
    }

    @Override
    public /* synthetic */ Vec3i m_6630_(int n) {
        return this.m_6630_(n);
    }

    @Override
    public /* synthetic */ Vec3i m_7494_() {
        return this.m_7494_();
    }

    @Override
    public /* synthetic */ Vec3i m_142393_(int n) {
        return this.m_142393_(n);
    }

    @Override
    public /* synthetic */ Vec3i m_121996_(Vec3i vec3i) {
        return this.m_121996_(vec3i);
    }

    @Override
    public /* synthetic */ Vec3i m_121955_(Vec3i vec3i) {
        return this.m_121955_(vec3i);
    }

    @Override
    public /* synthetic */ Vec3i m_7918_(int n, int n2, int n3) {
        return this.m_7918_(n, n2, n3);
    }

    @Override
    public /* synthetic */ Vec3i m_7637_(double d, double d2, double d3) {
        return this.m_7637_(d, d2, d3);
    }

    static {
        f_121856_ = f_121855_ = 1 + Mth.m_14173_(Mth.m_14125_(30000000));
        f_121857_ = 64 - f_121855_ - f_121856_;
        f_121858_ = (1L << f_121855_) - 1L;
        f_121859_ = (1L << f_121857_) - 1L;
        f_121860_ = (1L << f_121856_) - 1L;
        f_121861_ = f_121857_;
        f_121862_ = f_121857_ + f_121856_;
    }

    public static class MutableBlockPos
    extends BlockPos {
        public MutableBlockPos() {
            this(0, 0, 0);
        }

        public MutableBlockPos(int p_122130_, int p_122131_, int p_122132_) {
            super(p_122130_, p_122131_, p_122132_);
        }

        public MutableBlockPos(double p_122126_, double p_122127_, double p_122128_) {
            this(Mth.m_14107_(p_122126_), Mth.m_14107_(p_122127_), Mth.m_14107_(p_122128_));
        }

        @Override
        public BlockPos m_7637_(double p_122134_, double p_122135_, double p_122136_) {
            return super.m_7637_(p_122134_, p_122135_, p_122136_).m_7949_();
        }

        @Override
        public BlockPos m_7918_(int p_122163_, int p_122164_, int p_122165_) {
            return super.m_7918_(p_122163_, p_122164_, p_122165_).m_7949_();
        }

        @Override
        public BlockPos m_142393_(int p_175305_) {
            return super.m_142393_(p_175305_).m_7949_();
        }

        @Override
        public BlockPos m_5484_(Direction p_122152_, int p_122153_) {
            return super.m_5484_(p_122152_, p_122153_).m_7949_();
        }

        @Override
        public BlockPos m_5487_(Direction.Axis p_122145_, int p_122146_) {
            return super.m_5487_(p_122145_, p_122146_).m_7949_();
        }

        @Override
        public BlockPos m_7954_(Rotation p_122138_) {
            return super.m_7954_(p_122138_).m_7949_();
        }

        public MutableBlockPos m_122178_(int p_122179_, int p_122180_, int p_122181_) {
            this.m_142451_(p_122179_);
            this.m_142448_(p_122180_);
            this.m_142443_(p_122181_);
            return this;
        }

        public MutableBlockPos m_122169_(double p_122170_, double p_122171_, double p_122172_) {
            return this.m_122178_(Mth.m_14107_(p_122170_), Mth.m_14107_(p_122171_), Mth.m_14107_(p_122172_));
        }

        public MutableBlockPos m_122190_(Vec3i p_122191_) {
            return this.m_122178_(p_122191_.m_123341_(), p_122191_.m_123342_(), p_122191_.m_123343_());
        }

        public MutableBlockPos m_122188_(long p_122189_) {
            return this.m_122178_(MutableBlockPos.m_121983_(p_122189_), MutableBlockPos.m_122008_(p_122189_), MutableBlockPos.m_122015_(p_122189_));
        }

        public MutableBlockPos m_122139_(AxisCycle p_122140_, int p_122141_, int p_122142_, int p_122143_) {
            return this.m_122178_(p_122140_.m_7758_(p_122141_, p_122142_, p_122143_, Direction.Axis.X), p_122140_.m_7758_(p_122141_, p_122142_, p_122143_, Direction.Axis.Y), p_122140_.m_7758_(p_122141_, p_122142_, p_122143_, Direction.Axis.Z));
        }

        public MutableBlockPos m_122159_(Vec3i p_122160_, Direction p_122161_) {
            return this.m_122178_(p_122160_.m_123341_() + p_122161_.m_122429_(), p_122160_.m_123342_() + p_122161_.m_122430_(), p_122160_.m_123343_() + p_122161_.m_122431_());
        }

        public MutableBlockPos m_122154_(Vec3i p_122155_, int p_122156_, int p_122157_, int p_122158_) {
            return this.m_122178_(p_122155_.m_123341_() + p_122156_, p_122155_.m_123342_() + p_122157_, p_122155_.m_123343_() + p_122158_);
        }

        public MutableBlockPos m_175306_(Vec3i p_175307_, Vec3i p_175308_) {
            return this.m_122178_(p_175307_.m_123341_() + p_175308_.m_123341_(), p_175307_.m_123342_() + p_175308_.m_123342_(), p_175307_.m_123343_() + p_175308_.m_123343_());
        }

        public MutableBlockPos m_122173_(Direction p_122174_) {
            return this.m_122175_(p_122174_, 1);
        }

        public MutableBlockPos m_122175_(Direction p_122176_, int p_122177_) {
            return this.m_122178_(this.m_123341_() + p_122176_.m_122429_() * p_122177_, this.m_123342_() + p_122176_.m_122430_() * p_122177_, this.m_123343_() + p_122176_.m_122431_() * p_122177_);
        }

        public MutableBlockPos m_122184_(int p_122185_, int p_122186_, int p_122187_) {
            return this.m_122178_(this.m_123341_() + p_122185_, this.m_123342_() + p_122186_, this.m_123343_() + p_122187_);
        }

        public MutableBlockPos m_122193_(Vec3i p_122194_) {
            return this.m_122178_(this.m_123341_() + p_122194_.m_123341_(), this.m_123342_() + p_122194_.m_123342_(), this.m_123343_() + p_122194_.m_123343_());
        }

        public MutableBlockPos m_122147_(Direction.Axis p_122148_, int p_122149_, int p_122150_) {
            switch (p_122148_) {
                case X: {
                    return this.m_122178_(Mth.m_14045_(this.m_123341_(), p_122149_, p_122150_), this.m_123342_(), this.m_123343_());
                }
                case Y: {
                    return this.m_122178_(this.m_123341_(), Mth.m_14045_(this.m_123342_(), p_122149_, p_122150_), this.m_123343_());
                }
                case Z: {
                    return this.m_122178_(this.m_123341_(), this.m_123342_(), Mth.m_14045_(this.m_123343_(), p_122149_, p_122150_));
                }
            }
            throw new IllegalStateException("Unable to clamp axis " + p_122148_);
        }

        @Override
        public MutableBlockPos m_142451_(int p_175341_) {
            super.m_142451_(p_175341_);
            return this;
        }

        @Override
        public MutableBlockPos m_142448_(int p_175343_) {
            super.m_142448_(p_175343_);
            return this;
        }

        @Override
        public MutableBlockPos m_142443_(int p_175345_) {
            super.m_142443_(p_175345_);
            return this;
        }

        @Override
        public BlockPos m_7949_() {
            return new BlockPos(this);
        }

        @Override
        public /* synthetic */ Vec3i m_7724_(Vec3i vec3i) {
            return super.m_7724_(vec3i);
        }

        @Override
        public /* synthetic */ Vec3i m_5487_(Direction.Axis axis, int n) {
            return this.m_5487_(axis, n);
        }

        @Override
        public /* synthetic */ Vec3i m_5484_(Direction direction, int n) {
            return this.m_5484_(direction, n);
        }

        @Override
        public /* synthetic */ Vec3i m_121945_(Direction direction) {
            return super.m_121945_(direction);
        }

        @Override
        public /* synthetic */ Vec3i m_122030_(int n) {
            return super.m_122030_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_122029_() {
            return super.m_122029_();
        }

        @Override
        public /* synthetic */ Vec3i m_122025_(int n) {
            return super.m_122025_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_122024_() {
            return super.m_122024_();
        }

        @Override
        public /* synthetic */ Vec3i m_122020_(int n) {
            return super.m_122020_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_122019_() {
            return super.m_122019_();
        }

        @Override
        public /* synthetic */ Vec3i m_122013_(int n) {
            return super.m_122013_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_122012_() {
            return super.m_122012_();
        }

        @Override
        public /* synthetic */ Vec3i m_6625_(int n) {
            return super.m_6625_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_7495_() {
            return super.m_7495_();
        }

        @Override
        public /* synthetic */ Vec3i m_6630_(int n) {
            return super.m_6630_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_7494_() {
            return super.m_7494_();
        }

        @Override
        public /* synthetic */ Vec3i m_142393_(int n) {
            return this.m_142393_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_121996_(Vec3i vec3i) {
            return super.m_121996_(vec3i);
        }

        @Override
        public /* synthetic */ Vec3i m_121955_(Vec3i vec3i) {
            return super.m_121955_(vec3i);
        }

        @Override
        public /* synthetic */ Vec3i m_7918_(int n, int n2, int n3) {
            return this.m_7918_(n, n2, n3);
        }

        @Override
        public /* synthetic */ Vec3i m_7637_(double d, double d2, double d3) {
            return this.m_7637_(d, d2, d3);
        }

        @Override
        public /* synthetic */ Vec3i m_142443_(int n) {
            return this.m_142443_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_142448_(int n) {
            return this.m_142448_(n);
        }

        @Override
        public /* synthetic */ Vec3i m_142451_(int n) {
            return this.m_142451_(n);
        }
    }
}

