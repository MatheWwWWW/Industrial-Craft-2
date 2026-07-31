/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.portal;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;

public class PortalShape {
    private static final int f_164752_ = 2;
    public static final int f_164750_ = 21;
    private static final int f_164753_ = 3;
    public static final int f_164751_ = 21;
    private static final BlockBehaviour.StatePredicate f_77685_ = (p_77720_, p_77721_, p_77722_) -> p_77720_.m_60713_(Blocks.f_50080_);
    private final LevelAccessor f_77686_;
    private final Direction.Axis f_77687_;
    private final Direction f_77688_;
    private int f_77689_;
    @Nullable
    private BlockPos f_77690_;
    private int f_77691_;
    private final int f_77692_;

    public static Optional<PortalShape> m_77708_(LevelAccessor p_77709_, BlockPos p_77710_, Direction.Axis p_77711_) {
        return PortalShape.m_77712_(p_77709_, p_77710_, p_77727_ -> p_77727_.m_77698_() && p_77727_.f_77689_ == 0, p_77711_);
    }

    public static Optional<PortalShape> m_77712_(LevelAccessor p_77713_, BlockPos p_77714_, Predicate<PortalShape> p_77715_, Direction.Axis p_77716_) {
        Optional<PortalShape> $$4 = Optional.of(new PortalShape(p_77713_, p_77714_, p_77716_)).filter(p_77715_);
        if ($$4.isPresent()) {
            return $$4;
        }
        Direction.Axis $$5 = p_77716_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        return Optional.of(new PortalShape(p_77713_, p_77714_, $$5)).filter(p_77715_);
    }

    public PortalShape(LevelAccessor p_77695_, BlockPos p_77696_, Direction.Axis p_77697_) {
        this.f_77686_ = p_77695_;
        this.f_77687_ = p_77697_;
        this.f_77688_ = p_77697_ == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.f_77690_ = this.m_77733_(p_77696_);
        if (this.f_77690_ == null) {
            this.f_77690_ = p_77696_;
            this.f_77692_ = 1;
            this.f_77691_ = 1;
        } else {
            this.f_77692_ = this.m_77745_();
            if (this.f_77692_ > 0) {
                this.f_77691_ = this.m_77746_();
            }
        }
    }

    @Nullable
    private BlockPos m_77733_(BlockPos p_77734_) {
        int $$1 = Math.max(this.f_77686_.m_141937_(), p_77734_.m_123342_() - 21);
        while (p_77734_.m_123342_() > $$1 && PortalShape.m_77717_(this.f_77686_.m_8055_(p_77734_.m_7495_()))) {
            p_77734_ = p_77734_.m_7495_();
        }
        Direction $$2 = this.f_77688_.m_122424_();
        int $$3 = this.m_77735_(p_77734_, $$2) - 1;
        if ($$3 < 0) {
            return null;
        }
        return p_77734_.m_5484_($$2, $$3);
    }

    private int m_77745_() {
        int $$0 = this.m_77735_(this.f_77690_, this.f_77688_);
        if ($$0 < 2 || $$0 > 21) {
            return 0;
        }
        return $$0;
    }

    private int m_77735_(BlockPos p_77736_, Direction p_77737_) {
        BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
        for (int $$3 = 0; $$3 <= 21; ++$$3) {
            $$2.m_122190_(p_77736_).m_122175_(p_77737_, $$3);
            BlockState $$4 = this.f_77686_.m_8055_($$2);
            if (!PortalShape.m_77717_($$4)) {
                if (!f_77685_.m_61035_($$4, this.f_77686_, $$2)) break;
                return $$3;
            }
            BlockState $$5 = this.f_77686_.m_8055_($$2.m_122173_(Direction.DOWN));
            if (!f_77685_.m_61035_($$5, this.f_77686_, $$2)) break;
        }
        return 0;
    }

    private int m_77746_() {
        BlockPos.MutableBlockPos $$0 = new BlockPos.MutableBlockPos();
        int $$1 = this.m_77728_($$0);
        if ($$1 < 3 || $$1 > 21 || !this.m_77730_($$0, $$1)) {
            return 0;
        }
        return $$1;
    }

    private boolean m_77730_(BlockPos.MutableBlockPos p_77731_, int p_77732_) {
        for (int $$2 = 0; $$2 < this.f_77692_; ++$$2) {
            BlockPos.MutableBlockPos $$3 = p_77731_.m_122190_(this.f_77690_).m_122175_(Direction.UP, p_77732_).m_122175_(this.f_77688_, $$2);
            if (f_77685_.m_61035_(this.f_77686_.m_8055_($$3), this.f_77686_, $$3)) continue;
            return false;
        }
        return true;
    }

