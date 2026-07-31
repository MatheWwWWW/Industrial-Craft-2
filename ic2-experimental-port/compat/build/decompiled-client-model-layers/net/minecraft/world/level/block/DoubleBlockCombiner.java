/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class DoubleBlockCombiner {
    public static <S extends BlockEntity> NeighborCombineResult<S> m_52822_(BlockEntityType<S> p_52823_, Function<BlockState, BlockType> p_52824_, Function<BlockState, Direction> p_52825_, DirectionProperty p_52826_, BlockState p_52827_, LevelAccessor p_52828_, BlockPos p_52829_, BiPredicate<LevelAccessor, BlockPos> p_52830_) {
        BlockType $$14;
        boolean $$11;
        S $$8 = p_52823_.m_58949_(p_52828_, p_52829_);
        if ($$8 == null) {
            return Combiner::m_6502_;
        }
        if (p_52830_.test(p_52828_, p_52829_)) {
            return Combiner::m_6502_;
        }
        BlockType $$9 = p_52824_.apply(p_52827_);
        boolean $$10 = $$9 == BlockType.SINGLE;
        boolean bl = $$11 = $$9 == BlockType.FIRST;
        if ($$10) {
            return new NeighborCombineResult.Single<S>($$8);
        }
        BlockPos $$12 = p_52829_.m_121945_(p_52825_.apply(p_52827_));
        BlockState $$13 = p_52828_.m_8055_($$12);
        if ($$13.m_60713_(p_52827_.m_60734_()) && ($$14 = p_52824_.apply($$13)) != BlockType.SINGLE && $$9 != $$14 && $$13.m_61143_(p_52826_) == p_52827_.m_61143_(p_52826_)) {
            if (p_52830_.test(p_52828_, $$12)) {
                return Combiner::m_6502_;
            }
            S $$15 = p_52823_.m_58949_(p_52828_, $$12);
            if ($$15 != null) {
                S $$16 = $$11 ? $$8 : $$15;
                S $$17 = $$11 ? $$15 : $$8;
                return new NeighborCombineResult.Double<S>($$16, $$17);
            }
        }
        return new NeighborCombineResult.Single<S>($$8);
    }

    public static interface NeighborCombineResult<S> {
        public <T> T m_5649_(Combiner<? super S, T> var1);

        public static final class Single<S>
        implements NeighborCombineResult<S> {
            private final S f_52853_;

            public Single(S p_52855_) {
                this.f_52853_ = p_52855_;
            }

            @Override
            public <T> T m_5649_(Combiner<? super S, T> p_52857_) {
                return p_52857_.m_7693_(this.f_52853_);
            }
        }

        public static final class Double<S>
        implements NeighborCombineResult<S> {
            private final S f_52846_;
            private final S f_52847_;

            public Double(S p_52849_, S p_52850_) {
                this.f_52846_ = p_52849_;
                this.f_52847_ = p_52850_;
            }

            @Override
            public <T> T m_5649_(Combiner<? super S, T> p_52852_) {
                return p_52852_.m_6959_(this.f_52846_, this.f_52847_);
            }
        }
    }

    public static final class BlockType
    extends Enum<BlockType> {
        public static final /* enum */ BlockType SINGLE = new BlockType();
        public static final /* enum */ BlockType FIRST = new BlockType();
        public static final /* enum */ BlockType SECOND = new BlockType();
        private static final /* synthetic */ BlockType[] $VALUES;

        public static BlockType[] values() {
            return (BlockType[])$VALUES.clone();
        }

        public static BlockType valueOf(String p_52840_) {
            return Enum.valueOf(BlockType.class, p_52840_);
        }

        private static /* synthetic */ BlockType[] m_153172_() {
            return new BlockType[]{SINGLE, FIRST, SECOND};
        }

        static {
            $VALUES = BlockType.m_153172_();
        }
    }

    public static interface Combiner<S, T> {
        public T m_6959_(S var1, S var2);

        public T m_7693_(S var1);

        public T m_6502_();
    }
}

