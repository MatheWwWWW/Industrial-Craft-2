/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MultifaceSpreader {
    public static final SpreadType[] f_221586_ = new SpreadType[]{SpreadType.SAME_POSITION, SpreadType.SAME_PLANE, SpreadType.WRAP_AROUND};
    private final SpreadConfig f_221587_;

    public MultifaceSpreader(MultifaceBlock p_221590_) {
        this(new DefaultSpreaderConfig(p_221590_));
    }

    public MultifaceSpreader(SpreadConfig p_221592_) {
        this.f_221587_ = p_221592_;
    }

    public boolean m_221601_(BlockState p_221602_, BlockGetter p_221603_, BlockPos p_221604_, Direction p_221605_) {
        return Direction.m_235666_().anyMatch(p_221611_ -> this.m_221612_(p_221602_, p_221603_, p_221604_, p_221605_, (Direction)p_221611_, this.f_221587_::m_213973_).isPresent());
    }

    public Optional<SpreadPos> m_221619_(BlockState p_221620_, LevelAccessor p_221621_, BlockPos p_221622_, RandomSource p_221623_) {
        return Direction.m_235667_(p_221623_).stream().filter(p_221680_ -> this.f_221587_.m_221714_(p_221620_, (Direction)p_221680_)).map(p_221629_ -> this.m_221630_(p_221620_, p_221621_, p_221622_, (Direction)p_221629_, p_221623_, false)).filter(Optional::isPresent).findFirst().orElse(Optional.empty());
    }

    public long m_221657_(BlockState p_221658_, LevelAccessor p_221659_, BlockPos p_221660_, boolean p_221661_) {
        return Direction.m_235666_().filter(p_221670_ -> this.f_221587_.m_221714_(p_221658_, (Direction)p_221670_)).map(p_221667_ -> this.m_221644_(p_221658_, p_221659_, p_221660_, (Direction)p_221667_, p_221661_)).reduce(0L, Long::sum);
    }

    public Optional<SpreadPos> m_221630_(BlockState p_221631_, LevelAccessor p_221632_, BlockPos p_221633_, Direction p_221634_, RandomSource p_221635_, boolean p_221636_) {
        return Direction.m_235667_(p_221635_).stream().map(p_221677_ -> this.m_221637_(p_221631_, p_221632_, p_221633_, p_221634_, (Direction)p_221677_, p_221636_)).filter(Optional::isPresent).findFirst().orElse(Optional.empty());
    }

    private long m_221644_(BlockState p_221645_, LevelAccessor p_221646_, BlockPos p_221647_, Direction p_221648_, boolean p_221649_) {
        return Direction.m_235666_().map(p_221656_ -> this.m_221637_(p_221645_, p_221646_, p_221647_, p_221648_, (Direction)p_221656_, p_221649_)).filter(Optional::isPresent).count();
    }

    @VisibleForTesting
    public Optional<SpreadPos> m_221637_(BlockState p_221638_, LevelAccessor p_221639_, BlockPos p_221640_, Direction p_221641_, Direction p_221642_, boolean p_221643_) {
        return this.m_221612_(p_221638_, p_221639_, p_221640_, p_221641_, p_221642_, this.f_221587_::m_213973_).flatMap(p_221600_ -> this.m_221593_(p_221639_, (SpreadPos)p_221600_, p_221643_));
    }

    public Optional<SpreadPos> m_221612_(BlockState p_221613_, BlockGetter p_221614_, BlockPos p_221615_, Direction p_221616_, Direction p_221617_, SpreadPredicate p_221618_) {
        if (p_221617_.m_122434_() == p_221616_.m_122434_()) {
            return Optional.empty();
        }
        if (!(this.f_221587_.m_214107_(p_221613_) || this.f_221587_.m_221711_(p_221613_, p_221616_) && !this.f_221587_.m_221711_(p_221613_, p_221617_))) {
            return Optional.empty();
        }
        for (SpreadType $$6 : this.f_221587_.m_214109_()) {
            SpreadPos $$7 = $$6.m_213941_(p_221615_, p_221617_, p_221616_);
            if (!p_221618_.m_221728_(p_221614_, p_221615_, $$7)) continue;
            return Optional.of($$7);
        }
        return Optional.empty();
    }

    public Optional<SpreadPos> m_221593_(LevelAccessor p_221594_, SpreadPos p_221595_, boolean p_221596_) {
        BlockState $$3 = p_221594_.m_8055_(p_221595_.f_221717_());
        if (this.f_221587_.m_221701_(p_221594_, p_221595_, $$3, p_221596_)) {
            return Optional.of(p_221595_);
        }
        return Optional.empty();
    }

    public static class DefaultSpreaderConfig
    implements SpreadConfig {
        protected MultifaceBlock f_221681_;

        public DefaultSpreaderConfig(MultifaceBlock p_221683_) {
            this.f_221681_ = p_221683_;
        }

        @Override
        @Nullable
        public BlockState m_214136_(BlockState p_221694_, BlockGetter p_221695_, BlockPos p_221696_, Direction p_221697_) {
            return this.f_221681_.m_153940_(p_221694_, p_221695_, p_221696_, p_221697_);
        }

        protected boolean m_213938_(BlockGetter p_221688_, BlockPos p_221689_, BlockPos p_221690_, Direction p_221691_, BlockState p_221692_) {
            return p_221692_.m_60795_() || p_221692_.m_60713_(this.f_221681_) || p_221692_.m_60713_(Blocks.f_49990_) && p_221692_.m_60819_().m_76170_();
        }

        @Override
        public boolean m_213973_(BlockGetter p_221685_, BlockPos p_221686_, SpreadPos p_221687_) {
            BlockState $$3 = p_221685_.m_8055_(p_221687_.f_221717_());
            return this.m_213938_(p_221685_, p_221686_, p_221687_.f_221717_(), p_221687_.f_221718_(), $$3) && this.f_221681_.m_221571_(p_221685_, $$3, p_221687_.f_221717_(), p_221687_.f_221718_());
        }
    }

    public static interface SpreadConfig {
        @Nullable
        public BlockState m_214136_(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4);

        public boolean m_213973_(BlockGetter var1, BlockPos var2, SpreadPos var3);

        default public SpreadType[] m_214109_() {
            return f_221586_;
        }

        default public boolean m_221711_(BlockState p_221712_, Direction p_221713_) {
            return MultifaceBlock.m_153900_(p_221712_, p_221713_);
        }

        default public boolean m_214107_(BlockState p_221706_) {
            return false;
        }

        default public boolean m_221714_(BlockState p_221715_, Direction p_221716_) {
            return this.m_214107_(p_221715_) || this.m_221711_(p_221715_, p_221716_);
        }

        default public boolean m_221701_(LevelAccessor p_221702_, SpreadPos p_221703_, BlockState p_221704_, boolean p_221705_) {
            BlockState $$4 = this.m_214136_(p_221704_, p_221702_, p_221703_.f_221717_(), p_221703_.f_221718_());
            if ($$4 != null) {
                if (p_221705_) {
                    p_221702_.m_46865_(p_221703_.f_221717_()).m_8113_(p_221703_.f_221717_());
                }
                return p_221702_.m_7731_(p_221703_.f_221717_(), $$4, 2);
            }
            return false;
        }
    }

    @FunctionalInterface
    public static interface SpreadPredicate {
        public boolean m_221728_(BlockGetter var1, BlockPos var2, SpreadPos var3);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class SpreadType
    extends Enum<SpreadType> {
        public static final /* enum */ SpreadType SAME_POSITION = new SpreadType(){

            @Override
            public SpreadPos m_213941_(BlockPos p_221751_, Direction p_221752_, Direction p_221753_) {
                return new SpreadPos(p_221751_, p_221752_);
            }
        };
        public static final /* enum */ SpreadType SAME_PLANE = new SpreadType(){

            @Override
            public SpreadPos m_213941_(BlockPos p_221758_, Direction p_221759_, Direction p_221760_) {
                return new SpreadPos(p_221758_.m_121945_(p_221759_), p_221760_);
            }
        };
        public static final /* enum */ SpreadType WRAP_AROUND = new SpreadType(){

            @Override
            public SpreadPos m_213941_(BlockPos p_221765_, Direction p_221766_, Direction p_221767_) {
                return new SpreadPos(p_221765_.m_121945_(p_221766_).m_121945_(p_221767_), p_221766_.m_122424_());
            }
        };
        private static final /* synthetic */ SpreadType[] $VALUES;

        public static SpreadType[] values() {
            return (SpreadType[])$VALUES.clone();
        }

        public static SpreadType valueOf(String p_221745_) {
            return Enum.valueOf(SpreadType.class, p_221745_);
        }

        public abstract SpreadPos m_213941_(BlockPos var1, Direction var2, Direction var3);

        private static /* synthetic */ SpreadType[] m_221740_() {
            return new SpreadType[]{SAME_POSITION, SAME_PLANE, WRAP_AROUND};
        }

        static {
            $VALUES = SpreadType.m_221740_();
        }
    }

    public record SpreadPos(BlockPos f_221717_, Direction f_221718_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SpreadPos.class, "pos;face", "f_221717_", "f_221718_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SpreadPos.class, "pos;face", "f_221717_", "f_221718_"}, this);
        }

        @Override
        public final boolean equals(Object p_221725_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SpreadPos.class, "pos;face", "f_221717_", "f_221718_"}, this, p_221725_);
        }
    }
}