    private int m_77728_(BlockPos.MutableBlockPos p_77729_) {
        for (int $$1 = 0; $$1 < 21; ++$$1) {
            p_77729_.m_122190_(this.f_77690_).m_122175_(Direction.UP, $$1).m_122175_(this.f_77688_, -1);
            if (!f_77685_.m_61035_(this.f_77686_.m_8055_(p_77729_), this.f_77686_, p_77729_)) {
                return $$1;
            }
            p_77729_.m_122190_(this.f_77690_).m_122175_(Direction.UP, $$1).m_122175_(this.f_77688_, this.f_77692_);
            if (!f_77685_.m_61035_(this.f_77686_.m_8055_(p_77729_), this.f_77686_, p_77729_)) {
                return $$1;
            }
            for (int $$2 = 0; $$2 < this.f_77692_; ++$$2) {
                p_77729_.m_122190_(this.f_77690_).m_122175_(Direction.UP, $$1).m_122175_(this.f_77688_, $$2);
                BlockState $$3 = this.f_77686_.m_8055_(p_77729_);
                if (!PortalShape.m_77717_($$3)) {
                    return $$1;
                }
                if (!$$3.m_60713_(Blocks.f_50142_)) continue;
                ++this.f_77689_;
            }
        }
        return 21;
    }

    private static boolean m_77717_(BlockState p_77718_) {
        return p_77718_.m_60795_() || p_77718_.m_204336_(BlockTags.f_13076_) || p_77718_.m_60713_(Blocks.f_50142_);
    }

    public boolean m_77698_() {
        return this.f_77690_ != null && this.f_77692_ >= 2 && this.f_77692_ <= 21 && this.f_77691_ >= 3 && this.f_77691_ <= 21;
    }

    public void m_77743_() {
        BlockState $$0 = (BlockState)Blocks.f_50142_.m_49966_().m_61124_(NetherPortalBlock.f_54904_, this.f_77687_);
        BlockPos.m_121940_(this.f_77690_, this.f_77690_.m_5484_(Direction.UP, this.f_77691_ - 1).m_5484_(this.f_77688_, this.f_77692_ - 1)).forEach(p_77725_ -> this.f_77686_.m_7731_((BlockPos)p_77725_, $$0, 18));
    }

    public boolean m_77744_() {
        return this.m_77698_() && this.f_77689_ == this.f_77692_ * this.f_77691_;
    }

    public static Vec3 m_77738_(BlockUtil.FoundRectangle p_77739_, Direction.Axis p_77740_, Vec3 p_77741_, EntityDimensions p_77742_) {
        double $$12;
        double $$9;
        double $$4 = (double)p_77739_.f_124349_ - (double)p_77742_.f_20377_;
        double $$5 = (double)p_77739_.f_124350_ - (double)p_77742_.f_20378_;
        BlockPos $$6 = p_77739_.f_124348_;
        if ($$4 > 0.0) {
            float $$7 = (float)$$6.m_123304_(p_77740_) + p_77742_.f_20377_ / 2.0f;
            double $$8 = Mth.m_14008_(Mth.m_14112_(p_77741_.m_82507_(p_77740_) - (double)$$7, 0.0, $$4), 0.0, 1.0);
        } else {
            $$9 = 0.5;
        }
        if ($$5 > 0.0) {
            Direction.Axis $$10 = Direction.Axis.Y;
            double $$11 = Mth.m_14008_(Mth.m_14112_(p_77741_.m_82507_($$10) - (double)$$6.m_123304_($$10), 0.0, $$5), 0.0, 1.0);
        } else {
            $$12 = 0.0;
        }
        Direction.Axis $$13 = p_77740_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double $$14 = p_77741_.m_82507_($$13) - ((double)$$6.m_123304_($$13) + 0.5);
        return new Vec3($$9, $$12, $$14);
    }

    public static PortalInfo m_77699_(ServerLevel p_77700_, BlockUtil.FoundRectangle p_77701_, Direction.Axis p_77702_, Vec3 p_77703_, EntityDimensions p_77704_, Vec3 p_77705_, float p_77706_, float p_77707_) {
        BlockPos $$8 = p_77701_.f_124348_;
        BlockState $$9 = p_77700_.m_8055_($$8);
        Direction.Axis $$10 = $$9.m_61145_(BlockStateProperties.f_61364_).orElse(Direction.Axis.X);
        double $$11 = p_77701_.f_124349_;
        double $$12 = p_77701_.f_124350_;
        int $$13 = p_77702_ == $$10 ? 0 : 90;
        Vec3 $$14 = p_77702_ == $$10 ? p_77705_ : new Vec3(p_77705_.f_82481_, p_77705_.f_82480_, -p_77705_.f_82479_);
        double $$15 = (double)p_77704_.f_20377_ / 2.0 + ($$11 - (double)p_77704_.f_20377_) * p_77703_.m_7096_();
        double $$16 = ($$12 - (double)p_77704_.f_20378_) * p_77703_.m_7098_();
        double $$17 = 0.5 + p_77703_.m_7094_();
        boolean $$18 = $$10 == Direction.Axis.X;
        Vec3 $$19 = new Vec3((double)$$8.m_123341_() + ($$18 ? $$15 : $$17), (double)$$8.m_123342_() + $$16, (double)$$8.m_123343_() + ($$18 ? $$17 : $$15));
        return new PortalInfo($$19, $$14, p_77706_ + (float)$$13, p_77707_);
    }
}

